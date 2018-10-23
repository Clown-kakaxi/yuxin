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
import com.yuchengtech.bcrm.custview.model.AcrmFCiCustElecMgrInfo;
import com.yuchengtech.bcrm.custview.service.FilesCustInfoService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 对公客户视图 档案管理
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/filesCustInfo")
public class FilesCustInfoAction extends CommonAction{
	@Autowired
	private FilesCustInfoService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiCustElecMgrInfo();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
		StringBuilder sb = new StringBuilder();
		sb.append("select t.* from ACRM_F_CI_CUST_ELEC_MGR_INFO t  where 1=1 ");
		if(customerId != null){
			sb.append(" and t.cust_id = '"+customerId+"'");
		}
		for(String key:this.getJson().keySet()){
			 if (null != this.getJson().get(key) && !"".equals(this.getJson().get(key))){
				 if("FILE_TYPE".equals(key)){
					 sb.append(" and t."+key+" = '"+this.getJson().get(key)+"'");
				 }
				 if("FILE_NAME".equals(key)){
					 sb.append(" and t."+key+" like '%"+this.getJson().get(key)+"%'");
				 }
				 
//				 if("ARCHIVE_DATE".equals(key)){
//					 
//				 }
			 }
		}

		SQL = sb.toString();
		setPrimaryKey(" t.Id desc");
		addOracleLookup("FILE_TYPE", "FILE_TYPE");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql = "delete from AcrmFCiCustElecMgrInfo c where c.id in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
}
