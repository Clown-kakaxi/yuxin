package com.ytec.mdm.base.dao;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ytec.mdm.base.util.SQLUtils;

/**
 * <pre>
 * Title:��װ����JPA�����ݿ��������
 * Description: ��װ�����г��õ����ݲ���������CURD�����Ӳ�ѯ����ҳ���������ݿ⽻������ʱ������ֱ��ʹ�ô�����߼̳д��������չ��
 *              ����ײ㹦�ܻ���googlecode��genericdao�������˼򻯺���չ��
 *              �����ʹ�÷�ʽ�����Բο�JPABaseDaoTest.java�еĲ���������
 *              
 *  Examples:
 *             ֱ��ʹ�ã�               
 *              JPABaseDAO<User,Long> dao = new JPABaseDAO<User,Long>;
 *             
 *              �̳���չ��
 *              
 *              public class UserDAO extends JPABaseDAO<User,Long>{
 *              
 *                 //��չ����
 *              }
 *              
 *              UserDAO dao = new UserDAO();
 * 
 * 
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
@Repository("baseDAO")
public class JPABaseDAO<T, PK extends Serializable> extends SimpleJPADAO<T, PK> {

	protected static Logger log = LoggerFactory.getLogger(JPABaseDAO.class);
	private JPASearchProcessor searchProcessor = null;

	// ���캯��
	public JPABaseDAO() {

		this.searchProcessor = new JPASearchProcessor(
				new JPAAnnotationMetadataUtil());
	}

	public JPABaseDAO(EntityManager entityManager, Class<T> entityClass) {

		this();
		this.entityManager = entityManager;
		this.entityClass = entityClass;

	}

	public JPABaseDAO(Class<T> entityClass) {

		this();
		this.entityClass = entityClass;

	}

	/**
	 * ��idɾ������.
	 */
	@Transactional
	public void removeById(final PK id) {
		Assert.notNull(id, "id����Ϊ��");
		remove(get(id));
		log.debug("delete entity {},id is {}",
				this.entityClass.getSimpleName(), id);
	}

	/**
	 * ����id��ȡ�����б�
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> get(final Collection<PK> ids) {

		Search search = new Search(this.entityClass);
		search.setResultMode(Search.RESULT_LIST);

		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		T entity;
		try {
			entity = this.entityClass.newInstance();
		} catch (Exception e) {

			log.error("�����������ϲ��Ҷ��� {}�����쳣�� ", this.entityClass.getClass());
			throw new RuntimeException("�����������ϲ��Ҷ������쳣", e);
		}
		String propertyName = metadataUtil.getIdPropertyName(entity);

		SearchUtil.addFilterIn(search, propertyName, ids);

		return searchProcessor.search(entityManager, search);
	}
	
	/**
	 * ����id��ȡ�����б�,������
	 * 
	 * @param ids
	 * @param orderByProperty
	 * @param isDesc true ����false����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> get(final Collection<PK> ids,String orderByProperty,boolean isDesc) {

		Search search = new Search(this.entityClass);
		search.setResultMode(Search.RESULT_LIST);

		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		T entity;
		try {
			entity = this.entityClass.newInstance();
		} catch (Exception e) {

			log.error("�����������ϲ��Ҷ��� {}�����쳣�� ", this.entityClass.getClass());
			throw new RuntimeException("�����������ϲ��Ҷ������쳣", e);
		}
		String propertyName = metadataUtil.getIdPropertyName(entity);

		SearchUtil.addFilterIn(search, propertyName, ids);
		SearchUtil.addSort(search, orderByProperty, isDesc);

		return searchProcessor.search(entityManager, search);
	}

	/**
	 * ��ȡȫ������
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {

		Search search = new Search(this.entityClass);
		search.setResultMode(Search.RESULT_LIST);

		return searchProcessor.search(entityManager, search);
	}

	/**
	 * 
	 * ��ȡȫ������, ֧�ְ���������
	 * 
	 * @param orderByProperty
	 *            �������������
	 * @param isDesc
	 *            true������false:����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderByProperty, boolean isDesc) {

		Search search = new Search(this.entityClass);
		search.setResultMode(Search.RESULT_LIST);

		SearchUtil.addSort(search, orderByProperty, isDesc);

		return searchProcessor.search(entityManager, search);
	}

	/**
	 * �������Բ��Ҷ���ƥ�䷽ʽΪ"="
	 * 
	 * @param propertyName
	 *            ��������
	 * @param value
	 *            ����ֵ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");

		Search search = new Search(this.entityClass);
		SearchUtil.addFilterEqual(search, propertyName, value);
		
		return searchProcessor.search(entityManager, search);
	}
	
	/**
	 * �������Բ��Ҷ���ƥ�䷽ʽΪ"="
	 * 
	 * @param propertyName
	 *            ��������
	 * @param value
	 *            ����ֵ
	 * @param orderByProperty
	 *            ������������        
	 * @param isDesc
	 *            true:����false������           
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertyAndOrder(final String propertyName, final Object value,final String orderByProperty,boolean isDesc) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");

		Search search = new Search(this.entityClass);
		SearchUtil.addFilterEqual(search, propertyName, value);
		SearchUtil.addSort(search, orderByProperty, isDesc);
		
		return searchProcessor.search(entityManager, search);
	}
	
	/**
	 * �������Բ��Ҷ���ƥ�䷽ʽΪ"in"
	 * 
	 * @param propertyName
	 *            ��������
	 * @param value
	 *            ����ֵ
	 * @param orderByProperty
	 *            ������������        
	 * @param isDesc
	 *            true:����false������           
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertyAndOrderWithParams(final String propertyName, final Collection<?> values,final String orderByProperty,boolean isDesc) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");

		Search search = new Search(this.entityClass);
		SearchUtil.addFilterIn(search, propertyName, values);
		SearchUtil.addSort(search, orderByProperty, isDesc);
		
		return searchProcessor.search(entityManager, search);
	}

	/**
	 * �����Բ���Ψһ����, ƥ�䷽ʽΪ���.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(final String propertyName, final Object value) {

		Search search = new Search(this.entityClass);
		search.setResultMode(Search.RESULT_SINGLE);
		SearchUtil.addFilterEqual(search, propertyName, value);

		return (T) searchProcessor.searchUnique(entityManager, search);
	}
	
	/**
	 * �����Բ���Ψһ����, ƥ�䷽ʽΪ���.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty1(final String propertyName, final Object value) {

		Search search = new Search(this.entityClass);
		search.setResultMode(Search.RESULT_SINGLE);
		SearchUtil.addFilterEqual(search, propertyName, value);
		List<T> result=searchProcessor.search(entityManager, search);
		if(result!=null && result.size()>0){
			return result.get(0);
		}
		return null; 
	}

	/**
	 * ��JQL��ѯ�����б�.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findWithIndexParam(final String jql,
			final Object... values) {
		return createQueryWithIndexParam(jql, values).getResultList();
	}

	/**
	 * ��JQL��ѯ�����б�.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findWithNameParm(final String jql,
			final Map<String, ?> values) {
		return createQueryWithNameParam(jql, values).getResultList();
	}
	
	/**
	 * ��ԭ��SQL��ѯ�����б�.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	@SuppressWarnings("unchecked")
	public  List<Object[]> findByNativeSQLWithIndexParam(final String sql,
			final Object... values) {
		return createNativeQueryWithIndexParam(sql, values).getResultList();
	}
	
	
	/**
	 * ��ԭ��SQL��ѯ�����б�.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,�����ư�.
	 */
	@SuppressWarnings("unchecked")
	public  List<Object[]> findByNativeSQLWithNameParam(final String sql,
			final Map<String, ?> values) {
		return createNativeQueryWithNameParam(sql, values).getResultList();
	}

	/**
	 * ��JQL��ѯΨһ����.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueWithIndexParam(final String jql,
			final Object... values) {
		return (X) createQueryWithIndexParam(jql, values).getSingleResult();
	}
	
	/**
	 * ��JQL��ѯΨһ����.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueWithIndexParam1(final String jql,
			final Object... values) {
		try {
			List<X> result=createQueryWithIndexParam(jql, values).getResultList();
			if(result!=null && result.size()>0){
				return result.get(0);
			}
		} catch (NoResultException e) {
			// δ��ѯ�����
		} catch (NonUniqueResultException ne) {
			log.error("��¼��Ψһ");
		}
		return null;
	}

	/**
	 * ��JQL��ѯΨһ����.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueWithNameParam(final String jql,
			final Map<String, ?> values) {
		return (X) createQueryWithNameParam(jql, values).getSingleResult();
	}

	/**
	 * ִ��sql���������޸�/ɾ������.
	 * Query query = em.createNativeQuery("{call GetPersonPartProperties()}");
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 * @return ���¼�¼��.
	 */
	@Transactional
	public int batchExecuteNativeWithIndexParam(final String sql,
			final Object... values) {
		return createNativeQueryWithIndexParam(sql, values).executeUpdate();
	}
	
	/**
	 * ִ��JQL���������޸�/ɾ������. ���ô洢����
	 *
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 * @return ���¼�¼��.
	 */
	@Transactional
	public int batchExecuteWithIndexParam(final String jql,
			final Object... values) {
		return createQueryWithIndexParam(jql, values).executeUpdate();
	}

	/**
	 * ִ��JQL���������޸�/ɾ������.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 * @return ���¼�¼��.
	 */
	@Transactional
	public int batchExecuteWithNameParam(final String jql,
			final Map<String, ?> values) {
		return createQueryWithNameParam(jql, values).executeUpdate();
	}

	/**
	 * ��JQL��ҳ��ѯ�����б�.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <X> SearchResult<X> findPageIndexParam(final int firstResult,
			final int maxResult, final String jql, final Object... values) {

		SearchResult searResult = new SearchResult();

		List objectList = createQueryWithIndexParam(jql, values)
				.setFirstResult(firstResult).setMaxResults(maxResult)
				.getResultList();

		// ��ѯ��¼����
		String countJql = SQLUtils.buildCountSQL(jql);
		Long totalCount = (Long) createQueryWithIndexParam(countJql, values)
				.getSingleResult();

		searResult.setResult(objectList);
		searResult.setTotalCount(totalCount.intValue());

		return searResult;

	}

	/**
	 * ��JQL��ҳ��ѯ�����б�.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <X> SearchResult<X> findPageWithNameParam(final int firstResult,
			final int maxResult, final String jql, final Map<String, ?> values) {

		SearchResult searResult = new SearchResult();

		List objectList = createQueryWithNameParam(jql, values)
				.setFirstResult(firstResult).setMaxResults(maxResult)
				.getResultList();

		// ��ѯ��¼����
		String countJql = SQLUtils.buildCountSQL(jql);
		Long totalCount = (Long) createQueryWithNameParam(countJql, values)
				.getSingleResult();

		searResult.setResult(objectList);
		searResult.setTotalCount(totalCount.intValue());

		return searResult;
	}
	
	
	/**
	 * ���ݲ�ѯ���������ض����б�
	 * @param search  ��ѯ����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> search(Search search){
		
		if (search == null)
			throw new NullPointerException("Search is null.");
		if (search.getSearchClass() == null)
			throw new NullPointerException("Search class is null.");
		if (this.entityClass != null && !search.getSearchClass().equals(this.entityClass))
			throw new IllegalArgumentException("Search class does not match expected type: " + this.entityClass.getName());

		return this.searchProcessor.search(this.entityManager, this.entityClass, search);
		
	}
	
	
    /**
     * ���ݲ�ѯ���������ط��������ĵ��������û������ѯ
     * @param search ��ѯ����
     * @return
     */
	@SuppressWarnings("unchecked")
	public <X> X searchUnique(Search search){
		
		if (search == null)
			throw new NullPointerException("Search is null.");
		if (search.getSearchClass() == null)
			throw new NullPointerException("Search class is null.");

		return (X)this.searchProcessor.searchUnique(this.entityManager, search);
	}

    /**
     * ���ؼ�¼���������ڵ����ѯ
     * @param search  ��ѯ����
     * @return
     */
	public int count(Search search){
		
		if (search == null)
			throw new NullPointerException("Search is null.");
		if (search.getSearchClass() == null)
			throw new NullPointerException("Search class is null.");

		return this.searchProcessor.count(this.entityManager, search);
	}
	
	/**
	 * �����Բ���Ψһ����, ƥ�䷽ʽΪ���.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(Class clazz, final String propertyName, final Object value) {
		Search search = new Search(clazz);
		search.setResultMode(Search.RESULT_SINGLE);
		SearchUtil.addFilterEqual(search, propertyName, value);

		return (T) searchProcessor.searchUnique(entityManager, search);
	}
	
	/**
	 * �����Բ���Ψһ����, ƥ�䷽ʽΪ���.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty1(Class clazz, final String propertyName, final Object value) {
		Search search = new Search(clazz);
		search.setResultMode(Search.RESULT_SINGLE);
		SearchUtil.addFilterEqual(search, propertyName, value);
		List<T> result=searchProcessor.search(entityManager, search);
		if(result!=null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
}
