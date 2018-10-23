package com.yuchengtech.emp.biappframe.authobj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.authobj.entity.BioneRoleInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title:角色管理的业务逻辑类
 * Description: 提供角色管理相关业务逻辑处理功能，提供事务控制
 * </pre>
 * 
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("roleBS")
@Transactional(readOnly = true)
public class RoleBS extends BaseBS<BioneRoleInfo> {

	protected static Logger log = LoggerFactory.getLogger(RoleBS.class);

	/**
	 * 分页查询角色信息
	 * 
	 * @param firstResult
	 *            第一条记录
	 * @param pageSize
	 *            每页记录数
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序方式
	 * @param conditionMap
	 *            参数列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneRoleInfo> getRoleList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		String jql = "select role from BioneRoleInfo role WHERE role.logicSysNo=:logicSysNo ";

		if (!conditionMap.get("jql").equals("")) {
			jql += " and " + conditionMap.get("jql") + " ";
		}

		if (!StringUtils.isEmpty(orderBy)) {
			jql += "order by role." + orderBy + " " + orderType;
		}

		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());

		SearchResult<BioneRoleInfo> roleList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql, values);
		return roleList;
	}

	public boolean checkIsRoleNoExist(String roleNo) {
		boolean flag = true;
		String jql = "select role FROM BioneRoleInfo role where roleNo=?0 AND logicSysNo=?1";
		List<BioneRoleInfo> list = this.baseDAO.findWithIndexParam(jql, roleNo,
				BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		if (list != null && list.size() > 0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 删除指定id的角色
	 * 
	 * @param ids
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteRolesByIds(String[] ids) {
		if (ids != null) {
			List<String> idList = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			if (idList.size() > 0) {
				String jql = "delete from BioneRoleInfo role where role.roleId in (?0)";
				this.baseDAO.batchExecuteWithIndexParam(jql, idList);
			}
		}
	}

}
