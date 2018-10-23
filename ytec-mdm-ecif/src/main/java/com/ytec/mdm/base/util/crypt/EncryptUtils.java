package com.ytec.mdm.base.util.crypt;

import java.security.MessageDigest;

/**
 * 
 * <pre>
 * Title: ���ܹ�����
 * Description: 
 * </pre>
 * 
 * @author yunlei yunlei@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
 * </pre>
 */
public class EncryptUtils {
    /** MD5���ܣ����ɻ�ԭ */
    public static final String MD5 = "MD5";
    /** SHA���ܣ����ɻ�ԭ */
    public static final String SHA = "SHA";
    /** DES���ܣ����Ի�ԭ */
    public static final String DES = "DES";

    /**
     * DES������Կ������ʱҲҪ�õ�
     * �ñ��������Ըı䣬�����޷���������н��ܣ�������"encrypt"
     */
    private static final byte[] KEY = {(byte) 'e', (byte) 'n', (byte) 'c', (byte) 'r', (byte) 'y', 
        (byte) 'p', (byte) 't', (byte) '1' };

    /**
     * ���ռ������ͼ����ִ�
     * @param orgStr Ҫ���ܵ��ִ�
     * @param encType ��������
     * @return ���ܺ���ִ������û��ƥ��ļ��ܷ�ʽ���򷵻�ԭ�ִ�
     */
    public static String encrypt(String orgStr, String encType) {
        try {
            if ((encType.compareToIgnoreCase("SHA") == 0)) {
                return orgStr;
                /* �ݲ�ʹ��                   
                   // We need to use unicode here, to be independent of platform's
                   // default encoding.
                   MessageDigest md = MessageDigest.getInstance(encType);
                   byte[] digest = md.digest(orgStr.getBytes("UTF-8"));
                   ByteArrayOutputStream bas = new ByteArrayOutputStream(
                           digest.length + (digest.length / 3) + 1);
                   OutputStream encodedStream = MimeUtility.encode(bas, "base64");
                   encodedStream.write(digest);
                   return bas.toString();*/
            } else if (encType.compareToIgnoreCase("MD5") == 0) {
                MessageDigest alga = MessageDigest.getInstance("MD5");
                alga.update(orgStr.getBytes());
                byte[] digesta = alga.digest();
                String encodePass = byte2str(digesta);
                return encodePass;
            } else if (encType.compareToIgnoreCase("DES") == 0) {
            	return orgStr;
            	  /* �ݲ�ʹ��  
                Provider provider = new au.net.aba.crypto.provider.ABAProvider();
                Security.addProvider(provider);
                DESKeySpec keyspec = new DESKeySpec(KEY);

                //System.out.println(SecretKeyFactory.getInstance(DES).getProvider().getName());
                SecretKey key = SecretKeyFactory.getInstance(DES).generateSecret(keyspec);

                Cipher ecipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                ecipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] enc = ecipher.doFinal(orgStr.getBytes("UTF8"));
                String encryptedStr = new BASE64Encoder().encode(enc);
                return encryptedStr;*/
            }

            return orgStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return orgStr;
        }
    }

    /**
     * ���ս��������ͽ����ִ�
     * @param encryptedStr Ҫ���ܵ��ִ�
     * @param encType ��������
     * @return ���ܺ���ִ����������DES��ʽ���򷵻�ԭ�ִ�
     */
    public static String decrypt(String encryptedStr, String encType) {
    	//�ݲ�ʹ��
//        if ("DES".equals(encType)) {
//            try {
//                DESKeySpec keyspec = new DESKeySpec(KEY);
//                SecretKey key = SecretKeyFactory.getInstance(DES).generateSecret(keyspec);
//                Cipher dcipher = Cipher.getInstance("DES");
//                dcipher.init(Cipher.DECRYPT_MODE, key);
//                byte[] dec = new BASE64Decoder().decodeBuffer(encryptedStr);
//                byte[] utf8 = dcipher.doFinal(dec);
//                return new String(utf8, "UTF8");
//            } catch (Exception ex) {
//                return encryptedStr;
//            }
//        }

        return encryptedStr;
    }

    /**
     * ���Եõ�123456��DES���ܴ�
     *
     * @param args ����
     */
    public static void main(String[] args) {
        String encryptedStr = EncryptUtils.encrypt("123456", "MD5");
//        String orgStr = EncryptUtils.decrypt(encryptedStr, "DES");
        System.out.println(encryptedStr);
        System.out.println(encryptedStr.length());
    }

    /**
     * the following code is copied form other system example
     * @param b byte info
     * @return string info
     */
    private static String byte2str(byte[] b) {
        String output = "";
        try {
            for (int i = 0; i < b.length; i++) {
            	output+=Integer.toHexString((0x000000ff & b[i]) | 0xffffff00).substring(6);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
