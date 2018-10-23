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
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

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
public class DeptAuthObjImpl implements IAuthObject {

	private String icon_org = "images/classics/icons/organ.gif";
	private String icon_dept = "images/classics/icons/cur_activity_none.gif";

	@Autowired
	private AuthBS authBS;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ytec.bione.security.IAuthObject#getAuthObjDefNo()
	 */
	
	public String getAuthObjDefNo() {

		return AUTH_OBJ_DEF_ID_DEPT;

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
		//获取机构信息
		List<BioneOrgInfo> orgInfoList = this.authBS
				.findValidAuthOrgOfUser(logicNo_org);
		if (orgInfoList == null) {
			return nodes;
		}
		for (int i = 0; i < orgInfoList.size(); i++) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("objDefNo", AUTH_OBJ_DEF_ID_ORG);
			CommonTreeNode node = new CommonTreeNode();
			BioneOrgInfo obj = orgInfoList.get(i);
			node.setId(AUTH_OBJ_DEF_ID_ORG+"_"+obj.getOrgNo());
			node.setText(obj.getOrgName());
			if(CommonTreeNode.ROOT_ID.equals(obj.getUpNo())){
				node.setUpId(obj.getUpNo());
			}else{
				node.setUpId(AUTH_OBJ_DEF_ID_ORG+"_"+obj.getUpNo());
			}
			node.setData(obj);
			paramMap.put("id", obj.getOrgId());
			paramMap.put("realId", obj.getOrgNo());
			//有这个属性的节点不允许点击
			paramMap.put("cantClick", "1");
			node.setParams(paramMap);
			node.setIcon(icon_org);

			nodes.add(node);
		}
		//获取部门信息
		String logicNo_dep = userObj.getCurrentLogicSysNo();
		if(logicInfo != null && COMMON_STATUS_VALID.equals(logicInfo.getBasicDeptSts())){
			//若该系统使用基线部门
			logicNo_dep = SUPER_LOGIC_SYSTEM;
		}
		List<BioneDeptInfo> deptList = this.authBS
				.findValidAuthDeptOfUser(logicNo_dep);
		if(deptList == null){
			return nodes;
		}
		for(int j = 0 ; j < deptList.size() ; j ++){
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("objDefNo", AUTH_OBJ_DEF_ID_DEPT);
			CommonTreeNode node = new CommonTreeNode();
			BioneDeptInfo dept = deptList.get(j);
			node.setId(dept.getDeptNo());
			node.setText(dept.getDeptName());
			if(dept.getUpNo() == CommonTreeNode.ROOT_ID || CommonTreeNode.ROOT_ID.equals(dept.getUpNo())){				
				//若是最上层机构
				node.setUpId(AUTH_OBJ_DEF_ID_ORG+"_"+dept.getOrgNo());
			}else{
				node.setUpId(dept.getUpNo());
			}
			node.setData(dept);
			paramMap.put("id", dept.getDeptId());
			paramMap.put("realNo", dept.getDeptNo());
			node.setParams(paramMap);
			node.setIcon(icon_dept);
			
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
		List<String> authObjIdList = this.authBS.findValidAuthDeptIdOfUser(
				userObj.getCurrentLogicSysNo(), this.getAuthObjDefNo(),
				userObj.getUserId());

		return authObjIdList;
	}

}
