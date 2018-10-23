package com.yuchengtech.bcrm.notice.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.notice.service.OcrmSysRicheditInfoNoticeService;
import com.yuchengtech.bcrm.system.model.OcrmSysRicheditInfo;
import com.yuchengtech.bob.common.CommonAction;

/***
 * @author xiebz
 *
 */
@ParentPackage("json-default")
@SuppressWarnings("serial")
@Action("/ocrmSysRicheditInfoTemp")

public class OcrmSysRicheditInfoNoticeAction extends CommonAction {
	@Autowired
	private OcrmSysRicheditInfoNoticeService ocrmSysRicheditInfoService;
	
	@Autowired
	public void init(){
		model = new OcrmSysRicheditInfo();
		setCommonService(ocrmSysRicheditInfoService);
	}
	private String htmlspecialchars(String str) {
//		str = str.replaceAll("&", "&amp;");
//		str = str.replaceAll("<", "&lt;");
//		str = str.replaceAll(">", "&gt;");
//		str = str.replaceAll("\"", "&quot;");
		return str;
	}
	/***
	 * 保存文档内容信息
	 */
	public DefaultHttpHeaders create() {
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//        String wordId = request.getParameter("wordId");
        Map<String,Object> values=new HashMap<String,Object>();
        String relId = request.getParameter("relId");
        StringBuffer jql= new StringBuffer("select c from OcrmSysRicheditInfo c where 1=1 and c.relId ='"+relId+"'");
        String content = request.getParameter("content");
        OcrmSysRicheditInfo m = new OcrmSysRicheditInfo();
        List data=ocrmSysRicheditInfoService.findByJql(jql.toString(), values);//如果存在则修改
        if(data.size()>0){
        	m=(OcrmSysRicheditInfo)data.get(0);
        }
        if(m.getId()==null){//新增
        	m.setRelId(Long.parseLong(relId));
    		m.setContent(content);        	
        }else{//修改
        	m.setContent(htmlspecialchars(content));
        }
//        	if("".equals(wordId)||wordId == null){
//        		m.setRelId(Long.parseLong(relId));
//        		m.setContent(content);
//        	}else{
//        		m = (OcrmSysRicheditInfo)ocrmSysRicheditInfoService.find(Long.parseLong(wordId));
//        		m.setContent(htmlspecialchars(content));
//        	}
        	ocrmSysRicheditInfoService.save(m);
    	return new DefaultHttpHeaders("success");
    }
	
	//查询某组件的文档信息
	public HttpHeaders indexPage() throws Exception{
		try {
			
			StringBuilder sb=new StringBuilder("select c from OcrmSysRicheditInfo c where 1=1 ");
	    	Map<String,Object> values=new HashMap<String,Object>();
	    	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        if(request.getParameter("start")!=null)
	        	start = new Integer(request.getParameter("start")).intValue();
	        if(request.getParameter("limit")!=null)
	        	limit = new Integer(request.getParameter("limit")).intValue();
			//获取示例组件ID
			if(request.getParameter("relId")!=null){
				sb.append(" and c.relId = '"+request.getParameter("relId")+"'");
			}
			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
	    	  throw e;
		}
	}
	//查询某组件的文档信息
	public HttpHeaders indexPageTemp() throws Exception{
		try {
			
			StringBuilder sb=new StringBuilder("select c from OcrmSysRicheditInfoTemp c where 1=1 ");
	    	Map<String,Object> values=new HashMap<String,Object>();
	    	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        if(request.getParameter("start")!=null)
	        	start = new Integer(request.getParameter("start")).intValue();
	        if(request.getParameter("limit")!=null)
	        	limit = new Integer(request.getParameter("limit")).intValue();
			//获取示例组件ID
			if(request.getParameter("relId")!=null){
				sb.append(" and c.relId = '"+request.getParameter("relId")+"'");
			}
			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
	    	  throw e;
		}
	}
}
