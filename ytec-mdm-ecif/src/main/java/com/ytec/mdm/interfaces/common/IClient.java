/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common
 * @文件名：IClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:34:12
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common;

import java.util.Map;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IClient
 * @类描述：客户端接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:34:12   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:34:12
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IClient {
	/**
	 * @函数名称:sendMsg
	 * @函数描述:请求发送
	 * @参数与返回说明:
	 * 		@param ClientResponse
	 * 		@return
	 * @算法描述:
	 */
	public ClientResponse sendMsg(String msg);
	/**
	 * @函数名称:init
	 * @函数描述:客户端初始化
	 * @参数与返回说明:
	 * 		@param arg
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void init(Map arg)throws Exception;
}
