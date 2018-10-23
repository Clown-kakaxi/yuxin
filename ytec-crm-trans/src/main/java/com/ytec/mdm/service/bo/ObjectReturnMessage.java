/**
 * @项目名：ytec-mdm-trans
 * @包名：com.ytec.mdm.service.bo
 * @文件名：ObjectReturnMessage.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:55:02
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.service.bo;

import com.ytec.mdm.base.bo.Error;
import com.ytec.mdm.base.bo.ErrorCode;

/**
 * @项目名称：ytec-mdm-trans(CRM)
 * @类名称：ObjectReturnMessage
 * @类描述：根据技术和业务主键查找记录返回值
 * @功能描述:successFlag为真时，object有值；successFlag为假时，erroMessage有值
 * @创建人：wangtb@yuchengtech.com
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class ObjectReturnMessage {
	/**
	 * @属性名称:object
	 * @属性描述:返回对象
	 * @since 1.0.0
	 */
	private Object object;

	/**
	 * The success flag.
	 *
	 * @属性描述:是否成功
	 */
	private boolean successFlag;

	/**
	 * The error.
	 *
	 * @属性描述:错误码
	 */
	private Error error = ErrorCode.SUCCESS;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @函数名称:isSuccessFlag
	 * @函数描述:
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public boolean isSuccessFlag() {
		return successFlag;
	}

	/**
	 * Sets the success flag.
	 *
	 * @param successFlag
	 *        the new success flag
	 */
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error
	 *        the new error
	 */
	public void setError(Error error) {
		this.error = error;
	}
}
