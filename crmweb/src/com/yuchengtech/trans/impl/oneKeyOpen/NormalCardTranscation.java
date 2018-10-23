package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;

/**
 * 开通普通卡的日志工具类
 * @author Administrator
 *
 */
public class NormalCardTranscation extends BaseTransaction {

	private Logger log = LoggerFactory.getLogger(NormalCardTranscation.class);
	
	
	public NormalCardTranscation(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("CRM.CARDSYS.PORT");
		this.setTransName("卡系统开通普通卡");
		this.setHost(FileTypeConstance.getBipProperty("CRM.CARDSYS.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}
	
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxDt(new Date());
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("OpenNormalCardAccount");//开通普通卡账户
		txData.setTxName("OpenNormalCardAccount");
		txData.setTxCnName("开通普通卡");
		txData.setTxMethod("SOCKET");//通讯方式
		txData.setSrcSysCd("CRM");//请求系统编号
		txData.setSrcSysNm("CRM");//请求系统编号
		//其他参数
		return txData;
	}

	@Override
	public TxData read(TxData txData) {
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String reqMsg = txData.getReqMsg();
		String eaiReqMsg = reqMsg.substring(8, reqMsg.length());
		String msg = txData.getResMsg();
		if (msg == null || msg.equals("")) {
			String logMsg = "响应报文为空，请联系EAI或卡系统相关人员";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		if(msg.startsWith(eaiReqMsg)){
			String logMsg = "卡系统无响应，请联系EAI或卡系统相关人员";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		int[] itemLen = { 4, 10, 6, 5, 40 };
		String[] decodeMsg = decodeMsg(txData, msg, "GBK", itemLen);
		if (decodeMsg.length == 5) {
			String resCode = decodeMsg[3];// 应答码
			if (resCode != null && resCode.trim().equals("SUCC")) {
				String logMsg = "开卡成功";
				log.info(logMsg);
				this.setTxResult(txData, "0", "000000", "交易成功",  "success", logMsg);
			}else if(resCode != null && resCode.trim().equals("FSB64")){
				String logMsg = decodeMsg[4].trim();//应答内容：密码强度过低
				log.info(logMsg);
				this.setTxResult(txData, "2", "", logMsg, "failure", logMsg);
			}else {
				String resMsg = decodeMsg[4].trim();// 应答内容
				String logMsg = "开卡失败：" + resMsg;
				log.error(logMsg);
				this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			}
		} else {
			String logMsg = "报文解析失败,请联系管理员";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
		}
		return txData;
	}
}
