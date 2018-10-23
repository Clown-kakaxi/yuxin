package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.text.DateFormat;
import java.util.ArrayList;
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
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCaC;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeCaEService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 中小企客户营销流程 - 文件收集阶段(CO)
 * 
 * @author denghj
 * @since 2015-08-10
 */
@SuppressWarnings("serial")
@Action("/smeCaCo")
public class SmeCaCoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; // 声明数据源
	private HttpServletRequest request;

	@Autowired
	private OcrmFCiSmeCaEService service;

	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	@Autowired
	public void init() {
		model = new OcrmFCiMktCaC();
		setCommonService(service);
		// 新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog = true;
	}

	/**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String sqlapp = " select c.ID,c.CALL_ID,c.INTENT_ID,c.CUST_ID,c.CUST_NAME,c.GROUP_NAME,c.AREA_ID,c.AREA_NAME,"
				+ "c.DEPT_ID,c.DEPT_NAME,c.RM,c.APPLY_AMT,c.CASE_TYPE,c.IF_ADD,c.ADD_AMT,c.DD_DATE,"
				+ "c.SX_DATE,c.GRADE_LEVEL,c.COCO_DATE,c.COCO_INFO,c.CA_DATE_P,c.CA_DATE_R,c.CA_HARD_INFO,c.IF_THIRD_STEP,"
				+ "c.USER_ID,c.CHECK_STAT,c.RECORD_DATE,c.UPDATE_DATE,c.PIPELINE_ID,c.RM_ID,c.COMP_TYPE,"
				+ "c.IF_COCO,c.SUC_PROBABILITY,c.HARD_REMARK,c.FOREIGN_MONEY,"
				+ "c.FIRST_DOCU_DATE,c.GET_DOCU_DATE,c.SEND_DOCU_DATE,c.CA_DATE_S,c.CA_PP,c.GRADE_PERSECT,c.RM_REPLY_COCO,c.CUST_TYPE,c.IF_SUMBIT_CO,c.XD_CA_DATE,"
				+ "DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD', '12','TWD','13','USD',c.CURRENCY) as CURRENCY,"
				+ "a.user_name  from OCRM_F_CI_MKT_CA_C c  "
				+ "left join admin_auth_account a on c.user_id = a.account_name  where 1=1 and  (a.belong_busi_line = '5' or a.belong_busi_line ='0') ";

		String id = request.getParameter("id");
		StringBuilder sb = new StringBuilder(sqlapp);
		if (id != null && !"".equals(id)) {// 流程查询使用
			sb.append(" and c.id = '" + id + "'");
		} else {
			sb.append(" and  ((c.IF_THIRD_STEP  not  like '1' and c.IF_THIRD_STEP not like '99') or c.IF_THIRD_STEP is null) and c.CASE_TYPE = '16' and c.IF_SUMBIT_CO = '1' ");
			setPrimaryKey("c.ID desc ");
		}

		SQL = sb.toString();
		datasource = ds;
		setPrimaryKey("c.IF_THIRD_STEP desc, c.RECORD_DATE asc");
		configCondition("IF_THIRD_STEP", "=", "IF_THIRD_STEP", DataType.String);
		// configCondition("IF_SUMBIT_CO","=","IF_SUMBIT_CO",DataType.String);
		configCondition("CUST_ID", "=", "CUST_ID", DataType.String);
		configCondition("PIPELINE_ID","=","PIPELINE_ID",DataType.String);
		configCondition("CUST_NAME", "like", "CUST_NAME", DataType.String);
		configCondition("AREA_NAME", "like", "AREA_NAME", DataType.String);
		configCondition("DEPT_ID", "=", "DEPT_ID", DataType.String);
		configCondition("DEPT_NAME", "=", "DEPT_NAME", DataType.String);
		configCondition("RM", "like", "RM", DataType.String);
		configCondition("RM_ID", "=", "RM_ID", DataType.String);
		configCondition("APPLY_AMT", "=", "APPLY_AMT", DataType.String);
		configCondition("CASE_TYPE", "=", "CASE_TYPE", DataType.String);
		configCondition("SUC_PROBABILITY", "=", "SUC_PROBABILITY",
				DataType.String);
	}

	public DefaultHttpHeaders save() throws Exception {		
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		OcrmFCiMktCaC CaCo = (OcrmFCiMktCaC)model;
		CaCo.setUserId(auth.getUserId());
		CaCo.setCheckStat("2");
		CaCo.setIfSumbitCo("1");
		if (CaCo.getId() == null) {// 新增
			CaCo.setRecordDate(new Date());// 设置记录日期
			if ((CaCo.getPipelineId()) == null) {// 设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
				CaCo.setPipelineId(new Date().getTime());
			}// 设置PipelineId必须保证其唯一性
			service.save(model);
		} else {// 修改
			CaCo.setUpdateDate(new Date());
			service.save(model);
			Map<String, Object> map = new HashMap<String, Object>();
			// 根据字段IF_PIPELINE判断是否转入PIPELINE
			String flag1 = CaCo.getIfThirdStep();
			if ("1".equals(flag1)) {// 判断是否进入下一阶段，当需要进入下一阶段，把map的数据添加到下一阶段
				map.put("caId", CaCo.getId());
				map.put("pipelineId", CaCo.getPipelineId());
				map.put("custId", CaCo.getCustId());
				map.put("custName", CaCo.getCustName());
				map.put("areaId", CaCo.getAreaId());
				map.put("areaName", CaCo.getAreaName());
				map.put("deptId", CaCo.getDeptId());
				map.put("deptName", CaCo.getDeptName());
				map.put("rm", CaCo.getRm());
				map.put("rmId", CaCo.getRmId()); 
				map.put("applyAmt", CaCo.getApplyAmt());
				map.put("caseType", CaCo.getCaseType());
				map.put("compType", CaCo.getCompType());
				map.put("gradeLevel", CaCo.getGradeLevel());
				map.put("ifAdd", CaCo.getIfAdd());
				map.put("addAmt", CaCo.getAddAmt());
				map.put("currency", CaCo.getCurrency());
				map.put("foreignMoney",CaCo.getForeignMoney());
				map.put("custType", CaCo.getCustType());
				map.put("xdCaDate",  DateFormat.getDateInstance(DateFormat.MEDIUM).format(CaCo.getXdCaDate()));
				this.setJson(map);

			} else if ("3".equals(flag1)) {
				service.backRM(CaCo.getId().toString());
			}
		}

		return new DefaultHttpHeaders("success");
	}

	public void batchDel() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		String ids[] = idStr.split(",");

		List<List> pList = new ArrayList<List>();
		for (String id : ids) {
			List list = service.getBaseDAO().findByNativeSQLWithNameParam(
					"select pipeline_id from OCRM_F_CI_MKT_CA_C WHERE ID='"
							+ Long.parseLong(id) + "'", null);
			pList.add(list);
		}
		if (pList != null && pList.size() > 0) {
			for (List aList : pList) {
				for (int i = 0; i < aList.size(); i++) {
					String pId = aList.get(i) + "";
					service.batchUpdateByName("UPDATE OcrmFCiMktProspectC  SET ifPipeline='99' WHERE pipelineId='" + Long.parseLong(pId) + "'", new HashMap());
					service.batchUpdateByName("UPDATE OcrmFCiMktIntentC SET ifSecondStep='99' WHERE pipelineId='" + Long.parseLong(pId) + "'", new HashMap());
					service.batchUpdateByName("UPDATE OcrmFCiMktCaC   SET ifThirdStep='99'   WHERE pipelineId='" + Long.parseLong(pId) + "'", new HashMap());
					service.batchUpdateByName("UPDATE OcrmFCiMktCheckC  SET ifFourthStep='99' WHERE pipelineId='" + Long.parseLong(pId) + "'", new HashMap());
					service.batchUpdateByName("UPDATE OcrmFCiMktApprovlC  SET ifFifthStep='99' WHERE pipelineId='" + Long.parseLong(pId) + "'", new HashMap());
					service.batchUpdateByName("UPDATE OcrmFCiMktApprovedC  SET lastSendStep='99' WHERE pipelineId='" + Long.parseLong(pId) + "'", new HashMap());
				}
			}
		}
	}

}
