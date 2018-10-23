/**
 * 
 */
package com.yuchengtech.emp.ecif.base.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class CodeUtil extends BaseBS<Object> {

	/**
	 * 获取信息, 用于生成下拉框1
	 * @return
	 */
	public List<Map<String, String>> getComBoBox(String codeType) {
		//String sql = "select CODE_ID, CODE_DESC from STD_CODE where CATE_ID=?0";
		codeType = codeType.replaceAll(",", "','");		
		String sql = "select STD_CODE, STD_CODE_DESC " +
				" from TX_STD_CODE where  STD_CATE in ('"+codeType+"')";
		//
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		
		CollatorComparator comparator=new CollatorComparator();
		Collections.sort(objList, comparator);
		
		List<Map<String, String>> comboList = Lists.newArrayList();
		Map<String, String> map;
		for(Object[] obj: objList) {
			map = Maps.newHashMap();
			map.put("id", obj[0] != null ? obj[0].toString() : "");
			map.put("text", obj[1] != null ? obj[1].toString() : "");
			comboList.add(map);
		}
		return comboList;
	}
	
	/**
	 * 获取信息, 用于生成下拉框2（域+码值）
	 * @return
	 */
	public List<Map<String, String>> getComBoBox2(String codeType) {
		//String sql = "select CODE_ID, CODE_DESC from STD_CODE where CATE_ID=?0";
		codeType = codeType.replaceAll(",", "','");
		String sql = "select STD_CATE||STD_CODE code,STD_CODE_DESC val " +
				" from TX_STD_CODE where  STD_CATE in ('"+codeType+"')";
		//
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		
		CollatorComparator comparator=new CollatorComparator();
		Collections.sort(objList, comparator);
		
		List<Map<String, String>> comboList = Lists.newArrayList();
		Map<String, String> map;
		for(Object[] obj: objList) {
			map = Maps.newHashMap();
			map.put("id", obj[0] != null ? obj[0].toString() : "");
			map.put("text", obj[1] != null ? obj[1].toString() : "");
			comboList.add(map);
		}		
		return comboList;
	}
	
	/**
	 * 查询业务字典类型对应业务字典项
	 * @param codeTypes 业务字典类型 多个以逗号“,”隔开
	 * @return
	 */
	public Map<String, Map<String, String>> getDictListMap(String codeTypes) {
		codeTypes = "'" + codeTypes.replaceAll(",", "','") + "'";
		String sql = "SELECT STD_CODE, STD_CATE, STD_CODE_DESC FROM TX_STD_CODE where  STD_CATE IN (" + codeTypes + ") ";
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		Map<String, Map<String, String>> dictMaps = new HashMap<String, Map<String,String>>();
		if(list != null && list.size() > 0){
			codeTypes = codeTypes.substring(1, codeTypes.length()-1);
			codeTypes = codeTypes.replaceAll("','", ",");
			String[] codeTypess = codeTypes.split(",");
			for(String codeType : codeTypess){
				Map<String, String> map = new HashMap<String, String>();
				for(Object[] obj : list){
					String objCodeType = obj[1].toString();
					if(codeType.equals(objCodeType)){
						map.put(obj[0].toString(), obj[2].toString());
					}
				}
				dictMaps.put(codeType, map);
			}
		}
		return dictMaps;
	}
	
	/**
	 * 通过传入的域，得到需要的码值
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getCodeMap(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE code,STD_CODE_DESC val from TX_STD_CODE where  STD_CATE in ('");
			jql.append(codeType);
			jql.append("')");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值（域+码值）
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getCodeMap2(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CATE||STD_CODE code,STD_CODE_DESC val from TX_STD_CODE where  STD_CATE in ('");
			jql.append(codeType);
			jql.append("')");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getDescCodeMap(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE_DESC val,STD_CODE code from TX_STD_CODE where  STD_CATE in ('");
			jql.append(codeType);
			jql.append("')");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getDescCodeMap2(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE_DESC val,STD_CATE||STD_CODE code from TX_STD_CODE where  STD_CATE in ('");
			jql.append(codeType);
			jql.append("')");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 查询业务字典类型对应业务字典项
	 * @param codeTypes 业务字典类型 多个以逗号“,”隔开
	 * @return
	 */
	public Map<String, Map<String, String>> getDictListMapByColumns(String tableName, String columns) {
		StringBuffer sql = new StringBuffer();
		columns = "'" + columns.replaceAll(",", "','") + "'";
		sql.append(" SELECT COL_NAME, STD_CODE, STD_CATE, STD_CODE_DESC FROM TX_STD_CODE DICT,TX_COL_DEF CDEF,TX_TAB_DEF TDEF ");
		sql.append(" WHERE TDEF.TAB_ID = CDEF.TAB_ID AND CDEF.CATE_ID = DICT.STD_CATE AND CDEF.IS_CODE = '1' AND TDEF.TAB_NAME = '"+tableName+"' AND CDEF.COL_NAME IN ("+columns+") ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		Map<String, Map<String, String>> dictMaps = new HashMap<String, Map<String,String>>();
		if(list != null && list.size() > 0){
			columns = columns.substring(1, columns.length()-1);
			columns = columns.replaceAll("','", ",");
			String[] columnss = columns.split(",");
			for(String column : columnss){
				Map<String, String> map = new HashMap<String, String>();
				for(Object[] obj : list){
					if(column.equals(obj[0].toString())){
						map.put(obj[1].toString(), obj[3].toString());
					}
				}
				column = column.replaceAll("_", "");
				column = column.toUpperCase();
				dictMaps.put(column, map);
			}
		}
		return dictMaps;
	}
	
	/**
	 * 获取所有机构信息，做机构编号和机构名称映射
	 * @return
	 */
	public Map<String, String> getOrgMap(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT BRCCODE, BRCNAME FROM PUBBRANCHINFO ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		Map<String, String> map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	public Map<String, String> getOrgMap2(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT BRCNAME, BRCCODE FROM PUBBRANCHINFO ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		Map<String, String> map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 获取所有员工信息，做员工编号和员工名称映射
	 * @return
	 */
	public Map<String, String> getEmpMap(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT EMPCODE, EMPNAME FROM EMPLOYEEINFO ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		Map<String, String> map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 获取所有渠道信息，做渠道标识和渠道名称映射
	 * @return
	 */
	public Map<String, String> getChannelMap(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CHANNEL_ID, CHANNEL_NO,CHANNEL_NAME FROM CHANNELINFO ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		Map<String, String> map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				map.put(obj[0].toString(), obj[2].toString());
			}
		}
		return map;
	}
	
	/**
	 * 获取所有产品信息，做产品代码和产品名称映射
	 * @return
	 */
	public Map<String, String> getProductMap(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PROD_CODE, PROD_NAME FROM PRODUCT ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		Map<String, String> map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getDescCodeMapCustIdent(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE_DESC val,STD_CODE code from TX_STD_CODE where STD_CATE in ('");
			jql.append(codeType);
			jql.append("') and STATE = '1' ");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值（域+码值）
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getCodeMap2CustIdent(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CATE||STD_CODE code,STD_CODE_DESC val from TX_STD_CODE where STD_CATE in ('");
			jql.append(codeType);
			jql.append("') and STATE = '1' ");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 获取信息, 用于生成下拉框2（域+码值）
	 * @return
	 */
	public List<Map<String, String>> getComBoBox2CustIdent(String codeType) {
		//String sql = "select CODE_ID, CODE_DESC from STD_CODE where CATE_ID=?0";
		codeType = codeType.replaceAll(",", "','");
		String sql = "select STD_CATE||STD_CODE code,STD_CODE_DESC val " +
				" from TX_STD_CODE where STD_CATE in ('"+codeType+"') and STATE = '1' ";
		//
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		
		CollatorComparator comparator=new CollatorComparator();
		Collections.sort(objList, comparator);
		
		List<Map<String, String>> comboList = Lists.newArrayList();
		Map<String, String> map;
		for(Object[] obj: objList) {
			map = Maps.newHashMap();
			map.put("id", obj[0] != null ? obj[0].toString() : "");
			map.put("text", obj[1] != null ? obj[1].toString() : "");
			comboList.add(map);
		}		
		return comboList;
	}
	
	/**
	 * 通过传入的域，得到需要的码值
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getDescCodeMap2CustIdent(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE_DESC val,STD_CATE||STD_CODE code from TX_STD_CODE where STD_CATE in ('");
			jql.append(codeType);
			jql.append("') and STATE = '1' ");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getDescCodeMapCustRel(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE_DESC val,STD_CODE code from TX_STD_CODE where  STD_CATE in ('");
			jql.append(codeType);
			jql.append("') and length(std_code) > 10");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
}
