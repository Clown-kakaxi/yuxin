/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：QueryModel.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:01:46
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.bo;

import java.util.List;
import java.util.Map;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：QueryModel
 * @类描述：查询模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:02:07   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:02:07
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class QueryModel {
	
	/**
	 * The parent node value map.
	 * 
	 * @属性描述:查询参数列表
	 */
	private Map parentNodeValueMap;
	
	/**
	 * The query sql.
	 * 
	 * @属性描述:查询SQL
	 */
	private String querySql;
	
	/**
	 * The page start.
	 * 
	 * @属性描述:查询起始条数
	 */
	private int pageStart;
	
	/**
	 * The page size.
	 * 
	 * @属性描述:查询分页数
	 */
	private int pageSize;
	
	/**
	 * The result size.
	 * 
	 * @属性描述:查询结果条数
	 */
	private int resultSize;
	
	/**
	 * The resul list.
	 * 
	 * @属性描述:返回结果
	 */
	private List<Map<String, Object>> resulList;
	
	/**
	 * The query field list.
	 * 
	 * @属性描述:查询返回的字段
	 */
	private List<String> queryFieldList; 
	
	/**
	 * @属性名称:totalCount
	 * @属性描述:查询记录总条数
	 * @since 1.0.0
	 */
	private int totalCount;
	
	/**
	 * @属性名称:pageSelect
	 * @属性描述:是否分页查询
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
	 * @函数名称:void setResulList(List<Map<String,Object>> resulList)
	 * @函数描述:
	 * @参数与返回说明: void setResulList(List<Map<String,Object>> resulList)
	 * @算法描述:
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
