/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����DesUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:06:00
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util.crypt;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DesUtils
 * @��������Des����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:06:07   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:06:07
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class DesUtils {

	/**
	 * �ַ���Ĭ�ϼ�ֵ.
	 * 
	 * @��������:�ַ���Ĭ�ϼ�ֵ
	 */
	private static String strDefaultKey = "ytec_ecif";

	/**
	 * ���ܹ���.
	 * 
	 * @��������:���ܹ���
	 */
	private Cipher encryptCipher = null;

	/**
	 * ���ܹ���.
	 * 
	 * @��������:���ܹ���
	 */
	private Cipher decryptCipher = null;

	
	/**
	 * @��������:byteArr2HexStr
	 * @��������:��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]
	 * hexStr2ByteArr(String strIn) ��Ϊ�����ת������.
	 * @�����뷵��˵��:
	 * 		@param arrB
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// �Ѹ���ת��Ϊ����
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// С��0F������Ҫ��ǰ�油0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	
	/**
	 * @��������:hexStr2ByteArr
	 * @��������:����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)
	 * ��Ϊ�����ת������.
	 * @�����뷵��˵��:
	 * 		@param strIn
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	
	/**
	 *@���캯�� 
	 * @throws Exception
	 */
	public DesUtils() throws Exception {
		this(strDefaultKey);
	}

	
	/**
	 *@���캯�� 
	 * @param strKey
	 * @throws Exception
	 */
	public DesUtils(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * �����ֽ�����.
	 * 
	 * @param arrB
	 *            ����ܵ��ֽ�����
	 * @return ���ܺ���ֽ�����
	 * @throws Exception
	 *             the exception
	 * @��������:byte[] encrypt(byte[] arrB)
	 * @��������:
	 * @�����뷵��˵��: byte[] encrypt(byte[] arrB)
	 * @�㷨����:
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * �����ַ���.
	 * 
	 * @param strIn
	 *            ����ܵ��ַ���
	 * @return ���ܺ���ַ���
	 * @throws Exception
	 *             the exception
	 * @��������:String encrypt(String strIn)
	 * @��������:
	 * @�����뷵��˵��: String encrypt(String strIn)
	 * @�㷨����:
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * �����ֽ�����.
	 * 
	 * @param arrB
	 *            ����ܵ��ֽ�����
	 * @return ���ܺ���ֽ�����
	 * @throws Exception
	 *             the exception
	 * @��������:byte[] decrypt(byte[] arrB)
	 * @��������:
	 * @�����뷵��˵��: byte[] decrypt(byte[] arrB)
	 * @�㷨����:
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * �����ַ���.
	 * 
	 * @param strIn
	 *            ����ܵ��ַ���
	 * @return ���ܺ���ַ���
	 * @throws Exception
	 *             the exception
	 * @��������:String decrypt(String strIn)
	 * @��������:
	 * @�����뷵��˵��: String decrypt(String strIn)
	 * @�㷨����:
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ.
	 * 
	 * @param arrBTmp
	 *            ���ɸ��ַ������ֽ�����
	 * @return ���ɵ���Կ
	 * @throws Exception
	 *             the exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[8];

		// ��ԭʼ�ֽ�����ת��Ϊ8λ
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// ������Կ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	
	/**
	 * @��������:main
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@param args
	 * @�㷨����:
	 */
	public static void main(String[] args) {
		try {
			String desStr=null;
			String key=null;
			if (args.length == 1 ){
				desStr=args[0];
				DesUtils des = new DesUtils();
				System.out.println("���ܺ���ַ�[" + des.encrypt(desStr)+"]");
			}else{
				System.out.println("Usage: DesUtils �����ַ��� ");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
