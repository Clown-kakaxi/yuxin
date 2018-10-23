package com.yuchengtech.bcrm.customer.newCustomer.action;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.FsxWholeInfo;
import com.yuchengtech.bcrm.customer.newCustomer.service.CustomerManagerNewService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description: 非授信客户信息处理
 * @author sunjing5
 * @data 2017-05-16
 */
@ParentPackage("json-default")
@Action("/dealWithCom")
public class DealWithComAction extends CommonAction{
	private static Logger log = Logger.getLogger(CustomerManagerNewAction.class);
	private static final long serialVersionUID = -1307317536382455940L;

	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private CustomerManagerNewService CustomerManagerNewService;
    
    @Autowired
    public void init(){
        model = new FsxWholeInfo();
        setCommonService(CustomerManagerNewService);
    }
	
	/**
	 * 查询客户信息第一页基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryComfsx(){
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        //查询统一客户表信息-客户表信息
			StringBuffer sb = new StringBuffer(" SELECT C.CUST_ID  ,  C.CORE_NO  ,  C.LOAN_CUST_ID  ,  C.CUST_TYPE  ,  C.SHORT_NAME   ,  C.CUST_NAME  ,  C.IDENT_TYPE   ,  C.RISK_NATION_CODE, c.FIRST_LOAN_DATE,   "+
					"    C.STAFFIN  ,  C.CREATE_DATE  ,  C.EN_NAME    ,  C.IDENT_NO   ,  C.CREATE_BRANCH_NO,  C.LOAN_CUST_STAT  ,  C.SWIFT    ,  C.LOAN_CUST_RANK  ,C.POTENTIAL_FLAG,     "+
					"    O.CUST_ID    AS ORG1_CUST_ID     ,   O.ORG_CUST_TYPE    ,   O.FLAG_CAP_DTL   ,   O.LOAN_ORG_TYPE   ,   O.NATION_CODE    ,  O.IN_CLL_TYPE as IN_CLL_TYPE_ID  ,     "+
					"    O.EMPLOYEE_SCALE   ,   O.INVEST_TYPE   ,   O.ORG_TYPE    ,"+
					"   CASE WHEN C.CUST_STAT='A' THEN O.ENT_SCALE_CK ELSE O.ENT_SCALE END ENT_SCALE ,"+
					"   O.MAIN_BUSINESS    ,   O.CREDIT_CODE   ,   O.HQ_NATION_CODE   ,      "+
					"    O.ENT_PROPERTY  ,   O.COM_HOLD_TYPE    ,   O.BUILD_DATE   ,   O.ENT_SCALE_CK  , O.MINOR_BUSINESS   ,   O.TOTAL_ASSETS     ,   "+
					"    O.ANNUAL_INCOME    ,   O.LOAN_CARD_NO     , O.AREA_CODE,O.BUSI_LIC_NO ,O.REMARK,"+
					" 	E.LINKMAN_ID AS LEGAL_LINKMAN_ID,   E.LINKMAN_NAME    AS LEGAL_REPR_NAME, E.IDENT_NO AS LEGAL_REPR_IDENT_NO,"+
				    " 	E.IDENT_TYPE AS LEGAL_REPR_IDENT_TYPE,E.IDENT_EXPIRED_DATE AS LEGAL_IDENT_EXPIRED_DATE,  E.LINKMAN_TYPE AS LEGAL_LINKMAN_TYPE,"+
				    "	E.LAST_UPDATE_TM AS LEGAL_LAST_UPDATE_TM,  E.LAST_UPDATE_SYS AS LEGAL_LAST_UPDATE_SYS,  E.LAST_UPDATE_USER      AS LEGAL_LAST_UPDATE_USER,"+
					"    B.CUST_ID  AS BUSIINFO_CUST_ID ,   B.SALE_CCY  ,   B.SALE_AMT  ,    "+
					"    R.CUST_ID   AS REGISTER_CUST_ID,   R.REGISTER_DATE  ,   R.REGISTER_CAPITAL_CURR,   R.END_DATE  ,      "+
					"    R.REGISTER_NO ,   R.REGISTER_ADDR   ,  R.REG_CODE_TYPE   ,   R.REGISTER_TYPE   ,   R.REGISTER_CAPITAL   ,"+
					"	m.MEMBER_id as MEMBER_ID,m.grp_no AS GROUP_NO,i.GRP_NAME as BELONG_GROUP , m.cus_id as  GROUP_CUST_ID, "+
					"  I.GRP_NAME            AS BELONG_GROUP,      "+
				    "  C1.ID AS MGR_KEY_ID,C1.MGR_ID,C1.MGR_NAME AS BELONG_RM, C1.CUST_ID AS MGR_CUST_ID,                         "+
				    // modify by liuyx 20170921 开云优化
				    "  c1.INSTITUTION AS UNITID,c1.INSTITUTION_NAME AS BELONG_ORG  ,"+
				    //"  c1.INSTITUTION AS UNITID,c1.INSTITUTION_NAME AS BELONG_ORG  ,M1.BELONG_BUSI_LINE,"+
				    // modify by liuming 20170824 创建日期和最后修改日期要加时分秒
				    "  to_char( C.CREATE_TIME_LN,'yyyy-mm-dd hh24:mi:ss') as  CREATE_TIME_LN, to_char(C.LAST_UPDATE_TM,'yyyy-mm-dd hh24:mi:ss') as LAST_UPDATE_TM,C.LAST_UPDATE_USER as MGR_ID1,A.USER_NAME AS LAST_UPDATE_USER,C.LAST_UPDATE_SYS"+
				  
					"       FROM ACRM_F_CI_CUSTOMER C     "+
					"       LEFT JOIN ACRM_F_CI_ORG O ON C.CUST_ID=O.CUST_ID     "+
					"       LEFT JOIN ACRM_F_CI_ORG_EXECUTIVEINFO E ON  C.CUST_ID=E.ORG_CUST_ID    AND E.LINKMAN_TYPE='5'   "+//法人
					"       LEFT JOIN ACRM_F_CI_ORG_BUSIINFO B ON C.CUST_ID=B.CUST_ID        "+
					"       LEFT JOIN ACRM_F_CI_ORG_REGISTERINFO R ON C.CUST_ID=R.CUST_ID    "+
					
					"       LEFT JOIN OCRM_F_CI_GROUP_MEMBER_NEW m ON C.CUST_ID=m.CUS_ID    "+
					// modify by liuyx 20170921 开云优化
					//"       LEFT JOIN OCRM_F_CI_GROUP_INFO i ON m.grp_no=i.group_no    "+
					"       LEFT JOIN OCRM_F_CI_GROUP_INFO_NEW i ON m.grp_no=i.grp_no    "+
					" 		LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR C1 ON C.CUST_ID = C1.CUST_ID "+//归属客户经理
//					"  		LEFT JOIN OCRM_F_CI_BELONG_ORG O1  ON C.CUST_ID = O1.CUST_ID   "+
					" 		LEFT JOIN OCRM_F_CM_CUST_MGR_INFO M1 ON C1.MGR_ID=M1.CUST_MANAGER_ID"+
					" 		LEFT JOIN ADMIN_AUTH_ACCOUNT A ON C.LAST_UPDATE_USER=A.ACCOUNT_NAME"+//最后修改人
					"       WHERE C.CUST_ID ='"+custId+"'         ");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
//			query.addOracleLookup("LEGAL_REPR_IDENT_TYPE", "XD000040");
			Map<String, Object> result = query.getJSON();
			List<HashMap<String, Object>> rowsList = (List<HashMap<String, Object>>) result.get("data");
			HashMap<String, Object> resultMap = new HashMap<String,Object>();
			if(rowsList != null && rowsList.size() > 0){
				resultMap = rowsList.get(0);
			}
			//有树的放大镜的查询语句:查询行业类别
			sb = new StringBuffer("select case when T2.IN_CLL_TYPE is null then (SELECT O.IN_CLL_TYPE FROM ACRM_F_CI_ORG o WHERE O.CUST_ID='"+custId+"') else T2.IN_CLL_TYPE end IN_CLL_TYPE " +
					" from (SELECT REPLACE(T1.IN_CLL_TYPE ,',','>') AS IN_CLL_TYPE  "+
					" FROM(     "+
					"  SELECT to_char(wm_concat(T.F_VALUE)) AS IN_CLL_TYPE       "+
					"  FROM (  "+
					"  SELECT T.*   "+
					"  FROM ACRM_F_CI_BUSI_TYPE T     "+
					"  START WITH T.F_CODE =(SELECT O.IN_CLL_TYPE FROM ACRM_F_CI_ORG o WHERE O.CUST_ID='"+custId+"')"+
					"  CONNECT BY  T.F_CODE=PRIOR T.PARENT_CODE    "+
					"  order by LENGTH(f_code)) T)T1)T2  ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("IN_CLL_TYPE", map.get("IN_CLL_TYPE"));
			}else{
				resultMap.put("IN_CLL_TYPE", "");
			}
			

			//有树的放大镜的查询语句:查询行政区划名称
			sb = new StringBuffer(                                                                                  
					"   SELECT CASE  WHEN T2.REGISTER_AREA IS NULL THEN   "+
				    "      (SELECT R.REGISTER_AREA  FROM ACRM_F_CI_ORG_REGISTERINFO R WHERE R.CUST_ID = '"+custId+"')"+
				    "     ELSE   T2.REGISTER_AREA END REGISTER_AREA FROM(     "+
					"		SELECT REPLACE(T1.REGISTER_AREA,',','>')  as REGISTER_AREA FROM(    "+
					"  			SELECT to_char(WM_CONCAT(T.F_VALUE)) AS REGISTER_AREA    "+
					"  			 FROM (           "+
					"  				   SELECT * FROM ( SELECT T.F_VALUE, T.F_CODE,        "+
					"      			   CASE  WHEN SUBSTR(F_CODE,5,6) != '00' AND  SUBSTR(F_CODE,3,6) != '0000'    THEN  SUBSTR(F_CODE,0,4) ||'00'   "+ 
					"      			   WHEN SUBSTR(F_CODE,5,6) = '00' AND   SUBSTR(F_CODE,3,6) != '0000'    THEN SUBSTR(F_CODE,0,2) ||'0000'    "+
					"      			   WHEN  SUBSTR(F_CODE,3,6) = '0000'    THEN  '-1'    "+
					"      			   END PARENT_CODE          "+
					"       		  FROM OCRM_SYS_LOOKUP_ITEM T      "+
					"     				 WHERE T.F_LOOKUP_ID = 'XD000001')A        "+
					"    		 START WITH  A.F_CODE=(SELECT R.REGISTER_AREA FROM ACRM_F_CI_ORG_REGISTERINFO R WHERE R.CUST_ID='"+custId+"')   "+
					"   		  CONNECT BY A.F_CODE =PRIOR A.PARENT_CODE           "+
					"   		  ORDER BY A.F_CODE ) T)T1        )T2 ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("REGISTER_AREA", map.get("REGISTER_AREA"));
			}else{
				resultMap.put("REGISTER_AREA", "");
			}
			//有树的放大镜的查询语句:查询经济类型
			sb = new StringBuffer("SELECT CASE WHEN T2.REGISTER_TYPE IS NULL THEN "+
					" (SELECT R.REGISTER_TYPE FROM ACRM_F_CI_ORG_REGISTERINFO R     "+
					" WHERE R.CUST_ID='"+custId+"') ELSE T2.REGISTER_TYPE END REGISTER_TYPE    "+
					"     FROM (SELECT REPLACE(T1.REGISTER_TYPE ,',','>') AS REGISTER_TYPE       "+
					"     			FROM(  SELECT to_char(WM_CONCAT(T.F_VALUE)) AS REGISTER_TYPE "+
					"  									FROM (    "+
					"  												SELECT T.*       "+
					"  												FROM (SELECT I.F_CODE,F_VALUE,   "+
					"  															CASE   WHEN SUBSTR(I.F_CODE,2,1)='0' THEN -1 "+
					"   														WHEN I.F_CODE BETWEEN 110 AND 190 THEN 100  "+
					"   														WHEN I.F_CODE BETWEEN 210 AND 290 THEN 200  "+
					"   														WHEN I.F_CODE BETWEEN 310 AND 390 THEN 300  "+
					"   														END AS PARENT_CODE       "+
					"  															FROM   OCRM_SYS_LOOKUP_ITEM I  "+
					"   														WHERE I.F_LOOKUP_ID='XD000062' "+
					"   														AND SUBSTR(I.F_CODE,3,1) ='0') T     "+
					"   											START WITH T.F_CODE =(SELECT R.REGISTER_TYPE FROM ACRM_F_CI_ORG_REGISTERINFO R "+
					"      										WHERE R.CUST_ID='"+custId+"')    "+
					"   											CONNECT BY  T.F_CODE=PRIOR T.PARENT_CODE    "+
					"   											ORDER BY LENGTH(F_CODE)) T)T1)T2      ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("REGISTER_TYPE", map.get("REGISTER_TYPE"));
			}else{
				resultMap.put("REGISTER_TYPE", "");
			}
			//查询开户证件信息
			sb = new StringBuffer("select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date,I.IDENT_MODIFIED_TIME from ACRM_F_CI_CUST_IDENTIFIER i ");
			sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.CUST_ID = '"+custId+"' and i.IDENT_TYPE= c.ident_type "
//					+ " and i.IDENT_NO = c.ident_no "
					+ " order by i.last_update_tm desc ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("IDENT_ID", map.get("IDENT_ID"));
				resultMap.put("IDENT_CUST_ID", map.get("CUST_ID"));
				resultMap.put("IDENT_END_DATE", map.get("IDENT_EXPIRED_DATE"));
				resultMap.put("IDENT_MODIFIED_TIME", map.get("IDENT_MODIFIED_TIME"));
				
			}else{
				resultMap.put("IDENT_ID", "");
				resultMap.put("IDENT_CUST_ID", "");
				resultMap.put("IDENT_END_DATE", "");
				resultMap.put("IDENT_MODIFIED_TIME", "");
			}
			//查询Obu Code证件信息
			sb = new StringBuffer(" SELECT I.IDENT_ID, I.CUST_ID, I.IDENT_TYPE, I.IDENT_NO         "+
					"   FROM ACRM_F_CI_CUST_IDENTIFIER I                "+
					"  INNER JOIN ACRM_F_CI_CUSTOMER C ON C.CUST_ID = I.CUST_ID      "+
					"  WHERE I.CUST_ID = '"+custId+"' AND I.IDENT_TYPE = '15X' AND I.IDENT_NO = C.IDENT_NO  "+
					"  ORDER BY I.LAST_UPDATE_TM DESC      ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("OBU_ID", map.get("IDENT_ID"));	
				resultMap.put("OBU_CUST_ID", map.get("CUST_ID"));	
				resultMap.put("IDENT_NO2", map.get("IDENT_NO"));	
			}else{
				resultMap.put("OBU_ID","");	
				resultMap.put("OBU_CUST_ID", "");	
				resultMap.put("IDENT_NO2", "");
			}
			//查询税务登记证编号信息V,W国税，Y地税
			sb = new StringBuffer(" SELECT I.IDENT_NO AS SW_REGIS_CODE,I.IDENT_ID FROM ACRM_F_CI_CUST_IDENTIFIER I  WHERE I.CUST_ID='"+custId+"' AND I.IDENT_TYPE='V'");
			sb.append(" order by i.last_update_tm desc ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("REGIS_ID", map.get("IDENT_ID"));
				resultMap.put("SW_REGIS_CODE", map.get("SW_REGIS_CODE"));
			}else{
				resultMap.put("REGIS_ID", "");
				resultMap.put("SW_REGIS_CODE", "");
			}
			//查询W国税
			sb = new StringBuffer(" SELECT I.IDENT_NO AS NATION_REG_CODE,I.IDENT_ID FROM ACRM_F_CI_CUST_IDENTIFIER I  WHERE I.CUST_ID='"+custId+"' AND I.IDENT_TYPE='W'");
			sb.append(" order by i.last_update_tm desc ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("NATION_REG_ID", map.get("IDENT_ID"));
				resultMap.put("NATION_REG_CODE", map.get("NATION_REG_CODE"));
			}else{
				resultMap.put("NATION_REG_ID", "");
				resultMap.put("NATION_REG_CODE", "");
			}
			//查询Y地税
			sb = new StringBuffer(" SELECT I.IDENT_NO AS AREA_REG_CODE,I.IDENT_ID FROM ACRM_F_CI_CUST_IDENTIFIER I  WHERE I.CUST_ID='"+custId+"' AND I.IDENT_TYPE='Y'");
			sb.append(" order by i.last_update_tm desc ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("AREA_REG_ID", map.get("IDENT_ID"));
				resultMap.put("AREA_REG_CODE", map.get("AREA_REG_CODE"));
			}else{
				resultMap.put("AREA_REG_ID", "");
				resultMap.put("AREA_REG_CODE", "");
			}
			//查询开户信息许可证核批号
			sb = new StringBuffer(" SELECT I.IDENT_ID,I.IDENT_NO AS ACC_OPEN_LICENSE FROM ACRM_F_CI_CUST_IDENTIFIER I  WHERE I.CUST_ID='"+custId+"' AND I.IDENT_TYPE='Z'");
			sb.append(" order by i.last_update_tm desc ");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("OPEN_ID", map.get("IDENT_ID"));
				resultMap.put("ACC_OPEN_LICENSE", map.get("ACC_OPEN_LICENSE"));
			}else{
				resultMap.put("OPEN_ID", "");
				resultMap.put("ACC_OPEN_LICENSE", "");
			}
			//查询注册地址，07
			sb = new StringBuffer("SELECT CUST_ID   AS  ADDRESS_CUST_ID1  ,  ADDR_ID   AS  ADDR_ID1  ,     "+
					"    ADDR    AS  ADDR1 , ADDR_TYPE   AS  ADDR_TYPE1  ,      "+
					"    ADMIN_ZONE    AS  ADMIN_ZONE1 ,  LAST_UPDATE_SYS     AS  ADDRESS_LAST_UPDATE_SYS1  , "+
					"    LAST_UPDATE_USER    AS  ADDRESS_LAST_UPDATE_USER1   , LAST_UPDATE_TM    AS  ADDRESS_LAST_UPDATE_TM1  "+
					"    FROM ACRM_F_CI_ADDRESS ADDR WHERE ADDR.CUST_ID='"+custId+"' AND ADDR.ADDR_TYPE='07' ");
			
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("ADDR_ID1", map.get("ADDR_ID1"));
				resultMap.put("ADDR_TYPE1", map.get("ADDR_TYPE1"));
				resultMap.put("ADDRESS_CUST_ID1", map.get("ADDRESS_CUST_ID1"));
				resultMap.put("ADDR1", map.get("ADDR1"));
				resultMap.put("REGISTER_ADDR", map.get("ADDR1"));
			}else{
				resultMap.put("ADDR_ID1","");
				resultMap.put("ADDR_TYPE1","");
				resultMap.put("ADDRESS_CUST_ID1", "");
				resultMap.put("ADDR1", "");
				resultMap.put("REGISTER_ADDR", "");
			}
			//查询经营地址，08
			sb = new StringBuffer("SELECT CUST_ID   AS  ADDRESS_CUST_ID0  ,  ADDR_ID   AS  ADDR_ID0  ,     "+
					"    ADDR    AS  ADDR0 , ADDR_TYPE   AS  ADDR_TYPE0  ,      "+
					"    ADMIN_ZONE    AS  ADMIN_ZONE0 ,  LAST_UPDATE_SYS     AS  ADDRESS_LAST_UPDATE_SYS0  , "+
					"    LAST_UPDATE_USER    AS  ADDRESS_LAST_UPDATE_USER0   , LAST_UPDATE_TM    AS  ADDRESS_LAST_UPDATE_TM0  "+
					"     FROM ACRM_F_CI_ADDRESS ADDR WHERE ADDR.CUST_ID='"+custId+"' AND ADDR.ADDR_TYPE='08' ");
			
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("ADDR_ID0", map.get("ADDR_ID0"));
				resultMap.put("ADDR_TYPE0", map.get("ADDR_TYPE0"));
				resultMap.put("ADDRESS_CUST_ID0", map.get("ADDRESS_CUST_ID0"));
				resultMap.put("ADDR0", map.get("ADDR0"));
			}else{
				resultMap.put("ADDR_ID0","");
				resultMap.put("ADDR_TYPE0","");
				resultMap.put("ADDRESS_CUST_ID0", "");
				resultMap.put("ADDR0", "");
			}
			
			//查询客户经理信息
			sb = new StringBuffer("select id as MGR_KEY_ID,MGR_ID,CASE WHEN MGR_NAME IS NULL THEN MGR_ID ELSE MGR_NAME END AS MGR_NAME,EFFECT_DATE from OCRM_F_CI_BELONG_CUSTMGR mgr where mgr.cust_id = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("MGR_KEY_ID", map.get("MGR_KEY_ID"));
				resultMap.put("MGR_ID", map.get("MGR_ID"));
				resultMap.put("MGR_NAME", map.get("MGR_NAME"));
				resultMap.put("EFFECT_DATE", map.get("EFFECT_DATE"));
			}else{
				resultMap.put("MGR_KEY_ID", "");
				resultMap.put("MGR_ID", "");
				resultMap.put("MGR_NAME", "");
				resultMap.put("EFFECT_DATE", "");
			}
			//查询开户行
			sb = new StringBuffer(" SELECT B.ORG_NAME AS  CREATE_BRANCH_NO FROM ACRM_F_CI_CUSTOMER   C   LEFT JOIN ADMIN_AUTH_ORG B ON B.ORG_ID = C.CREATE_BRANCH_NO where c.cust_id='"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("CREATE_BRANCH_NO", map.get("CREATE_BRANCH_NO"));
			}else{
				resultMap.put("CREATE_BRANCH_NO", "");
			}
			
			//查询归属业务条线
			sb = new StringBuffer("SELECT L.BL_NAME  BELONG_BUSI_LINE  FROM ACRM_F_CI_ORG O  LEFT JOIN ACRM_F_CI_BUSI_LINE L  ON O.ORG_BIZ_CUST_TYPE = TO_CHAR(L.BL_NO)    WHERE O.CUST_ID = '"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("BELONG_BUSI_LINE", map.get("BELONG_BUSI_LINE"));
			}else{
				resultMap.put("BELONG_BUSI_LINE", "");
			}
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 查询客户信息第二页基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryComsecond(){
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
			StringBuffer sb = new StringBuffer("SELECT  C.AR_CUST_FLAG AS AR_CUST_FLAG,C.AR_CUST_TYPE AS AR_CUST_TYPE,C.INOUT_FLAG   AS INOUT_FLAG  ,    "+
					"   ORG.LNCUSTP         AS LNCUSTP        ,ORG.IF_ORG_SUB_TYPE AS IF_ORG_SUB_TYPE,ORG.ORG_SUB_TYPE    AS ORG_SUB_TYPE   ,       "+
					"   ORG.ORG_TYPE        AS ORG_TYPE       ,ORG.COM_SP_BUSINESS AS COM_SP_BUSINESS,          "+
					"   K.CUST_ID AS KEY_CUST_ID,  K.IS_LISTED_CORP    AS IS_LISTED_CORP   ,K.IS_NOT_LOCAL_ENT  AS IS_NOT_LOCAL_ENT , "+
					"   K.IS_STEEL_ENT      AS IS_STEEL_ENT     ,K.IS_FAX_TRANS_CUST AS IS_FAX_TRANS_CUST,K.IS_MATERIAL_RISK  AS IS_MATERIAL_RISK , "+
					"   K.IS_RURAL          AS IS_RURAL         ,K.IS_SCIENCE_TECH   AS IS_SCIENCE_TECH  ,K.ENERGY_SAVING     AS ENERGY_SAVING    , "+
					"   K.IS_TAIWAN_CORP    AS IS_TAIWAN_CORP   ,"+
					"   K.SHIPPING_IND      AS SHIPPING_IND     , "+
//					"   CASE WHEN  ORG.BUILD_DATE IS NULL  THEN    '9'"+
//					"    WHEN  ORG.BUILD_DATE IS NOT NULL AND (MONTHS_BETWEEN(SYSDATE,ORG.BUILD_DATE)/12 )<=2  THEN    '1'  "+
//					"    WHEN  ORG.BUILD_DATE IS NOT NULL AND (MONTHS_BETWEEN(SYSDATE,ORG.BUILD_DATE)/12 )>2  THEN '2'    "+
//					"      END AS IS_NEW_CORP,                                                                      "+
					"   K.IS_NEW_CORP,"+
					"   K.ENVIRO_PENALTIES  AS ENVIRO_PENALTIES ,K.IS_HIGH_POLLUTE   AS IS_HIGH_POLLUTE  ,      "+
					"   ISS.ISSUE_STOCK_ID   AS ISSUE_STOCK_ID,ISS.CUST_ID          AS STOCK_CUST_ID ,ISS.STOCK_CODE       AS STOCK_CODE    ,       "+
					"   ISS.MARKET_PLACE     AS MARKET_PLACE  , "+
					"   ORG.COM_SP_LIC_ORG AS COM_SP_ORG,       "+
					"   ORG.COM_SP_STR_DATE AS COM_SP_REG_DATE, "+
					"   ORG.COM_SP_LIC_NO AS COM_SP_CODE,       "+
					"   ORG.COM_SP_DETAIL AS COM_SP_SITU,       "+
					"   ORG.COM_SP_END_DATE AS COM_SP_END_DATE  ,"+
					  " S.CUST_ID SCIENCE_CUST_ID,S.IS_CREATIVE  IF_SCIENCE,  S.TERM  SCIENTIFIC_TERM,  S.ORG_TYPE  SCIENTIFIC_TYPE,  S.SUB_TYPE  SCIENTIFIC_ENT,  S.RANGE  SCIENTIFIC_RANGE,  S.RATE  SCIENTIFIC_RATE"+
					"   FROM ACRM_F_CI_CUSTOMER C "+
					"   LEFT JOIN ACRM_F_CI_ORG ORG ON C.CUST_ID=ORG.CUST_ID          "+
					"	LEFT JOIN OCRM_F_CI_CUST_SCIENCE s ON C.CUST_ID=s.CUST_ID "+
					"   LEFT JOIN ACRM_F_CI_ORG_KEYFLAG K ON C.CUST_ID=K.CUST_ID      "+
					"   LEFT JOIN ACRM_F_CI_ORG_ISSUESTOCK ISS ON C.CUST_ID=ISS.CUST_ID   "+
					"   WHERE C.CUST_ID='"+custId+"' ");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("IS_LISTED_CORP", "XD000072");
			query.addOracleLookup("LNCUSTP", "XD000364");
			query.addOracleLookup("IF_ORG_SUB_TYPE", "XD000072");
			query.addOracleLookup("ORG_SUB_TYPE", "XD000304");
			query.addOracleLookup("ORG_TYPE", "XD000054");
			query.addOracleLookup("COM_SP_BUSINESS", "XD000072");
			query.addOracleLookup("AR_CUST_FLAG", "XD000072");
			query.addOracleLookup("AR_CUST_TYPE", "XD000278");
			//科技型企业略
			query.addOracleLookup("IS_NOT_LOCAL_ENT", "XD000072");
			query.addOracleLookup("IS_STEEL_ENT", "XD000072");
			query.addOracleLookup("IS_FAX_TRANS_CUST", "XD000072");
			query.addOracleLookup("ENERGY_SAVING", "XD000072");
			query.addOracleLookup("IS_MATERIAL_RISK", "XD000072");
			query.addOracleLookup("IS_RURAL", "XD000072");
			query.addOracleLookup("IS_SCIENCE_TECH", "XD000072");
			query.addOracleLookup("IS_TAIWAN_CORP", "XD000072");
			query.addOracleLookup("IS_NEW_CORP", "XD000072");
			
			query.addOracleLookup("INOUT_FLAG", "XD000022");
			query.addOracleLookup("SHIPPING_IND", "XD000072");
			query.addOracleLookup("ENVIRO_PENALTIES", "XD000072");
			query.addOracleLookup("IS_HIGH_POLLUTE", "XD000072");
			
			Map<String, Object> result = query.getJSON();
			List<HashMap<String, Object>> rowsList = (List<HashMap<String, Object>>) result.get("data");
			HashMap<String, Object> resultMap = new HashMap<String,Object>();
			if(rowsList != null && rowsList.size() > 0){
				resultMap = rowsList.get(0);
			}
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	/**
	 * 查询客户信息第四页基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryComfourth(){
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        //查询机构表信息
	        StringBuffer sb = new StringBuffer(" SELECT  o.CUST_ID   as ORG1_CUST_ID ,    "+
					"	  o.SUPER_DEPT as SUPER_DEPT   ,  o.ORG_STATE as ORG_STATE    ,    "+
					"	  o.YEAR_RATE as YEAR_RATE    ,  o.ENT_BELONG as ENT_BELONG   ,    "+
					"	  o.BAS_CUS_STATE  as BAS_CUS_STATE   FROM ACRM_F_CI_ORG  o   where o.cust_id='"+custId+"'");
			
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
//			query.addOracleLookup("IS_LISTED_CORP", "XD000072");
						
			Map<String, Object> result = query.getJSON();
			List<HashMap<String, Object>> rowsList = (List<HashMap<String, Object>>) result.get("data");
			HashMap<String, Object> resultMap = new HashMap<String,Object>();
			if(rowsList != null && rowsList.size() > 0){
				resultMap = rowsList.get(0);
			}
	        //查询代理人	
			 sb = new StringBuffer("select  a.CUST_ID  as GRADE_CUST_ID    ,  a.AGENT_NATION_CODE  as AGENT_NATION_CODE, "+  
						"	  a.AGENT_ID as AGENT_ID   ,   a.IDENT_TYPE   as GRADE_IDENT_TYPE,   "+
						"	  a.TEL as TEL   ,   a.AGENT_NAME   as AGENT_NAME ,   "+
						"	  a.IDENT_NO as GRADE_IDENT_NO from ACRM_F_CI_AGENTINFO a   "+
						" where a.cust_id='"+custId+"'");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("GRADE_CUST_ID", map.get("GRADE_CUST_ID"));
				resultMap.put("AGENT_NATION_CODE", map.get("AGENT_NATION_CODE"));
				resultMap.put("AGENT_ID", map.get("AGENT_ID"));
				resultMap.put("GRADE_IDENT_TYPE", map.get("GRADE_IDENT_TYPE"));
				resultMap.put("TEL", map.get("TEL"));
				resultMap.put("AGENT_NAME", map.get("AGENT_NAME"));
				resultMap.put("GRADE_IDENT_NO", map.get("GRADE_IDENT_NO"));
			}else{
				resultMap.put("GRADE_CUST_ID", "");
				resultMap.put("AGENT_NATION_CODE", "");
				resultMap.put("AGENT_ID", "");
				resultMap.put("GRADE_IDENT_TYPE", "");
				resultMap.put("TEL", "");
				resultMap.put("AGENT_NAME", "");
				resultMap.put("GRADE_IDENT_NO", "");
			}
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	/**
	 * 查询客户信息第五页基本信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryComfifth(){
		try {
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        //查询实际控制人信息--6
			StringBuffer sb = new StringBuffer(" SELECT C.CUST_ID, NR.TASK_NUMBER ,NR.INTERVIEWEE_NAME ,NR.RES_CUSTSOURCE,NR.CUS_BUSISTATUS ,  "+
					"		 NR.CUS_OPERATEPTEL ,NR.CUS_MAJORPRODUCT ,NR.CALL_TIME,NR.CREATE_TIME ,E.LINKMAN_NAME  AS ACT_CTL_NAME, "+  
					" 	 CASE WHEN E.MOBILE IS NOT NULL THEN E.MOBILE "+
					"		 WHEN E.MOBILE2 IS NOT NULL THEN E.MOBILE2  "+
					"		 WHEN E.OFFICE_TEL IS NOT NULL THEN E.OFFICE_TEL"+
					"    WHEN E.HOME_TEL IS NOT NULL THEN E.HOME_TEL END AS ACT_CTL_PHONE "+
					"		 FROM ACRM_F_CI_CUSTOMER C LEFT JOIN OCRM_F_INTERVIEW_RECORD R ON C.CUST_ID=R.CUST_ID   "+
					"		 LEFT JOIN  OCRM_F_INTERVIEW_NEW_RECORD NR  ON NR.TASK_NUMBER=R.TASK_NUMBER  "+
					"		 LEFT JOIN ACRM_F_CI_ORG_EXECUTIVEINFO E ON C.CUST_ID=E.ORG_CUST_ID"+
					"		 WHERE C.CUST_ID ='"+custId+"'  AND E.LINKMAN_TYPE IN('6') and rownum=1   "+
					"		 ORDER BY R.CREATE_TIME DESC");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
//			query.addOracleLookup("IS_LISTED_CORP", "XD000072");
						
			Map<String, Object> result = query.getJSON();
			List<HashMap<String, Object>> rowsList = (List<HashMap<String, Object>>) result.get("data");
			HashMap<String, Object> resultMap = new HashMap<String,Object>();
			if(rowsList != null && rowsList.size() > 0){
				resultMap = rowsList.get(0);
			}
			//查询实际控制人配偶信息'11'
			sb = new StringBuffer("SELECT C.CUST_ID, NR.TASK_NUMBER ,E.LINKMAN_NAME AS  ACT_CTL_WIFE"+  
					"		 FROM ACRM_F_CI_CUSTOMER C LEFT JOIN OCRM_F_INTERVIEW_RECORD R ON C.CUST_ID=R.CUST_ID   "+
					"		 LEFT JOIN  OCRM_F_INTERVIEW_NEW_RECORD NR  ON NR.TASK_NUMBER=R.TASK_NUMBER  "+
					"		 LEFT JOIN ACRM_F_CI_ORG_EXECUTIVEINFO E ON C.CUST_ID=E.ORG_CUST_ID"+
					"		 WHERE C.CUST_ID ='"+custId+"'  AND E.LINKMAN_TYPE IN('11') and rownum=1   "+
					"		 ORDER BY R.CREATE_TIME DESC");
			query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String, Object>> tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			if(tempRowsList != null && tempRowsList.size()>0){
				Map<?, ?> map = tempRowsList.get(0);
				resultMap.put("ACT_CTL_WIFE", map.get("ACT_CTL_WIFE"));
			}
			else{
				resultMap.put("ACT_CTL_WIFE", "");
			}
			
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	

	/**
	 * 保存方法
	 */
	public void saveData() throws Exception{
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
			String custState = request.getParameter("custState");
			String custId = request.getParameter("custId");
			String custName = request.getParameter("custName");
			String submitFlag = request.getParameter("submitFlag");// 是否走流程的标识
			String Industry = request.getParameter("Industry");
			String Employee = request.getParameter("Employee");
			String AnnualIncome = request.getParameter("AnnualIncome");
			String TotalAssets = request.getParameter("TotalAssets");
			String comFirst = request.getParameter("comFirst");
			String comSecond = request.getParameter("comSecond");
			String comSecond2 = request.getParameter("comSecond2");// 发生日期
			String comThirdAddress = request.getParameter("comThirdAddress");
			String comThirdLinkman = request.getParameter("comThirdLinkman");
			String comThreeContact = request.getParameter("comThreeContact");
			String comThreeIdent = request.getParameter("comThreeIdent");
			String comFourth = request.getParameter("comFourth");
			JSONArray jComFirst = JSONArray.fromObject(comFirst);
			JSONArray jComSecond = JSONArray.fromObject(comSecond);
			JSONArray jComSecond2 = JSONArray.fromObject(comSecond2);
			JSONArray jComThirdAddress = JSONArray.fromObject(comThirdAddress);
			JSONArray jComThirdLinkman = JSONArray.fromObject(comThirdLinkman);
			JSONArray jComThreeContact = JSONArray.fromObject(comThreeContact);
			JSONArray jComThreeIdent = JSONArray.fromObject(comThreeIdent);
			JSONArray jComFourth = JSONArray.fromObject(comFourth);
			
			if (submitFlag.equals("true")) {// 需要走流程时的条件
				log.info("该客户为既有客户，需要走流程，开始判断该客户信息是否正在走流程中");
				// 验证是否已经提交审核,验证不通过会抛出异常
				CustomerManagerNewService.judgeExist("CF_" + custId);// 流程标识以CF_开头
				// 验证修改后，证件1号码是否已经存在，如若已存在，禁止提交
				// CustomerManagerNewService.identNoExist(jComFirst,custId);
			}

			List<?> returnList = CustomerManagerNewService.commitFsxComAll(jComFirst,
					Industry,Employee, AnnualIncome, TotalAssets,
					jComSecond,jComSecond2,
					jComThirdAddress,jComThirdLinkman,jComThreeContact,jComThreeIdent,
					jComFourth,custId,custName,submitFlag,custState);			

			String result = JSONUtil.serialize(returnList);
			response.getWriter().write(result);
			response.getWriter().flush();
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"0000","操作失败,请联系管理员");
		}
	
	}
	

	/**
	 * 删除：批量删除
	 * 
	 * @param ids
	 */
	public String batchDestroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idsStr = request.getParameter("idsStr"); // 获取需要提交的记录id
		CustomerManagerNewService.batchDestroy(idsStr);
		return "success";
	}
	 public List<Object> validationIfDelete(String sql,int clom){
	    	log.info(""+sql);
	    	List<Object> List = new ArrayList<Object>();
	    	Connection  connection=null;
	   		Statement stmt=null;
	   		ResultSet result=null;
	    	try{
	   				 connection = ds.getConnection();
	   				 stmt = connection.createStatement();
	   				 result = stmt.executeQuery(sql);
	   				 String custId="";
	   				 String custName="";
	   				 while (result.next()){
	   					if(clom==1){
	   						custId=result.getString(1);
	   						custName=result.getString(2);
			    			List.add(custId);
			    			List.add(custName);
	   					}	
	   			    }
	   			log.info("validationIfDelete: "+List.toString());
	   			 return List;
	   		}catch(Exception e){
	   			e.printStackTrace();
	   		}finally{
	   			JdbcUtil.close(result, stmt, connection);
	   		}
			return null;
	       }
	/**
	 * 验证是否可以删除该客户:
	 * 验证两个条件：1、是否在转移中
	 * 2、是否已完成callreport
	 * 只有完成转移的和不在callreport中的可以删除
	 */
		public void dodeleteCheck(){
		   	ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String type= "";
			
				try {
					String idsStr = request.getParameter("idsStr")==null?"":request.getParameter("idsStr");
					String custid[] = idsStr.split(",");
					for(int i=0;i<custid.length;i++){
						List<Object> list = null,list1 = null,list2=null;
						boolean  flag1 =false;  //不在移转中
						boolean	 flag2 =false;  //不在callreport中					
						if(idsStr!=null&&!"".equals(idsStr)){
							//1、取审批状态为1(待审批)的数据，若无，表明不在移转中
							String  sql1="SELECT C.CUST_ID,C.CUST_NAME FROM OCRM_F_CI_TRANS_CUST  C LEFT JOIN  OCRM_F_CI_TRANS_APPLY A ON C.APPLY_NO=A.APPLY_NO"+
									" WHERE C.CUST_ID='"+custid[i]+"' AND A.APPROVE_STAT ='1'";						
							list1=validationIfDelete(sql1,1); 
							if(list1!=null&&list1.size()>0){//在移转中
								flag1=true;
							}
							//2、取复核状态为0（未完成（移动信贷）），1（未完成），02（复核中），2（复核中（移动信贷）），若无，表明不在callreport中
							String  sql2="SELECT  R.CUST_ID,R.CUST_NAME FROM OCRM_F_INTERVIEW_RECORD R WHERE R.CUST_ID='"+custid[i]+"' AND R.REVIEW_STATE IN('0','1','02','2')";
							list2=validationIfDelete(sql2,1);
							if(list2!=null&&list2.size()>0){//在callreport中
								flag2=true;
							}
						}
	
						if(!(flag1||flag2))//可以删除
						{	type="1";
						}
						if(flag1){
							type="2";//flag1为true,flag2为false,该客户未完成移转
							list=list1;
						}
						if(flag2){
							type="3";//flag1为false,flag2为true,该客户未完成callreport
							list=list2;
							
						}
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("type", type);
						if(list!=null&&list.size()>0){
						map.put("custId", (list).get(0));   //客户编号
						map.put("custName", (list).get(1)); //客户名称
						}
						log.info("dodeleteCheck  type: "+type);
						this.setJson(map);
						if(type!="1"||!type.equals("1")){break;}
					}
				} catch (Exception e) {
					throw new BizException(1,0,"10001",":"+e.getMessage());
					//e.printStackTrace();
				}
				
		   	
		}
}
