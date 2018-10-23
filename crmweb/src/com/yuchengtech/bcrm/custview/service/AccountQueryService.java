package com.yuchengtech.bcrm.custview.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;
import com.yuchengtech.trans.impl.queryCustInfo.QueryWMSProductListTransaction;

@Service
public class AccountQueryService extends CommonService {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public AccountQueryService() {
		JPABaseDAO<TxLog, Long> baseDAO = new JPABaseDAO<TxLog, Long>(
				TxLog.class);
		super.setBaseDAO(baseDAO);
	}
	/**
	 * 拼接向财富系统请求报文
	 * @param custId
	 * @return
	 */
	public Map<String, Object> queryWMSProductList(String custId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(custId)){
			throw new BizException(1, 0, "0000", "没有获取到该客户的客户号，请联系管理员");
		}
		try {
			// 查询核心客户号
			String coreNo = null;
			String coreJql = "select t from AcrmFCiCustomer t where t.custId=:custId";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("custId", custId);
			List<AcrmFCiCustomer> custInfoList = this.baseDAO.findWithNameParm(coreJql, params);
			if(custInfoList == null || custInfoList.size() != 1){
				return map;
			}else{
				coreNo = custInfoList.get(0).getCoreNo();
			}
			if (StringUtils.isEmpty(coreNo)) {
				throw new BizException(1, 0, "0000", "没有获取到该客户的核心客户号，请联系管理员");
			}
			// 509000002710
			// custId = "509000002710";
			SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String VerNo = df20.format(new Date());
			// 组装请求报文
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
			sb.append("<TransBody>\n");
			sb.append("    <RequestHeader>\n");
			sb.append("        <SysFun>CRM</SysFun>\n");
			sb.append("        <TranFun>EB06</TranFun>\n");
			sb.append("        <TranType>0</TranType>\n");
			sb.append("        <VerNo>" + VerNo + "</VerNo>\n");
			sb.append("        <Filler></Filler>\n");
			sb.append("    </RequestHeader>\n");
			sb.append("    <RequestBody>\n");
			sb.append("        <ECIF_NO>" + coreNo + "</ECIF_NO>\n");
			sb.append("        <TRANS_TYPE_CODE>1</TRANS_TYPE_CODE>\n");
			sb.append("        <PAGE_NO></PAGE_NO>\n");
			sb.append("        <MAX_RENUM>10</MAX_RENUM>\n");
			sb.append("        <INFO_TOTAL_NUM></INFO_TOTAL_NUM>\n");
			sb.append("    </RequestBody>\n");
			sb.append("</TransBody>\n");
			String reqMsg = sb.toString();
			reqMsg = new String(reqMsg.getBytes("GBK"));//
			reqMsg = String.format("%08d", reqMsg.getBytes("GBK").length)
					+ reqMsg;//
			TxData txData = new TxData();
			txData.setReqMsg(reqMsg);
			BaseTransaction trans = new QueryWMSProductListTransaction(txData);
			txData = trans.process();
			TxLog txLog = trans.getTxLog();
			super.save(txLog);
			if (txData.containsKey("resList")) {
				Object oResList = txData.getAttribute("resList");
				if (oResList != null) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) oResList;
					map.put("count", list.size());
					map.put("data", list);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "Warning-000:数据格式转换失败，请联系管理员");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "Warning-168:请求财富系统数据信息失败");
		}
		return map;
	}
}
