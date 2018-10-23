package com.yuchengtech.bcrm.product.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.product.model.OcrmFPdProdTable;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFPdProdTableService extends CommonService{
	
	   public OcrmFPdProdTableService(){
		   JPABaseDAO<OcrmFPdProdTable, String>  baseDAO=new JPABaseDAO<OcrmFPdProdTable, String>(OcrmFPdProdTable.class);  
		   super.setBaseDAO(baseDAO);
	   }

	 //删除
	   public void batchDel(HttpServletRequest request) {
	   	String s[] = request.getParameter("idStr").split(",");
	   	String jql = null;
	   	HashMap<String, Object> values = null;
	   	for(int i=0;i<s.length;i++){
	   		// 删除属性配置
	   		jql = "delete from  OcrmFPdProdColumn p where p.tableId = "+s[i]+"";
	   		values = new HashMap<String, Object>();
	   		super.batchUpdateByName(jql, values);
	   		//删除表
	   		jql = "delete from  OcrmFPdProdTable p where p.tableId = "+Long.valueOf(s[i])+"";
	   		values = new HashMap<String, Object>();
	   		super.batchUpdateByName(jql, values);
	   	}
	   }
	
}
