package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibm.db2.jcc.sqlj.d;
import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCaC;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeCaEService;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 中小企客户营销流程 -  文件收集阶段  
 * @author denghj
 * @since 2015-08-07
 */
@SuppressWarnings("serial")
@Action("/smeCaE")
public class SmeCaEAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    
    @Autowired
    private OcrmFCiSmeCaEService service;
    
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
				"c.USER_ID,c.CHECK_STAT,c.RECORD_DATE,c.UPDATE_DATE,c.PIPELINE_ID,c.RM_ID,c.COMP_TYPE," +
				"c.IF_COCO,c.SUC_PROBABILITY,c.HARD_REMARK,c.FOREIGN_MONEY," +
				"c.FIRST_DOCU_DATE,c.GET_DOCU_DATE,c.SEND_DOCU_DATE,c.CA_DATE_S,c.CA_PP,c.GRADE_PERSECT,c.RM_REPLY_COCO,c.CUST_TYPE,c.IF_SUMBIT_CO,c.XD_CA_DATE,"+
				"DECODE(c.CURRENCY,'1','AUD','2','CAD','3','CHF','5','EUR','6','GBP','7','HKD','8','JPY','9','NZD','10','RMB','11','SGD', '12','TWD','13','USD',c.CURRENCY) as CURRENCY," +
    			"a.user_name  from OCRM_F_CI_MKT_CA_C c " +
    			"left join admin_auth_account a on c.user_id = a.account_name  where 1=1  and (a.belong_busi_line = '5' or a.belong_busi_line ='0')";
    	
    	String id = request.getParameter("id");
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	if( id != null && !"".equals(id)){//流程查询使用
    		sb.append(" and c.id = '"+id+"'");
    	}else{
    		sb.append(" and  ((c.IF_THIRD_STEP  not  like '1' and c.IF_THIRD_STEP not like '99') or c.IF_THIRD_STEP is null) and((c.IF_SUMBIT_CO not like '1'  and c.IF_SUMBIT_CO not like '3') or c.IF_SUMBIT_CO is null) ");
    		setPrimaryKey("c.ID desc ");
    	}
        	SQL=sb.toString();
        	datasource = ds;
        	setPrimaryKey("c.IF_THIRD_STEP desc, c.RECORD_DATE asc");
        	configCondition("IF_THIRD_STEP","=","IF_THIRD_STEP",DataType.String);
        	configCondition("IF_SUMBIT_CO","=","IF_SUMBIT_CO",DataType.String);
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
    		configCondition("SUC_PROBABILITY","=","SUC_PROBABILITY",DataType.String);
    		configCondition("GRADE_PERSECT","=","GRADE_PERSECT",DataType.String);
	}

    
    
    public DefaultHttpHeaders save() throws Exception{   	
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	OcrmFCiMktCaC CaC = (OcrmFCiMktCaC)model;
    	CaC.setUserId(auth.getUserId());
    	CaC.setCheckStat("2");
    	String ifSumbitCo = CaC.getIfSumbitCo();
    	String caseType = CaC.getCaseType();
    	if("16".equals(caseType)){
    		if(StringUtils.isNotEmpty(ifSumbitCo)){
    			if("0".equals(ifSumbitCo)){
    				CaC.setIfThirdStep("0");
    			}else if("1".equals(ifSumbitCo)){
    				CaC.setIfThirdStep("");
    			}else if("2".equals(ifSumbitCo)){
    				CaC.setIfThirdStep("2");
    			}
    		}
    	}
    	if(CaC.getId()==null){//新增
    		CaC.setRecordDate(new Date());//设置记录日期
    		if((CaC.getPipelineId())==null){//设置PipelineId为空的时候，表示此次保存的是从合作意向阶段新增数据
    			CaC.setPipelineId((new Date()).getTime());
    		}///设置PipelineId必须保证其唯一性
    		service.save(model);
    	}else{//修改
    		CaC.setUpdateDate(new Date());
    		service.save(model);    		
    		 Map<String,Object> map=new HashMap<String,Object>();
	    	//根据字段IF_PIPELINE判断是否转入PIPELINE
	    	String flag1=CaC.getIfThirdStep();
	    	if("1".equals(flag1)){//判断是否进入下一阶段，当需要进入下一阶段，把map的数据添加到下一阶段
	    		map.put("caId", CaC.getId());
	    		map.put("pipelineId", CaC.getPipelineId());
	    		map.put("custId", CaC.getCustId());
	    		map.put("custName", CaC.getCustName());
	    		map.put("areaId", CaC.getAreaId());
	    		map.put("areaName", CaC.getAreaName());
	    		map.put("deptId", CaC.getDeptId());
	    		map.put("deptName", CaC.getDeptName());
	    		map.put("rm", CaC.getRm());
	    		map.put("rmId", CaC.getRmId());
	    		map.put("applyAmt", CaC.getApplyAmt());
	    		map.put("caseType", CaC.getCaseType());
	    		map.put("compType", CaC.getCompType());
	    		map.put("gradeLevel", CaC.getGradeLevel());
	    		map.put("ifAdd", CaC.getIfAdd());
	    		map.put("addAmt", CaC.getAddAmt());
	    		map.put("currency", CaC.getCurrency());
	    		map.put("foreignMoney", CaC.getForeignMoney());
//	    		map.put("firstDocuDate", CaC.getFirstDocuDate());
//	    		map.put("getDocuDate", CaC.getGetDocuDate());
//	    		map.put("sendDocuDate", CaC.getSendDocuDate());
//	    		map.put("caDateS", CaC.getCaDateS());
	    		map.put("caPp", CaC.getCaPp());
	    		map.put("gradePersect", CaC.getGradePersect());
	    		map.put("rmReplyCoco", CaC.getRmReplyCoco());
	    		map.put("custType", CaC.getCustType());
//	    		map.put("ifSumbitCo", CaC.getIfSumbitCo());
	    		map.put("xdCaDate", DateFormat.getDateInstance(DateFormat.MEDIUM).format(CaC.getXdCaDate()));
	    		this.setJson(map);
	    		//save to PIPELINE
	    	}	
    	}  
    	if(StringUtils.isNotEmpty(caseType) && StringUtils.isNotEmpty(ifSumbitCo)){
    		if("16".equals(caseType) && "1".equals(ifSumbitCo)){
    			String custName = CaC.getCustName();
    			String caseTypeStr = "";
    			if("16".equals(caseType)){
    				caseTypeStr = "抵押贷款";
    			}
    			sendEmail(custName,caseTypeStr);
    		}
    	}
    	return new DefaultHttpHeaders("success");
    }
    
	@SuppressWarnings("unchecked")
	public void sendEmail(String custName,String caseTypeStr){
    	String sb = " select t2.role_name,t.user_name,t.account_name,t.user_code,t.email  from admin_auth_account t  " +
				" left join ADMIN_AUTH_ACCOUNT_ROLE t1 on t.id=t1.account_id " +
    			" left join ADMIN_AUTH_ROLE t2 on t1.role_id=t2.id " +
				" where t2.role_code in ('R121') and t.email is not null ";
		List<Object[]> list = service.getBaseDAO().findByNativeSQLWithNameParam(sb, null);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] object = list.get(i);
				String email = (String)object[4];
				if(StringUtils.isNotEmpty(email)){
					try {
						SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
						SimpleDateFormat sdfM = new SimpleDateFormat("MM");
						SimpleDateFormat sdfD = new SimpleDateFormat("dd");
						String year = sdfY.format(new Date());
						String month = sdfM.format(new Date());
						String date = sdfD.format(new Date());
						String nowDate = year + "年" + month + "月" + date + "日";
						String body = auth.getUsername() + "经理于" + nowDate + "将" + custName + "的" + caseTypeStr +"案件提交至风险管理处，请知悉。";
						MailClient.getInstance().sendMsg(email, "送案通知", body);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
    }
    
    public void batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	
    	List<List> pList = new ArrayList<List>();
    	for(String id : ids){
    		List list=service.getBaseDAO().findByNativeSQLWithNameParam("select pipeline_id from OCRM_F_CI_MKT_CA_C WHERE ID='"+Long.parseLong(id)+"'", null);
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
