/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.integration.sync
 * @�ļ�����SynchroToSystemHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:08:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.sync;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.plugins.synchelper.SynchroToSystemExecute;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�SynchroToSystemHandler
 * @������������ͬ������(������ϵͳ�ӿڵı��Ľӿ�ת��)
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:08:44   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:08:44
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
@Scope("prototype")
public class SynchroToSystemHandler extends SynchroToSystemExecute {
	private static Logger log = LoggerFactory.getLogger(SynchroToSystemHandler.class);
			

	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Document doc) {
		synchroRequestMsg = XMLUtils.xmlToString(doc);
		log.info(synchroRequestMsg);
		return true;
	}

	@Override
	public boolean executeResult() {
		// TODO Auto-generated method stub
		if(this.synchroResponseMsg!=null){
			log.info(this.synchroResponseMsg);
		}else{
			log.info("ͬ����Ӧ����Ϊ��");
		}
		return true;
	}

}
