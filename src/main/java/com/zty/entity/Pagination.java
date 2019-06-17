package com.zty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination implements Serializable {
    private Integer current;
    private Integer pageSize;
    private Integer total;
}
