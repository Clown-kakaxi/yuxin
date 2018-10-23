package com.yuchengtech.emp.biappframe.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttr;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrGrp;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserAttrVal;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Service("userAttrBS")
@Transactional(readOnly = true)
public class UserAttrBS extends BaseBS<BioneUserAttr> {

	/**
	 * 分页查询用户配置属性信息
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneUserAttr> getUserAttrInfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		String jql = "SELECT userAttr FROM BioneUserAttr userAttr WHERE 1=1 ";

		if (!conditionMap.get("jql").equals("")) {
			jql += "AND " + conditionMap.get("jql") + " ";
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql += "ORDER BY userAttr." + orderBy + " " + orderType;
		}

		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");

		SearchResult<BioneUserAttr> objgrpInfoList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql, values);
		return objgrpInfoList;
	}

	@Transactional(readOnly = false)
	public void removeEntityBatch(String ids) {
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] idArray = ids.split(",");
		List<String> idList = new ArrayList<String>();
		for (String id : idArray) {
			if(!idList.contains(id)){
				idList.add(id);
			}
			removeEntityById(id);
		}
		if(idList.size() > 0){
			//删除属性关系
			String jql = "delete from BioneUserAttrVal val where val.id.attrId in (?0)";
			this.baseDAO.batchExecuteWithIndexParam(jql, idList);
		}
		this.baseDAO.flush();
	}
	/**
	 * 根据分组Id获取用户配置属性信息
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param grpId
	 * @return
	 */
	public SearchResult<BioneUserAttr> getAttrPageByGrpId(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String grpId) {
		String jql = "SELECT userAttr FROM BioneUserAttr userAttr WHERE userAttr.grpId='"+grpId+"' ";

		if (!conditionMap.get("jql").equals("")) {
			jql += "AND " + conditionMap.get("jql") + " ";
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql += "ORDER BY userAttr." + orderBy + " " + orderType;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		SearchResult<BioneUserAttr> objgrpInfoList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql, values);
		return objgrpInfoList;
	}
	
	public boolean isVal(String attrId){
		String jql = "select t from BioneUserAttrVal t where t.id.attrId=?0";
		List<BioneUserAttrVal> entityList = this.baseDAO.findWithIndexParam(jql, attrId);
		if(entityList != null && entityList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<BioneUserAttr> getAttrsByFieldName(String fieldName , String attrId){
		List<BioneUserAttr> attrs = new ArrayList<BioneUserAttr>();
		if(fieldName != null && !"".equals(fieldName)){
			String jql = "select attr from BioneUserAttr attr where attr.fieldName = ?0 ";
			if(attrId != null && !"".equals(attrId)){
				jql += " and attr.attrId != ?1 ";
				attrs = this.baseDAO.findWithIndexParam(jql, fieldName , attrId);
			}else{				
				attrs = this.baseDAO.findWithIndexParam(jql, fieldName);
			}
		}
		return attrs;
	}
	
	public List<BioneUserAttrGrp> getGrpsByGrpName(String grpName , String grpId){
		List<BioneUserAttrGrp> grps = new ArrayList<BioneUserAttrGrp>();
		if(grpName != null && !"".equals(grpName)){
			String jql = "select grp from BioneUserAttrGrp grp where grp.grpName = ?0 ";
			if(grpId != null && !"".equals(grpId)){
				jql += " and grp.grpId != ?1 ";
				grps = this.baseDAO.findWithIndexParam(jql, grpName , grpId);
			}else{				
				grps = this.baseDAO.findWithIndexParam(jql, grpName);
			}
		}
		return grps;
	}

}
