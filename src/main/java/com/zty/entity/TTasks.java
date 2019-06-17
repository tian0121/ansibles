package com.zty.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_TASKS")
public class TTasks implements Serializable {
    @Id
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
    @Transient
    List<TServers> tServersList;
    @Transient
    List<TMetrics> tMetricsList;
    @Transient
    List<TaskRunningLogs> taskRunningLogsList;
}
