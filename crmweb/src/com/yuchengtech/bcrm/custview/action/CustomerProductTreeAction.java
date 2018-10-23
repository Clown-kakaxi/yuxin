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
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowPlanService;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.constance.SystemConstance;

@ParentPackage("json-default")
@Action(value="/customerProductTree", results={
    @Result(name="success", type="json"),
})
public class CustomerProductTreeAction  {
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	private HttpServletRequest request;
	
	@Autowired
	private OcrmFPdProdShowPlanService service;
	
	@Autowired
	private TargetCusSearchService targetCusSearchService;
	 
    private Map<String, Object> JSON;
    public Map<String, Object> getJSON() {
		return JSON;
	}

	public void setJSON(Map<String, Object> jSON) {
		JSON = jSON;
	}
/*
 * 查询客户持有产品树
 */
    public String index() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        try {
        	StringBuffer sb  = new StringBuffer();
        	if("DB2".equals(SystemConstance.DB_TYPE)){
        		sb.append(" WITH report(CATL_CODE,CATL_NAME,CATL_PARENT,VIEW_DETAIL,CATL_ORDER) AS " +
        			"( SELECT CATL_CODE, CATL_NAME,CATL_PARENT,VIEW_DETAIL,CATL_ORDER " +
        			"FROM OCRM_F_PD_PROD_CATL  " +
        			"WHERE CATL_CODE IN(" +
        			"SELECT DISTINCT CATL_CODE " +
        			"FROM  ACRM_F_AG_AGREEMENT " +
        			"LEFT JOIN OCRM_F_PD_PROD_INFO " +
        			"ON ACRM_F_AG_AGREEMENT.product_id = OCRM_F_PD_PROD_INFO.product_id " +
        			"where 1=1");
        	
	    		//单一客户视图
	        	if(!("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id")))
				{
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID = '" + request.getParameter("cust_id")+"'");
				}	
	    		//客户群视图
				else if(("").equals(request.getParameter("cust_id"))&&!("").equals(request.getParameter("base_id"))&&"1".equals(request.getParameter("viewType")))
				{
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID in "
							+getGroupNumber(request.getParameter("base_id"))//查询客户群的客户
							+" ");
				}
	        	//集团客户视图
				else if(("").equals(request.getParameter("cust_id"))&&!("").equals(request.getParameter("base_id"))&&"2".equals(request.getParameter("viewType")))
				{
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID in "
							+getGroupMemberNumber(request.getParameter("base_id"))//查询集团成员客户
							+" ");
				}
	           	//客户经理管理视图
				else if(("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id"))&&!("").equals(request.getParameter("mgrid"))){
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID in" +
							" (select t.cust_id from OCRM_F_CI_BELONG_CUSTMGR t " +
							"where t.mgr_id = '"+request.getParameter("mgrid")+"')");}
	    	    sb.append(") " +
	        			"UNION ALL " +
	        			"SELECT T1.CATL_CODE, T1.CATL_NAME, T1.CATL_PARENT,T1.VIEW_DETAIL,T1.CATL_ORDER " +
	        			"FROM OCRM_F_PD_PROD_CATL T1, report T2 " +
	        			"WHERE T1.CATL_CODE= T2.CATL_PARENT ) " +
	        			"SELECT DISTINCT CATL_CODE,CATL_NAME,CATL_PARENT,VIEW_DETAIL,CATL_ORDER FROM report ");
        	}else if("ORACLE".equals(SystemConstance.DB_TYPE)){
        		sb.append("SELECT DISTINCT CATL_CODE,CATL_NAME,CATL_PARENT,VIEW_DETAIL,CATL_ORDER FROM OCRM_F_PD_PROD_CATL t "+
        				"connect by t.catl_code = PRIOR t.catl_parent "+
        				"start with t.catl_code in ("+
        				"SELECT DISTINCT CATL_CODE FROM  ACRM_F_AG_AGREEMENT "+
        				"LEFT JOIN OCRM_F_PD_PROD_INFO ON ACRM_F_AG_AGREEMENT.product_id = OCRM_F_PD_PROD_INFO.product_id and  OCRM_F_PD_PROD_INFO.Type_Fit_Cust in ('1','1,2','2') "+
        				"where 1=1");
        		//单一客户视图
	        	if(!("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id")))
				{
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID = '" + request.getParameter("cust_id")+"'");
				}	
	    		//客户群视图
				else if(("").equals(request.getParameter("cust_id"))&&!("").equals(request.getParameter("base_id"))&&"1".equals(request.getParameter("viewType")))
				{
					
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID in "
							+getGroupNumber(request.getParameter("base_id"))//查询客户群的客户
							+" ");
				}
	        	//集团客户视图
				else if(("").equals(request.getParameter("cust_id"))&&!("").equals(request.getParameter("base_id"))&&"2".equals(request.getParameter("viewType")))
				{
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID in "
							+getGroupMemberNumber(request.getParameter("base_id"))//查询集团成员客户
							+" ");
				}
	        	//客户经理管理视图
				else if(("").equals(request.getParameter("cust_id"))&&("").equals(request.getParameter("base_id"))&&!("").equals(request.getParameter("mgrid"))){
					sb.append(" and ACRM_F_AG_AGREEMENT.CUST_ID in" +
							" (select t.cust_id from OCRM_F_CI_BELONG_CUSTMGR t " +
							"where t.mgr_id = '"+request.getParameter("mgrid")+"')");
				}
	        	sb.append(")");
        	}
        	//执行查询动作
        	String sb2=sb.toString();
        	setJSON(new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
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
				 sb.append(" and custinfo.CUST_TYPE='"+custFrom+"'");
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
