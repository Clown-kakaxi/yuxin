/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.mq.activemq.server
 * @文件名：ActiveMQExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:42:59
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.mq.activemq.server;

import javax.jms.Message;
import javax.jms.MessageListener;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.server.common.ServerConfiger;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ActiveMQExecutor
 * @类描述： ActiveMQ 执行端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:42:59   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:42:59
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class ActiveMQExecutor extends TxAdapterExcutor implements
		MessageListener {
	private static Logger log = LoggerFactory.getLogger(ActiveMQExecutor.class);
	private String url;
	private String rcvqueue;
	private String sndqueue;
	private int DEFAULT_SLEEP = 5000;
	private int DEFAULT_TIMEOUT = 1000;
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination rcv_dest;
	private MessageConsumer consumer;
	private Destination snd_dest;
	private MessageProducer producer;
	

	public ActiveMQExecutor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		url = ServerConfiger.getStringArg("activeMqUrl");
		rcvqueue = ServerConfiger.getStringArg("activeMqRcvQueueName");
		sndqueue = ServerConfiger.getStringArg("activeMqSndQueueName");
		DEFAULT_TIMEOUT = ServerConfiger.getIntArg("timeout");
		interFaceType="ActiveMQ";
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		try {
			connectionFactory = new ActiveMQConnectionFactory(url);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			rcv_dest = session.createQueue(rcvqueue);
			consumer = session.createConsumer(rcv_dest);
			consumer.setMessageListener(this);
			snd_dest = session.createQueue(sndqueue);
			producer = session.createProducer(snd_dest);
		} catch (Exception e) {
			log.error("连接ActiveMQ异常", e);
		}

	}

//	private void rcvmsg() {
//		try {
//			recvmsg = null;
//			message = consumer.receive(DEFAULT_TIMEOUT);
//			if (message instanceof TextMessage) {
//				TextMessage txtMsg = (TextMessage) message;
//				recvmsg = txtMsg.getText();
//			}
//		} catch (Exception e) {
//			log.error("同步接收ActiveMQ消息异常", e);
//		}
//	}

	private void sendMsg(String msg) {
		try {
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
		} catch (Exception e) {
			log.error("发送ActiveMQ消息异常：", e);
		}
	}

	public void close() {
		try {
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			log.error("关闭ActiveMQ：", e);
		}
	}

	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				recvmsg = null;
				recvmsg = ((TextMessage) message).getText();
			} catch (Exception e) {
				log.error("接收ActiveMQ消息异常", e);
			}
			if (null != recvmsg && recvmsg.length() > 0) {
				try {
					/** 前置处理 **/
					beginAdapter();
					/** 集成层前置处理 **/
					beforeExecutor();
					/****解析XML报文****/
					resolvingXml();
					if (doc != null) {
						/** 设置交易信息参数 **/
						getEcifData();
					} else {
						log.error("解析到错误的ESB请求");
						throw new RequestIOException("解析到错误的ESB请求");
					}
					/** 调用服务 */
					atp.process(data);
					/** 组装响应报文 **/
					resXml = createOutputDocument();

					/** 集成层后置处理 **/
					afterExecutor();

					/** 发送报文 **/
					sendMsg(resXml);

				} catch (RequestIOException eie) {
					log.error("请求非法:", eie);
					resXml = createDefauteMsg(
							ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(),
							eie.getMessage());
					afterExecutor();
					sendMsg(resXml);
				} catch (Exception e) {
					log.error("服务器内部错误:", e);
					resXml = createDefauteMsg(
							ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),
							e.getMessage());
					afterExecutor();
					sendMsg(resXml);
				} finally {
					/** 后置处理 **/
					endAdapter();
				}
			}
		}

	}

	/** 重写 **/
	public void run() {
		/** 适配器处理 ***/
		execute();
		while (ServerConfiger.serverRun) {
			try {
				Thread.sleep(DEFAULT_SLEEP);
			} catch (Exception e) {
				
			}
		}
		this.close();
	}

	protected abstract void beforeExecutor();

	protected abstract void afterExecutor();

	protected abstract void getEcifData() throws Exception;

	protected abstract String createOutputDocument() throws Exception;

	protected abstract String createDefauteMsg(String errorCode, String msg);

}
