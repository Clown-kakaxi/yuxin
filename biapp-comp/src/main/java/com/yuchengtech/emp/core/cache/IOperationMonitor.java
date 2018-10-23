package com.yuchengtech.emp.core.cache;

import java.util.concurrent.ConcurrentHashMap;

public abstract interface IOperationMonitor<K, V> {

	public abstract boolean beforeClear(
			ConcurrentHashMap<K, V> paramConcurrentHashMap);

	public abstract boolean beforePut(
			ConcurrentHashMap<K, V> paramConcurrentHashMap, K paramK, V paramV);

	public abstract void afterPut(
			ConcurrentHashMap<K, V> paramConcurrentHashMap, K paramK, V paramV);

	public abstract boolean beforeRemove(
			ConcurrentHashMap<K, V> paramConcurrentHashMap, Object paramObject);

	public abstract void afterRemove(
			ConcurrentHashMap<K, V> paramConcurrentHashMap,
			Object paramObject1, Object paramObject2);
}
