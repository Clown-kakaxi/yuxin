package com.yuchengtech.bcrm.oneKeyAccount.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import com.csii.pe.validation.ValidationException;

public class ThreeDes {
	
	private static String keybytesHex;
	
	
	public String getKeybytesHex() {
		return keybytesHex;
	}
	public void setKeybytesHex(String keybytesHex) {
		this.keybytesHex = keybytesHex;
	}

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
														// DES,DESede,Blowfish
	private final static byte[] hex = "0123456789ABCDEF".getBytes();  
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	private static int parse(char c) {  
	    if (c >= 'a')  
	        return (c - 'a' + 10) & 0x0f;  
	    if (c >= 'A')  
	        return (c - 'A' + 10) & 0x0f;  
	    return (c - '0') & 0x0f;  
	}  
	public static String Bytes2HexString(byte[] b) {  
	    byte[] buff = new byte[2 * b.length];  
	    for (int i = 0; i < b.length; i++) {  
	        buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
	        buff[2 * i + 1] = hex[b[i] & 0x0f];  
	    }  
	    return new String(buff);  
	}  
	// 从十六进制字符串到字节数组转换  
		public static byte[] HexString2Bytes(String hexstr) {  
		    byte[] b = new byte[hexstr.length() / 2];  
		    int j = 0;  
		    for (int i = 0; i < b.length; i++) {  
		        char c0 = hexstr.charAt(j++);  
		        char c1 = hexstr.charAt(j++);  
		        b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
		    }  
		    return b;  
		}  
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
	 
		public static String getPinBlock(String pwd,String cardNo) {
			String cardNotemp = "";
			if(cardNo.length()>12){
				cardNotemp=cardNo.substring(cardNo.length()-13, cardNo.length()-1);
				cardNotemp="0000"+cardNotemp;
				
			//	System.out.println(cardNotemp);
			}else{
				cardNotemp=cardNo.substring(0, cardNo.length()-1);
				int templength=cardNotemp.length();
				for(int i=0;i<12-templength;i++){
					
					cardNotemp="0"+cardNotemp;
				}
			}
			String  pwdtemp;
			if(pwd.length()<10){
			pwdtemp="0"+pwd.length()+pwd;
			}else{
				pwdtemp=pwd.length()+pwd;
			}
			int needpwdlength=7-pwd.length()/2;
			String needFF="";
			for(int i=0;i<needpwdlength;i++){
				needFF+="FF";
			}
			pwdtemp=pwdtemp+needFF;
		byte[] cardNobytes=	HexString2Bytes(cardNotemp);
		byte[]  pwdbytes=HexString2Bytes(pwdtemp);
		byte[]  finalbytes=new byte[cardNobytes.length];
		        for(int i=0;i<cardNobytes.length;i++){
		        	finalbytes[i]=(byte) (cardNobytes[i]^pwdbytes[i]);
		        	 
		        }
//		 	 System.out.println(Bytes2HexString(finalbytes));
			return Bytes2HexString(finalbytes);
		}

 	/* public static void main(String[] args) {
		// 添加新安全算法,如果用JCE就要把它添加进去
	//	Security.addProvider(new com.sun.crypto.provider.SunJCE());
  //   getPinBlock("123456", "1234567890123456");
	 final byte[] keyBytes = { 0x36, 0x36, 0x36, 0x36,  0x36, 0x36,
				0x36, 0x36, 0x36,0x36, 0x36, 0x36, 0x36, 0x36,
				0x36, 0x36}; // 16字节的密钥
	 System.out.println(new String(keyBytes));
	 String s="6666666666666666";
	 for(int i=0;i<s.getBytes().length;i++){
		 System.out.println(s.getBytes()[i]);
	 }
	 System.out.println(Bytes2HexString(keyBytes));
	
				
	 	 final byte[] keyBytes = { 54, 54, 54, 54,  54, 54,
		54, 54, 54,54, 54, 54, 54, 54,
		54, 54}; // 16字节的密钥
				
 		
//		byte[] keyBytes="36363636363636363636363636363636".getBytes();
		String szSrc = "1265165465464652";
		String szSrc2 = "4567 8901 2345";

		System.out.println("加密前的字符串:" + HexString2Bytes(szSrc2));
		

		byte[] encoded = encryptMode(keyBytes, HexString2Bytes(szSrc));
		 
		//	System.out.println("加密后的字符串:" + new String(encoded));
			System.out.println(Bytes2HexString(encoded));
		
		 

		byte[] srcBytes = decryptMode(keyBytes, encoded);
		System.out.println("解密后的字符串:" + (Bytes2HexString(srcBytes)));
 		try {
			getnewpwd("123456", "1234567890123456");
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  */
/*		public static String getnewpwd(String pwd,String cardno) throws ValidationException{
			 final byte[] keyBytes = { 0x36, 0x36, 0x36, 0x36,  0x36, 0x36,
						0x36, 0x36, 0x36,0x36, 0x36, 0x36, 0x36, 0x36,
						0x36, 0x36}; // 16字节的密钥
			
			byte[] keyBytes=HexString2Bytes(keybytesHex);
			String pinblock=getPinBlock(pwd, cardno);
		    byte[] newpwdbytes=encryptMode(keyBytes,HexString2Bytes(pinblock));
			System.out.println(Bytes2HexString(newpwdbytes));
			System.out.println(newpwdbytes.length);
			String newpwdallstring=Bytes2HexString(newpwdbytes);
			String newpwd="";
			if(newpwdallstring.length()>=16){
				newpwd=newpwdallstring.substring(0, 16);
			}else{
				throw new ValidationException("ibs.pin_error");
			}
			System.out.println(newpwd);
			String newpwdfinal="";
		 	System.out.println(HexString2Bytes(newpwd).length);
			try {
				  newpwdfinal=new String(HexString2Bytes(newpwd),"gbk");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(newpwdfinal);
		    return newpwd;
		     
			
		}*/

}
