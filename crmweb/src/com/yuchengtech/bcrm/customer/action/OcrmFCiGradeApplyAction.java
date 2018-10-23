package com.yuchengtech.bcrm.customer.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGradeApply;
import com.yuchengtech.bcrm.customer.service.OcrmFCiGradeApplyService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * @author huwei
 * 客户评级申请
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFCiGradeApply-info")
public class OcrmFCiGradeApplyAction extends CommonAction{
	
	@Autowired
	private OcrmFCiGradeApplyService service;
	@Autowired
	public void init() {
		model = new OcrmFCiGradeApply();
		setCommonService(service);
	}
	
	public HttpHeaders indexPage() throws Exception {
		try {
			StringBuilder sb = new StringBuilder(
					"select c from OcrmFCiGradeApply c where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			 if(request.getParameter("start")!=null)
			 start = new Integer(request.getParameter("start")).intValue();
			 if(request.getParameter("limit")!=null)
			 limit = new Integer(request.getParameter("limit")).intValue();
			this.setJson(request.getParameter("condition"));

			// 获取ID
			if (request.getParameter("id") != null) {
				sb.append(" and c.id = " + request.getParameter("id"));
			}

			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					sb.append(" and c." + key + " = :" + key);
					values.put(key, this.getJson().get(key));
				}
			}
			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 发起工作流
	 * */
	public void initFlow() throws Exception{

		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();
		String orgId = auth.getUnitId();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
		.get(ServletActionContext.HTTP_REQUEST);
//		String instanceid = request.getParameter("instanceid");
		String custName = request.getParameter("custname");
		//String custMgrName = auth.getCname();
		//system.out.printlnln("*********initFlow run************");
//system.out.printlnntln("*********工作流ID，instanceid："+instanceid+"*********");//system.out.printlnrintln("*********用户ID，userid："+userId+"*********"//system.out.println.println("*********机构ID，orgid："+orgId+"*********");
//		EVO vo=new EVO();
//    	vo.setCurrentUserID(userId);
//    	vo.setOrgid(orgId);
//    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date1 = format1.format(new java.util.Date()).toString();
//		date1=date1.trim();date1=date1.replace(" ", "");
//    	vo.setInstanceID("GA_"+instanceid+"_"+date1);//设置当前任务实例号
////    	vo.setWFSign("cust_class");  //设置任务标识，对应流程定制中的任务标识
//    	vo.setWFID("12");    //设置任务的文件名称（对应任务标识，如存储kevinmxt任务的文件名称为2.xml，就设置为2）
//    	vo.setJobName("客户评级申请:"+custName);  //  设置工作名称，显示在待办列表中
//    	vo=WorkFlowClient.getInstance().initializeWFWholeDocUNID(vo);   //发起任务
//    	WorkFlowClient.getInstance().wfSaveJob(vo);
////    	Long idLong = new Long(Integer.parseInt(instanceid));
////    	customerManagerEstimateService.setStatus(idLong);
		String requestId =  request.getParameter("instanceid");
		String instanceid = "REQ_"+requestId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  String date = format.format(new java.util.Date()).toString();
		  String jobName = custName+"_客户分级_"+date;//自定义流程名称
		  service.initWorkflowByWfidAndInstanceid("12", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		  String jql="update OcrmFCiGradeApply c set c.status='等待审核' where c.id="+requestId;
		  Map<String,Object> values=new HashMap<String,Object>();
		  service.batchUpdateByName(jql, values);
	}
	
}
