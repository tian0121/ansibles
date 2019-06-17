package com.zty.Service;

import com.alibaba.fastjson.JSONObject;
import com.zty.dto.RunningLogsDto;
import com.zty.dto.TaskDto;
import com.zty.entity.TTasks;
import com.zty.entity.TaskRunningLogs;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface TaskServerService {
    public JSONObject addOrUpdateTask(TTasks tTasks,Integer[] server , Integer[] metric);
    public TaskDto queryTask(Integer Page, Integer PageSize,Integer executeState,String taskType);
    public JSONObject deleteTask(Integer id);


    public RunningLogsDto queryTaskRunningLogs(Integer Page, Integer PageSize, Integer id);
}
