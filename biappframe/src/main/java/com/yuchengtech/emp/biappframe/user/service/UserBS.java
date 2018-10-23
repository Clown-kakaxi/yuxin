package com.yuchengtech.emp.biappframe.user.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttr;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrGrp;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrVal;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.biappframe.user.web.vo.BioneUserAttrGrpVO;
import com.yuchengtech.emp.biappframe.user.web.vo.BioneUserAttrValVO;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title:用户管理业务逻辑类
 * Description: 提供用户管理相关业务逻辑处理功能，提供事务控制
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class UserBS extends BaseBS<BioneUserInfo> {

	/**
	 * 获取列表数据, 支持查询
	 * 
	 * @param firstResult
	 *            分页的开始索引
	 * @param pageSize
	 *            页面大小
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序方式
	 * @param conditionMap
	 *            搜索条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneUserInfo> getUserList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select user from BioneUserInfo user where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by user." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	/**
	 * 获取机构下拉框数据
	 */
	public List<Map<String, String>> getOrgComboxData(String orgNo) {
		StringBuffer jql = new StringBuffer(
				"select org.orgNo, org.orgName from BioneOrgInfo org where 1=1");
		if (orgNo != null) {
			jql.append(" and org.orgNo = '" + orgNo + "'");
		}
		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(),
				null);
		List<Map<String, String>> comboList = new ArrayList<Map<String, String>>();
		Map<String, String> orgMap;
		for (Object[] obj : objList) {
			orgMap = new HashMap<String, String>();
			orgMap.put("id", obj[0] != null ? obj[0].toString() : "");
			orgMap.put("text", obj[1] != null ? obj[1].toString() : "");
			comboList.add(orgMap);
		}
		return comboList;
	}

	/**
	 * 获取部门下拉框数据
	 */
	public List<Map<String, String>> getDeptComboxData(String orgNo,
			String deptNo) {
		StringBuffer jql = new StringBuffer(
				"select dept.deptNo, dept.deptName from BioneDeptInfo dept where 1=1");
		if (orgNo != null) {
			jql.append(" and dept.orgNo = '" + orgNo + "'");
		}
		if (deptNo != null) {
			jql.append(" and dept.deptNo = '" + deptNo + "'");
		}
		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(),
				null);
		List<Map<String, String>> comboxList = new ArrayList<Map<String, String>>();
		Map<String, String> deptMap = new HashMap<String, String>();
		for (Object[] obj : objList) {
			deptMap.put("id", obj[0] != null ? obj[0].toString() : "");
			deptMap.put("text", obj[1] != null ? obj[1].toString() : "");
			comboxList.add(deptMap);
		}
		return comboxList;
	}

	/**
	 * @return 用于ligerui生成表单的json格式数据
	 */
	public String getUserEditEvents() {
		StringBuffer jql = new StringBuffer(
				"select attr from BioneUserAttr attr where attr.attrSts = :parm0 order by attr.grpId, attr.orderNo asc");

		Map<String, String> values = Maps.newHashMap();
		values.put("parm0", GlobalConstants.COMMON_STATUS_VALID);
		List<BioneUserAttr> attrList = this.baseDAO.findWithNameParm(
				jql.toString(), values);

		StringBuffer lml = new StringBuffer("");
		lml.append("[");
		lml.append("{name : 'userId',type : 'hidden'}");
		int i = 1;
		for (BioneUserAttr attr : attrList) {
			lml.append(", {");
			if (attr.getFieldName() != null) {
				if (!"02".equals(attr.getElementType())) {
					lml.append("name : '" + attr.getFieldName() + "'");
				} else {
					lml.append("name : '" + attr.getFieldName() + "Name'");
				}
				if (i == 1) {
					lml.append(", group : '用户信息'");
					lml.append(", groupicon : groupicon");
				}
			}
			if (attr.getLabelName() != null) {
				lml.append(", display : '" + attr.getLabelName() + "'");
			}
			if (attr.getIsNewline() != null) {
				lml.append(", newline : " + attr.getIsNewline());
			}
			if (attr.getLabelAlign() != null) {
				lml.append(", labelAlign : '" + attr.getLabelAlign() + "'");
			}
			if (attr.getLabelWidth() != null) {
				lml.append(", labelWidth : " + attr.getLabelWidth());
			}
			if (attr.getElementAlign() != null) {
				lml.append(", align : '" + attr.getElementAlign() + "'");
			}
			if (attr.getElementWidth() != null) {
				lml.append(", width : " + attr.getElementWidth());
			}
			if (attr.getElementType() != null
					&& !"02".equals(attr.getElementType())) {
				if ("01".equals(attr.getElementType())) {
					lml.append(", type : 'text'");
				} else if ("03".equals(attr.getElementType())) {
					lml.append(", type : 'date'");
				}
			} else if ("02".equals(attr.getElementType())) {
				lml.append(", type : 'select'");
				lml.append(", comboboxName : '" + attr.getFieldName()
						+ "Combo'");
				/*
				 * if(attr.getIfExt() != null) {
				 * lml.append(", options : { ifExt : " + attr.getIfExt());
				 * //是否扩展的处理 }
				 */
				lml.append(", options : { valueFieldID : '"
						+ attr.getFieldName() + "'");
				// lml.append(", url : " + attr.getUrl());
				lml.append(", ajaxType : 'get' }");
			}

			if (attr.getFieldLength() != null || attr.getIsAllowNull() != null
					|| attr.getCheckRuleType() != null
			// || attr.getUrl() != null
			) {
				lml.append(", validate : {");
				int j = 0;
				if (attr.getFieldLength() != null) {
					lml.append("maxlength : " + attr.getFieldLength());
					++j;
				}
				if (attr.getIsAllowNull() != null) {
					if (j != 0) {
						lml.append(", ");
					}
					lml.append("required : "
							+ ("0".equals(attr.getIsAllowNull()) ? true : false));
					++j;
				}
				if (attr.getCheckRuleType() != null) {
					if (j != 0) {
						lml.append(", ");
					}
					lml.append(attr.getCheckRuleType() + " : true");
					++j;
				}
				/*
				 * if(attr.getUrl() != null) { if(j != 0) { lml.append(", "); }
				 * lml.append("remote : " + attr.getUrl()); //
				 * if(attr.getMessages() != null) { //
				 * lml.append(", messages : { remote : " + attr.getMessages +
				 * "}"); //此处将 URL 当做了远程验证来处理, 实际是用于下拉框的 // } }
				 */
				lml.append("}");
			}
			lml.append("}");
			++i;
		}
		lml.append("]");
		return lml.toString().replaceAll("\\s*", "");
	}

	/**
	 * 获取用户头像
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserIcon(String userId) {
		String jql = "select user.userIcon from BioneUserInfo user where user.userId = :userId";
		Map<String, String> values = Maps.newHashMap();
		values.put("userId", userId);
		List<Object> objList = this.baseDAO.findWithNameParm(jql, values);
		if (!CollectionUtils.isEmpty(objList)) {
			return (String) objList.get(0);
		}
		return "";
	}

	/**
	 * 验证用户密码
	 * 
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	public BioneUserInfo validUserPwd(String userId, String userPwd) {
		String jql = "select user from BioneUserInfo user where user.userId = :userId and user.userPwd = :userPwd";
		Map<String, String> values = Maps.newHashMap();
		values.put("userId", userId);
		values.put("userPwd", userPwd);
		List<BioneUserInfo> userList = this.baseDAO.findWithNameParm(jql,
				values);
		if (!CollectionUtils.isEmpty(userList)) {
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 保存用户
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public String saveUserInfo(BioneUserInfo user) {
		String id = null;
		if (user != null) {
			if (user.getUserId() != null && !"".equals(user.getUserId())) {
				// 修改操作
				id = user.getUserId();
			} else {
				user.setUserId(RandomUtils.uuid2());
			}
			BioneUserInfo userTmp = (BioneUserInfo) this.baseDAO.merge(user);
			if (userTmp != null) {
				id = userTmp.getUserId();
			}
		}
		return id;
	}

	/**
	 * 获取有效的用户属性
	 * 
	 * @return
	 */
	public List<BioneUserAttrGrpVO> getAttrsAndGrps() {
		List<BioneUserAttrGrpVO> grps = new ArrayList<BioneUserAttrGrpVO>();
		Map<String, List<BioneUserAttr>> grpAttrMap = new HashMap<String, List<BioneUserAttr>>();
		// 获取全部分组
		Map<String, Object> params = new HashMap<String, Object>();
		String jql1 = "select grp from BioneUserAttrGrp grp order by grp.orderNo asc";
		List<BioneUserAttrGrp> grpList = this.baseDAO.findWithNameParm(jql1,
				params);
		if (grpList != null && grpList.size() > 0) {
			List<String> grpIds = new ArrayList<String>();
			for (int i = 0; i < grpList.size(); i++) {
				if (!grpIds.contains(grpList.get(i).getGrpId())) {
					grpIds.add(grpList.get(i).getGrpId());
				}
			}
			if (grpIds.size() > 0) {
				// 获取用户信息属性
				String jql2 = "select attr from BioneUserAttr attr where attr.grpId in (?0) and attr.attrSts = ?1 order by attr.orderNo asc";
				List<BioneUserAttr> attrList = this.baseDAO.findWithIndexParam(
						jql2, grpIds, GlobalConstants.COMMON_STATUS_VALID);
				if (attrList != null) {
					// 组装返回对象
					for (int j = 0; j < attrList.size(); j++) {
						BioneUserAttr attrTmp = attrList.get(j);
						List<BioneUserAttr> attrsTmp = grpAttrMap.get(attrTmp
								.getGrpId());
						if (attrsTmp == null) {
							if (attrTmp.getGrpId() != null
									&& !"".equals(attrTmp.getGrpId())) {

								attrsTmp = new ArrayList<BioneUserAttr>();
								attrsTmp.add(attrTmp);
								grpAttrMap.put(attrTmp.getGrpId(), attrsTmp);
							}
						} else {
							attrsTmp.add(attrTmp);
						}
					}
					for (int k = 0; k < grpList.size(); k++) {
						BioneUserAttrGrp grpTmp = grpList.get(k);
						BioneUserAttrGrpVO grpVO = new BioneUserAttrGrpVO();
						BeanUtils.copyProperties(grpTmp, grpVO);
						List<BioneUserAttr> attrListTmp = grpAttrMap.get(grpTmp
								.getGrpId());
						grpVO.setAttrs(attrListTmp);
						if (attrListTmp != null && attrListTmp.size() > 0) {
							grps.add(grpVO);
						}
					}
				}
			}
		}
		return grps;
	}

	/**
	 * 获取指定用户扩展属性集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<BioneUserAttrValVO> getAttrValsByUser(String userId) {
		List<BioneUserAttrValVO> vals = new ArrayList<BioneUserAttrValVO>();
		if (userId != null && !"".equals(userId)) {
			String jql = "select val.id.userId,val.id.attrId,val.attrValue,attr.fieldName from BioneUserAttrVal val , BioneUserAttr attr where val.id.attrId = attr.attrId and val.id.userId = ?0 ";
			List<Object[]> objs = this.baseDAO.findWithIndexParam(jql, userId);
			if (objs != null) {
				for (int i = 0; i < objs.size(); i++) {
					Object[] oTmp = objs.get(i);
					if (oTmp.length >= 4) {
						BioneUserAttrValVO valTmp = new BioneUserAttrValVO();
						valTmp.setUserId(oTmp[0] == null || "".equals(oTmp[0]) ? ""
								: (String) oTmp[0]);
						valTmp.setAttrId(oTmp[1] == null || "".equals(oTmp[1]) ? ""
								: (String) oTmp[1]);
						valTmp.setAttrValue(oTmp[2] == null
								|| "".equals(oTmp[2]) ? "" : (String) oTmp[2]);
						valTmp.setFieldName(oTmp[3] == null
								|| "".equals(oTmp[3]) ? "" : (String) oTmp[3]);

						vals.add(valTmp);
					}
				}
			}
		}
		return vals;
	}

	/**
	 * 获取指定用户指定扩展属性
	 * 
	 * @param userId
	 * @param attrId
	 * 
	 * @return
	 */
	public BioneUserAttrVal getValByUserAndAttr(String userId, String attrId) {
		BioneUserAttrVal val = null;
		if (userId != null && !"".equals(userId) && attrId != null
				&& !"".equals(attrId)) {
			String jql = "select val from BioneUserAttrVal val where val.id.userId = ?0 and val.id.attrId = ?1 ";
			List<BioneUserAttrVal> vals = this.baseDAO.findWithIndexParam(jql,
					userId, attrId);
			if (vals != null && vals.size() > 0) {
				val = vals.get(0);
			}
		}
		return val;
	}

	/**
	 * 移除指定用户扩展属性
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteUserAttrsByUId(String userId) {
		if (userId != null && !"".equals(userId)) {
			String jql = "delete from BioneUserAttrVal val where val.id.userId = ?0";
			this.baseDAO.batchExecuteWithIndexParam(jql, userId);
		}
	}

	/**
	 * 删除用户方法,动态表单时调用
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteUserByUId(String[] userIds) {
		if (userIds != null && userIds.length > 0) {
			List<String> userIdsList = new ArrayList<String>();
			for (int i = 0; i < userIds.length; i++) {
				userIdsList.add(userIds[i]);
			}
			String jql1 = "delete from BioneUserAttrVal val where val.id.userId in (?0)";
			String jql2 = "delete from BioneUserInfo use where use.userId in (?0)";
			this.baseDAO.batchExecuteWithIndexParam(jql1, userIdsList);
			this.baseDAO.batchExecuteWithIndexParam(jql2, userIdsList);
		}
	}

	/**
	 * 根据用户标识获取用户信息
	 * 
	 * @param userNo
	 * @return
	 */
	public BioneUserInfo getUserByUserNo(String userNo) {
		BioneUserInfo user = null;
		if (userNo != null && !"".equals(userNo)) {
			String jql = "select user from BioneUserInfo user where user.userNo = ?0 ";
			List<BioneUserInfo> users = this.baseDAO.findWithIndexParam(jql,
					userNo);
			if (users != null && users.size() > 0) {
				user = users.get(0);
			}
		}
		return user;
	}

	/**
	 * 检查某一用户属性在用户信息表中是否有值
	 * 
	 * @param userId
	 * @parma fieldName
	 * @return
	 */
	public String getFieldValueByUserId(String userId, String fieldName) {
		String value = null;
		if (userId != null && !"".equals(userId) && fieldName != null
				&& !"".equals(fieldName)) {
			// 判断该field是否是BioneUserInfo中的field
			try {
				Field f = BioneUserInfo.class.getDeclaredField(fieldName);
				if (f != null) {
					String jql = "select info." + fieldName
							+ " from BioneUserInfo info where info.userId = ?0";
					List<String> fieldList = this.baseDAO.findWithIndexParam(
							jql, userId);
					if (fieldList != null && fieldList.size() > 0) {
						value = fieldList.get(0) == null ? "" : fieldList
								.get(0);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return value;
	}
}
