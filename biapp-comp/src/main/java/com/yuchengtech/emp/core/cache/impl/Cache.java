package com.yuchengtech.emp.core.cache.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.yuchengtech.emp.core.cache.ICache;
import com.yuchengtech.emp.core.cache.ICacheLoader;
import com.yuchengtech.emp.core.cache.IOperationMonitor;

public class Cache<K, V> implements ICache<K, V>, InitializingBean {
	
	public static final String log001 = "缓存{0}的ICacheLoader类型的loader为空，该loader属性必须配置。";
	public static final String log002 = "缓存{0}在加载数据时获取信号量失败！";
	private static final Logger log = LoggerFactory.getLogger(Cache.class);
	private static final Semaphore available = new Semaphore(1, true);

	private ConcurrentHashMap<K, V> conMapCache = new ConcurrentHashMap<K, V>(9999);

	private IOperationMonitor<K, V> monitor = null;

	private long lastModify = 0L;

	private String name = null;

	private boolean loadOnStart = true;

	private ICacheLoader<K, V> loader = null;

	public Cache() {
	}

	public Cache(String name, ICacheLoader<K, V> loader, boolean loadOnStart) {
		this.name = name;
		this.loader = loader;
		this.loadOnStart = loadOnStart;
		if (loadOnStart)
			reload();
	}

	public IOperationMonitor<K, V> getMonitor() {
		return this.monitor;
	}

	public void setMonitor(IOperationMonitor<K, V> monitor) {
		this.monitor = monitor;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLoadOnStart(boolean ls) {
		this.loadOnStart = ls;
	}

	public void setLoader(ICacheLoader<K, V> loader) {
		this.loader = loader;
	}

	public boolean containsKey(Object key) {
		return this.conMapCache.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.conMapCache.contains(value);
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return this.conMapCache.entrySet();
	}

	public V get(Object key) {
		return this.conMapCache.get(key);
	}

	public boolean isEmpty() {
		return this.conMapCache.isEmpty();
	}

	public Set<K> keySet() {
		return this.conMapCache.keySet();
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		if (null != m)
			this.conMapCache.putAll(m);
	}

	public int size() {
		return this.conMapCache.size();
	}

	public Collection<V> values() {
		return this.conMapCache.values();
	}

	public void reload() {
		if (this.loader == null) {
			RuntimeException rte = new RuntimeException("loader is null");
			log.error("log001", rte, new Object[] { this.name });
			throw rte;
		}
		try {
			available.acquire(1);
			long nt = this.loader.lastModify();
			if ((nt == 0L) || (nt > this.lastModify)) {
				ConcurrentHashMap<K, V> tmpmap = new ConcurrentHashMap<K, V>();
				Map<? extends K, ? extends V> currentmap = this.loader.load();
				if (null != currentmap) {
					tmpmap.putAll(currentmap);
					this.conMapCache = tmpmap;
				}

				tmpmap = null;

				this.lastModify = nt;
			}
		} catch (InterruptedException e) {
			log.error("log002", e, new Object[] { this.name });

			return;
		} finally {
			available.release(1);
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (this.loadOnStart)
			reload();
	}

	public void clear() {
		if (this.monitor == null) {
			this.conMapCache.clear();
			return;
		}
		if (this.monitor.beforeClear(this.conMapCache))
			this.conMapCache.clear();
		else
			throw new RuntimeException("cache[" + this.name
					+ "] clear() operation is rejected by listener.");
	}

	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if (this.monitor == null) {
			return this.conMapCache.put(key, value);
		}
		if (this.monitor.beforePut(this.conMapCache, key, value)) {
			Object v = this.conMapCache.put(key, value);
			this.monitor.afterPut(this.conMapCache, key, value);
			return (V) v;
		}
		throw new RuntimeException("cache[" + this.name
				+ "] put() operation is rejected by listener.");
	}

	@SuppressWarnings("unchecked")
	public V putIfAbsent(K key, V value) {
		if (this.monitor == null) {
			return this.conMapCache.putIfAbsent(key, value);
		}
		if (this.monitor.beforePut(this.conMapCache, key, value)) {
			Object v = this.conMapCache.putIfAbsent(key, value);
			this.monitor.afterPut(this.conMapCache, key, value);
			return (V) v;
		}
		throw new RuntimeException("cache[" + this.name
				+ "] put() operation is rejected by listener.");
	}

	@SuppressWarnings("unchecked")
	public V replace(K key, V value) {
		if (this.monitor == null) {
			return this.conMapCache.replace(key, value);
		}
		if (this.monitor.beforePut(this.conMapCache, key, value)) {
			Object v = this.conMapCache.replace(key, value);
			if (v != null)
				this.monitor.afterPut(this.conMapCache, key, value);
			return (V) v;
		}
		throw new RuntimeException("cache[" + this.name
				+ "] replace() operation is rejected by listener.");
	}

	public boolean replace(K key, V oldValue, V newValue) {
		if (this.monitor == null) {
			return this.conMapCache.replace(key, oldValue, newValue);
		}
		if (this.monitor.beforePut(this.conMapCache, key, newValue)) {
			if (this.conMapCache.replace(key, oldValue, newValue)) {
				this.monitor.afterPut(this.conMapCache, key, newValue);
				return true;
			}
			return false;
		}
		throw new RuntimeException("cache[" + this.name
				+ "] replace() operation is rejected by listener.");
	}

	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		if (this.monitor == null) {
			return this.conMapCache.remove(key);
		}
		if (this.monitor.beforeRemove(this.conMapCache, key)) {
			Object v = this.conMapCache.remove(key);
			if (v != null)
				this.monitor.afterRemove(this.conMapCache, key, v);
			return (V) v;
		}
		throw new RuntimeException("cache[" + this.name
				+ "] remove() operation is rejected by listener.");
	}

	public boolean remove(Object key, Object value) {
		if (this.monitor == null) {
			return this.conMapCache.remove(key, value);
		}
		if (this.monitor.beforeRemove(this.conMapCache, key)) {
			if (this.conMapCache.remove(key, value)) {
				this.monitor.afterRemove(this.conMapCache, key, value);
				return true;
			}
			return false;
		}
		throw new RuntimeException("cache[" + this.name + "] remove() operation is rejected by listener.");
	}
}