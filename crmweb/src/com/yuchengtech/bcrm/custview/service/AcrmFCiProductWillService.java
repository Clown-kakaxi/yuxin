package com.yuchengtech.bcrm.custview.service;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiProductWill;
import com.yuchengtech.bcrm.custview.model.AcrmFCiProductWillTemp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 对私客户视图 客户产品意愿信息
 * @author agile
 *
 */
@Service
public class AcrmFCiProductWillService extends CommonService {
	
	public AcrmFCiProductWillService(){
		JPABaseDAO<AcrmFCiProductWill,String> baseDao = new JPABaseDAO<AcrmFCiProductWill,String>(AcrmFCiProductWill.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiProductWill will = (AcrmFCiProductWill)obj;
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiProductWill will2  = (AcrmFCiProductWill)this.find(will.getCustId());
		if(will2==null){
			will.setLastUpdateTm(new Date());
			will.setLastUpdateUser(auth.getUserId());
			will.setState("0");//0 暂存 1审核中，2审核通过
		}else{
			will.setLastUpdateTm(new Date());
			will.setLastUpdateUser(auth.getUserId());
		}
		return super.save(will);
	}
	
	public  void saveTemp(String financialProducts,String loanType,String collateral,String productType,String custId) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiProductWillTemp info = (AcrmFCiProductWillTemp)this.em.find(AcrmFCiProductWillTemp.class,custId);
		if(info!=null){
			info.setCollateral(collateral);
			info.setFinancialProducts(financialProducts);
			info.setLoanType(loanType);
			info.setProductType(productType);
			info.setLastUpdateTm(new Date());
			info.setLastUpdateUser(auth.getUserId());
			super.save(info);
		}else{
			AcrmFCiProductWillTemp temp = new AcrmFCiProductWillTemp();
			temp.setCustId(custId);
			temp.setCollateral(collateral);
			temp.setFinancialProducts(financialProducts);
			temp.setLoanType(loanType);
			temp.setProductType(productType);
			temp.setLastUpdateTm(new Date());
			temp.setLastUpdateUser(auth.getUserId());
			super.save(temp);
		}
	}
}
