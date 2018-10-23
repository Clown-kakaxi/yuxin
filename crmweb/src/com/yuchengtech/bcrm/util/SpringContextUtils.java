package com.yuchengtech.bcrm.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**   
 * @Description: Spring Bean辅助工具类
 * @author: liuyx   
 * @date: 2017年12月19日 下午3:29:06 
 */
public class SpringContextUtils {
	/**
	 * 系统上下文
	 */
	private static ServletContext servletContext = null;
	
	/**
	 * The Constant applicationContext.
	 * @属性描述:
	 */
	private static WebApplicationContext ctx = null;
	
	/**
	 * 根据bean的名称/ID获取Bean对象.
	 * @param beanName the bean name
	 * @return the bean
	 */
	public static Object getBean(String beanName){
		return ctx.getBean(beanName);
	}
	
	/**
	 * 根据bean的类型.
	 * @param beanName the bean name
	 * @return the bean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object getBean(Class clazz){
		if(ctx==null){
			ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}
		return ctx.getBean(clazz);
	}
	/**
	 * 初始化上下文
	 * @param sc ServletContext
	 */
	public static void init(ServletContext sc){
		servletContext = sc;
		ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}
}
