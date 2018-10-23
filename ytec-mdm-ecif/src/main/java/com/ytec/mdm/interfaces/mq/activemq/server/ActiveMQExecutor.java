/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.mq.activemq.server
 * @�ļ�����ActiveMQExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:42:59
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ActiveMQExecutor
 * @�������� ActiveMQ ִ�ж�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:42:59   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:42:59
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
			log.error("����ActiveMQ�쳣", e);
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
//			log.error("ͬ������ActiveMQ��Ϣ�쳣", e);
//		}
//	}

	private void sendMsg(String msg) {
		try {
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
		} catch (Exception e) {
			log.error("����ActiveMQ��Ϣ�쳣��", e);
		}
	}

	public void close() {
		try {
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			log.error("�ر�ActiveMQ��", e);
		}
	}

	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				recvmsg = null;
				recvmsg = ((TextMessage) message).getText();
			} catch (Exception e) {
				log.error("����ActiveMQ��Ϣ�쳣", e);
			}
			if (null != recvmsg && recvmsg.length() > 0) {
				try {
					/** ǰ�ô��� **/
					beginAdapter();
					/** ���ɲ�ǰ�ô��� **/
					beforeExecutor();
					/****����XML����****/
					resolvingXml();
					if (doc != null) {
						/** ���ý�����Ϣ���� **/
						getEcifData();
					} else {
						log.error("�����������ESB����");
						throw new RequestIOException("�����������ESB����");
					}
					/** ���÷��� */
					atp.process(data);
					/** ��װ��Ӧ���� **/
					resXml = createOutputDocument();

					/** ���ɲ���ô��� **/
					afterExecutor();

					/** ���ͱ��� **/
					sendMsg(resXml);

				} catch (RequestIOException eie) {
					log.error("����Ƿ�:", eie);
					resXml = createDefauteMsg(
							ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(),
							eie.getMessage());
					afterExecutor();
					sendMsg(resXml);
				} catch (Exception e) {
					log.error("�������ڲ�����:", e);
					resXml = createDefauteMsg(
							ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(),
							e.getMessage());
					afterExecutor();
					sendMsg(resXml);
				} finally {
					/** ���ô��� **/
					endAdapter();
				}
			}
		}

	}

	/** ��д **/
	public void run() {
		/** ���������� ***/
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
