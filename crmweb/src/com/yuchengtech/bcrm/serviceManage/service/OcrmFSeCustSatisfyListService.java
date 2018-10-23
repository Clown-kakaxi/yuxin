package com.yuchengtech.bcrm.serviceManage.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeCustSatisfyList;
import com.yuchengtech.bcrm.serviceManage.model.OcrmFSeCustSatisfyQa;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 满意度调查service
 * 
 * @author luyy
 * @since 2014-06-16
 */
@Service
public class OcrmFSeCustSatisfyListService extends CommonService {
   
   public OcrmFSeCustSatisfyListService(){
	   JPABaseDAO<OcrmFSeCustSatisfyList, Long>  baseDAO=new JPABaseDAO<OcrmFSeCustSatisfyList, Long>(OcrmFSeCustSatisfyList.class);  
		super.setBaseDAO(baseDAO);
	}
   
@SuppressWarnings("unchecked")
public void addSatisfy(OcrmFSeCustSatisfyList o,String title_result){
	   AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   o.setEvaluateDate(new Date());
	   o.setEvaluateName(auth.getUsername());
	   
	   this.save(o);
	   String midStr[] = title_result.substring(1, title_result.length()-1).replace("\"", "").split(",");
	   for(int i = 0; i<midStr.length;i++){
		   String[] df = midStr[i].split(":");
			if (df.length > 1 && (!"custName".equals(df[0]))
					&& (!"indageteQaScoring".equals(df[0]))
					&& (!"satisfyType".equals(df[0]))
					&& (!"papersId".equals(df[0])) && (!"custId".equals(df[0]))) {
			   String gg[]= df[1].split("_");
				   OcrmFSeCustSatisfyQa o2 = new OcrmFSeCustSatisfyQa();
					o2.setSatisfyId(o.getId().toString());
					o2.setTitleId(gg[0]);
					o2.setResultId(gg[1]);
					o2.setScoring(BigDecimal.valueOf(Long.parseLong(gg[2])));
					baseDAO.save(o2);   
		   }
	   }
		baseDAO.flush();
//	   //保存答案信息
//	   String[] tr = title_result.split(",");
//		for (String s : tr) {
//			String title = s.split(":")[0];
//			String result = s.split(":")[1];
//			String score = s.split(":")[2];
//			OcrmFSeCustSatisfyQa o2 = new OcrmFSeCustSatisfyQa();
//			o2.setSatisfyId(o.getId().toString());
//			o2.setTitleId(title);
//			o2.setResultId(result);
//			o2.setScoring(BigDecimal.valueOf(Long.parseLong(score)));
//			baseDAO.save(o2);
//		}
		
	
   }
   
}
