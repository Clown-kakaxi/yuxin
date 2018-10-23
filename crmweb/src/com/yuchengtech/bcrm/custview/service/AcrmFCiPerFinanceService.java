package com.yuchengtech.bcrm.custview.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFinance;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * 个人理财规划推荐
 * @author denghj
 *
 */
@Service
public class AcrmFCiPerFinanceService extends CommonService {
	
	public AcrmFCiPerFinanceService(){
		JPABaseDAO<AcrmFCiPerFinance,Long> baseDao = new JPABaseDAO<AcrmFCiPerFinance,Long>(AcrmFCiPerFinance.class);
		super.setBaseDAO(baseDao);
	}
	
	/**
	 * 删除推荐记录
	 * @param sql
	 */
	@SuppressWarnings("unchecked")
	public void delete(String financeId){
		baseDAO.removeById(financeId);
	}
}
