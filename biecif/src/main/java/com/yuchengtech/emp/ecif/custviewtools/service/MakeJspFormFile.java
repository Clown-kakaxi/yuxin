package com.yuchengtech.emp.ecif.custviewtools.service;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.yuchengtech.emp.ecif.core.entity.ColDefVO;
import com.yuchengtech.emp.utils.SpringContextHolder;

public class MakeJspFormFile {

	public String make(Document doc,String entityname){
		
		Element e = MakeUtils.getElementByEntity(doc,entityname);
		
		String bizName = e.getParent().attributeValue("BizName");		//上级节点的bizName
		String menuName = e.getParent().attributeValue("MenuName");		//上级节点的MenuName
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
		sb.append("<meta name=\"decorator\" content=\"/template/template14.jsp\">");sb.append("\r\n");
		sb.append("<script type=\"text/javascript\">");sb.append("\r\n");
		sb.append("	var groupicon = \"${ctx}/images/classics/icons/communication.gif\";");sb.append("\r\n");
		sb.append("	var mainform;");sb.append("\r\n");
		sb.append("    var field = [ {");sb.append("\r\n");
		sb.append("		display : \""+ firstvo.getColChName() +"\",");sb.append("\r\n");
		sb.append("		name : \""+ MakeEntityFile.convert2JavaFieldName(firstvo.getColName()) +"\",");sb.append("\r\n");
		sb.append("		newline : true,");sb.append("\r\n");
		sb.append("		type : \"text\",");sb.append("\r\n");
		sb.append("		group : \""+ menuName +"\",");sb.append("\r\n");
		sb.append("		groupicon : groupicon");sb.append("\r\n");
		sb.append("	}");
		
		for(int i=1;i<list.size();i++){
			
			ColDefVO vo = list.get(i);
			
			sb.append("	, {");sb.append("\r\n");
			sb.append("		display : \""+ vo.getColChName() +"\",");sb.append("\r\n");
			sb.append("		name : \""+ MakeEntityFile.convert2JavaFieldName(vo.getColName()) +"\",");sb.append("\r\n");
			if( i % MakeConstants.COL_EACH_ROW==0)	{
				sb.append("		newline : true,");sb.append("\r\n");
			}else{
				sb.append("		newline : false,");sb.append("\r\n");
			}
			sb.append("		type : \"text\"");sb.append("\r\n");
			sb.append("	}");sb.append("\r\n");
		}
		sb.append("     ];");sb.append("\r\n");
		sb.append("	//创建表单结构 ");sb.append("\r\n");
		sb.append("	function ligerFormNow() {");sb.append("\r\n");
		sb.append("		mainform = $(\"#mainform\").ligerForm({");sb.append("\r\n");
		sb.append("			     inputWidth : 150,");sb.append("\r\n");
		sb.append("			    labelWidth : 110,");sb.append("\r\n");
		sb.append("			    space : 30,");sb.append("\r\n");
		sb.append("			    fields : field");sb.append("\r\n");
		sb.append("		});");sb.append("\r\n");
		sb.append("		jQuery.metadata.setType(\"attr\", \"validate\");");sb.append("\r\n");
		sb.append("		BIONE.validate($(\"#mainform\"));");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");
		sb.append("	$(function(){");sb.append("\r\n");
		sb.append("		ligerFormNow();");sb.append("\r\n");
		sb.append("		if (\"${custId}\") {");sb.append("\r\n");
		sb.append("			BIONE.loadForm(mainform, {");sb.append("\r\n");
		sb.append("				url : \"${ctx}/ecif/"+bizName.toLowerCase()+"/"+entityname.toLowerCase() +"/${custId}\"");sb.append("\r\n");
		sb.append("			});");sb.append("\r\n");
		sb.append("			$(\":input\", $(\"#mainform\")).not(\":submit, :reset, :image,:button, [disabled]\")");sb.append("\r\n");
		sb.append("			.each(");sb.append("\r\n");
		sb.append("				function() {");sb.append("\r\n");
		sb.append("					$(this).attr(\"readonly\",\"readonly\");");sb.append("\r\n");
		sb.append("					$(this).css({color:\"#000000\"});");sb.append("\r\n");
		sb.append("				}");sb.append("\r\n");
		sb.append("			);");sb.append("\r\n");
		sb.append("		}");sb.append("\r\n");
		sb.append("	});");sb.append("\r\n");
			

		sb.append("</script>");sb.append("\r\n");

		sb.append("<title>"+menuName+"</title>");sb.append("\r\n");
		sb.append("</head>");sb.append("\r\n");
		sb.append("<body>");sb.append("\r\n");
		sb.append("<div id=\"template.center\">");sb.append("\r\n");
		sb.append("	<form name=\"mainform\" method=\"post\" id=\"mainform\" action=\"${ctx}/ecif/"+bizName.toLowerCase()+"\">");sb.append("\r\n");
		sb.append("	</form>");sb.append("\r\n");
		sb.append("</div>");sb.append("\r\n");
		sb.append("</body>");sb.append("\r\n");
		sb.append("</html>");sb.append("\r\n");
		
		return sb.toString();
	}
	
	
}