
package com.yuchengtech.bcrm.product.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowPlan;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowPlanService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 产品展示的展示方案处理
 * @author luyy
 *@since 2014-05-13
 */

@SuppressWarnings("serial")
@Action("/productPlan")
public class ProductShowPlanAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdShowPlanService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdShowPlan();
		setCommonService(service);
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 定义表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuffer sb = new StringBuffer(" select * from OCRM_F_PD_PROD_show_plan  p where 1=1 ");
    	
    	 for(String key:this.getJson().keySet()){
             if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                 if(key.equals("PLAN_ID")||key.equals("PLAN_NAME")||key.equals("PLAN_TYPE")||key.equals("CREATE_USER")||key.equals("REMARK")){
                 	sb.append(" and p."+key+" like '%"+this.getJson().get(key)+"%' ");
                 }
                 if(key.equals("CREATE_DATE")){
                 	sb.append(" and p.PUBLISH_DATE = to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
                 }
             }
         }
    	 SQL = sb.toString();
    	datasource = ds;
    }
	
	  
	  /***
		 * 判断所选数据是否可删除
		 * @throws IOException 
		 */
		  public HttpHeaders checkDel() throws IOException {
		    	ActionContext ctx = ActionContext.getContext();
		    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    	String exit = "ok";
		    	String ids = request.getParameter("ids");
		    	
		    	String sql = " select plan_name,plan_id from  OCRM_F_PD_PROD_SHOW_PLAN where plan_id in" +
		    			"( select VIEW_DETAIL from OCRM_F_PD_PROD_CATL where VIEW_DETAIL in ('"+ids.replace(",", "','")+"') " +
		    					"union select PROD_VIEW from OCRM_F_PD_PROD_CATL where PROD_VIEW in ('"+ids.replace(",", "','")+"')) ";
		    	List<Object[]> list = service.getBaseDAO().findByNativeSQLWithIndexParam(sql);
		    	
		    	if(list.size()>0){
		    		for (Object[] o : list) {
		    			exit = o[0].toString();//方案名
		    		}
		    	}
		    	
		    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		    	response.setCharacterEncoding("utf-8");
				response.getWriter().write(exit);
				response.getWriter().flush();
		    	return new DefaultHttpHeaders("success").disableCaching();
		    }
		  
		  //删除
		    public DefaultHttpHeaders batchDel(){
		    	ActionContext ctx = ActionContext.getContext();
		    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    	service.batchDel(request);
		    	
			return new DefaultHttpHeaders("success").setLocationId(((OcrmFPdProdShowPlan) model).getPlanId());
		    }
		  
		    //保存
		    public void save(){
				   if(((OcrmFPdProdShowPlan)model).getPlanId() == null){//新增时处理
					   ((OcrmFPdProdShowPlan)model).setCreateDate(new Date());
					   ((OcrmFPdProdShowPlan)model).setCreateUser(auth.getUsername());
				   }else{
					   Long id = ((OcrmFPdProdShowPlan)model).getPlanId();
					   OcrmFPdProdShowPlan oldmodel = (OcrmFPdProdShowPlan)service.find(id);
					   ((OcrmFPdProdShowPlan)model).setCreateDate(oldmodel.getCreateDate());
					   ((OcrmFPdProdShowPlan)model).setCreateUser(oldmodel.getCreateUser());
				   }
				   service.save(model);
				   
			   }
		    
		    //查询方案字典
		    public String searchPlan()  {
		    	ActionContext ctx = ActionContext.getContext();
		    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    	String type = request.getParameter("type");
				try {
					StringBuilder sb = new StringBuilder("");
					sb.append("select PLAN_NAME as value,PLAN_ID as key from OCRM_F_PD_PROD_SHOW_PLAN where plan_type='"+type+"'");
					this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return "success";
			}
}
