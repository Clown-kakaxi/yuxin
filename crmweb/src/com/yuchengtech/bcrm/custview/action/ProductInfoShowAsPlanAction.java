
package com.yuchengtech.bcrm.custview.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowPlanService;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;

/**
 * 产品根据方案展示
 * @author luyy
 *@since 2014-05-15
 */

@SuppressWarnings("serial")
@Action("/getViewInfo")
public class ProductInfoShowAsPlanAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdShowPlanService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
//		model = new OcrmFPdProdShowPlan();
		setCommonService(service);
	}
	 @Autowired
		private TargetCusSearchService targetCusSearchService;
	 /***
		 * 	方案查询
		 * @return
		 */
	
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId =this.getJson().get("planId")==null?"":this.getJson().get("planId")+"";
    	String cust_id = this.getJson().get("cust_id")==null?"":this.getJson().get("cust_id")+"";  
    	String base_id = this.getJson().get("base_id")==null?"":this.getJson().get("base_id")+"";  
    	String viewType=this.getJson().get("viewType")==null?"":this.getJson().get("viewType")+""; 
    	String flag=this.getJson().get("flag")==null?"false":this.getJson().get("flag")+"";
    	StringBuffer sb = new StringBuffer("select ");
    	String sql = "";
    	List<Object[]> list = null;
    	
    	//首先拼接查询字段部分
    	sql = "select  t.table_oth_name||'.'||t.column_name as column_name,column_name as column_name1, COLUMN_TYPE,DIC_NAME from OCRM_F_PD_PROD_COLUMN t where to_char(t.column_id) in " +
    			"(select COLUMN_ID from OCRM_F_PD_PROD_SHOW_COLUMN where plan_id = '"+planId+"')";
    	list = service.getBaseDAO().findByNativeSQLWithIndexParam(sql);
    	if(list.size()>0){
    		for (Object[] o : list) {
    			sb.append(o[0].toString()+",");
    			if("2".equals(o[2].toString())){//数据字典
    				this.addOracleLookup( o[1].toString(), o[3].toString());
    			}
    		}
    	}
    	sb.deleteCharAt(sb.length()-1);
    	
    	//添加from 和 where 子句
    	sql = "select R_FROM,R_WHERE,cust_column from OCRM_F_PD_PROD_SHOW_R where plan_id = '"+planId+"'";
    	list = service.getBaseDAO().findByNativeSQLWithIndexParam(sql);
    	if(list.size()>0){
    		for (Object[] o : list) {
    			sb.append(" "+o[0].toString());
    			sb.append(" "+o[1].toString());
    			if("false".equals(flag.toString())){
    				sb.append(" and "+o[2].toString().split("\\.")[0]+".status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') ");
    			}
    			sb.append(" and "+o[2].toString() +" in");
    		}
    	}
    	
    	//添加客户条件
    	if(!"".equals(cust_id)&&"".equals(base_id))
		{
		    sb.append(" ('" + cust_id+"')");
		}else if("".equals(cust_id)&&!"".equals(base_id)&&"1".equals(viewType))
		{
			sb.append(" " + getGroupNumber(base_id)+" ");
		}else if("".equals(cust_id)&&!"".equals(base_id)&&"2".equals(viewType))
		{
			sb.append(" " + getGroupMemberNumber(base_id)+" ");
		}
    	
    	SQL = sb.toString();
    	datasource = ds;
    	
    	
    	
 	}
	 /**
	 * 定义方案包括的属性定义
	 */
		    public String getData()  {
		    	ActionContext ctx = ActionContext.getContext();
		    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		    	String planId = request.getParameter("planId");
		    	
		    	StringBuffer sb = new StringBuffer(" select  a.table_oth_name,a.column_name,a.column_oth_name," +
		    			"a.column_type,decode(a.align_type,'1','left','2','right','3','center','left') as align_type,a.dic_name,a.show_width " +
		    			"from OCRM_F_PD_PROD_COLUMN a,OCRM_F_PD_PROD_SHOW_COLUMN b where to_char(a.column_id)=b.column_id " +
		    			"and b.plan_id='"+planId+"' order by b.cloumn_squence ");
		    	
		    	try {
					this.json = new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    	
		    	return "success";
		    }
		    
		    /***
		     * 根据baseId查询客户群包含客户的sql
		     */
		    public String getGroupNumber(String base_id){
		    	String ids = "";
		    	StringBuilder sb=new StringBuilder("");
		    	
				//获取客户群类型
		    	String type = "";
		    	String custFrom = "";
		    	
		    	List<Object[]> list = null;
		    	list = service.getBaseDAO().findByNativeSQLWithIndexParam( "select CUST_FROM ,GROUP_MEMBER_TYPE from OCRM_F_CI_BASE where id='"+base_id+"'");
		    	if(list.size()>0){
		    		type = list.get(0)[0].toString();
		    		custFrom = list.get(0)[1].toString();
		    	}
				 
				 if("1".equals(type)||"3".equals(type)){//手动添加和证件导入的，直接在关联客户表中查询
					 sb.append(" (select cust_id from OCRM_F_CI_RELATE_CUST_BASE where CUST_BASE_ID='"+base_id+"')");
				 }else{//筛选方案客户群，需要拼接客户群成员查询sql
					 //1.拼接查询条件的map
					 JSONArray jaCondition = new JSONArray();
					 String radio = "false";
					 Map map = new HashMap<String,Object>();
					 list = service.getBaseDAO().findByNativeSQLWithIndexParam( " select ss_col_item,ss_col_op,ss_col_value,ss_col_join from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+base_id+"')");
				    	if(list.size()>0){
				    		for(Object[] o : list ){
				    			if("true".equals(o[3].toString()))
				    				radio = "true";
				    			map.put("ss_col_item", o[0].toString());
				    			map.put("ss_col_op", o[1].toString());
				    			map.put("ss_col_value", o[2].toString());
				    			jaCondition.add(map);
				    		}
				    	}
					 //2.拼接sql语句
					String res = targetCusSearchService.generatorSql(jaCondition,radio);
					 //3.查询
					sb.append("( "+res );
					 if("1".equals(custFrom)||"2".equals(custFrom)){//客户群的类别有要求 2：对私，1：对公
						 sb.append(" and custinfo.CUST_TYPe='"+custFrom+"'");
					 }
					 sb.append(") " );
				 }
					 
				ids = sb.toString();
		    	return ids ;
		    	
		    }
		    public String getGroupMemberNumber(String busiId){
		    	String condition="";
		    	condition+=" (select cust_id from OCRM_F_CI_GROUP_MEMBER where GROUP_NO =" +
		    			"(select group_no from OCRM_F_CI_GROUP_INFO where id=" +
		    			"'"+busiId+"'))";
				return condition;
		    }
}
