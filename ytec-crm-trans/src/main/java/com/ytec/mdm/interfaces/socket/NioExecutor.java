/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：NioExecutor.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:46:52
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：NioExecutor
 * @类描述：NIO执行接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:46:52   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:46:52
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface NioExecutor extends Runnable{
	/**
	 * 初始化方法
	 * 
	 * @param session 请求方session
	 * @param selector 服务器selector
	 * @param pool 异步处理线程池大小
	 */
	public void init(IoSession session, NioProcessor selector) ;
	
	/**
	 * @函数名称:rejectedExecution
	 * @函数描述:流量控制，拒绝
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void rejectedExecution();

}
