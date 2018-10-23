package com.yuchengtech.bcrm.util;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 生成主键
 * @author wx
 *
 */
public class PrimaryKeyUtil {

	private static final AtomicInteger LAST_VALUE=new AtomicInteger(0);
	private static final int MAX = 999999;
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.err.println(PrimaryKeyUtil.getIdOfLong());
		}
	}
	/**
	 * Gets the idof string.
	 * 
	 * @return the idof string
	 */
	
//	public static String getIdofString(){
//		long time = System.currentTimeMillis();
//		String str = String.valueOf(time);
//		String a = str.substring(str.length() - 11);
//		LAST_VALUE.compareAndSet(MAX, 0);
//		return String.format("%d%s%06d", MdmConstants.SYSTEMJVMID,a,LAST_VALUE.incrementAndGet());
//	}
	public static String getIdofString(){
		long time = System.currentTimeMillis();
		String str = String.valueOf(time);
		String a = str.substring(str.length() - 11);
		LAST_VALUE.compareAndSet(MAX, 0);
		return String.format("%d%s%06d", 0,a,LAST_VALUE.incrementAndGet());
	}

	/**
	 * Gets the id of long.
	 * 
	 * @return the id of long
	 * @throws NumberFormatException
	 *             the number format exception
	 */
	public static Long getIdOfLong() throws NumberFormatException{
		return Long.parseLong(getIdofString());
	}
}
