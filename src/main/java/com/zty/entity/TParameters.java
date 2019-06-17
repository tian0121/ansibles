package com.zty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_PARAMETERS")
public class TParameters implements Serializable {
    @Id
    private Integer parameterId;
    private Integer parameterCatagory;
    private String  parameterCode;
    private String  parameterValue;
    private Integer parameterStatus;
    private String  comments;
}
