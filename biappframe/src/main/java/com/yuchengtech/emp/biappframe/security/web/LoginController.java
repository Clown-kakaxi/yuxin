package com.yuchengtech.emp.biappframe.security.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.authobj.service.DeptBS;
import com.yuchengtech.emp.biappframe.authobj.service.LogBS;
import com.yuchengtech.emp.biappframe.authobj.service.OrgBS;
import com.yuchengtech.emp.biappframe.authres.service.MenuBS;
import com.yuchengtech.emp.biappframe.base.common.LogicSysInfoHolder;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.lock.AccountLockValidator;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.util.ExDateUtils;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:用户登录Action
 * Description: 用户登录相关的业务处理
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:1.01    修改人： songxf  修改日期:2012-07-05     修改内容:增加逻辑系统支持
 * </pre>
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private UserBS userBS = null;
	@Autowired
	private OrgBS orgBS = null;
	@Autowired
	private AuthBS authBS = null;
	@Autowired
	private DeptBS deptBS = null;
	@Autowired
	private LogBS logBS;

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		mav.addObject("items", getLogicSysOption());
		mav.addObject("isFullScreen", true);
		return mav;
	}

	/**
	 * 用户登陆验证
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView logon(String username, String password,
			String logicsysno, boolean isFullScreen,
			HttpServletRequest request, HttpServletResponse response) {

		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		token.setRememberMe(false);

		Subject subject = BiOneSecurityUtils.getSubject();
		String loginFailInfo = null;
		String loginIp = this.getRequest().getRemoteAddr();
		try {

			logger.info("帐号[{}]于{}登录系统....", username,
					ExDateUtils.getCurrentStringDateTime());
			logBS.addLog(loginIp, logicsysno, username, "帐号[" + username + "]于"
					+ ExDateUtils.getCurrentStringDateTime() + "登录系统....");
			if (subject.isAuthenticated()) {

				subject.logout();

			}

			subject.login(token);

		} catch (UnknownAccountException uae) {

			logger.info("登录帐号: {} 不存在！", username);
			logBS.addLog(loginIp, logicsysno, username, "登录帐号: " + username
					+ " 不存在！");
			loginFailInfo = uae.getMessage();

		} catch (IncorrectCredentialsException ice) {

			logger.info("登录密码错误！");
			loginFailInfo = "登录密码错误,请重新输入!";
			logBS.addLog(loginIp, logicsysno, username, "登录密码错误！");
		} catch (LockedAccountException lae) {

			logger.info("登录帐号: {} 被锁定！", username);
			loginFailInfo = lae.getMessage();
			logBS.addLog(loginIp, logicsysno, username, "登录帐号: " + username
					+ " 被锁定！");
		} catch (AuthenticationException ae) {

			logger.error("系统登录时发生异常！");
			ae.printStackTrace();
			loginFailInfo = "系统发生未知异常,请联系管理员!";
		}

		/*@Revision 20130417071300-liuch 登录前要做密码验证  */
		AccountLockValidator lockValidator = AccountLockValidator.getInstance();
		lockValidator.validate(username, subject.isAuthenticated());
		if(!lockValidator.isValid() && lockValidator.isLocked()) {
			loginFailInfo = "登录帐号: [" + username + "] 被锁定！";
		}
		/*@Revision 20130417071300-liuch end */
		
		// 登录成功后,设置用户的相关属性

		if (subject.isAuthenticated()&& lockValidator.isValid()) {

			StringBuilder strBuilder = new StringBuilder();

			BiOneUser biOneUser = BiOneSecurityUtils.getCurrentUserInfo();
			BioneUserInfo user = userBS.getEntityById(biOneUser.getUserId());
			// 获取当前用户相关的信息
			biOneUser.setUserName(user.getUserName());
			// 设置逻辑系统标识为BIONE
			biOneUser.setCurrentLogicSysNo(logicsysno);
			strBuilder.append("欢迎您:" + user.getUserName());

			// 用户所属的机构和部门信息
			if (user.getOrgNo() != null) {
				BioneOrgInfo orgInfo = this.orgBS.findBasicOrgInfoByOrgNo(user
						.getOrgNo());
				if (orgInfo != null) {
					biOneUser.setOrgNo(orgInfo.getOrgNo());
					biOneUser.setOrgName(orgInfo.getOrgName());
				}
			}

			if (user.getDeptNo() != null) {
				BioneDeptInfo deptInfo = this.deptBS
						.findBasicDeptInfoByOrgNoandDeptNo(user.getOrgNo(),
								user.getDeptNo());
				if (deptInfo != null) {
					biOneUser.setDeptNo(deptInfo.getDeptNo());
					biOneUser.setDeptName(deptInfo.getDeptName());
				}
			}

			biOneUser.setAuthObjMap(this.authBS.findAuthObjUserRelMap(
					logicsysno, user.getUserId()));
			biOneUser.setSuperUser(this.authBS.findAdminUserInfo(
					user.getUserId(), logicsysno));
			logger.info("帐号[{}]登录验证通过.", username);
			logBS.addLog(loginIp, logicsysno, username, "帐号: " + username
					+ "登录验证通过,登陆成功! ");
			
			/*@Revision 20130415181900-liuch 
			 * 判断用户对该逻辑系统是否有操作权限，如果有则正常登陆，否则勒令退出系统 */
			int count = this.getPermissionMenuByLogicsys(biOneUser);
			if(count<=0) {
				loginFailInfo = "帐号["+biOneUser.getUserName()+"]对该系统没有操作权限，不允许登入.";
				logger.info("帐号[{}]对该系统没有任何操作权限，不允许登入.", username);
				logBS.addLog(loginIp, logicsysno, username, "帐号: " + username
						+ "对该系统没有任何操作权限，不允许登入! ");
				ModelAndView mav = new ModelAndView();
				mav.setViewName("login");
				mav.addObject("LOGIN_FAIL_INFO", loginFailInfo);
				mav.addObject("items", getLogicSysOption());
				mav.addObject("username", username);
				mav.addObject("password", password);
				mav.addObject("logicsysno", logicsysno);
				mav.addObject("isFullScreen", isFullScreen);
				return mav;
			}
			/*@Revision 20130415181900-liuch end*/
			
			/* @Revision 20130704182000 liucheng2@yuchengtech.com
			 * 登陆成功，向session插入标记  */
			request.getSession().setAttribute("onlineflag", "online");
			/* @Revision 20130704182000 END */
			
			if (isFullScreen) {
				openFullScreenWin(logicsysno, response);
				return null;
			} else {
				return new ModelAndView("redirect:/index");
			}
		} else {
			logger.info("帐号[{}]登录验证失败.", username);
			logBS.addLog(loginIp, logicsysno, username, "帐号: " + username
					+ "登录验证失败,登陆失败! ");
			ModelAndView mav = new ModelAndView();
			mav.setViewName("login");
			mav.addObject("LOGIN_FAIL_INFO", loginFailInfo);
			mav.addObject("items", getLogicSysOption());
			mav.addObject("username", username);
			mav.addObject("password", password);
			mav.addObject("logicsysno", logicsysno);
			mav.addObject("isFullScreen", isFullScreen);
			return mav;
		}

	}

	/**
	 * 在全屏窗口中打开首页
	 */
	private void openFullScreenWin(String logicsysno,
			HttpServletResponse response) {
		// 打开全屏窗口
		StringBuilder jsBuilder = new StringBuilder();

		jsBuilder.append("<script type=\"text/javascript\">");
		jsBuilder
				.append("var params = 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,';");
		jsBuilder.append("var newwin=window.open('"
				+ this.getRequest().getContextPath() + "/index?logicsysno="
				+ logicsysno + "','bione_index',params);\n");

		jsBuilder.append("if(!window.opener){");
		String agent = this.getRequest().getHeader("user-agent");
		if (agent.contains("MSIE 6.0")) {
			jsBuilder.append("window.opener=null;");
		} else {
			jsBuilder.append("window.open('','_self','');");
		}
		jsBuilder.append("setTimeout(\"window.close();\",1000);}");
		jsBuilder.append("</script>");

		this.renderHtml(jsBuilder.toString(), response);
	}

	/**
	 * 注销用户
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(String quit, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		// 清除逻辑系统缓存
		logger.info("用户[{}]于{}退出系统.",
				BiOneSecurityUtils.getCurrentUserInfo() == null ? ""
						: BiOneSecurityUtils.getCurrentUserInfo()
								.getLoginName(), ExDateUtils
						.getCurrentStringDateTime());
		String loginIp = this.getRequest().getRemoteAddr();
		// 清除逻辑系统缓存
		BiOneUser biOneUser = BiOneSecurityUtils.getCurrentUserInfo();
		if (biOneUser != null) {
			logBS.addLog(loginIp, biOneUser.getCurrentLogicSysNo(),
					biOneUser.getLoginName(), "用户: " + biOneUser.getUserName()
							+ "于" + ExDateUtils.getCurrentStringDateTime()
							+ "退出系统");
		}
		Subject subject = BiOneSecurityUtils.getSubject();

		subject.logout();
		getLogicSysOption();

		if ("true".equals(quit)) {// 注销并关闭窗口
			return null;

		} else {// 注销
			mav.setViewName("login");
			mav.addObject("items", getLogicSysOption());
			mav.addObject("isFullScreen", true);
			return mav;
		}
	}

	public List<BioneLogicSysInfo> getLogicSysOption() {
		List<BioneLogicSysInfo> logicSysList = LogicSysInfoHolder
				.getLogicSysInfo();
		return logicSysList;
	}

	/* 
	 * @Revision 20130415181900-liuch
	 * 查询用户在某逻辑系统下的可用菜单
	 * @param logicSysNo 逻辑系统编号
	 * @param userId 用户编号
	 * @return
	 */
	public int getPermissionMenuByLogicsys(BiOneUser userObj) {
		int count = 0;
		MenuBS menuBS = SpringContextHolder.getBean("menuBS");
		/*
		 * 查询用户有操作权限的菜单；
		 * 此处直接使用buildMenuTree这个方法，确实有些不妥；
		 * 但是由于buildMenuTree这个方法里面，没有把“查询用户可操作菜单”与“构建CommonTree”这两个操作进行分离，况且“查询用户可操作菜单”里面也包含了
		 * 大量的逻辑判断，所以这里直接就把buildMenuTree这个方法拿来用了，而不是仅仅借鉴里面的逻辑；
		 */
		List<CommonTreeNode> menuInfoList = menuBS.buildMenuTree(userObj.getCurrentLogicSysNo(), "0", false);
		if(menuInfoList!=null && !menuInfoList.isEmpty()) {
			count = menuInfoList.size();
		}
		return count;
	}
}