package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFSosService;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class AcrmFSosServiceService extends CommonService {

	public AcrmFSosServiceService(){
		JPABaseDAO<AcrmFSosService, Long>  baseDAO=new JPABaseDAO<AcrmFSosService, Long>(AcrmFSosService.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	
	/**
	 * 删除   钻石卡SOS接机服务信息
	 */
	public void batchRemove(String id) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM AcrmFSosService sos WHERE sos.id IN ("+ id +")", values);
    }
}
