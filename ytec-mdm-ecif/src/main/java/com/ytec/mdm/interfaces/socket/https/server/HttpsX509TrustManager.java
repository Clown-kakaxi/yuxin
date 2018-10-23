/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.https.server
 * @�ļ�����HttpsX509TrustManager.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-4-����4:49:32
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.https.server;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.validator.KeyStores;
import com.ytec.mdm.interfaces.socket.ssl.ClientTrustManager;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�HttpsX509TrustManager
 * @��������֤����֤
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-4 ����4:49:32   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-4 ����4:49:32
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class HttpsX509TrustManager implements ClientTrustManager,X509TrustManager {

	private Logger log = LoggerFactory.getLogger(HttpsX509TrustManager.class);
	/**
	 * @��������:validatorType
	 * @��������:��֤����
	 * @since 1.0.0
	 */
	private String validatorType;
	/**
	 * @��������:trustedCerts
	 * @��������:֤�鼯��
	 * @since 1.0.0
	 */
	private Collection trustedCerts;

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		if ((chain == null) || (chain.length == 0)) {
			log.warn("�ͻ���֤��Ϊ��");
			return;
		}
		if ((authType == null) || (authType.length() == 0)) {
			log.warn("�ͻ���֤������Ϊ��");
			return;
		}
		int nSize = chain.length;
		for (int i = 0; i < nSize; i++) {
			/**��֤��Ч��***/
			try {
				chain[i].checkValidity();
			}catch (GeneralSecurityException generalsecurityexception) {
				log.warn("֤���ѹ���ʧЧ,֤����Ч�ڵ���ֹ����Ϊ{}",chain[i].getNotAfter());
				throw new CertificateException("֤���ѹ���ʧЧ");
			}
//			String subjectPrincipal = chain[i].getSubjectX500Principal().toString();
//			log.info(subjectPrincipal);
			/***��ȡǩ����ǩ���㷨**/
			//log.info(CHexConver.byte2HexStr(chain[i].getSignature()));
			//chain[i].getSignature();
			//chain[i].getSigAlgName();
		}
		
		//showTrustedCerts(chain);

	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		 X509Certificate[] arrayOfX509Certificate = new X509Certificate[this.trustedCerts.size()];
		 this.trustedCerts.toArray(arrayOfX509Certificate);
		 return arrayOfX509Certificate;
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.ssl.ClientTrustManager#init(java.security.KeyStore)
	 */
	@Override
	public void init(String paramString, KeyStore paramKeyStore) throws Exception {
		// TODO Auto-generated method stub
		this.validatorType = paramString;
	    if (paramKeyStore == null)
	      this.trustedCerts = Collections.EMPTY_SET;
	    else {
	      this.trustedCerts = KeyStores.getTrustedCerts(paramKeyStore);
	    }
	}
	
	
	private void showTrustedCerts(X509Certificate[] chain) {
		for (X509Certificate localX509Certificate:chain) {
			System.out.println("adding as trusted cert:");
			System.out.println("  Subject: "
					+ localX509Certificate.getSubjectX500Principal());

			System.out.println("  Issuer:  "
					+ localX509Certificate.getIssuerX500Principal());

			System.out.println("  Algorithm: "
					+ localX509Certificate.getPublicKey().getAlgorithm()
					+ "; Serial number: 0x"
					+ localX509Certificate.getSerialNumber().toString(16));

			System.out.println("  Valid from "
					+ localX509Certificate.getNotBefore() + " until "
					+ localX509Certificate.getNotAfter());

			System.out.println();
		}
	}
}
