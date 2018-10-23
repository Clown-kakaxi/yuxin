package com.yuchengtech.emp.ecif.base.util;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class CollatorComparator implements Comparator<Object> {
	
	Collator collator = Collator.getInstance();
	
	public int compare(Object e1, Object e2) {
		
		Object[] element1 = (Object[]) e1;
		Object[] element2 = (Object[]) e2;
		// 如果想不让英文区分大小写
		// element1.toString().toLowerCase(),element2.toString().toLowerCase()
		CollationKey key1 = collator.getCollationKey(element1[1].toString());
		CollationKey key2 = collator.getCollationKey(element2[1].toString());
		return key1.compareTo(key2);
		// return -key1.compareTo(key2);
	}

	/**
	 * @param h
	 * @return 实现对map按照value升序排序
	 */
/*	@SuppressWarnings("unchecked")
	public Map.Entry[] getSortedHashtableByValue(Map h) {
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Long key1 = Long.valueOf(((Map.Entry) arg0).getValue()
						.toString());
				Long key2 = Long.valueOf(((Map.Entry) arg1).getValue()
						.toString());
//				CollationKey key1 = collator.getCollationKey(((Map.Entry) arg0).getValue()
//						.toString());
//				CollationKey key2 = collator.getCollationKey(((Map.Entry) arg1).getValue()
//						.toString());
				return key1.compareTo(key2);
			}
		});

		return entries;
	}*/

	/**
	 * @param h
	 * @return 实现对map按照key排序
	 */
/*	@SuppressWarnings("unchecked")
	public Map.Entry[] getSortedHashtableByKey(Map h) {

		Set set = h.entrySet();

		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable) key1).compareTo(key2);
			}

		});

		return entries;
	}*/
}
