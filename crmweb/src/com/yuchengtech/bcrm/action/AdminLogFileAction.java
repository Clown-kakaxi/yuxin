package com.yuchengtech.bcrm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/***
 * @author liuyx
 * 日志文件管理--控制层
 * @date: 2017年10月18日 下午5:03:54 
 */
@ParentPackage("json-default")
@SuppressWarnings("serial")
@Action("/adminLogFileAction")
public class AdminLogFileAction extends CommonAction {
	Logger log = LoggerFactory.getLogger(AdminLogFileAction.class);
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    /**
	 *查询日志文件信息
	 */
    @SuppressWarnings("unchecked")
    public void listLogFile() {
    	log.info("用户 "+auth.getUsername()+"/"+auth.getUserId()+" 开始读取日志文件列表。。。。。。。。。。");
    	List list = new ArrayList();
    	String path = this.getClass().getClassLoader().getResource("/").getPath();
    	path = path.replace("classes/", "");
    	path = path.replace("WEB-INF/", "");
    	path = path + "logs";
    	File logDir = new File(path);
    	if(this.json==null){
    		this.json = new HashMap<String,Object>();
    	}
    	if(!logDir.exists()){
    		this.json.put("data", list);
    	}else{
    		File[] files = logDir.listFiles();
    		for(int i=0;i<files.length;i++){
    			for (int j = 0; j < files.length; j++) {
    				String fname1 = files[i].getName();
    				String fname2 = files[j].getName();
    				if("crm.log".equals(fname1)){
    					fname1 = fname1 + ".2999-12-31";
    				}else if("crm.log".equals(fname2)){
    					fname2 = fname2 + ".2999-12-31";
    				}
    				if(fname1.compareTo(fname2)>0){
	                	File tmp = files[i];
	                	files[i] = files[j];
	                	files[j] = tmp;
	                }
                }
    		}
    		for(File file : files){
    			Map fileInfo = new HashMap();
    			fileInfo.put("logFileName", file.getName());
    			fileInfo.put("logFilePath", file.getAbsolutePath());
    			double size = FileUtils.sizeOf(file);
    			if(size<1024){
    				fileInfo.put("logFileSize", size+"B");
    			}else if(size>1024&&size<1024*1024){
    				size = (int)(size/1024*10);
    				size = size/10;
    				fileInfo.put("logFileSize", size+"KB");
    			}else{
    				size = (int)(size/1024/1024*10);
    				size = size/10;
    				fileInfo.put("logFileSize", size+"MB");
    			}
    			list.add(fileInfo);
    		}
    		this.json.put("total", list.size());
    		if(list.size()>start+limit){
    			list = list.subList(start, start+limit);
    		}
    		this.json.put("data", list);
    	}
    	log.info("用户 "+auth.getUsername()+"/"+auth.getUserId()+" 读取日志文件列表成功");
	}
    /**
     *下载日志文件
     */
    @SuppressWarnings("unchecked")
    public void downloadLogFile() {
    	log.info("用户 "+auth.getUsername()+"/"+auth.getUserId()+" 开始下载日志文件。。。。。。。。。。");
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        String path = this.getClass().getClassLoader().getResource("/").getPath();
    	path = path.replace("classes/", "");
    	path = path.replace("WEB-INF/", "");
    	path = path + "logs/";
        String fileName = request.getParameter("fileName");
        String filePath = path+fileName;
        File file = new File(filePath);
        try {
        	// 设置下载文件的类型为任意类型
        	response.setContentType("application/x-msdownload");
        	// 添加下载文件的头信息。此信息在下载时会在下载面板上显示
	        response.addHeader("Content-Disposition", "attachment;" + "filename=" + new String(fileName.getBytes("GBK"),"ISO8859_1"));
	        // 添加文件的大小信息
	        response.setContentLength((int) file.length());
	        // 获得输出网络流
	        OutputStream out;
	        out = response.getOutputStream();
	        FileInputStream fis = new FileInputStream(file);
	        byte[] buffer = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buffer)) != -1) {
	        	out.write(buffer, 0, i);
	        }
	        out.flush();
	        fis.close();
	        out.close();
        } catch (Exception e) {
        	log.info("用户 "+auth.getUsername()+"/"+auth.getUserId()+" 下载日志文件失败，失败原因："+e.getMessage());
	        e.printStackTrace();
        }
        log.info("用户 "+auth.getUsername()+"/"+auth.getUserId()+" 下载日志文件成功");
    }
}
