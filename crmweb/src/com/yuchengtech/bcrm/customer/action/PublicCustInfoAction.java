package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;

/**
 * 
* @ClassName: PublicCustInfoAction 
* @Description: 对公客户信息首页
* @author wangmk1 
* @date 2014-7-26 下午3:33:38 
*
 */
@Action("/publicCustInfo")
public class PublicCustInfoAction extends CommonAction {
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@SuppressWarnings("unchecked")
	public String searchBasicInfo()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT c1.CUST_ID,c1.cust_stat,c1.CUST_NAME,c1.IDENT_TYPE, c1.IDENT_NO,"+
       "c2.BUILD_DATE,c2.ORG_FORM,C2.ORG_ADDR,c2.LEGAL_REPR_NAME,R1.REGISTER_ADDR,"+
       "to_char(r1.REGISTER_CAPITAL, '999,999,990.99') REGISTER_CAPITAL,r1.REGISTER_CAPITAL_CURR,"+
       "to_char(r1.FACT_CAPITAL, '999,999,990.99') FACT_CAPITAL,k1.IS_LISTED_CORP,k1.IS_SOE,k1.IS_TAIWAN_CORP,"+
       "c2.INDUSTRY_CATEGORY,c2.EMPLOYEE_SCALE,c2.ASSETS_SCALE,c2.ORG_CUS,c1.SOURCE_CHANNEL,"+
       "C3.contmeth_info as ORG_TEL,to_char(debt.amt, '999,999,990.99') amt,   to_char(profit.amt, '999,999,990.99') as amt1 ,c2.ENT_SCALE "+
       "FROM ACRM_F_CI_CUSTOMER c1 "+
       "left join ACRM_F_CI_ORG c2 "+
       "on c1.cust_id = c2.cust_id "+
       "left join (select cust_id,contmeth_info from ACRM_F_CI_CONTMETH where 1=1 and contmeth_type = '2031') c3 "+
       "on c1.cust_id = c3.cust_id "+
       "left join ACRM_F_CI_ORG_REGISTERINFO r1 "+
       "on r1.cust_id = c1.cust_id "+
       "left join ACRM_F_CI_ORG_KEYFLAG k1 "+ 
       "on k1.cust_id = c1.cust_id "+
       
  		"left join (select t.*,"+
	        "ROW_NUMBER() OVER(PARTITION BY t.cust_id ORDER BY t.stat_year desc) as rn "+
	        "from ACRM_F_CI_ORG_ASSET_DEBT t "+
	        "where t.item_id = 'Z01000001' "+
	    "and t.cust_id = '"+custId+"' ) debt "+
	    "ON debt.CUST_ID=c1.cust_id and debt.rn=1 "+
       
		"left join "+
		"(select t1.*, " +
				"ROW_NUMBER() OVER(PARTITION BY t1.cust_id ORDER BY t1.stat_year desc) as rn2 "+
				" from ACRM_F_CI_ORG_PROFIT t1 "+
				" where t1.item_id = 'L01000000' "+
				" and t1.cust_id = '"+custId+"' ) profit "+
				" ON profit.CUST_ID=c1.cust_id and profit.rn2=1 "+
       
       " WHERE c1.CUST_ID = '"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("IDENT_TYPE", "XD000040");
			query.addOracleLookup("IS_LISTED_CORP", "XD000156");
			query.addOracleLookup("IS_SOE", "XD000181");
			query.addOracleLookup("IS_TAIWAN_CORP", "XD000182");
			query.addOracleLookup("INDUSTRY_CATEGORY", "XD000287");
			query.addOracleLookup("ORG_FORM", "XD000063");
			
			query.addOracleLookup("EMPLOYEE_SCALE", "XD000061");
			query.addOracleLookup("ASSETS_SCALE", "XD000060");
			query.addOracleLookup("SOURCE_CHANNEL", "XD000353");
			query.addOracleLookup("CUST_STAT", "XD000081");
			query.addOracleLookup("REGISTER_CAPITAL_CURR", "XD000027");
			query.addOracleLookup("ENT_SCALE", "XD000019");
			
			List<?> list = (List<?>)query.getJSON().get("data");
			Map<String, Object> map = (Map<String, Object>) list.get(0);
			String temps="";
			
			//查询年销售额
			temps="select to_char(b1.SALE_AMT,'999,999,990.99') SALE_AMT from ACRM_F_CI_ORG_BUSIINFO b1  where b1.cust_id='"+custId+"'";
			List<?> list1 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list1.toString())){
				Map<String, Object> map1 = (Map<String, Object>) list1.get(0);
				map.putAll(map1);
			}
			//查询客户归属集团
			temps="select b2.grp_name  as BELONG_GROUP"+
					"	from OCRM_F_CI_GROUP_MEMBER_NEW b1"+
					"	LEFT  join Ocrm_f_Ci_Group_INFO_NEW b2"+
					"	on b1.grp_no =B2.GRP_NO"+
					"	left join ACRM_F_CI_CROSSINDEX b3 on b3.src_sys_cust_no=b1.cus_id and b3.src_sys_no='LN'"+
					"	where b3.cust_id ='"+custId+"'";
			List<?> list2 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list2.toString())){
				Map<String, Object> map2 = (Map<String, Object>) list2.get(0);
				map.putAll(map2);
			}
			//归属业务条线
//			temps="select b1.BELONG_LINE_TYPE BELONG_LINE  from ACRM_F_CI_BELONG_LINE b1  where b1.cust_id='"+custId+"'";      //ACRM_F_CI_BELONG_LINE 这张表已经不使用了
			temps="select l.bl_name BELONG_LINE from acrm_f_ci_org o left join ACRM_F_CI_BUSI_LINE l on o.org_biz_cust_type = to_char(L.BL_NO) where o.cust_id='"+custId+"'";
			List<?> list3 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list3.toString())){
				Map<String, Object> map3 = (Map<String, Object>) list3.get(0);
				map.putAll(map3);
			}
			//归属机构
			temps="select b1.INSTITUTION_NAME  from OCRM_F_CI_BELONG_ORG b1  where b1.cust_id='"+custId+"'";
			List<?> list4 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list4.toString())){
				Map<String, Object> map4 = (Map<String, Object>) list4.get(0);
				map.putAll(map4);
			}
			//拜访信息VISIT_NOTE
			temps="select CUST_ID,VISIT_NOTE,TASK_NUMBER,call_time from "+
					 "(SELECT R.CUST_ID, r.VISIT_NOTE, R.TASK_NUMBER,call_time,ROW_NUMBER() OVER(PARTITION BY CUST_ID ORDER BY call_time DESC) nn "+
					  "FROM (SELECT T.CUST_ID,T.TASK_NUMBER,a.f_value AS VISIT_NOTE,t1.call_time "+
					          "FROM ocrm_f_interview_record T "+
					         "inner JOIN ocrm_f_interview_old_record T1 ON T.TASK_NUMBER = T1.TASK_NUMBER "+
					          "LEFT JOIN OCRM_SYS_LOOKUP_ITEM A ON A.F_CODE = t1.MARK_RESULT "+
					           "AND a.F_LOOKUP_ID = 'VISIT_RESULT_QS_OLD' "+
					        "UNION "+
					        "SELECT T.CUST_ID,T.TASK_NUMBER,b.f_value AS VISIT_NOTE,t2.call_time "+
					          "FROM ocrm_f_interview_record T "+
					         "inner JOIN ocrm_f_interview_NEW_record T2 ON T.TASK_NUMBER = T2.TASK_NUMBER "+
					          "LEFT JOIN OCRM_SYS_LOOKUP_ITEM b ON b.F_CODE = t2.MARK_RESULT "+
					           "AND b.F_LOOKUP_ID = 'VISIT_RESULT_QS') R "+
					 "WHERE 1 = 1 "+ 
					   "AND CUST_ID = '"+custId+"') "+
					   "where nn=1";
			List<?> list5 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list5.toString())){
				Map<String, Object> map5 = (Map<String, Object>) list5.get(0);
				map.putAll(map5);
			}
			
			List<Map<String, Object>> l1= new ArrayList<Map<String, Object>>();
			l1.add(map);
			this.json.put("data", l1);
			this.json.put("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String searchContactInfo()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select b1.cust_id,b1.cust_name,b1.faxtrade_norec_num,b1.acct_num,to_char(b3.crd_amt,'999,999,990.99') crd_amt,b3.currency,to_char(b1.DEPOSIT_BALANCE,'999,999,990.99') DEPOSIT_BALANCE, to_char(b1.loan_balance,'999,999,990.99') loan_balance, "+
					"to_char(b1.finanprd_balance,'999,999,990.99') finanprd_balance,to_char(b1.fundprd_balance,'999,999,990.99') fundprd_balance,b1.cust_integral,b1.is_netbank,b1.is_card,b1.etl_date"+
					",to_char(b1.CURRENT_BALANCE,'999,999,990.99') current_balance,to_char(b1.FIX_PERIOD_BALANCE,'999,999,990.99') fix_period_balance,to_char(b1.FINAN_DEPOSIT_BALANCE,'999,999,990.99') finan_deposit_balance"+
					",to_char(b1.CURRENT_AUM,'999,999,990.99') current_aum,to_char(b2.CURRENT_AUM,'999,999,990.99') correspod_aum,to_char(b1.CURRENT_AVG_AUM,'999,999,990.99') current_avg_aum"+
					",to_char(b1.LAST_YEAR_AUM,'999,999,990.99') last_year_aum,to_char(b1.LOAN_AMOUNT,'999,999,990.99') loan_amount,to_char(b1.DISCOUNT_AMOUNT,'999,999,990.99') discount_amount"+
					",to_char(b1.FINAN_AMOUNT,'999,999,990.99') finan_amount,to_char(b1.FACTOR_AMOUNT,'999,999,990.99') factor_amount,to_char(b1.INPORT_FINAN_AMOUNT,'999,999,990.99') inport_finan_amount"+
					",to_char(b1.TAELS_DISCOUNT,'999,999,990.99') taels_discount,to_char(b1.LIABILITIES_IN_TB,'999,999,990.99') liabilities_in_tb,to_char(b2.LIABILITIES_IN_TB,'999,999,990.99') liabilities_in_tb_sntq"+
					",to_char(b1.LIABILITIES_IN_TB_AVG,'999,999,990.99') liabilities_in_tb_avg,to_char(b1.LIABILITIES_IN_TB_SN,'999,999,990.99') liabilities_in_tb_sn"+
					",to_char(b1.UNPAY_BANK_ACCEPTANCE,'999,999,990.99') unpay_bank_acceptance,to_char(b1.UNPAY_LC,'999,999,990.99') unpay_lc,to_char(b1.UNPAY_SBLC,'999,999,990.99') unpay_sblc"+
					",to_char(b1.UNPAY_LG,'999,999,990.99') unpay_lg,to_char(b1.UNCLOSE_FOREIGN_EXCHANGE,'999,999,990.99') unclose_foreign_exchange,to_char(b1.LIABILITIES_OFF_TB,'999,999,990.99') liabilities_off_tb"+
					",to_char(b2.LIABILITIES_OFF_TB,'999,999,990.99') liabilities_off_tb_sntq,to_char(b1.LIABILITIES_OFF_TB_AVG,'999,999,990.99') liabilities_off_tb_avg"+
					",to_char(b1.LIABILITIES_OFF_TB_SN,'999,999,990.99') liabilities_off_tb_sn,to_char(b1.TOTAL_SALE,'999,999,990.99') total_sale,to_char(b1.TOTAL_SALE_SN,'999,999,990.99') total_sale_sn"+
					",to_char(b1.TOTAL_UNDERTAKE,'999,999,990.99') total_undertake,to_char(b1.TOTAL_UNDERTAKE_SN,'999,999,990.99') total_undertake_sn,to_char(b1.TOTAL_INCOME,'999,999,990.99') total_income"+
					",to_char(h.TOTAL_INCOME_SN,'999,999,990.99') total_income_sn,c1.RISK_LEVEL," +
					" to_char(R.WEIGHT_RATE,'999,999,990.999999') AS WB_WEIGHT_RATE,  " +
					" to_char(R1.WEIGHT_RATE,'999,999,990.999999') AS RMB_WEIGHT_RATE," +
					" to_char(R3.WEIGHT_RATE,'999,999,990.999999')  AS WB_LOAN_RATE," +
					" to_char(R4.WEIGHT_RATE,'999,999,990.999999') AS RMB_LOAN_RATE  " +
					" from ACRM_A_CI_GATH_BUSINESS b1 " +
					" LEFT join ACRM_A_CI_GATH_BUSINESS_his b2 on b1.cust_id=b2.cust_id and ADD_MONTHS(b1.ETL_DATE, -12)=b2.etl_date " +
					" LEFT join ACRM_A_CI_GATH_BUSINESS_his h on b1.cust_id=h.cust_id and TRUNC(b1.etl_date,'yyyy')=h.etl_date " +
					" left join (select * from ACRM_F_CI_CRE_CONTRACT where limit_no = '0' ) b3 on b1.cust_id = b3.cust_id " +
					" left join ACRM_F_CI_CUSTOMER c1 on b1.cust_id=c1.cust_id " +
					" left join ACRM_F_CI_WEIGHT_AVE_RATE R" +
					" on b1.cust_id = R.Cust_Id" +
					" AND R.MONEY_TYPE = '0' AND R.FLAG = '1'" +
					" left join ACRM_F_CI_WEIGHT_AVE_RATE R1" +
					" on b1.cust_id = R1.Cust_Id" +
					" AND R1.MONEY_TYPE='1' AND R1.FLAG='1'" +
					" left join ACRM_F_CI_WEIGHT_AVE_RATE R3" +
					" on b1.cust_id = R3.Cust_Id" +
					" AND R3.MONEY_TYPE='0' AND R3.FLAG='0'" +
					" left join ACRM_F_CI_WEIGHT_AVE_RATE R4" +
					" on b1.cust_id = R4.Cust_Id" +
					" AND R4.MONEY_TYPE='1' AND R4.FLAG='0'"+
					" where b1.cust_id = '"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("RISK_LEVEL", "XD000083");
			this.json=query.getJSON();
//			this.json.put("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 对公基本信息--查询客户基本信息
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public String searchBaseInfo1(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT distinct to_char(o.last_update_tm,'yyyy-mm-dd HH:mm:ss') LAST_UPDATE_TM,o.ORG_BIZ_CUST_TYPE as BELONG_LINE_TYPE,CASE WHEN (SYSDATE-o.BUILD_DATE) > 730 THEN '0' ELSE '1' END AS IS_BUILD_NEW,"
					+ " o.ANNUAL_INCOME,o.BUILD_DATE,o.COM_HOLD_TYPE,o.CUST_ID,o.CUST_NAME,o.ECONOMIC_TYPE,o.EMPLOYEE_SCALE,o.ENT_BELONG,CASE WHEN O.ENT_SCALE_CK IS NULL THEN O.ENT_SCALE_RH ELSE O.ENT_SCALE_CK END  AS ENT_SCALE," +
							"o.LAST_UPDATE_SYS,o.LAST_UPDATE_USER,E.LINKMAN_NAME AS LEGAL_REPR_NAME,e.IDENT_TYPE AS LEGAL_REPR_IDENT_TYPE,e.IDENT_NO AS LEGAL_REPR_IDENT_NO,E.citizenship AS LEGAL_REPR_NATION_CODE,o.LOAN_CARD_NO,o.MAIN_BUSINESS,o.MAIN_INDUSTRY,o.MINOR_INDUSTRY,o.ORG_ADDR,o.ORG_CUS," +
							"o.ORG_EMAIL,o.ORG_FEX,o.ORG_HOMEPAGE,c3.contmeth_info as ORG_TEL,o.ORG_ZIPCODE,o.REMARK,o.TOTAL_ASSETS,o.TOTAL_DEBT,"
					+ " c.EN_NAME,c.IDENT_NO,c.IDENT_TYPE,c.RISK_LEVEL,"
					+ " k.CUST_ID AS KEYFLAG_CUST_ID,k.HAS_IE_RIGHT,k.IS_ASSOCIATED_PARTY,k.IS_EBANK_SIGN_CUST,k.IS_GROUP_CUST,k.IS_LIMIT_INDUSTRY,k.IS_LISTED_CORP,k.IS_RURAL,k.IS_SMALL_CORP,k.IS_SOE,k.IS_TAIWAN_CORP,k.UDIV_FLAG,"
					+ " r.BUSINESS_SCOPE,r.FACT_CAPITAL,r.FACT_CAPITAL_CURR,r.REGISTER_ADDR,r.REGISTER_AREA,r.REGISTER_CAPITAL,r.REGISTER_CAPITAL_CURR,r.REGISTER_ZIPCODE,"
					+ " b.MANAGE_STAT,b.WORK_FIELD_AREA,b.WORK_FIELD_OWNERSHIP,"
					//+ " C7.CUST_MANAGER_NAME,"
					+ " case when BT.F_VALUE is null then o.main_industry else BT.F_VALUE end AS MAIN_INDUSTRY_NAME,"
					+ " case when BT2.F_VALUE is null then o.minor_industry else BT2.F_VALUE end as MINOR_INDUSTRY_NAME," 
					+ " c9.ORG_AUTH_ID,C9.AUTH_TYPE "
					+ " FROM ACRM_F_CI_ORG o" 
					+ " left join ACRM_F_CI_ORG_EXECUTIVEINFO e on e.LINKMAN_TYPE = '5' and e.ORG_CUST_ID = o.CUST_ID" 
					+ " left join (select cust_id,contmeth_info from ACRM_F_CI_CONTMETH where 1=1 and contmeth_type = '2031') c3 on o.cust_id = c3.cust_id"
					+ " inner join ACRM_F_CI_CUSTOMER c on o.cust_id= c.cust_id " //客户表
					+ " left join ACRM_F_CI_ORG_KEYFLAG k on o.cust_id = k.cust_id " //机构客户重要标志表
					+ " left join ACRM_F_CI_ORG_REGISTERINFO r on o.cust_id = r.cust_id " //注册信息
					+ " left join ACRM_F_CI_ORG_BUSIINFO b on o.cust_id = b.cust_id " //机构经营信息
					
					//+ " left join OCRM_F_CI_BELONG_CUSTMGR C6 on o.cust_id = C6.cust_id " //归属客户经理
					//+ " left join OCRM_F_CM_CUST_MGR_INFO C7 on C6.MGR_ID = C7.CUST_MANAGER_ID " //客户经理信息
					
					+ " left join ACRM_F_CI_ORG_AUTH C9 on o.cust_id = C9.cust_id " //资质认证表
					+ " left join ACRM_F_CI_BUSI_TYPE BT ON BT.F_CODE=o.main_industry" 
					+ " left join ACRM_F_CI_BUSI_TYPE BT2 ON BT2.F_CODE=o.minor_industry" 
					+ " WHERE o.CUST_ID='"+custId+"' ");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			List<?> list = (List<?>) new QueryHelper(sb.toString(), ds.getConnection()).getJSON().get("data");
			Map<String, Object> map=new HashMap<String,Object>();
			if(list != null && list.size() > 0){
				map = (Map<String, Object>) list.get(0);
			}
			String temps="";
			
			//查询客户归属区域中心
			temps= "SELECT (case when t2.org_level='2' then t2.org_name " +
					" when t2.org_level='3' then (select t3.org_name from ADMIN_AUTH_ORG t3 where  t3.org_id=t2.up_org_id ) end ) BELONG_ORG from OCRM_F_CI_BELONG_ORG t1 left join ADMIN_AUTH_ORG t2 " +
					" on t1.INSTITUTION_CODE=t2.ORG_ID where t1.cust_id ='"+custId+"'";
			List<?> list2 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list2.toString())){
				Map<String, Object> map2 = (Map<String, Object>) list2.get(0);
				map.putAll(map2);
			}else{
				map.put("BELONG_ORG", "");
			}
			
			//查询客户归属客户群
			temps="select wm_concat(b2.cust_base_name) CUST_BASE_NAME from OCRM_F_CI_RELATE_CUST_BASE b1 inner join OCRM_F_CI_BASE b2 on concat('C',b1.CUST_BASE_ID)=b2.CUST_BASE_NUMBER  where b1.cust_id='"+custId+"'";
			List<?> list3 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list3.toString())){
				Map<String, Object> map3 = (Map<String, Object>) list3.get(0);
				map.putAll(map3);
			}else{
				map.put("CUST_BASE_NAME", "");
			}
			//查询客户归属集团
			temps="select wm_concat(b2.GROUP_NAME) GROUP_NAME from OCRM_F_CI_GROUP_MEMBER b1 inner join OCRM_F_CI_GROUP_INFO b2 on b1.GROUP_NO=b2.GROUP_NO  where b1.cust_id='"+custId+"'";
			List<?> list4 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list4.toString())){
				Map<String, Object> map4 = (Map<String, Object>) list4.get(0);
				map.putAll(map4);
			}else{
				map.put("GROUP_NAME", "");
			}
			//查询客户证件有效期
			temps="select b1.IDENT_VALID_PERIOD from ACRM_F_CI_CUST_IDENTIFIER b1 inner join ACRM_F_CI_CUSTOMER b2 on b1.IDENT_TYPE=b2.IDENT_TYPE and b1.ident_no=b2.ident_no where b1.cust_id='"+custId+"'";
			List<?> list5 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list5.toString())){
				Map<String, Object> map5 = (Map<String, Object>) list5.get(0);
				map.putAll(map5);
			}else{
				map.put("IDENT_VALID_PERIOD", "");
			}
			//反洗钱风险等级
			temps="select b1.RISK_LEVEL as FXQ_RISK_LEVEL  from OCRM_F_CI_ANTI_CUST_LIST b1  where b1.cust_id='"+custId+"'";
			List<?> list6 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list6.toString())){
				Map<String, Object> map6 = (Map<String, Object>) list6.get(0);
				map.putAll(map6);
			}else{
				map.put("FXQ_RISK_LEVEL", "");
			}
			//归属客户经理
			temps="SELECT  T.MGR_ID  AS CUST_MANAGER_NO,T1.USER_NAME AS CUST_MANAGER_NAME, T2.USER_NAME  AS EMP_NAME "+

					"FROM OCRM_F_CI_BELONG_CUSTMGR T "+

					"LEFT JOIN ADMIN_AUTH_ACCOUNT T1 ON T.MGR_ID=T1.ACCOUNT_NAME "+

					"LEFT JOIN ADMIN_AUTH_ACCOUNT T2 ON T1.BELONG_TEAM_HEAD =T2.ACCOUNT_NAME "+

					"WHERE T.CUST_ID='"+custId+"'";
			List<?> list7 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list7.toString())){
				Map<String, Object> map7 = (Map<String, Object>) list7.get(0);
				map.putAll(map7);
			}else{
				map.put("CUST_MANAGER_NO", "");
				map.put("CUST_MANAGER_NAME", "");
				map.put("EMP_NAME", "");
			}
			//归属业务条线
//			temps="select b1.BELONG_LINE_TYPE  from ACRM_F_CI_BELONG_LINE b1  where b1.cust_id='"+custId+"'";//ACRM_F_CI_BELONG_LINE 已经不使用了
			temps="select l.BL_NAME BELONG_LINE from acrm_f_ci_org o left join ACRM_F_CI_BUSI_LINE l on o.org_biz_cust_type = to_char(L.BL_NO) where o.cust_id='"+custId+"'";
			List<?> list8 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list8.toString())){
				Map<String, Object> map8 = (Map<String, Object>) list8.get(0);
				map.putAll(map8);
			}else{
				map.put("BELONG_LINE", "");
			}
			//客户财务信息及营销策略,地址信息
			temps="SELECT B1.ID AS FIN_ID,B1.CUST_EN_ADDR FROM ACRM_F_CI_FINA_BUSI B1 WHERE B1.CUST_ID='"+custId+"'";
			List<?> list9 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list9.toString())){
				Map<String, Object> map9 = (Map<String, Object>) list9.get(0);
				map.putAll(map9);
			}else{
				map.put("FIN_ID", "");
				map.put("CUST_EN_ADDR", "");
			}
			//客户评级结果
			temps="select b1.GRADE_RESULT  from OCRM_F_CI_GRADE_RESULT b1  where b1.cust_id='"+custId+"'";
			List<?> list10 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list10.toString())){
				Map<String, Object> map10 = (Map<String, Object>) list10.get(0);
				map.putAll(map10);
			}else{
				map.put("GRADE_RESULT", "");
			}
			List<Map<String, Object>> l1= new ArrayList<Map<String, Object>>();
			l1.add(map);
			this.json.put("data", l1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}*/
	/**
	 * 对公基本信息2--查询客户基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String searchBaseInfo(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT distinct to_char(o.last_update_tm,'yyyy-mm-dd HH:mm:ss') LAST_UPDATE_TM,o.ORG_BIZ_CUST_TYPE as BELONG_LINE_TYPE,CASE WHEN (SYSDATE-o.BUILD_DATE) > 730 THEN '0' ELSE '1' END AS IS_BUILD_NEW,"
					+ " o.ANNUAL_INCOME,o.BUILD_DATE,o.COM_HOLD_TYPE,o.CUST_ID,o.CUST_NAME,o.ECONOMIC_TYPE,o.EMPLOYEE_SCALE,o.ENT_BELONG,CASE WHEN O.ENT_SCALE_CK IS NULL THEN O.ENT_SCALE_RH ELSE O.ENT_SCALE_CK END  AS ENT_SCALE," +
							"o.LAST_UPDATE_SYS,o.LAST_UPDATE_USER,E.LINKMAN_NAME AS LEGAL_REPR_NAME,e.IDENT_TYPE AS LEGAL_REPR_IDENT_TYPE,e.IDENT_NO AS LEGAL_REPR_IDENT_NO,E.citizenship AS LEGAL_REPR_NATION_CODE,o.LOAN_CARD_NO,o.MAIN_BUSINESS,o.MAIN_INDUSTRY,o.MINOR_INDUSTRY,o.ORG_ADDR,o.ORG_CUS," +
							"o.ORG_EMAIL,o.ORG_FEX,o.ORG_HOMEPAGE,c3.contmeth_info as ORG_TEL,o.ORG_ZIPCODE,o.REMARK,o.TOTAL_ASSETS,o.TOTAL_DEBT,"
					+ " c.EN_NAME,c.IDENT_NO,c.IDENT_TYPE,c.RISK_LEVEL,"
					+ " k.CUST_ID AS KEYFLAG_CUST_ID,k.HAS_IE_RIGHT,k.IS_ASSOCIATED_PARTY,k.IS_EBANK_SIGN_CUST,k.IS_GROUP_CUST,k.IS_LIMIT_INDUSTRY,k.IS_LISTED_CORP,k.IS_RURAL,k.IS_SMALL_CORP,k.IS_SOE,k.IS_TAIWAN_CORP,k.UDIV_FLAG,"
					+ " r.BUSINESS_SCOPE,r.FACT_CAPITAL,r.FACT_CAPITAL_CURR,r.REGISTER_ADDR,r.REGISTER_AREA,r.REGISTER_CAPITAL,r.REGISTER_CAPITAL_CURR,r.REGISTER_ZIPCODE,"
					+ " b.MANAGE_STAT,b.WORK_FIELD_AREA,b.WORK_FIELD_OWNERSHIP,"
					//+ " C7.CUST_MANAGER_NAME,"
					+ " case when BT.F_VALUE is null then o.main_industry else BT.F_VALUE end AS MAIN_INDUSTRY_NAME,"
					+ " case when BT2.F_VALUE is null then o.minor_industry else BT2.F_VALUE end as MINOR_INDUSTRY_NAME," 
					+ " c9.ORG_AUTH_ID,C9.AUTH_TYPE "
					+ " FROM ACRM_F_CI_ORG o" 
					+ " left join ACRM_F_CI_ORG_EXECUTIVEINFO e on e.LINKMAN_TYPE = '5' and e.ORG_CUST_ID = o.CUST_ID" 
					+ " left join (select cust_id,contmeth_info from ACRM_F_CI_CONTMETH where 1=1 and contmeth_type = '2031') c3 on o.cust_id = c3.cust_id"
					+ " inner join ACRM_F_CI_CUSTOMER c on o.cust_id= c.cust_id " //客户表
					+ " left join ACRM_F_CI_ORG_KEYFLAG k on o.cust_id = k.cust_id " //机构客户重要标志表
					+ " left join ACRM_F_CI_ORG_REGISTERINFO r on o.cust_id = r.cust_id " //注册信息
					+ " left join ACRM_F_CI_ORG_BUSIINFO b on o.cust_id = b.cust_id " //机构经营信息
					
					//+ " left join OCRM_F_CI_BELONG_CUSTMGR C6 on o.cust_id = C6.cust_id " //归属客户经理
					//+ " left join OCRM_F_CM_CUST_MGR_INFO C7 on C6.MGR_ID = C7.CUST_MANAGER_ID " //客户经理信息
					
					+ " left join ACRM_F_CI_ORG_AUTH C9 on o.cust_id = C9.cust_id " //资质认证表
					+ " left join ACRM_F_CI_BUSI_TYPE BT ON BT.F_CODE=o.main_industry" 
					+ " left join ACRM_F_CI_BUSI_TYPE BT2 ON BT2.F_CODE=o.minor_industry" 
					+ " WHERE o.CUST_ID='"+custId+"' ");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			List<?> list = (List<?>) new QueryHelper(sb.toString(), ds.getConnection()).getJSON().get("data");
			Map<String, Object> map=new HashMap<String,Object>();
			if(list != null && list.size() > 0){
				map = (Map<String, Object>) list.get(0);
			}
			String temps="";
			
			
			temps= "SELECT A.*, （SELECT(CASE   WHEN T2.ORG_LEVEL = '2' THEN  T2.ORG_NAME   WHEN T2.ORG_LEVEL = '3' THEN"+
					"   (SELECT T3.ORG_NAME    FROM ADMIN_AUTH_ORG T3   WHERE T3.ORG_ID = T2.UP_ORG_ID)  END) BELONG_ORG    "+
					"  FROM OCRM_F_CI_BELONG_ORG T1    LEFT JOIN ADMIN_AUTH_ORG T2   ON T1.INSTITUTION_CODE = T2.ORG_ID "+
					" WHERE T1.CUST_ID = '"+custId+"' ）BELONG_ORG,  "+//查询客户归属区域中心
					" (SELECT B.GROUP_NAME    FROM (SELECT WM_CONCAT(B2.GROUP_NAME) GROUP_NAME  "+
					"   FROM OCRM_F_CI_GROUP_MEMBER B1   INNER JOIN OCRM_F_CI_GROUP_INFO B2    ON B1.GROUP_NO = B2.GROUP_NO  "+
					"  WHERE B1.CUST_ID = '"+custId+"') B) GROUP_NAME,    "+//查询客户归属客户群
					" (SELECT B1.IDENT_VALID_PERIOD  FROM ACRM_F_CI_CUST_IDENTIFIER B1 "+
					"    INNER JOIN ACRM_F_CI_CUSTOMER B2    ON B1.IDENT_TYPE = B2.IDENT_TYPE    AND B1.IDENT_NO = B2.IDENT_NO    "+
					"    WHERE B1.CUST_ID = '"+custId+"') IDENT_VALID_PERIOD,  "+//查询客户证件有效期
					" (SELECT B1.RISK_LEVEL AS FXQ_RISK_LEVEL FROM OCRM_F_CI_ANTI_CUST_LIST B1   WHERE B1.CUST_ID = '"+custId+"') FXQ_RISK_LEVEL, "+//反洗钱风险等级
					" (SELECT L.BL_NAME BELONG_LINE    FROM ACRM_F_CI_ORG O    "+
					" LEFT JOIN ACRM_F_CI_BUSI_LINE L  ON O.ORG_BIZ_CUST_TYPE = TO_CHAR(L.BL_NO)  "+
					"    WHERE O.CUST_ID = '"+custId+"') BELONG_LINE,"+//归属业务条线
					" (SELECT B1.GRADE_RESULT    FROM OCRM_F_CI_GRADE_RESULT B1    "+
					"    WHERE B1.CUST_ID = '"+custId+"'  AND B1.RESULT_ID ="+
					" (SELECT MAX(B2.RESULT_ID)    FROM OCRM_F_CI_GRADE_RESULT B2  "+
					"  WHERE B1.CUST_ID = B2.CUST_ID)) GRADE_RESULT FROM    "+
					" (SELECT WM_CONCAT(B2.CUST_BASE_NAME) CUST_BASE_NAME    FROM OCRM_F_CI_RELATE_CUST_BASE B1"+
					"    INNER JOIN OCRM_F_CI_BASE B2    ON CONCAT('C', B1.CUST_BASE_ID) = B2.CUST_BASE_NUMBER"+
					"    WHERE B1.CUST_ID = '"+custId+"') A"
;
			List<?> list2 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list2.toString())){
				Map<String, Object> map2 = (Map<String, Object>) list2.get(0);
				map.putAll(map2);
			}else{
				map.put("BELONG_ORG", "");
				map.put("CUST_BASE_NAME", "");
				map.put("GROUP_NAME", "");
				map.put("IDENT_VALID_PERIOD", "");
				map.put("FXQ_RISK_LEVEL", "");
				map.put("BELONG_LINE", "");
			}

			//归属客户经理
			temps="SELECT  T.MGR_ID  AS CUST_MANAGER_NO,T1.USER_NAME AS CUST_MANAGER_NAME, T2.USER_NAME  AS EMP_NAME "+

					"FROM OCRM_F_CI_BELONG_CUSTMGR T "+

					"LEFT JOIN ADMIN_AUTH_ACCOUNT T1 ON T.MGR_ID=T1.ACCOUNT_NAME "+

					"LEFT JOIN ADMIN_AUTH_ACCOUNT T2 ON T1.BELONG_TEAM_HEAD =T2.ACCOUNT_NAME "+

					"WHERE T.CUST_ID='"+custId+"'";
			List<?> list7 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list7.toString())){
				Map<String, Object> map7 = (Map<String, Object>) list7.get(0);
				map.putAll(map7);
			}else{
				map.put("CUST_MANAGER_NO", "");
				map.put("CUST_MANAGER_NAME", "");
				map.put("EMP_NAME", "");
			}
			
			//客户财务信息及营销策略,地址信息
			temps="SELECT B1.ID AS FIN_ID,B1.CUST_EN_ADDR FROM ACRM_F_CI_FINA_BUSI B1 WHERE B1.CUST_ID='"+custId+"'";
			List<?> list9 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list9.toString())){
				Map<String, Object> map9 = (Map<String, Object>) list9.get(0);
				map.putAll(map9);
			}else{
				map.put("FIN_ID", "");
				map.put("CUST_EN_ADDR", "");
			}
			//客户评级结果
			temps="SELECT B1.GRADE_RESULT  FROM OCRM_F_CI_GRADE_RESULT B1 "+
					" WHERE B1.CUST_ID = '"+custId+"'  "+
					"   AND B1.RESULT_ID = (SELECT MAX(B2.RESULT_ID)        "+
					"     FROM OCRM_F_CI_GRADE_RESULT B2                    "+
					"     WHERE B1.CUST_ID = B2.CUST_ID)                    ";
			List<?> list10 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list10.toString())){
				Map<String, Object> map10 = (Map<String, Object>) list10.get(0);
				map.putAll(map10);
			}else{
				map.put("GRADE_RESULT", "");
			}
			List<Map<String, Object>> l1= new ArrayList<Map<String, Object>>();
			l1.add(map);
			this.json.put("data", l1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	/**
	 * 对公基本信息--查询股东信息列表
	 */
	public void searchShareholder(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT t.CUST_ID,t.HOLDER_NAME,t.IDENT_TYPE,t.IDENT_NO from ACRM_F_CI_ORG_HOLDERINFO t where t.cust_id ='"+custId+"'");
    	if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
		try {
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("IDENT_TYPE", "XD000040");
			this.json = query.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @deprecated
	 * 客户信息修改历史见公用方法调用
	 * @return
	 */
	public String searchHis(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append(" select c.*,t.USER_NAME from OCRM_F_CI_CUSTINFO_UPHIS  c  LEFT JOIN ADMIN_AUTH_ACCOUNT t on c.UPDATE_USER = t.ACCOUNT_NAME  " +
					" WHERE c.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			String ss= sb.toString();
			this.json=new QueryHelper(ss, ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
