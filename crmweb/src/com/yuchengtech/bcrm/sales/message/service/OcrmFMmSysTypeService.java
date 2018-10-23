package com.yuchengtech.bcrm.sales.message.service;




import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.sales.message.model.OcrmFMmSysType;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFMmSysTypeService extends CommonService{
//	
	public OcrmFMmSysTypeService(){
		
		JPABaseDAO<OcrmFMmSysType, Long>  baseDAO=new JPABaseDAO<OcrmFMmSysType, Long>(OcrmFMmSysType.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String s = request.getParameter("ids");
		JSONObject jsonObject = JSONObject.fromObject(s);
		JSONArray jarray = jsonObject.getJSONArray("id");
		for (int i = 0; i < jarray.size(); i++) {
			super.remove(Long.parseLong(jarray.get(i).toString()));
		}
	}
}
