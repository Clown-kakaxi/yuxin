package com.yuchengtech.bcrm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.model.OcrmFCiAlianceProgram;
import com.yuchengtech.bcrm.service.AlianceProgramInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 联盟商管理ACTION
 * @author hujun
 *	2014-06-23
 */
@SuppressWarnings("serial")
@Action("/alianceProgramInfoAction")
public class AlianceProgramInfoAction  extends CommonAction{
    @Autowired
    private AlianceProgramInfoService alianceProgramInfoService ;
    @Autowired
	public void init(){
	  	model = new OcrmFCiAlianceProgram(); 
		setCommonService(alianceProgramInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
    
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
  //（自定义）批量删除
    public String batchDestroy(){
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql="delete from OcrmFCiAlianceProgram c where c.id in ("+idStr+")";
			Map<String,Object> values=new HashMap<String,Object>();
			alianceProgramInfoService.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
	        return "success";
    }
    //退出联盟商
    public String outAliance(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		String reason=request.getParameter("reason");
		alianceProgramInfoService.outAliance(id,reason);
    	return "success";
    }
    /**
   	 * 发起工作流
   	 * */
   	public void initFlow() throws Exception{
   	  	ActionContext ctx = ActionContext.getContext();
   		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   		String requestId =  request.getParameter("instanceid");
   		String name =  request.getParameter("name");
   		String instanceid = "AP_"+requestId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
   		String jobName = "联盟商_"+name;//自定义流程名称
   		alianceProgramInfoService.initWorkflowByWfidAndInstanceid("21", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
   		Long idLong = new Long(Integer.parseInt(requestId));
   		alianceProgramInfoService.setState(idLong);
   		
   	  String nextNode = "21_a4";
		List list = auth.getRolesInfo();
		for(Object m:list){
			Map map = (Map)m;
			if("R105".equals(map.get("ROLE_CODE"))||"R104".equals(map.get("ROLE_CODE"))||"R106".equals(map.get("ROLE_CODE"))||"R107".equals(map.get("ROLE_CODE"))){//总行
				nextNode = "21_a9";
				break ;
			}else if("R201".equals(map.get("ROLE_CODE"))){//区域行长
				nextNode = "21_a7";
				continue ;
			}else if("R303".equals(map.get("ROLE_CODE"))){//客户经理主管
				nextNode = "21_a5";
				continue ;
			}else if("R301".equals(map.get("ROLE_CODE"))){//支行行长
				nextNode = "21_a6";
				continue ;
			}else if("R304".equals(map.get("ROLE_CODE"))){//客户经理
				nextNode = "21_a4";
				continue ;
			}else{
				continue ;
			}
		}	
	  Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("instanceid", instanceid);
	    map1.put("currNode", "21_a3");
	    map1.put("nextNode",  nextNode);
	    this.setJson(map1);
   	}
   	
}