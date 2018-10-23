package com.ytec.mdm.base.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ytec.mdm.base.util.ReflectionUtils;


/**
 * <pre>
 * Title:��װJPA��ԭ��API��������
 * Description: ��JPA��ԭ��API���з�װ�����鲻Ҫֱ��ʹ�ô��࣬ʹ�ô��������JPABaseDAO
 * </pre>
 * 
 * @author mengzx mengzx@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
 * </pre>
 */
public class SimpleJPADAO<T, PK extends Serializable> {

	
	protected static Logger log = LoggerFactory.getLogger(SimpleJPADAO.class);
	
	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = (Class<T>)ReflectionUtils.getTypeArguments(SimpleJPADAO.class,this.getClass()).get(0);
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	
	//���캯��
	public SimpleJPADAO(){

	}
	
	public SimpleJPADAO(EntityManager entityManager,Class<T> entityClass){
		
		this.entityManager = entityManager;
		this.entityClass = entityClass;
		
	}
	
	public SimpleJPADAO(Class<T> entityClass){

		this.entityClass = entityClass;
		
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * ���������Ķ���
	 * @param entity 
	 */
	public void persist(final T entity) {
		Assert.notNull(entity, "entity����Ϊ��");
		log.debug("persist entity: {}", entity);
		
		 entityManager.persist(entity);
		
	}
	
	/**
	 * �޸Ķ���
	 * @param entity
	 */
	public T merge(final T entity) {
		Assert.notNull(entity, "entity����Ϊ��");
		log.debug("merge entity: {}", entity);
		return entityManager.merge(entity);
		
	}

	/**
	 * ��������޸Ķ���
	 * @param entity
	 */
	public T save(final T entity) {

		Assert.notNull(entity, "entity����Ϊ��");
		log.debug("save entity: {}", entity);
		
		if (entityManager.contains(entity))
			return entity;
		
		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		
		Serializable id = metadataUtil.getId(entity);
		if (!validId(id)) {
			this.persist(entity);
			return entity;
		}
		
		@SuppressWarnings("unchecked")
		T prev = entityManager.find((Class<T>) entity.getClass(), id);
		if (prev == null) {
			this.persist(entity);
			return entity;
		} else {
			return this.merge(entity);
		}
		
		
	}

	/**
	 * ɾ������.
	 * 
	 * @param entity .
	 */
	public void remove(final T entity) {
		Assert.notNull(entity, "entity����Ϊ��");
		entityManager.remove(entity);
		log.debug("delete entity: {}", entity);
	}

	
	/**
	 * ����id��ȡ����
	 * @param id
	 * @return
	 */
	public T get(final PK id) {
		Assert.notNull(id, "id����Ϊ��");
		return (T) entityManager.find(this.entityClass, id);
	}
	
	
	/**
	 * ���ݲ�ѯJQL���������б���Query����JQL�в�����˳��󶨣���0��ʼ.
	 * 
	 * @param values һ�����߶������
	 */
	public Query createQueryWithIndexParam(final String queryJQL, final Object... values) {
		Assert.hasText(queryJQL, "queryJQL����Ϊ��");
		Query query = this.entityManager.createQuery(queryJQL);
		if (values != null) {
			int j=1;
			for (int i = 0; i < values.length; i++) {
				query.setParameter(j++, values[i]);
			}
		}
		return query;
	}

	/**
	 * ���ݲ�ѯJQL��������������б���Query����JQL�в��������ư�
	 * 
	 * @param values ����Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Query createQueryWithNameParam(final String queryJQL, final Map<String, ?> values) {
		Assert.hasText(queryJQL, "queryJQL����Ϊ��");
		Query query = this.entityManager.createQuery(queryJQL);
		
		/***
		 * ��JPA��������,values�п��ܳ��ֲ��ǲ��������ݣ������could not locate named parameter�Ĵ���
		 * ***/
//		if (values != null) {
//			
//			Iterator it = values.entrySet().iterator();
//			
//			while(it.hasNext()){
//				
//				Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
//				query.setParameter(entry.getKey(), entry.getValue());
//			}
//			
//		}
		
		/**
		 * 2014-06-20�޸�
		 * ��ȡ����Ҫ�Ĳ�����Ȼ���ٴӲ���MAP�л�ȡ����ֵ
		 * **/
		if(values != null){
			Set<Parameter> parameterSet=(Set)query.getParameters();
			Iterator it =parameterSet.iterator();
			Parameter p=null;
			while(it.hasNext()){
				p=(Parameter)it.next();
				query.setParameter(p.getName(),values.get(p.getName()));
			}
		}
		return query;
	}

	
	/**
	 * ���ݲ�ѯSQL���������б���Query����SQL�в�����˳��󶨣���0��ʼ.
	 * 
	 * @param values һ�����߶������
	 */
	public Query createNativeQueryWithIndexParam(final String sql, final Object... values) {
		
		Assert.hasText(sql, "sql����Ϊ��");
		Query query = this.entityManager.createNativeQuery(sql);
		if (values != null) {
			int j = 1;
			for (int i = 0; i < values.length; i++) {
				query.setParameter(j++, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * ���ݲ�ѯSQL���������б���Query����JQL�в��������ư�
	 * 
	 * @param values һ�����߶������
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Query createNativeQueryWithNameParam(final String sql, final Map<String, ?> values) {
		Assert.hasText(sql, "sql����Ϊ��");
		String sqlStr = sql;
//		if (values != null) {
//			for (String columnName : values.keySet()) {
//				if("".equals(values.get(columnName))||values.get(columnName)==null){
//					int andIndex = sqlStr.substring(0, sqlStr.indexOf(columnName)).lastIndexOf("and");
//					String beforeAnd = sqlStr.substring(0,andIndex );
//					String afterAnd = sqlStr.substring(sqlStr.indexOf(columnName)+columnName.length());
//					sqlStr = beforeAnd + afterAnd;
//				}
//			}
//		}
		Query query = this.entityManager.createNativeQuery(sqlStr);
//		if (values != null) {
//			Iterator it = values.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
//				String columnName = entry.getKey();
//				if(entry.getValue()!=null&&!"".equals(entry.getValue())){
//					if(sql.substring(sql.indexOf(columnName)-6,
//							sql.indexOf(columnName)).indexOf("like")>-1){
//						query.setParameter(entry.getKey(), "%"+entry.getValue()+"%");
//					}else if(sql.contains(":"+columnName)){
//						query.setParameter(entry.getKey(), entry.getValue());
//					}
//				}
//			}
//			
//		}
		
		/**
		 * 2014-06-20�޸�
		 * ��ȡ����Ҫ�Ĳ�����Ȼ���ٴӲ���MAP�л�ȡ����ֵ
		 * **/
		if(values != null){
			Set<Parameter> parameterSet=(Set)query.getParameters();
			Iterator it =parameterSet.iterator();
			Parameter p=null;
			while(it.hasNext()){
				p=(Parameter)it.next();
				query.setParameter(p.getName(),values.get(p.getName()));
			}
		}
		return query;
	}



	/**
	 * Flush��ǰSession.
	 */
	public void flush() {
		this.entityManager.flush();
	}

	
	/**
	 * ��֤ID�Ƿ���Ч
	 * @param id
	 * @return
	 */
	private boolean validId(Serializable id) {
		if (id == null)
			return false;
		if (id instanceof Number && ((Number) id).equals(0))
			return false;
		if (id instanceof String && "".equals(id))
			return false;
		return true;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
}
