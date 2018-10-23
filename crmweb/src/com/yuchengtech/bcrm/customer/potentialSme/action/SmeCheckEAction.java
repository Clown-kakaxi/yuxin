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
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCheckC;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeCheckEService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 中小企客户营销流程 -  信用审查  
 * @author denghj
 * @since 2015-08-07
 */
@SuppressWarnings("serial")
@Action("/smeCheckE")
public class SmeCheckEAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiSmeCheckEService service;
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFCiMktCheckC();  
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
    	String sqlapp = " select c.ID,c.CALL_ID,c.CA_ID,c.CUST_ID,c.CUST_NAME,c.GROUP_NAME,c.AREA_ID,c.AREA_NAME,c.DEPT_ID," +
				"c.DEPT_NAME,c.RM,c.APPLY_AMT,c.COMP_TYPE,c.GRADE_LEVEL,c.CASE_TYPE,c.IF_ADD,c.ADD_AMT," +
				"c.XD_CA_DATE,c.CA_FORM,c.QA_DATE,c.RM_DATE,c.CC_DATE,c.XS_CC_DATE,c.RM_C_DATE,c.CO,c.MEMO," +
				"c.SP_LEVEL,c.IF_FOURTH_STEP,c.USER_ID,c.CHECK_STAT,c.RECORD_DATE,c.UPDATE_DATE,c.REFUSE_REASON," +
				"c.REASON_REMARK,c.RM_ID,c.IF_BACK,c.PIPELINE_ID,c.FOREIGN_MONEY,c.CHECK_PROGRESS,c.ADD_CASE_CONTENT,c.ADD_CASE_DATE," +
				"c.CUST_TYPE,c.CA_FINISH_DATE,c.TO_CO_DATE,c.DOCU_CHECK,c.VISIT_FACTORY_DATE,c.REQUIRE_CASE_DATE,c.REFUSE_DATE,"+
				"DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD','12','TWD','13','USD',c.CURRENCY) as CURRENCY," +
				"a.user_name  from OCRM_F_CI_MKT_CHECK_C c " +
    			"left join admin_auth_account a on c.user_id = a.account_name  where 1=1 and (a.belong_busi_line = '5' or a.belong_busi_line ='0') ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    	}else{
    		List<?> list = auth.getRolesInfo();
    		sb.append(" and  (c.IF_FOURTH_STEP  not  in ('1','5','99') or c.IF_FOURTH_STEP is null ) ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.IF_FOURTH_STEP desc, c.RECORD_DATE asc");
        	configCondition("IF_FOURTH_STEP","=","IF_FOURTH_STEP",DataType.String);
        	configCondition("CUST_ID","=","CUST_ID",DataType.String);
        	configCondition("PIPELINE_ID","=","PIPELINE_ID",DataType.String);
    		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
    		configCondition("AREA_NAME","like","AREA_NAME",DataType.String);
    		configCondition("DEPT_ID","=","DEPT_ID",DataType.String);
    		configCondition("DEPT_NAME","=","DEPT_NAME",DataType.String);
    		configCondition("RM","like","RM",DataType.String);
    		configCondition("RM_ID","=","RM_ID",DataType.String);
    		configCondition("CASE_TYPE","=","CASE_TYPE",DataType.String);
    		configCondition("APPLY_AMT","=","APPLY_AMT",DataType.String);
    		configCondition("CO","like","CO",DataType.String);
	}

    
    
    public DefaultHttpHeaders save() throws Exception{
    	OcrmFCiMktCheckC checkC =(OcrmFCiMktCheckC)model;
    	checkC.setUserId(auth.getUserId());
    	checkC.setCheckStat("2");
    	if(checkC.getId()==null){//新增
    		Map<String,Object> value=new HashMap<String,Object>();
    		List list	=service.findByJql("select a from OcrmFCiMktCheckC a where a.pipelineId = '"+checkC.getPipelineId()+"'", value);
    		if(list.size()>0){//查询审查阶段是否有数据，如果有数据，则是二次进入，否则为新增；
    			OcrmFCiMktCheckC temp=(OcrmFCiMktCheckC)list.get(0);
    			checkC.setId(temp.getId());
    			checkC.setIfBack("0");//
    		}
    		checkC.setRecordDate(new Date());//设置记录日期
    		if((checkC.getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			checkC.setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(checkC);
    	}else{//修改
    		//根据字段IfFourthStep判断是否转入IfFourthStep
    		String flag1=checkC.getIfFourthStep();
    		checkC.setUpdateDate(new Date());
    		service.save(checkC);
    		   		
    		Map<String,Object> map=new HashMap<String,Object>();    	    	    	    	
	    	if("1".equals(flag1)){//判断是否进入下一阶段
	    		map.put("scId", checkC.getId());
	    		map.put("pipelineId", checkC.getPipelineId());
	    		map.put("custId", checkC.getCustId());
	    		map.put("custName", checkC.getCustName());
	    		map.put("areaId", checkC.getAreaId());
	    		map.put("areaName", checkC.getAreaName());
	    		map.put("deptId", checkC.getDeptId());
	    		map.put("deptName", checkC.getDeptName());
	    		map.put("rm", checkC.getRm());
	    		map.put("rmId", checkC.getRmId());
	    		map.put("applyAmt", checkC.getApplyAmt());
	    		map.put("caseType", checkC.getCaseType());
	    		map.put("compType", checkC.getCompType());
	    		map.put("gradeLevel", checkC.getGradeLevel());
	    		map.put("ifAdd", checkC.getIfAdd());
	    		map.put("addAmt", checkC.getAddAmt());
	    		map.put("spLevel", checkC.getSpLevel());
	    		map.put("co", checkC.getCo());
	    		map.put("currency", checkC.getCurrency());
	    		map.put("foreignMoney", checkC.getForeignMoney());
	    		map.put("custType", checkC.getCustType());
	    		map.put("checkProgress", checkC.getCheckProgress());
	    		this.setJson(map);
	    	}
	    	if("5".equals(flag1)){//判断是否退回文件收集阶段
	    		service.backCA(Long.parseLong(checkC.getCaId()),checkC.getId().toString());
	    	}
    	}
    	
    	
    	return new DefaultHttpHeaders("success");
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_CHECK_C WHERE ID='"+Long.parseLong(id)+"'", null);
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
    //把IF_FOURTH_STEP 字段的撤案，退案状态修改为否；
    public void changeStat(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
    	service.changeStat(id);
    }

}
