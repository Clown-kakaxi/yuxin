/**
 * 
 */
package com.yuchengtech.emp.biappframe.authobj.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

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
@Service("orgBS")
@Transactional(readOnly = true)
public class OrgBS extends BaseBS<BioneOrgInfo> {
	@Autowired
	private LogicSysBS logicSysBS;
	protected static Logger log = LoggerFactory.getLogger(OrgBS.class);

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
		String basePath = GlobalConstants.APP_CONTEXT_PATH;
		/* @Revision 20130403105200-liuch END */
		
		CommonTreeNode rootNode = new CommonTreeNode();
		BioneOrgInfo org=new BioneOrgInfo();
		org.setOrgNo(CommonTreeNode.ROOT_ID);
		org.setOrgName("全部");
		rootNode.setId(org.getOrgNo());
		rootNode.setText(org.getOrgName());
		rootNode.setIcon("/"+basePath + GlobalConstants.LOGIC_ALL_ICON);
		rootNode.setData(org);
		firstLevelNodeList.add(rootNode);
		
		String jql = "select org from BioneOrgInfo org where org.logicSysNo=?0 order by org.orgNo asc";

		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		BioneLogicSysInfo bioneLogicSysInfo = logicSysBS.getBioneLogicSysInfoByLogicSysNo(logicSysNo);
		if (GlobalConstants.COMMON_STATUS_VALID.equals(bioneLogicSysInfo.getBasicOrgSts())) {
			logicSysNo = GlobalConstants.SUPER_LOGIC_SYSTEM;
		}
		// 获取所有机构
		List<BioneOrgInfo> orgInfoList = this.baseDAO.findWithIndexParam(jql, logicSysNo);

		// 以父机构id->子机构列表的MAP机构对所有机构进行分组
		Map<String, List<BioneOrgInfo>> upOrgIdMap = Maps.newHashMap();

		if (orgInfoList != null) {

			// 将机构按层级进行分类
			List<BioneOrgInfo> oneLevelOrgInfoList = null;
			for (BioneOrgInfo orgInfo : orgInfoList) {

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
		List<BioneOrgInfo> rootOrgInfoList = upOrgIdMap.get(CommonTreeNode.ROOT_ID);
		if (rootOrgInfoList != null) {

			for (BioneOrgInfo orgInfo : rootOrgInfoList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(orgInfo.getOrgNo());
				treeNode.setUpId(orgInfo.getUpNo());
				treeNode.setText(orgInfo.getOrgName());
				treeNode.setIcon("/"+basePath + GlobalConstants.LOGIC_ORG_ICON);
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
	private void buildChildTreeNode(CommonTreeNode parentNode, Map<String, List<BioneOrgInfo>> upOrgIdMap) {

		// 构造当前节点的子节点
		List<BioneOrgInfo> childOrgInfoList = upOrgIdMap.get(parentNode.getId());
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		
		/* @Revision 20130403105200-liuch */
		String basePath = GlobalConstants.APP_CONTEXT_PATH;
		/* @Revision 20130403105200-liuch END */
		
		if (childOrgInfoList != null) {

			for (BioneOrgInfo orgInfo : childOrgInfoList) {

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

	/**
	 * 根据机构标识，查询机构信息
	 * 
	 * @param orgNo
	 *            机构标识
	 */
	public BioneOrgInfo findOrgInfoByOrgNo(String orgNo) {
		String sysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		BioneLogicSysInfo sys = this.getEntityByProperty(BioneLogicSysInfo.class, "logicSysNo", sysNo);
		if (sys != null) {
			String sysNoParam = GlobalConstants.SUPER_LOGIC_SYSTEM;
			if (GlobalConstants.COMMON_STATUS_INVALID.equals(sys.getBasicOrgSts())) {
				//若该逻辑系统不是使用基线机构
				sysNoParam = sysNo;
			}
			String jql = "select org from  BioneOrgInfo org where org.orgNo=?0 and logicSysNo=?1";
			List<BioneOrgInfo> list = this.baseDAO.findWithIndexParam(jql, orgNo, sysNoParam);
			if (list == null || list.size() == 0) {
				return null;
			} else {
				for (BioneOrgInfo orgInfo : list) {
					return orgInfo;
				}
			}
		}
		return null;

	}

	/**
	 * 根据机构标识查询基线机构信息
	 * 
	 * @param orgNo
	 *            机构标识
	 */
	public BioneOrgInfo findBasicOrgInfoByOrgNo(String orgNo) {
		String sysNoParam = GlobalConstants.SUPER_LOGIC_SYSTEM;
		String jql = "select org from  BioneOrgInfo org where org.orgNo=?0 and logicSysNo=?1";
		List<BioneOrgInfo> list = this.baseDAO.findWithIndexParam(jql, orgNo, sysNoParam);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			for (BioneOrgInfo orgInfo : list) {
				return orgInfo;
			}
		}
		return null;

	}
	
	@Transactional(readOnly=false)
	public void removeOrgAndDept(String orgId){
		BioneOrgInfo org=this.getEntityById(orgId);
		String jql="delete from BioneOrgInfo org where org.orgId=?0 AND org.logicSysNo=?1";
		this.baseDAO.batchExecuteWithIndexParam(jql, orgId,BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		jql="delete from BioneDeptInfo dept where dept.orgNo =?0 AND dept.logicSysNo=?1";
		this.baseDAO.batchExecuteWithIndexParam(jql, org.getOrgNo(),BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
	}

	@Transactional(readOnly=true)
	public void findLowerOrgInfosByOrgId(String orgNo,List<BioneOrgInfo> orgList){
		String jql="SELECT org FROM BioneOrgInfo org WHERE org.upNo=?0 AND org.logicSysNo=?1";
		List<BioneOrgInfo> orgInfos=this.baseDAO.findWithIndexParam(jql, orgNo,BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		if(orgInfos!=null&&orgInfos.size()!=0){
			orgList.addAll(orgInfos);
			for(BioneOrgInfo org:orgInfos){
				findLowerOrgInfosByOrgId(org.getOrgNo(), orgList);
			}
		}
		
	}
}
