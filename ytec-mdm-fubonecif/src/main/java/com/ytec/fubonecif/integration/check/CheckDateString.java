/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.integration.check
 * @�ļ�����CheckDateString.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-11-����4:47:20
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.check;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.adapter.message.ReqMsgValidation;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�CheckDateString
 * @��������У��ת�����ڸ�ʽ���ַ����������ַ�����ʽ��ΪĿ���ʽ
 * @��������:�����ѯ����Ϊ���ڸ�ʽ���ַ���,��Ҫ��֤�ø�ʽ��ת����Ŀ���ʽ���������ʱѡ�������ͣ���Ȼ������֤������ѯʱ��ת������������Ϊ��������ѯ�������Ͳ�ƥ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-11 ����4:47:20   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-11 ����4:47:20
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CheckDateString implements IMsgNodeFilter {
	private Logger log = LoggerFactory.getLogger(CheckDateString.class);
	/**
	 *@���캯�� 
	 */
	public CheckDateString() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	@Override
	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		if (value == null || expression==null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat((String)expression);
		/****
		 * ʱ��:��ʽ�����ų�����Ĭ��ֵ��ǰ̨������ܻᴫ�� 0000-00-00,0000-01-01��Ĭ��ֵ����������˵������ǿ�
		 */
		if (ReqMsgValidation.excludeDates != null) {
			for (String excludeDate : ReqMsgValidation.excludeDates) {
				if (value.equals(excludeDate)) {
					log.info("���˵�����ʱ��{}",value);
					return "";
				}
			}
		}
		// ///////////////////////////////////////////////////////////////////////
		dateFormat.setLenient(false);
		Date dateval = StringUtil.reverse2Date(value, dateFormat);
		if (dateval != null) {
			value = dateFormat.format(dateval);
		}else{
			return null;
		}
		return value;
	}

}
