package com.zty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_TASK_METRICS")
public class ServerMetrics implements Serializable {
    private Integer taskId;
    private Integer metricId;
}
