package com.yuchengtech.emp.ecif.custviewtools.service;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.yuchengtech.emp.ecif.core.entity.ColDefVO;
import com.yuchengtech.emp.utils.SpringContextHolder;

public class MakeJspListFile {

	public String make(Document doc,String entityname){
		
		Element e = MakeUtils.getElementByEntity(doc,entityname);

		String bizName = e.getParent().attributeValue("BizName");		//上级节点的bizName
		String tabName = e.attributeValue("tabName");					//本级节点的tabName
		
		DBHelperBS bs = SpringContextHolder.getBean(DBHelperBS.class);
		String schema = MakeUtils.getDefautSchema(doc);
		List<ColDefVO> list = bs.getColList(schema,tabName);
		
		StringBuffer sb = new StringBuffer("");
		sb.append("");

		for(int i=list.size()-1;i>=0;i--){
			
			ColDefVO vo = list.get(i);
			
			//对主键和客户号不显示
			String flag = "0";
			if(vo.getKeyType()!=null&&vo.getKeyType().equals("Primary")){
				flag = "1";
			}
			if(vo.getColName().toUpperCase().equals("CUST_ID")){
				flag = "1";
			}			
			
			if(flag.equals("1"))
				list.remove(i);
		}			
		
		ColDefVO firstvo = list.get(0);
		
		sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"");sb.append("\r\n");
		sb.append("	pageEncoding=\"UTF-8\"%>");sb.append("\r\n");
		sb.append("<%@ include file=\"/common/taglibs.jsp\"%>");sb.append("\r\n");
		sb.append("<html>");sb.append("\r\n");
		sb.append("<head>");sb.append("\r\n");
		sb.append("<meta name=\"decorator\" content=\"/template/template18.jsp\">");sb.append("\r\n");
		sb.append("<script type=\"text/javascript\">");sb.append("\r\n");
		sb.append("	var dialog;");sb.append("\r\n");
		sb.append("	var custId = \"${custId}\";");sb.append("\r\n");
		sb.append("	url = \"${ctx}/ecif/"+bizName.toLowerCase()+"/"+entityname.toLowerCase() +"/list.json?custId=\" + custId;");sb.append("\r\n");
		sb.append("	$(function() {");sb.append("\r\n");


			
		sb.append("		grid = $(\"#maingrid\").ligerGrid({");sb.append("\r\n");
		sb.append("			columns : [ {");sb.append("\r\n");
		sb.append("				display : '"+ firstvo.getColChName() +"',");sb.append("\r\n");
		sb.append("				name : '"+ MakeEntityFile.convert2JavaFieldName(firstvo.getColName()) +"',");sb.append("\r\n");
		sb.append("				align : 'center',");sb.append("\r\n");
		sb.append("				width : 100,");sb.append("\r\n");
		sb.append("				minWidth : 80");sb.append("\r\n");
		sb.append("			}");
		
		for(int i=1;i<list.size();i++){
			
			ColDefVO vo = list.get(i);
			
			sb.append("	, {");;sb.append("\r\n");
			sb.append("			display : \""+ vo.getColChName() +"\",");sb.append("\r\n");
			sb.append("			name : \""+ MakeEntityFile.convert2JavaFieldName(vo.getColName()) +"\",");sb.append("\r\n");
			sb.append("			align : 'center',");sb.append("\r\n");
			sb.append("			width : 100,");sb.append("\r\n");
			sb.append("			minWidth : 80");sb.append("\r\n");
			sb.append("	}");sb.append("\r\n");
		}
		sb.append("	],");sb.append("\r\n");
			
		sb.append("			checkbox : false,");sb.append("\r\n");
		sb.append("			usePager : true,");sb.append("\r\n");
		sb.append("			isScroll : false,");sb.append("\r\n");
		sb.append("			rownumbers : true,");sb.append("\r\n");
		sb.append("			alternatingRow : true,//附加奇偶行效果行");sb.append("\r\n");
		sb.append("			colDraggable : true,");sb.append("\r\n");
		sb.append("			dataAction : 'server',//从后台获取数据");sb.append("\r\n");
		sb.append("			method : 'post',");sb.append("\r\n");
		sb.append("			url : url,");sb.append("\r\n");
		sb.append("			sortName : 'custId',//第一次默认排序的字段");sb.append("\r\n");
		sb.append("			sortOrder : 'asc', //排序的方式");sb.append("\r\n");
		sb.append("			pageParmName : 'page',");sb.append("\r\n");
		sb.append("			pagesizeParmName : 'pagesize',");sb.append("\r\n");
		sb.append("			toolbar : {}");sb.append("\r\n");
		sb.append("		});");sb.append("\r\n");

		sb.append("	});");sb.append("\r\n");
		sb.append("</script>");sb.append("\r\n");
		sb.append("</head>");sb.append("\r\n");
		sb.append("<body>");sb.append("\r\n");
		sb.append("</body>");sb.append("\r\n");
		sb.append("</html>");sb.append("\r\n");
		
		return sb.toString();
		
	}
	
	
}