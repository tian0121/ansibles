package com.zty.dto;
import com.zty.entity.Pagination;
import com.zty.entity.TasksAndServers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto  implements Serializable {
    private List<TasksAndServers> list;
    private Pagination pagination;
}
