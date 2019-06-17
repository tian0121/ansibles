package com.zty.Mapper;

import com.zty.entity.TasksAndServers;
import com.zty.entity.TMetrics;
import com.zty.entity.TTasks;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TTaskMapper extends Mapper<TTasks> {
    //添加任务
    public void addTask(TTasks tTasks);
    //
    public List<TasksAndServers> queryTasksServer(@Param("Page") Integer Page, @Param("PageSize")Integer PageSize,@Param("executeState")Integer executeState,@Param("taskName")String taskName);

    public Integer queryTotal(@Param("Page") Integer Page, @Param("PageSize")Integer PageSize,@Param("executeState")Integer executeState,@Param("taskName")String taskName);
    public List<TMetrics> queryTasksMetrics(Integer taskId);
    public Integer queryTotalNoArgs();
    public TTasks updateExecuteCounts(TTasks tasks);
}
