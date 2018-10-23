package com.yuchengtech.bcrm.dynamicCrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriConf;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerAttriTreeService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户属性指标维护--方案属性指标数查询
 * @author denghj
 *
 */
@ParentPackage("json-default")
@Action("/customerAttriCheckTree")
@SuppressWarnings("serial")
public class CustomerAttriCheckTreeAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerAttriTreeService service;
	
	
	@Autowired
	public void init(){
		model = new CustomerAttriConf();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select t.ATTRI_ID, t.ATTRI_NAME, t.UP_ATTRI_ID, t.ATTRI_LEVEL, t.ATTRI_STATE,");
		sb.append("decode(a.ATTRI_NAME, '', '客户属性', null, '客户属性', a.ATTRI_NAME) AS ATTRI_PARENT_NAME ");
		sb.append("from (select c.* from OCRM_F_CI_CUST_ATTRI_CONF c ");
		sb.append("union all ");
		sb.append("select s.index_id attri_id,s.index_name attri_name,s.attri_id up_attri_id,'3' attri_level,");
		sb.append("s.index_state attri_state from OCRM_F_CI_CUST_ATTRI_SCORE s) t ");
		sb.append("left join OCRM_F_CI_CUST_ATTRI_CONF a on a.ATTRI_ID = T.UP_ATTRI_ID ");
		sb.append("where 1 = 1 ");

//		ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//		String level = request.getParameter("level");
//		if(level != null && !"".equals(level)){
//			sb.append(" and t.ATTRI_LEVEL='" + level + "'");
//		}
		
		SQL = sb.toString();
		setPrimaryKey("t.ATTRI_ID");
		datasource = ds;
	}
	
	
}
