package com.yuchengtech.bcrm.product.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowPlan;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdShowPlanService extends CommonService{
	
	   public OcrmFPdProdShowPlanService(){
		   JPABaseDAO<OcrmFPdProdShowPlan, String>  baseDAO=new JPABaseDAO<OcrmFPdProdShowPlan, String>(OcrmFPdProdShowPlan.class);  
		   super.setBaseDAO(baseDAO);
	   }
	  
	   //删除
	   public void batchDel(HttpServletRequest request) {
	   	String s[] = request.getParameter("idStr").split(",");
	   	String jql = null;
	   	HashMap<String, Object> values = null;
	   	for(int i=0;i<s.length;i++){
	   		// 删除OCRM_F_PD_PROD_SHOW_TABLE
	   		jql = "delete from  OcrmFPdProdShowTable p where p.planId = "+s[i]+"";
	   		values = new HashMap<String, Object>();
	   		super.batchUpdateByName(jql, values);
	   		// 删除OCRM_F_PD_PROD_SHOW_R
	   		jql = "delete from  OcrmFPdProdShowR p where p.planId = "+s[i]+"";
	   		values = new HashMap<String, Object>();
	   		super.batchUpdateByName(jql, values);
	   		// 删除OCRM_F_PD_PROD_SHOW_COLUMN
	   		jql = "delete from  OcrmFPdProdShowColumn p where p.planId = "+s[i]+"";
	   		values = new HashMap<String, Object>();
	   		super.batchUpdateByName(jql, values);
	   		
	   		//删除表OCRM_F_PD_PROD_SHOW_PLAN
	   		jql = "delete from  OcrmFPdProdShowPlan p where p.planId = "+Long.valueOf(s[i])+"";
	   		values = new HashMap<String, Object>();
	   		super.batchUpdateByName(jql, values);
	   	}
	   }
	
}
