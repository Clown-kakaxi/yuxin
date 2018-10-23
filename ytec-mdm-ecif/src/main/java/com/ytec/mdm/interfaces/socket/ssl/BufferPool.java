/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ssl
 * @文件名：BufferPool.java
 * @版本信息：1.0.0
 * @日期：2014-4-9-下午2:31:17
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：BufferPool
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-9 下午2:31:17
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-9 下午2:31:17
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class BufferPool {
	/**
	 * @属性名称:DEFAULT_POOL_SIZE
	 * @属性描述:默认缓冲队列大小
	 * @since 1.0.0
	 */
	private static final int DEFAULT_POOL_SIZE = 4096;
	/**
	 * @属性名称:DEFAULT_BUF_SIZE
	 * @属性描述:默认缓冲大小
	 * @since 1.0.0
	 */
	private static final int DEFAULT_BUF_SIZE = 20480;
	private final ByteBuffer[] pool;
	private final int MAXBUFFERCOUNT;
	private final int BUFFER_SIZE;
	/**
	 * @属性名称:NEXT_INDEX
	 * @属性描述:下个缓冲下标
	 * @since 1.0.0
	 */
	private static final AtomicInteger NEXT_INDEX = new AtomicInteger(0);

	/**
	 *@构造函数 
	 */
	public BufferPool() {
		this(DEFAULT_POOL_SIZE, DEFAULT_BUF_SIZE);
	}

	/**
	 *@构造函数 
	 * @param maxPoolSize  队列大小
	 * @param maxBufSize   缓冲大小
	 */
	public BufferPool(int maxPoolSize, int maxBufSize) {
		if (maxPoolSize <= 0) {
			this.MAXBUFFERCOUNT = DEFAULT_POOL_SIZE;
		} else {
			this.MAXBUFFERCOUNT = maxPoolSize;
		}
		if (maxBufSize <= 0) {
			this.BUFFER_SIZE = DEFAULT_BUF_SIZE;
		} else {
			this.BUFFER_SIZE = maxBufSize;
		}
		this.pool = new ByteBuffer[this.MAXBUFFERCOUNT];
	}

	/**
	 * @函数名称:acquireBuffer
	 * @函数描述:申请缓冲
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public ByteBuffer acquireBuffer() {
		ByteBuffer point = null;
		if (!NEXT_INDEX.compareAndSet(MAXBUFFERCOUNT-1, MAXBUFFERCOUNT-1)) {
			point = this.pool[NEXT_INDEX.getAndIncrement()];
		}
		if (point == null) {
			return ByteBuffer.allocate(BUFFER_SIZE);
		} else {
			return (ByteBuffer) point.clear();
		}
	}

	/**
	 * @函数名称:releaseBuffer
	 * @函数描述:释放缓冲
	 * @参数与返回说明:
	 * 		@param buffer
	 * @算法描述:
	 */
	public void releaseBuffer(ByteBuffer buffer) {
		if (!NEXT_INDEX.compareAndSet(0, 0)) {
			this.pool[NEXT_INDEX.decrementAndGet()] = buffer;
		}
	}
	
	/**
	 * @函数名称:resizeRequestBuffer
	 * @函数描述:缓冲区扩大两倍
	 * @参数与返回说明:
	 * 		@param requestBuffer
	 * 		@return
	 * @算法描述:
	 */
	public ByteBuffer resizeRequestBuffer(ByteBuffer requestBuffer) {
		ByteBuffer bb = ByteBuffer.allocate(requestBuffer.capacity() * 2);
		requestBuffer.flip(); // 复制前都要flip()一下
		bb.put(requestBuffer); // 把原来缓冲区中的数据拷贝到新的缓冲区
		return bb;
	}
	
	
	

}
