package com.yuchengtech.bcrm.customer.level.service;



import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFSysRuleScoreSet;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFSysRuleScoreSetService extends CommonService{
	
	   public OcrmFSysRuleScoreSetService(){
		   JPABaseDAO<OcrmFSysRuleScoreSet, Long>  baseDAO=new JPABaseDAO<OcrmFSysRuleScoreSet, Long>(OcrmFSysRuleScoreSet.class);  
		   super.setBaseDAO(baseDAO);
	   }
	   
	// 删除
		public void batchDel(HttpServletRequest request) {
			String s = request.getParameter("ids");
			JSONObject jsonObject = JSONObject.fromObject(s);
			JSONArray jarray = jsonObject.getJSONArray("id");
			for (int i = 0; i < jarray.size(); i++) {
				// 删除折算规则
				super.remove(Long.parseLong(jarray.get(i).toString()));
			}
		}
		
		
		
		
		//启用或者禁用
		public void batchUse(HttpServletRequest request) {
			String s = request.getParameter("ids");
			String use = request.getParameter("use");
			String jql = null;
			HashMap<String, Object> values = null;
			values = new HashMap<String, Object>();
			if("yes".equals(use)){
				jql = " update OcrmFSysRuleScoreSet p set p.status=:status where p.id in ("+s+")";
				values.put("status", "1");
			}
			if("no".equals(use)){
			jql = " update OcrmFSysRuleScoreSet p set p.status=:status where p.id in ("+s+")";
				values.put("status", "0");
			}
			
				super.batchUpdateByName(jql, values);
		}
}
