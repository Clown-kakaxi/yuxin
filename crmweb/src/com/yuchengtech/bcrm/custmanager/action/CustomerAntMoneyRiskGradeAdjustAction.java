package com.yuchengtech.bcrm.custmanager.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.wf.AMAdjust;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiGrade;
import com.yuchengtech.bcrm.service.AcrmFCiGradeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * @description : 反洗钱风险等级调整Action
 * @author : zhaolong
 * @date : 2016-1-22 下午2:29:00
 */
@Action("/customerAntMoneyRiskGradeAdjust")
public class CustomerAntMoneyRiskGradeAdjustAction extends CommonAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AcrmFCiGradeService service;
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性
	
	@Autowired
	public void init() {
		model = new AcrmFCiGrade();
		setCommonService(service);
		//needLog=true;
	}
	
	public void save() throws Exception{
	/*	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
    	String custId = request.getParameter("custId");
    	String custName = request.getParameter("custName");
    	String riskLevel = request.getParameter("riskLevel");
    	String lastUpdateUser = request.getParameter("lastUpdateUser");
//    	//update等级信息表对应记录
//    	service.batchUpdateByName(" update AcrmFCiGrade set custGradeId='"+custId+2014091701+"' ", new HashMap());
    	//删除客户原有等级信息
    	//service.batchUpdateByName(" delete from AcrmFCiGrade g where g.custId='"+custId+"' and g.custGradeType='01'", new HashMap());
    	//添加新的等级信息
    	AcrmFCiGrade grade = new AcrmFCiGrade();
    	Date now = new Date();
    	((AcrmFCiGrade) model).setCustId(custId);
    	((AcrmFCiGrade) model).setOrgCode(auth.getUnitId());
    	((AcrmFCiGrade) model).setOrgName(auth.getUnitName());
    	((AcrmFCiGrade) model).setCustGradeType("01");
    	((AcrmFCiGrade) model).setCustGrade(riskLevel);
    	((AcrmFCiGrade) model).setEvaluateDate(now);
    	((AcrmFCiGrade) model).setEffectiveDate(now);
    	((AcrmFCiGrade) model).setLastUpdateSys("CRM");
    	((AcrmFCiGrade) model).setLastUpdateUser(lastUpdateUser);
    	((AcrmFCiGrade) model).setLastUpdateTm(new Timestamp(System.currentTimeMillis()));
    	
    	service.save(model);*/
    	
		
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
   		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
   		
   		String custId = request.getParameter("custId");
    	String custName = request.getParameter("custName");
    	String riskLevel = request.getParameter("riskLevel");
    	//String risklevel = request.getParameter("fxqRiskLevel");
    	String oldRiskLevel = request.getParameter("oldFxqRiskLevel");
    	String custType=request.getParameter("custType");
    	String instruction=request.getParameter("instruction");

		int times = 0;
   		
   		//list办理中流程，list2已办结流程
   		List list = service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORD where instanceid like '%ANTMONEY%"+custId+"%'");
   		List list2 = service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from WF_MAIN_RECORDEND where instanceid like '%ANTMONEY2_"+custId+"%'");
   		Map<String, Object> map1 = new HashMap<String, Object>();
   		if(list!= null && list.size() > 0){
   			//如果有办理中流程，不让再提交
   			//map1.put("existTask", "existTask");
   			//如果有办理中流程，不让再提交
   	    	response.getWriter().write("{\"existTask\":\"existTask\"}");
   	    	response.getWriter().flush();
   		}else{
   			if(list2!= null && list2.size() > 0){
   				times = list.size();
   			}
   				
   				Map<String, Object> paramMap = new HashMap<String, Object>();
   				List<?> list101 = auth.getRolesInfo();
   				for (Object m : list101) {
   					Map<?, ?> map = (Map<?, ?>) m;// map自m引自list，ROLE_CODE为键, R000为值
   					paramMap.put("role", map.get("ROLE_CODE"));
   					paramMap.put("instruction", instruction);
   				}
	    		//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息  0是标示，1是用户ID,2调整前的的风险等级，3是调整后的风险等级，4 最后更新人ID，5客户类型（流程处理中显示客户信息分辨是个人还是企业）   6.是审核结束表中的数据总数 cust_type 
	    		String instanceid = "ANTMONEY2_"+custId+"_"+oldRiskLevel+"_"+riskLevel+"_"+auth.getUserId()+"_"+custType+"_"+times;
	    		String jobName = "反洗钱风险等级调整_"+custName;//自定义流程名称
	    		service.initWorkflowByWfidAndInstanceid("133", jobName, paramMap,
	    				instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
	    		//添加反洗钱等级调整说明记录到临时表中
	    		service.updatePotCusInfo("insert into OCRM_F_CHANGE_GRADE_TEMP(INSTANCEID,INSTRUCTION,CUST_ID) values('"+instanceid+"','"+instruction+"','"+custId+"')");
	    		
	    		
	    		response.getWriter().write("{\"instanceid\":\""+instanceid+"\"}");
   	    		response.getWriter().flush();
   	   			
		
	}
}
}
