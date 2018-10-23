package com.yuchengtech.emp.ecif.rulemanage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.rulemanage.entity.TxBizRuleGroup;


/**
 * <pre>
 * Description: 规则组BS 
 * </pre>	
 * @author lizongyu lizyu1@yuchengtech.com
 *
 */
@Service
@Transactional(readOnly = true)
public class TxBizRuleGroupBS extends BaseBS<TxBizRuleGroup>{


	/**
	 * 获取用于构造树的信息
	 */
	public List<CommonTreeNode> getRuleGroupTree() {
		List<CommonTreeNode> nodes = Lists.newArrayList();
			// 创建根节点
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId("ROOT");
			treeNode.setText("规则大类");
			treeNode.setIsexpand(true);
			Map<String, String> params = new HashMap<String, String>();
			params.put("realId", "0");
			params.put("isParent", "true");	
			treeNode.setParams(params);
		//添加规则大类子节点
		List<TxBizRuleGroup> ruleGroups = getAllEntityList();
		if(ruleGroups.size()>0){
		for(int i = 0;i<ruleGroups.size();i++){
			TxBizRuleGroup ruleGroup = ruleGroups.get(i);
			CommonTreeNode ruleGroupNode = new CommonTreeNode();
			ruleGroupNode.setUpId("ROOT");
			ruleGroupNode.setId(ruleGroup.getRuleGroupId().toString());
			ruleGroupNode.setText(ruleGroup.getRuleGroupName());
			Map<String, String> p = new HashMap<String, String>();
			p.put("realId", ruleGroup.getRuleGroupId().toString());
			ruleGroupNode.setParams(params);
			treeNode.addChildNode(ruleGroupNode);
		}
		}
		nodes.add(treeNode);
		return nodes;
	}

	public boolean deleteRuleGroup(String id) {
		String sql;
		sql = "select * from TX_BIZ_RULE_CONF tbrc where tbrc.RULE_GROUP_ID = ?0";
		if(this.baseDAO.createNativeQueryWithIndexParam(sql, Long.parseLong(id)).getResultList().size()!=0){
			return false;
		}
		return true;
		
	}

	public boolean ruleGroupValid(String ruleGroupNo, String ruleGroupName,
			String ruleGroupId) {
		int flag = 0;
		if(!StringUtils.isEmpty(ruleGroupNo)){
			List<TxBizRuleGroup> list = this.baseDAO.findByProperty(TxBizRuleGroup.class, "ruleGroupNo", ruleGroupNo);
			if(list!= null&&list.size()>0){
				flag = 1;
			}
		}
		if(!StringUtils.isEmpty(ruleGroupName)){
			List<TxBizRuleGroup> list = this.baseDAO.findByProperty(TxBizRuleGroup.class, "ruleGroupName", ruleGroupName);
			if(list!= null&&list.size()>0){
				flag = 1;
			}
		}
		if (!StringUtils.isEmpty(ruleGroupId)) {

			String jql = "select tbrg from TxBizRuleGroup tbrg where  tbrg.ruleGroupId = ?0 and tbrg.ruleGroupName=?1";
			List<TxBizRuleGroup> list = this.baseDAO.findWithIndexParam(
					jql, Long.parseLong(ruleGroupId), ruleGroupName);
			if (list.size() == 1) {
				flag = 0;
			}
		}
		if(flag==1){return false;}
		
		return true;
	}

}
