package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.AcrmACiProfRelation;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class ProfitRelaShipService extends CommonService{
	
	public ProfitRelaShipService(){
		JPABaseDAO<AcrmACiProfRelation, String> baseDao = new JPABaseDAO<AcrmACiProfRelation, String>(AcrmACiProfRelation.class);
		super.setBaseDAO(baseDao);
	}
	
	public void deleteById(HttpServletRequest request){
		String ids = request.getParameter("idStr");
		this.em.createNativeQuery("delete from ACRM_A_CI_PROF_RELATION where id in ("+ids+")").executeUpdate();
	}

}
