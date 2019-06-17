package com.zty.Controller;

import com.alibaba.fastjson.JSONObject;
import com.zty.Service.TServersService;
import com.zty.dto.TServerDto;
import com.zty.entity.TServers;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("server")
public class ServerController {
    @Autowired
    private TServersService tServersService;
    @RequestMapping("/addOrUpdateServerConfig")
    public JSONObject AddServer(@RequestBody TServers tServers){
        System.out.println(tServers);
        return tServersService.addServer(tServers);
    }
    @RequestMapping(value = "/deleteServerConfigById",method = RequestMethod.POST)
    public JSONObject DeleteServer(@RequestParam Integer id){
        System.out.println(id);
        return tServersService.deleteServer(id);
    }
    @RequestMapping("/queryServerConfigList")
    public TServerDto QueryServer(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer pageSize, String serverName,String serverStatus){
        System.out.println(serverName);
        System.out.println(serverStatus);
        return tServersService.queryServer(currentPage,pageSize,serverName,serverStatus);
    }

    @RequestMapping("/queryAllServer")
    public JSONObject queryAllServer(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode","0");
        jsonObject.put("msg","查询成功");
        jsonObject.put("data",tServersService.queryAllServers());
        return jsonObject;
    }
}
