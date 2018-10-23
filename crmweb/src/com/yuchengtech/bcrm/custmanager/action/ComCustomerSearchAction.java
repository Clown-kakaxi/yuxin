package com.yuchengtech.bcrm.custmanager.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @Description: 法金客户查询Action
 * @author likai3
 * @since 2015-03-03
 */
@SuppressWarnings("serial")
@Action("comCustomerSearch")
public class ComCustomerSearchAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	public void prepare() {

		// StringBuffer sb=new
		// StringBuffer("SELECT DISTINCT  C.CUST_ID,C.CUST_NAME,C.CORE_NO,C.IDENT_TYPE,C.IDENT_NO,C.CUST_TYPE,C.CUST_LEVEL,C.CURRENT_AUM,C.TOTAL_DEBT,C.RISK_LEVEL,"
		// +
		// " linkman.linkman_name, linkman.mobile LINKMAN_TEL, O.org_biz_cust_type as BELONG_LINE_NO,C.CUST_STAT,M.INSTITUTION_NAME AS ORG_NAME,M.MGR_NAME,GD.CUST_GRADE_TYPE, "
		// +
		// " C.CREDIT_LEVEL,C.FAXTRADE_NOREC_NUM,T.BELONG_TEAM_HEAD_NAME,L.BL_NAME,GD.CUST_GRADE as FXQ_RISK_LEVEL,"
		// +
		// " s.SPECIAL_LIST_TYPE, " + //特殊名单类型
		// " s.SPECIAL_LIST_KIND, " + //特殊名单类别
		// " s.SPECIAL_LIST_FLAG, " + //特殊名单标志
		// " s.ORIGIN, " + //数据来源
		// " s.STAT_FLAG, " + //状态标志
		// " s.APPROVAL_FLAG, " + //审核标志
		// " s.START_DATE, " + //起始日期
		// " s.END_DATE, " + //结束日期
		// " s.ENTER_REASON " + //列入原因
		// " FROM ACRM_F_CI_CUSTOMER C LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID "
		// +
		// " LEFT JOIN OCRM_F_CM_CUST_MGR_INFO T ON M.MGR_ID = T.CUST_MANAGER_ID "
		// +
		// " left join acrm_f_ci_org o on o.CUST_ID = C.CUST_ID " +
		// " LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type = to_char(L.BL_NO) "
		// +
		// " left join OCRM_F_CI_ANTI_CUST_LIST risk on risk.cust_id = c.cust_id "
		// +
		// " left join ACRM_F_CI_SPECIALLIST s on s.CUST_ID = C.CUST_ID " +
		// "left join ( select exe.org_cust_id cust_id,exe.linkman_name,exe.mobile  from   ACRM_F_CI_ORG_EXECUTIVEINFO exe where exe.linkman_type='21' "+
		// " union all "+
		// " select  plink.cust_id,plink.linkman_name,plink.mobile from  ACRM_F_CI_PER_LINKMAN plink where plink.linkman_type='21') linkman on linkman.cust_id=c.cust_id "+
		// " LEFT JOIN (SELECT ee.CUST_ID, ee.CUST_GRADE_TYPE,ee.CUST_GRADE," +
		// "   (case when ee.LAST_UPDATE_USER <> 'ETL' then tt.user_name else ee.LAST_UPDATE_USER end ) as LAST_UPDATE_USER FROM ACRM_F_CI_GRADE  ee "
		// +
		// "  left join admin_auth_account tt on tt.account_name = ee.LAST_UPDATE_USER "
		// +
		// "  WHERE CUST_GRADE_TYPE = '01') GD ON GD.CUST_ID = C.CUST_ID " +
		// " WHERE 1=1 and C.CUST_TYPE = '1' ");

		// SQL胡志编写 2015-03-03
		StringBuffer sb = new StringBuffer(
				" SELECT  C.CUST_ID,C.CUST_NAME,C.CORE_NO,C.IDENT_TYPE,C.CUST_TYPE,C.CUST_LEVEL,C.CURRENT_AUM,"
						+ " SUBSTR(C.IDENT_NO,0,LENGTH(C.IDENT_NO)-5)||'***'|| SUBSTR(C.IDENT_NO,LENGTH(C.IDENT_NO)-1,LENGTH(C.IDENT_NO)) IDENT_NO,"
						+ " C.TOTAL_DEBT,C.RISK_LEVEL,C.FAXTRADE_NOREC_NUM,  NVL(EXE.LINKMAN_NAME, PLINK.LINKMAN_NAME) LINKMAN_NAME,"
						+ " NVL(EXE.MOBILE, PLINK.MOBILE) LINKMAN_TEL,O.ORG_BIZ_CUST_TYPE AS BELONG_LINE_NO,C.CUST_STAT,"
						+ " M3.INSTITUTION_NAME AS ORG_NAME,"
						+ " M1.USER_NAME MGR_NAME,  M2.USER_NAME AS BELONG_TEAM_HEAD_NAME,GD01.CUST_GRADE_TYPE TYPE01, GD06.CUST_GRADE_TYPE TYPE06,"
						+ " GD07.CUST_GRADE_TYPE TYPE07, GD10.CUST_GRADE_TYPE TYPE10,"
						+ " L.BL_NAME,"
						+ " GD01.CUST_GRADE AS FXQ_RISK_LEVEL, GD06.CUST_GRADE AS COMP_TYPE,GD07.CUST_GRADE AS COMP_LEVEL,"
						+ " GD10.CUST_GRADE AS CREDIT_LEVEL,T2.GRP_NAME AS GROUP_NAME,"
						+ " S.SPECIAL_LIST_TYPE,S.SPECIAL_LIST_KIND,S.SPECIAL_LIST_FLAG,S.ORIGIN,S.STAT_FLAG,S.APPROVAL_FLAG,S.START_DATE,S.END_DATE,S.ENTER_REASON "
						+ " FROM ACRM_F_CI_CUSTOMER C "
						+ " LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR M3 ON C.CUST_ID = M3.CUST_ID"
						+ " LEFT JOIN ADMIN_AUTH_ACCOUNT M1 ON M3.MGR_ID = M1.ACCOUNT_NAME"
						+ " LEFT JOIN ADMIN_AUTH_ACCOUNT M2 ON M1.BELONG_TEAM_HEAD = M2.ACCOUNT_NAME "
						+ " LEFT JOIN OCRM_F_CI_GROUP_MEMBER_NEW T1 ON T1.CUS_ID = C.CUST_ID "
						+ " LEFT JOIN OCRM_F_CI_GROUP_INFO_NEW T2    ON T2.GRP_NO = T1.GRP_NO "
						+ " LEFT JOIN ACRM_F_CI_ORG O ON O.CUST_ID = C.CUST_ID "
						+ " LEFT JOIN ACRM_F_CI_BUSI_LINE L ON O.ORG_BIZ_CUST_TYPE = TO_CHAR(L.BL_NO) "
						+ " LEFT JOIN OCRM_F_CI_ANTI_CUST_LIST RISK ON RISK.CUST_ID = C.CUST_ID "
						+ " LEFT JOIN ACRM_F_CI_SPECIALLIST S ON S.CUST_ID = C.CUST_ID "
						+ " LEFT JOIN ACRM_F_CI_ORG_EXECUTIVEINFO EXE   ON EXE.ORG_CUST_ID = C.CUST_ID  AND EXE.LINKMAN_TYPE = '21' "
						+ " LEFT JOIN ACRM_F_CI_PER_LINKMAN PLINK  ON PLINK.CUST_ID = C.CUST_ID AND PLINK.LINKMAN_TYPE = '21'      "
						+ " LEFT JOIN ACRM_F_CI_GRADE GD01  ON C.CUST_ID = GD01.CUST_ID AND GD01.CUST_GRADE_TYPE = '01'            "
						+ " LEFT JOIN ADMIN_AUTH_ACCOUNT TT  ON TT.ACCOUNT_NAME = GD01.LAST_UPDATE_USER                            "
						+ " LEFT JOIN ACRM_F_CI_GRADE GD06  ON C.CUST_ID = GD06.CUST_ID  AND GD06.CUST_GRADE_TYPE = '06'           "
						+ " LEFT JOIN ACRM_F_CI_GRADE GD07  ON C.CUST_ID = GD07.CUST_ID AND GD07.CUST_GRADE_TYPE = '07'            "
						+ " LEFT JOIN ACRM_F_CI_GRADE GD10  ON C.CUST_ID = GD10.CUST_ID AND GD10.CUST_GRADE_TYPE = '10'            "
						+ " WHERE 1 = 1 ");

		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if ("ORG_NAME".equals(key)) {
					sb.append("and M3.INSTITUTION_NAME IN (SELECT ORG_NAME FROM SYS_UNITS WHERE UNITSEQ LIKE '%"
							+ this.json.get(key) + "%')");
				}
				if ("BL_NAME".equals(key)
						&& !"归属业务条线".equals(this.json.get(key))) {
					sb.append(" and  (o.org_biz_cust_type in (select distinct bl_ID from ACRM_F_CI_BUSI_LINE t START   WITH bl_ID='"
							+ this.json.get(key)
							+ "' CONNECT BY PRIOR BL_ID=PARENT_ID))");
				}
			}
		}
		// 添加证件类型与证件号码查询,取证件表上的数据进行查询
		if (null != this.getJson().get("IDENT_TYPE")
				&& !"".equals(this.getJson().get("IDENT_TYPE"))
				&& null != this.getJson().get("IDENT_NO")
				&& !"".equals(this.getJson().get("IDENT_NO"))) {
			sb.append(" AND EXISTS (SELECT 1 FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"
					+ this.json.get("IDENT_TYPE")
					+ "' AND I.IDENT_NO LIKE '%"
					+ this.json.get("IDENT_NO")
					+ "%'"
					+ "   AND C.CUST_ID = I.CUST_ID)");
		} else if (null != this.getJson().get("IDENT_TYPE")
				&& !"".equals(this.getJson().get("IDENT_TYPE"))) {
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_TYPE = '"
					+ this.json.get("IDENT_TYPE") + "')");
		} else if (null != this.getJson().get("IDENT_NO")
				&& !"".equals(this.getJson().get("IDENT_NO"))) {
			sb.append(" and c.CUST_ID in ( select I.CUST_ID FROM ACRM_F_CI_CUST_IDENTIFIER I WHERE I.IDENT_NO like '%"
					+ this.json.get("IDENT_NO") + "%')");
		}

		SQL = sb.toString();
		datasource = ds;

		setPrimaryKey("c.CUST_ID,CUST_TYPE asc ");
		configCondition("c.CUST_ID", "like", "CUST_ID", DataType.String);
		configCondition("c.CUST_NAME", "like", "CUST_NAME", DataType.String);
		configCondition("c.CUST_TYPE", "=", "CUST_TYPE", DataType.String);
		configCondition("c.CUST_STAT", "=", "CUST_STAT", DataType.String);
		configCondition("c.LINKMAN_NAME", "like", "LINKMAN_NAME",
				DataType.String);
		configCondition("c.LINKMAN_TEL", "like", "LINKMAN_TEL", DataType.String);
		configCondition("c.CUST_LEVEL", "=", "CUST_LEVEL", DataType.String);
		configCondition("c.RISK_LEVEL", "=", "RISK_LEVEL", DataType.String);
		configCondition("c.CREDIT_LEVEL", "=", "CREDIT_LEVEL", DataType.String);
		configCondition("M1.USER_NAME", "=", "MGR_NAME", DataType.String);
		configCondition("c.CURRENT_AUM", ">=", "CURRENT_AUM", DataType.Number);
		configCondition("M2.USER_NAME", "=", "BELONG_TEAM_HEAD_NAME",
				DataType.String);
		configCondition("C.CORE_NO", "=", "CORE_NO", DataType.String);
		configCondition("GD01.CUST_GRADE", "=", "FXQ_RISK_LEVEL",
				DataType.String);
	}
}
