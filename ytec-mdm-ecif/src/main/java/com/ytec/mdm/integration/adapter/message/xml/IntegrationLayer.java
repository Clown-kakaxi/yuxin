/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.adapter.message.xml
 * @�ļ�����IntegrationLayer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:34:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.adapter.message.xml;

import com.ytec.mdm.base.bo.EcifData;
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IntegrationLayer
 * @��������XML�����������ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:34:24   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:34:24
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IntegrationLayer {
	/**
	 * ���ɲ㴦��
	 * @param data
	 */
	public void process (EcifData data);
	/**
	 * ��¼���ݿ���־
	 * @param resXml
	 */
	public void setTxLog (String resXml);
	
}
