package com.yuchengtech.bcrm.customer.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFCiOnekeyopenLog;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.oneKeyOpen.CheckCustOrderInfoTranscation;
import com.yuchengtech.trans.inf.Transaction;
/**
 * 从ECIF获取客户预约信息
 * @author wx
 *
 */
@Service
public class QueryCustOrderInfoService extends CommonService{

	private static Logger log = LoggerFactory.getLogger(QueryCustOrderInfoService.class);
	public QueryCustOrderInfoService(){
		JPABaseDAO<OcrmFCiOnekeyopenLog, String>  baseDAO = new JPABaseDAO<OcrmFCiOnekeyopenLog, String>(OcrmFCiOnekeyopenLog.class);  
		super.setBaseDAO(baseDAO);
	}
	
	public Map<String, Object> queryCustOrderInfoFromEcif(String serializeId, String flag, String orderNo, String custName, String identiType, String identiNo){
		Map<String, Object> resMap = new HashMap<String, Object>();
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSS");
		SimpleDateFormat df10 = new SimpleDateFormat("HHmmssSS");
		Date currDate = new Date();
		String _userId = auth.getUserId();
		if(_userId.length() >= 8){
			_userId = _userId.substring(0, 3) + _userId.substring(4, 8);
		}
		String ReqSeqNo = df20.format(currDate);//交易流水号(当前时间ms+工号)
		String ReqDt = df8.format(currDate);//日期
		String ReqTm = df10.format(currDate);//时间
		try {
			//拼接报文
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
			sb.append("<TransBody>\n");
			StringBuffer sb_header = new StringBuffer();
			sb_header.append("    <RequestHeader>\n");
			sb_header.append("        <ReqSysCd>CRM</ReqSysCd>\n");
			sb_header.append("        <ReqSeqNo>" + ReqSeqNo + "</ReqSeqNo>\n");
			sb_header.append("        <ReqDt>" + ReqDt + "</ReqDt>\n");
			sb_header.append("        <ReqTm>" + ReqTm + "</ReqTm>\n");
			sb_header.append("        <DestSysCd>ECIF</DestSysCd>\n");
			sb_header.append("        <ChnlNo>82</ChnlNo>\n");
			sb_header.append("        <BrchNo>" + auth.getUnitId() + "</BrchNo>\n");
			sb_header.append("        <BizLine>6491</BizLine>\n");
			sb_header.append("        <TrmNo>TRM10010</TrmNo>\n");
			sb_header.append("        <TrmIP>" + auth.getCurrentIP() + "</TrmIP>\n");
			sb_header.append("        <TlrNo>0000</TlrNo>\n");
			sb_header.append("    </RequestHeader>\n");
			sb.append(sb_header);
			StringBuffer sb_body = new StringBuffer();
			sb_body.append("    <RequestBody>\n");
			sb_body.append("        <txCode>queryCustOrderInfo</txCode>\n");
			sb_body.append("        <txName>查询预约客户信息</txName>\n");
			sb_body.append("        <authType>1</authType>\n");
			sb_body.append("        <authCode>001</authCode>\n");
			sb_body.append("        <flag>" + flag + "</flag>\n");
			sb_body.append("        <identiNo>" + identiNo + "</identiNo>\n");
			sb_body.append("        <orderNo>" + orderNo + "</orderNo>\n");
			sb_body.append("        <custName>" + custName + "</custName>\n");
			sb_body.append("        <identiType>" + identiType + "</identiType>\n");
			sb_body.append("    </RequestBody>\n");
			sb.append(sb_body);
			sb.append("</TransBody>\n");
			resMap = this.process(sb.toString(), serializeId);
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，" + e.getMessage();
			log.error(logMsg);
			resMap.put("status", "error");
			resMap.put("msg", logMsg);
		}
		return resMap;
	}
	
	
	/**
	 * 发送交易
	 * @param mxlmsg
	 * @param serializeId
	 * @return
	 */
	private Map<String, Object> process(String mxlmsg, String serializeId){
		Map<String, Object> idsMap = new HashMap<String, Object>();
		TxData txData = new TxData();
		String msg = mxlmsg;
		try {
			msg = String.format("%08d", msg.getBytes("GBK").length) + msg;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			String logMsg = "转换报文长度失败，请联系管理员";
			log.error(logMsg);
			idsMap.put("status", "error");
			idsMap.put("msg", logMsg);
			return idsMap;
		}
		txData.setReqMsg(msg);
		Transaction trans = new CheckCustOrderInfoTranscation(txData);
		trans.process();
		TxLog txLog = trans.getTxLog();
		this.baseDAO.save(txLog);
		idsMap = txData.getTxMap();
		return idsMap;
	}
}
