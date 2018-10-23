package com.yuchengtech.bcrm.customer.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiRelationAnalNy;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
@Service
public class OcrmFCiRelationAnalNyService extends CommonService {
	  private static Logger log = Logger.getLogger(OcrmFCiRelationAnalNyService.class);
	   public OcrmFCiRelationAnalNyService(){
		   JPABaseDAO<OcrmFCiRelationAnalNy, String>  baseDAO=new JPABaseDAO<OcrmFCiRelationAnalNy, String>(OcrmFCiRelationAnalNy.class); 
			super.setBaseDAO(baseDAO);
	   }
	/**
	 * 保存客户关系计划表_明年Wallet Size分析
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object save(Object obj) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
		OcrmFCiRelationAnalNy ocrmFCiRelationAnalNy=(OcrmFCiRelationAnalNy)obj;
		return super.save(ocrmFCiRelationAnalNy);
	}
	
	@SuppressWarnings("unchecked")
	public void updateOcrmFCiRelationAnalNy(OcrmFCiRelationAnalNy r){
		OcrmFCiRelationAnalNy oran=null;
		oran=em.find(OcrmFCiRelationAnalNy.class, r.getId());
		if(oran!=null){
			oran.setPlanId(r.getPlanId());
			oran.setDepositRmbAverageNy(r.getDepositRmbAverageNy());
			oran.setDepositRmbMarginNy(r.getDepositRmbMarginNy());
			oran.setDepositRmbProportNy(r.getDepositRmbProportNy());
			oran.setDepositTradeAverageNy(r.getDepositTradeAverageNy());
			oran.setDepositTradeMarginNy(r.getDepositTradeMarginNy());
			oran.setDepositTradeProportNy(r.getDepositTradeProportNy());
			oran.setDepositOtherAverageNy(r.getDepositOtherAverageNy());
			oran.setDepositOtherMarginNy(r.getDepositOtherMarginNy());
			oran.setDepositOtherProportNy(r.getDepositOtherProportNy());
			oran.setExchangeImmediateAverageNy(r.getExchangeImmediateAverageNy());
			oran.setExchangeImmediateMarginNy(r.getExchangeImmediateMarginNy());
			oran.setExchangeImmediateProportNy(r.getExchangeImmediateProportNy());
			oran.setExchangeForwardAverageNy(r.getExchangeForwardAverageNy());
			oran.setExchangeForwardMarginNy(r.getExchangeForwardMarginNy());
			oran.setExchangeForwardProportNy(r.getExchangeForwardProportNy());
			oran.setExchangeInterestAverageNy(r.getExchangeInterestAverageNy());
			oran.setExchangeInterestMarginNy(r.getExchangeInterestMarginNy());
			oran.setExchangeInterestProportNy(r.getExchangeInterestProportNy());
			oran.setOptionsTradingAverageNy(r.getOptionsTradingAverageNy());
			oran.setOptionsTradingMarginNy(r.getOptionsTradingMarginNy());
			oran.setOptionsTradingProportNy(r.getOptionsTradingProportNy());
			oran.setTradeFinancingAverageNy(r.getTradeFinancingAverageNy());
			oran.setTradeFinancingMarginNy(r.getTradeFinancingMarginNy());
			oran.setTradeFinancingProportNy(r.getTradeFinancingProportNy());
			oran.setTradeFactoringAverageNy(r.getTradeFactoringAverageNy());
			oran.setTradeFactoringMarginNy(r.getTradeFactoringMarginNy());
			oran.setTradeFactoringProportNy(r.getTradeFactoringProportNy());
			oran.setTradeDiscountAverageNy(r.getTradeDiscountAverageNy());
			oran.setTradeDiscountMarginNy(r.getTradeDiscountMarginNy());
			oran.setTradeDiscountProportNy(r.getTradeDiscountProportNy());
			oran.setTradeAcceptanceAverageNy(r.getTradeAcceptanceAverageNy());
			oran.setTradeAcceptanceMarginNy(r.getTradeAcceptanceMarginNy());
			oran.setTradeAcceptanceProportNy(r.getTradeAcceptanceProportNy());
			oran.setTradeCreditAverageNy(r.getTradeCreditAverageNy());
			oran.setTradeCreditMarginNy(r.getTradeCreditMarginNy());
			oran.setTradeCreditProportNy(r.getTradeCreditProportNy());
			oran.setTradeGuaranteeAverageNy(r.getTradeGuaranteeAverageNy());
			oran.setTradeGuaranteeMarginNy(r.getTradeGuaranteeMarginNy());
			oran.setTradeGuaranteeProportNy(r.getTradeGuaranteeProportNy());
			oran.setLoanAverageNy(r.getLoanAverageNy());
			oran.setLoanMarginNy(r.getLoanMarginNy());
			oran.setLoanProportNy(r.getLoanProportNy());
			oran.setSuitProductsNy(r.getSuitProductsNy());
			oran.setWalletsizeProductsNy(r.getWalletsizeProductsNy());
			oran.setProvideProductsNy(r.getProvideProductsNy());
		}
		em.merge(oran);
		
	}
}
