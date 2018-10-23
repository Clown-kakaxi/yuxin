/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����WriteEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:18:05
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�WriteEcifDealEngine
 * @��������д���״�������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:18:05
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:18:05
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustrizEcifDealEngine extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory.getLogger(WriteEcifDealEngine.class);

	@Override
	public void execute(EcifData data) {
		String txCode = data.getTxCode();
		bizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txCode);

		if (bizLogic == null) {
			String msg = String.format("���׳���δ�н��״���(%s)��Ӧ�Ĵ�����", txCode);
			log.error(msg);
			data.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
			data.setSuccess(false);
		}
		try {
			String msg = String.format("����[���ױ���:%s]��ʼ����", txCode);
			log.info(msg);
			bizLogic.process(data);
		} catch (Exception e) {
			String msg = String.format("���׳���");
			log.info(msg);
			log.error("{}", e);
			data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), msg);
			data.setSuccess(false);
			return;
		}
	}
}
