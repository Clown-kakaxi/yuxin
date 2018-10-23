package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;

/**
 * 联网核查日志工具
 * @author Administrator
 *
 */
public class NetContactCheckTransaction extends BaseTransaction{
	
	private Logger log = LoggerFactory.getLogger(NetContactCheckTransaction.class);
	
	public NetContactCheckTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("CRM.NETCHECK.PORT");
		this.setTransName("联网核查");
		this.setHost(FileTypeConstance.getBipProperty("CRM.NETCHECK.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GB18030");
	}
	
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("NetContactCheck");
		txData.setTxName("NetContactCheck");
		txData.setTxCnName("身份证联网核查");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		//其他参数
		return txData;
	}

	@Override
	/**
	 * 联网核查响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unused")
	public TxData read(TxData txData) {
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String msg = txData.getResMsg();
		if (msg == null || msg.equals("")) {
			String logMsg = "响应报文为空";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg,  "error", logMsg);
			return txData;
		}
		try {
			if (msg.startsWith("9010")) {
				String logMsg = "联网核查失败：" + msg.substring(6, msg.length());
				log.error(logMsg);
				this.setTxResult(txData, "2", "", logMsg,  "error", logMsg);
				return txData;
			} else {
//				int[] itemLen = { 4, 2, 50, 30, 18, msg.length() - 105, 1 };//
				int[] itemLen = { 4, 2 };//
				String[] decodeMsg = this.decodeMsg(txData, msg, "GBK", itemLen);
				String status = "error";
				String txResult = "2";
				String logMsg = "系统错误";
//				if (decodeMsg.length == 7) {
				if (decodeMsg.length == 2) {
					String MESID = decodeMsg[0];// 请求类型
					String CheckResult = decodeMsg[1];// 核对结果
//					String IssueOffice = decodeMsg[2];// 签发机关
//					String Name = decodeMsg[3];// 姓名
//					String identId = decodeMsg[4];// 身份证号码
//					String Photo = decodeMsg[5];// 被核对人照片（经过BASE64转码后的数据）
//					String Separator = decodeMsg[6];// 记录分割符
					if (CheckResult != null) {
						if (CheckResult.equals("00")) {
							logMsg = "公民身份号码与姓名一致，且存在照片";
							status = "success";
							txResult = "0";
						} else if (CheckResult.equals("01")) {
							logMsg = "公民身份号码与姓名一致，但不存在照片";
							status = "success";
							txResult = "0";
						} else if (CheckResult.equals("02")) {
							logMsg = "公民身份号码存在，但与姓名不匹配";
							status = "error";
							txResult = "2";
						}else if(CheckResult.equals("03")){
							logMsg = "公民身份号码不存在";
							status = "error";
							txResult = "2";
						}else if(CheckResult.equals("04")){
							logMsg = "其他错误";
							status = "error";
							txResult = "2";
						} else {
							logMsg = "报文解析失败";
							status = "error";
							txResult = "2";
						}
					} else {
						logMsg = "报文解析失败";
						status = "error";
						txResult = "2";
					}
					log.error(logMsg);
					this.setTxResult(txData, txResult, "", logMsg,  status, logMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误: " + e.getMessage();
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		return txData;
	}
}
