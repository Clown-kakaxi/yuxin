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
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IEcifBizLogic
 * @类描述：:所有业务处理逻辑的接口
 * @功能描述: 交易处理模块和底层业务处理模块交互统一通过此接口，所有的业务逻辑必须实现此接口
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:21:02   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:21:02
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IEcifBizLogic {
	/**
	 * @函数名称:process
	 * @函数描述:交易业务逻辑处理
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void process(EcifData ecifData)  throws Exception ;
}
