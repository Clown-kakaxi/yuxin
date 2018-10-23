package com.yuchengtech.emp.ecif.transaction.web;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgSimulate;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgUCC;

@Controller
@RequestMapping("/ecif/transaction/txsimulate")
public class TxSimulatedTrading  extends BaseController{



	@Autowired
	private TxMsgUCC msgUCC;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("/ecif/transaction/txmsg-simulate");
	}

	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public TxMsgSimulate sedMsg(TxMsgSimulate t) {
		
       // TxMsgSimulate t = new  TxMsgSimulate();
//		if (reqObj != null && !"".equals(reqObj)) {
//			// 解析json
//			JSONObject qObj = JSONObject.fromObject(reqObj);
//			JSONArray reqArray = qObj.getJSONArray("reqArray");
//			for (Iterator<?> it = reqArray.iterator(); it.hasNext();) {
//				JSONObject objTmp = (JSONObject) it.next();
//				
//				t.setServerType((String)objTmp.get("serverType"));
//				t.setReqMsg((String)objTmp.get("reqMsg"));
//				t.setResMsg("");
//
//			}
//		}
//		
		
		String sendBody = t.getReqMsg();
		String rcvBody ="";
		String mqType = t.getServerType();
		String params  = t.getParams();
		
		Map paramMap = new HashMap();
		if(params!=null&&!params.equals("")){
			String[] paramsArray =  params.split("\n");
			for(int i=0;i<paramsArray.length;i++){
				String[] paramsPair = paramsArray[i].split("=");
				paramMap.put(paramsPair[0].trim(), paramsPair[1].trim());
			}
		}
		
		if (mqType != null && mqType.equals("ActiveMQ")) {
			try {
				msgUCC.sendMessage(paramMap,sendBody);
				rcvBody = msgUCC.receiveMessage(paramMap);
			} catch (JMSException e) {
				//e.printStackTrace();
				rcvBody = e.toString();
			}
		} else if (mqType != null && mqType.equals("HTTP")) {
			try {
				String ip = t.getIp();
				int port =  new Integer(t.getPort());
				rcvBody = msgUCC.httpMessage(ip,port,sendBody);
			} catch (Exception e) {
				//e.printStackTrace();
				rcvBody = e.toString();
			}
		} else if (mqType != null && mqType.equals("SOCKET")) {
			try {
				String ip = t.getIp();
				int port =  new Integer(t.getPort());
				rcvBody = msgUCC.socketMessage(ip,port,sendBody);
			} catch (Exception e) {
				//e.printStackTrace();
				rcvBody = e.toString();
			}
		} else if (mqType != null && mqType.equals("WebService")) {
			try {
				String ip = t.getIp();
				int port =  new Integer(t.getPort());
				rcvBody = msgUCC.webServiceMessage(ip,port,sendBody);
			} catch (Exception e) {
				//e.printStackTrace();
				rcvBody = e.toString();
			}

		} else if (mqType != null && mqType.equals("YCESB")) {
			try {
//				rcvBody = msgUCC.esbMessage(paramMap,sendBody);
			} catch (Exception e) {
				//e.printStackTrace();
				rcvBody = e.toString();
			}
		} else if (mqType != null && mqType.equals("YCESB2")) {
			try {
				rcvBody = msgUCC.esb2Message(paramMap,sendBody);
			} catch (Exception e) {
				//e.printStackTrace();
				rcvBody = e.toString();
			}
		}
		
		t.setResMsg(rcvBody);
		return t;
		
		//return "";
		//return new ModelAndView("/ecif/transaction/txmsg-simulate", "bb",rcvBody);
	}



}
