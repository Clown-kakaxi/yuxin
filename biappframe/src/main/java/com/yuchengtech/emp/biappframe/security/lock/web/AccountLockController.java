package com.yuchengtech.emp.biappframe.security.lock.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.lock.entity.BioneAccountLockInfo;
import com.yuchengtech.emp.biappframe.security.lock.service.IAccountLockService;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserBS;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title: 超级管理员强制解锁
 * Description: 超级管理员强制解锁
 * </pre>
 * 
 * @author liucheng liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/acclock")
public class AccountLockController extends BaseController {

	@Autowired
	private UserBS userBS;
	
	private IAccountLockService lockBS = SpringContextHolder.getBean("accountLockCacheBS");
	
	@RequestMapping(value = "/{id}/unlock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unlock(@PathVariable("id") String id) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (!BiOneSecurityUtils.getCurrentUserInfo().isSuperUser()) {
			retMap.put("msg", "非超级管理员不能使用解锁功能");
			return retMap;
		}
		try {
			// lockBS.clear(lockBS.get(userBS.getEntityById(id).getUserNo()));
			BioneUserInfo user = userBS.getEntityById(id);
			String userNo = user.getUserNo();
			BioneAccountLockInfo lockinfo = lockBS.get(userNo);
			if (lockinfo != null) {
				lockBS.clear(lockinfo);
			}
			retMap.put("msg", "S");
		} catch (Exception ex) {
			retMap.put("msg", "F");
		}
		return retMap;
	}
	
	
}
