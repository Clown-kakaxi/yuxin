/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ext
 * @文件名：IWithChannel.java
 * @版本信息：1.0.0
 * @日期：2014-5-7-下午5:32:59
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IWithChannel
 * @类描述：带通道的执行接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-7 下午5:32:59   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-7 下午5:32:59
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IWithChannel {
	/**
	 * @函数名称:asserOtherChannel
	 * @函数描述:断定该交易是否提交到另一个通道
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public boolean asserOtherChannel();
	/**
	 * @函数名称:otherChannelExe
	 * @函数描述:另一个通道执行
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void otherChannelExe();
}
