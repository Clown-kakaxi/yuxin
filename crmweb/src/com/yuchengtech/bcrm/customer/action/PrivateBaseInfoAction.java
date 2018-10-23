package com.yuchengtech.bcrm.customer.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.service.PrivateBaseInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 
* @ClassName: PrivateBaseInfoAction 
* @Description: 对私客户基本信息保存
* @author wangmk1 
* @date 2014-7-29 
*
 */
@Action("/privateBaseInfo")
public class PrivateBaseInfoAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PrivateBaseInfoService privateBaseInfoService;
	/**
	 * 修改对私客户基本信息
	 */
	@Autowired
	public void init() {
		model = new AcrmFCiPerson();
		setCommonService(privateBaseInfoService);
	}
	/**
	 * 对私客户基本信息调整历史
	 * @return
	 * @throws Exception
	 */
    public String save() throws Exception{
    	try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String s1 = request.getParameter("perModel");
		    JSONArray jarray = JSONArray.fromObject(s1);
		    privateBaseInfoService.bathsave(jarray);//去 HIS 存表方向
    	}catch(Exception e){
    		e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
    	}
        return "success";
    }
    public String savebusi(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String bb = request.getParameter("BBB");
        String custId=request.getParameter("custId");
        JSONObject bb1 = JSONObject.fromObject(bb);
	    privateBaseInfoService.savebusi(bb1,custId);
		return "success";
    }
  //保存财务信息块面  数据
    public String savefin(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String cc = request.getParameter("CCC");
        String custId=request.getParameter("custId");
        JSONObject cc1 = JSONObject.fromObject(cc);
	    privateBaseInfoService.savefin(cc1,custId);
		return "success";
    }
    public String savaImage(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String newImageName= request.getParameter("_tempFileName");
        String custId= request.getParameter("custId");
        privateBaseInfoService.saveImage(newImageName,custId);
		return "success";
    }
   public void setHasPhoto(){
   	ActionContext ctx = ActionContext.getContext();
    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    String custId= request.getParameter("custId");
    privateBaseInfoService.setHasPhoto(custId);
   }
   
   /**
	 * 发起审批工作流(对私视图基本信息)
	 */
	public void initFlowBI() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
	   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   	 
	   	 String custId = request.getParameter("custId");
	   	 String custName = request.getParameter("custName");
	   	 
	   	 String s1 = request.getParameter("perModel");
	   	 String s2 = request.getParameter("perModel2");
	   	 String s3 = request.getParameter("perModel3");
	   	 
	   	 JSONArray jarray = JSONArray.fromObject(s1);
	   	 JSONArray jarray2 = JSONArray.fromObject(s2);
	   	 JSONArray jarray3 = JSONArray.fromObject(s3);
	   	 
	   	 int p = privateBaseInfoService.judge("CI_"+custId);//判断该客户信息是否已经在审批中
	   	 if(p>0){
	   		 throw new BizException(1, 0, "1002", "客户未放行,正在复核流程中,...");
	   	 }
		 
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 String nextNode = "68_a4";
		 List<?> list = auth.getRolesInfo();//获得的角色信息是当前角色
		 for(Object m:list){
			Map<?, ?> map = (Map<?, ?>)m;//map自m引自list，ROLE_CODE为键, R000为值
			paramMap.put("role", map.get("ROLE_CODE"));
			if("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE"))){//ARMRM个金法金
				nextNode = "68_a4";
				continue ;
			}else if("R300".equals(map.get("ROLE_CODE"))){//OP经办
				nextNode = "68_a5";
				continue ;
			}else if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))
					|| "R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){
				nextNode = "68_a7";
				continue ;
			}
		 }
		 
		 String flag = DateUtils.currentTimeMillis();
	   	 String instanceid = "CI_"+custId+"_"+flag;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		 String jobName = "对私基本信息修改_"+custName;//自定义流程名称
		 //以下方法调用流程引擎..
		 privateBaseInfoService.bathsave2(jarray,jarray2,jarray3,new Date(),flag);
		 privateBaseInfoService.initWorkflowByWfidAndInstanceid("68", jobName, paramMap, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		 
		 response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"68_a3\",\"nextNode\":\""+nextNode+"\"}");
		 response.getWriter().flush();
	}
}
