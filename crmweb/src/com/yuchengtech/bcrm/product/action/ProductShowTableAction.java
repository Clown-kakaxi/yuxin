
package com.yuchengtech.bcrm.product.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowTable;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowTableService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 产品展示的展示方案关联表处理
 * @author luyy
 *@since 2014-05-14
 */

@SuppressWarnings("serial")
@Action("/showTables")
public class ProductShowTableAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdShowTableService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdShowTable();
		setCommonService(service);
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
    	StringBuffer sb = new StringBuffer(" select p.*,t.TABLE_NAME,t.TABLE_CH_NAME,t.TABLE_OTH_NAME from OCRM_F_PD_PROD_show_Table p " +
    			",OCRM_F_PD_PROD_TABLE t where p.table_id=t.table_id and p.plan_id = '"+planId+"' ");
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	/***
	 * 待添加表查询toAdd
	 */
	public void toAdd(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
		try {
			StringBuilder sb = new StringBuilder("");
				sb.append(" select * from OCRM_F_PD_PROD_TABLE where TABLE_ID not in " +
						"(select TABLE_ID from OCRM_F_PD_PROD_SHOW_TABLE where plan_id='"+planId+"') ");
				this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	  
	/***
	 * 添加关联表addTable
	 */
	public void addTable(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
    	String planName = request.getParameter("planName");
    	String ids =  request.getParameter("ids");
    	String id[] = ids.split(",");
    	for (int i=0;i<id.length;i++){
    		OcrmFPdProdShowTable table = new OcrmFPdProdShowTable();
    		table.setPlanId(planId);
    		table.setPlanName(planName);
    		table.setTableId(id[i]);
    		
    		service.save(table);
    	}
	}
	
	/***
	 * 删除关联表delTable
	 */
	public void delTable(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String ids =  request.getParameter("ids");
    	String id[] = ids.split(",");
    	for (int i=0;i<id.length;i++){
	   		service.batchRemove(id[i]);
    	}
	}
	
	  
}
