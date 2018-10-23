package com.yuchengtech.bcrm.customer.customerView.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.service.CustomerQueryAllNewService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description: 客户全行查询
 * @author xiebz
 * @data 2014-07-07
 * @update helin,20141028,查询需求变更
 */
@ParentPackage("json-default")
@Action("/customerQueryAllNew")
public class CustomerQueryAllNewAction extends CommonAction {
	private static final long serialVersionUID = -1307317536382455940L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	private CustomerQueryAllNewService service;

	@Autowired
	public void init() {
		model = new AcrmFCiCustomer();
		setCommonService(service);
	}

	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder(
				" select distinct A.CUST_ID,A.CUST_NAME,A.CUST_TYPE,A.CORE_NO,A.IDENT_TYPE,A.IDENT_NO,");
		sb.append(" CASE WHEN CR.CUST_ID IS NULL THEN 0 ELSE 1 END AS IS_SX_CUST,o.INSTITUTION_CODE || ',' ||r.INSTITUTION INSTITUTION_CODE,act.ACT_ORG_IDS,r.MGR_ID from ACRM_F_CI_CUSTOMER a ");
		sb.append(" LEFT JOIN OCRM_F_CI_BELONG_ORG O ON O.CUST_ID = A.CUST_ID ");
		sb.append(" LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR R ON A.CUST_ID = R.CUST_ID ");
		sb.append(" LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER I ON I.CUST_ID = A.CUST_ID ");
		sb.append(" LEFT JOIN ACRM_F_CI_CONTMETH L ON L.CUST_ID = A.CUST_ID");
		sb.append(" LEFT JOIN (SELECT CUST_ID FROM ACRM_F_CI_CROSSINDEX WHERE SRC_SYS_NO = 'LN') CR ON CR.CUST_ID = A.CUST_ID ");
		sb.append(" LEFT JOIN ACRM_F_CI_DEPOSIT_ORG act on act.CUST_ID = a.CUST_ID ");
		sb.append(" WHERE 1=1 AND (A.POTENTIAL_FLAG IS NULL OR A.POTENTIAL_FLAG != '1') ");
		
		String flag=request.getParameter("FLAG");
		if("cc".equals(flag)){
			sb.append(" and a.cust_type='2' ");
		}

		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if ("ACC_NO".equals(key)) {
					sb.append(" and a.cust_id in (select distinct cust_id from ACRM_F_CI_DEPOSIT_ACT where ACCT_NO like '"
							+ this.json.get(key) + "%')");
				} else if ("CUST_NAME".equals(key)) {
					// 如果查询条件为1,则表示客户名称是like,查询条件为2,则表示客户名称是=
					if (this.getJson().get("ROLE_COND") != null
							&& "1".equals(this.getJson().get("ROLE_COND"))) {
						sb.append(" AND a.CUST_NAME like '%"
								+ this.getJson().get(key) + "%'");
					} else {
						sb.append(" AND a.CUST_NAME = '"
								+ this.getJson().get(key) + "'");
					}
				} else if ("IDENT_TYPE".equals(key)) {
					sb.append(" AND i.IDENT_TYPE = '" + this.getJson().get(key)
							+ "'");
				} else if ("IDENT_NO".equals(key)) {
					sb.append(" AND i.IDENT_NO = '" + this.getJson().get(key)
							+ "'");
				} else if ("CORE_NO".equals(key)) {
					sb.append(" AND a.CORE_NO = '" + this.getJson().get(key)
							+ "'");
				} else if ("CONTMETH_INFO".equals(key)) {
					sb.append(" AND L.CONTMETH_INFO like '%" + this.getJson().get(key)
							+ "%'");
				}   //联系电话
			}
		}
		SQL = sb.toString();
		datasource = ds;

		addOracleLookup("CUST_TYPE", "XD000080");
		addOracleLookup("IDENT_TYPE", "XD000040");
	}
   //证件类型1 
    public void getIdentType(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.service.getIdentType(request.getParameter("name"));
			JSONObject json = new JSONObject();
			String retStr = JSONObject.fromObject(json).toString();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	//证件类型2
    public void getIdentType2(){
    	HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			List<Map<String, Object>> rstList = this.service.getIdentType2(request.getParameter("name"));
			JSONObject json = new JSONObject();
			String retStr = JSONObject.fromObject(json).toString();
			if (this.json != null) {
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("JSON", rstList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
   
	/**
	 * 判断客户记录是否被锁定
	 * 
	 * @return
	 */
	public String isLockCust() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String custId = "CI_" + request.getParameter("custId");

			StringBuffer sb = new StringBuffer(
					"SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
							+ custId
							+ "%'    union "
							+
							" select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
							+
							" left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
							+
							" where his.cust_id='"
							+ request.getParameter("custId")
							+ "' and his.appr_flag='0'");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();

			// "客户记录被锁定，操作员"+list.get(0)

			if (this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String, Object>();
			this.json.put("json", result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
		return "success";
	}
}
