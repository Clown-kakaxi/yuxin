package com.yuchengtech.emp.ecif.custviewtools.service;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class MakeLevel1BSFile {

	public String make(Document doc,String bizName){
		
		List<String> entitynames = new ArrayList<String>();
		
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("CustMenu1[@BizName='"+bizName +"']");
		List list = node.selectNodes("/CustMenu2");
	    for(Object o:list){
	    	Element e = (Element) o;
	    	String entityname = e.attributeValue("Entityname");
	    	entitynames.add(entityname);
	    }		
		
		StringBuffer sb = new StringBuffer("");
		sb.append("");
		
		sb.append("package ").append(MakeUtils.getServicePackage(doc)).append(";");
		sb.append("import java.util.ArrayList;");
		sb.append("import java.util.List;");
		sb.append("import java.util.Map;");

		sb.append("import org.apache.commons.lang3.StringUtils;");
		sb.append("import org.springframework.stereotype.Service;");
		sb.append("import org.springframework.transaction.annotation.Transactional;");

		sb.append("import com.google.common.collect.Lists;");
		sb.append("import com.google.common.collect.Maps;");
		sb.append("import com.yuchengtech.emp.biappframe.base.service.BaseBS;");
		sb.append("import com.yuchengtech.emp.bione.dao.SearchResult;");

		//导入实体类
		for (String entityname : entitynames){
			sb.append("import ").append(MakeUtils.getEntityPackage(doc, bizName)).append(entityname);
		}

		sb.append("@Service");
		sb.append("@Transactional(readOnly = true)");
		sb.append("public class "+bizName+"BS extends BaseBS<Object> {");

		//对二级菜单下的每个实体获取数据，列表或表单
	    for(Object o:list){
	    	Element e = (Element) o;
	    	String entityname = e.attributeValue("Entityname");
	    	String show = e.attributeValue("show");
	    	
	    	if(show==null||show.equals("")||show.equals("list")){
	    		sb.append("@SuppressWarnings(\"unchecked\")");
	    		sb.append("public SearchResult<"+entityname+"> get"+entityname+"List(int firstResult,");
	    		sb.append("		int pageSize, String orderBy, String orderType,");
	    		sb.append("		Map<String, Object> conditionMap,long custId) {");
	    		sb.append("	StringBuffer jql = new StringBuffer(\"\");");
	    		sb.append("	jql.append(\"select "+entityname+" from "+entityname+" "+entityname+" where 1=1\");");
	    		sb.append("	if (!conditionMap.get(\"jql\").equals(\"\")) {");
	    		sb.append("		jql.append(\" and \" + conditionMap.get(\"jql\"));");
	    		sb.append("	}");
	    		sb.append("	if(!\"\".equals(custId)){");
	    		sb.append("		jql.append(\" and "+entityname+".custId = \" + custId + \"\");");
	    		sb.append("	}");
	    		sb.append("	if (!StringUtils.isEmpty(orderBy)) {");
	    		sb.append("		jql.append(\" order by "+entityname+".\" + orderBy + \" \" + orderType);");
	    		sb.append("	}");
	    		sb.append("	Map<String, ?> values = (Map<String, ?>) conditionMap.get(\"params\");");
	    		sb.append("	return this.baseDAO.findPageWithNameParam(firstResult, pageSize,");
	    		sb.append("			jql.toString(), values);");
	    		sb.append("}");
	    		
	    	}
		}		
	    
		return sb.toString();

	}
	
}