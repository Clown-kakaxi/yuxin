/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.cover
 * @文件名：CoverByValidData.java
 * @版本信息：1.0.0
 * @日期：2014-6-20-下午3:58:25
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.biz.cover;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CoverByValidData
 * @类描述：数据覆盖规则
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-6-20 下午3:58:25   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-6-20 下午3:58:25
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
public class CoverByValidData extends AbsCoverByValidData {

	/**
	 *@构造函数 
	 */
	public CoverByValidData() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.service.component.biz.cover.AbsCoverByValidData#useCoveringRule(com.ytec.mdm.base.bo.EcifData, java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean useCoveringRule(EcifData ecifData,
			String tableEntityName, String fieldName) {
		// TODO Auto-generated method stub
		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.service.component.biz.cover.AbsCoverByValidData#exeCoveringRule(com.ytec.mdm.base.bo.EcifData, java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	protected boolean exeCoveringRule(EcifData ecifData,
			String tableEntityName, String fieldName, Object newValue) {
		// TODO Auto-generated method stub
		/***
		 * 无效码值不能覆盖有效
		 */
		if (EcifPubDataUtils.isCodeTableColumn(tableEntityName, fieldName)) {
			if (newValue.toString().startsWith(
					BusinessCfg.getString("TX_DECODE_NOT_STR"))) {
				return false;
			}
		}
		
		return true;
	}

}
