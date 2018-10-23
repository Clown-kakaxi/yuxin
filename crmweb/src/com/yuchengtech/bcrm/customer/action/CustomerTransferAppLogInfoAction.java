package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户转移-->日志
 *
 */
@SuppressWarnings("serial")
@Action("/customertransferapploginfoaction")
public class CustomerTransferAppLogInfoAction  extends CommonAction{

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;  
	private HttpServletRequest request;
	
 	public void prepare() {	
 		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String sign = "0";
        if("customerTransfer".equals(request.getParameter("adjSign"))){
        	sign="1";
        }else if("customerDeposit".equals(request.getParameter("adjSign"))){
        	sign="2";
        }
        StringBuilder sb = new StringBuilder("SELECT " +
        					   "T.ID,t.apply_date, "+
						       "T.CUST_ID, "+
						       "de.cust_zh_name as CUST_NAME, "+
						       "T.AFTER_ORG_ID, "+
						       "ORG.ORG_NAME, "+
						       "org1.org_name as apply_org_name, "+
						       "RIGHT_TYPE, "+
						       "T.APP_STATUS, "+
						       "T.OPER_TYPE, "+
						       "T.APPLY_USER_ID, "+
						       "T.APPLY_USER_NAME, "+
						       "T.BEFORE_ORG_ID, "+
						       "T.OLD_MGR_ID, "+
						       "T.OLD_MGR_NAME, "+
						       "T.OPER_RESULT, "+
						       "T.APP_DATE3, "+
						       "T.REMARK, "+
						       "T.REFUSE_REASON	 "+
						 " FROM OCRM_F_CI_TRANSFER_APP_INFO T  inner join ocrm_f_ci_cust_desc de on de.cust_id = t.cust_id " +
						 "left join admin_auth_org org1 on org1.org_id = t.BEFORE_ORG_ID "+
						 "left join admin_auth_org org on org.org_id = t.after_org_id ");
    					sb.append("where 1>0 ");
    					sb.append(" and t.OPER_TYPE = '"+sign+"'");
    	for(String key:this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    		if(key.equals("custId")){//客户编号
    			sb.append(" and T.cust_id ='"+this.getJson().get(key)+"'");
    		}else if(key.equals("APPLY_USER_ID")){//转入客户经理
    			String APPLY_USER_ID_T = this.getJson().get(key).toString();
    			sb.append(" and T.APPLY_USER_ID in('"+APPLY_USER_ID_T.replace(",", "','")+"')");
    		}else if(key.equals("OLD_MGR_ID")){//转出客户经理
    			String OLD_MGR_ID_T = this.getJson().get(key).toString();
    			sb.append(" and T.OLD_MGR_ID in('"+OLD_MGR_ID_T.replace(",", "','")+"')");
    		}else if(key.equals("instncode")){//归属机构
    			String BEFORE_ORG_ID_T = this.getJson().get(key).toString();
    			sb.append(" and T.BEFORE_ORG_ID in('"+BEFORE_ORG_ID_T.replace(",", "','")+"')");
    		}else if(key.equals("startTime")){//调整开始日期
    			 sb.append(" and T.APP_DATE3 >= to_date('" +this.getJson().get(key)+ "','yyyy-MM-dd')");
    		}else if(key.equals("endTime")){//调整结束日期
    			sb.append(" and T.APP_DATE3 <= to_date('" +this.getJson().get(key)+ "','yyyy-MM-dd')");
    		}if(key.equals("id")){//ID
    			sb.append(" and T.id ='"+this.getJson().get(key)+"'");
    		}
    	}
    	};

        addOracleLookup("CUST_TYP", "XD000080");
        addOracleLookup("CUST_LEV", "P_CUST_GRADE");
        addOracleLookup("CERT_TYPE", "PAR0100006");
        addOracleLookup("CUST_STAT","XD000081");
        addOracleLookup("IF_NETBANK","IF_FLAG");
        addOracleLookup("ENT_MAIN_INDUSTRY","PAR2100001");
        addOracleLookup("ENT_SCALE","DEM0200004");
        addOracleLookup("ENT_ECOM_TYPE","DEM0200002");
        addOracleLookup("MAIN_TYPE","MAINTAIN_TYPE");

       setPrimaryKey("T.ID DESC");
		SQL = sb.toString();
		datasource = dsOracle;
	}
}