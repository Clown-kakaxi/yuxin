package com.ytec.fubonecif.service.svc.comb;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.PropertyPlaceholderConfigurerExt;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * ECIF请求核心开户
 * 
 * @author Administrator
 * 
 */
@Service
public class OnekeyAccount2CB implements IEcifBizLogic {

	private static Logger log = LoggerFactory.getLogger(OnekeyAccount2CB.class);
	private JPABaseDAO baseDAO;
	private String TlrNo;
	
	
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		String reqMsg = ecifData.getPrimalMsg();// 请求报文的内容
		// 流水号--ReqSeqNo
		String reqSeqNo = ecifData.getReqSeqNo();
		if (StringUtils.isEmpty(reqSeqNo)) {
			String msg = "信息不完整，报文请求节点中reqSeqNo不允许为空";
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		Element body = ecifData.getBodyNode();// 获取节点
		TlrNo = ecifData.getTlrNo();
		
		String txCode = body.element("txCode").getTextTrim();// 获取交易编号
		if (StringUtils.isEmpty(txCode)) {
			String msg = "信息不完整，报文请求节点中txCode不允许为空";
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		//获取证件类型、证件号码
		String custId = body.element("CUSTID").getTextTrim();// 获取证件号码
		String identNo = body.element("BRAIDID").getTextTrim();// 获取证件号码
		String ONLYPUSHINFO = body.element("ONLYPUSHINFO").getTextTrim();//是否只推送信息不开户
		boolean isOnlyPushInfo = ONLYPUSHINFO != null && ONLYPUSHINFO.equals("true") ? true : false;
		// 校验非空字段
		boolean checkRes = this.checkNodeContent(ecifData);
		if (checkRes) {
			String reqCBMsg = this.packageReqMsg(ecifData);//请求报文
			String resCBMsg = this.process(reqCBMsg);//响应报文
			if (StringUtils.isEmpty(resCBMsg)) {
				String msg = "核心系统返回报文为空";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), msg);
				return;
			}
			//报文里一些关键字段
			String textTxStatCode = "";
			String textTxStatString = "";
			String textTxStatDesc = "";
			String textResult = "";
			String textCUSTCOD = "";
			String textACCNO = "";
			String textACCCY = "";
			String textErrNo = "";
			String textErrMsg = "";
			// 解析响应报文
			resCBMsg = resCBMsg.substring(8);
			SAXReader reader = new SAXReader();
			StringReader sr = new StringReader(resCBMsg);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			//ResponseTail部分
			Element resTail = root.element("ResponseTail");
			if(resTail != null){
				textTxStatCode = resTail.elementTextTrim("TxStatCode");
				textTxStatDesc = resTail.elementTextTrim("TxStatDesc");
			}
			//ResponseBody部分
			Element resBody = root.element("ResponseBody");
			if(resBody != null){
				textResult = resBody.elementTextTrim("Result");
				textCUSTCOD = resBody.elementTextTrim("CUSTCOD");
				textACCNO = resBody.elementTextTrim("ACCNO");
				textACCCY = resBody.elementTextTrim("ACCCY");
				textErrNo = resBody.elementTextTrim("ErrNo");
				textErrMsg = resBody.elementTextTrim("ErrMsg");
			}
			if(StringUtils.isEmpty(textTxStatCode)){//没有返回状态
				dealResponse(ecifData, textResult, textACCCY, textCUSTCOD, custId, textACCNO, textErrNo, textErrMsg, isOnlyPushInfo);
			}else{
				if(textTxStatCode.equals("000000")){
					dealResponse(ecifData, textResult, textACCCY, textCUSTCOD, custId, textACCNO, textErrNo, textErrMsg, isOnlyPushInfo);
				}else{
					if(!StringUtils.isEmpty(textTxStatCode) && !StringUtils.isEmpty(textTxStatDesc)){
						log.error(textTxStatDesc);
						ecifData.setStatus(textTxStatCode, textTxStatDesc);
						ecifData.setSuccess(false);
						return;
					}else{
						String errorMsg = "EAI响应报文字段{TxStatCode,TxStatDesc}不全";
						log.error(errorMsg);
						ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
						ecifData.setSuccess(false);
						return;
					}
				}
			}
		}
		return;
	}

	/**
	 * 检查报文字段
	 * 
	 * @param ecifData
	 * @return
	 */
	private boolean checkNodeContent(EcifData ecifData) {
		boolean flag = true;
		try {
			Element body = ecifData.getBodyNode();// 获取节点
			String[] checkNodeNms = new String[] { "IDCODE", "BRAIDID", "CUFULNM", "CUSNM", "CORCOUN", "RISCOUN",
					"BRANCH", "ACCODE" };
			for (int i = 0; i < checkNodeNms.length; i++) {
				Element ele = body.element(checkNodeNms[i]);
				if (ele == null || StringUtils.isEmpty(ele.getTextTrim())) {
					String msg = "信息不完整，报文请求节点中{" + checkNodeNms[i] + "}不允许为空";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
					ecifData.setSuccess(false);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 拼接报文
	 * 
	 * @param ecifData
	 * @return
	 */
	private String packageReqMsg(EcifData ecifData) {
		StringBuffer sb_msg = new StringBuffer();
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df14 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmss");
		int ranI = new Random().nextInt(90) + 10;
		try {
			sb_msg.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
			sb_msg.append("<TransBody>\n");
			StringBuffer sb_msgHeader = new StringBuffer();
			// 获取将要发送到核心的内容字段
			sb_msgHeader.append("    <RequestHeader>\n");
			sb_msgHeader.append("        <ReqSysCd>ECF</ReqSysCd>\n");
			sb_msgHeader.append("        <ReqSeqNo>" + df20.format(new Date()) + "</ReqSeqNo>\n");
			sb_msgHeader.append("        <ReqDt>" + df8.format(new Date()) + "</ReqDt>\n");
			sb_msgHeader.append("        <ReqTm>" + df10.format(new Date()) + "</ReqTm>\n");
			sb_msgHeader.append("        <DestSysCd>CB</DestSysCd>\n");
			sb_msgHeader.append("        <ChnlNo>82</ChnlNo>\n");
			sb_msgHeader.append("        <BrchNo>6801</BrchNo>\n");
			sb_msgHeader.append("        <BizLine>6491</BizLine>\n");
			sb_msgHeader.append("        <TrmNo>TRM10010</TrmNo>\n");
			sb_msgHeader.append("        <TrmIP>127.0.0.1</TrmIP>\n");
			sb_msgHeader.append("        <TlrNo>0000</TlrNo>\n");
			sb_msgHeader.append("    </RequestHeader>\n");
			sb_msg.append(sb_msgHeader);
			Element body = ecifData.getBodyNode();// 获取节点 
			//对证件类型进行转换
			String identType = body.elementTextTrim("IDCODE");
			//对地址进行特殊处理
			String addrInfo = body.elementTextTrim("MAILAD1");
			String[] fullAddrInfo = this.getAddrInfo(addrInfo);
			StringBuffer sb_body = new StringBuffer();
			sb_body.append("    <RequestBody>\n");
			sb_body.append("        <txCode>CRM1</txCode>\n");// 系统代号
			sb_body.append("        <SysFun>ECF</SysFun>\n");// 系统代号
			sb_body.append("        <TranFun>CRM1</TranFun>\n");// 交易代号
			sb_body.append("        <TranType>1</TranType>\n");// 数据类型1
			sb_body.append("        <VerNo>" + df14.format(new Date()) + ranI + "</VerNo>\n");// 流水号
			sb_body.append("        <Filler>" + this.getStringByLimitLen(body.elementTextTrim("Filler"), 31) + "</Filler>\n");// 流水号
			sb_body.append("        <IDCODE>" + identType + "</IDCODE>\n");// 证件类型
			sb_body.append("        <BRAIDID>" + this.getStringByLimitLen(body.elementTextTrim("BRAIDID"), 20) + "</BRAIDID>\n");// 证件号码
			sb_body.append("        <REFID>" + this.getStringByLimitLen(body.elementTextTrim("REFID"), 20) + "</REFID>\n");// 台胞证/港澳证号码
			sb_body.append("        <CUFULNM>" + this.getStringByLimitLen(body.elementTextTrim("CUFULNM"), 50) + "</CUFULNM>\n");// 中文名
			sb_body.append("        <CUSNM>" + this.getStringByLimitLen(body.elementTextTrim("CUSNM"), 35) + "</CUSNM>\n");// 英文名
			sb_body.append("        <CORCOUN>" + this.getStringByLimitLen(body.elementTextTrim("CORCOUN"), 3) + "</CORCOUN>\n");// 国别
			sb_body.append("        <RISCOUN>" + this.getStringByLimitLen(body.elementTextTrim("RISCOUN"), 3) + "</RISCOUN>\n");// 风险国别
			sb_body.append("        <SEXIND>" + this.getStringByLimitLen(body.elementTextTrim("SEXIND"), 1) + "</SEXIND>\n");// 性别
			sb_body.append("        <ESTDATE>" + this.getStringByLimitLen(body.elementTextTrim("ESTDATE"), 8) + "</ESTDATE>\n");// 出生日期
			sb_body.append("        <MAILAD1>" + fullAddrInfo[0] + "</MAILAD1>\n");// 地址1
			sb_body.append("        <MAILAD2>" + fullAddrInfo[1] + "</MAILAD2>\n");// 地址1
			sb_body.append("        <MAILAD3>" + fullAddrInfo[2] + "</MAILAD3>\n");// 地址1
			sb_body.append("        <CUSTEL1>" + this.getStringByLimitLen(body.elementTextTrim("CUSTEL1"), 14) + "</CUSTEL1>\n");// 电话1
			sb_body.append("        <CUSTEL2>" + this.getStringByLimitLen(body.elementTextTrim("CUSTEL2"), 14) + "</CUSTEL2>\n");// 电话1
			sb_body.append("        <CUSTEL3>" + this.getStringByLimitLen(body.elementTextTrim("CUSTEL3"), 14) + "</CUSTEL3>\n");// 电话1
			sb_body.append("        <EMAILAD>" + this.getStringByLimitLen(body.elementTextTrim("EMAILAD"), 35) + "</EMAILAD>\n");// 邮件地址
			sb_body.append("        <BRANCH>" + this.getStringByLimitLen(body.elementTextTrim("BRANCH"), 3) + "</BRANCH>\n");// 行号
			sb_body.append("        <ACCODE>" + this.getStringByLimitLen(body.elementTextTrim("ACCODE"), 10) + "</ACCODE>\n");// 客户经理代码
			sb_body.append("        <COCODE>" + this.getStringByLimitLen(body.elementTextTrim("COCODE"), 10) + "</COCODE>\n");// 操作员工号
//			sb_body.append("        <NEWOLD>" + this.getStringByLimitLen(body.elementTextTrim("NEWOLD"), 1) + "</NEWOLD>\n");// 新旧户标识
			sb_body.append("        <ACCATE>" + this.getStringByLimitLen(body.elementTextTrim("ACCATE"), 3) + "</ACCATE>\n");// 账号属性
			sb_body.append("    </RequestBody>\n");
			sb_msg.append(sb_body);
			sb_msg.append("</TransBody>\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb_msg.toString();
	}

	private String process(String mxlmsg) {
		System.out.println("核心开户请求报文:" + mxlmsg);
		log.info("核心开户请求报文:" + mxlmsg);
		String msg = mxlmsg;
		/*String ip = "10.20.35.242";
		int port = 12027;*/
		String ip = PropertyPlaceholderConfigurerExt.getContextProperty("CBS.IP");
		String portStr = PropertyPlaceholderConfigurerExt.getContextProperty("CBS.PORT");
		int port = Integer.parseInt(portStr);
		
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(String.format("%08d", msg.getBytes("GBK").length) + msg);
		} catch (Exception e) {
			log.info("调用核心系统超时!");
		}
		log.info("核心开户返回报文:" + resp);
		return resp;
	}
	
	/**
	 * 核心要求，对全半角字符进行特殊处理
	 * @param oriStr
	 * @param limitLen
	 * @return
	 */
	public String getStringByLimitLen(String oriStr, int limitLen){
		String retStr = "";
		if(oriStr == null){
			oriStr = "";
		}
		oriStr = oriStr.trim();
		int oriLen = oriStr.length();
		if(oriLen > limitLen){
			oriStr = oriStr.substring(0, (oriLen - limitLen));
		}
		int fullCharCount = 0;
		for(int i = 0; i < oriLen; i++){
			String str1 = oriStr.substring(i, i+1);
			boolean isFull1 = str1.length() == str1.getBytes().length ? false : true;//相等为半角，不等为全角
			if(isFull1){
				fullCharCount++;
			}
			if((i < (oriLen - 1))){//如果不是最后一个
				String str2 = oriStr.substring(i+1, i+2);
				boolean isFull2 = str2.length() == str2.getBytes().length ? false : true;
				if((isFull1 && !isFull2) || (!isFull1 && isFull2)){
					retStr += str1 + " ";
				}else{
					retStr += str1;
				}
			}else if(i == (oriLen - 1)){
				retStr += oriStr.substring(i, i+1);
			}
		}
		if(fullCharCount > 0){
			retStr = " " + retStr + " ";
		}
		int endLen = retStr.getBytes().length;
		if(endLen <= limitLen){
			for (int i = 0; i < (limitLen - endLen); i++) {
				retStr += " ";
			}
		}else{
			log.error("处理报文字段时出错：字段内容["+oriStr+"],长度限制["+limitLen+"]");
			return "";
		}
		return retStr;
	}
	
	
	
	/**
	 * 对地址进行特殊处理
	 * @param oriAddr
	 * @return
	 */
	public String[] getAddrInfo(String oriAddr){
		String[] addrs = new String[3];
		int oriLen = oriAddr.length();
		int fullCount = 0;
		String endStr = "";
		for (int i = 0; i < oriLen; i++) {
			String str1 = oriAddr.substring(i, (i+1));
			boolean isFull1 = str1.length() == str1.getBytes().length ? false : true;//是否是全角
			if(isFull1){
				fullCount++;
			}
			if(i < (oriLen - 1)){//不是最后一个
				String str2 = oriAddr.substring((i + 1), (i + 2));
				boolean isFull2 = str2.length() == str2.getBytes().length ? false : true;//是否是全角
				if((isFull1 && !isFull2) || (!isFull1 && isFull2)){
					endStr += (str1 + " ");
				}else{
					endStr += str1;
				}
			}else if(i == (oriLen - 1)){
				endStr += oriAddr.substring(i, i+1);
			}
		}
		if(fullCount > 0){
			endStr = " " + endStr + " ";
		}
		int desLen = endStr.getBytes().length;
		for (int i = 0; i < (105 - desLen); i++) {
			endStr += " ";
		}
		byte[] endB = endStr.getBytes();
		byte[] b1 = Arrays.copyOfRange(endB, 0, 35);
		byte[] b2 = Arrays.copyOfRange(endB, 35, 70);
		byte[] b3 = Arrays.copyOfRange(endB, 70, 105);
		addrs[0] = new String(b1);
		addrs[1] = new String(b2);
		addrs[2] = new String(b3);
		return addrs;
	}
	
	
	
	/**
	 * 处理响应报文
	 * @param ecifData		交易数据
	 * @param textResult	核心返回状态
	 * @param textACCCY		币种
	 * @param textCUSTCOD	核心客户号
	 * @param identType		证件类型
	 * @param identNo		证件号码
	 * @param textACCNO		核心客户账号
	 * @param textErrNo		核心返回错误编号
	 * @param textErrMsg	核心返回错误信息
	 */
	public void dealResponse(EcifData ecifData, String textResult, String textACCCY, String textCUSTCOD, String custId, String textACCNO, String textErrNo, String textErrMsg, boolean isOnlyPushInfo){
		//EAI返回报文中没有任何响应信息
		if(StringUtils.isEmpty(textResult)){
			String errorMsg = "EAI返回报文中没有任何响应信息";
			log.error(errorMsg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
			ecifData.setSuccess(false);
			return;
		}else{//虽然没有ResponseTail返回信息，但是ResponseBody中有返回信息
			if(textResult.equals("OK")){//成功开户
				String hxAcccy1 = "";
				String hxAcccy2 = "";
				if(!isOnlyPushInfo){
					if(textACCCY.length() >= 6){
						hxAcccy1 = textACCCY.substring(0, 3);
						hxAcccy2 = textACCCY.substring(3, 6);
					}
					//更新核心客户号
					String upJql = "update MCiCustomer t set t.coreNo=:coreNo where t.custId=:custId";
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("coreNo", textCUSTCOD);
					params.put("custId", custId);
					int upRes = this.baseDAO.batchExecuteWithNameParam(upJql, params);
					if(upRes < 0){
						String errorMsg = "核心开户成功，但更新ECIF客户信息时出错，请联系管理员";
						log.error(errorMsg);
						ecifData.setSuccess(false);
						ecifData.getWriteModelObj().setResult("TxStatCode", ErrorCode.ERR_ALL.getCode());
						ecifData.getWriteModelObj().setResult("TxStatDesc", errorMsg);
						ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
						return;
					}
					//添加交叉索引表信息
					String crossIndexId = "100000000";
					String seqSql = "select SEQ_CROSSINDEX_ID.Nextval from dual";//主键
					List<Object> seqL = this.baseDAO.findByNativeSQLWithNameParam(seqSql, null);
					if(seqL != null && seqL.size() == 1){
						crossIndexId = seqL.get(0).toString();
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
					String format = sdf.format(new Date());
					String insertSql = "INSERT INTO M_CI_CROSSINDEX";
					insertSql += "(CROSSINDEX_ID, SRC_SYS_NO, SRC_SYS_CUST_NO, CUST_ID, LAST_UPDATE_SYS, LAST_UPDATE_USER, LAST_UPDATE_TM, TX_SEQ_NO)";
					insertSql += " VALUES('" + crossIndexId + "', 'CB', '" + textCUSTCOD + "',";
					insertSql += " '" + custId + "', 'ECIF', 'CB',";
					insertSql += " TO_TIMESTAMP('"+format+"', 'yyyy--mm-dd hh24:mi:ss:ff'),'" + ecifData.getReqSeqNo() + "')";
					this.baseDAO.batchExecuteNativeWithIndexParam(insertSql);
				}
				ecifData.resetStatus();
				ecifData.getWriteModelObj().setResult("TxStatCode", "000000");
				ecifData.getWriteModelObj().setResult("TxStatDesc", "核心开户成功");
				ecifData.getWriteModelObj().setResult("CUSTCOD", textCUSTCOD);
				ecifData.getWriteModelObj().setResult("ACCNO", textACCNO);
				ecifData.getWriteModelObj().setResult("ACCCY1", hxAcccy1);
				ecifData.getWriteModelObj().setResult("ACCCY2", hxAcccy2);
//				ecifData.setStatus(ErrorCode.SUCCESS);
				ecifData.setStatus("000000", "核心开户成功");
				ecifData.setSuccess(true);
				log.info("核心开户成功");
				return;
			}else if("ER".equals(textResult)){
				//ErrNo ErrMsg
				//如果是0014，则说明是老户，需要从ECIF获取核心客户号返回到CRM
				if(textErrNo.equals("0014")){
					String custCoreNoJql = "select t from MCiCustomer t where t.custId=:custId";
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("custId", custId);
					List<MCiCustomer> custInfoList = this.baseDAO.findWithNameParm(custCoreNoJql, params);
					String ecifCoreNo = "";
					if(custInfoList != null && custInfoList.size() == 1){//有切仅有一条有效数据
						ecifCoreNo = custInfoList.get(0).getCoreNo();
					}
					ecifData.getWriteModelObj().setResult("CoreNo", ecifCoreNo == null ? "" : ecifCoreNo);//添加核心客户号，但是可能为空
				}
				//添加返回信息
				ecifData.getWriteModelObj().setResult("TxStatCode", textErrNo);
				ecifData.getWriteModelObj().setResult("TxStatDesc", textErrMsg);
				ecifData.setStatus(textErrNo, textErrMsg);
				ecifData.setSuccess(false);
				log.error("核心开户失败：["+textErrNo+"--"+textErrMsg+"]");
				return;
			}else{
				String errorMsg = "交易失败";
				log.error("交易失败");
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
				ecifData.setSuccess(false);
				return;
			}
		}
	}
}
