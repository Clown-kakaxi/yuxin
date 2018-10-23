package com.yuchengtech.bcrm.notice.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.notice.model.OcrmSysRicheditInfoTemp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/***
 * @author xiebz
 *
 */
@Service
public class OcrmSysRicheditInfoNoticeTempService extends CommonService {
	public OcrmSysRicheditInfoNoticeTempService(){
		JPABaseDAO<OcrmSysRicheditInfoTemp,Long> baseDAO = new JPABaseDAO<OcrmSysRicheditInfoTemp,Long>(OcrmSysRicheditInfoTemp.class);
		super.setBaseDAO(baseDAO);
	}
}
