package com.yuchengtech.bcrm.custview.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.service.MyPotentialCustomerService;
import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgExecutiveinfo;
import com.yuchengtech.bcrm.custview.service.AcrmFCiOrgExecutiveinfoService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 对公客户视图==联系人and高管信息
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiOrgExecutiveinfo")
public class AcrmFCiOrgExecutiveinfoAction extends CommonAction{
	@Autowired
	private AcrmFCiOrgExecutiveinfoService service;
	
	@Autowired
	private MyPotentialCustomerService service1;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiOrgExecutiveinfo();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
	    String flag = request.getParameter("flag");//是否高管标识1是0否
		StringBuilder sb = new StringBuilder();
		//客户联系人
		sb.append("select substr(t.office_tel,0,instr(t.office_tel,'|',1)-1) as office_tel_FJ ,substr(t.office_tel,instr(t.office_tel,'|',1)+1) as EXTENSION_TEL," +
				" to_char(t.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TMM ,REPLACE(T.OFFICE_TEL,'/','-') OFFICE_TELL ,REPLACE(T.OFFICE_TEL2,'/','-') OFFICE_TEL22 ,REPLACE(T.HOME_TEL,'/','-') HOME_TELL ,REPLACE(T.HOME_TEL2,'/','-') HOME_TEL22 ,REPLACE(T.MOBILE,'/','-') MOBILEE,REPLACE(T.MOBILE2,'/','-') MOBILE22,REPLACE(T.FEX,'/','-') FEXX,REPLACE(T.IDENT_REG_ADDR,'/','，') IDENT_REG_ADDRR,REPLACE(T.ADDRESS,'/','，') ADDRESSS,t.* from ACRM_F_CI_ORG_EXECUTIVEINFO t  where 1=1 " +
				" ");
		if(customerId != null){
			sb.append(" and t.org_cust_id = '"+customerId+"'");
		}
		//高管信息
		if("1".equals(flag)){
			sb.setLength(0);
			sb.append(" SELECT to_char(o.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TMM,REPLACE(O.OFFICE_TEL,'/','-') OFFICE_TELL ,REPLACE(O.OFFICE_TEL2,'/','-') OFFICE_TEL22 ,REPLACE(O.HOME_TEL,'/','-') HOME_TELL ,REPLACE(O.HOME_TEL2,'/','-') HOME_TEL22 ,REPLACE(O.MOBILE,'/','-') MOBILEE,REPLACE(O.MOBILE2,'/','-') MOBILE22,REPLACE(O.FEX,'/','-') FEXX,REPLACE(O.IDENT_REG_ADDR,'/','，') IDENT_REG_ADDRR,REPLACE(O.ADDRESS,'/','，') ADDRESSS,O.*, ")
			.append(" je.TX_SEQ_NO as TX_SEQ_NO1,je.last_update_user as last_update_user1,to_char(je.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TM1,je.START_DATE as START_DATE1,je.END_DATE as END_DATE1," +
					"JE.UNIT_NAME,JE.UNIT_CHAR,JE.WORK_DEPT as WORK_DEPT1,JE.POSITION,REPLACE(JE.UNIT_TEL,'/','-') UNIT_TEL,REPLACE(JE.UNIT_ADDRESS,'/','，') UNIT_ADDRESS ,JE.UNIT_ZIPCODE,")
			.append(" ee.TX_SEQ_NO as TX_SEQ_NO2,EE.last_update_user as last_update_user2,to_char(EE.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TM2,EE.START_DATE as START_DATE2,EE.END_DATE as END_DATE2," +
					" EE.UNIVERSITY,EE.COLLEGE,EE.MAJOR,EE.EDU_SYS,EE.CERTIFICATE_NO,EE.DIPLOMA_NO,")
			.append(" pe.TX_SEQ_NO as TX_SEQ_NO3,pE.last_update_user as last_update_user3,to_char(pE.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TM3," +
					" pe.LANG_PREFER ,pe.TITLE_PREFER, pe.CONTACT_TYPE, pe.CONTACT_FREQ_PREFER, pe.CONTACT_TIME_PREFER, pe.GIFT_PREFER,  pe.VEHICLE_PREFER," +
					" pe.CONSUM_HABIT,  pe.INSURANCE_PREFER,  pe.INVEST_EXPR, pe.RISK_PREFER, pe.INVEST_POSITION,  pe.INVEST_CYCLE, pe.FINANCE_BUSINESS_PREFER," +
					" pe.INTEREST_INVESTMENT,  pe.INVEST_STYLE, pe.INVEST_TARGET, pe.INVEST_CHANNEL, pe.POST_DATA_FLAG, pe.JOIN_CAMP_FLAG, pe.RECEIVE_SMS_FLAG, pe.WELCOME_TEXT,")
			.append(" lo.TX_SEQ_NO as TX_SEQ_NO4,LO.last_update_user as last_update_user4,to_char(LO.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TM4," +
					" lo.LIKE_ECHANL_TYPE, lo.LIKE_LEISURE_TYPE,  lo.LIKE_MEDIA_TYPE,  lo.LIKE_SPORT_TYPE, lo.LIKE_MAGAZINE_TYPE, lo.LIKE_FILM_TYPE," +
					" lo.LIKE_PET_TYPE,  lo.LIKE_COLLECTION_TYPE, lo.LIKE_INVEST_TYPE, lo.LIKE_BRAND_TYPE, lo.LIKE_BRAND_TEXT,  lo.FINA_SERV," +
					" lo.CONTACT_TYPE as CONTACT_TYPE1 ,  lo.FINA_NEWS,  lo.SALON , lo.INTERESTS,  lo.AVOID , lo.OTHER,")
			.append(" fs.TX_SEQ_NO as TX_SEQ_NO6,FS.last_update_user as last_update_user5,to_char(FS.LAST_UPDATE_TM,'yyyy-MM-dd hh24:mi:ss') as LAST_UPDATE_TM5,FS.REMARK as REMARK1," +
					" REPLACE(fs.MOBILE,'/','-')  MOBILE1,fs.EMAIL as EMAIL1,fs.BIRTHDAY as BIRTHDAY1," +
					" fs.MEMBERNAME, fs.FAMILYRELA, fs.MEMBERCRET_TYP,  fs.MEMBERCRET_NO, REPLACE(fs.TEL,'/','-') TEL,  fs.COMPANY ")
			.append(" FROM  ACRM_F_CI_ORG_EXECUTIVEINFO O ")//机构干系人表
			.append(" LEFT JOIN ACRM_F_CI_PER_JOBRESUME JE ON O.ORG_CUST_ID = JE.CUST_ID ")//工作履历表
			.append(" LEFT JOIN ACRM_F_CI_PER_EDURESUME EE ON O.ORG_CUST_ID = EE.CUST_ID ")//学业履历表
			.append(" LEFT JOIN ACRM_F_CI_PER_PREFERENCE PE ON O.ORG_CUST_ID = PE.CUST_ID ")//个人偏好表
			.append(" LEFT JOIN ACRM_F_CI_PER_LIKEINFO LO ON O.ORG_CUST_ID = LO.CUST_ID ")//个人喜好表
			.append(" LEFT JOIN ACRM_F_CI_PER_FAMILIES FS ON O.ORG_CUST_ID = FS.CUST_ID ")//家庭主要成员表
			.append(" WHERE O.ORG_CUST_ID = '").append(customerId).append("'")
			.append(" AND O.LINKMAN_TYPE = '3' ");
		}
		SQL = sb.toString();
		if("1".equals(flag)){
			setPrimaryKey(" o.linkman_Id desc");
		}else{
			setPrimaryKey(" t.linkman_Id desc");
		}
		addOracleLookup("GENDER", "XD000016");
		addOracleLookup("LINKMAN_TYPE", "LINKMAN_TYPE");
		addOracleLookup("IDENT_TYPE", "XD000040");
		addOracleLookup("IDENT_IS_VERIFY", "IF_FLAG");
		//addOracleLookup("IDENT_TYPE", "COM_CRET_TYPE");
		datasource = ds;
	}	
	
    // 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("messageId");
			String jql = "delete from AcrmFCiOrgExecutiveinfo c where c.linkmanId in ('"
					+ idStr + "')";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
	
	//判断是是否已在本行看开户，没有则创建 潜在客户
	public void ifExit() throws IOException{
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String identType = request.getParameter("identType");
    	String identNo = request.getParameter("identNo");
    	String custName = request.getParameter("custName");
    	
    	String gender = request.getParameter("gender");
    	String linkmanTitle = request.getParameter("linkmanTitle");
    	String workPosition = request.getParameter("workPosition");
    	String officeTel = request.getParameter("officeTel");
    	String mobile = request.getParameter("mobile");
    	String homeTel = request.getParameter("homeTel");
    	String fex = request.getParameter("fex");
    	String email = request.getParameter("email");
    	String remark = request.getParameter("remark");
    	
    	String ifExit = "no";
    	List<String> customers = service.findByJql("select c.custId from  AcrmFCiCustomer c where c.identType = '"+identType+"'" +
				" and c.identNo = '"+identNo+"' " +
				" and c.custName = '"+custName+"' ", null);
    	if(list != null && list.size()>0){
    		ifExit = "yse";
    	}else{
    		ifExit = "no";
    		//创建潜在客户
    	    service1.save(null, custName, "1", "", "", "", identType,mobile,"", "2", "", identNo, "", "", "");
    	    service.updateExecutiveinfo(identType,identNo,custName);
    	}
    	HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
    	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(ifExit);
		response.getWriter().flush();
		
	}
	//根据输入三要素 回显 客户信息
	public String check(){
		ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String identType = request.getParameter("identType");
        String identNo = request.getParameter("identNo");
        String linkmanName = request.getParameter("linkmanName");
        Map map = service.check(identType,identNo,linkmanName);
        if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
    	this.json.put("json",map);
        return "success";
	}
}
