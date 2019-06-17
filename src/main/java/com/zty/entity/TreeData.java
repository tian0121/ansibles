package com.zty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeData implements Serializable {
    private String title;
    private Integer value;
    private Integer key;
    private List<TreeData> children;
}
