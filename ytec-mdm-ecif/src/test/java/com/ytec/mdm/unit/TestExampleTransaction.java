package com.ytec.mdm.unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.socket.http.client.HttpClient;
public class TestExampleTransaction {
	
	public static void main(String[] args) throws Exception {
		String requestFile=null;
		String serverIp=null;
		String serverPort=null;
		if (args.length == 3 ){
			serverIp=args[0];
			serverPort=args[1];
			requestFile=args[2];
		}else if(args.length==0){
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/复杂业务流程_01.xml";
//			requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/联机批量结果查询_01.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/修改客户信息类交易_01.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/客户合并_01.xml";
//			requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/01-单元测试/交易/batch.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/多表子查询交易_05.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/个人开客户户交易_01.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/外部报文.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/个人开客户户交易_02.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/疑似客户判定查询.xml";
			//requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/分页查询.xml";
			requestFile="D:/公司SVN/产品/ytececif-doc/01-过程库/06-测试/02-集成测试/02-测试用例/交易服务/测试请求报文/签约信息查询.xml";
			serverIp="192.168.1.80";
			serverPort="9500";
		}else{
			System.out.println("Usage :服务器IP 服务器端口    请求报文");
			return;
		}
		if(StringUtil.isEmpty(serverIp)||StringUtil.isEmpty(serverPort)||StringUtil.isEmpty(requestFile)){
			System.out.println("Usage :服务器IP 服务器端口    请求报文");
			return;
		}
		//HttpClient client = new HttpClient();
		Map arg=new HashMap();
		arg.put("ip", serverIp);
		arg.put("port", serverPort);
		arg.put("url", "ytec-mdm-web/EcifServer");
		arg.put("charset", "GBK");
		arg.put("timeout", "60000");
		arg.put("selecttimeout", "3000");
		
		arg.put("keyStorePath", "D:/ssl/keystore");
		arg.put("keyPassWord", "123456");
		arg.put("trustStorePath", "D:/ssl/truststore");
		arg.put("trustPassWord", "123456");
		//client.init(arg);
		File file = new File(requestFile);
		if(!file.exists()){
			System.out.println("文件不存在");
			return ;
		}
		 StringBuffer sb=new StringBuffer();
		 BufferedReader reader = null;
	        try {
	        	String fileCharSet="GBK";
	            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),fileCharSet));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	                sb.append(tempString);
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    int m=1000;
	    String str=sb.toString();
	    while(m-->0){
//	    	System.out.println("#######################"+m);
//	    	String body = str.substring(str.indexOf("<RequestBody>"), str.indexOf("</RequestBody>")+14);
//	    	String checkSum=RequestCheckSum.CheckReponseSum(body);
//	    	str=str.replace("<HMac/>", "<HMac>"+checkSum+"</HMac>");
	    	//HttpClient client = new HttpClient();
	    	HttpClient client = new HttpClient();
	    	client.init(arg);
	    	client.sendMsg(str);
//	    	Thread r=new Thread(new rr(client,str));
//	    	r.start();
	    }
	}
	
	
	
}

//class rr implements Runnable{
//	HttpClient client;
//	String str;
//	public rr(HttpClient client,String str){
//		this.client=client;
//		this.str=str;
//	}
//	public void run() {
//		client.sendMsg(str);
//	}
//}
