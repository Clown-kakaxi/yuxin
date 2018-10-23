/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����SpringContextUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:27:58
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SpringContextUtils
 * @��������Spring�����Ĺ�����
 * @��������:��ΪMDM��Ʒ�л�ȡSpringBean�����
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:28:04   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:28:04
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SpringContextUtils{

	/**
	 * The Constant applicationContext.
	 * 
	 * @��������:
	 */
	private static ApplicationContext applicationContext = null;
	
	/**
	 * ����bean������/ID��ȡBean����.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the bean
	 */
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public static void setApplicationContext(ApplicationContext context){
		// TODO Auto-generated method stub
		applicationContext=context;
	}
	
	public static void setApplicationContext(){
		if(applicationContext==null){
			applicationContext = new ClassPathXmlApplicationContext(MdmConstants.ROOT_APPLICATION_CONTEXT_FILE==null?"applicationContext.xml":MdmConstants.ROOT_APPLICATION_CONTEXT_FILE);
		}
	}
	
	public static void clearApplicationContext() {
		applicationContext=null;
	}
}
