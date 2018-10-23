package com.yuchengtech.bcrm.custmanager.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfoReview;
import com.yuchengtech.bcrm.custmanager.service.CustomerManagerInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/CustomerManagerInfoAction1")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "CustomerManagerInfoAction1" }) })
public class CustomerManagerInfoQueryAction extends CommonAction {

	@Autowired
	private CustomerManagerInfoService service;// 定义UserManagerService属性

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	public void init() {
		model = new OcrmFCmCustMgrInfo();
		setCommonService(service);
		needLog = true;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String userId = auth.getUserId();
//		StringBuilder sb = new StringBuilder(
//				"SELECT su.*, s.MOBILEPHONE AS TELEPHONE, su.IF_CREDIT AS IS_HAVING_CARD, un.UNITNAME, s.USER_NAME, s.ORG_ID as UNITID, s.SEX as GENDER,  s.EMAIL"
//						+ " FROM OCRM_F_CM_CUST_MGR_INFO su INNER JOIN ADMIN_AUTH_ACCOUNT s   ON su.CUST_MANAGER_ID = s.ACCOUNT_NAME left join sys_units un on un.unitid=s.ORG_ID WHERE 1 > 0 ");
        StringBuffer sb=new StringBuffer("SELECT T.*,T2.ORG_NAME,T3.DPT_NAME FROM OCRM_F_CM_CUST_MGR_INFO T "+ 
        			"LEFT JOIN ADMIN_AUTH_ORG T2 ON T.AFFI_INST_ID=T2.ORG_ID "+
        			"LEFT JOIN ADMIN_AUTH_DPT T3 ON T.DPT_ID=T3.DPT_ID "+
        			" WHERE 1>0 ");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("CUST_MANAGER_ID")
						|| key.equals("CUST_MANAGER_NAME")
						|| key.equals("CUST_MANAGER_NAME")
						|| key.equals("EDUCATION")
						|| key.equals("BIRTHDAY")
						|| key.equals("ENTRANTS_DATE")
						|| key.equals("POSITION_TIME")
						|| key.equals("FINANCIAL_JOB_TIME")
						|| key.equals("CUST_MANAGER_LEVEL")
						|| key.equals("EVA_RESULT")
						|| key.equals("WORK_PERFORMANCE")
						|| key.equals("AWARD") || key.equals("POSITION_CHANGE")
						|| key.equals("CERTIFICATE")) {
					sb.append("AND T." + key + " LIKE '%"
							+ this.getJson().get(key) + "%'");
				}else if(key.equals("ORG_NAME") || key.equals("ORG_ID") ){
					sb.append("AND T2." + key + " LIKE '%"
							+ this.getJson().get(key) + "%'");
				}else if(key.equals("DPT_NAME") ){
					sb.append("AND T3." + key + " LIKE '%"
							+ this.getJson().get(key) + "%'");
				}
			}
		}
		SQL = sb.toString();
		setPrimaryKey("t.USER_ID desc");
		datasource = ds;
	}

	public Map<String, Object> getJson() {
		return super.getJson();
	}
	
	public String getMgrInfo(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String busiId = request.getParameter("mgrId");
		try {
			StringBuilder sb = new StringBuilder("");
	        sb.append("SELECT T.*,T2.ORG_NAME,T3.DPT_NAME FROM OCRM_F_CM_CUST_MGR_INFO T "+ 
	        			"LEFT JOIN ADMIN_AUTH_ORG T2 ON T.AFFI_INST_ID=T2.ORG_ID "+
	        			"LEFT JOIN ADMIN_AUTH_DPT T3 ON T.DPT_ID=T3.DPT_ID "+
	        			" WHERE 1>0 and T.CUST_MANAGER_ID='"+busiId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
//			this.json.put("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 退出客户经理
	 * 
	 * @return
	 */
	public DefaultHttpHeaders cancelCustMgr() {
		ActionContext ac = ActionContext.getContext();
		request = (HttpServletRequest) ac
				.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("CUST_MANAGER_ID");
		service.remove(Long.parseLong(id));
		return new DefaultHttpHeaders("success");
	}

	/**
	 * 修改客户经理信息
	 */
	public DefaultHttpHeaders saveData() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String id = ((OcrmFCmCustMgrInfo) model).getCustManagerId().toString();
		String jql = "update  OcrmFCmCustMgrInfo p set p.certificate=:certificate where p.custManagerId='"
				+ id + "'";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("certificate", ((OcrmFCmCustMgrInfo) model).getCertificate());
		service.batchUpdateByName(jql, values);
		return new DefaultHttpHeaders("success");
	}

	/**
	 * 修改客户经理信息审批流程
	 */
	/**
	 * 发起工作流
	 * */
	public void initFlow() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		OcrmFCmCustMgrInfoReview cmCustMgrInfoReview = new OcrmFCmCustMgrInfoReview();
		cmCustMgrInfoReview.setCustManagerId(((OcrmFCmCustMgrInfo) model)
				.getCustManagerId());
		cmCustMgrInfoReview.setCustManagerName(((OcrmFCmCustMgrInfo) model)
				.getCustManagerName());
		cmCustMgrInfoReview.setCertificate(((OcrmFCmCustMgrInfo) model)
				.getCertificate());
		service.saveReview(cmCustMgrInfoReview);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		String id = metadataUtil.getId(cmCustMgrInfoReview).toString();
		String mgrId = ((OcrmFCmCustMgrInfo) model).getCustManagerId();
		// String requestId = request.getParameter("instanceid");
		String name = ((OcrmFCmCustMgrInfo) model).getCustManagerName();
		String instanceid = "CM," + id+","+mgrId;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "客户经理信息修改_" + name;// 自定义流程名称
		service.initWorkflowByWfidAndInstanceid("26", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("instanceid", instanceid);
		map1.put("currNode", "26_a3");
		map1.put("nextNode", "26_a4");
		this.setJson(map1);

	}

}
