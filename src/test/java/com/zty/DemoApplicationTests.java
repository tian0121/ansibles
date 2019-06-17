package com.zty;

import com.alibaba.fastjson.JSONObject;
import com.zty.Mapper.TaskMetricMapper;
import com.zty.Service.ExecuteTaskService;
import com.zty.Service.TMetricsService;
import com.zty.Service.TServersService;
import com.zty.Service.TaskServerService;
import com.zty.dto.TServerDto;
import com.zty.entity.TMetrics;
import com.zty.entity.TServers;
import com.zty.entity.TTasks;
import com.zty.entity.TaskMetric;
import com.zty.util.AnsibleShellUtil;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	TServersService tServersService;
	@Autowired
	TMetricsService tMetricsService;
	@Test
	public void contextLoads() {
		TServers tServers = new TServers();
		tServers.setComments("111");
		tServers.setIpAddr("192.168.0.77");
		tServers.setDiscovery(1);
		tServers.setServerName("反反复复");
		tServers.setServerStatus(1);
		tServers.setDomain("xxxx");
		tServers.setCreator("zhansgan");
		tServers.setHeartbeat(new Date());
		tServersService.addServer(tServers);
	}
	@Test
	public void getPingResult(){
		String ips = "192.168.0.72";
		try{
			String QaueryIp = "ansible " +ips +" -m ping";
			Process p = Runtime.getRuntime().exec(QaueryIp);  //调用Linux的相关命令
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);      //创建IO管道，准备输出命令执行后的显示内容
			String line;
			while ((line = input.readLine ()) != null){     //按行打印输出内容
				System.out.println(line);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void contextLoads3() {
		TMetrics tMetrics = new TMetrics();
		tMetrics.setMetricName("Tenth ");
		tMetrics.setComments("XXXX");
		tMetrics.setCreator("zhangsan");
		tMetrics.setMetricCatagory(1);
		tMetrics.setMetricCode("ls");
		tMetrics.setMetricStatus(1);
		tMetrics.setMetricType(3);
		tMetrics.setMetricId(41);
		System.out.println(tMetrics);
		tMetricsService.addTMetrics(tMetrics);
	}

	@Test
	public void contextLoads4() {
		String ip ="192.168.0.72";
		String ips = "192.168.0.70";
		String command = "sed -i \"s/" + ip + "//g\" /etc/ansible/hosts";  //将hosts文件中的ip的字符串 替换成空字符
		String command2 = "sed -i \"s/" + ip + "/"+ips+"/g\" /etc/ansible/hosts";  //将hosts文件中的ip的字符串 替换成空字符
		System.out.println(command2);
		System.out.println(command);
	}

	@Autowired
	TaskServerService taskServerService;
	@Test
	public void contextLoads5() {
		TTasks tTasks = new TTasks();
		tTasks.setTaskName("wangwu");
		tTasks.setTaskType(1);
		Integer[] y= new Integer[]{281};
		Integer[] z = new Integer[]{23,25};
		taskServerService.addOrUpdateTask(tTasks,y,z);
	}
@Autowired
	TaskMetricMapper taskMetricMapper;
	@Test
	public void contextLoads6() {
//		TaskMetric taskMetric = new TaskMetric();
//		taskMetric.setMetricId(10001);
		List<TaskMetric> taskMetricsList = taskMetricMapper.queryTaskMetricByMetricId(10001);
		System.out.println(taskMetricsList);
	}
	@Test
	public void contextLoads7() {
		String code = "/root/test1.sh";
		String ip = "192.168.0.70";
		String command1 = "ansible " +ip +" -m shell -a " +code;
		System.out.println(command1);
	}

	@Test
	public void contextLoads8() {
		TTasks tTasks = new TTasks();
		tTasks.setTaskName("ce1");
		tTasks.setTaskType(0);
		Integer[] a = {321,341};
		Integer[] b = {10001,10002,10003,10004,10005};
		taskServerService.addOrUpdateTask(tTasks,a,b);
	}

	@Test
	public void contextLoads9() {
		String command ="ansible 192.168.0.55 -m command -a ls";
		JSONObject jsonObject = AnsibleShellUtil.getResult(command,301+""+"192.168.0.55");
		String x = jsonObject.getString("301");
		List<Object> list = Arrays.asList(x.split(","));
		for (Object o : list) {
			System.out.println(o);
			System.out.println(o.getClass());
		}
	}

	@Autowired
	ExecuteTaskService executeTaskService;

	@Test
	public void contextLoads10() {
		Integer id = 422;
		List<JSONObject> jsonObjectList = executeTaskService.executeTask(id);
		System.out.println(jsonObjectList);
	}
}