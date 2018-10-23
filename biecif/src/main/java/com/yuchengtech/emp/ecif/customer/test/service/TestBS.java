package com.yuchengtech.emp.ecif.customer.test.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.test.entity.Test;

@Service
@Transactional(readOnly = true)
public class TestBS extends BaseBS<Test> {

	private Logger log = LoggerFactory.getLogger(TestBS.class);

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
	public SearchResult<Test> getUserList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select test from Test test where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by test." + orderBy + " " + orderType);
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
