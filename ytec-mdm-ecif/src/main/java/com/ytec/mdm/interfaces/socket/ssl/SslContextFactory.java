/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ssl
 * @�ļ�����SslContextFactory.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-2-����4:39:58
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SslContextFactory
 * @��������SSL��ʼ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-2 ����4:39:58
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-2 ����4:39:58
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SslContextFactory {
	private String protocol = "TLS";
	private String keyStoreType = "JKS";
	private String trustStoreType = "JKS";
	private String keyManagerFactoryAlgorithm = "SunX509";
	private String trustManagerFactoryAlgorithm = "SunX509";
	private String trustPassWord;
	private String trustStorePath;
	private String keyPassWord;
	private String keyStorePath;
	private SSLContext sslContext = null;
	private TrustManager[] trustManagers;
	private String clientTrustManager;
	
	
	public SslContextFactory() {
	
	}

	public SslContextFactory(String trustPassWord, String trustStorePath,
			String keyPassWord, String keyStorePath) {
		this.trustPassWord = trustPassWord;
		this.trustStorePath = trustStorePath;
		this.keyPassWord = keyPassWord;
		this.keyStorePath = keyStorePath;
	}

	public SSLContext newInstance() throws Exception {
		/**��Կ����**/
		KeyStore ks = KeyStore.getInstance(keyStoreType);
		ks.load(new FileInputStream(keyStorePath), keyPassWord.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory
				.getInstance(keyManagerFactoryAlgorithm);
		kmf.init(ks, keyPassWord.toCharArray());
		/**֤�����**/
		KeyStore ts = KeyStore.getInstance(trustStoreType);
		ts.load(new FileInputStream(trustStorePath),
				trustPassWord.toCharArray());
		if(clientTrustManager!=null){
			Class clazz = Class.forName(clientTrustManager);
			ClientTrustManager clientTrustManager= (ClientTrustManager)clazz.newInstance();
			clientTrustManager.init(trustStoreType,ts);
			trustManagers = new TrustManager[]{clientTrustManager};
		}else{
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm);
			tmf.init(ts);
			trustManagers=tmf.getTrustManagers();
		}

		sslContext = SSLContext.getInstance(protocol);
		sslContext.init(kmf.getKeyManagers(), trustManagers, null);
		return sslContext;
	}
	

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getKeyStoreType() {
		return keyStoreType;
	}

	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public String getTrustStoreType() {
		return trustStoreType;
	}

	public void setTrustStoreType(String trustStoreType) {
		this.trustStoreType = trustStoreType;
	}

	public String getKeyManagerFactoryAlgorithm() {
		return keyManagerFactoryAlgorithm;
	}

	public void setKeyManagerFactoryAlgorithm(String keyManagerFactoryAlgorithm) {
		this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm;
	}

	public String getTrustManagerFactoryAlgorithm() {
		return trustManagerFactoryAlgorithm;
	}

	public void setTrustManagerFactoryAlgorithm(
			String trustManagerFactoryAlgorithm) {
		this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm;
	}

	public String getTrustPassWord() {
		return trustPassWord;
	}

	public void setTrustPassWord(String trustPassWord) {
		this.trustPassWord = trustPassWord;
	}

	public String getTrustStorePath() {
		return trustStorePath;
	}

	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}

	public String getKeyPassWord() {
		return keyPassWord;
	}

	public void setKeyPassWord(String keyPassWord) {
		this.keyPassWord = keyPassWord;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public SSLContext getSslContext() {
		return sslContext;
	}

	public TrustManager[] getTrustManagers() {
		return trustManagers;
	}

	public void setClientTrustManager(String clientTrustManager) {
		this.clientTrustManager = clientTrustManager;
	}
	
	
}
