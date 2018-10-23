/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.esb.ycesb.server
 * @�ļ�����YcEsbExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:39:47
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.esb.ycesb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spc.webos.endpoint.ESB2;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�YcEsbExecutor
 * @��������YC ESB ִ�г���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:39:48   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:39:48
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class YcEsbExecutor extends TxAdapterExcutor{
	private static Logger log = LoggerFactory.getLogger(YcEsbExecutor.class);
	/**
	 * @��������:esbCharsetName
	 * @��������:YCESB�ַ���
	 * @since 1.0.0
	 */
	protected static String esbCharsetName="UTF-8";
	/**
	 * @��������:esb2
	 * @��������:ESB2
	 * @since 1.0.0
	 */
	protected ESB2 esb2;
	
	/**
	 *@���캯�� 
	 */
	public YcEsbExecutor() {
		super();
	}

	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@param reqBuf
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void init(byte[] reqBuf) throws Exception {
		this.recvmsg=new String(reqBuf,esbCharsetName);
		log.info(">>>[RECV-XML]");
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		interFaceType="YCESB2";
		esb2=ESB2.getInstance();
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.TxAdapterExcutor#execute()
	 */
	public void execute() {
		try {
			/** ���ɲ�ǰ�ô��� **/
			beforeExecutor();
			/****����XML����****/
			resolvingXml();
			if (doc != null) {
				/** ���ý�����Ϣ���� **/
				data.setCharsetName(esbCharsetName);
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
			esb2.sendResponse(getResXml());
		} catch (RequestIOException eie) {
			try{
				log.error("����Ƿ�:{}", eie.getMessage());
				resXml = createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(), eie.getMessage());
				afterExecutor();
				esb2.sendResponse(getResXml());
			}catch(Exception e1){
				log.info("!> Thread[" + this.sid + "] Exception[1] loop.");
			}
			
		} catch (Exception e) {
			try{
				log.error("�������ڲ�����:", e);
				resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), e.getMessage());
				afterExecutor();
				esb2.sendResponse(getResXml());
			}catch(Exception e2){
				log.info("!> Thread[" + this.sid + "] Exception[2] loop.");
			}
			
		}catch(Throwable ex){
			try{
				log.error("���������ش���:", ex);
				resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), ex.getMessage());
				afterExecutor();
				esb2.sendResponse(getResXml());
			}catch (Exception e){
				return;
			}catch(Throwable ex1){
				return;
			}
		}
	}
	
	/**
	 * @��������:getResXml
	 * @��������:��ȡ��Ӧ����
	 * @�����뷵��˵��:
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	protected byte[] getResXml() throws Exception{
		log.info("<<<[SEND]");
		log.info(SensitHelper.getInstance().doInforFilter(this.resXml,null));
		if(this.resXml==null){
			return "".getBytes(esbCharsetName);
		}
		return this.resXml.getBytes(esbCharsetName);
	}
	
	/**
	 * �ӵ����ĺ�ǰ�ô���
	 */
	protected abstract void beforeExecutor();
	/**
	 * ��Ӧ���ĺ��ô���
	 */
	protected abstract void afterExecutor();

	/**
	 * �������ģ���װecif�ڲ�����
	 */
	protected abstract void getEcifData() throws Exception;

	/**
	 * ��װ��Ӧ����
	 */
	protected abstract String createOutputDocument() throws Exception;

	/**
	 * Ĭ����Ӧ������
	 */
	protected abstract String createDefauteMsg(String errorCode, String msg);


}
