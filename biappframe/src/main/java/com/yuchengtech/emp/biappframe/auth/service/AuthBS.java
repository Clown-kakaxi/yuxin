package com.yuchengtech.emp.biappframe.auth.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjDef;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRel;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjUserRel;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpObjRel;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneRoleInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneDataRuleInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAdminUserInfo;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAuthObjSysRel;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAuthResSysRel;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:授权管理业务逻辑类
 * Description: 提供授权管理相关业务逻辑处理功能，提供事务控制
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:1.01   修改人：songxf  修改日期: 2012-07-05    修改内容:增加逻辑系统支持
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class AuthBS extends BaseBS<Object> {
	protected static Logger log = LoggerFactory.getLogger(AuthBS.class);

	/**
	 * 根据授对象类型，查找用户被授权的授权对象的Id列表
	 * 
	 * @param logicSysNo
	 * @param authObjType
	 * @param userId
	 * @return
	 */
	public List<String> findAuthObjectIdByType(String logicSysNo,
			String authObjType, String userId) {

		String jql = "select rel.id.objId from  BioneAuthObjUserRel rel where rel.id.logicSysNo=?0 and rel.id.objDefNo=?1 and rel.id.userId=?2 ";

		return this.baseDAO.findWithIndexParam(jql, logicSysNo, authObjType,
				userId);

	}

	/**
	 * 查找当前用户关联的授权对象集合
	 * 
	 * @param logicSysNo
	 * @param userId
	 * @return
	 */
	public List<BioneAuthObjUserRel> findAuthObjUserRelList(String logicSysNo,
			String userId) {

		String jql = "select rel from  BioneAuthObjUserRel rel where rel.id.logicSysNo=?0 and rel.id.userId=?1 ";

		return this.baseDAO.findWithIndexParam(jql, logicSysNo, userId);

	}

	/**
	 * 查找当前用户关联的授权对象分类Map
	 * 
	 * @param logicSysNo
	 * @param userId
	 * @return
	 */
	public Map<String, List<String>> findAuthObjUserRelMap(String logicSysNo,
			String userId) {

		Map<String, List<String>> authObjUserRelMap = Maps.newHashMap();
		String jql = "select obj from BioneAuthObjDef obj,BioneAuthObjSysRel rel where obj.objDefNo=rel.id.objDefNo and rel.id.logicSysNo=?0";

		// 所有的授权对象定义
		List<BioneAuthObjDef> authObjList = this.baseDAO.findWithIndexParam(
				jql, logicSysNo);

		if (authObjList != null) {

			for (BioneAuthObjDef authObjDef : authObjList) {
				if (!authObjDef.getObjDefNo().equals(
						GlobalConstants.AUTH_OBJ_DEF_ID_USER)) {
					IAuthObject authObj = null;
					List<String> authObjIdList = null;
					try {
						authObj = SpringContextHolder.getBean(authObjDef
								.getBeanName());
						authObjIdList = authObj.doGetAuthObjectIdListOfUser();
					} catch (Exception e) {
						log.error("授权对象[" + authObjDef.getBeanName() + "]定义错误!");
						continue;
					}
					if (!CollectionUtils.isEmpty(authObjIdList))
						authObjUserRelMap.put(authObjDef.getObjDefNo(),
								authObjIdList);
				}
			}
		}

		return authObjUserRelMap;

	}

	/**
	 * 查找某类授权对象所可以访问的某类授权资源的ID集合
	 * 
	 * @param logicSysNo
	 * @param resDefNo
	 *            授权资源标识
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param authObjId
	 *            授权对象Id
	 * @return
	 */
	public List<String> findAuthResIdListByType(String logicSysNo,
			String resDefNo, String authObjDefNo, List<String> authObjIds) {

		if (CollectionUtils.isEmpty(authObjIds))
			return null;

		String jql = "select rel.id.resId from  BioneAuthObjResRel rel where rel.id.logicSysNo=?0 and rel.id.resDefNo=?1 and  rel.id.objDefNo=?2 and rel.id.objId in (?3) ";

		return this.baseDAO.findWithIndexParam(jql, logicSysNo, resDefNo,
				authObjDefNo, authObjIds);

	}

	/**
	 * 查找当前用户的授权关系
	 * 
	 * @param logicSysNo
	 * @param objDefNo
	 *            授权对象标识
	 * @param authObjIds
	 *            授权对象id
	 * @return
	 */
	public List<BioneAuthObjResRel> findCurrentUserAuthObjResRels(
			String logicSysNo, String objDefNo, List<String> objIds) {

		if (CollectionUtils.isEmpty(objIds))
			return null;

		String jql = "select rel from  BioneAuthObjResRel rel where rel.id.logicSysNo=?0 and rel.id.objDefNo=?1 and rel.id.objId in (?2) ";

		return this.baseDAO.findWithIndexParam(jql, logicSysNo, objDefNo,
				objIds);

	}

	/**
	 * 查找当前用户所属的授权组Id
	 * 
	 * @return
	 */
	public List<String> findAuthGroupIdOfCurrentUser(String logicSysNo) {

		List<String> authGorupIdList = Lists.newArrayList();

		List<BioneAuthObjgrpObjRel> authObjGrpRelList = this.baseDAO
				.findWithIndexParam(
						"select rel from BioneAuthObjgrpObjRel rel where rel.id.logicSysNo=?0 order by rel.id.objgrpId,rel.id.objDefNo",
						logicSysNo);

		// 将授权组和授权对象的关系按照 授权组Id-授权对象标识-授权对象Id分组
		Map<String, Map<String, List<String>>> authGroupMap = Maps.newHashMap();

		if (authObjGrpRelList != null) {

			String objGroupId = null;
			String objDefNo = null;
			Map<String, List<String>> objDefMap = null;
			List<String> objIdList = null;

			for (BioneAuthObjgrpObjRel rel : authObjGrpRelList) {

				objGroupId = rel.getId().getObjgrpId();
				objDefNo = rel.getId().getObjDefNo();
				objDefMap = authGroupMap.get(objGroupId);

				// 授权组Id-所有授权对象分类集合
				if (objDefMap == null) {
					objDefMap = Maps.newHashMap();
					authGroupMap.put(objGroupId, objDefMap);
				}

				objIdList = objDefMap.get(objDefNo);

				// 授权对象分类标识-授权对象集合
				if (objIdList == null) {
					objIdList = Lists.newArrayList();
					objDefMap.put(objDefNo, objIdList);
				}

				objIdList.add(rel.getId().getObjId());
			}

			BiOneUser currentUser = BiOneSecurityUtils.getCurrentUserInfo();
			// 当前用户与授权对象的关系
			Map<String, List<String>> userAuthObjMap = currentUser
					.getAuthObjMap();

			Iterator<String> it = authGroupMap.keySet().iterator();

			boolean bool = false;

			while (it.hasNext()) {

				bool = true;
				objGroupId = it.next();
				objDefMap = authGroupMap.get(objGroupId);

				Iterator<String> innerIt = objDefMap.keySet().iterator();

				List<String> userAuthObjIdList = null;
				while (bool && innerIt.hasNext()) {

					bool = false;

					objDefNo = innerIt.next();
					objIdList = objDefMap.get(objDefNo);

					userAuthObjIdList = userAuthObjMap.get(objDefNo);

					if (userAuthObjIdList != null) {

						for (String authObjId : userAuthObjIdList) {

							if (objIdList.contains(authObjId)) {
								bool = true;
								break;
							}

						}
					}

				}

				// 当前用户满足此授权组的条件
				if (bool)
					authGorupIdList.add(objGroupId);

			}

		}

		return authGorupIdList;

	}

	/**
	 * 查找所有的资源操作信息，以Map形式返回
	 * 
	 * @param logicSysNo
	 * @return
	 */
	public Map<String, BioneResOperInfo> findAllResOperInfoMap() {

		Map<String, BioneResOperInfo> retMap = Maps.newHashMap();
		List<BioneResOperInfo> resOperInfos = this
				.getEntityList(BioneResOperInfo.class);

		if (resOperInfos != null) {

			for (BioneResOperInfo resOperInfo : resOperInfos)
				retMap.put(resOperInfo.getOperId(), resOperInfo);
		}

		return retMap;
	}

	/**
	 * 查找所有数据规则信息，以Map形式返回
	 * 
	 * @return
	 */
	public Map<String, BioneDataRuleInfo> findAllDataRuleInfoMap() {

		Map<String, BioneDataRuleInfo> retMap = Maps.newHashMap();
		List<BioneDataRuleInfo> dataRuleInfos = this
				.getEntityList(BioneDataRuleInfo.class);

		if (dataRuleInfos != null) {

			for (BioneDataRuleInfo dataRuleInfo : dataRuleInfos)
				retMap.put(dataRuleInfo.getDataRuleId(), dataRuleInfo);
		}

		return retMap;
	}

	/**
	 * 查找所有菜单信息，以Map形式返回
	 * 
	 * @return
	 */
	public Map<String, BioneFuncInfo> findAllMenuInfoMap() {

		Map<String, BioneFuncInfo> retMap = Maps.newHashMap();
		List<BioneFuncInfo> menuInfos = this.getEntityList(BioneFuncInfo.class);

		if (menuInfos != null) {

			for (BioneFuncInfo menuInfo : menuInfos)
				retMap.put(menuInfo.getFuncId(), menuInfo);
		}

		return retMap;
	}

	/**
	 * 查找所有菜单访问路径列表，路径不包含参数部分
	 * 
	 * @return
	 */
	public List<String> findAllMenuNavPathList() {

		List<String> menuNavPathList = Lists.newArrayList();
		List<BioneFuncInfo> menuInfos = this.getEntityList(BioneFuncInfo.class);

		if (menuInfos != null) {

			String navPath = null;
			for (BioneFuncInfo menuInfo : menuInfos) {

				navPath = menuInfo.getNavPath();

				// 去掉URL后面的参数
				navPath = StringUtils.substringBefore(navPath, "?");

				// 对路径进行格式化处理
				navPath = WebUtils.normalize(navPath);

				menuNavPathList.add(navPath);
			}

		}

		return menuNavPathList;
	}

	/**
	 * 查找当前用户有效的授权机构id
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<String> findValidAuthOrgIdOfUser(String loigcSysNo,
			String authObjDefNo, String userId) {

		// 查找机构状态为生效的机构
		String jql = "select org.orgId from BioneAuthObjUserRel rel,BioneOrgInfo org where rel.id.userId=?0 and rel.id.objDefNo=?1 and rel.id.objId=org.orgId and org.orgSts=?2 and org.logicSysNo=?3  and rel.id.logicSysNo=?3";

		return this.baseDAO.findWithIndexParam(jql, userId, authObjDefNo,
				GlobalConstants.COMMON_STATUS_VALID, loigcSysNo);
	}

	/**
	 * 查找当前用户有效的授权部门id
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<String> findValidAuthDeptIdOfUser(String loigcSysNo,
			String authObjDefNo, String userId) {

		String jql = "select dept.deptId from BioneAuthObjUserRel rel,BioneDeptInfo dept where rel.id.userId=?0 and rel.id.objDefNo=?1 and rel.id.objId=dept.deptId and dept.deptSts=?2 and dept.logicSysNo=?3  and rel.id.logicSysNo=?3";

		return this.baseDAO.findWithIndexParam(jql, userId, authObjDefNo,
				GlobalConstants.COMMON_STATUS_VALID, loigcSysNo);
	}

	/**
	 * 查找当前用户有效的角色id
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<String> findValidAuthRoleIdOfUser(String loigcSysNo,
			String authObjDefNo, String userId) {

		// 查找机构状态为生效的机构
		String jql = "select role.roleId from BioneAuthObjUserRel rel,BioneRoleInfo role where rel.id.userId=?0 and rel.id.objDefNo=?1 and rel.id.objId=role.roleId and role.roleSts=?2 and role.logicSysNo=?3 and rel.id.logicSysNo=?3";

		return this.baseDAO.findWithIndexParam(jql, userId, authObjDefNo,
				GlobalConstants.COMMON_STATUS_VALID, loigcSysNo);
	}

	/**
	 * 查找当前用户有效的授权组id
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<String> findValidAuthObjGrpIdOfUser(String loigcSysNo,
			String authObjDefNo, String userId) {

		// 查找机构状态为生效的机构
		String jql = "select grp.objgrpId from BioneAuthObjUserRel rel,BioneAuthObjgrpInfo grp where rel.id.userId=?0 and rel.id.objDefNo=?1 and rel.id.objId=grp.objgrpId and grp.objgrpSts=?2 and grp.logicSysNo=?3 and rel.id.logicSysNo=?3";

		return this.baseDAO.findWithIndexParam(jql, userId, authObjDefNo,
				GlobalConstants.COMMON_STATUS_VALID, loigcSysNo);
	}

	/**
	 * 查找当前用户有效的授权机构对象
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @return
	 */
	public List<BioneOrgInfo> findValidAuthOrgOfUser(String loigcSysNo) {

		// 查找机构状态为生效的机构
		String jql = "select org from BioneOrgInfo org where org.logicSysNo=?0 and org.orgSts=?1";

		return this.baseDAO.findWithIndexParam(jql, loigcSysNo,
				GlobalConstants.COMMON_STATUS_VALID);
	}

	/**
	 * 查找当前用户有效的授权角色对象
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @return
	 */
	public List<BioneAuthObjgrpInfo> findValidAuthObjGrpOfUser(String loigcSysNo) {

		// 查找机构状态为生效的角色
		String jql = "select obj from BioneAuthObjgrpInfo obj where obj.logicSysNo=?0";

		return this.baseDAO.findWithIndexParam(jql, loigcSysNo);
	}

	/**
	 * 查找当前用户有效的授权部门对象
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @return
	 */
	public List<BioneDeptInfo> findValidAuthDeptOfUser(String loigcSysNo) {

		// 查找机构状态为生效的角色
		String jql = "select dept from BioneDeptInfo dept where dept.logicSysNo=?0 and dept.deptSts=?1";

		return this.baseDAO.findWithIndexParam(jql, loigcSysNo,
				GlobalConstants.COMMON_STATUS_VALID);
	}

	/**
	 * 查找当前用户有效的授权组对象
	 * 
	 * @param loigcSysNo
	 *            逻辑系统
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<BioneRoleInfo> findValidAuthRoleOfUser(String loigcSysNo) {

		// 查找机构状态为生效的角色
		String jql = "select role from BioneRoleInfo role where role.logicSysNo=?0 and role.roleSts=?1 ";

		return this.baseDAO.findWithIndexParam(jql, loigcSysNo,
				GlobalConstants.COMMON_STATUS_VALID);
	}

	/**
	 * 查找当前用户有效的授权用户id
	 * 
	 * @return
	 */
	public List<String> findValidAuthUserIdOfUser() {

		// 查找状态为启用并且不包含内置的用户
		String jql = "select usr.userId from BioneUserInfo usr where usr.userSts=?0 and usr.isBuiltin=?1 or usr.isBuiltin is null order by usr.userName";

		return this.baseDAO.findWithIndexParam(jql,
				GlobalConstants.COMMON_STATUS_VALID,
				GlobalConstants.COMMON_STATUS_INVALID);
	}

	/**
	 * 查找当前用户有效的授权用户对象集合
	 * 
	 * @return
	 */
	public List<BioneUserInfo> findValidAuthUserObjOfUser() {

		// 查找机构状态为启用并且不包含内置的用户
		String jql = "select usr from BioneUserInfo usr where usr.userSts=?0 and usr.isBuiltin=?1 or usr.isBuiltin is null order by usr.userName";

		return this.baseDAO.findWithIndexParam(jql,
				GlobalConstants.COMMON_STATUS_VALID,
				GlobalConstants.COMMON_STATUS_INVALID);
	}

	/**
	 * 查找具体某个授权对象的实现类
	 * 
	 * @param authObjDefNo
	 *            授权对象标识
	 * @return
	 */
	public List<String> findAuthObjBeanNameByType(String authObjDefNo) {

		// 查找授权定义对象实现类
		String jql = "select beanName from BioneAuthObjDef def where def.objDefNo=?0";

		return this.baseDAO.findWithIndexParam(jql, authObjDefNo);
	}

	/**
	 * 查找具体某个授权资源的实现类
	 * 
	 * @param resObjDefNo
	 *            授权资源标识
	 * @return
	 */
	public List<String> findResObjBeanNameByType(String resObjDefNo) {

		// 查找授权资源实现类
		String jql = "select beanName from BioneAuthResDef def where def.resDefNo=?0";

		return this.baseDAO.findWithIndexParam(jql, resObjDefNo);
	}

	/**
	 * 查找有效的授权资源对象集合
	 * 
	 * @param logicSysNo
	 *            逻辑系统标志
	 * @return
	 */
	public List<Object[]> findValidMenu(String logicSysNo) {

		// 获取有效的菜单
		String jql = "select menu.menuId,menu.funcId,func.funcName,menu.upId,func.navIcon from BioneMenuInfo menu,BioneFuncInfo func where menu.funcId = func.funcId and menu.logicSysNo=?0 and func.funcSts=?1 order by func.orderNo,func.upId asc";

		return this.baseDAO.findWithIndexParam(jql, logicSysNo,
				GlobalConstants.COMMON_STATUS_VALID);
	}

	/**
	 * 查询某个授权对象所可以访问的授权资源的关系对象集合
	 * 
	 * @param logicSysNo
	 * @param authObjDefNo
	 *            授权对象标识
	 * @param authObjId
	 *            授权对象Id
	 * @return
	 */
	public List<BioneAuthObjResRel> findAuthResListByType(String logicSysNo,
			String authObjDefNo, String authObjId) {

		String jql = "select rel from BioneAuthObjResRel rel where rel.id.logicSysNo=?0 and rel.id.objDefNo=?1 and rel.id.objId=?2";

		return this.baseDAO.findWithIndexParam(jql, logicSysNo, authObjDefNo,
				authObjId);

	}

	/**
	 * 查询某个授权资源的所有操作许可
	 * 
	 * @param resDefNo
	 * 
	 * @param resNos
	 *            授权资源标识
	 * @return
	 */
	public List<BioneResOperInfo> findResOperList(String resDefNo,
			List<String> resNos) {

		String jql = "select oper from BioneResOperInfo oper where oper.resNo in (?0) and oper.resDefNo = ?1";

		return this.baseDAO.findWithIndexParam(jql, resNos, resDefNo);

	}

	/**
	 * 查询用户是否是逻辑系统管理员
	 * 
	 * @param userid
	 * @param logicsysno
	 * @return
	 */
	public boolean findAdminUserInfo(String userid, String logicsysno) {

		String jql = "select user from BioneAdminUserInfo user,BioneLogicSysInfo sys where user.id.logicSysId=sys.logicSysId and user.id.userId=?0 and sys.logicSysNo=?1 and user.userSts=?2";

		List<BioneAdminUserInfo> result = this.baseDAO.findWithIndexParam(jql,
				userid, logicsysno, GlobalConstants.COMMON_STATUS_VALID);
		if (result != null && result.size() == 1) {
			return true;
		}
		return false;

	}

	/**
	 * 获取当前逻辑系统有效的授权对象标识
	 * 
	 * @param userid
	 * @param logicsysno
	 * @return
	 */
	public List<String> getObjDefNoBySys(String logicSysNo) {
		List<String> returnLst = new ArrayList<String>();
		if (logicSysNo != null && !"".equals(logicSysNo)) {
			// 若不是超级逻辑系统
			String jql = "select rel from BioneAuthObjSysRel rel where rel.id.logicSysNo=?0";
			List<BioneAuthObjSysRel> sysRels = this.baseDAO.findWithIndexParam(
					jql, logicSysNo);
			if (sysRels != null) {
				for (int i = 0; i < sysRels.size(); i++) {
					if (!returnLst.contains(sysRels.get(i).getId()
							.getObjDefNo())) {
						returnLst.add(sysRels.get(i).getId().getObjDefNo());
					}
				}
			}
		}
		return returnLst;
	}

	/**
	 * 获取当前逻辑系统有效的资源对象标识
	 * 
	 * @param userid
	 * @param logicsysno
	 * @return
	 */
	public List<String> getResDefNoBySys(String logicSysNo) {
		List<String> returnLst = new ArrayList<String>();

		if (logicSysNo != null && !"".equals(logicSysNo)) {
			// 若不是超级逻辑系统
			String jql = "select rel from BioneAuthResSysRel rel where rel.id.logicSysNo=?0";
			List<BioneAuthResSysRel> rels = this.baseDAO.findWithIndexParam(
					jql, logicSysNo);
			if (rels != null) {
				for (int i = 0; i < rels.size(); i++) {
					if (!returnLst.contains(rels.get(i).getId().getResDefNo())) {
						returnLst.add(rels.get(i).getId().getResDefNo());
					}
				}
			}

		}
		return returnLst;
	}

	/**
	 * 根据menuId 获取 功能
	 * @param menuId
	 * @return
	 */
	public BioneFuncInfo findFuncByMenuId(String menuId) {
		String jql = "select func from BioneFuncInfo func where func.funcId in (" +
				"	select menuInfo.funcId from BioneMenuInfo menuInfo where menuInfo.menuId =?0 )";
		List<BioneFuncInfo> funcInfos =  this.baseDAO.findWithIndexParam(jql, menuId);
		if(funcInfos.size() == 1){
			return funcInfos.get(0);
		}else{
			return null;
		}
		
	}

}
