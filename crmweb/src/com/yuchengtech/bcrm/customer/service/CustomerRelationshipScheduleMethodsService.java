package com.yuchengtech.bcrm.customer.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;




import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiRelationplanPattern;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class CustomerRelationshipScheduleMethodsService extends CommonService{
   private static Logger log = Logger.getLogger(CustomerRelationshipScheduleMethodsService.class);
   public CustomerRelationshipScheduleMethodsService(){
	   JPABaseDAO<OcrmFCiRelationplanPattern, String>  baseDAO=new JPABaseDAO<OcrmFCiRelationplanPattern, String>(OcrmFCiRelationplanPattern.class); 
		super.setBaseDAO(baseDAO);
   }
/**
 * 保存/修改客户关系计划表_交易模式   
 */
/*@Override
@SuppressWarnings("unchecked")
public Object save(Object obj) {
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	ActionContext ctx = ActionContext.getContext();
	HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	
	OcrmFCiRelationplanPattern ocrmFCiRelationplanPattern=(OcrmFCiRelationplanPattern)obj;
	return super.save(ocrmFCiRelationplanPattern);
}*/
   
   public void saveOcrmFCiRelationplanPattern(OcrmFCiRelationplanPattern orp,HttpServletResponse response) {
		 String planidstr="";
		 planidstr=orp.getPlanId();
	     em.merge(orp);
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(planidstr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
 

@SuppressWarnings("unchecked")
public void  updateOcrmFCiRelationplanPattern(OcrmFCiRelationplanPattern orp){
	OcrmFCiRelationplanPattern orpt=null;
	orpt=em.find(OcrmFCiRelationplanPattern.class, orp.getId());
	if(orpt!=null){
		orpt.setCorpProfile(orp.getCorpProfile());
		orpt.setCorpCulture(orp.getCorpCulture());
		orpt.setSaleEstimate(orp.getSaleEstimate());
		orpt.setSaleRangeEstimate(orp.getSaleRangeEstimate());
		orpt.setSaleArea(orp.getSaleArea());
		orpt.setPurchaseArea(orp.getPurchaseArea());
		orpt.setSettleTypeFir(orp.getSettleTypeFir());
		orpt.setSettleTypeFirScale(orp.getSettleTypeFirScale());
		orpt.setSettleTypeSec(orp.getSettleTypeSec());
		orpt.setSettleTypeSecScale(orp.getSettleTypeSecScale());
		orpt.setSettleTypeThir(orp.getSettleTypeThir());
		orpt.setSettleTypeThirScale(orp.getSettleTypeThirScale());
		orpt.setReceivablesCycle(orp.getReceivablesCycle());
		orpt.setPurchaseTypeFir(orp.getPurchaseTypeFir());
		orpt.setPurchaseTypeFirScale(orp.getPurchaseTypeFirScale());
		orpt.setPurchaseTypeSec(orp.getPurchaseTypeSec());
		orpt.setPurchaseTypeSecScale(orp.getPurchaseTypeSecScale());
		orpt.setPurchaseTypeThir(orp.getPurchaseTypeThir());
		orpt.setPurchaseTypeThirScale(orp.getPurchaseTypeThirScale());
		orpt.setAccountsPayableCycle(orp.getAccountsPayableCycle());
		orpt.setMainMaterial(orp.getMainMaterial());
		orpt.setMaterialAmmount(orp.getMaterialAmmount());
		orpt.setReceivablesCurrence(orp.getReceivablesCurrence());
		orpt.setAccountsPayableCurrence(orp.getAccountsPayableCurrence());
		orpt.setExportVolume(orp.getExportVolume());
		orpt.setImportVolume(orp.getImportVolume());
		orpt.setCreditLevel(orp.getCreditLevel());
		orpt.setCbLevle(orp.getCbLevle());
		orpt.setLineOfCredit(orp.getLineOfCredit());
		orpt.setOutstandingLoan(orp.getOutstandingLoan());
		orpt.setNextAnnualTime(orp.getNextAnnualTime());
		em.merge(orpt);
	
	}
	
}
   
}
