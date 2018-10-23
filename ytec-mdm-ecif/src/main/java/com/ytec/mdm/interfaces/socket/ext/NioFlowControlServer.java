/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：NioServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:45:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import com.ytec.mdm.interfaces.socket.NioServer;
import com.ytec.mdm.interfaces.socket.Server_I;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：NioServer
 * @类描述： nio 监听
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:40   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:40
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class NioFlowControlServer extends NioServer{
	/***
	 * 构造函数
	 */
	public NioFlowControlServer(){
		super();
	}
	
	protected Server_I getServer_I(){
		return new FlowServer_I();
	}
}





