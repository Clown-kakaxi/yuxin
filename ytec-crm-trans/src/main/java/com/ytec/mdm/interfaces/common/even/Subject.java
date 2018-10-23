/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：Subject.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:33:11
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.interfaces.common.even;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：Subject
 * @类描述：事件管理接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:33:12
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:33:12
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public interface Subject {
	/**
	 * 增加事件
	 * @param o 事件对象
	 */
	 public void addObserver(Observer o);

	 /**
	 * @函数名称:eventNotify
	 * @函数描述:进行事件通知同步
	 * @参数与返回说明:
	 * 		@param ecifData
	 * @算法描述:
	 */
	public void eventNotify(EcifData ecifData);

	 /**
	 * @函数名称:clearObserver
	 * @函数描述:清除事件列表
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void clearObserver();
}
