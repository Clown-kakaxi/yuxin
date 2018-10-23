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
import com.yuchengtech.crm.constance.SystemConstance;
/**
 * 
 * @ClassName: PrivateCustInfoAction 
 * @Description: 对私零售客户基本信息查询
 * @author wangmk1 
 * @date 2014-7-23 下午3:06:54 
 *
 */
@Action("/privateCustInfo")
public class PrivateCustInfoAction extends CommonAction {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	public String searchBasicInfo()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			//2018-02-02崔恒薇修改：名单下发的新增客户能够查到数据
			sb.append("select * from (" +
					" SELECT CUST.SOURCE_CHANNEL,NVL(T.PERSONAL_NAME,MGRCUS.CUST_NAME) PERSONAL_NAME,T.CITIZENSHIP,T.MARRIAGE,T.CAREER_TYPE,T.BIRTHDAY,T.UNIT_NAME,T.HOME_ADDR,T.EMAIL,NVL(CON.CONTMETH_INFO,MGRCUS.TEL_NO) AS MOBILE_PHONE,")
			.append(" to_char(t.LAST_UPDATE_TM, 'yyyy-mm-dd HH:mm:ss') LUTIME,")
			.append(" CUST.CORE_NO,CID.IDENT_NO,")
			.append(" to_char(CID.IDENT_EXPIRED_DATE, 'yyyy-MM-dd') AS IDENT_EXPIRED_DATE,")
//			.append(" LK2.F_VALUE AS RISK_LEVEL,")
			.append(" T2.CUST_RISK_LEVEL_NAME RISK_LEVEL,T2.CUST_IPQ_EXPIRY_D RISK_VALID_DATE,")
			.append(" decode(UPPER(PERKEY.IS_PAYROLL_CUST),'Y','是','N','否',")
			.append(" PERKEY.IS_PAYROLL_CUST) IS_PAYROLL_CUST,")
			.append(" decode(ACT.Cust_Id, null, '注销', '正常') HOLD_ACCT_ORA,")
			//.append(" LK1.F_VALUE AS HOLD_CARD_ORA,")
			
			.append(" ( SELECT LISTAGG(LK.F_VALUE, ',') WITHIN GROUP(ORDER BY NULL) cardtype FROM OCRM_SYS_LOOKUP_ITEM LK WHERE LK.F_CODE IN (SELECT to_char(DETAIL1.CARD_TYPE)")
			.append(" FROM OCRM_F_CI_CARD_DETAIL_INFO DETAIL1")
            .append(" WHERE DETAIL1.CUST_ID = '"+custId+"') AND LK.F_LOOKUP_ID = 'CARD_TYPE')  AS HOLD_CARD_ORA,")
            
			.append(" CUST.CREATE_DATE,CID2.IDENT_NO AS IDENT_NO2,to_char(CID2.IDENT_EXPIRED_DATE, 'yyyy-MM-dd') AS IDENT_EXPIRED_DATE2")
			//2018-02-02崔恒薇修改：名单下发的新增客户能够查到数据
			.append(" FROM (SELECT '"+custId+"' AS cust_id FROM dual) d ")
			.append(" LEFT JOIN ACRM_F_CI_PERSON t ON d.cust_id=t.cust_id ")
			.append(" left join ACRM_F_CI_CONTMETH CON ON CON.CUST_ID=d.CUST_ID AND CON.CONTMETH_TYPE='102'")
			.append(" LEFT JOIN ACRM_F_CI_CUSTOMER CUST")
			.append(" ON CUST.CUST_ID = d.CUST_ID")
			.append(" LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER CID")
			.append(" ON CID.CUST_ID = d.CUST_ID")
			.append(" AND CID.IS_OPEN_ACC_IDENT = 'Y'")
			.append(" LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER CID2")
			.append(" ON CID2.CUST_ID = d.CUST_ID")
			.append(" AND CID2.IDENT_TYPE IN ('X3','X24','5','6')")
			.append(" LEFT JOIN ACRM_F_CI_PER_KEYFLAG PERKEY")
			.append(" ON PERKEY.CUST_ID = d.CUST_ID")
			.append(" LEFT JOIN ACRM_F_CI_DEPOSIT_ACT ACT")
			.append(" ON ACT.CUST_ID = d.CUST_ID")
			
			
			//.append(" LEFT JOIN (select cust_id,max(CARD_TYPE) as CARD_TYPE from OCRM_F_CI_CARD_DETAIL_INFO DETAIL1 where  DETAIL1.Cust_Id= '"+custId+"' group by cust_id) DETAIL")
			//.append(" ON DETAIL.CUST_ID = CUST.CUST_ID")
			//.append(" LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK1")
			//.append(" ON LK1.F_LOOKUP_ID = 'CARD_TYPE'")
			//.append(" AND LK1.F_CODE = DETAIL.CARD_TYPE")
			
			
//			.append(" LEFT JOIN OCRM_F_CI_ANTI_CUST_LIST T2" )//--反洗钱风险客户
//		    .append(" ON T2.cust_id = d.CUST_ID")
			.append(" LEFT JOIN ACRM_O_CUST_IPQ T2" )//--客户风险等级
		    .append(" ON T2.CUST_ID = CUST.IDENT_NO AND T2.CUST_ID_TYPE_CODE = CUST.IDENT_TYPE")
			 
//			.append(" LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK2")
			//.append(" AND LK2.F_CODE = CUST.RISK_LEVEL")
//			.append(" AND LK2.F_CODE = T2.RISK_LEVEL")
			
			//2018-02-02崔恒薇修改：名单下发的新增客户能够查到数据
			.append(" LEFT JOIN OCRM_F_MGR_CUS mgrcus ON mgrcus.cust_id=d.cust_id ")
			/*sb.append(" SELECT t.*,to_char(t.LAST_UPDATE_TM, 'yyyy-mm-dd HH:mm:ss') LUTIME, ")
			.append(" to_char(t.ANNUAL_INCOME, '999,999,990.99') PER_ANNUAL_INCOME, ")
			.append("PREF.CONTACT_FREQ_PREFER AS CONT_FREQ,")
			.append("       LK.F_VALUE AS FAMILY_ANN_INC_SCOPE, CUST.CORE_NO,CID.IDENT_NO,")
			.append("       to_char(CID.IDENT_EXPIRED_DATE, 'yyyy-MM-dd') AS IDENT_EXPIRED_DATE,")
			.append("      LK2.F_VALUE AS RISK_LEVEL,")
			.append("       PER.UNIT_NAME,")
			.append("      CASE WHEN UPPER(PERKEY.IS_PAYROLL_CUST) = 'Y' THEN '是' ")
			.append("        WHEN UPPER(PERKEY.IS_PAYROLL_CUST) = 'N' THEN '否' ")
			.append("          ELSE PERKEY.IS_PAYROLL_CUST END IS_PAYROLL_CUST")
			.append("     ,CASE WHEN ACT.Cust_Id IS NOT NULL THEN '正常' ELSE '注销'  END HOLD_ACCT_ORA,")
			.append("     LK1.F_VALUE AS HOLD_CARD_ORA")
			.append("  FROM ACRM_F_CI_PERSON t")
			.append("  LEFT JOIN ACRM_F_CI_PER_PREFERENCE PREF")
			.append("    ON T.CUST_ID = PREF.CUST_ID")
			.append("  LEFT JOIN ACRM_F_CI_PER_FAMILY F")
			.append("    ON F.CUST_ID = T.CUST_ID")
			.append("  LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK")
			.append("    ON LK.F_LOOKUP_ID = 'XD000149'")
			.append("   AND LK.F_CODE = F.FAMILY_ANN_INC_SCOPE")
			.append("  LEFT JOIN ACRM_F_CI_CUSTOMER CUST")
			.append("    ON CUST.CUST_ID = T.CUST_ID")
			.append("  LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER CID")
			.append("    ON CID.CUST_ID = T.CUST_ID")
			.append("   AND CID.IS_OPEN_ACC_IDENT = 'Y'")
			.append("  LEFT JOIN ACRM_F_CI_PERSON PER")
			.append("    ON PER.CUST_ID = T.CUST_ID")
			.append("  LEFT JOIN ACRM_F_CI_PER_KEYFLAG PERKEY")
			.append("    ON PERKEY.CUST_ID = T.CUST_ID")
			.append(" LEFT JOIN ACRM_F_CI_DEPOSIT_ACT ACT ON  ACT.CUST_ID = T.CUST_ID")
			.append(" LEFT JOIN OCRM_F_CI_CARD_DETAIL_INFO DETAIL ON DETAIL.CUST_ID = CUST.CUST_ID")
			.append(" LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK1 ON LK1.F_LOOKUP_ID = 'CARD_TYPE' AND LK1.F_CODE = DETAIL.CARD_TYPE")
			.append(" LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK2 ON LK2.F_LOOKUP_ID = 'FXQ_RISK_LEVEL' AND LK2.F_CODE = CUST.RISK_LEVEL  ")*/
			
			/*.append(" WHERE t.CUST_ID = '210000141201' AND ROWNUM =1")
			sb.append("SELECT t.*, to_char(t.LAST_UPDATE_TM,'yyyy-mm-dd HH:mm:ss') LUTIME  ,to_char(t.ANNUAL_INCOME,'999,999,990.99') PER_ANNUAL_INCOME,PREF.CONTACT_FREQ_PREFER AS CONT_FREQ ,LK.F_VALUE AS FAMILY_ANN_INC_SCOPE,CUST.CORE_NO,CID.IDENT_NO,to_char(CID.IDENT_EXPIRED_DATE,'yyyy-MM-dd') AS IDENT_EXPIRED_DATE,CUST.RISK_LEVEL,PER.UNIT_NAME,PERKEY.IS_PAYROLL_CUST ");
			sb.append(" ,CASE WHEN ACT.Cust_Id IS NOT NULL THEN '正常' ELSE '注销' END HOLD_ACCT_ORA, LK1.F_VALUE AS HOLD_CARD_ORA ");
			sb.append(" FROM ACRM_F_CI_PERSON t ");
			sb.append(" LEFT JOIN ACRM_F_CI_PER_PREFERENCE PREF ON T.CUST_ID = PREF.CUST_ID ");
			sb.append(" LEFT JOIN ACRM_F_CI_PER_FAMILY F ON F.CUST_ID = T.CUST_ID");
			sb.append(" LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK ON LK.F_LOOKUP_ID='XD000149' AND LK.F_CODE=F.FAMILY_ANN_INC_SCOPE");
			sb.append(" LEFT JOIN ACRM_F_CI_CUSTOMER CUST ON CUST.CUST_ID=T.CUST_ID");
			sb.append(" LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER CID ON CID.CUST_ID=T.CUST_ID AND CID.IS_OPEN_ACC_IDENT='Y'");
			sb.append(" LEFT JOIN ACRM_F_CI_PERSON PER ON PER.CUST_ID=T.CUST_ID");
			//add on 2017-12-14
			sb.append(" LEFT JOIN ACRM_F_CI_PER_KEYFLAG PERKEY ON PERKEY.CUST_ID=T.CUST_ID");
			sb.append(" LEFT JOIN ACRM_F_CI_DEPOSIT_ACT ACT ON ACT.CUST_ID = CUST.CUST_ID ");
			sb.append(" LEFT JOIN OCRM_F_CI_CARD_DETAIL_INFO DETAIL ON DETAIL.CUST_ID = CUST.CUST_ID ");
			sb.append(" LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK1 ON LK1.F_LOOKUP_ID = 'CARD_TYPE' AND LK1.F_CODE = DETAIL.CARD_TYPE ");*/
			//2018-02-02崔恒薇修改：名单下发的新增客户能够查到数据
			.append(" order by t2.CUST_IPQ_EXPIRY_D desc ) WHERE ROWNUM =1 ");
//					+ " FROM ACRM_F_CI_PERSON t "
//					+ " LEFT JOIN ACRM_F_CI_PER_PREFERENCE PREF ON T.CUST_ID = PREF.CUST_ID "
//					+ " LEFT JOIN ACRM_F_CI_PER_FAMILY F ON F.CUST_ID = T.CUST_ID"
//					+ " LEFT JOIN OCRM_SYS_LOOKUP_ITEM LK ON LK.F_LOOKUP_ID='XD000149' AND LK.F_CODE=F.FAMILY_ANN_INC_SCOPE"
//					+ " LEFT JOIN ACRM_F_CI_CUSTOMER CUST ON CUST.CUST_ID=T.CUST_ID"
//					+ " LEFT JOIN ACRM_F_CI_CUST_IDENTIFIER CID ON CID.CUST_ID=T.CUST_ID AND CID.IS_OPEN_ACC_IDENT='Y'"
//					+ " LEFT JOIN ACRM_F_CI_PERSON PER ON PER.CUST_ID=T.CUST_ID"
//					+ " WHERE t.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
//			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());

			query.addOracleLookup("SOURCE_CHANNEL", "XD000353");
			query.addOracleLookup("CAREER_TYPE", "XD000005");
			query.addOracleLookup("CITIZENSHIP", "XD000025");
			query.addOracleLookup("MARRIAGE", "XD000024");
			query.addOracleLookup("HOLD_ACCT", "XD000013");
			query.addOracleLookup("HOLD_CARD", "XD000026");
			query.addOracleLookup("FAMILY_ANN_INC_SCOPE", "XD000050");
			query.addOracleLookup("CONT_FREQ", "XD000252");
			this.json=query.getJSON();
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
			sb.append("select c.SOURCE_CHANNEL ,c.risk_level,c.RISK_VALID_DATE,mgr.mgr_id,mgr.mgr_name,to_char(a3.CURRENT_AUM,'999,999,990.99') as AUM_BALANCE "+
		             ",a3.IS_NETBANK,a3.is_card,to_char(a3.current_balance,'999,999,990.99') as current_balance,to_char(a3.fix_period_balance,'999,999,990.99') as fix_period_balance,to_char(a3.FINAN_DEPOSIT_BALANCE,'999,999,990.99') as FINAN_DEPOSIT_BALANCE,to_char(a3.loan_amount,'999,999,990.99') as loan_amount, "+
		             "to_char(A3.DEPOSIT_BALANCE,'999,999,990.99') as DEPOSIT_BALANCE,to_char(AA.MORT_LOAN,'999,999,990.99') as MORT_LOAN,to_char(AB.COLL_LOAN,'999,999,990.99') as COLL_LOAN,to_char(AC.BW_LOANS,'999,999,990.99') as BW_LOANS,to_char(a3.LOAN_BALANCE,'999,999,990.99') as LOAN_BALANCE,PR.CONTACT_FREQ_PREFER " +
		             //2018-02-02崔恒薇修改：名单下发的新增客户能够查到数据
		             //" from ACRM_F_CI_CUSTOMER c"+
					 " FROM (SELECT 'YX-158888889' AS cust_id FROM dual) D " + 
					 " LEFT JOIN ACRM_F_CI_CUSTOMER C ON D.CUST_ID = C.CUST_ID "+
					 " left join OCRM_F_CI_BELONG_CUSTMGR mgr on mgr.cust_id = c.cust_id  " +
					 " left join ACRM_A_CI_GATH_BUSINESS a3 on a3.cust_id = c.cust_id " +
					 " LEFT JOIN (SELECT CUST_ID,SUM(BAL) AS MORT_LOAN FROM ACRM_F_CI_ASSET_BUSI_PROTO WHERE PRODUCT_ID = '100001' GROUP BY CUST_ID) AA " +
					 " ON AA.CUST_ID = C.CUST_ID " +
					 " LEFT JOIN (SELECT CUST_ID,SUM(BAL) AS COLL_LOAN FROM ACRM_F_CI_ASSET_BUSI_PROTO WHERE PRODUCT_ID = '100002' GROUP BY CUST_ID) AB " +
					 " ON AB.CUST_ID = C.CUST_ID " +
					 " LEFT JOIN (SELECT CUST_ID,SUM(BAL) AS BW_LOANS FROM ACRM_F_CI_ASSET_BUSI_PROTO WHERE PRODUCT_ID = '102' GROUP BY CUST_ID) AC " +
					 " ON AC.CUST_ID = C.CUST_ID " +
					 " LEFT JOIN ACRM_F_CI_PER_PREFERENCE PR ON PR.CUST_ID = C.CUST_ID " +
					//2018-02-02崔恒薇修改：名单下发的新增客户能够查到数据
					" LEFT JOIN OCRM_F_MGR_CUS mgrcus ON mgrcus.cust_id=D.cust_id  ");
					// " where c.cust_id = '"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("RISK_LEVEL", "XD000083");
			query.addOracleLookup("IS_CARD", "XD000327");
			query.addOracleLookup("IS_NETBANK", "IF_FLAG");
			query.addOracleLookup("SOURCE_CHANNEL", "XD000353");
			List<?> list = (List<?>)query.getJSON().get("data");
			Map<String, Object> map = (Map<String, Object>) list.get(0);
//			String temps="";
			//查询客户满意度
			String temps="select b1.SATISFY_TYPE  from OCRM_F_SE_CUST_SATISFY_LIST b1  where b1.cust_id='"+custId+"'";
			QueryHelper	query2=new QueryHelper(temps.toString(), ds.getConnection());
			query2.addOracleLookup("SATISFY_TYPE", "SATISFY_TYPE");
			List<?> list1 = (List<?>) query2.getJSON().get("data");
			if(!"[]".equals(list1.toString())){
				Map<String, Object> map1 = (Map<String, Object>) list1.get(0);
				map.putAll(map1);
			}
			//查询产品持有数
			temps="select COUNT(1) HOLDINGS from ACRM_F_AG_AGREEMENT where cust_id = '"+custId+"'";
			List<?> list2 = (List<?>) new QueryHelper(temps, ds.getConnection()).getJSON().get("data");
			if(!"[]".equals(list2.toString())){
				Map<String, Object> map2 = (Map<String, Object>) list2.get(0);
				map.putAll(map2);
			}
			List<Map<String, Object>> l1= new ArrayList<Map<String, Object>>();
			l1.add(map);
			this.json.put("data", l1);
			this.json.put("success", true);
			
//			this.json=query.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 策略信息查询
	 * @return
	 */
	public String searchStrategyInfo(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT FB.ACC_RUN_STRATEGY,FB.PROD_ADVICE FROM ACRM_F_CI_CUSTOMER CU " +
					" LEFT JOIN ACRM_F_CI_FINA_BUSI FB ON FB.CUST_ID = CU.CUST_ID  WHERE CU.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 财务信息查询
	 * @return
	 */
	public String searchFinInfo(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT  FB.ID AS FIN_ID ,FB.IS_RETIRE_PLAN,FB.IS_HOUSE_PLAN,FB.IS_EDU_PLAN,FB.ASSET_AMT,FB.FINA_INFO_IN,FB.IS_MORT,FB.HOUSE_INFO,FB.ASSET_INFO_OUT," +
					" FB.ACC_RUN_STRATEGY,FB.PROD_ADVICE,FB.SERVICE_ATTENTION " +
					"FROM ACRM_F_CI_CUSTOMER CU " +
					" LEFT JOIN ACRM_F_CI_FINA_BUSI FB ON FB.CUST_ID = CU.CUST_ID WHERE CU.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 客户贵宾信息
	 * @return
	 */
	public String searchCardInfo(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append( "SELECT DISTINCT T.CUST_ID,T.HIGHEST_CARD_LEVEL,T.HOLD_CARD_LEVEL,T.IS_STANDARD,T.IS_VIP_CUST,T.IS_FLIGHT_INSURANCE  FROM OCRM_F_CI_CARD_TOTAL T WHERE T.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String searchInfo(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT c1.CUST_ID,c1.RISK_LEVEL,c1.CUST_NAME,c1.SHORT_NAME,c1.EN_NAME,c1.ident_type,c1.ident_no,c1.INDUST_TYPE,BT.F_VALUE AS INDUST_TYPE_NAME, "
					+ " c1.SOURCE_CHANNEL,c1.FAXTRADE_NOREC_NUM,c1.INOUT_FLAG, s1.SATISFY_TYPE,"
					+ " c2.PERSON_TITLE,c2.BIRTHDAY,c2.BIRTHLOCALE,c2.PINYIN_NAME,c2.GENDER,c2.CITIZENSHIP,c2.NATIONALITY,"+       
					" c2.MARRIAGE,c2.MOBILE_PHONE,c2.EMAIL,c2.WEIXIN,c2.HOME_ADDR,c2.HOME_ZIPCODE,c2.HOME_TEL,c2.HIGHEST_SCHOOLING,"+ 
					" c2.GRADUATE_SCHOOL,c2.CAREER_TYPE,c2.PROFESSION,c2.UNIT_NAME,c2.UNIT_ADDR,c2.UNIT_ZIPCODE,c2.UNIT_TEL,"+          
					" c2.UNIT_FEX,c2.DUTY,c2.ANNUAL_INCOME,c2.LAST_DEALINGS_DESC,F.FAMILY_ANN_INC_SCOPE,f.id as FAMILY_ID,PR.CUST_ID as PREFERENCE_ID,PR.CONTACT_FREQ_PREFER "+ 
					" FROM ACRM_F_CI_CUSTOMER c1" +
					" left join ACRM_F_CI_PERSON c2 on c1.cust_id=c2.cust_id" +
					" left join OCRM_F_SE_CUST_SATISFY_LIST s1 on s1.cust_id=c1.cust_id " +
					" LEFT JOIN ACRM_F_CI_PER_FAMILY F ON F.CUST_ID = c1.CUST_ID " +
					" LEFT JOIN ACRM_F_CI_PER_PREFERENCE PR ON PR.CUST_ID = C1.CUST_ID" +
					" LEFT JOIN ACRM_F_CI_BUSI_TYPE BT ON BT.F_CODE=c1.INDUST_TYPE " +
					" WHERE c1.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String searchBusiInfo()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT p2.cust_id as KEY_CUST_ID,p2.IS_ON_JOB_WORKER,p2.IS_PAYROLL_CUST,p2.USA_TAX_FLAG, "+
					"p2.IS_DIVIDEND_CUST,p2.HAS_PHOTO,c2.*,p1.RECEIVE_SMS_FLAG,c1.USA_TAX_IDEN_NO USTIN "
					+ " FROM ACRM_F_CI_PERSON c1 " +
					" left join ACRM_A_CI_VIP_CUST c2 on c1.cust_id=c2.cust_id " +
					" left join ACRM_F_CI_PER_PREFERENCE p1 on p1.cust_id=c1.cust_id " +
					" left join ACRM_F_CI_PER_KEYFLAG p2 on c1.cust_id =p2.cust_id " +
					" WHERE c1.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
//			query.addOracleLookup("IS_VALIDATE", "IF_FLAG");
//			query.addOracleLookup("H_CARD_TYPE", "CARD_TYPE");
			this.json=query.getJSON();
			
//			this.json.put("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	public String searchCredit()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT * FROM OCRM_F_CI_CARD_DETAIL_INFO c1 " +
					" WHERE c1.etl_date = (select max(etl_date) from OCRM_F_CI_CARD_DETAIL_INFO) and c1.CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("GET_WAY", "GET_WAY");
			query.addOracleLookup("CARD_TYPE", "CARD_TYPE");
			query.addOracleLookup("BUSINESS_CARD_STATE", "CARD_STATE"); 
			this.json=query.getJSON();
//			this.json.put("success", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String searchStandardHis()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT DISTINCT TO_CHAR(trunc(ETL_DATE, 'mm')-1,'YYYY/MM') as ETL_DATE," +
					"A.AUM_MONTH,A.HIGHEST_CARD_LEVEL,A.HOLD_CARD_LEVEL,A.IS_STANDARD FROM OCRM_F_CI_CARD_TOTAL_HIS A " +
					"WHERE A.ETL_DATE=trunc(ETL_DATE, 'mm') AND  A.ETL_DATE>=trunc(ETL_DATE, 'mm')-interval '12' month and A.CUST_ID='"+custId+"'" +
							"order by ETL_DATE desc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("HIGHEST_CARD_LEVEL", "CARD_TYPE");
			query.addOracleLookup("HOLD_CARD_LEVEL", "CARD_TYPE");
			query.addOracleLookup("IS_STANDARD", "IF_FLAG");
			this.json=query.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String searchHis(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append(" select c.*,t.USER_NAME from OCRM_F_CI_CUSTINFO_UPHIS  c  LEFT JOIN ADMIN_AUTH_ACCOUNT t on c.UPDATE_USER = t.ACCOUNT_NAME  " +
					" WHERE c.APPR_FLAG = '1' and c.CUST_ID='"+custId+"'");
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
	/**
	 * 修改历史查询flag
	 * @return
	 */
	public String searchHisF(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String flag = request.getParameter("flag");///改FLAG!!!
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select c.*,t.USER_NAME from OCRM_F_CI_CUSTINFO_UPHIS c LEFT JOIN ADMIN_AUTH_ACCOUNT t on c.UPDATE_USER = t.ACCOUNT_NAME  WHERE c.UPDATE_FLAG='"+flag+"'");
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
	
	public String searchBank(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append(" select c.* from OCRM_F_CI_OTHER_BANK  c " +
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
	public String searchVisit(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
    		String rownumcondition = " where rownum<4";
			if("DB2".equals(SystemConstance.DB_TYPE)){
				rownumcondition = " fetch first 3 rows only ";
			} else {
				rownumcondition = " where rownum < 4 ";
			}
			StringBuilder sb = new StringBuilder("");
			sb.append(" select * from(select t.* ,a1.user_name VISITOR2,a2.user_name ARANGE_ID2,a1.user_name USER_NAME1  from OCRM_F_WP_SCHEDULE_VISIT t " +
					" left join ADMIN_AUTH_ACCOUNT a1 on t.VISITOR=a1.account_name " +
					" left join ADMIN_AUTH_ACCOUNT a2 on t.ARANGE_ID=a2.account_name " +
					" WHERE  t.CUST_ID='"+custId+"' " +
					" order by t.SCH_EDN_TIME desc )" + rownumcondition );
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			String ss= sb.toString();
//			this.json=new QueryHelper(ss, ds.getConnection()).getJSON();
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("VISIT_TYPE", "VISIT_TYPE");
			query.addOracleLookup("VISIT_STAT", "VISIT_STAT");
			this.json=query.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	public void searchImage(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select cust_image from ACRM_F_CI_CUSTOMER where cust_id= '"+custId+"' ");
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
	}
}
