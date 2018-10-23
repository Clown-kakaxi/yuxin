package com.yuchengtech.bcrm.sales.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.belong.action.CustTransAction;
//import com.yuchengtech.bcrm.sales.service.MktActivityManegerbackService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.DatabaseHelper;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.download.DownloadThread;
import com.yuchengtech.bob.download.DownloadThreadManager;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;
@ParentPackage("json-default")
@Action(value="/mktCustManegerback", results={
    @Result(name="success", type="json"),
})
public class MktCustManegerbackAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	private Map<String, Object> JSON;

	public Map<String, Object> getJSON() {
		return JSON;
	}

	public void setJSON(Map<String, Object> jSON) {
		JSON = jSON;
	}
	
	private static Logger log = Logger.getLogger(CustTransAction.class);
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	 public void prepare() {
		 System.out.println("******"+JSONSerializer.toJSON(request.getParameterMap()));
		 json.remove("impFlag");
		 StringBuilder sb  = new StringBuilder();
		 sb.append("SELECT NVL(T2.INSTITUTION,A.ORG_ID) ORG_ID ,NVL(T2.INSTITUTION_NAME,A2.ORG_NAME) ORG_NAME,T1.CUST_ID,T1.CUST_NAME, ")
		 .append(" T1.CORE_NO,NVL(T2.MGR_ID,T1.MGR_NO) MGR_NO,NVL(T2.MGR_NAME,A.USER_NAME) MGR_NAME,")
		 .append(" DECODE(T1.CORE_NO,NULL,T1.TEL_NO,(SELECT T5.CONTMETH_INFO FROM ACRM_F_CI_CONTMETH T5 WHERE T5.CUST_ID = T1.CUST_ID AND T5.CONTMETH_TYPE = '102' AND ROWNUM = 1)) TEL_NO, ")
		 .append(" DECODE(T1.MGR_STATUS,'Y','已同意','未同意') MGR_STATUS FROM OCRM_F_MGR_CUS T1 LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR T2 ON T1.CUST_ID = T2.CUST_ID  ")
		 .append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T1.MGR_NO ")
		 .append(" LEFT JOIN  ADMIN_AUTH_ORG A2 ON A2.ORG_ID = A.ORG_ID ")
		 .append(" where  1 > 0 ");

		 for(String key:this.getJson().keySet()){
    	     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    	    	 if(key.equals("ORG_ID"))//机构号
    	        	 sb.append(" and NVL(T2.INSTITUTION,A.ORG_ID) ='" +this.getJson().get(key)+"'");
    	    	 if(key.equals("CUST_ID"))//客户编号
    	             sb.append(" and T1.cust_id like  '%"+this.getJson().get(key)+"%'");
    	    	 if(key.equals("CUST_NAME"))
    	             sb.append(" and T1.cust_name like  '%"+this.getJson().get(key)+"%'");
    	    	 
    	         if(key.equals("MGR_NAME")){
    	        	    String mgr=this.getJson().get(key).toString();
	                 	String mgrName []=mgr.split(",");
	                 	StringBuilder mgrsb = new StringBuilder();
	 	                for(int i=0;i<mgrName.length;i++){
	 	                	if(i==0)
	 	                		mgrsb.append("'"+mgrName[i]+"'");
	 	                	else
	 	                		mgrsb.append(",'"+mgrName[i]+"'");
	 	                }
	    	             sb.append(" and NVL(T2.INSTITUTION_NAME,A.USER_NAME) in ("+mgrsb.toString()+")");
    	         }
    	         if(key.equals("MGR_STATUS")){
    	        	 if(this.getJson().get(key).equals("Y")){
    	        		 sb.append(" AND T1.MGR_STATUS = 'Y' ");
    	        	 }else  if(this.getJson().get(key).equals("N")){
    	        		 sb.append(" AND T1.MGR_STATUS IS NULL ");
    	        	 }
    	         }
    	     }
        }
	 
	    setPrimaryKey(" NVL(T2.INSTITUTION,A.ORG_ID) asc ");

	     SQL=sb.toString();
	     datasource = ds;
	}
	 
	 public void prepareFalse(){
		StringBuffer sb = new StringBuffer();
		sb.append("select t.CORE_NO,t.CUST_NAME,")
		.append(" DECODE(t.CORE_NO,NULL,t.TEL_NO,T2.CONTMETH_INFO) TEL_NO,t.MGR_NO,t.imp_msg from Ocrm_f_Mk_Mkt_My_Acti_Temp t ")
		.append(" LEFT JOIN ACRM_F_CI_CUSTOMER T1 ON T1.CORE_NO = t.CORE_NO ")
		.append(" LEFT JOIN ACRM_F_CI_CONTMETH T2 ON T1.CUST_ID = T2.CUST_ID ")
		.append(" where t.create_user='" + auth.getUserId() + "' and t.IMP_STATUS='0' ")
		.append("  AND DECODE(T1.CORE_NO, NULL,'102',T2.CONTMETH_TYPE)= '102'");
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
			downloadInfo.put("queryCon", request.getParameter("condition"));;
			prepareFalse();
			//processSQL();
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
			fieldMap.put("CUST_NAME", "客户名称");
			fieldMap.put("TEL_NO", "手机号");
			fieldMap.put("MGR_NO", "归属客户经理");
			fieldMap.put("IMP_MSG", "校验信息");
			DownloadThread thread = (DownloadThread) ctx.getSession().get("BACKGROUND_EXPORT_CSV_TASK");
			if (thread == null || thread.status.equals(DownloadThread.status_completed)) {
				DatabaseHelper dh = new DatabaseHelper(datasource);
				int taskId = dh.getNextValue("ID_BACKGROUND_TASK");
				DownloadThreadManager dtm = DownloadThreadManager.getInstance();
				thread = dtm.addDownloadThread(taskId, SQL, datasource, downloadInfo);
				if (thread == null || DownloadThread.status_wating.equals(thread.status)) {
					throw new Exception("当前下载人数过多，请稍后重试。");
					// throw new BizException(1,0,"2001","当前下载人数过多，下载进程已放入队列，请不要重复点击下载。");
				} else {
					json.put("taskID", thread.getThreadID());
					thread.setFieldLabel(fieldMap);
					thread.setOracleMapping(oracleMapping);
					ctx.getSession().put("BACKGROUND_EXPORT_CSV_TASK", thread);
				}
			} else {
				json.put("taskID", thread.getThreadID());
				// throw new Exception("请等待当前下载任务完成。");
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
	
	 /**
	 * 连接callreport--导出历史信息
	 */
	public String exportHistory() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			this.setJson(request.getParameter("condition"));
			Map<String, String> downloadInfo = new HashMap<String, String>();
			downloadInfo.put("menuId", request.getParameter("menuId"));
			downloadInfo.put("queryCon", request.getParameter("condition"));
			prepareFalse();
			//processSQL();
			// 添加导出列字典映射字段
			Map<String, String> translateMap = new HashMap<String, String>();
			translateMap = (Map<String, String>) JSONUtil.deserialize(request.getParameter("translateMap"));
			for (String key : translateMap.keySet()) {
				if (null != translateMap.get(key) && !"".equals(translateMap.get(key))) {
					this.addOracleLookup(key, translateMap.get(key).toString());
				}
			}
			Map<String, String> fieldMap = new LinkedHashMap<String, String>();// 导出文件列映射
			fieldMap.put("CALLREPORT_INFO", "访谈内容");
			fieldMap.put("CREATE_TM", "录入时间");
			fieldMap.put("CREATE_USER", "记录人");
			fieldMap.put("CREATE_USERNAME", "记录人姓名");
			DownloadThread thread = (DownloadThread) ctx.getSession().get("BACKGROUND_EXPORT_CSV_TASK");
			if (thread == null || thread.status.equals(DownloadThread.status_completed)) {
				DatabaseHelper dh = new DatabaseHelper(datasource);
				int taskId = dh.getNextValue("ID_BACKGROUND_TASK");
				DownloadThreadManager dtm = DownloadThreadManager.getInstance();
				thread = dtm.addDownloadThread(taskId, SQL, datasource, downloadInfo);
				if (thread == null || DownloadThread.status_wating.equals(thread.status)) {
					throw new Exception("当前下载人数过多，请稍后重试。");
					// throw new BizException(1,0,"2001","当前下载人数过多，下载进程已放入队列，请不要重复点击下载。");
				} else {
					json.put("taskID", thread.getThreadID());
					thread.setFieldLabel(fieldMap);
					thread.setOracleMapping(oracleMapping);
					ctx.getSession().put("BACKGROUND_EXPORT_CSV_TASK", thread);
				}
			} else {
				json.put("taskID", thread.getThreadID());
				// throw new Exception("请等待当前下载任务完成。");
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
	
	/**
	 * 查看今天的callreport数量
	 * @param custId
	 * @return
	 */
	public void getTodayCallCount(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String custId = request.getParameter("custId");
		int count = 0;
		JSONObject retJso = new JSONObject();
		StringBuilder sb = new StringBuilder();
		sb.append("select 1 from OCRM_F_CI_CALLREPORT_INFO c where to_char(c.CREATE_TM,'yyyyMMdd')='"+DateUtils.formatDate(new Date(), "yyyyMMdd")+"'")
		  .append(" and c.CUST_ID='"+custId+"' and c.UPDATE_USER = '"+ auth.getUserId() +"'");
		try {
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String,Object>> tempList = (List<HashMap<String,Object>>)query.getJSON().get("data");
			if(tempList != null){
				count = tempList.size();
			}
			retJso.put("count", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.json = (Map)retJso;
	}
	
	/**
	 * 客户经理同意客户名片的分配
	 * @return
	 */
	public void acceptCus(){
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject retJso = new JSONObject();
		String custId = request.getParameter("custId");//客户编号
		String coreNo = request.getParameter("coreNo");//核心客户号
		if(coreNo != null && !coreNo.equals("")){
			//如果核心客户号不为空，同步ecif
			String responseXml = TranCrmToEcifMgrOrg(custId,auth.getUserId(),auth.getUnitId());
			boolean responseFlag;
			try {
				responseFlag = doResXms(responseXml);
				if (!responseFlag) {
					retJso.put("msg", "Warning-168:数据信息同步失败,请及时联系IT部门!");
					throw new BizException(1, 0, "0000", "Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
				}else{
					StringBuilder sb = new StringBuilder();
					sb.append("INSERT INTO OCRM_F_CI_BELONG_CUSTMGR T ")
					.append(" (CUST_ID,MGR_ID,MGR_NAME,MAIN_TYPE,INSTITUTION,INSTITUTION_NAME,EFFECT_DATE) ")
					.append(" SELECT T1.CUST_ID,T1.MGR_NO,A.USER_NAME,'1',A.ORG_ID,A2.ORG_NAME,SYSDATE FROM OCRM_F_MGR_CUS T1  ")
					.append(" LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T1.MGR_NO ")
					.append(" LEFT JOIN ADMIN_AUTH_ORG A2 ON A2.ORG_ID = A.ORG_ID  ")
					.append(" WHERE T1.CUST_ID = '"+ custId +"'");
					ds.getConnection().createStatement().executeUpdate(sb.toString());
					StringBuilder sb1 = new StringBuilder();
					sb1.append(" UPDATE OCRM_F_MGR_CUS T1 SET T1.MGR_STATUS = 'Y' WHERE T1.CUST_ID = '"+ custId +"'");
					ds.getConnection().createStatement().executeUpdate(sb1.toString());
					retJso.put("success", true);
					retJso.put("msg", "该客户已经分配到您的名下！");
				}
			} catch (Exception e1) {
				retJso.put("msg", "系统报错，请稍后重试或联系管理员！");
				e1.printStackTrace();
			}
		}else{
			StringBuilder sb2 = new StringBuilder();
			sb2.append(" UPDATE OCRM_F_MGR_CUS T1 SET T1.MGR_STATUS = 'Y' WHERE T1.CUST_ID = '"+ custId +"'");
			try {
				ds.getConnection().createStatement().executeUpdate(sb2.toString());
				retJso.put("success", true);
				retJso.put("msg", "该客户已经分配到您的名下！");
			} catch (SQLException e) {
				retJso.put("msg", "系统报错，请稍后重试或联系管理员！");
				e.printStackTrace();
			}
		}
		this.json = (Map) retJso;
	}
	
	/**
	 * 如果有核心客户号，要ecif同步
	 * @param custId
	 * @param mgrId
	 * @param orgId
	 * @return
	 */
	public String TranCrmToEcifMgrOrg(String custId, String mgrId,String orgId) {
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
			
			/*sb.append("<customer>");// customer：客户信息--start
			sb.append("    <custName>" + custName + "</custName>");// 客户名称**
			sb.append("    <coreNo>" + coreNo + "</coreNo>");// 核心客户号--
			sb.append("    <mobilePhone>" + telNo + "</mobilePhone>\n");// 手机号码**
			sb.append("</customer>");*/
			
			sb.append("<belongBranch>");
			sb.append("<belongBranchType></belongBranchType>");
			sb.append("<validFlag></validFlag>");
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<belongBranchNo>" + orgId + "</belongBranchNo>");
			sb.append("<mainType></mainType>");
			sb.append("</belongBranch>");
			sb.append("<belongManager>");
			sb.append("<custManagerType></custManagerType>");
			sb.append("<validFlag></validFlag>");
			sb.append("<mainType></mainType>");
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<custManagerNo>" + mgrId + "</custManagerNo>");
			sb.append("</belongManager>");
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
	 * 客户经理拒绝接受客户
	 */
	public void rejectCus(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String custId = request.getParameter("custId");
		JSONObject retJson = new JSONObject();
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE OCRM_F_MGR_CUS SET MGR_NO = '',MGR_STATUS = 'N'")
		.append(" WHERE CUST_ID ='"+custId+"'");
		Connection conn;
		try {
			conn = ds.getConnection();
			Statement sm = conn.createStatement();
			int rejectCount = sm.executeUpdate(sb.toString());
			if(rejectCount > 0){
				retJson.put("rejectOk", "success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		this.json = (Map)retJson;
	}
	
	/**
	 * 判断是否已经分配过客户经理
	 */
	public void isHasMgr(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String custId = request.getParameter("custId");
		String coreNo = request.getParameter("coreNo");
		boolean acceptBtn = true;
		boolean rejectBtn = true;
		StringBuilder sb = new StringBuilder();
		JSONObject retJson = new JSONObject();
		if(coreNo != null && !coreNo.equals("")){
			sb.append("SELECT 1 FROM OCRM_F_MGR_CUS T WHERE T.CORE_NO = '"+coreNo+"' AND T.MGR_NO = '"+auth.getUserId()+"'");
			try {
				QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
				List<HashMap<String,Object>> tempList = (List<HashMap<String,Object>>)query.getJSON().get("data");
				if(tempList ==null || tempList.size() == 0){//归属客户经理发生变动
					retJson.put("success", false);
					retJson.put("msg", "该客户归属客户经理发生变动，请刷新后操作");
				}else{
					sb = new StringBuilder();
					sb.append(" SELECT 1 FROM OCRM_F_CI_BELONG_CUSTMGR T WHERE T.CUST_ID = '"+ custId +"'");
					QueryHelper query1 = new QueryHelper(sb.toString(), ds.getConnection());
					List<HashMap<String,Object>> tempList1 = (List<HashMap<String,Object>>)query1.getJSON().get("data");
					if(tempList1!=null && tempList1.size() > 0){//已经分配过客户经理
						acceptBtn = false;
						rejectBtn = false;
					}
					retJson.put("success", true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			sb = new StringBuilder();
			sb.append("SELECT 1 FROM OCRM_F_MGR_CUS T WHERE T.CUST_ID = '"+ custId +"' AND T.MGR_NO = '"+auth.getUserId()+"'");
			
			try {
				QueryHelper query3 = new QueryHelper(sb.toString(), ds.getConnection());
				List<HashMap<String,Object>> tempList3 = (List<HashMap<String,Object>>)query3.getJSON().get("data");
				if(tempList3 ==null || tempList3.size() == 0){//已经分配过客户经理
					retJson.put("success", false);
					retJson.put("msg", "该客户归属客户经理发生变动，请刷新后操作");
				}else{
					sb = new StringBuilder();
					sb.append("SELECT T.MGR_STATUS FROM OCRM_F_MGR_CUS T WHERE T.CUST_ID = '"+ custId +"' AND T.MGR_STATUS IN('Y','N') ");
					QueryHelper query2 = new QueryHelper(sb.toString(), ds.getConnection());
					List<HashMap<String,Object>> tempList2 = (List<HashMap<String,Object>>)query2.getJSON().get("data");
					if(tempList2!= null && tempList2.size() > 0){
						if(tempList2.get(0).get("MGR_STATUS") != null){
							String mgrStatus = tempList2.get(0).get("MGR_STATUS").toString();
							if(mgrStatus.equals("Y")){
								acceptBtn = false;
								rejectBtn = true;
							}else if(mgrStatus.equals("N")){
								acceptBtn = false;
								rejectBtn = false;
							}
						}
					}
					retJson.put("success", true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		retJson.put("acceptBtn", acceptBtn);
		retJson.put("rejectBtn", rejectBtn);
		this.json = (Map)retJson;
	}

	/**
	 * 查询客户基础信息
	 * @return
	 */
	public void queryCustBase(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String custId = request.getParameter("custId");
		StringBuilder sb = new StringBuilder();
		JSONObject retObject = new JSONObject();
		sb.append("SELECT T.CUST_ID CUST_ID,NVL(T1.CUST_NAME,T2.CUST_NAME) CUST_NAME,NVL(T1.CUST_TYPE,'2') CUST_TYPE,NVL(T1.CORE_NO,T2.CORE_NO) CORE_NO FROM ")
		.append(" (SELECT '"+custId+"' CUST_ID FROM DUAL)T  ")
		.append(" LEFT JOIN ACRM_F_CI_CUSTOMER T1 ON T1.CUST_ID = T.CUST_ID ")
		.append(" LEFT JOIN OCRM_F_MGR_CUS T2 ON T.CUST_ID = T2.CUST_ID ");
		try {
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String,Object>> tempList = (List<HashMap<String,Object>>)query.getJSON().get("data");
			if(tempList!= null && tempList.size() > 0 ){
				retObject.put("custName", tempList.get(0).get("CUST_NAME"));
				retObject.put("custType", tempList.get(0).get("CUST_TYPE"));
				retObject.put("coreNo", tempList.get(0).get("CORE_NO"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.json = (Map)retObject;
	}
	
	/**
	 * 名单回收
	 */
	public void menuRecovery(){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT T.CORE_NO,T.CUST_ID,T.CUST_NAME,")
		.append(" DECODE(T.CORE_NO,NULL,T.TEL_NO,(SELECT T1.CONTMETH_INFO FROM ACRM_F_CI_CONTMETH T1 WHERE T1.CONTMETH_TYPE = '102' AND T1.CUST_ID = T.CUST_ID AND ROWNUM = 1)) AS TEL_NO")
		.append(" FROM  OCRM_F_MGR_CUS T WHERE MGR_NO IS NULL ");
		SQL = sb.toString();
		datasource = ds;
		log.info(sb.toString());
	}
	
	/**
	 * 导出名单回收信息
	 * @return
	 */
	public String exportMenuRecovery() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			this.setJson(request.getParameter("condition"));
			Map<String, String> downloadInfo = new HashMap<String, String>();
			downloadInfo.put("menuId", request.getParameter("menuId"));
			downloadInfo.put("queryCon", request.getParameter("condition"));;
			menuRecovery();
			//processSQL();
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
			fieldMap.put("CUST_ID", "客户编号");
			fieldMap.put("CUST_NAME", "客户名称");
			fieldMap.put("TEL_NO", "手机号");
			DownloadThread thread = (DownloadThread) ctx.getSession().get("BACKGROUND_EXPORT_CSV_TASK");
			if (thread == null || thread.status.equals(DownloadThread.status_completed)) {
				DatabaseHelper dh = new DatabaseHelper(datasource);
				int taskId = dh.getNextValue("ID_BACKGROUND_TASK");
				DownloadThreadManager dtm = DownloadThreadManager.getInstance();
				thread = dtm.addDownloadThread(taskId, SQL, datasource, downloadInfo);
				if (thread == null || DownloadThread.status_wating.equals(thread.status)) {
					throw new Exception("当前下载人数过多，请稍后重试。");
					// throw new BizException(1,0,"2001","当前下载人数过多，下载进程已放入队列，请不要重复点击下载。");
				} else {
					json.put("taskID", thread.getThreadID());
					thread.setFieldLabel(fieldMap);
					thread.setOracleMapping(oracleMapping);
					ctx.getSession().put("BACKGROUND_EXPORT_CSV_TASK", thread);
				}
			} else {
				json.put("taskID", thread.getThreadID());
				// throw new Exception("请等待当前下载任务完成。");
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
	
	
	/**
     * 查询客户视图树形菜单，根据视图类型或客户号
     * 1、零售客户视图，2、对公客户视图，3、客户群视图，4、集团客户视图，5、客户经理视图
     * @return
     */
	public void queryCustViewTree() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String viewType = request.getParameter("viewtype");
		String custId = request.getParameter("custId");
		
		StringBuilder roleIdSb = new StringBuilder("");
		for (int i = 0; i < auth.getAuthorities().size(); i++) {
			if (!"".equals(auth.getAuthorities().get(i).toString()) && auth.getAuthorities().get(i) != null) {
				if (i == 0){
					roleIdSb.append("'"+ auth.getAuthorities().get(i).toString() + "'");
				}else{
					roleIdSb.append(",'"+ auth.getAuthorities().get(i).toString() + "'");
				}
			}
		}

		Map<?, ?> numMap = searchCustViewTree(roleIdSb.toString());
		List<?> numList = (List<?>) numMap.get("data");
		Map<?, ?> numMap2 = (Map<?, ?>) numList.get(0);
		StringBuilder sb = new StringBuilder("");
		try {
			if (!"0".equals(numMap2.get("NUM_ID").toString())) {
				sb.append("select distinct o1.* from OCRM_SYS_VIEW_MANAGER o1 inner join OCRM_SYS_VIEW_USER_RELATION o2  on  o1.ID=o2.VIEW_ID  where 1=1");

				sb.append(" and o2.ROLE_ID IN (");
				sb.append(roleIdSb.toString());
				sb.append(")");
				sb.append(" and viewtype='1'");
				/*if(viewType != null && !"".equals(viewType)){
					sb.append(" and viewtype='" + viewType + "'");
				}
				//传入参数是客户号时，获取客户类型
				if(custId != null && !"".equals(custId)){
					sb.append(" and viewtype = (SELECT case when cust_type = '1' then '2' when cust_type = '2' then  '1' end  FROM ACRM_F_CI_CUSTOMER WHERE CUST_ID = '" + custId + "') ");
				}*/
				sb.append(" order by o1.orders");
			} else {
				sb.append("select * from OCRM_SYS_VIEW_MANAGER o where 1=1");
				sb.append(" and viewtype='1'");
				/*if(viewType != null && !"".equals(viewType)){
					sb.append(" and viewtype='" + viewType + "'");
				}
				//传入参数是客户号时，获取客户类型
				if(custId != null && !"".equals(custId)){
					sb.append(" and viewtype = (SELECT case when cust_type = '1' then '2' when cust_type = '2' then  '1' end FROM ACRM_F_CI_CUSTOMER WHERE CUST_ID = '" + custId + "') ");
				}*/
				sb.append(" order by o.orders");
			}
			//setJSON(new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			List<HashMap<String,Object>> tempList = (List<HashMap<String,Object>>)query.getJSON().get("data");
			JSONObject retObject = new JSONObject();
			if(tempList!= null && tempList.size() > 0 ){
				retObject.put("data", tempList);
			}
			this.json = (Map)retObject;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查角色是否被授权过
	 * @param useId
	 * @return
	 */
	public Map<String, Object> searchCustViewTree(String useId) {
		String s = " select count(t1.id) as num_id  from OCRM_SYS_VIEW_USER_RELATION t1  where t1.role_id in("+ useId + ")";
		QueryHelper qh;
		try {
			qh = new QueryHelper(s, ds.getConnection());
			return qh.getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	    * 查询我的视图树形菜单，根据视图类型或客户号
	    * 1、零售客户视图，2、对公客户视图，3、客户群视图，4、集团客户视图，5、客户经理视图
	    * @return
	    */
		public void queryMyViewTree(){
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String viewType = request.getParameter("viewtype");
			String custId = request.getParameter("custId");
			
			StringBuilder roleIdSb = new StringBuilder("");
			for (int i = 0; i < auth.getAuthorities().size(); i++) {
				if (!"".equals(auth.getAuthorities().get(i).toString()) && auth.getAuthorities().get(i) != null) {
					if (i == 0){
						roleIdSb.append("'"+ auth.getAuthorities().get(i).toString() + "'");
					}else{
						roleIdSb.append(",'"+ auth.getAuthorities().get(i).toString() + "'");
					}
				}
			}

			Map<?, ?> numMap = searchCustViewTree(roleIdSb.toString());
			List<?> numList = (List<?>) numMap.get("data");
			Map<?, ?> numMap2 = (Map<?, ?>) numList.get(0);
			StringBuilder sb = new StringBuilder("");
			try {
				if (!"0".equals(numMap2.get("NUM_ID").toString())) {
					sb.append("SELECT T1.ID,T1.NAME,T1.ADDR,T1.ORDERS,T1.VIEWTYPE,'0' AS PARENTID,t.id as MY_VIEW_ID from ocrm_sys_view_manager_comm t inner join ( ");
					sb.append(" select distinct o1.* from OCRM_SYS_VIEW_MANAGER o1 inner join OCRM_SYS_VIEW_USER_RELATION o2  on  o1.ID=o2.VIEW_ID  where 1=1");
					sb.append(" and o2.ROLE_ID IN ("+roleIdSb.toString()+")");
					sb.append(" and viewtype='1'");
					/*if(viewType != null && !"".equals(viewType)){
						sb.append(" and viewtype='" + viewType + "'");
					}
					//传入参数是客户号时，获取客户类型
					if(custId != null && !"".equals(custId)){
						sb.append(" and viewtype = (SELECT case when cust_type = '1' then '2' when cust_type = '2' then  '1' end FROM ACRM_F_CI_CUSTOMER WHERE CUST_ID = '" + custId + "') ");
					}*/
					sb.append(") t1 on t1.id = t.view_id ");
					sb.append(" and t.user_id = '"+auth.getUserId()+"'");
					sb.append(" order by t1.orders");
				} else {
					sb.append("SELECT T1.ID,T1.NAME,T1.ADDR,T1.ORDERS,T1.VIEWTYPE,'0' AS PARENTID,t.id as MY_VIEW_ID from ocrm_sys_view_manager_comm t inner join ( ");
					sb.append("select * from OCRM_SYS_VIEW_MANAGER o where 1=1");
					sb.append(" and viewtype='1'");
					/*if(viewType != null && !"".equals(viewType)){
						sb.append(" and viewtype='" + viewType + "'");
					}
					//传入参数是客户号时，获取客户类型
					if(custId != null && !"".equals(custId)){
						sb.append(" and viewtype = (SELECT case when cust_type = '1' then '2' when cust_type = '2' then  '1' end FROM ACRM_F_CI_CUSTOMER WHERE CUST_ID = '" + custId + "') ");
					}*/
					sb.append(") t1 on t1.id = t.view_id ");
					sb.append(" and t.user_id = '"+auth.getUserId()+"'");
					sb.append(" order by t1.orders");
				}
				QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
				List<HashMap<String,Object>> tempList = (List<HashMap<String,Object>>)query.getJSON().get("data");
				JSONObject retObject = new JSONObject();
				if(tempList!= null && tempList.size() > 0 ){
					retObject.put("data", tempList);
				}
				this.json = (Map)retObject;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
}
