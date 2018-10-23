package com.yuchengtech.bcrm.flex.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuchengtech.bob.upload.FileTypeConstance;

public class FlexHandlerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response){
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("============Servlet上传开始");
    	long s = System.currentTimeMillis();
    	//前端上传文件的输入流
    	ServletInputStream sis = null;
    	//保存录音文件的输出流
    	FileOutputStream fos = null;
    	PrintWriter out = null;
    	try {
    		//流程业务ID
    		String busiId = request.getParameter("busiId");
    		String saveFilePath = (FileTypeConstance.getUploadPath()+"/recorder/").replaceAll("\\\\", "/");
    		if (! new File(saveFilePath).exists()) {
                new File(saveFilePath).mkdirs();
            }
    		String prefix = saveFilePath +busiId+"_";
    		String suffix = ".mp3";
    		long currTime = System.currentTimeMillis();
    		String filepath = prefix + currTime + suffix;
    		File f1 = new File(filepath);
    		synchronized (f1) {
    			while (f1.exists()) {
    				currTime = currTime + 1;
    				filepath = prefix + currTime + suffix;
    				f1 = new File(filepath);
    			}
    			f1.createNewFile();
    		}
			sis = request.getInputStream(); // 获取文件输入流
			fos = new FileOutputStream(f1); // 创建文件输出流
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = sis.read(buffer,0,1024)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			//向前端返回信息
			out = response.getWriter();
            out.append("{\"success\":true,\"realFileName\":\""+filepath+"\"}");
            out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 try {
				out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
    	long e = System.currentTimeMillis();
    	System.out.println("============Servlet上传结束: " + (e-s) +"ms");
    }
}
