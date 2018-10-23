package com.yuchengtech.bcrm.notice.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.system.model.OcrmSysRicheditInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/***
 * @author xiebz
 *
 */
@Service
public class OcrmSysRicheditInfoNoticeService extends CommonService {
	public OcrmSysRicheditInfoNoticeService(){
		JPABaseDAO<OcrmSysRicheditInfo,Long> baseDAO = new JPABaseDAO<OcrmSysRicheditInfo,Long>(OcrmSysRicheditInfo.class);
		super.setBaseDAO(baseDAO);
	}
}
