package com.yuchengtech.emp.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 功能描述: 所有Dao的统一父类
 * 
 * 
 * Copyright: Copyright (c) 2011 
 * Company: 北京宇信易诚科技有限公司
 * 
 * ************************************************ 
 */
@SuppressWarnings("hiding")
@Component
public abstract class AbstractEMPDaoSupport<EntityManager> {

	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public AbstractEMPDaoSupport() {
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

}