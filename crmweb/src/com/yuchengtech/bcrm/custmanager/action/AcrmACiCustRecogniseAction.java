package com.yuchengtech.bcrm.custmanager.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmACiCustRecognise;
import com.yuchengtech.bcrm.custmanager.service.AcrmACiCustRecogniseService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/acrmACiCustRecognise")
public class AcrmACiCustRecogniseAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmACiCustRecogniseService  service;
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	
	@Autowired
	public void init(){
	  	model = new AcrmACiCustRecognise(); 
		setCommonService(service);
		needLog=true;//新增修改删除记录是否记录日志,默认为false，不记录日志
	}
	
	//数据查询
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder("SELECT * FROM ACRM_A_CI_CUST_RECOGNISE where 1=1");
		SQL = sb.toString();
		datasource = ds;
		configCondition("CUST_ID", "like", "CUST_ID", DataType.String);
		configCondition("CUST_NAME", "like", "CUST_NAME", DataType.String);
	}
	
	//数据保存
	public DefaultHttpHeaders save(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		if(((AcrmACiCustRecognise)model).getCustId() == null){
			Random random1 = new Random();
			int sp=Math.abs(random1.nextInt());
			String result="crm"+sp;
 			((AcrmACiCustRecognise)model).setCustId(result);
			service.save(model);
		}else if(((AcrmACiCustRecognise)model).getCustId() != null){
			String custId = ((AcrmACiCustRecognise)model).getCustId().toString();
			String jql = "update AcrmACiCustRecognise r set r.custName=:custName,r.nickName=:nickName,r.isPrivBankCust=:isPrivBankCust,r.isHuge=:isHuge,r.needsLast=:needsLast,"
				+"r.avoid=:avoid,r.drink=:drink,r.barcode=:barcode,r.cardCode=:cardCode,r.cardIc=:cardIc,r.cardNfc=:cardNfc,r.bankbook=:bankbook,r.cusFingerprint=:cusFingerprint,"
				+"r.cusFaceIdent=:cusFaceIdent,r.cusVein=:cusVein,r.note=:note,r.backMrk=:backMrk,r.custSource=:custSource,r.cusCons=:cusCons,r.comeDate=:comeDate where r.custId = '"+custId+"'";
			Map<String,Object> values = new HashMap<String,Object>();
			values.put("custName",((AcrmACiCustRecognise)model).getCustName());
			values.put("nickName",((AcrmACiCustRecognise)model).getNickName());
			values.put("isPrivBankCust",((AcrmACiCustRecognise)model).getIsPrivBankCust());
			values.put("isHuge",((AcrmACiCustRecognise)model).getIsHuge());
			values.put("needsLast",((AcrmACiCustRecognise)model).getNeedsLast());
			values.put("avoid",((AcrmACiCustRecognise)model).getAvoid());
			values.put("drink",((AcrmACiCustRecognise)model).getDrink());
			values.put("barcode",((AcrmACiCustRecognise)model).getBarcode());
			values.put("cardCode",((AcrmACiCustRecognise)model).getCardCode());
			values.put("cardIc",((AcrmACiCustRecognise)model).getCardIc());
			values.put("cardNfc",((AcrmACiCustRecognise)model).getCardNfc());
			values.put("bankbook",((AcrmACiCustRecognise)model).getBankbook());
			values.put("cusFingerprint",((AcrmACiCustRecognise)model).getCusFingerprint());
			values.put("cusFaceIdent",((AcrmACiCustRecognise)model).getCusFaceIdent());
			values.put("cusVein",((AcrmACiCustRecognise)model).getCusVein());
			values.put("note",((AcrmACiCustRecognise)model).getNote());
			values.put("backMrk",((AcrmACiCustRecognise)model).getBackMrk());
			values.put("custSource",((AcrmACiCustRecognise)model).getCustSource());
			values.put("cusCons",((AcrmACiCustRecognise)model).getCusCons());
			values.put("comeDate",((AcrmACiCustRecognise)model).getComeDate());
			service.batchUpdateByName(jql, values);
		}
		return new DefaultHttpHeaders("success");
	}
	
	//数据删除
	public DefaultHttpHeaders batchDel(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		service.batchDel(request);
		return new DefaultHttpHeaders("success");
	}
}
