package com.yuchengtech.emp.biappframe.user.service;

import java.util.Collection;
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
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;


@Service
@Transactional(readOnly = true)
public class OrgTreeBS extends BaseBS<BioneOrgInfo> {

	protected static Logger log = LoggerFactory.getLogger(OrgTreeBS.class);

	/**
	 * 根据条件查找匹配的所有机构
	 * 
	 * @param logicSystem
	 * 			逻辑系统标识
	 * @param conditionMap
	 * 			生成的搜索查询语句->BaseController->buildSerachCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void findAllValidOrg(String logicSystem, Map<String, Object> conditionMap, List<CommonTreeNode> commonTreeNodeList) {
		
		StringBuffer jql = new StringBuffer("");
//		jql.append("select logicSys.basicOrgSts from BioneLogicSysInfo logicSys where logicSys.logicSysNo = :logicSysNo");
		
		Map<String, Object> values = Maps.newHashMap();
		values.put("logicSysNo", GlobalConstants.SUPER_LOGIC_SYSTEM);
		
//		List<Object> objList = this.baseDAO.findWithNameParm(jql.toString(), values);
		
		jql = new StringBuffer("select org from BioneOrgInfo org where org.orgSts = :orgSts");
//		if(objList != null && GlobalConstants.COMMON_STATUS_VALID.equals(objList.get(0).toString())) {
//			values = Maps.newHashMap();
//		} else {
			jql.append(" and org.logicSysNo = :logicSysNo");
//		}
		
		values.put("orgSts", GlobalConstants.COMMON_STATUS_VALID);
		
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
		List<BioneOrgInfo> orgInfoList = this.baseDAO.findWithNameParm(jql.toString(), values);
		
		// 树结构的完整性检查及修复
		// 此处 index 为所有匹配查询条件的搜索结果的 “结束索引   + 1”
		int index = orgInfoList.size();
		if(index != 0 && key) {
			repairUpOrg(orgInfoList, 0, commonTreeNodeList);
			
			// 此处   p 为所有匹配搜索节点的下级节点的开始索引
			int p = orgInfoList.size();
			
			// 执行目标节点的子节点修复
			for (int i = 0; i < index; i++) {
//				List<BioneOrgInfo> downOrgInfoList = this.baseDAO.findByProperty("upNo", orgInfoList.get(i).getOrgNo());
				List<BioneOrgInfo> downOrgInfoList = this.baseDAO.findByProperty(BioneOrgInfo.class, "upNo", orgInfoList.get(i).getOrgNo());
				if(downOrgInfoList != null) {
					for(BioneOrgInfo orgInfo : downOrgInfoList) {
						
						// 查检结果集中是否已存在
						int t = 0, length = orgInfoList.size();
						for( ; t < length; t++) {
							if(orgInfo.getOrgId().equals(orgInfoList.get(t).getOrgId())) {
								break;
							}
						}
						
						// 若提前退出上面的循环, 则表明存在, 否则说明不存在
						if(t >= length) {
							orgInfoList.add(orgInfo);
							CommonTreeNode treeNode = new CommonTreeNode();
							treeNode.setId(orgInfo.getOrgNo());
							treeNode.setUpId(orgInfo.getUpNo());
							treeNode.setText(orgInfo.getOrgName());
							treeNode.setData(orgInfo);
							commonTreeNodeList.add(treeNode);
						}
					}
				}
			}
			
			// 执行目标节点的孙以及孙的后代节点修复
			repairDownOrg(orgInfoList, p, commonTreeNodeList);
		} else if(index != 0 && !key) {
			for(BioneOrgInfo orgInfo : orgInfoList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(orgInfo.getOrgNo());
				treeNode.setUpId(orgInfo.getUpNo());
				treeNode.setText(orgInfo.getOrgName());
				treeNode.setData(orgInfo);
				commonTreeNodeList.add(treeNode);
			}
		} else {
			return;
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
	public void repairUpOrg(List<BioneOrgInfo> orgInfoList, int index, List<CommonTreeNode> commonTreeNodeList) {
		boolean flag = false;
		BioneOrgInfo orgInfo;
		for(int length = orgInfoList.size(); index < length; index++) {
			orgInfo = orgInfoList.get(index);
			
			// begin 封装的树
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(orgInfo.getOrgNo());
			treeNode.setUpId(orgInfo.getUpNo());
			treeNode.setText(orgInfo.getOrgName());
			treeNode.setData(orgInfo);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("open", "true");
			treeNode.setParams(paramMap);
			commonTreeNodeList.add(treeNode);
			// end
			
			if(orgInfo.getUpNo().equals(CommonTreeNode.ROOT_ID)) {
				continue;
			}
			
			// 如果存在上级, 则将 flag 置为 true, 否则, 保持 false
			for(BioneOrgInfo _orgInfo : orgInfoList) {
				if(orgInfo.getUpNo().equals(_orgInfo.getOrgNo())) {
					flag = true;
					break;
				}
			}
			
			// 如果不存在上级, 则进行修复
			if(!flag) {
				BioneOrgInfo orgInfo_ = this.baseDAO.findUniqueByProperty(BioneOrgInfo.class, "orgNo", orgInfo.getUpNo());
				if(orgInfo_ == null) {
					
					// 如果找不到上级节点, 则此节点为异常节点, 将此节点的上级标识为"0", 即根节点标识, 
					// 此为辅助措施, 破坏了树结构, 但保证了数据完整性, 可帮助使用者发现。
					orgInfo.setUpNo("0");
					
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
	public void repairDownOrg(List<BioneOrgInfo> orgInfoList, int index, List<CommonTreeNode> commonTreeNodeList) {
		for (int i = index; i < orgInfoList.size(); i++) {
			List<BioneOrgInfo> downOrgInfoList = this.baseDAO.findByProperty(BioneOrgInfo.class, "upNo", orgInfoList.get(i).getOrgNo());
			if(downOrgInfoList != null) {
				for(BioneOrgInfo orgInfo : downOrgInfoList) {
					
					// 查检结果集中是否已存在
					int t = 0, length = orgInfoList.size();
					for( ; t < length; t++) {
						if(orgInfo.getOrgId().equals(orgInfoList.get(t).getOrgId())) {
							break;
						}
					}
					
					// 若提前退出上面的循环, 则表明存在, 否则说明不存在
					if(t >= length) {
						orgInfoList.add(orgInfo);
						CommonTreeNode treeNode = new CommonTreeNode();
						treeNode.setId(orgInfo.getOrgNo());
						treeNode.setUpId(orgInfo.getUpNo());
						treeNode.setText(orgInfo.getOrgName());
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
		
		CommonTreeNode treeNode = new CommonTreeNode();
		treeNode.setId("0");
		treeNode.setUpId("0");
		treeNode.setIcon(null);
		treeNode.setText("机构树");
		treeNode.setData(null);
		
		Map<String, String> params = Maps.newHashMap();
		params.put("open", "true");
		
		treeNode.setParams(params);
		
		commonTreeNodeList.add(treeNode);

		this.findAllValidOrg(logicSystem, conditionMap, commonTreeNodeList);
			
		return commonTreeNodeList;
	}

	/**
	 * 获取 机构标识 与 机构名称 信息列表,准备存入缓存
	 * 
	 * @param logicSysNo
	 * 			逻辑系统标识
	 * 
	 * @return
	 */
	public Map<String, Map<String, Object>> findOrgNoAndOrgName(Collection<?> logicSysNo) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select org.orgNo, org.orgName, org.logicSysNo from BioneOrgInfo org where org.orgSts = :orgSts and org.logicSysNo = :logicSysNo");
		
		Iterator<?> iter = logicSysNo.iterator();
		
		Map<String, Map<String, Object>> orgNoAndNameMapMap = Maps.newHashMap();
		
		while(iter.hasNext()) {
			
			Map<String, Object> values = Maps.newHashMap();
			values.put("orgSts", GlobalConstants.COMMON_STATUS_VALID);
			values.put("logicSysNo", iter.next());
			
			List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), values);
			
			if(!objList.isEmpty()) {
				
				Map<String, Object> orgNoAndNameMap = Maps.newHashMap();
				
				for(Object[] obj : objList) {
					orgNoAndNameMap.put(obj[0] + "", obj[1]);
				}
				
				orgNoAndNameMapMap.put((String) objList.get(0)[2], orgNoAndNameMap);
			}
		}
		return orgNoAndNameMapMap;
	}
}
