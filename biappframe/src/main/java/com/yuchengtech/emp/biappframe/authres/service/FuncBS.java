package com.yuchengtech.emp.biappframe.authres.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

@Service
@Transactional(readOnly = true)
public class FuncBS extends BaseBS<BioneFuncInfo> {

	protected static Logger log = LoggerFactory.getLogger(FuncBS.class);

	/**
	 * 查找当前模块下的所有功能
	 * 
	 * @param logicSystem
	 * @param parentId
	 * @return
	 */
	public List<BioneFuncInfo> findAllValidFunc(String logicSystem, String moduleId, String parentId) {

		String jql = "select func from BioneFuncInfo func where func.moduleId=?0";

		List<Object> values = Lists.newArrayList();
		values.add(moduleId);

		if (parentId != null) {

			jql += " and func.upId=?1";
			values.add(parentId);
		}

		jql += " order by func.orderNo asc";

		// 获取所有效菜单
		List<BioneFuncInfo> funcInfoList = this.baseDAO.findWithIndexParam(jql, values.toArray());

		return funcInfoList;

	}
	
	/**
	 * 获取目标点的所有上级的列表
	 * @param funcInfoList
	 * 			     待查询列表
	 * @param Id
	 *            目标节点
	 * @return
	 */
	public String findUpId(List<BioneFuncInfo> funcInfoList, String Id) {
		for(int i = 0; i < funcInfoList.size(); i++) {
			if(Id.equals(funcInfoList.get(i).getFuncId())) {
				return funcInfoList.get(i).getUpId();
			}
		}
		return "0";
	}
	
	/**
	 * 构造菜单显示树，并返回第一层的节点
	 * 
	 * @param parentId
	 *            上级节点id
	 * @param isLoop
	 *            是否遍历子节点
	 * @return
	 */
	public List<CommonTreeNode> buildFuncTree(String logicSystem,String moduleId, boolean isLoop, String targetId) {

		List<CommonTreeNode> commonTreeNodeList = new ArrayList<CommonTreeNode>();

		List<BioneFuncInfo> funcInfoList = null;
		
		if (isLoop) {
			funcInfoList = this.findAllValidFunc(logicSystem, moduleId, null);
		} else {
			funcInfoList = this.findAllValidFunc(logicSystem, moduleId, moduleId);
		}
		
	/*	List<String> ids = null;
		if(targetId != null) {
			ids = Lists.newLinkedList();
			ids.add(targetId);
			do {
				ids.add(findUpId(funcInfoList, ((LinkedList<String>) ids).getLast()));
			} while (!"0".equals(((LinkedList<String>) ids).getLast()));
			((LinkedList<String>) ids).removeLast();
		}*/
		
		if (funcInfoList != null) {
			
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId("0");
			treeNode.setUpId("0");
			treeNode.setIcon(null);
			treeNode.setText("模块树");
			treeNode.setData(null);
			
		/*	Map<String, String> params;
			if(ids != null && ids.size() != 0) {
				params = Maps.newHashMap();
				params.put("open", "true");
				treeNode.setParams(params);
			}*/
			
			commonTreeNodeList.add(treeNode);
			
			for (BioneFuncInfo funcInfo :funcInfoList) {
				treeNode = new CommonTreeNode();
				treeNode.setId(funcInfo.getFuncId());
				treeNode.setUpId(funcInfo.getUpId());
				treeNode.setIcon(funcInfo.getNavIcon());
				treeNode.setText(funcInfo.getFuncName());
				treeNode.setData(funcInfo);
				
		/*		if(ids != null && ids.contains(funcInfo.getFuncId())) {
					params = Maps.newHashMap();
					params.put("open", "true");*/
					
					if(targetId != null && targetId.equals(funcInfo.getFuncId())) 
						treeNode.setIschecked(true);
					
					/*treeNode.setParams(params);*/
//				}
				commonTreeNodeList.add(treeNode);
			}
		}
		return commonTreeNodeList;
	}

	/*
	 * @param entity
	 * @return 被持化的对象
	 *
	@Transactional(readOnly = false)
	public BioneFuncInfo merge(BioneFuncInfo entity) {
		return this.baseDAO.merge(entity);
	}*/
	
	/**
	 * 删除当前功能结点, 及其子孙结点
	 */
	@Transactional(readOnly = false)
	public void removeEntityAndChild(String id) {
		BioneFuncInfo func_ = new BioneFuncInfo();
		func_.setFuncId(id);
		List<BioneFuncInfo> funcList = Lists.newArrayList();
		funcList.add(func_);
		findMiddleFuncNode(0, funcList);
		
		for(int i = 0; i < funcList.size(); i++) {
			this.removeEntity(funcList.get(i));
		}
	}
	
	public void findMiddleFuncNode(int index, List<BioneFuncInfo> funcList) {
		int j = funcList.size();
		for(int i = index; i < j; i++) {
			List<BioneFuncInfo> funcList_ = this.findEntityListByProperty("upId", !StringUtils.isEmpty(funcList.get(i).getFuncId()) ? funcList.get(i).getFuncId() : "");
			if(funcList_ != null && funcList_.size() != 0) 
				funcList.addAll(funcList_);
		}
		if(funcList.size() > j)
			findMiddleFuncNode(j, funcList);
	}
	
	/**
	 * 带返回值的保存
	 * 
	 * @param model
	 * @return
	 */
	@Transactional(readOnly = false)
	public BioneFuncInfo saveOrUpdate(BioneFuncInfo model) {
		return this.baseDAO.save(model);
	}
}
