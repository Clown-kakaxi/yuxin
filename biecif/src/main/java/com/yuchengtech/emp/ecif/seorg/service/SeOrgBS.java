/**
 * 
 */
package com.yuchengtech.emp.ecif.seorg.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.seorg.entity.SeOrgInfo;

/**
 * <pre>
 * Title:机构管理类
 * Description: 提供机构管理相关业务逻辑处理功能，提供事务控制
 * </pre>
 * 
 * @author mengzx mengzx@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("seOrgBS")
@Transactional(readOnly = true)
public class SeOrgBS extends BaseBS<SeOrgInfo> {
	@Autowired
	private LogicSysBS logicSysBS;
	protected static Logger log = LoggerFactory.getLogger(SeOrgBS.class);

	/**
	 * 构造机构的结构树，并返回第一层的节点
	 * 
	 * @return
	 */
	public List<CommonTreeNode> buildOrgTree() {
		
		List<CommonTreeNode> firstLevelNodeList = Lists.newArrayList();
		
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		
		/* @Revision 20130403105200-liuch */
		//应用上下文路径 
		String basePath = GlobalConstants.APP_CONTEXT_PATH;
		/* @Revision 20130403105200-liuch END */
		
		/**
		 * 构建树形根节点 
		 */
		CommonTreeNode rootNode = new CommonTreeNode();
		
		SeOrgInfo org=new SeOrgInfo();
		org.setOrgNo(CommonTreeNode.ROOT_ID);
		org.setOrgName("全部");
		
		rootNode.setId(org.getOrgNo());
		rootNode.setText(org.getOrgName());
		//rootNode.setIcon(basePath + GlobalConstants.LOGIC_ALL_ICON); ///biecif-web/images/classics/icons/folder.png
		rootNode.setIcon("/biecif-web/images/classics/icons/folder.png"); 
		rootNode.setData(org);
		firstLevelNodeList.add(rootNode);
		
		String jql = "select seOrgInfo from SeOrgInfo seOrgInfo where seOrgInfo.logicSysNo=?0 order by seOrgInfo.orgNo asc";

		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		BioneLogicSysInfo bioneLogicSysInfo = logicSysBS.getBioneLogicSysInfoByLogicSysNo(logicSysNo);
		if (GlobalConstants.COMMON_STATUS_VALID.equals(bioneLogicSysInfo.getBasicOrgSts())) {
			logicSysNo = GlobalConstants.SUPER_LOGIC_SYSTEM;
		}
		// 获取所有机构
		List<SeOrgInfo> orgInfoList = this.baseDAO.findWithIndexParam(jql, logicSysNo);

		// 以父机构id->子机构列表的MAP机构对所有机构进行分组
		Map<String, List<SeOrgInfo>> upOrgIdMap = Maps.newHashMap();

		if (orgInfoList != null) {

			// 将机构按层级进行分类
			List<SeOrgInfo> oneLevelOrgInfoList = null;
			for (SeOrgInfo orgInfo : orgInfoList) {

				oneLevelOrgInfoList = upOrgIdMap.get(orgInfo.getUpNo().toString());

				if (oneLevelOrgInfoList == null) {

					oneLevelOrgInfoList = Lists.newArrayList();
					upOrgIdMap.put(orgInfo.getUpNo().toString(), oneLevelOrgInfoList);
				}

				oneLevelOrgInfoList.add(orgInfo);

			}
		}
		// 循环遍历，构造树形结构
		// 获取最顶层的结构信息
		List<SeOrgInfo> rootOrgInfoList = upOrgIdMap.get(CommonTreeNode.ROOT_ID);
		if (rootOrgInfoList != null) {

			for (SeOrgInfo orgInfo : rootOrgInfoList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(orgInfo.getOrgNo());
				treeNode.setUpId(orgInfo.getUpNo());
				treeNode.setText(orgInfo.getOrgName());
				treeNode.setIcon(basePath + GlobalConstants.LOGIC_ORG_ICON);
				treeNode.setData(orgInfo);
				//firstLevelNodeList.add(treeNode);
				rootNode.addChildNode(treeNode);

				// 循环构造子节点
				buildChildTreeNode(treeNode, upOrgIdMap);
			}
		}

		upOrgIdMap.clear();

		return firstLevelNodeList;

	}

	/**
	 * 循环构造子节点
	 * 
	 * @param parentNode
	 *            父亲节点
	 * @param upOrgIdMap
	 *            机构信息
	 */
	private void buildChildTreeNode(CommonTreeNode parentNode, Map<String, List<SeOrgInfo>> upOrgIdMap) {

		// 构造当前节点的子节点
		List<SeOrgInfo> childOrgInfoList = upOrgIdMap.get(parentNode.getId());
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		
		/* @Revision 20130403105200-liuch */
		String basePath = GlobalConstants.APP_CONTEXT_PATH;
		/* @Revision 20130403105200-liuch END */
		
		if (childOrgInfoList != null) {

			for (SeOrgInfo orgInfo : childOrgInfoList) {

				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(orgInfo.getOrgNo());
				treeNode.setUpId(orgInfo.getUpNo());
				treeNode.setText(orgInfo.getOrgName());
				treeNode.setData(orgInfo);
				treeNode.setIcon(basePath + GlobalConstants.LOGIC_ORG_ICON);
				parentNode.addChildNode(treeNode);

				buildChildTreeNode(treeNode, upOrgIdMap);
			}
		}
	}
}
