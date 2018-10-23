package com.yuchengtech.emp.ecif.customer.customization.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.customer.customization.constant.CtztConstant;
import com.yuchengtech.emp.ecif.customer.customization.entity.DefinetableviewLookVO;

@Service("CustomizationQueryBS")
@Transactional(readOnly = true)
public class CustomizationQueryBS extends BaseBS<T> {

	protected static Logger log = LoggerFactory
			.getLogger(CustomizationQueryBS.class);
	
	@Autowired
	private CustomizationColumnQueryBS customizationColumnQueryBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	/**
	 * 根据表类型获取查询客户信息SQL
	 * @param tbType 表类型
	 * @return
	 */
	public Map<String, Object> getQueryCstListSql(String tbType) {
		DefinetableviewLookVO[] tables = customizationColumnQueryBS.getIsResultDefinetableviews(tbType);
		DefinetableviewLookVO[] columns = customizationColumnQueryBS.getisMustColumn(tbType);
		
		if(columns != null && columns.length > 0){
			StringBuffer oneToOneSql = new StringBuffer();
			StringBuffer selectSql = new StringBuffer(" SELECT ");
			StringBuffer fromSql = new StringBuffer(" FROM ");
			StringBuffer fromTablesSql = new StringBuffer();
			StringBuffer whereSql = new StringBuffer(" WHERE 1 =1 ");
			StringBuffer columnsSql = new StringBuffer();    
			StringBuffer beginfromTablesSql = new StringBuffer(" M_CI_CUSTOMER CST ");
			StringBuffer dictTypeStr = new StringBuffer();
			StringBuffer dictColumnStr = new StringBuffer();
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			Map<String, String> dictMap = new HashMap<String, String>();
			int breaks = 0;
			for(int i = 0; i < tables.length; i ++){
				DefinetableviewLookVO table = tables[i];
				if(table.getIsManytoone().equals(CtztConstant.ECIF_CTZT_IS_MANYTOONE_NO)){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
					if(!table.getTbName().equals("M_CI_CUSTOMER")){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
						if(table.getIsMiddleTable().equals(CtztConstant.ECIF_CTZT_IS_MIDDLE_TABLE_YES)){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
							fromTablesSql.append(" LEFT JOIN ");
							fromTablesSql.append(table.getMiddleTableNm());
							fromTablesSql.append(" ON ");
							fromTablesSql.append(table.getMiddleTableNm());
							fromTablesSql.append(".");
							fromTablesSql.append(table.getRelationColumn().split(",")[1]);
							fromTablesSql.append(" = ");
							fromTablesSql.append(CtztConstant.CUSTOMER_ACRONYM);
							fromTablesSql.append(".");
							fromTablesSql.append(table.getRelationColumn().split(",")[1]);
							fromTablesSql.append(" LEFT JOIN ");
							fromTablesSql.append(table.getTbName());
//							fromTablesSql.append(" AS ");
							fromTablesSql.append("  ");							
							fromTablesSql.append(table.getTbNmAcronym());
							fromTablesSql.append(" ON ");
							fromTablesSql.append(table.getMiddleTableNm());
							fromTablesSql.append(".");
							fromTablesSql.append(table.getRelationColumn().split(",")[0]);
							fromTablesSql.append(" = ");
							fromTablesSql.append(table.getTbNmAcronym());
							fromTablesSql.append(".");
							fromTablesSql.append(table.getRelationColumn().split(",")[0]);
						}else{
							fromTablesSql.append(" LEFT JOIN ");
							fromTablesSql.append(table.getTbName());
//							fromTablesSql.append(" AS ");
							fromTablesSql.append("  ");	
							fromTablesSql.append(table.getTbNmAcronym());  
							fromTablesSql.append(" ON ");   
							fromTablesSql.append(table.getTbNmAcronym());    
							fromTablesSql.append(".");   
							fromTablesSql.append(table.getRelationColumn()); 
							fromTablesSql.append(" = ");   
							fromTablesSql.append(CtztConstant.CUSTOMER_ACRONYM);  
							fromTablesSql.append(".");
							fromTablesSql.append(table.getRelationColumn());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
						}      
					}
					for(int j = 0; j < columns.length; j ++){
						if(breaks != 0){
							break;
						}
						DefinetableviewLookVO column = columns[j];
						if(column.getIsCode().equals("1")){
							StringBuffer aliasBStr = new StringBuffer();
							aliasBStr.append(column.getTbNmAcronym());
							aliasBStr.append(column.getCumName());
							String aliasStr = aliasBStr.toString().replaceAll("_", "").toLowerCase();
							dictMap.put(aliasStr, column.getCateId());
							dictTypeStr.append(column.getCateId());
							dictTypeStr.append(",");
							dictColumnStr.append(aliasStr);
							dictColumnStr.append(",");
						}
						if(column.getIsManytoone().equals(CtztConstant.ECIF_CTZT_IS_MANYTOONE_NO)){
							columnsSql.append(column.getTbNmAcronym());
							columnsSql.append(".");
							columnsSql.append(column.getCumName());
							columnsSql.append(" AS ");
							columnsSql.append(column.getTbNmAcronym());
							columnsSql.append(column.getCumName());
							columnsSql.append(",");
						}
					}
					breaks ++;
				}
				if(table.getIsManytoone().equals(CtztConstant.ECIF_CTZT_IS_MANYTOONE_YES)){
					StringBuffer manyColumnsStr = new StringBuffer();
					StringBuffer manyfromTablesSql = new StringBuffer();
					if(table.getIsMiddleTable().equals(CtztConstant.ECIF_CTZT_IS_MIDDLE_TABLE_YES)){  
						manyfromTablesSql.append(" LEFT JOIN ");
						manyfromTablesSql.append(table.getMiddleTableNm());
						manyfromTablesSql.append(" ON ");
						manyfromTablesSql.append(table.getMiddleTableNm());
						manyfromTablesSql.append(".");
						manyfromTablesSql.append(table.getRelationColumn().split(",")[1]);
						manyfromTablesSql.append(" = ");
						manyfromTablesSql.append(CtztConstant.CUSTOMER_ACRONYM);
						manyfromTablesSql.append(".");
						manyfromTablesSql.append(table.getRelationColumn().split(",")[1]);
						manyfromTablesSql.append(" INNER JOIN ");
						manyfromTablesSql.append(table.getTbName());
//						manyfromTablesSql.append(" AS ");
						manyfromTablesSql.append("  ");	
						manyfromTablesSql.append(table.getTbNmAcronym());
						manyfromTablesSql.append(" ON ");
						manyfromTablesSql.append(table.getMiddleTableNm());
						manyfromTablesSql.append(".");
						manyfromTablesSql.append(table.getRelationColumn().split(",")[0]);
						manyfromTablesSql.append(" = ");
						manyfromTablesSql.append(table.getTbNmAcronym());
						manyfromTablesSql.append(".");
						manyfromTablesSql.append(table.getRelationColumn().split(",")[0]);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
					}else{          
						manyfromTablesSql.append(" INNER JOIN ");  
						manyfromTablesSql.append(table.getTbName());  
//						manyfromTablesSql.append(" AS ");  
						manyfromTablesSql.append("  ");  
						manyfromTablesSql.append(table.getTbNmAcronym());   
						manyfromTablesSql.append(" ON ");    
						manyfromTablesSql.append(table.getTbNmAcronym()); 
						manyfromTablesSql.append(".");      
						manyfromTablesSql.append(table.getRelationColumn());   
						manyfromTablesSql.append(" = ");     
						manyfromTablesSql.append(CtztConstant.CUSTOMER_ACRONYM);    
						manyfromTablesSql.append(".");  
						manyfromTablesSql.append(table.getRelationColumn());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
					}  
					for(int j = 0; j < columns.length; j ++){
						DefinetableviewLookVO column = columns[j];
						if(table.getTbSerialNo() == column.getTbSerialNo()){
							manyColumnsStr.append(column.getTbNmAcronym());
							manyColumnsStr.append(".");
							manyColumnsStr.append(column.getCumName());
							manyColumnsStr.append(" AS ");
							manyColumnsStr.append(column.getTbNmAcronym());
							manyColumnsStr.append(column.getCumName());
							manyColumnsStr.append(",");
						}
					}
					if(manyColumnsStr.length() != 0){
						StringBuffer manyToOneSql = new StringBuffer();
						manyToOneSql.append(selectSql);
						manyToOneSql.append(manyColumnsStr.substring(0, manyColumnsStr.length()-1));
						manyToOneSql.append(fromSql);
						manyToOneSql.append(beginfromTablesSql);
						manyToOneSql.append(manyfromTablesSql);
						manyToOneSql.append(whereSql);
						sqlMap.put(table.getTbName(), manyToOneSql.toString());
					}
					
				}
			}
			if(columnsSql.length() != 0){
				oneToOneSql.append(selectSql);
				oneToOneSql.append(columnsSql.substring(0, columnsSql.length()-1));
				oneToOneSql.append(fromSql);
				oneToOneSql.append(beginfromTablesSql);
				oneToOneSql.append(fromTablesSql);
				oneToOneSql.append(whereSql);
				sqlMap.put("oneToOneSql", oneToOneSql.toString());
			}
			if(dictTypeStr.length() != 0){
				sqlMap.put("dictTypeStr", dictTypeStr.substring(0, dictTypeStr.length()-1).toString());
			}
			if(dictColumnStr.length() != 0){
				String dictColumn = dictColumnStr.substring(0, dictColumnStr.length()-1).replaceAll(",", "|");
				dictColumnStr.append("|");
				dictColumnStr.append(dictColumn);
				dictColumnStr.append("|");
				sqlMap.put("dictColumnStr", dictColumnStr.toString());
			}
			sqlMap.put("dictMap", dictMap);
			return sqlMap;
		}else{
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> getDictMap(Map<String, Object> sqlMap) {
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String,String>>();
		if(sqlMap != null && sqlMap.get("dictTypeStr") != null){
			String dictTypeStr = (String) sqlMap.get("dictTypeStr");
			if(dictTypeStr != null && dictTypeStr.length() > 0){
				Map<String, Map<String, String>> dictListMap = codeUtil.getDictListMap(dictTypeStr);
				Map<String, String> map = (Map<String, String>) sqlMap.get("dictMap");
				Set<Entry<String, String>> mapEntries = map.entrySet();
				Iterator<Entry<String, String>> mapIterator = mapEntries.iterator();
				while(mapIterator.hasNext()){
					Map.Entry<String, String> mapEntry = (Entry<String, String>) mapIterator.next();
					String mapKeyName = mapEntry.getKey();
					String mapKeyValue = mapEntry.getValue();
					Map<String, String> dict = dictListMap.get(mapKeyValue);
					resultMap.put(mapKeyName, dict);
				}	
			}
		}
		return resultMap;
	}
}
