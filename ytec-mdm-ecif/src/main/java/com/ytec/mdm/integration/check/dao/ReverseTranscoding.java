/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.dao
 * @�ļ�����ReverseTranscoding.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:49:27
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
 * @�����ƣ�ReverseTranscoding
 * @������������ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:49:40   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:49:40
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ReverseTranscoding implements IMsgNodeFilter {

	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory
			.getLogger(ReverseTranscoding.class);
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	public String execute(Object expression, String value, String... params) {
		String src_sys_cd=params[0];
		String cate_id=params[1];
		if(src_sys_cd==null||cate_id==null){
			log.warn("��ֵ�������Ϊ��");
			return null;
		}
		String s_code=null;
		if ((s_code=EcifPubDataUtils.getSrcCode(src_sys_cd,value,cate_id))!=null) {
			return s_code;
		} else{
			log.warn("ԭϵͳ��Ϊ{}����ֵ����Ϊ{}��ECIF��ֵΪ{}����ת��ʧ��",src_sys_cd,cate_id,value);
			return value;
		}
	}

}
