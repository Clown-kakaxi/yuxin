package com.yuchengtech.bcrm.nioclient;
//package com.yuchengtech.bcrm.webservice;
//
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.rpc.client.RPCServiceClient;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import com.yuchengtech.bcrm.webservice.S003001990MS5702ServiceStub.RequestBody;
//import com.yuchengtech.bcrm.webservice.S003001990MS5702ServiceStub.RequestHeader;
//import com.yuchengtech.bcrm.webservice.S003001990MS5702ServiceStub.ResponseBody;
//import javax.xml.namespace.QName;
//
//public class WsUtil {
//	
//	public static int TIMEOUT = 300000;
//	
//	public static void main(String[] args) throws AxisFault {
//		try {
//			String target = "http://130.1.9.182:12117/S002001010990002?wsdl";
//			RPCServiceClient client = new RPCServiceClient();
//			Options options = client.getOptions();
//			options.setAction(target);
//			options.setManageSession(true);
//			
//			EndpointReference epr = new EndpointReference(target);
//			options.setTo(epr);
//			options.setTimeOutInMilliSeconds(TIMEOUT);
//			QName qname = new QName("http://www.adtec.com.cn", "s003001990MS5702");
//			RequestHeader requestHeader = new RequestHeader();
//			requestHeader.setVerNo("20121016ESB");
//			requestHeader.setAllChnSeq("1");
//			requestHeader.setBgnRec("2");
//			requestHeader.setBrchNo("0100");
//			requestHeader.setChnlNo("4");
//			requestHeader.setFileHMac("5");
//			requestHeader.setTxnCd("S002001010990002");
//			RequestBody requestBody = new RequestBody();
//			requestBody.setAmt("120");
//			requestBody.setAcctNo1("62242501003333999");
//			Object[] paramValues = new Object[]{requestBody,requestHeader};
//			Class<Object>[] returnTypes = new Class[]{Object.class};
//			Object[] response = client.invokeBlocking(qname, paramValues, returnTypes);
//			ResponseBody responsea = (ResponseBody)response[0];
//			System.out.println("服务器返回给客户端的报文：" + responsea);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
