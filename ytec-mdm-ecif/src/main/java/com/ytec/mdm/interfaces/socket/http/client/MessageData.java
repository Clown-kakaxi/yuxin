package com.ytec.mdm.interfaces.socket.http.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
public class MessageData {
	
	/**
	* @Title: getMessage 
	* @Description: 读取文件内容 
	* @param @param filePath 源文件地�?
	* @return String   返回类型 
	* @throws
	 */
	public static String getMessage(String filePath){
		File sourFile = new File(filePath);
		
		StringBuffer sb = new StringBuffer();
		 try {
			 
			 BufferedReader br = null;
			try {
				br=new BufferedReader(new InputStreamReader(new FileInputStream(sourFile),"GBK")); 
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} 
			try {
				int lineNo = 1;
				String strLine = null;
				while((strLine=br.readLine())!=null){
					if(lineNo>9&&lineNo<56){
						sb.append(strLine+"\r");
					}
					if(lineNo==56){
						sb.append(strLine);
						break;
					}
					lineNo++;
				}
				br.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sb.toString();
		
	}
}
