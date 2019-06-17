package com.zty.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TasksAndServers implements Serializable {
    private Integer taskId;
    private String taskName;
    private Integer taskType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date executeDate;
    private Integer executeState;
    private Integer elapsedSeconds;
    private Integer executeCounts;
    private String lastFailureMsg;
    private List<TServers> serverList;
    private List<TMetrics> metricList;
}
