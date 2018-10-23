package com.yuchengtech.emp.ecif.transaction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.rulemanage.entity.TxBizRuleConf;
import com.yuchengtech.emp.ecif.rulemanage.service.TxBizRuleConfBS;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttr;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrCt;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeAttrVO;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeBFO;

@Service
@Transactional(readOnly = false)
public class TxMsgNodeAttrBS extends BaseBS<TxMsgNodeAttr> {

	@Autowired
	private TxMsgNodeAttrCtBS txMsgNodeAttrCtBS;
	
	@Autowired
	private TxBizRuleConfBS txBizRuleConfBS;
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNodeAttr> getTxMsgNodeAttrList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,Integer tabId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNodeAttr from TxMsgNodeAttr TxMsgNodeAttr where tabId=" + tabId);
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxMsgNodeAttr." + orderBy + " " + orderType);
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNodeAttr> TxMsgNodeAttr = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNodeAttr;
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNodeAttr> getTxMsgNodeAttrListByTab(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String  nodeId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append(" select TxMsgNodeAttr from TxMsgNodeAttr TxMsgNodeAttr where nodeId="+nodeId);
		jql.append(" order by TxMsgNodeAttr.tabId asc" );
		
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNodeAttr> TxMsgNodeAttr = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNodeAttr;
	}	
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select txCode, txName from TxDef  t");

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
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getTxMsgNodeAttrVOList(String nodeId) {
		
		Map paramMap = new HashMap();
		paramMap.put("nodeId", nodeId);

		StringBuffer sql = new StringBuffer("");
		
		sql.append(" SELECT  r.ATTR_ID,r.NODE_ID,r.ATTR_CODE,r.ATTR_NAME,r.TAB_ID,r.COL_ID,r.NULLS,r.DATA_TYPE,r.DATA_LEN,r.DATA_FMT,r.CHECK_RULE,r.CATE_ID,r.DEFAULT_VAL,r.FK_ATTR_ID,r.ATTR_SEQ,r.STATE,r.CREATE_TM,r.CREATE_USER,r.UPDATE_TM,r.UPDATE_USER,t1.TAB_DESC,t3.COL_CH_NAME,t4.ATTR_NAME as  ATTR_NAME1");  
		sql.append(" FROM  TX_MSG_NODE_ATTR r ");
		sql.append(" left join TX_TAB_DEF t1 on r.TAB_ID = t1.TAB_ID");
		sql.append(" left join TX_COL_DEF t3 on r.TAB_ID = t3.TAB_ID  AND r.COL_ID = t3.COL_ID ");
		sql.append(" left join TX_MSG_NODE_ATTR t4 on r.FK_ATTR_ID = t4.ATTR_ID ");
		sql.append(" where r.node_id= :nodeId ");
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), paramMap);
		
		List<TxMsgNodeAttrVO> results =new ArrayList<TxMsgNodeAttrVO>();
		
		for(int i=0;i<list.size();i++){
			TxMsgNodeAttrVO bat=new TxMsgNodeAttrVO();
			
			bat.setAttrId(list.get(i)[0]!=null?new Long(list.get(i)[0].toString()):null);
			bat.setNodeId(list.get(i)[1]!=null?new Long(list.get(i)[1].toString()):null);
			bat.setAttrCode(list.get(i)[2]!=null?list.get(i)[2].toString():"");
			bat.setAttrName(list.get(i)[3]!=null?list.get(i)[3].toString():"");
			if(list.get(i)[4]!=null){
				bat.setTabId(list.get(i)[4]!=null?new Integer(list.get(i)[4].toString()):null);
			}
			if(list.get(i)[5]!=null){
				bat.setColId(list.get(i)[5]!=null?new Integer(list.get(i)[5].toString()):null);
			} 
			bat.setNulls(list.get(i)[6]!=null?list.get(i)[6].toString():"");
			bat.setDataType(list.get(i)[7]!=null?list.get(i)[7].toString():"");
			bat.setDataLen(list.get(i)[8]!=null?new Integer(list.get(i)[8].toString()):null);
			bat.setDataFmt(list.get(i)[9]!=null?list.get(i)[9].toString():"");
			bat.setCheckRule(list.get(i)[10]!=null?list.get(i)[10].toString():"");
			if(list.get(i)[11]!=null&&!list.get(i)[11].equals("")){
				bat.setCateId(list.get(i)[11]!=null?list.get(i)[11].toString():null);
			}			
			bat.setDefaultVal(list.get(i)[12]!=null?list.get(i)[12].toString():"");
			bat.setFkAttrId(list.get(i)[13]!=null?new Long(list.get(i)[13].toString()):null);
			bat.setAttrSeq(list.get(i)[14]!=null?new Integer(list.get(i)[14].toString()):null);
			bat.setState(list.get(i)[15]!=null?list.get(i)[15].toString():"");
			bat.setTabDesc(list.get(i)[20]!=null?list.get(i)[20].toString():"");
			bat.setColChName(list.get(i)[21]!=null?list.get(i)[21].toString():"");
			bat.setFkAttrName(list.get(i)[22]!=null?list.get(i)[22].toString():"");

			List<TxMsgNodeAttrCt> ctlist =  txMsgNodeAttrCtBS.getCtList(bat.getAttrId());
			
			if(ctlist!=null && ctlist.size()>0){
				
				TxMsgNodeAttrCt ct = ctlist.get(0);
				String ctRule = ct.getCtRule();
				String ctDesc = ct.getCtDesc();
				
				for(int j=1;j<ctlist.size();j++){
					ct = ctlist.get(j);
					ctRule = ctRule +"," + ct.getCtRule();
					ctDesc = ctDesc +"," + ct.getCtDesc();
				}
				
				bat.setCtRule(ctRule);
				bat.setCtDesc(ctDesc);
			}
			
			if(StringUtils.isNotEmpty(bat.getCheckRule())){
			String checkRuleDesc = "" ;
			String [] sarr = bat.getCheckRule().split(",");
			//if(sarr.length>0){
			for (int j = 0; j < sarr.length; j++) {
				//Long ruleId = Long.parseLong(sarr[j]);
				//TxBizRuleConf ruleconf = txBizRuleConfBS.getEntityById(TxBizRuleConf.class, ruleId);
				TxBizRuleConf ruleconf = txBizRuleConfBS.getEntityByProperty(TxBizRuleConf.class, "ruleNo",sarr[j]);
				if(j==0){
					checkRuleDesc = ruleconf.getRuleName();
				}else{
				checkRuleDesc = checkRuleDesc + ","+ruleconf.getRuleName();
				}
			}
			bat.setCheckRuleDesc(checkRuleDesc);
			}
			results.add(bat);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("Rows", results);
		map.put("Total", results.size());		
		
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getTxMsgNodeAttrVOListByTabids(String nodeId, String tabids) {
		
		Map paramMap = new HashMap();
		paramMap.put("nodeId", nodeId);

		StringBuffer sql = new StringBuffer("");
		
//		sql.append(" SELECT  0 as  ATTR_ID,0 as NODE_ID ,r.COL_NAME,r.COL_CH_NAME,r.TAB_ID,r.COL_ID,r.NULLS,r.DATA_TYPE,r.DATA_LEN,r.DATA_FMT,'' as CHECK_RULE,r.CATE_ID,'' as DEFAULT_VAL ,0 as FK_ATTR_ID,0 as ATTR_SEQ,r.STATE,r.CREATE_TM,r.CREATE_USER,r.UPDATE_TM,r.UPDATE_USER,t1.TAB_DESC,r.COL_CH_NAME  FROM  TXP.COL_DEF r  left join TXP.TAB_DEF t1 on r.TAB_ID = t1.TAB_ID  where r.tab_id in("+tabids +") "); 
//		sql.append(" and Concat (r.TAB_ID,r.COL_ID) not in  ( SELECT  Concat (a.TAB_ID,a.COL_ID) FROM  TXP.TX_MSG_NODE_ATTR a  inner join TXP.COL_DEF t4 on a.TAB_ID = t4.TAB_ID  AND a.COL_ID = t4.COL_ID  where node_id= :nodeId )");

		sql.append(" SELECT  0 as  ATTR_ID,0 as NODE_ID ,r.COL_NAME,r.COL_CH_NAME,r.TAB_ID,r.COL_ID,r.NULLS,r.DATA_TYPE,r.DATA_LEN,r.DATA_FMT,'' as CHECK_RULE,r.CATE_ID,'' as DEFAULT_VAL ,0 as FK_ATTR_ID,0 as ATTR_SEQ,r.STATE,r.CREATE_TM,r.CREATE_USER,r.UPDATE_TM,r.UPDATE_USER,t1.TAB_DESC,r.COL_CH_NAME,r.DATA_PREC  FROM  TX_COL_DEF r  left join TX_TAB_DEF t1 on r.TAB_ID = t1.TAB_ID  where r.tab_id in("+tabids +") "); 
		sql.append(" and Concat (r.TAB_ID,r.COL_ID) not in  ( SELECT  Concat (a.TAB_ID,a.COL_ID) FROM  TX_MSG_NODE_ATTR a  inner join TX_COL_DEF t4 on a.TAB_ID = t4.TAB_ID  AND a.COL_ID = t4.COL_ID  where node_id= :nodeId )");
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), paramMap);
		
		List<TxMsgNodeAttrVO> results =new ArrayList<TxMsgNodeAttrVO>();
		
		for(int i=0;i<list.size();i++){
			TxMsgNodeAttrVO bat=new TxMsgNodeAttrVO();
			
			bat.setAttrId(null);
			bat.setNodeId(null);
			bat.setAttrCode(list.get(i)[2]!=null?list.get(i)[2].toString():"");
			bat.setAttrName(list.get(i)[3]!=null?list.get(i)[3].toString():"");
			if(list.get(i)[4]!=null){
				bat.setTabId(list.get(i)[4]!=null?new Integer(list.get(i)[4].toString()):null);
			}
			if(list.get(i)[5]!=null){
				bat.setColId(list.get(i)[5]!=null?new Integer(list.get(i)[5].toString()):null);
			}
			bat.setNulls(list.get(i)[6]!=null?list.get(i)[6].toString():"");
			Integer dataprec = list.get(i)[22]!=null?new Integer(list.get(i)[22].toString()):null;
			bat.setDataType(convertColDatatype2AttrDatatype(list.get(i)[7]!=null?list.get(i)[7].toString():"",dataprec));
			
			//对于已经设置属性的，应以日期型:'50'时间型:'60'时间戳型:'70'作为判断条件
			bat.setDataLen(list.get(i)[8]!=null?new Integer(list.get(i)[8].toString()):null);
			if(bat.getDataType().equals("50")){					//日期型强制长度设为10
				bat.setDataLen(10);
			}else if(bat.getDataType().equals("60")){				//时间型强制长度设为10
				bat.setDataLen(10);
			}else if(bat.getDataType().startsWith("70")){	//时间戳型强制长度设为19
				bat.setDataLen(19);
			}
			
			bat.setDataFmt(list.get(i)[9]!=null?list.get(i)[9].toString():"");
			bat.setCheckRule(list.get(i)[10]!=null?list.get(i)[10].toString():"");
			if(list.get(i)[11]!=null&&!list.get(i)[11].equals("")){
				bat.setCateId(list.get(i)[11]!=null?list.get(i)[11].toString():null);
			}			

			bat.setDefaultVal(list.get(i)[12]!=null?list.get(i)[12].toString():"");
			bat.setFkAttrId(null);
			bat.setAttrSeq(null);
			bat.setState(list.get(i)[15]!=null?list.get(i)[15].toString():"");
			bat.setTabDesc(list.get(i)[20]!=null?list.get(i)[20].toString():"");
			bat.setColChName(list.get(i)[21]!=null?list.get(i)[21].toString():"");

			List<TxMsgNodeAttrCt> ctlist =  txMsgNodeAttrCtBS.getCtList(bat.getAttrId());
			
			if(ctlist!=null && ctlist.size()>0){
				
				TxMsgNodeAttrCt ct = ctlist.get(0);
				String ctRule = ct.getCtRule();
				String ctDesc = ct.getCtDesc();
				
				for(int j=1;j<ctlist.size();j++){
					ct = ctlist.get(j);
					ctRule = ctRule +"," + ct.getCtRule();
					ctDesc = ctDesc +"," + ct.getCtDesc();
				}
				
				bat.setCtRule(ctRule);
				bat.setCtDesc(ctDesc);
			}
			
			results.add(bat);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("Rows", results);
		map.put("Total", results.size());		
		
		return map;
	}	
	@SuppressWarnings("unchecked")
	public List<TxMsgNodeBFO>  getTxMsgNodeAttrBFOList(String msgId) {
		
		Map paramMap = new HashMap();
		paramMap.put("msgId", msgId);

		StringBuffer sql = new StringBuffer("");
		
		sql.append(" SELECT  ATTR_ID,t4.NODE_ID,ATTR_CODE,ATTR_NAME,r.NULLS,r.DATA_TYPE,r.DATA_LEN,r.CHECK_RULE,t4.node_Code,t4.node_Name,t4.up_Node_Id,t4.node_Group,t4.NODE_LABEL  ");  
		sql.append(" FROM  TX_MSG_NODE t4  ");
		sql.append(" left outer join  TX_MSG_NODE_ATTR r on r.NODE_ID = t4.NODE_ID ");
		sql.append(" where t4.msg_id= :msgId ");
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), paramMap);
		
		List<TxMsgNodeBFO> results =new ArrayList<TxMsgNodeBFO>();
		
		for(int i=0;i<list.size();i++){
			
			TxMsgNodeBFO bat = new TxMsgNodeBFO();
			
			bat.setAttrId(list.get(i)[0]!=null?(list.get(i)[0].toString()):null);
			bat.setNodeId(list.get(i)[1]!=null?(list.get(i)[1].toString()):null);
			bat.setAttrCode(list.get(i)[2]!=null?list.get(i)[2].toString():"");
			bat.setAttrName(list.get(i)[3]!=null?list.get(i)[3].toString():"");
			bat.setNulls(list.get(i)[4]!=null?list.get(i)[4].toString():"");
			bat.setDataType(list.get(i)[5]!=null?list.get(i)[5].toString():"");
			bat.setDataLen(list.get(i)[6]!=null?(list.get(i)[6].toString()):null);
			bat.setCheckRule(list.get(i)[7]!=null?list.get(i)[7].toString():"");
			bat.setNodeCode(list.get(i)[8]!=null?(list.get(i)[8].toString()):null);
			bat.setNodeName(list.get(i)[9]!=null?(list.get(i)[9].toString()):null);
			bat.setUpNodeId(list.get(i)[10]!=null?(list.get(i)[10].toString()):null);
			bat.setNodeGroup(list.get(i)[11]!=null?list.get(i)[11].toString():"");
			bat.setNodeLabel(list.get(i)[12]!=null?list.get(i)[12].toString():"");

			results.add(bat);
		}
	
		
		return results;
	}
	
	/**
	 * 		字符型(固定长度):'10'
			字符型(可变长度):'20'
			整型            :'30'
			浮点型          :'40'
			日期型		:'50'
			时间型		:'60'
			时间戳型	:'70'	
			大文本型         :'80'
	 */
	public String convertColDatatype2AttrDatatype(String datatype,Integer dataprec){
		
		if(datatype==null) return null;
		
		if(datatype.equals("")) return "";
		
		if(datatype.equals("BIGINT")){
			return "30";
		}else if(datatype.equals("INT")){
			return "30";
		}else if(datatype.equals("CHARACTER")){
			return "10";
		}else if(datatype.equals("CHAR")){
			return "10";
		}else if(datatype.equals("CLOB")){
			return "80";
		}else if(datatype.equals("DATE")){
			return "50";
		}else if(datatype.equals("DECIMAL")){
			return "40";
		}else if(datatype.equals("BIGDECIMAL")){
			return "40";
		}else if(datatype.equals("INTEGER")){
			return "30";
		}else if(datatype.startsWith("NUMBER")){
			if(dataprec==null||dataprec.intValue()==0){		//精度为0
				return "30";
			}else{											//精度不为0
				return "40";				
			}
//		}else if(datatype.startsWith("TIME")){
//			return "60";
		}else if(datatype.startsWith("TIMESTAMP")){
			return "70";
		}else if(datatype.equals("VARCHAR")){
			return "20";
		}else if(datatype.equals("VARCHAR2")){
			return "20";
		} 
		
		
		return "";
	}
	

}
