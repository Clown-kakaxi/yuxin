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
import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpInfo;
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
public class ObjGrpAuthObjImpl implements IAuthObject {

	private String icon = "images/classics/icons/couple.png";

	@Autowired
	private AuthBS authBS;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#getAuthObjDefNo()
	 */
	
	public String getAuthObjDefNo() {

		return AUTH_OBJ_DEF_ID_GROUP;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#doGetAuthObjectInfo()
	 */
	
	public List<CommonTreeNode> doGetAuthObjectInfo() {
		List<CommonTreeNode> nodes = new ArrayList<CommonTreeNode>();
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		List<BioneAuthObjgrpInfo> objList = this.authBS
				.findValidAuthObjGrpOfUser(userObj.getCurrentLogicSysNo());
		if (objList == null) {
			return nodes;
		}
		for (int i = 0; i < objList.size(); i++) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("objDefNo", AUTH_OBJ_DEF_ID_GROUP);
			CommonTreeNode node = new CommonTreeNode();
			BioneAuthObjgrpInfo obj = objList.get(i);
			node.setId(obj.getObjgrpId());
			node.setText(obj.getObjgrpName());
			node.setData(obj);
			paramMap.put("id", obj.getObjgrpId());
			paramMap.put("realNo", obj.getObjgrpNo());
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
		List<String> authObjIdList = this.authBS.findValidAuthObjGrpIdOfUser(
				userObj.getCurrentLogicSysNo(), this.getAuthObjDefNo(),
				userObj.getUserId());

		return authObjIdList;
	}

}
