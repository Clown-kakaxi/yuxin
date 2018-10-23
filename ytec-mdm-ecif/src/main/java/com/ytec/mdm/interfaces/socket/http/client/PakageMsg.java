package com.ytec.mdm.interfaces.socket.http.client;

public class PakageMsg {
	static String header = "POST / HTTP/1.1\n" +	
					"Accept:*/*\n" +
					"Accept-Language:zh-cn\n" +	
					"User-Agent:Mozilla/4.0(compatible;MSIE6.0;WindowsNT5.1;SV1;.NETCLR2.0.50727;TheWorld)\n" +	
					"Host: 127.0.0.1\n" +	
					"Connection:Keep-Alive\n" +	
					"Content-type: application/xml\n" +	
					"Content-Length:";
	
	/**
	* @Title: combineMsg 
	* @Description: 组合报文
	* @param @param msgBody
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String combineMsg(String msgBody){
		String msg = null;
		header = header+msgBody.getBytes().length;
		// msg = header+"\n\n"+msgBody;
		msg = header+"\n\n"+msgBody;
		return msg;
	}
}
