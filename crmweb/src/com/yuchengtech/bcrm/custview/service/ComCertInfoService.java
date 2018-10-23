package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.custview.model.AcrmFCiCertInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

@Service
@Transactional(value = "postgreTransactionManager")
public class ComCertInfoService extends CommonService {
	
	
	public ComCertInfoService(){
		JPABaseDAO<AcrmFCiCertInfo, Integer>  baseDAO = new JPABaseDAO<AcrmFCiCertInfo, Integer>(AcrmFCiCertInfo.class);  
		super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 服务层方法
	 * 根据主键判断，调用新增或修改方法
	 * @param acrmFCiCertInfo 页面实体对象
	 * @author isAddFlag 新增修改标识
	 */
	public void save(AcrmFCiCertInfo acrmFCiCertInfo, Boolean isAddFlag) throws BizException {
		String flag = null;
		if (isAddFlag) {
			flag = "新增";
		} else {
			flag = "修改";
		}
		try {
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Date date = new Date();
			long t = System.currentTimeMillis();
		    if (isAddFlag) {
		    	if (acrmFCiCertInfo.getIdentId() == null) {
		    		throw new BizException(1, 2, "1003", "ECIF返回主键为空,请联系哈尔滨银行!");
		    	} 
		    	acrmFCiCertInfo.setLasupdOpr(auth.getUserId());
		        acrmFCiCertInfo.setLasupdDate(date);
		        acrmFCiCertInfo.setLasupdTime(new Timestamp(t));
				em.persist(acrmFCiCertInfo);
			} else {
				acrmFCiCertInfo.setLasupdOpr(auth.getUserId());
		        acrmFCiCertInfo.setLasupdDate(date);
		        acrmFCiCertInfo.setLasupdTime(new Timestamp(t));
				em.merge(acrmFCiCertInfo);
			}
		} catch(BizException e) {
			e.printStackTrace();
			throw new BizException(1, e.getLevel(), e.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "1002", "客户证件信息服务层维护"+flag+"失败!");
		}

	}
}
