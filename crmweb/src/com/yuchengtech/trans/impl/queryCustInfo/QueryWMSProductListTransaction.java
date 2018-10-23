package com.yuchengtech.trans.impl.queryCustInfo;

import java.util.ArrayList;
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
 * 查询财富系统理财产品交易日志
 * 
 * @author Administrator
 *
 */
public class QueryWMSProductListTransaction extends BaseTransaction {

	private Logger log = LoggerFactory.getLogger(QueryWMSProductListTransaction.class);

	public QueryWMSProductListTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("WMS2.PORT");
		this.setTransName("查询财富系统理财产品");
		this.setHost(FileTypeConstance.getBipProperty("WMS2.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}

	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("QueryWMSProductList");
		txData.setTxName("QueryWMSProductList");
		txData.setTxCnName("查询财富系统理财产品");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		// 其他参数
		return txData;
	}

	@Override
	/**
	 * QueryWMSProductList响应报文解析
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
			Element eleHeader = root.element("ResponseHeader");
			if (eleHeader != null) {
				String resMark = eleHeader.elementTextTrim("Result");
				if (!StringUtils.isEmpty(resMark)) {
					if (resMark.equals("OK")) {// 交易成功
						// ResponseBody
						Element eleResBody = root.element("ResponseBody");
						List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
						if (eleResBody != null) {
							List<Element> ACCT_ARRAY = eleResBody.elements("ACCT_ARRAY");
							if (ACCT_ARRAY != null && ACCT_ARRAY.size() >= 1) {
								for (int i = 0; i < ACCT_ARRAY.size(); i++) {
									Element eleAcct = ACCT_ARRAY.get(i);
									String CCY = eleAcct.elementTextTrim("CCY");
									String ACCT_NO = eleAcct.elementTextTrim("ACCT_NO");
									// TERM_ARRAY
									List<Element> TERM_ARRAY = eleAcct.elements("TERM_ARRAY");
									if (TERM_ARRAY != null && TERM_ARRAY.size() >= 1) {
										for (int j = 0; j < TERM_ARRAY.size(); j++) {
											Element eleTerm = TERM_ARRAY.get(j);
											// ORDER_ARRAY
											List<Element> ORDER_ARRAY = eleTerm.elements("ORDER_ARRAY");
											if (ORDER_ARRAY != null
													&& ORDER_ARRAY.size() >= 1) {
												for (int k = 0; k < ORDER_ARRAY.size(); k++) {
													Element eleOrder = ORDER_ARRAY.get(k);
													String PRO_TENOR = eleOrder.elementTextTrim("PRO_TENOR");
													String VALUE_DATE = eleOrder.elementTextTrim("VALUE_DATE");
													String END_DATE = eleOrder.elementTextTrim("END_DATE");
													//String RATE = eleOrder.elementTextTrim("RATE");
													String ANTI_RAT = eleOrder.elementTextTrim("ANTI_RAT");
													String ORDER_BALANCE = eleOrder.elementTextTrim("ORDER_BALANCE");
													Map<String, Object> singleMap = new HashMap<String, Object>();
													singleMap.put("CCY", CCY);
													singleMap.put("ACCT_NO", ACCT_NO);
													singleMap.put("PRO_TENOR", PRO_TENOR);
													singleMap.put("VALUE_DATE", VALUE_DATE);
													singleMap.put("END_DATE", END_DATE);
													singleMap.put("ANTI_RAT", ANTI_RAT);
													singleMap.put("ORDER_BALANCE", ORDER_BALANCE);
													resList.add(singleMap);
												}
											}
										}
									}
								}
							}
						}
						String logMsg = "查询财富系统理财产品信息成功";//
						log.info(logMsg);
						txData.setAttribute("resList", resList);
						this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
						return txData;
					} else {
						String ErrMsg = eleHeader.elementTextTrim("ErrMsg");
						String logMsg = "查询财富系统理财产品信息失败：" + ErrMsg;
						log.error(logMsg);
						this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
						return txData;
					}
				}
			}else{
				String logMsg = "查询财富系统理财产品信息失败：没有找到可以解析的字段";
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
