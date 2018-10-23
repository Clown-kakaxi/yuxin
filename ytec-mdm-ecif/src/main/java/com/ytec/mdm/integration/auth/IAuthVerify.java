/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.auth
 * @�ļ�����IAuthVerify.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:39:14
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.auth;

import java.util.List;

import com.ytec.mdm.base.bo.AuthModel;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IAuthVerify
 * @��������������Ȩ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:39:24   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:39:24
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IAuthVerify {
	
	/**
	 * @��������:clientAuth
	 * @��������:�ͻ�����Ȩ
	 * @�����뷵��˵��:
	 * 		@param authModel
	 * 		@return
	 * @�㷨����:
	 */
	public List clientAuth(AuthModel authModel);

	
	/**
	 * @��������:serviceAuth
	 * @��������:������Ȩ
	 * @�����뷵��˵��:
	 * 		@param authModel
	 * 		@return
	 * @�㷨����:
	 */
	public List serviceAuth(AuthModel authModel); 
	
}
