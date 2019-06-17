package com.zty.Controller;
import com.alibaba.fastjson.JSONObject;
import com.zty.Service.ExecuteTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("execute")
@RestController
public class ExecuteController {
    @Autowired
    ExecuteTaskService executeTaskService;
    @RequestMapping("executeTask")
    public JSONObject executeTask(@RequestBody JSONObject jsonObject){
        try{
           String[] idsArray = jsonObject.getString("taskIds").replaceAll("\\[","").replaceAll("\\]","").replace(" ","").split(",");
            List<Integer> list2 = new ArrayList<>();
            for (int i = 0; i < idsArray.length; i++) {
                Integer s = Integer.valueOf(idsArray[i]);
                list2.add(s);
            }
            Integer[] taskIds = list2.toArray(new Integer[list2.size()]);
           for (int i = 0; i < taskIds.length; i++) {
                executeTaskService.executeTask(taskIds[i]);
            }
            JSONObject jsonObjectResult = new JSONObject();
            jsonObjectResult.put("resultCode","0");
            jsonObjectResult.put("msg","执行成功");
            return jsonObjectResult;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","执行失败");
            return jsonObject1;
        }
    }
    @RequestMapping("queryResult")
    public List<JSONObject> queryResult(){
        return  null;
    }
}
