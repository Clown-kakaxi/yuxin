package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerInvestment;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 对私客户视图（个人投资信息）
 * @author agile
 *
 */
@Service
public class AcrmFCiPerInvestmentInfoService extends CommonService {
	
	public AcrmFCiPerInvestmentInfoService(){
		JPABaseDAO<AcrmFCiPerInvestment,String> baseDao = new JPABaseDAO<AcrmFCiPerInvestment,String>(AcrmFCiPerInvestment.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiPerInvestment investment = (AcrmFCiPerInvestment)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(investment.getInvestmentId()==null){
			investment.setLastUpdateUser(auth.getUsername());
			investment.setLastUpdateTm(new Timestamp(new Date().getTime()));
			return super.save(investment);
		}else{
			AcrmFCiPerInvestment investmentInfo = (AcrmFCiPerInvestment) this.find(investment.getInvestmentId());
	  		investmentInfo.setInvestAim(investment.getInvestAim());
	  		investmentInfo.setInvestExpect(investment.getInvestExpect());
	  		investmentInfo.setInvestType(investment.getInvestType());
	  		investmentInfo.setInvestAmt(investment.getInvestAmt());
	  		investmentInfo.setInvestCurr(investment.getInvestCurr());
	  		investmentInfo.setInvestYield(investment.getInvestYield());
	  		investmentInfo.setInvestIncome(investment.getInvestIncome());
	  		investmentInfo.setStartDate(investment.getStartDate());
	  		investmentInfo.setEndDate(investment.getEndDate());
			investmentInfo.setLastUpdateUser(auth.getUsername());
			investmentInfo.setLastUpdateTm(new Timestamp(new Date().getTime()));
			return super.save(investmentInfo);
		}
	}

	
}
