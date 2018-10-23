package com.yuchengtech.bcrm.dynamicCrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerScheme;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerSchemeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;

/**
 * 方案action
 * @author 亮
 *
 */

@SuppressWarnings("serial")
@Action("customerScheme")
public class CustomerSchemeAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerSchemeService service;

	@Autowired
	public void init(){
		model = new CustomerScheme();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.SCHEME_ID, t.SCHEME_NAME, t.SCHEME_STATE, a.F_VALUE ");
		sb.append("from OCRM_F_CI_CUST_SCHEME t ");
		sb.append("left join OCRM_SYS_LOOKUP_ITEM a on a.F_CODE=t.SCHEME_STATE and a.F_LOOKUP_ID='ATTR001'");
		sb.append("where 1=1 ");
        
		sb.append(" order by t.SCHEME_ID");
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
	 * 保存客户属性
	 */
	public DefaultHttpHeaders create() {
		try {
			CustomerScheme cs = (CustomerScheme) model;
			service.save(cs);
		} catch (BizException e) {
			throw e;
		}catch(Exception e){
    		e.printStackTrace();
    		throw new BizException(1, 2, "1002", e.getMessage());
    	}
		return new DefaultHttpHeaders("success").setLocationId(model);
	}
	
//	public void save(){
//		ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//		String schemeId = request.getParameter("schemeId");
//		String schemeName = request.getParameter("schemeName");
//		String schemeState = request.getParameter("schemeState");
//		CustomerScheme cs = new CustomerScheme();
//		if(schemeId != null && schemeId.equals("")){
//			cs.setSchemeId(null);
//		}else{
//			cs.setSchemeId(schemeId);
//		}
//		cs.setSchemeName(schemeName);
//		cs.setSchemeState(schemeState);
//		service.save(cs);
//	}

	public void delete(){
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    String id = request.getParameter("id");
		service.delete(id);
	}
}
