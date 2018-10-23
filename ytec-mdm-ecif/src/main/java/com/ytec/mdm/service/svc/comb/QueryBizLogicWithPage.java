/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.comb
 * @文件名：QueryBizLogicWithPage.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:05:52
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.svc.comb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SQLUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：QueryBizLogicWithPage
 * @类描述：带分页的查询业务处理逻辑
 * @功能描述:查询业务逻辑，实现业务处理逻辑接口，用于交易处理模块和底层查询业务处理模块交互。
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:05:52
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:05:52
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryBizLogicWithPage implements IEcifBizLogic {
	protected static Logger log = LoggerFactory.getLogger(QueryBizLogicWithPage.class);
	private JPABaseDAO baseDAO;

	public void process(EcifData ecifData) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		QueryModel queryModel=ecifData.getQueryModelObj();
		String jql = queryModel.getQuerySql();
		Map<String, Object> requestMap = queryModel.getParentNodeValueMap();
		List<Object> result = null;
		int startPosition=0;
		/***
		 * 查询条件中有空值，但是配置中允许其为空值，将该条件从SQL中移除
		 */
		for (Entry<String, Object> columnName : requestMap.entrySet()) {
			if (StringUtil.isEmpty(columnName.getValue())) {
				while (jql.contains(":" + columnName.getKey())) {
					log.warn("参数[{}]值为空,将该条件从SQL中移除",columnName.getKey());
					String beforeAnd = null;
					int andIndex = jql.substring(0,
							jql.indexOf(":" + columnName.getKey()))
							.lastIndexOf("and");
					if (andIndex == -1) {
						andIndex = jql.substring(0,
								jql.indexOf(":" + columnName.getKey()))
								.lastIndexOf("where");
						if (andIndex != -1) {
							andIndex += "where".length();
						} else {
							log.warn("参数[{}]值为空,将该条件从SQL中移除失败",columnName.getKey());
							break;
						}
						beforeAnd = jql.substring(0, andIndex) + " 1=1";
					} else {
						beforeAnd = jql.substring(0, andIndex);
					}
					String afterAnd = jql.substring(jql.indexOf(":"
							+ columnName.getKey())
							+ columnName.getKey().length() + 1);
					jql = beforeAnd + afterAnd;
				}
			}
		}
		
		
		if(queryModel.isPageSelect()){
			//起始位置
			startPosition=(queryModel.getPageStart()-1)*queryModel.getPageSize();
			String countJql = SQLUtils.buildCountSQL(jql);
			int totalCount = Integer.valueOf(baseDAO.createNativeQueryWithNameParam(countJql, requestMap).getSingleResult().toString());
			queryModel.setTotalCount(totalCount);
			if(totalCount<startPosition){
				queryModel.setResultSize(0);
				queryModel.setResulList(list);
				return;
			}
		}
		//查询用SQL
		if(queryModel.isPageSelect()){
			result = baseDAO.createNativeQueryWithNameParam(jql, requestMap)
					.setFirstResult(startPosition)
					.setMaxResults(queryModel.getPageSize())
					.getResultList();
		}else{
			result = baseDAO.createNativeQueryWithNameParam(jql, requestMap)
					.getResultList();
			/*** 限定最大返回记录数 ***/
			if (result.size() > MdmConstants.queryMaxsize) {
				log.error("错误信息：查询返回记录超过最大限制{" + jql + "}");
				ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_MAX.getCode(),
						"查询返回记录超过最大限制");
				return;
			}
		}
		int resultSize = result.size();
		int fieldSize = queryModel.getQueryFieldList().size();
		List<String> fieldlist = queryModel.getQueryFieldList();
		queryModel.setResultSize(resultSize);
		// 把查询结果封装 ，放入ResponseData中的resultMap中
		boolean isArray=true;
		if(resultSize>0){
			isArray=result.get(0).getClass().isArray();
		}
		for (int i = 0; i < resultSize; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			Object[] ob;
			if (isArray) {
				ob = (Object[]) result.get(i);
			} else {
				ob = new Object[1];
				ob[0] = (Object)result.get(i);
			}
			for (int j = 0; j < fieldSize; j++) {
				map.put(fieldlist.get(j), ob[j]);
			}
			list.add(map);
		}
		queryModel.setResulList(list);
		return;
	}

}
