package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;


/**
 * 检查核心是否允许开户日志工具处理
 * @author Administrator
 *
 */
public class CheckCBAccountTransaction extends BaseTransaction {

	private Logger log = LoggerFactory.getLogger(CheckCBAccountTransaction.class);

	
	public CheckCBAccountTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("CRM.CBS.PORT");
		this.setTransName("校验核心是否允许开户");
		this.setHost(FileTypeConstance.getBipProperty("CRM.CBS.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}

	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		// 其他参数
		txData.setTxCode("CheckCBAccount");
		txData.setTxName("CheckCBAccount");
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCnName("校验核心是否允许开户");
		txData.setTxMethod("SOCKET");
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		txData.setTxDt(new Date());
		return txData;
	}

	@Override
	public TxData read(TxData txData) {
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String msg = txData.getResMsg();
		if(StringUtils.isEmpty(msg)){
			String logMsg = "响应报文为空";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		String responseStr = readResponse2(msg);
		if (responseStr != null && !"".equals(responseStr)) {
			if (responseStr.startsWith("3")) {
				String logMsg = "";  //前台提示信息
				String coreNo = (String) txData.getAttribute("coreNo");
				if(StringUtils.isEmpty(coreNo)){
					logMsg = "数据有误--该客户在核心已开户，但在ECIF和CRM中都未获取到核心客户号，请联系管理员";
					log.error(logMsg);
					this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
					return txData;
				}else{
					logMsg = "该客户已存在,核心客户号为["+coreNo+"]";
					log.error(logMsg);
					this.setTxResult(txData, "2", "", "该客户在核心已有账户", "error", logMsg);
					return txData;
				}
			} else if(responseStr.startsWith("1") || responseStr.startsWith("2")){
				String logMsg = "允许开户";
				log.error(logMsg);
				this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
				//return txData;
			}else {
				String logMsg = "与核心系统通讯失败！";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", "与核心系统通讯失败", "error", logMsg);
				return txData;
			}
		}else{
			String logMsg = "核心响应报文缺失相关字段！";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", "核心响应报文缺失相关字段！", "error", logMsg);
			return txData;
		}
		return txData;
	}

	/**
	 * 解析核心返回的报文
	 */
	public String readResponse2(String sResponse) {
		String str = "false@失败";
		if (sResponse.length() >= 14) {
			String sFlag = subString(sResponse, 11, 13);
			if ("OK".equals(sFlag)) { // 反馈报文头=反馈成功标示
				str = subString(sResponse, 13, 14);
			} else {
				str = "false@" + subString(sResponse, 13, sResponse.length());
			}

		} else {
			str = "false@反馈报文长度不足";
		}
		// System.out.println("*********************发送核心后的反馈信息*********************"+str);
		return str;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public String subString(String str, int beginIndex, int endIndex) {
		if (endIndex < beginIndex) {
			return "";
		} else if (str.length() >= beginIndex && str.length() >= endIndex) {
			return str.substring(beginIndex, endIndex);
		} else {
			return "";
		}
	}
}
