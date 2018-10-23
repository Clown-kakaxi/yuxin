/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common
 * @文件名：IServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:35:16
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common;

import java.util.Map;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IServer
 * @类描述：服务端接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:35:04   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:35:04
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IServer {
	/**
	 * @函数名称:stop
	 * @函数描述:服务端停止
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void stop() ;
	/**
	 * @函数名称:start
	 * @函数描述:服务端启动
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void start();
	/**
	 * @函数名称:init
	 * @函数描述:服务端初始化
	 * @参数与返回说明:
	 * 		@param arg
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void init(Map arg) throws Exception;
}
