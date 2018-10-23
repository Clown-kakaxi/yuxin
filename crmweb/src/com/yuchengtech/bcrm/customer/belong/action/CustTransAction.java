
package com.yuchengtech.bcrm.customer.belong.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransApply;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransCust;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransMgr;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransApplyService;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransCustService;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransMgrService;
import com.yuchengtech.bcrm.customer.model.OcrmFCiBelongHist;
import com.yuchengtech.bcrm.customer.service.BelongCustmgrService;
import com.yuchengtech.bcrm.customer.service.CustomerTransService;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bcrm.custview.service.CustBelOrgInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.DatabaseHelper;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.download.DownloadThread;
import com.yuchengtech.bob.download.DownloadThreadManager;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;

@SuppressWarnings("serial")
@Action("/custTrans")
public class CustTransAction extends CommonAction {
	private static Logger log = Logger.getLogger(CustTransAction.class);

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

	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	@Autowired
	public void init() {
		this.model = new OcrmFCiTransApply();
		setCommonService(this.service);
	}
	/**
	 * 客户移交查询
	 */
	public void prepare() {
		//导入查询标志，如果值为 1，则是导入查询，否则是普通查询
		String impFlag=(String)json.get("impFlag");
		json.remove("impFlag");
		StringBuffer sb = new StringBuffer();
		if("1".equals(impFlag)){
			sb.append("select m.cust_id,m.mgr_id,m.main_type,m.maintain_right,m.check_right,"
			+"case when his.assign_user is null then m.assign_user else his.assign_user end as assign_user," 
			+ "case when his.assign_username is null then m.assign_username else his.assign_username end as assign_username," 
			+ "case when his.assign_date is null then m.assign_date else his.assign_date end   as assign_date," 
			+ "m.institution,m.institution_name,m.mgr_name," 
			+ "case when his.WORK_TRAN_DATE is null then m.effect_date else his.WORK_TRAN_DATE end  as effect_date ," 
			+ "c.cust_name, c.cust_type "
			+ "from OCRM_F_CI_BELONG_CUSTMGR_TEMP t"
			+ " left join OCRM_F_CI_BELONG_CUSTMGR m on t.cust_id=m.cust_id and t.create_user='"+auth.getUserId()+"'"
			+ " left join ACRM_F_CI_CUSTOMER c on m.cust_id=c.cust_id"
			//以归属历史表里所有客户的最新的一条归属数据最为子集进行关联，
			+ " left join (select t.*,row_number() over (partition by t.cust_id order by t.work_tran_date desc) as rn from OCRM_F_CI_BELONG_HIST t ) his on m.cust_id=his.cust_id and his.rn=1     "
			//在流程中的或者审批通过但是还没有同步的数据(APPROVE_STAT:1待审批；2审批通过；3拒绝),这里貌似没起作用，考虑删除
			/*
			+ " left join (select RECORD_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO in ("
			+ "				select APPLY_NO from OCRM_F_CI_TRANS_APPLY where APPROVE_STAT='1' "
			+ "				union all "
			+ "				SELECT Y.APPLY_NO FROM OCRM_F_CI_TRANS_APPLY Y "
			+ "				LEFT JOIN OCRM_F_CI_TRANS_CUST T ON Y.APPLY_NO = T.APPLY_NO WHERE Y.APPROVE_STAT = '2' AND T.STATE = '1'"
			+ ")) wx on wx.RECORD_ID=m.id and wx.RECORD_ID is null   "
			 */
			+ " where m.cust_id=c.cust_id");
			sb.append(" and m.mgr_id ='" + auth.getUserId() + "'");
		}else{
			String type = this.request.getParameter("type");
			sb.append(" select m.id,m.cust_id,m.mgr_id,m.main_type,m.maintain_right,m.check_right," 
			+"case when  his.assign_user is null then m.assign_user else his.assign_user end as assign_user,"
			+ "case when his.assign_username is null then m.assign_username else his.assign_username end as assign_username," 
			+ "case when his.assign_date is null then m.assign_date else his.assign_date end   as assign_date," 
			+ "m.institution,m.institution_name,m.mgr_name, " 
			+ "case when his.WORK_TRAN_DATE is null then m.effect_date else his.WORK_TRAN_DATE end  as effect_date ," 
			+ "c.cust_name, c.cust_type " 
			+ "from OCRM_F_CI_BELONG_CUSTMGR m " 
			+ "left join ACRM_F_CI_CUSTOMER c on m.cust_id=c.cust_id "
			//以归属历史表里所有客户的最新的一条归属数据最为子集进行关联
			+ "left join (select t.*,row_number() over (partition by t.cust_id order by t.work_tran_date desc) as rn from OCRM_F_CI_BELONG_HIST t ) his on m.cust_id=his.cust_id and his.rn=1    "
			//在流程中的或者审批通过但是还没有同步的数据(APPROVE_STAT:1待审批；2审批通过；3拒绝),这里貌似没起作用，考虑删除
			/*
			+ "left join (select RECORD_ID from OCRM_F_CI_TRANS_CUST where APPLY_NO in ("
			+ "				select APPLY_NO from OCRM_F_CI_TRANS_APPLY where APPROVE_STAT='1' "
			+ "				union all "
			+ "				SELECT Y.APPLY_NO FROM OCRM_F_CI_TRANS_APPLY Y "
			+ "				LEFT JOIN OCRM_F_CI_TRANS_CUST T ON Y.APPLY_NO = T.APPLY_NO WHERE Y.APPROVE_STAT = '2' AND T.STATE = '1'"
			+ ")) wx on wx.RECORD_ID=m.id and wx.RECORD_ID is null   " 
			*/
			+ "where m.cust_id=c.cust_id " +
			// add by liuming 20170220 过滤流程中的
	        " AND NOT EXISTS (SELECT 1 FROM OCRM_F_CI_TRANS_CUST TC, OCRM_F_CI_TRANS_APPLY TA WHERE TC.APPLY_NO = TA.APPLY_NO AND TA.APPROVE_STAT = '1' AND TC.CUST_ID = M.CUST_ID) " + " ");
			//主管直接移交，限制归属机构为当前登录人的机构及其子机构
			if ("charge".equals(type)) {
				if ("DB2".equals(SystemConstance.DB_TYPE)) {
					StringBuilder withSb = new StringBuilder();
					withSb.append("with rpl (org_id,up_org_id) as  (  select org_id,up_org_id   from admin_auth_org   where org_id ='" + this.auth.getUnitId() + "' " + " union all  select  child.org_id,child.up_org_id from rpl parent, admin_auth_org child where child.up_org_id=parent.org_id " + " ) ");
					this.withSQL = withSb.toString();
					sb.append(" and m.INSTITUTION in (select org_id from rpl)");
				} else {
					sb.append(" and m.INSTITUTION in (select org_id from admin_auth_org start with org_id = '" + this.auth.getUnitId() + "' connect by UP_ORG_ID = prior org_id )");
				}
			}
			//客户经理移交，限制归属客户经理为当前登录人
			if ("mgr".equals(type)) {
				sb.append(" and m.mgr_id ='" + this.auth.getUserId() + "'");
			}
			for (String key : getJson().keySet()) {
				if ((getJson().get(key) != null) && (!getJson().get(key).equals(""))) {
					//客户号，客户名称，客户类型模糊查询
					if (((key != null) && (key.equals("CUST_ID"))) || ("CUST_NAME".equals(key)) || ("CUST_TYPE".equals(key))){
						sb.append("  and c." + key + " like '%" + getJson().get(key) + "%'  ");
					} else if ((key != null) && (key.equals("ASSIGN_DATE"))){
						// sb.append("  and m.ASSIGN_DATE =to_date('%" +
						// getJson().get(key).toString().substring(0, 10) +
						// "%'  ,'yyyy-mm-dd')");
						// modify by liuming 20170224
						sb.append("  and to_char(m.ASSIGN_DATE,'yyyy-mm-dd') ='" + getJson().get(key).toString().substring(0, 10) + "'");
					} else if (key != null) {
						sb.append("  and  m." + key + " like '%" + getJson().get(key) + "%' ");
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

	public void MgrTrans() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		HttpServletResponse response = (HttpServletResponse) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		String tMgr = this.request.getParameter("tMgr");
		String type = this.request.getParameter("type");
		if (("1".equals(type)) && ("3".equals(((OcrmFCiTransApply) this.model).getHandKind()))) {
			type = "2";
		}
		((OcrmFCiTransApply) this.model).setTMgrId(tMgr);
		((OcrmFCiTransApply) this.model).setApplyDate(new Date());
		((OcrmFCiTransApply) this.model).setApproveStat("1");
		((OcrmFCiTransApply) this.model).setUserId(this.auth.getUserId());
		((OcrmFCiTransApply) this.model).setUserName(this.auth.getUsername());
		((OcrmFCiTransApply) this.model).setApplyType(type);
		((OcrmFCiTransApply) this.model).setType("0");
		this.service.save(this.model);
		Long id = ((OcrmFCiTransApply) this.model).getApplyNo();
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
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
		String name = ((OcrmFCiTransApply) this.model).getTMgrName();
		String instanceid = "YJ_" + id;
		String jobName = "客户移交_" + name;
		Map mapParma = new HashMap();
		mapParma.put("type", type);
		this.service.initWorkflowByWfidAndInstanceid("28", jobName, mapParma, instanceid);
		String nextNode = "28_a4";
		switch (Integer.valueOf(type).intValue()) {
		case 1:
			nextNode = "28_a4";
			break;
		case 2:
			nextNode = "28_a8";
			break;
		case 3:
			nextNode = "28_a12";
			break;
		case 4:
			nextNode = "28_a16";
			break;
		case 5:
			nextNode = "28_a21";
		}
		Map map = new HashMap();
		map.put("instanceid", instanceid);
		map.put("currNode", "28_a3");
		map.put("nextNode", nextNode);
		setJson(map);
	}

	public Date getEffectDate() throws ParseException {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(2);
		int year = cal.get(1);
		if (month == 11) {
			month = 1;
			year++;
		} else {
			month += 2;
		}
		String toDate = year + "-" + (month > 9 ? Integer.valueOf(month) : new StringBuilder("0").append(month).toString()) + "-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(toDate);
		return date;
	}

	public String getOrgandCust(String org, String cust) {
		this.service.batchUpdateByName(" delete from OcrmFCiBelongOrg o where o.custId='" + cust + "' and o.institutionCode='" + org + "'", new HashMap());
		List list1 = this.service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from OCRM_F_CI_BELONG_CUSTMGR where INSTITUTION='" + org + "'" + " and CUST_ID='" + cust + "' and MAIN_TYPE='1' ", new Object[0]);
		if ((list1 != null) && (list1.size() > 0)) {
			return "1";
		}
		List list2 = this.service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from OCRM_F_CI_BELONG_CUSTMGR where INSTITUTION='" + org + "'" + " and CUST_ID='" + cust + "' and MAIN_TYPE='2' ", new Object[0]);
		if ((list2 != null) && (list2.size() > 0)) {
			return "2";
		}
		return "";
	}

	/**
	 * 对私主管直接移交
	 * 
	 * @throws Exception
	 */
	public void pChargeTrans() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		String tMgrId = ((OcrmFCiTransApply) this.model).getTMgrId();// 接受客户经理
		String tOrgId = ((OcrmFCiTransApply) this.model).getTOrgId();// 接受机构
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");// 客户归属表主键
		String CUST_IDs = this.request.getParameter("CUST_IDs");// 客户ID
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		for (int i = 0; i < jarray1.size(); i++) {
			String responseXml = TranCrmToEcifMgr(jarray2.getString(i), tMgrId, jarray1.getString(i), null);
			boolean responseFlag = doResXms(responseXml);
			if (!responseFlag) {
				throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
			}
		}
		for (int i = 0; i < jarray1.size(); i++) {
			String responseXml = TranCrmToEcifOrg(jarray2.getString(i), tOrgId, jarray1.getString(i), null);
			boolean responseFlag = doResXms(responseXml);
			if (!responseFlag) {
				throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
			}
		}
		pChargeTransEnd();
	}

	/**
	 * 对私主管直接移交交易完成后处理程序
	 * 
	 * @throws Exception
	 */
	public void pChargeTransEnd() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		String tMgr = this.request.getParameter("tMgr");
		((OcrmFCiTransApply) this.model).setTMgrId(tMgr);
		((OcrmFCiTransApply) this.model).setApplyDate(new Date());
		((OcrmFCiTransApply) this.model).setApproveStat("3");
		((OcrmFCiTransApply) this.model).setUserId(this.auth.getUserId());
		((OcrmFCiTransApply) this.model).setUserName(this.auth.getUsername());
		((OcrmFCiTransApply) this.model).setApplyType("6");
		this.service.save(this.model);
		Long id = ((OcrmFCiTransApply) this.model).getApplyNo();
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
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
			this.cservice.save(cust);
		}
		String tMgrId = ((OcrmFCiTransApply) this.model).getTMgrId();
		String tMgrName = ((OcrmFCiTransApply) this.model).getTMgrName();
		String tOrgId = ((OcrmFCiTransApply) this.model).getTOrgId();
		String tOrgName = ((OcrmFCiTransApply) this.model).getTOrgName();
		Map values = new HashMap();
		for (int i = 0; i < jarray1.size(); i++) {
			values.put("recordId", jarray1.getLong(i));
			this.service.batchUpdateByName(" delete from OcrmFCiBelongCustmgr t where t.id  = :recordId", values);
		}
		values.clear();
		Date date = getEffectDate();
		for (int i = 0; i < jarray1.size(); i++) {
			OcrmFCiBelongCustmgr mgr = new OcrmFCiBelongCustmgr();
			OcrmFCiBelongHist his = new OcrmFCiBelongHist();
			mgr.setCustId(jarray2.getString(i));
			mgr.setMgrId(tMgrId);
			mgr.setMgrName(tMgrName);
			mgr.setCheckRight("1");
			mgr.setMaintainRight("1");
			mgr.setAssignUser(this.auth.getUserId());
			mgr.setAssignUsername(this.auth.getUsername());
			mgr.setAssignDate(new Date());
			mgr.setInstitution(tOrgId);
			mgr.setInstitutionName(tOrgName);
			mgr.setEffectDate(date);
			this.mservice.save(mgr);
			his.setBeforeInstCode(jarray7.getString(i));
			his.setAfterInstCode(tOrgId);
			his.setBeforeMgrId(jarray4.getString(i));
			his.setBeforeInstName(jarray8.getString(i));
			his.setAfterMgrName(tMgrName);
			his.setAssignUser(this.auth.getUserId());
			his.setAssignDate(new Date());
			his.setAssignUsername(this.auth.getUsername());
			his.setBeforeMgrName(jarray5.getString(i));
			his.setWorkTranDate(((OcrmFCiTransApply) this.model).getWorkInterfixDt());
			his.setWorkTranLevel(((OcrmFCiTransApply) this.model).getHandKind());
			his.setWorkTranReason(((OcrmFCiTransApply) this.model).getHandOverReason());
			his.setCustId(jarray2.getString(i));
			his.setAfterMgrId(tMgrId);
			his.setAfterInstName(tOrgName);
			this.hservice.save(his);
		}
		for (int i = 0; i < jarray1.size(); i++) {
			this.service.batchUpdateByName(" delete from OcrmFCiBelongOrg t where t.institutionCode='" + jarray7.getString(i) + "' and t.custId ='" + jarray2.getString(i) + "' ", values);
			OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
			org.setCustId(jarray2.getString(i));
			org.setInstitutionCode(tOrgId);
			org.setInstitutionName(tOrgName);
			org.setAssignUser(this.auth.getUserId());
			org.setAssignDate(new Date());
			org.setAssignUsername(this.auth.getUsername());
			this.oservice.save(org);
		}
	}

	/**
	 * 主管直接移交对公客户(主办)----机构
	 * 代码中涉及：/////////////////////////////////注释的地方都是要增加与ECIF处理的地方
	 * 特别注意：报文先增加的一定要先执行，此报文有执行顺序上的关系
	 * 
	 * @throws Exception
	 */
	public void cChargeTransM() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");//移交客户的客户号
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		String MGR_IDss = this.request.getParameter("MGR_IDss");
		String MGR_NAMEss = this.request.getParameter("MGR_NAMEss");
		String INSTITUTIONss = this.request.getParameter("INSTITUTIONss");
		String INSTITUTION_NAMEss = this.request.getParameter("INSTITUTION_NAMEss");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONObject jsonObject9 = JSONObject.fromObject(MGR_IDss);
		JSONObject jsonObject10 = JSONObject.fromObject(MGR_NAMEss);
		JSONObject jsonObject11 = JSONObject.fromObject(INSTITUTIONss);
		JSONObject jsonObject12 = JSONObject.fromObject(INSTITUTION_NAMEss);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
		JSONArray jarray9 = jsonObject9.getJSONArray("MGR_ID");
		JSONArray jarray10 = jsonObject10.getJSONArray("MGR_NAME");
		JSONArray jarray11 = jsonObject11.getJSONArray("INSTITUTION");
		JSONArray jarray12 = jsonObject12.getJSONArray("INSTITUTION_NAME");
		// 实现客户关系移交
		String tMgrId = ((OcrmFCiTransApply) this.model).getTMgrId();//接收客户经理ID
		String tMgrName = ((OcrmFCiTransApply) this.model).getTMgrName();//接收客户经理名称
		String tOrgId = ((OcrmFCiTransApply) this.model).getTOrgId();//接收客户经理归属机构
		String tOrgName = ((OcrmFCiTransApply) this.model).getTOrgName();//接收客户经理归属机构名称
		//循环所有被选中的客户，更新Ecif客户归属信息和协办信息
		for (int i = 0; i < jarray1.size(); i++) {
			// 先处理主办关系,向Ecif发起交易，更新归属客户经理为接收客户经理
			String responseXml = TranCrmToEcifMgr(jarray2.getString(i), tMgrId, jarray1.getString(i), "1");
			boolean responseFlag = doResXms(responseXml);
			if (!responseFlag) {
				throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
			}
			// 处理协办经理,向Ecif发起交易，更新协办客户经理信息
			for (int j = 0; j < jarray9.size(); j++) {
				responseXml = TranCrmToEcifMgr(jarray2.getString(i), jarray9.getString(j), jarray1.getString(i), "2");
				responseFlag = doResXms(responseXml);
				if (!responseFlag) {
					throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
				}
			}
		}
		/**
		 * 客户经理业务逻辑处理，更新CRM数据 
		 */
		cChargeTransMEndMgr();
		// 3.重新处理与机构的关系(新的关系统一通过OCRM_F_CI_BELONG_CUSTMGR表确定)
		for (int i = 0; i < jarray1.size(); i++) {
			String mainType = "";
			// 3.1.处理与原机构关系
			mainType = getOrgandCust(jarray7.getString(i), jarray2.getString(i));
			if (!"".equals(mainType)) {// 没有新的关系
				if ("1".equals(mainType)) {// 新关系为主办
					String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray7.getString(i), jarray1.getString(i), "1");
					boolean responseFlag = doResXms(responseXml);
					if (!responseFlag)
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
				} else if ("2".equals(mainType)) {// 新关系为协办
					String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray7.getString(i), jarray1.getString(i), "2");
					boolean responseFlag = doResXms(responseXml);
					if (!responseFlag) {
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
					}
				}
			}
			// 3.2.处理与目标主办机构关系
			mainType = getOrgandCust(tOrgId, jarray2.getString(i));
			if (!"".equals(mainType)) {// 没有新的关系
				if ("1".equals(mainType)) {// 新关系为主办
					String responseXml = TranCrmToEcifOrg(jarray2.getString(i), tOrgId, jarray1.getString(i), "1");
					boolean responseFlag = doResXms(responseXml);
					if (!responseFlag)
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
				} else if ("2".equals(mainType)) {// 新关系为协办
					String responseXml = TranCrmToEcifOrg(jarray2.getString(i), tOrgId, jarray1.getString(i), "2");
					boolean responseFlag = doResXms(responseXml);
					if (!responseFlag) {
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
					}
				}
			}
			// 3.3.处理与协办机构关系
			for (int j = 0; j < jarray9.size(); j++) {
				mainType = getOrgandCust(jarray11.getString(j), jarray2.getString(i));
				if (!"".equals(mainType)) {// 没有新的关系
					if ("1".equals(mainType)) {// 新关系为主办
						String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray11.getString(j), jarray1.getString(i), "1");
						boolean responseFlag = doResXms(responseXml);
						if (!responseFlag)
							throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
					} else if ("2".equals(mainType)) {// 新关系为协办
						String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray11.getString(j), jarray1.getString(i), "2");
						boolean responseFlag = doResXms(responseXml);
						if (!responseFlag) {
							throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
						}
					}
				}
			}
		}
		/**
		 * 处理客户归属机构业务逻辑
		 */
		cChargeTransMEndOrg();
		// add by liuming 20170523
		// 客户移交同步信贷
		TranCrmListToLN();
	}

	/**
	 * 处理客户归属客户经理业务逻辑(主办)--客户经理
	 * 
	 * @throws Exception
	 */
	public void cChargeTransMEndMgr() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		// 保存申请表信息
		String tMgr = this.request.getParameter("tMgr");
		((OcrmFCiTransApply) this.model).setTMgrId(tMgr);
		((OcrmFCiTransApply) this.model).setApplyDate(new Date());
		((OcrmFCiTransApply) this.model).setApproveStat("3");
		((OcrmFCiTransApply) this.model).setUserId(this.auth.getUserId());
		((OcrmFCiTransApply) this.model).setUserName(this.auth.getUsername());
		((OcrmFCiTransApply) this.model).setApplyType("7");
		this.service.save(this.model);
		Long id = ((OcrmFCiTransApply) this.model).getApplyNo();
		Date date = getEffectDate();
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		String MGR_IDss = this.request.getParameter("MGR_IDss");
		String MGR_NAMEss = this.request.getParameter("MGR_NAMEss");
		String INSTITUTIONss = this.request.getParameter("INSTITUTIONss");
		String INSTITUTION_NAMEss = this.request.getParameter("INSTITUTION_NAMEss");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONObject jsonObject9 = JSONObject.fromObject(MGR_IDss);
		JSONObject jsonObject10 = JSONObject.fromObject(MGR_NAMEss);
		JSONObject jsonObject11 = JSONObject.fromObject(INSTITUTIONss);
		JSONObject jsonObject12 = JSONObject.fromObject(INSTITUTION_NAMEss);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
		JSONArray jarray9 = jsonObject9.getJSONArray("MGR_ID");
		JSONArray jarray10 = jsonObject10.getJSONArray("MGR_NAME");
		JSONArray jarray11 = jsonObject11.getJSONArray("INSTITUTION");
		JSONArray jarray12 = jsonObject12.getJSONArray("INSTITUTION_NAME");
		// 保存移交客户列表
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
			this.cservice.save(cust);
		}
		// 保存协办客户经理
		for (int i = 0; i < jarray9.size(); i++) {
			OcrmFCiTransMgr mgr = new OcrmFCiTransMgr();
			mgr.setApplyNo(id.toString());
			mgr.setMgrId(jarray9.getString(i));
			mgr.setMgrName(jarray10.getString(i));
			mgr.setInstitution(jarray11.getString(i));
			mgr.setInstitutionName(jarray12.getString(i));
			this.mgservice.save(mgr);
		}
		// 实现客户关系移交
		String tMgrId = ((OcrmFCiTransApply) this.model).getTMgrId();
		String tMgrName = ((OcrmFCiTransApply) this.model).getTMgrName();
		String tOrgId = ((OcrmFCiTransApply) this.model).getTOrgId();
		String tOrgName = ((OcrmFCiTransApply) this.model).getTOrgName();
		Map values = new HashMap();
		// 1.1删除原客户经理归属关系 1.2删除目标客户经理和所选客户的原有协办关系 避免新的关系产生重复
		for (int i = 0; i < jarray1.size(); i++) {
			values.put("recordId", jarray1.getLong(i));
			this.service.batchUpdateByName(" delete from OcrmFCiBelongCustmgr t where t.id  =:recordId ", values);
			values.clear();
			this.service.batchUpdateByName(
				" delete from OcrmFCiBelongCustmgr t where t.custId  = '" +
				jarray2.getString(i) + "' and t.mgrId='" + tMgrId + 
				"' and t.mainType='2'", values);
			for (int j = 0; j < jarray9.size(); j++) {
				values.clear();
		    	//1.3 删除协办经理与所选客户的原有协办关系 避免新的关系产生重复
				this.service.batchUpdateByName(
				" delete from OcrmFCiBelongCustmgr t where t.custId  = '" +
				jarray2.getString(i) + 
		        "' and t.mgrId='" + 
		        jarray9.getString(j) + 
		        "' and t.mainType='2'", values);
			}
		}
		// 2.新增客户归属关系 同时写入历史信息
		for (int i = 0; i < jarray1.size(); i++) {
			// 先处理主办关系
			OcrmFCiBelongCustmgr mgr = new OcrmFCiBelongCustmgr();
			OcrmFCiBelongHist his = new OcrmFCiBelongHist();
			mgr.setCustId(jarray2.getString(i));
			mgr.setMgrId(tMgrId);
			mgr.setMgrName(tMgrName);
			mgr.setMainType("1");
			mgr.setCheckRight("1");
			mgr.setMaintainRight("1");
			mgr.setAssignUser(this.auth.getUserId());
			mgr.setAssignUsername(this.auth.getUsername());
			mgr.setAssignDate(new Date());
			mgr.setInstitution(tOrgId);
			mgr.setInstitutionName(tOrgName);
			mgr.setEffectDate(date);
			this.mservice.save(mgr);
			his.setBeforeInstCode(jarray7.getString(i));
			his.setAfterInstCode(tOrgId);
			his.setBeforeMgrId(jarray4.getString(i));
			his.setBeforeInstName(jarray8.getString(i));
			his.setAfterMgrName(tMgrName);
			his.setAssignUser(this.auth.getUserId());
			his.setAssignDate(new Date());
			his.setAssignUsername(this.auth.getUsername());
			his.setBeforeMgrName(jarray5.getString(i));
			his.setWorkTranDate(((OcrmFCiTransApply) this.model).getWorkInterfixDt());
			his.setWorkTranLevel(((OcrmFCiTransApply) this.model).getHandKind());
			his.setWorkTranReason(((OcrmFCiTransApply) this.model).getHandOverReason());
			his.setCustId(jarray2.getString(i));
			his.setBeforeMainType(jarray6.getString(i));
			his.setAfterMainType("1");
			his.setAfterMgrId(tMgrId);
			his.setAfterInstName(tOrgName);
			this.hservice.save(his);
			// 处理协办经理
			for (int j = 0; j < jarray9.size(); j++) {
				OcrmFCiBelongCustmgr mgr1 = new OcrmFCiBelongCustmgr();
				OcrmFCiBelongHist his1 = new OcrmFCiBelongHist();
				mgr1.setCustId(jarray2.getString(i));
				mgr1.setMgrId(jarray9.getString(j));
				mgr1.setMgrName(jarray10.getString(j));
				mgr1.setMainType("2");
				mgr1.setCheckRight("1");
				mgr1.setMaintainRight("0");
				mgr1.setAssignUser(this.auth.getUserId());
				mgr1.setAssignUsername(this.auth.getUsername());
				mgr1.setAssignDate(new Date());
				mgr1.setInstitution(jarray11.getString(j));
				mgr1.setInstitutionName(jarray12.getString(j));
				mgr1.setEffectDate(date);
				this.mservice.save(mgr1);
				his1.setBeforeInstCode(jarray7.getString(i));
				his1.setAfterInstCode(jarray11.getString(j));
				his1.setBeforeMgrId(jarray4.getString(i));
				his1.setBeforeInstName(jarray8.getString(i));
				his1.setAfterMgrName(jarray10.getString(j));
				his1.setAssignUser(this.auth.getUserId());
				his1.setAssignDate(new Date());
				his1.setAssignUsername(this.auth.getUsername());
				his1.setBeforeMgrName(jarray5.getString(i));
				his1.setWorkTranDate(((OcrmFCiTransApply) this.model).getWorkInterfixDt());
				his1.setWorkTranLevel(((OcrmFCiTransApply) this.model).getHandKind());
				his1.setWorkTranReason(((OcrmFCiTransApply) this.model).getHandOverReason());
				his1.setCustId(jarray2.getString(i));
				his1.setBeforeMainType(jarray6.getString(i));
				his1.setAfterMainType("2");
				his1.setAfterMgrId(jarray9.getString(j));
				his1.setAfterInstName(jarray12.getString(j));
				this.hservice.save(his1);
			}
		}
	}

	public void cChargeTransMEndOrg() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		Date date = getEffectDate();
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		String MGR_IDss = this.request.getParameter("MGR_IDss");
		String MGR_NAMEss = this.request.getParameter("MGR_NAMEss");
		String INSTITUTIONss = this.request.getParameter("INSTITUTIONss");
		String INSTITUTION_NAMEss = this.request.getParameter("INSTITUTION_NAMEss");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONObject jsonObject9 = JSONObject.fromObject(MGR_IDss);
		JSONObject jsonObject10 = JSONObject.fromObject(MGR_NAMEss);
		JSONObject jsonObject11 = JSONObject.fromObject(INSTITUTIONss);
		JSONObject jsonObject12 = JSONObject.fromObject(INSTITUTION_NAMEss);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
		JSONArray jarray9 = jsonObject9.getJSONArray("MGR_ID");
		JSONArray jarray10 = jsonObject10.getJSONArray("MGR_NAME");
		JSONArray jarray11 = jsonObject11.getJSONArray("INSTITUTION");
		JSONArray jarray12 = jsonObject12.getJSONArray("INSTITUTION_NAME");
		String tOrgId = ((OcrmFCiTransApply) this.model).getTOrgId();
		String tOrgName = ((OcrmFCiTransApply) this.model).getTOrgName();
		for (int i = 0; i < jarray1.size(); i++) {
			String mainType = "";
			mainType = getOrgandCust(jarray7.getString(i), jarray2.getString(i));
			if (!"".equals(mainType)) {
				if ("1".equals(mainType)) {
					OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
					org.setMainType("1");
					org.setCustId(jarray2.getString(i));
					org.setInstitutionCode(jarray7.getString(i));
					org.setInstitutionName(jarray8.getString(i));
					org.setAssignUser(this.auth.getUserId());
					org.setAssignDate(new Date());
					org.setAssignUsername(this.auth.getUsername());
					this.oservice.save(org);
				} else if ("2".equals(mainType)) {
					OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
					org.setMainType("2");
					org.setCustId(jarray2.getString(i));
					org.setInstitutionCode(jarray7.getString(i));
					org.setInstitutionName(jarray8.getString(i));
					org.setAssignUser(this.auth.getUserId());
					org.setAssignDate(new Date());
					org.setAssignUsername(this.auth.getUsername());
					this.oservice.save(org);
				}
			}
			mainType = getOrgandCust(tOrgId, jarray2.getString(i));
			if (!"".equals(mainType)) {
				if ("1".equals(mainType)) {
					OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
					org.setMainType("1");
					org.setCustId(jarray2.getString(i));
					org.setInstitutionCode(tOrgId);
					org.setInstitutionName(tOrgName);
					org.setAssignUser(this.auth.getUserId());
					org.setAssignDate(new Date());
					org.setAssignUsername(this.auth.getUsername());
					this.oservice.save(org);
				} else if ("2".equals(mainType)) {
					OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
					org.setMainType("2");
					org.setCustId(jarray2.getString(i));
					org.setInstitutionCode(tOrgId);
					org.setInstitutionName(tOrgName);
					org.setAssignUser(this.auth.getUserId());
					org.setAssignDate(new Date());
					org.setAssignUsername(this.auth.getUsername());
					this.oservice.save(org);
				}
			}
			for (int j = 0; j < jarray9.size(); j++) {
				mainType = getOrgandCust(jarray11.getString(j), jarray2.getString(i));
				if (!"".equals(mainType))
					if ("1".equals(mainType)) {
						OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
						org.setMainType("1");
						org.setCustId(jarray2.getString(i));
						org.setInstitutionCode(jarray11.getString(j));
						org.setInstitutionName(jarray12.getString(j));
						org.setAssignUser(this.auth.getUserId());
						org.setAssignDate(new Date());
						org.setAssignUsername(this.auth.getUsername());
						this.oservice.save(org);
					} else if ("2".equals(mainType)) {
						OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
						org.setMainType("2");
						org.setCustId(jarray2.getString(i));
						org.setInstitutionCode(jarray11.getString(j));
						org.setInstitutionName(jarray12.getString(j));
						org.setAssignUser(this.auth.getUserId());
						org.setAssignDate(new Date());
						org.setAssignUsername(this.auth.getUsername());
						this.oservice.save(org);
					}
			}
		}
	}

	public void cChargeTransF() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		String MGR_IDss = this.request.getParameter("MGR_IDss");
		String MGR_NAMEss = this.request.getParameter("MGR_NAMEss");
		String INSTITUTIONss = this.request.getParameter("INSTITUTIONss");
		String INSTITUTION_NAMEss = this.request.getParameter("INSTITUTION_NAMEss");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONObject jsonObject9 = JSONObject.fromObject(MGR_IDss);
		JSONObject jsonObject10 = JSONObject.fromObject(MGR_NAMEss);
		JSONObject jsonObject11 = JSONObject.fromObject(INSTITUTIONss);
		JSONObject jsonObject12 = JSONObject.fromObject(INSTITUTION_NAMEss);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
		JSONArray jarray9 = jsonObject9.getJSONArray("MGR_ID");
		JSONArray jarray10 = jsonObject10.getJSONArray("MGR_NAME");
		JSONArray jarray11 = jsonObject11.getJSONArray("INSTITUTION");
		JSONArray jarray12 = jsonObject12.getJSONArray("INSTITUTION_NAME");
		for (int i = 0; i < jarray1.size(); i++) {
			for (int j = 0; j < jarray9.size(); j++) {
				String responseXml = TranCrmToEcifMgr(jarray2.getString(i), jarray9.getString(j), jarray1.getString(i), "2");
				boolean responseFlag = doResXms(responseXml);
				if (!responseFlag) {
					throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
				}
			}
		}
		cChargeTransFEndMrg();
		for (int i = 0; i < jarray1.size(); i++) {
			String mainType = "";
			mainType = getOrgandCust(jarray7.getString(i), jarray2.getString(i));
			if (!"".equals(mainType)) {
				if ("1".equals(mainType)) {
					String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray7.getString(i), jarray1.getString(i), "1");
					boolean responseFlag = doResXms(responseXml);
					if (!responseFlag)
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
				} else if ("2".equals(mainType)) {
					String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray7.getString(i), jarray1.getString(i), "2");
					boolean responseFlag = doResXms(responseXml);
					if (!responseFlag) {
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
					}
				}
			}
			for (int j = 0; j < jarray9.size(); j++) {
				mainType = getOrgandCust(jarray11.getString(j), jarray2.getString(i));
				if (!"".equals(mainType)) {
					if ("1".equals(mainType)) {
						String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray11.getString(i), jarray1.getString(i), "1");
						boolean responseFlag = doResXms(responseXml);
						if (!responseFlag)
							throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
					} else if ("2".equals(mainType)) {
						String responseXml = TranCrmToEcifOrg(jarray2.getString(i), jarray11.getString(i), jarray1.getString(i), "2");
						boolean responseFlag = doResXms(responseXml);
						if (!responseFlag) {
							throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
						}
					}
				}
			}
		}
		cChargeTransFEndOrg();
	}

	public void cChargeTransFEndMrg() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		((OcrmFCiTransApply) this.model).setApplyDate(new Date());
		((OcrmFCiTransApply) this.model).setApproveStat("3");
		((OcrmFCiTransApply) this.model).setUserId(this.auth.getUserId());
		((OcrmFCiTransApply) this.model).setUserName(this.auth.getUsername());
		((OcrmFCiTransApply) this.model).setApplyType("8");
		this.service.save(this.model);
		Long id = ((OcrmFCiTransApply) this.model).getApplyNo();
		Date date = getEffectDate();
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		String MGR_IDss = this.request.getParameter("MGR_IDss");
		String MGR_NAMEss = this.request.getParameter("MGR_NAMEss");
		String INSTITUTIONss = this.request.getParameter("INSTITUTIONss");
		String INSTITUTION_NAMEss = this.request.getParameter("INSTITUTION_NAMEss");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONObject jsonObject9 = JSONObject.fromObject(MGR_IDss);
		JSONObject jsonObject10 = JSONObject.fromObject(MGR_NAMEss);
		JSONObject jsonObject11 = JSONObject.fromObject(INSTITUTIONss);
		JSONObject jsonObject12 = JSONObject.fromObject(INSTITUTION_NAMEss);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
		JSONArray jarray9 = jsonObject9.getJSONArray("MGR_ID");
		JSONArray jarray10 = jsonObject10.getJSONArray("MGR_NAME");
		JSONArray jarray11 = jsonObject11.getJSONArray("INSTITUTION");
		JSONArray jarray12 = jsonObject12.getJSONArray("INSTITUTION_NAME");
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
			this.cservice.save(cust);
		}
		for (int i = 0; i < jarray9.size(); i++) {
			OcrmFCiTransMgr mgr = new OcrmFCiTransMgr();
			mgr.setApplyNo(id.toString());
			mgr.setMgrId(jarray9.getString(i));
			mgr.setMgrName(jarray10.getString(i));
			mgr.setInstitution(jarray11.getString(i));
			mgr.setInstitutionName(jarray12.getString(i));
			this.mgservice.save(mgr);
		}
		Map values = new HashMap();
		for (int i = 0; i < jarray1.size(); i++) {
			values.put("recordId", jarray1.getLong(i));
			this.service.batchUpdateByName(" delete from OcrmFCiBelongCustmgr t where t.id  =:recordId ", values);
			values.clear();
			for (int j = 0; j < jarray9.size(); j++) {
				this.service.batchUpdateByName(" delete from OcrmFCiBelongCustmgr t where t.custId  = '" + jarray2.getString(i) + "' and t.mgrId='" + jarray9.getString(j) + "' and t.mainType='2'", values);
			}
		}
		for (int i = 0; i < jarray1.size(); i++)
			for (int j = 0; j < jarray9.size(); j++) {
				OcrmFCiBelongCustmgr mgr1 = new OcrmFCiBelongCustmgr();
				OcrmFCiBelongHist his1 = new OcrmFCiBelongHist();
				mgr1.setCustId(jarray2.getString(i));
				mgr1.setMgrId(jarray9.getString(j));
				mgr1.setMgrName(jarray10.getString(j));
				mgr1.setMainType("2");
				mgr1.setCheckRight("1");
				mgr1.setMaintainRight("0");
				mgr1.setAssignUser(this.auth.getUserId());
				mgr1.setAssignUsername(this.auth.getUsername());
				mgr1.setAssignDate(new Date());
				mgr1.setInstitution(jarray11.getString(j));
				mgr1.setInstitutionName(jarray12.getString(j));
				mgr1.setEffectDate(date);
				this.mservice.save(mgr1);
				his1.setBeforeInstCode(jarray7.getString(i));
				his1.setAfterInstCode(jarray11.getString(j));
				his1.setBeforeMgrId(jarray4.getString(i));
				his1.setBeforeInstName(jarray8.getString(i));
				his1.setAfterMgrName(jarray10.getString(j));
				his1.setAssignUser(this.auth.getUserId());
				his1.setAssignDate(new Date());
				his1.setAssignUsername(this.auth.getUsername());
				his1.setBeforeMgrName(jarray5.getString(i));
				his1.setWorkTranDate(((OcrmFCiTransApply) this.model).getWorkInterfixDt());
				his1.setWorkTranLevel(((OcrmFCiTransApply) this.model).getHandKind());
				his1.setWorkTranReason(((OcrmFCiTransApply) this.model).getHandOverReason());
				his1.setCustId(jarray2.getString(i));
				his1.setBeforeMainType(jarray6.getString(i));
				his1.setAfterMainType("2");
				his1.setAfterMgrId(jarray9.getString(j));
				his1.setAfterInstName(jarray12.getString(j));
				this.hservice.save(his1);
			}
	}

	public void cChargeTransFEndOrg() throws ParseException {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		String RECORD_IDs = this.request.getParameter("RECORD_IDs");
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String CUST_NAMEs = this.request.getParameter("CUST_NAMEs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String MGR_NAMEs = this.request.getParameter("MGR_NAMEs");
		String MAIN_TYPEs = this.request.getParameter("MAIN_TYPEs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		String INSTITUTION_NAMEs = this.request.getParameter("INSTITUTION_NAMEs");
		String MGR_IDss = this.request.getParameter("MGR_IDss");
		String MGR_NAMEss = this.request.getParameter("MGR_NAMEss");
		String INSTITUTIONss = this.request.getParameter("INSTITUTIONss");
		String INSTITUTION_NAMEss = this.request.getParameter("INSTITUTION_NAMEss");
		JSONObject jsonObject1 = JSONObject.fromObject(RECORD_IDs);
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject3 = JSONObject.fromObject(CUST_NAMEs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject5 = JSONObject.fromObject(MGR_NAMEs);
		JSONObject jsonObject6 = JSONObject.fromObject(MAIN_TYPEs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONObject jsonObject8 = JSONObject.fromObject(INSTITUTION_NAMEs);
		JSONObject jsonObject9 = JSONObject.fromObject(MGR_IDss);
		JSONObject jsonObject10 = JSONObject.fromObject(MGR_NAMEss);
		JSONObject jsonObject11 = JSONObject.fromObject(INSTITUTIONss);
		JSONObject jsonObject12 = JSONObject.fromObject(INSTITUTION_NAMEss);
		JSONArray jarray1 = jsonObject1.getJSONArray("RECORD_ID");
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray3 = jsonObject3.getJSONArray("CUST_NAME");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray5 = jsonObject5.getJSONArray("MGR_NAME");
		JSONArray jarray6 = jsonObject6.getJSONArray("MAIN_TYPE");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		JSONArray jarray8 = jsonObject8.getJSONArray("INSTITUTION_NAME");
		JSONArray jarray9 = jsonObject9.getJSONArray("MGR_ID");
		JSONArray jarray10 = jsonObject10.getJSONArray("MGR_NAME");
		JSONArray jarray11 = jsonObject11.getJSONArray("INSTITUTION");
		JSONArray jarray12 = jsonObject12.getJSONArray("INSTITUTION_NAME");
		for (int i = 0; i < jarray1.size(); i++) {
			String mainType = "";
			mainType = getOrgandCust(jarray7.getString(i), jarray2.getString(i));
			if (!"".equals(mainType)) {
				if ("1".equals(mainType)) {
					OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
					org.setMainType("1");
					org.setCustId(jarray2.getString(i));
					org.setInstitutionCode(jarray7.getString(i));
					org.setInstitutionName(jarray8.getString(i));
					org.setAssignUser(this.auth.getUserId());
					org.setAssignDate(new Date());
					org.setAssignUsername(this.auth.getUsername());
					this.oservice.save(org);
				} else if ("2".equals(mainType)) {
					OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
					org.setMainType("2");
					org.setCustId(jarray2.getString(i));
					org.setInstitutionCode(jarray7.getString(i));
					org.setInstitutionName(jarray8.getString(i));
					org.setAssignUser(this.auth.getUserId());
					org.setAssignDate(new Date());
					org.setAssignUsername(this.auth.getUsername());
					this.oservice.save(org);
				}
			}
			for (int j = 0; j < jarray9.size(); j++) {
				mainType = getOrgandCust(jarray11.getString(j), jarray2.getString(i));
				if (!"".equals(mainType))
					if ("1".equals(mainType)) {
						OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
						org.setMainType("1");
						org.setCustId(jarray2.getString(i));
						org.setInstitutionCode(jarray11.getString(j));
						org.setInstitutionName(jarray12.getString(j));
						org.setAssignUser(this.auth.getUserId());
						org.setAssignDate(new Date());
						org.setAssignUsername(this.auth.getUsername());
						this.oservice.save(org);
					} else if ("2".equals(mainType)) {
						OcrmFCiBelongOrg org = new OcrmFCiBelongOrg();
						org.setMainType("2");
						org.setCustId(jarray2.getString(i));
						org.setInstitutionCode(jarray11.getString(j));
						org.setInstitutionName(jarray12.getString(j));
						org.setAssignUser(this.auth.getUserId());
						org.setAssignDate(new Date());
						org.setAssignUsername(this.auth.getUsername());
						this.oservice.save(org);
					}
			}
		}
	}

	public String TranCrmToEcifMgr(String custId, String mgrId, String recordId, String mainType) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String resp = "";
		try {
			RequestHeader header = new RequestHeader();
			header.setReqSysCd("CRM");
			header.setReqSeqNo(format.format(new Date()));
			header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));
			header.setDestSysCd("ECIF");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP("127.0.0.1");
			header.setTlrNo(auth.getUserId());
			StringBuffer sb = new StringBuffer();
			sb.append("<RequestBody>");
			sb.append("<txCode>updateBelong</txCode>");
			sb.append("<txName>修改客户归属信息</txName>");
			sb.append("<authType>1</authType>");
			sb.append("<authCode>1010</authCode>");
			sb.append("<custNo>" + custId + "</custNo>");
			sb.append("<belongBranch></belongBranch>");
			sb.append("<belongManager>");
			sb.append("<custManagerType></custManagerType>");
			sb.append("<validFlag></validFlag>");
			if (mainType != null)
				sb.append("<mainType>" + mainType + "</mainType>");
			else {
				sb.append("<mainType></mainType>");
			}
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<custManagerNo>" + mgrId + "</custManagerNo>");
			sb.append("</belongManager>");
			sb.append("</RequestBody>");
			String Xml = new String(sb.toString().getBytes());
			resp = TransClient.process(header, Xml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
		}
		return resp;
	}

	public String TranCrmToEcifOrg(String custId, String orgId, String recordId, String mainType) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String req = "";
		try {
			RequestHeader header = new RequestHeader();
			header.setReqSysCd("CRM");
			header.setReqSeqNo(format.format(new Date()));
			header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));
			header.setDestSysCd("ECIF");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP("127.0.0.1");
			header.setTlrNo(auth.getUserId());
			StringBuffer sb = new StringBuffer();
			sb.append("<RequestBody>");
			sb.append("<txCode>updateBelong</txCode>");
			sb.append("<txName>修改客户归属信息</txName>");
			sb.append("<authType>1</authType>");
			sb.append("<authCode>1010</authCode>");
			sb.append("<custNo>" + custId + "</custNo>");
			sb.append("<belongManager></belongManager>");
			sb.append("<belongBranch>");
			sb.append("<belongBranchType></belongBranchType>");
			sb.append("<validFlag></validFlag>");
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<belongBranchNo>" + orgId + "</belongBranchNo>");
			if (mainType != null)
				sb.append("<mainType>" + mainType + "</mainType>");
			else {
				sb.append("<mainType></mainType>");
			}
			sb.append("</belongBranch>");
			sb.append("</RequestBody>");
			String Xml = new String(sb.toString().getBytes());
			req = TransClient.process(header, Xml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
		}
		return req;
	}

	public boolean doResXms(String xml) throws Exception {
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail").element("TxStatCode").getTextTrim();
			if ((TxStatCode != null) && (!TxStatCode.trim().equals("")) && (TxStatCode.trim().equals("000000")))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * add by liuming 20170523
	 */
	public void TranCrmListToLN() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		this.request = ((HttpServletRequest) ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
		String CUST_IDs = this.request.getParameter("CUST_IDs");
		String MGR_IDs = this.request.getParameter("MGR_IDs");
		String INSTITUTIONs = this.request.getParameter("INSTITUTIONs");
		JSONObject jsonObject2 = JSONObject.fromObject(CUST_IDs);
		JSONObject jsonObject4 = JSONObject.fromObject(MGR_IDs);
		JSONObject jsonObject7 = JSONObject.fromObject(INSTITUTIONs);
		JSONArray jarray2 = jsonObject2.getJSONArray("CUST_ID");
		JSONArray jarray4 = jsonObject4.getJSONArray("MGR_ID");
		JSONArray jarray7 = jsonObject7.getJSONArray("INSTITUTION");
		// 接收客户经理编号
		String tMgrId = ((OcrmFCiTransApply) this.model).getTMgrId();
		// 接收客户经理的机构编号
		String tOrgId = ((OcrmFCiTransApply) this.model).getTOrgId();
		// 交接日期
		Date workInterfixDt = ((OcrmFCiTransApply) this.model).getWorkInterfixDt();
		// 工作移交原因
		String handOverReason = ((OcrmFCiTransApply) this.model).getHandOverReason();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 调用信贷客户移交接口
		for (int i = 0; i < jarray2.size(); i++) {
			// 判断是否是信贷客户
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs1 = null;
			String sql1 = null;
			boolean retMsg = false;
			String retM = "";
			try {
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql1 = "SELECT 1 FROM ACRM_F_CI_CROSSINDEX C WHERE C.CUST_ID ='" + jarray2.getString(i) + "' AND C.SRC_SYS_NO='LN'";
				rs1 = stmt.executeQuery(sql1);
				rs1.last();
				System.out.println("length:" + rs1.getRow());
				if (rs1 != null && rs1.getRow() > 0) {
					String responseXml4LN = TranCrmToLN("", jarray2.getString(i), this.auth.getUserId(), this.auth.getUnitId(), tMgrId, tOrgId, sdf.format(workInterfixDt), handOverReason, jarray4.getString(i), jarray7.getString(i));
					boolean responseFlag4LN;
					responseFlag4LN = doResXms(responseXml4LN);
					if (!responseFlag4LN) {
						// throw new BizException(1,0,"0000","Warning-168:数据信息同步信贷失败2,请及时联系IT部门!")
						retM = getReturnMessage(responseXml4LN).toString();
						retMsg = true;
						throw new BizException(1, 0, "0000", "Warning-168:数据信息同步信贷失败2:" + getReturnMessage(responseXml4LN) + ">>>>>[客户编号:" + jarray2.getString(i) + "]");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (retMsg) {
					throw new BizException(1, 0, "0000", "Warning-168:数据信息同步信贷失败2:" + retM + ">>>>>[客户编号:" + jarray2.getString(i) + "]");
				} else {
					throw new BizException(1, 0, "0000", "Warning-168:数据信息同步信贷失败3,请及时联系IT部门!");
				}
			} finally {
				JdbcUtil.close(rs1, stmt, conn);
			}
		}
	}
  
	/**
	 * 客户移交同步信贷
	 * add by liuming 20170522
	 * 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public String TranCrmToLN(String appleType, String custId, String userId, String userOrg, String mgrId, String orgId, String applyDate, String handOverReason, String sMgrId, String sOrgId) {
		String req = "";
		try {
			StringBuffer custInfoList = new StringBuffer("");// 待移交的客户信息集合
			custInfoList.append("                 <CusInfoList>\n");
			custInfoList.append("                    <CusInfo>\n");
			if (handOverReason == null || handOverReason.equals("")) {
				handOverReason = "无";
			}
			custInfoList.append("                       <cus_id>" + custId + "</cus_id>\n");
			custInfoList.append("                       <handover_type>10</handover_type>\n");// 业务类型 默认为：10 客户资料，其他类型尚未使用
			custInfoList.append("                    </CusInfo>\n");
			custInfoList.append("                 </CusInfoList>\n");
			// CRM系统与信贷系统用户编码规则不一致，需要转换。CRM:511N1456,信贷：5111456
			String lnInputId = chargeUserIdForLN(userId);
			String lnHandoverId = chargeUserIdForLN(sMgrId);
			String lnMgrId = chargeUserIdForLN(mgrId);
			String orgType = "";
			if (appleType != null && appleType.equals("3")) {
				orgType = "10";
			} else {
				orgType = "21";
			}
			// 组装请求报文
			StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
			sb.append("<TransBody>\n");
			sb.append("  <RequestHeader>\n");
			sb.append("      <DestSysCd>LN</DestSysCd>\n");
			sb.append("  </RequestHeader>\n");
			sb.append("  <RequestBody>\n");
			sb.append("      <Packet>\n");
			sb.append("         <Data>\n");
			sb.append("           <Req>\n");
			sb.append("                 <area_code>1</area_code>\n");// 移交方式（必填） 1：移出客户(默认)；2：转入客户;
			sb.append("                 <org_type>" + orgType + "</org_type>\n");// 移交范围(按机构)（必填） 10：支行内移交；21：跨支行移交
			sb.append("                 <handover_mode>2</handover_mode>\n");// 移交内容（必填） 2：客户与业务移交（默认）
			sb.append("                 <handover_scope>1</handover_scope>\n");// 移交范围(按客户经理)（必填） 1：单个客户移交（指定客户,个数有限制不能超出报文长度）；2：按客户经理所有客户
			sb.append(custInfoList.toString());// 待移交客户信息
			sb.append("                 <handover_br_id>" + sOrgId + "</handover_br_id>\n");// 被移出客户经理机构（必填）
			sb.append("                 <handover_id>" + lnHandoverId + "</handover_id>\n");// 被移出客户经理编号（必填）
			sb.append("                 <receiver_br_id>" + orgId + "</receiver_br_id>\n");// 接收人客户经理机构（必填）
			sb.append("                 <receiver_id>" + lnMgrId + "</receiver_id>\n");// 接收人客户经理编号（必填）
			sb.append("                 <supervise_br_id></supervise_br_id>\n");// 监交机构（非必填）
			sb.append("                 <supervise_id></supervise_id>\n");// 监交人（非必填）
			sb.append("                 <handover_detail>" + handOverReason + "</handover_detail>\n");// 移交说明（必填）
			sb.append("                 <input_id>" + lnInputId + "</input_id>\n");// 申请人编号（必填）
			sb.append("                 <input_br_id>" + userOrg + "</input_br_id>\n");// 申请人机构（必填）
			sb.append("                 <input_date>" + applyDate + "</input_date>\n");// 申请日期（必填）
			sb.append("           </Req>\n");
			sb.append("           <Pub>\n");
			sb.append("               <prcscd>CusHandoverByCrm</prcscd>\n");
			sb.append("           </Pub>\n");
			sb.append("         </Data>\n");
			sb.append("     </Packet>\n");
			sb.append("   </RequestBody>\n");
			sb.append("</TransBody>\n");
			StringBuffer sbReq = new StringBuffer();
			sbReq.append(String.format("%08d", sb.toString().getBytes("GBK").length));
			sbReq.append(sb.toString());
			System.out.println("requestToLN:" + sbReq.toString());
			// 调用信贷客户移交 接口,得到返回报文。
			req = TransClient.processLN(sbReq.toString());// 调用信贷
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000", "Warning-168:数据信息同步信贷失败1,请及时联系IT部门!");
		}
		return req;
	}
	
	public String chargeUserIdForLN(String userid) {
		String useridForLN = "";
		if (userid.toUpperCase().equals("ADMIN")) {
			useridForLN = userid;
		} else {
			useridForLN = userid.substring(0, 3) + userid.substring(4, 8);
		}
		return useridForLN;
	}

	// 获取响应报文中的返回信息
	public String getReturnMessage(String xml) throws Exception {
		String retMessage = "";
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			retMessage = root.element("ResponseTail").element("TxStatDesc").getTextTrim();
		} catch (Exception e) {
			e.printStackTrace();
			retMessage = "解析信贷系统返回报文失败";
		}
		return retMessage;
	}
	/**
	 * 对私批量导入查询错误信息
	 */
//	public void exportFalseDs() {
//		StringBuffer sb = new StringBuffer();
//		sb.append(" select t.CUST_ID,t.CUST_NAME,t.CUST_TYPE,t.MGR_ID,t.MGR_NAME,t.TMGR_ID,t.TMGR_NAME,t.HAND_KIND,t.WORK_INTERFIX_DT,t.OLD_AUM,t.NEW_AUM,t.OLD_CREDIT,t.NEW_CREDIT,t.TRANSCONTENT,t.TRANSOTHER,t.IMP_MSG");
//		sb.append(" from OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.create_user='" + auth.getUserId() + "' and t.IMP_STATUS='0' ");
//		SQL = sb.toString();
//		datasource = ds;
//		log.info(sb.toString());
//	}
	/**
	 * 个金、法金批量导入查询错误信息
	 */
//	public void exportFalse() {
//		StringBuffer sb = new StringBuffer();
//		sb.append(" select t.CUST_ID,t.CUST_NAME,t.CUST_TYPE,t.MGR_ID,t.MGR_NAME,t.TMGR_ID,t.TMGR_NAME,t.TORG_ID,t.HAND_KIND,t.HAND_OVER_REASON,t.WORK_INTERFIX_DT,t.IMP_MSG");
//		sb.append(" from OCRM_F_CI_BELONG_CUSTMGR_TEMP t where t.create_user='" + auth.getUserId() + "' and t.IMP_STATUS='0' ");
//		SQL = sb.toString();
//		datasource = ds;
//		log.info(sb.toString());
//	}
	public void prepareFalse(){
		String pkHead = request.getParameter("pkHead");
		String importFlag = request.getParameter("importFlag");
		StringBuffer sb = new StringBuffer();
		if("charge".equals(importFlag)){//主管直接移交错误查询
			sb.append(" select t.CORE_NO,t.CUST_NAME,t.CUST_TYPE,t.MGR_ID,t.MGR_NAME,t.T_MGR_ID,t.T_MGR_NAME,t.HAND_KIND,t.HAND_OVER_REASON,t.WORK_INTERFIX_DT,t.IMP_MSG");
			sb.append(" from OCRM_F_CI_MANAGE_TRANS_TEMP t where t.ASSIGN_USER='" + auth.getUserId() + "' and t.IMP_STATUS='0' and t.id like '"+pkHead+"%' ");
		}else{
			sb.append("select t.core_no,t.imp_msg "
					+ "from OCRM_F_CI_BELONG_CUSTMGR_TEMP t "
					+ "where t.create_user='" + auth.getUserId() + "' "
					+ "and t.IMP_STATUS='0'"
					+ "and t.id like '"+pkHead+"%' ");
		}
		SQL = sb.toString();
		datasource = ds;
		log.info(sb.toString());
	}
	
	/**
	 * 查询主管移交错误信息
	 */
	/*public void exportManagerFalse() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.CORE_NO,t.CUST_NAME,t.CUST_TYPE,t.MGR_ID,t.MGR_NAME,t.T_MGR_ID,t.T_MGR_NAME,t.HAND_KIND,t.HAND_OVER_REASON,t.WORK_INTERFIX_DT,t.IMP_MSG");
		sb.append(" from OCRM_F_CI_MANAGE_TRANS_TEMP t where t.ASSIGN_USER='" + auth.getUserId() + "' and t.IMP_STATUS='0' ");
		SQL = sb.toString();
		datasource = ds;
		log.info(sb.toString());
	}*/
	
	/**
	 * 导出主管直接移交错误信息
	 * @return
	 */
//	public String exportManager(){
//		try {
//			ActionContext ctx = ActionContext.getContext();
//			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//			this.setJson(request.getParameter("condition"));
//			// Map<String, String> fieldMap = getFieldMap();
//			// fieldMap = getFieldMap();
//			Map<String, String> downloadInfo = new HashMap<String, String>();
//			downloadInfo.put("menuId", request.getParameter("menuId"));
//			downloadInfo.put("queryCon", request.getParameter("condition"));
//			StringBuffer sb = new StringBuffer();
//			sb.append("select cust_type from OCRM_F_CI_BELONG_CUSTMGR_TEMP where create_user='" + auth.getUserId() + "'");
//			Connection cn = ds.getConnection();
//			QueryHelper qh = new QueryHelper(sb.toString(), cn);
//			List<HashMap<String, Object>> tempList = (List<HashMap<String, Object>>) qh.getJSON().get("data");
//			exportManagerFalse();
//			processSQL();
//			// 添加导出列字典映射字段
//			Map<String, String> translateMap = new HashMap<String, String>();
//			translateMap = (Map<String, String>) JSONUtil.deserialize(request.getParameter("translateMap"));
//			for (String key : translateMap.keySet()) {
//				if (null != translateMap.get(key) && !"".equals(translateMap.get(key))) {
//					this.addOracleLookup(key, translateMap.get(key).toString());
//				}
//			}
//			// Map<String, String> fieldMapoRI = this.getFieldMap();
//			Map<String, String> fieldMap = new LinkedHashMap<String, String>();// 导出文件列映射
//			fieldMap.put("CORE_NO", "核心客户号");
//			fieldMap.put("CUST_NAME", "客户姓名");
//			fieldMap.put("CUST_TYPE", "客户类型");
//			fieldMap.put("MGR_ID", "客户经理编号");
//			fieldMap.put("MGR_NAME", "客户经理名称");
//			fieldMap.put("T_MGR_ID", "接受客户经理编号");
//			fieldMap.put("T_MGR_NAME", "接受客户经理名字");
//			fieldMap.put("HAND_KIND", "客户移交类别");
//			fieldMap.put("HAND_OVER_REASON", "工作移交原因");
//			fieldMap.put("WORK_INTERFIX_DT", "工作移交时间");
//			fieldMap.put("IMP_MSG", "校验信息");
//			DownloadThread thread = (DownloadThread) ctx.getSession().get("BACKGROUND_EXPORT_CSV_TASK");
//			if (thread == null || thread.status.equals(DownloadThread.status_completed)) {
//				DatabaseHelper dh = new DatabaseHelper(datasource);
//				int taskId = dh.getNextValue("ID_BACKGROUND_TASK");
//				DownloadThreadManager dtm = DownloadThreadManager.getInstance();
//				thread = dtm.addDownloadThread(taskId, SQL, datasource, downloadInfo);
//				if (thread == null || DownloadThread.status_wating.equals(thread.status)) {
//					throw new Exception("当前下载人数过多，请稍后重试。");
//					// throw new BizException(1,0,"2001","当前下载人数过多，下载进程已放入队列，请不要重复点击下载。");
//				} else {
//					json.put("taskID", thread.getThreadID());
//					thread.setFieldLabel(fieldMap);
//					thread.setOracleMapping(oracleMapping);
//					ctx.getSession().put("BACKGROUND_EXPORT_CSV_TASK", thread);
//				}
//			} else {
//				json.put("taskID", thread.getThreadID());
//				// throw new Exception("请等待当前下载任务完成。");
//				throw new BizException(1, 0, "2002", "请等待当前下载任务完成。");
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//			throw new BizException(1, 0, "1002", "导出列字典映射字段转换出错。");
//		} catch (BizException e) {
//			throw e;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BizException(1, 2, "1002", e.getMessage());
//		}
//		return "success";
//	}
	
}