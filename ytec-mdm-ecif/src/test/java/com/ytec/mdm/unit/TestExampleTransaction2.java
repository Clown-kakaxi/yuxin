package com.ytec.mdm.unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.socket.http.client.HttpClient;
import com.ytec.mdm.interfaces.socket.https.client.HttpsClient;
import com.ytec.mdm.interfaces.socket.https.client.HttpsNioClient;
public class TestExampleTransaction2 {
	
	public static void main(String[] args) throws Exception {
		String requestFile=null;
		String serverIp=null;
		String serverPort=null;
		if (args.length == 3 ){
			serverIp=args[0];
			serverPort=args[1];
			requestFile=args[2];
		}else if(args.length==0){
//			requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/����ҵ������_01.xml";
//			requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�������������ѯ_01.xml";
			//requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�޸Ŀͻ���Ϣ�ཻ��_01.xml";
			//requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�ͻ��ϲ�_01.xml";
//			requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/01-��Ԫ����/����/batch.xml";
			requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/���˿��ͻ�������_01.xml";
			//requestFile="D:/��˾SVN/��Ʒ/ytececif-doc/01-���̿�/06-����/02-���ɲ���/02-��������/���׷���/����������/�ⲿ����.xml";
			serverIp="127.0.0.1";
			serverPort="9500";
		}else{
			System.out.println("Usage :������IP �������˿�    ������");
			return;
		}
		if(StringUtil.isEmpty(serverIp)||StringUtil.isEmpty(serverPort)||StringUtil.isEmpty(requestFile)){
			System.out.println("Usage :������IP �������˿�    ������");
			return;
		}
		//HttpClient client = new HttpClient();
		Map arg=new HashMap();
		arg.put("ip", serverIp);
		arg.put("port", serverPort);
		arg.put("url", "ytec-mdm-ecifTrans/EcifServer");
		arg.put("charset", "GB18030");
		arg.put("timeout", "60000");
		arg.put("selecttimeout", "3000");
		
		arg.put("keyStorePath", "D:/ssl/keystore");
		arg.put("keyPassWord", "123456");
		arg.put("trustStorePath", "D:/ssl/truststore");
		arg.put("trustPassWord", "123456");
		//client.init(arg);
		File file = new File(requestFile);
		if(!file.exists()){
			System.out.println("�ļ�������");
			return ;
		}
		 StringBuffer sb=new StringBuffer();
		 BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
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
//	    int m=2;
	    String str=sb.toString();
//	    while(m-->0){
//	    	System.out.println("#######################"+m);
//	    	String body = str.substring(str.indexOf("<RequestBody>"), str.indexOf("</RequestBody>")+14);
//	    	String checkSum=RequestCheckSum.CheckReponseSum(body);
//	    	str=str.replace("<HMac/>", "<HMac>"+checkSum+"</HMac>");
	    	//HttpClient client = new HttpClient();
	    HttpsClient client = new HttpsClient();
	    	client.init(arg);
	    	client.sendMsg(str);
//	    	Thread r=new Thread(new rr(client,str));
//	    	r.start();
//	    }
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
