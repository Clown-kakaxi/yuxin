/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper
 * @�ļ�����SynchroEntityHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-25-����2:29:10
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SynchroEntityHandler
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-25 ����2:29:10   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-25 ����2:29:10
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SynchroEntityHandler extends SynchroEntity {
	private static Logger log = LoggerFactory.getLogger(SynchroEntityHandler.class);
	/* (non-Javadoc)
	 * @see com.ytec.mdm.plugins.synchelper.SynchroEntity#differentialEntity()
	 */
	@Override
	public boolean differentialEntity() {
		// TODO Auto-generated method stub
		if("A".equals(opType)){
			if(newEntity==null){
				return false;
			}
			incrEntity=newEntity;
		}else if("U".equals(opType)){
			if(newEntity==null&& odlEntity==null){
				return false;
			}
			incrEntity=newEntity;
			//�Ա�
			Class clazz = newEntity.getClass();
			Field[] fields = clazz.getDeclaredFields();
			try{
				for (Field field : fields) {
					field.setAccessible(true);
					Object oldValue;
					oldValue = field.get(odlEntity);
					Object newValue;
					newValue = field.get(newEntity);
					if (newValue != null) {
						
					}else{
						
					}
				}
			}catch(Exception e){
				log.error("�Ա��쳣",e);
				return false;
			}
		}else if("D".equals(opType)){
			if(odlEntity==null){
				return false;
			}
			incrEntity=odlEntity;
		}else{
			return false;
		}
		return true;
	}

}
