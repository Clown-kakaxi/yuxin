package com.yuchengtech.bcrm.customer.level.action;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiCustList;
import com.yuchengtech.bcrm.customer.level.service.OcrmFciAntiCustListService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.ecif.RequestBody4UpdateOrgCust;
import com.yuchengtech.trans.bo.ecif.RequestBody4UpdatePerCust;
import com.yuchengtech.trans.client.TransClient;

/**
 * 加入反洗钱风险客户处理
 * @author luyy
 *@since 2014-07-15
 */
@SuppressWarnings("serial")
@Action("/addRisk")
public class OcrmFCiAntiCustListAction  extends CommonAction {
	@Autowired
	private OcrmFciAntiCustListService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFCiAntiCustList();
		setCommonService(service);
	}
	
	public void save(){
		try {
			ActionContext ctx = ActionContext.getContext();
	    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	    	String custId = request.getParameter("custId");
	    	String custName = request.getParameter("custName");
	    	String riskLevel = request.getParameter("riskLevel");
	    	String custType = request.getParameter("custType");
	    	//删除客户原有等级信息
	    	service.batchUpdateByName(" delete from OcrmFCiAntiCustList l where l.custId='"+custId+"'", new HashMap());
//	    	Map<String,Object> values = new HashMap<String,Object>();
//	    	values.put("riskLevel",((OcrmFCiAntiCustList)model).getRiskLevel());
//	    	values.put("addUser",auth.getUserId());
//	    	values.put("addWay", "1");
//	    	values.put("addDate", new Date());
//	    	service.batchUpdateByName("update OcrmFCiAntiCustList l set l.riskLevel=:riskLevel,l.addUser=:addUser,l.addWay=:addWay,l.addDate=:addDate where l.custId='"+custId+"'",values);
	    	//添加新的等级信息
	    	OcrmFCiAntiCustList list = new OcrmFCiAntiCustList();
	    	list.setCustId(custId);
	    	list.setCustName(custName);
	    	list.setRiskLevel(riskLevel);
	    	list.setAddUser(auth.getUserId());
	    	list.setAddWay("1");
	    	list.setAddDate(new Date());
	    	service.save(list);
	    	
	    	/**更新客户等级信息 ACRM_F_CI_GRADE*/
	    	service.updateGrade(list);
	    	
	    	/**调用接口*/
	    	process(list,custType);
	    	
		} catch (Exception e) {
			throw new BizException(1, 0, "1002", e.getMessage());
		}
	}
	/**
	 * 更新客户反洗钱等级 调用接口
	 * @param list
	 * @throws Exception 
	 */
	public void process(OcrmFCiAntiCustList list,String custType) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		ActionContext ctx = ActionContext.getContext();
		
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String redirect_url = request.getHeader("host").split(":")[0];
    	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
		 header.setReqSysCd("CRM");
		 header.setReqSeqNo(format.format(new Date()));//交易流水号
		 header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));//请求日期
		 header.setReqTm(new SimpleDateFormat("HHmmss").format(new Date()));//请求时间
		 header.setDestSysCd("ECIF");
		 header.setChnlNo("");//业务渠道
		 header.setBizLine("");//业务条线
		 header.setBrchNo(auth.getUnitId());//机构号
		 header.setTlrNo(auth.getUserId());//用户编号（客户经理编码）
		 header.setTrmNo("");//终端号,可以为空
		 header.setTrmIP(redirect_url);//客户端IP
	
		 if("1".equals(custType)){//对公
			 String txCode = "updateOrgCustInfo";// char(4) 交易代码
			  
			 RequestBody4UpdateOrgCust   requestBody = new  RequestBody4UpdateOrgCust();
			 
			 AcrmFCiCustomer customer = new AcrmFCiCustomer();
			 customer.setRiskLevel(list.getRiskLevel());
			 
			 requestBody.setCustomer(customer);
			 requestBody.setTxCode(txCode);
			 requestBody.setCustNo(list.getCustId());
			 TransClient.process(header,requestBody);
			  
		 }else if("2".equals(custType)){//对私
			 String txCode = "updatePerCustInfo";// char(4) 交易代码
			 
			 RequestBody4UpdatePerCust requestBody = new RequestBody4UpdatePerCust();
			 
			 AcrmFCiCustomer customer = new AcrmFCiCustomer();
			 customer.setRiskLevel(list.getRiskLevel());
			 
			 requestBody.setCustomer(customer);
			 requestBody.setTxCode(txCode);
			 requestBody.setCustNo(list.getCustId());
			 TransClient.process(header,requestBody);
		 }
	}
}
