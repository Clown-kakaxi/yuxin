/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.identification
 * @�ļ�����AbsBizGetContId.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-7-8-����9:42:25
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.component.biz.identification;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.service.facade.IBizGetContId;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�AbsBizGetContId
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-7-8 ����9:42:25   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-7-8 ����9:42:25
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class AbsBizGetContId implements IBizGetContId {

	/* (non-Javadoc)
	 * @see com.ytec.mdm.service.facade.IBizGetContId#bizGetContId(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public abstract void bizGetContId(EcifData ecifData);

}
