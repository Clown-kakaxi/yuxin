package com.yuchengtech.bcrm.customer.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiRelationAnalysi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
@Service
public class OcrmFCiRelationAnalysiService extends CommonService {
	   private static Logger log = Logger.getLogger(OcrmFCiRelationAnalysiService.class);
	   public OcrmFCiRelationAnalysiService(){
		   JPABaseDAO<OcrmFCiRelationAnalysi, String>  baseDAO=new JPABaseDAO<OcrmFCiRelationAnalysi, String>(OcrmFCiRelationAnalysi.class); 
			super.setBaseDAO(baseDAO);
	   }
	/**
	 * 保存客户关系计划表_今年Wallet Size分析
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object save(Object obj) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
		OcrmFCiRelationAnalysi ocrmFCiRelationAnalysi=(OcrmFCiRelationAnalysi)obj;
		return super.save(ocrmFCiRelationAnalysi);
	}
	
	@SuppressWarnings("unchecked")
	public void updateOcrmFCiRelationAnalysi(OcrmFCiRelationAnalysi o){
		OcrmFCiRelationAnalysi ora=null;
		ora=em.find(OcrmFCiRelationAnalysi.class, o.getId());
		if(ora!=null){
	    ora.setPlanId(o.getPlanId());
		ora.setDepositRmbAverage(o.getDepositRmbAverage());
		ora.setDepositRmbMargin(o.getDepositRmbMargin());
		ora.setDepositRmbProportion(o.getDepositRmbProportion());
		ora.setDepositTradeAverage(o.getDepositTradeAverage());
		ora.setDepositTradeMargin(o.getDepositTradeMargin());
		ora.setDepositTradeProportion(o.getDepositTradeProportion());
		ora.setDepositOtherAverage(o.getDepositOtherAverage());
		ora.setDepositOtherMargin(o.getDepositOtherMargin());
		ora.setDepositOtherProportion(o.getDepositOtherProportion());
		ora.setExchangeImmediateAverage(o.getExchangeImmediateAverage());
		ora.setExchangeImmediateMargin(o.getExchangeImmediateMargin());
		ora.setExchangeImmediateProportion(o.getExchangeImmediateProportion());
		ora.setExchangeForwardAverage(o.getExchangeForwardAverage());
		ora.setExchangeForwardMargin(o.getExchangeForwardMargin());
		ora.setExchangeForwardProportion(o.getExchangeForwardProportion());
		ora.setExchangeInterestAverage(o.getExchangeInterestAverage());
		ora.setExchangeInterestMargin(o.getExchangeInterestMargin());
		ora.setExchangeInterestProportion(o.getExchangeInterestProportion());
		ora.setOptionsTradingAverage(o.getOptionsTradingAverage());
		ora.setOptionsTradingMargin(o.getOptionsTradingMargin());
		ora.setOptionsTradingProportion(o.getOptionsTradingProportion());
		ora.setTradeFinancingAverage(o.getTradeFinancingAverage());
		ora.setTradeFinancingMargin(o.getTradeFinancingMargin());
		ora.setTradeFinancingProportion(o.getTradeFinancingProportion());
		ora.setTradeFactoringAverage(o.getTradeFactoringAverage());
		ora.setTradeFactoringMargin(o.getTradeFactoringMargin());
		ora.setTradeFactoringProportion(o.getTradeFactoringProportion());
		ora.setTradeDiscountAverage(o.getTradeDiscountAverage());
		ora.setTradeDiscountMargin(o.getTradeDiscountMargin());
		ora.setTradeDiscountProportion(o.getTradeDiscountProportion());
		ora.setTradeAcceptanceAverage(o.getTradeAcceptanceAverage());
		ora.setTradeAcceptanceMargin(o.getTradeAcceptanceMargin());
		ora.setTradeAcceptanceProportion(o.getTradeAcceptanceProportion());
		ora.setTradeCreditAverage(o.getTradeCreditAverage());
		ora.setTradeCreditMargin(o.getTradeCreditMargin());
		ora.setTradeCreditProportion(o.getTradeCreditProportion());
		ora.setTradeGuaranteeAverage(o.getTradeGuaranteeAverage());
		ora.setTradeGuaranteeMargin(o.getTradeGuaranteeMargin());
		ora.setTradeGuaranteeProportion(o.getTradeGuaranteeProportion());
		ora.setLoanAverage(o.getLoanAverage());
		ora.setLoanMargin(o.getLoanMargin());
		ora.setLoanProportion(o.getLoanProportion());
		ora.setSuitProducts(o.getSuitProducts());
		ora.setWalletsizeProducts(o.getWalletsizeProducts());
		ora.setProvideProducts(o.getProvideProducts());
		}
		em.merge(ora);
		
	}
}
