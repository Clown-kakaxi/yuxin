package com.yuchengtech.bcrm.customer.belong.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiBelongTrusteeship;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiBelongTrusteeshipService;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.ecif.RequestBody4UpdateBelong;
import com.yuchengtech.trans.client.TransClient;

/**
 * 客户托管处理
 * @author luyy
 *@since 2014-07-10
 */
@SuppressWarnings("serial")
@Action("/custTrust")
public class OcrmFCiBelongTrusteeshipAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFCiBelongTrusteeshipService service;
	
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFCiBelongTrusteeship();
		setCommonService(service);
	}
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	
	/**
	 * 托管客户查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
    	if(id !=null && !"".equals(id)){//工作流展示查询
    		StringBuffer sb = new StringBuffer(" select m.* from OCRM_F_CI_BELONG_TRUSTEESHIP m  where id = '"+id+"'");
    		SQL = sb.toString();
        	datasource = ds;
    	}else{
    		StringBuffer sb = new StringBuffer(" select m.*,a.cust_name,t.trust_stat," +
        			"t.trust_mgr_name ,t.DEAD_LINE,t.id as tid " +
        			"from OCRM_F_CI_BELONG_CUSTMGR m " +
        			"left join OCRM_F_CI_BELONG_TRUSTEESHIP t on m.cust_id = t.cust_id and t.trust_stat in ('01','02') " +
        			"left join ACRM_F_CI_CUSTOMER a on m.cust_id = a.cust_id " +
        			"where 1=1 ");
        	String role = "charge";
        	List<?> list = auth.getRolesInfo();
    		for(Object m:list){
    			Map<?, ?> map = (Map<?, ?>)m;
    			if("R302".equals(map.get("ROLE_CODE"))||"R303".equals(map.get("ROLE_CODE"))||"R304".equals(map.get("ROLE_CODE"))||"R305".equals(map.get("ROLE_CODE"))){//客户经理
    				role = "mgr";
    			}else{
    				continue ;
    			}
    		}
    		if("charge".equals(role)){//查询机构内的
    			sb.append(" and m.INSTITUTION ='"+auth.getUnitId()+"'");
    		}
    		if("mgr".equals(role)){//查询自己名下的
    			sb.append(" and m.MGR_ID ='"+auth.getUserId()+"'");
    		}
        	//处理页面查询条件
        	 for(String key : this.getJson().keySet()){
         		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
     				if(null!=key&&(key.equals("CUST_ID")||"MGR_NAME".equals(key)||"INSTITUTION_NAME".equals(key))){
     					sb.append("  and m."+key+" like '%"+this.getJson().get(key)+"%'  ");
     				}else if(null!=key&&key.equals("DEAD_LINE")){
     					sb.append("  and t.DEAD_LINE =to_date('%"+this.getJson().get(key).toString().substring(0,10)+"%'  ,'yyyy-mm-dd')");
     				}else if(null!=key&&"CUST_NAME".equals(key)){
     					sb.append("  and a.CUST_NAME like '%"+this.getJson().get(key)+"%'  ");
     				}
     				else if(null!=key&&"TRUST_STAT".equals(key)){
     					sb.append("  and t.TRUST_STAT like '%"+this.getJson().get(key)+"%'  ");
     				}
     				else if(null!=key&&"TRUST_MGR_NAME".equals(key)){
     					sb.append("  and t.TRUST_MGR_NAME like '%"+this.getJson().get(key)+"%'  ");
     				}else if(null!=key){
     					sb.append("  and  m."+key+" like '%"+this.getJson().get(key)+"%' ");
                     }
         		}
     		}
        	SQL = sb.toString();
        	datasource = ds;
    	}
    }
	
	/**
	 * 提交客户托管、判断是主管提交还是客户经理提交
	 * 客户经理提交需要走流程？？？？
	 * 客户托管表、不属于需要走交易接口的表，判断是否需要走流程
	 */
	public void save(){
		ActionContext ctx = ActionContext.getContext();
//		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String type = request.getParameter("type");
    	((OcrmFCiBelongTrusteeship)model).setSetUserId(auth.getUserId());
    	((OcrmFCiBelongTrusteeship)model).setSetUserName(auth.getUsername());
    	
    	if("charge".equals(type)){
    		((OcrmFCiBelongTrusteeship)model).setTrustStat("02");//主管操作，直接生效
    		service.save(model);
//    		/**托管调用接口*/
//    		process((OcrmFCiBelongTrusteeship)model);
    	}
    	if("mgr".equals(type)){
    		((OcrmFCiBelongTrusteeship)model).setTrustStat("01");
    		service.save(model);
//    		//提交流程处理
//    		Long id = ((OcrmFCiBelongTrusteeship)model).getId();
//    		String name = ((OcrmFCiBelongTrusteeship)model).getCustName();
//    		String instanceid = "TG_"+id;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
//    		String jobName = "客户托管_"+name;//自定义流程名称
//			try {
//				service.initWorkflowByWfidAndInstanceid("29", jobName, null, instanceid);
//				response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"29_a3\",\"nextNode\":\"29_a4\"}");
//				response.getWriter().flush();
//			} catch (Exception e) {
//				throw new BizException(1,0,"","初始化流程失败!");
//			}
    	}
	}
	
	/**
	 * 托管客户回收
	 */
	public void batchUpdate(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id :ids){
    		service.batchUpdateByName("update OcrmFCiBelongTrusteeship t set t.trustStat='05' where t.id='"+Long.parseLong(id)+"'", new HashMap<String, Object>());
    	}
	}
	
	/**
	 * 托管调用接口 (客户归属关系)--注：托管不需要更新归属机构表，故不需要该交易
	 * @deprecated
	 * @param obj
	 * @param id
	 * @param vo
	 */
	public void process(OcrmFCiBelongTrusteeship mdl){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
    	String redirect_url = request.getHeader("host").split(":")[0];
    	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;
		try{
			 conn = ds.getConnection();
			 statement = conn.createStatement();
			
			 String sql = "SELECT R.BELONG_BUSI_LINE FROM OCRM_F_CM_CUST_MGR_INFO R WHERE R.CUST_MANAGER_ID = '"+auth.getUserId()+"'";
			 
			 rs = statement.executeQuery(sql);
			 com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
			 if(rs.next()){
				 header.setBizLine(rs.getString("BELONG_BUSI_LINE"));//业务条线
			 }else{
				 header.setBizLine("");//业务条线
			 }
			 header.setReqSysCd("CRM");
			 header.setReqSeqNo(format.format(new Date()));//交易流水号
			 header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));//请求日期
			 header.setReqTm(new SimpleDateFormat("HHmmss").format(new Date()));//请求时间
			 header.setDestSysCd("ECIF");
			 //当前用户
			 header.setChnlNo("");//业务渠道
			 header.setBrchNo(auth.getUnitId());//机构号
			 
			 header.setTlrNo(mdl.getTrustMgrId());//用户编号
			 header.setTrmNo("");//终端号,可以为空
			 header.setTrmIP(redirect_url);//客户端IP
			 String txCode = "updateBelong";// char(4) 交易代码
			 
			 RequestBody4UpdateBelong requestBody = new RequestBody4UpdateBelong();
			 OcrmFCiBelongCustmgr custMgr = new OcrmFCiBelongCustmgr();
			 
			 custMgr.setMgrId(mdl.getMgrId());
			 custMgr.setMgrName(mdl.getMgrName());
			 
			 requestBody.setBelongManager(custMgr);
			 requestBody.setTxCode(txCode);
			 requestBody.setCustNo(mdl.getCustId());
			 TransClient.process(header, requestBody);
		}catch(Exception e){
    		throw new BizException(1, 0, "1002", e.getMessage());
    	}finally{
    		JdbcUtil.close(rs, statement, conn);
    	}
	}
}
