/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����StopWatch.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-20-����2:06:39
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.bo;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�StopWatch
 * @����������ʱ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-20 ����2:06:39
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-20 ����2:06:39
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class StopWatch {
	/**
	 * @��������:runningState
	 * @��������:״̬ 0:���� 1:��ʱ 2:��ͣ 3:ֹͣ
	 * @since 1.0.0
	 */
	private int runningState = 0;
	/**
	 * @��������:startTime
	 * @��������:��ʼʱ��
	 * @since 1.0.0
	 */
	private long startTime = -1L;
	/**
	 * @��������:stopTime
	 * @��������:����ʱ��
	 * @since 1.0.0
	 */
	private long stopTime = -1L;
	/**
	 * @��������:suspendTime
	 * @��������:��ͣ��ʱ
	 * @since 1.0.0
	 */
	private long suspendTime = 0L;

	/**
	 * @��������:start
	 * @��������:��ʼ��ʱ
	 * @�����뷵��˵��:
	 * @�㷨����:
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
	 * @��������:stop
	 * @��������:ֹͣ��ʱ
	 * @�����뷵��˵��:
	 * @�㷨����:
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
	 * @��������:reset
	 * @��������:����
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void reset() {
		this.runningState = 0;
		this.startTime = -1L;
		this.stopTime = -1L;
		this.suspendTime = 0L;
	}

	/**
	 * @��������:suspend
	 * @��������:��ͣ��ʱ
	 * @�����뷵��˵��:
	 * @�㷨����:
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
	 * @��������:resume
	 * @��������:�ָ���ʱ
	 * @�����뷵��˵��:
	 * @�㷨����:
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
	 * @��������:getStartTime
	 * @��������:��ȡ��ʼʱ��
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public long getStartTime() {
		if (this.runningState == 0) {
			System.out.println("Stopwatch has not been started");
			return -1L;
		}
		return this.startTime;
	}

	/**
	 * @��������:getStopTime
	 * @��������:��ȡֹͣʱ��
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public long getStopTime() {
		if (this.runningState == 0 || this.runningState == 1) {
			System.out.println("Stopwatch has not been stoped");
			return -1L;
		}
		return stopTime;
	}

	/**
	 * @��������:getElapsedTime
	 * @��������:��ȡ��ʱ
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
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
