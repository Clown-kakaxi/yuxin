package com.yuchengtech.bcrm.customer.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiBelongHist;
import com.yuchengtech.bcrm.customer.service.CustomerTransService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户移交历史
 */
@SuppressWarnings("serial")
@Action("/customer_trans_hist")
public class CustomerTransHistAction extends CommonAction {
	
	@Autowired
	private CustomerTransService customerTransService;

	@Autowired
	public void init() {
		model = new OcrmFCiBelongHist();
		setCommonService(customerTransService);
		// 新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog = false;;
	}

	/**
	 * 通过获得客户编号查询客户归属机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public HttpHeaders indexPage() throws Exception {
		try {
			StringBuilder sb = new StringBuilder("select bo from OcrmFCiBelongHist bo where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			if(request.getParameter("start")!=null)
	        	start = new Integer(request.getParameter("start")).intValue();
	        if(request.getParameter("limit")!=null)
	        	limit = new Integer(request.getParameter("limit")).intValue();
			this.setJson(request.getParameter("condition"));
	    	for(String key:this.getJson().keySet()){
	            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
	                if(key.equals("custId")){
	                    sb.append("and bo.custId like :custId");
	                    values.put("custId", "%"+(String)this.getJson().get(key)+"%");
	                }
	                else{
	                	sb.append(" and bo."+key+" = :"+key);
	                	values.put(key, Long.parseLong((String) this.getJson().get(key)));
	                	
	                }
	            }
	        }
			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}