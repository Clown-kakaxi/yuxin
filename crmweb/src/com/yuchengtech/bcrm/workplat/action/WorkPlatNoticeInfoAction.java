package com.yuchengtech.bcrm.workplat.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.vo.AuthUser;

@SuppressWarnings("serial")
@Action("/workplatnoticeInfo")
public class WorkPlatNoticeInfoAction extends CommonAction{
 	    
//    @Autowired
//    private NoticeService ns;
    
    @Autowired
    private CommonService service;
    
    public  void prepare(){
		
	}
    /**
   	 * 发起工作流(页面按钮发布流程)
   	 * */
   	public void initFlow() throws Exception{
   	  	ActionContext ctx = ActionContext.getContext();
   		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   		String requestId =  request.getParameter("instanceid");
   		String name =  request.getParameter("name");
   		String published =  request.getParameter("published");
   		String instanceid = "NOTICE_"+requestId;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
   		if("1".equals(published)){//审核中 发布 用来 区分在审核页面取值时的数据来源
   			 instanceid = "NOTICE_"+requestId+"_1";
   		}else if("2".equals(published)){//发布暂存 发布
   			 instanceid = "NOTICE_"+requestId+"_2";
   		}
   		String jobName = "公告发布_"+name;//自定义流程名称
   		
   		service.initWorkflowByWfidAndInstanceid("46", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
   		
   		Long idLong = new Long(Integer.parseInt(requestId));
   		service.batchUpdateByName("update WorkingplatformNotice n set n.published = '1' where n.noticeId = "+idLong+" ", null);
   		
   	    String nextNode = "46_a4";
		List list = auth.getRolesInfo();
		for(Object m:list){
			Map map = (Map)m;
			if("R104".equals(map.get("ROLE_CODE")) || "R105".equals(map.get("ROLE_CODE"))){//总行部门主管
				nextNode = "46_a3";
				break ;
			}else if("R303".equals(map.get("ROLE_CODE"))){//分支行行长
				nextNode = "46_a5";
				continue ;
			}else{
				continue ;
			}
		}	
	    Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("instanceid", instanceid);
	    map1.put("currNode", "46_a4");
	    map1.put("nextNode",  nextNode);
	    this.setJson(map1);
   	}
   	
   	public void saveNotice(){
   		
   	}
}
