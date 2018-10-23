package com.yuchengtech.bcrm.dynamicCrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 指标编辑action
 * @author 亮
 *
 */

@SuppressWarnings("serial")
@Action("adminZoneQuery")
public class AdminZoneQueryAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//		String fLookupId = request.getParameter("fLookupId");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select t.ID, t.ATTRI_ID, t.INDEX_VALUE, t.INDEX_VALUE_NAME " 
				+ "from OCRM_F_CI_CUST_ATTRI_ITEM t where 1=1 ");
		
        for(String key : this.getJson().keySet()){
            if(null != this.getJson().get(key) && !"".equals(this.getJson().get(key))){
        		if("fLookupId".equals(key)){
        			sb.append(" and t.ATTRI_ID ='" + this.getJson().get(key) + "'");
        		}
//                if("F_CODE".equals(key)){
//                    sb.append(" AND t.F_CODE like '%" + this.getJson().get(key) + "%'");
//                }
//            	if("F_VALUE".equals(key)){
//                    sb.append(" AND t.F_VALUE LIKE '%" + this.getJson().get(key) + "%'");
//                }
            }
        }
        
		sb.append(" order by t.ID");
		SQL = sb.toString();
		datasource = ds;
	}

}
