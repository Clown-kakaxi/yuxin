/**
 * 
 */
package com.yuchengtech.emp.ecif.sysmonitor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.yuchengtech.emp.ecif.sysmonitor.entity.ServiceInfo;
import com.yuchengtech.emp.ecif.sysmonitor.entity.TxServiceStatus;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:  
 * Description:  启用Listener来监听Ecif服务状态，将服务状态更新到服务状态信息表
 * </pre>
 * 
 * @author  yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *     
 * </pre>
 */
public class ScanServiceListener  implements ServletContextListener {

	private static Logger logger = LoggerFactory
			.getLogger(ScanServiceListener.class);	
	/**
	 * 定义后台扫描线程
	 */
	private ScanThread  scanThread;
	/**
	 * 服务器登录信息集合
	 */
	private List<ServiceInfo> serviceLoginInfoList ;
	/**
	 * 读取服务对象
	 */
	private TxServiceStatusBS txServiceStatusBS ;
	/**
	 * 服务信息集合
	 */
	private List<TxServiceStatus> txServiceStatusList ;
	/**
	 * 扫描命令，检查进程是否运行
	 */
	private String scanCMD="netstat -nltp|grep  :";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		try {
			logger.info("定时扫描服务初始化开始.......");           
		    scanThread = new ScanThread();
			scanThread.start();
			logger.info("定时扫描服务初始化结束.......");
		} catch (Exception e) {
			logger.error("定时扫描服务初始化失败.......", e);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */

	public void contextDestroyed(ServletContextEvent sce) {
		 if (scanThread != null && scanThread.isInterrupted()) {  
			 scanThread.interrupt();  
	       }
	}
	/**
	 * 
	 * 定时调用扫描主程序线程类
	 *
	 */
	class ScanThread extends Thread {
		    public void run() {  
		        while (!this.isInterrupted()) {// 线程未中断执行循环   
		           try {  
					   Sacn();   
		        	   //休眠3分钟
		        	   sleep(1000*10);
		        	   //调用扫描程序
		          } catch (Exception e) {
		        	   logger.error("扫描线程调用错误!",e); 
				}  
		     }
	     }
	}
	/**
	 * 
	 * @param time 线程休眠时间
	 */
	public void sleep(long time){
		 try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.warn("线程休眠错误!",e);  
		}
	}
	/**
	 * 登录服务器 ，扫描线程是否处于运行状态。如停止，则更新服务状态信息表。
	 * @throws Exception
	 */
	public void Sacn() throws Exception{
		//获取登录服务器信息集合
		serviceLoginInfoList = this.getLoginInfo();
		//定义服务接口
		if(txServiceStatusBS == null){
			 txServiceStatusBS = SpringContextHolder.getBean("txServiceStatusBS");
		}
		//服务信息集合
		txServiceStatusList = txServiceStatusBS.getAllEntityList();
		//如果服务信息为空，则返回
		if(txServiceStatusList == null)
			return ;
		//如果登录信息为空，  则返回
		if(serviceLoginInfoList == null)
			return ;
		for(ServiceInfo sif:serviceLoginInfoList){
			//登录服务器
			Connection conn = null;  
			Session sess = null;
			try{
				 for(TxServiceStatus tss:txServiceStatusList){
					 //如果ip地址相同
					 if(sif.getIp().equals(tss.getIpAddr())){
						conn = new Connection(sif.getIp());
						conn.connect();
						//logger.info("登录服务器ip为："+sif.getIp());
						/* Authenticate */  
						boolean isAuthenticated = conn.authenticateWithPassword(sif.getUserName(), sif.getPassWord());  
						if (isAuthenticated == false)  
							throw new IOException("Authentication failed."); 
						sess = conn.openSession();  
						//logger.info("扫描服务名称为："+tss.getServiceName());
					    //sess.execCommand(scanCMD+tss.getServicePort()+" "); 
						String cmd = " ps -ef |grep \"EcifServerLauncher " + tss.getServiceName() +"\"|grep -v \"grep\" |awk '{print $2}'";
					    sess.execCommand(cmd);
					    InputStream stdout = new StreamGobbler(sess.getStdout());  
					    BufferedReader br = new BufferedReader(  
					                       new InputStreamReader(stdout));  
					    StringBuffer result = new StringBuffer();
					    char[] arr = new char[512];  
					    int read;  
					    int i = 0;  
					    while (true) {  
					       // 将结果流中的数据读入字符数组   
					       read = br.read(arr, 0, arr.length);  
					       if (read < 0)  
					           break;  
					       // 将结果拼装进StringBuilder   
					       result.append(new String(arr, 0, read));  
					           i++;  
					     }  
						//logger.info("扫描返回结果为：\n"+result); 
						br.close();
						//服务是否运行
						boolean isRun = false;
						//服务运行进程号
						Integer processPort= null;
						//分行读取
						String reg = "\n" ;
						String[] lineString = result.toString().split(reg);
						for(String str :lineString){
//							if(str.indexOf(tss.getServicePort().toString())!=-1 && str.indexOf("java")!=-1 ){
//								isRun = true;
//								int index = str.indexOf("java");
//								//logger.info("查询到java字符串位置：："+index);
//								processPort = Integer.parseInt(str.substring(index-9, index-1).replace(" ", ""));
//								//logger.info("进程端口号："+processPort);
//								//查询到服务进程 ，则跳出循环
//								break;
//							}
							if(str!=null&&!str.equals("") ){
								isRun = true;
								//logger.info("查询到java字符串位置：："+index);
								processPort = Integer.parseInt(str) ;
								//logger.info("进程端口号："+processPort);
								//查询到服务进程 ，则跳出循环
								break;
							}							
						}
						//logger.info("指定服务进程是否在运行："+(isRun?"运行":"停止"));
						//该服务已经停止
						if(!isRun){
							tss.setServiceStart("1");
							if(tss.getStopTime() ==null ||tss.getStopTime().equals("")){
								tss.setStopTime(new Timestamp(System.currentTimeMillis()));
							}
						}else{
							tss.setServiceStart("0");
							if(tss.getStartTime() ==null ||tss.getStartTime().equals("")){
								tss.setStartTime(new Timestamp(System.currentTimeMillis()));
							}
							tss.setProcessID(processPort);
							tss.setStopTime(null);
						}	
						//刷新服务状态
						txServiceStatusBS.updateEntity(tss);
					 }
					 //关闭会话
					 sess.close();  
					 //关闭连接 
				 	 conn.close(); 
				 	 //logger.info("注销服务器ip为：："+sif.getIp());   
				 }
			}catch(Exception e){
				 logger.error("登录服务器出现异常"+e.getMessage());
				 throw new Exception(e);
			}
		}
	}
	/**
	 * 从配置文件中获取登录服务器信息，封装为List集合对象
	 * @return
	 */
	public List<ServiceInfo> getLoginInfo(){
		//logger.info("读取服务器登录配置信息开始！");
		List<ServiceInfo>   serviceInfoList = new ArrayList<ServiceInfo>();
		Properties prop=new Properties();
		InputStream is= null;
		try {
			//从配置信息读取服务器登录信息
		    is=ScanServiceListener.class.getResourceAsStream("/loginserviceinfo.properties");
			prop.load(is);
			Set<Entry<Object, Object>> serviceInfo = prop.entrySet();
			for(Object serviceName:serviceInfo){		
				String str = serviceName.toString();
				String reg = ":";
				String[] s = str.split("=")[1].split(reg);
				ServiceInfo si = new ServiceInfo();
				si.setIp(s[0]);
				si.setUserName(s[1]);
				si.setPassWord(s[2]);
				serviceInfoList.add(si);
			}
		} catch (IOException e) {
			logger.error("读取服务器登录配置信息错误!", e) ;
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				logger.warn("关闭服务器登录配置文件错误!", e);
			}
		}
		//logger.info("读取服务器登录配置信息结束！");
		return serviceInfoList;
	}
}