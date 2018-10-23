package com.yuchengtech.emp.core.cache;

import java.util.Map;

public abstract interface ICache<K, V> extends Map<K, V> {

	public abstract void reload();

}
