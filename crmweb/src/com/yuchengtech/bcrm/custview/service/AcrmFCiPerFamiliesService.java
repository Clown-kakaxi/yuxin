package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamilies;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 成员 Service
 * @author YOYOGI
 * 2014-8-15
 */
@Service
public class AcrmFCiPerFamiliesService extends CommonService {

	public AcrmFCiPerFamiliesService(){
		JPABaseDAO<AcrmFCiPerFamilies, Long>  baseDAO=new JPABaseDAO<AcrmFCiPerFamilies, Long>(AcrmFCiPerFamilies.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 新增+修改家庭成员
	 */
	public Object save(Object obj){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiPerFamilies member = (AcrmFCiPerFamilies) obj;
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		if(member.getMxtid() == null){
			member.setLastUpdateSys("CRM");
			member.setLastUpdateUser(auth.getUserId());
			member.setLastUpdateTm(now);
			return super.save(member);
		}else{
			member.setLastUpdateSys("CRM");
			member.setLastUpdateUser(auth.getUserId());
			member.setLastUpdateTm(now);
	  		return super.save(member);
		}
	}
	
	/**
	 * 删除家庭成员
	 */
	public void batchRemove(String id) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM AcrmFCiPerFamilies fmy WHERE fmy.mxtid IN('"+ id +"')", values);
    }
}
