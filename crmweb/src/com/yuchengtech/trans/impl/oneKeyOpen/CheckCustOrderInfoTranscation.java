package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 查询客户预约信息交易
 * @author Administrator
 *
 */
public class CheckCustOrderInfoTranscation extends BaseTransaction{

	private Logger log = LoggerFactory.getLogger(CheckCustOrderInfoTranscation.class);
	
	public CheckCustOrderInfoTranscation(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("ECIF.PORT");
		this.setTransName("查询客户预约信息");
		this.setHost(FileTypeConstance.getBipProperty("ECIF.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}

	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("CheckCustOrderInfo");
		txData.setTxName("CheckCustOrderInfo");
		txData.setTxCnName("查询客户预约信息");
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
		//解析报文
		msg = msg.substring(8);
		Document doc;
		try {
			doc = DocumentHelper.parseText(msg);
			Element root = doc.getRootElement();
			Element eleTail = root.element("ResponseTail");
//			String TxStatDesc = eleTail.elementTextTrim("TxStatDesc");//
			String TxStatCode = eleTail.elementTextTrim("TxStatCode");//
			if(TxStatCode.equals("000000")){//成功
				Element e_resBody = root.element("ResponseBody");
				List<Element> e_bodyNodes = e_resBody.elements();
				Map<String, Object> nodeMap = new HashMap<String, Object>();
				if(e_bodyNodes != null && e_bodyNodes.size() >= 1){
					for (Element ele : e_bodyNodes) {
						String nodeNm = ele.getName();
						String nodeCont = ele.getTextTrim();
						nodeMap.put(nodeNm, nodeCont);
					}
				}
				txData.setAttribute("custInfo", nodeMap);
				String logMsg = "交易成功";
				log.error(logMsg);
				this.setTxResult(txData, "0", "000000", logMsg, "success", logMsg);
				return txData;
			}else{
				String logMsg = "客户预约信息查询失败！";
				log.error(logMsg);
				this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
				return txData;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			String logMsg = "响应报文格式不正确，无法解析，请查看ECIF系统";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
	}
}
