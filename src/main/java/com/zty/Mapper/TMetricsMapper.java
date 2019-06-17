package com.zty.Mapper;
import com.zty.entity.TMetrics;
import com.zty.entity.TreeData;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
public interface TMetricsMapper extends Mapper<TMetrics> {
    public void addTMetrics(TMetrics tMetrics);
    public Integer queryTotal(@Param("metricName")String metricName,@Param("metricType") String metricType);
    public List<TMetrics> pageRows(@Param("Page") Integer Page, @Param("PageSize")Integer PageSize, @Param("metricName") String metricName,@Param("metricType")String metricType);
    public Integer queryTotalNoArgs();
    public List<TMetrics> queryAllCatagory();
    public List<TreeData> queryMetricByCatagory(Integer cataGory);
    public List<TMetrics> queryMetrics(Integer taskType);
}
