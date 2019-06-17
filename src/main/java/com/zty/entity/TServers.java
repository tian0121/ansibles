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
@Table(name = "T_SERVERS")
public class TServers  implements Serializable {
    @Id
    private Integer serverId;
    private String serverName;
    private String ipAddr;
    private String domain;
    private String tags;
    private Integer discovery;
    private Integer serverStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date heartbeat;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private String creator;
    private String comments;
}
