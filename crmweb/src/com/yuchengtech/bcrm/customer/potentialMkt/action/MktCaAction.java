package com.yuchengtech.bcrm.customer.potentialMkt.action;

import java.math.BigDecimal;
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
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktCaCService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 企商金客户营销流程 -  企商金CA准备页面  luyy  2014-07-25
 */

@SuppressWarnings("serial")
@Action("/mktCaC")
public class MktCaAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiMktCaCService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktCaC();  
        	setCommonService(service);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sqlapp = " select c.ID,c.CALL_ID,c.INTENT_ID,c.CUST_ID,c.CUST_NAME,c.GROUP_NAME,c.AREA_ID,c.AREA_NAME," +
				"c.DEPT_ID,c.DEPT_NAME,c.RM,c.APPLY_AMT,c.CASE_TYPE,c.IF_ADD,c.ADD_AMT,c.DD_DATE," +
				"c.SX_DATE,c.GRADE_LEVEL,c.COCO_DATE,c.COCO_INFO,c.CA_DATE_P,c.CA_DATE_R,c.CA_HARD_INFO,c.IF_THIRD_STEP," +
				"c.USER_ID,c.CHECK_STAT,ca.RECORD_DATE,c.UPDATE_DATE,c.PIPELINE_ID,c.RM_ID,c.COMP_TYPE," +
				"c.IF_COCO,c.SUC_PROBABILITY,c.HARD_REMARK,c.FOREIGN_MONEY," +
				"DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD', '12','TWD','13','USD',c.CURRENCY) as CURRENCY," +
    			"a.user_name,(trunc(sysdate, 'DD') - ca.record_date) TREAMENT_DAYS " +
    			"from OCRM_F_CI_MKT_CA_C c left join admin_auth_account a on c.user_id = a.account_name  " +
    			"left join (select c.pipeline_id,min(c.record_date) record_date from OCRM_F_CI_MKT_CA_C c group by c.pipeline_id) ca on c.pipeline_id = ca.pipeline_id where 1=1  ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
//    		addOracleLookup("CASE_TYPE", "CASE_TYPE");
//    		addOracleLookup("IF_ADD", "IF_FLAG");
//    		addOracleLookup("IF_THIRD_STEP", "IF_FLAG");
//    		addOracleLookup("GRADE_LEVEL", "GRADE_PERSECT");
    	}else{
//    		sb.append("and c.rm_id='"+auth.getUserId()+"' ");
    		sb.append(" and  ((c.IF_THIRD_STEP  not  like '1' and c.IF_THIRD_STEP not like '99') or c.IF_THIRD_STEP is null  ) ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.IF_THIRD_STEP desc, c.RECORD_DATE asc");
        	configCondition("IF_THIRD_STEP","=","IF_THIRD_STEP",DataType.String);
        	configCondition("CUST_ID","=","CUST_ID",DataType.String);
    		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("AREA_NAME","like","AREA_NAME",DataType.String);
    		configCondition("DEPT_ID","=","DEPT_ID",DataType.String);
    		configCondition("DEPT_NAME","=","DEPT_NAME",DataType.String);
    		configCondition("RM","like","RM",DataType.String);
    		configCondition("RM_ID","=","RM_ID",DataType.String);
    		configCondition("APPLY_AMT","=","APPLY_AMT",DataType.String);
    		configCondition("CASE_TYPE","=","CASE_TYPE",DataType.String);
    		configCondition("SUC_PROBABILITY","=","SUC_PROBABILITY",DataType.String);
//    		configCondition("CUST_SOURCE","=","CUST_SOURCE",DataType.String);
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((OcrmFCiMktCaC)model).setUserId(auth.getUserId());
    	((OcrmFCiMktCaC)model).setCheckStat("2");
    	if(((OcrmFCiMktCaC)model).getId()==null){//新增
    		((OcrmFCiMktCaC)model).setRecordDate(new Date());//设置记录日期
    		if((((OcrmFCiMktCaC)model).getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			((OcrmFCiMktCaC)model).setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(model);
    	}else{//修改
    		((OcrmFCiMktCaC)model).setUpdateDate(new Date());
    		service.save(model);
    	}
    	 Map<String,Object> map=new HashMap<String,Object>();
    	//根据字段IF_PIPELINE判断是否转入PIPELINE
    	String flag1=((OcrmFCiMktCaC)model).getIfThirdStep();
//    	String flag2=((OcrmFCiMktIntentC)model).getGradePersect();
    	if("1".equals(flag1)){//判断是否进入下一阶段，当需要进入下一阶段，把map的数据添加到下一阶段
    		map.put("caId", ((OcrmFCiMktCaC)model).getId());
    		map.put("pipelineId", ((OcrmFCiMktCaC)model).getPipelineId());
    		map.put("custId", ((OcrmFCiMktCaC)model).getCustId());
    		map.put("custName", ((OcrmFCiMktCaC)model).getCustName());
    		map.put("areaId", ((OcrmFCiMktCaC)model).getAreaId());
    		map.put("areaName", ((OcrmFCiMktCaC)model).getAreaName());
    		map.put("deptId", ((OcrmFCiMktCaC)model).getDeptId());
    		map.put("deptName", ((OcrmFCiMktCaC)model).getDeptName());
    		map.put("rm", ((OcrmFCiMktCaC)model).getRm());
    		map.put("rmId", ((OcrmFCiMktCaC)model).getRmId());
    		map.put("applyAmt", ((OcrmFCiMktCaC)model).getApplyAmt());
    		map.put("caseType", ((OcrmFCiMktCaC)model).getCaseType());
    		map.put("compType", ((OcrmFCiMktCaC)model).getCompType());
    		map.put("gradeLevel", ((OcrmFCiMktCaC)model).getGradeLevel());
    		map.put("ifAdd", ((OcrmFCiMktCaC)model).getIfAdd());
    		map.put("addAmt", ((OcrmFCiMktCaC)model).getAddAmt());
    		map.put("currency", ((OcrmFCiMktCaC)model).getCurrency());
    		map.put("foreignMoney", ((OcrmFCiMktCaC)model).getForeignMoney());
    		this.setJson(map);
    		//save to PIPELINE
    	}
//    	Long id = ((OcrmFCiMktCaC)model).getId();
//    	
//		String instanceid = "CA_"+id+"_23";//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
//		String jobName = "CA准备复核_"+ca.getCustName();//自定义流程名称
//		service.initWorkflowByWfidAndInstanceid("42", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
//		
//		 Map<String,Object> map1=new HashMap<String,Object>();
//			map1.put("instanceid", instanceid);
//		    map1.put("currNode", "42_a3");
//		    map1.put("nextNode",  "42_a4");
//		    this.setJson(map1);
    	
    	return new DefaultHttpHeaders("success");
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
//    	for(String id : ids){
//    		service.batchUpdateByName(" delete from OcrmFCiMktCaC g where g.id='"+Long.parseLong(id)+"'", new HashMap());
//    	}
    	
    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_CA_C WHERE ID='"+Long.parseLong(id)+"'", null);
            // List<Long> list=service.findByJql("select pipelineId from OcrmFCiMktCaC WHERE ID='"+Long.parseLong(id)+"'", null);
             pList.add(list);
    	}
    	if(pList!=null && pList.size()>0){
    		for(List aList:pList){
    			for(int i=0;i<aList.size();i++){
    				String pId=aList.get(i)+"";
    				service.batchUpdateByName("UPDATE OcrmFCiMktProspectC  SET ifPipeline='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktIntentC SET ifSecondStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktCaC   SET ifThirdStep='99'  WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktCheckC  SET ifFourthStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktApprovlC  SET ifFifthStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    				service.batchUpdateByName("UPDATE OcrmFCiMktApprovedC  SET lastSendStep='99' WHERE pipelineId='"+Long.parseLong(pId)+"'", new HashMap());
    			}
    			
    		}
    	}
    	
    	
    	
    	
    }
}