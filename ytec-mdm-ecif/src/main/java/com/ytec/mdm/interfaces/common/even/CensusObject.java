/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����CensusObject.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-29-����10:28:19
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CensusObject
 * @��������ͳ�Ƽ�������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-29 ����10:28:19   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-29 ����10:28:19
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CensusObject {
	/**
	 * @��������:censusTotal
	 * @��������:����(ʧ��+�ɹ�)
	 * @since 1.0.0
	 */
	private final AtomicInteger censusTotal=new AtomicInteger(0); 
	/**
	 * @��������:censusError
	 * @��������:ʧ��
	 * @since 1.0.0
	 */
	private final AtomicInteger censusError=new AtomicInteger(0);
	
	/**
	 * @��������:execuCensus
	 * @��������:ִ��ͳ��
	 * @�����뷵��˵��:
	 * 		@param success
	 * @�㷨����:
	 */
	public void execuCensus(boolean success){
		/**��������**/
		censusTotal.incrementAndGet();
		if(success){
			censusError.incrementAndGet();
		}
	}
	
	/**
	 * @��������:execuReset
	 * @��������:����
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void execuReset(){
		censusTotal.set(0);
		censusError.set(0);
	}
	
	/**
	 * @��������:getTotal
	 * @��������:��ȡ����
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public int getTotal(){
		return censusTotal.get();
	}
	/**
	 * @��������:getError
	 * @��������:��ȡ������
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public int getError(){
		return censusError.get();
	}
}
