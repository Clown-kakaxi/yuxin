package com.yuchengtech.bcrm.customer.service;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGradeApply;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * @author huwei
 * 客户评级申请
 *
 */
@Service
public class OcrmFCiGradeApplyService extends CommonService{
	public OcrmFCiGradeApplyService(){
		JPABaseDAO<OcrmFCiGradeApply, Long> baseDAO = new JPABaseDAO<OcrmFCiGradeApply, Long>(OcrmFCiGradeApply.class);
		super.setBaseDAO(baseDAO);
	}
	
	public Object save(Object model) {
		OcrmFCiGradeApply ocrmFMmMaterialDesc=(OcrmFCiGradeApply)model;
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ocrmFMmMaterialDesc.setApplyDate(new Date());
		ocrmFMmMaterialDesc.setApplyOrg(auth.getUnitName());
		ocrmFMmMaterialDesc.setApplyUser(auth.getUsername());
		ocrmFMmMaterialDesc.setStatus("等待提交");
		return super.save(ocrmFMmMaterialDesc);
	}
	
}
