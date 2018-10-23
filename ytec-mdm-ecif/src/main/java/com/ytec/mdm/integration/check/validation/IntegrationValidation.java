/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.validation
 * @�ļ�����IntegrationValidation.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:51:04
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�IntegrationValidation
 * @�����������ɲ㱨��У��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:51:05
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:51:05
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class IntegrationValidation extends AbstractValidationChain {
	private static IVerifChain validationHead = new IntegrationValidation();

	public void init(Map arg) throws Exception {
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		Class clazz = null;
		IVerifChain cc = null;
		IVerifChain point = validationHead;
		while (it.hasNext()) {
			clazz = Class.forName(it.next());
			cc = (IVerifChain) clazz.newInstance();
			point.addChain(cc);
			point = cc;
		}
		point = null;
	}

	public IntegrationValidation() {
	}

	/**
	 * @��������:getInstance
	 * @��������:��ȡУ�������
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public static IVerifChain getInstance() {
		return validationHead;
	}

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(ecifData.getTxCode())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), "���״���Ϊ��");
			log.warn("���״���Ϊ��");
			return false;
		}
		/** ����ϵͳ */
		if (StringUtil.isEmpty(ecifData.getOpChnlNo())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), "����ϵͳΪ��");
			log.warn("����{}����ϵͳ��Ϊ��", ecifData.getTxCode());
			return false;
		}
		/** �����Ա�ţ����ײ�����Ա */
		if (StringUtil.isEmpty(ecifData.getTlrNo())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), "������Ա��Ϊ��");
			log.warn("����{}������Ա��Ϊ��", ecifData.getTxCode());
			return false;
		}
		/** ������ˮ�� */
		if (StringUtil.isEmpty(ecifData.getReqSeqNo())) {
			ecifData.setStatus(ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getCode(), ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getChDesc());
			log.warn("����{}{}", ecifData.getTxCode(), ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getChDesc());
			return false;
		} else {
			String reqSeqNo = ecifData.getReqSeqNo();
			SimpleDateFormat format = new SimpleDateFormat(ServerConfiger.getStringArg("reqSeqNoDateFormat"));
			try {
				long reqTimestamp = format.parse(reqSeqNo).getTime();
				long currTimestamp = (new Date()).getTime();

				// ��֤ECIF���׷��������󷽵�ʵʱ�ԣ�����ʱ������Ľ��ײ���
				long timeSub = (currTimestamp - reqTimestamp)/1000;
				if (timeSub >= ServerConfiger.getIntArg("socketTimeOut")) {
					String msg = String.format("%s(%ds,Ԥ��:%ds)", ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getChDesc(),timeSub,ServerConfiger.getIntArg("socketTimeOut")/1000);
					ecifData.setStatus(ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getCode(),
							msg);
					log.info("����{}{}", ecifData.getTxCode(), msg);
					System.out.println();
					return false;
				}
			} catch (ParseException e) {
				ecifData.setStatus(ErrorCode.ERR_REQ_REQSEQ_FORMAT.getCode(),
						ErrorCode.ERR_REQ_REQSEQ_FORMAT.getChDesc());
				log.error("����{}{}\n{}", ecifData.getTxCode(), ErrorCode.ERR_REQ_REQSEQ_FORMAT.getChDesc(),
						e.getLocalizedMessage());
				return false;
			}
		}
		return true;
	}

}
