package com.yuchengtech.bcrm.customer.newCustomer.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.customerView.model.FsxWholeInfo;
import com.yuchengtech.bcrm.customer.customerView.service.CustomerQueryAllNewService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.PagingInfo;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description: 非授信客户信息处理
 * @author luyy
 * @data 2014-08-06
 */
@ParentPackage("json-default")
@Action("/dealWithComThree")
public class DealWIthComThreeAction extends CommonAction{
	private static final long serialVersionUID = -1307317536382455940L;

	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private CustomerQueryAllNewService customerQueryAllService;
    
    @Autowired
    public void init(){
        model = new FsxWholeInfo();
        setCommonService(customerQueryAllService);
    }

	
	/**
	 * 查询对公客户地址信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryAddr(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        
			StringBuffer sb = new StringBuffer("SELECT T.ADDR_ID,T.CUST_ID AS ADDRESS_CUST_ID,T.COUNTRY_OR_REGION AS ADDR_COUNTRY, T.ADDR_TYPE,"+
			"	T.ADMIN_ZONE AS ADMIN_ZONE_ID,"+
			"  T.ADDR,T.ZIPCODE,T.LAST_UPDATE_SYS AS ADDRESS_LAST_UPDATE_SYS,T.LAST_UPDATE_USER AS ADDRESS_LAST_UPDATE_USER,"+

			"	to_char(T.LAST_UPDATE_TM,'yyyy-MM-dd') as ADDRESS_LAST_UPDATE_TM from ACRM_F_CI_ADDRESS t where t.cust_id = '"+custId+"' ORDER BY T.ADDR_TYPE");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("ADDR_TYPE", "XD000192");//地址类型
			query.addOracleLookup("ADDR_COUNTRY", "XD000025");//国别
			Map<String, Object> result = query.getJSON();
			List<HashMap<String, Object>> tempResult=((List<HashMap<String, Object>>)result.get("data"));
			//有树的放大镜的查询语句:查询行政区划名称
			if(tempResult != null && tempResult.size() > 0){
				for(int i=0;i<tempResult.size();i++){
					sb = new StringBuffer(                                                                                  
							"   SELECT CASE  WHEN T2.REGISTER_AREA IS NULL THEN   "+
							"	(SELECT A.ADMIN_ZONE FROM ACRM_F_CI_ADDRESS A WHERE A.CUST_ID='"+custId+"' and A.ADDR_TYPE='"+
									tempResult.get(i).get("ADDR_TYPE")+"' )   "+
						    "     ELSE   T2.REGISTER_AREA END ADMIN_ZONE FROM(     "+
							"		SELECT REPLACE(T1.REGISTER_AREA,',','>')  as REGISTER_AREA FROM(    "+
							"  			SELECT to_char(WM_CONCAT(T.F_VALUE)) AS REGISTER_AREA    "+
							"  			 FROM (           "+
							"  				   SELECT * FROM ( SELECT T.F_VALUE, T.F_CODE,        "+
							"      			   CASE  WHEN SUBSTR(F_CODE,5,6) != '00' AND  SUBSTR(F_CODE,3,6) != '0000'    THEN  SUBSTR(F_CODE,0,4) ||'00'   "+ 
							"      			   WHEN SUBSTR(F_CODE,5,6) = '00' AND   SUBSTR(F_CODE,3,6) != '0000'    THEN SUBSTR(F_CODE,0,2) ||'0000'    "+
							"      			   WHEN  SUBSTR(F_CODE,3,6) = '0000'    THEN  '-1'    "+
							"      			   END PARENT_CODE          "+
							"       		   FROM OCRM_SYS_LOOKUP_ITEM T      "+
							"     				 WHERE T.F_LOOKUP_ID = 'XD000001')A        "+
							"    		 START WITH  A.F_CODE=(SELECT A.ADMIN_ZONE FROM ACRM_F_CI_ADDRESS A WHERE A.CUST_ID='"+custId+"' and A.ADDR_TYPE='"+
							tempResult.get(i).get("ADDR_TYPE")+"' )   "+
							"   		  CONNECT BY A.F_CODE =PRIOR A.PARENT_CODE           "+
							"   		  ORDER BY A.F_CODE ) T)T1        )T2 ");
					
					query = new QueryHelper(sb.toString(), ds.getConnection());
					List<HashMap<String, Object>> resultNew=(List<HashMap<String, Object>>)query.getJSON().get("data");
					tempResult.get(i).put("ADMIN_ZONE",resultNew.get(0).get("ADMIN_ZONE"));
				}
			}
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}

	
	/**
	 * 查询对公联系人信息
	 * @return
	 */
	public String queryComContactPerson(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        
			StringBuffer sb = new StringBuffer("SELECT T.LINKMAN_ID,T.ORG_CUST_ID,T.LINKMAN_TYPE,T.LINKMAN_NAME,T.LINKMAN_TITLE,T.BIRTHDAY,T.EMAIL,T.FEX,T.GENDER,T.HOME_TEL,T.IDENT_NO,T.IDENT_TYPE,T.MOBILE,T.MOBILE2,T.OFFICE_TEL "
				+ " ,T.IDENT_EXPIRED_DATE,T.LAST_UPDATE_SYS AS PER_LAST_UPDATE_SYS,  T.LAST_UPDATE_USER AS PER_LAST_UPDATE_USER, A.USER_NAME AS PER_LAST_UPDATE_USER_NAME,to_char(T.LAST_UPDATE_TM,'yyyy-MM-dd') as PER_LAST_UPDATE_TM FROM ACRM_F_CI_ORG_EXECUTIVEINFO T " +
				"	LEFT JOIN ADMIN_AUTH_ACCOUNT A ON T.LAST_UPDATE_USER=A.ACCOUNT_NAME"+
				" 	WHERE T.ORG_CUST_ID = '"+custId+"' ORDER BY T.LINKMAN_TYPE");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("LINKMAN_TYPE", "XD000339");
			query.addOracleLookup("LINKMAN_TITLE", "XD000250");
			query.addOracleLookup("GENDER", "XD000016");
			query.addOracleLookup("IDENT_TYPE", "XD000040");
			Map<String, Object> result = query.getJSON();
			
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	/**
	 * 查询对公联系信息
	 * @return
	 */
	public String queryContact(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
	        String check = request.getParameter("check");
			StringBuffer sb = new StringBuffer("SELECT T.CONTMETH_ID,T.CUST_ID as CONTMETH_CUST_ID,T.IS_PRIORI,T.CONTMETH_TYPE,TO_CHAR(T.LAST_UPDATE_TM, 'yyyy-MM-dd hh24:mi:ss') AS CONTMETH_LAST_UPDATE_TM,REPLACE(T.CONTMETH_INFO, '/', '-') CONTMETH_INFOO,T.CONTMETH_INFO,T.CONTMETH_SEQ,T.REMARK,T.STAT,T.LAST_UPDATE_SYS as CONTMETH_LAST_UPDATE_SYS, " +
					" (case when a.user_name is not null then a.user_name else T.LAST_UPDATE_USER end ) as CONTMETH_LAST_UPDATE_USER,T.TX_SEQ_NO,T.ETL_DATE " +
					" FROM ACRM_F_CI_CONTMETH t " +
					" left join admin_auth_account a on t.LAST_UPDATE_USER = a.account_name " +
					" where ( T.CONTMETH_TYPE <> '503') and t.cust_id='"+custId+"'");
			if("1".equals(check)){
				sb.append(" and t.stat = '1' ");//表示未删除
			}
			if("0".equals(check)){
				sb.append(" and t.stat = '0' ");//表示已删除
			}
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("CONTMETH_TYPE", "XD000193");
			query.addOracleLookup("IS_PRIORI", "XD000332");			
			Map<String, Object> result = query.getJSON();
			
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	/**
	 * 查询对公证件信息
	 * @return
	 */
	public String queryIdent(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
			StringBuffer sb = new StringBuffer(" SELECT T.IDENT_ID  AS  LIST_IDENT_ID  ,T.CUST_ID   AS  IDENT_CUST_ID ,T.IDENT_TYPE  AS  LIST_IDENT_TYPE  ,     "+
					"   T.COUNTRY_OR_REGION   AS  COUNTRY_OR_REGION ,T.IS_OPEN_ACC_IDENT   AS  IS_OPEN_ACC_IDENT ,    "+
					"   T.IDENT_NO  AS  LIST_IDENT_NO  ,T.IDENT_CUST_NAME   AS  IDENT_CUST_NAME ,T.IDEN_REG_DATE AS  IDEN_REG_DATE , "+
					"   T.IDENT_VALID_PERIOD  AS  IDENT_VALID_PERIOD  ,T.IDENT_EXPIRED_DATE  AS  LIST_EXPIRED_DATE  ,     "+
					"   T.IDENT_CHECKING_DATE   AS  IDENT_CHECKING_DATE ,T.IDENT_ORG   AS  IDENT_ORG ,      "+
					"   T.LAST_UPDATE_SYS   AS  IDENT_LAST_UPDATE_SYS ,T.LAST_UPDATE_USER  AS  IDENT_LAST_UPDATE_USER  ,   "+
					"   T.LAST_UPDATE_TM  AS  IDENT_LAST_UPDATE_TM       "+
					"  FROM ACRM_F_CI_CUST_IDENTIFIER T        "+
					"  WHERE T.CUST_ID='"+custId+ "' AND IDENT_TYPE NOT IN ('15X')");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("IS_OPEN_ACC_IDENT", "XD000300");
			query.addOracleLookup("COUNTRY_OR_REGION", "XD000025");	
			query.addOracleLookup("LIST_IDENT_TYPE", "XD000040");
			Map<String, Object> result = query.getJSON();
			
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 查询处罚发生信息
	 * @return
	 */
	public String queryHappenDate(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = request.getParameter("custId");
			StringBuffer sb = new StringBuffer("select P.ID AS HAPPEN_ID,P.CUST_ID AS HAPPEN_CUST_ID,P.PENALIZE_TYPE,P.HAPPEN_DATE from ocrm_f_ci_cust_penalized p where p.cust_id='"+custId+"'");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			query.addOracleLookup("PENALIZE_TYPE", "XD000072");		
			Map<String, Object> result = query.getJSON();
			
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
}
