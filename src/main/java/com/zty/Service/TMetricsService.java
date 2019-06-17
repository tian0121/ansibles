package com.zty.Service;
import com.alibaba.fastjson.JSONObject;
import com.zty.dto.TMetricsDto;
import com.zty.entity.TMetrics;
import com.zty.entity.TreeData;


import java.util.List;

public interface TMetricsService{
    public JSONObject addTMetrics(TMetrics tMetrics);
    public JSONObject deleteTMetrics(Integer id);
    public TMetricsDto queryTMetrics(Integer Page, Integer PageSize, String metricName,String metricType);
    public List<TMetrics> queryTMetricsByCatagoryList(Integer Catagory);
    public List<TMetrics> queryAllTMetrics();
    public List<TreeData> queryTreeDate();
    public List<TMetrics> queryMetrics(Integer taskType);
}
