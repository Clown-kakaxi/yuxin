package com.yuchengtech.bcrm.sales.message.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.yuchengtech.trans.socket.NioClient;

public class MsgClient{

	private static Map<String, String> sysAddrPortMap = new HashMap<String, String>();
	private static Map<String, String> sysAddrIpMap = new HashMap<String, String>();
	
	public static MsgClient getInstance() {
		return new MsgClient();
	}

	public static void main(String[] args) throws Exception{
		//MsgClient.getInstance().process("13585865975","尊敬的陈尚贤客户：您好，您的钻石卡等级卡片已经制作完成，请您抽空到网点领取卡片，有任何疑问请向您的客户经理：林彤晔咨询，联系电话： ，感谢您一直以来对富邦华一银行的支持。", "");
		MsgClient.getInstance().process("13585865975","你好","");
	}
	
	public void init(){
		sysAddrIpMap.put("CRM", "192.168.2.42");
		sysAddrPortMap.put("CRM", "80");
	}
	/**
	 * 组装请求报文
	 * @param mobile
	 * @param message
	 * @param sendTime
	 * @return
	 * @throws Exception
	 */
	public String MsgXml(String mobile,String message,String sendTime) throws Exception{		
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"gbk\"?>\n");
		sb.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n");
		sb.append("   <soap12:Body>\n");
		sb.append("    <SendSMS xmlns=\"http://tempuri.org/\">\n");
		sb.append("      <strMobiles>"+mobile+"</strMobiles>\n");
		sb.append("      <strContent>"+message+"</strContent>\n");
		sb.append("      <strStartTime>"+sendTime+"</strStartTime>\n");
		sb.append("      <strEndTime></strEndTime>\n");
		sb.append("      <strUserID></strUserID>\n");
		sb.append("      <strMessageType>1</strMessageType>\n");
		sb.append("    </SendSMS>\n");
		sb.append("  </soap12:Body>\n");
		sb.append("</soap12:Envelope>\n");
	    StringBuffer sbReq = new StringBuffer();
		sbReq.append("POST /SMS_WCF/sendsms.asmx HTTP/1.1\n");
		sbReq.append("Host: 192.168.2.42\n");
		sbReq.append("Content-Type: application/soap+xml; charset=gbk\n");
		sbReq.append("Content-Length: " + sb.toString().getBytes().length+"\n\n");
	    sbReq.append(sb.toString());
	    return sbReq.toString();
	}
	
	public String process(String mobile,String message,String sendTime) throws Exception {
//		init();
//		String msg = MsgXml(mobile,message,sendTime);
//		String ip = sysAddrIpMap.get("CRM");
//		int port = Integer.parseInt(sysAddrPortMap.get("CRM"));
//		NioClient cl = new NioClient(ip, port);
//		String resp = null;
//		try {
//			resp = cl.SocketCommunication(msg);
//		} catch (IOException e) {
//			System.out.printf("destSysNo:%s, ip:%s, port:%d\n", "CRM", ip, port);
//			e.printStackTrace();
//		}
//		return resp;
		StringBuffer sb = new StringBuffer();
	       String http="http://192.168.2.42/SMS_WCF/URLSendSMS.aspx";
	       URL url;
	       try {
	    	   message =  URLEncoder.encode(message,"UTF-8");
	           url = new URL(http+"?UserID=1282&MobilePhone="+mobile+"&Content="+message);
	           BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
	           String inputLine;
	           while ((inputLine = in.readLine()) != null){
	              sb.append(inputLine);
	           }
	           in.close();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       int index = sb.indexOf("=");
	       String backCode = sb.substring(0, index);
	       String backMess = sb.substring(index+1, sb.length());
	       if("SUC".equalsIgnoreCase(backCode)){
	           return "true@"+backMess;
	       }else{
	           if(backMess == null || "".equals(backMess)) backMess = "短信平台";
	           return "false@"+backMess;
	       }
	}
}
