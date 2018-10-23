package com.yuchengtech.emp.ecif.custviewtools.service;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class MakeBSFile {

	public String make(Document doc,String entityname){
		
		Element e = MakeUtils.getElementByEntity(doc,entityname);

		String bizName = e.getParent().attributeValue("BizName");		//上级节点的bizName
				
		StringBuffer sb = new StringBuffer("");
		sb.append("");
		
		sb.append("package ").append(MakeUtils.getServicePackage(doc)).append(";");sb.append("\r\n");
		sb.append("import java.util.ArrayList;");sb.append("\r\n");
		sb.append("import java.util.List;");sb.append("\r\n");
		sb.append("import java.util.Map;");sb.append("\r\n");

		sb.append("import org.apache.commons.lang3.StringUtils;");sb.append("\r\n");
		sb.append("import org.springframework.stereotype.Service;");sb.append("\r\n");
		sb.append("import org.springframework.transaction.annotation.Transactional;");sb.append("\r\n");

		sb.append("import com.google.common.collect.Lists;");sb.append("\r\n");
		sb.append("import com.google.common.collect.Maps;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.biappframe.base.service.BaseBS;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.bione.dao.SearchResult;");sb.append("\r\n");

		//导入实体类
		sb.append("import ").append(MakeUtils.getEntityPackage(doc, bizName.toLowerCase())).append(".").append(entityname).append(";");sb.append("\r\n");

		sb.append("@Service");sb.append("\r\n");
		sb.append("@Transactional(readOnly = true)");sb.append("\r\n");
		sb.append("public class "+entityname+"BS extends BaseBS<Object> {");sb.append("\r\n");

		Element root = doc.getRootElement();
		Node pnode = root.selectSingleNode("CustMenu1[@BizName='"+bizName +"']");sb.append("\r\n");
		Element o = (Element)pnode.selectSingleNode("CustMenu2[@Entityname='"+entityname +"']");sb.append("\r\n");
		
		//对每个实体获取数据，列表或表单
    	String show = o.attributeValue("show");
    	String packagename = MakeUtils.getEntityPackage(doc, bizName.toLowerCase());
    	String fullentityname = packagename +"." + entityname;
    	
    	if(show==null||show.equals("")||show.equals("list")){
    		sb.append("@SuppressWarnings(\"unchecked\")");sb.append("\r\n");
    		sb.append("public SearchResult<"+entityname+"> get"+entityname+"List(int firstResult,");sb.append("\r\n");
    		sb.append("		int pageSize, String orderBy, String orderType,");sb.append("\r\n");
    		sb.append("		Map<String, Object> conditionMap,long custId) {");sb.append("\r\n");
    		sb.append("	StringBuffer jql = new StringBuffer(\"\");");sb.append("\r\n");
    		sb.append("	jql.append(\"select "+entityname+" from "+fullentityname+" "+entityname+" where 1=1\");");sb.append("\r\n");
    		sb.append("	if (!conditionMap.get(\"jql\").equals(\"\")) {");sb.append("\r\n");
    		sb.append("		jql.append(\" and \" + conditionMap.get(\"jql\"));");sb.append("\r\n");
    		sb.append("	}");sb.append("\r\n");
    		sb.append("	if(!\"\".equals(custId)){");sb.append("\r\n");
    		sb.append("		jql.append(\" and "+entityname+".custId = \" + custId + \"\");");sb.append("\r\n");
    		sb.append("	}");sb.append("\r\n");
    		sb.append("	if (!StringUtils.isEmpty(orderBy)) {");sb.append("\r\n");
    		sb.append("		jql.append(\" order by "+entityname+".\" + orderBy + \" \" + orderType);");sb.append("\r\n");
    		sb.append("	}");sb.append("\r\n");
    		sb.append("	Map<String, ?> values = (Map<String, ?>) conditionMap.get(\"params\");");sb.append("\r\n");
    		sb.append("	return this.baseDAO.findPageWithNameParam(firstResult, pageSize,");sb.append("\r\n");
    		sb.append("			jql.toString(), values);");sb.append("\r\n");
    		sb.append("}");sb.append("\r\n");
    		
    	}
    	
    	//BaseBS此方法有问题，在自己的BS加入这个方法
    	sb.append("@SuppressWarnings({ \"unchecked\", \"rawtypes\" })");sb.append("\r\n");
    	sb.append("public <X> X getEntityByProperty(final Class entityClass, String propertyName, Object value) {");sb.append("\r\n");
    	sb.append("	String jql = \"select obj from \" + entityClass.getName() ");sb.append("\r\n");
    	sb.append("			+ \" obj where obj.\" + propertyName + \"=?0\";");sb.append("\r\n");
    	sb.append("	return (X) this.baseDAO.findUniqueWithIndexParam(jql, value);");sb.append("\r\n");
    	sb.append("}");sb.append("\r\n");
   	
		sb.append("}");sb.append("\r\n");
	    
		return sb.toString();

	}
	
}