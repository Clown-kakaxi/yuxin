package com.yuchengtech.bcrm.nioclient;

import java.util.ArrayList;
import java.util.Map;

import com.yuchengtech.bcrm.util.CrmConstants;
import com.yuchengtech.bcrm.util.CrmUtil;
import com.yuchengtech.bcrm.xml.DomXml;
import com.yuchengtech.crm.exception.BizException;


/**
 * 本类实现将ESB的报文信息，转化成model对象
 * @author WuDi
 *
 */
public class EsbUtil {
	
//	private static NIOClient esbService;
	
//	public static <T> T[] listToEntityBean(ArrayList list, Class entityBeanClass) {
//		
//		if (list==null || list.size()==0){
//			return null;
//		}
//		
//		//获取实体bean的方法集合
//		Method []entityBeanMethod= entityBeanClass.getDeclaredMethods();
//		//创建实体bean数组
//		T[] entityBeans = (T[])java.lang.reflect.Array.newInstance(entityBeanClass, list.size());
//		Iterator listTrt = list.iterator();
//		while(listTrt.hasNext()){//迭代每条记录
//			listTrt.next();
//		}
//		return null;
//		
//	}
	
	/**
	 * 获取ESB服务器
	 */
	public static NIOClient getEsbService() {
		NIOClient esbService = null;
//		if (esbService == null) {
		try {
			esbService = new NIOClient(CrmConstants.ESB_SERVICE_HOST, CrmConstants.ESB_SERVICE_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
		if (esbService == null) {
			throw new BizException(1, 0, "1003", "构建ESB服务失败!");
		}
		return esbService;
	}
	
	/**
	 * 将ESB报文信息转换成map对象,接口转换成功，返回true，异常情况返回false
	 * @param receiveText ESB返回报文信息
	 * @param txnCd 交易码
	 * @return 返回值
	 */
	public static Boolean parseStringToMap(String receiveText) {
		try {
			Map returnMap = DomXml.readString2Xml(receiveText);
			if (returnMap != null) {
				String faultCode = (String) returnMap.get("FaultCode");
				if (CrmConstants.ESB_SUCCESS_CODE.equals(faultCode)) {
					return true;
				} else {
					String faultString = (String) returnMap.get("FaultString");
					throw new BizException(1, 2, "1003", faultString);
				}
			} else {
				return false;
			}
		} catch (BizException e) {
			e.printStackTrace();
			throw new BizException(1, 2, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1003", "ESB返回报文转换异常!");
		}
	}
	
	/**
	 * 将ESB报文信息转换成map对象,不自动抛出业务异常，需要手工判断
	 * @param receiveText ESB返回报文信息
	 * @param txnCd 交易码
	 * @return 返回值
	 */
	public static Map<String, String> parseStringToMapForUnThrowException(String receiveText) {
		try {
			Map<String, String> returnMap = DomXml.readString2Xml(receiveText);
			return returnMap;
		} catch (BizException e) {
			e.printStackTrace();
			throw new BizException(1, 2, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1003", "ESB返回报文转换异常!");
		}
	}
	
	/**
	 * 发送ESB消息方法
	 * @param sendMsg 报文体内容
	 * @param esbTxCode esb交易码
	 * @return 成功标识
	 * @throws Exception 异常
	 */
	public static Boolean sendMsgToEcif(String sendMsg, String esbTxCode) throws Exception {
    	try {
        	NIOClient esbServer = EsbUtil.getEsbService();
    		String receiveText = esbServer.interactive(sendMsg, esbTxCode);
    		if (CrmUtil.isEmpty(receiveText)) {
    			throw new BizException(1, 2, "1003", "解析报文异常!");
    		}
    		Boolean returnFlag = EsbUtil.parseStringToMap(receiveText);
    		return returnFlag;
    	} catch (BizException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1003", "发送ESB消息失败!");
		}
	}
	
	/**
	 * 发送ESB消息方法
	 * @param sendMsg 报文体内容
	 * @param esbTxCode esb交易码
	 * @return 成功标识
	 * @throws Exception 异常
	 */
	public static Map<String, String> sendMsgToEcifUnThrowException(String sendMsg, String esbTxCode) throws Exception {
    	try {
        	NIOClient esbServer = EsbUtil.getEsbService();
    		String receiveText = esbServer.interactive(sendMsg, esbTxCode);
    		if (CrmUtil.isEmpty(receiveText)) {
    			throw new BizException(1, 2, "1003", "解析报文异常!");
    		}
    		Map<String, String> map = EsbUtil.parseStringToMapForUnThrowException(receiveText);
    		return map;
    	} catch (BizException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1003", "发送ESB消息失败!");
		}
	}
	
	
	/**
	 * 通过ESB查询ECIF列表信息
	 * @param receiveText ESB返回报文信息
	 * @param txnCd 交易码
	 * @return 返回值
	 */
	public static ArrayList<Map<String, String>> getEsbList(String receiveText, String txnCd, String listRowName) {
		try {
			Map returnMap = DomXml.readRowListToMap(receiveText, listRowName);
			if (returnMap != null) {
				String faultCode = (String) returnMap.get("faultCode");
				if (CrmConstants.ESB_SUCCESS_CODE.equals(faultCode)) {
					if (returnMap.get("rowList") != null) {
						ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>)returnMap.get("rowList");
						return list;
					}
				} else {
					String faultString = (String) returnMap.get("faultString");
					throw new BizException(1, 2, "1003", faultString);
				}
			} else {
				return null;
			}
		} catch (BizException e) {
			e.printStackTrace();
			throw new BizException(1, 2, e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1003", "ESB返回报文转换异常!");
		}
		return null;
	}
	
	public static ArrayList<Map<String, String>> geEcifListMsg(String sendMsg, String esbTxCode, String listRowName) throws Exception {
    	try {
        	NIOClient esbServer = EsbUtil.getEsbService();
    		String receiveText = esbServer.interactive(sendMsg, esbTxCode);
    		if (CrmUtil.isEmpty(receiveText)) {
    			throw new BizException(1, 2, "1003", "解析报文异常!");
    		}
    		ArrayList<Map<String, String>> esbList = EsbUtil.getEsbList(receiveText, esbTxCode, listRowName);
    		return esbList;
    	} catch (BizException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1003", "发送ESB消息失败!");
		}
	}
}
