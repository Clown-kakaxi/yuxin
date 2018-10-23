/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.normalsocket.server
 * @�ļ�����SocketExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-13:19:33
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SocketExecutor
 * @����������ͨsocket�ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����1:19:49
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����1:19:49
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public abstract class SocketExecutor extends TxAdapterExcutor implements NioExecutor {

	/**
	 * The log.
	 * 
	 * @��������:
	 */
	protected static Logger log = LoggerFactory.getLogger(SocketExecutor.class);

	protected IoSession session;

	/**
	 * The client.
	 * 
	 * @��������:�ͻ���ͨ��
	 */
	protected SocketChannel client;

	/**
	 * The selector.
	 * 
	 * @��������:ѡ����
	 */
	protected NioProcessor selector;

	/**
	 * The receivebuffer.
	 * 
	 * @��������:���ջ�����
	 */
	protected ByteBuffer receivebuffer;

	/**
	 * The write timeout.
	 * 
	 * @��������:��Ӧ��ʱʱ��
	 */
	protected static int writeTimeout = ServerConfiger.getIntArg("writeTimeout");

	/**
	 * The buffer block.
	 * 
	 * @��������:���Ļ�������С
	 */
	protected static int bufferBlock = ServerConfiger.getIntArg("bufferBlock");

	/**
	 * The decoder.
	 * 
	 * @��������:������
	 */
	protected final SocketRequestCoder decoder;

	/**
	 * The sleep time.
	 * 
	 * @��������:���粻�ȶ�ʱ���������ȴ�ʱ��
	 */
	protected int sleepTime = 50;

	/**
	 * The read max num.
	 * 
	 * @��������:���粻�ȶ�ʱ��һ��û�н����꣬��ȡ���ٴ�
	 */
	protected int readMaxNum = 100;

	/**
	 * ���캯��.
	 * 
	 * @param decoder
	 *        the decoder
	 * @���캯�� socket executor
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
	 * �����߳�.
	 * 
	 * @��������:void execute()
	 * @��������:
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
						/*** �������� ****/
						request = decoder.decode(receivebuffer);
					} catch (RequestTooLargeException e) {// ����̫��
						log.error("���ĳ�����", e);
						throw new RequestIOException(e.getMessage());
					} catch (Exception e) {// ���Ĵ���
						log.error("���Ľ�������", e);
						throw new RequestIOException(e.getMessage());
					}
					if (request) {
						/** û���������� ***/
						if (decoder.getContentLength() == 0) {
							log.info("����Ϊ��");
							send(decoder.encode(createDefauteMsg(ErrorCode.INF_PUBLISH_SUCCESS.getCode(), "������������,���ύECIF��������")));
							selector.removeOpenSocket(client);
							return;
						}
						log.info(">>>[RECV] Client[" + client.socket().getRemoteSocketAddress() + "][" + decoder.getContentLength() + "]");
						beforeExecutor();
						/** ���ý�����Ϣ���� **/
						if (!StringUtil.isEmpty(decoder.getCharSet())) {
							charSet = decoder.getCharSet();
						}
						data.setCharsetName(charSet);
						getEcifData();
						decoder.reset();
						/** ���ñ�׼���� */
						atp.process(data);
						resXml = createOutputDocument();
						afterExecutor();
						send(decoder.encode(resXml));
						if (!receivebuffer.hasRemaining()) {
							selector.registerChannel(session);
						}
					} else {
						if (rcount > readMaxNum) {
							log.error("���Ľ�����ʱ");
							throw new RequestIOException("���Ľ�����ʱ");
						}
						if (bufferBlock != read) {
							log.warn("����״̬���ã���Ҫ��ȡ��Ρ�����");
							try {
								Thread.sleep(sleepTime);
							} catch (Exception e) {
								log.warn(e.getMessage());
							}
						}
						rcount++;
						log.warn("��ȡ����" + read + "Byte,��Ҫ��ȡ��Ρ�����");
						this.receivebuffer.clear(); // �ٶ�
						read = client.read(receivebuffer);
						receivebuffer.flip(); // flip for read
						if (read < 0) {
							selector.removeOpenSocket(client);
							log.warn("!> @" + this.sid + " TxExecutor.run() exit.");
							return;
						} else if (read == 0) {
							log.warn("��ȡ0Byte����");
						}
					}
				} while (receivebuffer.hasRemaining()); // consume all
				if (!request) {
					log.error("���Ĵ���");
					throw new RequestIOException("���Ĵ���");
				}
			} else {
				/*** ע����¼� ****/
				selector.registerChannel(session);
			}
			/********/
		} catch (RequestIOException eie) {
			/*** ����Ƿ� ***/
			log.error("����Ƿ�:{}", eie.getMessage());
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
			/*** IO���� ***/
			log.error("IO����", ie);
			try {
				selector.removeOpenSocket(client);
			} catch (Exception e2) {
				log.error("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			return;
		} catch (Exception e) {
			/** ���������� **/
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
			log.error("!> [FATAL]���������ش���", ex);
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
	 * @��������:rejectedExecution
	 * @��������:�������ƣ��ܾ�
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void rejectedExecution() {
		/** ����ܾ� **/
		log.warn("!> ����ܾ�");
		try {
			resXml = createDefauteMsg(ErrorCode.ERR_SERVER_SERVICE_BUSY.getCode(), "����æ,��ȴ�һ�����ύ");
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
	 * ������Ϣ.
	 * 
	 * @param msg
	 *        ��Ϣ����
	 * @return ���͵����ݳ���
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 * @��������:long send(String msg)
	 * @��������:
	 * @�����뷵��˵��: long send(String msg)
	 * @�㷨����:
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
	 * ** ������Ϣ ��������״̬���õ����.
	 * 
	 * @param buf
	 *        the buf
	 * @return the long
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 * @��������:long sendChannel(ByteBuffer buf)
	 * @��������:
	 * @�����뷵��˵��: long sendChannel(ByteBuffer buf)
	 * @�㷨����:
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
					log.warn("����״̬���ã���Ҫ���Ͷ�Ρ�����");
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
	 * SOAP������װ.
	 * 
	 * @return the string
	 * @throws Exception
	 *         the exception
	 * @��������:String createOutputDocument()
	 * @��������:
	 */
	protected abstract String createOutputDocument() throws Exception;

	/**
	 * * SOAP����Ĭ����װ.
	 * 
	 * @param errorCode
	 *        the error code
	 * @param msg
	 *        the msg
	 * @return the string
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 * @��������:String createDefauteMsg(String errorCode, String msg)
	 * @��������:
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg) throws IOException;

	/**
	 * * �������ģ���ȡ����.
	 * 
	 * @return the ecif data
	 * @throws Exception
	 *         the exception
	 */
	protected abstract void getEcifData() throws Exception;

	/**
	 * * ������Ԥ����.
	 * 
	 * @��������:void beforeExecutor()
	 * @��������:
	 */
	protected abstract void beforeExecutor() throws Exception;

	/**
	 * * ��Ӧ���ķ���ǰԤ����.
	 * 
	 * @��������:void afterExecutor()
	 * @��������:
	 */
	protected abstract void afterExecutor();
}
