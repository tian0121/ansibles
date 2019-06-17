package com.zty.ServiceImpl;

import com.alibaba.fastjson.JSONObject;

import com.zty.Mapper.*;
import com.zty.Service.TaskServerService;
import com.zty.dto.RunningLogsDto;
import com.zty.dto.TaskDto;
import com.zty.entity.TasksAndServers;
import com.zty.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskServerServiceImpl implements TaskServerService {
    @Autowired
    private TTaskMapper tTaskMapper;
    @Autowired
    private TaskServersMapper taskServersMapper;
    @Autowired
    private TServerMapper tServerMapper;
    @Autowired
    private ServersMetricMapper serversMetricMapper;
    @Override
    public JSONObject addOrUpdateTask(TTasks tTasks, Integer[] server, Integer[] metric) {
        try{
            if(tTasks.getTaskId()==null){       //判断任务id是否存在 不存在为新增，存在就是更新
            tTasks.setCreateDate(new Date());   //创建时间
            tTasks.setCreator("zhangsan");      //创建者
            tTasks.setExecuteCounts(0);         //执行次数
            tTasks.setExecuteDate(new Date());  //设置执行时间
            tTasks.setElapsedSeconds(35);       //设置耗时
            tTasks.setExecuteState(1);          //设置执行state
            tTasks.setLastFailureMsg("111111"); //设置最后一次失败原因，本应执行任务失败之后才更新该数据
            tTaskMapper.addTask(tTasks);        //插入任务   获取任务id
                //遍历该任务所分配服务器的id数组
                System.out.println(tTasks);
                System.out.println(server);
            for (Integer serverid : server) {
                TServers tServers = new TServers();
                tServers.setServerId(serverid);
                TServers tServers1 =  tServerMapper.selectByPrimaryKey(tServers);  //通过id查询该服务器
                TaskServer taskServer = new TaskServer();
                System.out.println(tServers1);
                taskServer.setServerName(tServers1.getServerName());   //设置服务器名称
                taskServer.setIpAddr(tServers1.getIpAddr());   //设置服务器ip
                taskServer.setTaskId(tTasks.getTaskId());      //设置任务id
                System.out.println(tTasks.getTaskId());
                taskServersMapper.addTaskServers(taskServer);   //持久化到任务服务器表
                System.out.println("你持久了嘛");
            }
            //遍历所分配的指标的id数组
            for (Integer metricId : metric) {
                ServerMetrics serverMetrics =new ServerMetrics(tTasks.getTaskId(),metricId);//设置任务id，指标的id
                serversMetricMapper.addServersMetrics(serverMetrics);   //持久化到任务指标表中
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode", "0");
            jsonObject.put("msg", "任务创建成功");
            return jsonObject;
            }
            //修改任务表
            TTasks tTasks1 = tTaskMapper.selectByPrimaryKey(tTasks);  //通过id查询task
            tTasks1.setTaskName(tTasks.getTaskName());                //设置新的任务名称
            tTasks1.setTaskType(tTasks.getTaskType());                //设置新的任务类型
            tTaskMapper.updateByPrimaryKey(tTasks1);                  //持久化到数据库中
            //删除与这个id相关的任务服务器表中的数据
            taskServersMapper.deleteTaskServer(tTasks.getTaskId());
            //重新添加与id相关的任务服务器中的数据
            for (Integer serverid : server) {
                TServers tServers = new TServers();
                tServers.setServerId(serverid);
                TServers tServers1 =  tServerMapper.selectByPrimaryKey(tServers);  //通过id查询该服务器
                TaskServer taskServer = new TaskServer();
                taskServer.setServerName(tServers1.getServerName());   //设置服务器名称
                taskServer.setIpAddr(tServers1.getIpAddr());   //设置服务器ip
                taskServer.setTaskId(tTasks.getTaskId());      //设置任务id
                taskServersMapper.addTaskServers(taskServer);   //持久化到任务服务器表
            }
            //删除与这个id相关的任务指标表中的数据
            serversMetricMapper.deleteTaskMetric(tTasks.getTaskId());
            //添加与这个id相关的任务指标表中的数据
            for (Integer metricId : metric) {
                ServerMetrics serverMetrics =new ServerMetrics(tTasks.getTaskId(),metricId);//设置任务id，指标的id
                serversMetricMapper.addServersMetrics(serverMetrics);   //持久化到任务指标表中
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode", "0");
            jsonObject.put("msg", "任务修改成功");
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("任务创建失败");
        }
    }

    @Override
    public TaskDto queryTask(Integer Page, Integer PageSize,Integer executeState,String taskName) {
        try{
            List<TasksAndServers> list = tTaskMapper.queryTasksServer(Page, PageSize,executeState,taskName);//查询list
            Integer total = tTaskMapper.queryTotal(Page, PageSize,executeState,taskName);// 查询数据总数，通过条件
            for (TasksAndServers tasksAndServers : list) {  //拿到任务关联的服务器的list
                //通过任务id查询指标
                List<TMetrics> list2 = tTaskMapper.queryTasksMetrics(tasksAndServers.getTaskId());
                //将指标集合放入对象中
                tasksAndServers.setMetricList(list2);
            }
            if(executeState==null&&taskName==null){
                total = tTaskMapper.queryTotalNoArgs(); //无条件的时候计算分页总数
            }
            Pagination pagination = new Pagination(Page,PageSize,total);//设置页数//设置每页几个 //设置总数
            TaskDto taskDto = new TaskDto(list,pagination);         //封装到Dto对象中
            return taskDto;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
    }

    @Override
    public JSONObject deleteTask(Integer id) {
        try{
            TTasks tTasks = new TTasks();
            tTasks.setTaskId(id);
            //先通过查询看任务服务器表有没有改任务的数据 任务指标表中有没有改任务数据
            if(taskServersMapper.queryTask(id)!=null | tTaskMapper.queryTasksMetrics(id)!=null){
                TaskServer taskServer = new TaskServer();
                taskServer.setTaskId(id);
                taskServersMapper.deleteTaskServer(id); //通过任务id删除任务服务器表中的相关数据
                ServerMetrics serverMetrics = new ServerMetrics();
                serverMetrics.setTaskId(id);
                serversMetricMapper.deleteTaskMetric(id);//通过任务id删除任务指标表中的相关数据
            }
            tTaskMapper.deleteByPrimaryKey(tTasks);               //删除任务
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode", "0");
            jsonObject.put("msg", "任务删除成功");
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
    }


    @Autowired
    private TaskRunningLogsMapper taskRunningLogsMapper;
    @Override
    public RunningLogsDto queryTaskRunningLogs(Integer Page,Integer PageSize,Integer id) {
        try{
            List<TTasks> tTasksList = taskRunningLogsMapper.queryTask(Page,PageSize);  //先查task的数量进行分页
            //遍历 拿到每一个任务的任务id
            for (TTasks tTasks : tTasksList) {
                //再通过id 查询该id所有的记录，并按照时间的先后顺序进行排序
                List<TaskRunningLogs> taskRunningLogsList = taskRunningLogsMapper.queryTaskRunningLogs(tTasks.getTaskId());
                tTasks.setTaskRunningLogsList(taskRunningLogsList);
            }
            Integer total  = null;
            if(id==null){
                total = taskRunningLogsMapper.queryTotal();
            }
            if(id!=null){
                total = 1;
            }
            Pagination pagination = new Pagination(Page,PageSize,total);
            RunningLogsDto runningLogsDto = new RunningLogsDto(tTasksList,pagination);
            return runningLogsDto;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
    }
}
