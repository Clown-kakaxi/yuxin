/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.mq.ibmmq.server
 * @文件名：IbmMQExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:44:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称： IbmMQExecutor
 * @类描述：IbmMQ 服务端执行
 * @功能描述:
 * @创建人： wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 11:44:28
 * @修改人： wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 11:44:28
 * @修改备注：
 * @修改日期 修改人员 修改原因
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public abstract class IbmMQExecutor extends TxAdapterExcutor implements MessageListener{
	
	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	private static Logger log = LoggerFactory.getLogger(IbmMQExecutor.class);
	
	/**
	 * The rcvqueue.
	 * 
	 * @属性描述:
	 */
	private String rcvqueue;
	
	/**
	 * The sndqueue.
	 * 
	 * @属性描述:
	 */
	private String sndqueue;
	
	/**
	 * The Constant DEFAULT_SLEEP.
	 * 
	 * @属性描述:
	 */
	private static final int DEFAULT_SLEEP = 5000;
	
	/**
	 * The cf.
	 * 
	 * @属性描述:
	 */
	private JmsConnectionFactory cf;	
	
	/**
	 * The connection.
	 * 
	 * @属性描述:
	 */
	private Connection connection;
	
	/**
	 * The session.
	 * 
	 * @属性描述:
	 */
	private Session session;
	
	/**
	 * The rcv_dest.
	 * 
	 * @属性描述:
	 */
	private Destination rcv_dest;
	
	/**
	 * The consumer.
	 * 
	 * @属性描述:
	 */
	private MessageConsumer consumer;
	
	/**
	 * The snd_dest.
	 * 
	 * @属性描述:
	 */
	private Destination snd_dest;
	
	/**
	 * The producer.
	 * 
	 * @属性描述:
	 */
	private MessageProducer producer;
	
	
	/**
	 * The Constructor.
	 * 
	 * @构造函数 ibm mq executor
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
	 * @函数名称:void init()
	 * @函数描述:
	 * @参数与返回说明: void init()
	 * @算法描述:
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
			log.error("连接IBM WebSphere MQ异常：",e);
		}
	}
	
	/**
	 * Send msg.
	 * 
	 * @param msg
	 *            the msg
	 * @函数名称:void sendMsg(String msg)
	 * @函数描述:
	 * @参数与返回说明: void sendMsg(String msg)
	 * @算法描述:
	 */
	protected void sendMsg(String msg){		
		try {
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
		} catch (Exception e) {
			log.error("发送IBM WebSphere MQ消息异常：",e);
		}
	}

	/**
	 * Close.
	 * 
	 * @函数名称:void close()
	 * @函数描述:
	 * @参数与返回说明: void close()
	 * @算法描述:
	 */
	public void close() {
		try {
			consumer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			log.error("关闭IBM WebSphere MQ连接异常：",e);
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
	
	/**
	 * 重写 *.
	 * 
	 * @函数名称:void run()
	 * @函数描述:
	 * @参数与返回说明: void run()
	 * @算法描述:
	 */
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

	/**
	 * Before executor.
	 * 
	 * @函数名称:void beforeExecutor()
	 * @函数描述:
	 * @参数与返回说明: void beforeExecutor()
	 * @算法描述:
	 */
	protected abstract void beforeExecutor();

	/**
	 * After executor.
	 * 
	 * @函数名称:void afterExecutor()
	 * @函数描述:
	 * @参数与返回说明: void afterExecutor()
	 * @算法描述:
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
	 * @函数名称:String createOutputDocument()
	 * @函数描述:
	 * @参数与返回说明: String createOutputDocument()
	 * @算法描述:
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
	 * @函数名称:String createDefauteMsg(String errorCode, String msg)
	 * @函数描述:
	 * @参数与返回说明: String createDefauteMsg(String errorCode, String msg)
	 * @算法描述:
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg);

}
