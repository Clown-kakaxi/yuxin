/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：DesUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:06:00
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util.crypt;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DesUtils
 * @类描述：Des加密
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:06:07   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:06:07
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class DesUtils {

	/**
	 * 字符串默认键值.
	 * 
	 * @属性描述:字符串默认键值
	 */
	private static String strDefaultKey = "ytec_ecif";

	/**
	 * 加密工具.
	 * 
	 * @属性描述:加密工具
	 */
	private Cipher encryptCipher = null;

	/**
	 * 解密工具.
	 * 
	 * @属性描述:解密工具
	 */
	private Cipher decryptCipher = null;

	
	/**
	 * @函数名称:byteArr2HexStr
	 * @函数描述:将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程.
	 * @参数与返回说明:
	 * 		@param arrB
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
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
	 * @函数名称:hexStr2ByteArr
	 * @函数描述:将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程.
	 * @参数与返回说明:
	 * 		@param strIn
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	
	/**
	 *@构造函数 
	 * @throws Exception
	 */
	public DesUtils() throws Exception {
		this(strDefaultKey);
	}

	
	/**
	 *@构造函数 
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
	 * 加密字节数组.
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 *             the exception
	 * @函数名称:byte[] encrypt(byte[] arrB)
	 * @函数描述:
	 * @参数与返回说明: byte[] encrypt(byte[] arrB)
	 * @算法描述:
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串.
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 *             the exception
	 * @函数名称:String encrypt(String strIn)
	 * @函数描述:
	 * @参数与返回说明: String encrypt(String strIn)
	 * @算法描述:
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节数组.
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             the exception
	 * @函数名称:byte[] decrypt(byte[] arrB)
	 * @函数描述:
	 * @参数与返回说明: byte[] decrypt(byte[] arrB)
	 * @算法描述:
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串.
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 *             the exception
	 * @函数名称:String decrypt(String strIn)
	 * @函数描述:
	 * @参数与返回说明: String decrypt(String strIn)
	 * @算法描述:
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位.
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 *             the exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	
	/**
	 * @函数名称:main
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@param args
	 * @算法描述:
	 */
	public static void main(String[] args) {
		try {
			String desStr=null;
			String key=null;
			if (args.length == 1 ){
				desStr=args[0];
				DesUtils des = new DesUtils();
				System.out.println("加密后的字符[" + des.encrypt(desStr)+"]");
			}else{
				System.out.println("Usage: DesUtils 加密字符串 ");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
