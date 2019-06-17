package com.zty.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.zty.entity.TServers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnsibleShellUtil {
    public static void appened(String ip){
        try{
            String command1 = "/root/test2.sh "+ip+"";  //拼接ip执行脚本，使得hosts文件追加ip纳入ansible管理"
            Process p = Runtime.getRuntime().exec(command1);
            System.out.println("纳入hosts文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void sendSSH(String ip){
        try{
            String command2 = "ssh-copy-id -i ~/.ssh/id_rsa.pub root@"+ip;
            Process p = Runtime.getRuntime().exec(command2);  //调用Linux的相关命令
            String command3 = "yes";
            Process p1 = Runtime.getRuntime().exec(command3);  //调用Linux的相关命令
            System.out.println("发送SSH成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void replace(String ip,String ips){
        try{
            if(ips==null&&ip!=null) {
                String command2 = "sed -i \"s/" + ip + "//g\" /etc/ansible/hosts";  //将hosts文件中的ip的字符串 替换成ip
                Process p = Runtime.getRuntime().exec(command2);  //调用Linux的相关命令
                System.out.println("删除hosts中的ip成功");
            }if(ips!=null&&ip!=null){
                String command2 = "sed -i \"s/" + ip + "/"+ips+"/g\" /etc/ansible/hosts";  //将hosts文件中的ip的字符串 替换成空字符
                Process p = Runtime.getRuntime().exec(command2);  //调用Linux的相关命令
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static List<TServers> checkOnlion(List<TServers> tServers){
        List<TServers> list = new ArrayList<>();
        for (TServers tServer : tServers) {
            list.add(getPingResult(tServer));
        }
        System.out.println(list);
        return list;
    }
    public static TServers getPingResult(TServers tServers){
        try{
            String QaueryIp = "ansible " +tServers.getIpAddr() +" -m ping";
            Process p = Runtime.getRuntime().exec(QaueryIp);  //调用Linux的相关命令
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);      //创建IO管道，准备输出命令执行后的显示内容
            String line;
            while ((line = input.readLine ()) != null){     //按行打印输出内容
                if(line.indexOf("SUCCESS")!=-1){
                    tServers.setServerStatus(1);
                    return tServers;
                }else {
                    tServers.setServerStatus(0);
                    return tServers;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    return tServers;
    }

    public static JSONObject getResult(String command2,String id){
        try{

            Process p = Runtime.getRuntime().exec(command2);  //调用Linux的相关命令
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);      //创建IO管道，准备输出命令执行后的显示内容
            String line;
            String x = "";
            while ((line = input.readLine ()) != null){     //按行打印输出内容
                if (line.indexOf("没有那个文件或目录")!=-1){
                    String x1 = "没有那个文件或目录";
                    JSONObject jsonpObject=new JSONObject();
                    jsonpObject.put(id+"",x1);
                    return jsonpObject;
                }
                System.out.println(line);
                line.trim();
                if (line!=null){
                    x=x+line;
                }
            }

            String x1 = x.replaceAll("\\}","");
            long endTime=System.nanoTime(); //获取结束时间
            JSONObject jsonpObject=new JSONObject();
            jsonpObject.put(id,x1);
            return jsonpObject;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("任务执行失败");
        }
    }
}
