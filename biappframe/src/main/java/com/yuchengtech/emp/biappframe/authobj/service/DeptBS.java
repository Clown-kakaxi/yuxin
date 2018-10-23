package com.yuchengtech.emp.biappframe.authobj.service;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
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
@Service("deptBS")
@Transactional(readOnly = true)
public class DeptBS extends BaseBS<BioneDeptInfo> {

	/**
	 * 
	 * @return 部门树List
	 */
	public List<CommonTreeNode> buldDeptTree(String orgNo) {
		List<CommonTreeNode> orgTree = Lists.newArrayList();
		// 获取所有部门信息,用于通过机构Id，查找对应机构下属部门。
		String jql = "SELECT dept FROM BioneDeptInfo dept WHERE dept.logicSysNo=?0 AND dept.orgNo=?1";
		List<BioneDeptInfo> deptInfoList = this.baseDAO.findWithIndexParam(jql,
				BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(),
				orgNo);

		// 将部门信息处理成 上级部门标识-->部门信息 格式
		Map<String, List<BioneDeptInfo>> upDeptIdMap = Maps.newHashMap();
		if (deptInfoList != null) {
			List<BioneDeptInfo> oneLevelDeptInfoList = null;
			for (BioneDeptInfo deptInfo : deptInfoList) {
				oneLevelDeptInfoList = upDeptIdMap.get(deptInfo.getUpNo());
				if (oneLevelDeptInfoList == null) {
					oneLevelDeptInfoList = Lists.newArrayList();
					upDeptIdMap.put(deptInfo.getUpNo(), oneLevelDeptInfoList);
				}
				oneLevelDeptInfoList.add(deptInfo);
			}
		}

		// 上级部门标识的commonTreeNode.Root_id的为根节点
		List<BioneDeptInfo> rootDeptInfoList = upDeptIdMap.get(new String(
				CommonTreeNode.ROOT_ID));
		if (rootDeptInfoList != null) {
			for (BioneDeptInfo deptInfo : rootDeptInfoList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(deptInfo.getDeptNo());
				treeNode.setText(deptInfo.getDeptName());
				treeNode.setUpId(deptInfo.getUpNo());
				treeNode.setData(deptInfo);
				orgTree.add(treeNode);
				buildDeptChildrenTree(treeNode, upDeptIdMap);
			}
		}
		return orgTree;
	}

	/**
	 * 通过上级部门编号--绑定在上级节点中 ,循环查找并构造下级部门树 列表信息
	 * 
	 * @param treeNode
	 *            父节点
	 * @param upDeptIdMap
	 *            父标识--》子节点列表
	 */
	private void buildDeptChildrenTree(CommonTreeNode parentNode,
			Map<String, List<BioneDeptInfo>> upDeptIdMap) {
		List<BioneDeptInfo> deptInfoList = upDeptIdMap.get(new String(
				parentNode.getId()));
		if (deptInfoList != null) {
			for (BioneDeptInfo deptInfo : deptInfoList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(deptInfo.getDeptNo());
				treeNode.setText(deptInfo.getDeptName());
				treeNode.setUpId(deptInfo.getUpNo());
				treeNode.setData(deptInfo);
				parentNode.addChildNode(treeNode);
				buildDeptChildrenTree(treeNode, upDeptIdMap);
			}
		}
	}

	/**
	 * 通过部门标识查找部门信息
	 * 
	 * @param orgNo
	 * @param deptNo
	 * @return
	 */
	public BioneDeptInfo findDeptInfoByOrgNoandDeptNo(String orgNo,
			String deptNo) {
		String sysNo = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		BioneLogicSysInfo sys = this.getEntityByProperty(
				BioneLogicSysInfo.class, "logicSysNo", sysNo);
		if (sys != null) {
			String sysNoParam = GlobalConstants.SUPER_LOGIC_SYSTEM;
			if(GlobalConstants.COMMON_STATUS_INVALID.equals(sys.getBasicDeptSts())){
				//若该逻辑系统不是使用基线部门
				sysNoParam = sysNo;
			}
			String jql = "SELECT dept FROM BioneDeptInfo dept where  dept.orgNo=?0 and dept.deptNo=?1 and dept.logicSysNo=?2";
			List<BioneDeptInfo> depts = this.baseDAO.findWithIndexParam(jql,
					orgNo, deptNo, sysNoParam);
			if(depts != null && depts.size() > 0){
				return depts.get(0);
			}else{				
				return null;
			}
		}
		return null;
	}
	/**
	 * 通过部门标识查找部门信息
	 * 
	 * @param orgNo
	 * @param deptNo
	 * @return
	 */
	public BioneDeptInfo findBasicDeptInfoByOrgNoandDeptNo(String orgNo,
			String deptNo) {
		String sysNo = GlobalConstants.SUPER_LOGIC_SYSTEM;
		BioneLogicSysInfo sys = this.getEntityByProperty(
				BioneLogicSysInfo.class, "logicSysNo", sysNo);
		if (sys != null) {
			String sysNoParam = GlobalConstants.SUPER_LOGIC_SYSTEM;
			if(GlobalConstants.COMMON_STATUS_INVALID.equals(sys.getBasicDeptSts())){
				//若该逻辑系统不是使用基线部门
				sysNoParam = sysNo;
			}
			String jql = "SELECT dept FROM BioneDeptInfo dept where  dept.orgNo=?0 and dept.deptNo=?1 and dept.logicSysNo=?2";
			BioneDeptInfo dept=null;
			try{
				dept = this.baseDAO.findUniqueWithIndexParam(jql,
					orgNo, deptNo, sysNoParam);
			}catch(NoResultException e){
				return null;
			}
			return dept;
		}
		return null;
	}
	/**
	 * 通过机构编号，查询所有当前机构下的部门信息
	 * 
	 * @param orderBy
	 * @param orderType
	 * @param orgNo
	 * @return
	 */
	public List<BioneDeptInfo> findDeptInfoByOrg(String orderBy, String orderType, String orgNo) {
		String jql = "SELECT dept FROM BioneDeptInfo dept WHERE dept.orgNo=:orgNo AND dept.logicSysNo=:logicSysNo";

		if (!StringUtils.isEmpty(orderBy)) {
			jql += " order by dept." + orderBy + " " + orderType;
		}
		Map<String, Object> paramMap = Maps.newHashMap();

		paramMap.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());
		paramMap.put("orgNo", orgNo);

		List<BioneDeptInfo> deptList = this.baseDAO.findWithNameParm(jql,
				paramMap);
		return deptList;
	}

	/**
	 * 查询某机构下所有部门信息
	 * 
	 * @param orgNo
	 * 			机构标识
	 * @param isBasicDept
	 * 			是否启用基线部门
	 * @return
	 */
	public Map<String, BioneDeptInfo> findAllDeptInfoWithOrg(String orgNo, String isBasicDept) {
		StringBuffer jql = new StringBuffer("");
		
		jql.append("select dept from BioneDeptInfo dept where dept.logicSysNo = :logicSysNo");
		
		Map<String, Object> values = Maps.newHashMap();
		
		if (GlobalConstants.COMMON_STATUS_VALID.equals(isBasicDept)) {
			values.put("logicSysNo", GlobalConstants.SUPER_LOGIC_SYSTEM);
		} else {
			values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		}

		if (orgNo != null) {
			jql.append(" and dept.orgNo=:orgNo");
			values.put("orgNo", orgNo);
		}

		List<BioneDeptInfo> deptList = this.baseDAO.findWithNameParm(jql.toString(), values);
		Map<String, BioneDeptInfo> resultMap = Maps.newHashMap();
		for (BioneDeptInfo dept : deptList) {
			resultMap.put(dept.getDeptNo() + "_" + dept.getOrgNo(), dept);
		}
		return resultMap;
	}

	/**
	 * 根据查询条件获取记录 用以搭配全部记录而构造查询部门树
	 * 
	 * @param orgNo
	 * 			机构标识
	 * @param conditionMap
	 * 			查询支持
	 * @param isBasicDept
	 * 			是否启用基线部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BioneDeptInfo> findDeptInfoByOrg(String orgNo, Map<String, Object> conditionMap, String isBasicDept) {
		
		StringBuffer jql = new StringBuffer("");
		
		jql.append("select dept from BioneDeptInfo dept where dept.logicSysNo = :logicSysNo");
		
		Map<String, Object> values = Maps.newHashMap();
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
			values = (Map<String, Object>) conditionMap.get("params");
		}
		
		if (GlobalConstants.COMMON_STATUS_VALID.equals(isBasicDept)) {
			values.put("logicSysNo", GlobalConstants.SUPER_LOGIC_SYSTEM);
		} else {
			values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		}

		if (orgNo != null) {
			jql.append(" and dept.orgNo=:orgNo");
			values.put("orgNo", orgNo);
		}

		List<BioneDeptInfo> deptList = this.baseDAO.findWithNameParm(jql.toString(), values);
		return deptList;
	}
	
	/**
	 * 判断当前用户是否启用了基线部门
	 * 
	 * @return
	 */
	public String isBasicDept() {
		String jql = "select logicSys.basicDeptSts from BioneLogicSysInfo logicSys where logicSys.logicSysNo = :logicSysNo";
		
		Map<String, Object> values = Maps.newHashMap();
		values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		
		List<Object> objList = this.baseDAO.findWithNameParm(jql, values);
		
		if (objList != null && objList.size() != 0) {
			return objList.get(0).toString();
		} else {
			return GlobalConstants.COMMON_STATUS_INVALID;
		}
	}
	
	@Transactional(readOnly=false)
	public void removeEntityBatch(String ids){
		if(ids.endsWith(",")){
			ids=ids.substring(0,ids.length()-1);
		}
		String[] idArray=ids.split(",");
		for(String id:idArray){
			this.removeEntityById(id);
		}
	}
	
}
