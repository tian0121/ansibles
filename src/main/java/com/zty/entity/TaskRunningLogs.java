package com.zty.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_TASK_RUNNING_LOGS")
public class TaskRunningLogs implements Serializable {
    private Integer taskId;
    private Integer executeState;
    private Integer elapsedSeconds;
    private String taskFailureMsg;
    private String taskResult;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date executeDate;
    private String taskName;
}
