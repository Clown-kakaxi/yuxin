/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：IReturnMessage.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:00:45
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.bo;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IReturnMessage
 * @类描述：返回参数
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:00:56   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:00:56
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class IReturnMessage {
	
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
	private Error error=ErrorCode.SUCCESS;

	
	/**
	 * @函数名称:isSuccessFlag
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public boolean isSuccessFlag() {
		return successFlag;
	}

	/**
	 * Sets the success flag.
	 * 
	 * @param successFlag
	 *            the new success flag
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
	 *            the new error
	 */
	public void setError(Error error) {
		this.error = error;
	}

}
