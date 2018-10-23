package com.yuchengtech.bcrm.customer.level.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.AcrmAAntiHighIndex;
import com.yuchengtech.bcrm.customer.level.model.OcrmFCiAntiIndexInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiAntiIndexInfoService extends CommonService{
	public OcrmFCiAntiIndexInfoService(){
		JPABaseDAO<OcrmFCiAntiIndexInfo,Long> baseDao = new JPABaseDAO<OcrmFCiAntiIndexInfo,Long>(OcrmFCiAntiIndexInfo.class);
		super.setBaseDAO(baseDao);
	}
	/**
	 * 手工设置反洗钱高风险指标
	 * @param info
	 * @param jarray
	 */
	public void saveHighIndex(OcrmFCiAntiIndexInfo info,String jarray){
		//先删除后插入
		this.batchUpdateByName("delete from AcrmAAntiHighIndex C where C.indexId = '"+info.getIndexValue()+"' and C.indexCode = '"+info.getIndexCode()+"'", null);
		if("1".equals(jarray)){//设置为是高风险时 写入表
			AcrmAAntiHighIndex  index = new AcrmAAntiHighIndex();
			index.setIndexCode(info.getIndexCode());
			index.setIndexId(info.getIndexValue());
			this.save(index);
		}
	}
}
