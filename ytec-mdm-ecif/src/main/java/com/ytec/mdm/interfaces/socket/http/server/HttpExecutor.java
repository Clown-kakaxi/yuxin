/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.http.server
 * @文件名：HttpExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:48:30
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * ECIF http 接口解析
 * @author wangzy1@yuchengtech.com
 * 
 */
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：HttpExecutor
 * @类描述：HTTP工作类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:48:30   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:48:30
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class HttpExecutor extends TxAdapterExcutor implements NioExecutor{
	private static Logger log = LoggerFactory.getLogger(HttpExecutor.class);

	protected HttpStatus respcode;                  								//http状态
	protected IoSession session;
	protected SocketChannel client;                 								//客户端通道
	protected NioProcessor selector;               									//选择器
	protected ByteBuffer receivebuffer;             								//接收缓冲区              
	protected static int writeTimeout=ServerConfiger.getIntArg("writeTimeout"); 		//响应超时时间
	protected static int httpServerBlock=ServerConfiger.getIntArg("bufferBlock");     //报文缓冲区大小
	protected static String SERVERNAME=ServerConfiger.getStringArg("httpServerName");	//http 服务器名称
	protected static int maxBody=ServerConfiger.getIntArg("httpServerMaxBody");		//报文最大字节数
	protected static int maxLine=ServerConfiger.getIntArg("httpServerMaxLine");       //报文中每行最大字节数
	protected static boolean isCloseAfterSvc=ServerConfiger.getBooleanArg("isCloseAfterSvc");        //是否服务结束就关闭
	protected final RequestDecoder decoder= new RequestDecoder(maxBody, maxLine);   //http解析器    
	protected int sleepTime=50;             											//网络不稳定时，解析器等待时间
	protected int readMaxNum=100;            										//网络不稳定时，一次没有解析完，读取多少次
	
	protected HeaderMap headers;                                                    //客户自定义报文头
	protected HttpRequest request=null;

	/**
	 * 构造函数
	 * 
	 */
	public HttpExecutor() {
	}
	/**
	 * 初始化方法
	 * 
	 * @param client 请求方套接字
	 * @param selector 服务器selector
	 * @param pool 异步处理线程池大小
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
	 * 处理线程
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
						/***解析http报文****/
						request = decoder.decode(receivebuffer);
					}catch(RequestTooLargeException e){//报文太长
						log.error("http报文解析：",e);
						this.respcode = HttpStatus.REQUEST_ENTITY_TOO_LARGE;
						throw new RequestIOException(e.getMessage());
					}catch(ProtocolException e){
						this.respcode = HttpStatus.PRECONDITION_FAILED;
						log.info("http PRECONDITION_FAILED");
						throw new RequestIOException(e.getMessage());
					}catch(Exception e){//报文错误
						log.error("http报文解析：",e);
						this.respcode = HttpStatus.BAD_REQUEST;
						throw new RequestIOException(e.getMessage());
					}
					if (request != null) {
						decoder.reset();
						/**没有请求内容***/
						if(request.contentLength==0){
							log.info("请求为空");
							send(this.packing(createDefauteMsg(ErrorCode.INF_PUBLISH_SUCCESS.getCode(),"服务正在运行，请提交ECIF请求数据"),  HttpStatus.BAD_REQUEST));
							selector.removeOpenSocket(client);
							return;
						}
						this.recvmsg=request.getBodyString();
						log.info(">>>[RECV-XML] Client["+ client.socket().getRemoteSocketAddress() + "]["+ request.contentLength + "]");
						log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
						beforeExecutor();
						/****解析XML报文****/
						resolvingXml();
						
						if (doc != null) {
							/** 设置交易信息参数 **/
							if(!StringUtil.isEmpty(request.getCharset())){
								charSet=request.getCharset();
							}
							data.setCharsetName(charSet);
							getEcifData();
						}else{
							this.respcode = HttpStatus.UNPROCESSABLE_ENTITY;
							log.info("解析到错误http请求");
							throw new RequestIOException("解析到错误http请求"+respcode.getReasonPhrase());
						}
						/** 调用标准服务 */
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
							log.error("http报文解析超时");
							this.respcode = HttpStatus.BAD_REQUEST;
							throw new RequestIOException("http报文解析超时");
						}
						if(httpServerBlock!=read){
							log.warn("网络状态不好，需要读取多次。。。");
							try{
								Thread.sleep(sleepTime);
							}catch(Exception e){
								log.warn(e.getMessage());
							}
						}
						rcount++;
						log.warn("读取数据"+read+"Byte,需要读取多次。。。");
						this.receivebuffer.clear(); // 再读
						read = client.read(receivebuffer);
						receivebuffer.flip(); // flip for read
						if(read<0){
							selector.removeOpenSocket(client);
							log.warn("!> @" + this.sid + " TxExecutor.run() exit.");
							return;
						}else if(read==0){
							log.warn("读取0Byte数据");
						}
					}
				} while (receivebuffer.hasRemaining()); // consume all
				if(request==null){
					log.error("http报文错误");
					this.respcode = HttpStatus.BAD_REQUEST;
					throw new RequestIOException("http报文错误");
				}
			}else{
				/***注册读事件****/
				selector.registerChannel(session);
			}
			/********/
		} catch (RequestIOException eie) {
			/***请求非法***/
			log.error("请求非法:{}",eie.getMessage());
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
			/***IO错误***/
			log.error("IO错误",ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/**服务器错误**/
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
			log.error("!> [FATAL]服务器严重错误",ex);
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
	 * @函数名称:rejectedExecution
	 * @函数描述:流量控制，拒绝
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void rejectedExecution(){
		/**服务拒绝**/
		log.warn("!> 服务拒绝");
		try {
			resXml=createDefauteMsg(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(),"服务忙,请等待一会再提交");
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
	 * 组装HTTP协议应答消息
	 * 
	 * @param body http体部分，主要是SOAP报文
	 * @param http_response http响应码
	 * @return 组装好的应答消息
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
	 * 发送消息
	 * 
	 * @param msg 消息内容
	 * @return 发送的数据长度
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
	 * 发送消息      用于网络状态不好的情况
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
	            	log.warn("网络状态不好，需要发送多次。。。");
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
	 * SOAP报文组装
	 * 
	 * @param data 返回数据对象
	 */
	protected abstract String createOutputDocument() throws Exception;
	
	/***
	 * SOAP报文默认组装
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg) throws IOException;
	
	/***
	 * 解析报文，提取参数
	 * @param doc
	 * @param srcXml
	 * @throws Exception
	 */
	protected abstract void getEcifData() throws Exception;
	
	/***
	 * 请求报文预处理
	 * 
	 */
	protected abstract void beforeExecutor();
	
	/***
	 * 响应报文发送前预处理
	 * 
	 */
	protected abstract void afterExecutor();
}
