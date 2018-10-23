/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common
 * @�ļ�����TxAdapterExcutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-29-17:58:38
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */

package com.ytec.mdm.interfaces.common;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.integration.adapter.message.xml.IntegrationLayer;
import com.ytec.mdm.integration.adapter.message.xml.XmlIntegrationLayer;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.common.even.EvenSubject;
import com.ytec.mdm.interfaces.common.even.Subject;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�TxAdapterExcutor
 * @���������ӿ�ִ�л���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-29 ����5:56:27
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-29 ����5:56:27
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public abstract class TxAdapterExcutor implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TxAdapterExcutor.class);
	/**
	 * @��������:interFaceType
	 * @��������:�ӿ�����
	 * @since 1.0.0
	 */
	protected String interFaceType;
	/**
	 * @��������:atp
	 * @��������:�Զ���������
	 * @since 1.0.0
	 */
	protected IntegrationLayer atp;
	/**
	 * @��������:sid
	 * @��������:�̺߳�
	 * @since 1.0.0
	 */
	protected String sid;
	/**
	 * @��������:doc
	 * @��������:�����Ķ���
	 * @since 1.0.0
	 */
	protected Document doc;
	/**
	 * @��������:recvmsg
	 * @��������:������
	 * @since 1.0.0
	 */
	protected String recvmsg;
	/**
	 * @��������:resXml
	 * @��������:��Ӧ����
	 * @since 1.0.0
	 */
	protected String resXml;
	protected EcifData data;
	/**
	 * @��������:evenSubject
	 * @��������:�¼�֪ͨ
	 * @since 1.0.0
	 */
	protected Subject evenSubject = EvenSubject.getInstance();
	/**
	 * @��������:charSet
	 * @��������:�ַ���
	 * @since 1.0.0
	 */
	protected String charSet;

	/**
	 *@���캯��
	 */
	public TxAdapterExcutor() {
		/***
		 * Ϊÿ���������̷߳���һ��ID��
		 */
		long id = (long) (Math.random() * 10000000)
				+ System.currentTimeMillis() + this.hashCode();
		this.sid = this.getClass().getSimpleName() + "-" + id;
		/**
		 * ������XMLΪ���ĵļ��ɲ㴦��
		 */
		atp = new XmlIntegrationLayer();
		/***
		 * ����Ĭ�ϵ��ַ�������
		 */
		charSet = MdmConstants.TX_XML_ENCODING;
	}

	/**
	 * @��������:beginAdapter
	 * @��������:�ӿ�ִ�п�ʼ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected void beginAdapter() {
		log.info("!> @ {}  Executor.run() START.", this.sid);
		/***
		 * ����ECIF�ڲ�����
		 */
		data = new EcifData();
		data.setInterFaceType(interFaceType);
		/***
		 * ��ʼ��ʱ
		 */
		data.getStopWatch().start();
	}

	/**
	 * @��������:endAdapter
	 * @��������:ִ�н�������
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected void endAdapter() {
		/** ��ʱ���� **/
		data.getStopWatch().stop();
		/**����Ϊ�գ������˳�����***/
		if (this.recvmsg != null) {
			if (data.getTxCode() != null) {
				log.info("������[{}],����ϵͳ[{}],���׷���[{}],�����ʱ[{}]ms", data
						.getTxCode(), data.getOpChnlNo(), data.getDetailDes(),
						data.getStopWatch().getElapsedTime());
			}
			if (!data.isSuccess()) {
				log.info("���׳ɹ�״̬[FALSE],���÷�����Ϣλ��:{}", data.getFullTraceInfo());
			}
			/** ��¼��־ **/
			atp.setTxLog(resXml);
			/** �¼�֪ͨ */
			evenSubject.eventNotify(data);
		}
		data = null;
		log.info("!> @ {} Executor.run() DONE.", this.sid);
	}

	/**
	 * @��������:resolvingXml
	 * @��������:����XML
	 * @�����뷵��˵��:
	 * 		@throws RequestIOException
	 * @�㷨����:
	 */
	protected void resolvingXml() throws RequestIOException {
		try {
			/****
			 * ����XML����
			 ****/
			doc = XMLUtils.stringToXml(recvmsg);
			/***
			 * ����ԭʼ���Ĺ��Ժ�ͻ�������ʹ��
			 */
			data.setPrimalMsg(recvmsg);
			/**
			 * ����XML����
			 */
			data.setPrimalDoc(doc);
		} catch (Exception e) {
			log.error("�������ĳ���:{}",e.getMessage() );
			throw new RequestIOException("XML���ķǷ�");
		}
	}

	/**
	 * �����߳�
	 */
	public void run() {
		/** ǰ�ô��� **/
		beginAdapter();
		/** ���������� ***/
		execute();
		/** ���ô��� **/
		endAdapter();
	}

	/**
	 * @��������:execute
	 * @��������:ִ��
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected abstract void execute();

}
