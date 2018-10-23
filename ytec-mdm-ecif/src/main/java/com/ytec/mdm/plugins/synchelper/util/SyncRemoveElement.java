/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper.util
 * @�ļ�����SyncRemoveElement.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-24-����1:55:54
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;
import com.ytec.mdm.plugins.synchelper.SyncXmlObject;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SyncRemoveElement
 * @�����������������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-24 ����1:55:54   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-24 ����1:55:54
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SyncRemoveElement implements ISyncXmlFun{
	private Logger log = LoggerFactory.getLogger(SyncRemoveElement.class);

	/* (non-Javadoc)
	 * @see com.ytec.mdm.plugins.synchelper.ISyncXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public Object getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==2){
			SyncXmlObject syncXmlObject=(SyncXmlObject)arg[1];
			if("delete".equals(syncXmlObject.getOpType())){
				syncXmlObject.getPoint().detach();
			}else{
				log.warn("�����������������{}��֧��",syncXmlObject.getOpType());
			}
			
		}else{
			log.warn("�������������Ϊ��");
		}
		return null;
	}

}
