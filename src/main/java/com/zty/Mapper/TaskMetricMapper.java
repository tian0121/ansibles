package com.zty.Mapper;

import com.zty.entity.TaskMetric;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskMetricMapper extends Mapper<TaskMetric> {
    public List<TaskMetric> queryTaskMetricByMetricId(Integer taskId);
}
