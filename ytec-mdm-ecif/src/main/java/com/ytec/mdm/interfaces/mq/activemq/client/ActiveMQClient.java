/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.mq.activemq.client
 * @�ļ�����ActiveMQClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:43:38
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ActiveMQClient
 * @��������ActiveMQ �ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:43:39   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:43:39
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
			log.error("����MQ�쳣",e);
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
				log.error("����MQ��Ϣ�쳣",e);
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
			log.error("�ر�MQ�����쳣",e);
		}
	}

}
