package com.yuchengtech.bcrm.customer.potentialSme.action;

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
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktIntentC;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeIntentEService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 中小企客户营销流程 -  合作意向信息  
 * @author denghj
 * @since 2015-08-07
 */
@SuppressWarnings("serial")
@Action("/smeIntentE")
public class SmeIntentEAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiSmeIntentEService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktIntentC();  
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
//    	String sqlapp = "select c.*,a.user_name  from OCRM_F_CI_MKT_INTENT_C c left join admin_auth_account a on c.user_id = a.account_name " +
//    					" where 1=1 ";
    	String sqlapp = "select c.ID,c.PIPELINE_ID,c.CALL_ID,c.PROSPECT_ID,c.CUST_ID,c.CUST_NAME,c.GROUP_NAME,c.AREA_ID,c.AREA_NAME," +
						"c.DEPT_ID,c.DEPT_NAME,c.RM,c.RM_ID,c.APPLY_AMT,c.CASE_TYPE,c.VISIT_DATE,c.MAIN_INSURE,c.MAIN_AMT,c.COMBY_AMT,c.GRADE_PERSECT,c.COMP_TYPE,c.HARD_INFO,c.CP_HARD_INFO,c.SUC_PROBABILITY," +
						"c.IF_SECOND_STEP,c.USER_ID,c.CHECK_STAT,c.RECORD_DATE,c.UPDATE_DATE,c.HARD_REMARK,c.IF_ADD,c.ADD_AMT,c.CUST_TYPE,c.REFUSE_REASON,c.REMARK," +
						"DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD', '12','TWD','13','USD',c.CURRENCY) AS CURRENCY,c.FOREIGN_MONEY,a.user_name  from OCRM_F_CI_MKT_INTENT_C c left join admin_auth_account a on c.user_id = a.account_name " +
						" where 1=1 and (a.belong_busi_line = '5' or a.belong_busi_line ='0')";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    	}else{
    		sb.append("and  ((c.if_second_step is null or c.if_second_step ='0' or c.if_second_step ='2') or (c.GRADE_PERSECT ='6' )) ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.IF_SECOND_STEP desc, c.RECORD_DATE asc");
        	configCondition("IF_SECOND_STEP","=","IF_SECOND_STEP",DataType.String);
        	configCondition("CUST_ID","=","CUST_ID",DataType.String);
        	configCondition("PIPELINE_ID","=","PIPELINE_ID",DataType.String);
    		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("CALL_DATE","=","CALL_DATE",DataType.Date);
    		configCondition("AREA_NAME","like","AREA_NAME",DataType.String);
    		configCondition("DEPT_ID","=","DEPT_ID",DataType.String);
    		configCondition("DEPT_NAME","=","DEPT_NAME",DataType.String);
    		configCondition("RM","like","RM",DataType.String);
    		configCondition("RM_ID","=","RM_ID",DataType.String);
    		configCondition("CASE_TYPE","=","CASE_TYPE",DataType.String);
    		configCondition("CUST_TYPE","=","CUST_TYPE",DataType.String);
    		configCondition("APPLY_AMT","=","APPLY_AMT",DataType.String);
    		configCondition("VISIT_DATE","=","VISIT_DATE",DataType.Date);
    		configCondition("GRADE_PERSECT","=","GRADE_PERSECT",DataType.String);
    		configCondition("SUC_PROBABILITY","=","SUC_PROBABILITY",DataType.String);
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	OcrmFCiMktIntentC IntentC = (OcrmFCiMktIntentC)model;
    	IntentC.setUserId(auth.getUserId());
    	IntentC.setCheckStat("2");
    	if(IntentC.getId()==null){//新增
    		IntentC.setRecordDate(new Date());//设置记录日期
    		if((IntentC.getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			IntentC.setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(model);
    	}else{//修改
    		IntentC.setUpdateDate(new Date());
    		service.save(model);
    	}
    	Map<String,Object> map=new HashMap<String,Object>();
    	//根据字段IF_PIPELINE判断是否转入PIPELINE
    	String flag1=IntentC.getIfSecondStep();
    	String flag2=IntentC.getGradePersect();
    	if("1".equals(flag1)&&!"6".equals(flag2)){//判断是否进入下一阶段
    		map.put("intentId", IntentC.getId());
    		map.put("pipelineId", IntentC.getPipelineId());
    		map.put("custId", IntentC.getCustId());
    		map.put("custName", IntentC.getCustName());
    		map.put("areaId", IntentC.getAreaId());
    		map.put("areaName", IntentC.getAreaName());
    		map.put("deptId", IntentC.getDeptId());
    		map.put("deptName", IntentC.getDeptName());
    		map.put("rm", IntentC.getRm());
    		map.put("applyAmt", IntentC.getApplyAmt());
    		map.put("caseType", IntentC.getCaseType());
    		map.put("rmId", IntentC.getRmId());
    		map.put("compType", IntentC.getCompType());
    		map.put("sucProbability", IntentC.getSucProbability());
    		map.put("currency", IntentC.getCurrency());
    		map.put("foreignMoney", IntentC.getForeignMoney());
    		map.put("ifAdd", IntentC.getIfAdd());
    		map.put("addAmt", IntentC.getAddAmt());
    		map.put("custType", IntentC.getCustType());
//    	    		map.put("refuseReason", IntentC.getRefuseReason());
//    	    		map.put("remark", IntentC.getRemark());
    		this.setJson(map);
    		//save to PIPELINE
    	}
//    	}
    		
    	return new DefaultHttpHeaders("success");
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
  	
    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_INTENT_C WHERE ID='"+Long.parseLong(id)+"'", null);
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
