package com.yuchengtech.bcrm.customer.customergroup.action;

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
/**
 * 查询客户群客户反洗钱指标信息
 */
@SuppressWarnings("serial")
@Action("/groupAntMoneyTarget")
public class GetGroupAntMoneyTargetAction  extends CommonAction {
    @Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		//客户群或集团id
		String id = request.getParameter("id");
		//StringBuilder sb=new StringBuilder("select cust_id from OCRM_F_CI_RELATE_CUST_BASE a where a.CUST_BASE_ID='" + id + "'");
		StringBuilder sb=new StringBuilder(
				" SELECT ROWNUM AS CUST_NUM ,"+//  --序号
				" A.CUST_ID ,"+ //--客户编号
				" A.CORE_NO ,"+ // --核心客户号
				" A.CUST_NAME ,"+ //--客户名称
				" A.NATION_CODE ,"+ // --国籍
				" A.CUST_GRADE ,"+ //  --反洗钱等级  
				" A.IDENT_TYPE1 ,"+ //--证件类型1
				" A.ident_no1 ,"+ //--证件号码1
				" A.IDENT_EXPIRED_DATE1 ,"+ //--证件有效期1
				" A.IDENT_TYPE2 ,"+ //--证件类型2
				" A.ident_no2 ,"+ //--证件号码2
				" A.IDENT_EXPIRED_DATE2,"+ //--证件有效期2
				" A.INSTITUTION_CODE,"+ //--归属机构
				" A.MGR_ID ,"+ // --所属客户经理ID
				" A.USER_NAME "+ // --所属客户经理名称
				" FROM ("+ //
				" SELECT T.CUST_TYPE,T.CUST_ID ,T.CORE_NO ,T.CUST_NAME ," +
				//" T1.NATION_CODE ," +
				" CASE WHEN T.CUST_TYPE='2'THEN T6.CITIZENSHIP " + 
	            " WHEN  T.CUST_TYPE='1' THEN " +
	            " T1.NATION_CODE END NATION_CODE," +
				"T2.CUST_GRADE ,"+ //
				" t9.IDENT_TYPE IDENT_TYPE1 ,t9.ident_no ident_no1 ,t9.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE1 ,t10.IDENT_TYPE IDENT_TYPE2 ,t10.ident_no ident_no2,"+ //
				" t10.IDENT_EXPIRED_DATE IDENT_EXPIRED_DATE2 ,T3.INSTITUTION_CODE,T4.MGR_ID ,T5.USER_NAME "+ //
				" FROM ACRM_F_CI_CUSTOMER T"+ //
				" INNER JOIN ACRM_A_FACT_FXQ_CUSTOMER S ON T.CUST_ID = S.CUST_ID"+ //
				" LEFT JOIN ACRM_F_CI_ORG T1 ON T.CUST_ID = T1.CUST_ID"+ //
				" LEFT JOIN ACRM_F_CI_PERSON T6 ON T.CUST_ID = T6.CUST_ID "+
				" LEFT JOIN ACRM_F_CI_GRADE T2 ON T.CUST_ID = T2.CUST_ID AND T2.CUST_GRADE_TYPE = '01'"+ //
				" LEFT JOIN (  "+ // --XD000040
				" SELECT T.CUST_ID,T.IDENT_NO,T.IDENT_EXPIRED_DATE,T.IDENT_TYPE"+ //
				" FROM ACRM_F_CI_CUST_IDENTIFIER T "+ //
				" WHERE IS_OPEN_ACC_IDENT='Y'"+ //
				"  ) T9 ON T.CUST_ID=T9.CUST_ID  "+ //
				" LEFT JOIN ("+ //
				" SELECT T.CUST_ID,T.IDENT_NO,T.IDENT_EXPIRED_DATE,T.IDENT_TYPE"+ //
				" FROM ACRM_F_CI_CUST_IDENTIFIER T"+ //
				" WHERE (IS_OPEN_ACC_IDENT <> 'Y' OR IS_OPEN_ACC_IDENT IS NULL) "+ //
				" AND IDENT_TYPE NOT IN ('V','15X','W','Y')"+ //
				" ) T10  ON T.CUST_ID=T10.CUST_ID "+ //
				" LEFT JOIN OCRM_F_CI_BELONG_ORG T3 ON T.CUST_ID = T3.CUST_ID"+ //
		  " LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR T4 ON T.CUST_ID = T4.CUST_ID  "+ //
		  " LEFT JOIN ADMIN_AUTH_ACCOUNT T5 ON T4.MGR_ID = T5.ACCOUNT_NAME"+ //
		  " INNER join OCRM_F_CI_RELATE_CUST_BASE c on  T.CUST_ID=c.cust_id"+ //
		  " where c.cust_base_id='"+id+"'"+ //
 " ORDER BY CUST_ID ) A");
		SQL=sb.toString();
        datasource = ds;
    }  
}