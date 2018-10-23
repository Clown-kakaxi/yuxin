package com.yuchengtech.bcrm.sales.message.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bcrm.sales.message.model.OcrmFMmSysMsg;
import com.yuchengtech.bcrm.sales.message.service.OcrmFMmSysMsgService;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpRemindMsg1;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpRemindMsgService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.service.CommonQueryService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * 
 * 渠道自动营销管理
 * @author luyy
 * @since 2014-2-26
 */

@Action("/ocrmFMmSysMsg")
public class OcrmFMmSysMsgAction extends CommonAction {
	
	private static final long serialVersionUID = -7585128487880869302L;

	private static Logger log = LoggerFactory.getLogger(OcrmFMmSysMsgAction.class);
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	 private  OcrmFMmSysMsgService  ocrmFMmSysMsgService;
	 
	 @Autowired
	 private  OcrmFWpRemindMsgService ocrmFWpRemindMsgService;
	 
	 @Autowired
	 private CommonQueryService cqs;
	 
	 @Autowired
	 private TargetCusSearchService targetCusSearchService;
	 
	 private Map<String, Object> map = new HashMap<String, Object>();
	 
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFMmSysMsg(); 
			setCommonService(ocrmFMmSysMsgService);
			//新增修改删除记录是否记录日志,默认为false，不记录日志
			needLog=true;
		}

	/**
	 *信息查询SQL
	 */
	public void prepare() {
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String flag=request.getParameter("flag");//是否对私客户视图查询调用
    	 String custId=request.getParameter("custId");
    	 String operate=request.getParameter("operate");//是否复核审批时调用
    	 String id=request.getParameter("instanceId");
		 StringBuilder sb = new StringBuilder("");
		  if("0".equals(flag)){
			  sb.setLength(0);
			  sb.append("SELECT T.ID, T.CUST_ID, T.PROD_ID, T.PROD_NAME,T.MODEL_TYPE,T.MODEL_NAME,T.MESSAGE_REMARK,T.CRT_DATE,T.USER_NAME,NVL(IS_FEEDBACK,0) as IS_FEEDBACK,T.FEEDBACK_CONT  FROM OCRM_F_MM_SYS_MSG T WHERE T.APPROVE_STATE='2' and T.CUST_ID='"+custId+"'");
		  }else if("review".equals(operate) &&"MK".equals(id.split("_")[0])){
			  sb.setLength(0);
			  sb.append("select * from OCRM_F_MM_SYS_MSG t where t.msgsendtime='"+id.split("_")[1]+"' and  t.approve_state='0'");
		  }else{
			  sb.setLength(0);
			  sb.append(" select * from OCRM_F_MM_SYS_MSG where user_id='"+auth.getUserId()+"'  ");
		  }
		  for(String key:this.getJson().keySet()){
	            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
	                if(key.equals("CUST_NAME")||key.equals("PROD_ID")||key.equals("CUST_ID")||key.equals("APPROVE_STATE")){
	                	if(key.equals("PROD_ID")){
	                		sb.append(" AND "+"PROD_ID="+""+"'"+this.getJson().get("PROD_ID")+"'");
	                	}else{
		                	sb.append(" AND "+key+" like"+" '%"+this.getJson().get(key)+"%'");

	                	}
	                }else if(key.equals("IS_FEEDBACK")){
	                	if("1".equals(this.getJson().get("IS_FEEDBACK"))){
	                		sb.append(" AND "+"IS_FEEDBACK="+""+"'"+this.getJson().get("IS_FEEDBACK")+"'");
	                	}else{
	                		sb.append(" AND "+"IS_FEEDBACK is null");
	                	}
	                }
	        }
		  }
		this.addOracleLookup("IF_APPROVE", "IF_FLAG");
		this.addOracleLookup("APPROVE_STATE", "APPROVEL_STATUS");
		this.addOracleLookup("MODEL_TYPE", "MODEL_TYPE");
		this.addOracleLookup("MODEL_SOURCE", "MODEL_SOURCE");
		this.setPrimaryKey(" id desc");
		SQL=sb.toString();
		datasource = ds;
		configCondition("CRT_DATE", "=", "CRT_DATE",DataType.Date);
		configCondition("MODEL_SOURCE", "=", "MODEL_SOURCE",DataType.String);
		configCondition("MODEL_TYPE", "=", "MODEL_TYPE",DataType.String);
		configCondition("MODEL_NAME", "like", "MODEL_NAME",DataType.String);
		configCondition("MESSAGE_REMARK", "like", "MESSAGE_REMARK",DataType.String);
		configCondition("USER_NAME", "like", "USER_NAME",DataType.String);
	}
	
	/**
     * 查询模板信息字典
     */
    public String searchMktDic(){
	    ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String code = request.getParameter("code");
	    String sql = "";
		sql = " select ID as key,model_NAME as value from  OCRM_F_MM_SYS_TYPE t where t.catl_code='"+code+"'";
//		if(!"".equals(code)&&code!=null){
//			sql = sql+" where CATL_CODE=( select catl_code from ocrm_f_pd_prod_info where product_id='"+code+"')";
//		}
		map = cqs.excuteQuery(sql.toString(),0,200);
	    this.json = map;
		return "success";
    }
    /**
     * 查询模板适用渠道
     * @throws IOException
     */
    public String searchModelType(){
	    ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
	    String sql = "";
		sql = " select t.model_type from OCRM_F_MM_SYS_TYPE t where t.id="+id+"";
//		if(!"".equals(code)&&code!=null){
//			sql = sql+" where CATL_CODE=( select catl_code from ocrm_f_pd_prod_info where product_id='"+code+"')";
//		}
		map = cqs.excuteQuery(sql.toString(),0,200);
	    this.json = map;
		return "success";
    }
    
    //生成短信信息
    public void getMessage() throws IOException{
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String message = "false";
		String modelId = request.getParameter("modelId");
		String custId = request.getParameter("custId");
		String custName = request.getParameter("custName");
		String prodId = request.getParameter("prodId");
		String prodName = request.getParameter("prodName");
		//首先查询出模板内容
		List<Object[]>  list = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select ID,MODEL_INFO,CATL_CODE from OCRM_F_MM_SYS_TYPE where ID='"+modelId+"'");
		if(list != null && list.size()>0){
			Object[] o = list.get(0);
			message = (o[1]==null)?"false":o[1].toString();
		}
		//根据产品id查询出产品信息
		List<Object[]>  list1 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
//				"prod_start_date,prod_end_date,rate,cost_rate,income_profit from ocrm_f_pd_prod_info where product_id='"+prodId+"'");
		"prod_start_date,prod_end_date,rate,cost_rate from ocrm_f_pd_prod_info where product_id='"+prodId+"'");
		//替换模板中的内容
		if(list1 != null && list1.size()>0){
			Object[] o= list1.get(0);
			message = message.replace("@custName", custName);
			message = message.replace("@productId", prodId);
			message = message.replace("@prodName", prodName);
			message = message.replace("@prodStartDate", (o[0]==null)?"":o[0].toString());
			message = message.replace("@prodEndDate", (o[1]==null)?"":o[1].toString());
			message = message.replace("@rate", (o[2]==null)?"":o[2].toString());
			message = message.replace("@castRate", (o[3]==null)?"":o[3].toString());
//			message = message.replace("@incomeProfit", (o[4]==null)?"":o[4].toString());
			message = message.replace("@manger", auth.getUsername());
		}
		HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(message);
		response.getWriter().flush();
    }
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		try{
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String custId=request.getParameter("custId");
    	 String custName=request.getParameter("custName");
    	 String messageRemark=request.getParameter("messageRemark");
    	 String telNum=request.getParameter("telNum");
    	 String prodName=request.getParameter("prodName");
    	 String prodId=request.getParameter("prodId");
    	 String modelId=request.getParameter("modelId");
    	 String modelType=request.getParameter("modelType");
    	 String modelSource=request.getParameter("modelSource");
    	 String modelName=request.getParameter("modelName");
    	 String mail=request.getParameter("email");
    	 String weixin=request.getParameter("weixin");
    	 String ifApprove=request.getParameter("ifApprove");
    	 ((OcrmFMmSysMsg)model).setCustId(custId);
    	 ((OcrmFMmSysMsg)model).setCustName(custName);
    	 ((OcrmFMmSysMsg)model).setCellNumber(telNum);
    	 ((OcrmFMmSysMsg)model).setMessageRemark(messageRemark);
    	 ((OcrmFMmSysMsg)model).setProdId(prodId);
    	 ((OcrmFMmSysMsg)model).setProdName(prodName);
    	 ((OcrmFMmSysMsg)model).setModelId(modelId);
    	 ((OcrmFMmSysMsg)model).setModelType(modelType);
    	 ((OcrmFMmSysMsg)model).setModelSource(modelSource);
    	 ((OcrmFMmSysMsg)model).setCrtDate(new Date());
    	 ((OcrmFMmSysMsg)model).setIfApprove("N");
    	 ((OcrmFMmSysMsg)model).setUserId(auth.getUserId());
    	 ((OcrmFMmSysMsg)model).setModelName(modelName);
    	 ((OcrmFMmSysMsg)model).setMailAddress(mail);
    	 ((OcrmFMmSysMsg)model).setWxAccount(weixin);
//    	 ((OcrmFMmSysMsg)model).setFrId(auth.getUnitInfo().get("FR_ID").toString());
    	 
    	 
    	 ocrmFMmSysMsgService.save(model);
    	 if(telNum!=null && !telNum.trim().equals("")){
    		 MsgClient.getInstance().process(telNum, messageRemark, "");
    		 //保存发送短信记录
        	 OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
        	 msg.setCellNumber(((OcrmFMmSysMsg)model).getCellNumber());
        	 msg.setCtrDate(new Date());
        	 msg.setSendDate(new Date());
        	 msg.setIfSend("1");
        	 msg.setProdId(((OcrmFMmSysMsg)model).getProdId());
        	 msg.setProdName(((OcrmFMmSysMsg)model).getProdName());
        	 msg.setModelId(((OcrmFMmSysMsg)model).getModelId());
        	 msg.setMessageRemark(((OcrmFMmSysMsg)model).getMessageRemark());
        	 msg.setCustId(((OcrmFMmSysMsg)model).getCustId());
        	 msg.setCustName(((OcrmFMmSysMsg)model).getCustName());
        	 msg.setUserId(auth.getUserId());
        	 ocrmFWpRemindMsgService.save(msg);
    	 }
    	 if(mail!=null && !mail.trim().equals("")){
    		 MailClient.getInstance().sendMsg(mail, "富邦华一银行"+prodName+"推荐", messageRemark);
    		 //保存发送邮件记录
        	 OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
        	// msg.setCellNumber(((OcrmFMmSysMsg)model).getCellNumber());
        	 msg.setCtrDate(new Date());
        	 msg.setSendDate(new Date());
        	 msg.setIfSend("2");
        	 msg.setProdId(((OcrmFMmSysMsg)model).getProdId());
        	 msg.setProdName(((OcrmFMmSysMsg)model).getProdName());
        	 msg.setModelId(((OcrmFMmSysMsg)model).getModelId());
        	 msg.setMessageRemark(((OcrmFMmSysMsg)model).getMessageRemark());
        	 msg.setCustId(((OcrmFMmSysMsg)model).getCustId());
        	 msg.setCustName(((OcrmFMmSysMsg)model).getCustName());
        	 msg.setMailAddr(mail);
        	 msg.setUserId(auth.getUserId());
        	 ocrmFWpRemindMsgService.save(msg);
    	 }
    	
		}catch(Exception e){
			e.printStackTrace();
			log.error("营销推送失败："+e.getMessage());
		}
    	 
		return new DefaultHttpHeaders("success");
	}
	
	/**
	 * 发起审批工作流
	 * @throws Exception 
	 * @throws IOException
	 */
	public void initFlow() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
	   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 String custId=request.getParameter("custId");
	   	 String custName=request.getParameter("custName");
	   	 String messageRemark=request.getParameter("messageRemark");
	   	 String telNum=request.getParameter("telNum");
	   	 String prodName=request.getParameter("prodName");
	   	 String prodId=request.getParameter("prodId");
	   	 String modelId=request.getParameter("modelId");
	   	 String modelType=request.getParameter("modelType");
	   	 String modelSource=request.getParameter("modelSource");
	   	 String modelName=request.getParameter("modelName");
	   	 String mail=request.getParameter("email");
	   	 String weixin=request.getParameter("weixin");
	   	 String ifApprove=request.getParameter("ifApprove");
	   	 OcrmFMmSysMsg ocrmFMmSysMsg=new OcrmFMmSysMsg();
	   	 ocrmFMmSysMsg.setCustId(custId);
	   	 ocrmFMmSysMsg.setCustName(custName);
	   	 ocrmFMmSysMsg.setCellNumber(telNum);
	   	 ocrmFMmSysMsg.setMessageRemark(messageRemark);
	   	 ocrmFMmSysMsg.setProdId(prodId);
	   	 ocrmFMmSysMsg.setProdName(prodName);
	   	 ocrmFMmSysMsg.setModelId(modelId);
	   	 ocrmFMmSysMsg.setModelType(modelType);
	   	 ocrmFMmSysMsg.setModelSource(modelSource);
	   	 ocrmFMmSysMsg.setCrtDate(new Date());
	   	 ocrmFMmSysMsg.setIfApprove("Y");
	   	 ocrmFMmSysMsg.setApproveState("0");
	   	 ocrmFMmSysMsg.setUserId(auth.getUserId());
	   	 ocrmFMmSysMsg.setUserName(auth.getUsername());
	   	 ocrmFMmSysMsg.setModelName(modelName);
	   	 ocrmFMmSysMsg.setMailAddress(mail);
	   	 ocrmFMmSysMsg.setWxAccount(weixin);
	     SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 String time = formart.format(new Date());
		 ocrmFMmSysMsg.setMsgsendtime(time);
	   	 ocrmFMmSysMsgService.save(ocrmFMmSysMsg);
	   	 
	   	 JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
	   	// String id =  metadataUtil.getId(ocrmFMmSysMsg).toString();
	   	 String instanceid = "MK_"+time;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
 		 String jobName = "自动营销推送_"+custName;//自定义流程名称
 		 ocrmFMmSysMsgService.initWorkflowByWfidAndInstanceid("44", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
 		 String nextNode = "44_a4";
		 List list = auth.getRolesInfo();
		 for(Object m:list){
			Map map = (Map)m;
			if("R120".equals(map.get("ROLE_CODE"))|| "R125".equals(map.get("ROLE_CODE"))){//总行产品专员、总行财管专员
				nextNode = "44_a10";
				continue ;
			}else if("R202".equals(map.get("ROLE_CODE"))){//区域行长
				nextNode = "44_a15";
				continue ;
			}else if("R201".equals(map.get("ROLE_CODE"))){//分行行长/区域中心主管
				nextNode = "44_a9";
				continue ;
			}else if("R311".equals(map.get("ROLE_CODE"))){//支行行长
				nextNode = "44_a7";
				continue ;
			}else if("R310".equals(map.get("ROLE_CODE"))){//客户经理主管
				nextNode = "44_a5";
				continue ;
			}else if("R303".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){//客户经理
				nextNode = "44_a4";
				continue ;
			}else{
				continue ;
			}
		}	
	  Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("instanceid", instanceid);
	    map1.put("currNode", "44_a3");
	    map1.put("nextNode",  nextNode);
	    this.setJson(map1);
	}
	
//	  /**
//   	 * 流程提交
//   	 * */
//   	public void initFlowJob() throws Exception{
//   	  	ActionContext ctx = ActionContext.getContext();
//   		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//   		String instanceid = request.getParameter("instanceid");
//   		//流程参数，判断节点走向
//   		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//   			Map<String, String> mapParma = new HashMap<String, String>();//参数map
//   			List list = auth.getRolesInfo();
//   			for(Object m:list){
//   				Map map = (Map)m;
//   				/********************注意：此处角色码需要修改**********************/
//   				if("R104".equals(map.get("ROLE_CODE"))){//总行产品专员
//   					mapParma.put("roleLevel", "R104");
//   					break ;
//   				}else if("R105".equals(map.get("ROLE_CODE"))){//总行财管专员
//   					mapParma.put("roleLevel", "R105");
//   					break ;
//   				}else if("R201".equals(map.get("ROLE_CODE"))){//区域行长
//   					mapParma.put("roleLevel", "R201");
//   					break ;
//   				}else if("R301".equals(map.get("ROLE_CODE"))){//支行行长
//   					mapParma.put("roleLevel", "R301");
//   					break ;
//   				}else if("R303".equals(map.get("ROLE_CODE"))){//经理主管
//   					mapParma.put("roleLevel", "R303");
//   					break ;
//   				}else if("R304".equals(map.get("ROLE_CODE"))){//客户经理
//   					mapParma.put("roleLevel", "R304");
//   					break ;
//   				}else{
//   					continue ;
//   				}
//   			}
//   			  
//   			ocrmFMmSysMsgService.wfCompleteJob(instanceid, "44_a3", "", null, mapParma);
//   	}
//	
	
	 public void  saveData2() throws IOException{
		 try{
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 String modelId = request.getParameter("modelId");
			String prodId = request.getParameter("prodId");
			String prodName = request.getParameter("prodName"); 
			String groupId = request.getParameter("groupId"); 
			String message_p=request.getParameter("message");
			String modelSource=request.getParameter("modelSource");
			String modelType=request.getParameter("modelType");
			String modelName=request.getParameter("modelName");
			String custIds=request.getParameter("custIds");
			StringBuffer buffer=new StringBuffer();
			String[] ids=custIds.split(",");
			for(int i=0;i<ids.length;i++){
				buffer.append("'"+ids[i]+"',");
			}
			String ids_r=buffer.substring(0,buffer.length()-1);
			
			List<Object[]> list = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select  id,CUST_FROM from " +
					"OCRM_F_CI_BASE where cust_base_number='"+groupId+"'");
			
			List<Object[]>  listall = null;
			if(list!=null&&list.size()>0){
				Object[] o = list.get(0);
				String custForm = (o[1]==null)?"":o[1].toString();
				if("2".equals(custForm)){//动态客户群，需要通过客户群筛选条件查询客户
					List<Object[]> list1 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select ss_col_item,ss_col_op,ss_col_value,ss_col_join " +
							"from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+groupId+"')");
					if(list1!=null&&list1.size()>0){
						JSONArray jaCondition = new JSONArray();
						String	radio = "true";
						for(int i=0;i<list1.size();i++){
							Object[] oo = list1.get(i);
							radio = (oo[3]==null)?"":oo[3].toString();
							Map map = new HashMap<String,Object>();
			    			map.put("ss_col_item", (oo[0]==null)?"":oo[0].toString());
			    			map.put("ss_col_op", (oo[1]==null)?"":oo[1].toString());
			    			map.put("ss_col_value", (oo[2]==null)?"":oo[2].toString());
							jaCondition.add(map);
						}
						//客户筛选
						String res = targetCusSearchService.generatorSql(jaCondition,radio);	
						listall = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
								"cust_id,cust_name as cust_zh_name,cust_type as cust_typ,cust_stat,linkman_name as link_user  from  b.cust_typ where cust_id in ( select t.cust_id from ("+res+") t)");
		    			
					}
				}else {
					//静态客户群，直接关联客户查询
					listall = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam("select t2.cust_id,t2.personal_name,t2.mobile_phone,t2.email,t2.weixin  from ocrm_f_ci_relate_cust_base t1 left join ACRM_F_CI_PERSON t2 on t1.cust_id=t2.cust_id "+
							" where t1.cust_id in("+ids_r+")");
				}
			
			}
			
			//根据产品id查询出产品信息
			List<Object[]>  list1 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
			"prod_start_date,prod_end_date,rate,cost_rate from ocrm_f_pd_prod_info where product_id='"+prodId+"'");
			//替换模板中的内容
			Object[] product = null;
			if(list1 != null && list1.size()>0){
				product= list1.get(0);
			}
			
			String message0 = "";
			List<Object[]>  list2 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select ID,MODEL_INFO,CATL_CODE from OCRM_F_MM_SYS_TYPE where ID='"+modelId+"'");
			Object[] oo = null;
			if(list2 != null && list2.size()>0){
				oo = list2.get(0);
				message0 = (oo[1]==null)?"false":oo[1].toString();
			}
			
			//查询客户
			
			String custId = "";
			String custName = "";
			String number = "";
			String email="";
			String weixin="";
			int num = 0;
			if(listall != null && listall.size()>0){
				for(int i=0;i<listall.size();i++){
					Object[] cust = listall.get(i);
					custId = (cust[0]==null)?"":cust[0].toString();
					custName = (cust[1]==null)?"":cust[1].toString();
					number = (cust[2]==null)?"":cust[2].toString();
					email=(cust[3]==null)?"":cust[3].toString();
					weixin=(cust[4]==null)?"":cust[4].toString();
					String message = "";
					if(modelSource.equals("自定义")){
						message=message_p;
					}else{
						message = message0;
						message = message.replace("@custName", custName);
						message = message.replace("@productId", prodId);
						message = message.replace("@prodName", prodName);
						message = message.replace("@prodStartDate", (product[0]==null)?"":product[0].toString());
						message = message.replace("@prodEndDate", (product[1]==null)?"":product[1].toString());
						message = message.replace("@rate", (product[2]==null)?"":product[2].toString());
						message = message.replace("@castRate", (product[3]==null)?"":product[3].toString());
						message = message.replace("@manger", auth.getUsername());
					}
					OcrmFMmSysMsg msg1 = new OcrmFMmSysMsg();
					msg1.setCustId(custId);
					msg1.setCustName(custName);
					msg1.setCellNumber(number);
					msg1.setMailAddress(email);
					msg1.setWxAccount(weixin);
					msg1.setProdId(prodId);
					msg1.setProdName(prodName);
					msg1.setModelId(modelId);
					msg1.setModelName(modelName);
					msg1.setMessageRemark(message);
					msg1.setModelType(modelType);
					msg1.setModelSource(modelSource);
					msg1.setIfApprove("N");
					msg1.setUserId(auth.getUserId());
					msg1.setCrtDate(new Date());
					 ocrmFMmSysMsgService.save(msg1);
					 if(msg1.getCellNumber()!=null && !msg1.getCellNumber().trim().equals("")){
						 MsgClient.getInstance().process(msg1.getCellNumber(), msg1.getMessageRemark(), "");
						 //保存发送短信记录
				    	 OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
				    	 msg.setCellNumber(msg1.getCellNumber());
				    	 msg.setCtrDate(new Date());
				    	 msg.setSendDate(new Date());
				    	 msg.setIfSend("1");
				    	 msg.setProdId(msg1.getProdId());
				    	 msg.setProdName(msg1.getProdName());
				    	 msg.setModelId(msg1.getModelId());
				    	 msg.setMessageRemark(msg1.getMessageRemark());
				    	 msg.setCustId(msg1.getCustId());
				    	 msg.setCustName(msg1.getCustName());
				    	 msg.setUserId(auth.getUserId());
					     
				    	 ocrmFWpRemindMsgService.save(msg);
					 }
					 if(msg1.getMailAddress()!=null && !msg1.getMailAddress().trim().equals("")){
						 MailClient.getInstance().sendMsg(msg1.getMailAddress(), "富邦华一银行"+prodName+"推荐", msg1.getMessageRemark());
						 //保存发送邮件记录
				    	 OcrmFWpRemindMsg1 msg = new OcrmFWpRemindMsg1();
				    	 //msg.setCellNumber(msg1.getCellNumber());
				    	 msg.setCtrDate(new Date());
				    	 msg.setSendDate(new Date());
				    	 msg.setIfSend("2");
				    	 msg.setProdId(msg1.getProdId());
				    	 msg.setProdName(msg1.getProdName());
				    	 msg.setModelId(msg1.getModelId());
				    	 msg.setMailAddr(msg1.getMailAddress());
				    	 msg.setMessageRemark(msg1.getMessageRemark());
				    	 msg.setCustId(msg1.getCustId());
				    	 msg.setCustName(msg1.getCustName());
				    	 msg.setUserId(auth.getUserId());
					     
				    	 ocrmFWpRemindMsgService.save(msg);
					 }
			    	 
			    	
			    	 
			    	 num++;
				}
			}
			
			HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
			response.getWriter().write(num);
			response.getWriter().flush();
		 }catch(Exception e){
			 e.printStackTrace();
			 log.error("推销营销信息失败："+e.getMessage());
		 }
			
	}
		
	/**
	 * 客户群推送
	 */
    public void send(){
	    ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
		//产品名
		String prodName = request.getParameter("prodName");
		
		//推送渠道
		String modelTypeStr = request.getParameter("modelType");		
		String[] modelTypeArr = modelTypeStr.split(",");
		
		//营销用语message_p=@custName先生/女士你好，我行将于@prodStartDate发售新产品@prodName，请关注。联系客户经理：@manger。
		String message_p = request.getParameter("message");
		message_p = message_p.replace("@prodName", prodName);
		
		//客户编号
		String custIds = request.getParameter("custIds");
		custIds = custIds.substring(0, custIds.length() - 1);
		String[] cids = custIds.split(",");
		
		//通过客户编号查询客户经理
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		//客户ID-客户经理map
		HashMap<String, String> custMgrMap = new HashMap<String, String>();
		try {
			conn = ds.getConnection();
			statement = conn.createStatement();
			String sql = "select t.CUST_ID, t.BELONG_CUST_MGR_NAME from OCRM_F_CI_RELATE_CUST_BASE t where t.cust_id in(" + custIds + ")";
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				custMgrMap.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			//关闭jdbc连接
			JdbcUtil.close(rs, statement, conn);
		}
		System.out.println("客户经理Map：" + custMgrMap);

		//客户ID-邮箱map
		HashMap<String, String> custMailMap = new HashMap<String, String>();		
		try {
			conn = ds.getConnection();
			statement = conn.createStatement();
			String sql = "select t.CUST_ID, t.EMAIL from ACRM_F_CI_PERSON t where t.cust_id in(" + custIds + ")";
			System.out.println(sql);
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				custMailMap.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			//关闭jdbc连接
			JdbcUtil.close(rs, statement, conn);
		}
		System.out.println("客户邮箱Map：" + custMailMap);
		
		//客户名称
		String personalNames = request.getParameter("personalNames");
		String[] pns = personalNames.split(",");		
		//客户ID-客户名称map
		HashMap<String, String> custMap = new HashMap<String, String>();
		for (int i = 0; i < cids.length; i++) {
			custMap.put(cids[i], pns[i]);
		}
		
		//客户手机号
		String mobilePhones = request.getParameter("mobilePhones");
		String[] mps = mobilePhones.split(",");
		
		//1表示短信渠道，2表示邮件渠道
		for (int i = 0; i < modelTypeArr.length; i++) {
			if(modelTypeArr[i].equals("1")){
				//遍历手机号，如果为空则不调用发送
				for (int j = 0; j < mps.length; j++) {
					if(mps[j] != null && !mps[j].equals("")){
						message_p = message_p.replace("@custName", pns[j]);
						message_p = message_p.replace("@manger", custMgrMap.get(cids[j]));
						System.out.println(message_p);
						//调用短信发送接口
						try {
							MsgClient.getInstance().process(mps[i], message_p, "");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}				
			}else if(modelTypeArr[i].equals("2")){
				//迭代客户邮箱map，如果邮箱为空，则不调用发送程序
				for(Map.Entry<String, String> entry : custMailMap.entrySet()){
					if(entry.getValue() != null && !entry.getValue().equals("")){
						message_p = message_p.replace("@custName", custMap.get(entry.getKey()));
						message_p = message_p.replace("@manger", custMgrMap.get(entry.getKey()));
						//调用短信发送接口
						try {
							//第一个参数邮箱地址，第二个参数邮件主题，第三个参数邮箱正文
							MailClient.getInstance().sendMsg(entry.getValue(), "测试邮件", message_p);
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}
				}			
			}
		}
    }
	 
	 /**
	  * 客户群渠道自动营销-发起审批工作流
	  * @return
	  */
	 public void initFlow2() throws Exception{
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 String modelId = request.getParameter("modelId");
			String prodId = request.getParameter("prodId");
			String prodName = request.getParameter("prodName"); 
			String groupId = request.getParameter("groupId"); 
			String message_p=request.getParameter("message");
			String modelSource=request.getParameter("modelSource");
			String modelType=request.getParameter("modelType");
			String modelName=request.getParameter("modelName");
			String custIds=request.getParameter("custIds");
			StringBuffer buffer=new StringBuffer();
			String[] ids=custIds.split(",");
			for(int i=0;i<ids.length;i++){
				buffer.append("'"+ids[i]+"',");
			}
			String ids_r=buffer.substring(0,buffer.length()-1);
			
			List<Object[]> list = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select  id,CUST_FROM from " +
					"OCRM_F_CI_BASE where cust_base_number='"+groupId+"'");
			
			List<Object[]>  listall = null;
			if(list!=null&&list.size()>0){
				Object[] o = list.get(0);
				String custForm = (o[1]==null)?"":o[1].toString();
				if("2".equals(custForm)){//动态客户群，需要通过客户群筛选条件查询客户
					List<Object[]> list1 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select ss_col_item,ss_col_op,ss_col_value,ss_col_join " +
							"from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+groupId+"')");
					if(list1!=null&&list1.size()>0){
						JSONArray jaCondition = new JSONArray();
						String	radio = "true";
						for(int i=0;i<list1.size();i++){
							Object[] oo = list1.get(i);
							radio = (oo[3]==null)?"":oo[3].toString();
							Map map = new HashMap<String,Object>();
			    			map.put("ss_col_item", (oo[0]==null)?"":oo[0].toString());
			    			map.put("ss_col_op", (oo[1]==null)?"":oo[1].toString());
			    			map.put("ss_col_value", (oo[2]==null)?"":oo[2].toString());
							jaCondition.add(map);
						}
						//客户筛选
						String res = targetCusSearchService.generatorSql(jaCondition,radio);	
						listall = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
								"cust_id,cust_name as cust_zh_name,cust_type as cust_typ,cust_stat,linkman_name as link_user  from  b.cust_typ where cust_id in ( select t.cust_id from ("+res+") t)");
		    			
					}
				}else {
					//静态客户群，直接关联客户查询
					listall = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam("select t2.cust_id,t2.personal_name,t2.mobile_phone,t2.email,t2.weixin  from  ACRM_F_CI_PERSON t2 "+
							" where t2.cust_id in("+ids_r+")");
				}
			
			}
			
			//根据产品id查询出产品信息
			List<Object[]>  list1 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select " +
			"prod_start_date,prod_end_date,rate,cost_rate from ocrm_f_pd_prod_info where product_id='"+prodId+"'");
			//替换模板中的内容
			Object[] product = null;
			if(list1 != null && list1.size()>0){
				product= list1.get(0);
			}
			
			String message0 = "";
			List<Object[]>  list2 = ocrmFMmSysMsgService.getBaseDAO().findByNativeSQLWithIndexParam(" select ID,MODEL_INFO,CATL_CODE from OCRM_F_MM_SYS_TYPE where ID='"+modelId+"'");
			Object[] oo = null;
			if(list2 != null && list2.size()>0){
				oo = list2.get(0);
				message0 = (oo[1]==null)?"false":oo[1].toString();
			}
			
			//查询客户
			JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
			String custId = "";
			String custName = "";
			String number = "";
			String email="";
			String weixin="";
			SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String time = formart.format(new Date());
			if(listall != null && listall.size()>0){
				for(int i=0;i<listall.size();i++){
					Object[] cust = listall.get(i);
					custId = (cust[0]==null)?"":cust[0].toString();
					custName = (cust[1]==null)?"":cust[1].toString();
					number = (cust[2]==null)?"":cust[2].toString();
					email=(cust[3]==null)?"":cust[3].toString();
					weixin=(cust[4]==null)?"":cust[4].toString();
					String message = "";
					if(modelSource.equals("自定义")){
						message=message_p;
					}else{
						message = message0;
						message = message.replace("@custName", custName);
						message = message.replace("@productId", prodId);
						message = message.replace("@prodName", prodName);
						message = message.replace("@prodStartDate", (product[0]==null)?"":product[0].toString());
						message = message.replace("@prodEndDate", (product[1]==null)?"":product[1].toString());
						message = message.replace("@rate", (product[2]==null)?"":product[2].toString());
						message = message.replace("@castRate", (product[3]==null)?"":product[3].toString());
						message = message.replace("@manger", auth.getUsername());
					}
					OcrmFMmSysMsg msg1 = new OcrmFMmSysMsg();
					msg1.setCustId(custId);
					msg1.setCustName(custName);
					msg1.setCellNumber(number);
					msg1.setMailAddress(email);
					msg1.setWxAccount(weixin);
					msg1.setProdId(prodId);
					msg1.setProdName(prodName);
					msg1.setModelId(modelId);
					msg1.setModelName(modelName);
					msg1.setMessageRemark(message);
					msg1.setModelType(modelType);
					msg1.setModelSource(modelSource);
					msg1.setIfApprove("Y");
					msg1.setApproveState("0");
					msg1.setUserId(auth.getUserId());
					msg1.setUserName(auth.getUsername());
					msg1.setCrtDate(new Date());
					msg1.setMsgsendtime(time);
					
					ocrmFMmSysMsgService.save(msg1);
				   	//String id =  metadataUtil.getId(msg1).toString();
				}
			}
			String instanceIds="MK_"+time;
			 String jobName = "自动营销推送_客户群";//自定义流程名称
	 		 ocrmFMmSysMsgService.initWorkflowByWfidAndInstanceid("44", jobName, null, (instanceIds==null)?"":instanceIds);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
	 		String nextNode = "44_a4";
	 		List rolelist = auth.getRolesInfo();
	 		for(Object m:rolelist){
				Map map = (Map)m;
				if("R120".equals(map.get("ROLE_CODE"))|| "R125".equals(map.get("ROLE_CODE"))){//总行产品专员、总行财管专员
					nextNode = "44_a10";
					continue ;
				}else if("R202".equals(map.get("ROLE_CODE"))){//区域行长
					nextNode = "44_a15";
					continue ;
				}else if("R201".equals(map.get("ROLE_CODE"))){//分行行长/区域中心主管
					nextNode = "44_a9";
					continue ;
				}else if("R311".equals(map.get("ROLE_CODE"))){//支行行长
					nextNode = "44_a7";
					continue ;
				}else if("R310".equals(map.get("ROLE_CODE"))){//客户经理主管
					nextNode = "44_a5";
					continue ;
				}else if("R303".equals(map.get("ROLE_CODE")) || "R305".equals(map.get("ROLE_CODE"))){//客户经理
					nextNode = "44_a4";
					continue ;
				}else{
					continue ;
				}
			}
			 Map<String, Object> map1 = new HashMap<String, Object>();
	 		 map1.put("instanceid", instanceIds);
	 		 map1.put("currNode", "44_a3");
	 		 map1.put("nextNode",  nextNode);
	 		 this.setJson(map1);
	 }
	 
	  
	 public DefaultHttpHeaders saveFeedBack(){
		    long id = ((OcrmFMmSysMsg)model).getId();
			String jql = "update  OcrmFMmSysMsg p set p.isFeedback=:isFeedback ,p.feedbackCont=:feedbackCont,p.feedbackUserId=:feedbackUserId,p.feedbackUserName=:feedbackUserName,p.feedbackDate=:feedbackDate where p.id='"+id+"'";
	        Map<String,Object> values = new HashMap<String,Object>();
	        values.put("isFeedback","1");
	        values.put("feedbackCont",((OcrmFMmSysMsg)model).getFeedbackCont());
	        values.put("feedbackUserId",auth.getUserId());
	        values.put("feedbackUserName",auth.getUsername());
	        values.put("feedbackDate",new Date());
	        ocrmFMmSysMsgService.batchUpdateByName(jql, values);
		 return new DefaultHttpHeaders("success");
	 }
	 
}


