package com.zty.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.zty.Mapper.ServersMetricMapper;
import com.zty.Mapper.TMetricsMapper;
import com.zty.Mapper.TaskMetricMapper;
import com.zty.Mapper.TreeDataMapper;
import com.zty.Service.TMetricsService;
import com.zty.dto.TMetricsDto;
import com.zty.dto.TServerDto;
import com.zty.entity.Pagination;
import com.zty.entity.TMetrics;
import com.zty.entity.TaskMetric;
import com.zty.entity.TreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TMetricsServiceImpl implements TMetricsService {
    @Autowired
    private TMetricsMapper tMetricsMapper;
    /*

    //添加 /修改
    /
     */
    public JSONObject addTMetrics(TMetrics tMetrics){
       try{
           if(tMetrics.getMetricId()==null){
               tMetrics.setCreateDate(new Date());    //设置创建时间
               tMetrics.setCreator("zhangsan");       //设置创建者
               tMetricsMapper.addTMetrics(tMetrics);  //插入指标
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("resultCode","0");
               jsonObject.put("msg","success");
               return  jsonObject;
           }
           TMetrics tMetrics1 = tMetricsMapper.selectByPrimaryKey(tMetrics); //根据具有id的对象查询数据库中的对象
           tMetrics.setCreateDate(tMetrics1.getCreateDate());    //给新对象设置数据库中对象的创建时间
           tMetrics.setCreator(tMetrics1.getCreator());      //给新对象设置数据库中对象的创建者
           tMetricsMapper.updateByPrimaryKey(tMetrics);   //更新数据库中的数据
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("resultCode","0");
           jsonObject.put("msg","success");
           return jsonObject;

       }catch (Exception e){
           e.printStackTrace();
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("resultCode","1");
           jsonObject.put("msg","指标插入失败");  //失败返回
           return jsonObject;
       }
    }

    @Autowired
    private TaskMetricMapper taskMetricMapper;
    @Override
    public JSONObject deleteTMetrics(Integer id) {
        try{
            TMetrics tMetrics = new TMetrics();
            tMetrics.setMetricId(id);
            TaskMetric taskMetric = new TaskMetric();
            taskMetric.setMetricId(id);
            List<TaskMetric> taskMetricsList = taskMetricMapper.queryTaskMetricByMetricId(id);
            if(taskMetricsList!=null){//删除指标的同时删除任务指标表中的数据
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resultCode","1");
                jsonObject.put("msg","您有未删除的任务在使用该指标");
                return jsonObject;
            }
            taskMetricMapper.deleteByExample(taskMetric);  //删除任务指标表中的信息
            tMetricsMapper.deleteByPrimaryKey(tMetrics);  //通过id 删除指标
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","0");
            jsonObject.put("msg","success");
            return  jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","删除指标失败");
            return jsonObject;
        }

    }
    @Override
    public TMetricsDto queryTMetrics(Integer Page, Integer PageSize, String metricName,String metricType) {
        Integer total = tMetricsMapper.queryTotalNoArgs();    //没有条件的时候默认为全表计数
        if(metricName!=null|metricType!=null){
             total =  tMetricsMapper.queryTotal(metricName,metricType);  //有条件时是条件计数
        }
        List<TMetrics> list = tMetricsMapper.pageRows(Page,PageSize,metricName,metricType);
        Pagination pagination = new Pagination(Page,PageSize,total);   //设置page pagesize等
        TMetricsDto tMetricsDto = new TMetricsDto(list,pagination);    //返回tMetricsDto
        return tMetricsDto;
    }

    @Override
    public List<TMetrics> queryTMetricsByCatagoryList(Integer Catagory) {
        TMetrics tMetrics = new TMetrics();  //
        tMetrics.setMetricCatagory(Catagory);
        return tMetricsMapper.select(tMetrics);   //通过类别查询指标
    }

    @Override
    public List<TMetrics> queryAllTMetrics() {   //查询所有的指标
        try{
            return tMetricsMapper.selectAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
    }
    @Override
    public List<TreeData> queryTreeDate() {
        try {
            List<TMetrics> list = tMetricsMapper.queryAllCatagory();  //通过分类查询类别
            List<TreeData> list2 = new ArrayList<>();
            for (Integer i = 0; i < list.size(); i++) {
                TreeData treeData = new TreeData();      //开始封装Treedata
                List<TMetrics> list1 = tMetricsMapper.select(list.get(i));       //查询该类的对象从而得到指标名称
                for (TMetrics tMetrics : list1) {
                    treeData.setTitle(tMetrics.getMetricName());   //设置指标Name
                }
                //通过指标映射到treedata上 list 通过类别查询list
                List<TreeData> treeDataList = tMetricsMapper.queryMetricByCatagory(list.get(i).getMetricCatagory());
                treeData.setKey(i);                   //设置key
                treeData.setValue(i);                 // 设置value
                treeData.setChildren(treeDataList);  //通过指标查询的集合
                list2.add(treeData);                  //将treedata放入list中
            }
            return list2;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
    }

    @Override
    public List<TMetrics> queryMetrics(Integer taskType) {
        try{
            List<TMetrics> list  = tMetricsMapper.queryMetrics(taskType);
            return list;
        }catch (Exception e){
            throw new RuntimeException("任务类型查询失败");
        }


    }
}
