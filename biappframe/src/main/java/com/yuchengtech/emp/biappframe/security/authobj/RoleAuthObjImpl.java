/**
 * 
 */
package com.yuchengtech.emp.biappframe.security.authobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneRoleInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

import static com.yuchengtech.emp.biappframe.base.common.GlobalConstants.*;


/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Component
public class RoleAuthObjImpl implements IAuthObject {

	private String icon = "images/classics/icons/role.gif";

	@Autowired
	private AuthBS authBS;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#getAuthObjDefNo()
	 */
	
	public String getAuthObjDefNo() {

		return AUTH_OBJ_DEF_ID_ROLE;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#doGetAuthObjectInfo()
	 */
	
	public List<CommonTreeNode> doGetAuthObjectInfo() {
		List<CommonTreeNode> nodes = new ArrayList<CommonTreeNode>();
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		List<BioneRoleInfo> roleList = this.authBS
				.findValidAuthRoleOfUser(userObj.getCurrentLogicSysNo());
		if (roleList == null) {
			return nodes;
		}
		for (int i = 0; i < roleList.size(); i++) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("objDefNo", AUTH_OBJ_DEF_ID_ROLE);
			CommonTreeNode node = new CommonTreeNode();
			BioneRoleInfo role = roleList.get(i);
			node.setId(role.getRoleId());
			node.setText(role.getRoleName());
			node.setData(role);
			paramMap.put("realNo", role.getRoleNo());
			paramMap.put("id", role.getRoleId());
			node.setParams(paramMap);
			node.setIcon(icon);

			nodes.add(node);
		}
		return nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ytec.bione.security.IAuthObject#doGetAuthObjectIdListOfUser()
	 */
	
	public List<String> doGetAuthObjectIdListOfUser() {
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		List<String> authObjIdList = this.authBS.findValidAuthRoleIdOfUser(
				userObj.getCurrentLogicSysNo(), this.getAuthObjDefNo(),
				userObj.getUserId());

		return authObjIdList;
	}

}
