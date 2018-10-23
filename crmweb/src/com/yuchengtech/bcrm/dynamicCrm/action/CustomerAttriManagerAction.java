package com.yuchengtech.bcrm.dynamicCrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriScore;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerAttriManagerService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;

/**
 * 客户属性指标维护
 * @author 亮
 *
 */
@ParentPackage("json-default")
@Action("/customerAttriManager")
@SuppressWarnings("serial")
public class CustomerAttriManagerAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerAttriManagerService service;

	@Autowired
	public void init(){
		model = new CustomerAttriScore();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		StringBuilder sb = new StringBuilder("");
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	
		sb.append(" select t.*, c.ATTRI_NAME, i.INDEX_VALUE_NAME from OCRM_F_CI_CUST_ATTRI_SCORE t ");
		sb.append(" left join OCRM_F_CI_CUST_ATTRI_CONF c on c.ATTRI_ID=t.ATTRI_ID ");
		sb.append(" left join OCRM_F_CI_CUST_ATTRI_ITEM i on i.ATTRI_ID=c.ATTRI_ID and i.INDEX_VALUE=t.INDEX_VALUE ");
		sb.append(" where 1=1 ");

		String attriId = "";
    	for(String key : this.getJson().keySet()){
    		if(null != this.getJson().get(key) && !this.getJson().get(key).equals("")){
    			if(null != key && key.equals("attriId")){
    				attriId = this.getJson().get(key).toString();
    			}
    		}
    	}
    	
    	if(!attriId.equals("")){
    		sb.append(" and t.ATTRI_ID = '" + attriId + "'");
    	}
    	
		SQL = sb.toString();
		setPrimaryKey("t.INDEX_ID");
		datasource = ds;
	}
	
	//重写保存方法
	public DefaultHttpHeaders create() {
		try {
			CustomerAttriScore cas = (CustomerAttriScore) model;
			service.saveCustomerAttriScore(cas);
		} catch (BizException e) {
			throw e;
		}catch(Exception e){
    		e.printStackTrace();
    		throw new BizException(1, 2, "1002", e.getMessage());
    	}
		return new DefaultHttpHeaders("success").setLocationId(model);
	}

	public void delete(){
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    String indexId = request.getParameter("id");
	    service.delete(indexId);
	}
}
