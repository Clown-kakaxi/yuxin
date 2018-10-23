package com.ecc.echain.wf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

/**
 * @describtion: 报文封装工具类
 *
 * @author : 
 * @date : 
 */
public class XmlTableUtilNew extends EChainCallbackCommon{
	
	/**
	 * 封装查询出来的内容
	 * @param list 此参数必须按UPDATE_TABLE项排序，否则会存在逻辑错误
	 * @param vo 不能通过此参数去获取流程实例号等相关信息,因为全行非授信一次复核已经作了特殊处理
	 * @return
	 * @throws Exception
	 */
	public String reqBodyXml(List<Map<Object,Object>> list,EVO vo) throws Exception{
		StringBuffer sb = new StringBuffer();
		//首先将拿出来的封装成一个Map
		Map<Object,List<Map<Object,Object>>> parmMap = new HashMap<Object,List<Map<Object,Object>>>();
		List<Map<Object,Object>> lists = new ArrayList<Map<Object,Object>>();
		for(int i=0;i<list.size();i++){
			Map<Object,Object> columnMap = new HashMap<Object, Object>();
			Map<Object,Object> map = list.get(i);
			Object tableName = map.get("UPDATE_TABLE");
			if(tableName!=null && !tableName.equals("")){
				if(checkTable(tableName.toString())){
					if(parmMap.containsKey(tableName)){
						columnMap.put(map.get("UPDATE_ITEM_EN"), map.get("UPDATE_AF_CONT"));	
						lists.add(columnMap);
						parmMap.put(tableName, lists);
					}else{
						lists = new ArrayList<Map<Object,Object>>();
						columnMap.put(map.get("UPDATE_ITEM_EN"), map.get("UPDATE_AF_CONT"));
						lists.add(columnMap);
						parmMap.put(tableName,lists);
					}
				}else{
					continue;
				}
			}
		}
		//在对里面的Map做具体的处理
		Set<Object> set = parmMap.keySet();
		Iterator<Object> it = set.iterator();
		while(it.hasNext()){
			Object tableName = it.next();
			List<Map<Object,Object>> bodyList = parmMap.get(tableName);
			sb.append("<"+returnTableName((String)tableName)+">");
			for(int i=0;i<bodyList.size();i++){
				Map<Object,Object> bodyMap = bodyList.get(i);
				Set<Object> bodyset = bodyMap.keySet();
				Iterator<Object> bodyit = bodyset.iterator();
				while(bodyit.hasNext()){
					Object column = bodyit.next();
					//column=getColunmName((String)column,vo);
					if(column!=null && !column.toString().trim().equals("")){
						String content = bodyMap.get(column) != null ?(String)bodyMap.get(column):"";
						sb.append("<"+changeColunm((String)column)+">"+encodeString(content)+"</"+changeColunm((String)column)+">");
					}
				}
			}
			sb.append("</"+returnTableName((String)tableName)+">");
		}
		return sb.toString();
	}
	
	/**
	 * 核对是否需要同步到ECIF交易的表
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
//	public boolean checkTable(String tableName) throws Exception{
//		String[] str={"ACRM_F_CI_ADDRESS0","ACRM_F_CI_ADDRESS1","ACRM_F_CI_CUST_IDENTIFIER","ACRM_F_CI_CONTMETH","ACRM_F_CI_CUSTOMER",
//                      "ACRM_F_CI_PERSON","ACRM_F_CI_ORG","OCRM_F_CI_BELONG_CUSTMGR","OCRM_F_CI_BELONG_ORG","ACRM_F_CI_SPECIALLIST","ACRM_F_CI_PER_KEYFLAG"};
//		for(String strs:str){
//			if(tableName!=null && strs.equals(tableName.trim())){
//				return true;
//			}
//		}
//		return false;	
//	}
	public boolean checkTable(String tableName) throws Exception{
		String[] str={"ACRM_F_CI_CUSTOMER","ACRM_F_CI_ORG","ACRM_F_CI_ORG_BUSIINFO","ACRM_F_CI_ORG_REGISTERINFO","ACRM_F_CI_ADDRESS0","ACRM_F_CI_ADDRESS1", "ACRM_F_CI_ORG_EXECUTIVEINFO1",
				      "ACRM_F_CI_CUST_IDENTIFIER","ACRM_F_CI_CUST_IDENTIFIER1","ACRM_F_CI_CUST_IDENTIFIER2","ACRM_F_CI_CUST_IDENTIFIER3","ACRM_F_CI_CUST_IDENTIFIER4","ACRM_F_CI_CUST_IDENTIFIER5",
				      "ACRM_F_CI_ORG_KEYFLAG","ACRM_F_CI_ORG_ISSUESTOCK",
//				      "OCRM_F_CI_CUST_SCIENCE",
				      "ACRM_F_CI_ADDRESS","ACRM_F_CI_ORG_EXECUTIVEINFO","ACRM_F_CI_CONTMETH",
                      "ACRM_F_CI_AGENTINFO",
                      "OCRM_F_CI_BELONG_CUSTMGR","OCRM_F_CI_BELONG_ORG","ACRM_F_CI_SPECIALLIST","ACRM_F_CI_PER_KEYFLAG"};
		for(String strs:str){
			if(tableName!=null && strs.equals(tableName.trim())){
				return true;
			}
		}
		return false;	
	}
	/**
	 * 对表名字做映射
	 * @param tableName
	 * @return
	 */
//	public String returnTableName(String tableName){
//		if(tableName.equals("ACRM_F_CI_CUSTOMER")){
//			tableName="customer";
//		}else if(tableName.equals("ACRM_F_CI_ADDRESS0")) {
//			tableName="address";
//		}else if(tableName.equals("ACRM_F_CI_ADDRESS1")) {
//			tableName="address";
//		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER")) {
//			tableName="identifier";
//		}else if(tableName.equals("ACRM_F_CI_CONTMETH")) {
//			tableName="contmeth";
//		}else if(tableName.equals("ACRM_F_CI_PERSON")) {
//			tableName="person";
//		}else if(tableName.equals("ACRM_F_CI_ORG")) {
//			tableName="org";
//		}else if(tableName.equals("OCRM_F_CI_BELONG_ORG")) {
//			tableName="belongBranch";
//		}else if(tableName.equals("OCRM_F_CI_BELONG_CUSTMGR")) {
//			tableName="belongManager";
//		}else if(tableName.equals("ACRM_F_CI_SPECIALLIST")) {
//			tableName="speciallist";
//		}else if(tableName.equals("ACRM_F_CI_PER_KEYFLAG")) {
//			tableName="perKeyFlag";
//		}
//		return tableName;
//	}
	/**
	 * 对表名字做映射
	 * @param tableName
	 * @return
	 */
	public String returnTableName(String tableName){
		if(tableName.equals("ACRM_F_CI_CUSTOMER")){//客户表
			tableName="customer";
		}else if(tableName.equals("ACRM_F_CI_ORG")) {//机构表
			tableName="org";
		}else if(tableName.equals("ACRM_F_CI_ORG_BUSIINFO")) {//机构经营信息
			tableName="busiinfo";
		}else if(tableName.equals("ACRM_F_CI_ORG_REGISTERINFO")) {//注册信息
			tableName="registerinfo";
		}else if(tableName.equals("ACRM_F_CI_ADDRESS0")) {//地址信息
			tableName="address";
		}else if(tableName.equals("ACRM_F_CI_ADDRESS1")) {
			tableName="address";
		}else if(tableName.equals("ACRM_F_CI_ORG_EXECUTIVEINFO1")) {//机构干系人
			tableName="executive";
		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER")) {//证件信息
			tableName="identifier";
//		}else if(tableName.equals("OCRM_F_CI_GROUP_MEMBER_NEW")) {//集团信息
//			tableName="member";
		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER1")) {
			tableName="identifier";
		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER2")) {
			tableName="identifier";
		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER3")) {
			tableName="identifier";
		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER4")) {
			tableName="identifier";
		}else if(tableName.equals("ACRM_F_CI_CUST_IDENTIFIER5")) {
			tableName="identifier";
		}else if(tableName.equals("ACRM_F_CI_ORG_KEYFLAG")) {//机构客户重要标志
			tableName="keyflag";
		}else if(tableName.equals("ACRM_F_CI_ORG_ISSUESTOCK")) {//发行股票信息
			tableName="issuestock";
//		}else if(tableName.equals("OCRM_F_CI_CUST_SCIENCE")) {//科技型企业信息
//			tableName="science";
		}else if(tableName.equals("ACRM_F_CI_ADDRESS")) {
			tableName="address";
		}else if(tableName.equals("ACRM_F_CI_ORG_EXECUTIVEINFO")) {
			tableName="executive";
		}else if(tableName.equals("ACRM_F_CI_CONTMETH")) {//联系信息
			tableName="contmeth";
		}else if(tableName.equals("ACRM_F_CI_AGENTINFO")) {//代理人信息
			tableName="agentinfo";
		}else if(tableName.equals("ACRM_F_CI_PERSON")) {//个人客户信息
			tableName="person";
		}else if(tableName.equals("OCRM_F_CI_BELONG_ORG")) {//归属机构信息
			tableName="belongBranch";
		}else if(tableName.equals("OCRM_F_CI_BELONG_CUSTMGR")) {//归属客户经理信息
			tableName="belongManager";
		}else if(tableName.equals("ACRM_F_CI_SPECIALLIST")) {//特殊名单
			tableName="speciallist";
		}else if(tableName.equals("ACRM_F_CI_PER_KEYFLAG")) {//个人客户重要标志
			tableName="perKeyFlag";
		}
		return tableName;
	}
	
	/**
     * 替换XML字符串中特殊字符
     */
	public String encodeString(String oldstr){
        if (oldstr == null){
            return "";
        }
        oldstr = replaceString(oldstr, "&", "&amp;");
        oldstr = replaceString(oldstr, "<", "&lt;");
        oldstr = replaceString(oldstr, ">", "&gt;");
        oldstr = replaceString(oldstr, "'", "&apos;");
        oldstr = replaceString(oldstr, "\"", "&quot;");
        return oldstr;
    }
	
	/** 
	 * 替换一个字符串中的某些指定字符 
	 * @param oldstr 原始字符串 
	 * @param oldChar 要替换的字符串 
	 * @param newChar 替代字符串 
	 * @return 替换后的字符串 
	 */  
	public String replaceString(String oldstr, String oldChar, String newChar){
		if (oldstr == null) {
	         return null;
	    }
	    int index  = oldstr.indexOf(oldChar);
	    String newstr = "";
	    if (index > -1){
	        while (index > -1){
	        	newstr += oldstr.substring(0, index) + newChar;
	            oldstr = oldstr.substring(index + oldChar.length());
	            index = oldstr.indexOf(oldChar);
	        }
	        newstr += oldstr;
	        return newstr;
	    }
	    return oldstr;  
	}
	
	/**
	 * @deprecated 不建议使用此方法,每次循环去查询一次数据库效率很慢
	 * @param update_item_en
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getColunmName(String update_item_en,EVO vo) throws Exception{
		try{
	       SQL="select origin_column from review_mapping where page_column='"+update_item_en+"'";
	       Result result=querySQL(vo);
	       List<Map<Object,Object>> list =new ArrayList<Map<Object,Object>>();
			for (SortedMap item : result.getRows()){
				list.add(item);
			}
			if(list!=null && list.size()>0){
		    	Map map = list.get(0);
		    	String colunmName = (String) map.get("origin_column");
		    	return colunmName;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 转换字段类型
	 * @param colunm
	 * @return
	 * @throws Exception
	 */
	public String changeColunm(String colunm) throws Exception{
		if(colunm!=null && !colunm.trim().equals("")){
			 String newColunm = colunm.toLowerCase();
			 if(newColunm.contains("_")){
			    StringBuffer sb = new StringBuffer();
			    String[] str = newColunm.split("_");
			    for(int i=0;i<str.length;i++){
			    	String colunmSplit = str[i];
			    	if(i==0){
			    		sb.append(colunmSplit);	
			    	}else{
			    		char[] cs=colunmSplit.toCharArray();
			 	        cs[0]-=32;
			 	        sb.append(String.valueOf(cs));
			    	}
			    }
			    return sb.toString();
			 }else{
				 return newColunm;
			 }
		}
		return null;
	}
	

	
}
