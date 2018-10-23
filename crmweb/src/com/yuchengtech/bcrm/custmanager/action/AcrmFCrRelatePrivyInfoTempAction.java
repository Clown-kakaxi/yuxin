package com.yuchengtech.bcrm.custmanager.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCrRelatePrivyInfo;
import com.yuchengtech.bcrm.custmanager.service.AcrmFCrRelatePrivyInfoTempService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 关联人新增修改等，并存入正式内
 * 
 * @author : dongyi
 * @date : 2014-10-22 
 */
@Action("/AcrmFCrRelatePrivyInfoTemp")

public class AcrmFCrRelatePrivyInfoTempAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private AcrmFCrRelatePrivyInfoTempService acrmfcrrelateprivyinfotempservice;
    
    @Autowired
    public void init(){
        model = new AcrmFCrRelatePrivyInfo();
        setCommonService(acrmfcrrelateprivyinfotempservice);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String KeyId= request.getParameter("MAIN_ID");
        StringBuffer sb1 = new StringBuffer("SELECT distinct t.* FROM ACRM_F_CR_RELATE_PRIVY_INFO t WHERE 1=1 AND t.CANCEL_STATE ='1' ");
        if(KeyId!=null){
        	sb1.append(" AND t.MAIN_ID = '"+KeyId+"'");
        	
        }
        for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key) && !"".equals(this.getJson().get(key))){
            	if("PRIVY_NAME".equals(key)){
                   sb1.append(" AND t.PRIVY_NAME LIKE '%"+this.getJson().get(key)+"%'");
                }
            	if("MAIN_ID".equals(key)){	
                    sb1.append(" AND t.MAIN_ID = '"+this.getJson().get(key)+"'");
                }
            }
        }
        SQL = sb1.toString();
        datasource =ds;
    }
    /**
     *  修改关联人的关联方注销状态
     */
    public Object updateCancelStat(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("RELATE_ID");
        acrmfcrrelateprivyinfotempservice.updcancelStat(id);
        return "success";
        
        
    }
    /**
     *  保存申报人创建时的关联人的关联方
     */
    public Object saveData(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        acrmfcrrelateprivyinfotempservice.save(model);
        return "success";
    }
    /**
     *  查询关联人的关联方信息
     * @throws SQLException 
     */
    public String queryRelatePrivyInfo() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        
    	return "success";
    }
    //主申报人申报流程发起
	public void initFlow() throws Exception {
		Date currentdate = new Date();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		 String requestId = request.getParameter("MAIN_ID");
		String instanceid = "RM_"+requestId+"_"+currentdate.getTime();// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "关联方申报";// 自定义流程名称
		acrmfcrrelateprivyinfotempservice.initWorkflowByWfidAndInstanceid("57", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("instanceid", instanceid);
		map1.put("currNode", "57_a3");
		map1.put("nextNode", "57_a4");
		this.setJson(map1);
	}
	//关系人申报流程发起
	public void initFlow1() throws Exception {
		Date currentdate = new Date();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		 String requestId = request.getParameter("MAIN_ID");
		String instanceid = "relateprivy_"+requestId+"_"+currentdate.getTime();// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "关系人审批";// 自定义流程名称
		acrmfcrrelateprivyinfotempservice.initWorkflowByWfidAndInstanceid("59", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("instanceid", instanceid);
		map1.put("currNode", "59_a3");
		map1.put("nextNode", "59_a4");
		this.setJson(map1);
	}
}

