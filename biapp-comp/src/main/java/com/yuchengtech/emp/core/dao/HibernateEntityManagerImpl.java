package com.yuchengtech.emp.core.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.yuchengtech.emp.core.Constants;
import com.yuchengtech.emp.core.entity.AbstractEntity;
import com.yuchengtech.emp.core.entity.page.Page;
import com.yuchengtech.emp.core.entity.page.PageInfo;
import com.yuchengtech.emp.core.entity.page.QueryConditions;
import com.yuchengtech.emp.core.exception.SystemException;

/**
 * 
 * 功能描述: 基于Hibernate的entity管理实现类 ，提供enity的增删改查功能。 
 * Copyright: Copyright (c) 2011
 * Company: 北京宇信易诚科技有限公司
 * 
 * @author 陈路凝
 * @version 1.0 2011-5-18下午03:17:52
 * @see HISTORY 2011-5-18下午03:17:52 创建文件
 **************************************************/
@Repository
public class HibernateEntityManagerImpl extends HibernateDaoSupport implements
		HibernateEntityManager {

	@Autowired
	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 删除现有实例，同步持久层。
	 * 
	 * @param entity
	 *            要删除的实例
	 * @throws Exception
	 *             数据访问异常，是一个运行时异常
	 */
	public <T> void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 删除集合内全部持久化类实例，同步持久层。
	 * 
	 * @param entities
	 *            要删除的实例集合
	 * @throws Exception
	 *             数据访问异常，是一个运行时异常
	 */
	public <T> void delete(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 根据主键删除现有持久化类的实例，同步持久层。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param id
	 *            主键
	 */
	public <T> void delete(Class<T> entityClass, Serializable id) {
		getHibernateTemplate().delete(load(entityClass, id));
	}

	/**
	 * 根据HQL，执行update操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 */
	public int executeUpdate(String HQL) {
		return getSession().createQuery(HQL).executeUpdate();
	}

	/**
	 * 根据HQL和参数（参数根据次序设置），执行update操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @param params
	 */
	public int executeUpdate(String HQL, Object[] params) {
		Query queryObject = getSession().createQuery(HQL);
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				queryObject.setParameter(i, params[i]);
		}
		return queryObject.executeUpdate();
	}

	/**
	 * 根据HQL，执行查询操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	/**
	 * 根据HQL和参数，执行查询操作。
	 * 
	 * @param HQL
	 *            HQL语句
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString, Object[] params) {
		return getHibernateTemplate().find(queryString, params);
	}

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
	public List find(String queryString, String parameterName, Object[] params) {
		Query query = getSession().createQuery(queryString);
		query.setParameterList(parameterName, params);
		return query.list();
	}

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
	public List find(String queryString, int start, int size) {
		Query query = getSession().createQuery(queryString);
		query.setFirstResult(start);
		query.setFetchSize(size);
		query.setMaxResults(size);
		return query.list();
	}

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
	public List find(String queryString, Object[] params, int start, int size) {
		Query query = getSession().createQuery(queryString);
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i, params[i]);
		}
		query.setFirstResult(start);
		query.setFetchSize(size);
		query.setMaxResults(size);
		return query.list();
	}

	/**
	 * 根据主键加载特定持久化类的实例。
	 * 
	 * @param var_class
	 *            持久化类
	 * @param serializable
	 *            主键
	 * @return Object 取得的实例
	 */
	public <T> T load(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 查询出所有特定持久化类的实例。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @return List 取得的实例结果集
	 */
	public <T> List<T> loadAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

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
	@SuppressWarnings("unchecked")
	public <T> List<T> loadAll(Class<T> entityClass, int start, int size) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setFetchSize(size);
		criteria.setFirstResult(start);
		criteria.setMaxResults(size);
		return criteria.list();
	}

	/**
	 * 更新特定实例。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @return Object 取得的实例结果
	 */
	public <T> T refresh(T entity) {
		getHibernateTemplate().refresh(entity);
		return entity;
	}

	/**
	 * 保存特定实例。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @return Serializable 取得的实例的主键
	 */
	public Serializable save(Object entity) {
		return getHibernateTemplate().save(entity);
	}

	/**
	 * 保存多个特定实例（循环依次保存）。
	 * 
	 * @param entities
	 *            持久化类集
	 * @return List 保存后包含主键的结果集
	 */
	public <T> List<? extends Serializable> save(Collection<T> entities) {
		List<Serializable> result = new ArrayList<Serializable>();
		if (entities != null) {
			Iterator<T> iterator = entities.iterator();
			while (iterator.hasNext()) {
				T entity = iterator.next();
				result.add(save(entity));
			}
		}
		return result;
	}

	/**
	 * 更新特定实例。
	 * 
	 * @param entity
	 *            持久化类
	 */
	public <T> void update(T entity) {
		merge(entity);
	}

	@SuppressWarnings("unchecked")
	private <T> boolean isObjectExisted(T entity) {
		String entityName = entity.getClass().getName();
		String hql = " from " + entityName + " where id = "
				+ ((AbstractEntity) entity).getId();
		List<T> result = this.find(hql);
		if (result.size() == 1)
			return true;
		else
			return false;
	}

	/**
	 * 如果session中存在相同持久化标识(identifier)的实例，用用户给出的对象覆盖session已有的持久实例
	 * (1)当我们使用update的时候，执行完成后，会抛出异常:
	 * org.springframework.orm.hibernate3.HibernateSystemException: a different
	 * object with the same identifier value was already associated with the
	 * session: (2)但当我们使用merge的时候，把处理自由态的po对象A的属性copy到session当中处于持久态的po的属性中，
	 * 执行完成后原来是持久状态还是持久态，而我们提供的A还是自由态
	 */
	public <T> void merge(T entity) {
		if (isObjectExisted(entity))
			getHibernateTemplate().merge(entity);
		else
			throw new SystemException("ID是" + ((AbstractEntity) entity).getId()
					+ "的记录不存在,修改失败！");
	}

	/**
	 * 更新多个特定实例（循环依次更新）。
	 * 
	 * @param entities
	 *            持久化类集
	 */
	public <T> void update(Collection<T> entities) {
		if (entities != null) {
			Iterator<T> iterator = entities.iterator();
			while (iterator.hasNext()) {
				T entity = iterator.next();
				update(entity);
			}
		}
	}

	/**
	 * 分页查询HQL结果集。
	 * 
	 * @param queryString
	 *            完整的HQL语句
	 * @param pageInfo
	 *            页面对象（CurrentPageNum 当前页，从零开始；RowsOfPage 每页条数）
	 * @return Page (包括结果集 list，与pageInfo对象）
	 */
	public <T> Page<T> find(String queryString, PageInfo pageInfo) {
		Page<T> result = new Page<T>();
		@SuppressWarnings("unchecked")
		List<T> data = find(queryString, pageInfo.getStartIndex(),
				pageInfo.getRowsOfPage());
		result.setData(data);
		result.setPageInfo(pageInfo);
		preparePageInfo(queryString, null, pageInfo);
		return result;
	}

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
	public <T> Page<T> find(String queryString, Object[] params, PageInfo pageInfo) {
		Page<T> result = new Page<T>();
		@SuppressWarnings("unchecked")
		List<T> data = find(queryString, params, pageInfo.getStartIndex(),
				pageInfo.getRowsOfPage());
		result.setData(data);
		result.setPageInfo(pageInfo);
		preparePageInfo(queryString, params, pageInfo);
		return result;
	}

	private void preparePageInfo(String queryString, Object[] params,
			PageInfo pageInfo) {
		int index = queryString.toUpperCase().indexOf("FROM");
		if (index != -1)
			queryString = queryString.substring(index);
		queryString = new StringBuilder("SELECT COUNT(*) ").append(queryString)
				.toString();
		index = queryString.toUpperCase().lastIndexOf("ORDER");
		if (index != -1)
			queryString = queryString.substring(0, index);
		computePage(queryString, params, pageInfo);
	}

	@SuppressWarnings("unchecked")
	private <T> void computePage(String queryString, Object[] params,
			PageInfo pageInfo) {
		List<T> rowcount = null;
		if (params != null)
			rowcount = getHibernateTemplate().find(queryString, params);
		else
			rowcount = getHibernateTemplate().find(queryString);
		if (rowcount != null && rowcount.size() > 0) {
			int count = ((Number) rowcount.get(0)).intValue();
			pageInfo.setTotalRowCount(count);
			if (count % pageInfo.getRowsOfPage() > 0)
				pageInfo.setTotalPageCount(count / pageInfo.getRowsOfPage() + 1);
			else
				pageInfo.setTotalPageCount(count / pageInfo.getRowsOfPage());
		}
	}

	public <T> Page<T> loadAll(Class<T> entityClass, PageInfo pageInfo) {
		Page<T> result = new Page<T>();
		List<T> data = loadAll(entityClass, pageInfo.getStartIndex(),
				pageInfo.getRowsOfPage());
		result.setData(data);
		result.setPageInfo(pageInfo);
		String queryString = new StringBuilder("SELECT COUNT(*) FROM ").append(
				entityClass.getName()).toString();
		computePage(queryString, null, pageInfo);
		return result;
	}

	/**
	 * 根据条件使用HQL语句查询数据。<br>
	 * <p/>
	 * 具有功能：<br>
	 * 1）支持查询分页，该方法会利用数据库本身的分页技术实现。说明如下：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;a)如果数据库(如MySQL,Oracle,SQL Server2005等)支持limit
	 * n,m，查询效率最高；<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;b)如果数据库(如informix,Sybase 12.5.3,SQL Server等)支持top
	 * n，查询效率次之（查询结果越大，效率越低）； <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;c)如果以上两种均不支持，查询效率最低。<br>
	 * 2）支持查询总记录数<br>
	 * 3）支持order by，group by,having等 <br>
	 * <p/>
	 * 不支持功能：<br>
	 * 1）不支持select里的嵌套子查询。如不允许这种用法：select a,b,(select c from table1) as d from
	 * table2 ...<br>
	 * 2）条件与条件之间不支持or，而是用and。<br>
	 * <p/>
	 * 示例如下：<br>
	 * 
	 * <pre>
	 * 1)查询所有用户信息:
	 * <p/>
	 * xxxDao.select("from TUser",null,null,null,0,0);
	 * <p/>
	 * 2)查询用户名含有"admin"开头，注册日期2006-12-01之前前10条用户信息(用户名及注册日期):
	 * <p/>
	 * xxxDao.select("select userName,createDate from TUser",
	 *               new String[]{"userName","createDate"},
	 *               new String[]{"like",">="},
	 *               new Object[]{"admin%","2006-12-01"},0,10);
	 * <p/>
     * <b>注意：对于多对象关联查询，必须指定返回的对象类型。</b><br>
     * 示例如下：
     * <pre>
     * <b>1. 插寻结果bean，A的属性a、b来源于已影射的bean M,N</b>
     * class A{
     *      String a,b;
     *      public A(String a,String b){
     *          this.a=a;
     *          thia.b=b;
     *      }
     * }
     * <p/>
     * <b>2.编写查询方法</b>
     * Query q= session.createQuery("select new A(M.a,N.b) from M as M,N as N where M.id=N.id");
     *
     * @param hql           HQL查询语句（不带Where条件）。不允许在select段内使用子查询，如不允许这种用法：
     *                      select a,b,(select c from table1) as d from table2 ...
     *                      1)对于查询全部对象属性，(<b>select *</b>)不可写。如：from TUser；
     *                      2)对于查询部分对象属性，则必须写完整，如：select userName,password from TUser;
     * @param propertyNames 查询条件的属性名列表
     * @param operators     查询条件的操作符列表，如果查询条件中存在不为<b>=</b>的操作符，需要填写该列表，否则为null，
     *                      应与属性名列表一一对应。操作符包括=, >=, <=, <>, !=, like。
     * @param values        查询条件的值列表，该列表应当与属性列表一一对应
     * @param offset        查询结果的起始行，从0开始。如果不需要，则设置为-1。
     * @param size          查询结果的最大行数。如果不需要，则设置为-1
     * @param isTotalSize   是否需要返回本次查询的总记录数，默认false
     * @param orderBy       排序字符串,不含order by字符串，如orderBy="a desc,b asc",最后生成为：order by a desc,b asc
     * @param groupBy       分组字符串,不含group by 字符串，如groupBy="a desc,b asc",最后生成为：group by a desc,b asc
     * @param otherCause    where后面的其它语句，如排序(order by),分组(group by)及聚集(having)。
     *                      如"group by name order by name desc"
     * @return Object[]    有两个值，第一个值表示查询结果列表List list = (List)Object[0]
     *         第二个表示查询返回的总记录数，int count = ((Integer)Object[1]).intValue;
     * @throws com.longtop.intelliweb.sample.exception.Exception
     *          基础不可控异常类
     *          </pre>
	 */
	@SuppressWarnings("unchecked")
	private Object[] find(final String hql, final String[] propertyNames,
			final String[] operators, final Object[] values, final int offset,
			final int size, final boolean isTotalSize, final String orderBy,
			final String groupBy, final String otherCause) {

		Map map = (Map) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query;
				String countSql;
				String fullSql;
				Integer count = new Integer(0);
				Map map = new HashMap();

				if (hql == null || hql.trim().equals("")) {
					// log.logError(Message.getMessage(ResourceConstants.E_BASE_0000));
					throw new SQLException();
				}

				String where = "";
				if (hql.toLowerCase().indexOf("where") != -1)
					where = " ";

				if (propertyNames != null && propertyNames.length > 0
						&& values != null && values.length > 0) {
					if (propertyNames.length != values.length) {
						// log.logError(Message.getMessage(ResourceConstants.E_BASE_0000));
						throw new HibernateException(
								"propertyNames length noe equal values length");
					}

					if (operators != null
							&& propertyNames.length != operators.length) {
						// log.logError(Message.getMessage(ResourceConstants.E_BASE_0000));
						throw new HibernateException(
								"propertyNames length noe equal operators length");
					}

					for (int i = 0; i <= propertyNames.length - 1; i++) {
						if ("".equals(where)) {
							where = " where ";
						} else {
							where += "and ";
						}
						if (operators != null
								&& operators[i].equalsIgnoreCase("isnull")) {
							where += propertyNames[i] + " is null ";
						} else if (operators != null
								&& operators[i].equalsIgnoreCase("isnotnull")) {
							where += propertyNames[i] + " is not null ";
						} else if (operators != null
								&& operators[i].equalsIgnoreCase("isempty")) {
							where += propertyNames[i] + " = '' ";
						} else if (operators != null
								&& operators[i].equalsIgnoreCase("isnotempty")) {
							where += propertyNames[i] + " <> '' ";
						} else {
							where += propertyNames[i]
									+ (operators == null
											|| operators[i] == null ? "=" : " "
											+ operators[i]) + " ? ";
						}
					}

					fullSql = hql + where;
					fullSql += orderBy == null || orderBy.trim().equals("") ? ""
							: " order by " + orderBy;
					fullSql += groupBy == null || groupBy.trim().equals("") ? ""
							: " group by " + groupBy;
					fullSql += otherCause == null
							|| otherCause.trim().equals("") ? "" : " "
							+ otherCause;

					query = session.createQuery(fullSql);

					for (int i = 0; i <= values.length - 1; i++) {
						if (operators != null
								&& operators[i].equalsIgnoreCase("isnull"))
							continue;
						if (operators != null
								&& operators[i].equalsIgnoreCase("isnotnull"))
							continue;
						if (operators != null
								&& operators[i].equalsIgnoreCase("isempty"))
							continue;
						if (operators != null
								&& operators[i].equalsIgnoreCase("isnotempty"))
							continue;
						// 要求强类型对应，例如ID必须为long.
						query.setParameter(i, values[i]);
					}

				} else {

					fullSql = hql + where;
					fullSql += orderBy == null || orderBy.trim().equals("") ? ""
							: " order by " + orderBy;
					fullSql += groupBy == null || groupBy.trim().equals("") ? ""
							: " group by " + groupBy;
					fullSql += otherCause == null
							|| otherCause.trim().equals("") ? "" : " "
							+ otherCause;

					query = session.createQuery(fullSql);

				}

				// 如果需要统计本次查询总记录数
				if (isTotalSize) {

					// 生成统计总数查询语句（不能累加order by ，否则效率会受影响）
					countSql = hql + where;
					countSql += groupBy == null || groupBy.trim().equals("") ? ""
							: " group by " + groupBy;
					countSql += otherCause == null
							|| otherCause.trim().equals("") ? "" : " "
							+ otherCause;

					// 生成查询总记录的hql语句。在hql中，不允许在select段内使用子查询，如不允许这种用法：
					// select a,b,(select c from table1) as d from table2 ...
					countSql = "select count(*) from "
							+ countSql.substring(countSql.toLowerCase()
									.indexOf("from") + 5);
					Query query2 = session.createQuery(countSql);

					if (values != null) {
						for (int i = 0; i <= values.length - 1; i++) {
							if (operators != null
									&& operators[i].equalsIgnoreCase("isnull"))
								continue;
							if (operators != null
									&& operators[i]
											.equalsIgnoreCase("isnotnull"))
								continue;
							if (operators != null
									&& operators[i].equalsIgnoreCase("isempty"))
								continue;
							if (operators != null
									&& operators[i]
											.equalsIgnoreCase("isnotempty"))
								continue;
							query2.setParameter(i, values[i]);
						}
					}
					count = new Integer(String.valueOf(query2.list().get(0)));
				}
				if (offset > 0) {
					query.setFirstResult(offset);
				}

				if (size > 0) {
					query.setMaxResults(size);
				}

				map.put("list", query.list());
				map.put("count", count);
				return map;
			}
		});
		return new Object[] { map.get("list"), map.get("count") };
	}

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
	@SuppressWarnings("unchecked")
	public <T> Page<T> find(String hql, QueryConditions condition, PageInfo pageInfo,
			boolean isTotalSize) {
		Page<T> result = new Page<T>();
		// todo: condition is null,请考虑
		int index = condition.getPropertyNames().size();
		String[] propertyNames = new String[index];
		String[] operators = new String[index];

		for (int i = 0; i < index; i++) {
			propertyNames[i] = (String) condition.getPropertyNames().get(i);
			operators[i] = (String) condition.getOperators().get(i);
		}
		// 如果当前页号少于1的话，当前页号等于1
		if (pageInfo.getCurrentPageNum() < 1) {
			pageInfo.setCurrentPageNum(1);
		}

		Object[] objs = find(hql, propertyNames, operators, condition
				.getValues().toArray(), (pageInfo.getCurrentPageNum() - 1)
				* pageInfo.getRowsOfPage(), pageInfo.getRowsOfPage(),
				isTotalSize, condition.getOrderBy(), condition.getGroupBy(),
				condition.getOtherHql());

		if (isTotalSize) {
			int count = ((Integer) objs[1]).intValue();
			pageInfo.setTotalRowCount(count);
			if (count % pageInfo.getRowsOfPage() > 0)
				pageInfo.setTotalPageCount(count / pageInfo.getRowsOfPage() + 1);
			else
				pageInfo.setTotalPageCount(count / pageInfo.getRowsOfPage());
		}
		result.setData((List<T>) objs[0]);
		result.setPageInfo(pageInfo);

		return result;
	}

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
	public List find(String hql, QueryConditions condition) {
		int index = condition.getPropertyNames().size();
		String[] propertyNames = new String[index];
		String[] operators = new String[index];

		for (int i = 0; i < index; i++) {
			propertyNames[i] = (String) condition.getPropertyNames().get(i);
			operators[i] = (String) condition.getOperators().get(i);
		}

		Object[] objs = find(hql, propertyNames, operators, condition
				.getValues().toArray(), 0, Constants.MAX_RETURN_RECORD, false,
				condition.getOrderBy(), condition.getGroupBy(),
				condition.getOtherHql());
		List result = (ArrayList) objs[0];
		return result;
	}

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
	public List find(String hql, QueryConditions condition, int maxRowNum) {
		int index = condition.getPropertyNames().size();
		String[] propertyNames = new String[index];
		String[] operators = new String[index];

		for (int i = 0; i < index; i++) {
			propertyNames[i] = (String) condition.getPropertyNames().get(i);
			operators[i] = (String) condition.getOperators().get(i);
		}

		Object[] objs = find(hql, propertyNames, operators, condition
				.getValues().toArray(), 0, maxRowNum, false,
				condition.getOrderBy(), condition.getGroupBy(),
				condition.getOtherHql());
		List result = (ArrayList) objs[0];
		return result;
	}

}