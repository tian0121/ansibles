package com.zty.Controller;

import com.alibaba.fastjson.JSONObject;
import com.zty.Service.TMetricsService;
import com.zty.dto.TMetricsDto;
import com.zty.entity.TMetrics;
import com.zty.entity.TServers;
import com.zty.entity.TreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("metrics")
public class TMetricsController {
    @Autowired
    private TMetricsService tMetricsService;
    @RequestMapping("addOrUpdateMetric")
    public JSONObject addTMetrics(@RequestBody TMetrics tMetrics){
        System.out.println(tMetrics);
        return tMetricsService.addTMetrics(tMetrics);
    }
    @RequestMapping("deleteMetricById")
    public JSONObject deleteTMetrics(@RequestParam Integer id){
        System.out.println(id);
        return tMetricsService.deleteTMetrics(id);
    }
    @RequestMapping("queryMetricList")
    public TMetricsDto queryTMetricsList(@RequestParam(defaultValue = "1")Integer currentPage, @RequestParam(defaultValue = "5")Integer pageSize,String metricName,String metricType){
        System.out.println(metricName);
        System.out.println(metricType);
        return tMetricsService.queryTMetrics(currentPage,pageSize,metricName,metricType);
    }
    @RequestMapping("queryTMetricsByCatagoryList")
    public Object queryTMetricsByCatagoryList(Integer Catagory){
        System.out.println(Catagory);
       try{
           List<TMetrics> list = tMetricsService.queryTMetricsByCatagoryList(Catagory);
           if(list!=null){
               return list;
           }
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("resultCode","1");
           jsonObject.put("msg","查不到该分类数据");
           return jsonObject;
       }catch (Exception e){
            e.printStackTrace();
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("resultCode","1");
           jsonObject.put("msg","分类查询失败，请稍后再试");
           return jsonObject;
       }

    }

    @RequestMapping("/queryAllMetricByMetricCatagory")
    public JSONObject queryAllTMetrics(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","0");
            jsonObject.put("msg","success");
            jsonObject.put("data",tMetricsService.queryTreeDate());
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
    @RequestMapping("queryMetricListByTaskType")
    public JSONObject queryAllMetricByTaskType(@RequestParam("taskType") Integer taskType){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","0");
            jsonObject.put("msg","success");
            jsonObject.put("data",tMetricsService.queryMetrics(taskType));
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
