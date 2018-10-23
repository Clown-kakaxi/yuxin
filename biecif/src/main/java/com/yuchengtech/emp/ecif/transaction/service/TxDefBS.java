package com.yuchengtech.emp.ecif.transaction.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibm.db2.jcc.uw.Clob;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsg;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgCheckinfo;

@Service
@Transactional(readOnly = true)
public class TxDefBS extends BaseBS<TxDef> {
	
	public static final String TX_MSG_TYPE_REQ = "1";//请求报文
	public static final String TX_MSG_TYPE_RES = "2";//响应报文
	@SuppressWarnings("unchecked")
	public SearchResult<TxDef> getTxDefList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxDef from TxDef TxDef where 1=1 ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxDef." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxDef> TxDefList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxDefList;
	}
	
	public List<TxDef> getTxDefList() {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxDef from TxDef TxDef order by txId asc");

		List<TxDef> TxDefList = this.baseDAO.findWithIndexParam(jql.toString(), null);
		return TxDefList;
	}

	
	public TxDef getTxDef(Long txId){
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxDef from TxDef TxDef where txId=" + txId);
		List<TxDef> objList =this.baseDAO.findWithNameParm(jql.toString(), null);
        return (TxDef)objList.get(0);
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select srcSysCd, srcSysNm from SrcSystem  t");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
	
	
	public Boolean downloadTxMsgCheckInfo(String txId, String txName,
			HttpServletResponse response) {
		
		//List<TxMsgCheckinfo> list = new ArrayList();
		String[] aTxTd   = txId.split(",");
		String[] aTxName = txName.split(",");
		
		Boolean result = true;
		try {
			ZipOutputStream zipOut = new ZipOutputStream(
					response.getOutputStream());
			
			for(int i=0;i<aTxTd.length;i++){
				List<TxMsgCheckinfo> list = this.getTxMsgCheckInfoList(aTxTd[i]);
				String pTxName = aTxName[i];
				
				for (TxMsgCheckinfo txInfo : list) {
					List<TxMsg> txMsglist = this.getTxMsg(txInfo.getMsgId());
					String checkInfo = txInfo.getCheckinfo();
					String descInfo = txInfo.getDescinfo();
					for (TxMsg txMsg : txMsglist) {
						if (TX_MSG_TYPE_REQ.equals(txMsg.getMsgTp())) {
							zipOut.putNextEntry(new ZipEntry(pTxName + "_req"
									+ ".xml"));
							if (!StringUtils.isEmpty(descInfo)) {
								zipOut.write(descInfo.getBytes());
							}
	
							zipOut.putNextEntry(new ZipEntry(pTxName + "_req"
									+ ".xsd"));
							if (!StringUtils.isEmpty(checkInfo)) {
								zipOut.write(checkInfo.getBytes());
							}
						}
						if (TX_MSG_TYPE_RES.equals(txMsg.getMsgTp())) {
	
							zipOut.putNextEntry(new ZipEntry(pTxName + "_res"
									+ ".xml"));
							if (!StringUtils.isEmpty(descInfo)) {
								zipOut.write(descInfo.getBytes());
							}
	
							zipOut.putNextEntry(new ZipEntry(pTxName + "_res"
									+ ".xsd"));
							if (!StringUtils.isEmpty(checkInfo)) {
								zipOut.write(checkInfo.getBytes());
							}
						}
					}
				}		
			}
			zipOut.close();

		} catch (IOException e) {

			e.printStackTrace();
			result = false;
			//log.error("下载报文交易文件失败!" + e.getMessage());
		}

		return result;
	}
	
	
	public List<TxMsgCheckinfo> getTxMsgCheckInfo(String txId) {
		
		String sql = "select check_id,msg_id,checkinfo,descinfo from Tx_Msg_Checkinfo t where t.msg_Id in (select tx.msg_Id from Tx_Msg tx where  tx.tx_Id = "
				+ txId + ")";

		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql,null);
		
		List<TxMsgCheckinfo> tlist = new ArrayList<TxMsgCheckinfo>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj  = list.get(i);
			
			TxMsgCheckinfo t = new TxMsgCheckinfo();
			t.setCheckId(Long.valueOf(((BigInteger)obj[0]).toString()));
			t.setMsgId(Long.valueOf( ((BigInteger)obj[1]).toString()));
			t.setCheckinfo(((Clob)obj[2]).toString());
			t.setCheckinfo(((Clob)obj[3]).toString());
			
			tlist.add(t);
		}

		return tlist;
	}
	
	public List<TxMsgCheckinfo> getTxMsgCheckInfoList(String txId) {
		
		String jql = "select TxMsgCheckinfo from TxMsgCheckinfo TxMsgCheckinfo  where TxMsgCheckinfo.msgId in (select msgId from TxMsg tx where  tx.txId = "
				+ txId + ")";

		List<TxMsgCheckinfo> tlist = this.baseDAO.createQueryWithIndexParam(jql, null).getResultList();
		
		return tlist;
	}
	
	
	private List<TxMsg> getTxMsg(Long msgId) {
		
		String sql = "select msg_id,tx_id,msg_name,msg_desc,msg_tp from Tx_Msg t where t.msg_Id  = "	+ msgId ;

		List<Object[]> list = this.baseDAO.findByNativeSQLWithNameParam(sql,null);
		
		List<TxMsg> tlist = new ArrayList<TxMsg>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj  = list.get(i);
			
			TxMsg t = new TxMsg();
			//jlnx
			//t.setMsgId(Long.valueOf(((BigDecimal)obj[0]).toString()));
			t.setMsgId(Long.valueOf((obj[0]).toString()));
			//t.setTxId(Long.valueOf(((BigDecimal)obj[1]).toString()));
			t.setTxId(Long.valueOf((obj[1]).toString()));
			t.setMsgName((String)obj[2]);
			t.setMsgTp(String.valueOf((Character)obj[4]));
			
			tlist.add(t);
		}

		return tlist;
	}	
	
}
