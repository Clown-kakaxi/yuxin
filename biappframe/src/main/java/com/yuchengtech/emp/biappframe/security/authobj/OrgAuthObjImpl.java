/**
 * 
 */
package com.yuchengtech.emp.biappframe.security.authobj;

import static com.yuchengtech.emp.biappframe.base.common.GlobalConstants.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;

/**
 * <pre>
 * Title:机构授权对象实现类
 * Description: 授权对象接口的机构实现
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
@Component
public class OrgAuthObjImpl implements IAuthObject {
	
	private String icon = "images/classics/icons/organ.gif";

	@Autowired
	private AuthBS authBS;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#getAuthObjDefNo()
	 */
	
	public String getAuthObjDefNo() {

		return GlobalConstants.AUTH_OBJ_DEF_ID_ORG;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#doGetAuthObjectInfo()
	 */
	
	public List<CommonTreeNode> doGetAuthObjectInfo() {
		List<CommonTreeNode> nodes = new ArrayList<CommonTreeNode>();
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		BioneLogicSysInfo logicInfo = this.authBS.getEntityByProperty(BioneLogicSysInfo.class, "logicSysNo",userObj.getCurrentLogicSysNo());
		String logicNo_org = userObj.getCurrentLogicSysNo();
		if(logicInfo != null && COMMON_STATUS_VALID.equals(logicInfo.getBasicOrgSts())){
			//若该系统使用基线机构
			logicNo_org = SUPER_LOGIC_SYSTEM;
		}
		List<BioneOrgInfo> orgInfoList = this.authBS
				.findValidAuthOrgOfUser(logicNo_org);
		if (orgInfoList == null) {
			return nodes;
		}
		for (int i = 0; i < orgInfoList.size(); i++) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("objDefNo", GlobalConstants.AUTH_OBJ_DEF_ID_ORG);
			BioneOrgInfo org = orgInfoList.get(i);
			CommonTreeNode node = new CommonTreeNode();
			node.setId(org.getOrgId());
			node.setText(org.getOrgName());
			node.setUpId(org.getUpNo());
			paramMap.put("id", org.getOrgId());
			paramMap.put("realNo", org.getOrgNo());
			node.setParams(paramMap);
			node.setData(org);
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
		List<String> authObjIdList = this.authBS.findValidAuthOrgIdOfUser(
				userObj.getCurrentLogicSysNo(), this.getAuthObjDefNo(),
				userObj.getUserId());

		return authObjIdList;
	}

}
