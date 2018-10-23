package com.yuchengtech.emp.ecif.custviewtools.service;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class MakeLevel1ControllerFile {

	public String make(Document doc,String bizName){
		
		List<String> entitynames = new ArrayList<String>();
		
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("/Body/CustMenu1[@BizName='"+bizName +"']");
		List list = node.selectNodes("/CustMenu2");
	    for(Object o:list){
	    	Element e = (Element) o;
	    	String entityname = e.attributeValue("Entityname");
	    	entitynames.add(entityname);
	    }		
		
		StringBuffer sb = new StringBuffer("");
		sb.append("");
		
		sb.append("package ").append(MakeUtils.getWebPackage(doc)).append(";");
		sb.append("import java.util.List;");
		sb.append("import java.util.Map;");

		sb.append("import org.slf4j.Logger;");
		sb.append("import org.slf4j.LoggerFactory;");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;");
		sb.append("import org.springframework.stereotype.Controller;");
		sb.append("import org.springframework.web.bind.annotation.PathVariable;");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;");
		sb.append("import org.springframework.web.bind.annotation.RequestMethod;");
		sb.append("import org.springframework.web.bind.annotation.RequestParam;");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;");
		sb.append("import org.springframework.web.servlet.ModelAndView;");

		sb.append("import com.google.common.collect.Maps;");
		sb.append("import com.yuchengtech.emp.biappframe.base.web.BaseController;");
		sb.append("import com.yuchengtech.emp.bione.dao.SearchResult;");
		sb.append("import com.yuchengtech.emp.bione.entity.page.Pager;");
		sb.append("import com.yuchengtech.emp.ecif.base.util.ResultUtil;");

		//导入实体类
		for (String entityname : entitynames){
			sb.append("import ").append(MakeUtils.getEntityPackage(doc, bizName)).append(entityname).append(";");
		}

		sb.append("import ").append(MakeUtils.getServicePackage(doc)).append(".").append(bizName).append("BS;");
		
		sb.append("@Controller");
		sb.append("@RequestMapping(\"/ecif/"+bizName+"\")");
		sb.append("public class "+bizName+"Controller extends BaseController {");

		sb.append("	@Autowired");
		sb.append("	private "+bizName+"BS "+bizName+"BS;");
			
		sb.append("	@Autowired");
		sb.append("	private ResultUtil resultUtil;");
			
		sb.append("	protected static Logger log = LoggerFactory");
		sb.append("			.getLogger("+bizName+"Controller.class);");

		sb.append("	@RequestMapping(value = \"/form\", method = RequestMethod.GET)");
		sb.append("	public ModelAndView edit(@RequestParam(\"custId\") String custId,");
		sb.append("			@RequestParam(\"URL\") String URL) {");
		sb.append("		return new ModelAndView(URL, \"custId\", custId);");
		sb.append("	}");

		//对二级菜单下的每个实体获取数据，列表或表单
	    for(Object o:list){
	    	Element e = (Element) o;
	    	String entityname = e.attributeValue("Entityname");
	    	String show = e.attributeValue("show");
	    	
	    	if(show==null||show.equals("")||show.equals("list")){
				sb.append("	@RequestMapping(value = \"/"+entityname.toLowerCase()+"/list.*\", method = RequestMethod.POST)");
				sb.append("	@ResponseBody");
				sb.append("		public Map<String, Object> show"+entityname+"(Pager pager,@RequestParam(\"custId\") long custId) {");
				sb.append("			Map<String, Object> userMap = Maps.newHashMap();");
				sb.append("			SearchResult<"+entityname+"> searchResult = "+bizName+"BS.get"+entityname+"List(");
				sb.append("					pager.getPageFirstIndex(), pager.getPagesize(),");
				sb.append("					pager.getSortname(), pager.getSortorder(),");
				sb.append("					pager.getSearchCondition(),custId);");
				sb.append("			List<"+entityname+"> list = null;");
				sb.append("			try {");
				sb.append("				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), "+entityname+".class);");
				sb.append("			} catch (Exception e) {");
				sb.append("				e.printStackTrace();");
				sb.append("			}");
				sb.append("			userMap.put(\"Rows\", list);");
				sb.append("			userMap.put(\"Total\", searchResult.getTotalCount());");
				sb.append("			return userMap;");
				sb.append("		}");
	    		
	    	}else if(show.equals("form")){
	    		
				sb.append("	@RequestMapping(value = \"/"+entityname.toLowerCase()+"/{custId}\", method = RequestMethod.GET)");
				sb.append("	@ResponseBody");
				sb.append("	public "+entityname+" show"+entityname+"(@PathVariable(\"custId\") long custId) {");
						
				sb.append("		"+entityname+" model = "+bizName+"BS.getEntityById("+entityname+".class, custId);");
				sb.append("		try {");
				sb.append("			model = resultUtil.jpaBeanDictTran(model);");
				sb.append("		} catch (Exception e) {");
				sb.append("			e.printStackTrace();");
				sb.append("		}");
				sb.append("		return model;");
				sb.append("	}");
	    	}
		}				
	    
		return sb.toString();

	}
	
}