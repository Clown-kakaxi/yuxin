
package com.yuchengtech.bcrm.product.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowColumn;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowColumnService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 产品展示的展示方案关联属性处理
 * @author luyy
 *@since 2014-05-14
 */

@SuppressWarnings("serial")
@Action("/showcolumn")
public class ProductShowColumnAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdShowColumnService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdShowColumn();
		setCommonService(service);
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联s属性查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
    	StringBuffer sb = new StringBuffer(" select p.*,t.COLUMN_OTH_NAME from OCRM_F_PD_PROD_SHOW_COLUMN p " +
    			",OCRM_F_PD_PROD_COLUMN t where p.column_id=t.column_id and p.plan_id = '"+planId+"' order by p.CLOUMN_SQUENCE");
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	/***
	 * 待添加属性查询toAdd
	 */
	public void toAdd(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
		try {
			StringBuilder sb = new StringBuilder("");
				sb.append(" select p.*,t.R_TABLE_ID " +
						"from OCRM_F_PD_PROD_COLUMN p,OCRM_F_PD_PROD_SHOW_TABLE t  " +
						"where p.table_id = t.TABLE_ID and t.plan_id = '"+planId+"' " +
						"and p.COLUMN_ID not in (select COLUMN_ID from OCRM_F_PD_PROD_SHOW_COLUMN where plan_id='"+planId+"') " +
						"and t.table_id in (select table_id from OCRM_F_PD_PROD_SHOW_TABLE where plan_id='"+planId+"')");
				this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	  
	/***
	 * 添加属性addcolumns
	 */
	public void addcolumns(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
    	String tableID = request.getParameter("tableID");
    	String ids =  request.getParameter("ids");
    	String id[] = ids.split(",");
    	String table[] = tableID.split(",");
    	for (int i=0;i<id.length;i++){
    		OcrmFPdProdShowColumn Column = new OcrmFPdProdShowColumn();
    		Column.setPlanId(planId);
    		Column.setColumnId(id[i]);
    		Column.setRTableId(table[i]);
    		
    		service.save(Column);
    	}
	}
	
	/***
	 * 删除属性delcolumns
	 */
	public void delcolumns(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String ids =  request.getParameter("ids");
    	String id[] = ids.split(",");
    	for (int i=0;i<id.length;i++){
	   		service.batchRemove(id[i]);
    	}
	}
	/**
	 * 保存排序字段
	 * @return
	 */
	public  DefaultHttpHeaders saveData(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String rCloumnIds = request.getParameter("rCloumnIds");
		String cloumnSquences = request.getParameter("cloumnSquences");
		
		JSONObject jsonObject1 =JSONObject.fromObject(rCloumnIds);
		JSONObject jsonObject2 =JSONObject.fromObject(cloumnSquences);

		JSONArray jarray1 =  jsonObject1.getJSONArray("rCloumnId");
		JSONArray jarray2 =  jsonObject2.getJSONArray("cloumnSquence");
		
		Map<String,Object> values = new HashMap();
	    for(int i = 0;i<jarray1.size();i++){
	    	Long rCloumnId =  (jarray1.get(i) == null||"".equals(jarray1.get(i)))?null:Long.valueOf(jarray1.get(i).toString());
	    	BigDecimal columnSquence =  (jarray2.get(i) == null||"".equals(jarray2.get(i)))?BigDecimal.valueOf(0):BigDecimal.valueOf(jarray2.getDouble(i));
	    	
	    	String sql = "update OcrmFPdProdShowColumn set cloumnSquence=:columnSquence where rCloumnId=:rCloumnId ";
	    	values.put("rCloumnId", rCloumnId);
	    	values.put("columnSquence", columnSquence);
	    	service.batchUpdateByName(sql, values);
	    }
    	
    	return new DefaultHttpHeaders("success").setLocationId(((OcrmFPdProdShowColumn) model).getRCloumnId());
    }
	
	  
}
