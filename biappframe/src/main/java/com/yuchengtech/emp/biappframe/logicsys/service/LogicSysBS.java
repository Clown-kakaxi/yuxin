package com.yuchengtech.emp.biappframe.logicsys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjDef;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthResDef;
import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneMenuInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneModuleInfo;
import com.yuchengtech.emp.biappframe.authres.util.ComparatorMenu;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAdminUserInfo;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.util.ComparatorFunc;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title:逻辑系统管理类
 * Description: 提供逻辑系统管理相关业务逻辑处理功能，提供事务控制
 * </pre>
 * 
 * @author songxf songxf@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LogicSysBS extends BaseBS<BioneLogicSysInfo> {

	protected static Logger log = LoggerFactory.getLogger(LogicSysBS.class);

	
	/* @Revision 20130403105200-liuch */
	private String basePath = GlobalConstants.APP_CONTEXT_PATH;
	/* @Revision 20130403105200-liuch END */
	
	
	/**
	 * 获得现有逻辑系统列表
	 * 
	 * @return list
	 */
	public List<BioneLogicSysInfo> findLogicSysInfo() {
		String jql = "select logicSysInfo from BioneLogicSysInfo logicSysInfo where logicSysInfo.logicSysSts=?0 order by logicSysInfo.orderNo asc";
		List<BioneLogicSysInfo> logicSysInfoList = this.baseDAO.findWithIndexParam(jql,
				GlobalConstants.COMMON_STATUS_VALID);
		return logicSysInfoList;
	}

	/**
	 * 查询逻辑系统
	 * @param currentPage  当前页数
	 * @param maxResult 每一页最大数目
	 * @param values 查询条件
	 * @return	map 查询结果集
	 * @throws Exception
	 */
	public SearchResult<BioneLogicSysInfo> findResults(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) throws Exception {
		//		String jql = "from " +BioneLogicSysInfo.class.getName()+" where 1=1";

		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from BioneLogicSysInfo obj where obj.isBuiltin <>1 ");

		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");

		return this.baseDAO.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
	}

	/**
	 * 批量删除
	 * @param ids 记录ID号
	 */
	@Transactional(readOnly = false)
	public void deleteBatch(String[] ids) {
		for (String id : ids) {
			removeEntity(getEntityById(id));
		}
	}

	/**
	 * 获取最大序号
	 * @return
	 */
	public Long getMaxOrder() {
		String jpa = "select max(logicSysInfo.orderNo) from BioneLogicSysInfo logicSysInfo";
		List<Long> maxList = this.baseDAO.findWithIndexParam(jpa);
		Long m = 0l;
		if (maxList.size() == 1) {
			m = maxList.get(0) + 1;
		}
		return m;
	}

	/**
	 * 加载认证方式
	 * @return
	 */
	public List findAuthType() {
		String jpa = "from BioneAuthInfo authInfo where 1=?0";
		List list = this.baseDAO.findWithIndexParam(jpa, 1);
		return list;
	}

	/**
	 * 获取所有的用户信息
	 * @param id
	 * @param params
	 * @return
	 */
	public List<BioneUserInfo> getUserList(String id, Map<String, String> params) {
		StringBuffer jpa = new StringBuffer("select obj from BioneUserInfo obj where 1=?0");
		String userName = params.get("userName");
		if (!"".equals(userName) && userName != null) {
			jpa.append(" and obj.userName like '%");
			jpa.append(userName);
			jpa.append("%'");
		}
		List<BioneUserInfo> list = this.baseDAO.findWithIndexParam(jpa.toString(), 1);
		List<BioneUserInfo> adminList = getAdminList(id);
		list.removeAll(adminList);
		return list;
	}

	/**
	 * 用户信息映射匹配Tree的响应数据
	 * @param userList
	 * @return
	 */
	public List<CommonTreeNode> userToTree(List<BioneUserInfo> userList) {
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		List<CommonTreeNode> list = new ArrayList();
		for (BioneUserInfo userInfo : userList) {
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(userInfo.getUserId());
			treeNode.setText(userInfo.getUserName());
			treeNode.setUpId(CommonTreeNode.ROOT_ID);
			treeNode.setIcon(basePath + GlobalConstants.LOGIC_ADMIN_ICON);
			list.add(treeNode);
		}
		return list;
	}

	/**
	 * 根据逻辑系统ID获取逻辑系统授权信息
	 * @return
	 */
	public List<BioneUserInfo> getAdminList(String id) {

		List<BioneAdminUserInfo> adminList = getEntityListByProperty(BioneAdminUserInfo.class, "id.logicSysId", id);
		List<BioneUserInfo> userList = new ArrayList();
		for (BioneAdminUserInfo adminUser : adminList) {
			userList.add((BioneUserInfo) getEntityById(BioneUserInfo.class, adminUser.getId().getUserId()));
		}
		return userList;
	}

	/**
	 * 获得功能树 不含主页
	 * @return
	 */
	public List<CommonTreeNode> funcToTree(List<BioneFuncInfo> funcList) {
		return this.funcToTree(funcList, false);
	}

	public CommonTreeNode getMenuRoot() {
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		CommonTreeNode menuRoot = new CommonTreeNode();
		menuRoot.setId(CommonTreeNode.ROOT_ID);
		menuRoot.setText("菜单根");
		menuRoot.setUpId("-1");
		menuRoot.setIcon(basePath + GlobalConstants.LOGIC_ADMIN_ICON);
		return menuRoot;
	}

	/**
	 * 获得功能树
	 * @param funcList
	 * @param isIndex true:该功能点是主页
	 * @return
	 */
	public List<CommonTreeNode> funcToTree(List<BioneFuncInfo> funcList, boolean isIndex) {
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		List<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();
		boolean isLogicFunc = true;
		if (funcList == null && !isIndex) {
			isLogicFunc = false;
			List<BioneModuleInfo> modulList = getEntityList(BioneModuleInfo.class);
			for (BioneModuleInfo moduleInfo : modulList) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(moduleInfo.getModuleId());
				treeNode.setText(moduleInfo.getModuleName());
				treeNode.setUpId(CommonTreeNode.ROOT_ID);
				treeNode.setIcon(basePath + GlobalConstants.LOGIC_MODULE_ICON);
				Map params = new HashMap();
				params.put("type", GlobalConstants.MENU_TYPE_MODULE);
				treeNode.setParams(params);
				treeNodes.add(treeNode);
			}
			funcList = getEntityList(BioneFuncInfo.class);
		}

		//对功能进行排序
		Collections.sort(funcList, new ComparatorFunc());
		
		for (int i = 0; i < funcList.size(); i++) {

			BioneFuncInfo funcInfo = funcList.get(i);
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(funcInfo.getFuncId());
			if (isIndex) {
				treeNode.setText("<font color='red'>" + funcInfo.getFuncName() + "</font>");
			} else {
				treeNode.setText(funcInfo.getFuncName());
			}
			Map params = new HashMap();
			params.put("realName", funcInfo.getFuncName());
			params.put("type", GlobalConstants.MENU_TYPE_FUNCTION);
			if (!isLogicFunc) {
				params.put("isNewNode", GlobalConstants.COMMON_STATUS_VALID);
				if(GlobalConstants.COMMON_STATUS_INVALID.equals(funcInfo.getUpId())){
					//为最顶端方法
					treeNode.setUpId(funcInfo.getModuleId());
				}else{
					treeNode.setUpId(funcInfo.getUpId());
				}
			}else{
				treeNode.setUpId(funcInfo.getUpId());
			}

			treeNode.setParams(params);

			if(funcInfo.getNavIcon()!=null&&!"".equals(funcInfo.getNavIcon())){
				treeNode.setIcon(basePath+"/"+funcInfo.getNavIcon());
			}else{
				treeNode.setIcon(basePath+GlobalConstants.LOGIC_FUNC_ICON);
			}

			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/**
	 * 根据逻辑系统标识 获取逻辑系统菜单功能
	 * @param logicSysNo
	 * @return
	 */
	public List<BioneFuncInfo> getMenuByLogicSysNo(String logicSysNo) {
		List<BioneMenuInfo> menuList = getEntityListByProperty(BioneMenuInfo.class, "logicSysNo", logicSysNo);
		//对菜单进行排序
		Collections.sort(menuList, new ComparatorMenu());
		
		List<BioneFuncInfo> funcList = new ArrayList<BioneFuncInfo>();
		for (BioneMenuInfo menuInfo : menuList) {
			if (!GlobalConstants.COMMON_STATUS_VALID.equals(menuInfo.getIndexSts())) {
				BioneFuncInfo funcInfo = this.getEntityById(BioneFuncInfo.class, menuInfo.getFuncId());
				if (funcInfo != null) {
					BioneMenuInfo upMenuInfo =this.getEntityById(BioneMenuInfo.class, menuInfo.getUpId());
					if(upMenuInfo !=null){
						funcInfo.setUpId(upMenuInfo.getFuncId());
					}else{
						funcInfo.setUpId("0");
					}
					funcList.add(funcInfo);
				}
			}
		}
		return funcList;
	}

	/**
	 * 获得查询节点
	 * @param funcName
	 * @return
	 */
	public List<CommonTreeNode> searchNodes(String funcName) {
		List<BioneFuncInfo> funcInfos = this.searchFunc(funcName);
		Map map = new HashMap();
		List<CommonTreeNode> treeNodes = new ArrayList();
		for (BioneFuncInfo funcInfo : funcInfos) {
			treeNodes = getResultNode(funcInfo, map, treeNodes);
		}
		return treeNodes;
	}

	/**
	 * 根据查询的结果 构造显示数据
	 * @param funcInfo
	 * @param map
	 * @param treeNodes
	 * @return
	 */
	public List<CommonTreeNode> getResultNode(BioneFuncInfo funcInfo, Map map, List<CommonTreeNode> treeNodes) {
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		if (map.get(funcInfo.getFuncId()) == null) {
			map.put(funcInfo.getFuncId(), funcInfo);
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(funcInfo.getFuncId());
			treeNode.setText(funcInfo.getFuncName());

			treeNode.setIcon(basePath + funcInfo.getNavIcon());
			if (GlobalConstants.COMMON_STATUS_INVALID.equals(funcInfo.getUpId())) {
				//为最顶端方法
				treeNode.setUpId(funcInfo.getModuleId());
				BioneModuleInfo moduleInfo = this.getEntityById(BioneModuleInfo.class, funcInfo.getModuleId());
				if (moduleInfo != null) {
					CommonTreeNode moduleNode = new CommonTreeNode();
					moduleNode.setId(moduleInfo.getModuleId());
					moduleNode.setText(moduleInfo.getModuleName());
					moduleNode.setUpId(CommonTreeNode.ROOT_ID);
					treeNodes.add(moduleNode);
				}
			} else {
				treeNode.setUpId(funcInfo.getUpId());
				BioneFuncInfo bioneFuncInfo = this.getEntityById(BioneFuncInfo.class, funcInfo.getUpId());
				treeNodes = getResultNode(bioneFuncInfo, map, treeNodes);
			}
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	/**
	 * 查询功能节点
	 * @param funcName
	 * @return
	 */
	public List<BioneFuncInfo> searchFunc(String funcName) {
		String jql = "select obj from BioneFuncInfo obj where obj.funcName like ?0";
		List<BioneFuncInfo> funcInfos = this.baseDAO.findWithIndexParam(jql, "%" + funcName + "%");
		return funcInfos;
	}

	/**
	 * 通过逻辑系统标识获取逻辑系统信息表对象
	 * @param logicSysNo
	 * @return
	 */
	public BioneLogicSysInfo getBioneLogicSysInfoByLogicSysNo(String logicSysNo) {
		List<BioneLogicSysInfo> list = this.getEntityListByProperty(BioneLogicSysInfo.class, "logicSysNo", logicSysNo);
		return list.get(0);
	}

	/**
	 * 根据逻辑系统号获取已经授权的资源
	 * @param logicSysNo
	 * @return
	 */
	public List<BioneAuthResDef> getAuthResByLogicSysNo(String logicSysNo) {
		String jql = "select obj from BioneAuthResDef obj,BioneAuthResSysRel resSysRel where obj.resDefNo=resSysRel.id.resDefNo "
				+ " and resSysRel.id.logicSysNo =?0 ";
		List<BioneAuthResDef> authResList = this.baseDAO.findWithIndexParam(jql, logicSysNo);
		return authResList;
	}

	/**
	 * 获取该逻辑系统没有授权的资源
	 * @param logicSysNo
	 * @return
	 */
	public List<BioneAuthResDef> getAuthRes(String logicSysNo) {
		List<BioneAuthResDef> resDefs = this.getEntityList(BioneAuthResDef.class);
		List<BioneAuthResDef> authResDefs = this.getAuthResByLogicSysNo(logicSysNo);
		resDefs.removeAll(authResDefs);
		return resDefs;
	}

	/**
	 * 将授权资源组装成树
	 * @param authResByLogicSysNo
	 * @return
	 */
	public List<CommonTreeNode> AuthResToTree(List<BioneAuthResDef> authResByLogicSysNo) {
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		List<CommonTreeNode> resTree = new ArrayList<CommonTreeNode>();
		for (int i = 0; i < authResByLogicSysNo.size(); i++) {
			BioneAuthResDef authResDef = authResByLogicSysNo.get(i);
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(authResDef.getResDefNo());
			treeNode.setText(authResDef.getResName());
			treeNode.setIcon(basePath + GlobalConstants.LOGIC_AUTH_RES_ICON);
			resTree.add(treeNode);
		}
		return resTree;
	}


	/**
	 * 根据逻辑系统号获取已经授权对象
	 * @param logicSysNo
	 * @return
	 */
	public List<BioneAuthObjDef> getAuthObjByLogicSysNo(String logicSysNo) {
		String jql = "select obj from BioneAuthObjDef obj,BioneAuthObjSysRel objSysRel where obj.objDefNo=objSysRel.id.objDefNo "
				+ " and objSysRel.id.logicSysNo =?0 ";
		List<BioneAuthObjDef> authObjList = this.baseDAO.findWithIndexParam(jql, logicSysNo);
		return authObjList;
	}

	/**
	 * 获取该逻辑系统没有授权的资源
	 * @param logicSysNo
	 * @return
	 */
	public List<BioneAuthObjDef> getAuthObj(String logicSysNo) {
		List<BioneAuthObjDef> objDefs = this.getEntityList(BioneAuthObjDef.class);
		List<BioneAuthObjDef> authObjDefs = this.getAuthObjByLogicSysNo(logicSysNo);
		objDefs.removeAll(authObjDefs);
		return objDefs;
	}

	/**
	 * 将授权资源组装成树
	 * @param authObjByLogicSysNo
	 * @return
	 */
	public List<CommonTreeNode> AuthObjToTree(List<BioneAuthObjDef> authObjByLogicSysNo) {
//		String basePath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//				.getSession().getServletContext().getServletContextName();
		List<CommonTreeNode> objTree = new ArrayList<CommonTreeNode>();
		for (int i = 0; i < authObjByLogicSysNo.size(); i++) {
			BioneAuthObjDef authObjDef = authObjByLogicSysNo.get(i);
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(authObjDef.getObjDefNo());
			treeNode.setText(authObjDef.getObjDefName());
			treeNode.setIcon(basePath + GlobalConstants.LOGIC_AUTH_OBJ_ICON);
			objTree.add(treeNode);
		}
		return objTree;
	}



	/**
	 * 检查该资源或对象 是否能被移除
	 * @param logicSysNo
	 * @param authResNo
	 */
	public List checkResOrObj(String logicSysNo, String authResNo, String authObj) {
		List list = new ArrayList();
		if (logicSysNo != null && !"".equals(logicSysNo)) {
			String param = "";
			StringBuffer jql = new StringBuffer("select obj from BioneAuthObjResRel obj ");
			jql.append(" where obj.id.logicSysNo =?0 ");
			if (authResNo != null && !"".equals(authResNo)) {
				jql.append(" and obj.id.resDefNo =?1 ");
				param = authResNo;
			} else if (authObj != null && !"".equals(authObj)) {
				jql.append(" and obj.id.objDefNo =?1 ");
				param = authObj;
			} else {
				jql.append(" and 1=2 ");
			}
			list = this.baseDAO.findWithIndexParam(jql.toString(), logicSysNo, param);
		}
		return list;
	}

	/**
	 * 检查该资源 是否能被移除
	 * @param logicSysNo
	 * @param authResNo
	 * @return
	 */
	public boolean checkRes(String logicSysNo, String authResNo) {
		List list = checkResOrObj(logicSysNo, authResNo, null);
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查该对象 是否能被移除
	 * @param logicSysNo
	 * @param authObjNo
	 * @return
	 */
	public boolean checkObj(String logicSysNo, String authObjNo) {
		List list = checkResOrObj(logicSysNo, null, authObjNo);
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取首页
	 * @param logicSysNo
	 * @return
	 */
	public List<BioneFuncInfo> indexToTree(String logicSysNo) {
		String jql = "select func from BioneFuncInfo func,BioneMenuInfo obj "
				+ "where obj.logicSysNo =?0 and obj.indexSts=?1 and func.funcId=obj.funcId ";
		List<BioneFuncInfo> indexFuncInfos = this.baseDAO.findWithIndexParam(jql, logicSysNo, "1");
		if (indexFuncInfos == null)
			indexFuncInfos = new ArrayList();

		return indexFuncInfos;
	}
}
