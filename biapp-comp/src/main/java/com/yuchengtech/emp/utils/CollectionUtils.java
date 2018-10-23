package com.yuchengtech.emp.utils;

import java.util.Collection;
import java.util.Iterator;

public final class CollectionUtils {

	public static Collection<?> clearNullElement(Collection<?> collection) {
		if (collection != null) {
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				if (it.next() == null)
					it.remove();
			}
		}
		return collection;
	}

	public static Collection<?> clearElement(Collection<?> collection,
			Object element) {
		if (collection != null && element != null) {
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				if (it.next().equals(element))
					it.remove();
			}
		}
		return collection;
	}

}
