/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ext
 * @文件名：OtherChannelExecutor.java
 * @版本信息：1.0.0
 * @日期：2014-5-7-下午5:44:06
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：OtherChannelExecutor
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-7 下午5:44:06   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-7 下午5:44:06
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class OtherChannelExecutor implements Runnable{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	private IWithChannel work;
	
	public OtherChannelExecutor(IWithChannel work) {
		this.work = work;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		work.otherChannelExe();
	}
	
}
