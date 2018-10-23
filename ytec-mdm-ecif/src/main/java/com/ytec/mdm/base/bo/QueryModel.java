/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����QueryModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:01:46
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.bo;

import java.util.List;
import java.util.Map;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�QueryModel
 * @����������ѯģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:02:07   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:02:07
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class QueryModel {
	
	/**
	 * The parent node value map.
	 * 
	 * @��������:��ѯ�����б�
	 */
	private Map parentNodeValueMap;
	
	/**
	 * The query sql.
	 * 
	 * @��������:��ѯSQL
	 */
	private String querySql;
	
	/**
	 * The page start.
	 * 
	 * @��������:��ѯ��ʼ����
	 */
	private int pageStart;
	
	/**
	 * The page size.
	 * 
	 * @��������:��ѯ��ҳ��
	 */
	private int pageSize;
	
	/**
	 * The result size.
	 * 
	 * @��������:��ѯ�������
	 */
	private int resultSize;
	
	/**
	 * The resul list.
	 * 
	 * @��������:���ؽ��
	 */
	private List<Map<String, Object>> resulList;
	
	/**
	 * The query field list.
	 * 
	 * @��������:��ѯ���ص��ֶ�
	 */
	private List<String> queryFieldList; 
	
	/**
	 * @��������:totalCount
	 * @��������:��ѯ��¼������
	 * @since 1.0.0
	 */
	private int totalCount;
	
	/**
	 * @��������:pageSelect
	 * @��������:�Ƿ��ҳ��ѯ
	 * @since 1.0.0
	 */
	private boolean pageSelect;
	
	
	/**
	 * Gets the parent node value map.
	 * 
	 * @return the parent node value map
	 */
	public Map getParentNodeValueMap() {
		return parentNodeValueMap;
	}
	
	/**
	 * Sets the parent node value map.
	 * 
	 * @param parentNodeValueMap
	 *            the new parent node value map
	 */
	public void setParentNodeValueMap(Map parentNodeValueMap) {
		this.parentNodeValueMap = parentNodeValueMap;
	}
	
	/**
	 * Gets the query sql.
	 * 
	 * @return the query sql
	 */
	public String getQuerySql() {
		return querySql;
	}
	
	/**
	 * Sets the query sql.
	 * 
	 * @param querySql
	 *            the new query sql
	 */
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	
	/**
	 * Gets the page start.
	 * 
	 * @return the page start
	 */
	public int getPageStart() {
		return pageStart;
	}
	
	/**
	 * Sets the page start.
	 * 
	 * @param pageStart
	 *            the new page start
	 */
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	
	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * Sets the page size.
	 * 
	 * @param pageSize
	 *            the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * Gets the result size.
	 * 
	 * @return the result size
	 */
	public int getResultSize() {
		return resultSize;
	}
	
	/**
	 * Sets the result size.
	 * 
	 * @param resultSize
	 *            the new result size
	 */
	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}
	
	
	/**
	 * Gets the resul list.
	 * 
	 * @return the resul list
	 */
	public List<Map<String, Object>> getResulList() {
		return resulList;
	}
	
	/**
	 * Sets the resul list.
	 * 
	 * @param resulList
	 *            the resul list
	 * @��������:void setResulList(List<Map<String,Object>> resulList)
	 * @��������:
	 * @�����뷵��˵��: void setResulList(List<Map<String,Object>> resulList)
	 * @�㷨����:
	 */
	public void setResulList(List<Map<String, Object>> resulList) {
		this.resulList = resulList;
	}
	
	/**
	 * Gets the query field list.
	 * 
	 * @return the query field list
	 */
	public List<String> getQueryFieldList() {
		return queryFieldList;
	}
	
	/**
	 * Sets the query field list.
	 * 
	 * @param queryFieldList
	 *            the new query field list
	 */
	public void setQueryFieldList(List<String> queryFieldList) {
		this.queryFieldList = queryFieldList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(boolean pageSelect) {
		this.pageSelect = pageSelect;
	}
	
	
	
}
