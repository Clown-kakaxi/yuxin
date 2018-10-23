package com.yuchengtech.emp.biappframe.label.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelTypeInfo;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;
/**
 * <pre>
 * Title:标签配置
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class LabelBS extends BaseBS<BioneLabelInfo> {

	/**
	 * 得出树节点
	 * 
	 * @param realId
	 * @param type
	 * @return
	 */
	public List<CommonTreeNode> getTreeNode(String realId, String type) {
		String basePath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getServletContextName();
		String logicSysNo = getLogicSysNo();
		List<CommonTreeNode> nodeList = Lists.newArrayList();
		if (StringUtils.isEmpty(realId) && StringUtils.isEmpty(type)) {
			String jql = "select count(t) from BioneLabelObjInfo t where t.logicSysNo=?0";
			Long num = baseDAO.findUniqueWithIndexParam(jql, logicSysNo);
			CommonTreeNode node = new CommonTreeNode();
			node.setId("root");
			node.setText("全部");
			Map<String, String> params = Maps.newHashMap();
			params.put("type", "root");
			node.setParams(params);
			node.setIcon(basePath + "/images/classics/icons/tag_yellow.png");
			if (num > 0) {
				node.setIsParent(true);
			} else {
				node.setIsParent(false);
			}
			nodeList.add(node);
		}

		if (!StringUtils.isEmpty(type) && "root".equals(type)) {
			String jql1 = "select t from BioneLabelObjInfo t where t.logicSysNo=?0 order by t.labelObjNo asc";
			String jql2 = "select distinct t.labelObjId from BioneLabelTypeInfo t, BioneLabelObjInfo b where t.labelObjId=b.labelObjId and b.logicSysNo=?0";
			List<BioneLabelObjInfo> list = baseDAO.findWithIndexParam(jql1,
					logicSysNo);
			List<Object> pList = baseDAO.findWithIndexParam(jql2, logicSysNo);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					BioneLabelObjInfo entity = list.get(i);
					CommonTreeNode node = new CommonTreeNode();
					node.setId("obj_" + entity.getLabelObjId());
					node.setText(entity.getLabelObjName());
					Map<String, String> params = Maps.newHashMap();
					params.put("type", "obj");
					params.put("realId", entity.getLabelObjId());
					node.setParams(params);
					node.setIsParent(false);
					if (pList != null && pList.contains(entity.getLabelObjId())) {
						node.setIsParent(true);
					}
					node.setIcon(basePath
							+ "/images/classics/icons/tag_red.png");
					nodeList.add(node);
				}
			}
		}

		if (!StringUtils.isEmpty(realId) && !StringUtils.isEmpty(type)) {
			if ("obj".equals(type)) {
				String jql1 = "select t from BioneLabelTypeInfo t, BioneLabelObjInfo b where t.labelObjId=b.labelObjId and b.logicSysNo=?0 and t.labelObjId=?1 order by t.typeId desc";
				List<BioneLabelTypeInfo> list1 = baseDAO.findWithIndexParam(
						jql1, logicSysNo, realId);
				if (list1 != null) {
					for (int i = 0; i < list1.size(); i++) {
						BioneLabelTypeInfo entity = list1.get(i);
						CommonTreeNode node = new CommonTreeNode();
						node.setId("type_" + entity.getTypeId());
						node.setText(entity.getTypeName());
						Map<String, String> params = Maps.newHashMap();
						params.put("type", "type");
						params.put("realId", entity.getTypeId());
						node.setParams(params);
						node.setIsParent(false);
						node.setIcon(basePath
								+ "/images/classics/icons/tag_green.png");
						nodeList.add(node);
					}
				}
			}

			if ("type".equals(type)) {
				String jql = "select t from BioneLabelInfo t where t.typeId=?0 order by t.labelName asc";
				List<BioneLabelInfo> list = baseDAO.findWithIndexParam(jql,
						realId);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						BioneLabelInfo entity = list.get(i);
						CommonTreeNode node = new CommonTreeNode();
						node.setId("label_" + entity.getLabelId());
						node.setText(entity.getLabelName());
						Map<String, String> params = Maps.newHashMap();
						params.put("type", "label");
						params.put("realId", entity.getLabelId());
						node.setParams(params);
						node.setIsParent(false);
						node.setIcon(basePath
								+ "/images/classics/icons/tag_blue.png");
						nodeList.add(node);
					}
				}
			}
		}
		return nodeList;
	}

	/**
	 * 得到标签对象内容列表
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public SearchResult<BioneLabelObjInfo> findObjPage(int firstResult,
			int pageSize, Map<String, Object> condition) {
		StringBuffer jql = new StringBuffer();
		jql.append("select t from BioneLabelObjInfo t where t.logicSysNo=:logicSysNo");
		String where = (String) condition.get("jql");
		if (!StringUtils.isEmpty(where)) {
			jql.append(" and ").append(where);
		}
		jql.append(" order by t.labelObjNo asc");
		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) condition
				.get("params");
		values.put("logicSysNo", getLogicSysNo());
		SearchResult<BioneLabelObjInfo> sr = baseDAO.findPageWithNameParam(
				firstResult, pageSize, jql.toString(), values);
		return sr;
	}

	/**
	 * 得到标签类型内容列表
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param condition
	 * @param realId
	 * @return
	 */
	public SearchResult<BioneLabelTypeInfo> findTypePage(int firstResult,
			int pageSize, Map<String, Object> condition, String realId) {
		StringBuffer jql = new StringBuffer();
		jql.append("select t from BioneLabelTypeInfo t, BioneLabelObjInfo b where t.labelObjId=b.labelObjId and t.labelObjId=:realId and b.logicSysNo=:logicSysNo");
		String where = (String) condition.get("jql");
		if (!StringUtils.isEmpty(where)) {
			jql.append(" and ").append(where);
		}
		jql.append(" order by t.typeId asc");
		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) condition
				.get("params");
		values.put("realId", realId);
		values.put("logicSysNo", getLogicSysNo());
		SearchResult<BioneLabelTypeInfo> sr = baseDAO.findPageWithNameParam(
				firstResult, pageSize, jql.toString(), values);
		return sr;
	}

	/**
	 * 得到标签内容列表
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param condition
	 * @param realId
	 * @return
	 */
	public SearchResult<BioneLabelInfo> findLabelPage(int firstResult,
			int pageSize, Map<String, Object> condition, String realId) {
		StringBuffer jql = new StringBuffer();
		jql.append("select t from BioneLabelInfo t where t.typeId=:realId");
		String where = (String) condition.get("jql");
		if (!StringUtils.isEmpty(where)) {
			jql.append(" and ").append(where);
		}
		jql.append(" order by t.labelId asc");
		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) condition
				.get("params");
		values.put("realId", realId);
		SearchResult<BioneLabelInfo> sr = baseDAO.findPageWithNameParam(
				firstResult, pageSize, jql.toString(), values);
		return sr;
	}

	/**
	 * 获取系统逻辑号
	 * 
	 * @return
	 */
	public String getLogicSysNo() {
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		return logicSysNo;
	}

	/**
	 * 删除标签对象
	 * 
	 * @param id
	 */
	public void deleteObj(String id) {
		JSONArray ja = JSONArray.fromObject(id);
		List<String> idList = Lists.newArrayList();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = ja.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			idList.add(str);
		}
		String jql1 = "delete from BioneLabelObjInfo t where t.labelObjId in ?0";
		String jql2 = "delete from BioneLabelTypeInfo t where t.labelObjId in ?0";
		String jql3 = "delete from BioneLabelInfo t where t.typeId in (select t2.typeId from BioneLabelTypeInfo t2 where t2.labelObjId in (?0))";
		String jql4 = "delete from BioneLabelObjRel t where t.id.labelObjId in ?0";
		baseDAO.batchExecuteWithIndexParam(jql4, idList);
		baseDAO.batchExecuteWithIndexParam(jql3, idList);
		baseDAO.batchExecuteWithIndexParam(jql2, idList);
		baseDAO.batchExecuteWithIndexParam(jql1, idList);
	}

	/**
	 * 删除标签类型
	 * 
	 * @param id
	 */
	public void deleteType(String id) {
		JSONArray ja = JSONArray.fromObject(id);
		List<String> idList = Lists.newArrayList();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = ja.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			idList.add(str);
		}
		String jql1 = "delete from BioneLabelTypeInfo t where t.typeId in ?0";
		String jql2 = "delete from BioneLabelInfo t where t.typeId in ?0";
		String jql3 = "delete from BioneLabelObjRel t where t.id.labelId in (select t2.labelId from BioneLabelInfo t2 where t2.typeId in (?0))";
		baseDAO.batchExecuteWithIndexParam(jql3, idList);
		baseDAO.batchExecuteWithIndexParam(jql2, idList);
		baseDAO.batchExecuteWithIndexParam(jql1, idList);
	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 */
	public void deleteLabel(String id) {
		JSONArray ja = JSONArray.fromObject(id);
		List<String> idList = Lists.newArrayList();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = ja.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			idList.add(str);
		}
		String jql1 = "delete from BioneLabelInfo t where t.labelId in ?0";
		String jql2 = "delete from BioneLabelObjRel t where t.id.labelId in ?0";
		baseDAO.batchExecuteWithIndexParam(jql2, idList);
		baseDAO.batchExecuteWithIndexParam(jql1, idList);
	}

	/**
	 * 是否存在标签对象标识
	 * 
	 * @param id
	 * @param labelObjId
	 * @return
	 */
	public boolean isContainObj(String id, String labelObjId) {
		String jql = "";
		Long obj = 0L;
		if (StringUtils.isEmpty(labelObjId)) {
			jql = "select count(t) from BioneLabelObjInfo t where t.labelObjNo=?0";
			obj = baseDAO.findUniqueWithIndexParam(jql, id);
		} else {
			jql = "select count(t) from BioneLabelObjInfo t where t.labelObjNo=?0 and t.labelObjId<>?1";
			obj = baseDAO.findUniqueWithIndexParam(jql, id, labelObjId);
		}
		if (obj != null && obj > 0L) {
			return true;
		}
		return false;
	}

	/**
	 * 是否存在标签对象名
	 * 
	 * @param name
	 * @param labelObjId
	 * @return
	 */
	public boolean isContainObjName(String name, String labelObjId) {
		String jql = "";
		Long obj = 0L;
		if (StringUtils.isEmpty(labelObjId)) {
			jql = "select count(t) from BioneLabelObjInfo t where t.labelObjName=?0";
			obj = baseDAO.findUniqueWithIndexParam(jql, name);
		} else {
			jql = "select count(t) from BioneLabelObjInfo t where t.labelObjName=?0 and t.labelObjId<>?1";
			obj = baseDAO.findUniqueWithIndexParam(jql, name, labelObjId);
		}
		if (obj != null && obj > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否存在标签类型名
	 * 
	 * @param id
	 * @param typeName
	 * @param labelObjId
	 * @return
	 */
	public boolean isContainTypeName(String id, String typeName,
			String labelObjId) {
		String jql = "";
		Long obj = 0L;
		if (StringUtils.isEmpty(id)) {
			jql = "select count(t) from BioneLabelTypeInfo t where t.typeName=?0 and t.labelObjId=?1";
			obj = baseDAO.findUniqueWithIndexParam(jql, typeName, labelObjId);
		} else {
			jql = "select count(t) from BioneLabelTypeInfo t where t.typeName=?0 and t.labelObjId=?1 and t.typeId<>?2";
			obj = baseDAO.findUniqueWithIndexParam(jql, typeName, labelObjId,
					id);
		}
		if (obj > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否存在标签名
	 * 
	 * @param id
	 * @param labelName
	 * @param typeId
	 * @return
	 */
	public boolean isContainLabelName(String id, String labelName, String typeId) {
		String jql = "";
		Long obj = 0L;
		if (StringUtils.isEmpty(id)) {
			jql = "select count(t) from BioneLabelInfo t where t.labelName=?0 and t.typeId=?1";
			obj = baseDAO.findUniqueWithIndexParam(jql, labelName, typeId);
		} else {
			jql = "select count(t) from BioneLabelInfo t where t.labelName=?0 and t.typeId=?1 and t.labelId<>?2";
			obj = baseDAO.findUniqueWithIndexParam(jql, labelName, typeId, id);
		}
		if (obj > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 接口: 根据标签类型获取标签
	 * @param typeId 标签类型
	 * @return 标签列表
	 */
	public List<BioneLabelInfo> getLabelsByType(String typeId) {
		String jql = "select t from BioneLabelInfo t where t.typeId=?0";
		List<BioneLabelInfo> list = baseDAO.findWithIndexParam(jql, typeId);
		return list;
	}
	
	/**
	 * 接口: 根据标签对象获取标签类型
	 * @param objId
	 * @return
	 */
	public List<BioneLabelTypeInfo> getLabelTypesByObj(String objId) {
		String jql = "select t from BioneLabelTypeInfo t where t.labelObjId=?0";
		List<BioneLabelTypeInfo> list = baseDAO.findWithIndexParam(jql, objId);
		return list;
	}
	/**
	 * 接口：根据标签对象获取标签树
	 * @param objId
	 * @return
	 */
	public List<CommonTreeNode> getLabelTree(String objId) {
		String basePath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getServletContextName();
		String jql1 = "select t2 from BioneLabelTypeInfo t2 where t2.labelObjId=?0";
		String jql2 = "select t1 from BioneLabelInfo t1 where t1.typeId in (select t2.typeId from BioneLabelTypeInfo t2 where t2.labelObjId=?0)";
		List<BioneLabelTypeInfo> typeLst = baseDAO.findWithIndexParam(jql1, objId);
		List<BioneLabelInfo> labelLst = baseDAO.findWithIndexParam(jql2, objId);
		List<CommonTreeNode> tree = Lists.newArrayList();
		// 以TypeId为KEY存放对应标签
		Map<String, List<CommonTreeNode>> labelMap = Maps.newHashMap();
		for (Iterator<BioneLabelInfo> iterator = labelLst.iterator(); iterator
				.hasNext();) {
			BioneLabelInfo labelInfo = (BioneLabelInfo) iterator.next();
			CommonTreeNode node = new CommonTreeNode();
			String typeId = labelInfo.getTypeId();
			if (labelMap.get(typeId) == null) {
				List<CommonTreeNode> info = Lists.newArrayList();
				labelMap.put(typeId, info);
			}
			node.setId("label_" + labelInfo.getLabelId());
			node.setText(labelInfo.getLabelName());
			Map<String, String> params = Maps.newHashMap();
			params.put("type", "label");
			params.put("realId", labelInfo.getLabelId());
			node.setParams(params);
			node.setIsParent(false);
			node.setIcon(basePath
					+ "/images/classics/icons/tag_blue.png");
			labelMap.get(typeId).add(node);
		}
		
		for (Iterator<BioneLabelTypeInfo> iterator = typeLst.iterator(); iterator
				.hasNext();) {
			BioneLabelTypeInfo typeInfo = (BioneLabelTypeInfo) iterator.next();
			CommonTreeNode node = new CommonTreeNode();
			node.setId("type_" + typeInfo.getTypeId());
			node.setText(typeInfo.getTypeName());
			Map<String, String> params = Maps.newHashMap();
			params.put("type", "type");
			params.put("realId", typeInfo.getTypeId());
			node.setParams(params);
			node.setIsParent(true);
			node.setIcon(basePath
					+ "/images/classics/icons/tag_green.png");
			node.setChildren(labelMap.get(typeInfo.getTypeId()));
			tree.add(node);
		}
		return tree;
	}
}
