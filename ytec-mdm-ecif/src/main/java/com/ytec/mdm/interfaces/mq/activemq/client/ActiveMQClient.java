/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.mq.activemq.client
 * @文件名：ActiveMQClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:43:38
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.mq.activemq.client;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ActiveMQClient
 * @类描述：ActiveMQ 客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:43:39   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:43:39
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ActiveMQClient implements IClient{
	private static Logger log = LoggerFactory.getLogger(ActiveMQClient.class);
	private String url;
	private String rcvqueue;
	private String sndqueue;
	private Connection connection = null;
	private ActiveMQConnectionFactory connectionFactory = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;
	private Message message = null;
	
	public boolean connect() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(url);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(sndqueue);
			producer = session.createProducer(destination);
			return true;
		} catch (Exception e) {
			log.error("连接MQ异常",e);
		}
		return false;
	}

	public ClientResponse sendMsg(String msg) {
		// TODO Auto-generated method stub
		ClientResponse clientResponse=new ClientResponse();
		if(connect()){
			try {
				message = session.createTextMessage(msg);
				producer.send(message);
			} catch (Exception e) {
				log.error("发送MQ信息异常",e);
				clientResponse.setSuccess(false);
			}
		}
		return clientResponse;
	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		url = (String)arg.get("activeMqUrl");
		sndqueue =(String)arg.get("activeMqRcvQueueName");
		rcvqueue = (String)arg.get("activeMqSndQueueName");
	}
	
	public void close() {
		try {
			connection.close();
		} catch (JMSException e) {
			log.error("关闭MQ连接异常",e);
		}
	}

}
