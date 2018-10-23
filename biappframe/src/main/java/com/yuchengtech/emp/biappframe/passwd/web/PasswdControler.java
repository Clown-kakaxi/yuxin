package com.yuchengtech.emp.biappframe.passwd.web;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.passwd.entity.BionePwdSecurityInfo;
import com.yuchengtech.emp.biappframe.passwd.service.PwdSecurityBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserBS;


@Controller
@RequestMapping("/bione/admin/passwd")
public class PasswdControler extends BaseController {

	
	@Autowired
	private PwdSecurityBS pwdBS ;
	
	@Autowired
	private UserBS userBS ;
	
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/passwd/passwd-security-index", "id", id);
	}
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BionePwdSecurityInfo show(@PathVariable("id") String id) {
		BionePwdSecurityInfo model = this.pwdBS.getEntityById(id);
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void create(BionePwdSecurityInfo model) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		model.setCreateTime(now);
		model.setLastUpdateTime(now);
		this.pwdBS.updateEntity(model);
	}
	
	@RequestMapping(value = "/getOverdue.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOverdue() {
		Map<String, Object> result = Maps.newHashMap();
		Boolean state = Boolean.TRUE;
		//
		BiOneUser biOneUser = BiOneSecurityUtils.getCurrentUserInfo();
		BioneUserInfo user = userBS.getEntityById(biOneUser.getUserId());
		BionePwdSecurityInfo pwdSequrity = pwdBS.getEntityById(GlobalConstants.BIONE_PWD_SECURITY_INFO_ID);
		if(pwdSequrity==null || !pwdSequrity.getUsePwdSecurity().equals(GlobalConstants.COMMON_YES)) {
			result.put("data", GlobalConstants.COMMON_NO);
		}
		else {
			try {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				Timestamp lastPwdUpdateTime = user.getLastPwdUpdateTime();
				/* @Revision 20130423150800-liuch 当lastPwdUpdateTime与lastUpdateTime同时为空时，
				 * 把这两个字段设置为当前时间，并更新到数据库。 */
				if(lastPwdUpdateTime==null) {
					if (user.getLastUpdateTime()!=null) {
						lastPwdUpdateTime = user.getLastUpdateTime();
					} else {
						lastPwdUpdateTime = now; // 设置为当前时间
						user.setLastUpdateTime(now);
					}
					user.setLastPwdUpdateTime(lastPwdUpdateTime);
					userBS.updateEntity(user);
				}
				long differDay = (now.getTime() / 1000 - lastPwdUpdateTime.getTime() / 1000) / (24 * 60 * 60); // 相差天数
				long stdUseTime = pwdSequrity.getPwdUseTime().longValue() * GlobalConstants.COMMON_MONTH_DAYS; // 按30天计算
				if ((differDay - stdUseTime) >= 0) {
					result.put("data", GlobalConstants.COMMON_YES);
				} else {
					result.put("data", GlobalConstants.COMMON_NO);
				}
			} 
			catch (Exception e) {
				state = Boolean.FALSE;
				logger.error("获取用户密码已使用过的时间失败.", e);
				result.put("msg", "获取用户密码已使用过的时间失败");
			}
		}
		result.put("success", state + "");
		return result;
	}

	
	@RequestMapping(value = "/getPwdComplex.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPwdComplex() {
		Map<String, Object> result = Maps.newHashMap();
		Boolean state = Boolean.TRUE;
		try {
			BionePwdSecurityInfo pwdSecurity = (BionePwdSecurityInfo) pwdBS.getEntityById(GlobalConstants.BIONE_PWD_SECURITY_INFO_ID);
			if(pwdSecurity!=null) {
				result.put("userPwdSecurity", pwdSecurity.getUsePwdSecurity());
				result.put("pwdCpx", pwdSecurity.getPwdComplex());
				result.put("maxLength", pwdSecurity.getPwdMaxLength());
				result.put("mixLength", pwdSecurity.getPwdMixLength());
			} else {
				result.put("userPwdSecurity", GlobalConstants.COMMON_NO);
			}
		} 
		catch (Exception e) {
			state = Boolean.FALSE;
			logger.error("获取用户密码已使用过的时间失败.", e);
			result.put("msg", "获取用户密码已使用过的时间失败");
		}
		result.put("success", state + "");
		return result;
	}
	
	@RequestMapping(value = "/isDefault", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> isDefault() throws Exception {
		Map<String, Object> result = Maps.newHashMap();
		boolean state = false;
		
		BioneUserInfo user = this.userBS.getEntityById(BiOneSecurityUtils.getCurrentUserInfo().getUserId());
		String passwd = user.getUserPwd();
		String cyDeft = BiOneSecurityUtils.getHashedPasswordBase64(GlobalConstants.DEFAULT_PASSWD); // default-passwd
		if (passwd.equals(cyDeft)) {
			state = true;
		}
		
		result.put("success", state);
		return result;
	}
	
}
