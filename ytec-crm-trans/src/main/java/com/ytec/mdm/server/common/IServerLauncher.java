/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server.common
 * @文件名：IServerLauncher.java
 * @版本信息：1.0.0
 * @日期：2014-4-30-上午10:05:38
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.server.common;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IServerLauncher
 * @类描述：服务器启动接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-30 上午10:05:38   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-30 上午10:05:38
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IServerLauncher {
	/**
	 * @函数名称:start
	 * @函数描述:服务器启动
	 * @参数与返回说明:
	 * 		@param args
	 * @算法描述:
	 */
	public void start(String args[]);
	/**
	 * @函数名称:stop
	 * @函数描述:服务器停止
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void stop();
}
