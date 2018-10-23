package com.yuchengtech.bcrm.payOrRepay.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.bcrm.payOrRepay.model.OcrmFMkTmuOther;
import com.yuchengtech.bcrm.payOrRepay.service.OcrmFMkTmuOtherService;

/**
 * @描述：新拨还款查询Action
 * @author sunjing5
 * @date:2017-02-10
 */
@SuppressWarnings("serial")
@Action("/ocrmFMkTmuOtherAction")
public class OcrmFMkTmuOtherAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 数据源

	@Autowired
	private OcrmFMkTmuOtherService service;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	@Autowired
	public void init() {// 初始化module
		model = new OcrmFMkTmuOther();
		setCommonService(service);
		needLog = false;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder querySql = new StringBuilder(" SELECT * FROM (   "+
				" SELECT '0' AS POTENTIAL_FLAG, A.ID, A.REGION,O3.ORG_NAME AS REGION1, A.ORG_ID,O4.ORG_NAME AS ORG_ID1, A.APPLY_NO, A.CUST_TYPE,  A.IF_TAIWAN, A.CUST_ID, A.CUST_NAME,"+
				"  A.gather_or_back, A.gather_type, A.PROGRESS, A.PROBABILITY,  A.IF_RE,"+
				"   decode(nvl(A.AMOUNT,0),0,0,A.AMOUNT/1000) AS AMOUNT,"+
				"   decode(nvl(A.DISCOUNT_OCCUR_AMT,0),0,0,A.DISCOUNT_OCCUR_AMT/1000)AS DISCOUNT_OCCUR_AMT,"+
				"   A.REMARK, A.APPROVE_STATE,  "+
				"    A.CREATE_USER, A1.USER_NAME AS CREATE_USERNAME, A.CREATE_ORG,  O1.ORG_NAME AS CREATE_ORGNAME,   "+
				"      TO_CHAR(A.CREATE_TM, 'yyyy-mm-dd hh24:mi:ss') AS CREATE_TM,     "+
				"   A.LAST_UPDATE_USER, A2.USER_NAME AS LAST_UPDATE_USERNAME, A.LAST_UPDATE_ORG, O2.ORG_NAME AS LAST_UPDATE_ORGNAME, "+
				"     TO_CHAR(A.LAST_UPDATE_TM, 'yyyy-mm-dd hh24:mi:ss') AS LAST_UPDATE_TM, "+
				"    B.MGR_ID, B.MGR_NAME, A.ORG_ID AS INSTITUTION      "+
				"  FROM OCRM_F_MK_TMU_OTHER A       "+
				"    JOIN OCRM_F_CI_BELONG_CUSTMGR B ON A.CUST_ID = B.CUST_ID "+
				"    LEFT JOIN ADMIN_AUTH_ACCOUNT A1  ON A.CREATE_USER = A1.ACCOUNT_NAME    "+
				"   LEFT JOIN ADMIN_AUTH_ACCOUNT A2  ON A.LAST_UPDATE_USER = A2.ACCOUNT_NAME"+
				"    LEFT JOIN ADMIN_AUTH_ORG O1 ON A.CREATE_ORG = O1.ORG_ID      "+
				"     LEFT JOIN ADMIN_AUTH_ORG O2 ON A.CREATE_ORG = O2.ORG_ID     "+
				"	 LEFT JOIN ADMIN_AUTH_ORG O3 ON A.REGION = O3.ORG_ID"+
				"	 LEFT JOIN ADMIN_AUTH_ORG O4 ON A.ORG_ID = O4.ORG_ID"+
				"  UNION        "+
				"  SELECT '1' AS POTENTIAL_FLAG, A.ID,A.REGION,O3.ORG_NAME AS REGION1, A.ORG_ID,O4.ORG_NAME AS ORG_ID1,  A.APPLY_NO, A.CUST_TYPE,  A.IF_TAIWAN,  A.CUST_ID, A.CUST_NAME,   "+
				" A.gather_or_back, A.gather_type,A.PROGRESS, A.PROBABILITY, A.IF_RE,        "+
				"   decode(nvl(A.AMOUNT,0),0,0,A.AMOUNT/1000) AS AMOUNT,"+
				"   decode(nvl(A.DISCOUNT_OCCUR_AMT,0),0,0,A.DISCOUNT_OCCUR_AMT/1000)AS DISCOUNT_OCCUR_AMT,"+
				"  A.REMARK, A.APPROVE_STATE, A.CREATE_USER,  A1.USER_NAME AS CREATE_USERNAME,    A.CREATE_ORG,  O1.ORG_NAME AS CREATE_ORGNAME,"+
				"  TO_CHAR(A.CREATE_TM, 'yyyy-mm-dd hh24:mi:ss') AS CREATE_TM,     "+
				"  A.LAST_UPDATE_USER,A2.USER_NAME AS LAST_UPDATE_USERNAME,   A.LAST_UPDATE_ORG,  O2.ORG_NAME AS LAST_UPDATE_ORGNAME,"+
				"  TO_CHAR(A.LAST_UPDATE_TM, 'yyyy-mm-dd hh24:mi:ss') AS LAST_UPDATE_TM,     "+
				"   P.CUST_MGR AS MGR_ID, AC.USER_NAME AS MGR_NAME, A.ORG_ID AS INSTITUTION  "+
				"  FROM OCRM_F_MK_TMU_OTHER A        "+
				"    JOIN ACRM_F_CI_POT_CUS_COM P   ON A.CUST_ID = P.CUS_ID    "+
				"   LEFT JOIN ADMIN_AUTH_ACCOUNT AC  ON P.CUST_MGR = AC.ACCOUNT_NAME         "+
				"  LEFT JOIN ADMIN_AUTH_ACCOUNT A1 ON A.CREATE_USER = A1.ACCOUNT_NAME        "+
				"  LEFT JOIN ADMIN_AUTH_ACCOUNT A2  ON A.LAST_UPDATE_USER = A2.ACCOUNT_NAME  "+
				"  LEFT JOIN ADMIN_AUTH_ORG O1  ON A.CREATE_ORG = O1.ORG_ID        "+
				"	 LEFT JOIN ADMIN_AUTH_ORG O3 ON A.REGION = O3.ORG_ID"+
				"	 LEFT JOIN ADMIN_AUTH_ORG O4 ON A.ORG_ID = O4.ORG_ID"+
				"   LEFT JOIN ADMIN_AUTH_ORG O2  ON A.CREATE_ORG = O2.ORG_ID) B    "+
				"   WHERE 1 = 1");

		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("MGR_NAME")) {// 客户经理名称
					querySql.append(" and B.MGR_NAME like '%"
							+ this.getJson().get(key) + "%'");
				} else if (null != key && key.equals("REGION1")) {// 区域
					querySql.append("  and (B.REGION ='"
							+ this.getJson().get(key) + "'  OR B.REGION1 LIKE '%"	+ this.getJson().get(key) +"%')");
				} else if (null != key && key.equals("ORG_ID1")) {// 分支行
					querySql.append("  and (B.ORG_ID ='"
							+ this.getJson().get(key) + "'  OR B.ORG_ID1 LIKE '%"	+ this.getJson().get(key) +"%')");
				} else if (null != key && key.equals("CUST_TYPE")) {// 客户类别
					querySql.append(" and  B.CUST_TYPE  ='"
							+ this.getJson().get(key) + "' ");
				}else if (null != key && key.equals("CUST_NAME")) {// 客户名称
					querySql.append(" and  B.CUST_NAME  like'%"
							+ this.getJson().get(key) + "%' ");
				} else if (null != key && key.equals("APPROVE_STATE")) {// 审批状态
					querySql.append(" and  B.APPROVE_STATE  ='"
							+ this.getJson().get(key) + "' ");
				} else if (null != key && key.equals("IF_TAIWAN")) {//是否台商
					querySql.append(" and  B.IF_TAIWAN ='"
							+ this.getJson().get(key) + "' ");
				};
			}
		}
		  addOracleLookup("POTENTIAL_FLAG", "XD000084");
		  
		SQL = querySql.toString(); // 为父类SQL属性赋值（设置查询SQL）
		setPrimaryKey("b.LAST_UPDATE_TM desc "); // 设置查询排序条件
		datasource = ds; // 为父类数据源赋值
	}

	/**
	 * 数据新增 HTTP:POST方法 URL:/actionName
	 */
	public DefaultHttpHeaders create() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		service.save((OcrmFMkTmuOther) model);
		return new DefaultHttpHeaders("success");
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
		service.batchDestroy(idsStr);
		return "success";
	}


	/**
	 * 区域中心码值
	 */
	public void searchArea() {
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT UNIT.ORG_NAME as VALUE,UNIT.UNITID as KEY FROM SYS_UNITS UNIT WHERE UNITSEQ LIKE '500%'   and levelunit in('2','1') order by UNIT.levelunit,UNIT.id ");
			this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 客户类别码值
	 */
	public void searchCustType() {
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT I.F_VALUE AS VALUE,I.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM I  WHERE I.F_LOOKUP_ID='XD000052'  ORDER BY I.F_CODE");
			this.json = new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 业务条线码值
	 */
/*	public void searchBlname() {
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append(" SELECT I.PARENT_NAME AS VALUE, I.BL_NO AS KEY"
					+ "  FROM ( SELECT  L.BL_NAME,L.BL_LEVEL,L.BL_NO,L.BL_ID,  "
					+ "  CASE  WHEN L.BL_LEVEL = '2' AND L.PARENT_ID='1'  THEN  'TT'  "
					+ "        WHEN L.BL_LEVEL = '2' AND L.PARENT_ID='2'   THEN  'CB'  "
					+ "  END AS PARENT_NAME    "
					+ " FROM ACRM_F_CI_BUSI_LINE L  ) I  "				
					+ "    WHERE I.PARENT_NAME IS NOT NULL   "
					+ " ORDER BY I.BL_NO");
			this.json = new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	/**
	 * 客户经理提交新拨审批流程
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void submit() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idsStr = request.getParameter("idsStr"); // 获取需要提交的记录id
		String id[] = idsStr.split(",");
	
		for (int i = 0; i < id.length; i++) {
			String instanceid = "";
			String jobName = "";
			String custName="";
	          List<OcrmFMkTmuOther> ts = (List<OcrmFMkTmuOther>)service.findByJql("select c from OcrmFMkTmuOther c where c.id = '"+id[i]+"'", null);
	          for(OcrmFMkTmuOther t:ts){
	        	  custName = t.getCustName();
	          }	
			String submitName = auth.getUserId();
			String applyno=service.selectSequence(id[i]);
			instanceid = "TMU_" + applyno + "_01" + "_"
					+ new SimpleDateFormat("HHmmss").format(new Date())+"_"+submitName;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			jobName = "TMU、投行及其他手续费审批_" + custName;// 自定义流程名称

			service.initWorkflowByWfidAndInstanceid("142", jobName, null,
					instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			//更改字段状态
			service.updateApply(applyno,id[i]);
			String nextNode = "";
			List roles = auth.getRolesInfo();
			for (Object m : roles) {
				Map map = (Map) m;
				if ("R305".equals(map.get("ROLE_CODE"))|| "R105".equals(map.get("ROLE_CODE"))||"R304".equals(map.get("ROLE_CODE"))|| "R104".equals(map.get("ROLE_CODE"))) {// 法金RM与法金ARM
					//总行RM没有直接上级Team Head,可由法金业务条线主管审批
					String teamHead = service.getTeamhead(submitName);
					if(teamHead != null && !teamHead.isEmpty()){
						nextNode = "142_a4";// 法金team head,直属上级Team Head
					}else{
						nextNode = "142_a5";// 行长
					}
					continue;
				} else if("R106".equals(map.get("ROLE_CODE"))|| "R309".equals(map.get("ROLE_CODE"))){
						nextNode = "142_a5";// 行长
				}else{}
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("instanceid", instanceid);
			map1.put("currNode", "142_a3");
			map1.put("nextNode", nextNode);
			this.setJson(map1);
		}
	}

	private List<OcrmFMkTmuOther> findByJql(String string, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询区域
	 * @return
	 */
	public String QuYu() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		
		try { 
			StringBuilder sb = new StringBuilder("");
			sb.append( "SELECT CASE O.ORG_ID WHEN '500' THEN '500' ELSE UP_ORG_ID END AS UP_ORG_ID FROM ADMIN_AUTH_ORG O WHERE  O.ORG_ID='"+auth.getUnitId()+"'");
			String querySql = sb.toString();
			this.json = new QueryHelper(querySql, ds.getConnection()).getJSON();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 只有创建人才能修改、删除和提交该记录
	 * @return
	 */
	public String ifRole() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idsStr = request.getParameter("idsStr"); // 获取需要提交的记录id
		String flag="";
		try { 
			StringBuilder sb = new StringBuilder("");
			sb.append( "SELECT A.CREATE_USER FROM OCRM_F_MK_NEW_REPAY A  WHERE A.ID='"+idsStr+"'");
			String querySql = sb.toString();
			String creater="";
			this.json = new QueryHelper(querySql, ds.getConnection()).getJSON();
			List<?> list = (List<?>) json.get("data");// 赋给list
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(0);// 获取第一个，即把查询出的值赋值给MAP
			creater = (String) map.get("CREATE_USER");
			if(creater.equals(auth.getUserId())){
				flag= "success";
			}
			else{
				flag= "failure";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}