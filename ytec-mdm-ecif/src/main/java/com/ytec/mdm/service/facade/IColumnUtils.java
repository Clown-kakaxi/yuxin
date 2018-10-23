/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.facade
 * @文件名：IColumnUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:00:20
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IColumnUtils
 * @类描述：通用字段处理接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:00:20   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:00:20
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IColumnUtils {
	/**
	 * 设置新增信息的通用字段
	 * @param ecifData
	 * @param obj
	 * @return
	 */
	public Object setCreateGeneralColumns(EcifData ecifData, Object obj);
	/**
	 * 设置修改信息的通用字段
	 * @param ecifData
	 * @param obj
	 * @return
	 */
	public Object setUpdateGeneralColumns(EcifData ecifData, Object obj);
	/**
	 *  将业务模型转成对应的历史模型
	 * @param oldObj
	 * @param hisOperSys
	 * @param hisOperType
	 * @return
	 */
	public Object toHistoryObj(Object oldObj,String hisOperSys,String hisOperType);
	
	/**
	 * 将历史模型转成对应的业务模型
	 * @param oldObj
	 * @param objName
	 * @return
	 */
	public  Object backFromHistoryObj(Object oldObj,String objName);

}
