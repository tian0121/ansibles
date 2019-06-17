package com.zty.Mapper;

import com.zty.entity.TaskRunningLogs;
import tk.mybatis.mapper.common.Mapper;

public interface ExecuteTaskMapper extends Mapper<TaskRunningLogs>{
    public void addExecuteRunningLog(TaskRunningLogs taskRunningLogs);
}
