/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.http.server
 * @�ļ�����HttpExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:48:30
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.http.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioExecutor;
import com.ytec.mdm.interfaces.socket.NioProcessor;
import com.ytec.mdm.interfaces.socket.http.tools.HeaderMap;
import com.ytec.mdm.interfaces.socket.http.tools.HttpRequest;
import com.ytec.mdm.interfaces.socket.http.tools.HttpStatus;
import com.ytec.mdm.interfaces.socket.http.tools.ProtocolException;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;
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
 * @�����ƣ�HttpExecutor
 * @��������HTTP������
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
public abstract class HttpExecutor extends TxAdapterExcutor implements NioExecutor{
	private static Logger log = LoggerFactory.getLogger(HttpExecutor.class);

	protected HttpStatus respcode;                  								//http״̬
	protected IoSession session;
	protected SocketChannel client;                 								//�ͻ���ͨ��
	protected NioProcessor selector;               									//ѡ����
	protected ByteBuffer receivebuffer;             								//���ջ�����              
	protected static int writeTimeout=ServerConfiger.getIntArg("writeTimeout"); 		//��Ӧ��ʱʱ��
	protected static int httpServerBlock=ServerConfiger.getIntArg("bufferBlock");     //���Ļ�������С
	protected static String SERVERNAME=ServerConfiger.getStringArg("httpServerName");	//http ����������
	protected static int maxBody=ServerConfiger.getIntArg("httpServerMaxBody");		//��������ֽ���
	protected static int maxLine=ServerConfiger.getIntArg("httpServerMaxLine");       //������ÿ������ֽ���
	protected static boolean isCloseAfterSvc=ServerConfiger.getBooleanArg("isCloseAfterSvc");        //�Ƿ��������͹ر�
	protected final RequestDecoder decoder= new RequestDecoder(maxBody, maxLine);   //http������    
	protected int sleepTime=50;             											//���粻�ȶ�ʱ���������ȴ�ʱ��
	protected int readMaxNum=100;            										//���粻�ȶ�ʱ��һ��û�н����꣬��ȡ���ٴ�
	
	protected HeaderMap headers;                                                    //�ͻ��Զ��屨��ͷ
	protected HttpRequest request=null;

	/**
	 * ���캯��
	 * 
	 */
	public HttpExecutor() {
	}
	/**
	 * ��ʼ������
	 * 
	 * @param client �����׽���
	 * @param selector ������selector
	 * @param pool �첽�����̳߳ش�С
	 */
	public void init(IoSession session, NioProcessor selector) {
		this.session=session;
		this.client = session.getClient();
		this.selector = selector;
		this.receivebuffer = ByteBuffer.allocate(httpServerBlock);
		//this.pool = pool;
		interFaceType="HTTP";
	}

	/**
	 * �����߳�
	 */
	public void execute() {
		try {
			this.receivebuffer.clear(); // clear for read
			int read = client.read(receivebuffer);
			if (read <0) {
				selector.removeOpenSocket(client);
				log.info("!> @" + this.sid + " TxExecutor.run() exit.");
				return;
			} else if (read > 0) {
				receivebuffer.flip(); // flip for read
				request=null;
				int rcount=0;
				do {
					try{
						/***����http����****/
						request = decoder.decode(receivebuffer);
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
							selector.removeOpenSocket(client);
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
						if(rcount>readMaxNum){
							log.error("http���Ľ�����ʱ");
							this.respcode = HttpStatus.BAD_REQUEST;
							throw new RequestIOException("http���Ľ�����ʱ");
						}
						if(httpServerBlock!=read){
							log.warn("����״̬���ã���Ҫ��ȡ��Ρ�����");
							try{
								Thread.sleep(sleepTime);
							}catch(Exception e){
								log.warn(e.getMessage());
							}
						}
						rcount++;
						log.warn("��ȡ����"+read+"Byte,��Ҫ��ȡ��Ρ�����");
						this.receivebuffer.clear(); // �ٶ�
						read = client.read(receivebuffer);
						receivebuffer.flip(); // flip for read
						if(read<0){
							selector.removeOpenSocket(client);
							log.warn("!> @" + this.sid + " TxExecutor.run() exit.");
							return;
						}else if(read==0){
							log.warn("��ȡ0Byte����");
						}
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
	protected String packing(String body, HttpStatus http_response) {
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
		remsg.append("Content-Type: application/xml; charset="+charSet+"\r\n");
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
	protected long send(String msg) throws IOException {
		long len = 0;
		ByteBuffer sendbuffer = ByteBuffer.wrap(msg.getBytes(charSet));
		len=sendChannel(sendbuffer);
		log.info("<<<[SEND] Client[" + client.socket().getRemoteSocketAddress()+ "][" + len + "]");
		log.info(SensitHelper.getInstance().doInforFilter(msg,null));
		return len;
	}
	
	/****
	 * ������Ϣ      ��������״̬���õ����
	 * @param bb
	 * @param writeTimeout
	 * @return
	 * @throws IOException
	 */
	protected long sendChannel(ByteBuffer buf) throws IOException
	{
	    SelectionKey key = null;
	    Selector writeSelector = null;
	    int attempts = 0;
	    int bytesProduced = 0;
	    try {
	        while (buf.hasRemaining()) {
	            int len = this.client.write(buf);
	            attempts++;
	            if (len < 0){
	                throw new EOFException();
	            }
	            bytesProduced += len;
	            if (len == 0) {
	            	log.warn("����״̬���ã���Ҫ���Ͷ�Ρ�����");
	                if (writeSelector == null){
	                    writeSelector = Selector.open();
	                    if (writeSelector == null){
	                        continue;
	                    }
	                }
	                key = this.client.register(writeSelector, key.OP_WRITE);
	                if (writeSelector.select(this.writeTimeout) == 0) {
	                    if (attempts > 2)
	                        throw new IOException("Client disconnected");
	                } else {
	                    attempts--;
	                }
	            } else {
	                attempts = 0;
	            }
	        }
	    } finally {
	        if (key != null) {
	            key.cancel();
	            key = null;
	        }
	        if (writeSelector != null) {
	            writeSelector.close();
	        }
	    }
	    return bytesProduced;
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
