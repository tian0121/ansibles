package com.zty.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.zty.dto.TServerDto;
import com.zty.entity.TServers;

import java.util.List;

public interface TServersService {
    public JSONObject addServer(TServers tServers);
    public JSONObject deleteServer(Integer id);
    public TServerDto queryServer(Integer Page,Integer PageSize,String serverName,String serverStatus);
    public List<TServers> queryAllServers();
}
