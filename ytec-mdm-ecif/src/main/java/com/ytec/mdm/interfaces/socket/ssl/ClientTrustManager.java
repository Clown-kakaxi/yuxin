/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ssl
 * @�ļ�����ClientTrustManager.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-8-����11:01:49
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;
import java.security.KeyStore;

import javax.net.ssl.TrustManager;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ClientTrustManager
 * @���������ͻ���֤����֤�ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-8 ����11:01:49   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-8 ����11:01:49
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface ClientTrustManager extends TrustManager {
	public void init(String paramString, KeyStore paramKeyStore) throws Exception;
}
