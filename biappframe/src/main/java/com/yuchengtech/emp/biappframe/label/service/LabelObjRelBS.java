package com.yuchengtech.emp.biappframe.label.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjRel;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelObjRelPK;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelTypeInfo;
import com.yuchengtech.emp.biappframe.label.web.vo.LabelVO;
import com.yuchengtech.emp.bione.dao.SearchResult;
/**
 * <pre>
 * Title: 标签与对象关系
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
public class LabelObjRelBS extends BaseBS<BioneLabelObjRel> {
	/**
	 * 添加对象与标签关系
	 * 
	 * @param objId
	 * @param labelId
	 */
	public void addRel(String objId, List<String> labelIds) {
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = labelIds.iterator(); iterator.hasNext();) {
			String labelId = (String) iterator.next();
			BioneLabelObjRel rel = new BioneLabelObjRel();
			BioneLabelObjRelPK relPK = new BioneLabelObjRelPK();
			relPK.setLabelId(labelId);
			relPK.setObjId(objId);
			BioneLabelObjInfo objInfo = this.getObjByLabel(labelId);
			relPK.setLabelObjId(objInfo.getLabelObjId());
			rel.setId(relPK);
			this.saveEntity(rel);
		}
	}

	/**
	 * 通过标签获取标签对象
	 * 
	 * @param labelId
	 * @return
	 */
	private BioneLabelObjInfo getObjByLabel(String labelId) {
		String jql = "select t1 from BioneLabelObjInfo t1, BioneLabelTypeInfo t2, BioneLabelInfo t3 where t1.labelObjId=t2.labelObjId and t2.typeId=t3.typeId and t3.labelId=?0";
		List<BioneLabelObjInfo> infoList = baseDAO.findWithIndexParam(jql,
				labelId);
		if (infoList != null && infoList.size() > 0) {
			return infoList.get(0);
		}
		return null;
	}

	/**
	 * 通过标签对象获取该对象下所有标签
	 * 
	 * @param labelObjId
	 * @return
	 */
	public List<LabelVO> getLabelByObj(String labelObjId) {
		String jql = "select t from BioneLabelTypeInfo t where t.labelObjId=?0";
		List<BioneLabelTypeInfo> typeList = baseDAO.findWithIndexParam(jql,
				labelObjId);
		Map<String, List<BioneLabelInfo>> map = Maps.newHashMap();
		Map<String, BioneLabelTypeInfo> typeMap = Maps.newHashMap();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = typeList.iterator(); iterator.hasNext();) {
			BioneLabelTypeInfo typeInfo = (BioneLabelTypeInfo) iterator.next();
			List<BioneLabelInfo> list = Lists.newArrayList();
			map.put(typeInfo.getTypeId(), list);
			typeMap.put(typeInfo.getTypeId(), typeInfo);
		}
		String jql2 = "select t from BioneLabelInfo t where t.typeId in ?0";
		if (map.keySet().size() == 0) {
			return null;
		}
		List<BioneLabelInfo> labelList = baseDAO.findWithIndexParam(jql2,
				map.keySet());
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = labelList.iterator(); iterator.hasNext();) {
			BioneLabelInfo labelInfo = (BioneLabelInfo) iterator.next();
			if (map.containsKey(labelInfo.getTypeId())) {
				map.get(labelInfo.getTypeId()).add(labelInfo);
			}
		}
		List<LabelVO> voList = Lists.newArrayList();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			LabelVO vo = new LabelVO();
			vo.setLabelType(typeMap.get(key));
			vo.setLabel(map.get(key));
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 接口：根据标签，资源，获取资源列表
	 * 
	 * @param labelIdSet
	 *            类型为Set, 集合中不允许有相同的ID
	 * @return 符合条件的对象ID列表
	 */
	public List<String> getOBjIdByLabel(Set<String> labelIdSet) {
		Long count = (long) labelIdSet.size();
		String sql = "select t2.obj_Id from ( select t1.obj_Id, count(*) as ct from Bione_Label_Obj_Rel t1 where t1.label_Id in ?0 group by t1.obj_Id) t2 where t2.ct=?1";
		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sql, labelIdSet, count);
		List<String> result = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object str = list.get(i);
			result.add((String) str);
		}
		return result;
	}
	/**
	 * 案例2：根据id获取对象
	 * @param firstResult
	 * @param maxResult
	 * @param ids
	 * @return
	 */
	public SearchResult<BioneLabelObjInfo> getObj(int firstResult, int maxResult, List<String> ids) {
		if (ids != null && ids.size() > 0) {
			String jql = "select t from BioneLabelObjInfo t where t.labelObjId in ?0";
			SearchResult<BioneLabelObjInfo> rs = baseDAO.findPageIndexParam(firstResult, maxResult, jql, ids);
			return rs;
		} else {
			SearchResult<BioneLabelObjInfo> rs = new SearchResult<BioneLabelObjInfo>();
			rs.setResult(null);
			rs.setTotalCount(0);
			return rs;
		}
	}
	/**
	 * 案例2：获取全部对象
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public SearchResult<BioneLabelObjInfo> getAllObj(int firstResult, int maxResult) {
		String jql = "select t from BioneLabelObjInfo t";
		SearchResult<BioneLabelObjInfo> rs = baseDAO.findPageIndexParam(firstResult, maxResult, jql);
		return rs;
	}
	
	/**
	 * 接口：注册标签与资源
	 * @param objId 资源ID
	 * @param Labels 标签列表
	 */
	@Transactional(readOnly = false)
	public void updateRel(String objId, List<String> Labels) {
		String jql = "select t.id.labelId from BioneLabelObjRel t where t.id.objId=?0";
		List<String> create = Lists.newArrayList();
		List<String> list = baseDAO.findWithIndexParam(jql, objId);
		// 对比标签结果，分别找出需要添加和删除的标签
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = Labels.iterator(); iterator.hasNext();) {
			String labelId = (String) iterator.next();
			if (!list.contains(labelId)) {
				create.add(labelId);
			} else {
				list.remove(labelId);
			}
		}
		// list中剩余的标签为应删除标签
		// create中标签为应添加标签
		// 删除标签
		if (list.size() > 0) {
			String jql2 = "delete from BioneLabelObjRel t where t.id.objId=?0 and t.id.labelId in ?1";
			baseDAO.batchExecuteWithIndexParam(jql2, objId, list);
		}
		
		// 添加标签
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = create.iterator(); iterator.hasNext();) {
			// 对关系项进行判断
			String labelId = (String) iterator.next();
			BioneLabelObjRel rel = new BioneLabelObjRel();
			BioneLabelObjRelPK relPK = new BioneLabelObjRelPK();
			relPK.setLabelId(labelId);
			relPK.setObjId(objId);
			BioneLabelObjInfo objInfo = this.getObjByLabel(labelId);
			relPK.setLabelObjId(objInfo.getLabelObjId());
			rel.setId(relPK);
			this.saveEntity(rel);
		}
	}
	/**
	 * 案例1：根据对象id获取标签
	 * @param objId
	 * @return
	 */
	public List<BioneLabelObjRel> getLabels(String labelObjId, String objId) {
		String jql = "select t1 from BioneLabelObjRel t1, BioneLabelObjInfo t2 where t1.id.labelObjId=t2.labelObjId and t1.id.labelObjId=?0 and t1.id.objId=?1";
		List<BioneLabelObjRel> list = baseDAO.findWithIndexParam(jql, labelObjId, objId);
		return list;
	}
}
