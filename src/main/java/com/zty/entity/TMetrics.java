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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_METRICS")
public class TMetrics implements Serializable {
    @Id
    private Integer metricId;
    private String metricName;
    private Integer metricCatagory;
    private Integer metricType;
    private String metricCode;
    private Integer metricStatus;
    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private String comments;
}
