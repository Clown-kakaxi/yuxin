package com.yuchengtech.emp.core.entity.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 功能描述: 查询条件类<br>
 * 该类封装数据访问层的查询条件。
 * 
 * Copyright: Copyright (c) 2011 
 * Company: 北京宇信易诚科技有限公司
 * 
 * @author TanDong
 * @version 1.0 2011-6-1下午04:08:32
 * @see HISTORY 2011-6-1下午04:08:32 创建文件
 ************************************************* 
 */
public class QueryConditions {

	/**
	 * 等于
	 */
	public static final String EQUAL = "=";
	/**
	 * 不等于
	 */
	public static final String NOT_EQUAL = "<>";
	/**
	 * 大于
	 */
	public static final String GREATER_THAN = ">";
	/**
	 * 大于等于
	 */
	public static final String GREATER_EQUAL = ">=";
	/**
	 * 小于
	 */
	public static final String LESS_THAN = "<";
	/**
	 * 小于等于
	 */
	public static final String LESS_EQUAL = "<=";
	/**
	 * 中间包含
	 */
	public static final String INCLUDE = "%value%";
	/**
	 * 左边包括
	 */
	public static final String LEFT_INCLUDE = "value%";
	/**
	 * 右边包括
	 */
	public static final String RIGHT_INCLUDE = "%value";
	/**
	 * 空值
	 */
	public static final String ISNULL = "isnull";

	/**
	 * 不为空值
	 */
	public static final String ISNOTNULL = "isnotnull";

	/**
	 * 空字符串值
	 */
	public static final String ISEMPTY = "isempty";

	/**
	 * 不为空字符串值
	 */
	public static final String ISNOTEMPTY = "isnotempty";

	/**
	 * 排序语句
	 */
	private String orderBy;

	/**
	 * 分组语句
	 */
	private String groupBy;

	/**
	 * 查询的HQL其它语句，可为having等
	 */
	private String otherHql;

	/**
	 * 查询条件的属性名列表
	 */
	private List<Object> propertyNames;

	/**
	 * 查询条件的操作符列表，与属性名列表一一对应。操作符包括=, >=, <=, <>, !=, like
	 */
	private List<Object> operators;

	/**
	 * 查询条件的值列表，该列表应当与属性列表一一对应
	 */
	private List<Object> values;

	/**
	 * 查询Sql Map语句id
	 */
	private String statementId;

	/**
	 * 统计总记数查询的Sql Map语句idSql
	 */
	private String countStatementId;

	/**
	 * 查询参数值对象(可为Javabean,Map等)
	 */
	private Object paramValue;

	public QueryConditions() {
		orderBy = null;
		groupBy = null;
		propertyNames = new ArrayList<Object>();
		operators = new ArrayList<Object>();
		values = new ArrayList<Object>();
		otherHql = null;
		statementId = null;
		countStatementId = null;
		paramValue = null;
	}

	/**
	 * 设置查询条件(where后面),该方法迭加多次调用。 示例如下<br>
	 * 
	 * <pre>
	 * hqlWrapper.setCondition(&quot;codeType&quot;, HqlWrapper.EQUAL, codeType);
	 * hqlWrapper.setCondition(&quot;codeTypeName&quot;, HqlWrapper.EQUAL, codeTypeName);
	 * </pre>
	 * 
	 * @param propertyName
	 *            查询条件的属性名
	 * @param operator
	 *            查询条件的操作符
	 * @param value
	 *            查询条件的值
	 */
	public void setCondition(String propertyName, String operator, Object value) {

		if (propertyName == null || propertyName.trim().equals(""))
			return;
		if (operator == null || operator.trim().equals("")) {
			operator = EQUAL;
		}

		if (operator.equals(ISNULL) || operator.equals(ISNOTNULL)
				|| operator.equals(ISEMPTY) || operator.equals(ISNOTEMPTY)) {
			value = operator;
		}

		if (value == null)
			return;
		if (value instanceof String && value.toString().trim().equals(""))
			return;
		propertyNames.add(propertyName);

		if (operator.equals(INCLUDE)) {
			operators.add("like");
			values.add("%" + value + "%");
		} else if (operator.equals(LEFT_INCLUDE)) {
			operators.add("like");
			values.add(value + "%");
		} else if (operator.equals(RIGHT_INCLUDE)) {
			operators.add("like");
			values.add("%" + value);
		} else {
			operators.add(operator);
			values.add(value);
		}
	}

	public void setConditionEqual(String propertyName, Object value) {
		setCondition(propertyName, EQUAL, value);
	}

	public void setConditionNotEqual(String propertyName, Object value) {
		setCondition(propertyName, NOT_EQUAL, value);
	}

	public void setConditionGreaterEqual(String propertyName, Object value) {
		setCondition(propertyName, GREATER_EQUAL, value);
	}

	public void setConditionGreaterThan(String propertyName, Object value) {
		setCondition(propertyName, GREATER_THAN, value);
	}

	public void setConditionLessEqual(String propertyName, Object value) {
		setCondition(propertyName, LESS_EQUAL, value);
	}

	public void setConditionLessThan(String propertyName, Object value) {
		setCondition(propertyName, LESS_THAN, value);
	}

	public void setConditionLeftInclude(String propertyName, Object value) {
		setCondition(propertyName, LEFT_INCLUDE, value);
	}

	public void setConditionRightInclude(String propertyName, Object value) {
		setCondition(propertyName, RIGHT_INCLUDE, value);
	}

	public void setConditionInclude(String propertyName, Object value) {
		setCondition(propertyName, INCLUDE, value);
	}

	public void setConditionIsNull(String propertyName) {
		setCondition(propertyName, ISNULL, ISNULL);
	}

	public void setConditionIsNotNull(String propertyName) {
		setCondition(propertyName, ISNOTNULL, ISNOTNULL);
	}

	public void setConditionIsEmpty(String propertyName) {
		setCondition(propertyName, ISEMPTY, ISEMPTY);
	}

	public void setConditionIsNotEmpty(String propertyName) {
		setCondition(propertyName, ISNOTEMPTY, ISNOTEMPTY);
	}

	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序条件。<br>
	 * 示例：<br>
	 * <p/>
	 * hqlWrapper.setOrderBy("a desc,b asc"); -->order by a desc,b asc
	 * 
	 * @param orderBy
	 *            排序字符串
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * 设置分组条件。<br>
	 * 示例：<br>
	 * <p/>
	 * hqlWrapper.setGroupBy("a,b"); --> group by a,b
	 * 
	 * @param groupBy
	 *            分组字符串
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * 查询的HQL其它语句，如having等
	 * 
	 * @return String
	 */
	public String getOtherHql() {
		return otherHql;
	}

	public void setOtherHql(String otherHql) {
		this.otherHql = otherHql;
	}

	/**
	 * 查询条件的属性名列表
	 * 
	 * @return Set
	 */
	public List<Object> getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(List<Object> propertyNames) {
		this.propertyNames = propertyNames;
	}

	/**
	 * 查询条件的操作符列表，与属性名列表一一对应。操作符包括=, >=, <=, <>, !=, like
	 * 
	 * @return Set
	 */
	public List<Object> getOperators() {
		return operators;
	}

	public void setOperators(List<Object> operators) {
		this.operators = operators;
	}

	/**
	 * 查询条件的值列表，该列表应当与属性列表一一对应
	 * 
	 * @return Set
	 */
	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	// =======================================================
	/**
	 * 设置iBatis执行查询语句Sql Map的statementId及执行查询总计数(如果需要返回查询总记录数)<br>
	 * <p/>
	 * 示例如下<br>
	 * 
	 * <pre>
	 * //一、用普通Javabean作为参数查询：<br>
	 * IwbFrameCodeType codeTypeObj = new IwbFrameCodeType();
	 * codeTypeObj.setCodeType("0010");
	 * codeTypeObj.setCodeTypeName("机构类型");
	 * <p/>
	 * sqlWrapper.setParameter("codeType.searchCodeType","codeType.countCodeTypeCount",codeTypeObj);
	 * <p/>
	 * //二、用Map作为参数查询：<br>
	 * Map map = new HashMap();
	 * map.put("codeType","0010");
	 * map.put("codeTypeName","机构类型");
	 * <p/>
	 * sqlWrapper.setParameter("codeType.searchCodeType","codeType.countCodeTypeCount",map);
	 * <p/>
	 * //三、配置SQL Map文件
	 * &lt;sqlMap namespace="codeType"&gt;
	 * <p/>
	 * &lt;typeAlias alias="IwbFrameCodeType" type="com.longtop.intelliweb.sample.base.model.IwbFrameCodeType"/&gt;
	 * <p/>
	 * &lt;resultMap id="IwbFrameCodeTypeResult" class="com.longtop.intelliweb.sample.base.model.IwbFrameCodeType"&gt;
	 * &lt;result column="code_type" property="codeType" jdbcType="VARCHAR"/&gt;
	 * &lt;result column="code_type_name" property="codeTypeName" jdbcType="VARCHAR"/&gt;
	 * &lt;/resultMap&gt;
	 * <p/>
	 * &lt;!--以下定义查询语句--&gt;
	 * &lt;select id="searchCodeType" resultMap="IwbFrameCodeTypeResult"  parameterClass="IwbFrameCodeType"&gt;
	 * select code_type,code_type_name from iwb_frame_code_type
	 * where code_type =#codeType# and code_type_name = #codeTypeName#
	 * &lt;/select&gt;
	 * <p/>
	 * &lt;!--以下定义返回总记录数的查询语句--&gt;
	 * &lt;select id="countCodeTypeCount" resultClass="int" parameterClass="IwbFrameCodeType"&gt;
	 * select count(*) as result from iwb_frame_code_type
	 * where code_type =#codeType# and code_type_name = #codeTypeName#
	 * &lt;/select&gt;
	 * <p/>
	 * &lt;/sqlMap&gt;
	 * </pre>
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param countStatementId
	 *            统计总记数查询的Sql Map语句的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param paramValue
	 *            查询条件的值，可为JavaBean,Map对象。
	 */
	public void setParameter(String statementId, String countStatementId,
			Object paramValue) {
		this.statementId = statementId;
		this.countStatementId = countStatementId;
		this.paramValue = paramValue;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public String getCountStatementId() {
		return countStatementId;
	}

	public void setCountStatementId(String countStatementId) {
		this.countStatementId = countStatementId;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
}
