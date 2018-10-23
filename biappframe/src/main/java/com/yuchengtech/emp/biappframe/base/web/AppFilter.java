/**
 * 
 */
package com.yuchengtech.emp.biappframe.base.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

/**
 * <pre>
 * 
 * Title:系统全局过滤器
 * Description: 1、将请求客户端的IP地址压入slf4j的MDC，供记录日志时使用
 *              2、记录操作日志到数据库 （暂未实现）
 *              3、如果客户端使用的是IE8,使用IE7标准模式
 * 
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class AppFilter implements Filter {

	private final String IP_KEY = "client_ip";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String clientIp = req.getHeader("x-forwarded-for");

		if (clientIp == null) {

			clientIp = request.getRemoteAddr();

		}

		MDC.put(IP_KEY, clientIp);

		try {
			
			String agent = req.getHeader("user-agent");
			if (agent.contains("MSIE 8.0")) {// 如果客户端使用的是IE8,使用IE7标准模式
				((HttpServletResponse) response).addHeader("X-UA-Compatible",
						"IE=7");
			}

			chain.doFilter(request, response);

	
		

		} finally {

			MDC.remove(IP_KEY);

		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
