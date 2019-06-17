package com.zty.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class St implements Serializable {
    private String changed;
    private String rc;
    private String stderr ;
    private String stdout;
    private String stdout_lines;
}
