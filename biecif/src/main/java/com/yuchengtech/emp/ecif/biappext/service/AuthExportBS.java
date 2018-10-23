package com.yuchengtech.emp.ecif.biappext.service;

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

@Service("AuthexportBS")
@Transactional(readOnly = true)
public class AuthExportBS extends BaseBS<T> {

	protected static Logger log = LoggerFactory
			.getLogger(AuthExportBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	@Autowired
	private ExportReport exportReport;
	
	
	@SuppressWarnings("unchecked")
	public SearchResult<Map<String, String>> queryCustomzationListMaps(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap){
		
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		if(sqlMap != null && sqlMap.get("oneToOneSql") != null){
			StringBuffer sql = new StringBuffer("");
			sql.append("select user_id,user_no,user_name from BIONE_USER_INFO ");
			
			String oneToOneSql =  sql.toString();
			System.out.println(oneToOneSql.toString());
			SearchResult<Object> searchResultOjbect = this.baseDAO.findPageWithNameParamByNativeSQL(firstResult, pageSize,
					oneToOneSql.toString(), null);
			List<Object> list = searchResultOjbect.getResult();
			List<Map<String, String>> resutlListMaps = new ArrayList<Map<String,String>>();
			try{
				List<Map<String, String>> listMaps = resultUtil.listObjectToListMaps(list, oneToOneSql.toString());
				String show = "page";
				resutlListMaps = setListMapManyToOneVOrgCust(sqlMap, listMaps, show);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				System.out.println(e.getMessage());
			}
			if(resutlListMaps != null){
				SearchResult<Map<String, String>> searchResult = new SearchResult<Map<String, String>>();
				searchResult.setResult(resutlListMaps);
				searchResult.setTotalCount(searchResultOjbect.getTotalCount());
				return searchResult;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public List<Map<String, String>> queryCustomzationListMaps(){
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(" select user.user_id,user.user_no,user.user_name,'' as employee_no,org_name,role_no,role_name from bione_user_info user ");
		                         sql.append(" left join bione_org_info org on user.org_no = org.org_no");
		                         sql.append(" left join bione_auth_obj_user_rel rel on user.user_id = rel.user_id and obj_def_no= 'AUTH_OBJ_ROLE' ");
		                         sql.append(" left join bione_role_info role on rel.obj_id = role.role_id");
		                         sql.append(" where user_no<>'bione_super'");
		                         sql.append(" order by  user_no ");
		String oneToOneSql =  sql.toString();
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(oneToOneSql.toString());
		
		//去重，用于excel显示
		for(int i=list.size()-1;i>0;i--){
			Object[] objs = (Object[])list.get(i);
			String user_id = objs[0].toString();
			
			Object[] objs_next = (Object[])list.get(i-1);
			String user_id_next = objs_next[0].toString();
			if(user_id_next.equals(user_id)){
				objs[0] = "";
				objs[1] = "";
				objs[2] = "";
				objs[3] = "";
				objs[4] = "";
				
				list.set(i, objs);
			}
				
		}
		
		List<Map<String, String>> resutlListMaps = new ArrayList<Map<String,String>>();
		try{
			List<Map<String, String>> listMaps = resultUtil.listObjectsToListMaps(list, oneToOneSql.toString());
			String show = "excel";
			resutlListMaps = setListMapManyToOneVOrgCust(sqlMap, listMaps, show);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return resutlListMaps;

	}
		
	public List<Map<String, String>> queryCustomzationListMapsGrant(){
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(" select role_no,role_name,rel.res_id,func.func_name as res_name from bione_role_info role   ");
		                         sql.append(" left join bione_auth_obj_res_rel rel on role.role_id = rel.obj_id  and obj_def_no= 'AUTH_OBJ_ROLE' and  res_def_no ='AUTH_RES_MENU'  ");
		                         sql.append(" left join bione_menu_info menu on rel.res_id = menu.menu_id");
		                         sql.append(" left join bione_func_info func on menu.func_id = func.func_id ");
		                         sql.append(" order by  role_no,func.func_name");
		String oneToOneSql =  sql.toString();
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(oneToOneSql.toString());
		
		//去重，用于excel显示
		for(int i=list.size()-1;i>0;i--){
			Object[] objs = (Object[])list.get(i);
			String user_id = objs[0].toString();
			
			Object[] objs_next = (Object[])list.get(i-1);
			String user_id_next = objs_next[0].toString();
			if(user_id_next.equals(user_id)){
				objs[0] = "";
				objs[1] = "";
				
				list.set(i, objs);
			}
				
		}
		
		List<Map<String, String>> resutlListMaps = new ArrayList<Map<String,String>>();
		try{
			List<Map<String, String>> listMaps = resultUtil.listObjectsToListMaps(list, oneToOneSql.toString());
			String show = "excel";
			resutlListMaps = setListMapManyToOneVOrgCust(sqlMap, listMaps, show);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return resutlListMaps;

	}
		
	
	/**
	 * 设置与客户为对多一关系信息
	 * @param sqlMap 多对一关系SQL
	 * @param listMaps 返回结果
	 * @param show 查询结果展现形式
	 */
	public List<Map<String, String>> setListMapManyToOneVOrgCust(Map<String, Object> sqlMap, List<Map<String, String>> listMaps, String show) {
		if(listMaps != null && listMaps.size() > 0){
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
					}
				}
				if(sqlMap != null){
					Set<Entry<String, Object>> mapEntries = sqlMap.entrySet();
					if(mapEntries != null){
						Iterator<Entry<String, Object>> mapIterator = mapEntries.iterator();
						while(mapIterator.hasNext()){
							Map.Entry<String, Object> mapEntry = (Entry<String, Object>) mapIterator.next();
							String mapKeyName = mapEntry.getKey();
							if(!mapKeyName.equals("oneToOneSql") && !mapKeyName.equals("dictMap") && !mapKeyName.equals("dictTypeStr") && !mapKeyName.equals("dictColumnStr")){
								String mapValue = String.valueOf(mapEntry.getValue());
								String sql = mapValue.toString() + " AND CST.CUST_ID = " + cstMap.get("cstcustid");
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
													if(dictColumnStr.toUpperCase().indexOf("|"+manyToOnekey.toUpperCase()+"|") == -1){
														continue;
													}
													String manyToOnevalue = manyToOneMapEntry.getValue();
													String manyToOnevalueName = "";
													if(manyToOnevalue != null && manyToOnevalue.length() > 0){
														manyToOnevalue = manyToOnevalue.replaceAll("【", "");
														manyToOnevalue = manyToOnevalue.substring(0, manyToOnevalue.length()-1);
														String[] values = null;
														if(show.equals("page")){
															values = manyToOnevalue.split("\\】<br>");
														}else{
															values = manyToOnevalue.split("\\】\n");
														}
														int s = 0;
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
    					keyValue = keyValue + "【"+ obj.toString() + "】\r\n";
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
	public String export(String ids) {
		//DownloadFile downloadFile = new DownloadFile(); 
		DownloadBigFile downloadFile = new DownloadBigFile(); 
		//HSSFWorkbook wb = null;
		SXSSFWorkbook wb = null;
		try{
			
			List<Map<String, String>> listMaps = queryCustomzationListMaps();
			Date date = new Date();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			
			String[][] tableName  = {
					{"userid", "用户标识"}, 
					{"userno","用户编号"}, 
					{"username", "用户名称"}, 
					{"employeeno", "员工号"}, 
					{"orgname", "所属机构"},
					{"roleno", "角色编号"}, 
					{"rolename", "角色名称"}};
			
			String[][] tableReader = new String[1][2];
			tableReader[0][0] = "用户角色信息";
			tableReader[0][1] = f.format(date);

			Integer[] columnWidhts = new Integer[7];
			String[][] extraLeaderRow = new String[1][2]; 

			for(int i = 0; i < 7; i ++){
				int width = 20;
				columnWidhts[i] = width;
			}
			
			downloadFile.setColumnFormat(columnWidhts);
			downloadFile.setTableReader(tableReader);  
//			downloadFile.addExtraLeaderRow(extraLeaderRow);  
			downloadFile.setTableArray(tableName);	
			downloadFile.setPromptMsg("");
			downloadFile.setFileName("用户角色信息.xls");
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
		String file = exportReport.getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, wb,"EXCEL_REPORT_PER_DZ");
		return file;
	}
	
	/**
	 * 生成报表入口
	 * @param reportNo
	 * @param custNo
	 * @param identNo
	 * @param lastUpdateSys
	 * @return
	 */
	public String exportGrant(String ids) {
		//DownloadFile downloadFile = new DownloadFile(); 
		DownloadBigFile downloadFile = new DownloadBigFile(); 
		//HSSFWorkbook wb = null;
		SXSSFWorkbook wb = null;
		try{
			
			List<Map<String, String>> listMaps = queryCustomzationListMapsGrant();
			Date date = new Date();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			
			String[][] tableName  = {
					{"roleno", "角色编号"}, 
					{"rolename", "角色名称"},
					{"resid", "权限编号"}, 
					{"resname", "权限名称"}};
			
			String[][] tableReader = new String[1][2];
			tableReader[0][0] = "角色权限信息";
			tableReader[0][1] = f.format(date);

			Integer[] columnWidhts = new Integer[4];
			String[][] extraLeaderRow = new String[1][2]; 

			for(int i = 0; i < 4; i ++){
				int width = 20;
				columnWidhts[i] = width;
			}
			
			downloadFile.setColumnFormat(columnWidhts);
			downloadFile.setTableReader(tableReader);  
//			downloadFile.addExtraLeaderRow(extraLeaderRow);  
			downloadFile.setTableArray(tableName);	
			downloadFile.setPromptMsg("");
			downloadFile.setFileName("角色权限信息.xls");
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
		String file = exportReport.getFilePath2(realpath, GlobalConstants.EXCEL_REPORT_FOLDER, wb,"EXCEL_REPORT_PER_DZ");
		return file;
	}	
	
}
