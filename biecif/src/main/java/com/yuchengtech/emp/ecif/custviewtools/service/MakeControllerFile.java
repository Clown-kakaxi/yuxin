package com.yuchengtech.emp.ecif.custviewtools.service;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class MakeControllerFile {

	public String make(Document doc,String entityname){
		
		Element e = MakeUtils.getElementByEntity(doc,entityname);

		String bizName = e.getParent().attributeValue("BizName");		//上级节点的bizName
		
		StringBuffer sb = new StringBuffer("");
		sb.append("");
		
		sb.append("package ").append(MakeUtils.getWebPackage(doc)).append(";");sb.append("\r\n");
		sb.append("import java.util.List;");sb.append("\r\n");
		sb.append("import java.util.Map;");sb.append("\r\n");

		sb.append("import org.slf4j.Logger;");sb.append("\r\n");
		sb.append("import org.slf4j.LoggerFactory;");sb.append("\r\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;");sb.append("\r\n");
		sb.append("import org.springframework.stereotype.Controller;");sb.append("\r\n");
		sb.append("import org.springframework.web.bind.annotation.PathVariable;");sb.append("\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;");sb.append("\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMethod;");sb.append("\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestParam;");sb.append("\r\n");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;");sb.append("\r\n");
		sb.append("import org.springframework.web.servlet.ModelAndView;");sb.append("\r\n");

		sb.append("import com.google.common.collect.Maps;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.biappframe.base.web.BaseController;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.bione.dao.SearchResult;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.bione.entity.page.Pager;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.ecif.base.util.ResultUtil;");sb.append("\r\n");

		//导入实体类
		sb.append("import ").append(MakeUtils.getEntityPackage(doc, bizName)).append(".").append(entityname).append(";");sb.append("\r\n");

		sb.append("import ").append(MakeUtils.getServicePackage(doc)).append(".").append(entityname).append("BS;");sb.append("\r\n");
		
		sb.append("@Controller");sb.append("\r\n");
		sb.append("@RequestMapping(\"/ecif/"+bizName.toLowerCase()+"/"+ entityname.toLowerCase()+ "\")");	sb.append("\r\n");	// 例如/ecif/perbasic/personprofile
		sb.append("public class "+ entityname +"Controller extends BaseController {");sb.append("\r\n");

		sb.append("	@Autowired");sb.append("\r\n");
		sb.append("	private "+entityname+"BS "+entityname+"BS;");sb.append("\r\n");
			
		sb.append("	@Autowired");sb.append("\r\n");
		sb.append("	private ResultUtil resultUtil;");sb.append("\r\n");
			
		sb.append("	protected static Logger log = LoggerFactory");sb.append("\r\n");
		sb.append("			.getLogger("+entityname+"Controller.class);");sb.append("\r\n");

		sb.append("	@RequestMapping(value = \"/form\", method = RequestMethod.GET)");sb.append("\r\n");
		sb.append("	public ModelAndView edit(@RequestParam(\"custId\") String custId,");sb.append("\r\n");
		sb.append("			@RequestParam(\"URL\") String URL) {");sb.append("\r\n");
		sb.append("		return new ModelAndView(URL, \"custId\", custId);");sb.append("\r\n");
		sb.append("	}");sb.append("\r\n");

		Element root = doc.getRootElement();
		Node pnode = root.selectSingleNode("CustMenu1[@BizName='"+bizName +"']");sb.append("\r\n");
		Element o = (Element)pnode.selectSingleNode("CustMenu2[@Entityname='"+entityname +"']");sb.append("\r\n");
		
		//对每个实体获取数据，列表或表单
    	String show = o.attributeValue("show");
    	
    	if(show==null||show.equals("")||show.equals("list")){
			sb.append("	@RequestMapping(value = \"/list.*\", method = RequestMethod.POST)");sb.append("\r\n");
			sb.append("	@ResponseBody");sb.append("\r\n");
			sb.append("		public Map<String, Object> show"+entityname+"(Pager pager,@RequestParam(\"custId\") long custId) {");sb.append("\r\n");
			sb.append("			Map<String, Object> userMap = Maps.newHashMap();");sb.append("\r\n");
			sb.append("			SearchResult<"+entityname+"> searchResult = "+entityname+"BS.get"+entityname+"List(");sb.append("\r\n");
			sb.append("					pager.getPageFirstIndex(), pager.getPagesize(),");sb.append("\r\n");
			sb.append("					pager.getSortname(), pager.getSortorder(),");sb.append("\r\n");
			sb.append("					pager.getSearchCondition(),custId);");sb.append("\r\n");
			sb.append("			List<"+entityname+"> list = null;");sb.append("\r\n");
			sb.append("			try {");sb.append("\r\n");
			sb.append("				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), "+entityname+".class);");sb.append("\r\n");
			sb.append("			} catch (Exception e) {");sb.append("\r\n");
			sb.append("				e.printStackTrace();");sb.append("\r\n");
			sb.append("			}");sb.append("\r\n");
			sb.append("			userMap.put(\"Rows\", list);");sb.append("\r\n");
			sb.append("			userMap.put(\"Total\", searchResult.getTotalCount());");sb.append("\r\n");
			sb.append("			return userMap;");sb.append("\r\n");
			sb.append("		}");sb.append("\r\n");
    		
    	}else if(show.equals("form")){
    		String packagename = MakeUtils.getEntityPackage(doc, bizName.toLowerCase());
    		
			sb.append("	@RequestMapping(value = \"/{custId}\", method = RequestMethod.GET)");sb.append("\r\n");
			sb.append("	@ResponseBody");sb.append("\r\n");
			sb.append("	public "+entityname+" show"+entityname+"(@PathVariable(\"custId\") long custId) {");sb.append("\r\n");
					
			sb.append("		"+entityname+" model = "+entityname+"BS.getEntityByProperty("+ packagename+"."+ entityname+".class, \"custId\",custId);");sb.append("\r\n");
			sb.append("		try {");sb.append("\r\n");
			sb.append("			model = resultUtil.jpaBeanDictTran(model);");sb.append("\r\n");
			sb.append("		} catch (Exception e) {");sb.append("\r\n");
			sb.append("			e.printStackTrace();");sb.append("\r\n");
			sb.append("		}");sb.append("\r\n");
			sb.append("		return model;");sb.append("\r\n");
			sb.append("	}");sb.append("\r\n");
    	}
		sb.append("	}");sb.append("\r\n");
	    
		return sb.toString();

	}
	
}