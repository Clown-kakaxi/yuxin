/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����JarLoader.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-1-2-����4:21:19
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�JarLoader
 * @������������ʱ����JAR
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-1-2 ����4:21:19
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-1-2 ����4:21:19
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class JarLoader {
	private URLClassLoader urlClassLoader;
	private Method addURL=null;

	public JarLoader(URLClassLoader urlClassLoader) {
		this.urlClassLoader = urlClassLoader;
	}

	private void loadJar(URL url) throws Exception {
		addURL.invoke(urlClassLoader, url);
	}

	/**
	 * @��������:loadjar
	 * @��������:����jar�ļ�
	 * @�����뷵��˵��:
	 * 		@param path    lib��·��
	 * 		@throws MalformedURLException
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void loadjar(String path)
			throws MalformedURLException, Exception {
		File libdir = new File(path);
		if (libdir != null && libdir.isDirectory()) {
			File[] listFiles = libdir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					// TODO Auto-generated method stub
					return file.exists() && file.isFile()&& file.getName().endsWith(".jar");
				}
			});
			addURL = URLClassLoader.class.getDeclaredMethod("addURL",
					URL.class);
			addURL.setAccessible(true);
			for (File file : listFiles) {
				loadJar(file.toURI().toURL());
			}
		} else {
			System.out.println("[Console Message] Directory [" + path+ "] does not exsit, please check it");
			System.exit(1);
		}
	}
}