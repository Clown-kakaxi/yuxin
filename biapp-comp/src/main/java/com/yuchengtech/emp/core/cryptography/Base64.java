package com.yuchengtech.emp.core.cryptography;

/**
 * Base 64
 * 
 * @version 1.0
 * @since 1.0.1
 */
public class Base64 {
	
	private final static char[] BASE64Alphabet = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'+', '/'
	};

	private final static char PAD = '=';

	
	public String encrypt(byte[] bytes) {
		int c = bytes.length;
		int full = c / 3;
		int r = c - full * 3;
		int i = 0, j = 0, k;
		char[] chars = new char[full * 4 + (r > 0 ? 4 : 0)];
		char[] A = BASE64Alphabet;

		for (k = 0; k < full; k++, i += 3) {
			chars[j++] = A[(bytes[i] >> 2) & 0x3f];
			chars[j++] = A[((bytes[i] & 3) << 4) | ((bytes[i + 1] >> 4) & 0x0f)];
			chars[j++] = A[((bytes[i + 1] & 0x0f) << 2) | ((bytes[i + 2] >> 6) & 3)];
			chars[j++] = A[bytes[i + 2] & 0x3f];
		}
		if (r == 1) {
			chars[j++] = A[(bytes[i] >> 2) & 0x3f];
			chars[j++] = A[(bytes[i] & 3) << 4];
			chars[j++] = PAD;
			chars[j++] = PAD;
		}
		else if (r == 2) {
			chars[j++] = A[(bytes[i] >> 2) & 0x3f];
			chars[j++] = A[((bytes[i] & 3) << 4) | ((bytes[i + 1] >> 4) & 0x0f)];
			chars[j++] = A[(bytes[i + 1] & 0x0f) << 2];
			chars[j++] = PAD;
		}

		return new String(chars);
	}

	public byte[] decrypt(String text) {
		
		int c = text.length();
		if (c == 0) {
			return new byte[0];
		}
		if (c % 4 != 0) {
			throw new IllegalArgumentException("wrong length: " + text);
		}
		int full = c / 4;
		int r = 0;

		if (text.charAt(c - 1) == PAD) {
			r = text.charAt(c - 2) == PAD ? 1 : 2;
			full--;
		}

		byte[] bytes = new byte[full * 3 + r];
		char[] chars = text.toCharArray();
		int a, b, i = 0, j = 0;
		int[] k = new int[4];

		for (a = 0; a < full; a++) {
			for (b = 0; b < 4; b++) {
				k[b] = getInt(chars[i++]);
			}
			bytes[j++] = (byte) ((k[0] << 2) | (k[1] >> 4));
			bytes[j++] = (byte) (((k[1] & 0x0f) << 4) | (k[2] >> 2));
			bytes[j++] = (byte) (((k[2] & 3) << 6) | k[3]);
		}

		if (r == 1) {
			k[0] = getInt(chars[i++]);
			k[1] = getInt(chars[i++]);
			bytes[j++] = (byte) ((k[0] << 2) | (k[1] >> 4));
		}
		else if (r == 2) {
			k[0] = getInt(chars[i++]);
			k[1] = getInt(chars[i++]);
			k[2] = getInt(chars[i++]);
			bytes[j++] = (byte) ((k[0] << 2) | (k[1] >> 4));
			bytes[j++] = (byte) (((k[1] & 0x0f) << 4) | (k[2] >> 2));
		}
		
		return bytes;
	}

	private int getInt(char ch) {
		int k;

		if (ch >= 'A' && ch <= 'Z') {
			k = ch - 'A';
		}
		else if (ch >= 'a' && ch <= 'z') {
			k = ch - 'a' + 26;
		}
		else if (ch >= '0' && ch <= '9') {
			k = ch - '0' + 52;
		}
		else if (ch == '+') {
			k = 62;
		}
		else if (ch == '/') {
			k = 63;
		}
		else {
			throw new IllegalArgumentException("char " + ch);
		}
		return k;
	}
}
