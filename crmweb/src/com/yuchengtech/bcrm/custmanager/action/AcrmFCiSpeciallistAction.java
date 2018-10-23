package com.yuchengtech.bcrm.custmanager.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCiSpeciallist;
import com.yuchengtech.bcrm.custmanager.service.AcrmFCiSpeciallistService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describe 特殊名单客户
 * @author likai
 * @since 2014-09-02
 *
 */
@Action("/acrmFCiSpeciallist")
public class AcrmFCiSpeciallistAction extends CommonAction {

	@Autowired
    private  AcrmFCiSpeciallistService  service;
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init() {
		model = new AcrmFCiSpeciallist();
		setCommonService(service);
	}
	//审批表单业务信息查询
	public void prepare(){
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String instanceId =request.getParameter("instanceId");
    	String custId="";
    	String updateFlag="";
    	if(instanceId!=null){
    		custId=instanceId.split("_")[1];
    		updateFlag=instanceId.split("_")[2];
    	}
    	/*
    	 * 特殊名单客户
    	 */
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("SELECT S.* FROM ACRM_F_CI_SPECIALLIST S WHERE S.CUST_ID='"+custId+"'");
    	/*
    	 
        StringBuffer sb1 = new StringBuffer("select t.up_id,t.update_date,t.update_item,t.update_be_cont,t.update_user,t.cust_id,t.cust_name ,t.update_flag,t.update_item_en,t.update_table,t.update_table_id,t.update_af_cont_view as update_af_cont,a.user_name ");
        sb1.append(" from OCRM_F_CI_CUSTINFO_UPHIS t left join admin_auth_account a on a.account_name = t.update_user where t.cust_id='"+custId+"' and t.update_flag='"+updateFlag+"'");
        //排除主键字段或放大镜字段不显示label
        sb1.append(" and (t.update_table_id is null or t.update_table_id != '1') ");
        sb1.append(" and (t.update_item is not null or t.update_item <> '')");
        setPrimaryKey("t.update_table,t.update_item_en");
        */
        this.addOracleLookup("SPECIAL_LIST_TYPE", "XD000245");
        this.addOracleLookup("SPECIAL_LIST_KIND", "XD000246");
        this.addOracleLookup("SPECIAL_LIST_FLAG", "XD000247");
        this.addOracleLookup("IDENT_TYPE", "XD000040");
        SQL = buffer.toString();
        datasource =ds;
    }
	public void save() throws Exception{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST); 
    	String custId = request.getParameter("custId");
    	String custName = request.getParameter("custName");
    	String identType = request.getParameter("identType");
    	String identNo = request.getParameter("identNo");
    	String specialListType = request.getParameter("specialListType");
    	String specialListKind = request.getParameter("specialListKind");
    	String specialListFlag = request.getParameter("specialListFlag");
    	String origin = request.getParameter("origin");
    	String statFlag = request.getParameter("statFlag");
//    	String approvalFlag = request.getParameter("approvalFlag");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
    	String enterReason = request.getParameter("enterReason");
    	/**
    	 * 数据库操作调整到service中执行
    	 * 修改时间:2014-10-16
    	 * 修改人:wuxl2
    	 */
    	//删除客户原特殊名单信息
//    	service.batchUpdateByName(" delete from AcrmFCiSpeciallist s where s.custId='"+custId+"'", new HashMap());
    	//添加新的特殊名单信息
    	AcrmFCiSpeciallist list = new AcrmFCiSpeciallist();
    	list.setCustId(custId);
    	list.setCustName(custName);
    	list.setIdentType(identType);
    	list.setIdentNo(identNo);
    	list.setSpecialListType(specialListType);
    	list.setSpecialListKind(specialListKind);
    	list.setSpecialListFlag(specialListFlag);
    	list.setOrigin(origin);
    	list.setStatFlag("1");
    	/*
    	 * APP_STATUS状态：1-暂存；2-待审批；3-已审批；0-退回/拒绝
    	 */
    	list.setApprovalFlag("1");
    	list.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
    	list.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
    	list.setEnterReason(enterReason);
    	list.setLastUpdateUser(auth.getUserId());
    	list.setLastUpdateTm(new Timestamp(new Date().getTime()));
    	list.setLastUpdateSys("CRM");
    	service.saveSpe(list);
	}
	
	public String initFlow(){
		try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
	        String custId = request.getParameter("custId");
	    	String custName = request.getParameter("custName");
	    	String identType = request.getParameter("identType");
	    	String identNo = request.getParameter("identNo");
	    	String specialListType = request.getParameter("specialListType");
	    	String specialListKind = request.getParameter("specialListKind");
	    	String specialListFlag = request.getParameter("specialListFlag");
	    	String origin = request.getParameter("origin");
	    	String statFlag = request.getParameter("statFlag");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
	    	String enterReason = request.getParameter("enterReason");
	    	AcrmFCiSpeciallist aspe = new AcrmFCiSpeciallist();
	    	aspe.setCustId(custId);
	    	aspe.setCustName(custName);
	    	aspe.setIdentType(identType);
	    	aspe.setIdentNo(identNo);
	    	aspe.setSpecialListType(specialListType);
	    	aspe.setSpecialListKind(specialListKind);
	    	aspe.setSpecialListFlag(specialListFlag);
	    	aspe.setOrigin(origin);
	    	aspe.setStatFlag("1");
	    	/*
	    	 * APP_STATUS状态：1-暂存；2-待审批；3-已审批；0-退回/拒绝
	    	 */
	    	aspe.setApprovalFlag("2");
	    	aspe.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
	    	aspe.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
	    	aspe.setEnterReason(enterReason);
	    	aspe.setLastUpdateUser(auth.getUserId());
	    	aspe.setLastUpdateTm(new Timestamp(new Date().getTime()));
	    	aspe.setLastUpdateSys("CRM");
	    	service.saveSpe(aspe);//直接调用保存/更新
	        
			String s1 = request.getParameter("perModel");
		    JSONArray jarray = JSONArray.fromObject(s1);
		    Date date=new Date();
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String flag=sdf.format(date);
//		    service.bathsave(jarray,date,flag,aspe);
		    String instanceid = "CI"+"_"+custId+"_"+flag;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			String jobName = "设为特殊名单客户_" + custName;// 自定义流程名称
			/*
			 * 判断该客户流程是否在审批中
			 */
			int j = service.check(jobName);
			if(j>0){
				throw new BizException(1,0,"10001","正在审核中...请务重复提交!");
			}
			String nextNode = "";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List list = auth.getRolesInfo();
			for (Object m : list) {
				Map map = (Map) m;
				paramMap.put("role", map.get("ROLE_CODE"));
				if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
					nextNode = "74_a5";
					continue;
				} else if ("R302".equals(map.get("ROLE_CODE")) || "R303".equals(map.get("ROLE_CODE")) || 
						"R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
					nextNode = "74_a4";
					continue;
				} 
			}
			service.initWorkflowByWfidAndInstanceid("74", jobName, paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("instanceid", instanceid);
			map1.put("currNode", "74_a3");
			map1.put("nextNode", nextNode);
			this.setJson(map1);
    	}catch(Exception e){
    		e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
    	}
        return "success";
	}
	
	//法金设为特殊名单客户
	public String initFlow1(){
		try{
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
	        String custId = request.getParameter("custId");
	    	String custName = request.getParameter("custName");
	    	String identType = request.getParameter("identType");
	    	String identNo = request.getParameter("identNo");
	    	String specialListType = request.getParameter("specialListType");
	    	String specialListKind = request.getParameter("specialListKind");
	    	String specialListFlag = request.getParameter("specialListFlag");
	    	String origin = request.getParameter("origin");
	    	String statFlag = request.getParameter("statFlag");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
	    	String enterReason = request.getParameter("enterReason");
	    	AcrmFCiSpeciallist aspe = new AcrmFCiSpeciallist();
	    	aspe.setCustId(custId);
	    	aspe.setCustName(custName);
	    	aspe.setIdentType(identType);
	    	aspe.setIdentNo(identNo);
	    	aspe.setSpecialListType(specialListType);
	    	aspe.setSpecialListKind(specialListKind);
	    	aspe.setSpecialListFlag(specialListFlag);
	    	aspe.setOrigin(origin);
	    	aspe.setStatFlag("1");
	    	/*
	    	 * APP_STATUS状态：1-暂存；2-待审批；3-已审批；0-退回/拒绝
	    	 */
	    	aspe.setApprovalFlag("2");
	    	aspe.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
	    	aspe.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
	    	aspe.setEnterReason(enterReason);
	    	aspe.setLastUpdateUser(auth.getUserId());
	    	aspe.setLastUpdateTm(new Timestamp(new Date().getTime()));
	    	aspe.setLastUpdateSys("CRM");
	    	service.saveSpe(aspe);//直接调用保存/更新
	        
			String s1 = request.getParameter("perModel");
		    JSONArray jarray = JSONArray.fromObject(s1);
		    Date date=new Date();
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String flag=sdf.format(date);
//		    service.bathsave(jarray,date,flag,aspe);
		    String instanceid = "CI"+"_"+custId+"_"+flag;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			String jobName = "法金设为特殊名单客户_" + custName;// 自定义流程名称
			/*
			 * 判断该客户流程是否在审批中
			 */
			int j = service.check(jobName);
			if(j>0){
				throw new BizException(1,0,"10001","正在审核中...请务重复提交!");
			}
			String nextNode = "";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List list = auth.getRolesInfo();
			for (Object m : list) {
				Map map = (Map) m;
				paramMap.put("role", map.get("ROLE_CODE"));
				if ("R304".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))) {// 法金ARM,RM
					nextNode = "115_a4";
					continue;
				} 
			}
			service.initWorkflowByWfidAndInstanceid("115", jobName, paramMap, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("instanceid", instanceid);
			map1.put("currNode", "115_a3");
			map1.put("nextNode", nextNode);
			this.setJson(map1);
    	}catch(Exception e){
    		e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
    	}
        return "success";
	}
	
}
