package com.yuchengtech.emp.biappframe.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttr;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrGrp;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
/**
 * <pre>
 * Title: 用户属性信息分组
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class UserAttrGrpBS extends BaseBS<BioneUserAttrGrp> {

	public List<CommonTreeNode> getGrpTree() {
		List<CommonTreeNode> nodes = Lists.newArrayList();
		List<BioneUserAttrGrp> grpList = this.getAllEntityList("orderNo", false);
		CommonTreeNode root = new CommonTreeNode();
		root.setId("0");
		root.setText("属性分组");
		root.setIsParent(true);
		if(grpList != null) {
			for (int i = 0; i < grpList.size(); i++) {
				BioneUserAttrGrp grp = grpList.get(i);
				CommonTreeNode node = new CommonTreeNode();
				node.setIcon(grp.getGrpIcon());
				node.setId(grp.getGrpId());
				node.setText(grp.getGrpName());
				nodes.add(node);
			}
		}
		root.setChildren(nodes);
		List<CommonTreeNode> result = Lists.newArrayList();
		result.add(root);
		return result;
	}
	
	/**
	 * 删除属性组
	 * 
	 *@param 属性组id
	 *
	 */
	@Transactional(readOnly = false)
	public void deleteAttrGrpById(String grpId){
		if(grpId != null && !"".equals(grpId)){
			String jqlQuery = "select attr from BioneUserAttr attr where attr.grpId = ?0";
			List<BioneUserAttr> attrs = this.baseDAO.findWithIndexParam(jqlQuery, grpId);
			List<String> attrList = new ArrayList<String>();
			if(attrs != null){
				for(int i = 0 ; i < attrs.size() ; i++){
					if(!attrList.contains(attrs.get(i).getAttrId())){
						attrList.add(attrs.get(i).getAttrId());
					}
				}
			}
			//删除分组
			String jql1 = "delete from BioneUserAttrGrp grp where grp.grpId = ?0";
			//删除属性
			String jql2 = "delete from BioneUserAttr attr where attr.attrId in (?0)";
			//删除属性关系
			String jql3 = "delete from BioneUserAttrVal val where val.id.attrId in (?0)";
			this.baseDAO.batchExecuteWithIndexParam(jql1, grpId);
			if(attrList != null && attrList.size() > 0) {
				this.baseDAO.batchExecuteWithIndexParam(jql2, attrList);
				this.baseDAO.batchExecuteWithIndexParam(jql3, attrList);
			}
		}
	}
}
