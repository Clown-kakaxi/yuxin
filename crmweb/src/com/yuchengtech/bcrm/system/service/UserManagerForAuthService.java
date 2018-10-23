package com.yuchengtech.bcrm.system.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bcrm.system.model.AdminAuthAccountRole;
import com.yuchengtech.bcrm.system.model.AdminAuthAccountRoleDel;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 用户管理Service
 * 
 * @author wangwan
 * @since 2012-10-08
 */
@Service
public class UserManagerForAuthService extends CommonService {

	public UserManagerForAuthService() {
		JPABaseDAO<AdminAuthAccountRole, Long> baseDAO = new JPABaseDAO<AdminAuthAccountRole, Long>(
				AdminAuthAccountRole.class);
		super.setBaseDAO(baseDAO);
	}

	/**
	 * 保存用户授权角色信息
	 * 
	 * @param jarray
	 * @return
	 */
	public void save(JSONArray jarray) {
		// add by liuming 20170217
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (jarray.size() > 0) {
			for (int i = 0; i < jarray.size(); ++i) {
				JSONObject wa = (JSONObject) jarray.get(i);
				AdminAuthAccountRole ws = new AdminAuthAccountRole();
				ws.setAccountId(Long.valueOf((String) wa.get("accountId")));
				ws.setRoleId(Long.valueOf((String) wa.get("roleId")));
				ws.setAppId((String) wa.get("appId"));
				// add by liuming 20170217
				ws.setCreateUser(auth.getUserId());
				ws.setCreateTm(new Date());
				ws.setUpdateUser(auth.getUserId());
				ws.setUpdateTm(new Date());

				this.em.persist(ws);
			}
		}
	}

	/**
	 * 删除用户授权角色信息
	 * 
	 * @param jarray2
	 * @return
	 */
	public void remove(JSONArray jarray2) {
		// add by liuming 20170217
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (jarray2.size() > 0) {
			for (int i = 0; i < jarray2.size(); ++i) {

				JSONObject wb = (JSONObject) jarray2.get(i);
				String t = (String) wb.get("id");
				AdminAuthAccountRole ws2 = (AdminAuthAccountRole) this.em.find(
						AdminAuthAccountRole.class, Long.valueOf(t));
				if (ws2 != null) {
					// add by liuming 20170217
					AdminAuthAccountRoleDel del = new AdminAuthAccountRoleDel();
					del.setAccountId(ws2.getAccountId());
					del.setAppId(ws2.getAppId());
					del.setId(ws2.getId());
					del.setRoleId(ws2.getRoleId());
					del.setCreateTm(ws2.getCreateTm());
					del.setCreateUser(ws2.getCreateUser());
					del.setUpdateUser(ws2.getUpdateUser());
					del.setUpdateTm(ws2.getUpdateTm());
					del.setDelTm(new Date());
					del.setDelUser(auth.getUserId());
					
					this.em.remove(ws2);
					this.em.persist(del);
				}
			}
		}
	}

}
