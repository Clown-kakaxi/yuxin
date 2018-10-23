/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.ws.server
 * @�ļ�����WsExecutor.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:49:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.ws.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.TxAdapterExcutor;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�WsExecutor
 * @��������WEB SERVICE ִ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:49:28   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:49:28
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class WsExecutor extends TxAdapterExcutor{
	private static Logger log = LoggerFactory.getLogger(WsExecutor.class);
	private static final Pattern ENCODING = Pattern.compile("encoding=('|\")([\\w|-]+)('|\")",
            Pattern.CASE_INSENSITIVE);   //XML�ַ���ƥ������ʽ
	
	/**
	 *@���캯�� 
	 */
	public WsExecutor() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@param req ������
	 * @�㷨����:
	 */
	public void init(String req){
		this.recvmsg=req;
		log.info(">>>[RECV-XML]");
		log.info(SensitHelper.getInstance().doInforFilter(this.recvmsg, null));
		interFaceType="WS";
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.TxAdapterExcutor#execute()
	 */
	protected void execute() {
		try {
			beforeExecutor();
			
			/****����XML����****/
			resolvingXml();
			
			if (doc != null) {
				/** ���ý�����Ϣ���� **/
				if(!StringUtil.isEmpty(doc.getXMLEncoding())){
					charSet=doc.getXMLEncoding();
				}else{
					/*��ȡ�ַ���**/
					Matcher matcher = ENCODING.matcher(this.recvmsg);
			        if (matcher.find()) {
			            charSet=matcher.group(2);
			        }
				}
				//�����ַ���
				data.setCharsetName(charSet);
				getEcifData();
			} else {
				log.error("�����������ESB����");
				throw new RequestIOException("�����������ESB����");
			}
			/** ���÷��� */
			atp.process(data);
			resXml = createOutputDocument();
		} catch (RequestIOException eie) {
			log.error("����Ƿ�:{}", eie.getMessage());
			resXml = createDefauteMsg(ErrorCode.ERR_CLIENT_BAD_REQUEST.getCode(), eie.getMessage());
		} catch (Exception e) {
			log.error("�������ڲ�����:", e);
			resXml = createDefauteMsg(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), e.getMessage());
		}
		afterExecutor();
	}
	
	/**
	 * @��������:getResXml
	 * @��������:��ȡ��Ӧ����
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public String getResXml(){
		log.info(">>>[SEND-XML]");
		log.info(SensitHelper.getInstance().doInforFilter(this.resXml, null));
		return this.resXml;
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
