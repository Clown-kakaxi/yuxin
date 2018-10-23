package com.yuchengtech.crm.log.client;

import java.util.Arrays;

import org.apache.log4j.Logger;
/**
 * 
 * @author 队列抽象类
 *
 * @param <T>
 */
public abstract class AbstractQueue<T> {
	/**
	 * 日志记录类
	 */
	protected static Logger logger;
	/**
	 * 队列默认长度
	 * @return int
	 */
	public abstract int getDefaultSize();
	/**
	 * 保存数组的长度
	 */
	private int capacity;
	/**
	 * 定义一个数组用于保存顺序队列的元素
	 */
	private Object[] elementData;
	/**
	 * 保存顺序队列中元素的当前个数
	 * 即起始游标位置
	 */
	private int front = 0;
	/**
	 * 结束游标位置
	 */
	private int rear = 0;

	// 以默认数组长度创建空顺序队列
	public AbstractQueue() {
		capacity = getDefaultSize();
		elementData = new Object[capacity];
		if (logger == null) {
    		logger = Logger.getLogger(this.getClass());
    	}
	}

	// 以一个初始化元素来创建顺序队列
	public AbstractQueue(T element) {
		this();
		elementData[0] = element;
		rear++;
	}

	/**
	 * 以指定长度的数组来创建顺序队列
	 * 
	 * @param element
	 *            指定顺序队列中第一个元素
	 * @param initSize
	 *            指定顺序队列底层数组的长度
	 */
	public AbstractQueue(T element, int initSize) {
		this.capacity = initSize;
		elementData = new Object[capacity];
		elementData[0] = element;
		rear++;
	}

	/**
	 * 获取顺序队列的大小
	 * @return int
	 */
	public int length() {
		return rear - front;
	}
	/**
	 * 将从front到rear的数据移动到从0开始，同时将起始游标重置为0，结束游标设置为实际数据长度
	 */
	public void move2Top(){
		Object[] newElementData = new Object[capacity];
		System.arraycopy(elementData, front, newElementData, 0,length());
		elementData = newElementData;
		rear = length();
		front = 0;
	}
	/**
	 * 插入队列
	 * @param  T element
	 */
	public void add(T element) {
		if (rear > capacity - 1) {
			logger.info("队列已满,移动队列数据，从头开始");
			move2Top();
		}
		if (rear > capacity - 1) {
			logger.info("队列已满的异常");
			throw new IndexOutOfBoundsException("队列已满的异常");
		}
		elementData[rear++] = element;
	}

	/**
	 * 移除队列
	 * @return
	 */
	public T remove() {
		if (empty()) {
			logger.info("空队列异常");
			throw new IndexOutOfBoundsException("空队列异常");
		}
		// 保留队列的rear端的元素的值
		T oldValue = (T) elementData[front];
		// 释放队列的rear端的元素
		elementData[front++] = null;
		return oldValue;
	}

	/**
	 * 返回队列顶元素，但不删除队列顶元素
	 * @return
	 */
	public T element() {
		if (empty()) {
			logger.info("空队列异常");
			throw new IndexOutOfBoundsException("空队列异常");
		}
		return (T) elementData[front];
	}

	/**
	 * 判断顺序队列是否为空队列
	 * @return
	 */
	public boolean empty() {
		return rear == front;
	}

	/**
	 * 清空顺序队列
	 */
	public void clear() {
		// 将底层数组所有元素赋为null
		Arrays.fill(elementData, null);
		front = 0;
		rear = 0;
	}
	
	public String toString() {
		if (empty()) {
			return "[]";
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (int i = front; i < rear; i++) {
				sb.append(elementData[i].toString() + ", ");
			}
			int len = sb.length();
			return sb.delete(len - 2, len).append("]").toString();
		}
	}

}
