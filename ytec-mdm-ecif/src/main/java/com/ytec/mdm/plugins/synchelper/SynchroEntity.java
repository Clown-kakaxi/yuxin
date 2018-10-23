/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper
 * @文件名：SynchroEntity.java
 * @版本信息：1.0.0
 * @日期：2014-2-24-下午4:27:26
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SynchroEntity
 * @类描述：维护交易操作时记录的变动对象和原对象的副本
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-24 下午4:27:26   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-24 下午4:27:26
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class SynchroEntity {
	/**
	 * @属性名称:entityName
	 * @属性描述:实体名称
	 * @since 1.0.0
	 */
	String entityName;
	/**
	 * @属性名称:newEntity
	 * @属性描述:新实体
	 * @since 1.0.0
	 */
	Object newEntity;
	/**
	 * @属性名称:odlEntity
	 * @属性描述:旧实体
	 * @since 1.0.0
	 */
	Object odlEntity;
	/**
	 * @属性名称:opType
	 * @属性描述:操作类型  A 新增，U修改，D删除
	 * @since 1.0.0
	 */
	String opType;
	
	/**
	 * @属性名称:incrEntity
	 * @属性描述:差分比对后的实体
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
	 * @函数名称:differentialEntity
	 * @函数描述:差分比对,对比对象中哪些做了修改，将该属性分离出来(包括必输属性)
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public abstract boolean differentialEntity();
	
	
}
