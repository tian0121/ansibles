package com.zty.Mapper;

import com.zty.entity.ServerMetrics;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ServersMetricMapper extends Mapper<ServerMetrics> {
    public void addServersMetrics(ServerMetrics serverMetrics);
    public void deleteTaskMetric(Integer taskId);
}
