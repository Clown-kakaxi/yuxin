package com.yuchengtech.emp.core.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * MD5
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class MD5 {

	/**
	 * 加密
	 * @param source 明文
	 * @return
	 */
	public String encrypt(String source) {
		return encode(source);
	}
	
	private String encode(String source) {
		String saltedPass = mergePasswordAndSalt(source, salt, false);
		MessageDigest messageDigest = getMessageDigest();
		byte[] digest;
		try {
			digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}
		return new String(Hex.encodeHex(digest));
	}

	private final MessageDigest getMessageDigest() {
		String algorithm = "MD5";
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
		}
	}

	private String mergePasswordAndSalt(String password, Object salt, boolean strict) {
		if (password == null) {
			password = "";
		}
		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1)
					|| (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
			}
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	/** salt */
	private String salt = "EMP_SECURITY";

	/**
	 * 设置混淆key
	 * @param salt salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
