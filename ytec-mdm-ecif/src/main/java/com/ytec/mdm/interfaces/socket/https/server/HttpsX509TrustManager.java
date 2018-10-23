/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.https.server
 * @文件名：HttpsX509TrustManager.java
 * @版本信息：1.0.0
 * @日期：2014-4-4-下午4:49:32
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：HttpsX509TrustManager
 * @类描述：证书验证
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-4 下午4:49:32   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-4 下午4:49:32
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class HttpsX509TrustManager implements ClientTrustManager,X509TrustManager {

	private Logger log = LoggerFactory.getLogger(HttpsX509TrustManager.class);
	/**
	 * @属性名称:validatorType
	 * @属性描述:验证类型
	 * @since 1.0.0
	 */
	private String validatorType;
	/**
	 * @属性名称:trustedCerts
	 * @属性描述:证书集合
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
			log.warn("客户端证书为空");
			return;
		}
		if ((authType == null) || (authType.length() == 0)) {
			log.warn("客户端证书类型为空");
			return;
		}
		int nSize = chain.length;
		for (int i = 0; i < nSize; i++) {
			/**验证有效期***/
			try {
				chain[i].checkValidity();
			}catch (GeneralSecurityException generalsecurityexception) {
				log.warn("证书已过期失效,证书有效期的终止日期为{}",chain[i].getNotAfter());
				throw new CertificateException("证书已过期失效");
			}
//			String subjectPrincipal = chain[i].getSubjectX500Principal().toString();
//			log.info(subjectPrincipal);
			/***获取签名及签名算法**/
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
