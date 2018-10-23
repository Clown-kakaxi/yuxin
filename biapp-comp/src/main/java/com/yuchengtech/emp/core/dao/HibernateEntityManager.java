package com.yuchengtech.emp.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.yuchengtech.emp.core.entity.page.Page;
import com.yuchengtech.emp.core.entity.page.PageInfo;
import com.yuchengtech.emp.core.entity.page.QueryConditions;

/**
 * 
 * 功能描述: 基于Hibernate的entity管理接口
 * 
 * Copyright: Copyright (c) 2011 Company: 北京宇信易诚科技有限公司
 * 
 * @author 陈路凝
 * @version 1.0 2011-5-18下午02:46:49
 * @see HISTORY 2011-5-18下午02:46:49 创建文件
 **************************************************/

public interface HibernateEntityManager extends EntityManager {

	/**
	 * 根据主键加载特定持久化类的实例。
	 * 
	 * @param var_class
	 *            持久化类
	 * @param serializable
	 *            主键
	 * @return Object 取得的实例
	 */
	public <T> T load(Class<T> var_class, Serializable serializable);

	/**
	 * 
	 * 加载所有特定持久化类的实例
	 * @param <T>
	 * 
	 * @param var_class
	 * @return Date: 2011-5-18下午02:50:52
	 */
	public <T> List<T> loadAll(Class<T> var_class);

	/**
	 * 查询出所有特定持久化类的实例，再返回指定结果集。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param start
	 *            开始点
	 * @param size
	 *            条数
	 * @return List 取得的实例结果集
	 */
	public <T> List<T> loadAll(Class<T> entityClass, int start, int size);

	/**
	 * 
	 * 按分页条件，查询出所有特定持久化类的实例，再返回指定结果集。
	 * @param <T>
	 * 
	 * @param var_class
	 * @param pageinfo
	 * @return Date: 2011-5-18下午02:54:27
	 */
	public <T> Page<T> loadAll(Class<T> var_class, PageInfo pageinfo);

	/**
	 * 更新特定实例。
	 * 
	 * @param entity
	 */
	public <T> void update(T t);

	/**
	 * 删除特定实例。
	 * 
	 * @param entity
	 */
	public <T> void delete(T t);

	/**
	 * 批量删除特定实例。
	 * 
	 * @param collection
	 */
	public <T> void delete(Collection<T> collection);

	/**
	 * 根据主键删除现有持久化类的实例，同步持久层。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param id
	 *            主键
	 */
	public <T> void delete(Class<T> entityClass, Serializable id);

	/**
	 * 保存新的实例至持久层,并返回主键。
	 * 
	 * @param entity
	 *            要保存的新实例
	 * @return Serializable 返回主键
	 */
	public <T> Serializable save(T t);

	/**
	 * 
	 * 批量保存新的实例至持久层,并返回保存的实例集。
	 * 
	 * @param entities
	 * @return Date: 2011-5-18下午02:58:12
	 */
	public <T> List<? extends Serializable> save(Collection<T> entities) ;
	
	/**
	 * 更新多个特定实例（循环依次更新）。
	 * 
	 * @param entities
	 *            持久化类集
	 */
	public <T> void update(Collection<T> entities);

	/**
	 * 如果session中存在相同持久化标识(identifier)的实例，用用户给出的对象覆盖session已有的持久实例
	 * (1)当我们使用update的时候，执行完成后，会抛出异常:
	 * org.springframework.orm.hibernate3.HibernateSystemException: a different
	 * object with the same identifier value was already associated with the
	 * session: (2)但当我们使用merge的时候，把处理自由态的po对象A的属性copy到session当中处于持久态的po的属性中，
	 * 执行完成后原来是持久状态还是持久态，而我们提供的A还是自由态
	 */
	public <T> void merge(T t);

	/**
	 * 更新特定实例。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @return Object 取得的实例结果
	 */
	public <T> T refresh(T t);

	/**
	 * 根据HQL，执行查询操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString);

	/**
	 * 根据HQL和参数，执行 带有in关键字的查询操作。
	 * 
	 * @param HQL
	 *            HQL语句 * @param parameterName 指代名 * @param params 参数值
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString, String parameterName, Object[] params);

	/**
	 * 根据HQL和参数，执行 带有in关键字的查询操作。
	 * 
	 * 这种方式只能带有一个参数，即 queryString 中只能有一个参数 例如：根据人员的角色查询包含权限的有效叶子菜单 public
	 * List<MenuFunc> queryMenuResourceWithRoleIds(String roleIdsString){ String
	 * hql =
	 * " from MenuFunc where value in (select url from Resource where id in " +
	 * "(select resourceId from RoleResource where roleId in ( :roleIds ))) and inUse='1'"
	 * ; String parameterName = "roleIds"; String[] selected =
	 * roleIdsString.split(","); Long[] params = new Long[selected.length];
	 * for(int i=0; i<selected.length; i++){ params[i] =
	 * Long.valueOf(selected[i]); }
	 * 
	 * return this.getEntityManager().find(hql,parameterName ,params ); }
	 * 
	 * params中放多个role id的对象例如：
	 * 
	 * 
	 * 
	 * @param HQL
	 *            HQL语句
	 * @param parameterName
	 *            指代名
	 * @param params
	 *            参数值
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString, Object[] params);

	/**
	 * 根据HQL和开始数和条数，执行查询操作，注意是查询全部结果集，再返回指定条数，大数据可能存在性能问题。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @param start
	 *            开始点
	 * @param size
	 *            条数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString, int start, int size);

	/**
	 * 根据HQL和开始数和条数，执行查询操作，针对oracle/PostgreSQL/mysql，hibernate均能支持数据库分页。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @param params
	 *            参数集
	 * @param start
	 *            开始点
	 * @param size
	 *            条数
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString, Object[] params, int start, int size);

	/**
	 * 分页查询HQL结果集。
	 * 
	 * @param queryString
	 *            完整的HQL语句
	 * @param pageInfo
	 *            页面对象（CurrentPageNum 当前页，从零开始；RowsOfPage 每页条数）
	 * @return Page (包括结果集 list，与pageInfo对象）
	 */
	public <T> Page<T> find(String queryString, PageInfo pageInfo);

	/**
	 * 带参数分页查询HQL结果集。
	 * 
	 * @param queryString
	 *            完整的HQL语句
	 * @param params
	 *            参数集
	 * @param pageInfo
	 *            页面对象（CurrentPageNum 当前页，从零开始；RowsOfPage 每页条数）
	 * @return Page (包括结果集 list，与pageInfo对象）
	 */
	public <T> Page<T> find(String queryString, Object[] params, PageInfo pageInfo);

	// 参数太多，暂不提供！
	// public Object[] find(final String hql, final String[] propertyNames,
	// final String[] operators,
	// final Object[] values, final int offset, final int size, final boolean
	// isTotalSize,
	// final String orderBy, final String groupBy, final String otherCause);
	/**
	 * 根据条件使用HQL语句查询数据。用queryCondition和pageInfo包装了方法 find(final String hql,
	 * final String[] propertyNames, final String[] operators, final Object[]
	 * values, final int offset, final int size, final boolean isTotalSize,
	 * final String orderBy, final String groupBy, final String otherCause)
	 * ====多表查询示例 start=====： HQL = select new
	 * com.travelsky.pss.dao.VO1(s.flightsNo,e.id) from Flights s, dict e .....
	 * VO1要求:必须有针对这些属性的构造函数（如：public VO1(String name,long id)),可以通过ECLIPSE自动生成。
	 * 注意类型要求一致性，例如long与Long ====多表查询示例 end=====：
	 * 
	 * @param hql
	 *            HQL查询语句（不带Where条件）。不允许在select段内使用子查询，如不允许这种用法： select
	 *            a,b,(select c from table1) as d from table2 ...
	 *            1)对于查询全部对象属性，(<b>select *</b>)不可写。如：from TUser；
	 *            2)对于查询部分对象属性，则必须写完整，如：select userName,password from TUser;
	 * @param queryCondition
	 *            查询条件，
	 * @param pageInfo
	 *            分页信息，
	 * @param isTotalSize
	 *            是否需要返回本次查询的总记录数，默认false
	 * @return page 返回带分页的查询结果
	 */
	public <T> Page<T> find(String hql, QueryConditions condition, PageInfo pageInfo, boolean isTotalSize);

	/**
	 * 根据条件使用HQL语句查询数据。用queryCondition包装了方法 find(final String hql, final
	 * String[] propertyNames, final String[] operators, final Object[] values,
	 * final int offset, final int size, final boolean isTotalSize, final String
	 * orderBy, final String groupBy, final String otherCause) ====多表查询示例
	 * start=====： HQL = select new com.travelsky.pss.dao.VO1(s.flightsNo,e.id)
	 * from Flights s, dict e ..... VO1要求:必须有针对这些属性的构造函数（如：public VO1(String
	 * name,long id)),可以通过ECLIPSE自动生成。 注意类型要求一致性，例如long与Long ====多表查询示例
	 * end=====：
	 * 
	 * @param hql
	 *            HQL查询语句（不带Where条件）。不允许在select段内使用子查询，如不允许这种用法： select
	 *            a,b,(select c from table1) as d from table2 ...
	 *            1)对于查询全部对象属性，(<b>select *</b>)不可写。如：from TUser；
	 *            2)对于查询部分对象属性，则必须写完整，如：select userName,password from TUser;
	 * @param queryCondition
	 *            查询条件，
	 * @param pageInfo
	 *            分页信息，
	 * @return page 返回带分页的查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String hql, QueryConditions condition);

	/**
	 * 根据条件使用HQL语句查询数据。用queryCondition包装了方法 find(final String hql, final
	 * String[] propertyNames, final String[] operators, final Object[] values,
	 * final int offset, final int size, final boolean isTotalSize, final String
	 * orderBy, final String groupBy, final String otherCause) ====多表查询示例
	 * start=====： HQL = select new com.travelsky.pss.dao.VO1(s.flightsNo,e.id)
	 * from Flights s, dict e ..... VO1要求:必须有针对这些属性的构造函数（如：public VO1(String
	 * name,long id)),可以通过ECLIPSE自动生成。 注意类型要求一致性，例如long与Long ====多表查询示例
	 * end=====：
	 * 
	 * @param hql
	 *            HQL查询语句（不带Where条件）。不允许在select段内使用子查询，如不允许这种用法： select
	 *            a,b,(select c from table1) as d from table2 ...
	 *            1)对于查询全部对象属性，(<b>select *</b>)不可写。如：from TUser；
	 *            2)对于查询部分对象属性，则必须写完整，如：select userName,password from TUser;
	 * @param queryCondition
	 *            查询条件，
	 * @param pageInfo
	 *            分页信息，
	 * @param maxRowNum
	 *            本次查询返回最多的结果条数
	 * @return page 返回带分页的查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String hql, QueryConditions condition, int maxRowNum);

	// public List autoPageFind(String string);
	//
	// public List autoPageFind(String string, Object[] objects);

	/**
	 * 根据HQL，执行update操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 */
	public int executeUpdate(String HQL);

	/**
	 * 根据HQL和参数（参数根据次序设置），执行update操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @param params
	 */
	public int executeUpdate(String HQL, Object[] params);

	/**
	 * 
	 * 获取HibernateTemplate
	 * 
	 * @return Date: 2011-5-18下午03:07:30
	 */
	public HibernateTemplate getHibernateTemplate();
}