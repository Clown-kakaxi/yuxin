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
 * ECIF开户交易日志
 * @author Administrator
 *
 */
public class EcifOpenAccountTransaction extends BaseTransaction{
	
	private Logger log = LoggerFactory.getLogger(EcifOpenAccountTransaction.class);
	
	public EcifOpenAccountTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("ECIF.PORT");
		this.setTransName("ECIF开户");
		this.setHost(FileTypeConstance.getBipProperty("ECIF.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}
	
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("EcifOpenAccount");
		txData.setTxName("EcifOpenAccount");
		txData.setTxCnName("ECIF开户");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		//其他参数
		return txData;
	}

	@Override
	/**
	 * ECIF开户响应报文解析
	 * @param msg
	 * @return
	 */
	public TxData read(TxData txData) {
		//如果已经有处理结果了就不再处理
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String resp = txData.getResMsg();//响应报文
		if(StringUtils.isEmpty(resp)){
			String logMsg = "响应报文为空";
			log.error(logMsg);
			txData = this.setTxResult(txData, "2", "", logMsg, "errorr", logMsg);
			return txData;
		}else{
			if(resp.length() > 8){
				resp = resp.substring(8);
				try {
					Document doc = DocumentHelper.parseText(resp);
					Element root = doc.getRootElement();
					String custNo = "";
					String custId = "";
					String belongManagerId = "";
					String belongBranchId = "";
					Element eleTail = root.element("ResponseTail");
					String TxStatDesc = eleTail.elementTextTrim("TxStatDesc");//交易结果描述
					String TxStatCode = eleTail.elementTextTrim("TxStatCode");//交易状态
					if ("000000".equals(TxStatCode)) {
						// 返回技术主键：
						Element eleResBody = root.element("ResponseBody");
						custNo = eleResBody.elementTextTrim("custNo");
						custId = eleResBody.elementTextTrim("custId");
						belongManagerId = eleResBody.elementTextTrim("belongManagerId");
						belongBranchId = eleResBody.elementTextTrim("belongBranchId");
						//添加返回信息
						txData.setAttribute("resultState", "success");
						txData.setAttribute("custNo", custNo);//ECIF客户号
						txData.setAttribute("custId", custId);//ECIF客户号
						txData.setAttribute("belongManagerId", belongManagerId);
						txData.setAttribute("belongBranchId", belongBranchId);
						//identId/addrId/contmethId
						List<Element> eleIdentInfo = eleResBody.elements("identifyInfo");
						Map<String, Object> identInfo = new HashMap<String, Object>();
						if(eleIdentInfo != null && eleIdentInfo.size() >= 1){
							for (Element e : eleIdentInfo) {
								identInfo.put(e.elementTextTrim("identType"), e.elementTextTrim("identId"));
							}
						}
						txData.setAttribute("identifyInfo", identInfo);//添加返回信息
						List<Element> eleAddress = eleResBody.elements("address");
						Map<String, Object> addrInfo = new HashMap<String, Object>();
						if(eleAddress != null && eleAddress.size() >= 1){
							for (Element e : eleAddress) {
								addrInfo.put(e.elementTextTrim("addrType"), e.elementTextTrim("addrId"));
							}
						}
						txData.setAttribute("addressInfo", addrInfo);//添加返回信息
						List<Element> eleContmeth = eleResBody.elements("contMeth");
						Map<String, Object> contmethInfo = new HashMap<String, Object>();
						if(eleContmeth != null && eleContmeth.size() >= 1){
							for (Element e : eleContmeth) {
								contmethInfo.put(e.elementTextTrim("contmenthType"), e.elementTextTrim("contmethId"));
							}
						}
						txData.setAttribute("contmethInfo", contmethInfo);//添加返回信息
						
						String logMsg = "开户成功";
						log.error(logMsg);
						txData = this.setTxResult(txData, "1", "000000", logMsg, "success", logMsg);
						return txData;
					} else {
						txData.setAttribute("resultState", "error");
						txData.setAttribute("resultContent", TxStatDesc);
						log.info("调用ECIF开户接口异常:" + TxStatDesc);
						txData = this.setTxResult(txData, "1", "000000", "开户失败", "success", TxStatDesc);
					}
				} catch (DocumentException e) {
					e.printStackTrace();
					String logMsg = "响应报文转换失败，请联系管理员";
					log.error(logMsg);
					txData = this.setTxResult(txData, "2", "", logMsg, "errorr", logMsg);
					return txData;
				}
			}else{
				String logMsg = "响应信息异常，请联系ECIF相关人员";
				log.error(logMsg);
				txData = this.setTxResult(txData, "2", "", logMsg, "errorr", logMsg);
				return txData;
			}
		}
		
		return txData;
	}
}
