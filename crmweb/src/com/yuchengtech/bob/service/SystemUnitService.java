package com.yuchengtech.bob.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.SystemUnits;

@Service
@Transactional(value="postgreTransactionManager")
public class SystemUnitService {

	private EntityManager em;

	@PersistenceContext(unitName = "BOB_JPA_ORACLE")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	private EntityManager getEntityManager() {
		return em;
	}

	/**
	 * 保存：包括新增和修改
	 * 
	 * @param wri
	 * */
	public void save(SystemUnits wri) {
		if (wri.getId() == null) {
			em.persist(wri);
		} else {
			em.merge(wri);
		}
	}

	/**
	 * 移除记录
	 * 
	 * @param id
	 */
	public void remove(Long id) {
		em.remove(em.find(SystemUnits.class, id));
	}

	/**
	 * 查看记录
	 * 
	 * @param id
	 * @return
	 */
	public SystemUnits find(Long id) {
		return em.find(SystemUnits.class, id);
	}

	/**
	 * 根据机构的ID取得机构的名称
	 * 
	 * @param unitid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SystemUnits> getUnitByunitId(Long unitid) {

		Query query = getEntityManager().createQuery("select su FROM SystemUnits su where su.id=?1");
		query.setParameter(1, unitid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SystemUnits> findAll() {
		String wriFindAll = "Select wri from SystemUnits wri";
		Query wriQuery = em.createQuery(wriFindAll);
		return wriQuery.getResultList();
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @see 根据机构的ID取得机构的名称(重載)
	 */
	public SystemUnits findUnitByunitId(Long id) {
		Query query = getEntityManager().createQuery("select su FROM SystemUnits su where su.id=?1");
		query.setParameter(1, id);
		return (SystemUnits) query.getSingleResult();

	}

}
