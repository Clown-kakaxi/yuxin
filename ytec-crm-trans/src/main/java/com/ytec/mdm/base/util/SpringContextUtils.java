/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：SpringContextUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:27:58
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SpringContextUtils
 * @类描述：Spring上下文工具类
 * @功能描述:作为MDM产品中获取SpringBean的入口
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:28:04   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:28:04
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SpringContextUtils{

	/**
	 * The Constant applicationContext.
	 * 
	 * @属性描述:
	 */
	private static ApplicationContext applicationContext = null;
	
	/**
	 * 根据bean的名称/ID获取Bean对象.
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
