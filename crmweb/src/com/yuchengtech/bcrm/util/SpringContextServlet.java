package com.yuchengtech.bcrm.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**   
 * @Description: TODO
 * @Package: com.yuchengtech.bcrm.util 
 * @author: liuyx   
 * @date: 2017年12月27日 上午10:40:25 
 */
public class SpringContextServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(SpringContextServlet.class);
	@Override
	public void init() throws ServletException {
		log.info("开始初始化SpringContextUtils");
	    SpringContextUtils.init(getServletContext());
	    log.info("初始化SpringContextUtils完成");
	}
}
