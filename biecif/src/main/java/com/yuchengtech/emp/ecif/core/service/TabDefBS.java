package com.yuchengtech.emp.ecif.core.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.util.PropertiesUtils;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.core.entity.TabDef;
import com.yuchengtech.emp.ecif.core.entity.TxMetaDataVO;

@Service
@Transactional(readOnly = true)
public class TabDefBS extends BaseBS<TabDef> {
	
	protected static Logger log = LoggerFactory.getLogger(TabDefBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	
	@SuppressWarnings("unchecked")
	public SearchResult<TabDef> getTabDefList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TabDef from TabDef TabDef where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TabDef." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TabDef> TabDefList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
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
		
	public List<Map<String, String>> getSchemaTableList(String schema,String tabName) {
		
		String dbtype = getDbtype();
		
		PropertiesUtils tool = new PropertiesUtils("database.properties");
		String username =  tool.getProperty("jdbc.username").toUpperCase();
		String sql ="";
	    
		String sqllike ="";
		String[] tabs = null;
		
		if(dbtype.equals("oracle")){
			sql = " select table_name,case when comments is null then table_name else comments  end comments from user_tab_comments " ;
			
			if(tabName!=null)	{
				tabs = tabName.split(",");
				for(int i=0;i<tabs.length;i++){
					if(i==0)
					  sqllike +=" table_name like '"+ tabs[i] +"%' ";
					else
					  sqllike +=" or table_name like '"+ tabs[i] +"%' ";
				}				
				
				sql +=" where " + sqllike; 
			}
			sql +=" order by table_name";
			
		}else{
			sql = "select tabname, remarks from syscat.tables where tabschema = '"+ schema +"' " ;
			if(tabName!=null)	{
				tabs = tabName.split(",");
				for(int i=0;i<tabs.length;i++){
					if(i==0)
					  sqllike +=" tabname like '"+ tabs[i] +"%' ";
					else
					  sqllike +=" or tabname like '"+ tabs[i] +"%' ";
				}				
				
				sql +=" and " + sqllike; 
			}
			
			sql +=" order by tabname";				
		}

		List<Object[]> objList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("tabName", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("tabDesc", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
	

	/**
	 * 批量导入表
	 * @param value  属性值，支持多个属性值，属性值之间用，分割
	 */
	@Transactional(readOnly = false)
	public void batchimportTabs(String value,String tabSchema) {
		
		if (StringUtils.isBlank(value)) {
			return;
		}
		String[] values = value.split(",");
		
		PropertiesUtils tool = new PropertiesUtils("database.properties");
		String username =  tool.getProperty("jdbc.username").toUpperCase();

		String dbtype = getDbtype();
		String sql = "";
		String sqlinsert = "";
		String sqlupdate = "";

		if (values != null) {
			
			//批量导入表
			for (String val : values) {
												

				if(dbtype.equals("oracle")){
					
					BigDecimal tabid ; 

					sql = "select table_name,case when comments is null then table_name else comments  end comments from user_tab_comments where table_name = '"+ val + "'";
					List<Object[]> objList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
					Object[] obj = objList.get(0);
					String tabname = (String)obj[0]; 
					String tabcomment = (String)obj[1]; 
					String objname = getObjName(tabname);
					
					sql = "select tab_id from tx_tab_def where tab_name = '"+ val + "'";
					List<Object> list = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();

					
					if(list.size()>0){				//如果已经存在此数据元定义
						tabid = new BigDecimal(list.get(0).toString());						
						sqlupdate = " update      tx_tab_def set TAB_NAME = '"+tabname +"',TAB_DESC='"+tabcomment+"',OBJ_NAME='"+ objname +"' where tab_id=" + tabid;
						this.baseDAO.createNativeQueryWithIndexParam(sqlupdate, null).executeUpdate();
					}else{								//如果不存在此数据元定义
						sqlinsert = " insert into tx_tab_def(tab_id,tab_name,tab_desc,obj_name) values(seq_tx_tab_def.nextval, '"+tabname+"','"+tabcomment+"','"+objname+"')";
						this.baseDAO.createNativeQueryWithIndexParam(sqlinsert, null).executeUpdate();
					}
					
					//导入此表的字段
					sql = "select tab_id from tx_tab_def where tab_name = '"+ val + "'";
					List<Object> tList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
					tabid = new BigDecimal(tList.get(0).toString());	
					
					syncTabColumns(tabid.toString());			
					
				}else{
					
					BigInteger tabid ;
					
					sql = "select tabname, remarks from syscat.tables where tabschema = '"+ tabSchema +"' and tabname = '"+ val + "'";
					List<Object[]> objList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
					Object[] obj = objList.get(0);
					String tabname = (String)obj[0]; 
					String tabcomment = (String)obj[1]; 
					String objname = getObjName(tabname);
					
					sql = "select tab_id from tx_tab_def where tab_name = '"+ val + "'";
					List<Object> list = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
					
					if(list.size()>0){				//如果已经存在此数据元定义
						tabid = (BigInteger)list.get(0);
						sqlupdate = " update      tx_tab_def set TAB_NAME = '"+tabname +"',TAB_DESC='"+tabcomment+"',OBJ_NAME='"+ objname +"',	TAB_SCHEMA='"+ tabSchema +"' where tab_id=" + tabid;
						this.baseDAO.createNativeQueryWithIndexParam(sqlupdate, null).executeUpdate();
					}else{								//如果不存在此数据元定义
						sqlinsert = " insert into tx_tab_def(tab_id,tab_name,tab_desc,obj_name,	tab_schema) values(NEXTVAL FOR  SEQ_TX_TAB_DEF, '"+tabname+"','"+tabcomment+"','"+objname+"','"+ tabSchema +"')";
						this.baseDAO.createNativeQueryWithIndexParam(sqlinsert, null).executeUpdate();
					}
					
					//导入此表的字段
					sql = "select tab_id from tx_tab_def where tab_name = '"+ val + "'";
					List<Object> tList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
					tabid= (BigInteger)tList.get(0);
					
					syncTabColumns(tabid.toString());

				}
				
			}
		}
	}
	
	
	/**
	 * 更新元数据列对象
	 * @param value  属性值，支持多个属性值，属性值之间用，分割
	 */
	@Transactional(readOnly = false)
	public void syncTabColumns(String value) {
		if (StringUtils.isBlank(value)) {
			return;
		}
		String[] values = value.split(",");
		
		PropertiesUtils tool = new PropertiesUtils("database.properties");
		String username =  tool.getProperty("jdbc.username").toUpperCase();

		String dbtype = getDbtype();
		String sqldelete = ""  ;
		String sqlinsert = "";

		List<Object> valueList = Lists.newArrayList();
		if (values != null) {
			for (String val : values) {
				
				valueList.add(val);
				
				sqldelete = "delete from TX_col_def where TAB_ID=" + val;
				
				if(dbtype.equals("oracle")){    // and b.OWNER = '"+ username+"'
					sqlinsert = "insert into TX_col_def (TAB_ID,COL_ID,COL_NAME,COL_CH_NAME,COL_DESC,COL_SEQ,DATA_TYPE,DATA_LEN,DATA_PREC,NULLS,DATA_FMT,IS_CODE,DECODE,STATE) select a.tab_id, b.column_id, b.column_name, cast ( c.comments as varchar(50)), '', b.column_id, b.data_type, b.data_length, b.data_scale, b.nullable, '', '0', '0', '1' from TX_tab_def a,user_tab_columns b,user_col_comments c where a.tab_name = b.table_name and b.table_name = c.table_name and b.COLUMN_NAME=c.column_name  and a.tab_id="+ val +"  order by a.tab_id, b.column_id";
				}else{
					TabDef model = this.getEntityById(new Long(val));
					String schema = model.getTabSchema();
					sqlinsert = "insert into TX_col_def (TAB_ID,COL_ID,COL_NAME,COL_CH_NAME,COL_DESC,COL_SEQ,DATA_TYPE,DATA_LEN,DATA_PREC,NULLS,DATA_FMT,IS_CODE,DECODE,STATE) select a.tab_id, b.colno + 1, b.colname, cast ( b.remarks as varchar(50)), '', b.colno + 1, b.typename, length, scale, nulls, '', '0', '0', '1' from TX_tab_def a,syscat.columns b where a.tab_name = b.tabname  and a.tab_id="+ val +" and b.tabschema = '"+ schema +"' order by a.tab_id, b.colno";				
				}
			}
			this.baseDAO.createNativeQueryWithIndexParam(sqldelete, null).executeUpdate();
			this.baseDAO.createNativeQueryWithIndexParam(sqlinsert, null).executeUpdate();		
		}
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
