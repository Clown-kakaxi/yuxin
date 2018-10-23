package com.yuchengtech.bcrm.custmanager.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.eclipse.persistence.annotations.ReadOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmACiProfRelation;
import com.yuchengtech.bcrm.custmanager.service.ProfitRelatedPartyManagementService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 利润关联方管理
 * @author xuhf@yuchengtech.com
 *
 */

@Action("/profitRelatedPartyManagement")
public class ProfitRelatedPartyManagementAction extends CommonAction {
	@Autowired
	private ProfitRelatedPartyManagementService profitRelatedPartyManagementService ;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds ;

	@Autowired
	public void init(){
		model = new AcrmACiProfRelation();
		setCommonService(profitRelatedPartyManagementService);
	}
	
	public void prepare(){
		
		
		String sql="delete from ACRM_A_CI_PROF_RELATION where r_state is null ";
		profitRelatedPartyManagementService.deleteById(sql);
		
		StringBuffer sb = new StringBuffer("select c.* from ACRM_A_CI_PROF_RELATION c where 1=1 ");
		
		String ID = request.getParameter("ID");
		
		if(null!=ID && !"".equals(ID)){
			sb.append(" and c.ID = '" + ID + "' ");
		}
		
		for (String key : this.getJson().keySet()) {
			
			if (null != this.getJson().get(key) && !this.getJson().get(key).equals("")) {
				if("ID".equals(key)){
					sb.append(" and c.ID = '" + this.getJson().get(key) + "' ");
				}
			}
		}
		SQL = sb.toString();
		datasource = ds ;
		//如果排序的话。
		setPrimaryKey("c.CUST_ID desc");
		configCondition("c.CUST_NAME", "like", "CUST_NAME", DataType.String);
		configCondition("c.CUST_NAME_R", "=", "CUST_NAME_R", DataType.String);
	}
	
	public void saveObj() throws Exception{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String mgrName =auth.getUsername();//获取当前的客户经理
		String userId = auth.getUserId();//获取用户编号
//		AcrmACiProfRelation relation =(AcrmACiProfRelation) model ;
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String creatTimes = request.getParameter("createTimes");
    	if(creatTimes==null || "".equals(creatTimes)){
    		throw new BizException(1,0,"0000","主客户未与关联客户建立关联关系，不能提交");
    	}
    	String applyType=request.getParameter("type");
		Date date = new Date();
		Long time = date.getTime();
		profitRelatedPartyManagementService.updateCustState(request);
		
//		//根据创建日期是否为空，判断是新增还是修改。 -------
//		if(null==relation.getCreatDate()){
//			relation.setCreatDate(new Date());
//		}
//		
//		if(null==relation.getCreateUserId()){
//			relation.setCreateUserId(userId);
//		}
//		if(null==relation.getCreateUserName()){
//			relation.setCreateUserName(auth.getUsername());
//		}
//		
//		relation.setRState("1");
//		profitRelatedPartyManagementService.save(relation);
		
		//Long id = relation.getId();
		String name = request.getParameter("custName");
		String instanceid = "LF_"+creatTimes+"_"+time+"_"+name+"_"+applyType;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "利润关联方新增_"+name;//自定义流程名称
		//流程参数，判断节点走向
		String type=checkCurrentNode(userId);
		
		Map<String, String> mapParma = new HashMap<String, String>();//参数map
		mapParma.put("type", type);
		//具体的处理流程
		String nextStr="114";
		//当前节点班里人
		String currNode="114_a3";
		//下一届定点办理人
		String nextNode = "114_a4";
		switch (Integer.valueOf(type)) {
			case 1:
				nextNode="114_a4";
				break;
			case 2:
				nextNode="114_a6";
				break;
			case 3:
				nextNode="114_a5";
				break;
			case 4:
				nextNode="114_a7";
				break;
			case 5:
				nextNode="114_a8";
				break;
			case 6:
				nextNode="114_a9";
				break;
			case 7:
				nextNode="114_a10";
				break;
			case 8:
				nextNode="114_a12";
				break;
			default:
				break;
		}
		
		profitRelatedPartyManagementService.initWorkflowByWfidAndInstanceid(nextStr, jobName, mapParma, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", currNode);
		map.put("nextNode",  nextNode);
		this.setJson(map);
	}
	
	
	public void updatesaveObj() throws Exception{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();//获取用户编号
		AcrmACiProfRelation relation =(AcrmACiProfRelation) model ;
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String applyType=request.getParameter("type");
		Date date = new Date();
		Long time = date.getTime();	
		String createTimes=time+"";
		//根据创建日期是否为空，判断是新增还是修改。 -------
		if(null==relation.getCreatDate()){
			relation.setCreatDate(new Date());
		}
		
		if(null==relation.getCreateUserId()){
			relation.setCreateUserId(userId);
		}
		if(null==relation.getCreateUserName()){
			relation.setCreateUserName(auth.getUsername());
		}
		if(null==relation.getCreateTimes()){
			relation.setCreateUserName(createTimes);
		}else{
			createTimes=relation.getCreateTimes();
		}
		relation.setRState("1");
		relation.setOperateTime(new Timestamp(time));
		profitRelatedPartyManagementService.save(relation);
		
		profitRelatedPartyManagementService.updateCustObject(relation.getCustId(),createTimes);

		String name = relation.getCustName();
		String instanceid = "LF_"+createTimes+"_"+time+"_"+name+"_"+applyType;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "利润关联方修改_"+name;//自定义流程名称
		//流程参数，判断节点走向
		String type=checkCurrentNode(userId);
		
		Map<String, String> mapParma = new HashMap<String, String>();//参数map
		mapParma.put("type", type);
		//具体的处理流程
		String nextStr="114";
		//当前节点班里人
		String currNode="114_a3";
		//下一届定点办理人
		String nextNode = "114_a4";
		switch (Integer.valueOf(type)) {
			case 1:
				nextNode="114_a4";
				break;
			case 2:
				nextNode="114_a6";
				break;
			case 3:
				nextNode="114_a5";
				break;
			case 4:
				nextNode="114_a7";
				break;
			case 5:
				nextNode="114_a8";
				break;
			case 6:
				nextNode="114_a9";
				break;
			case 7:
				nextNode="114_a10";
				break;
			case 8:
				nextNode="114_a12";
				break;
			default:
				break;
		}
		
		profitRelatedPartyManagementService.initWorkflowByWfidAndInstanceid(nextStr, jobName, mapParma, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", currNode);
		map.put("nextNode",  nextNode);
		this.setJson(map);
	}
	
	public void tempsave() throws Exception{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();//获取用户编号
		AcrmACiProfRelation relation =(AcrmACiProfRelation) model ;
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String createTimes = request.getParameter("createTimes");
		
		String custId=relation.getCustId();
		String custNoR = relation.getCustNoR();
		String custNoRs="";
		if(custNoR.contains(",")){
		   String[] custNo = custNoR.split(",");
		   for(int i=0;i<custNo.length;i++){
			   if(i==0){
				   custNoRs+="'"+custNo[i]+"'" ;
			   }else{
				   custNoRs+=",'"+custNo[i]+"'";
			   }
		   }
		}else{
			custNoRs+="'"+custNoR+"'";
		}
		
		
		boolean flag = checkCustNoR(custNoRs,custId);
		if(!flag){
			throw new BizException(1,0,"0000","主客户与关联客户已经存在关联关系不允许新建");
		}
		
		Date date = new Date();
		Long time = date.getTime();
		//根据创建日期是否为空，判断是新增还是修改。 -------
		if(null==relation.getCreatDate()){
			relation.setCreatDate(new Date());
		}
		
		if(null==relation.getCreateUserId()){
			relation.setCreateUserId(userId);
		}
		if(null==relation.getCreateUserName()){
			relation.setCreateUserName(auth.getUsername());
		}
		if(null==createTimes || "".equals(createTimes)){
			relation.setCreateTimes(time+"");
		}else{
			relation.setCreateTimes(createTimes);
		}
		if(custNoR.contains(",")){
			   String[] custNo = custNoR.split(",");
			   for(int i=0;i<custNo.length;i++){
				   String custName = getCustNameByCustId(custNo[i]);
				   AcrmACiProfRelation newrelation=new AcrmACiProfRelation();
				   newrelation.setCustId(relation.getCustId());
				   newrelation.setCustName(relation.getCustName());
				   newrelation.setCustNoR(custNo[i]);
				   newrelation.setCustNameR(custName);
				   newrelation.setRDesc(relation.getRDesc()==null?"":relation.getRDesc());
				   newrelation.setRelationship(relation.getRelationship());
				   newrelation.setCreatDate(relation.getCreatDate());
				   newrelation.setCreateUserId(relation.getCreateUserId());
				   newrelation.setCreateUserName(relation.getCreateUserName());
				   newrelation.setCreateTimes(relation.getCreateTimes());
				   profitRelatedPartyManagementService.save(newrelation);

			   }
		}else{
			profitRelatedPartyManagementService.save(relation);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("times", relation.getCreateTimes());
		this.setJson(map);
	}
	
	public void updatetempsave() throws Exception{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();//获取用户编号
		AcrmACiProfRelation relation =(AcrmACiProfRelation) model ;
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	if(null==relation.getCreatDate()){
			relation.setCreatDate(new Date());
		}
		
		if(null==relation.getCreateUserId()){
			relation.setCreateUserId(userId);
		}
		if(null==relation.getCreateUserName()){
			relation.setCreateUserName(auth.getUsername());
		}

		profitRelatedPartyManagementService.save(relation);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("times", relation.getCreateTimes());
		this.setJson(map);
	}
	
	public String getCustNameByCustId(String custId) throws Exception{
		String custName="";
		Connection  connection = null;
		Statement ps =null;
		ResultSet rs = null;
		String sql="select cust_name from acrm_f_ci_customer where cust_id ='"+custId+"'";
		try{
			connection = ds.getConnection();
		   	ps = connection.createStatement();
		    rs= ps.executeQuery(sql);
		    if(rs.next()){
		      custName=rs.getString("cust_name");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return custName;
	}
	
	//校验主客户与关联客户是否已经存在关联关系
	public boolean checkCustNoR(String custNoR,String custId) throws Exception{
		boolean flag = false;
		Connection  connection = null;
		Statement ps =null;
		ResultSet rs = null;
		String sql="select cust_id from ACRM_A_CI_PROF_RELATION where cust_id="+custId+" and cust_no_r in ("+custNoR+")" ;
		try{
		  	connection = ds.getConnection();
		   	ps = connection.createStatement();
		    rs= ps.executeQuery(sql);
		    if(!rs.next()){
		      flag=true;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	//新增界面里面的删除
	public void  batchDel() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String ids = request.getParameter("idStr");
		ids=ids.substring(0, ids.length()-1);
		String sql="delete ACRM_A_CI_PROF_RELATION where id in ("+ids+")";
		profitRelatedPartyManagementService.deleteById(sql);
	}
	
	public String checkCurrentNode(String userId) throws Exception{
	   Connection  connection = null;
	   Statement ps =null;
	   ResultSet rs = null;
	   String type ="";
	   String roleCode="";
	   String sql="SELECT ROLE_CODE FROM ( SELECT F.*," +
			"(CASE WHEN " +
			" F.IDCHECK IS NULL THEN " +
			" 0 " +
			" ELSE " +
			" 1 " +
			" END) IS_CHECKED " +
			" FROM (SELECT * " +
			" FROM ADMIN_AUTH_ROLE T0 " +
			" LEFT JOIN (SELECT T1.ID AS IDCHECK, T1.ROLE_ID " +
			"  FROM ADMIN_AUTH_ACCOUNT_ROLE T1 " +
			"  LEFT JOIN ADMIN_AUTH_ACCOUNT T2 " +
			"  ON T2.ID = T1.ACCOUNT_ID " +
			"  WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME='"+userId+"')) E " +
			"   ON E.ROLE_ID = T0.ID " +
			"  WHERE T0.ROLE_LEVEL >= '1') F " +
			" WHERE 1 = 1 " +
			" ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'";
	   	connection = ds.getConnection();
	   	ps = connection.createStatement();
	    rs= ps.executeQuery(sql);
	    List<String> list = new ArrayList<String>();
	   while(rs.next()){
	    	roleCode=rs.getString(1);
	    	list.add(roleCode);
	    }
       if(list.contains("R311")){
    		if(list.contains("R202")){
    			type="8";
    		}else{
    		   type="7";
    		}
    	}else if(list.contains("R309")){
    		type="5";
    	}else if(list.contains("R106")){
    		type="6";
    	}else if(list.contains("R104") || list.contains("R105")){
    		if(checkTeamHead(userId)){
	    		type="3";
	    	}else{
	    		type="4";
	    	}
    	}else if(list.contains("R304") || list.contains("R305")){
    		if(checkTeamHead(userId)){
    		    type="1";
    		}else{
    			type="2";
    		}
    	}
		return type;
		
	}
	
	/**
	 * 检测总行法金/法金 ARM和RM是否存在TEAMHEAD
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean checkTeamHead(String userId) throws Exception{
		boolean flag = false;
	    Connection  connection = null;
	    Statement ps =null;
	    ResultSet rs = null;
	    String teamHead="";
		String sql="SELECT A.BELONG_TEAM_HEAD FROM ADMIN_AUTH_ACCOUNT  A WHERE ACCOUNT_NAME='"+userId+"'";
		try{
		  	connection = ds.getConnection();
		   	ps = connection.createStatement();
		    rs= ps.executeQuery(sql);
		    if(rs.next()){
		    	teamHead=rs.getString(1);
		    }
		    if(teamHead!=null && !"".equals(teamHead)){
		    	flag = true;
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		
		return flag;
		
	}
	
	/**
	 * 删除 利润关联方管理
	 * @return
	 */
	public void batchDestory() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		AcrmACiProfRelation relation =(AcrmACiProfRelation) model ;
		String applyType=request.getParameter("type");
		Date date = new Date();
		Long time = date.getTime();	
		String createTimes=time+"";
		Long id = relation.getId();
		if(null==relation.getCreateTimes()){
			relation.setCreateUserName(createTimes);
		}else{
			createTimes=relation.getCreateTimes();
		}
		relation.setRState("1");
		relation.setOperateTime(new Timestamp(time));
		profitRelatedPartyManagementService.save(relation);
		String name = relation.getCustName();

		String instanceid = "LF_"+createTimes+"_"+time+"_"+name+"_"+applyType+"_"+id;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "利润关联方刪除_"+name;//自定义流程名称
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = auth.getUserId();//获取用户编号
		
        String type=checkCurrentNode(userId);
		
		Map<String, String> mapParma = new HashMap<String, String>();//参数map
		mapParma.put("type", type);
		//具体的处理流程
		String nextStr="114";
		//当前节点班里人
		String currNode="114_a3";
		//下一届定点办理人
		String nextNode = "114_a4";
		switch (Integer.valueOf(type)) {
			case 1:
				nextNode="114_a4";
				break;
			case 2:
				nextNode="114_a6";
				break;
			case 3:
				nextNode="114_a5";
				break;
			case 4:
				nextNode="114_a7";
				break;
			case 5:
				nextNode="114_a8";
				break;
			case 6:
				nextNode="114_a9";
				break;
			case 7:
				nextNode="114_a10";
				break;
			case 8:
				nextNode="114_a12";
				break;
			default:
				break;
		}
		
		profitRelatedPartyManagementService.initWorkflowByWfidAndInstanceid(nextStr, jobName, mapParma, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", currNode);
		map.put("nextNode",  nextNode);
		this.setJson(map);
	}
	
}
