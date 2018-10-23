
package com.yuchengtech.bcrm.product.action;

import java.io.IOException;
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

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdTable;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdTableService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 产品展示的基础数据定义处理
 * @author luyy
 *@since 2014-05-13
 */

@SuppressWarnings("serial")
@Action("/productShowBase")
public class ProductShowBaseAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdTableService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdTable();
		setCommonService(service);
	}
	/**
	 * 定义表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuffer sb = new StringBuffer(" select * from OCRM_F_PD_PROD_TABLE  p where 1=1 ");
    	
    	 for(String key:this.getJson().keySet()){
             if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                 if(key.equals("TABLE_ID")||key.equals("TABLE_NAME")||key.equals("TABLE_CH_NAME")||key.equals("TABLE_TYPE")||key.equals("TABLE_OTH_NAME")){
                 	sb.append(" and p."+key+" like '%"+this.getJson().get(key)+"%' ");
                 }
             }
         }
    	 SQL = sb.toString();
    	datasource = ds;
    }
	/***
	 * 判断所选表是否已经配置过，判断别名是否已经使用
	 * @throws IOException 
	 */
	  public HttpHeaders checkData() throws IOException {
	    	ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    	String exit = "ok";
	    	String name = request.getParameter("TABLE_NAME");
	    	String othName = request.getParameter("TABLE_OTH_NAME");
	    	String id = request.getParameter("TABLE_ID");
	    	
	    	String sql = " select table_id from OCRM_F_PD_PROD_TABLE where table_name='"+name+"'";
	    	String sql1 = " select table_id from OCRM_F_PD_PROD_TABLE where table_oth_name='"+othName+"'";
	    	if(!"".equals(id)&&id!=null){
	    		sql += " and to_char(table_id) <>'" +id+"'";
	    		sql1 += " and to_char(table_id) <>'" +id+"'";
	    	}
	    	List<Object[]> list = service.getBaseDAO().findByNativeSQLWithIndexParam(sql);
	    	List<Object[]> list1 = service.getBaseDAO().findByNativeSQLWithIndexParam(sql1);
	    	
	    	if(list.size()>0&&list1.size()>0){
	    		exit = "exit";
	    	}
	    	if(list.size()>0&&(list1.size()== 0||list1 == null)){
	    		exit = "exit1";
	    	}
	    	if((list.size()== 0||list == null)&&list1.size()>0){
	    		exit = "exit2";
	    	}
	    	
	    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
			response.getWriter().write(exit);
			response.getWriter().flush();
	    	return new DefaultHttpHeaders("success").disableCaching();
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
		    	
		    	String sql = " select table_ch_name,table_id from  OCRM_F_PD_PROD_TABLE where table_id in" +
		    			"( select TABLE_ID from OCRM_F_PD_PROD_SHOW_TABLE where TABLE_ID in ('"+ids.replace(",", "','")+"'))";
		    	List<Object[]> list = service.getBaseDAO().findByNativeSQLWithIndexParam(sql);
		    	
		    	if(list.size()>0){
		    		for (Object[] o : list) {
		    			exit = o[0].toString();//表名
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
		    	
			return new DefaultHttpHeaders("success").setLocationId(((OcrmFPdProdTable) model).getTableId());
		    }
		  
		  
}
