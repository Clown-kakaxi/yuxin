package com.ytec.mdm.interfaces.socket.http.tools;

import java.util.Arrays;
import java.util.Map;

public class HeaderMap {
	
	/**Ĭ�ϴ�С**/
	private final int INIT_SIZE = 8;
	private final char COLON=58;
	private final char CR=13; // \r
	
	private final char SP= 32;
	private final char LF=10; // \n
	/**ʵ�ʴ�С**/
	private int size = 0;
	private String keys[] = new String[INIT_SIZE];
	private String values[] = new String[INIT_SIZE];
	
	/****
	 * �Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/****
	 * ����httpͷ
	 * @param key
	 * @param obj
	 */

	public void put(String key, String obj) {
		if (size == keys.length - 1) {
			keys = Arrays.copyOf(keys, keys.length * 2);
			values = Arrays.copyOf(values, keys.length * 2);
		}
		keys[size] = key;
		values[size] = obj;
		size += 1;
	}
	
	/****
	 * ��ȡͷ
	 * @param key
	 * @return
	 */

	public Object get(String key) {
		for (int i = 0; i < size; ++i) {
			if (key.equals(keys[i])) {
				return values[i];
			}
		}
		return null;
	}
	
	/****
	 * �Ƿ����kay
	 * @param key
	 * @return
	 */

	public boolean containsKey(String key) {
		for (int i = 0; i < keys.length; ++i) {
			if (key.equals(keys[i])) {
				return true;
			}
		}
		return false;
	}

	/***
	 * ���
	 */
	public void clear() {
		this.size = 0;
	}

	public static HeaderMap camelCase(Map<String, String> map) {
		HeaderMap tmp = new HeaderMap();
		if (map != null) {
			for (Map.Entry<String, String> e : map.entrySet()) {
				tmp.put(HttpUtils.camelCase(e.getKey()), e.getValue());
			}
		}
		return tmp;
	}

	public void encodeHeaders(StringBuffer headBuf) {
		for (int i = 0; i < size; ++i) {
			String k = keys[i];
			Object v = values[i];
			headBuf.append(k);
			headBuf.append(COLON).append(SP);
			headBuf.append(v);
			headBuf.append(CR).append(LF);
		}
	}


}
