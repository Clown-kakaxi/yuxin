package com.yuchengtech.emp.core.cache;

import java.util.Map;

public abstract interface ICacheLoader<K, V> {

	public abstract Map<? extends K, ? extends V> load();

	public abstract long lastModify();
}
