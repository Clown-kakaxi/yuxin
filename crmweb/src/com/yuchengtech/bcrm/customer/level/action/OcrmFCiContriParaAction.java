
package com.yuchengtech.bcrm.customer.level.action;



import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiContriPara;
import com.yuchengtech.bcrm.customer.level.service.OcrmFCiContriParaService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * 贡献度参数处理
 * @author luyy
 *@since 2014-07-14
 */

@SuppressWarnings("serial")
@Action("/contribution")
public class OcrmFCiContriParaAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFCiContriParaService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFCiContriPara();
		setCommonService(service);
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	StringBuffer sb = new StringBuffer("select p.*,a.user_name from  OCRM_F_CI_CONTRI_PARAM p left join admin_auth_account a on p.user_id=a.account_name where 1=1 ");
    	
    	//处理页面查询条件
    	 for(String key : this.getJson().keySet()){
     		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
 				if(null!=key&&key.equals("USER_NAME")){
 					sb.append("  and a."+key+" like '%"+this.getJson().get(key)+"%'  ");
 				}else if(null!=key&&key.equals("PARAM_DATE")){
 					sb.append("  and p.PARAM_DATE =to_date('%"+this.getJson().get(key).toString().substring(0,10)+"%'  ,'yyyy-mm-dd')");
 				}else if(null!=key&&key.equals("PARAM_VALUE")){
 					sb.append("  and p.PARAM_VALUE ='"+this.getJson().get(key).toString().substring(0,10)+"'");
 				}else if(null!=key){
 					sb.append("  and  p."+key+" like '%"+this.getJson().get(key)+"%' ");

                 }
     		}
 		}
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	
	public void save() throws Exception{
		((OcrmFCiContriPara)model).setUserId(auth.getUserId());
		((OcrmFCiContriPara)model).setParamDate(new Date());
		if(((OcrmFCiContriPara)model).getParamId() == null){
			List<?> list = service.getEntityManager().createQuery("select t from OcrmFCiContriPara t where t.paramCode = '"+((OcrmFCiContriPara)model).getParamCode()+"'").getResultList();
			if(list != null && list.size() > 0){
				throw new BizException(1,0,"","该贡献度参数已存在,不能再新增！");
			}
		}
		service.save(model);
	}
	//判断是参数是否已存在
	public void ifExit() throws IOException{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String code = request.getParameter("code");
    	String ifExit = "no";
    	List list = service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from OCRM_F_CI_CONTRI_PARAM where PARAM_CODE='"+code+"'");
    	if(list != null && list.size()>0)
    		ifExit = "yse";
    	else 
    		ifExit = "no";
    	
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(ifExit);
		response.getWriter().flush();
		
	}
	
	public void batchDel(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String idStr = request.getParameter("idStr");
    	String ids[] = idStr.split(",");
    	for(String id : ids){
    		service.batchUpdateByName(" delete from OcrmFCiContriParam p where p.paramId='"+Long.parseLong(id)+"'", new HashMap());
    	}
	}
	  
}
