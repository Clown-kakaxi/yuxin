package com.yuchengtech.bcrm.workplat.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.workplat.model.OcrmFHoliday;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFHolidayService extends CommonService {
	public OcrmFHolidayService(){
		
		JPABaseDAO<OcrmFHoliday, Long>  baseDAO=new JPABaseDAO<OcrmFHoliday, Long>(OcrmFHoliday.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String id = request.getParameter("id");
		this.em.createNativeQuery("delete from OCRM_F_HOLIDAY where ID in ("+id+")").executeUpdate();
	}

}
