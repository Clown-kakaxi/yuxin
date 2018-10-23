package com.yuchengtech.bcrm.sales.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.model.OcrmFMkActiRecord;
import com.yuchengtech.bcrm.sales.service.MktMyActiDetailMangeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 我的营销活动
 * hujun
 * 2014-07-02
 */

@SuppressWarnings("serial")
@Action("/mktMyActiDetailManage")
public class MktMyActiDetailManageAction  extends CommonAction{
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	
    @Autowired
    private MktMyActiDetailMangeService mktmyactidetailmangeservice ;
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
        	model = new OcrmFMkActiRecord();  
        	setCommonService(mktmyactidetailmangeservice);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
//   （自定义）批量删除
    public String batchDestroy(){
	   	ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		String jql = "delete from OcrmFMkActiRecord p where p.recordId in (" + idStr + ")";
		
		Map<String,Object> values = new HashMap<String,Object>();
		mktmyactidetailmangeservice.batchUpdateByName(jql, values);
		addActionMessage("batch removed successfully");
		
	    return "success";

    }
    
    //新增或修改方法
    public String saveData(){
    	ActionContext ctx = ActionContext.getContext();
    	Connection conn = null ;
    	Statement stmt = null ;
    	Statement stmt1 = null ;
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String progressStage = "";
         String Update = "";
         String Update1 = "";
         String custId = "";
  	
    	String sign = request.getParameter("operate");//获取操作类型 维护关联客户、关联产品、渠道、附件等
    	if(!("".equals(sign))&&sign!=null&&sign.length()>0){
    		if("add".equals(sign)){
    			//mktActStr = request.getParameter("myActiId");//获取到营销活动编号
        		
                ((OcrmFMkActiRecord) model).setCreateUser(auth.getUserId());
                ((OcrmFMkActiRecord) model).setCreateDate(new Date());//创建时间
                //((OcrmFMkActiRecord) model).setMktActiId(mktActStr1);//营销活动编号
                
            	mktmyactidetailmangeservice.saveData(model);
            	
            	custId = ((OcrmFMkActiRecord) model).getCustId();
            	progressStage = ((OcrmFMkActiRecord) model).getProgressStage();
            	BigDecimal mktActStr11 =((OcrmFMkActiRecord) model).getMktActiId();
        		Update = " update OCRM_F_MK_MKT_MY_ACTI set PROGRESS_STAGE='"+progressStage+"' where MY_ACTI_ID='"+mktActStr11.toString()+"'";
        		Update1 = " update ocrm_f_mk_acti_customer set PROGRESS_STEP='"+progressStage+"' where MKT_ACTI_ID=(" +
        				"select MKT_ACTI_ID from OCRM_F_MK_MKT_MY_ACTI where MY_ACTI_ID='"+mktActStr11.toString()+"') and CUST_ID = '"+custId+"'";
            	
            	}else if("update".equals(sign)){
            		((OcrmFMkActiRecord) model).setUpdateUser(auth.getUserId());
            		((OcrmFMkActiRecord) model).setUpdateDate(new Date());
            		
            		 mktmyactidetailmangeservice.save(model);
            			
            		progressStage = ((OcrmFMkActiRecord) model).getProgressStage();
                	BigDecimal mktActStr2 = ((OcrmFMkActiRecord) model).getMktActiId();
                	custId = ((OcrmFMkActiRecord) model).getCustId();
                	
                	Update = " update OCRM_F_MK_MKT_MY_ACTI set PROGRESS_STAGE='"+progressStage+"' where MY_ACTI_ID='"+mktActStr2.toString()+"'";
                	Update1 = " update ocrm_f_mk_acti_customer set PROGRESS_STEP='"+progressStage+"' where MKT_ACTI_ID=(" +
    				"select MKT_ACTI_ID from OCRM_F_MK_MKT_MY_ACTI where MY_ACTI_ID='"+mktActStr2.toString()+"') and CUST_ID = '"+custId+"'";
            		
            	}
    		//同步修改我的营销活动以及营销活动的进展阶段
    	        try {
    	        	conn=ds.getConnection();
    	            stmt = conn.createStatement();
    	            stmt.executeUpdate(Update);
    	            stmt1 = conn.createStatement();
    	            stmt1.executeUpdate(Update1);
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }finally{
    	        	JdbcUtil.closeStatement(stmt1);
    	        	JdbcUtil.close(null, stmt, conn);
    	        }
            	}	
    	
    		addActionMessage("saveData successfully");
    	    return "success";	
    	
    }
    /**
	 * 设置查询SQL并为父类相关属性赋值
	 */
	public void prepare() {

		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String myActiId =request.getParameter("myActiId");
    	String sqlapp = " select o.*,a.user_name from OCRM_F_MK_ACTI_RECORD o ,ADMIN_AUTH_ACCOUNT a where o.create_user=a.account_name";
    	if(null==myActiId){
    		myActiId="";
    	}
    	
    	StringBuilder sb  = new StringBuilder(sqlapp);
    	
    	for(String key:this.getJson().keySet()){
    	     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    	         if(key.equals("PROGRESS_STAGE"))
    	             sb.append(" and o."+key+"= '"+this.getJson().get(key)+"'");
    	         if(key.equals("ACTI_DATE"))
    	             sb.append(" and o."+key+"= to_date('" +this.getJson().get(key)+"','yyyy-MM-dd')");
    	         else{
    	          	sb.append(" and o."+key+" like '%"+this.getJson().get(key)+"%'");
    	          }
    	     }
        }
    	if(!"".equals(myActiId))//客户经理营销活动反馈由于引入js所以会默认执行本函数，但是无参数，所以作此处理
			sb.append("  and MKT_ACTI_ID = '"+myActiId+"'");
       		setPrimaryKey("o.acti_DATE desc ");
       	 addOracleLookup("PROGRESS_STAGE", "STAGE_LEAVL");

        	SQL=sb.toString();
        	datasource = ds;
	}

    //分页查询
    public HttpHeaders indexPage() throws Exception {
    	try{	
    		StringBuilder      sb     = new StringBuilder("select p from FwSysProp p where 1=1 ");
			Map<String,Object> values = new HashMap<String,Object>();
			ActionContext      ctx    = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			
			if(request.getParameter("start") != null) {
				start = new Integer(request.getParameter("start")).intValue();
			}
			if(request.getParameter("limit") != null){
				limit = new Integer(request.getParameter("limit")).intValue();
			}
        
			this.setJson(request.getParameter("condition"));
			for(String key:this.getJson().keySet()){
			    if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
			        if(key.equals("propName")){
			            sb.append(" and p.propName like :propName");
			            values.put("propName", "%" + (String) this.getJson().get(key) + "%");
			        }
			        else if(key.equals("p.propDesc")){
			            sb.append(" and p.propDesc like :propDesc");
			            values.put("propDesc", "%" + (String)this.getJson().get(key) + "%");
			        } else if(key.equals("id")){
	                    sb.append(" and p.id = :id");
	                    values.put("id", Long.parseLong((String) this.getJson().get(key)));
	                } else{
			        	sb.append(" and p." + key + " = :" + key);
			        	values.put(key, this.getJson().get(key));
			        }
			    }
			}
			
			return super.indexPageByJql(sb.toString(), values);
			
    	} catch (Exception e){
    		e.printStackTrace();
    		throw e;
    	}
	}
    
}