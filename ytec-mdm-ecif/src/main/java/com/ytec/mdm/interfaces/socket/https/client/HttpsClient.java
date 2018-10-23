/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.https.client
 * @�ļ�����HttpsClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:47:33
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.https.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;

class testTrustStrategy implements TrustStrategy{

	@Override
	public boolean isTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		// TODO Auto-generated method stub
		return true;
	}
	
}

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�HttpsClient
 * @��������HTTPS�ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:47:33   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:47:33
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class HttpsClient implements IClient{
	private static Logger log = LoggerFactory.getLogger(HttpsClient.class);
	private static String PROTOCOL="https";
	private static int CONNECTIONTIMEOUT=5000;
	protected String url;
	protected String ip;
	protected int port;
	protected String charset ;
	protected int TIMEOUT = 60000;
	//�ͻ�����Կ 
	protected String keyStoreType; 
	protected String keyStorePath;
	protected String keyPassWord;
	
	//�ͻ���֤�� 
	protected String trustStoreType; 
	protected String trustStorePath;
	protected String trustPassWord;

	public CloseableHttpClient getHttpsClent(){
		CloseableHttpClient httpclient=null;
		FileInputStream instream=null;
		try {
			/**֤��*/
			KeyStore trustStore=null;
			/**��Կ*/
			KeyStore keyStore =null;
			/**���ÿͻ�����֤*/
			SSLContextBuilder contextBuilder=SSLContexts.custom();
			if(keyStoreType!=null&&keyStorePath!=null&&keyPassWord!=null){
				keyStore  = KeyStore.getInstance(keyStoreType);
				instream = new FileInputStream(new File(keyStorePath));
				keyStore.load(instream, keyPassWord.toCharArray());
				instream.close();
				contextBuilder.loadKeyMaterial(keyStore, keyPassWord.toCharArray());
			}
			if(trustStoreType!=null&&trustStorePath!=null&&trustPassWord!=null){
				trustStore  = KeyStore.getInstance(trustStoreType);
				instream = new FileInputStream(new File(trustStorePath));
				trustStore.load(instream, trustPassWord.toCharArray());
				instream.close();
				contextBuilder.loadTrustMaterial(trustStore,new testTrustStrategy());
			}
			
            SSLContext sslcontext = contextBuilder.build();
            
            /**���÷������֤*/
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			return httpclient;
        }catch(Exception e) {
        	log.error("�ͻ���ǩ����֤����",e);
        	return null;
        }finally {
            try {
            	if(instream!=null){
            		instream.close();
            	}
			} catch (IOException e) {
				
			}
        }
	}

	@Override
	public ClientResponse sendMsg(String msg) {
		CloseableHttpClient httpclient=null;
		ClientResponse clientResponse=new ClientResponse();
		log.info("https �ͻ�������ʼ");
		httpclient=getHttpsClent();
		if(httpclient==null){
			log.error("��ȡhttps��֤�ͻ���ʧ��");
			return null;
		}
		try {
			HttpPost httppost = new HttpPost(String.format("%s://%s:%d/%s", PROTOCOL,ip,port,url));
			httppost.setHeader("Content-Type", "text/xml;charset="+charset);
			
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(CONNECTIONTIMEOUT).build();//��������ʹ��䳬ʱʱ��
			httppost.setConfig(requestConfig);

			StringEntity requestEntity = new StringEntity(msg);
			httppost.setEntity(requestEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                log.info("https status {}",response.getStatusLine());
                if (entity != null) {
                	log.info("Response content length: " + entity.getContentLength());
                }
                clientResponse.setResponseMsg(EntityUtils.toString(entity));
            }catch(Exception e) {
            	log.error("��ȡ��Ӧ���Ĵ���",e);
            }finally {
            	if(response!=null){
            		response.close();
            	}
            }
        }catch(SSLException e) {
        	log.error("SSL/TLS����ʧ��:{}",e.getMessage());
        }catch(Exception e) {
        	log.error("https����ʧ��",e);
        }finally {
            try {
            	if(httpclient!=null){
            		httpclient.close();
            	}
			} catch (IOException e) {
				
			}
            log.info("https �ͻ����������");
        }
		return clientResponse;
	}

	@Override
	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		this.ip = (String) arg.get("ip");
		this.port = Integer.parseInt((String) arg.get("port"));
		this.charset = (String) arg.get("charset");
		if(this.charset==null){
			charset = "GB18030";
		}
		this.TIMEOUT = Integer.valueOf((String) arg.get("timeout"));
		this.url = (String) arg.get("url");
		this.keyStorePath = (String) arg.get("keyStorePath");
		this.keyPassWord = (String) arg.get("keyPassWord");
		this.trustStorePath = (String) arg.get("trustStorePath");
		this.trustPassWord = (String) arg.get("trustPassWord");
		this.keyStoreType = (String) arg.get("keyStoreType");
		this.trustStoreType = (String) arg.get("trustStoreType");
		if(StringUtil.isEmpty(keyStoreType)){
			keyStoreType=KeyStore.getDefaultType();
		}
		if(StringUtil.isEmpty(trustStoreType)){
			trustStoreType=KeyStore.getDefaultType();
		}
	}
}
