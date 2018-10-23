package com.yuchengtech.bcrm.customer.customerView.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.customerView.model.FsxWholeInfo;
import com.yuchengtech.bcrm.customer.customerView.service.CustomerQueryAllNewService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description: 非授信客户信息处理
 * @author luyy
 * @data 2014-08-06
 */
@ParentPackage("json-default")
@Action("/dealWithFsx")
public class DealWithFsxPreAction extends CommonAction
{
  private static final long serialVersionUID = -1307317536382455940L;

  @Autowired
  @Qualifier("dsOracle")
  private DataSource ds;

  @Autowired
  private CustomerQueryAllNewService customerQueryAllService;

  @Autowired
  public void init()
  {
    this.model = new FsxWholeInfo();
    setCommonService(this.customerQueryAllService);
  }
  /**
	 * 查询零售非授信第一屏客户信息
	 * @return
	 */
  public String queryPerfsx()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");
      //查询统一客户表信息-个人客户表信息
      StringBuffer sb = new StringBuffer(
        "select c.core_no,c.cust_id,c.cust_type,c.ident_type,c.ident_no,c.CUST_NAME,c.RECOMMENDER,at.USER_NAME AS RECOMMENDER_NAME,c.en_name,c.inout_flag,c.ar_cust_flag,c.risk_nation_code,c.CUSTNM_IDENT_MODIFIED_FLAG,c.VIP_FLAG,C.SWIFT,C.STAFFIN");
      sb.append(" ,p.ORG_SUB_TYPE,p.IF_ORG_SUB_TYPE,P.REMARK,P.AREA_CODE,P.JOINT_CUST_TYPE,p.PER_CUST_TYPE,p.gender,p.birthday,p.citizenship,p.usa_tax_iden_no,p.birthlocale,p.email,p.unit_fex");
     //新增信息查询 -----开始
      sb.append(" ,P.NATIONALITY,P.HIGHEST_SCHOOLING,P.HOME_TEL,P.UNIT_TEL,P.UNIT_NAME,P.UNIT_ADDR,trunc(P.ANNUAL_INCOME/12,2) ANNUAL_INCOME,trunc(P.ANNUAL_INCOME_SCOPE/12,2) ANNUAL_INCOME_SCOPE,P.MARRIAGE");
      sb.append(" ,P.SPOUSE_NAME,P.SPOUSE_PHONE,P.SPOUSE_MOBILE,P.SPOUSE_ID,P.SPOUSE_CORE_ID");
      sb.append(" ,special.SPECIAL_LIST_FLAG,special.ENTER_REASON,special.START_DATE");
      //新增信息查询 -----结束
      sb.append(" ,g.CUST_GRADE_ID as VIP_GRADE_ID,g.CUST_GRADE as VIP_CUST_GRADE,g1.CUST_GRADE_ID as RISK_GRADE_ID,G1.CUST_GRADE AS RISK_CUST_GRADE");
      sb.append(" ,k.CUST_ID as KEYFLAG_CUST_ID,K.USA_TAX_FLAG,K.IS_SEND_ECOMSTAT_FLAG,K.IS_FAX_TRANS_CUST,c.CREATE_BRANCH_NO as BASIC_ACCT_BANK_NO,b.org_name as basic_acct_bank_name,c.CREATE_DATE as BASIC_ACCT_OPEN_DATE");
      sb.append(" ,a.AGENT_ID,a.AGENT_NAME,a.IDENT_TYPE as AGENT_IDENT_TYPE,a.IDENT_NO as AGENT_IDENT_NO,a.AGENT_NATION_CODE,a.TEL as AGENT_TEL,a.CUST_ID as AGENT_CUST_ID");
      sb.append(",ofsx.if_sign_service,ofsx.cust_id as service_cust_id ");
      sb.append(" from ACRM_F_CI_CUSTOMER c ");
      sb.append(" inner join ACRM_F_CI_PERSON p on p.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_PER_KEYFLAG k on k.cust_id = c.cust_id ");
      sb.append(" left join admin_auth_org b on b.org_id = c.create_branch_no ");
      sb.append(" left join ACRM_F_CI_GRADE g on g.cust_id = c.cust_id and g.CUST_GRADE_TYPE='04'");
      sb.append(" left join ACRM_F_CI_GRADE g1 on g1.cust_id = c.cust_id and g1.cust_grade_type='01'");
      sb.append(" left join ACRM_F_CI_AGENTINFO a on a.cust_id = c.cust_id ");
      sb.append(" left join ADMIN_AUTH_ACCOUNT at on at.account_name = c.RECOMMENDER ");
      sb.append(" left join ACRM_F_CI_FSXINFO_OTHER ofsx  on c.cust_id=ofsx.cust_id ");
      //通过特殊名单的证件号码关联查找
      //sb.append(" LEFT JOIN  ACRM_F_CI_CUST_IDENTIFIER iden  on iden.cust_id=c.cust_id ");
      sb.append(" left join ACRM_F_CI_SPECIALLIST special   on c.ident_no=special.ident_no ");
      sb.append(" where c.cust_Id = '" + custId + "'");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      Map result = query.getJSON();
      List rowsList = 
        (List)result
        .get("data");
      HashMap resultMap = new HashMap();
      if ((rowsList != null) && (rowsList.size() > 0)) {
        resultMap = (HashMap)rowsList.get(0);
      }
      //查询证件表信息(证件号码1)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.CUST_ID = '" + 
        custId + "' and i.IDENT_TYPE= c.ident_type " + 
        " order by i.last_update_tm desc ");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      List tempRowsList = 
        (List)query
        .getJSON().get("data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID", map.get("IDENT_ID"));
        resultMap.put("IDENT_EXPIRED_DATE", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID", "");
        resultMap.put("IDENT_EXPIRED_DATE", "");
      }
      //查询证件表信息(证件号码2)
      sb = new StringBuffer(
        "select R.ident_id,R.cust_id,R.ident_type,R.ident_no,R.ident_effective_date,R.ident_expired_date   FROM (select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date , CASE WHEN c.ident_type='X2' and i.ident_type='6' then 1 else 0 end as flag  from ACRM_F_CI_CUST_IDENTIFIER i inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id   where i.cust_id='" + 
        custId + 
        "' " + 
        " and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) order by I.IDENT_TYPE DESC) R  ORDER BY R.FLAG DESC");

      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID1", map.get("IDENT_ID"));
        resultMap.put("IDENT_TYPE1", map.get("IDENT_TYPE"));
        resultMap.put("IDENT_NO1", map.get("IDENT_NO"));
        resultMap.put("IDENT_EXPIRED_DATE1", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID1", "");
        resultMap.put("IDENT_TYPE1", "");
        resultMap.put("IDENT_NO1", "");
        resultMap.put("IDENT_EXPIRED_DATE1", "");
      }
      //查询客户经理信息
      sb = new StringBuffer(
        "select id as MGR_KEY_ID,MGR_ID,CASE WHEN MGR_NAME IS NULL THEN MGR_ID ELSE MGR_NAME END AS MGR_NAME,EFFECT_DATE from OCRM_F_CI_BELONG_CUSTMGR mgr where mgr.cust_id = '" + 
        custId + "'");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("MGR_KEY_ID", map.get("MGR_KEY_ID"));
        resultMap.put("MGR_ID", map.get("MGR_ID"));
        resultMap.put("MGR_NAME", map.get("MGR_NAME"));
        resultMap.put("EFFECT_DATE", map.get("EFFECT_DATE"));
      } else {
        resultMap.put("MGR_KEY_ID", "");
        resultMap.put("MGR_ID", "");
        resultMap.put("MGR_NAME", "");
        resultMap.put("EFFECT_DATE", "");
      }

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询对公非授信第一屏客户信息
	 * @return
	 */
  public String queryComfsx()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");
      //查询统一客户表信息-对公客户表信息
      StringBuffer sb = new StringBuffer(
        "select c.cust_id,c.cust_type,c.ident_type,c.ident_no,c.CUST_NAME,c.RECOMMENDER,at.user_name as RECOMMENDER_NAME,c.en_name,c.inout_flag,c.ar_cust_flag,c.risk_nation_code,c.CUSTNM_IDENT_MODIFIED_FLAG,c.VIP_FLAG,C.staffin,c.SWIFT");
      sb.append(" ,g.CUST_GRADE_ID as VIP_GRADE_ID,g.cust_grade as VIP_CUST_GRADE,g1.CUST_GRADE_ID as RISK_GRADE_ID,G1.CUST_GRADE AS RISK_CUST_GRADE");
      sb.append(" ,o.ORG_EMAIL,o.CREDIT_CODE,o.HQ_NATION_CODE,o.JOINT_CUST_TYPE,o.ORG_FEX,o.LNCUSTP,o.ORG_SUB_TYPE,O.ORG_TYPE,o.IF_ORG_SUB_TYPE,o.ORG_CUST_TYPE,O.BUILD_DATE,o.HQ_NATION_CODE,o.CREDIT_CODE,o.NATION_CODE,O.AREA_CODE,o.JOINT_CUST_TYPE,o.IN_CLL_TYPE, CASE WHEN LENGTH(O.BUSI_LIC_NO)=18 THEN O.BUSI_LIC_NO ELSE '' END AS BUSI_LIC_NO,CASE WHEN BT.F_VALUE IS NULL THEN O.IN_CLL_TYPE ELSE BT.F_VALUE END AS IN_CLL_TYPE_NAME, k.cust_id as KEYFLAG_CUST_ID,k.IS_SEND_ECOMSTAT_FLAG,k.IS_FAX_TRANS_CUST,c.CREATE_BRANCH_NO as BASIC_ACCT_BANK_NO,b.org_name as basic_acct_bank_name,c.CREATE_DATE as basic_acct_open_date");

      sb.append(" ,a.AGENT_ID,a.AGENT_NAME,a.IDENT_TYPE as AGENT_IDENT_TYPE,a.IDENT_NO as AGENT_IDENT_NO,a.AGENT_NATION_CODE,a.TEL as AGENT_TEL,a.CUST_ID as AGENT_CUST_ID");
      
	  //add by liuming 20170612,是否台资
	  sb.append(" ,k.IS_TAIWAN_CORP ");

      sb.append(" from ACRM_F_CI_CUSTOMER c ");
      sb.append(" inner join ACRM_F_CI_ORG o on o.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_ORG_REGISTERINFO reg on reg.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_ORG_KEYFLAG k on k.cust_id = c.cust_id ");
      sb.append(" left join ADMIN_AUTH_ORG b on b.org_id = c.create_branch_no ");
      sb.append(" left join ACRM_F_CI_AGENTINFO a on a.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_GRADE g on g.cust_id = c.cust_id and g.cust_grade_type='04'");
      sb.append(" left join ACRM_F_CI_GRADE g1 on g1.cust_id = c.cust_id and g1.cust_grade_type='01'");
      sb.append(" left join ACRM_F_CI_BUSI_TYPE BT ON BT.F_CODE = o.IN_CLL_TYPE");
      sb.append(" left join ADMIN_AUTH_ACCOUNT at on at.account_name = c.RECOMMENDER ");
      sb.append(" where c.cust_Id = '" + custId + "'");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      Map result = query.getJSON();
      List rowsList = 
        (List)result
        .get("data");
      HashMap resultMap = new HashMap();
      if ((rowsList != null) && (rowsList.size() > 0)) {
        resultMap = (HashMap)rowsList.get(0);
      }
      //查询证件表信息(证件号码1)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and i.ident_type= c.ident_type and i.ident_no = c.ident_no order by i.last_update_tm desc");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      List tempRowsList = 
        (List)query
        .getJSON().get("data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID", map.get("IDENT_ID"));
        resultMap.put("IDENT_EXPIRED_DATE", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID", "");
        resultMap.put("IDENT_EXPIRED_DATE", "");
      }
      //查询证件表信息(证件号码2) 不取国税、地税、ObuCode、税务登记证
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) and i.ident_type NOT IN ('V','15X','W','Y','C') UNION " + 
        "\tselect i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date " + 
        "\tfrom ACRM_F_CI_CUST_IDENTIFIER i " + 
        "\tinner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id " + 
        "\twhere i.cust_id = '" + 
        custId + 
        "' and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) and i.ident_type  = 'C'  and trim(i.ident_no) is not null " + 
        "\torder by IDENT_TYPE DESC");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID1", map.get("IDENT_ID"));
        resultMap.put("IDENT_TYPE1", map.get("IDENT_TYPE"));
        resultMap.put("IDENT_NO1", map.get("IDENT_NO"));
        resultMap.put("IDENT_EXPIRED_DATE1", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID1", "");
        resultMap.put("IDENT_TYPE1", "");
        resultMap.put("IDENT_NO1", "");
        resultMap.put("IDENT_EXPIRED_DATE1", "");
      }
      //查询证件表信息(证件号码3) --ObuCode
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" where i.cust_id = '" + 
        custId + 
        "' AND i.ident_type = '15X' order by i.last_update_tm desc");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID2", map.get("IDENT_ID"));
        resultMap.put("IDENT_TYPE2", map.get("IDENT_TYPE"));
        resultMap.put("IDENT_NO2", map.get("IDENT_NO"));
      } else {
        resultMap.put("IDENT_ID2", "");
        resultMap.put("IDENT_TYPE2", "");
        resultMap.put("IDENT_NO2", "");
      }
      //查询客户经理信息
      sb = new StringBuffer(
        "select id as MGR_KEY_ID,MGR_ID,CASE WHEN MGR_NAME IS NULL THEN MGR_ID ELSE MGR_NAME END AS MGR_NAME,EFFECT_DATE from OCRM_F_CI_BELONG_CUSTMGR mgr where mgr.cust_id = '" + 
        custId + "'");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("MGR_KEY_ID", map.get("MGR_KEY_ID"));
        resultMap.put("MGR_ID", map.get("MGR_ID"));
        resultMap.put("MGR_NAME", map.get("MGR_NAME"));
        resultMap.put("EFFECT_DATE", map.get("EFFECT_DATE"));
      } else {
        resultMap.put("MGR_KEY_ID", "");
        resultMap.put("MGR_ID", "");
        resultMap.put("MGR_NAME", "");
        resultMap.put("EFFECT_DATE", "");
      }

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询客户地址信息
	 * @return
	 */
  public String queryAddr()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");
      String flag = this.request.getParameter("flag");
      StringBuffer sb = new StringBuffer(
        "SELECT T.ADDR_ID,T.CUST_ID,T.ADDR_TYPE,T.ADDR,T.ZIPCODE,T.LAST_UPDATE_SYS,T.LAST_UPDATE_USER,to_char(T.LAST_UPDATE_TM,'yyyy-MM-dd') as LAST_UPDATE_TM from ACRM_F_CI_ADDRESS t where t.cust_id = '" + 
        custId + "' ORDER BY T.ADDR_TYPE");
      if ("callcenter".equals(flag)) {
        sb = new StringBuffer(
          "SELECT T.ADDR_ID,T.CUST_ID,T.ADDR_TYPE,T.ADDR,T.ZIPCODE,T.LAST_UPDATE_SYS,T.LAST_UPDATE_USER,to_char(T.LAST_UPDATE_TM,'yyyy-MM-dd') as LAST_UPDATE_TM from ACRM_F_CI_ADDRESS t where t.cust_id = '" + 
          custId + 
          "' and t.ADDR_TYPE in ('01','04') ORDER BY T.ADDR_TYPE");
      }

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      query.addOracleLookup("ADDR_TYPE", "XD000192");
      Map result = query.getJSON();

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询对私联系人信息
	 * @return
	 */
  public String queryPerContactPerson()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");

      StringBuffer sb = new StringBuffer(
        "SELECT T.LINKMAN_ID,T.CUST_ID,T.LINKMAN_TYPE,T.LINKMAN_NAME, T.IDENT_TYPE,T.IDENT_NO,T.TEL,T.TEL2,T.MOBILE,T.MOBILE2,T.EMAIL,T.ADDRESS,T.GENDER,T.BIRTHDAY,T.LAST_UPDATE_SYS,T.LAST_UPDATE_USER,TO_CHAR(T.LAST_UPDATE_TM,'YYYY-MM-DD HH24:MI:SS') FROM ACRM_F_CI_PER_LINKMAN T WHERE T.CUST_ID = '" + 
        custId + "' ORDER BY T.LINKMAN_TYPE");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      query.addOracleLookup("LINKMAN_TYPE", "XD000195");
      query.addOracleLookup("GENDER", "XD000016");
      query.addOracleLookup("IDENT_TYPE", "XD000040");
      Map result = query.getJSON();

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询对公联系人信息
	 * @return
	 */
  public String queryComContactPerson()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");

      StringBuffer sb = new StringBuffer(
        "SELECT T.LINKMAN_ID,T.ORG_CUST_ID,T.LINKMAN_TYPE,T.LINKMAN_NAME,T.LINKMAN_TITLE,T.BIRTHDAY,T.EMAIL,T.FEX,T.GENDER,T.HOME_TEL,T.IDENT_NO,T.IDENT_TYPE,T.MOBILE,T.MOBILE2,T.OFFICE_TEL  ,T.IDENT_EXPIRED_DATE,T.LAST_UPDATE_SYS,T.LAST_UPDATE_USER,to_char(T.LAST_UPDATE_TM,'yyyy-MM-dd') as LAST_UPDATE_TM FROM ACRM_F_CI_ORG_EXECUTIVEINFO T WHERE T.ORG_CUST_ID = '" + 
        custId + "' ORDER BY T.LINKMAN_TYPE");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      query.addOracleLookup("LINKMAN_TYPE", "XD000339");
      query.addOracleLookup("LINKMAN_TITLE", "XD000250");
      query.addOracleLookup("GENDER", "XD000016");
      query.addOracleLookup("IDENT_TYPE", "XD000040");
      Map result = query.getJSON();

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询零售授信客户信息
	 * @return
	 */
  public String queryPersx()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");
      //查询统一客户表信息-个人客户表信息
      StringBuffer sb = new StringBuffer(
        "select c.EN_NAME,c.INOUT_FLAG,c.VIP_FLAG,c.ident_type,c.ident_no,c.FIRST_LOAN_DATE,C.CUSTNM_IDENT_MODIFIED_FLAG,");
      sb.append(" p.CUST_ID,p.PER_CUST_TYPE,p.PERSONAL_NAME,p.GENDER,p.CITIZENSHIP,p.LOAN_CARD_NO,p.BANK_DUTY,p.HOLD_STOCK_AMT,");
      sb.append(" p.HOLD_ACCT,p.NATIONALITY,p.NATIVEPLACE,p.HUKOU_PLACE,p.BIRTHDAY,p.POLITICAL_FACE,p.HIGHEST_SCHOOLING,p.HIGHEST_DEGREE,p.HEALTH,");
      sb.append(" m.CUST_ID as MATEINFO_KEY_ID,p.MARRIAGE as PO_MARRIAGE,m.IDENT_TYPE AS PO_IDENT_TYPE,m.CUST_ID_MATE AS PO_CUST_ID_MATE,m.WORK_UNIT AS PO_WORK_UNIT,m.JOB_TITLE AS PO_JOB_TITLE,m.OFFICE_TEL AS PO_OFFICE_TEL,m.WORK_START_DATE AS PO_WORK_START_DATE,m.MARR_CERT_NO AS PO_MARR_CERT_NO,m.IDENT_NO AS PO_IDENT_NO,m.CAREER AS PO_CAREER,m.DUTY AS PO_DUTY,m.MOBILE AS PO_MOBILE,m.ANNUAL_INCOME AS PO_ANNUAL_INCOME,");
      sb.append(" k.CUST_ID AS KEYFLAG_CUST_ID,k.FOREIGN_PASSPORT_FLAG, g.CUST_GRADE_ID,g.CUST_GRADE,g.EVALUATE_DATE,");
      //与我行关系
      sb.append(" c.CUS_BANK_REL,c.CUS_CORP_REL,P.HOLD_CARD,p.REMARK,P.ANNUAL_INCOME, ");
      //配偶信息
      sb.append(" M.MATE_NAME as PO_MATE_NAME,");
      //保险情况
      sb.append(" K.HAS_ENDO_INSURE,K.HAS_MEDI_INSURE,K.HAS_IDLE_INSURE,K.HAS_INJURY_INSURE,K.HAS_BIRTH_INSURE,K.HAS_HOUSE_FUND,");
      //个人兴趣爱好
      sb.append(" L.CUST_ID AS LIKE_CUST_ID,  substr(L.INTERESTS,0,instr(L.INTERESTS,'|')-1) as INTERESTSS ,  substr(L.INTERESTS,instr(L.INTERESTS,'|')+1) as INTERESTS , ");

      sb.append(" p.POST_ADDR,p.POST_ZIPCODE,p.HOME_ADDR,p.RESIDENCE,p.HOME_ZIPCODE,p.HOME_TEL,p.MOBILE_PHONE,p.UNIT_FEX,p.EMAIL,");
      sb.append(" p.CAREER_TYPE,p.UNIT_NAME,p.UNIT_CHAR,p.PROFESSION,CASE WHEN BT.F_VALUE IS NULL THEN p.PROFESSION ELSE BT.F_VALUE END AS PROFESSION_NAME,p.UNIT_ADDR,p.UNIT_ZIPCODE,p.UNIT_TEL,p.CNT_NAME,p.CAREER_START_DATE,p.DUTY,p.CAREER_TITLE,p.SALARY_ACCT_BANK,p.SALARY_ACCT_NO,p.RESUME");

      sb.append(" from ACRM_F_CI_CUSTOMER c ");
      sb.append(" INNER JOIN ACRM_F_CI_PERSON P ON P.CUST_ID = C.CUST_ID ");
      sb.append(" left join ACRM_F_CI_PER_MATEINFO m on m.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_PER_KEYFLAG k on k.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_PER_LIKEINFO L on L.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_GRADE g on g.cust_id = c.cust_id and g.cust_grade_type='03'");
      sb.append(" left join ACRM_F_CI_BUSI_TYPE BT ON BT.F_CODE = P.PROFESSION");
      sb.append(" where c.cust_Id = '" + custId + "'");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      Map result = query.getJSON();
      List rowsList = 
        (List)result
        .get("data");
      HashMap resultMap = new HashMap();
      if ((rowsList != null) && (rowsList.size() > 0)) {
        resultMap = (HashMap)rowsList.get(0);
      }
      //查询证件表信息(证件号码1)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and i.ident_type= c.ident_type and i.ident_no = c.ident_no order by i.last_update_tm desc");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      List tempRowsList = 
        (List)query
        .getJSON().get("data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID", map.get("IDENT_ID"));
        resultMap.put("IDENT_EXPIRED_DATE", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID", "");
        resultMap.put("IDENT_EXPIRED_DATE", "");
      }
      //查询证件表信息(证件号码2)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) order by I.IDENT_TYPE DESC");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID1", map.get("IDENT_ID"));
        resultMap.put("IDENT_TYPE1", map.get("IDENT_TYPE"));
        resultMap.put("IDENT_NO1", map.get("IDENT_NO"));
        resultMap.put("IDENT_EXPIRED_DATE1", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID1", "");
        resultMap.put("IDENT_TYPE1", "");
        resultMap.put("IDENT_NO1", "");
        resultMap.put("IDENT_EXPIRED_DATE1", "");
      }
      //查询客户经理信息
      sb = new StringBuffer(
        "select id as MGR_KEY_ID,MGR_ID,CASE WHEN MGR_NAME IS NULL THEN MGR_ID ELSE MGR_NAME END AS MGR_NAME,EFFECT_DATE from OCRM_F_CI_BELONG_CUSTMGR mgr where mgr.cust_id = '" + 
        custId + "'");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("MGR_KEY_ID", map.get("MGR_KEY_ID"));
        resultMap.put("MGR_ID", map.get("MGR_ID"));
        resultMap.put("MGR_NAME", map.get("MGR_NAME"));
        resultMap.put("EFFECT_DATE", map.get("EFFECT_DATE"));
      } else {
        resultMap.put("MGR_KEY_ID", "");
        resultMap.put("MGR_ID", "");
        resultMap.put("MGR_NAME", "");
        resultMap.put("EFFECT_DATE", "");
      }
      //查主管机构信息
      sb = new StringBuffer(
        "select id as ORG_KEY_ID,INSTITUTION_CODE AS BELONG_ORG,INSTITUTION_NAME AS BELONG_ORG_NAME from OCRM_F_CI_BELONG_ORG org where org.cust_id = '" + 
        custId + "'");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("ORG_KEY_ID", map.get("ORG_KEY_ID"));
        resultMap.put("BELONG_ORG", map.get("BELONG_ORG"));
        resultMap.put("BELONG_ORG_NAME", map.get("BELONG_ORG_NAME"));
      } else {
        resultMap.put("ORG_KEY_ID", "");
        resultMap.put("BELONG_ORG", "");
        resultMap.put("BELONG_ORG_NAME", "");
      }

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询对公授信信息
	 * @return
	 */
  public String queryComsx()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");
      //查询统一客户表信息-机构客户表信息
      StringBuffer sb = new StringBuffer(
        "select distinct c.SHORT_NAME,c.EN_NAME,c.IDENT_TYPE,c.IDENT_NO,c.INOUT_FLAG,c.AR_CUST_FLAG,c.AR_CUST_TYPE,c.FIRST_LOAN_DATE,C.CUSTNM_IDENT_MODIFIED_FLAG");
      sb.append(" ,o.CUST_ID,o.ORG_CUST_TYPE,o.CUST_NAME,o.NATION_CODE,o.ENT_PROPERTY,o.INVEST_TYPE,o.COM_HOLD_TYPE,o.INDUSTRY_CATEGORY,o.EMPLOYEE_SCALE,o.ENT_BELONG,o.ENT_SCALE,o.ENT_SCALE_RH,o.BUILD_DATE,o.COM_HOLD_TYPE,o.ORG_CODE,o.ORG_EXP_DATE,o.ORG_CODE_ANN_DATE,o.ORG_REG_DATE,o.ORG_CODE_UNIT,o.MINOR_BUSINESS,o.MAIN_BUSINESS,o.COM_SP_BUSINESS,o.LOAN_CARD_FLAG,o.LOAN_CARD_NO,o.LOAN_CARD_STAT,o.LOAD_CARD_PWD,o.LOAD_CARD_AUDIT_DT,o.LEGAL_REPR_IDENT_TYPE,o.LEGAL_REPR_IDENT_NO,o.LEGAL_REPR_NAME,o.ORG_ADDR,o.ORG_ZIPCODE,o.ORG_FEX,o.ORG_HOMEPAGE,o.ORG_TEL,o.ORG_EMAIL, CASE WHEN (SYSDATE-BUILD_DATE) > 730 THEN '0' ELSE '1' END AS IS_BUILD_NEW,o.TOP_CORP_LEVEL,o.FIN_REP_TYPE,o.HOLD_STOCK_AMT");
      /***
		* 特种经营标识 选是时 所显示的字段 begin 
		* add 20150107
		*/
      sb.append(" ,O.COM_SP_LIC_NO,O.COM_SP_DETAIL,O.COM_SP_LIC_ORG,O.COM_SP_STR_DATE,O.COM_SP_END_DATE ");
      /**
		* end
		*/
      // add 法定代表人/负责人及其配偶信息 begin
      sb.append(" ,p1.RESUME as FRDB_RESUME,e2.LINKMAN_ID as FRDB_LINKMAN_ID,e2.IDENT_TYPE AS FRDB_IDENT_TYPE,e2.IDENT_NO AS FRDB_IDENT_NO,e2.LINKMAN_NAME AS FRDB_LINKMAN_NAME,e2.INDIV_CUS_ID AS FRDB_INDIV_CUS_ID,e2.SIGN_START_DATE AS FRDB_SIGN_START_DATE,e2.SIGN_END_DATE AS FRDB_SIGN_END_DATE ");
      sb.append(" ,e3.LINKMAN_ID as FRDBPO_LINKMAN_ID,e3.IDENT_TYPE AS FRDBPO_IDENT_TYPE,e3.IDENT_NO AS FRDBPO_IDENT_NO,e3.LINKMAN_NAME AS FRDBPO_LINKMAN_NAME,e3.INDIV_CUS_ID AS FRDBPO_INDIV_CUS_ID ");
      //end
	  //add 实际控制人及其配偶信息 begin
      sb.append(" ,p2.RESUME as SJKG_RESUME,e4.AUTH_START_DATE as SJKG_AUTH_START_DATE,e4.AUTH_END_DATE as SJKG_AUTH_END_DATE,e4.LINKMAN_ID as SJKG_LINKMAN_ID,e4.IDENT_TYPE AS SJKG_IDENT_TYPE,e4.IDENT_NO AS SJKG_IDENT_NO,e4.LINKMAN_NAME AS SJKG_LINKMAN_NAME,e4.INDIV_CUS_ID AS SJKG_INDIV_CUS_ID,e4.SIGN_START_DATE AS SJKG_SIGN_START_DATE,e4.SIGN_END_DATE AS SJKG_SIGN_END_DATE ");
      sb.append(" ,e5.LINKMAN_ID as SJKGPO_LINKMAN_ID,e5.IDENT_TYPE AS SJKGPO_IDENT_TYPE,e5.IDENT_NO AS SJKGPO_IDENT_NO,e5.LINKMAN_NAME AS SJKGPO_LINKMAN_NAME,e5.INDIV_CUS_ID AS SJKGPO_INDIV_CUS_ID ");
	  //end
	  //add  是否2年内新设立企业-  预计年度资产总额（元）- 预计营业收入（元）
      sb.append(",o.TOTAL_ASSETS,o.ANNUAL_INCOME ");
  	  //end
	  //add 进出口权标识         外汇许可证号码 - 主要生产设备  -实际生产能力 - 上级主管单位
      sb.append(" ,o.FEXC_PRM_CODE,o.FACILITY_MAIN,o.PROD_CAPACITY,o.COMP_ORG ");
      //end
	  //集团客户
      sb.append(" ,gm.MEMBER_TYPE,gm.GROUP_NO ");

      sb.append(" ,o.MAIN_INDUSTRY,CASE WHEN BT.F_VALUE IS NULL THEN O.MAIN_INDUSTRY ELSE BT.F_VALUE END AS MAIN_INDUSTRY_NAME,o.SUPER_DEPT,org.ORG_NAME as SUPER_DEPT_NAME");
      sb.append(" ,K.CUST_ID AS KEYFLAG_CUST_ID,k.IS_RURAL_CORP,k.IS_LISTED_CORP,k.HAS_IE_RIGHT,k.IS_PREP_ENT,k.IS_AREA_IMP_ENT,k.IS_NTNAL_MACRO_CTRL,k.IS_HIGH_RISK_POLL,k.IS_STEEL_ENT,k.IS_NOT_LOCAL_ENT");
      sb.append(" ,r.CUST_ID as REGISTER_CUST_ID,r.REGISTER_NO,r.REGISTER_DATE,r.REG_ORG,r.APPR_DOC_NO,r.REGISTER_ADDR,r.REGISTER_CAPITAL,r.REGISTER_TYPE,r.END_DATE,r.AUDIT_END_DATE,r.APPR_ORG,r.REGISTER_AREA,r.REGISTER_EN_ADDR,r.REGISTER_CAPITAL_CURR");
      sb.append(" ,e1.LINKMAN_ID as CWFZ_LINKMAN_ID,e1.linkman_name as CWFZ_PERSON,e.LINKMAN_ID as CWLX_LINKMAN_ID,e.linkman_name as CWLX_PERSON,e.mobile as CWLX_MOBILE ,b.CUST_ID AS BUSI_CUST_ID,b.MAIN_PRODUCT,b.WORK_FIELD_AREA,b.MANAGE_STAT,b.WORK_FIELD_OWNERSHIP ,g.CUST_GRADE_ID,g.cust_grade,g.evaluate_date");

      sb.append(" ,i.IDENT_ID AS IDENT_REG_ID,I.IDENT_NO AS IDENT_REG_NO,I.ident_org AS IDENT_REG_ORG,I.IDEN_REG_DATE,I.IDENT_VALID_PERIOD,I.IDENT_TYPE as IDENT_REG_TYPE");

      sb.append(" from ACRM_F_CI_CUSTOMER c ");
      sb.append(" INNER JOIN ACRM_F_CI_ORG o ON o.CUST_ID = c.CUST_ID ");
      sb.append(" left join ACRM_F_CI_ORG_KEYFLAG k on k.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e on e.org_cust_id = c.cust_id and e.linkman_type = '13'");
      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e1 on e1.org_cust_id = c.cust_id and e1.linkman_type = '10'");
      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e2 on e2.org_cust_id = c.cust_id and e2.linkman_type = '5'");//法人
      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e3 on e3.org_cust_id = c.cust_id and e3.linkman_type = '12'");//法人配偶
      sb.append(" left join ACRM_F_CI_CUSTOMER cr on cr.ident_no = e2.ident_no and cr.ident_type = e2.ident_type ");
      sb.append(" left join ACRM_F_CI_PERSON p1 on p1.cust_id = cr.cust_id ");

      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e4 on e4.org_cust_id = c.cust_id and e4.linkman_type = '6'");//实际控制人
      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e5 on e5.org_cust_id = c.cust_id and e5.linkman_type = '11'");//实际控制人配偶
      sb.append(" left join ACRM_F_CI_CUSTOMER cr1 on cr1.ident_no = e4.ident_no and cr1.ident_type = e4.ident_type ");
      sb.append(" left join ACRM_F_CI_PERSON p2 on p2.cust_id = cr1.cust_id ");

      sb.append(" left join OCRM_F_CI_GROUP_MEMBER gm on gm.cust_id = c.cust_id ");

      sb.append(" left join ACRM_F_CI_CUST_IDENTIFIER i on i.cust_id = c.cust_id and i.IDENT_TYPE = 'Y'");//地税登记证
      sb.append(" left join ACRM_F_CI_ORG_BUSIINFO b on b.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_ORG_REGISTERINFO r on r.cust_id = c.cust_id ");
      sb.append(" left join ADMIN_AUTH_ORG org on org.ORG_ID = o.SUPER_DEPT");
      sb.append(" left join ACRM_F_CI_GRADE g on g.cust_id = c.cust_id and g.cust_grade_type='03'");//信用等级
      sb.append(" left join ACRM_F_CI_BUSI_TYPE BT ON BT.F_CODE = o.MAIN_INDUSTRY");
      sb.append(" where c.cust_Id = '" + custId + "'");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      Map result = query.getJSON();
      List rowsList = 
        (List)result
        .get("data");
      HashMap resultMap = new HashMap();
      if ((rowsList != null) && (rowsList.size() > 0)) {
        resultMap = (HashMap)rowsList.get(0);
      }

      List tempRowsList = null;
      //查询证件表信息(证件号码，对应的ID)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and i.ident_type= c.ident_type and i.ident_no = c.ident_no order by i.last_update_tm desc");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID1", map.get("IDENT_ID"));
      } else {
        resultMap.put("IDENT_ID1", "");
      }
      //查询客户经理信息
      sb = new StringBuffer(
        "select id as MGR_KEY_ID,MGR_ID,MGR_NAME from OCRM_F_CI_BELONG_CUSTMGR mgr where mgr.cust_id = '" + 
        custId + "'");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("MGR_KEY_ID", map.get("MGR_KEY_ID"));
        resultMap.put("MGR_ID", map.get("MGR_ID"));
        resultMap.put("MGR_NAME", map.get("MGR_NAME"));
      } else {
        resultMap.put("MGR_KEY_ID", "");
        resultMap.put("MGR_ID", "");
        resultMap.put("MGR_NAME", "");
      }
      //查主管机构信息
      sb = new StringBuffer(
        "select id as ORG_KEY_ID,INSTITUTION_CODE AS BELONG_ORG,INSTITUTION_NAME AS BELONG_ORG_NAME from OCRM_F_CI_BELONG_ORG org where org.cust_id = '" + 
        custId + "'");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("ORG_KEY_ID", map.get("ORG_KEY_ID"));
        resultMap.put("BELONG_ORG", map.get("BELONG_ORG"));
        resultMap.put("BELONG_ORG_NAME", map.get("BELONG_ORG_NAME"));
      } else {
        resultMap.put("ORG_KEY_ID", "");
        resultMap.put("BELONG_ORG", "");
        resultMap.put("BELONG_ORG_NAME", "");
      }

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 查询对公非授信第三屏客户信息
	 * @return
	 */
  public String queryComfsxThree()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      String custId = this.request.getParameter("custId");
      //查询统一客户表信息-对公客户表信息
      StringBuffer sb = new StringBuffer(
        "select c.cust_id,c.cust_type,c.ident_type,c.ident_no,c.CUST_NAME");
      sb.append(" ,e.LINKMAN_ID as LEGAL_ID,E.LINKMAN_NAME AS LEGAL_REPR_NAME,e.IDENT_TYPE AS LEGAL_REPR_IDENT_TYPE,e.IDENT_NO AS LEGAL_REPR_IDENT_NO,e.IDENT_EXPIRED_DATE AS LEGAL_IDENT_EXPIRED_DATE,e.ARTIFICIAL_PERSON as LEGAL_ARTIFICIAL_PERSON,O.EMPLOYEE_SCALE,O.ENT_SCALE_CK,O.REMARK");

      sb.append(" ,REG.CUST_ID AS REG_CUST_ID,REG.REGISTER_CAPITAL_CURR,REG.REGISTER_CAPITAL,B.CUST_ID AS BUSI_CUST_ID,B.SALE_CCY,B.SALE_AMT");

      sb.append(" from ACRM_F_CI_CUSTOMER c ");
      sb.append(" inner join ACRM_F_CI_ORG o on o.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_ORG_REGISTERINFO reg on reg.cust_id = c.CUST_ID ");
      sb.append(" left join ACRM_F_CI_ORG_BUSIINFO b on b.cust_id = c.cust_id ");
      sb.append(" left join ACRM_F_CI_ORG_EXECUTIVEINFO e on e.LINKMAN_TYPE = '5' and e.ORG_CUST_ID = c.CUST_ID");
      sb.append(" where c.cust_Id = '" + custId + "'");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection());
      Map result = query.getJSON();
      List rowsList = 
        (List)result
        .get("data");
      HashMap resultMap = new HashMap();
      if ((rowsList != null) && (rowsList.size() > 0)) {
        resultMap = (HashMap)rowsList.get(0);
      }
      //查询证件表信息(证件号码1)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and i.ident_type= c.ident_type and i.ident_no = c.ident_no order by i.last_update_tm desc");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      List tempRowsList = 
        (List)query
        .getJSON().get("data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID", map.get("IDENT_ID"));
        resultMap.put("IDENT_EXPIRED_DATE", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID", "");
        resultMap.put("IDENT_EXPIRED_DATE", "");
      }
      //查询证件表信息(证件号码2)
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) and i.ident_type NOT IN ('V','15X','W','Y','C') UNION select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date," + 
        "i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id where i.cust_id = '" + 
        custId + 
        "' and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) and i.ident_type ='C'  and trim(i.ident_no) is not null order by IDENT_TYPE DESC");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("IDENT_ID1", map.get("IDENT_ID"));
        resultMap.put("IDENT_TYPE1", map.get("IDENT_TYPE"));
        resultMap.put("IDENT_NO1", map.get("IDENT_NO"));
        resultMap.put("IDENT_EXPIRED_DATE1", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("IDENT_ID1", "");
        resultMap.put("IDENT_TYPE1", "");
        resultMap.put("IDENT_NO1", "");
        resultMap.put("IDENT_EXPIRED_DATE1", "");
      }
      //查询税务登记证
      sb = new StringBuffer(
        "select i.ident_id,i.cust_id,i.ident_type,i.ident_no,i.ident_effective_date,i.ident_expired_date from ACRM_F_CI_CUST_IDENTIFIER i ");
      sb.append(" where i.cust_id = '" + custId + 
        "' and i.ident_type = 'V' order by i.last_update_tm desc");
      query = new QueryHelper(sb.toString(), this.ds.getConnection());
      tempRowsList = (List)query.getJSON().get(
        "data");
      if ((tempRowsList != null) && (tempRowsList.size() > 0)) {
        Map map = (Map)tempRowsList.get(0);
        resultMap.put("TAX_REG_ID", map.get("IDENT_ID"));
        resultMap.put("TAX_REGISTRATION_NO", map.get("IDENT_NO"));
        resultMap.put("TAX_IDENT_EXPIRED_DATE", 
          map.get("IDENT_EXPIRED_DATE"));
      } else {
        resultMap.put("TAX_REG_ID", "");
        resultMap.put("TAX_REGISTRATION_NO", "");
        resultMap.put("TAX_IDENT_EXPIRED_DATE", "");
      }

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
  /**
	 * 查询客户信息修改历史,便于用于打印
	 * @return
	 */
  public String queryCustUpdateHis()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      if (this.request.getParameter("start") != null)
        this.start = Integer.parseInt(this.request.getParameter("start"));
      if (this.request.getParameter("limit") != null)
        this.limit = Integer.parseInt(this.request.getParameter("limit"));
      int currentPage = this.start / this.limit + 1;
      PagingInfo pi = null;
      if ((this.request.getParameter("start") != null) && 
        (this.request.getParameter("limit") != null)) {
        pi = new PagingInfo(this.limit, currentPage);
      }
      String custId = this.request.getParameter("custId");

      StringBuffer sb = new StringBuffer(
        " select T.UP_ID,T.UPDATE_DATE,T.UPDATE_ITEM,T.UPDATE_BE_CONT,T.UPDATE_USER,T.CUST_ID,T.CUST_NAME,T.UPDATE_FLAG,T.UPDATE_ITEM_EN,T.UPDATE_TABLE,T.UPDATE_TABLE_ID,T.UPDATE_AF_CONT_VIEW, T.USER_NAME,T.APPR_NAME,t.APPR_DATE from (SELECT T.UP_ID,T.UPDATE_DATE,T.UPDATE_ITEM,T.UPDATE_BE_CONT,T.UPDATE_USER,T.CUST_ID,T.CUST_NAME,T.UPDATE_FLAG,T.UPDATE_ITEM_EN,T.UPDATE_TABLE,T.UPDATE_TABLE_ID,     T.UPDATE_AF_CONT_VIEW,A.USER_NAME,A1.USER_NAME AS APPR_NAME,t.APPR_DATE FROM OCRM_F_CI_CUSTINFO_UPHIS T  LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.UPDATE_USER  LEFT JOIN ADMIN_AUTH_ACCOUNT A1 ON A1.ACCOUNT_NAME = T.APPR_USER WHERE T.APPR_FLAG = '1' AND (T.UPDATE_TABLE_ID IS NULL OR T.UPDATE_TABLE_ID != '1') AND (T.UPDATE_ITEM IS NOT NULL OR T.UPDATE_ITEM <> '') union all SELECT H.UP_ID,H.UPDATE_DATE,H.UPDATE_ITEM,H.UPDATE_BE_CONT,H.UPDATE_USER,H.CUST_ID,H.CUST_NAME,H.UPDATE_FLAG,H.UPDATE_ITEM_EN,H.UPDATE_TABLE,H.UPDATE_TABLE_ID,H.UPDATE_AF_CONT AS UPDATE_AF_CONT_VIEW,     CI.CUST_NAME || '(客户)' AS USER_NAME,'' AS APPR_NAME,to_char(H.UPDATE_DATE,'yyyy-MM-dd HH24:mi:ss') AS  APPR_DATE FROM OCRM_F_CI_CUSTINFO_UPHIS H   LEFT JOIN ACRM_F_CI_CUSTOMER CI ON CI.CUST_ID = H.CUST_ID WHERE H.APPR_FLAG = 'X'   AND (H.UPDATE_ITEM IS NOT NULL OR H.UPDATE_ITEM <> '')) T WHERE  T.CUST_ID ='" + 
        custId + "'");
      setJson(this.request.getParameter("condition"));
      for (String key : getJson().keySet()) {
        if ((getJson().get(key) != null) && 
          (!"".equals(getJson().get(key)))) {
          if ("START_DATE".equals(key)) {
            sb.append(" AND to_date(t.APPR_DATE,'yyyy-MM-dd HH24:mi:ss') >= to_date('" + 
              getJson().get(key) + 
              " 00:00:00','yyyy-MM-dd HH24:mi:ss')");
          }
          if ("END_DATE".equals(key)) {
            sb.append(" AND to_date(t.APPR_DATE,'yyyy-MM-dd HH24:mi:ss') <= to_date('" + 
              getJson().get(key) + 
              " 23:59:59','yyyy-MM-dd HH24:mi:ss')");
          }
          if ("UPDATE_ITEM".equals(key)) {
            sb.append(" AND t.UPDATE_ITEM LIKE '%" + 
              getJson().get(key) + "%' ");
          }
          if ("UPDATE_USER".equals(key)) {
            sb.append(" AND (T.USER_NAME LIKE '%" + 
              getJson().get(key) + 
              "%' OR T.USER_NAME like '%" + 
              getJson().get(key) + "%')");
          }
        }
      }

      sb.append(" ORDER BY T.APPR_DATE DESC,T.UPDATE_FLAG DESC,T.UPDATE_USER ,T.UPDATE_ITEM_EN ");

      QueryHelper query = new QueryHelper(sb.toString(), this.ds.getConnection(), pi);
      Map result = query.getJSON();

      if (this.json != null)
        this.json.clear();
      else
        this.json = new HashMap();
      this.json.put("json", result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 对私客户授信信息修改提交审批
	 * @return
	 * @throws Exception
	 */
  public String initFlowPersx()
    throws Exception
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      HttpServletResponse response = (HttpServletResponse)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
      AuthUser auth = (AuthUser)SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

      String custId = this.request.getParameter("custId");
      String custName = this.request.getParameter("custName");
      String s1 = this.request.getParameter("perModel");
      JSONArray jarray = JSONArray.fromObject(s1);
      //验证是否已经提交审核,验证不通过会抛出异常
      this.customerQueryAllService.judgeExist("CI_" + custId);
      //验证修改后，证件1号码是否已经存在，如若已存在，禁止提交
      this.customerQueryAllService.identNoExist(jarray, custId);

      Map paramMap = new HashMap();
      String nextNode = "70_a4";
      List list = auth.getRolesInfo();
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object m = localIterator.next();
        Map map = (Map)m;
        paramMap.put("role", map.get("ROLE_CODE"));
        if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
          nextNode = "70_a4";
        }
        else if (("R302".equals(map.get("ROLE_CODE"))) || 
          ("R303".equals(map.get("ROLE_CODE")))) {// 个法金ARM,RM
          nextNode = "70_a5";
        }
        else if (("R104".equals(map.get("ROLE_CODE"))) || 
          ("R105".equals(map.get("ROLE_CODE"))) || 
          ("R304".equals(map.get("ROLE_CODE"))) || 
          ("R305".equals(map.get("ROLE_CODE")))) {
          nextNode = "70_a7";
        }

      }

      String flag = DateUtils.currentTimeMillis();// 修改标识更改为毫秒级
      String instanceid = "CI_" + custId + "_" + flag;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
      String jobName = "对私授信信息修改_" + custName;// 自定义流程名称

      this.customerQueryAllService
        .bathsave(jarray, new Date(), flag, "对私授信信息");
      this.customerQueryAllService.initWorkflowByWfidAndInstanceid("70", 
        jobName, paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等

      response.getWriter().write(
        "{\"instanceid\":\"" + instanceid + 
        "\",\"currNode\":\"70_a3\",\"nextNode\":\"" + 
        nextNode + "\"}");
      response.getWriter().flush();
    } catch (BizException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
	/**
	 * 对公授信信息
	 * @return
	 * @throws Exception
	 */
  public String initFlowOrgsx()
    throws Exception
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      HttpServletResponse response = (HttpServletResponse)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
      AuthUser auth = (AuthUser)SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

      String custId = this.request.getParameter("custId");
      String custName = this.request.getParameter("custName");
      String s1 = this.request.getParameter("orgModel");
      JSONArray jarray = JSONArray.fromObject(s1);
      //验证是否已经提交审核,验证不通过会抛出异常
      this.customerQueryAllService.judgeExist("CI_" + custId);
	  //验证修改后，证件1号码是否已经存在，如若已存在，禁止提交
      this.customerQueryAllService.identNoExist(jarray, custId);

      Map paramMap = new HashMap();
      String nextNode = "80_a4";
      List list = auth.getRolesInfo();
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object m = localIterator.next();
        Map map = (Map)m;
        paramMap.put("role", map.get("ROLE_CODE"));
        if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办---op复核
          nextNode = "80_a4";
        }
        else if (("R302".equals(map.get("ROLE_CODE"))) || 
          ("R303".equals(map.get("ROLE_CODE")))) {// 个法金ARM,RM--AO主管
          nextNode = "80_a5";
        }
        else if (("R104".equals(map.get("ROLE_CODE"))) || 
          ("R105".equals(map.get("ROLE_CODE"))) || 
          ("R304".equals(map.get("ROLE_CODE"))) || 
          ("R305".equals(map.get("ROLE_CODE")))) {//总分行法金ARM,RM
          nextNode = "80_a7";
        }

      }

      String flag = DateUtils.currentTimeMillis();// 修改标识更改为毫秒级
      String instanceid = "CI_" + custId + "_" + flag;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
      String jobName = "对公授信信息修改_" + custName;// 自定义流程名称

      this.customerQueryAllService
        .bathsave(jarray, new Date(), flag, "对公授信信息");
      this.customerQueryAllService.initWorkflowByWfidAndInstanceid("80", 
        jobName, paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等

      response.getWriter().write(
        "{\"instanceid\":\"" + instanceid + 
        "\",\"currNode\":\"80_a3\",\"nextNode\":\"" + 
        nextNode + "\"}");
      response.getWriter().flush();
    } catch (BizException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "1002", e.getMessage(), new Object[0]);
    }
    return "success";
  }
  /**
	 * 对私非授信一次提交
	 * @throws Exception 
	 */
  public void commitFsxPerAll()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      HttpServletResponse response = (HttpServletResponse)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");

      String custId = this.request.getParameter("custId");
      String custName = this.request.getParameter("custName");
      //验证是否已经提交审核,验证不通过会抛出异常
      this.customerQueryAllService.judgeExist("CI_" + custId);

      String fsxFirst = this.request.getParameter("fsxFirst");
      String fsxSec_1 = this.request.getParameter("fsxSec_1");
      String fsxSec_2 = this.request.getParameter("fsxSec_2");
      String fsxSec_3 = this.request.getParameter("fsxSec_3");

      JSONArray jFsxFirst = JSONArray.fromObject(fsxFirst);
      JSONArray jFsxSec_1 = JSONArray.fromObject(fsxSec_1);
      JSONArray jFsxSec_2 = JSONArray.fromObject(fsxSec_2);
      JSONArray jFsxSec_3 = JSONArray.fromObject(fsxSec_3);
      //验证修改后，证件1号码是否已经存在，如若已存在，禁止提交
      this.customerQueryAllService.identNoExist(jFsxFirst, custId);

      List returnList = this.customerQueryAllService.commitFsxPerAll(
        jFsxFirst, jFsxSec_1, jFsxSec_2, jFsxSec_3, custId, 
        custName);
      String result = JSONUtil.serialize(returnList);

      response.getWriter().write(result);
      response.getWriter().flush();
    } catch (BizException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "0000", "操作失败,请联系管理员", new Object[0]);
    }
  }
  /**
	 * 对公非授信一次提交
	 * @throws Exception 
	 */
  public void commitFsxComAll()
  {
    try
    {
      ActionContext ctx = ActionContext.getContext();
      this.request = 
        ((HttpServletRequest)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
      HttpServletResponse response = (HttpServletResponse)ctx
        .get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");

      String custId = this.request.getParameter("custId");
      String custName = this.request.getParameter("custName");
      //验证是否已经提交审核,验证不通过会抛出异常
      this.customerQueryAllService.judgeExist("CI_" + custId);

      String fsxFirst = this.request.getParameter("fsxFirst");
      String fsxSec_1 = this.request.getParameter("fsxSec_1");
      String fsxSec_2 = this.request.getParameter("fsxSec_2");
      String fsxSec_3 = this.request.getParameter("fsxSec_3");
      String fsxSec_4 = this.request.getParameter("fsxSec_4");//股东信息
      String fsxThird = this.request.getParameter("fsxThird");

      JSONArray jFsxFirst = JSONArray.fromObject(fsxFirst);
      JSONArray jFsxSec_1 = JSONArray.fromObject(fsxSec_1);
      JSONArray jFsxSec_2 = JSONArray.fromObject(fsxSec_2);
      JSONArray jFsxSec_3 = JSONArray.fromObject(fsxSec_3);
      JSONArray jFsxSec_4 = JSONArray.fromObject(fsxSec_4);
      JSONArray jfsxThird = JSONArray.fromObject(fsxThird);
      //验证修改后，证件1号码是否已经存在，如若已存在，禁止提交
      this.customerQueryAllService.identNoExist(jFsxFirst, custId);

      List returnList = this.customerQueryAllService.commitFsxComAll(
        jFsxFirst, jFsxSec_1, jFsxSec_2, jFsxSec_3, jFsxSec_4, jfsxThird, 
        custId, custName);
      String result = JSONUtil.serialize(returnList);

      response.getWriter().write(result);
      response.getWriter().flush();
    } catch (BizException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(1, 2, "0000", "操作失败,请联系管理员", new Object[0]);
    }
  }
  
  /**
	 * 判断rm或op是否有复核信息
	 * @return
	 */
	public String isOp() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String custId = request.getParameter("custId");

//			StringBuffer sb = new StringBuffer(" select A.* ,B.ROLE_CODE from (select t.update_date, t.update_user, t.cust_id, ROW_NUMBER() OVER( order by t.update_date desc) AS rm "
//					+ "  from ocrm_f_ci_custinfo_uphis t where t.cust_id = " +custId
//					+ "  and t.update_item_en = 'IS_TAIWAN_CORP' and t.appr_flag = '1') a "
//					// rm或op有复核信息
//					+ "  left join admin_auth_account c on c.account_name = a.update_user "
//					+ "  left join admin_auth_account_role r on r.account_id = c.id " 
//					+ "  join admin_auth_role b on b.id = r.role_id and b.role_code = 'R300'" 
//					+ "  where a.rm = 1");
			
			StringBuffer sb = new StringBuffer(" select A.*  from (select t.update_date, t.update_user, t.cust_id, ROW_NUMBER() OVER( order by t.update_date desc) AS rm "
					+ "  from ocrm_f_ci_custinfo_uphis t where t.cust_id = '" +custId
					+ "'  and t.update_item_en = 'IS_TAIWAN_CORP' and t.appr_flag = '1') a ");
			
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
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