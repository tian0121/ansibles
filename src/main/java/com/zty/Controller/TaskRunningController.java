package com.zty.Controller;

import com.alibaba.fastjson.JSONObject;
import com.zty.Mapper.TTaskMapper;
import com.zty.Service.TaskServerService;
import com.zty.entity.TTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("running")
public class TaskRunningController {
    @Autowired
    TaskServerService taskServerService;
    @RequestMapping("queryTaskRunningLogs")
    public JSONObject queryTaskRunningLogs(@RequestParam("currentPage") Integer currentPage,@RequestParam("PageSize") Integer PageSize,Integer taskId){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","0");
            jsonObject.put("msg","查询成功");
            jsonObject.put("data",taskServerService.queryTaskRunningLogs(currentPage, PageSize, taskId));
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","查询失败");
            jsonObject.put("data",null);
            return jsonObject;
        }
    }
}
