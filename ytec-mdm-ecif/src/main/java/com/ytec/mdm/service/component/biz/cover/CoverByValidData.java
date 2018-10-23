/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.cover
 * @�ļ�����CoverByValidData.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-20-����3:58:25
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CoverByValidData
 * @�����������ݸ��ǹ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-6-20 ����3:58:25   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-6-20 ����3:58:25
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class CoverByValidData extends AbsCoverByValidData {

	/**
	 *@���캯�� 
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
		 * ��Ч��ֵ���ܸ�����Ч
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
