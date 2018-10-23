package com.yuchengtech.bcrm.customer.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bcrm.customer.service.MyPotentialCustomerService;
import com.yuchengtech.bcrm.custview.model.AcrmFCiProductWill;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

@Action("myPotentialCustomer")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "myPotentialCustomer"})
})
public class MyPotentialCustomerAction extends CommonAction{
	private static final long serialVersionUID = -2010621122837504304L;
	private AcrmFCiCustomer wi = new AcrmFCiCustomer();
	private Long id;
	private static Logger log = Logger.getLogger(MyPotentialCustomerAction.class);
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
    @Autowired
    private MyPotentialCustomerService myPotentialCustomerService;
    
    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init()
	{
		model = new AcrmFCiCustomer();
		setCommonService(myPotentialCustomerService);
	}
    public DefaultHttpHeaders create() throws BizException{
    		  ActionContext ctx = ActionContext.getContext();
    	      request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	      String strphon="";
    	      String custId = request.getParameter("cusId")==null?"":request.getParameter("cusId");//客户号
    	      String custZhName = request.getParameter("cusName")==null?"":request.getParameter("cusName");//客户名称
    	      String custTyp = request.getParameter("custType")==null?"":request.getParameter("custType");//客户类型
    	      String linkUser = request.getParameter("attenName")==null?"":request.getParameter("attenName");//联系人
    	      String zipcode = request.getParameter("zipcode")==null?"":request.getParameter("zipcode");//邮编
    	      String sourceChannel = request.getParameter("sourceChannel")==null?"":request.getParameter("sourceChannel");//来源渠道
    	      String mktActivitie = request.getParameter("mktActivitie")==null?"":request.getParameter("mktActivitie");//营销活动
    	      String cusNationality = request.getParameter("cusNationality")==null?"":request.getParameter("cusNationality");//国籍
    	      String refereesId = request.getParameter("refereesId")==null?"":request.getParameter("refereesId");//推荐人
    	      
    	      String certType = request.getParameter("certType")==null?"":request.getParameter("certType");//证件类型
    	      String linkPhone = request.getParameter("callNo")==null?"":request.getParameter("callNo");//手机号码
    	      String phoneAreaCode = request.getParameter("phoneAreaCode")==null?"":request.getParameter("phoneAreaCode");//电话区号
    	      strphon=linkPhone=phoneAreaCode+"-#-"+linkPhone;
    	      linkPhone=strphon;
    	      String contmethInfo = request.getParameter("contmethInfo")==null?"无":request.getParameter("contmethInfo");//座机号
    	      String custStat = request.getParameter("custStat")==null?"":request.getParameter("custStat");//客户状态默认‘潜在’
    	      String jobType = request.getParameter("jobType")==null?"":request.getParameter("jobType");
    	      String industType = request.getParameter("industType")==null?"":request.getParameter("industType");
    	      String recordSession = request.getParameter("recordSession")==null?"":request.getParameter("recordSession");//沟通话术
    	      String cusEmail = request.getParameter("cusEmail")==null?"":request.getParameter("cusEmail");//Email
    	      String cusWechatid = request.getParameter("cusWechatid")==null?"":request.getParameter("cusWechatid");//微信号
    	      if(custStat.length()<1){
    	    	custStat="2";  
    	      };
    	      String certNum = request.getParameter("certCode")==null?"":request.getParameter("certCode");//证件号码
    	      String addr = request.getParameter("cusAddr")==null?"":request.getParameter("cusAddr");//通讯地址
    	      
//    	      String checkStr = myPotentialCustomerService.saveLatent(custId, custZhName, custTyp, linkUser, zipcode,  sourceChannel,mktActivitie,cusNationality,refereesId,certType,   linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid);
    	      String  checkStr = myPotentialCustomerService.save( custId, custZhName, custTyp, linkUser, zipcode, "aaaaa", certType,
    	  			 linkPhone, contmethInfo, addr, custStat, "bbbbb", certNum, jobType, industType);
    	      if(null==checkStr){
    	    		return new DefaultHttpHeaders("success");
    	     }else{
               throw new BizException(1, 2, "1000", checkStr);
             }		
            }
    

    public List<Object> validationNewLatentCustomer(String sql,int clom){
    	log.info(""+sql);
    	List<Object> List = new ArrayList<Object>();
    	Connection  connection=null;
   		Statement stmt=null;
   		ResultSet result=null;
    	try{
   				 connection = ds.getConnection();
   				 stmt = connection.createStatement();
   				 result = stmt.executeQuery(sql);
   				String custId="";
   				String sourceStr="";
   			    while (result.next()){
   			    	custId = result.getString(1);
   			    	List.add(custId);
   			    	if(clom==8){
	   			    	sourceStr=result.getString(2);
	   			    	List.add(sourceStr);
	   			    	List.add(result.getString(3));
	   			    	List.add(result.getString(4));
	   			    	List.add(result.getString(5));
	   			    	List.add(result.getString(6));
	   			    	List.add(result.getString(7));
	   			    	List.add(result.getString(8));
	   			    	}
   			    	
   			    }
   			log.info("validationNewLatentCustomer: "+List.toString());
   			 return List;
   		}catch(Exception e){
   			e.printStackTrace();
   		}finally{
   			JdbcUtil.close(result, stmt, connection);
   		}
		return null;
       }
   	/**
   	 * 
   	 * @param cusId
   	 * @return
   	 * 根据创建规则 验证：
   	 * 手动新增时，首先通过客户名称、证件类型、证件号码与CRM中正式客户作精确匹配，如有匹配记录，则提示认为该客户已是正式客户，不允许作为潜在客户新增；
     * 再通过客户名称、证件类型、证件号码与个金潜在客户池中客户作精确匹配，若有匹配记录，则提示该潜在客户已存在，不允许新增；
     * 若客户名称不同，证件类型、证件号码相同，则提示用户是否为同一用户，若用户选择是，则提示用户通过修改的方式维护该客户信息，如果用户选择否，则允许新增；
     * 再通过客户名称和电话号码与个金潜在客户池中客户作精确匹配，如有匹配记录，则提示该潜在客户已存在，不允许重复新建；
     * 若客户名称不同，电话号码相同，则提示是否为同一客户，如果用户选择是，则提示用户通过修改的方式维护该客户信息，如果用户选择否，则仍允许新增
   	 */
   public void  doParameterCheck(){
	   ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String type;
		String phtype;
		try {
			String custnamev = request.getParameter("custnamev")==null?"":request.getParameter("custnamev");
			String identtypev = request.getParameter("identtypev")==null?"":request.getParameter("identtypev");
			String identnov = request.getParameter("identnov")==null?"":request.getParameter("identnov");
			String callnov = request.getParameter("callnov")==null?"":request.getParameter("callnov");
			type = "";
			phtype = "";
			List<Object> list=null,list5=null,list6=null;
			boolean  flag1 =false;  //存在与个金潜在客户有匹配记录 客户名称、证件类型、证件号码  true
			boolean	 flag2 =false;   //存在与个金潜在客户匹配  客户名称不同  ,证件类型、证件号码相同  true
			boolean	 flag3 =false;   //存在与个金潜在客户匹配 电话相同,客户名不同  true
			boolean	 flag4 =false;   //存在与个金潜在客户匹配 电话相同,客户名相同同  true
			boolean	 flag6=false;     //存在与正式客户有匹配记录 客户名称、证件类型、证件号码  true
			if(custnamev!=null&&!"".equals(custnamev)&&identtypev!=null&&!"".equals(identtypev)&&identnov!=null&&!"".equals(identnov)){
				String sql6 ="select cust_id from acrm_f_ci_customer c where c.cust_name='"+custnamev+"' and c.ident_type='"+identtypev+"' and c.ident_no='"+identnov+"' and c.potential_flag='0' and c.cust_type='2'";
				List<Object> list1=validationNewLatentCustomer(sql6,1);
				if(list1!=null&&list1.size()>0){
					flag6=true;
				}
				String  sql3="select pc.cus_id from acrm_f_ci_pot_cus_com pc where pc.cus_name='"+custnamev+"' and pc.cert_type='"+identtypev+"' and pc.cert_code='"+identnov+"'";
				List<Object> list3=validationNewLatentCustomer(sql3,1);
				if(list3!=null&&list3.size()>0){
					flag1=true;
				}
				String  sql4="select pc.cus_id from acrm_f_ci_pot_cus_com pc where pc.cus_name<>'"+custnamev+"' and pc.cert_type='"+identtypev+"' and pc.cert_code='"+identnov+"'";
				List<Object> list4=validationNewLatentCustomer(sql4,1);
				if(list4!=null&&list4.size()>0){
					flag2=true;
				}
			}else{
				String sql6 =" select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id   where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace('"+callnov+"',substr('"+callnov+"',0,instr('"+callnov+"','-',1,2))) and c.cust_name='"+custnamev+"' ";
				List<Object> list2=validationNewLatentCustomer(sql6,1);
				if(list2!=null&&list2.size()>0){
					flag6=true;
			    }
			}
			if(callnov!=null&&!"".equals(callnov)){
				String  sql5="select pc.cus_id cust_id,pc.mkt_activitie,pc.cus_name,pc.cust_mgr,ac.user_name,pc.main_br_id,ao.org_name,pc.call_no from acrm_f_ci_pot_cus_com pc   left join ADMIN_AUTH_ACCOUNT ac on pc.CUST_MGR = ac.account_name left join ADMIN_AUTH_ORG ao on pc.main_br_id = ao.org_id  where pc.call_no='"+callnov+"'";
				list5=validationNewLatentCustomer(sql5,8);
				if(list5!=null&&list5.size()>0){
					flag3=true;
			  }
			}
			if(custnamev!=null&&!"".equals(custnamev)){
				String  sql7="select pc.cus_id cust_id,pc.mkt_activitie,pc.cus_name,pc.cust_mgr,ac.user_name,pc.main_br_id,ao.org_name,pc.call_no from acrm_f_ci_pot_cus_com pc   left join ADMIN_AUTH_ACCOUNT ac on pc.CUST_MGR = ac.account_name left join ADMIN_AUTH_ORG ao on pc.main_br_id = ao.org_id where pc.cus_name='"+custnamev+"' and pc.call_no='"+callnov+"'";
				list6=validationNewLatentCustomer(sql7,8);
				if(list6!=null&&list6.size()>0){
					flag4=true;
			}}
			
			if(flag6==true){
				type="1";       //与正式客户有匹配记录 客户名称、证件类型、证件号码
			}else if(flag1==true){
				type="2";      //与个金潜在客户有匹配记录 客户名称、证件类型、证件号码
			}else if(flag2==true){
				type="3";     //与个金潜在客户匹配  客户名称不同  ,证件类型、证件号码相同
			} 
			
			if(flag3){
				phtype="4";   //与个金潜在客户匹配 电话相同,客户名不同 
				list=list5;
			}
			if(flag4==true){
				phtype="5";  //与个金潜在客户匹配 电话相同,客户名相同
				list=list6;
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("phtype", phtype);
			if(list!=null&&list.size()>0){
			map.put("custid", list.get(0));   //潜在客户id
			map.put("source", list.get(1)==null?"":list.get(1));   //已有营销活动
			map.put("cusname", list.get(2));  
			map.put("custmgr", list.get(3)); 
			map.put("custmgrv", list.get(4));  
			map.put("cusorg", list.get(5));
			map.put("cusorgv", list.get(6));  
			map.put("callno", list.get(7));  
			}
			log.info("doParameterCheck  type: "+type+" phtype :"+phtype);
			this.setJson(map);
		} catch (Exception e) {
			throw new BizException(1,0,"10001",":"+e.getMessage());
			//e.printStackTrace();
		}
		
   	}
   
 //删除
   public DefaultHttpHeaders batchDel(){
   	ActionContext ctx = ActionContext.getContext();
   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	myPotentialCustomerService.batchDel(request);
		return new DefaultHttpHeaders("success");
   }
   
   
   
 //恢复
   public DefaultHttpHeaders recoverBackInfo(){
   	ActionContext ctx = ActionContext.getContext();
   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	myPotentialCustomerService.recoverBackInfo(request);
		return new DefaultHttpHeaders("success");
   }
   
   //修改营销活动
   public DefaultHttpHeaders editedmktActivitie(){
		ActionContext ctx = ActionContext.getContext();
	   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	myPotentialCustomerService.editedmktActivitie(request);
			return new DefaultHttpHeaders("success");
	   
   }
   
   
   //分派状态
   public boolean checkCustBackState(String custId){
	   	boolean flag=false;
	   	Connection  connection=null;
   		Statement stmt=null;
   		ResultSet result=null;
	   	try{
	   		  connection = ds.getConnection();
			 stmt = connection.createStatement();
	   		String sql="select back_state from acrm_f_ci_pot_cus_com  where cus_id in ("+custId+") ";
	   		 result = stmt.executeQuery(sql);
	   		List<String> list = new ArrayList<String>();
	   		String backState="";
	   		while(result.next()){
	   			backState=result.getString("back_state");
	   			list.add(backState);
	   		}
	   		if(list!=null){
	   			if(list.contains("1")){
	   				flag=true;
	   			}
	   		}
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		JdbcUtil.close(result, stmt, connection);
	   	}
	   	return flag;
   }
   
   //是否有分配人
   public boolean checkMoverUser(String custId){
	   	boolean flag = false;
	   	try{
	   		String sql="select p.mover_user from acrm_f_ci_pot_cus_com p where cus_id IN ("+custId+")";
	   	 	Connection  connection = ds.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery(sql);
				String mover_user="";
			    while (result.next()){
			    	mover_user = result.getString(1);
			    	if(mover_user==null || "".equals(mover_user)){
			    		mover_user="1";
			    	}else{
			    		mover_user="2";
			    	}
			    }
			    if(mover_user.equals("2")){
			    	flag = true;
			    }
			    log.info("checkMoverUser :"+flag+" sql"+sql);
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}
	   	return flag;
   }
   //校验归属人角色
   /*public boolean checkUserNameRole(String cusId){	
	   	boolean flag = false;
	   	try{	
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
							"  WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME IN (SELECT  CUST_MGR FROM ACRM_F_CI_POT_CUS_COM  WHERE CUS_ID IN ("+cusId+")))) E " +
							"   ON E.ROLE_ID = T0.ID " +
							"  WHERE T0.ROLE_LEVEL >= '1') F " +
							" WHERE 1 = 1 " +
							" ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'";
		    	Connection  connection = ds.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery(sql);
				List<String> List = new ArrayList<String>();
				String role="";
			    while (result.next()){
			    	role = result.getString(1);
			    	List.add(role);
			    }
			    if(List.size()==1){
			    	if(List.get(0).equals("R303") || List.get(0).equals("R122")){
			    		flag = true;
			    	}
			    }
			    log.info("checkUserNameRole :"+flag+" sql"+sql);
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}
	   	return flag;
   }*/
   public String checkUserNameRole(String cusId){	
	   	String flagstr = "";
	   	Connection  connection =null;
		Statement stmt =null;
		ResultSet result =null;
	   	try{	
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
							"  WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME IN (SELECT  CUST_MGR FROM ACRM_F_CI_POT_CUS_COM  WHERE CUS_ID IN ("+cusId+")))) E " +
							"   ON E.ROLE_ID = T0.ID " +
							"  WHERE T0.ROLE_LEVEL >= '1') F " +
							" WHERE 1 = 1 " +
							" ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'";
		    	  connection = ds.getConnection();
				stmt = connection.createStatement();
				 result = stmt.executeQuery(sql);
				List<String> List = new ArrayList<String>();
				String role="";
			    while (result.next()){
			    	role = result.getString(1);
			    	List.add(role);
			    }
			    
			    if(List.size()==1){
			    	if(List.get(0).equals("R303") || List.get(0).equals("R122")){
			    		flagstr="1";
			    	}
			    }else if(List.size()<1){
			    	flagstr="2";
			    }else{
			    	flagstr="3";
			    } 
			    log.info("checkUserNameRole :"+flagstr+" sql"+sql);
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		JdbcUtil.close(result, stmt, connection);
	   	}
	   	return flagstr;
  }
   //分派前校验
  /* public void  doCheckRole(){
   	ActionContext ctx = ActionContext.getContext();
   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    String custId = request.getParameter("cusId")==null?"":request.getParameter("cusId");
   	List<String> list = new ArrayList<String>();
   	String type="";
   	boolean backFlag = checkCustBackState(custId);
   	if(backFlag){//存在未收回的
   		type="4";
   	}else{
	      	String types="";
	    	if(custId.contains(",")){
	    		String[] strList = custId.split(",");
	    		for(String str:strList){
	    		 	boolean MoverFlag = checkMoverUser(str);//判断是否存在分配移交人
	    	    	String roleCheck = checkUserNameRole(str);//判断客户的归属人
	    	    	log.info("doCheckRole backFlag :"+backFlag+" MoverFlag :"+MoverFlag+" roleCheck:"+roleCheck+" type :"+type);
	    	    	if(MoverFlag && roleCheck=="1"){//已有分配人,归属人角色是RM
			    		types="1";
			    		list.add(types);
			    	}else if((MoverFlag && (roleCheck=="1"||roleCheck=="3")) || (!MoverFlag && (roleCheck=="1"||roleCheck=="3"))){//已有分配人,但是归属人角色不是RM或者如果没有归属人并且归属是RM的是不能分配的
			    		types="3";
			    		list.add(types);
			    	}else{//没有分配人并且归属不是RM的
			    		types="2";
			    		list.add(types);
			    	}
	    		}
	    	}else{
	    		boolean MoverFlag = checkMoverUser(custId);
	    		String roleCheck = checkUserNameRole(custId);
		    	log.info(" doCheckRole backFlag :"+backFlag+" MoverFlag :"+MoverFlag+" roleCheck:"+roleCheck+" type :"+type);
		    	if(MoverFlag && roleCheck=="1"){//已有分配人,归属人角色是RM
		    		types="1";
		    		list.add(types);
		    	}else if((MoverFlag && (roleCheck=="1"||roleCheck=="3")) || (!MoverFlag && (roleCheck=="1"||roleCheck=="3"))){//已有分配人,但是归属人角色不是RM或者如果没有归属人并且归属是RM的是不能分配的
		    		types="3";
		    		list.add(types);
		    	}else{//没有分配人并且归属不是RM的
		    		types="2";
		    		list.add(types);
		    	}
	    	}
	    	if(list.contains("1")){//如果存在某一个客户他存在分配移交人并且主管客户经理是客户经理只能先收回
	    		type="1";
	    	}else if(list.contains("3")){
	    		type="3";
	    	}else{
	    		type="2";
	    	} 
       }
    log.info("doCheckRole type :"+type);
   	Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		this.setJson(map);
   }*/
   
   /**
    * 批量导入情况统计 
    */
   public void getSumimportRecord(){
	   ActionContext ctx = ActionContext.getContext();
	   request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   String strs= auth.getTemp1();
	   String[] arrys=strs.split("-");
	   Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("sumSuccesscount", arrys[0]);
		maps.put("sunfirescount", arrys[1]);
		maps.put("failureBycallno", arrys[2]);
		maps.put("failurecount", arrys[3]);
		this.setJson(maps);
   }
   


 
   
   /**
    * 分派前校验 ：
    * 允许分派条件：所属客户经理和分派人所属机构为同一级别时允许分派
    */
   public void  doFpCheckRole(){
	   ActionContext ctx = ActionContext.getContext();
	   request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String custId = request.getParameter("cusId")==null?"":request.getParameter("cusId");
	    //String custMgr = request.getParameter("custMgr")==null?"":request.getParameter("custMgr");
	    Map<String,String> map=getFpBeforeCheckOrgLevel(custId,"cust_mgr");
	    String levelmgr=map.get("OrgLevel");
	    String levelmover1="";
	    String result="";
		 String result1="";
	    String levelmover=auth.getUnitlevel();
	    String moveru="";
	    List<String> allresult=new ArrayList<String>();
	    if(custId.contains(",")){
    		String[] strList = custId.split(",");
    		for(String str:strList){
			    Map<String,String> map1=getFpBeforeCheckOrgLevel(str,"mover_user");
			    moveru=map1.get("moverUser");
			    //String levelmover=map1.get("OrgLevel");
			    List<Object> list1=getResultStr("select aog.org_level from admin_auth_account act  left join ADMIN_AUTH_ORG aog on aog.org_id=act.org_id  where act.account_name='"+moveru+"'");
				if(list1!=null && list1.size()==1){
					levelmover1=(String) list1.get(0);
				}
			    if(levelmgr.equals(levelmover)){
			    	result="1";   //所属机构级别相同
			    }else{
			    	result="2";  //所属机构级别不同
			    }
			    if(moveru==null){
			    	result="1";  //未分
			    }
			    allresult.add(result);  
    		}
	    }else{
		    Map<String,String> map1=getFpBeforeCheckOrgLevel(custId,"mover_user");
		    moveru=map1.get("moverUser");
		    //String levelmover=map1.get("OrgLevel");
		    List<Object> list1=getResultStr("select aog.org_level from admin_auth_account act  left join ADMIN_AUTH_ORG aog on aog.org_id=act.org_id  where act.account_name='"+moveru+"'");
			if(list1!=null && list1.size()==1){
				levelmover1=(String) list1.get(0);
			}
		    if(levelmgr.equals(levelmover)){
		    	result="1";  //所属机构级别相同
		    }else{
		    	result="2";   //所属机构级别不同
		    }
		    if(moveru==null){
		    	result="1"; //未分
		    }
		    List<Object> list=getResultStr("SELECT ROLE_CODE FROM ( SELECT F.*, (CASE WHEN F.IDCHECK IS NULL THEN 0 ELSE 1 END) IS_CHECKED FROM (SELECT * FROM ADMIN_AUTH_ROLE T0 LEFT JOIN (SELECT T1.ID AS IDCHECK, T1.ROLE_ID FROM ADMIN_AUTH_ACCOUNT_ROLE T1 LEFT JOIN ADMIN_AUTH_ACCOUNT T2 ON T2.ID = T1.ACCOUNT_ID WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME IN ('"+moveru+"'))) E ON E.ROLE_ID = T0.ID WHERE T0.ROLE_LEVEL >= '1') F WHERE 1 = 1 ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1' ");
			if(list!=null){
		    for(Object item:list){
				if("R202".equals(item)){
					result1="1";
				}
			}
			}
	    }
	    if(allresult!=null&&allresult.contains("2")){
	    	result="2";
	    }
	    Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("result", result);
		maps.put("result1", result1);
		maps.put("levelmgr", levelmgr);
		maps.put("levelmover1", levelmover1);
		maps.put("levelmover", levelmover);
		maps.put("moveru", moveru);
		this.setJson(maps);
   }
   
   //分派前根据用户id查询所属机构级别
   public Map<String,String> getFpBeforeCheckOrgLevel(String custId,String pamas){
		Map<String,String> map = new HashMap<String, String>();
	   String OrgLevel="";
	   String custMgr="";
	   String moverUser="";
	   Connection  connection=null;
  		Statement stmt=null;
  		ResultSet result=null;
   	try{
   		  connection = ds.getConnection();
		 stmt = connection.createStatement();
		 StringBuffer sb1=new StringBuffer();
		 sb1.append("select aog.org_level,d.cust_mgr,d.mover_user from ACRM_F_CI_POT_CUS_COM d  left join admin_auth_account act on act.account_name=d."+pamas+"  left join ADMIN_AUTH_ORG aog on aog.org_id=act.org_id  where d.cust_type='2' ");
		 if(custId.contains(",")){
			 sb1.append("and d.cus_id in ("+custId+")");
   		}else{
   			sb1.append("and d.cus_id ="+custId+"");
   		}
		 result = stmt.executeQuery(sb1.toString());
   		while(result.next()){
   			OrgLevel=result.getString("org_level");
   			custMgr=result.getString("cust_mgr");
   			moverUser=result.getString("mover_user");
   		}
   		map.put("OrgLevel", OrgLevel);
   		map.put("custMgr", custMgr);
   		map.put("moverUser", moverUser);
   	}catch(Exception e){
   		e.printStackTrace();
   	}finally{
   		JdbcUtil.close(result, stmt, connection);
   	}
   	return map;
   }
   
   //分派前所属客户经理
   public String getFpBeforeCusMgr(String custId){
	   String custMgr="";
	   Connection  connection=null;
  		Statement stmt=null;
  		ResultSet result=null;
   	try{
   		  connection = ds.getConnection();
		 stmt = connection.createStatement();
   		String sql="select cust_mgr from acrm_f_ci_pot_cus_com  where cus_id ="+custId+"";
   		 result = stmt.executeQuery(sql);
   		while(result.next()){
   			custMgr=result.getString("cust_mgr");
   		}	
   	}catch(Exception e){
   		e.printStackTrace();
   	}finally{
   		JdbcUtil.close(result, stmt, connection);
   	}
   	return custMgr;
   }
   
   /**
    * 分派 回收 邮件提醒
    */
   public void doEmailRemind(String custMgr,String remindremark){
	   String email= ""; 
	   String username="";
	   List<Object> listTemp1=getResultStr("select a.EMAIL from ADMIN_AUTH_ACCOUNT a  where a.account_name='"+custMgr+"' and trim(a.EMAIL) is not null");
	   if(listTemp1!=null&&listTemp1.size()>0){
		   email=(String) listTemp1.get(0);
	   }
	   List<Object> listTemp2=getResultStr("select a.USER_NAME from ADMIN_AUTH_ACCOUNT a  where a.account_name='"+custMgr+"' and trim(a.EMAIL) is not null");
	   if(listTemp2!=null&&listTemp2.size()>0){
		   username=(String) listTemp2.get(0);
	   }
	   StringBuffer remindremarkstr=new StringBuffer("尊敬的："+username+"\r\n"+"<br/>");
	   remindremarkstr.append(remindremark);
	   try {
			MailClient.getInstance().sendMsg(email, "个金潜在客户分派/回收提醒信息通知", remindremarkstr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
   

   //分配  
   public DefaultHttpHeaders fpPotCusInfo() throws Exception{
   	ActionContext ctx = ActionContext.getContext();
   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	String authId = auth.getUserId();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String date = sdf.format(new Date());
	String cusId = request.getParameter("cusId");
	//String type=request.getParameter("type");
	String custMgr = request.getParameter("custMgr");
	String defaultdate = request.getParameter("monthvalue")==null?"":request.getParameter("monthvalue");
	
    log.info(""+cusId+"custMgr-------->:"+custMgr);
    String sql2="";
    String sql3="";
  //记录分派历史
  	SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
  	Date newdate=new Date();
  	String time = sdfs.format(newdate);
  	if(cusId.contains(",")){
  	  String[] custIds = cusId.split(",");
  	  for(String custId:custIds){
  		  String custName = getCustNameByCustId(custId);
  		  String custMgrBefor=getFpBeforeCusMgr(custId);
  		  sql2="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,cust_mgr_before,cust_mgr_after,mover_date) values("+custId+",'"+custName+"','1','"+authId+"','"+custMgrBefor+"','"+custMgr+"','"+time+"')";
  		  myPotentialCustomerService.updatePotCusInfo(sql2);
  	  }
  		
  	}else{
  		String custName = getCustNameByCustId(cusId);
  		 String custMgrBefor=getFpBeforeCusMgr(cusId);
  		sql3="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,cust_mgr_before,cust_mgr_after,mover_date) values("+cusId+",'"+custName+"','1','"+authId+"','"+custMgrBefor+"','"+custMgr+"','"+time+"')";
  		myPotentialCustomerService.updatePotCusInfo(sql3);
  	}
    
    
/*	if(type.equals("1")){  //机构
		Map<String,String> map = getOrgInfo(custMgr);
		String orgId=map.get("orgId")==null?"":map.get("orgId");
		StringBuffer sb=new StringBuffer("");
		sb.append("update acrm_f_ci_pot_cus_com  set cust_mgr='', main_br_id='"+orgId+"',mover_user='"+authId+"',back_state='2',mover_date=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'),OPERATE_TIME=systimestamp,actual_receive_date=null, ");
		if(!"".equals(defaultdate)){
			sb.append(" default_receive_date=to_date('"+defaultdate+"','yyyy-mm-dd hh24:mi:ss')");
		}else{
			sb.append(" default_receive_date=add_months(sysdate, 3) ");
		}
		sb.append(" where  cus_id in ("+cusId+")");
		myPotentialCustomerService.updatePotCusInfo(sb.toString());
		log.info("fpPotCusInfo  >>"+sb.toString());
		
	}else if(type.equals("2")){  //客户经理
*/	
  
  	Map<String,String> map = getCustMgrInfo(custMgr);
		String orgId=map.get("orgId")==null?"":map.get("orgId");
		String accountName=map.get("accountName")==null?"":map.get("accountName");
		StringBuffer sb=new StringBuffer("");
		sb.append("update acrm_f_ci_pot_cus_com  set cust_mgr='"+accountName+"', main_br_id='"+orgId+"',mover_user='"+authId+"',back_state='2',mover_date=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'),OPERATE_TIME=systimestamp,actual_receive_date=null, ");
		if(!"".equals(defaultdate)){
			sb.append(" default_receive_date=to_date('"+defaultdate+"','yyyy-mm-dd hh24:mi:ss') ");
		}else{
			sb.append(" default_receive_date=add_months(sysdate, 3) ");
		}
		sb.append(" where  cus_id in ("+cusId+")");
		log.info("fpPotCusInfo  >>"+sb.toString());
		myPotentialCustomerService.updatePotCusInfo(sb.toString());
		String sqlup1="update OCRM_F_SE_CALLREPORT p set p.create_user='"+accountName+"' where p.cust_id in ("+cusId+")";
		myPotentialCustomerService.updatePotCusInfo(sqlup1);
		//同时提醒表中插入提醒记录
		List<Object> list=getResultStr("select d.cus_name from ACRM_F_CI_POT_CUS_COM d where d.cust_type='2' and d.cus_id in("+cusId+")");
		//邮件提醒客户经理
		doEmailRemind(custMgr,"个金潜在客户"+list+"已分派给您，请及时处理，谢谢！");
		String time1=new SimpleDateFormat("yyyy-MM-dd").format(newdate);
		String sqlRemind="insert into OCRM_F_WP_REMIND (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) values(SEQ_ID.NEXTVAL,'"+accountName+"',to_date('"+time1+"','yyyy-mm-dd')+1,'个金潜在客户"+list+"已分派给您，请及时处理，谢谢！','1')";
		updateRemindsql(sqlRemind);
	  	
	//}
	
	
   	//myPotentialCustomerService.fpLatentInfo(request);
		return new DefaultHttpHeaders("success");
   }
   
   /**
    * 恢复已删除潜在客户前校验  是否已经转为正式客户
    */
   public void huifubeforCheck(){
	   ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String cusId = request.getParameter("cusId");
		List<String> list = new ArrayList<String>();
		String types="";
		String type="";
		if(cusId.contains(",")){
			  String[] custIds = cusId.split(",");
			  for(String custId:custIds){
				  List<Object[]> listResult=new ArrayList<Object[]>();
				  listResult =myPotentialCustomerService.selectBySql("select pc.cus_name,pc.cert_type,pc.cert_code,pc.call_no from ACRM_F_CI_POT_CUS_COM pc where   pc.cus_id="+custId+"");
					Object[] listResults=null;
					if(listResult!=null&&listResult.size()>0){
						 listResults=listResult.get(0);
					}
					String custnamev = (String) listResults[0];
					String identtypev = (String) listResults[1]==null?"":(String) listResults[1];
					String identnov = (String) listResults[2]==null?"":(String) listResults[2];
					if("null".equals(identtypev)){
						identtypev="";
					}
					if("null".equals(identnov)){
						identnov="";
					}
					String callnov = (String) listResults[3];
					
					if(custnamev!=null&&!"".equals(custnamev)&&identtypev!=null&&!"".equals(identtypev)&&identnov!=null&&!"".equals(identnov)){
						String sql6 ="select cust_id from acrm_f_ci_customer c where c.cust_name='"+custnamev+"' and c.ident_type='"+identtypev+"' and c.ident_no='"+identnov+"' and c.potential_flag='0' and c.cust_type='2'";
						List<Object> list1=validationNewLatentCustomer(sql6,1);
						if(list1!=null&&list1.size()>0){
							types="1";
							list.add(types);
						}
					}else{
						String sql6 =" select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id   where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace('"+callnov+"',substr('"+callnov+"',0,instr('"+callnov+"','-',1,2))) and c.cust_name='"+custnamev+"' ";
						List<Object> list2=validationNewLatentCustomer(sql6,1);
						if(list2!=null&&list2.size()>0){
							types="1";
							list.add(types);
					    }
					}
			  }
			}else{
				List<Object[]> listResult=new ArrayList<Object[]>();
				  listResult =myPotentialCustomerService.selectBySql("select pc.cus_name,pc.cert_type,pc.cert_code,pc.call_no from ACRM_F_CI_POT_CUS_COM pc where   pc.cus_id="+cusId+"");
					Object[] listResults=null;
					if(listResult!=null&&listResult.size()>0){
						 listResults=listResult.get(0);
					}
					String custnamev = (String) listResults[0];
					String identtypev = (String) listResults[1]==null?"":(String) listResults[1];
					String identnov = (String) listResults[2]==null?"":(String) listResults[2];
					if("null".equals(identtypev)){
						identtypev="";
					}
					if("null".equals(identnov)){
						identnov="";
					}
					String callnov = (String) listResults[3];
					
					if(custnamev!=null&&!"".equals(custnamev)&&identtypev!=null&&!"".equals(identtypev)&&identnov!=null&&!"".equals(identnov)){
						String sql6 ="select cust_id from acrm_f_ci_customer c where c.cust_name='"+custnamev+"' and c.ident_type='"+identtypev+"' and c.ident_no='"+identnov+"' and c.potential_flag='0' and c.cust_type='2'";
						List<Object> list1=validationNewLatentCustomer(sql6,1);
						if(list1!=null&&list1.size()>0){
							types="1";
							list.add(types);
						}
					}else{
						String sql6 =" select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id   where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace('"+callnov+"',substr('"+callnov+"',0,instr('"+callnov+"','-',1,2))) and c.cust_name='"+custnamev+"' ";
						List<Object> list2=validationNewLatentCustomer(sql6,1);
						if(list2!=null&&list2.size()>0){
							types="1";
							list.add(types);
					    }
					}
			}
		if(list.contains("1")){
    		type="1";
    	}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		this.setJson(map);
   }
	
   
   //回收
   public DefaultHttpHeaders backReceiveLatentCus() throws Exception{
	   try {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String cusId = request.getParameter("cusId");
		String userId=null;//当前用户ID
		String userName = auth.getUsername();//当前用户名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String orgId = null;//当前用户的机构编号
		//String orgName = auth.getUnitName();//当前用户的机构名称
		String custMgr = request.getParameter("custMgr");
		String orgId1 = request.getParameter("orgId");
		String sql2="";
		String sql3="";
		String sqlRemind="";
		/*if("".equals(custMgr)&&"".equals(orgId1)){
			 userId=auth.getUserId();//当前用户ID
		   	 orgId = auth.getUnitId();//当前用户的机构编号
		}else{
			userId=custMgr;
			orgId=orgId1;
		}*/
		if("".equals(custMgr)){
			 userId=auth.getUserId();//当前用户ID
		   	 orgId = auth.getUnitId();//当前用户的机构编号
		}else{
			userId=custMgr;
			 orgId = auth.getUnitId();//当前用户的机构编号
		}
  //记录回收历史
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map map = new HashMap<String, Object>();
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
		Date newdate=new Date();
		String time = sdfs.format(newdate);
		String time1=new SimpleDateFormat("yyyy-MM-dd").format(newdate);
		if(cusId.contains(",")){
		  String[] custIds = cusId.split(",");
		  for(String custId:custIds){
			  String custName = getCustNameByCustId(custId);
			  String custMgrBefor=getFpBeforeCusMgr(custId);
			   sql2="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,cust_mgr_before,cust_mgr_after,mover_date) values("+custId+",'"+custName+"','2','"+userId+"','"+custMgrBefor+"','"+userId+"','"+time+"')";
			   myPotentialCustomerService.updatePotCusInfo(sql2);
			   map.put(custId, custMgrBefor);
		       list.add(map);
		     //同时提醒表中插入提醒记录
			    sqlRemind="insert into OCRM_F_WP_REMIND (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) values(SEQ_ID.NEXTVAL,'"+custMgrBefor+"',to_date('"+time1+"','yyyy-mm-dd')+1,'个金潜在客户["+custName+"]已被回收！','1')";
			    updateRemindsql(sqlRemind);
			   
		  }
		  //同时提醒表中插入提醒记录
		 
		}else{
			String custName = getCustNameByCustId(cusId);
			 String custMgrBefor=getFpBeforeCusMgr(cusId);
			 sql3="insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,cust_mgr_before,cust_mgr_after,mover_date) values("+cusId+",'"+custName+"','2','"+userId+"','"+custMgrBefor+"','"+userId+"','"+time+"')";
			 myPotentialCustomerService.updatePotCusInfo(sql3);
			 map.put(cusId, custMgrBefor);
		       list.add(map);
		    //同时提醒表中插入提醒记录
		    sqlRemind="insert into OCRM_F_WP_REMIND (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) values(SEQ_ID.NEXTVAL,'"+custMgrBefor+"',to_date('"+time1+"','yyyy-mm-dd')+1,'个金潜在客户["+custName+"]已被回收！','1')";
		    updateRemindsql(sqlRemind);
		}
		
		String sql="update acrm_f_ci_pot_cus_com  set cust_mgr='"+userId+"', main_br_id='"+orgId+"', back_state ='0',mover_user='"+userId+"',mover_date=null,default_receive_date=null,actual_receive_date=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'),OPERATE_TIME=systimestamp where  cus_id in ("+cusId+")";
		myPotentialCustomerService.updatePotCusInfo(sql);
		
		for (Map<String, Object> m : list)
	    {
	      for (String k : m.keySet())
	      {
	        String namestr=getCustNameByCustId(k);
	      //邮件提醒客户经理
	        doEmailRemind((String) m.get(k),"个金潜在客户["+namestr+"]已被回收，谢谢！");
	      }
	    }
		  String sqlup1="update OCRM_F_SE_CALLREPORT p set p.create_user='"+userId+"' where p.cust_id in ("+cusId+")";
		  myPotentialCustomerService.updatePotCusInfo(sqlup1);
		 
	} catch (Exception e) {
		e.printStackTrace();
	}
   	
  
	   return new DefaultHttpHeaders("success");
   }
   
   
   
   public Map<String,String> getCustMgrInfo(String custMgr){
   	Map<String,String> map = new HashMap<String, String>();
   	try{
   		String upOgrId="";
   		String upOrgName="";
   		String orgId="";
   		String orgName="";
   		String accountName="";
   		String userName="";
	    	String sql="select t.org_id as up_org_id, t.org_name as up_org_name, e.org_id,e.org_name,e.account_name,e.user_name from admin_auth_org t," +
	    			" (select o.up_org_id,o.org_id,o.org_name,a.account_name,a.user_name from admin_auth_org o" +
	    			" left join admin_auth_account a" +
	    			" on o.org_id = a.org_id" +
	    			" where a.account_name='"+custMgr+"'" +
	    			" ) e where t.org_id = e.up_org_id";
	    	Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				 upOgrId = result.getString("up_org_id");
				 upOrgName = result.getString("up_org_name");
				 orgId = result.getString("org_id");
				 orgName= result.getString("org_name");
				 accountName = result.getString("account_name");
				 userName = result.getString("user_name");
			}
			map.put("upOgrId", upOgrId);
			map.put("upOrgName", upOrgName);
			map.put("orgId", orgId);
			map.put("orgName", orgName);
			map.put("accountName", accountName);
			map.put("userName", userName);
			log.info("getCustMgrInfo upOgrId: "+upOgrId+" upOrgName :"+upOrgName+" orgId :"+orgId+" orgName: "+orgName+" accountName： "+accountName+" userName:"+userName);
   	}catch(Exception e){
   		e.printStackTrace();
   	}
   	return map;
   }
   
   //归属是机构
   public Map<String,String> getOrgInfo(String custMgr){
		Map<String,String> map = new HashMap<String, String>();
		try{
			String upOgrId="";
			String upOrgName="";
			String orgId="";
			String orgName="";
			String sql="select  a.org_id as up_org_id,a.org_name as up_org_name,e.org_id ,e.org_name from admin_auth_org a ," +
					" (select org_id,org_name,up_org_id from admin_auth_org o where org_id='"+custMgr+"') e where a.org_id=e.up_org_id";
			Connection  connection = ds.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()){
				 upOgrId = result.getString("up_org_id");
				 upOrgName = result.getString("up_org_name");
				 orgId = result.getString("org_id");
				 orgName= result.getString("org_name");
			}
			map.put("upOgrId", upOgrId);
			map.put("upOrgName", upOrgName);
			map.put("orgId", orgId);
			map.put("orgName", orgName);
			log.info("getOrgInfo upOgrId: "+upOgrId+" upOrgName :"+upOrgName+" orgId :"+orgId+" orgName: "+orgName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
  
   
   
   //根据条件查询
   public List<Object> getResultStr(String sql){
   	List<Object> List = new ArrayList<Object>();
   	Connection  connection=null;
   	Statement stmt=null;
   	ResultSet result=null;
   	try{
  				 connection = ds.getConnection();
  				 stmt = connection.createStatement();
  				 result = stmt.executeQuery(sql);
  				String ResultStr="";
  			    while (result.next()){
  			    	ResultStr = result.getString(1);
  			    	List.add(ResultStr);
  			    }
  			 return List;
  		}catch(Exception e){
  			e.printStackTrace();
  		}finally{
  			JdbcUtil.close(result, stmt, connection);
  		}
		return null;
      }
   
   //执行添加修改语句
   public void updateRemindsql(String sql){
   	Connection  connection=null;
   	Statement stmt=null;
   	ResultSet result=null;
   	try{
  				 connection = ds.getConnection();
  				 connection.setAutoCommit(false);
  				 stmt = connection.createStatement();
  				 stmt.executeUpdate(sql);
  				 connection.commit();
  		}catch(Exception e){
  			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
  			e.printStackTrace();
  		}finally{
  			JdbcUtil.close(result, stmt, connection);
  		}
      }
   
   /**
  	 * 发起工作流
  	 * */
  	public void commitApproval() throws Exception{
  	  	try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			String cusId =  request.getParameter("cusId");
			String deleteReason =  request.getParameter("deleteReason");
			String deleteNote =  request.getParameter("deleteNote");
			 Date date = new Date();
			   	 SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			   	 String p = sdf.format(date);
			   	 String instanceid = "CUSDEL"+"_"+cusId+"_"+p;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
				 String jobName = "个金潜在客户删除复核_"+cusId;//自定义流程名称
				 myPotentialCustomerService.initWorkflowByWfidAndInstanceid("130", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
				 String nextNode = "130_a4";
				 String sql="update acrm_f_ci_pot_cus_com  set delete_cust_state='1', delete_reason='"+deleteReason+"',delete_note='"+deleteNote+"'  where  cus_id='"+cusId+"'";
					myPotentialCustomerService.updatePotCusInfo(sql);
			     response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"130_a3\",\"nextNode\":\""+nextNode+"\"}");
					response.getWriter().flush();
		} catch (Exception e) {
			throw new BizException(0, 1, "1002",e.getMessage());
			//e.printStackTrace();
		}
  	}
  	
  	/**
  	 * 删除审批待办界面查询
  	 * @return
  	 */
  	public HttpHeaders queryLatentById() {
   		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String cusId = request.getParameter("cusId");
   			StringBuilder sb = new StringBuilder();
   			sb.append(" select d.CUS_ID,d.CUS_NAME,d.CALL_NO,d.CUST_MGR,ac.user_name CUST_MGR_NAME,ao.org_name MAIN_BR_NAME, FUN_TRANS_CODES(d.mkt_activitie) MKT_ACTIVITIE_V,d.MKT_ACTIVITIE,a.USER_NAME MOVER_USER,");
   			sb.append(" case when d.STATE = '0' then '正式的潜在客户' else '无效的潜在客户' end STATE,");
   			sb.append(" case when d.BACK_STATE='3' then '新建' when  d.BACK_STATE='0' then '已回收' when  d.BACK_STATE='2' then '已分派' else '未回收' end BACK_STATE,d.DELETE_NOTE,l.f_value SOURCE_CHANNEL, ll.f_value DELETE_REASON");
   			sb.append(" from ACRM_F_CI_POT_CUS_COM d ");
   			sb.append(" left join ADMIN_AUTH_ACCOUNT ac on d.CUST_MGR = ac.account_name");
   			sb.append(" left join ADMIN_AUTH_ACCOUNT a on d.MOVER_USER = a.account_name");
   			sb.append(" left join ADMIN_AUTH_ACCOUNT M on d.CUST_MGR = M.account_name");
   			sb.append(" left join ADMIN_AUTH_ORG ao on d.main_br_id = ao.org_id");
   			sb.append(" left join ocrm_sys_lookup_item l on ");
   			sb.append(" l.f_code=d.SOURCE_CHANNEL and l.f_lookup_id='XD000353' ");
   			sb.append(" left join ocrm_sys_lookup_item ll on ");
   			sb.append(" ll.f_code=d.DELETE_REASON and ll.f_lookup_id='LATENT_DELETE_REASON' ");
   			sb.append(" where d.cus_id='"+cusId+"'");
   			QueryHelper queryHelper = new QueryHelper(sb.toString(), ds.getConnection());
   			if(this.json!=null){
           		this.json.clear();
   			}else {
           		this.json = new HashMap<String,Object>(); 
           	}
   			this.json.put("json",queryHelper.getJSON());
   		} catch (Exception e) {
   			e.printStackTrace();
   			throw new BizException(1,2,"1002",e.getMessage());
   		}
   		return new DefaultHttpHeaders("success").disableCaching();
   	}
  	
  	/**
  	 * 查询营销活动数据源
  	 * @return
  	 */
  	public HttpHeaders querymktActivitys() {
   		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			StringBuilder sb = new StringBuilder();
   			sb.append(" select m.mkt_acti_code, m.mkt_acti_name from OCRM_F_MK_MKT_ACTIVITY m where m.mkt_app_state='3' and m.mkt_acti_code is not null ");
   			QueryHelper queryHelper = new QueryHelper(sb.toString(), ds.getConnection());
   			if(this.json!=null){
           		this.json.clear();
   			}else {
           		this.json = new HashMap<String,Object>(); 
           	}
   			this.json.put("json",queryHelper.getJSON());
   		} catch (Exception e) {
   			e.printStackTrace();
   			throw new BizException(1,2,"1002",e.getMessage());
   		}
   		return new DefaultHttpHeaders("success").disableCaching();
   	}
  	
  	 /**
     * 根据客户编号获取客户名称
     * @param custId
     * @return
     * @throws Exception
     */
    public String getCustNameByCustId(String custId){
    	String custName="";
    	Connection  connection=null;
   		Statement stmt=null;
   		ResultSet result=null;
    	try{
    		 connection = ds.getConnection();
		     stmt = connection.createStatement();
    		String sql="select cus_name from acrm_f_ci_pot_cus_com  where cus_id ="+custId+"";
    		 result = stmt.executeQuery(sql);
    		while(result.next()){
    			custName=result.getString("cus_name");
    		}	
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
	   		JdbcUtil.close(result, stmt, connection);
	   		
	   	}
    	
    	return custName;
    }
    
    //删除前验证
    public void  delBeforCheck(){
       	try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			String custId = request.getParameter("cusId")==null?"":request.getParameter("cusId");
			String conclusionstr="";  //删除审批状态 
			String spandaysstr="";    //创建时间大于3天不允许删除
					List<Object>  conclusionlist=getResultStr("select pc.delete_cust_state from acrm_f_ci_pot_cus_com pc where pc.cus_id in ('"+custId+"')");
					     if(conclusionlist!=null &&conclusionlist.size()>0){
					    	 if(conclusionlist.contains("1")){   //删除审批状态 1 已经提交审批
			   				  conclusionstr="2";
			   			   }
					     }
				   List<Object>  spandayslist=getResultStr("select case when floor(to_number(sysdate-to_date(pc.input_date,'yyyy-mm-dd hh24:mi:ss')))>3 then '3' else '1' end spanDays  from acrm_f_ci_pot_cus_com pc where pc.cus_id in ('"+custId+"')");
					     if(spandayslist!=null &&spandayslist.size()>0){
					    	 if(spandayslist.contains("3")){  //创建时间大于3天不允许删除
			   				  spandaysstr="2";
			   			   }
					     }
			log.info("delBeforCheck conclusionstr :"+conclusionstr);
			response.getWriter().write("{\"conclusionstr\":\""+conclusionstr+"\",\"spandaysstr\":\""+spandaysstr+"\"}");
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
       }
  //删除前验证
    public void  editeBeforCheck(){
       	try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
			String custId = request.getParameter("cusId")==null?"":request.getParameter("cusId");
			String spandaysstr="";    //创建时间大于3天不允许删除
				   List<Object>  spandayslist=getResultStr("select case when floor(to_number(sysdate-to_date(pc.input_date,'yyyy-mm-dd hh24:mi:ss')))>3 then '3' else '1' end spanDays  from acrm_f_ci_pot_cus_com pc where pc.cus_id in ('"+custId+"')");
					     if(spandayslist!=null &&spandayslist.size()>0){
					    	 if(spandayslist.contains("3")){  //创建时间大于3天不允许
			   				  spandaysstr="1";
			   			   }
					     }
			log.info("delBeforCheck conclusionstr :"+spandaysstr);
			response.getWriter().write("{\"spandaysstr\":\""+spandaysstr+"\"}");
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
       }
    
    /**
     * 批量导入待处理清单 再次创建
     * @return
     * @throws BizException
     */
    public DefaultHttpHeaders createargen() throws BizException{
		  ActionContext ctx = ActionContext.getContext();
	      request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	      String strphon="";
	      String custId="";
	      String custIdtemp = request.getParameter("cusId")==null?"":request.getParameter("cusId");//客户号
	      String custZhName = request.getParameter("cusName")==null?"":request.getParameter("cusName");//客户名称
	      String custTyp = request.getParameter("custType")==null?"":request.getParameter("custType");//客户类型
	      String linkUser = request.getParameter("attenName")==null?"":request.getParameter("attenName");//联系人
	      String zipcode = request.getParameter("zipcode")==null?"":request.getParameter("zipcode");//邮编
	      String sourceChannel = request.getParameter("sourceChannel")==null?"":request.getParameter("sourceChannel");//来源渠道
	      String mktActivitie = request.getParameter("mktActivitie")==null?"":request.getParameter("mktActivitie");//营销活动
	      String cusNationality = request.getParameter("cusNationality")==null?"":request.getParameter("cusNationality");//国籍
	      String refereesId = request.getParameter("refereesId")==null?"":request.getParameter("refereesId");//推荐人
	      String certType = request.getParameter("certType")==null?"":request.getParameter("certType");//证件类型
	      String linkPhone = request.getParameter("callNo")==null?"":request.getParameter("callNo");//手机号码
	      String phoneAreaCode = request.getParameter("phoneAreaCode")==null?"":request.getParameter("phoneAreaCode");//电话区号
	      strphon=linkPhone=phoneAreaCode+"-#-"+linkPhone;
	      linkPhone=strphon;
	      String contmethInfo = request.getParameter("contmethInfo")==null?"无":request.getParameter("contmethInfo");//座机号
	      String custStat = request.getParameter("custStat")==null?"":request.getParameter("custStat");//客户状态默认‘潜在’
	      String jobType = request.getParameter("jobType")==null?"":request.getParameter("jobType");
	      String industType = request.getParameter("industType")==null?"":request.getParameter("industType");
	      String recordSession = request.getParameter("recordSession")==null?"":request.getParameter("recordSession");//沟通话术
	      String cusEmail = request.getParameter("cusEmail")==null?"":request.getParameter("cusEmail");//Email
	      String cusWechatid = request.getParameter("cusWechatid")==null?"":request.getParameter("cusWechatid");//微信号
	      if(custStat.length()<1){
	    	custStat="2";  
	      };
	      String certNum = request.getParameter("certCode")==null?"":request.getParameter("certCode");//证件号码
	      String addr = request.getParameter("cusAddr")==null?"":request.getParameter("cusAddr");//通讯地址
	      
	      String checkStr = myPotentialCustomerService.saveLatent(custId, custZhName, custTyp, linkUser, zipcode, sourceChannel,mktActivitie,cusNationality,refereesId,certType, linkPhone, contmethInfo, addr, custStat,certNum, jobType, industType,recordSession,cusEmail,cusWechatid);
	   
	      String delesql="delete from acrm_f_ci_pot_cus_createtemp te where te.cus_id='"+custIdtemp+"' ";
			myPotentialCustomerService.updatePotCusInfo(delesql);
	      if(null==checkStr){
	    		return new DefaultHttpHeaders("success");
	     }else{
         throw new BizException(1, 2, "1000", checkStr);
       }		
      }
}
