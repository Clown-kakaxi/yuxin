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
 * 对私客户授信信息审批查询
 * @author geyu
 * 2014-9-10
 */
@Action("/perSxInfoReview")
public class PerSxInfoReviewAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	public void prepare(){
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String instanceId =request.getParameter("instanceId");
    	String custId="";
    	String updateFlag="";
    	if(instanceId!=null){
    		custId=instanceId.split("_")[1];
    		updateFlag=instanceId.split("_")[2];
    	}
//      StringBuffer sb1 = new StringBuffer("select c.core_no,t.cust_id,t.cust_name,t.up_id,t.update_date,t.update_item,t.update_be_cont,t.update_user,t.update_flag,t.update_item_en,t.update_table,t.update_table_id,t.update_af_cont_view as update_af_cont,a.user_name,t.APPR_FLAG ");
//    	modify by liuming 20170824 修改日期加时分秒
    	StringBuffer sb1 = new StringBuffer("select c.core_no,t.cust_id,t.cust_name,t.up_id,to_char(t.update_date,'yyyy-mm-dd hh24:mi:ss') update_date,t.update_item,t.update_be_cont,t.update_user,t.update_flag,t.update_item_en,t.update_table,t.update_table_id,t.update_af_cont_view as update_af_cont,a.user_name,t.APPR_FLAG ");
    	sb1.append(" from OCRM_F_CI_CUSTINFO_UPHIS t ");
        sb1.append(" left join acrm_f_ci_customer c on c.cust_id = t.cust_id ");
        sb1.append(" left join admin_auth_account a on a.account_name = t.update_user ");
        sb1.append(" where t.cust_id='"+custId+"' and t.update_flag like '"+updateFlag+"%'");//全行客户查询非授信一次提交一次复核,故要用like
        //排除主键字段或放大镜字段不显示label
        sb1.append(" and (t.update_table_id is null or t.update_table_id != '1') ");
        sb1.append(" and (t.update_item is not null or t.update_item <> '')");
        //add by liuming 20170824
        sb1.append(" and t.update_item_en <> 'LOAN_CUST_STAT' ");
        setPrimaryKey("t.update_flag,t.update_table,t.update_item_en");
        
        SQL = sb1.toString();
        datasource =ds;
    }

}
