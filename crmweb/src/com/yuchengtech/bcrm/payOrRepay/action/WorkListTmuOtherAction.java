package com.yuchengtech.bcrm.payOrRepay.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

import com.yuchengtech.bcrm.payOrRepay.model.OcrmFMkTmuOther;
import com.yuchengtech.bcrm.payOrRepay.service.OcrmFMkTmuOtherService;
/**
 * @描述：新拨还款查询Action
 * @author sunjing5
 * @date:2017-02-10
 */
@SuppressWarnings("serial")
@Action("/workListTmuOtherAction")
public class WorkListTmuOtherAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 数据源

	@Autowired
	private OcrmFMkTmuOtherService service;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	@Autowired
	public void init() {// 初始化module
		model = new OcrmFMkTmuOther();
		setCommonService(service);
		needLog = false;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String applyNO = request.getParameter("applyNO"); // 获取需要提交的记录id

		StringBuilder querySql = new StringBuilder(
				" SELECT A.ID, A.APPLY_NO, A.REGION, O.ORG_NAME,   A.CUST_TYPE,  A.IF_RE, A.CUST_ID, A.CUST_NAME,  "+
				 " A.gather_or_back ,  A.ORG_ID,  O3.ORG_NAME AS ORG_NAME1,        "+
				 " A.gather_type ,  "+
				 " A.PROGRESS,   A.PROBABILITY,    A.IF_TAIWAN,  A.APPROVE_STATE,   "+  
					"   decode(nvl(A.AMOUNT,0),0,0,A.AMOUNT/1000) AS AMOUNT,"+
					"   decode(nvl(A.DISCOUNT_OCCUR_AMT,0),0,0,A.DISCOUNT_OCCUR_AMT/1000)AS DISCOUNT_OCCUR_AMT,"+
				 " A.CREATE_USER,A1.USER_NAME AS CREATE_USERNAME, A.CREATE_ORG,O1.ORG_NAME AS CREATE_ORGNAME,to_char(A.CREATE_TM,'yyyy-mm-dd hh24:mi:ss') CREATE_TM,A.LAST_UPDATE_USER, A2.USER_NAME AS LAST_UPDATE_USERNAME,A.LAST_UPDATE_ORG,O2.ORG_NAME AS LAST_UPDATE_ORGNAME ,to_char(A.LAST_UPDATE_TM,'yyyy-mm-dd hh24:mi:ss') LAST_UPDATE_TM,   "+ 
				 " B.MGR_ID, B.MGR_NAME, I.F_VALUE,A.REMARK "+
				 "  FROM OCRM_F_MK_TMU_OTHER A     "+
				 " LEFT JOIN ADMIN_AUTH_ACCOUNT A1 ON A.CREATE_USER=A1.ACCOUNT_NAME "+
				 "   LEFT JOIN  ADMIN_AUTH_ACCOUNT A2 ON A.LAST_UPDATE_USER=A2.ACCOUNT_NAME    "+
				 "   LEFT JOIN ADMIN_AUTH_ORG O1 ON A.CREATE_ORG = O1.ORG_ID        "+
				 "  LEFT JOIN ADMIN_AUTH_ORG O2 ON A.CREATE_ORG = O2.ORG_ID         "+
				 "  LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR B  ON A.CUST_ID = B.CUST_ID  "+
				 "  LEFT JOIN ADMIN_AUTH_ORG O  ON A.REGION = O.ORG_ID   "+
				 "  LEFT JOIN ADMIN_AUTH_ORG O3   ON O3.ORG_ID = A.ORG_ID"+
				 "  LEFT JOIN OCRM_SYS_LOOKUP_ITEM I ON I.F_CODE=A.CUST_TYPE        "+
				 "  LEFT JOIN WF_NODE_RECORD WF    "+
				 "    ON SUBSTR(INSTANCEID,  INSTR(INSTANCEID, '_', 1, 1) + 1,  INSTR(INSTANCEID, '_', 1, 2) - 1 - INSTR(INSTANCEID, '_', 1, 1)) = A.APPLY_NO "+
				 " WHERE 1 = 1  AND I.F_LOOKUP_ID='XD000052' ");
		if(null != applyNO && !applyNO.equals("")){
			querySql.append(" AND A.APPLY_NO = '"+applyNO+"'");
		}

		SQL = querySql.toString(); // 为父类SQL属性赋值（设置查询SQL）
		setPrimaryKey("a.LAST_UPDATE_TM desc "); // 设置查询排序条件
		datasource = ds; // 为父类数据源赋值
	}
}