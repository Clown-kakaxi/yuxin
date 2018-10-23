/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.mq.ibmmq.server
 * @�ļ�����IbmMQExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:44:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.mq.ibmmq.server;

import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.server.common.ServerConfiger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

/**
 * The Class IbmMQExecutor.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ� IbmMQExecutor
 * @��������IbmMQ �����ִ��
 * @��������:
 * @�����ˣ� wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 11:44:28
 * @�޸��ˣ� wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 11:44:28
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public abstract class IbmMQExecutor extends TxAdapterExcutor implements MessageListener{
	
	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(IbmMQExecutor.class);
	
	/**
	 * The rcvqueue.
	 * 
	 * @��������:
	 */
	private String rcvqueue;
	
	/**
	 * The sndqueue.
	 * 
	 * @��������:
	 */
	private String sndqueue;
	
	/**
	 * The Constant DEFAULT_SLEEP.
	 * 
	 * @��������:
	 */
	private static final int DEFAULT_SLEEP = 5000;
	
	/**
	 * The cf.
	 * 
	 * @��������:
	 */
	private JmsConnectionFactory cf;	
	
	/**
	 * The connection.
	 * 
	 * @��������:
	 */
	private Connection connection;
	
	/**
	 * The session.
	 * 
	 * @��������:
	 */
	private Session session;
	
	/**
	 * The rcv_dest.
	 * 
	 * @��������:
	 */
	private Destination rcv_dest;
	
	/**
	 * The consumer.
	 * 
	 * @��������:
	 */
	private MessageConsumer consumer;
	
	/**
	 * The snd_dest.
	 * 
	 * @��������:
	 */
	private Destination snd_dest;
	
	/**
	 * The producer.
	 * 
	 * @��������:
	 */
	private MessageProducer producer;
	
	
	/**
	 * The Constructor.
	 * 
	 * @���캯�� ibm mq executor
	 */
	public IbmMQExecutor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Inits the.
	 * 
	 * @throws Exception
	 *             the exception
	 * @��������:void init()
	 * @��������:
	 * @�����뷵��˵��: void init()
	 * @�㷨����:
	 */
	public void init() throws Exception {
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
	    cf = ff.createConnectionFactory();
	    cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, ServerConfiger.getStringArg("ibmMqHostname"));
	    cf.setIntProperty(WMQConstants.WMQ_PORT, ServerConfiger.getIntArg("ibmMqQmPort"));
	    cf.setStringProperty(WMQConstants.WMQ_CHANNEL, ServerConfiger.getStringArg("ibmMqChannel"));
	    cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
	    cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, ServerConfiger.getStringArg("ibmMqQmName"));
	    sndqueue = ServerConfiger.getStringArg("ibmMqSndQueueName");
	    rcvqueue = ServerConfiger.getStringArg("ibmMqRcvQueueName");
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.TxAdapterExcutor#execute()
	 */
	public void execute() {
		try {
		    connection = cf.createConnection();		    
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			rcv_dest = session.createQueue(rcvqueue);		      
		    consumer = session.createConsumer(rcv_dest);
			snd_dest = session.createQueue(sndqueue);
			producer = session.createProducer(snd_dest);
			consumer.setMessageListener(this);
		} catch (Exception e) {
			log.error("����IBM WebSphere MQ�쳣��",e);
		}
	}
	
	/**
	 * Send msg.
	 * 
	 * @param msg
	 *            the msg
	 * @��������:void sendMsg(String msg)
	 * @��������:
	 * @�����뷵��˵��: void sendMsg(String msg)
	 * @�㷨����:
	 */
	protected void sendMsg(String msg){		
		try {
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
		} catch (Exception e) {
			log.error("����IBM WebSphere MQ��Ϣ�쳣��",e);
		}
	}

	/**
	 * Close.
	 * 
	 * @��������:void close()
	 * @��������:
	 * @�����뷵��˵��: void close()
	 * @�㷨����:
	 */
	public void close() {
		try {
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			log.error("�ر�IBM WebSphere MQ�����쳣��",e);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
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
	
	/**
	 * ��д *.
	 * 
	 * @��������:void run()
	 * @��������:
	 * @�����뷵��˵��: void run()
	 * @�㷨����:
	 */
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

	/**
	 * Before executor.
	 * 
	 * @��������:void beforeExecutor()
	 * @��������:
	 * @�����뷵��˵��: void beforeExecutor()
	 * @�㷨����:
	 */
	protected abstract void beforeExecutor();

	/**
	 * After executor.
	 * 
	 * @��������:void afterExecutor()
	 * @��������:
	 * @�����뷵��˵��: void afterExecutor()
	 * @�㷨����:
	 */
	protected abstract void afterExecutor();

	/**
	 * Gets the ecif data.
	 * 
	 * @return the ecif data
	 * @throws Exception
	 *             the exception
	 */
	protected abstract void getEcifData() throws Exception;

	/**
	 * Creates the output document.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @��������:String createOutputDocument()
	 * @��������:
	 * @�����뷵��˵��: String createOutputDocument()
	 * @�㷨����:
	 */
	protected abstract String createOutputDocument() throws Exception;

	/**
	 * Creates the defaute msg.
	 * 
	 * @param errorCode
	 *            the error code
	 * @param msg
	 *            the msg
	 * @return the string
	 * @��������:String createDefauteMsg(String errorCode, String msg)
	 * @��������:
	 * @�����뷵��˵��: String createDefauteMsg(String errorCode, String msg)
	 * @�㷨����:
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg);

}
