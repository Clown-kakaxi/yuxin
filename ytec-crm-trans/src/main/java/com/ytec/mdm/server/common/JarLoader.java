/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server.common
 * @文件名：JarLoader.java
 * @版本信息：1.0.0
 * @日期：2014-1-2-下午4:21:19
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：JarLoader
 * @类描述：运行时加载JAR
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-1-2 下午4:21:19
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-1-2 下午4:21:19
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
	 * @函数名称:loadjar
	 * @函数描述:加载jar文件
	 * @参数与返回说明:
	 * 		@param path    lib包路径
	 * 		@throws MalformedURLException
	 * 		@throws Exception
	 * @算法描述:
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