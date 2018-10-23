/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.esb.ycesb.server
 * @文件名：BizOnMessage.java
 * @版本信息：1.0.0
 * @日期：2014-5-9-15:44:08
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.interfaces.esb.ycesb.server;
import java.util.concurrent.ExecutorService;
import spc.webos.queue.AbstractReceiverThread;
import spc.webos.queue.AccessTPool;
import spc.webos.queue.IOnMessage;
import spc.webos.queue.QueueMessage;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：BizOnMessage
 * @类描述：YC ESB IOnMessage实现
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-9 下午3:43:54   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-9 下午3:43:54
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class BizOnMessage implements IOnMessage {
	/**
	 * @属性名称:adapterClazz
	 * @属性描述:YC ESB接口执行类
	 * @since 1.0.0
	 */
	private Class adapterClazz;
	/**
	 * @属性名称:executorPool
	 * @属性描述:工作线程池
	 * @since 1.0.0
	 */
	private ExecutorService executorPool;
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@param ycEsbExecutor YC ESB接口执行类
	 * 		@param pool 工作线程池
	 * @算法描述:
	 */
	public void init(Class ycEsbExecutor,ExecutorService pool) {
		this.adapterClazz = ycEsbExecutor;
		this.executorPool=pool;
	}
	
	/* (non-Javadoc)
	 * @see spc.webos.queue.IOnMessage#onMessage(java.lang.Object, spc.webos.queue.AccessTPool, spc.webos.queue.AbstractReceiverThread)
	 */
	public void onMessage(Object obj, AccessTPool pool,AbstractReceiverThread thread) throws Exception {
		QueueMessage qmsg = (QueueMessage) obj;
		if(qmsg!=null&&qmsg.buf!=null){
			YcEsbExecutor ycEsbExecutor=(YcEsbExecutor)adapterClazz.newInstance();
			ycEsbExecutor.init(qmsg.buf);
			if(this.executorPool!=null){/**在工作线程池中运行**/
				executorPool.execute(ycEsbExecutor);
			}else{
				ycEsbExecutor.run();
			}
		}
	}
}
