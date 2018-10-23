package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;

/**
 * 电信黑灰名单核查日志工具
 * @author Administrator
 *
 */
public class ChinaTelBlackOrderCheckTranscation extends BaseTransaction{

	private Logger log = LoggerFactory.getLogger(ChinaTelBlackOrderCheckTranscation.class);
	
	public ChinaTelBlackOrderCheckTranscation(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("CRM.BLACKORDER.PORT");
		this.setTransName("电信黑灰名单核查");
		this.setHost(FileTypeConstance.getBipProperty("CRM.BLACKORDER.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("ChinaTelBlackOrderCheck");
		txData.setTxName("ChinaTelBlackOrderCheck");
		txData.setTxCnName("电信黑灰名单核查");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		//其他参数
		return txData;
	}
	
	@Override
	public TxData read(TxData txData) {
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String msg = txData.getResMsg();
		if (msg == null || msg.equals("")) {
			String logMsg = "响应报文为空";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		try {
			if(msg.getBytes().length > 125 && msg.getBytes().length < 450){//系统错误，没有黑名单的查询结果
				String logMsg = "交易失败，黑名单系统异常，请联系管理员";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
				return txData;
			}
			int[] itemLen = { 4, 6, 8, 6, 5, 100, 12, 2, 325 };
			String[] decodeMsg = decodeMsg(txData, msg, "GBK", itemLen);
			String s_respcode = decodeMsg[4];// 响应码
			String s_zt = decodeMsg[7];// 黑名单查询状态
			String s_ms = decodeMsg[8];// 黑名单查询描述
			if (s_respcode != null && s_respcode.equals("00000")) {// 成功响应
				if (s_zt.equals("00")) {
					String logMsg = "校验通过";
					log.info(logMsg);
					this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
				} else if (s_zt.equals("01")) {
					String logMsg = s_ms.trim();
					log.error(logMsg);
					this.setTxResult(txData, "2", "", "黑名单校验未通过", "error", logMsg);
				} else if (s_zt.equals("02")) {
					String logMsg = s_ms.trim();
					log.error(logMsg);
					this.setTxResult(txData, "2", "", "黑名单校验未通过", "error", logMsg);
				}
			} else {
				String logMsg = "响应报文异常:报文内容【" + msg + "】";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", "响应报文异常", "error", logMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//String logMsg = "系统错误: "+e.getMessage();
			String logMsg = "系统错误，请联系管理员！！！ ";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		return txData;
	}
}
