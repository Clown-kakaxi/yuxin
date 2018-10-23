/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ext
 * @文件名：ExtServer_I.java
 * @版本信息：1.0.0
 * @日期：2014-4-24-下午3:43:06
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import java.nio.channels.SocketChannel;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioExecutor;
import com.ytec.mdm.interfaces.socket.Server_I;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ExtServer_I
 * @类描述： nio 服务
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:47   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:47
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ExtServer_I extends Server_I{

	/**
	 * 构造函数
	 * 
	 */
	public ExtServer_I() {
		super();
	}
	/**
	 * 构造函数
	 * 
	 * @param poolsize 线程池大小
	 */
	public ExtServer_I(int poolsize) {
		super(poolsize);
	}
	
	protected NioExecutor buildExecutor(IoSession session) throws Exception{
		NioExecutor executor=null;
		Class clazz=((ExtIoSession)session).getClazz();
		if(clazz!=null){
			executor=(NioExecutor)clazz.newInstance();
		}else{
			executor=(NioExecutor)this.clazz.newInstance();
		}
		executor.init(session, this);
		return executor;
	}
	
	protected IoSession buildIoSession(SocketChannel client) throws Exception{
		return new ExtIoSession(client);
	}
}