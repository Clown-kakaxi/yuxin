package com.yuchengtech.bcrm.customer.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrg;
import com.yuchengtech.bcrm.customer.service.PublicBaseInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
* @ClassName: PublicBaseInfoAction 
* @Description: 对公客户基本信息保存
* @author wangmk1 
* @date 2014-8-7 
*
 */
@Action("/publicBaseInfo")
public class PublicBaseInfoAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PublicBaseInfoService publicBaseInfoService;
	
	@Autowired
	public void init() {
		model = new AcrmFCiOrg();
		setCommonService(publicBaseInfoService);
	}
	
	/**
	 * 发起审批工作流(对私视图基本信息)
	 */
	public void initFlowBF() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
	   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   	 
	   	 String custId = request.getParameter("custId");
	   	 String custName = request.getParameter("custName");
	   	 
	   	 String s1 = request.getParameter("perModel");
	   	 JSONArray jarray = JSONArray.fromObject(s1);
	     
	   	 //验证是否已经提交审核,验证不通过会抛出异常
	   	 publicBaseInfoService.judge("CI_"+custId);//判断该客户信息是否已经在审批中
	   	 
	   	 String flag = DateUtils.currentTimeMillis();// 修改标识更改为毫秒级
	   	 String instanceid = "CI_"+custId+"_"+flag;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		 String jobName = "对公基本信息修改_"+custName;//自定义流程名称
		 
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 String nextNode = "73_a4";
		 List<?> list = auth.getRolesInfo();
		 for(Object m:list){
			Map<?, ?> map = (Map<?, ?>)m;//map自m引自list，ROLE_CODE为键, R000为值
			paramMap.put("role", map.get("ROLE_CODE"));
			if("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE"))){//ARMRM个金法金
				nextNode = "73_a4";
				continue ;
			}else if("R300".equals(map.get("ROLE_CODE"))){//OP经办
				nextNode = "73_a5";
				continue ;
			}else if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
					|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
				nextNode = "73_a7";
				continue ;
			}
		 }
		 
		 publicBaseInfoService.bathsave(jarray,new Date(),flag);
		 publicBaseInfoService.initWorkflowByWfidAndInstanceid("73", jobName, paramMap, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		 
		 response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"73_a3\",\"nextNode\":\""+nextNode+"\"}");
		 response.getWriter().flush();
	}
}
