/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：IEcifBizLogic.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:21:01
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;
public interface IEcifBizLogic {
	/**
	 * @函数名称:process
	 * @函数描述:交易业务逻辑处理
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void process(EcifData data)  throws Exception ;

}
