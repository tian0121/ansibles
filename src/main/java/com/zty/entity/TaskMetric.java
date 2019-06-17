package com.zty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Table;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_TASK_METRICS")
public class TaskMetric implements Serializable {
    private Integer taskId;
    private Integer metricId;
}
