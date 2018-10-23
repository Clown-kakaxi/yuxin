package com.ytec.mdm.base.util.crypt;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�EncrypDES3
 * @��������DES3����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:10:30   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:10:30
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EncrypDES3 {
	private static String strDefaultKey = "ytec_ecif_EncrypDES3";
	// SecretKey ���𱣴�Գ���Կ
	private Key deskey;
	// Cipher������ɼ��ܻ���ܹ���
	private Cipher c;
	
	/**
	 * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]
	 * hexStr2ByteArr(String strIn) ��Ϊ�����ת������
	 * 
	 * @param arrB
	 *            ��Ҫת����byte����
	 * @return ת������ַ���
	 * @throws Exception
	 *             �������������κ��쳣�������쳣ȫ���׳�
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
	 * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)
	 * ��Ϊ�����ת������
	 * 
	 * @param strIn
	 *            ��Ҫת�����ַ���
	 * @return ת�����byte����
	 * @throws Exception
	 *             �������������κ��쳣�������쳣ȫ���׳�
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		strIn = strIn.toLowerCase();
		final byte[] byteArray = new byte[strIn.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
                        //��Ϊ��16���ƣ����ֻ��ռ��4λ��ת�����ֽ���Ҫ����16���Ƶ��ַ�����λ����
			byte high = (byte) (Character.digit(strIn.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(strIn.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	public EncrypDES3() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		// ������Կ
		deskey = getKey(strDefaultKey.getBytes());
		// ����Cipher����,ָ����֧�ֵ�DES�㷨
		c = Cipher.getInstance("DESede");
	}
	
	public EncrypDES3(String keyString) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		// ������Կ
		deskey = getKey(keyString.getBytes());
		// ����Cipher����,ָ����֧�ֵ�DES�㷨
		c = Cipher.getInstance("DESede");
	}

	/**
	 * ���ַ�������
	 * 
	 * @param str
	 * @return 
	 */
	public String Encrytor(String str) throws Exception {
		// ������Կ����Cipher������г�ʼ����ENCRYPT_MODE��ʾ����ģʽ
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = str.getBytes();
		// ���ܣ���������cipherByte
		byte[] cipherByte = c.doFinal(src);
		return byteArr2HexStr(cipherByte);
	}

	/**
	 * ���ַ�������
	 * 
	 * @param buff
	 * @return
	 * @throws Exception 
	 */
	public String Decryptor(String mm) throws Exception {
		// ������Կ����Cipher������г�ʼ����DECRYPT_MODE��ʾ����ģʽ
		byte[] buff=hexStr2ByteArr(mm);
		c.init(Cipher.DECRYPT_MODE, deskey);
		return new String(c.doFinal(buff));
	}
	
	
	/**
	 * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
	 * 
	 * @param arrBTmp
	 *            ���ɸ��ַ������ֽ�����
	 * @return ���ɵ���Կ
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[24];
		// ��ԭʼ�ֽ�����ת��Ϊ8λ
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// ������Կ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DESede");
		return key;
	}

	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws Exception {		
		try {
			String desStr=null;
			String key=null;
			if (args.length == 1 ){
				desStr=args[0];
				EncrypDES3 des = new EncrypDES3();
				System.out.println("���ܺ���ַ���[" + des.Encrytor(desStr)+"]");
			}else{
				System.out.println("Usage: EncrypDES3 �����ַ��� ");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
