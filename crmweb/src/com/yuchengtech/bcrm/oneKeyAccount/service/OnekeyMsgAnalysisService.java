package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.io.StringReader;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.util.Log;
import org.xml.sax.InputSource;

import com.csii.pe.common.util.CsiiUtils;
import com.csii.pe.security.EnDecrypt;
import com.csii.pe.security.EnDecryptFactory;

/**
 * @项目名称：CRM一键开户
 * @类名称：OnekeyMsgAnalysisService
 * @类描述：一键开户接口响应报文分析
 * @功能描述:一键开户接口响应报文分析
 * @创建人：wx
 * @创建时间：2017年8月29日上午9:23:33
 * @修改人：wx
 * @修改时间：2017年8月29日上午9:23:33
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class OnekeyMsgAnalysisService {
//	
//	private OcrmFCiOnekeyopenLog crmDBLog = new OcrmFCiOnekeyopenLog();
//	private AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	private SimpleDateFormat sdf_8 = new SimpleDateFormat("yyyy-MM-dd");
//	private SimpleDateFormat sdf_16 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 预约查询响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	public Map<String, Object> analysisApointmentAccountMsg(String msg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (msg == null || msg.equals("")) {
			retMap.put("status", "error");
			retMap.put("msg", "响应报文为空");
		}
		//
		return retMap;
	}

	/**
	 * 联网核查响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unused")
	public Map<String, Object> analysisNetCheckMsg(String msg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (msg == null || msg.equals("")) {
			retMap.put("status", "error");
			retMap.put("msg", "响应报文为空");
			return retMap;
		}
		try {
			if (msg.startsWith("9010")) {
				retMap.put("status", "error");
				retMap.put("msg", "联网核查失败：" + msg.substring(6, msg.length()));
			} else {
				int[] itemLen = { 4, 2, 50, 30, 18, msg.length() - 105, 1 };//
				String[] decodeMsg = decodeMsg(msg, "GBK", itemLen);
				if (decodeMsg.length == 7) {
					String MESID = decodeMsg[0];// 请求类型
					String CheckResult = decodeMsg[1];// 核对结果
					String IssueOffice = decodeMsg[2];// 签发机关
					String Name = decodeMsg[3];// 姓名
					String identId = decodeMsg[4];// 身份证号码
					String Photo = decodeMsg[5];// 被核对人照片（经过BASE64转码后的数据）
					String Separator = decodeMsg[6];// 记录分割符
					if (CheckResult != null) {
						if (CheckResult.equals("00")) {
							retMap.put("status", "success");
							retMap.put("msg", "00  公民身份号码与姓名一致，且存在照片");
						} else if (CheckResult.equals("01")) {
							retMap.put("status", "success");
							retMap.put("msg", "01  公民身份号码与姓名一致，但不存在照片");
						} else if (CheckResult.equals("02")) {
							retMap.put("status", "error");
							retMap.put("msg", "02  公民身份号码存在，但与姓名不匹配");
						}else if(CheckResult.equals("03")){
							retMap.put("status", "error");
							retMap.put("msg", "03  公民身份号码不存在");
						}else if(CheckResult.equals("04")){
							retMap.put("status", "error");
							retMap.put("msg", "04  其他错误");
						} else {
							retMap.put("status", "error");
							retMap.put("msg", "报文解析失败");
						}
					} else {
						retMap.put("status", "error");
						retMap.put("msg", "报文解析失败");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("status", "error");
			retMap.put("msg", "系统错误");
			return retMap;
		}
		return retMap;
	}

	/**
	 * 电信黑名单查询响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	public Map<String, Object> analysisBlackListCheckMsg(String msg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (msg == null || msg.equals("")) {
			retMap.put("status", "error");
			retMap.put("msg", "响应报文为空");
			return retMap;
		}
		try {
			int[] itemLen = { 4, 6, 8, 6, 5, 100, 12, 2, 325 };
			String[] decodeMsg = decodeMsg(msg, "GBK", itemLen);
			// System.out.println("decodeMsg:【"+JSONArray.fromObject(decodeMsg).toString()+"】");
			String s_respcode = decodeMsg[4];// 响应码
			// String s_respmsg = decodeMsg[5];//响应信息
			String s_zt = decodeMsg[7];// 黑名单查询状态
			String s_ms = decodeMsg[8];// 黑名单查询描述
			if (s_respcode != null && s_respcode.equals("00000")) {// 成功响应
				if (s_zt.equals("00")) {
					retMap.put("status", "success");
					retMap.put("msg", s_ms);
				} else if (s_zt.equals("01")) {
					retMap.put("status", "error");
					retMap.put("msg", s_ms);
				} else if (s_zt.equals("02")) {
					retMap.put("status", "error");
					retMap.put("msg", s_ms);
				}
			} else {
				retMap.put("status", "error");
				retMap.put("msg", "响应报文异常:报文内容【" + msg + "】");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("status", "error");
			retMap.put("msg", "响应报文异常: "+e);
			return retMap;
		}
		return retMap;
	}

	
	/**
	 * 解析卡系统开通定制卡的响应报文
	 * @param msg
	 * @return
	 */
	public Map<String, Object> analysisReserceICCardAccountMsg(String msg){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(msg)){
				String logMsg = "开通定制卡响应报文为空";
				Log.error(logMsg);
				retMap.put("status", "error");
				retMap.put("msg", logMsg);
				return retMap;
			}
			String field1 = msg.substring(4, 20);
			String field2 = msg.substring(20, 25);
			String field3 = msg.substring(25, 61);
			String field4 = msg.substring(61, msg.length());
			if(field2.trim().equals("SUCC")){
				String logMsg = field3.trim();
				String cardNo = field4.trim();
				Log.info(logMsg);
				retMap.put("status", "success");
				retMap.put("msg", logMsg);
				retMap.put("cardNo", cardNo);
			}else{
				String logMsg = field3.trim();
				Log.error(logMsg);
				retMap.put("status", "error");
				retMap.put("msg", logMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，请联系管理员";
			Log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
		}
		return retMap;
	}
	

	/**
	 * IC卡系统响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	public Map<String, Object> analysisICCardAccountMsg(String msg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (msg == null || msg.equals("")) {
			retMap.put("status", "error");
			retMap.put("msg", "响应报文为空");
			return retMap;
		}
		int[] itemLen = { 4, 10, 6, 5, 40 };
		String[] decodeMsg = decodeMsg(msg, "GBK", itemLen);
		if (decodeMsg.length == 5) {
			String resCode = decodeMsg[3];// 应答码
			if (resCode != null && resCode.trim().equals("SUCC")) {
				retMap.put("status", "success");
				retMap.put("msg", "开卡成功");
			} else {
				String resMsg = decodeMsg[3] + decodeMsg[4];// 应答内容
				retMap.put("status", "fail");
				retMap.put("msg", "开卡失败：【" + resMsg + "】");
			}
		} else {
			retMap.put("status", "error");
			retMap.put("msg", "报文解析失败!!!");
		}
		return retMap;
	}

	
	/**
	 * 网银开户响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> analysisNetBankAccountMsg(String msg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(msg.trim())) {
			retMap.put("status", "error");
			retMap.put("msg", "响应报文为空");
			return retMap;
		}
		// dom4j解析xml格式的字符串
		try {
			SAXReader reader = new SAXReader();
			StringReader sr = new StringReader(msg);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List<Element> headList = root.elements("Head");
			Element ele_head = headList.get(0);
			Element e_RejCode = ele_head.element("_RejCode");
			String s_RejCode = e_RejCode.getTextTrim();// 返回码
			Element e_RejMsg = ele_head.element("_RejMsg");
			String s_RejMsg = e_RejMsg.getTextTrim();// 返回信息
			if (s_RejCode != null && s_RejCode.equals("000000")) {
				//解密密码
				List<Element> bodyList = root.elements("Body");
				Element ele_body = bodyList.get(0);
				//CifNo
				Element e_CifNo = ele_body.element("CifNo");
				if(e_CifNo == null || StringUtils.isEmpty(e_CifNo.getTextTrim())){
					retMap.put("status", "error");
					retMap.put("msg", "开通网银失败，从响应报文中获取核心客户号失败");
					return retMap;
				}
				retMap.put("cifNo", e_CifNo.getTextTrim());
				//Password
				Element e_psw = ele_body.element("Password");
				if(e_psw != null){
					String str_psw = e_psw.getTextTrim();
					if(!StringUtils.isEmpty(str_psw)){
						String str_dePsw = this.decrypt(str_psw);
						retMap.put("status", "success");
						retMap.put("msg", "开通网银成功");
						retMap.put("psw", str_dePsw);
					}else{
						retMap.put("status", "error");
						retMap.put("msg", "开通网银失败，返回密码为空");
					}
				}else{
					retMap.put("status", "error");
					retMap.put("msg", "开通网银失败，返回报文中没有密码节点");
				}
			} else {
				retMap.put("status", "error");
				retMap.put("msg", "网银开通失败，具体原因：" + s_RejMsg + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("status", "error");
			retMap.put("msg", "网银开通失败，具体原因：" + e + "");
			e.printStackTrace();
		}
		return retMap;
	}

	
public String decrypt(String pwd) throws Exception{
	Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
	if(provider == null){
		Security.addProvider(new BouncyCastleProvider());
	}
	byte[] key1Buffer = "123456781234567812345678".getBytes();
	byte[] initVector = "sPKdhdz3".getBytes();
	int key1Offset = 100;
	EnDecryptFactory.registerSecretKey(key1Offset, "DESede", key1Buffer);
	EnDecrypt enDecrypt = EnDecryptFactory.getInstance();
	byte[] textBuffer = enDecrypt.deCrypt("DESede", CsiiUtils.hexStr2Bytes(pwd),key1Offset, "CBC/PKCS5Padding", initVector);
	return new String(textBuffer);
}

/*
public static void main(String[] args) {
	String pwd = "038BD53160FD7683"; 
	try {
		String psd = new OnekeyMsgAnalysisService().decrypt(pwd);
		System.err.println("psd:"+psd);
	} catch (Exception e) {
		e.printStackTrace();
	}
}*/

	

	/**
	 * 定长报文解析
	 * 
	 * @param msg
	 *            报文内容
	 * @param charset
	 *            编码格式
	 * @param itemLen
	 *            各字段长度
	 * @return
	 */
	private String[] decodeMsg(String msg, String charset, int[] itemLen) {

		if (msg == null || msg.equals("") || itemLen == null
				|| itemLen.length < 1) {
			return null;
		}
		if (charset == null || charset.equals("")) {
			charset = "GBK";
		}
		String[] retArr = new String[itemLen.length];
		try {
			byte[] b_msg = msg.getBytes(charset);
			int startIdx = 0;
			for (int i = 0; i < itemLen.length; i++) {
				int currLen = itemLen[i];
				byte[] bt = new byte[currLen];
				System.arraycopy(b_msg, startIdx, bt, 0, currLen);
				startIdx += currLen;
				String currStr = new String(bt, charset);
				retArr[i] = currStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println(e.getMessage());
			return null;
		}
		return retArr;
	}
}
