package com.yuchengtech.emp.core.dao;

import java.util.List;
import java.util.Map;
import antlr.Version;
import com.yuchengtech.emp.core.dao.jdbc.support.StoredProcedure;

/**
 * 功能描述: 基于Jdbc的entity管理接口类 提供基于jdbc的sql执行功能，包括存储过程执行， 同时提供基于sql的查询功能 ，主要用于多表关联查询，特别是左/右连接查询。 
 * 
 * Copyright: Copyright (c) 2011 
 * Company: 北京宇信易诚科技有限公司
 * 
 * @author 陈路凝
 * @version 1.0 2011-5-18下午03:20:38
 * @see HISTORY 2011-5-18下午03:20:38 创建文件
 *************************************************/
public interface JdbcEntityManager extends EntityManager {

	/**
	 * 可直接运行的sql语句, 该方法有风险，客户端采用sql注入攻击时，会对系统有危害， 建议仅针对存储过程使用
	 * 
	 * @param sql
	 *            sql语句 例如：update pub_user set a ='newea' ,b=3 where c = 5
	 * @return
	 */
	public void executeSQL(String sql);
	
	/**
	 * 执行指定的sql并返回更新的记录数。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @return 更新的记录数
	 */
	public int update(String sql, Object[] params);

	/**
	 * 执行sql 注意：这里的sql不支持 命名参数，只能用 ? 代替
	 * 
	 * @param sql
	 *            sql语句 例如：update ca_user set a =? ,b=? where c = ?
	 * @param args
	 *            sql参数
	 * @param types
	 *            sql参数类型，详细参见java.sql.Types
	 * @return
	 */
	public int update(String sql, Object[] params, int[] types);

	/**
	 * 批量操作
	 * 
	 * @param sql
	 *            String[0]: update pub_user set a ='newea' ,b=3 where c = 5
	 * @return
	 */
	public int[] batchUpdate(String[] sql);

	/**
	 * 根据条件查询实体Object
	 * 
	 * @param sql
	 *            SQL语句
	 * @param entityClass
	 *            返回的实体类型
	 * @return Date：2013-1-22下午5:47:53
	 * 
	 * @since {@link Version 1.0.1}
	 */
	public <T> T findObject(String sql, Class<T> entityClass) ;
	
	
	/**
	 * 根据条件查询实体Object
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL中参数
	 * @param entityClass
	 *            返回的实体类型
	 * @return Date：2013-1-22下午5:47:53
	 * 
	 * @since {@link Version 1.0.1}
	 */
	public <T> T findObject(String sql, Object[] params, Class<T> entityClass) ;
	
	/**
	 * 执行指定的sql,并返回指定实体类型的集合，主要用于多表关联，左右连接查询， 指定的实现类型必须声明一个不带参数的构造方法。 例如，如下调用方式
	 * this.getEntityManager().find("select r.url as url,r.id as id,rr.role_id as roleId"
	 * +"from abf_resource r left join abf_role_resource rr on r.id=rr.resource_id", ResourceRoleVo.class); ResourceRoleVo类必须声明一个不带参数的构造方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param entityClass
	 *            返回的实体类型
	 * @return 查询结果
	 */
	public <T> List<T> find(String sql, Class<T> entityClass);

	/**
	 * 执行指定的sql, sql中可以用?参数 同样，指定的实现类型必须声明一个不带参数的构造方法。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL中参数
	 * @param entityClass
	 *            返回的实体类型
	 * @return 查询结果
	 */
	public <T> List<T> find(String sql, Object[] params, Class<T> entityClass);

	/**
	 * 执行指定的sql, 并可以指定返回结果的开始记录和大小 同样，指定的实现类型必须声明一个不带参数的构造方法。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param entityClass
	 *            返回的实体类型
	 * @param start
	 *            返回的结果开始记录
	 * @param size
	 *            返回的结果集大小
	 * @return 查询结果
	 */
	public <T> List<T> find(String sql, Class<T> entityClass, int start, int size);

	/**
	 * 执行指定的sql, 并可以指定返回结果的开始记录和大小 同样，指定的实现类型必须声明一个不带参数的构造方法。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param start
	 *            返回的结果开始记录
	 * @param entityClass
	 *            返回的实体类型
	 * @param size
	 *            返回的结果集大小
	 * @return 查询结果
	 * 
	 * @since {@link Version 1.0.1}
	 */
	public <T> List<T> find(String sql, Object[] params, Class<T> entityClass, int start, int size);
	
	/**
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL参数
	 * @return 
	 * 
	 * @Date：2013-1-22下午10:36:54
	 * 
	 * @since {@link Version 1.0.1}
	 */
	public Map<String,Object> findObject(String sql, Object[] params) ;
	
	/**
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL参数
	 * @return 
	 * 
	 * @Date：2013-1-22下午5:52:52
	 * 
	 * @since {@link Version 1.0.1}
	 */
	public List<Map<String,Object>> find(String sql, Object[] params);

	/**
	 * 获取执行存储方法对象
	 * 
	 * @param StoredProcedureName
	 *            存储过程名称
	 */
	public StoredProcedure getStoredProcedure(String StoredProcedureName);

}
