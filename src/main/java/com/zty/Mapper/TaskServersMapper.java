package com.zty.Mapper;

import com.zty.entity.TaskServer;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskServersMapper extends Mapper<TaskServer> {
//添加任务服务器表
    public void addTaskServers(TaskServer taskServer);
//chanchu
    public List<TaskServer> queryTask(Integer taskId);
    public void deleteTaskServer(Integer taskId);
}
