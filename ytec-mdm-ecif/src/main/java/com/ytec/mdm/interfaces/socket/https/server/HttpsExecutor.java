/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.http.server
 * @�ļ�����HttpsExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:48:30
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.https.server;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioExecutor;
import com.ytec.mdm.interfaces.socket.NioProcessor;
import com.ytec.mdm.interfaces.socket.SSLIoSession;
import com.ytec.mdm.interfaces.socket.http.server.RequestDecoder;
import com.ytec.mdm.interfaces.socket.http.tools.HeaderMap;
import com.ytec.mdm.interfaces.socket.http.tools.HttpRequest;
import com.ytec.mdm.interfaces.socket.http.tools.HttpStatus;
import com.ytec.mdm.interfaces.socket.http.tools.ProtocolException;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;
import com.ytec.mdm.interfaces.socket.ssl.SslHandler;
import com.ytec.mdm.server.common.ServerConfiger;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.StringUtil;

/**
 * ECIF http �ӿڽ���
 * @author wangzy1@yuchengtech.com
 * 
 */
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�HttpsExecutor
 * @��������HTTPS������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:48:30   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:48:30
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class HttpsExecutor extends TxAdapterExcutor implements NioExecutor{
	private static Logger log = LoggerFactory.getLogger(HttpsExecutor.class);

	protected HttpStatus respcode;                  								//http״̬
	protected SocketChannel client;                 									//�ͻ���ͨ��
	protected NioProcessor selector;               								//ѡ����
	protected ByteBuffer receivebuffer;             									//���ջ�����              
	protected static String SERVERNAME=ServerConfiger.getStringArg("httpServerName");	//http ����������
	protected static int maxBody=ServerConfiger.getIntArg("httpServerMaxBody");		//��������ֽ���
	protected static int maxLine=ServerConfiger.getIntArg("httpServerMaxLine");        //������ÿ������ֽ���
	protected static boolean isCloseAfterSvc=ServerConfiger.getBooleanArg("isCloseAfterSvc");        //�Ƿ��������͹ر�
	protected final RequestDecoder decoder= new RequestDecoder(maxBody, maxLine);     //http������    
	
	protected HeaderMap headers;                                                    //�ͻ��Զ��屨��ͷ
	protected HttpRequest request=null;
	protected SSLIoSession session;
	protected SslHandler sslHandler;

	/**
	 * ���캯��
	 * 
	 */
	public HttpsExecutor() {
	}
	/**
	 * ��ʼ������
	 * 
	 * @param client �����׽���
	 * @param selector ������selector
	 * @param pool �첽�����̳߳ش�С
	 */
	public void init(IoSession session, NioProcessor selector) {
		this.client = session.getClient();
		this.session=(SSLIoSession)session;
		this.selector = selector;
		this.sslHandler=this.session.getSslHandler();
		//this.pool = pool;
		interFaceType="HTTPS";
	}

	/**
	 * �����߳�
	 */
	public void execute() {
		try {
			/**************************/
//			try{
//				sslHandler.doHandShake(this.client);
//			}catch(Exception e){
//				log.error("doHandShake error",e);
//				selector.removeOpenSocket(client);
//				return;
//			}
			int read = sslHandler.read(this.client);
        	/***************************/
			if (read <0) {
				selector.removeOpenSocket(client);
				log.info("!> @" + this.sid + " TxExecutor.run() exit.");
				return;
			} else if (read > 0) {
				/*************����*************/
				receivebuffer=sslHandler.getAppBuffer();
	        	/***************************/
				
				receivebuffer.flip(); // flip for read
				request=null;
				do {
					try{
						/***����http����****/
						request = decoder.decode(receivebuffer);
						//System.out.println(decoder.decode(receivebuffer));
					}catch(RequestTooLargeException e){//����̫��
						log.error("http���Ľ�����",e);
						this.respcode = HttpStatus.REQUEST_ENTITY_TOO_LARGE;
						throw new RequestIOException(e.getMessage());
					}catch(ProtocolException e){
						this.respcode = HttpStatus.PRECONDITION_FAILED;
						log.info("http PRECONDITION_FAILED");
						throw new RequestIOException(e.getMessage());
					}catch(Exception e){//���Ĵ���
						log.error("http���Ľ�����",e);
						this.respcode = HttpStatus.BAD_REQUEST;
						throw new RequestIOException(e.getMessage());
					}
					if (request != null) {
						decoder.reset();
						/**û����������***/
						if(request.contentLength==0){
							log.info("����Ϊ��");
							send(this.packing(createDefauteMsg(ErrorCode.INF_PUBLISH_SUCCESS.getCode(),"�����������У����ύECIF��������"),  HttpStatus.BAD_REQUEST));
							selector.registerChannel(session);
							return;
						}
						this.recvmsg=request.getBodyString();
						log.info(">>>[RECV-XML] Client["+ client.socket().getRemoteSocketAddress() + "]["+ request.contentLength + "]");
						log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
						beforeExecutor();
						/****����XML����****/
						resolvingXml();
						
						if (doc != null) {
							/** ���ý�����Ϣ���� **/
							if(!StringUtil.isEmpty(request.getCharset())){
								charSet=request.getCharset();
							}
							data.setCharsetName(charSet);
							getEcifData();
						}else{
							this.respcode = HttpStatus.UNPROCESSABLE_ENTITY;
							log.info("����������http����");
							throw new RequestIOException("����������http����"+respcode.getReasonPhrase());
						}
						/** ���ñ�׼���� */
						atp.process(data);
						resXml=createOutputDocument();
						afterExecutor();
						send(this.packing(resXml, HttpStatus.OK));
						if(!isCloseAfterSvc&&request.isKeepAlive){
							if(!receivebuffer.hasRemaining()){
								selector.registerChannel(session);
							}
						}else{
							selector.removeOpenSocket(client);
							break;
						}
					}else{
						log.warn("����״̬���ã����ձ��Ĳ���ȫ");
						this.respcode = HttpStatus.BAD_REQUEST;
						throw new RequestIOException("����״̬���ã����ձ��Ĳ���ȫ");
					}
				} while (receivebuffer.hasRemaining()); // consume all
				if(request==null){
					log.error("http���Ĵ���");
					this.respcode = HttpStatus.BAD_REQUEST;
					throw new RequestIOException("http���Ĵ���");
				}
			}else{
				/***ע����¼�****/
				selector.registerChannel(session);
			}
			/********/
		} catch (RequestIOException eie) {
			/***����Ƿ�***/
			log.error("����Ƿ�:{}",eie.getMessage());
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(),eie.getMessage());
				afterExecutor();
				if(respcode==null){
					respcode=HttpStatus.BAD_REQUEST;
				}
				send(this.packing(resXml, this.respcode));
			} catch (Exception e1) {
				log.info("!> Thread[" + this.sid + "] Exception[1] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		} catch (IOException ie) {
			/***IO����***/
			log.error("IO����",ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/**����������**/
			log.error("!> [FATAL] Exception: " , e);
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),e.getMessage());
				afterExecutor();
				send(this.packing(resXml, HttpStatus.INTERNAL_SERVER_ERROR));
			} catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		}catch(Throwable ex){
			log.error("!> [FATAL]���������ش���",ex);
			try {
				resXml=createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),ex.getMessage());
				afterExecutor();
				send(this.packing(resXml, HttpStatus.INTERNAL_SERVER_ERROR));
			}catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
			}catch(Throwable ex1){
				log.error("!> Thread[" + this.sid + "] Throwable[3] loop.");
			}finally{
				selector.removeOpenSocket(client);
			}
			return;
		}
	}
	
	/**
	 * @��������:rejectedExecution
	 * @��������:�������ƣ��ܾ�
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void rejectedExecution(){
		/**����ܾ�**/
		log.warn("!> ����ܾ�");
		try {
			resXml=createDefauteMsg(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"����æ,��ȴ�һ�����ύ");
			afterExecutor();
			send(this.packing(resXml, HttpStatus.OK));
		} catch (Exception e3) {
			log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
		}finally{
			selector.removeOpenSocket(client);
		}
		return;
	}

	/**
	 * ��װHTTPЭ��Ӧ����Ϣ
	 * 
	 * @param body http�岿�֣���Ҫ��SOAP����
	 * @param http_response http��Ӧ��
	 * @return ��װ�õ�Ӧ����Ϣ
	 */
	private String packing(String body, HttpStatus http_response) {
		
		StringBuffer remsg = new StringBuffer();
		remsg.append("HTTP/1.1 " + http_response.getCode() + " "
				+ http_response.getReasonPhrase() + "\r\n");
		remsg.append("Date: " +  new Date() + "\r\n");
		remsg.append("Server: "+SERVERNAME+"\r\n");
		if (body == null){
			remsg.append("Content-Length: 0\r\n");
		}else{
			try {
				remsg.append("Content-Length: " + body.getBytes(charSet).length + "\r\n");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				remsg.append("Content-Length: " + body.getBytes().length + "\r\n");
			}
		}
		remsg.append("Content-Type: text/plain; charset="+charSet+"\r\n");//text/plain  //application/xml
		remsg.append("Connection: close\r\n");
		if(headers!=null){
			headers.encodeHeaders(remsg);
		}
		remsg.append("\r\n");
		if (body != null) {
			remsg.append(body);
		}
		return remsg.toString();
	}

	/**
	 * ������Ϣ
	 * 
	 * @param msg ��Ϣ����
	 * @return ���͵����ݳ���
	 * @throws IOException
	 */
	private long send(String msg) throws IOException {
		long len =sslHandler.write(msg.getBytes(charSet),this.client);
		log.info("<<<[SEND] Client[" + client.socket().getRemoteSocketAddress()+ "][" + len + "]");
		log.info(SensitHelper.getInstance().doInforFilter(msg,null));
		return len;
	}
	

	/**
	 * SOAP������װ
	 * 
	 * @param data �������ݶ���
	 */
	protected abstract String createOutputDocument() throws Exception;
	
	/***
	 * SOAP����Ĭ����װ
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg) throws IOException;
	
	/***
	 * �������ģ���ȡ����
	 * @param doc
	 * @param srcXml
	 * @throws Exception
	 */
	protected abstract void getEcifData() throws Exception;
	
	/***
	 * ������Ԥ����
	 * 
	 */
	protected abstract void beforeExecutor();
	
	/***
	 * ��Ӧ���ķ���ǰԤ����
	 * 
	 */
	protected abstract void afterExecutor();
}
