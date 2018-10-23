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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：EncrypDES3
 * @类描述：DES3加密
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:10:30   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:10:30
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class EncrypDES3 {
	private static String strDefaultKey = "ytec_ecif_EncrypDES3";
	// SecretKey 负责保存对称密钥
	private Key deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	
	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		strIn = strIn.toLowerCase();
		final byte[] byteArray = new byte[strIn.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
                        //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(strIn.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(strIn.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	public EncrypDES3() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		// 生成密钥
		deskey = getKey(strDefaultKey.getBytes());
		// 生成Cipher对象,指定其支持的DES算法
		c = Cipher.getInstance("DESede");
	}
	
	public EncrypDES3(String keyString) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		// 生成密钥
		deskey = getKey(keyString.getBytes());
		// 生成Cipher对象,指定其支持的DES算法
		c = Cipher.getInstance("DESede");
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return 
	 */
	public String Encrytor(String str) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = str.getBytes();
		// 加密，结果保存进cipherByte
		byte[] cipherByte = c.doFinal(src);
		return byteArr2HexStr(cipherByte);
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws Exception 
	 */
	public String Decryptor(String mm) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		byte[] buff=hexStr2ByteArr(mm);
		c.init(Cipher.DECRYPT_MODE, deskey);
		return new String(c.doFinal(buff));
	}
	
	
	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[24];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
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
				System.out.println("加密后的字符串[" + des.Encrytor(desStr)+"]");
			}else{
				System.out.println("Usage: EncrypDES3 加密字符串 ");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
