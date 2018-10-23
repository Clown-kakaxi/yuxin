package com.yuchengtech.bcrm.custmanager.service;


import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.yuchengtech.bcrm.custmanager.model.AcrmACiProfRelation;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;


/**
 * 利润关联方管理
 * @author wangmk1
 *
 */
@Service
public class ProfitRelatedPartyManagementService extends CommonService {
		
	
	public ProfitRelatedPartyManagementService(){
		JPABaseDAO<AcrmACiProfRelation, String> baseDao = new JPABaseDAO<AcrmACiProfRelation, String>(AcrmACiProfRelation.class);
		super.setBaseDAO(baseDao);
	}
	/**
	 * 新增 或 修改  利润关联方管理
	 */
	public Object save(Object obj) {
//		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		AcrmACiProfRelation relation =(AcrmACiProfRelation) obj ;
//
//		//根据创建日期是否为空，判断是新增还是修改。 -------
//		if(null==relation.getCreatDate()){
//			relation.setCreatDate(new Date());
//		}
		return super.save(obj);
	}
	
	public void updateCustState(HttpServletRequest request){
        String creatTimes = request.getParameter("createTimes");
		this.em.createNativeQuery("update ACRM_A_CI_PROF_RELATION set r_state='1' where create_times='"+creatTimes+"'").executeUpdate();
	}
	
	public void updateCustObject(String custId,String createTimes){
		this.em.createNativeQuery("update ACRM_A_CI_PROF_RELATION set r_state='1',create_times="+createTimes+" where cust_id='"+custId+"'").executeUpdate();
	}
	
	public void deleteById(String sql){
		this.em.createNativeQuery(sql).executeUpdate();
	}
	
	
}
