package com.yuchengtech.bcrm.dynamicCrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriItem;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerAttriItemService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户属性指标值action
 * @author 亮
 *
 */

@SuppressWarnings("serial")
@Action("customerAttriItem")
public class CustomerAttriItemAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerAttriItemService service;

	@Autowired
	public void init(){
		model = new CustomerAttriItem();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String fLookupId = request.getParameter("fLookupId");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select t.ID, t.ATTRI_ID, t.INDEX_VALUE, t.INDEX_VALUE_NAME, ");
		sb.append("REGEXP_SUBSTR(INDEX_VALUE,'[^-]+',1,1) INDEX_VALUE1, ");
		sb.append("REGEXP_SUBSTR(INDEX_VALUE,'[^-]+',1,2) INDEX_VALUE2 ");
		sb.append("from OCRM_F_CI_CUST_ATTRI_ITEM t where 1=1 ");
		
		if(fLookupId != null){
			sb.append(" and t.ATTRI_ID ='" + fLookupId + "'");
		}
		
        for(String key : this.getJson().keySet()){
            if(null != this.getJson().get(key) && !"".equals(this.getJson().get(key))){
        		if("fLookupId".equals(key)){
        			sb.append(" and t.ATTRI_ID ='" + this.getJson().get(key) + "'");
        		}
            }
        }
        
		sb.append(" order by t.ID");
		SQL = sb.toString();
		datasource = ds;
	}
	
	public void save(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		String attriId = request.getParameter("attriId");
		String indexValue = request.getParameter("indexValue");
		String indexValueName = request.getParameter("indexValueName");
		CustomerAttriItem cai = new CustomerAttriItem();
		if(id != null && id.equals("")){
			cai.setId(null);			
		}else{
			cai.setId(id);
		}
		cai.setAttriId(attriId);
		cai.setIndexValue(indexValue);
		cai.setIndexValueName(indexValueName);
		service.save(cai);
	}

	public void delete(){
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    String id = request.getParameter("id");
		service.delete(id);
	}
}
