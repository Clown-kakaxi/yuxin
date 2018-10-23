/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：StopWatch.java
 * @版本信息：1.0.0
 * @日期：2014-2-20-下午2:06:39
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.bo;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：StopWatch
 * @类描述：计时器
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-20 下午2:06:39
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-20 下午2:06:39
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class StopWatch {
	/**
	 * @属性名称:runningState
	 * @属性描述:状态 0:就绪 1:计时 2:暂停 3:停止
	 * @since 1.0.0
	 */
	private int runningState = 0;
	/**
	 * @属性名称:startTime
	 * @属性描述:起始时间
	 * @since 1.0.0
	 */
	private long startTime = -1L;
	/**
	 * @属性名称:stopTime
	 * @属性描述:结束时间
	 * @since 1.0.0
	 */
	private long stopTime = -1L;
	/**
	 * @属性名称:suspendTime
	 * @属性描述:暂停耗时
	 * @since 1.0.0
	 */
	private long suspendTime = 0L;

	/**
	 * @函数名称:start
	 * @函数描述:开始计时
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void start() {
		if (this.runningState != 0) {
			System.out.println("Stopwatch already started");
			return;
		}
		this.stopTime = -1L;
		this.suspendTime = 0L;
		this.startTime = System.currentTimeMillis();
		this.runningState = 1;
	}

	/**
	 * @函数名称:stop
	 * @函数描述:停止计时
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void stop() {
		if ((this.runningState != 1) && (this.runningState != 3)) {
			System.out.println("StopWatch is not running");
			return;
		}
		if (this.runningState == 1) {
			this.stopTime = System.currentTimeMillis();
		}
		this.runningState = 3;
	}

	/**
	 * @函数名称:reset
	 * @函数描述:重置
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void reset() {
		this.runningState = 0;
		this.startTime = -1L;
		this.stopTime = -1L;
		this.suspendTime = 0L;
	}

	/**
	 * @函数名称:suspend
	 * @函数描述:暂停计时
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void suspend() {
		if (this.runningState != 1) {
			System.out.println("Stopwatch must be running to suspend. ");
			return;
		}
		this.stopTime = System.currentTimeMillis();
		this.runningState = 2;
	}

	/**
	 * @函数名称:resume
	 * @函数描述:恢复计时
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void resume() {
		if (this.runningState != 2) {
			System.out.println("Stopwatch must be suspended to resume. ");
			return;
		}
		this.suspendTime += System.currentTimeMillis() - this.stopTime;
		this.stopTime = -1L;
		this.runningState = 1;
	}

	/**
	 * @函数名称:getStartTime
	 * @函数描述:获取起始时间
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public long getStartTime() {
		if (this.runningState == 0) {
			System.out.println("Stopwatch has not been started");
			return -1L;
		}
		return this.startTime;
	}

	/**
	 * @函数名称:getStopTime
	 * @函数描述:获取停止时间
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public long getStopTime() {
		if (this.runningState == 0 || this.runningState == 1) {
			System.out.println("Stopwatch has not been stoped");
			return -1L;
		}
		return stopTime;
	}

	/**
	 * @函数名称:getElapsedTime
	 * @函数描述:获取耗时
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public long getElapsedTime() {
		if (this.runningState == 0 || this.runningState == 1) {
			System.out.println("Stopwatch has not been stoped");
			return -1L;
		}
		return this.stopTime - this.startTime - this.suspendTime;
	}

	@Override
	public String toString() {
		return "StopWatch [runningState=" + runningState + ", startTime="
				+ startTime + ", stopTime=" + stopTime + ", suspendTime="
				+ suspendTime + "]";
	}

}
