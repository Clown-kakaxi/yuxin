/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����ChangeOrgCode.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:42:06
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.bs;

import org.apache.commons.lang.StringUtils;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ChangeOrgCode
 * @����������֯����������֤
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:42:11   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:42:11
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ChangeOrgCode implements IMsgNodeFilter {
	private final static int[] wi = { 3, 7, 9, 10, 5, 8, 4, 2 };
	public String execute(Object expression, String orgCode, String... params) {
		if (StringUtils.isEmpty(orgCode)) {
			return null;
		}
		orgCode = orgCode.replace("x", "X");
		if (orgCode.length() == 9) {
			orgCode = orgCode.substring(0, 8) + "-" + orgCode.charAt(8);
		}
		if (!orgCode.matches("^[0-9A-Z]{8}-[0-9X]$")) {
			return null;
		}
		String[] all = orgCode.split("-");
		char[] values = all[0].toCharArray();
		int parity = 0;
		for (int i = 0; i < values.length; i++) {
			if(values[i]<='9' && values[i]>='0'){
				parity += wi[i] * (values[i]-'0');
			}else{
				parity += wi[i] * (values[i]-55);
			}
		}
		String cheak = (11 - parity % 11) == 10 ? "X" : Integer
				.toString((11 - parity % 11));
		if (cheak.equals("11")) {
			cheak = "0";
		}
		if (cheak.equals(all[1])) {
			return orgCode;
		} else {
			return null;
		}
	}
}
