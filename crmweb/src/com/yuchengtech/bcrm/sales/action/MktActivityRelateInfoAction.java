package com.yuchengtech.bcrm.sales.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdItemRel;
import com.yuchengtech.bcrm.product.service.ProductContrastRelationService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/mktactivityrelateinfoaction")
public class MktActivityRelateInfoAction extends CommonAction {

	private HttpServletRequest request;

	@Autowired
	private ProductContrastRelationService service;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	public void init() {
		model = new OcrmFPdProdItemRel();
		setCommonService(service);
	}

	/**
	 * 产品对照关系查询
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String mktActiId = request.getParameter("mktActiId");
		String querysign = request.getParameter("querysign");
		String __custStr = request.getParameter("__custStr");//捕获需要按客户号查询关联产品的数据
		String table = "OCRM_F_MK_ACTI_CUSTOMER";
		String sqlapp = "select A.*,u.username as CREATE_USER_NAME from "
				+ table
				+ " A inner join sys_users u on a.create_user = u.userid ";
		if (null == querysign && null == mktActiId) {
			mktActiId = "";
			querysign = "";
		}
		if (querysign.equals("customer")) {
			sqlapp = "select A.*,act.MKT_ACTI_STAT,  "
					+ " u.username as CREATE_USER_NAME,"
					+ "  org.INSTITUTION_NAME,"
					+ "  CUS.MGR_NAME"
					+ "  from OCRM_F_MK_ACTI_CUSTOMER A"
					+ " inner join sys_users u"
					+ "  on a.create_user = u.userid"
					+ "  left join ocrm_f_ci_belong_org org"
					+ "   on org.CUST_ID = A.CUST_ID"
					+ "  AND ORG.MAIN_TYPE = '1'"
					+ " left JOIN OCRM_F_CI_BELONG_CUSTMGR CUS"
					+ "    ON CUS.CUST_ID = A.CUST_ID"
					+ "  AND ( CUS.MAIN_TYPE = '1' or cus.main_type is null)"
					+ " inner join ocrm_f_mk_mkt_activity act on act.MKT_ACTI_ID  = A.MKT_ACTI_ID ";
		} else {
			if (querysign.equals("prod")) {
				table = "OCRM_F_MK_ACTI_PRODUCT";
				sqlapp = "select A.*,u.username as CREATE_USER_NAME from "+ table+ " A inner join sys_users u on a.create_user = u.userid ";
			} else {
				if (querysign.equals("chanel")) {
					table = "OCRM_F_MK_ACTI_CHANNEL";
					sqlapp = "select A.ACTI_CHANNEL_ID,a.mkt_acti_id,a.product_id AS CHANNEL_ID,a.app_cust_lever,a.cahn_tem_name,a.cahn_tem_cont,a.create_user,a.create_date,chanel.CHANNEL_NAME AS CHANNEL_NAMES,chanel.channel_code as TEMPLETNAME,u.username as CREATE_USER_NAME from "
							+ table
							+ " A inner join sys_users u on a.create_user = u.userid inner join OCRM_F_MM_CHANNEL_INFO chanel  on chanel.channel_id = A.PRODUCT_ID ";
				}
			}
		}
		StringBuilder sb = new StringBuilder(sqlapp);
		
		if(null!=__custStr&&__custStr.length()>0){				//添加限制，至能查询到与客户有关联关系的营销活动关联产品 2013-03-18 sujm
			String queryStr[]=__custStr.split(",");				//queryStr[0]标识客户编号，queryStr[1]标识客户来源渠道的类型 
			if("001".equals(queryStr[1])){						//在配置 客户来源渠道只有产品目标客户筛选时，添加约束
				sb.append(" inner join OCRM_F_PD_AIM_CUST aim_cust on aim_cust.cust_id = '"+queryStr[0]+"' and aim_cust.product_id = A.product_id ");	
			}	
		}
		
		sb.append("	WHERE 1=1");
		if(""!=mktActiId)
			sb.append("   and A.MKT_ACTI_ID = '" + mktActiId + "'");
		
		addOracleLookup("REL_TYPE", "CON_TYPE");				// 对照类型
		addOracleLookup("AIM_CUST_SOURCE", "AIM_CUST_SOURCE");  // 客户来源
		addOracleLookup("PROGRESS_STEP", "STAGE_LEAVL");		// 进展阶段

		SQL = sb.toString();
		datasource = ds;
	}
}
