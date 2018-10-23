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

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovlC;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeApprovlEService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 中小企客户营销流程 -  核批阶段 
 * @author denghj
 * @since 2015-08-07
 */
@SuppressWarnings("serial")
@Action("/smeApprovlE")
public class SmeApprovlEAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiSmeApprovlEService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktApprovlC();  
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
    	String sqlapp = " select c.ID,c.CALL_ID,c.SC_ID,c.CUST_ID,c.CUST_NAME,c.GROUP_NAME,c.AREA_ID,c.AREA_NAME,c.DEPT_ID," +
				"c.DEPT_NAME,c.RM,c.APPLY_AMT,c.COMP_TYPE,c.GRADE_LEVEL,c.CASE_TYPE,c.IF_ADD,c.ADD_AMT," +
				"c.XZ_CA_DATE,c.XZ_CA_FORM,c.USE_DATE_P,c.SP_LEVEL,c.CC_OPEN_DATE,c.IF_SURE,c.INSURE_AMT," +
				"c.INSURE_FORM,c.DELINE_REASON,c.IF_FIFTH_STEP,c.USER_ID,c.CHECK_STAT,c.RECORD_DATE," +
				"c.UPDATE_DATE,c.RESON_REMARK,c.PIPELINE_ID,c.RM_ID,c.IF_BACK,c.CO,c.FOREIGN_MONEY,c.INSURE_MONEY," +
				"c.CHECK_PROGRESS,c.SC_DATE,c.CC_DATE,c.LEVEL1_DATE,c.LEVEL2_DATE,c.LEVEL34_DATE,c.CHECK_DATE,c.CUST_TYPE,c.TRAIL_COMPLETION_DATE,c.REFUSE_REASON,c.REFUSE_REASON_REMARK," +
				"DECODE(c.INSURE_CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD','12','TWD','13','USD',c.INSURE_CURRENCY) as INSURE_CURRENCY," +
				"DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD','12','TWD','13','USD',c.CURRENCY) as CURRENCY," +
				"a.user_name  from OCRM_F_CI_MKT_APPROVL_C c " +
    			"left join admin_auth_account a on c.user_id = a.account_name  where 1=1  and (a.belong_busi_line = '5' or a.belong_busi_line ='0')";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    	}else{
    		List<?> list = auth.getRolesInfo();
    		sb.append(" and  (c.IF_FIFTH_STEP  not  in ('1','5','99') or (c.IF_FIFTH_STEP is null) or (c.if_fifth_step = '1' and c.if_sure = '0') ) ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.IF_FIFTH_STEP desc, c.RECORD_DATE asc");
        	configCondition("IF_FIFTH_STEP","=","IF_FIFTH_STEP",DataType.String);
        	configCondition("CUST_ID","=","CUST_ID",DataType.String);
        	configCondition("PIPELINE_ID","=","PIPELINE_ID",DataType.String);
    		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("AREA_NAME","like","AREA_NAME",DataType.String);
    		configCondition("DEPT_ID","=","DEPT_ID",DataType.String);
    		configCondition("DEPT_NAME","=","DEPT_NAME",DataType.String);
    		configCondition("RM","like","RM",DataType.String);
    		configCondition("RM_ID","=","RM_ID",DataType.String);
    		configCondition("APPLY_AMT","=","APPLY_AMT",DataType.String);
    		configCondition("CASE_TYPE","=","CASE_TYPE",DataType.String);
    		configCondition("CO","like","CO",DataType.String);
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	OcrmFCiMktApprovlC approvlC =(OcrmFCiMktApprovlC)model;
    	approvlC.setUserId(auth.getUserId());
    	approvlC.setCheckStat("2");
    	if(approvlC.getId()==null){//新增
    		Map<String,Object> value=new HashMap<String,Object>();
    		List list	=service.findByJql("select a from OcrmFCiMktApprovlC a where a.pipelineId = '"+approvlC.getPipelineId()+"'", value);
    		if(list.size()>0){//查询审查阶段是否有数据，如果有数据，则是二次进入，否则为新增；
    			OcrmFCiMktApprovlC temp=(OcrmFCiMktApprovlC)list.get(0);
    			approvlC.setId(temp.getId());
    			approvlC.setIfBack("0");//
    		}
    		approvlC.setRecordDate(new Date());//设置记录日期
    		if((approvlC.getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			approvlC.setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(approvlC);
    	}else{//修改
    		//根据字段IfFifthStep判断是否转入IfFifthStep
    		String flag1=approvlC.getIfFifthStep();
    		String flag2 = approvlC.getIfSure();
    		approvlC.setUpdateDate(new Date());
    		service.save(approvlC);
    		
    		
    		 Map<String,Object> map=new HashMap<String,Object>();
    	    	
	    	
	    	if("1".equals(flag1) && "1".equals(flag2)){//判断是否进入下一阶段
	    		map.put("hpId", approvlC.getId());
	    		map.put("pipelineId", approvlC.getPipelineId());
	    		map.put("custId", approvlC.getCustId());
	    		map.put("custName", approvlC.getCustName());
	    		map.put("areaId", approvlC.getAreaId());
	    		map.put("areaName", approvlC.getAreaName());
	    		map.put("deptId", approvlC.getDeptId());
	    		map.put("deptName", approvlC.getDeptName());
	    		map.put("rm", approvlC.getRm());
	    		map.put("rmId", approvlC.getRmId());
	    		map.put("applyAmt", approvlC.getApplyAmt());
	    		map.put("caseType", approvlC.getCaseType());
	    		map.put("compType", approvlC.getCompType());
	    		map.put("gradeLevel", approvlC.getGradeLevel());
	    		map.put("ifAdd", approvlC.getIfAdd());
	    		map.put("addAmt", approvlC.getAddAmt());
	    		map.put("currency", approvlC.getCurrency());
	    		map.put("insureAmt", approvlC.getInsureAmt());
	    		map.put("insureCurrency", approvlC.getInsureCurrency());
	    		map.put("insureMoney", approvlC.getInsureMoney());
	    		map.put("custType", approvlC.getCustType());
//	    		map.put("trailCompletionDate", approvlC.getTrailCompletionDate());
//	    		map.put("refuseReason", approvlC.getRefuseReason());
//	    		map.put("refuseReasonRemark", approvlC.getRefuseReasonRemark());
	    		this.setJson(map);
	    	}
	    	if("5".equals(flag1)){//判断是否退回信用审查阶段
	    		service.backSC(Long.parseLong(approvlC.getScId()),approvlC.getId().toString());
	    	}
    	}
    	
    	
    	return new DefaultHttpHeaders("success").disableCaching();
    	
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");

    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_APPROVL_C WHERE ID='"+Long.parseLong(id)+"'", null);
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
    public void changeStat(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
    	service.changeStat(id);
    }

}
