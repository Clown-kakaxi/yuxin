/**
 * 
 */
package com.yuchengtech.emp.ecif.base.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.yuchengtech.emp.ecif.base.nio.NIOClient;
/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class TestExampleTransaction {
	
//	public static final int BLOCK = 1*1024*1024;
//	public static final int MAX_EXECUTER = 50;
//	public static final int MAX_BATCH_EXECUTER = 10;
//	
//	public static final String ESB_IP = "127.0.0.1";
//	public static final int ESB_PORT = 8888;
	public static final String ESB_URL = "/ECIF";

//	public static void main(String[] args) throws Exception {
//		NIOClient client = new NIOClient("127.0.0.1", 7777);
//        
//		//每次修改请求报文的文件名，即可测试不同的接口。
//		//File file = new File("D:/公司SVN/ECIF/6_系统测试/1_SIT测试/标准服务测试报文/开户/OP0000.xml");
//		File file = new File("D:/公司SVN/ECIF/6_系统测试/1_SIT测试/标准服务测试报文/维护/DC02001.xml");
//		if(!file.exists()){
//			System.out.println("文件不存在");
//			return ;
//		}
//		 
//		 StringBuffer sb=new StringBuffer();
//		 BufferedReader reader = null;
//	        try {
//	            reader = new BufferedReader(new FileReader(file));
//	            String tempString = null;
//	            while ((tempString = reader.readLine()) != null) {
//	                sb.append(tempString);
//	            }
//	            reader.close();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } finally {
//	            if (reader != null) {
//	                try {
//	                    reader.close();
//	                } catch (IOException e1) {
//	                }
//	            }
//	        }
//	        
//		client.interactive(sb.toString(), ESB_URL);
//	}
	
}
