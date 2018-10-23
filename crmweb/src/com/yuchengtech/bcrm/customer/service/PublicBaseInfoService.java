package com.yuchengtech.bcrm.customer.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.AcrmFCiAddress;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustomerReview;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrg;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrgBusiinfo;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrgKeyflag;
import com.yuchengtech.bcrm.customer.model.AcrmFCiOrgRegisterinfo;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGradeResult;
import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 
* @ClassName: PublicBaseInfoService 
* @Description: 对公客户基本信息保存
* @author wangmk1 
* @date 2014-8-7  
*
 */
@Service
public class PublicBaseInfoService extends CommonService {
	
	private static Logger log = Logger.getLogger(PublicBaseInfoService.class);
	
	public PublicBaseInfoService(){
		JPABaseDAO<AcrmFCiOrg, String>  baseDAO=new JPABaseDAO<AcrmFCiOrg, String>(AcrmFCiOrg.class);  
		super.setBaseDAO(baseDAO);
	}
	
	@Override
	public Object save(Object obj) {
		// TODO Auto-generated method stub
		AcrmFCiOrg org = (AcrmFCiOrg)obj;
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.setLastUpdateUser(auth.getCname());
		org.setLastUpdateSys("CRM");
		Date date = new Date();  
		DateFormat ss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Timestamp lastUpdateTm=Timestamp.valueOf(ss.format(date));
		org.setLastUpdateTm(lastUpdateTm);
		//更新客户表的字段
		@SuppressWarnings("unchecked")
		List<AcrmFCiCustomer> list = this.em.createQuery("select t from AcrmFCiCustomer t where t.custId='"+org.getCustId()+"'").getResultList();
		AcrmFCiCustomer customer = list.get(0);
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String s1 = request.getParameter("AAA");
		
		JSONObject aa = JSONObject.fromObject(s1);
		customer.setShortName((String)aa.get("identType"));
		customer.setCustName((String)aa.get("identNo"));
		customer.setEnName((String)aa.get("enName"));
		customer.setRiskLevel((String)aa.get("riskLevel"));
		super.save(customer);
		//更新客户证件有效期
		@SuppressWarnings("unchecked")
		List<AcrmFCiCustIdentifier> list2 = this.em.createQuery("select t from AcrmFCiCustIdentifier t where t.custId='"+org.getCustId()+"' and t.identType='"+customer.getIdentType()+"' and t.identNo='"+customer.getIdentNo()+"' ").getResultList();
		if(!"[]".equals(list2.toString())){
			AcrmFCiCustIdentifier identifier = list2.get(0);
			String ivp=(String)aa.get("identValidPeriod");
			if(ivp!=null&&!"".equals(ivp)){
				BigDecimal bd=new BigDecimal(ivp); 
				identifier.setIdentValidPeriod(bd);
				super.save(identifier);
			}
		}
		//更新客户评级结果
		@SuppressWarnings("unchecked")
		List<OcrmFCiGradeResult> list3 = this.em.createQuery("select t from OcrmFCiGradeResult t where t.custId='"+org.getCustId()+"'").getResultList();
		if(!"[]".equals(list3.toString())){
			OcrmFCiGradeResult gradeResult = list3.get(0);
			gradeResult.setGradeResult((String)aa.get("gradeResult"));
			super.save(gradeResult);
		}
		//更新客户重要标志
		@SuppressWarnings("unchecked")
		List<AcrmFCiOrgKeyflag> list4 = this.em.createQuery("select t from AcrmFCiOrgKeyflag t where t.custId='"+org.getCustId()+"'").getResultList();
		if(!"[]".equals(list4.toString())){
			AcrmFCiOrgKeyflag orgKeyflag = list4.get(0);
			orgKeyflag.setIsListedCorp((String)aa.get("isListedCorp"));
			orgKeyflag.setIsSmallCorp((String)aa.get("isSmallCorp"));
			orgKeyflag.setHasIeRight((String)aa.get("hasIeRight"));
			orgKeyflag.setIsGroupCust((String)aa.get("isGroupCust"));
			orgKeyflag.setIsEbankSignCust((String)aa.get("isEbankSignCust"));
			orgKeyflag.setIsAssociatedParty((String)aa.get("isAssociatedParty"));
			orgKeyflag.setIsRural((String)aa.get("isRural"));
			orgKeyflag.setIsLimitIndustry((String)aa.get("isLimitIndustry"));
			orgKeyflag.setUdivFlag((String)aa.get("udivFlag"));
			orgKeyflag.setIsSoe((String)aa.get("isSoe"));
			orgKeyflag.setIsTaiwanCorp((String)aa.get("isTaiwanCorp"));
			super.save(orgKeyflag);
		}
		//更新注册信息
		@SuppressWarnings("unchecked")
		List<AcrmFCiOrgRegisterinfo> list5 = this.em.createQuery("select t from AcrmFCiOrgRegisterinfo t where t.custId='"+org.getCustId()+"'").getResultList();
		if(!"[]".equals(list5.toString())){
			AcrmFCiOrgRegisterinfo orgRegisterinfo = list5.get(0);
			orgRegisterinfo.setRegisterAddr((String)aa.get("registerAddr"));
			orgRegisterinfo.setRegisterArea((String)aa.get("registerArea"));
			orgRegisterinfo.setRegisterZipcode((String)aa.get("registerZipcode"));
			orgRegisterinfo.setRegisterCapitalCurr((String)aa.get("registerCapitalCurr"));
			BigDecimal sb=new BigDecimal((String)aa.get("registerCapital")); 
			orgRegisterinfo.setRegisterCapital(sb);
			orgRegisterinfo.setFactCapitalCurr((String)aa.get("factCapitalCurr"));
			BigDecimal sb2=new BigDecimal((String)aa.get("factCapital")); 
			orgRegisterinfo.setFactCapital(sb2);
			orgRegisterinfo.setBusinessScope((String)aa.get("businessScope"));
			super.save(orgRegisterinfo);
		}
		//更新英文地址信息
		@SuppressWarnings("unchecked")
		List<AcrmFCiAddress> list6 = this.em.createQuery("select t from AcrmFCiAddress t where t.custId='"+org.getCustId()+"' and t.addrType='09' ").getResultList();
		if(!"[]".equals(list6.toString())){
			AcrmFCiAddress address = list6.get(0);
			address.setEnAddr((String)aa.get("enAddr"));
			super.save(address);
		}
		//更新机构经营信息
		@SuppressWarnings("unchecked")
		List<AcrmFCiOrgBusiinfo> list7 = this.em.createQuery("select t from AcrmFCiOrgBusiinfo t where t.custId='"+org.getCustId()+"'").getResultList();
		if(!"[]".equals(list7.toString())){
			AcrmFCiOrgBusiinfo orgBusiinfo = list7.get(0);
			BigDecimal sb3=new BigDecimal((String)aa.get("workFieldArea")); 
			orgBusiinfo.setWorkFieldArea(sb3);
			orgBusiinfo.setWorkFieldOwnership((String)aa.get("workFieldOwnership"));
			orgBusiinfo.setManageStat((String)aa.get("manageStat"));
			super.save(orgBusiinfo);
		}
		return super.save(org);
	}
	
	public void saveReview(AcrmFCiCustomerReview review){
		super.save(review);
	}
	
	/**
	 * 对私客户基本信息调整历史2(3参)
	 * @param jarray
	 * @param date
	 * @param flag
	 */
	@SuppressWarnings("unchecked")
	public void bathsave(JSONArray jarray, Date date, String flag) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		List<ReviewMapping>  list =this.findByJql("select c from ReviewMapping c where c.moduleItem='对公基本信息'", null);
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				if(jarray.get(i) == null || "[]".equals(jarray.get(i).toString())){
					continue;
				}
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem")==null?"":String.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont")==null?"":String.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont")==null?"":String.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView")==null?"":String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId")==null?"":String.valueOf(wa.get("updateTableId")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updateItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(flag);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn("对公基本信息-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
		return;
	}
	
	/**
	 * 判断该客户信息是否在审批中
	 * @param custId
	 * @return
	 */
	public int judge(String instancePre){
		List list = this.em.createNativeQuery("SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
				+ instancePre
				+ "%'"
				+ " union  "
				+ " select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
				+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
				+ " where his.cust_id=substr('"+instancePre+"',instr ('"+instancePre+"','_')+1) and his.appr_flag='0' ").getResultList();
		if(list != null && list.size() >0 && list.get(0) != null){
			throw new BizException(1, 0, "1002", "客户记录被锁定，操作员"+list.get(0));
		}
		return 1;
	}
	
}
