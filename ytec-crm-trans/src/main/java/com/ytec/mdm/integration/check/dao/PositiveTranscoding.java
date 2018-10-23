/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.dao
 * @�ļ�����PositiveTranscoding.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:48:52
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�PositiveTranscoding
 * @������������ת��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:48:52   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:48:52
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class PositiveTranscoding implements IMsgNodeFilter {
	private static Logger log = LoggerFactory
			.getLogger(PositiveTranscoding.class);

	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		String src_sys_cd=params[0];
		String cate_id=params[1];
		if(src_sys_cd==null||cate_id==null){
			log.warn("��ֵ�������Ϊ��");
			return null;
		}
		String code_id=null;
		if ((code_id=EcifPubDataUtils.getEcifCode(src_sys_cd,value,cate_id))!=null) {
			return code_id;
		} else{
			log.warn("ԭϵͳ��Ϊ{}����ֵ����Ϊ{}��Դ��ֵΪ{}ת��ʧ��",src_sys_cd,cate_id,value);
			if(StringUtil.isEmpty(expression)){
				return null;
			}else{
				code_id=expression.toString().replace("{0}", value).replace("{1}", src_sys_cd).replace("{2}", cate_id);
				log.warn("ԭ��ֵ{}��ת��Ϊ{}",value,code_id);
				return code_id;
			}
		}
	}

}
