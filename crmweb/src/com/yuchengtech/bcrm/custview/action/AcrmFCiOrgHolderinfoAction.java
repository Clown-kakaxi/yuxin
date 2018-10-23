package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgHolderinfo;
import com.yuchengtech.bcrm.custview.service.AcrmFCiOrgHolderinfoService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 对公客户视图==股东信息
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiOrgHolderinfo")
public class AcrmFCiOrgHolderinfoAction extends CommonAction{
	@Autowired
	private AcrmFCiOrgHolderinfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiOrgHolderinfo();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");

		StringBuilder sb = new StringBuilder("");
		sb.append("select to_char(t.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TMM,t.* from ACRM_F_CI_ORG_HOLDERINFO t  where 1=1 ");
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
				 if ("HOLDER_NAME".equals(key))
		                sb.append(" and t." + key + " like '%"+ this.getJson().get(key) + "%'");
		         else if ("HOLDER_TYPE".equals(key))
		                sb.append(" and t." + key + " = '" + this.getJson().get(key)+ "'");
			}
		}
		
		SQL = sb.toString();
		setPrimaryKey(" t.holder_Id desc");
		//addOracleLookup("IDENT_TYPE", "COM_CRET_TYPE");
		addOracleLookup("IDENT_TYPE", "XD000040");
		addOracleLookup("IS_REG_AT_USA", "IF_FLAG");
		addOracleLookup("HOLDER_PER_GENDER", "DEM0100005");
		addOracleLookup("HOLDER_TYPE", "SH_TYP");
		addOracleLookup("SPONSOR_KIND", "CZ_TYP");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("messageId");
			String jql = "delete from AcrmFCiOrgHolderinfo c where c.holderId in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}
