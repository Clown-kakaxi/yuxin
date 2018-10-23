package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;

/**
 * 开通定制卡的日志工具类
 * @author Administrator
 *
 */
public class CustomNameCardTransaction extends BaseTransaction {

	private Logger log = LoggerFactory.getLogger(CustomNameCardTransaction.class);
	
	public CustomNameCardTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("CRM.CARDSYS.PORT");
		this.setTransName("卡系统开通定制姓名卡");
		this.setHost(FileTypeConstance.getBipProperty("CRM.CARDSYS.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}
	
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxDt(new Date());
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("OpenSpecialCardAccount");//开通定制卡账户
		txData.setTxName("OpenSpecialCardAccount");
		txData.setTxCnName("开通定制卡");
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
		String txStr = txData.getResMsg();
		try {
			if(StringUtils.isEmpty(txStr)){
				String logMsg = "开通定制卡响应报文为空";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", "响应报文为空", "error", logMsg);
				return txData;
			}
			if(txStr.length() > 114){
				String logMsg = "响应结果异常，请联系EAI或卡系统相关人员";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
				return txData;
			}
//			String field1 = txStr.substring(4, 20);
			String field2 = txStr.substring(20, 25);
			String field3 = txStr.substring(25, 61);
			String field4 = txStr.substring(61, txStr.length());
			if(field2.trim().equals("SUCC")){
				String logMsg = "开卡成功";
				String cardNo = field4.trim();
				txData.setAttribute("cardNo", cardNo);
				log.error(logMsg);
				this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
			}else{
				String logMsg = field3.trim();
				log.error(logMsg);
				this.setTxResult(txData, "2", "", "开卡失败", "error", logMsg);
				return txData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，请联系管理员";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", "系统错误： " + e.getMessage(), "error", logMsg);
			return txData;
		}
		return txData;
	}
}
