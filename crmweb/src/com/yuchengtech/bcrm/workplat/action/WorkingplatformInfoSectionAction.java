package com.yuchengtech.bcrm.workplat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpInfoSection;
import com.yuchengtech.bcrm.workplat.service.WorkingplatformInfoSection_Service;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 
 * @author huwei
 * 资源模块改造
 *
 */
@SuppressWarnings("serial")
@Action("/workplatforminfosection")
public class WorkingplatformInfoSectionAction extends CommonAction {

	@Autowired
	private WorkingplatformInfoSection_Service workPFISectionService;
	@Autowired
	public void init() {
		model = new OcrmFWpInfoSection();
		setCommonService(workPFISectionService);
		// 新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog = false;;
	}
	
	/*删除节点*/
	public String batchDestroy(){
    	   	ActionContext ctx = ActionContext.getContext();
    	   	Map<String,Object> values=new HashMap<String,Object>();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			long idStr = Long.parseLong((request.getParameter("sectionId")));
			String jql = "";
			String jql1 = "";
			StringBuffer bf  = new StringBuffer();
			String[] strings = String.valueOf(idStr).split(",");
			for(String s : strings){
				bf.append(",'");
				bf.append(s);
				bf.append("'");
			}
			jql = "delete from OcrmFWpInfo o where o.messageType in ("+bf.substring(1)+")";//删除节点内容
			//删除节点以及子节点
			jql1= "delete from OcrmFWpInfoSection c where (c.sectionId in ("+idStr+") or c.sectionId " +
					" in (SELECT T.sectionId FROM OcrmFWpInfoSection T WHERE T.parentSection =("+idStr+"))) ";
			workPFISectionService.batchUpdateByName(jql, values);
			workPFISectionService.batchUpdateByName(jql1, values);
			addActionMessage("batch removed successfully");
	        return "success";
    }
	
	//更新左的树形结构
	public String update_new() throws Exception{
		try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			long idStr = Long.parseLong((request.getParameter("sectionId")));
			String sectionName = request.getParameter("sectionName");
			String parentSection = request.getParameter("parentSection");
			String sectionSummary = request.getParameter("sectionSummary");
			String sectionCategory = request.getParameter("sectionCategory");
			String jql="update  OcrmFWpInfoSection c  set c.sectionName ='"+sectionName+"', c.sectionSummary='"+sectionSummary+"' ,c.parentSection='"+parentSection+"',c.sectionCategory = '"+sectionCategory+"' where c.sectionId in ("+idStr+")";
			Map<String,Object> values=new HashMap<String,Object>();
			workPFISectionService.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
	        return "success";
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
	}
	
	public HttpHeaders indexPage() throws Exception {
		try {
			StringBuilder sb = new StringBuilder("select c from OcrmFWpInfoSection c where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			// if(request.getParameter("start")!=null)
			// start = new Integer(request.getParameter("start")).intValue();
			// if(request.getParameter("limit")!=null)
			// limit = new Integer(request.getParameter("limit")).intValue();
			this.setJson(request.getParameter("condition"));
			// 获取栏目ID
			if (request.getParameter("sectionId") != null) {
				sb.append(" and c.sectionId = " + Long.parseLong(request.getParameter("sectionId")));
			}
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					sb.append(" and c." + key + " = :" + key);
					values.put(key, this.getJson().get(key));
				}
			}
			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
