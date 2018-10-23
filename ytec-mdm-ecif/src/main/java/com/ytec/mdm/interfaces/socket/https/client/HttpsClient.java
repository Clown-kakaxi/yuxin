/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.https.client
 * @文件名：HttpsClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:47:33
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：HttpsClient
 * @类描述：HTTPS客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:47:33   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:47:33
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
	//客户端密钥 
	protected String keyStoreType; 
	protected String keyStorePath;
	protected String keyPassWord;
	
	//客户端证书 
	protected String trustStoreType; 
	protected String trustStorePath;
	protected String trustPassWord;

	public CloseableHttpClient getHttpsClent(){
		CloseableHttpClient httpclient=null;
		FileInputStream instream=null;
		try {
			/**证书*/
			KeyStore trustStore=null;
			/**密钥*/
			KeyStore keyStore =null;
			/**设置客户端验证*/
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
            
            /**设置服务端验证*/
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			return httpclient;
        }catch(Exception e) {
        	log.error("客户端签名验证错误",e);
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
		log.info("https 客户端请求开始");
		httpclient=getHttpsClent();
		if(httpclient==null){
			log.error("获取https验证客户端失败");
			return null;
		}
		try {
			HttpPost httppost = new HttpPost(String.format("%s://%s:%d/%s", PROTOCOL,ip,port,url));
			httppost.setHeader("Content-Type", "text/xml;charset="+charset);
			
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(CONNECTIONTIMEOUT).build();//设置请求和传输超时时间
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
            	log.error("获取响应报文错误",e);
            }finally {
            	if(response!=null){
            		response.close();
            	}
            }
        }catch(SSLException e) {
        	log.error("SSL/TLS连接失败:{}",e.getMessage());
        }catch(Exception e) {
        	log.error("https请求失败",e);
        }finally {
            try {
            	if(httpclient!=null){
            		httpclient.close();
            	}
			} catch (IOException e) {
				
			}
            log.info("https 客户端请求结束");
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
