package com.zty.dto;

import com.zty.entity.Pagination;
import com.zty.entity.TTasks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunningLogsDto implements Serializable {
    private List<TTasks> list;
    private Pagination pagination;
}
