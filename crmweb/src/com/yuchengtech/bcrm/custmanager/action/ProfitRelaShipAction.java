package com.yuchengtech.bcrm.custmanager.action;



import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;




@SuppressWarnings("serial")
@Action("/profitRelatedShip")
public class ProfitRelaShipAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds; //声明数据源
	private HttpServletRequest request;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	private ProfitRelaShipService profitservice;
	
	@Autowired
	public void init(){
		setCommonService(profitservice);
	}
	
	public void prepare(){
		
		StringBuffer sb = new StringBuffer("select c.* from ACRM_A_CI_PROF_RELATION c where 1=1 ");
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String times = request.getParameter("ID");
		
		if(null!=times && !"".equals(times)){
			sb.append(" and c.create_times = " + times + " ");
		}else{
			sb.append(" and c.create_times = '0' ");
		}
		addOracleLookup("RELATIONSHIP", "CUS0100038");  //关联关系
		SQL = sb.toString();
		datasource = ds ;
	}
	
	//新增界面里面的删除
	public void  batchDel() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		profitservice.deleteById(request);
	}
	

}
