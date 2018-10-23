/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util.crypt
 * @�ļ�����CHexConver.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-10-����1:49:00
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util.crypt;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�CHexConver
 * @��������Hex 16���Ƶ� byte String ת���� 
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-10 ����1:49:00
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-10 ����1:49:00
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CHexConver {
	/**
	 * �ַ���ת����ʮ�������ַ���
	 * 
	 * @param String
	 *            str ��ת����ASCII�ַ���
	 * @return String
	 */
	public static String str2HexStr(String str) {
		final StringBuilder hexString = new StringBuilder();
		byte[] byteArray=str.getBytes();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10)//0~Fǰ�治��
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return hexString.toString().toUpperCase();
	}

	/**
	 * ʮ������ת���ַ���
	 * 
	 * @param String
	 *            str Byte�ַ���(Byte֮���޷ָ��� ��:[616C6B])
	 * @return String ��Ӧ���ַ���
	 */
	public static String hexStr2Str(String hexStr) {
		hexStr = hexStr.toLowerCase();
		final byte[] byteArray = new byte[hexStr.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
                        //��Ϊ��16���ƣ����ֻ��ռ��4λ��ת�����ֽ���Ҫ����16���Ƶ��ַ�����λ����
			byte high = (byte) (Character.digit(hexStr.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexStr.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return new String(byteArray);

	}
	
	/**
	 * �ַ���ת����ʮ�������ַ���
	 * 
	 * @param String
	 *            str ��ת����ASCII�ַ���
	 * @return String
	 */
	public static String byte2HexStr(byte[] byteArray) {
		final StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10)//0~Fǰ�治��
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return hexString.toString().toUpperCase();
	}

	/**
	 * String���ַ���ת����unicode��String
	 * 
	 * @param String
	 *            strText ȫ���ַ���
	 * @return String ÿ��unicode֮���޷ָ���
	 * @throws Exception
	 */
	public static String strToUnicode(String strText) throws Exception {
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128)
				str.append("\\u" + strHex);
			else
				// ��λ��ǰ�油00
				str.append("\\u00" + strHex);
		}
		return str.toString();
	}

	/**
	 * unicode��Stringת����String���ַ���
	 * 
	 * @param String
	 *            hex 16����ֵ�ַ��� ��һ��unicodeΪ2byte��
	 * @return String ȫ���ַ���
	 */
	public static String unicodeToString(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// ��λ��Ҫ����00��ת
			String s1 = s.substring(2, 4) + "00";
			// ��λֱ��ת
			String s2 = s.substring(4);
			// ��16���Ƶ�stringתΪint
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// ��intת��Ϊ�ַ�
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}
	
}
