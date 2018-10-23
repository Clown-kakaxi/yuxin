/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.normalsocket.server
 * @文件名：SocketExecutor.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-13:19:33
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.interfaces.socket.normalsocket.server;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioExecutor;
import com.ytec.mdm.interfaces.socket.NioProcessor;
import com.ytec.mdm.interfaces.socket.normalsocket.coder.SocketRequestCoder;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;
import com.ytec.mdm.server.common.ServerConfiger;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SocketExecutor
 * @类描述：普通socket接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 下午1:19:49
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 下午1:19:49
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */
public abstract class SocketExecutor extends TxAdapterExcutor implements NioExecutor {

	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	protected static Logger log = LoggerFactory.getLogger(SocketExecutor.class);

	protected IoSession session;

	/**
	 * The client.
	 * 
	 * @属性描述:客户端通道
	 */
	protected SocketChannel client;

	/**
	 * The selector.
	 * 
	 * @属性描述:选择器
	 */
	protected NioProcessor selector;

	/**
	 * The receivebuffer.
	 * 
	 * @属性描述:接收缓冲区
	 */
	protected ByteBuffer receivebuffer;

	/**
	 * The write timeout.
	 * 
	 * @属性描述:响应超时时间
	 */
	protected static int writeTimeout = ServerConfiger.getIntArg("writeTimeout");

	/**
	 * The buffer block.
	 * 
	 * @属性描述:报文缓冲区大小
	 */
	protected static int bufferBlock = ServerConfiger.getIntArg("bufferBlock");

	/**
	 * The decoder.
	 * 
	 * @属性描述:解析器
	 */
	protected final SocketRequestCoder decoder;

	/**
	 * The sleep time.
	 * 
	 * @属性描述:网络不稳定时，解析器等待时间
	 */
	protected int sleepTime = 50;

	/**
	 * The read max num.
	 * 
	 * @属性描述:网络不稳定时，一次没有解析完，读取多少次
	 */
	protected int readMaxNum = 100;

	/**
	 * 构造函数.
	 * 
	 * @param decoder
	 *        the decoder
	 * @构造函数 socket executor
	 */
	public SocketExecutor(SocketRequestCoder decoder) {
		this.decoder = decoder;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioExecutor#init(java.nio.channels.SocketChannel, com.ytec.mdm.interfaces.socket.NioProcessor)
	 */
	public void init(IoSession session, NioProcessor selector) {
		this.session = session;
		this.client = session.getClient();
		this.selector = selector;
		this.receivebuffer = ByteBuffer.allocate(bufferBlock);
		this.interFaceType = "SOCKET";
	}

	/**
	 * 处理线程.
	 * 
	 * @函数名称:void execute()
	 * @函数描述:
	 */
	public void execute() {
		try {
			this.receivebuffer.clear(); // clear for read
			int read = client.read(receivebuffer);
			if (read < 0) {
				selector.removeOpenSocket(client);
				log.info("!> @" + this.sid + " TxExecutor.run() exit.");
				return;
			} else if (read > 0) {
				receivebuffer.flip(); // flip for read
				boolean request;
				int rcount = 0;
				do {
					try {
						/*** 解析报文 ****/
						request = decoder.decode(receivebuffer);
					} catch (RequestTooLargeException e) {// 报文太长
						log.error("报文超长：", e);
						throw new RequestIOException(e.getMessage());
					} catch (Exception e) {// 报文错误
						log.error("报文解析错误", e);
						throw new RequestIOException(e.getMessage());
					}
					if (request) {
						/** 没有请求内容 ***/
						if (decoder.getContentLength() == 0) {
							log.info("请求为空");
							send(decoder.encode(createDefauteMsg(ErrorCode.INF_PUBLISH_SUCCESS.getCode(), "服务正在运行,请提交ECIF请求数据")));
							selector.removeOpenSocket(client);
							return;
						}
						log.info(">>>[RECV] Client[" + client.socket().getRemoteSocketAddress() + "][" + decoder.getContentLength() + "]");
						beforeExecutor();
						/** 设置交易信息参数 **/
						if (!StringUtil.isEmpty(decoder.getCharSet())) {
							charSet = decoder.getCharSet();
						}
						data.setCharsetName(charSet);
						getEcifData();
						decoder.reset();
						/** 调用标准服务 */
						atp.process(data);
						resXml = createOutputDocument();
						afterExecutor();
						send(decoder.encode(resXml));
						if (!receivebuffer.hasRemaining()) {
							selector.registerChannel(session);
						}
					} else {
						if (rcount > readMaxNum) {
							log.error("报文解析超时");
							throw new RequestIOException("报文解析超时");
						}
						if (bufferBlock != read) {
							log.warn("网络状态不好，需要读取多次。。。");
							try {
								Thread.sleep(sleepTime);
							} catch (Exception e) {
								log.warn(e.getMessage());
							}
						}
						rcount++;
						log.warn("读取数据" + read + "Byte,需要读取多次。。。");
						this.receivebuffer.clear(); // 再读
						read = client.read(receivebuffer);
						receivebuffer.flip(); // flip for read
						if (read < 0) {
							selector.removeOpenSocket(client);
							log.warn("!> @" + this.sid + " TxExecutor.run() exit.");
							return;
						} else if (read == 0) {
							log.warn("读取0Byte数据");
						}
					}
				} while (receivebuffer.hasRemaining()); // consume all
				if (!request) {
					log.error("报文错误");
					throw new RequestIOException("报文错误");
				}
			} else {
				/*** 注册读事件 ****/
				selector.registerChannel(session);
			}
			/********/
		} catch (RequestIOException eie) {
			/*** 请求非法 ***/
			log.error("请求非法:{}", eie.getMessage());
			try {
				data.setStatus(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(), eie.getMessage());
				data.setSuccess(false);
				resXml = createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(), eie.getMessage());
				afterExecutor();
				send(decoder.encode(resXml));
			} catch (Exception e1) {
				log.info("!> Thread[" + this.sid + "] Exception[1] loop {}", e1.getMessage());
			} finally {
				selector.removeOpenSocket(client);
			}
			return;
		} catch (IOException ie) {
			/*** IO错误 ***/
			log.error("IO错误", ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/** 服务器错误 **/
			log.error("!> [FATAL] Exception: ", e);
			try {
				resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), e.getMessage());
				afterExecutor();
				send(decoder.encode(resXml));
			} catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[3] loop. {}", e.getMessage());
			} finally {
				selector.removeOpenSocket(client);
			}
			return;
		} catch (Throwable ex) {
			log.error("!> [FATAL]服务器严重错误", ex);
			try {
				resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), ex.getMessage());
				afterExecutor();
				send(decoder.encode(resXml));
			} catch (Exception e3) {
				log.error("!> Thread[" + this.sid + "] Exception[4] loop.");
			} catch (Throwable ex1) {
				log.error("!> Thread[" + this.sid + "] Throwable[4] loop.");
			} finally {
				selector.removeOpenSocket(client);
			}
			return;
		}
	}

	/**
	 * Rejected execution.
	 * 
	 * @函数名称:rejectedExecution
	 * @函数描述:流量控制，拒绝
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void rejectedExecution() {
		/** 服务拒绝 **/
		log.warn("!> 服务拒绝");
		try {
			resXml = createDefauteMsg(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(), "服务忙,请等待一会再提交");
			afterExecutor();
			send(decoder.encode(resXml));
		} catch (Exception e3) {
			log.error("!> Thread[" + this.sid + "] Exception[3] loop.");
		} finally {
			selector.removeOpenSocket(client);
		}
		return;
	}

	/**
	 * 发送消息.
	 * 
	 * @param msg
	 *        消息内容
	 * @return 发送的数据长度
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 * @函数名称:long send(String msg)
	 * @函数描述:
	 * @参数与返回说明: long send(String msg)
	 * @算法描述:
	 */
	protected long send(String msg) throws IOException {
		long len = 0;
		ByteBuffer sendbuffer = ByteBuffer.wrap(msg.getBytes(charSet));
		len = sendChannel(sendbuffer);
		log.info("<<<[SEND] Client[" + client.socket().getRemoteSocketAddress() + "][" + len + "]");
		log.info(SensitHelper.getInstance().doInforFilter(msg, null));
		return len;
	}

	/**
	 * ** 发送消息 用于网络状态不好的情况.
	 * 
	 * @param buf
	 *        the buf
	 * @return the long
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 * @函数名称:long sendChannel(ByteBuffer buf)
	 * @函数描述:
	 * @参数与返回说明: long sendChannel(ByteBuffer buf)
	 * @算法描述:
	 */
	protected long sendChannel(ByteBuffer buf) throws IOException {
		SelectionKey key = null;
		Selector writeSelector = null;
		int attempts = 0;
		int bytesProduced = 0;
		try {
			while (buf.hasRemaining()) {
				int len = this.client.write(buf);
				attempts++;
				if (len < 0) { throw new EOFException(); }
				bytesProduced += len;
				if (len == 0) {
					log.warn("网络状态不好，需要发送多次。。。");
					if (writeSelector == null) {
						writeSelector = Selector.open();
						if (writeSelector == null) {
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
	 * SOAP报文组装.
	 * 
	 * @return the string
	 * @throws Exception
	 *         the exception
	 * @函数名称:String createOutputDocument()
	 * @函数描述:
	 */
	protected abstract String createOutputDocument() throws Exception;

	/**
	 * * SOAP报文默认组装.
	 * 
	 * @param errorCode
	 *        the error code
	 * @param msg
	 *        the msg
	 * @return the string
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 * @函数名称:String createDefauteMsg(String errorCode, String msg)
	 * @函数描述:
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg) throws IOException;

	/**
	 * * 解析报文，提取参数.
	 * 
	 * @return the ecif data
	 * @throws Exception
	 *         the exception
	 */
	protected abstract void getEcifData() throws Exception;

	/**
	 * * 请求报文预处理.
	 * 
	 * @函数名称:void beforeExecutor()
	 * @函数描述:
	 */
	protected abstract void beforeExecutor() throws Exception;

	/**
	 * * 响应报文发送前预处理.
	 * 
	 * @函数名称:void afterExecutor()
	 * @函数描述:
	 */
	protected abstract void afterExecutor();
}
