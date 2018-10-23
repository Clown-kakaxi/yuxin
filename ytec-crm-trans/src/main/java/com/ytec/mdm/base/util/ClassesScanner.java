/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：ClassesScanner.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:03:48
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：ClassesScanner
 * @类描述：从包package中获取所有的Class
 * @功能描述:从包package中获取所有的Class
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:03:54
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:03:54
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ClassesScanner {

	/**
	 * @函数名称:scanEntityByPackages
	 * @函数描述:从包package中获取所有的Class
	 * @参数与返回说明:
	 * @param packages
	 * @return
	 * @throws IOException
	 * @算法描述:
	 */
	public static Map<String, String> scanEntityByPackages(String packages[])
			throws IOException {
		// 第一个class类的集合
		Map<String, String> classes = new HashMap<String, String>();
		// 是否循环迭代
		boolean recursive = true;
		for (String pack : packages) {
			// 获取包的名字 并进行替换
			String packageName = pack;
			String packageDirName = packageName.replace('.', '/');
			// 定义一个枚举的集合 并进行循环来处理这个目录下的things
			Enumeration<URL> dirs;
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					findAndAddClassesInPackageByJar(url, packageDirName,
							packageName, recursive, classes);
				}
			}
		}
		return classes;
	}

	/**
	 * @函数名称:findAndAddClassesInPackageByFile
	 * @函数描述:文件夹中从包package中获取所有的Class
	 * @参数与返回说明:
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 * @算法描述:
	 */
	private static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive,
			Map<String, String> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				classes.put(className, packageName + "." + className);
			}
		}
	}

	/**
	 * @函数名称:findAndAddClassesInPackageByJar
	 * @函数描述:jar包中，从包package中获取所有的Class
	 * @参数与返回说明:
	 * @param url
	 * @param packageDirName
	 * @param packageName
	 * @param recursive
	 * @param classes
	 * @throws IOException
	 * @算法描述:
	 */
	private static void findAndAddClassesInPackageByJar(URL url,
			String packageDirName, String packageName, boolean recursive,
			Map<String, String> classes) throws IOException {
		// 如果是jar包文件
		// 定义一个JarFile
		JarFile jar;
		// 获取jar
		jar = ((JarURLConnection) url.openConnection()).getJarFile();
		// 从此jar包 得到一个枚举类
		Enumeration<JarEntry> entries = jar.entries();
		// 同样的进行循环迭代
		while (entries.hasMoreElements()) {
			// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			// 如果是以/开头的
			if (name.charAt(0) == '/') {
				// 获取后面的字符串
				name = name.substring(1);
			}
			// 如果前半部分和定义的包名相同
			if (name.startsWith(packageDirName)) {
				int idx = name.lastIndexOf('/');
				// 如果以"/"结尾 是一个包
				if (idx != -1) {
					// 获取包名 把"/"替换成"."
					packageName = name.substring(0, idx).replace('/', '.');
				}
				// 如果可以迭代下去 并且是一个包
				if ((idx != -1) || recursive) {
					// 如果是一个.class文件 而且不是目录
					if (name.endsWith(".class") && !entry.isDirectory()) {
						// 去掉后面的".class" 获取真正的类名
						String className = name.substring(
								packageName.length() + 1, name.length() - 6);
						// 添加到classes
						classes.put(className, packageName + "." + className);
					}
				}
			}
		}
	}
}
