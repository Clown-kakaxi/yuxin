package com.yuchengtech.bcrm.customer.potentialMkt.action;

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
import com.yuchengtech.bcrm.customer.potentialMkt.service.OcrmFCiMktCheckCService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 企商金客户营销流程 -  信用审查  luyy  2014-07-25
 */

@SuppressWarnings("serial")
@Action("/mktCheckC")
public class MktCheckCAction  extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiMktCheckCService service;
    
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
				"DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD','12','TWD','13','USD',c.CURRENCY) as CURRENCY," +
				"a.user_name,(trunc(sysdate, 'DD') - c.record_date) TREAMENT_DAYS  " +
				"from OCRM_F_CI_MKT_CHECK_C c left join admin_auth_account a on c.user_id = a.account_name  where 1=1 ";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
//    		addOracleLookup("CASE_TYPE", "CASE_TYPE");
//    		addOracleLookup("IF_ADD", "IF_FLAG");
//    		addOracleLookup("IF_FOURTH_STEP", "IF_FLAG");
//    		addOracleLookup("GRADE_LEVEL", "GRADE_PERSECT");
//    		addOracleLookup("SP_LEVEL", "SP_LEVEL");
    	}else{
    		//sb.append("and c.user_id='"+auth.getUserId()+"' ");
    		List<?> list = auth.getRolesInfo();
//   		 	for(Object m:list){
//   		 		Map<?, ?> map = (Map<?, ?>)m;//map自m引自list，ROLE_CODE为键, R000为值
//   		 		if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
//   					|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
//   		 			sb.append("and c.user_id in (select t.account_name "+
//   		 					"from admin_auth_account t "+
//   		 					"where t.org_id in (select t1.org_id "+
//   		 					"from admin_auth_account t1 "+
//   		 					"where t1.account_name = '"+auth.getUserId()+"')) ");
//   		 			continue ;
//   		 		}
//   		 }
    		sb.append(" and  (c.IF_FOURTH_STEP  not  in ('1','5','99') or c.IF_FOURTH_STEP is null ) ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.IF_FOURTH_STEP desc, c.RECORD_DATE asc");
        	configCondition("IF_FOURTH_STEP","=","IF_FOURTH_STEP",DataType.String);
        	configCondition("CUST_ID","=","CUST_ID",DataType.String);
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
    		List list	=service.findByJql("select a from OcrmFCiMktCheckC a where a.pipelineId = '"+checkC.getPipelineId()+"' order by a.recordDate asc", value);
    		if(list.size()>0){//查询审查阶段是否有数据，如果有数据，则是二次进入，否则为新增；
    			OcrmFCiMktCheckC temp=(OcrmFCiMktCheckC)list.get(0);
    			checkC.setId(temp.getId());
    			checkC.setIfBack("0");//若为2次进入，则将原记录ifBack改为否
    			checkC.setRecordDate(temp.getRecordDate());//若为2次进入，则当前记录日期recordDate设置为原记录日期
    		}else{//没有数据即为新增    			
        			checkC.setRecordDate(null);        		
    		}    		  		
    		if((checkC.getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			checkC.setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(checkC);
    	}else{//修改
    		//根据字段IfFourthStep判断是否转入IfFourthStep
    		if(checkC.getIfFourthStep() == "5"){
    			checkC.setRecordDate(null);
    		}else{
    			checkC.setRecordDate(new Date());
    		}
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
    	    		this.setJson(map);
    	    	}
    	    	if("5".equals(flag1)){//判断是否退回CA阶段
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
//    	for(String id : ids){
//    		service.batchUpdateByName(" delete from OcrmFCiMktCheckC g where g.id='"+Long.parseLong(id)+"'", new HashMap());
//    	}
    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_CHECK_C WHERE ID='"+Long.parseLong(id)+"'", null);
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
    //把IF_FOURTH_STEP 字段的撤案，退案状态修改为否；
    public void changeStat(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
    	service.changeStat(id);
    }
}