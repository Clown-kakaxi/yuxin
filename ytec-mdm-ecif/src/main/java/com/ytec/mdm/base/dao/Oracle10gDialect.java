/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.dao
 * @�ļ�����Oracle10gDialect.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-1-17-����12:04:31
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.dao;

import java.sql.Types;
import org.hibernate.type.StandardBasicTypes;



/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�Oracle10gDialect
 * @���������޸�hibernate����
 * @��������:�޸�hibernate���� ,���oracle ��ѯchar����һ���ַ�������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-1-17 ����12:04:31   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-1-17 ����12:04:31
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class Oracle10gDialect extends org.hibernate.dialect.Oracle10gDialect {

	public Oracle10gDialect() {
		super();
		// TODO Auto-generated constructor stub
		//ע��CHAR����ӳ��ΪSTRING
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName()); 
	}
	

}
