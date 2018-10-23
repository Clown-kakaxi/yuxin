package com.yuchengtech.emp.biappframe.security.realm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRel;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthResDef;
import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.security.BiOneAuthorizationInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IResObject;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.service.UserBS;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * Title: 自定义Realm类,将来实现新的认证授权接口时，参考此实现
 * Description: 自定义实现shiro框架的Realm功能，通过访问数据库获取用户密码信息和用户授权许可信息
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:1.01     修改人：songxf  修改日期:2012-07-05     修改内容:增加逻辑系统支持
 * </pre>
 */
@Component
public class DefaultAuthorizingRealm extends AuthorizingRealm {

	protected UserBS userBS = null;
	protected AuthBS authBS = null;

	@Autowired
	public void setUserBS(UserBS userBS) {
		this.userBS = userBS;
	}

	@Autowired
	public void setAuthBS(AuthBS authBS) {
		this.authBS = authBS;
	}

	public DefaultAuthorizingRealm() {

		setName("DefaultAuthorizingRealm");

		// 设置密码加密算法
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		matcher.setStoredCredentialsHexEncoded(false);// base64 encoded
		setCredentialsMatcher(matcher);
	}

	/**
	 * 认证回调函数,获取用户认证信息(用户名，密码)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SimpleAuthenticationInfo authInfo = null;

		if (StringUtils.isBlank(token.getUsername())) {

			throw new UnknownAccountException("请输入登录帐号和密码!");
		}

		BioneUserInfo user = userBS.findUniqueEntityByProperty("userNo",
				token.getUsername());

		if (user == null) {

			throw new UnknownAccountException("帐号[" + token.getUsername()
					+ "]在系统中不存在!");
		} else {

			if (GlobalConstants.COMMON_STATUS_INVALID.equals(user.getUserSts())) {

				throw new LockedAccountException("帐号[" + token.getUsername()
						+ "]已经被锁定,请联系管理员解锁!");
			}

			BiOneUser biOneUser = new BiOneUser();
			biOneUser.setUserId(user.getUserId());
			biOneUser.setLoginName(token.getUsername());

			authInfo = new SimpleAuthenticationInfo(biOneUser,
					user.getUserPwd(), getName());

			// 设置密码Hash散列的salt值，加强安全性，增加字典攻击的难度
			// 目前salt值使用的是固定值，可以每个用户独立绑定一个salt值
			authInfo.setCredentialsSalt(ByteSource.Util
					.bytes(GlobalConstants.CREDENTIALS_SALT));

		}

		return authInfo;
	}

	/**
	 * 获取当前用户的授权信息
	 */
	@SuppressWarnings("unused")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		SimpleAuthorizationInfo authzInfo = new BiOneAuthorizationInfo();
		BiOneUser currentUser = BiOneSecurityUtils.getCurrentUserInfo();
		String logicSysNo = currentUser.getCurrentLogicSysNo();
		if (currentUser != null) {

			String userId = currentUser.getUserId();

			Set<String> allPermissions = Sets.newHashSet();
			List<BioneAuthObjResRel> authObjResRelList = Lists.newArrayList();

			// 查询通过用户授权的资源列表
			List<String> objIdList = new ArrayList<String>();
			objIdList.add(userId);
			List<BioneAuthObjResRel> userResRelList = this.authBS
					.findCurrentUserAuthObjResRels(logicSysNo,
							GlobalConstants.AUTH_OBJ_DEF_ID_USER, objIdList);

			if (userResRelList != null)
				authObjResRelList.addAll(userResRelList);

			// 获取授权组权限
			List<String> authGrpIdList = this.authBS
					.findAuthGroupIdOfCurrentUser(logicSysNo);

			List<BioneAuthObjResRel> grpResRelList = this.authBS
					.findCurrentUserAuthObjResRels(logicSysNo,
							GlobalConstants.AUTH_OBJ_DEF_ID_GROUP,
							authGrpIdList);

			if (grpResRelList != null)
				authObjResRelList.addAll(grpResRelList);

			// 通过其他授权对象获取授权资源
			Map<String, List<String>> userAuthObjMap = currentUser
					.getAuthObjMap();
			Iterator<String> it = userAuthObjMap.keySet().iterator();

			String objDefNo = null;
			List<String> authObjIdList = null;
			while (it.hasNext()) {

				objDefNo = it.next();
				authObjIdList = userAuthObjMap.get(objDefNo);

				if (authObjIdList != null) {
					List<BioneAuthObjResRel> objDefResRelList = this.authBS
							.findCurrentUserAuthObjResRels(logicSysNo,
									objDefNo, authObjIdList);
					if (objDefResRelList != null)
						authObjResRelList.addAll(objDefResRelList);
				}
			}

			// 将用户所拥有权限的资源按照资源类型分组
			Map<String, List<BioneAuthObjResRel>> resDefGroupMap = Maps
					.newHashMap();

			String resDefNo = null;
			List<BioneAuthObjResRel> authObjResList = null;

			for (BioneAuthObjResRel authObjResRel : authObjResRelList) {

				resDefNo = authObjResRel.getId().getResDefNo();
				authObjResList = resDefGroupMap.get(resDefNo);

				if (authObjResList == null) {

					authObjResList = Lists.newArrayList();
					resDefGroupMap.put(resDefNo, authObjResList);
				}

				authObjResList.add(authObjResRel);
			}

			Iterator<String> resDefIt = resDefGroupMap.keySet().iterator();

			while (resDefIt.hasNext()) {

				resDefNo = resDefIt.next();
				authObjResList = resDefGroupMap.get(resDefNo);

				BioneAuthResDef adminAuthResDef = this.authBS
						.getEntityByProperty(BioneAuthResDef.class, "resDefNo",
								resDefNo);
				if (adminAuthResDef != null) {
					try{
						IResObject reObj = (IResObject) SpringContextHolder
							.getBean(adminAuthResDef.getBeanName());
						List<String> resSermissions = reObj
								.doGetResPermissions(authObjResList);
						
						if (resSermissions != null)
							allPermissions.addAll(resSermissions);
					}catch(org.springframework.beans.factory.NoSuchBeanDefinitionException e){
						//跳过不存在的已定义授权资源bean
						continue;
					}
				}

			}

			authzInfo.setStringPermissions(allPermissions);

			return authzInfo;
		}
		return null;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}
