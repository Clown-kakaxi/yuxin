/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：Observer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:32:38
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：Observer
 * @类描述：事件接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:32:38   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:32:38
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface Observer {
	/**
	 * @函数名称:executeObserver
	 * @函数描述:执行事件通知
	 * @参数与返回说明:
	 * 		@param ecifData ecif数据对象
	 * @算法描述:
	 */
	public void executeObserver(EcifData ecifData);
	/**
	 * @函数名称:init
	 * @函数描述:事件类初始化
	 * @参数与返回说明:
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void init() throws Exception;
}
