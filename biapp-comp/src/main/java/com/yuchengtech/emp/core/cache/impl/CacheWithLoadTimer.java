package com.yuchengtech.emp.core.cache.impl;

import com.yuchengtech.emp.core.cache.ICache;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class CacheWithLoadTimer<K, V> extends Cache<K, V> implements
		InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(CacheWithLoadTimer.class);
	public static final String log001 = "缓存{0}加载数据成功！";
	public static final String log002 = "缓存{0}加载数据失败，缓存实现类为{1}!";
	Timer timer;
	long reloadPeriod = 0L;
	CacheWithLoadTimer<K, V>.CacheReloadTimerTask reloadTask;

	public CacheWithLoadTimer() {
	}

	public CacheWithLoadTimer(Timer timer, long reloadPeriod) {
		this.reloadPeriod = reloadPeriod;
		this.timer = timer;

		if ((timer != null) && (reloadPeriod != 0L)) {
			timer.schedule(this.reloadTask, 0L, this.reloadPeriod);
		}
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timer getTimer() {
		return this.timer;
	}

	public void setReloadPeriod(long reloadPeriod) {
		this.reloadPeriod = reloadPeriod;
	}

	public long getReloadPeriod() {
		return this.reloadPeriod;
	}

	public long getRecentReloadTime() {
		if (this.reloadTask == null)
			return 0L;
		return this.reloadTask.scheduledExecutionTime();
	}

	public void init() {
		if ((this.timer != null) && (this.reloadPeriod != 0L)) {
			this.timer.schedule(this.reloadTask, 0L, this.reloadPeriod);
		}
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		init();
	}

	public class CacheReloadTimerTask extends TimerTask {
		ICache<K, V> cache;

		public CacheReloadTimerTask() {
		}

		public void run() {
			try {
				this.cache.reload();
				CacheWithLoadTimer.log.error("log001",
						new Object[] { CacheWithLoadTimer.this.getName() });
			} catch (Throwable e) {
				CacheWithLoadTimer.log.error("log002", e,
						new Object[] { CacheWithLoadTimer.this.getName(),
						this.cache.getClass() });
			}
		}
	}
}
