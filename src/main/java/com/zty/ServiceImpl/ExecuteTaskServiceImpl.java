package com.zty.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.zty.Mapper.*;
import com.zty.Service.ExecuteTaskService;
import com.zty.entity.*;
import com.zty.util.AnsibleShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ExecuteTaskServiceImpl implements ExecuteTaskService {
    @Autowired
    private TaskServersMapper taskServersMapper;
    @Autowired
    private TaskMetricMapper taskMetricMapper;
    @Autowired
    private TMetricsMapper tMetricsMapper;
    @Autowired
    private TaskRunningLogsMapper taskRunningLogsMapper;
    @Autowired
    private TTaskMapper tTaskMapper;
    @Override
    public List<JSONObject> executeTask(Integer id) {
        try{
            //任务下发
            //先通过任务id查询相关的数据
            TaskServer taskServer = new TaskServer();
            taskServer.setTaskId(id);  //通过id查询到所关联的服务器
            TTasks tTasks = new TTasks();
            tTasks.setTaskId(id);
            TTasks tTasksDetail = tTaskMapper.selectByPrimaryKey(tTasks);
            tTasksDetail.setExecuteCounts(tTasksDetail.getExecuteCounts()+1);    //任务次数+1
            List<TaskServer> taskServerList = taskServersMapper.queryTask(id);//根据id拿到一堆ip 和服务器名称
            for (TaskServer server : taskServerList) {
                AnsibleShellUtil.appened(server.getIpAddr());   //追加ip到hosts
                AnsibleShellUtil.sendSSH(server.getIpAddr());    //发送ssh到管理主机
            }
            //通过任务id去拿指标
            List<TaskMetric> list= taskMetricMapper.queryTaskMetricByMetricId(id); //拿到一堆指标的集合
            //遍历指标集合
            List<TMetrics> list2 = new ArrayList<>();
            for (TaskMetric metric : list) {
                TMetrics tMetrics = new TMetrics();
                tMetrics.setMetricId(metric.getMetricId());
                TMetrics  tMetrics1 = tMetricsMapper.selectByPrimaryKey(tMetrics); //每通过一个指标id  拿到一个指标
                list2.add(tMetrics1);//将拿到的指标对象放到list中
            }
            //根据指标类型执行命令
            List<JSONObject> resultList =  new ArrayList<>();
            //计算任务的执行耗时
            Long time = 0L;
            for (TMetrics tMetrics : list2) {
                //单个ip单个指标执行
                for (TaskServer server : taskServerList) {
                    if (tMetrics.getMetricType()==1){   //操作系统指标
                        String command3 = "ansible " +server.getIpAddr() +" -m command -a " +tMetrics.getMetricCode();
                        long startTime=System.currentTimeMillis();   //获取开始时间
                        //以指标id和ip结合作为jsonObject的为唯一key
                        JSONObject jsonObject = AnsibleShellUtil.getResult(command3,tMetrics.getMetricId()+""+server.getIpAddr());
                        long endTime=System.currentTimeMillis(); //获取结束时间
                        Long elapsedSeconds = endTime-startTime; //   单个ip耗时
                        time = time + elapsedSeconds;            //如果是指标1 计算单个ip 耗时 并累加
                        ResultProcess(jsonObject,tMetrics,elapsedSeconds.intValue(),tTasksDetail,server.getIpAddr());
                        resultList.add(jsonObject);
                    }else if(tMetrics.getMetricType()==2){  //shell脚本
                        String command2 = "ansible " + server.getIpAddr() +" -m shell -a " +tMetrics.getMetricCode();
                        long startTime=System.currentTimeMillis();   //获取开始时间
                        JSONObject jsonObject  = AnsibleShellUtil.getResult(command2,tMetrics.getMetricId()+""+server.getIpAddr());
                        long endTime=System.currentTimeMillis(); //获取结束时间
                        Long elapsedSeconds = endTime-startTime; //耗时
                        ResultProcess(jsonObject,tMetrics,elapsedSeconds.intValue(),tTasksDetail,server.getIpAddr());
                        time = time + elapsedSeconds;            //如果是指标2 计算单个ip 耗时 并累加
                        resultList.add(jsonObject);
                    }else if(tMetrics.getMetricType()==3){
                        throw new RuntimeException("暂不支持");
                    }
                }
            }
            tTasksDetail.setElapsedSeconds(tTasksDetail.getElapsedSeconds()+time.intValue());
            tTaskMapper.updateByPrimaryKey(tTasksDetail);
            return resultList;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("失败");
        }
    }
    public void ResultProcess(JSONObject jsonObject, TMetrics tMetrics, Integer elapsedSeconds, TTasks tasks,String ip){
        if(jsonObject.getString(tMetrics.getMetricId()+""+ip)==null){
            TaskRunningLogs taskRunningLogs = getTaskRunningLogs(elapsedSeconds,tasks,0,new Date(),jsonObject,new Date(),tMetrics,ip);
            taskRunningLogsMapper.addTaskRunningLog(taskRunningLogs); //插入运行记录表
        }
        //ssh不对的
        String x = jsonObject.getString(tMetrics.getMetricId()+""+ip);
        List<Object> objectList = Arrays.asList(x.split(","));
        for (Object o : objectList) {
            if(o.toString().contains("UNREACHABLE")){
                jsonObject.put("unreachable","没有添加管理的机器"+ip);//没有添加管理的机器+ip
                TaskRunningLogs taskRunningLogs = getTaskRunningLogs(elapsedSeconds,tasks,0,new Date(),jsonObject,new Date(),tMetrics,ip);
                System.out.println(taskRunningLogs);
                taskRunningLogsMapper.addTaskRunningLog(taskRunningLogs); //插入运行记录表
            }
        }
        //success
        List<Object> successObjectList = Arrays.asList(x.split("\\n"));
        for (Object o : successObjectList) {
            System.out.println(o);
            if(o.toString().contains("SUCCESS")){
                jsonObject.put("Mark","SUCCESS");
                TaskRunningLogs taskRunningLogs = getTaskRunningLogs(elapsedSeconds,tasks,1,new Date(),jsonObject,new Date(),tMetrics,ip);
                taskRunningLogsMapper.addTaskRunningLog(taskRunningLogs); //插入运行记录表
            }
        }
    }
    public TaskRunningLogs getTaskRunningLogs(Integer elapsedSeconds, TTasks tasks, Integer executeState, Date executeDate, JSONObject jsonObject, Date createDate, TMetrics tMetrics, String ip){
        TaskRunningLogs taskRunningLogs = new TaskRunningLogs();
        taskRunningLogs.setTaskId(tasks.getTaskId());
        taskRunningLogs.setExecuteState(executeState);
        taskRunningLogs.setExecuteDate(executeDate);
        taskRunningLogs.setTaskName(tasks.getTaskName());
        taskRunningLogs.setCreateDate(createDate);
        taskRunningLogs.setElapsedSeconds(elapsedSeconds);
        taskRunningLogs.setTaskResult(jsonObject.getString(tMetrics.getMetricId()+""+ip));
        if(("没有添加管理的机器"+ip).equals(jsonObject.getString("unreachable"))){
            taskRunningLogs.setTaskFailureMsg("msg:"+ "Failed to connect to the host via ssh: ssh: connect to host" +  ip  +  " port 22: No route to host\r\n");
            TTasks tTaskDetail = new TTasks();
            tTaskDetail.setTaskId(tasks.getTaskId());
            TTasks tTaskResult = tTaskMapper.selectByPrimaryKey(tTaskDetail);
            tTaskResult.setLastFailureMsg(tTaskResult.getLastFailureMsg()+taskRunningLogs.getTaskFailureMsg());
        }
        if(jsonObject.getString(tMetrics.getMetricId()+""+ip)==null){
            //错误信息
            taskRunningLogs.setTaskFailureMsg("error");
            TTasks tTaskDetail = new TTasks();
            tTaskDetail.setTaskId(tasks.getTaskId());
            TTasks tTaskResult = tTaskMapper.selectByPrimaryKey(tTaskDetail);
            tTaskResult.setLastFailureMsg(tTaskResult.getLastFailureMsg()+taskRunningLogs.getTaskFailureMsg());
        }
        if("SUCCESS".equals(jsonObject.getString("Mark"))){
            taskRunningLogs.setTaskFailureMsg("当前没有失败");
        }
        return taskRunningLogs;
    }
}
