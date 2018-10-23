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

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;

/**
 * <pre>
 * 
 * Title:系统全局ajax过滤器
 * Description: 如果ajax请求发起时session已过期，则在response头信息中添加session-timeout的标记；
 * 相关的文件描述，请参考：@Revision 20130704182000
 * </pre>
 * 
 * @author liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class AjaxFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest  httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String path = WebUtils.getPathWithinApplication(httpRequest);
		path = StringUtils.substringBefore(path, "?");
		
		// 判断session是否失效
		Object subject = null;
		if (httpRequest.getSession() != null) {
			subject = httpRequest.getSession().getAttribute("onlineflag");
		}
		boolean isNew = httpRequest.getSession().isNew();
		
		if (subject == null || isNew) {
			httpResponse.setHeader("sessionstatus", "timeout"); // 在响应头设置session状态
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
