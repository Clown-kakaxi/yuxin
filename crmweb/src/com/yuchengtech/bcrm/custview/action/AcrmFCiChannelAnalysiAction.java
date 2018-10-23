package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiChannelAnalysi;
import com.yuchengtech.bcrm.custview.service.AcrmFCiChannelAnalysiService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/AcrmFCiChannelAnalysi")
public class AcrmFCiChannelAnalysiAction extends CommonAction {
	@Autowired
	private AcrmFCiChannelAnalysiService acrmFCiChannelAnalysiService;
	@Autowired
	public void init(){
		model = new AcrmFCiChannelAnalysi();
		setCommonService(acrmFCiChannelAnalysiService);
	}
	
	//分页查询
	public HttpHeaders indexPage() throws Exception{
		try {
			StringBuilder sb=new StringBuilder("select c from AcrmFCiChannelAnalysi c where 1=1 ");
	    	Map<String,Object> values=new HashMap<String,Object>();
	    	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        if(request.getParameter("start")!=null)
	        	start = new Integer(request.getParameter("start")).intValue();
	        if(request.getParameter("limit")!=null)
	        	limit = new Integer(request.getParameter("limit")).intValue();
			this.setJson(request.getParameter("condition"));
			
			//获取客户编号
			if(request.getParameter("cust_id")!=null){
				sb.append(" and c.custId = '" + request.getParameter("cust_id")+"'");
			}
			
			for(String key:this.getJson().keySet()){
				if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
					sb.append(" and c."+key+" = :"+key);
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
