/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper
 * @文件名：SynchroEntityHandler.java
 * @版本信息：1.0.0
 * @日期：2014-2-25-下午2:29:10
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SynchroEntityHandler
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-25 下午2:29:10   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-25 下午2:29:10
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
			//对比
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
				log.error("对比异常",e);
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
