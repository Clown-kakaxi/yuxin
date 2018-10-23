/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.comb
 * @�ļ�����QueryBizLogicWithPage.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:05:52
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�QueryBizLogicWithPage
 * @������������ҳ�Ĳ�ѯҵ�����߼�
 * @��������:��ѯҵ���߼���ʵ��ҵ�����߼��ӿڣ����ڽ��״���ģ��͵ײ��ѯҵ����ģ�齻����
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:05:52
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:05:52
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
		 * ��ѯ�������п�ֵ������������������Ϊ��ֵ������������SQL���Ƴ�
		 */
		for (Entry<String, Object> columnName : requestMap.entrySet()) {
			if (StringUtil.isEmpty(columnName.getValue())) {
				while (jql.contains(":" + columnName.getKey())) {
					log.warn("����[{}]ֵΪ��,����������SQL���Ƴ�",columnName.getKey());
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
							log.warn("����[{}]ֵΪ��,����������SQL���Ƴ�ʧ��",columnName.getKey());
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
			//��ʼλ��
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
		//��ѯ��SQL
		if(queryModel.isPageSelect()){
			result = baseDAO.createNativeQueryWithNameParam(jql, requestMap)
					.setFirstResult(startPosition)
					.setMaxResults(queryModel.getPageSize())
					.getResultList();
		}else{
			result = baseDAO.createNativeQueryWithNameParam(jql, requestMap)
					.getResultList();
			/*** �޶���󷵻ؼ�¼�� ***/
			if (result.size() > MdmConstants.queryMaxsize) {
				log.error("������Ϣ����ѯ���ؼ�¼�����������{" + jql + "}");
				ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_MAX.getCode(),
						"��ѯ���ؼ�¼�����������");
				return;
			}
		}
		int resultSize = result.size();
		int fieldSize = queryModel.getQueryFieldList().size();
		List<String> fieldlist = queryModel.getQueryFieldList();
		queryModel.setResultSize(resultSize);
		// �Ѳ�ѯ�����װ ������ResponseData�е�resultMap��
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
