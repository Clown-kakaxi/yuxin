/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.general
 * @文件名：ColumnUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:59:02
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.general;


import java.lang.reflect.Field;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.service.facade.IColumnUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ColumnUtils
 * @类描述：通用字段工具
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:59:02   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:59:02
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
public class ColumnUtils implements IColumnUtils
{

	/**
	 * 新增通用字段赋值
	 * 
	 * @param general
	 * @param obj
	 * @return
	 */
	private static Logger log = LoggerFactory
			.getLogger(ColumnUtils.class);
	public Object setCreateGeneralColumns(EcifData ecifData, Object obj)
	{
		/*最后更新系统**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateSys",ecifData.getOpChnlNo());
		/*最后更新人**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateTm",new Timestamp(System.currentTimeMillis()));
		/*最后更新时间**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateUser",ecifData.getTlrNo());
		/**交易流水号**/
		ReflectionUtils.setFieldValue(obj,"txSeqNo",ecifData.getReqSeqNo());
		return obj;
	}

	/**
	 * 更新通用字段赋值
	 * 
	 * @param general
	 * @param obj
	 * @return
	 */
	public Object setUpdateGeneralColumns(EcifData ecifData, Object obj)
	{

		/*最后更新系统**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateSys",ecifData.getOpChnlNo());
		/*最后更新人**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateTm",new Timestamp(System.currentTimeMillis()));
		/*最后更新时间**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateUser",ecifData.getTlrNo());
		/**交易流水号**/
		ReflectionUtils.setFieldValue(obj,"txSeqNo",ecifData.getReqSeqNo());
		return obj;
	}

	/**
	 * 为一个映射对象，生成该映射的历史表的映射对象
	 * 
	 * @param oldObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object toHistoryObj(Object oldObj,String hisOperSys,String hisOperType) {
		try {
			Class oldClazz = oldObj.getClass();// 原对象类型
			Class newClazz =ServiceEntityMgr.getEntityByName("H"+oldClazz.getSimpleName());// 新对象类型
			if(newClazz==null){
				return null;
			}
			Class idClazz = Class.forName(newClazz.getName() + "Id");// 新对象的主键类型
			if(idClazz==null){
				return null;
			}
			Object newObj = newClazz.newInstance();// 新对象
			Object idObj = idClazz.newInstance();// 生成一个主键对象
			if(oldClazz!=null){
				Field[] fields=oldClazz.getDeclaredFields();
				for(Field field:fields){
					field.setAccessible(true);
					try{
						ReflectionUtils.setFieldValue(idObj, field.getName(),field.get(oldObj) );
					}catch(Exception e){
						log.debug("向历史类中设置值错误",e);
					}
				}
			}
			ReflectionUtils.setFieldValue(idObj, "hisOperTime", new Timestamp(System.currentTimeMillis()));
			ReflectionUtils.setFieldValue(idObj, "hisOperSys", hisOperSys);
			ReflectionUtils.setFieldValue(idObj, "hisOperType", hisOperType);
			ReflectionUtils.setFieldValue(newObj, "id", idObj);
			return newObj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("生成该映射的历史表的映射对象错误",e);

		}

		return null;

	}
	
	
	
	@SuppressWarnings("unchecked")
	public Object backFromHistoryObj(Object oldObj,String objName) {
		try {
			Class newClazz =ServiceEntityMgr.getEntityByName(objName);// 新对象类型
			if(newClazz==null){
				return null;
			}
			Object oldObjId=ReflectionUtils.getFieldValue(oldObj, "id");
			if(oldObjId==null){
				return null;
			}
			Object newObj = newClazz.newInstance();// 新对象
			Field[] fields=newClazz.getDeclaredFields();
			for(Field field:fields){
				try{
					ReflectionUtils.setFieldValue(newObj, field.getName(),ReflectionUtils.getFieldValue(oldObjId, field.getName()) );
				}catch(Exception e){
					log.debug("从历史类中取值设置实体类错误",e);
				}
			}
			return newObj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("生历史数据生成实体对象错误",e);

		}
		return null;
	}
}
