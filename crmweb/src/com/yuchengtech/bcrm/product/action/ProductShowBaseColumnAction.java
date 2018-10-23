
package com.yuchengtech.bcrm.product.action;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdColumn;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdColumnService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 产品展示的基础数据定义(属性)处理
 * @author luyy
 *@since 2014-05-13
 */

@SuppressWarnings("serial")
@Action("/productCloumn")
public class ProductShowBaseColumnAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdColumnService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdColumn();
		setCommonService(service);
	}
	/**
	 * 定义属性查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	//首先判断表是否定义了属性，定义了就直接查询OCRM_F_PD_PROD_Column ，否则根据表结构查询
    	String tableId = request.getParameter("tableId");
    	StringBuffer sb = new StringBuffer("");
    	List<Object[]> list = service.getBaseDAO().findByNativeSQLWithIndexParam(" select * from OCRM_F_PD_PROD_COLUMN where TABLE_ID='"+tableId+"'");
    	if(list.size()>0){
    		sb.append(" select * from OCRM_F_PD_PROD_COLUMN where TABLE_ID='"+tableId+"'");
    	}else{
    		sb.append("select null as COLUMN_ID,c.table_id,c.table_name,c.table_ch_name,c.table_oth_name,a.COLUMN_NAME," +
    				"decode(b.comments,'',a.COLUMN_NAME,null,a.COLUMN_NAME,b.comments) as column_oth_name," +
    				"decode(a.DATA_TYPE,'NUMBER','3','1') as column_type," +//Number默认为数值，其他为字符串
    				"decode(a.DATA_TYPE,'NUMBER','2','1') as ALIGN_TYPE," +//number默认右对齐，其他左对齐
    				"'100' as SHOW_WIDTH," +
    				"'' as DIC_NAME" +
    				" from user_tab_columns a,user_col_comments b,OCRM_F_PD_PROD_TABLE c " +
    				"where a.COLUMN_NAME = b.column_name and a.TABLE_NAME =c.table_name and b.table_name = c.table_name and c.table_id='"+tableId+"'");
    	}
    	
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	public  DefaultHttpHeaders saveData(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String COLUMN_IDs = request.getParameter("COLUMN_IDs");
		String TABLE_IDs = request.getParameter("TABLE_IDs");
		String TABLE_NAMEs = request.getParameter("TABLE_NAMEs");
		String TABLE_CH_NAMEs = request.getParameter("TABLE_CH_NAMEs");
		String TABLE_OTH_NAMEs = request.getParameter("TABLE_OTH_NAMEs");
		String COLUMN_NAMEs = request.getParameter("COLUMN_NAMEs");
		String COLUMN_OTH_NAMEs = request.getParameter("COLUMN_OTH_NAMEs");
		String COLUMN_TYPEs = request.getParameter("COLUMN_TYPEs");
		String ALIGN_TYPEs = request.getParameter("ALIGN_TYPEs");
		String DIC_NAMEs = request.getParameter("DIC_NAMEs");
		String SHOW_WIDTHs = request.getParameter("SHOW_WIDTHs");
		
		JSONObject jsonObject1 =JSONObject.fromObject(COLUMN_IDs);
		JSONObject jsonObject2 =JSONObject.fromObject(TABLE_IDs);
		JSONObject jsonObject3 =JSONObject.fromObject(TABLE_NAMEs);
		JSONObject jsonObject4 =JSONObject.fromObject(TABLE_CH_NAMEs);
		JSONObject jsonObject5 =JSONObject.fromObject(TABLE_OTH_NAMEs);
		JSONObject jsonObject6 =JSONObject.fromObject(COLUMN_NAMEs);
		JSONObject jsonObject7 =JSONObject.fromObject(COLUMN_OTH_NAMEs);
		JSONObject jsonObject8 =JSONObject.fromObject(COLUMN_TYPEs);
		JSONObject jsonObject9 =JSONObject.fromObject(ALIGN_TYPEs);
		JSONObject jsonObject10 =JSONObject.fromObject(DIC_NAMEs);
		JSONObject jsonObject11 =JSONObject.fromObject(SHOW_WIDTHs);

		JSONArray jarray1 =  jsonObject1.getJSONArray("COLUMN_ID");
		JSONArray jarray2 =  jsonObject2.getJSONArray("TABLE_ID");
		JSONArray jarray3 =  jsonObject3.getJSONArray("TABLE_NAME");
		JSONArray jarray4 =  jsonObject4.getJSONArray("TABLE_CH_NAME");
		JSONArray jarray5 =  jsonObject5.getJSONArray("TABLE_OTH_NAME");
		JSONArray jarray6 =  jsonObject6.getJSONArray("COLUMN_NAME");
		JSONArray jarray7 =  jsonObject7.getJSONArray("COLUMN_OTH_NAME");
		JSONArray jarray8 =  jsonObject8.getJSONArray("COLUMN_TYPE");
		JSONArray jarray9 =  jsonObject9.getJSONArray("ALIGN_TYPE");
		JSONArray jarray10 =  jsonObject10.getJSONArray("DIC_NAME");
		JSONArray jarray11 =  jsonObject11.getJSONArray("SHOW_WIDTH");
		
	   
       
		OcrmFPdProdColumn condition = new OcrmFPdProdColumn();
	    for(int i = 0;i<jarray1.size();i++){
	    	condition.setColumnId((jarray1.get(i) == null||"".equals(jarray1.get(i)))?null:Long.valueOf(jarray1.get(i).toString()));
	    	condition.setTableId(jarray2.getString(i));
	    	condition.setTableName(jarray3.getString(i));
	    	condition.setTableChName(jarray4.getString(i));
	    	condition.setTableOthName(jarray5.getString(i));
	    	condition.setColumnName(jarray6.getString(i));
	    	condition.setColumnOthName(jarray7.getString(i));
	    	condition.setColumnType(jarray8.getString(i));
	    	condition.setAlignType(jarray9.getString(i));
	    	condition.setDicName(jarray10.getString(i));
	    	condition.setShowWidth(jarray11.get(i) == null?BigDecimal.valueOf(100l):BigDecimal.valueOf(jarray11.getDouble(i)));
	    	
	    	service.save(condition);	
	    }
    	
    	return new DefaultHttpHeaders("success").setLocationId(((OcrmFPdProdColumn) model).getColumnId());
    }
	
		  
}
