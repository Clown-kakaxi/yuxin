package com.yuchengtech.notify.client;

import java.util.Map;

import com.yuchengtech.trans.socket.NioClient;

public class SmsClient {
	public static SmsClient getInstance() {
		return new SmsClient();
	}

	private SmsClient() {

	}

	public Map sendMsg(String telNo, String content, String sendTime) throws Exception {
		StringBuffer sbBody = new StringBuffer();
		sbBody.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbBody.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n");
		sbBody.append("  <soap12:Body>\n");
		sbBody.append("    <SendSMS xmlns=\"http://tempuri.org/\">\n");
		sbBody.append("      <strMobiles>" + telNo + "</strMobiles>\n");
		sbBody.append("      <strContent>" + content + "</strContent>\n");
		sbBody.append("      <strStartTime>" + sendTime == null ? "" : sendTime + "</strStartTime>\n");
		sbBody.append("      <strEndTime></strEndTime>\n");
		sbBody.append("      <strUserID></strUserID>\n");
		sbBody.append("      <strMessageType>1</strMessageType>\n");
		sbBody.append("    </SendSMS>\n");
		sbBody.append("  </soap12:Body>\n");
		sbBody.append("</soap12:Envelope>\n");

		StringBuffer sbMsg = new StringBuffer();
		sbMsg.append("POST /SMS_WCF/sendsms.asmx HTTP/1.1\n");
		sbMsg.append("Host: 192.168.2.42\n");
		sbMsg.append("Content-Type: application/soap+xml; charset=utf-8\n");
		sbMsg.append("Content-Length: " + sbBody.toString().getBytes().length + "\n");
		sbMsg.append("\n");
		sbMsg.append(sbBody.toString());

		NioClient cl = new NioClient("192.168.2.42", 80);
		try {
			System.out.println("req:\n[" + sbMsg.toString() + "\n]");
			String resp = cl.SocketCommunication(sbMsg.toString());
			System.out.println("rsp:\n[" + resp + "\n]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			SmsClient.getInstance().sendMsg("13770728834", "傻逼", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
