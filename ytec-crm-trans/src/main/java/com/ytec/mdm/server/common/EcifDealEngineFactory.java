/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����EcifDealEngineFactory.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:09:29
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.core.ErrorEcifDealEngine;
import com.ytec.mdm.integration.transaction.core.QueryEcifDealEngine;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.core.WriteEcifDealEngine;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�EcifDealEngineFactory
 * @�����������׽��״������湤����
 * @��������:���콻�״����������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:09:30
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:09:30
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public class EcifDealEngineFactory {

	private static Logger log = LoggerFactory
			.getLogger(EcifDealEngineFactory.class);

	/**
	 * @��������:getTxDealEngine
	 * @��������:��ȡ��������
	 * @�����뷵��˵��:
	 * 		@param txCode
	 * 		@return
	 * @�㷨����:
	 */
	public static IEcifDealEngine getTxDealEngine(String txCode) {

		IEcifDealEngine txDealEngine = null;
		try {
			TxModel4CRM txModel = TxModelHolder.getTxModel(txCode);
			System.out.println("txCode:"+ txCode);
			// ���ݽ�������������Ӧ�Ľ��״�������
			String txType = txModel.getTxDef().getTxType();
			String cfgTy=txModel.getTxDef().getCfgTp();
			if(MdmConstants.TX_CFG_TP_CUS.equals(cfgTy)){
				log.info("�ͻ�������[{}:{}]",txModel.getTxDef().getName(),txModel.getTxDef().getCnName());
				txDealEngine = (IEcifDealEngine)SpringContextUtils.getBean(txModel.getTxDef().getDealEngine());
			}else{
				if (MdmConstants.TX_TYPE_R.equals(txType)){// ��ѯ����
					log.info("��ѯ����[{}:{}]",txModel.getTxDef().getName(),txModel.getTxDef().getCnName());
					txDealEngine = new QueryEcifDealEngine();
				}else if(MdmConstants.TX_TYPE_W.equals(txType)){
					log.info("д����[{}:{}]",txModel.getTxDef().getName(),txModel.getTxDef().getCnName());
					txDealEngine = new WriteEcifDealEngine();
				}else {
					txDealEngine = new ErrorEcifDealEngine("��������:"+txType+" ,ϵͳ��֧��!");
					log.error("��������:{},ϵͳ��֧��!", txType);
				}
			}

		} catch (Exception e) {
			log.error("���콻�״�������ʧ��",e);
			String errorMsg = "����"+txCode+"�����ڻ�������Ϣ�쳣";
			txDealEngine = new ErrorEcifDealEngine(errorMsg);
		}

		return txDealEngine;
	}
}
