/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：ErrorEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:10:04
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.core;


import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;


/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：ErrorEcifDealEngine
 * @类描述：默认错误交易引擎
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:10:04
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:10:04
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public class ErrorEcifDealEngine extends AbstractEcifDealEngine {

	/**
	 * @属性名称:errorMsg
	 * @属性描述:错误描述
	 * @since 1.0.0
	 */
	private String errorMsg;

	/**
	 *@构造函数
	 */
	public ErrorEcifDealEngine() {
		super();
	}
	/**
	 *@构造函数
	 * @param errorMsg
	 */
	public ErrorEcifDealEngine(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}


	/* (non-Javadoc)
	 * @see com.ytec.mdm.transaction.core.AbstractTxDealEngine#execute(java.lang.String)
	 */
	@Override
	public void execute(EcifData data) {
		data.setSuccess(false);
		data.setStatus (ErrorCode.ERR_ADAPTER_TRANSFORM_ERROR.getCode(),errorMsg);
		return;
	}

}
