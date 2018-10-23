/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.dao
 * @�ļ�����CheckStdCode.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:48:00
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CheckStdCode
 * @��������У���Ƿ��Ǳ�׼��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:48:01   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:48:01
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CheckStdCode implements IMsgNodeFilter{
	private static Logger log = LoggerFactory
			.getLogger(CheckStdCode.class);
	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		String cate_id=params[1];
		if(cate_id==null){
			log.warn("��ֵ�������Ϊ��");
			return null;
		}
		if(EcifPubDataUtils.isEcifStdCode(value, cate_id)){
			return value;
		}else{
			log.warn("��ֵ���{}����ֵ{}����ECIF��׼��",cate_id,value);
			return null;
		}
	}

}
