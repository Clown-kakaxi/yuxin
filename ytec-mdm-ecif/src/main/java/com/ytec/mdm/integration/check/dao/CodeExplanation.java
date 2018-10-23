/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.dao
 * @�ļ�����CodeExplanation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:48:32
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
 * @�����ƣ�CodeExplanation
 * @����������ֵ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:48:32   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:48:32
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CodeExplanation implements IMsgNodeFilter {
	private static Logger log = LoggerFactory
			.getLogger(CodeExplanation.class);
	public String execute(Object expression, String value, String... params) {
		String cate_id=params[1];
		if(cate_id==null){
			log.warn("��ֵ�������Ϊ��");
			return null;
		}
		String desc=null;
		if((desc=EcifPubDataUtils.getStdCodeDes(value, cate_id))!=null){
			return desc;
		}else{
			log.warn("��ֵ���{}����ֵ{}����Ϊ��",cate_id,value);
			return "";
		}
	}

}
