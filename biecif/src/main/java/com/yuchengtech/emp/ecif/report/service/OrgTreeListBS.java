package com.yuchengtech.emp.ecif.report.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.report.entity.PubBranchInfoRel;


@Service
@Transactional(readOnly = true)
public class OrgTreeListBS extends BaseBS<PubBranchInfoRel> {

	protected static Logger log = LoggerFactory.getLogger(OrgTreeListBS.class);

	/**
	 * 根据条件查找匹配的所有机构
	 */
	@SuppressWarnings("unchecked")
	public void findAllValidOrg(String logicSystem, Map<String, Object> conditionMap, List<CommonTreeNode> commonTreeNodeList) {
		
		Map<String, Object> values = Maps.newHashMap();
//		values.put("logicSysNo", GlobalConstants.SUPER_LOGIC_SYSTEM);
		StringBuffer jql = new StringBuffer("");
		jql = new StringBuffer("select org from PubBranchInfoRel org order by parentBrcCode ");
		
		boolean key = false;
		if(key = !"".equals(conditionMap.get("jql"))) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		
		Map<String, ?> params = (Map<String, ?>) conditionMap.get("params");		
		Iterator<?> iter = params.entrySet().iterator();		
		while(iter.hasNext()) {			
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();			
			values.put(entry.getKey(), entry.getValue());
		}
		
		// 获取所有效部门信息
		List<PubBranchInfoRel> orgInfoList = this.baseDAO.findWithNameParm(jql.toString(), values);
		
		// 树结构的完整性检查及修复
		// 此处 index 为所有匹配查询条件的搜索结果的 “结束索引   + 1”
		int index = orgInfoList.size();
		if(index != 0 && key) {
			repairUpOrg(orgInfoList, 0, commonTreeNodeList);
			
			// 此处   p 为所有匹配搜索节点的下级节点的开始索引
			int p = orgInfoList.size();
			
			// 执行目标节点的子节点修复
			for (int i = 0; i < index; i++) {
				List<PubBranchInfoRel> downOrgInfoList = this.baseDAO.findByProperty(PubBranchInfoRel.class, "parentBrcCode", orgInfoList.get(i).getParentBrcCode());
				if(downOrgInfoList != null) {
					for(PubBranchInfoRel orgInfo : downOrgInfoList) {
						
						// 查检结果集中是否已存在
						int t = 0, length = orgInfoList.size();
						for( ; t < length; t++) {
							if(orgInfo.getBrcCode().equals(orgInfoList.get(t).getBrcCode())) {
								break;
							}
						}
						
						// 若提前退出上面的循环, 则表明存在, 否则说明不存在
						if(t >= length) {
							orgInfoList.add(orgInfo);
							CommonTreeNode treeNode = new CommonTreeNode();
							treeNode.setId(orgInfo.getBrcCode() + "--" + orgInfo.getBrcCode());
							treeNode.setUpId(orgInfo.getParentBrcCode());
							treeNode.setText(orgInfo.getBrcName());
							treeNode.setData(null);
							commonTreeNodeList.add(treeNode);
						}
					}
				}
			}
			
			// 执行目标节点的孙以及孙的后代节点修复
			repairDownOrg(orgInfoList, p, commonTreeNodeList);
		} else if(index != 0 && !key) {
			// 将机构信息处理成 上级机构编号 -- 机构信息 的键值对
			Map<String, List<PubBranchInfoRel>> upOrgIdMap = Maps.newHashMap();
			List<PubBranchInfoRel> oneLevelorgInfoList = null;
			for (PubBranchInfoRel orgInfo : orgInfoList) {
				if(orgInfo.getBrcCode().equals("0000")){
					orgInfo.setParentBrcCode("");
				}
				oneLevelorgInfoList = upOrgIdMap.get(orgInfo.getParentBrcCode());
				if (oneLevelorgInfoList == null) {
					oneLevelorgInfoList = Lists.newArrayList();
					upOrgIdMap.put(orgInfo.getParentBrcCode(), oneLevelorgInfoList);
				}
				oneLevelorgInfoList.add(orgInfo);
			}
			// 上级部门标识的commonTreeNode.Root_id的为根节点
			List<PubBranchInfoRel> rootOrgInfoList = upOrgIdMap.get("");
			if (rootOrgInfoList != null) {
				for (PubBranchInfoRel orgInfo : rootOrgInfoList) {
					CommonTreeNode treeNode = new CommonTreeNode();
					treeNode.setId(orgInfo.getBrcCode());
					treeNode.setText(orgInfo.getBrcCode() + "--" + orgInfo.getBrcName());
					treeNode.setUpId(orgInfo.getParentBrcCode());
					treeNode.setData(orgInfo);
					commonTreeNodeList.add(treeNode);
					buildDeptChildrenTree(treeNode, upOrgIdMap);
				}
			}
		} else {
			return;
		}
	}
	
	/**
	 * 通过上级机构编号--绑定在上级节点中 ,循环查找并构造下级机构树 列表信息
	 * 
	 * @param treeNode 父节点
	 * @param upDeptIdMap 父标识--》子节点列表
	 */
	private void buildDeptChildrenTree(CommonTreeNode parentNode,
			Map<String, List<PubBranchInfoRel>> upDeptIdMap) {
		List<PubBranchInfoRel> orgInfoList = upDeptIdMap.get(new String(
				parentNode.getId()));
		if (orgInfoList != null) {
			for (PubBranchInfoRel orgInfo : orgInfoList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(orgInfo.getBrcCode());
				treeNode.setText(orgInfo.getBrcCode() + "--" + orgInfo.getBrcName());
				treeNode.setUpId(orgInfo.getParentBrcCode());
				treeNode.setData(orgInfo);
				parentNode.addChildNode(treeNode);
				buildDeptChildrenTree(treeNode, upDeptIdMap);
			}
		}
	}
	
	/**
	 * 树结构的完整性检查及修复
	 * 			修复上级
	 * @param orgInfoList
	 * 			机构集合
	 * @param index
	 * 			已遍历到的索引值
	 */
	public void repairUpOrg(List<PubBranchInfoRel> orgInfoList, int index, List<CommonTreeNode> commonTreeNodeList) {
		boolean flag = false;
		PubBranchInfoRel orgInfo;
		for(int length = orgInfoList.size(); index < length; index++) {
			orgInfo = orgInfoList.get(index);
			
			// begin 封装的树
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(orgInfo.getBrcCode());
			treeNode.setUpId(orgInfo.getParentBrcCode());
			treeNode.setText(orgInfo.getBrcName());
			treeNode.setData(null);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("open", "true");
			treeNode.setParams(paramMap);
			commonTreeNodeList.add(treeNode);
			// end
			
			if(orgInfo.getParentBrcCode().equals(CommonTreeNode.ROOT_ID)) {
				continue;
			}
			
			// 如果存在上级, 则将 flag 置为 true, 否则, 保持 false
			for(PubBranchInfoRel _orgInfo : orgInfoList) {
				if(orgInfo.getParentBrcCode().equals(_orgInfo.getBrcCode())) {
					flag = true;
					break;
				}
			}
			
			// 如果不存在上级, 则进行修复
			if(!flag) {
				PubBranchInfoRel orgInfo_ = this.baseDAO.findUniqueByProperty(PubBranchInfoRel.class, "brcCode", orgInfo.getParentBrcCode());
				if(orgInfo_ == null) {
					
					// 如果找不到上级节点, 则此节点为异常节点, 将此节点的上级标识为"0", 即根节点标识, 
					// 此为辅助措施, 破坏了树结构, 但保证了数据完整性, 可帮助使用者发现。
					orgInfo.setParentBrcCode("0");
					
				} else {
					orgInfoList.add(orgInfo_);
				}
				repairUpOrg(orgInfoList, index + 1, commonTreeNodeList);
			}
			flag = false;
		}
	}
	
	/**
	 * 树结构的完整性检查及修复
	 * 			修复下级
	 * @param orgInfoList
	 * 			机构集合
	 * @param index
	 * 			查询节点的目标索引
	 */
	public void repairDownOrg(List<PubBranchInfoRel> orgInfoList, int index, List<CommonTreeNode> commonTreeNodeList) {
		for (int i = index; i < orgInfoList.size(); i++) {
			List<PubBranchInfoRel> downOrgInfoList = this.baseDAO.findByProperty(PubBranchInfoRel.class, "parentBrcCode", orgInfoList.get(i).getBrcCode());
			if(downOrgInfoList != null) {
				for(PubBranchInfoRel orgInfo : downOrgInfoList) {
					
					// 查检结果集中是否已存在
					int t = 0, length = orgInfoList.size();
					for( ; t < length; t++) {
						if(orgInfo.getBrcCode().equals(orgInfoList.get(t).getBrcCode())) {
							break;
						}
					}
					
					// 若提前退出上面的循环, 则表明存在, 否则说明不存在
					if(t >= length) {
						orgInfoList.add(orgInfo);
						CommonTreeNode treeNode = new CommonTreeNode();
						treeNode.setId(orgInfo.getBrcCode());
						treeNode.setUpId(orgInfo.getParentBrcCode());
						treeNode.setText(orgInfo.getBrcName());
						treeNode.setData(orgInfo);
						commonTreeNodeList.add(treeNode);
					}
				}
			}
		}
	}

	/**
	 * 获取用于构造树的信息
	 * 
	 * @param isLoop
	 *          是否遍历子节点
	 * @param conditionMap
	 * 			生成的搜索查询语句->BaseController->buildSerachCondition
	 * @return commonTreeNodeList
	 * 			部门信息列表
	 */
	public List<CommonTreeNode> buildOrgTree(String logicSystem, boolean isLoop, Map<String, Object> conditionMap) {

		List<CommonTreeNode> commonTreeNodeList = Lists.newArrayList();

		this.findAllValidOrg(logicSystem, conditionMap, commonTreeNodeList);
			
		return commonTreeNodeList;
	}

}
