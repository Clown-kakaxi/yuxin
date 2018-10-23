/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.facade
 * @�ļ�����IVerifChain.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:23:47
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IVerifChain
 * @��������У�����ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:23:47   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:23:47
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IVerifChain {
	/**
	 * ������һ��У��
	 * @param c
	 */
	public void addChain(IVerifChain c);
	/**
	 * ������һ��У��
	 * @param ecifData
	 */
    public abstract boolean sendToChain(EcifData ecifData);
    /**
     * ��ȡ��һ��У��
     * @return
     */
    public IVerifChain getChain();
}
