package com.yuchengtech.bcrm.custview.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.OcrmFCiCustFeedbackInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 客户反馈信息Service
 * @author YOYOGI
 * 2014-7-27
 */
@Service
public class OcrmFCiCustFeedbackInfoService extends CommonService {

	public OcrmFCiCustFeedbackInfoService(){
		JPABaseDAO<OcrmFCiCustFeedbackInfo, Long>  baseDAO=new JPABaseDAO<OcrmFCiCustFeedbackInfo, Long>(OcrmFCiCustFeedbackInfo.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 删除
	 */
	public void batchDel(HttpServletRequest request) {
		String feedbackId = request.getParameter("feedbackId");
		this.em.createNativeQuery("delete from OCRM_F_CI_CUST_FEEDBACK_INFO where ID in ("+feedbackId+")").executeUpdate();
	}
}
