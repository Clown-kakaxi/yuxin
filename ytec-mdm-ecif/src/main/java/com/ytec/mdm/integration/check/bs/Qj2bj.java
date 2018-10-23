/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����Qj2bj.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-21-����11:36:29
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.bs;

import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�Qj2bj
 * @��������ȫ���ַ�->����ַ�ת�� 
 * @��������:ֻ����ȫ�ǵĿո�ȫ�ǣ���ȫ�ǡ�֮����ַ�������������
 * "Ϊ��ط�����12321������������������������������������������������������		�������������������������������ܣ�"
 * ת����"Ϊ��ط�����123211234567890~!@#��%����&��()-=����+		 ,��������?;:��������{}\|"
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-21 ����11:36:29   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-21 ����11:36:29
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class Qj2bj implements IMsgNodeFilter {
	/**
	 * ȫ�Ƕ�Ӧ��ASCII��Ŀɼ��ַ��ӣ���ʼ��ƫ��ֵΪ65281
	 */
	private static final char SBC_CHAR_START = 65281; // ȫ�ǣ�

	/**
	 * ȫ�Ƕ�Ӧ��ASCII��Ŀɼ��ַ�����������ƫ��ֵΪ65374
	 */
	private static final char SBC_CHAR_END = 65374; // ȫ�ǡ�

	/**
	 * ASCII���г��ո���Ŀɼ��ַ����Ӧ��ȫ���ַ������ƫ��
	 */
	private static final int CONVERT_STEP = 65248; // ȫ�ǰ��ת�����

	/**
	 * ȫ�ǿո��ֵ����û�������ASCII�����ƫ�ƣ����뵥������
	 */
	private static final char SBC_SPACE = 12288; // ȫ�ǿո� 12288

	/**
	 * ��ǿո��ֵ����ASCII��Ϊ32(Decimal)
	 */
	private static final char DBC_SPACE = ' '; // ��ǿո�
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter#execute(java.lang.Object, java.lang.String, java.lang.String[])
	 */
	@Override
	public String execute(Object expression, String value, String... params) {
		// TODO Auto-generated method stub
		if (value == null) {
			return value;
		}
		StringBuilder buf = new StringBuilder(value.length());
		char[] ca = value.toCharArray();
		for (int i = 0; i < value.length(); i++) {
			if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // ���λ��ȫ�ǣ���ȫ�ǡ�������
				buf.append((char) (ca[i] - CONVERT_STEP));
			} else if (ca[i] == SBC_SPACE) { // �����ȫ�ǿո�
				buf.append(DBC_SPACE);
			} else { // ������ȫ�ǿո�ȫ�ǣ���ȫ�ǡ���������ַ�
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}

}
