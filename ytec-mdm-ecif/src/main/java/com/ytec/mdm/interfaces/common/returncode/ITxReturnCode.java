/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.returncode
 * @�ļ�����ITxReturnCode.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-24-����10:53:05
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.returncode;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.Error;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ITxReturnCode
 * @���������������ӳ��ӿ�
 * @��������:�ṩECIF�����������Χϵͳ�������ӳ�书��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-24 ����10:53:05   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-24 ����10:53:05
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface ITxReturnCode {
	/**
	 * @��������:getExterReturnCode
	 * @��������:��ȡ��Χϵͳ�������
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@return Error
	 * @�㷨����:
	 */
	public Error getExterReturnCode(EcifData ecifData);
}
