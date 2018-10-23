/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.returncode
 * @文件名：ITxReturnCode.java
 * @版本信息：1.0.0
 * @日期：2013-12-24-上午10:53:05
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common.returncode;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.Error;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ITxReturnCode
 * @类描述：错误代码映射接口
 * @功能描述:提供ECIF错误代码与外围系统错误代码映射功能
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-24 上午10:53:05   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-24 上午10:53:05
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface ITxReturnCode {
	/**
	 * @函数名称:getExterReturnCode
	 * @函数描述:获取外围系统错误代码
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@return Error
	 * @算法描述:
	 */
	public Error getExterReturnCode(EcifData ecifData);
}
