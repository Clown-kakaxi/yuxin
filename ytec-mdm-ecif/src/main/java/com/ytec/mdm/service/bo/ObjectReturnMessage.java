/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.bo
 * @文件名：ObjectReturnMessage.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:55:02
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.bo;

import com.ytec.mdm.base.bo.IReturnMessage;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ObjectReturnMessage
 * @类描述：根据技术和业务主键查找记录返回值
 * @功能描述:successFlag为真时，object有值；successFlag为假时，erroMessage有值
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:55:02   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:55:02
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ObjectReturnMessage extends IReturnMessage {
	/**
	 * @属性名称:object
	 * @属性描述:返回对象
	 * @since 1.0.0
	 */
	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
