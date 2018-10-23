package com.yuchengtech.emp.ecif.customer.customization.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.core.ExportReport;
import com.yuchengtech.emp.ecif.base.util.DownloadBigFile;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.customization.constant.CtztConstant;
import com.yuchengtech.emp.ecif.customer.customization.entity.DefinetableviewLookVO;

@Service("OrgCustomizationBS")
@Transactional(readOnly = true)
public class OrgCustomizationBS extends BaseBS<T> {

	protected static Logger log = LoggerFactory
			.getLogger(OrgCustomizationBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	@Autowired
	private ExportReport exportReport;
	
	@Autowired
	private CustomizationQueryBS customizationQueryBS;
	
	@Autowired
	private CustomizationColumnQueryBS customizationColumnQueryBS;	

	@SuppressWarnings("unchecked")
	public SearchResult<Map<String, String>> queryCustomzationListMaps(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap){
		String tbType = CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG;
		Map<String, Object> sqlMap = this.customizationQueryBS.getQueryCstListSql(tbType);
		if(sqlMap != null && sqlMap.get("oneToOneSql") != null){
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT CST.CUST_ID FROM M_CI_CUSTOMER CST WHERE 1 = 1 AND CST.CUST_TYPE = '"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"' ");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			if(conditionMap != null && conditionMap.get("fieldValues") != null){
				queryMap = (Map<String, Object>) conditionMap.get("fieldValues");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("custNo")))){
				sql.append(" AND CST.CUST_NO = '");
				sql.append(String.valueOf(queryMap.get("custNo")));
				sql.append("' ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("name")))){
				sql.append(" AND EXISTS (SELECT 1 FROM M_CI_NAMETITLE NMT WHERE NMT.CUST_ID = CST.CUST_ID AND CUST_NAME LIKE '%");
				sql.append(String.valueOf(queryMap.get("name")));
				sql.append("%') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("nationCode"))) 
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("orgType"))) 
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("industry")))
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("entProperty")))
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("entScale")))
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("economicType")))){
				sql.append(" AND EXISTS (SELECT 1 FROM M_CI_ORG OPF WHERE OPF.CUST_ID = CST.CUST_ID ");
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("nationCode")))){
					sql.append(" AND NATION_CODE = '");
					sql.append(String.valueOf(queryMap.get("nationCode")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("orgType")))){
					sql.append(" AND ORG_TYPE = '");
					sql.append(String.valueOf(queryMap.get("orgType")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("industry")))){
					sql.append(" AND INDUSTRY = '");
					sql.append(String.valueOf(queryMap.get("industry")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("entProperty")))){
					sql.append(" AND ENT_PROPERTY = '");
					sql.append(String.valueOf(queryMap.get("entProperty")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("entScale")))){
					sql.append(" AND ENT_SCALE = '");
					sql.append(String.valueOf(queryMap.get("entScale")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("economicType")))){
					sql.append(" AND ECONOMIC_TYPE = '");
					sql.append(String.valueOf(queryMap.get("economicType")));
					sql.append("' ");
				}
				sql.append(") ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createTellerNo")))){
				sql.append(" AND CREATE_TELLER_NO = '");
				sql.append(String.valueOf(queryMap.get("createTellerNo")));
				sql.append("' ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
				sql.append(" AND CREATE_BRANCH_NO = '");
				sql.append(String.valueOf(queryMap.get("createBranchNo")));
				sql.append("' ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("brccode1")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_BELONG_BRANCH BGR WHERE BGR.CUST_ID = CST.CUST_ID AND belong_branch_no = '");
				sql.append(String.valueOf(queryMap.get("brccode1")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("empcode1")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_BELONG_MANAGER BMA WHERE BMA.CUST_ID = CST.CUST_ID AND cust_manager_no = '");
				sql.append(String.valueOf(queryMap.get("empcode1")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("lifecycleStatType")))){
				sql.append(" AND EXISTS (SELECT 1 FROM M_CI_LIFECYCLE LCI WHERE LCI.CUST_ID = CST.CUST_ID AND LIFECYCLE_STAT_TYPE = '");
				sql.append(String.valueOf(queryMap.get("lifecycleStatType")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("identtype")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_ORG_IDENTIFIER OIF WHERE OIF.CUST_ID = CST.CUST_ID AND OIF.IDENT_TYPE = '");
				sql.append(String.valueOf(queryMap.get("identtype")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("contmethinfo")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_CONTMETH CON WHERE CON.CUST_ID = CST.CUST_ID AND con.CONTMETH_INFO = '");
				sql.append(String.valueOf(queryMap.get("contmethinfo")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("addr")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_ADDRESS ADDRESS WHERE ADDRESS.CUST_ID = CST.CUST_ID AND ADDRESS.ADDR LIKE '%");
				sql.append(String.valueOf(queryMap.get("addr")));
				sql.append("%') ");
			}
			
//			sql.append(" AND EXISTS(SELECT 1 FROM belongmanager bm WHERE bm.CUST_ID = CST.CUST_ID AND bm.belong_type <> '21') ");
//			sql.append(" AND EXISTS(SELECT 1 FROM belongbranch bb WHERE bb.CUST_ID = CST.CUST_ID AND bb.belong_type <> '21') ");
			
			if (!StringUtils.isEmpty(orderBy)) {
				sql.append(" ORDER BY " + orderBy + " " + orderType);
			}
			SearchResult<Object[]> cstResults = this.baseDAO
					.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql.toString(),
							null);
			List listObjs = cstResults.getResult();
			String custIds = "";
			if(listObjs.size()==1){
				custIds = listObjs.get(0).toString();
			}else{
				for (int m=0;m< listObjs.size();m++) {
					if(listObjs.get(m) instanceof Object[]){
						Object[] obj = (Object[])listObjs.get(m);
						custIds = custIds + obj[0] + ",";
					}else{
						custIds = custIds + listObjs.get(m).toString() + ",";
					}
				}
			}
			if(custIds.length() > 0){
				//超过一条记录进行处理
				if(custIds.indexOf(",")>-1){
					custIds = custIds.substring(0, custIds.length()-1);
				}
				String oneToOneSql = String.valueOf(sqlMap.get("oneToOneSql")) + " AND " + getSqlStrByMany(custIds,"CST.CUST_ID");
				List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(oneToOneSql.toString());
				List<Map<String, String>> returnListMaps = new ArrayList<Map<String, String>>();
				try{
					List<Map<String, String>> listMaps = resultUtil.listObjectsToListMaps(list, oneToOneSql.toString());
					String show = "page";
					returnListMaps = setListMapManyToOneVOrgCust(sqlMap, listMaps, show);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				if(returnListMaps != null){
					SearchResult<Map<String, String>> searchResult = new SearchResult<Map<String, String>>();
					searchResult.setResult(returnListMaps);
					searchResult.setTotalCount(cstResults.getTotalCount());
					return searchResult;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	public String getSqlStrByMany(String many,String columnName) { 

		int splitNum = 1000;
		String[] ids = many.split(",");
		
		StringBuffer sql = new StringBuffer(""); 
		if (ids != null) { 
			sql.append(" ").append(columnName).append (" IN ( "); 
			for (int i = 0; i < ids.length; i++) { 
				sql.append("'").append(ids[i] + "',"); 
				if ((i + 1) % splitNum == 0 && (i + 1) < ids.length) { 
					sql.deleteCharAt(sql.length() - 1); 
					sql.append(" ) OR ").append(columnName).append (" IN ("); 
				} 
			} 
			sql.deleteCharAt(sql.length() - 1); 
			sql.append(" )"); 
		} 
		return sql.toString(); 
	} 
	
	public List<Map<String, String>> queryCustomzationListMaps(String ids){
		ids = "'" + ids.replaceAll(",", "','") + "'";
		String tbType = CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG;
		Map<String, Object> sqlMap = this.customizationQueryBS.getQueryCstListSql(tbType);
		if(sqlMap != null && sqlMap.get("oneToOneSql") != null){
			StringBuffer sql = new StringBuffer("");
			sql.append(" AND CST.CUST_ID IN ("+ids+") AND CST.CUST_TYPE = '"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"' ");
			sql.append(" ORDER BY CST.CUST_ID ");
			String oneToOneSql = String.valueOf(sqlMap.get("oneToOneSql")) + sql.toString();
			List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(oneToOneSql.toString());
			List<Map<String, String>> returnListMaps = new ArrayList<Map<String, String>>();
			try{
				List<Map<String, String>> listMaps = resultUtil.listObjectsToListMaps(list, oneToOneSql.toString());
				String show = "excel";
				returnListMaps = setListMapManyToOneVOrgCust(sqlMap, listMaps, show);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			return returnListMaps;
		}else{
			return null;
		}
	}
	
	public List<Map<String, String>> queryCustomzationListMaps(Map<String, Object> queryMap){
		String tbType = CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG;
		Map<String, Object> sqlMap = this.customizationQueryBS.getQueryCstListSql(tbType);
		if(sqlMap != null && sqlMap.get("oneToOneSql") != null){
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT CST.CUST_ID FROM M_CI_CUSTOMER CST WHERE 1 = 1 AND CST.CUST_TYPE = '"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"' ");
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("custNo")))){
				sql.append(" AND CST.CUST_NO = '");
				sql.append(String.valueOf(queryMap.get("custNo")));
				sql.append("' ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("name")))){
				sql.append(" AND EXISTS (SELECT 1 FROM M_CI_NAMETITLE NMT WHERE NMT.CUST_ID = CST.CUST_ID AND CUST_NAME LIKE '%");
				sql.append(String.valueOf(queryMap.get("name")));
				sql.append("%') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("nationCode"))) 
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("orgType"))) 
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("industry")))
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("entProperty")))
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("entScale")))
					|| !ResultUtil.isEmpty(String.valueOf(queryMap.get("economicType")))){
				sql.append(" AND EXISTS (SELECT 1 FROM M_CI_ORG OPF WHERE OPF.CUST_ID = CST.CUST_ID ");
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("nationCode")))){
					sql.append(" AND NATION_CODE = '");
					sql.append(String.valueOf(queryMap.get("nationCode")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("orgType")))){
					sql.append(" AND ORG_TYPE = '");
					sql.append(String.valueOf(queryMap.get("orgType")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("industry")))){
					sql.append(" AND INDUSTRY = '");
					sql.append(String.valueOf(queryMap.get("industry")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("entProperty")))){
					sql.append(" AND ENT_PROPERTY = '");
					sql.append(String.valueOf(queryMap.get("entProperty")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("entScale")))){
					sql.append(" AND ENT_SCALE = '");
					sql.append(String.valueOf(queryMap.get("entScale")));
					sql.append("' ");
				}
				if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("economicType")))){
					sql.append(" AND ECONOMIC_TYPE = '");
					sql.append(String.valueOf(queryMap.get("economicType")));
					sql.append("' ");
				}
				sql.append(") ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createTellerNo")))){
				sql.append(" AND CREATE_TELLER_NO = '");
				sql.append(String.valueOf(queryMap.get("createTellerNo")));
				sql.append("' ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
				sql.append(" AND CREATE_BRANCH_NO = '");
				sql.append(String.valueOf(queryMap.get("createBranchNo")));
				sql.append("' ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("brccode1")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_BELONG_BRANCH BGR WHERE BGR.CUST_ID = CST.CUST_ID AND belong_branch_no = '");
				sql.append(String.valueOf(queryMap.get("brccode1")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("empcode1")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_BELONG_MANAGER BMA WHERE BMA.CUST_ID = CST.CUST_ID AND cust_manager_no = '");
				sql.append(String.valueOf(queryMap.get("empcode1")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("lifecycleStatType")))){
				sql.append(" AND EXISTS (SELECT 1 FROM M_CI_LIFECYCLE LCI WHERE LCI.CUST_ID = CST.CUST_ID AND LIFECYCLE_STAT_TYPE = '");
				sql.append(String.valueOf(queryMap.get("lifecycleStatType")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("identtype")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_ORG_IDENTIFIER OIF WHERE OIF.CUST_ID = CST.CUST_ID AND OIF.IDENT_TYPE = '");
				sql.append(String.valueOf(queryMap.get("identtype")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("contmethinfo")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_CONTMETH CON WHERE CON.CUST_ID = CST.CUST_ID AND con.CONTMETH_INFO = '");
				sql.append(String.valueOf(queryMap.get("contmethinfo")));
				sql.append("') ");
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("addr")))){
				sql.append(" AND EXISTS(SELECT 1 FROM M_CI_ADDRESS ADDRESS WHERE ADDRESS.CUST_ID = CST.CUST_ID AND ADDRESS.ADDR LIKE '%");
				sql.append(String.valueOf(queryMap.get("addr")));
				sql.append("%') ");
			}
			
			sql.append(" ORDER BY CST.CUST_NO ASC ");
			List<Object[]> cstlist = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
			String custIds = "";
			for (Object obj : cstlist) {
				custIds = custIds + obj + ",";
			}
			if(custIds.length() > 0){
				custIds = custIds.substring(0, custIds.length()-1);
				String oneToOneSql = String.valueOf(sqlMap.get("oneToOneSql")) + " AND CST.CUST_ID IN ("+custIds+")";
				List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(oneToOneSql.toString());
				List<Map<String, String>> resutlListMaps = new ArrayList<Map<String,String>>();
				try{
					List<Map<String, String>> listMaps = resultUtil.listObjectsToListMaps(list, oneToOneSql.toString());
					String show = "excel";
					resutlListMaps = setListMapManyToOneVOrgCust(sqlMap, listMaps, show);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				return resutlListMaps;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 设置与客户为对多一关系信息
	 * @param sqlMap 查询SQL
	 * @param listMaps 返回结果集
	 * @param show 信息展现形式
	 * @return
	 */
	public List<Map<String, String>> setListMapManyToOneVOrgCust(Map<String, Object> sqlMap, List<Map<String, String>> listMaps, String show) {
		if(listMaps != null && listMaps.size() > 0){
			Map<String, Map<String, String>> dictMap =  this.customizationQueryBS.getDictMap(sqlMap);
			String dictColumnStr = (String) sqlMap.get("dictColumnStr");
			for(Map<String, String> cstMap : listMaps){
				if(dictColumnStr != null){
					Set<Entry<String, String>> cstMapEntries = cstMap.entrySet();
					Iterator<Entry<String, String>> cstMapIterator = cstMapEntries.iterator();
					while(cstMapIterator.hasNext()){
						Map.Entry<String, String> cstMapEntry = (Entry<String, String>) cstMapIterator.next();
						String cstkey = cstMapEntry.getKey();
						if(dictColumnStr.toUpperCase().indexOf("|"+cstkey.toUpperCase()+"|") == -1){
							continue;
						}
						String cstvalue = cstMapEntry.getValue();
						if(dictMap.get(cstkey) != null && dictMap.get(cstkey).get(cstvalue) != null){
							String cstvalueName = dictMap.get(cstkey).get(cstvalue);
							if(cstvalueName == null || cstvalueName.length() == 0){
								cstvalueName = cstvalue;
							}
							cstMap.put(cstkey, cstvalueName);
						}
					}	
				}
				if(sqlMap != null){
					Set<Entry<String, Object>> mapEntries = sqlMap.entrySet();
					if(mapEntries != null){
						Iterator<Entry<String, Object>> mapIterator = mapEntries.iterator();
						while(mapIterator.hasNext()){
							Map.Entry<String, Object> mapEntry = (Entry<String, Object>) mapIterator.next();
							String mapKeyName = mapEntry.getKey();
//							if(mapKeyName.equals("M_CI_ORG_IDENTIFIER") && cstMap.get("cstcustid").equals("1000222010921")){
//								System.out.println(1);
//							}
							if(!mapKeyName.equals("oneToOneSql") && !mapKeyName.equals("dictMap") && !mapKeyName.equals("dictTypeStr") && !mapKeyName.equals("dictColumnStr")){
								String mapValue = String.valueOf(mapEntry.getValue());
								String sql = "";
								if(mapValue.indexOf("BELONGMANAGER") != -1){
									sql = mapValue.toString() + " AND CST.CUST_ID = " + cstMap.get("cstcustid") + " AND belong_type <> '21'";
								}else if(mapValue.indexOf("BELONGBRANCH") != -1){
									sql = mapValue.toString() + " AND CST.CUST_ID = " + cstMap.get("cstcustid") + " AND belong_type <> '21'";
								}else{
									sql = mapValue.toString() + " AND CST.CUST_ID = " + cstMap.get("cstcustid");
								}
								if(mapValue != null && mapValue.length() > 0){
									List<Object[]> manyToOneLsits = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
									if(manyToOneLsits.size() == 1){
										boolean t = false;
										if(manyToOneLsits.get(0) instanceof java.lang.Object[]){
											Object[] objs = manyToOneLsits.get(0);
											if(objs == null){
												continue;
											}
											for(Object obj : objs){
												if(obj != null && obj !="null" && obj != ""){
													t = true;
												}
											}
											if(t == false){
												continue;
											}
										}else{
											 if(manyToOneLsits.get(0) == null){
												 continue;
											 }
										}
									}
									Map<String, String> map = new HashMap<String, String>();
									try {
										map = this.listObjectsToListMaps(manyToOneLsits, mapValue.toString(), show);
										if(map != null){
											if(dictColumnStr != null){
												Set<Entry<String, String>> manyToOneMapEntries = map.entrySet();
												Iterator<Entry<String, String>> manyToOneMapIterator = manyToOneMapEntries.iterator();
												while(manyToOneMapIterator.hasNext()){
													Map.Entry<String, String> manyToOneMapEntry = (Entry<String, String>) manyToOneMapIterator.next();
													String manyToOnekey = manyToOneMapEntry.getKey();
													String manyToOnevalue = manyToOneMapEntry.getValue();
													String manyToOnevalueName = "";
													if(manyToOnevalue != null && manyToOnevalue.length() > 0){
														manyToOnevalue = manyToOnevalue.replaceAll("【", "");
														manyToOnevalue = manyToOnevalue.substring(0, manyToOnevalue.length()-1);
														if(manyToOnevalue.length() != 0){
															String[] values = null;
															if(show.equals("page")){
																values = manyToOnevalue.split("\\】<br>");
															}else{
																values = manyToOnevalue.split("\\】\n");
															}
															if(values != null && values.length != 0){
																if(dictColumnStr.toUpperCase().indexOf("|"+manyToOnekey.toUpperCase()+"|") == -1){
																	continue;
																}
																int s = 0;
																for(String dictId : values){
																	String dictName = dictMap.get(manyToOnekey).get(dictId);
																	if(dictName == null || dictName.length() == 0){
																		dictName = dictId;
																	}
											            			if(show.equals("page")){
											            				if(s == values.length-1){
																			manyToOnevalueName = manyToOnevalueName + "【" + dictName + "】"; 
											            				}else{
																			manyToOnevalueName = manyToOnevalueName + "【" + dictName + "】<br>"; 
											            				}
											            			}else{
											            				if(s == values.length-1){
																			manyToOnevalueName = manyToOnevalueName + "【" + dictName + "】"; 
											            				} else {
																			manyToOnevalueName = manyToOnevalueName + "【" + dictName + "】\n"; 
											            				}
											            			}
											            			s ++;
																}
															}
														}	
													}
													map.put(manyToOnekey, manyToOnevalueName);
												}
											}	
											cstMap.putAll(map);
										}
									} catch (Exception e) {
										log.error(e.getMessage(),e);
									}
								}
							}
						}	
					}
				}
			}
			return listMaps;
		}else{
			return null;
		}
	}

	/**
	 * 将List<Object[]>对象通过查询SQL映射成Map<String, String>
	 * @param list List<Object[]>对象 
	 * @param selectsql 查询SQL
	 * @return 
	 * @throws Exception
	 */
	public Map<String, String> listObjectsToListMaps(List<Object[]> list, String selectsql, String show) throws Exception {

        if (list == null || list.size() == 0) {
            return null;
        }
        if(selectsql == null || selectsql.trim().length() == 0){
        	throw new Exception("查询Sql不能为空!");
        }
        selectsql = selectsql.toUpperCase();
        if(selectsql.indexOf("SELECT ") == -1 || selectsql.indexOf(" FROM") == -1){
        	throw new Exception("查询Sql语句有误!");
        }
        String colAllStr = selectsql.substring(selectsql.indexOf("SELECT ")+7, selectsql.indexOf(" FROM"));
        String cols = "";
        if(colAllStr != null && colAllStr.length() > 0){
	        String[] colStrs = colAllStr.split(",");
	        int i = 0;
	        for(String colStr : colStrs){
	        	colStr = colStr.trim();
	        	if(colStr.indexOf(" AS ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" AS ")+4, colStr.length());
	        	}
	        	if(colStr.indexOf(" ") != -1){
	        		colStr = colStr.substring(colStr.indexOf(" ")+1, colStr.length());
	        	} 
	        	if(colStr.indexOf(".") != -1){
	        		colStr = colStr.substring(colStr.indexOf(".")+1, colStr.length());
	        	}
	        	colStr = colStr.replaceAll("_", "");
	        	if(i == colStrs.length-1){
	        		cols = cols + colStr;
	        	}else{
	        		cols = cols + colStr + ",";
	        	}
	        	i ++;
	        }
        }
        cols = cols.toLowerCase();
        String[] colss = cols.split(",");
        if(colss.length > 1){
            Object[] objjust = list.get(0) ;
            if(objjust.length != colss.length){
            	throw new Exception("查询语句列与查询结果列数量不符!");
            }
            Map<String, String> map = new HashMap<String, String>();
            for(int i = 0; i < colss.length; i ++){
            	String keyName = colss[i];
            	String keyValue = "";
            	int s = 0;
            	for(Object[] obj : list){
            		if(obj[i] == null || obj[i].equals("null")){
            			obj[i] = "";
            		}
        			if(show.equals("page")){
        				if(s == list.size()-1){
            				keyValue = keyValue + "【"+ obj[i].toString() + "】";
        				}else{
            				keyValue = keyValue + "【"+ obj[i].toString() + "】<br>";
        				}
        			}else{
        				if(s == list.size()-1){
            				keyValue = keyValue + "【"+ obj[i].toString() + "】";
        				} else {
            				keyValue = keyValue + "【"+ obj[i].toString() + "】\n";
        				}
        			}
        			s ++;
            	}
            	map.put(keyName, keyValue);
            }
            return map;
        }else{
            Map<String, String> map = new HashMap<String, String>();
        	String keyName = colss[0];
        	String keyValue = "";
        	int s = 0;
        	for(Object obj : list){
        		if(obj == null || obj.equals("null")){
        			obj = "";
        		}
    			if(show.equals("page")){
    				if(s == list.size()-1){
    					keyValue = keyValue + "【"+ obj.toString() + "】";
    				}else{
    					keyValue = keyValue + "【"+ obj.toString() + "】<br>";
    				}
    			}else{
    				if(s == list.size()-1){
    					keyValue = keyValue + "【"+ obj.toString() + "】";
    				} else {
    					keyValue = keyValue + "【"+ obj.toString() + "】\n";
    				}
    			}
    			s ++;
        	}
            map.put(keyName, keyValue);
            return map;
        }
    }
	
	/**
	 * 生成报表入口
	 * @param reportNo
	 * @param custNo
	 * @param identNo
	 * @param lastUpdateSys
	 * @return
	 */
	public String export(String rule) {
		JSONArray rulesJson = JSONObject.fromObject(rule).getJSONArray("rules");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter.hasNext();) {
			JSONObject jsonstr = (JSONObject) conditioniter.next();
			queryMap.put((String) jsonstr.get("field"), (String) jsonstr.get("value"));
		}
		
		//DownloadFile downloadFile = new DownloadFile(); 
		DownloadBigFile downloadFile = new DownloadBigFile(); 
		//HSSFWorkbook wb = null;
		SXSSFWorkbook wb = null;
		try{
			DefinetableviewLookVO[] tables = customizationColumnQueryBS.getIsResultDefinetableviews(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG);
			DefinetableviewLookVO[] columns = customizationColumnQueryBS.getisMustColumn(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG);
			List<Map<String, String>> listMaps = queryCustomzationListMaps(queryMap);
			Date date = new Date();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String[][] tableName = new String[columns.length][2];
			String[][] tableReader = new String[1][2];
			tableReader[0][0] = "对公客户定制查询客户信息";
			tableReader[0][1] = f.format(date);

			Integer[] columnWidhts = new Integer[columns.length];
			String[][] extraLeaderRow = new String[tables.length][2]; 
			int tbs = 0;
			for(DefinetableviewLookVO table : tables) {
				int tablecolumn = 0;
				for(DefinetableviewLookVO column : columns) {
					if(table.getTbSerialNo() == column.getTbSerialNo()){
						
						tablecolumn ++;
					}
				}
				extraLeaderRow[tbs][0] = tablecolumn+"";
				extraLeaderRow[tbs][1] = table.getTbCnnm();
				tbs ++;
			}
			for(int i = 0; i < columns.length; i ++){
				String columName = columns[i].getTbNmAcronym()+columns[i].getCumName().replaceAll("_", "");
				columName = columName.toLowerCase();
				tableName[i][0] = columName;
				tableName[i][1] = columns[i].getCumCnnm();
				int width = 2*4 + 1;
				int len = 2*columns[i].getCumCnnm().length();
				if(len > width){
					width = len +1;
				}
				if(columns[i].getDataType().equals("CHARACTER")){
					if(columns[i].getDataLen() != null && columns[i].getDataLen().trim().length() > 0){
						int dataLen = Integer.valueOf(columns[i].getDataLen());
						if(dataLen > width){
							width = dataLen +1;
						}
					}
				}
				if(columns[i].getDataType().equals("BIGINT") || columns[i].getDataType().equals("VARCHAR")){
					width = 20;
				}
				columnWidhts[i] = width;
			}
			downloadFile.setColumnFormat(columnWidhts);
			downloadFile.setTableReader(tableReader);         
			downloadFile.addExtraLeaderRow(extraLeaderRow);     
			downloadFile.setTableArray(tableName);	
			downloadFile.setPromptMsg("对公客户定制查询客户信息");
			downloadFile.setFileName("对公客户定制查询客户信息.xlsx");
			downloadFile.produce(listMaps);
			wb = downloadFile.getWorkTable();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		// 准备工作
		String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
		String file = exportReport.getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, wb,"EXCEL_REPORT_ORG_DZ");
		return file;
	}
}
