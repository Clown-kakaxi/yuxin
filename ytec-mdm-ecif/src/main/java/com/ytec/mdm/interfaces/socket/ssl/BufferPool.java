/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ssl
 * @�ļ�����BufferPool.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-9-����2:31:17
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�BufferPool
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-9 ����2:31:17
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-9 ����2:31:17
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class BufferPool {
	/**
	 * @��������:DEFAULT_POOL_SIZE
	 * @��������:Ĭ�ϻ�����д�С
	 * @since 1.0.0
	 */
	private static final int DEFAULT_POOL_SIZE = 4096;
	/**
	 * @��������:DEFAULT_BUF_SIZE
	 * @��������:Ĭ�ϻ����С
	 * @since 1.0.0
	 */
	private static final int DEFAULT_BUF_SIZE = 20480;
	private final ByteBuffer[] pool;
	private final int MAXBUFFERCOUNT;
	private final int BUFFER_SIZE;
	/**
	 * @��������:NEXT_INDEX
	 * @��������:�¸������±�
	 * @since 1.0.0
	 */
	private static final AtomicInteger NEXT_INDEX = new AtomicInteger(0);

	/**
	 *@���캯�� 
	 */
	public BufferPool() {
		this(DEFAULT_POOL_SIZE, DEFAULT_BUF_SIZE);
	}

	/**
	 *@���캯�� 
	 * @param maxPoolSize  ���д�С
	 * @param maxBufSize   �����С
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
	 * @��������:acquireBuffer
	 * @��������:���뻺��
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
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
	 * @��������:releaseBuffer
	 * @��������:�ͷŻ���
	 * @�����뷵��˵��:
	 * 		@param buffer
	 * @�㷨����:
	 */
	public void releaseBuffer(ByteBuffer buffer) {
		if (!NEXT_INDEX.compareAndSet(0, 0)) {
			this.pool[NEXT_INDEX.decrementAndGet()] = buffer;
		}
	}
	
	/**
	 * @��������:resizeRequestBuffer
	 * @��������:��������������
	 * @�����뷵��˵��:
	 * 		@param requestBuffer
	 * 		@return
	 * @�㷨����:
	 */
	public ByteBuffer resizeRequestBuffer(ByteBuffer requestBuffer) {
		ByteBuffer bb = ByteBuffer.allocate(requestBuffer.capacity() * 2);
		requestBuffer.flip(); // ����ǰ��Ҫflip()һ��
		bb.put(requestBuffer); // ��ԭ���������е����ݿ������µĻ�����
		return bb;
	}
	
	
	

}
