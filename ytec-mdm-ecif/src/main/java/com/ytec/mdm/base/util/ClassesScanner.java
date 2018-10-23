/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����ClassesScanner.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:03:48
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�ClassesScanner
 * @���������Ӱ�package�л�ȡ���е�Class
 * @��������:�Ӱ�package�л�ȡ���е�Class
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:03:54
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:03:54
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ClassesScanner {

	/**
	 * @��������:scanEntityByPackages
	 * @��������:�Ӱ�package�л�ȡ���е�Class
	 * @�����뷵��˵��:
	 * @param packages
	 * @return
	 * @throws IOException
	 * @�㷨����:
	 */
	public static Map<String, String> scanEntityByPackages(String packages[])
			throws IOException {
		// ��һ��class��ļ���
		Map<String, String> classes = new HashMap<String, String>();
		// �Ƿ�ѭ������
		boolean recursive = true;
		for (String pack : packages) {
			// ��ȡ�������� �������滻
			String packageName = pack;
			String packageDirName = packageName.replace('.', '/');
			// ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things
			Enumeration<URL> dirs;
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// ѭ��������ȥ
			while (dirs.hasMoreElements()) {
				// ��ȡ��һ��Ԫ��
				URL url = dirs.nextElement();
				// �õ�Э�������
				String protocol = url.getProtocol();
				// ��������ļ�����ʽ�����ڷ�������
				if ("file".equals(protocol)) {
					// ��ȡ��������·��
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
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
	 * @��������:findAndAddClassesInPackageByFile
	 * @��������:�ļ����дӰ�package�л�ȡ���е�Class
	 * @�����뷵��˵��:
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 * @�㷨����:
	 */
	private static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive,
			Map<String, String> classes) {
		// ��ȡ�˰���Ŀ¼ ����һ��File
		File dir = new File(packagePath);
		// ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// ѭ�������ļ�
		for (File file : dirfiles) {
			// �����Ŀ¼ �����ɨ��
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// �����java���ļ� ȥ�������.class ֻ��������
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				classes.put(className, packageName + "." + className);
			}
		}
	}

	/**
	 * @��������:findAndAddClassesInPackageByJar
	 * @��������:jar���У��Ӱ�package�л�ȡ���е�Class
	 * @�����뷵��˵��:
	 * @param url
	 * @param packageDirName
	 * @param packageName
	 * @param recursive
	 * @param classes
	 * @throws IOException
	 * @�㷨����:
	 */
	private static void findAndAddClassesInPackageByJar(URL url,
			String packageDirName, String packageName, boolean recursive,
			Map<String, String> classes) throws IOException {
		// �����jar���ļ�
		// ����һ��JarFile
		JarFile jar;
		// ��ȡjar
		jar = ((JarURLConnection) url.openConnection()).getJarFile();
		// �Ӵ�jar�� �õ�һ��ö����
		Enumeration<JarEntry> entries = jar.entries();
		// ͬ���Ľ���ѭ������
		while (entries.hasMoreElements()) {
			// ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�
			JarEntry entry = entries.nextElement();
			String name = entry.getName();
			// �������/��ͷ��
			if (name.charAt(0) == '/') {
				// ��ȡ������ַ���
				name = name.substring(1);
			}
			// ���ǰ�벿�ֺͶ���İ�����ͬ
			if (name.startsWith(packageDirName)) {
				int idx = name.lastIndexOf('/');
				// �����"/"��β ��һ����
				if (idx != -1) {
					// ��ȡ���� ��"/"�滻��"."
					packageName = name.substring(0, idx).replace('/', '.');
				}
				// ������Ե�����ȥ ������һ����
				if ((idx != -1) || recursive) {
					// �����һ��.class�ļ� ���Ҳ���Ŀ¼
					if (name.endsWith(".class") && !entry.isDirectory()) {
						// ȥ�������".class" ��ȡ����������
						String className = name.substring(
								packageName.length() + 1, name.length() - 6);
						// ��ӵ�classes
						classes.put(className, packageName + "." + className);
					}
				}
			}
		}
	}
}
