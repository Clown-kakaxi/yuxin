package com.yuchengtech.bcrm.oneKeyAccount.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

public class HSDES {
	
	/**
	 * 使用"12345678"为密钥生成4字节的MAC，ASCII字符
	 * @param datain 整个报文
	 * @return 4字节的MAC，ASCII字符
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] genMAC(byte datain[]) throws UnsupportedEncodingException
	{
		return genMAC(datain, "12345678".getBytes());
	}

	
	/**
	 * 生成4字节的MAC，ASCII字符
	 * @param datain 整个报文
	 * @param key DES加密密钥
	 * @return 4字节的MAC，ASCII字符
	 */
	public static byte[] genMAC(byte datain[], byte key[])
	{
		try
		{
			//初始化密钥基础设施
			SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key));
			Cipher c = Cipher.getInstance("DES/ECB/NoPadding");
	        c.init(Cipher.ENCRYPT_MODE, desKey);

	        //至少补一个8字节块
			int len = (datain.length / 8 + 1) * 8;
	
			byte data[] = new byte[len];
			System.arraycopy(datain, 0, data, 0, datain.length);
			//补的数据以0x80打头，后面是0;
			data[datain.length] = (byte)0x80;
			
			//加密缓冲区
			byte buff[] = new byte[8];
			for (int j=0;j<len;j+=8)
			{
				//以8字节为一块循环
				for (int i=0;i<8;i++)
				{
					if (j == 0)
						buff[i] = data[i];
					else
						buff[i] ^= data[j + i];
				}
				buff = c.doFinal(buff);
				
				//使用加密后值的前四字节的大写HEX码作为下一次运算的值 —— 略奇葩
				char hex[] = Hex.encodeHex(buff, false);
				for (int i=0;i<8;i++)
					buff[i] = (byte)hex[i];
			}
			
			//取最后值的前四字节为MAC值
			return buff;
		}
		catch (Exception e)
		{
			//所有异常都当运行时抛出
			throw new IllegalArgumentException(e);
		}
	}
}
