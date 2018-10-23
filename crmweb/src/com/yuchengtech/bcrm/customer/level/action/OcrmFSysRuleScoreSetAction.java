package com.yuchengtech.bcrm.customer.level.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.level.model.OcrmFSysRuleScoreSet;
import com.yuchengtech.bcrm.customer.level.service.OcrmFSysRuleScoreSetService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * 折算规则处理类 luyy  2014-07-16
 *
 */
@SuppressWarnings("serial")
@Action("/ocrmFSysRuleScoreSet")
public class OcrmFSysRuleScoreSetAction  extends CommonAction{
    @Autowired
    private  OcrmFSysRuleScoreSetService  ocrmFSysRuleScoreSetService;
    @Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
    
    @Autowired
	public void init(){
	  	model = new OcrmFSysRuleScoreSet(); 
		setCommonService(ocrmFSysRuleScoreSetService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}

    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
    
    public void prepare(){
    	 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 StringBuilder sb=new StringBuilder();
    		 sb.append(" select s.*,a.user_name from OCRM_F_SYS_RULE_SCORE_SET s,admin_auth_account a where s.user_id = a.account_name  ");
    	
    		 for(String key:this.getJson().keySet()){
				  if(null!=this.getJson().get(key)){
					 if("CUST_TYPE".equals(key)||"STATUS".equals(key)||"COMPUTE_TYPE".equals(key)||("FREQUENCE").equals(key)){
						sb.append(" and s."+key+" ='"+this.getJson().get(key)+"'");
					}
					else if("USER_NAME".equals(key)){
						sb.append(" and a."+key+" like '%"+this.getJson().get(key)+"%'");
					}
					else if("SET_DATE".equals(key)){
						sb.append(" and s."+key+" =to_date('"+this.getJson().get(key)+"','YYYY-MM-dd')");
					}
					else{
						sb.append(" and s."+key+" like '%"+this.getJson().get(key)+"%'");
					}
										
				  }
    		 }
    	
    	setPrimaryKey("set_Date desc");
    	
		SQL = sb.toString();
		datasource = ds;
	}
	
    
    public DefaultHttpHeaders saveData(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	((OcrmFSysRuleScoreSet)model).setOrgId(auth.getUnitId());
    	((OcrmFSysRuleScoreSet)model).setUserId(auth.getUserId());
    	((OcrmFSysRuleScoreSet)model).setSetDate(new Date());
    	ocrmFSysRuleScoreSetService.save(model);
    	
    	JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		auth.setPid(metadataUtil.getId(model).toString());//获取新增操作产生的最新记录的ID
    	return new DefaultHttpHeaders("success").setLocationId(((OcrmFSysRuleScoreSet) model).getId());
    }
    
  //删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFSysRuleScoreSetService.batchDel(request);
    	
	return new DefaultHttpHeaders("success").setLocationId(((OcrmFSysRuleScoreSet) model).getId());
    }
    
    
    //启用或者禁用方法
    public DefaultHttpHeaders batchUse(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFSysRuleScoreSetService.batchUse(request);
    	
	return new DefaultHttpHeaders("success").setLocationId(((OcrmFSysRuleScoreSet) model).getId());
    }
    
    //判断是否已经存在规则
    public HttpHeaders ifExits(){
	Connection conn = null ;
	Statement stmt = null ;
	ResultSet rs = null;
	ActionContext ctx = ActionContext.getContext();
	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	String custType = request.getParameter("custType");
	String indexCode = request.getParameter("indexCode");
	String exit = "no";
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String id = request.getParameter("id");
	 try {
        	conn=ds.getConnection();
        	stmt = conn.createStatement();
        	 String orgScopeId = "";
    		String count =  " select id from OCRM_F_SYS_RULE_SCORE_SET where  CUST_TYPE='"+custType+"' and index_code='"+indexCode+"' ";
    		if(id!=null&&!"".equals(id)){
    			count+=" and id<>'"+id+"'";//如果是修改，需要排除本条记录
    		}
    		rs = stmt.executeQuery(count);
    		while(rs.next()){
    			exit = "yes";
    		}
	 }catch (SQLException e) {
 		throw new BizException(1,2,"1002",e.getMessage());
        }finally{
        	JdbcUtil.close(rs, stmt, conn);
        }
        if(this.json!=null)
			this.json.clear();
		else 
			this.json = new HashMap<String,Object>();  
		
		this.json.put("exit",exit);
		return new DefaultHttpHeaders("success").disableCaching();
}

}