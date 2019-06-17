package com.zty.Controller;

import com.alibaba.fastjson.JSONObject;
import com.zty.Service.TaskServerService;
import com.zty.dto.TaskDto;
import com.zty.entity.TTasks;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskServerService taskServerService;
    @RequestMapping("addOrUpdateTask")
    public JSONObject addOrUpdateTask(@RequestBody JSONObject jsonObject){
        try{
            String taskId = jsonObject.getString("taskId");
            System.out.println(taskId);
            String taskName=jsonObject.getString("taskName");
            String taskType = jsonObject.getString("taskType");
            String[] metricArray = jsonObject.getString("metrics").replaceAll("\\[","").replaceAll("\\]","").replace(" ","").split(",");
            String[] serverArray = jsonObject.getString("servers").replaceAll("\\[","").replaceAll("\\]","").replace(" ","").split(",");
            System.out.println(metricArray);
            System.out.println(serverArray);
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            for (int i = 0; i < serverArray.length; i++) {
                Integer s = Integer.valueOf(serverArray[i]);
                list1.add(s);
            }
            for (int i = 0; i < metricArray.length; i++) {
                Integer s = Integer.valueOf(metricArray[i]);
               list2.add(s);
            }
            Integer[] metric = list2.toArray(new Integer[list2.size()]);
            Integer[] server = list1.toArray(new Integer[list1.size()]);//能正确运行
            for (Integer integer : server) {
                System.out.println(integer);
            }
            TTasks tTasks = new TTasks();
            tTasks.setTaskType(Integer.valueOf(taskType));
            tTasks.setTaskName(taskName);
            if(taskId!=null){
                tTasks.setTaskId(Integer.valueOf(taskId));
            }
            for (Integer integer : metric) {
                System.out.println(integer);
            }
            return taskServerService.addOrUpdateTask(tTasks,server,metric);
        }catch (Exception e){
            JSONObject jsonObject1 = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","编辑失败");
            return jsonObject1;
        }
    }
    @RequestMapping("queryTaskList")
    public TaskDto queryTasksList(@RequestParam(defaultValue = "1")Integer currentPage, @RequestParam(defaultValue = "5")Integer pageSize,Integer executeState,String taskName){
        try{
            System.out.println(pageSize);
            System.out.println(currentPage);
            return taskServerService.queryTask(currentPage,pageSize,executeState,taskName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("deleteTaskById")
    public JSONObject deleteTaskById(@RequestParam("id")Integer id){
        System.out.println(id);
        try{
            return taskServerService.deleteTask(id);
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","编辑失败");
            return jsonObject;
        }
    }
}
