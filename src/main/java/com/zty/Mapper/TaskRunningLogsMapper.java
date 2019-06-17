package com.zty.Mapper;

import com.zty.entity.TTasks;
import com.zty.entity.TaskRunningLogs;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskRunningLogsMapper extends Mapper<TaskRunningLogs> {
    public void addTaskRunningLog(TaskRunningLogs taskRunningLogs);
    public Integer queryTotal();
    public List<TTasks> queryTask(@Param("Page")Integer Page, @Param("PageSize") Integer PageSize);
    public List<TaskRunningLogs> queryTaskRunningLogs(Integer taskId);
}
