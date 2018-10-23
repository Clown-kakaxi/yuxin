package com.yuchengtech.emp.biappframe.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.common.MenuInfoHolder;
import com.yuchengtech.emp.biappframe.base.common.ResOperInfoHolder;
import com.yuchengtech.emp.biappframe.base.exception.ServiceException;
import com.yuchengtech.emp.bione.util.ArrayUtils;

/**
 * <pre>
 * Title:自定义安全拦截器
 * Description: 拦截用户的所有请求,判断当前登录用户是否有权限访问请求的URL和方法操作权限,没有权限则抛出UnauthorizedException异常
 * </pre>
 * 
 * @author songxf
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

	/**
	 * 
	 */
	private static Logger logger = LoggerFactory
			.getLogger(AuthorizationInterceptor.class);

	// 不进行过滤的正则表达式集
	private Set<Pattern> excludeParams = Collections.emptySet();

	// 开发模式还是生产模式
	private boolean devMode = false;

	/**
	 * 判断url访问权限
	 * 
	 * @param path
	 * @param currentPath
	 * @param methodType
	 * @return 0失败 1成功不需要进行操作权限判断 2 成功 需要进行操作权限判断
	 */
	private int isUrlPermitted(final String currentPath, final String methodType) {

		Subject subject = BiOneSecurityUtils.getSubject();

		boolean isPermitted = false;
		// 权限许可字符串
		String stringPermission = GlobalConstants.PERMISSION_PREFIX_URL
				+ GlobalConstants.PERMISSION_SEPARATOR + currentPath;
		if (isExcluded(stringPermission)
				|| subject.isPermitted(stringPermission)
				|| BiOneSecurityUtils.getCurrentUserInfo().isSuperUser()
		) {
			isPermitted = true;
			return 1;
		} else {
			if (BiOneSecurityUtils.getCurrentUserInfo() != null) {
				// 查找当前的URL是否在系统中注册，如果没有注册，则认为有权限访问
				List<String> menuUrlList = MenuInfoHolder
						.getMenuUrlInfo(BiOneSecurityUtils.getCurrentUserInfo()
								.getCurrentLogicSysNo());
				List<String> newMenuList = new ArrayList<String>();
				for (String url : menuUrlList) {
					if (url != null) {
						if (!url.startsWith("/"))
							url = "/" + url;
						newMenuList.add(url);
					}
				}
				if (CollectionUtils.isEmpty(newMenuList)
						|| !newMenuList.contains(currentPath)) {
					isPermitted = true;
				}
				return 2;
			}
		}

		if (!isPermitted) {
			String[] strs = {
					BiOneSecurityUtils.getCurrentUserInfo().getLoginName(),
					methodType, currentPath };
			logger.warn("当前登录帐号{}没有以{}方式访问{}的权限.", strs);
		}

		return 0;
	}

	/**
	 * 判断资源操作权限
	 * 
	 * @param visitUrl
	 * @param methodType
	 * @return
	 */
	private boolean isResOperPermitted(String visitUrl, String methodType) {
		boolean isPermitted = false;

		final String permissionPrefix = visitUrl
				+ GlobalConstants.PERMISSION_SEPARATOR + methodType
				+ GlobalConstants.PERMISSION_SEPARATOR;

		List<String> resOperPermissionList = ResOperInfoHolder
				.getResOperInfo(BiOneSecurityUtils.getCurrentUserInfo()
						.getCurrentLogicSysNo());

		Collection<String> filterCollection = null;

		if (resOperPermissionList != null) {
			filterCollection = Collections2.filter(resOperPermissionList,
					new Predicate<String>() {

						public boolean apply(String resOperPermission) {

							if (resOperPermission.startsWith(permissionPrefix))
								return true;
							else
								return false;
						}
					});
		}

		// 说明当前访问的方法不使用权限保护
		if (CollectionUtils.isEmpty(filterCollection)) {
			isPermitted = true;
		} else {

			if (filterCollection.size() > 1) {

				logger.error("操作权限配置不合法：{}协议下，路径{}的对应多个操作权限!", methodType,
						visitUrl);
				throw new ServiceException("操作权限配置不合法!");
			}

			String resOperPermission = (String) filterCollection.toArray()[0];

			// 权限许可字符串
			String stringPermission = GlobalConstants.PERMISSION_PREFIX_OPER
					+ GlobalConstants.PERMISSION_SEPARATOR
					+ StringUtils.substringAfter(resOperPermission,
							permissionPrefix);

			Subject subject = BiOneSecurityUtils.getSubject();

			if (subject.isPermitted(stringPermission))
				isPermitted = true;
			else
				isPermitted = false;
		}

		if (!isPermitted) {
			String[] strs = {
					BiOneSecurityUtils.getCurrentUserInfo().getLoginName(),
					methodType, visitUrl };
			logger.warn("当前登录帐号{}没有在{}协议下访问{}的权限.", strs);
		}
		return isPermitted;
	}

	private boolean isExcluded(String paramName) {
		if (!this.excludeParams.isEmpty()) {
			for (Pattern pattern : excludeParams) {
				Matcher matcher = pattern.matcher(paramName);
				if (matcher.matches()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Sets a comma-delimited list of regular expressions to match parameters
	 * that should be removed from the parameter map.
	 * 
	 * @param commaDelim
	 *            A comma-delimited list of regular expressions
	 */
	public void setExcludeParams(String commaDelim) {
		Collection<String> excludePatterns = ArrayUtils
				.asCollection(commaDelim);
		if (excludePatterns != null) {
			excludeParams = new HashSet<Pattern>();
			for (String pattern : excludePatterns) {
				excludeParams.add(Pattern.compile(pattern));
			}
		}
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 当前请求的url
		String path = WebUtils.getPathWithinApplication(request);
		// 当前请求的方式
		String methodType = request.getMethod();
		// 去掉URL后面的参数
		path = StringUtils.substringBefore(path, "?");

		boolean isPermitted = false;
		boolean isSuperUser = false;
		boolean isResOper = false;// 是否判断具有操作权限
		BiOneUser currentUser = BiOneSecurityUtils.getCurrentUserInfo();
		if (currentUser != null)
			// isSuperUser = currentUser.isSuperUser();
			isSuperUser = false;

		if (devMode || isSuperUser || this.isExcluded(path)) {
			isPermitted = true;
		} else {
			int returnFlag = isUrlPermitted(path,methodType);
			if (returnFlag == 0) {
				isPermitted = false;
			} else if (returnFlag == 1) {
				isPermitted = true;
			} else {
				isPermitted = true;
				isResOper = true;
			}

		}
		// 判断是否有method访问权限
		if (isPermitted && isResOper) {
			isPermitted = isResOperPermitted(path, methodType);
		}
		if (isPermitted) {
			return true;
		} else {

			throw new UnauthorizedException();

		}
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
