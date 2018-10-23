package com.yuchengtech.emp.biappframe.security.authobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

import static com.yuchengtech.emp.biappframe.base.common.GlobalConstants.*;


/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author caiqy  caiqy@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Component
public class UserAuthObjImpl implements IAuthObject {
	
	private String icon = "images/classics/icons/user.gif";
	
	@Autowired
	private AuthBS authBS;

	/* (non-Javadoc)
	 * @see com.ytec.bione.security.IAuthObject#getAuthObjDefNo()
	 */
	
	public String getAuthObjDefNo() {
		
		return AUTH_OBJ_DEF_ID_USER;
		
	}

	/* (non-Javadoc)
	 * @see com.ytec.bione.security.IAuthObject#doGetAuthObjectInfo()
	 */
	
	public List<CommonTreeNode> doGetAuthObjectInfo() {
		List<CommonTreeNode> nodes = new ArrayList<CommonTreeNode>();
		List<BioneUserInfo> users = this.authBS.findValidAuthUserObjOfUser();
		if(users == null){
			return nodes;
		}
		for(int i = 0 ; i < users.size() ; i++){
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("objDefNo", AUTH_OBJ_DEF_ID_USER);
			CommonTreeNode node = new CommonTreeNode();
			BioneUserInfo user = users.get(i);
			node.setId(user.getUserId());
			node.setText(user.getUserName());
			node.setData(user);
			paramMap.put("id", user.getUserId());
			paramMap.put("realNo", user.getUserNo());
			node.setParams(paramMap);
			node.setIcon(icon);
			
			nodes.add(node);
		}
		return nodes;
	}

	/* (non-Javadoc)
	 * @see com.ytec.bione.security.IAuthObject#doGetAuthObjectIdListOfUser()
	 */
	
	public List<String> doGetAuthObjectIdListOfUser() {
		List<String> authUserIdList = this.authBS.findValidAuthUserIdOfUser();

		return authUserIdList;
	}

}
