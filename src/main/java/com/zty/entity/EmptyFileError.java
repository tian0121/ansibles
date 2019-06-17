package com.zty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmptyFileError  implements Serializable {
    private  String EmptyFile_id;
    private String EmptyFile_msg;

}
