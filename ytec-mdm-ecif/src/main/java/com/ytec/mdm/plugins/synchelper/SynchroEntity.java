/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper
 * @�ļ�����SynchroEntity.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-24-����4:27:26
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SynchroEntity
 * @��������ά�����ײ���ʱ��¼�ı䶯�����ԭ����ĸ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-24 ����4:27:26   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-24 ����4:27:26
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class SynchroEntity {
	/**
	 * @��������:entityName
	 * @��������:ʵ������
	 * @since 1.0.0
	 */
	String entityName;
	/**
	 * @��������:newEntity
	 * @��������:��ʵ��
	 * @since 1.0.0
	 */
	Object newEntity;
	/**
	 * @��������:odlEntity
	 * @��������:��ʵ��
	 * @since 1.0.0
	 */
	Object odlEntity;
	/**
	 * @��������:opType
	 * @��������:��������  A ������U�޸ģ�Dɾ��
	 * @since 1.0.0
	 */
	String opType;
	
	/**
	 * @��������:incrEntity
	 * @��������:��ֱȶԺ��ʵ��
	 * @since 1.0.0
	 */
	Object incrEntity;
	
	
	public String getEntityName() {
		return entityName;
	}
	public Object getNewEntity() {
		return newEntity;
	}
	
	public Object getOdlEntity() {
		return odlEntity;
	}
	
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public void copyEntity(Object newEntity, Object odlEntity) throws Exception{
		if(newEntity!=null){
			this.newEntity=BeanUtils.cloneBean(newEntity);
			this.entityName=newEntity.getClass().getSimpleName();
		}
		if(odlEntity!=null){
			this.odlEntity=BeanUtils.cloneBean(odlEntity);
			if(entityName==null){
				this.entityName=odlEntity.getClass().getSimpleName();
			}
		}
	}
	
	
	public Object getIncrEntity() {
		return incrEntity;
	}
	/**
	 * @��������:differentialEntity
	 * @��������:��ֱȶ�,�Աȶ�������Щ�����޸ģ��������Է������(������������)
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public abstract boolean differentialEntity();
	
	
}
