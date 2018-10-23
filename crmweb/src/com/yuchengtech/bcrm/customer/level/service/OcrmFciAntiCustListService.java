package com.yuchengtech.bcrm.customer.level.service;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiCustList;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class OcrmFciAntiCustListService extends CommonService{
	public OcrmFciAntiCustListService(){
		JPABaseDAO<OcrmFCiAntiCustList,Long> baseDao = new JPABaseDAO<OcrmFCiAntiCustList,Long>(OcrmFCiAntiCustList.class);
		super.setBaseDAO(baseDao);
	}
	/**
	 * 更新客户等级信息 
	 * @param list
	 */
	public void updateGrade(OcrmFCiAntiCustList list){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.batchUpdateByName(" update AcrmFCiGrade g set " +
    			" g.custGrade = '"+list.getRiskLevel()+"', " +
    			" g.evaluateDate = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'," +
    			" g.lastUpdateUser = '"+auth.getUserId()+"'," +
    			" g.orgCode = '"+auth.getUnitId()+"', " +
    			" g.orgName = '"+auth.getUnitName()+"', " +
    		    " g.lastUpdateTm = '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"'" +
    			" where g.custId='"+list.getCustId()+"' and g.custGradeType = '01' ", null);
	}
}
