package com.zty.ServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.zty.Mapper.TServerMapper;
import com.zty.Mapper.TaskServersMapper;
import com.zty.Service.TServersService;
import com.zty.dto.TServerDto;
import com.zty.entity.Pagination;
import com.zty.entity.TServers;
import com.zty.entity.TaskServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TServerServiceImpl implements TServersService {
    @Autowired
     private TServerMapper tServerMapper;
    @Override
    public JSONObject addServer(TServers tServers) {
        try{
            if(tServers.getServerId()!=null) {       //如果服务器有id，则代表修改，没id代表新增
                TServers tServers2 = new TServers();
                tServers2.setServerId(tServers.getServerId());
                TServers tServers1 = tServerMapper.selectByPrimaryKey(tServers2); //通过id查询 保证服务器名称必须唯一
                if(tServers1.getServerName().equals(tServers.getServerName())){  //比对服务器名称
                    throw new RuntimeException("相同的服务器名称");
                }
//                AnsibleShellUtil.replace(tServers1.getIpAddr(),tServers.getIpAddr()); //替换 覆盖原ip
//                AnsibleShellUtil.sendSSH(tServers.getIpAddr());//将新的ip发送到ssh管理
                tServers1.setServerName(tServers.getServerName());//修改服务器名称
                tServers1.setDiscovery(tServers.getDiscovery()); //修改服务器发现方式
                tServers1.setIpAddr(tServers.getIpAddr());  //修改服务器ip
                tServers1.setComments(tServers.getComments());//修改服务器备注
                tServerMapper.updateByPrimaryKey(tServers1);//持久化到数据库
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resultCode", "0");
                jsonObject.put("msg", "success");
                return  jsonObject;
            }
//            AnsibleShellUtil.appened(tServers.getIpAddr());  //拼接ip执行脚本，使得hosts文件追加ip纳入ansible管理
//            AnsibleShellUtil.sendSSH(tServers.getIpAddr());  //发送SSH给远程主机使得ansible管理其机器
            tServers.setCreateDate(new Date());    //填充创建时间
            tServers.setServerStatus(1);           //服务器状态
            tServers.setTags("Tags");              //Tags
            tServers.setHeartbeat(new Date());     //心跳时间
            tServers.setDomain("Domain");          //Domain
            tServers.setCreator("zhangsan");       //创建者
            tServerMapper.addServer(tServers);     //插入表中
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode", "0");
            jsonObject.put("msg", "success");
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","编辑失败");
            return jsonObject;
        }
    }
    @Autowired
    TaskServersMapper taskServersMapper;
    @Override
    public JSONObject deleteServer(Integer id) {
        try{
            TServers tServers = new TServers();
            tServers.setServerId(id);
            TaskServer taskServer = new TaskServer();
            taskServer.setTaskId(id);
            List<TaskServer> list = taskServersMapper.queryTask(id);
            if(list!=null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("resultCode","1");
                jsonObject.put("msg","服务器删除失败,有相关任务正在使用");
                return jsonObject;
            }

            tServerMapper.deleteByPrimaryKey(tServers);  //删除服务器
//            AnsibleShellUtil.replace(tServers.getIpAddr(),null);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","0");
            jsonObject.put("msg","success");
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode","1");
            jsonObject.put("msg","服务器删除失败");
            return jsonObject;
        }

    }
    @Override
    public TServerDto queryServer(Integer Page,Integer PageSize,String serverName,String serverStatus) {
            List<TServers> list  = tServerMapper.pageRows(Page,PageSize,serverName,serverStatus); //查找分页list
            //判断total查找的表
            Integer total = null;
            if(serverName==null&&serverStatus==null){
                total = tServerMapper.queryTotalNoArgs();
                System.out.println(total+"xxx");
            }if(serverName!=null|serverStatus!=null){
                total = tServerMapper.queryTotal(serverName,serverStatus);
                System.out.println(total);
            }
            //计算页数
//
//            if(total%PageSize==0){
//                Pages = total/PageSize;
//            }else {
//                Pages = total/PageSize+1;
//            }
            //设置page pagesize的值
        Pagination pagination = new Pagination(Page,PageSize,total);  //封装一个pagination
        TServerDto td = new TServerDto(list,pagination);//封装一个Dto对象
        return td;
    }

    @Override
    public List<TServers> queryAllServers() {
        try{
            return tServerMapper.queryAllServers();  //查询所有的服务器
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        }
    }
}
