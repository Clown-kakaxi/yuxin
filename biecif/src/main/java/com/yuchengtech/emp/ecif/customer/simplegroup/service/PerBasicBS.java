package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.EduResume;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.JobResume;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;
import com.yuchengtech.emp.ecif.customer.test.entity.Test;

@Service
@Transactional(readOnly = true)
public class PerBasicBS extends BaseBS<Object> {
	
	//展示EduResume的页面
	@SuppressWarnings("unchecked")
	public SearchResult<EduResume> getEduResumeList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select eduresume from EduResume eduresume where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if(!"".equals(custId)){
			jql.append(" and eduresume.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by eduresume." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	    //展示JobResume的页面
		@SuppressWarnings("unchecked")
		public SearchResult<JobResume> getJobResumeList(int firstResult,
				int pageSize, String orderBy, String orderType,
				Map<String, Object> conditionMap,long custId) {
			StringBuffer jql = new StringBuffer("");
			jql.append("select jobresume from JobResume jobresume where 1=1");
			if (!conditionMap.get("jql").equals("")) {
				jql.append(" and " + conditionMap.get("jql"));
			}
			if(!"".equals(custId)){
				jql.append(" and jobresume.custId = " + custId + "");
			}
			if (!StringUtils.isEmpty(orderBy)) {
				jql.append(" order by jobresume." + orderBy + " " + orderType);
			}
			Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
			return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
					jql.toString(), values);
		}
		
		//展示PersonIdentifier的页面
		@SuppressWarnings("unchecked")
		public SearchResult<PersonIdentifier> getPersonIdentifierList(int firstResult,
				int pageSize, String orderBy, String orderType,
				Map<String, Object> conditionMap,long custId) {
			StringBuffer jql = new StringBuffer("");
			jql.append("select obj from PersonIdentifier obj where 1=1");
			if (!conditionMap.get("jql").equals("")) {
				jql.append(" and " + conditionMap.get("jql"));
			}
			if(!"".equals(custId)){
				jql.append(" and obj.custId = " + custId + "");
			}
			if (!StringUtils.isEmpty(orderBy)) {
				//jql.append(" order by obj." + orderBy + " " + orderType);
				jql.append(" order by obj.identType, obj.lastUpdateTm desc ");
			}
			Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
			return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
					jql.toString(), values);
		}
	
	/**
	 * 保存
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveInfo(Test test){
		String sql = "insert into test(testname, testtype, testflag) values ('"
			+ test.getTestName() + "','" + test.getTestType() + "','" + test.getTestFlag() + "')";
		this.baseDAO.createNativeQueryWithIndexParam(sql).executeUpdate();
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select t.typeId, t.typeName from Ttype t");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
	
	/**
	 * 删除用户方法,动态表单时调用
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteUserByUId(String[] userIds){
		if(userIds != null && userIds.length > 0){
			List<String> userIdsList = new ArrayList<String>();
			for(int i = 0 ; i < userIds.length ; i++){
				userIdsList.add(userIds[i]);
			}
//			String jql1 = "delete from BioneUserAttrVal val where val.id.userId in (?0)";
			String jql2 = "delete from Test test where test.id in (?0)";
//			this.baseDAO.batchExecuteWithIndexParam(jql1, userIdsList);
			this.baseDAO.batchExecuteWithIndexParam(jql2, userIdsList);
		}
	}

}
