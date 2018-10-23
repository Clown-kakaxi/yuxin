/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.general
 * @�ļ�����ColumnUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:59:02
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ColumnUtils
 * @��������ͨ���ֶι���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:59:02   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:59:02
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component
public class ColumnUtils implements IColumnUtils
{

	/**
	 * ����ͨ���ֶθ�ֵ
	 * 
	 * @param general
	 * @param obj
	 * @return
	 */
	private static Logger log = LoggerFactory
			.getLogger(ColumnUtils.class);
	public Object setCreateGeneralColumns(EcifData ecifData, Object obj)
	{
		/*������ϵͳ**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateSys",ecifData.getOpChnlNo());
		/*��������**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateTm",new Timestamp(System.currentTimeMillis()));
		/*������ʱ��**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateUser",ecifData.getTlrNo());
		/**������ˮ��**/
		ReflectionUtils.setFieldValue(obj,"txSeqNo",ecifData.getReqSeqNo());
		return obj;
	}

	/**
	 * ����ͨ���ֶθ�ֵ
	 * 
	 * @param general
	 * @param obj
	 * @return
	 */
	public Object setUpdateGeneralColumns(EcifData ecifData, Object obj)
	{

		/*������ϵͳ**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateSys",ecifData.getOpChnlNo());
		/*��������**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateTm",new Timestamp(System.currentTimeMillis()));
		/*������ʱ��**/
		ReflectionUtils.setFieldValue(obj,"lastUpdateUser",ecifData.getTlrNo());
		/**������ˮ��**/
		ReflectionUtils.setFieldValue(obj,"txSeqNo",ecifData.getReqSeqNo());
		return obj;
	}

	/**
	 * Ϊһ��ӳ��������ɸ�ӳ�����ʷ���ӳ�����
	 * 
	 * @param oldObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object toHistoryObj(Object oldObj,String hisOperSys,String hisOperType) {
		try {
			Class oldClazz = oldObj.getClass();// ԭ��������
			Class newClazz =ServiceEntityMgr.getEntityByName("H"+oldClazz.getSimpleName());// �¶�������
			if(newClazz==null){
				return null;
			}
			Class idClazz = Class.forName(newClazz.getName() + "Id");// �¶������������
			if(idClazz==null){
				return null;
			}
			Object newObj = newClazz.newInstance();// �¶���
			Object idObj = idClazz.newInstance();// ����һ����������
			if(oldClazz!=null){
				Field[] fields=oldClazz.getDeclaredFields();
				for(Field field:fields){
					field.setAccessible(true);
					try{
						ReflectionUtils.setFieldValue(idObj, field.getName(),field.get(oldObj) );
					}catch(Exception e){
						log.debug("����ʷ��������ֵ����",e);
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
			log.error("���ɸ�ӳ�����ʷ���ӳ��������",e);

		}

		return null;

	}
	
	
	
	@SuppressWarnings("unchecked")
	public Object backFromHistoryObj(Object oldObj,String objName) {
		try {
			Class newClazz =ServiceEntityMgr.getEntityByName(objName);// �¶�������
			if(newClazz==null){
				return null;
			}
			Object oldObjId=ReflectionUtils.getFieldValue(oldObj, "id");
			if(oldObjId==null){
				return null;
			}
			Object newObj = newClazz.newInstance();// �¶���
			Field[] fields=newClazz.getDeclaredFields();
			for(Field field:fields){
				try{
					ReflectionUtils.setFieldValue(newObj, field.getName(),ReflectionUtils.getFieldValue(oldObjId, field.getName()) );
				}catch(Exception e){
					log.debug("����ʷ����ȡֵ����ʵ�������",e);
				}
			}
			return newObj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("����ʷ��������ʵ��������",e);

		}
		return null;
	}
}
