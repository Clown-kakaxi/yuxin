package com.yuchengtech.trans.impl.custTransfer;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;
/**
 * 主管直接移交同步信贷
 * @author xuyl
 *
 */
public class CustTransferSyncLNTransaction extends BaseTransaction{
	private Logger log = LoggerFactory.getLogger(CustTransferSyncLNTransaction.class);
	
	public CustTransferSyncLNTransaction(TxData txData){
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("LOAN.PORT");
		this.setTransName("主管直接移交同步信贷");
		this.setHost(FileTypeConstance.getBipProperty("LOAN.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}
	
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("custTransferSyncLN");
		txData.setTxName("custTransferSyncLN");
		txData.setTxCnName("主管直接移交同步信贷");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		// 其他参数
		return txData;
	}
	
	@Override
	/**
	 * updateBelong响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	public TxData read(TxData txData) {
		if (!StringUtils.isEmpty(txData.getTxResult())) {
			return txData;
		}
		String resp = txData.getResMsg();
		if (StringUtils.isEmpty(resp)) {
			String logMsg = "响应报文为空";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		resp = resp.substring(8);
		Document doc;
		try {
			doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();
			Element responseTail = root.element("ResponseTail");
			
			if (responseTail != null) {
				String txStatCode = responseTail.elementTextTrim("TxStatCode");
				if (!StringUtils.isEmpty(txStatCode)) {
					if ("000000".equals(txStatCode)) {// 交易成功
						String logMsg = "主管直接移交同步信贷成功";//
						log.info(logMsg);
						this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
						return txData;
					} else {
						String ErrMsg = responseTail.elementTextTrim("TxStatDesc");
						String logMsg = "主管直接移交同步信贷失败：" + ErrMsg;
						log.error(logMsg);
						this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
						return txData;
					}
				}
			}else{
				String logMsg = "主管直接移交同步信贷失败：没有找到可以解析的字段";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
				return txData;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			String logMsg = "响应报文转换XML失败，请联系管理员";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}

		return txData;
	}
}
