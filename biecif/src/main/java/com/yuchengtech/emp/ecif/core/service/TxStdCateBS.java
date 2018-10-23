package com.yuchengtech.emp.ecif.core.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.util.PropertiesUtils;
import com.yuchengtech.emp.ecif.core.entity.TxStdCate;
import com.yuchengtech.emp.ecif.transaction.service.TxDefBS;

@Service
@Transactional(readOnly = true)
public class TxStdCateBS extends BaseBS<TxStdCate> {
	
	@Autowired
	private TxStdCodeBS txStdCodeBS;
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxStdCate> getTabDefList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxStdCate from TxStdCate TxStdCate where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxStdCate." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxStdCate> TabDefList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TabDefList;
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
		
	@SuppressWarnings("unchecked")
	public void deleteBatch(String stdCate) {

		//删除节点
		String jql1 = "delete from TxStdCate  	where stdCate = ?0";
		String jql2 = "delete from TxStdCode  	where id.stdCate = ?0";
	
		this.baseDAO.batchExecuteWithIndexParam(jql1, stdCate);
		this.baseDAO.batchExecuteWithIndexParam(jql2, stdCate);

	}
	
	
	/**
	 * 获取当前的数据库类型
	 * @return
	 */
	public String getDbtype(){
		
		String dbtype ="";

		PropertiesUtils tool = new PropertiesUtils("database.properties");
		String driverClassName = tool.getProperty("jdbc.driverClassName");
		
		if(driverClassName!=null&&driverClassName.equals("oracle.jdbc.driver.OracleDriver")){
			dbtype = "oracle";
		}else{
			dbtype = "db2";			
		}
		
		return dbtype;
		
	}
	
	/**
	 * 根据表名获取对象名称
	 * @param tabName
	 * @return
	 */
	public String getObjName(String tabName){
		
		String objName = "";
		if(tabName!=null){
			String[] names = tabName.split("_");
			objName = names[0].substring(0,1).toUpperCase() + names[0].substring(1).toLowerCase() ;
		    for(int i=1;i<names.length;i++){
		    	objName += names[i].substring(0,1).toUpperCase() + names[i].substring(1).toLowerCase();
		    }
		}
		return objName;
	}
}
