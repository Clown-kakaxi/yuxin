package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamily;
import com.yuchengtech.bcrm.custview.service.AcrmFCiPerFamilyService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 家庭信息Action
 * @author YOYOGI
 * 2014-8-13
 */
@Action("/acrmFCiPerFamily")
public class AcrmFCiPerFamilyAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private AcrmFCiPerFamilyService acrmFCiPerFamilyService;
	
	@Autowired
	public void init(){
		model = new AcrmFCiPerFamily();
		setCommonService(acrmFCiPerFamilyService);
	}
	
	/**
	 * 数据查询
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		//StringBuffer sb = new StringBuffer("SELECT AM.* FROM ACRM_F_CI_PER_FAMILY AM WHERE AM.CUST_ID='"+ custId +"'");  	
    	StringBuffer sb = new StringBuffer(
    			"SELECT t.cust_id," +
    			"       AM.id," + 
    			"       AM.family_addr," + 
    			"       AM.home_tel," + 
    			"       AM.population," + 
    			"       AM.children_num," + 
    			"       AM.provide_pop_num," + 
    			"       AM.supply_pop_num," + 
    			"       AM.labor_pop_num," + 
    			"       AM.is_house_holder," + 
    			"       AM.house_holder_name," + 
    			"       AM.residence_stat," + 
    			"       AM.house_stat," + 
    			"       AM.has_home_car," + 
    			"       AM.is_credit_family," + 
    			"       AM.is_harmony," + 
    			"       AM.credit_amount," + 
    			"       AM.credit_info," + 
    			"       AM.busi_and_scale," + 
    			"       AM.main_income_source," + 
    			"       AM.family_ann_inc_scope," + 
    			"       AM.family_annual_pay_scope," + 
    			"       AM.family_assets_info," + 
    			"       AM.family_debt_scope," + 
    			"       AM.family_adverse_records," + 
    			"       AM.remark," + 
    			"       AM.last_update_sys," + 
    			"       AM.last_update_user," + 
    			"       AM.last_update_tm," + 
    			"       AM.tx_seq_no," + 
    			"       AM.debt_state," + 
    			"       AM.fmy_jitsuryoku," + 
    			"       AM.etl_date" + 
    			"  FROM ACRM_F_CI_PER_FAMILY AM, ACRM_F_CI_CUSTOMER t" + 
    			" WHERE AM.Cust_Id(+) = t.cust_id" + 
    			" and t.CUST_ID = '"+ custId +"'");
		SQL = sb.toString();
		datasource = ds;
	}
}