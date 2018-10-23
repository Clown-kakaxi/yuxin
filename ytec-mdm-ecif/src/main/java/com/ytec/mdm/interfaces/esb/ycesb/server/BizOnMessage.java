/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.esb.ycesb.server
 * @�ļ�����BizOnMessage.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-9-15:44:08
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.interfaces.esb.ycesb.server;
import java.util.concurrent.ExecutorService;
import spc.webos.queue.AbstractReceiverThread;
import spc.webos.queue.AccessTPool;
import spc.webos.queue.IOnMessage;
import spc.webos.queue.QueueMessage;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�BizOnMessage
 * @��������YC ESB IOnMessageʵ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-9 ����3:43:54   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-9 ����3:43:54
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class BizOnMessage implements IOnMessage {
	/**
	 * @��������:adapterClazz
	 * @��������:YC ESB�ӿ�ִ����
	 * @since 1.0.0
	 */
	private Class adapterClazz;
	/**
	 * @��������:executorPool
	 * @��������:�����̳߳�
	 * @since 1.0.0
	 */
	private ExecutorService executorPool;
	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@param ycEsbExecutor YC ESB�ӿ�ִ����
	 * 		@param pool �����̳߳�
	 * @�㷨����:
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
			if(this.executorPool!=null){/**�ڹ����̳߳�������**/
				executorPool.execute(ycEsbExecutor);
			}else{
				ycEsbExecutor.run();
			}
		}
	}
}
