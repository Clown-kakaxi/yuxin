package com.yuchengtech.bcrm.customer.level.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 指标查询
 */
@ParentPackage("json-default")
@Action(value = "/IndexbaseQueryAction")
public class IndexBaseQueryAction  extends CommonAction{

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
	}

	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

	public void prepare(){
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 String gradeUseage = request.getParameter("gradeUseage");
		 String gradeType = request.getParameter("gradeType");
		 StringBuilder sb=new StringBuilder();
		 if(gradeUseage != null && !"".equals(gradeUseage)){//方案配置查询指标
			 if("2".equals(gradeUseage) || "3".equals(gradeUseage)){//反洗钱指标
				 String indexType = "1";
				 if("2".equals(gradeUseage)){
					 indexType = "1".equals(gradeType)? "1":"3";
				 }else{
					 indexType = "1".equals(gradeType)? "2":"4";
				 }
				 sb.append(" SELECT i.*,'反洗钱指标' as INDEX_USE_ORA FROM OCRM_F_CI_ANTI_MONEY_INDEX i WHERE i.INDEX_TYPE  = '"+indexType+"'");
			 }else {//普通指标
				 sb.append(" SELECT * FROM OCRM_F_SYS_INDEX_BASE WHERE 1=1 ");
			 }
			 
		 }else{//指标放大镜查询
			String type = request.getParameter("typeid");
			sb.append(" SELECT * FROM OCRM_F_SYS_INDEX_BASE WHERE 1=1 ");
			if(type!=null&&!"".equals(type)&&!"1000".equals(type))
					sb.append(" and index_use='"+type+"'");
			
			for(String key:this.getJson().keySet()){
		        if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
		            if(key.equals("INDEX_CODE")||key.equals("INDEX_NAME")){
		                sb.append(" and "+key+" like '%"+this.getJson().get(key)+"%'");
		            }
		        }
		    }
		}
		
	    addOracleLookup("INDEX_USE", "RULE_USE");
		
		SQL = sb.toString();
		datasource = ds;
	}
}