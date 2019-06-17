package com.zty.dto;

import com.zty.entity.Pagination;
import com.zty.entity.TMetrics;
import com.zty.entity.TServers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TMetricsDto implements Serializable {
    private List<TMetrics> list;
    private Pagination pagination;
}
