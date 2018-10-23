package com.yuchengtech.bcrm.customer.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.yuchengtech.bcrm.customer.service.PrivateApplyInfoService;
import com.yuchengtech.bcrm.custview.model.AcrmFCiAccountInfo;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 个人账户变更申请书 复核Action
 * @author denghj
 * @since 2015-12-22
 */
@Action("/privateApplyChecked")
public class PrivateApplyCheckedAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@Autowired
	private PrivateApplyInfoService privateApplyInfoService;
	
	/**
	 * 个人账户变更复核_查询B客户变更信息
	 */
	public void  prepare(){
		 ActionContext ctx = ActionContext.getContext();
	    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    	String instanceId =request.getParameter("instanceid");
//	    	String tableName = request.getParameter("tableName");
	    	String custId="";
	    	String updateFlag="";
	    	if(instanceId!=null){
	    		custId=instanceId.split("_")[1];
	    		updateFlag=instanceId.split("_")[2];
	    	}
	        StringBuffer sb = new StringBuffer("select t.cust_id,c.core_no,t.cust_name,t.up_id,t.update_date,t.update_item,");
	        sb.append("t.update_user,t.update_flag,t.update_item_en,t.update_page_item_en,t.update_table,t.update_table_id,a.user_name,t.appr_flag,");
	        sb.append("case when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and  t.update_item_en = 'ACCOUNT_CONTENTS') then FUN_CHANGE_CODES(t.update_be_cont,'APPLYINFO_ACCOUNT_CONTENT_TYPE') ");
	        sb.append("		when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'ACCOUNT_NUMBERS') then fun_change_code1(t.update_be_cont,'APPLYINFO_ACCOUNT_CONTENT_TYPE') ");
	        sb.append("		when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'IS_DOMESTIC_CUST' and t.update_be_cont = '0') then '境内客户' ");
	        sb.append("		when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'IS_DOMESTIC_CUST' and t.update_be_cont = '1') then '境外客户' ");
	        sb.append("		when (t.update_item_en = 'IF_UPDATE_MAIL' and t.update_be_cont = 'on') then '同步' ");
	        sb.append("		when (t.update_item_en = 'IF_UPDATE_MAIL' and  t.update_be_cont != 'on') then '取消同步' ");
	        sb.append("		when (t.update_item_en = 'HOME_STYLE' and t.update_be_cont = '1') then '租赁' ");
	        sb.append("		when (t.update_item_en = 'HOME_STYLE' and t.update_be_cont = '2') then '自有' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_be_cont = '1') then '全日制雇员' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_be_cont = '2') then '自雇' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_be_cont = '3') then '退休' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_be_cont = '4') then '其他' ");
	        sb.append("		when (t.update_item_en in ('IS_RELATED_BANK', 'USA_TAX_FLAG') and t.update_be_cont = 'Y') then '是' ");
	        sb.append("		when (t.update_item_en in ('IS_RELATED_BANK', 'USA_TAX_FLAG') and t.update_be_cont = 'N') then '否' ");
	        sb.append("		else t.update_be_cont ");
	        sb.append("	end as update_be_cont, t.update_af_cont,");
	        sb.append("case when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'ACCOUNT_CONTENTS') then FUN_CHANGE_CODES(t.update_af_cont,'APPLYINFO_ACCOUNT_CONTENT_TYPE') ");
	        sb.append("		when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'ACCOUNT_NUMBERS') then fun_change_code1(t.update_af_cont,'APPLYINFO_ACCOUNT_CONTENT_TYPE') ");
	        sb.append("		when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'IS_DOMESTIC_CUST' and t.update_af_cont = '0') then '境内客户' ");
	        sb.append("		when (trim(t.update_table) = 'ACRM_F_CI_ACCOUNT_INFO' and t.update_item_en = 'IS_DOMESTIC_CUST' and t.update_af_cont = '1') then '境外客户' ");
	        sb.append("		when (t.update_item_en = 'IF_UPDATE_MAIL' and t.update_af_cont = 'on') then '同步' ");
	        sb.append("		when (t.update_item_en = 'IF_UPDATE_MAIL' and t.update_af_cont != 'on') then '取消同步' ");
	        sb.append("		when (t.update_item_en = 'HOME_STYLE' and t.update_af_cont = '1') then '租赁' ");
	        sb.append("		when (t.update_item_en = 'HOME_STYLE' and t.update_af_cont = '2') then '自有' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_af_cont = '1') then '全日制雇员' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_af_cont = '2') then '自雇' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_af_cont = '3') then '退休' ");
	        sb.append("		when (t.update_item_en = 'PROFESSION_STYLE' and t.update_af_cont = '4') then '其他' ");
	        sb.append("		when (t.update_item_en in ('IS_RELATED_BANK', 'USA_TAX_FLAG') and t.update_af_cont = 'Y') then '是' ");
	        sb.append("		when (t.update_item_en in ('IS_RELATED_BANK', 'USA_TAX_FLAG') and t.update_af_cont = 'N') then '否' ");
	        sb.append("	else t.update_af_cont_view ");
	        sb.append("end as update_af_cont_view "); 
	        sb.append("from OCRM_F_CI_CUSTINFO_UPHIS t ");
	        sb.append("left join acrm_f_ci_customer c on c.cust_id = t.cust_id ");
	        sb.append("left join admin_auth_account a on a.account_name = t.update_user ");
	        sb.append("where t.cust_id='"+custId+"' ");
	        sb.append("and t.update_item_en not in ('CUST_ID','ADDR_ID','ADDR_TYPE','IDENT_ID','IDENT_TYPE','SERIALNO','STATE','STAT','CONTMETH_ID','CONTMETH_TYPE','IS_PRIORI','LAST_UPDATE_SYS','LAST_UPDATE_TM','LAST_UPDATE_USER') ");
	        sb.append("and t.appr_flag <> '1' ");
	        sb.append("and t.update_flag like '"+updateFlag+"%' ");
	        sb.append("order by t.update_table,t.update_item_en");
	        SQL = sb.toString();
	        datasource =ds;

	}
	
	/**
	 * 查询B客户变更前信息数据
	 * @return
	 */
	public String queryCustInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select p.cust_id,p.personal_name,p.pinyin_name,m2.f_value gender,i.ident_expired_date,p.birthday,m1.f_value citizenship,p.birthlocale,");
		sb.append("c1.contmeth_info contmeth_info_sj,c2.contmeth_info contmeth_info_yd,c4.contmeth_info contmeth_info_jt,");
		sb.append("a1.addr home_addr,a1.zipcode home_zipcode,a2.addr post_addr,a2.zipcode post_zipcode,");
		sb.append("a.home_style,c3.contmeth_info email,a.if_update_mail,a.profession_style, a.profession_style_remark,");
		sb.append("p.unit_name,p.duty,k.usa_tax_flag,p.usa_tax_iden_no,k.is_related_bank ");
		sb.append("from ACRM_F_CI_PERSON p ");
		sb.append("left join (select * from ocrm_sys_lookup_item where f_lookup_id = 'XD000025') m1 on m1.f_code = p.citizenship ");
		sb.append("left join (select * from ocrm_sys_lookup_item where f_lookup_id = 'XD000016') m2 on m2.f_code = p.gender ");
		sb.append("left join (select * from ACRM_F_CI_PERSON_ADDITIONAL) a on p.cust_id = a.cust_id ");
		sb.append("left join (select * from ACRM_F_CI_CUST_IDENTIFIER) i on p.cust_id = i.cust_id ");
		sb.append("left join (select * from ACRM_F_CI_PER_KEYFLAG) k on p.cust_id = k.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '102') c1 on p.cust_id = c1.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '209') c2 on p.cust_id = c2.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '500') c3 on p.cust_id = c3.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '2031') c4 on p.cust_id = c4.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_ADDRESS where addr_type = '04') a1 on p.cust_id = a1.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_ADDRESS where addr_type = '01') a2 on p.cust_id = a2.cust_id "); 
		sb.append("where p.cust_id = '"+custId+"'");
	
		try{
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("json",result);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return  "success";
	}
	
	/**
	 * 查询C账户变更前信息数据
	 * @return
	 * @throws SQLException 
	 */
	public String queryAccountInfo() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("");
		sb.append("select * from acrm_f_ci_account_info a where a.cust_id = '"+custId+"' ");
		QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
		Map<String, Object> result = query.getJSON();
		if(this.json != null){
			this.json.clear();
		}else{
			this.json = new HashMap<String, Object>();
		}
		this.json.put("json", result);

		return "success";
	}
	
	/**
	 * 查询C账户变更后信息数据
	 * @return
	 * @throws SQLException 
	 */
	public String queryAccountChangedInfo() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String instanceId = request.getParameter("instanceid");
		String contentType = request.getParameter("contentType");
    	String custId = null;
    	String updateFlag = null;
    	if(instanceId!=null){
    		custId = instanceId.split("_")[1];
    		updateFlag = instanceId.split("_")[2];
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append("select uphis.cust_id,uphis.account_contents,uphis.account_numbers ");
    	sb.append("from (select s1.update_af_cont	account_numbers,s1.cust_id,s1.update_flag,s1.update_table,"); 
        sb.append("s1.update_item_en   update_item_en1,s2.update_item_en   update_item_en2,s2.account_contents ");      
        sb.append("from ocrm_f_ci_custinfo_uphis s1 ");    
        sb.append("left join ");
        sb.append("(select s.update_af_cont account_contents,s.cust_id,s.update_flag,s.update_table,");       
        sb.append("s.update_item_en from ocrm_f_ci_custinfo_uphis s) s2 ");       
        sb.append("on s1.cust_id = s2.cust_id and s1.update_flag = s2.update_flag and s1.update_table = s2.update_table) uphis ");       
        sb.append("where uphis.update_table = 'ACRM_F_CI_ACCOUNT_INFO' and uphis.update_item_en1 = 'ACCOUNT_NUMBERS' and uphis.update_item_en2 = 'ACCOUNT_CONTENTS' ");       
        sb.append("and uphis.cust_id = '"+custId+"' ");
        sb.append("and uphis.update_flag like '"+updateFlag+"%' ");
        
        //用以存放转换后的查询信息
        List list = new ArrayList<String>();
        //取出账户账号类型和账户账号
        ResultSet result =  ds.getConnection().createStatement().executeQuery(sb.toString());
        String accountContents = "";
        String accountNumbers = "";
        while(result.next()){
        	accountContents = result.getString("account_contents");
        	accountNumbers = result.getString("account_numbers");
        }
        //根据contentType，分解账户类型和账户账号
        if(accountContents != null && accountNumbers != null){
        	String[] contentStrs = accountContents.split(",");
        	for(String content : contentStrs){
        		if(content.equals(contentType)){
        			String[] numberStrs = accountNumbers.split(",");
        			for(String number : numberStrs){
        				if(!(number.indexOf(contentType+"-")<0)){
        					Map<String, Object> map = new HashMap<String, Object>();
        					map.put("ACCOUNT_CONTENTS", content);
        					map.put("ACCOUNT_NUMBERS", number.split("-")[1]);
        					list.add(map);
        				}
        			}
        		}
        	}
        }
        //将map存入json 返回前台页面
        if(this.json != null){
			this.json.clear();
		}else{
			this.json = new HashMap<String, Object>();
		}
		//若map无结果集，则json为空，避免前台出现空数据
		if(list.isEmpty()){
			this.json.put("json","");
		}else{
			JSONArray accountJson = JSONArray.fromObject(list);
			this.json.put("json",accountJson);
		}
		return "";
	}
	
	/**
	 * 查询D服务变更前信息数据
	 * @return
	 */
	public String queryServiceInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select b.cust_id,b.ukey,b.message_code,b.phone_banking,b.mobile_banking,b.statements,b.change_notice,");
		sb.append("b.transaction_service,b.mail,b.mail_address,b.is_nt_bank,b.ukey_lost,b.mobile_banking_query,b.mobile_banking_trade,b.fax_number ");
		sb.append("from acrm_f_ci_bank_service b ");
		sb.append("where b.cust_id = '"+custId+"'");
		
		try{
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("json",result);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return "success";
	}
}
