package com.zty.Mapper;
import com.zty.entity.TServers;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
public interface TServerMapper extends Mapper<TServers> {
    public void addServer(TServers tServers);
    public Integer queryTotal(@Param("serverName")String serverName,@Param("serverStatus")String serverStatus);
    public List<TServers> pageRows(@Param("Page") Integer Page,@Param("PageSize")Integer PageSize,@Param("serverName") String serverName,@Param("serverStatus")String serverStatus);
    public Integer queryTotalNoArgs();
    public List<TServers> queryAllServers();

}
