package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.oneKeyAccount.utils.HSDES;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.oneKeyOpen.CheckCBAccountTransaction;
import com.yuchengtech.trans.inf.Transaction;
import com.yuchengtech.trans.socket.NioClient;

@Service
public class CheckHXOpenAccountService extends CommonService {
	private static Logger log = LoggerFactory
			.getLogger(CheckHXOpenAccountService.class);
	public CheckHXOpenAccountService() {
		JPABaseDAO<AcrmFCiCustomer, String> baseDAO = new JPABaseDAO<AcrmFCiCustomer, String>(
				AcrmFCiCustomer.class);
		super.setBaseDAO(baseDAO);
	}
	 /**
	 * 非联名户 验证核心是否可以开户
	 * @param certTypeStr
	 * @param certNoStr
	 * @return
	 */
	public Map<String,Object> checkHXOpenAccount(String certType,String certNo){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(certType) || StringUtils.isEmpty(certNo)){
			String logMsg = "参数{证件号码、证件类型}不全，请联系管理员";
			log.error(logMsg);
			modelMap.put("msg", logMsg);
			modelMap.put("success", false);
			return modelMap;
		}
		TxData txData1 = new TxData();
		//获取核心客户号
		String coreNo = getCoreNoByIdentity(certType,certNo);
		txData1.setAttribute("coreNo", coreNo);
		coreNo = null;  //回收
		try {
			certType = convertIdentityType(certType);
			if(certType == null){
				String logMsg = "证件类型转换失败，请检查选择的证件类型是否是已知证件类型";
				log.error(logMsg);
				modelMap.put("msg", logMsg);
				modelMap.put("success", false);
				return modelMap;
			}
			txData1 = sendHX(txData1, certType, certNo);
			Transaction trans1 = new CheckCBAccountTransaction(txData1);
			trans1.process();
			TxLog txLog1 = trans1.getTxLog();
			this.baseDAO.save(txLog1);
			String responseStr1 = txData1.getResMsg();
			if(StringUtils.isEmpty(responseStr1)){
				String logMsg = "响应报文为空，请联系管理员";
				log.error(logMsg);
				modelMap.put("msg", logMsg);
				modelMap.put("success", false);
				return modelMap;
			}else{
				String status = txData1.getStatus();
				String msg = txData1.getMsg();
				if(status.equals("success")){
					modelMap.put("msg", "允许开户");
					modelMap.put("success", true);
				}else{
					//modelMap.put("msg", "该客户已在核心开户");
					modelMap.put("msg", msg);
					modelMap.put("success", false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误：" + e.getMessage();
			log.error(logMsg);
			modelMap.put("msg", logMsg);
			modelMap.put("success", false);
			return modelMap;
		}
		return modelMap;
	}
	
	
	/**
	 * 联名户验证核心是否可以开户
	 * @param certType1  证件类型1
	 * @param certNo1 证件号1
	 * @param certType2 证件类型2
	 * @param certNo2 证件号2
	 * @return
	 */
	public Map<String,Object> checkHXOpenJointAccount(String certType1,String certNo1,String certType2,String certNo2){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//非空检验
		if(StringUtils.isEmpty(certType1) || StringUtils.isEmpty(certNo1) || StringUtils.isEmpty(certType2)
				|| StringUtils.isEmpty(certNo2)){
			String logMsg = "参数{主户证件号码、主户证件类型、从户证件号码、从户证件类型}不全，请联系管理员";
			log.error(logMsg);
			modelMap.put("msg", logMsg);
			modelMap.put("success", false);
			return modelMap;
		}
		TxData txData1 = new TxData();
		TxData txData2 = new TxData();
		try {
			//CRM证件类型1转换为核心证件类型
			String transCertType1 = convertIdentityType(certType1);
			if(transCertType1 == null){
				String logMsg = "证件1类型转换失败，请检查选择的证件类型是否是已知证件类型";
				log.error(logMsg);
				modelMap.put("msg", logMsg);
				modelMap.put("success", false);
				return modelMap;
			}
			//CRM证件类型2转换为核心证件类型
			String transCertType2 = convertIdentityType(certType2);
			if(transCertType2 == null){
				String logMsg = "证件2类型转换失败，请检查选择的证件类型是否是已知证件类型";
				log.error(logMsg);
				modelMap.put("msg", logMsg);
				modelMap.put("success", false);
				return modelMap;
			}
			
			//联名户是否核心开户,根据证件1、证件2查询联名户的证件类型和证件号
			StringBuilder sb1 = new StringBuilder();
			sb1.append("SELECT M.CUST_ID CUST_ID,M.IDENT_TYPE IDENT_TYPE,M.IDENT_NO IDENT_NO ")
			.append(" FROM OCRM_F_CI_CUSTJOIN_INFO T ,ACRM_F_CI_CUSTOMER M ")
			.append(" WHERE  M.CUST_ID = T.CUST_ID ")
			.append(" AND  T.IDENT_TYPE1 = '"+ certType2 +"' AND T.IDENT_NO1 = '"+certNo2+"' ")
			.append(" AND M.IDENT_TYPE ='"+ certType1 +"' AND REPLACE(M.IDENT_NO,'*','') = '"+ certNo1 +"'");
			
			List<Object[]> tempList = this.baseDAO.findByNativeSQLWithNameParam(sb1.toString(), null);
			if(tempList != null && tempList.size() > 0){
				Object[] object1 = tempList.get(0);
				if(object1 != null && object1.length >0){
					String certType3 = object1[1].toString();//联名户的证件类型
					String certNo3 = object1[2].toString();//联名户的证件号
					TxData txData3 = new TxData();
					//转换联名户的CRM证件类型为核心证件类型
					String transCertType3 = convertIdentityType(certType3);
					txData3 = sendHX(txData3, transCertType3, certNo3);
					Transaction trans3 = new CheckCBAccountTransaction(txData3);
					//执行查询交易
					trans3.process();
					TxLog txLog3 = trans3.getTxLog();
					//保存交易日志
					this.baseDAO.save(txLog3);
					String responseStr3 = txData3.getResMsg();
					if(StringUtils.isEmpty(responseStr3)){
						String logMsg = "响应报文为空，请联系管理员";
						log.error(logMsg);
						modelMap.put("msg", logMsg);
						modelMap.put("success", false);
						return modelMap;
					}else{
						String status3 = txData3.getStatus();
						if(status3.equals("success")){
							modelMap.put("certNo3", certNo3);
							modelMap.put("ecifIsOpen", true);
							//modelMap.put("msg", "允许开通联名户");
							modelMap.put("success", true);
						}else{
							modelMap.put("msg", "该联名户已在核心开户");
							modelMap.put("ecifIsOpen", true);
							modelMap.put("success", false);
						}
					}
				}
			}else{//新开的联名户
				modelMap.put("ecifIsOpen", false);
				txData1 = sendHX(txData1, transCertType1, certNo1);
				Transaction trans1 = new CheckCBAccountTransaction(txData1);
				txData2 = sendHX(txData2, transCertType2, certNo2);
				Transaction trans2 = new CheckCBAccountTransaction(txData2);
				trans1.process();
				trans2.process();
				TxLog txLog1 = trans1.getTxLog();
				TxLog txLog2 = trans2.getTxLog();
				this.baseDAO.save(txLog1);
				this.baseDAO.save(txLog2);
				String responseStr1 = txData1.getResMsg();
				String responseStr2 = txData2.getResMsg();
				if(StringUtils.isEmpty(responseStr1) || StringUtils.isEmpty(responseStr2)){
					String logMsg = "响应报文为空，请联系管理员";
					log.error(logMsg);
					modelMap.put("msg", logMsg);
					modelMap.put("success", false);
					return modelMap;
				}else{
					String status1 = txData1.getStatus();
					if(status1.equals("success")){
						modelMap.put("msg", "主户未在我行开户，无法进行开通联名户操作！");
						modelMap.put("success", false);
						return modelMap;
					}else{
						String status2 = txData2.getStatus();
						if(status2.equals("success")){
							modelMap.put("msg", "从户未在我行开户，无法进行开通联名户操作！");
							modelMap.put("success", false);
							return modelMap;
						}else{
							modelMap.put("msg", "允许开通联名户");
							modelMap.put("success", true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误：" + e.getMessage();
			log.error(logMsg);
			modelMap.put("msg", logMsg);
			modelMap.put("success", false);
			return modelMap;
		}
		return modelMap;
	}
	
	
	/**
	 * 根据开户信息验证条件表，转换证件类型，将CRM证件类型转换为核心证件类型
	 * @param certTypeStr
	 * @return String 核心证件类型,ID+表里查询到的核心类型，例如：户口本，CRM为1，核心为I,则返回IDI
	 */
	private String convertIdentityType(String certTypeStr){
		if(certTypeStr == null){
			return null;
		}
		//CC2为CRM与CB证件对应关系，REL1为CRM证件类型，REL2为核心证件类型
		String idTypeSql = "select T.REL2 from OCRM_F_SYS_ACCHK T where  T.CHK_TYPE='CC2' and T.REL1='"+certTypeStr+"'";
		List<Object> idTypeList = this.baseDAO.findByNativeSQLWithNameParam(idTypeSql, null);
		String cbIdentType = null;
		if(idTypeList != null && idTypeList.size() == 1){
			Object o = idTypeList.get(0);
			if(o != null && !o.toString().equals("")){
				cbIdentType = "ID" + o.toString();
			}
		}
		return cbIdentType;
//		if(certTypeStr.equals("0")){//境内居民身份证
//			return "IDC";
//		}else if(certTypeStr.equals("1")){//户口簿
//			return "IDI";
//		}else if(certTypeStr.equals("2")){//境外居民护照
//			return "IDF";
//		}else if(certTypeStr.equals("3")){//军官证
//			return "IDS";
//		}else if(certTypeStr.equals("8")){//外国人永久居住证
//			return "IDL";
//		}else if(certTypeStr.equals("X14")){//武警身份证件
//			return "IDW";
//		}else if(certTypeStr.equals("X5")){//边民出入境通行证
//			return "IDP";
//		}else if(certTypeStr.equals("X1")){//港澳身份证
//			return "IDE";
//		}else if(certTypeStr.equals("X2")){// 台湾身份证/临时台胞证/旅行证件
//			return "IDD";
//		}else if(certTypeStr.equals("7")){// 临时身份证
//			return "IDZ";
//		}else{
//			return null;
//		}
	}
	
	/**
	 * 请求核心检查是否允许开户
	 * @param certTypeStr
	 * @param certNoStr
	 * @return
	 */
	 public TxData sendHX(TxData txData, String certTypeStr,String certNoStr){
		try {
			DecimalFormat df = new DecimalFormat("0000");
			//证件类型，定长3个字节
			String certTypeFinal = jointStr(certTypeStr,3);
			//证件号，定长20个字节
	    	String certNoFinal = jointStr(certNoStr, 20);
			String request = "CRMFB0300000001                               "+certTypeFinal+certNoFinal;
			byte[] str = request.getBytes("GBK");
			int bLen = 4+str.length;//请求报总长度=记录报文长度位数4位 + 报文内容长度
			request = df.format(bLen)+request;
			String MACCheck = new String(HSDES.genMAC(request.getBytes("GBK")));//添加MAC校验
			request = request+MACCheck.substring(0,4).toUpperCase();// 4;//请求报文头-FILTERLEN
			txData.setReqMsg(request);//添加请求报文
		} catch (Exception e) {
			e.printStackTrace();
			log.error("系统错误：" + e.getMessage());
			return txData;
		}
		return txData;
	}

	
	/**
	 * 发送请求到核心
	 * @param param
	 * @param requestCharSet
	 * @param hostIp
	 * @param port
	 * @return
	 */
	 private String sendRequest(TxData txData, String requestCharSet,
				String hostIp, int port) {
		NioClient cl = new NioClient(hostIp, port);

		String resp = null;
		if (requestCharSet == null || requestCharSet.equals("")) {
			requestCharSet = "GBK";
		}
		resp = cl.SocketCommunication(txData, requestCharSet, "核心校验是否可以开户！");
		return resp;
	}
	 
	 /**
	  * 解析核心返回的报文
	  */
	 public  String readResponse2(String sResponse){
		String str =  "false@失败";
		if(sResponse.length()>=14){
			String sFlag = subString(sResponse,11,13);
			if("OK".equals(sFlag)){ //反馈报文头=反馈成功标示
				str =  subString(sResponse,13,14);
			}else{
				str =  "false@"+subString(sResponse,13,sResponse.length());
			}
			
		}else{
			str =  "false@反馈报文长度不足";
		}
//		System.out.println("*********************发送核心后的反馈信息*********************"+str);
		return str;
	}

	 /**
	 * 凑够长度 增删或空格补起来
	 * @param str
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String jointStr(String str,int len) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		str = (str == null)?"":str;
		
		int count = len - str.getBytes("GBK").length;
		if(count==0){
			return str;
		}else if(count<0){
			str = subString(str,0,str.length()-1);
			return jointStr(str,len);
		}else{
			sb.append(str);
			for(int i=0;i<count;i++){
				sb.append(" ");
			}
			return sb.toString();
		}
	}
	
	/**
	 * 截取字符串
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public String subString(String str,int beginIndex,int endIndex){
		if(endIndex<beginIndex){
			return "";
		}else if(str.length()>=beginIndex && str.length()>=endIndex){
			return str.substring(beginIndex,endIndex);
		}else{
			return "";
		}
	}
	/**
	 * 
	    * @Title: getCoreNoByIdentity
	    * @Description: TODO(通过证件信息去crm库取核心客户号)
	    * @param @param certTypeStr
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public String getCoreNoByIdentity(String certType,String certNo){
		String coreNo = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("certType", certType);
		params.put("certNo", certNo);
		String jql = "select t from AcrmFCiCustomer t where t.identType=:certType and t.identNo=:certNo";//
		List<AcrmFCiCustomer> custInfo = this.baseDAO.findWithNameParm(jql, params);
		if(custInfo != null && custInfo.size() >= 1){
			coreNo =  custInfo.get(0).getCoreNo();
		}
		return coreNo;
//		if(StringUtils.isEmpty(coreNo)){
//			String msg = "数据有误--该客户在核心已开户，但在ECIF和CRM中都未获取到核心客户号，请联系管理员";
//			log.error(msg);
//			retMap.put("status", "error");
//			retMap.put("msg", msg);
//			return retMap;
//		}
	
	}
}
