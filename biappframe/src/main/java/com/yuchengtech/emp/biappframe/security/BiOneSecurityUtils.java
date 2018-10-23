/**
 * 
 */
package com.yuchengtech.emp.biappframe.security;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ByteSource;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:安全认证的工具类
 * Description: 扩展shiro框架的SecurityUtils类
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
public class BiOneSecurityUtils extends SecurityUtils {

	/**
	 * 返回当前用户的信息
	 * 
	 * @return
	 */
	public static BiOneUser getCurrentUserInfo() {
		return (BiOneUser) getSubject().getPrincipal();
	}

	/**
	 * 获取当前用户的用户ID
	 * 
	 * @return
	 */
	public static String getCurrentUserId() {

		return getCurrentUserInfo().getUserId();
	}

	/**
	 * 获取用户有权限访问的某类资源的集合
	 * 
	 * @param resDefNo
	 *            资源标识
	 * @return
	 */
	public static List<String> getResIdListOfUser(String resDefNo) {

		List<String> resIdList = Lists.newArrayList();

		AuthBS authBS = SpringContextHolder.getBean("authBS");
		String logicSysNo = getCurrentUserInfo().getCurrentLogicSysNo();
		// 查找用户具有的权限
		List<String> objIdList = Lists.newArrayList();
		objIdList.add(getCurrentUserId());
		List<String> resIdListOfUser = authBS.findAuthResIdListByType(logicSysNo, resDefNo,
				GlobalConstants.AUTH_OBJ_DEF_ID_USER, objIdList);

		if (resIdListOfUser != null)
			resIdList.addAll(resIdListOfUser);

		// 查找当前用户是否属于某个授权组
		List<String> authGrpIdList = authBS.findAuthGroupIdOfCurrentUser(logicSysNo);

		List<String> resIdListOfGrp = authBS.findAuthResIdListByType(logicSysNo, resDefNo,
				GlobalConstants.AUTH_OBJ_DEF_ID_GROUP, authGrpIdList);

		if (resIdListOfGrp != null)
			resIdList.addAll(resIdListOfGrp);

		// 当前用户授权对象的集合
		Map<String, List<String>> userAuthObjMap = getCurrentUserInfo().getAuthObjMap();
		Iterator<String> it = userAuthObjMap.keySet().iterator();

		String objDefNo = null;
		List<String> authObjIdList = null;
		while (it.hasNext()) {

			objDefNo = it.next();
			authObjIdList = userAuthObjMap.get(objDefNo);

			if (authObjIdList != null) {
				// 授权对象所拥有的资源访问集合
				List<String> resIdOfAuthObj = authBS.findAuthResIdListByType(logicSysNo, resDefNo, objDefNo,
						authObjIdList);
				if (resIdOfAuthObj != null)
					resIdList.addAll(resIdOfAuthObj);
			}
		}

		return resIdList;

	}

	/**
	 * 获得加密后的密码字符串
	 * 
	 * @param password
	 *            密码
	 * @return String
	 *            加密后的密码
	 */
	public static String getHashedPasswordBase64(String password) {
		return new org.apache.shiro.crypto.hash.Sha256Hash(password,
				ByteSource.Util.bytes(GlobalConstants.CREDENTIALS_SALT)).toBase64();
	}

}
