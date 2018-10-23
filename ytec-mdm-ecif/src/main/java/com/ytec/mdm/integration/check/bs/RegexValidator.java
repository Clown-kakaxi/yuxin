/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����RegexValidator.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:44:26
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.bs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�RegexValidator
 * @��������������֤
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:44:27   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:44:27
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class RegexValidator implements IMsgNodeFilter {

	public String execute(Object expression, String value, String... params) {
		if(expression instanceof Pattern){
			Matcher match = ((Pattern)expression).matcher(value);
			if(!match.matches()){
				return null;
			}
		}
		return value;
	}
}
