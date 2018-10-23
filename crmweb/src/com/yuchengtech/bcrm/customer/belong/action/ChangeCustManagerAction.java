package com.yuchengtech.bcrm.customer.belong.action;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransApply;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransCust;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransApplyService;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransCustService;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransMgrService;
import com.yuchengtech.bcrm.customer.service.BelongCustmgrService;
import com.yuchengtech.bcrm.customer.service.CustomerTransService;
import com.yuchengtech.bcrm.custview.service.CustBelOrgInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.DatabaseHelper;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.download.DownloadThread;
import com.yuchengtech.bob.download.DownloadThreadManager;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.crm.exception.BizException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Action("/changeCustManager")
public class ChangeCustManagerAction extends CommonAction{
  private static Logger log = Logger.getLogger(ChangeCustManagerAction.class);

  @Autowired
  private OcrmFCiTransApplyService service;

  @Autowired
  private OcrmFCiTransCustService cservice;

  @Autowired
  private OcrmFCiTransMgrService mgservice;

  @Autowired
  private BelongCustmgrService mservice;

  @Autowired
  private CustBelOrgInfoService oservice;

  @Autowired
  private CustomerTransService hservice;

  @Autowired
  @Qualifier("dsOracle")
  private DataSource ds;
  AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

  @Autowired
  public void init() {
    this.model = new OcrmFCiTransApply();
    setCommonService(this.service);
  }

  public void prepare() {
    ActionContext ctx = ActionContext.getContext();
    this.request = ((HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
    String impFlag=(String)json.get("impFlag");
	json.remove("impFlag");
	StringBuffer sb = new StringBuffer();
	if("1".equals(impFlag)){
		sb.append(" select m.id,m.cust_id,m.mgr_id,m.main_type,m.maintain_right,m.check_right,  "
				+ "case when  his.assign_user is null then m.assign_user else his.assign_user end as assign_user,  "
				+ "case when his.assign_username is null then m.assign_username else his.assign_username end as assign_username,  "
				+ "case when his.assign_date is null then m.assign_date else his.assign_date end   as assign_date,  m.institution,m.institution_name,m.mgr_name,   "
				+ "case when his.WORK_TRAN_DATE is null then m.effect_date else his.WORK_TRAN_DATE end  as effect_date ,  c.cust_name, c.cust_type  from OCRM_F_CI_BELONG_CUSTMGR_TEMP temp  "
				+ "left join OCRM_F_CI_BELONG_CUSTMGR m on m.cust_id=temp.cust_id and temp.create_user='"+auth.getUserId()+"' "
				+ "left join  ACRM_F_CI_CUSTOMER c on m.cust_id=c.cust_id  "
				+ "left join (select t.*,row_number() over (partition by t.cust_id order by t.work_tran_date desc) as rn from OCRM_F_CI_BELONG_HIST t ) his   on m.cust_id=his.cust_id and his.rn=1  "
				+ "left join (select RECORD_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO in (select APPLY_NO from OCRM_F_CI_TRANS_APPLY where APPROVE_STAT='1')) wx on wx.RECORD_ID=m.id  and wx.RECORD_ID is null   "
				+ "where m.cust_id=c.cust_id  "
				+ "AND NOT EXISTS (SELECT 1 FROM OCRM_F_CI_TRANS_CUST TC, OCRM_F_CI_TRANS_APPLY TA WHERE TC.APPLY_NO = TA.APPLY_NO AND TA.APPROVE_STAT = '1' AND TC.CUST_ID = M.CUST_ID)  ");
	}else{
		String type = this.request.getParameter("type");
	    sb.append(
	      " select m.id,m.cust_id,m.mgr_id,m.main_type,m.maintain_right,m.check_right,  "
	      + "case when  his.assign_user is null then m.assign_user else his.assign_user end as assign_user,  "
	      + "case when his.assign_username is null then m.assign_username else his.assign_username end as assign_username,  "
	      + "case when his.assign_date is null then m.assign_date else his.assign_date end   as assign_date,  m.institution,m.institution_name,m.mgr_name,   "
	      + "case when his.WORK_TRAN_DATE is null then m.effect_date else his.WORK_TRAN_DATE end  as effect_date ,  c.cust_name, c.cust_type  "
	      + "from OCRM_F_CI_BELONG_CUSTMGR m  "
	      + "left join  ACRM_F_CI_CUSTOMER c on m.cust_id=c.cust_id  "
	      + "left join (select t.*,row_number() over (partition by t.cust_id order by t.work_tran_date desc) as rn from OCRM_F_CI_BELONG_HIST t ) his   on m.cust_id=his.cust_id and his.rn=1  "
	      + "left join (select RECORD_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO in (select APPLY_NO from OCRM_F_CI_TRANS_APPLY where APPROVE_STAT='1')) wx on wx.RECORD_ID=m.id  and wx.RECORD_ID is null   "
	      + "where m.cust_id=c.cust_id  "
	      + "AND NOT EXISTS (SELECT 1 FROM OCRM_F_CI_TRANS_CUST TC, OCRM_F_CI_TRANS_APPLY TA WHERE TC.APPLY_NO = TA.APPLY_NO AND TA.APPROVE_STAT = '1' AND TC.CUST_ID = M.CUST_ID)  ");
	    //此处疑似没有用到，考虑删除
	    if ("charge".equals(type)) {
	      if ("DB2".equals(SystemConstance.DB_TYPE)) {
	        StringBuilder withSb = new StringBuilder();
	        withSb.append("with rpl (org_id,up_org_id) as  (  select org_id,up_org_id   from admin_auth_org   where org_id ='" + 
	          this.auth.getUnitId() + 
	          "' " + 
	          " union all  select  child.org_id,child.up_org_id from rpl parent, admin_auth_org child where child.up_org_id=parent.org_id " + 
	          " ) ");
	        this.withSQL = withSb.toString();
	        sb.append(" and m.INSTITUTION in (select org_id from rpl)");
	      } else {
	        sb.append(" and m.INSTITUTION in (select org_id from admin_auth_org start with org_id = '" + 
	          this.auth.getUnitId() + 
	          "' connect by UP_ORG_ID = prior org_id )");
	      }
	    }
	    if ("mgr".equals(type)) {
	      sb.append(" and m.mgr_id ='" + this.auth.getUserId() + "'");
	    }

	    for (String key : getJson().keySet()) {
	      if ((getJson().get(key) != null) && 
	        (!getJson().get(key).equals(""))) {
	        if (((key != null) && (key.equals("CUST_ID"))) || 
	          ("CUST_NAME".equals(key)) || 
	          ("CUST_TYPE".equals(key)))
	          sb.append("  and c." + key + " like '%" + 
	            getJson().get(key) + "%'  ");
	        else if ((key != null) && (key.equals("ASSIGN_DATE")))
	          sb.append("  and m.ASSIGN_DATE =to_date('%" + 
	            getJson().get(key).toString().substring(0, 10) + 
	            "%'  ,'yyyy-mm-dd')");
	        else if (key != null) {
	          sb.append("  and  m." + key + " like '%" + 
	            getJson().get(key) + "%' ");
	        }
	      }
	    }
	}
    
    this.SQL = sb.toString();
    this.datasource = this.ds;
  }
  
  /**
	 * 查询导入时的客户类型
	 */
	public void searchCustId(){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct m.cust_type from OCRM_F_CI_BELONG_CUSTMGR_TEMP t,ACRM_F_CI_CUSTOMER m where t.create_user='"+auth.getUserId()+"' and m.cust_id=t.cust_id and t.imp_status='1'");
		QueryHelper query;
		try {
			 query = new QueryHelper(sb.toString(), ds.getConnection());
			 List<HashMap<String, Object>>	tempRowsList = (List<HashMap<String, Object>>) query.getJSON().get("data");
			 Map<String,Object> resultMap =  new HashMap<String,Object>();
			 if(tempRowsList.size()>0){
				 resultMap.put("CUST_TYPE",tempRowsList.get(0).get("CUST_TYPE"));
				 this.setJson(resultMap);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  public void changeMgrTrans() throws Exception {
    ActionContext ctx = ActionContext.getContext();
    this.request = 
      ((HttpServletRequest)ctx
      .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
    HttpServletResponse response = (HttpServletResponse)ctx
      .get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");

    String tMgr = this.request.getParameter("tMgr");
    boolean recevierflag = checkRecevierMgrName(tMgr);
    if (!recevierflag) {
      throw new BizException(1, 0, "0000", "接受的角色不是客户经理", new Object[0]);
    }
    String type = this.request.getParameter("type");
    if (("1".equals(type)) && 
      ("3".equals(((OcrmFCiTransApply)this.model).getHandKind()))) {
      type = "2";
    }
    ((OcrmFCiTransApply)this.model).setTMgrId(tMgr);
    ((OcrmFCiTransApply)this.model).setApplyDate(new Date());
    ((OcrmFCiTransApply)this.model).setApproveStat("1");
    ((OcrmFCiTransApply)this.model).setUserId(this.auth.getUserId());
    ((OcrmFCiTransApply)this.model).setUserName(this.auth.getUsername());
    ((OcrmFCiTransApply)this.model).setApplyType(type);
    ((OcrmFCiTransApply)this.model).setType("1");

    this.service.save(this.model);

    Long id = ((OcrmFCiTransApply)this.model).getApplyNo();

    String RECORD_IDs = this.request.getParameter("RECORD_IDs");
    String CUST_IDs = this.request.getParameter("CUST_IDs");
    String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
    String MGR_IDs = this.request.getParameter("MGR_IDs");
    String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
    String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
    String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
    String INSTITUTION_NAMEs = this.request
      .getParameter("INSTITUTION_NAMEs");
    String MAIN_TYPE_NEWs = "";

    JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
    JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
    JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
    JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
    JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
    JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
    JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
    JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
    JSONObject jsonObject9 = null;

    JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
    JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
    JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
    JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
    JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
    JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
    JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
    JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
    JSONArray jarray9 = null;
    if (("3".equals(type)) || ("4".equals(type)) || ("5".equals(type))) {
      MAIN_TYPE_NEWs = this.request.getParameter("MAIN_TYPE_NEWs");
      jsonObject9 = JSONObject.fromObject(MAIN_TYPE_NEWs);
      jarray9 = jsonObject9.getJSONArray("MAIN_TYPE_NEW");
    }

    for (int i = 0; i < jarray1.size(); i++) {
      OcrmFCiTransCust cust = new OcrmFCiTransCust();
      cust.setApplyNo(id.toString());
      cust.setRecordId(jarray1.getString(i));
      cust.setCustId(jarray2.getString(i));
      cust.setCustName(jarray3.getString(i));
      cust.setMgrId(jarray4.getString(i));
      cust.setMgrName(jarray5.getString(i));
      cust.setMainType(jarray6.getString(i));
      cust.setInstitution(jarray7.getString(i));
      cust.setInstitutionName(jarray8.getString(i));
      if (("3".equals(type)) || ("4".equals(type)) || ("5".equals(type))) {
        cust.setMainTypeNew(jarray9.getString(i));
      }
      cust.setState("1");
      this.cservice.save(cust);
    }

    String name = ((OcrmFCiTransApply)this.model).getTMgrName();
    String instanceid = "YJ_" + id;
    String jobName = "客户移交_" + name;

    Map mapParma = new HashMap();
    mapParma.put("type", type);

    String nextStr = "106";

    String currNode = "106_a3";

    String nextNode = "106_a5";
    boolean flag = false;
    boolean sendFlag = false;
    boolean branchFlag = false;
    switch (Integer.valueOf(type).intValue()) {
    case 3:
      branchFlag = checkBranchLeader(id);
      if (branchFlag) {
        currNode = "116_a3";
        nextNode = "116_a4";
        nextStr = "116";
      } else {
        currNode = "106_a3";
        nextNode = "106_a5";
        nextStr = "106";
      }
      break;
    case 4:
      flag = checkLeader(id);
      branchFlag = checkBranchLeader(id);
      if (flag) {
        if (branchFlag) {
          currNode = "118_a3";
          nextNode = "118_a4";
          nextStr = "118";
        } else {
          currNode = "110_a3";
          nextNode = "110_a4";
          nextStr = "110";
        }
      } else if (branchFlag) {
        currNode = "117_a3";
        nextNode = "117_a4";
        nextStr = "117";
      } else {
        currNode = "107_a3";
        nextNode = "107_a4";
        nextStr = "107";
      }

      break;
    case 5:
      flag = checkLeader(id);
      sendFlag = checkSendLeader(id);
      if ((flag) && (sendFlag)) {
        currNode = "113_a3";
        nextNode = "113_a4";
        nextStr = "113";
      } else if ((flag) && (!sendFlag)) {
        currNode = "111_a3";
        nextNode = "111_a4";
        nextStr = "111";
      } else if ((!flag) && (sendFlag)) {
        currNode = "112_a3";
        nextNode = "112_a4";
        nextStr = "112";
      } else if ((!flag) && (!sendFlag)) {
        currNode = "108_a3";
        nextNode = "108_a4";
        nextStr = "108";
      }
      break;
    }

    this.service.initWorkflowByWfidAndInstanceid(nextStr, jobName, 
      mapParma, instanceid);

    Map map = new HashMap();
    map.put("instanceid", instanceid);
    map.put("currNode", currNode);
    map.put("nextNode", nextNode);
    setJson(map);

    //response.getWriter().write("{\"instanceid\":\"" + instanceid + "\",\"currNode\":\"" + currNode + "\",\"nextNode\":\"" + nextNode + "\"}");
    //response.getWriter().flush();
  }

  public boolean checkLeader(Long id) throws Exception {
    Connection connection = null;
    Statement ps = null;
    ResultSet rs = null;
    boolean flag = false;

    String sql = "SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME,AR.ID FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN (SELECT * FROM ADMIN_AUTH_ACCOUNT T WHERE T.ORG_ID IN( SELECT ORG_ID FROM ADMIN_AUTH_ORG START WITH ORG_ID=(SELECT O.UP_ORG_ID FROM ADMIN_AUTH_ORG  O  WHERE ORG_ID=(SELECT ORG_ID FROM ADMIN_AUTH_ACCOUNT WHERE ACCOUNT_NAME =(SELECT T_MGR_ID FROM OCRM_F_CI_TRANS_APPLY WHERE APPLY_NO = '" + 
      id + 
      "'))) CONNECT BY UP_ORG_ID = PRIOR ORG_ID)) ACC" + 
      " ON ACC.ID = AR.ACCOUNT_ID" + 
      " INNER JOIN SYS_UNITS ORG" + 
      " ON ORG.UNITID = ACC.ORG_ID" + 
      " WHERE AR.APP_ID = 62" + 
      " AND AR.ROLE_ID = 202" + 
      " ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY";

    connection = this.ds.getConnection();
    ps = connection.createStatement();
    rs = ps.executeQuery(sql);
    List list = new ArrayList();
    while (rs.next()) {
      String area_leader = rs.getString(1);
      list.add(area_leader);
    }
    if ((list != null) && (list.size() == 1)) {
      String sql1 = " SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*  FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID  FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 311 ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY WHERE org_id=(select t.t_org_id from OCRM_F_CI_TRANS_APPLY t where t.apply_no='" + 
        id + "')";
      List branchlist = new ArrayList();
      rs = ps.executeQuery(sql1);
      while (rs.next()) {
        String area_leader = rs.getString(1);
        branchlist.add(area_leader);
      }
      if (branchlist != null) {
        if (branchlist.size() == 1) {
          String areaLeader = (String)list.get(0);
          String branchLeader = (String)branchlist.get(0);
          if (areaLeader.equals(branchLeader))
            flag = true;
        } else if (branchlist.size() == 0) {
          flag = true;
        }
      }

    }

    return flag;
  }

  public boolean checkSendLeader(Long id) throws Exception {
    Connection connection = null;
    Statement ps = null;
    ResultSet rs = null;
    boolean flag = false;

    String sql1 = "SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME,AR.ID FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN (SELECT * FROM ADMIN_AUTH_ACCOUNT T WHERE T.ORG_ID IN( SELECT ORG_ID FROM ADMIN_AUTH_ORG START WITH ORG_ID=(SELECT O.UP_ORG_ID FROM ADMIN_AUTH_ORG  O  WHERE ORG_ID=(SELECT ORG_ID FROM ADMIN_AUTH_ACCOUNT WHERE ACCOUNT_NAME =(SELECT USER_ID FROM OCRM_F_CI_TRANS_APPLY WHERE APPLY_NO = '" + 
      id + 
      "'))) CONNECT BY UP_ORG_ID = PRIOR ORG_ID)) ACC" + 
      " ON ACC.ID = AR.ACCOUNT_ID" + 
      " INNER JOIN SYS_UNITS ORG" + 
      " ON ORG.UNITID = ACC.ORG_ID" + 
      " WHERE AR.APP_ID = 62" + 
      " AND AR.ROLE_ID = 202" + 
      " ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY";
    connection = this.ds.getConnection();
    ps = connection.createStatement();
    rs = ps.executeQuery(sql1);
    List list = new ArrayList();
    while (rs.next()) {
      String area_leader = rs.getString(1);
      list.add(area_leader);
    }
    if ((list != null) && (list.size() == 1)) {
      String sql = " SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*  FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID  FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 311 ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY WHERE org_id= (SELECT A.ORG_ID FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME=( SELECT TA.USER_ID FROM OCRM_F_CI_TRANS_APPLY TA WHERE TA.APPLY_NO ='" + 
        id + "'))";
      rs = ps.executeQuery(sql);
      List branchlist = new ArrayList();
      while (rs.next()) {
        String area_leader = rs.getString(1);
        branchlist.add(area_leader);
      }
      if (branchlist != null) {
        if (branchlist.size() == 1) {
          String areaLeader = (String)list.get(0);
          String branchLeader = (String)branchlist.get(0);
          if (areaLeader.equals(branchLeader))
            flag = true;
        } else if (branchlist.size() == 0) {
          flag = true;
        }
      }
    }

    return flag;
  }

  public boolean checkBranchLeader(Long id) throws Exception {
    boolean flag = false;
    Connection connection = null;
    Statement ps = null;
    ResultSet rs = null;

    String sql = " SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*  FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID  FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 311 ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY WHERE org_id= (SELECT A.ORG_ID FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME=( SELECT TA.USER_ID FROM OCRM_F_CI_TRANS_APPLY TA WHERE TA.APPLY_NO ='" + 
      id + "'))";
    connection = this.ds.getConnection();
    ps = connection.createStatement();
    rs = ps.executeQuery(sql);
    List list = new ArrayList();
    while (rs.next()) {
      String branch_leader = rs.getString(1);
      list.add(branch_leader);
    }
    if ((list == null) || (list.size() == 0)) {
      flag = true;
    }
    return flag;
  }

  public boolean checkRecevierMgrName(String tMgrId) throws Exception {
    boolean flag = false;
    try {
      String sql = "SELECT ROLE_CODE FROM ( SELECT F.*,(CASE WHEN  F.IDCHECK IS NULL THEN  0  ELSE  1  END) IS_CHECKED  FROM (SELECT *  FROM ADMIN_AUTH_ROLE T0  LEFT JOIN (SELECT T1.ID AS IDCHECK, T1.ROLE_ID   FROM ADMIN_AUTH_ACCOUNT_ROLE T1   LEFT JOIN ADMIN_AUTH_ACCOUNT T2   ON T2.ID = T1.ACCOUNT_ID   WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME='" + 
        tMgrId + 
        "')) E " + 
        "   ON E.ROLE_ID = T0.ID " + 
        "  WHERE T0.ROLE_LEVEL >= '1') F " + 
        " WHERE 1 = 1 " + 
        " ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'";
      Connection connection = this.ds.getConnection();
      Statement stmt = connection.createStatement();
      ResultSet result = stmt.executeQuery(sql);
      String roleCode = "";
      List roleList = new ArrayList();
      while (result.next()) {
        roleCode = result.getString(1);
        roleList.add(roleCode);
      }
      if ((roleList != null) && (
        (roleList.contains("R303")) || 
        (roleList.contains("R305")) || 
        (roleList.contains("R105")) || 
        (roleList
        .contains("R302"))))
        flag = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }
  
  public void prepareFalse(){
		StringBuffer sb = new StringBuffer();
		sb.append("select t.CORE_NO,t.imp_msg from OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.create_user='" + auth.getUserId() + "' and t.IMP_STATUS='0'");
		SQL = sb.toString();
		datasource = ds;
		log.info(sb.toString());
	}
	/**
	 * 导出错误信息
	 */
	public String export() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			this.setJson(request.getParameter("condition"));
			Map<String, String> downloadInfo = new HashMap<String, String>();
			downloadInfo.put("menuId", request.getParameter("menuId"));
			downloadInfo.put("queryCon", request.getParameter("condition"));
			prepareFalse();
			processSQL();
			// 添加导出列字典映射字段
			Map<String, String> translateMap = new HashMap<String, String>();
			translateMap = (Map<String, String>) JSONUtil.deserialize(request.getParameter("translateMap"));
			for (String key : translateMap.keySet()) {
				if (null != translateMap.get(key) && !"".equals(translateMap.get(key))) {
					this.addOracleLookup(key, translateMap.get(key).toString());
				}
			}
			Map<String, String> fieldMap = new LinkedHashMap<String, String>();// 导出文件列映射
			fieldMap.put("CORE_NO", "核心客户号");
			fieldMap.put("IMP_MSG", "校验信息");
			DownloadThread thread = (DownloadThread) ctx.getSession().get("BACKGROUND_EXPORT_CSV_TASK");
			if (thread == null || thread.status.equals(DownloadThread.status_completed)) {
				DatabaseHelper dh = new DatabaseHelper(datasource);
				int taskId = dh.getNextValue("ID_BACKGROUND_TASK");
				DownloadThreadManager dtm = DownloadThreadManager.getInstance();
				thread = dtm.addDownloadThread(taskId, SQL, datasource, downloadInfo);
				if (thread == null || DownloadThread.status_wating.equals(thread.status)) {
					throw new Exception("当前下载人数过多，请稍后重试。");
				} else {
					json.put("taskID", thread.getThreadID());
					thread.setFieldLabel(fieldMap);
					thread.setOracleMapping(oracleMapping);
					ctx.getSession().put("BACKGROUND_EXPORT_CSV_TASK", thread);
				}
			} else {
				json.put("taskID", thread.getThreadID());
				throw new BizException(1, 0, "2002", "请等待当前下载任务完成。");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new BizException(1, 0, "1002", "导出列字典映射字段转换出错。");
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
		return "success";
	}
}