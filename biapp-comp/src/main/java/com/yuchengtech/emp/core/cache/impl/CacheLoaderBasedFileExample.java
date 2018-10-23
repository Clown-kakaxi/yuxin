package com.yuchengtech.emp.core.cache.impl;

import com.yuchengtech.emp.core.cache.ICacheLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLoaderBasedFileExample implements
		ICacheLoader<String, String> {

	private static final Logger log = LoggerFactory.getLogger(CacheLoaderBasedFileExample.class);
	public static final String log001 = "必须配置有效的数据文件，目前的文件路径全称为{0}";
	public static final String log002 = "文件不存在或者不是一个文件，目前的文件路径全称为{0}";
	public static final String log003 = "必须配置有效的数据文件";
	public static final String log004 = "打开文件{0}失败";
	public static final String log005 = "加载文件{0}失败";
	public static final String log006 = "关闭文件流失败，文件名为{0}";
	private String filePath;
	private File file;

	public CacheLoaderBasedFileExample() {
		this.filePath = null;
		this.file = null;
	}

	public Map load() {
		return loadProp();
	}

	public long lastModify() {
		if (null == this.file) {
			RuntimeException rte = new RuntimeException(
					"Must config the available data file");

			log.error("log001", rte, new Object[] { this.filePath });
			throw rte;
		}
		return this.file.lastModified();
	}

	public void setFilePath(String path) {
		this.filePath = path;

		this.file = new File(this.filePath);
		if ((!(this.file.exists())) || (!(this.file.isFile()))) {
			RuntimeException rte = new RuntimeException(
					"File does not exist,or not a file.[" + path + ']');

			log.error("log002", rte, new Object[] { this.filePath });
			throw rte;
		}
	}

	public String getFilePath() {
		return this.filePath;
	}

	private Properties loadProp() {
		InputStream is = null;
		if (null == this.file) {
			RuntimeException rte = new RuntimeException(
					"Must config the available data file");

			log.error("log003", rte, new Object[0]);
			throw rte;
		}
		try {
			is = new FileInputStream(this.file);
		} catch (FileNotFoundException e) {
			log.error("log004", e, new Object[] { this.filePath });
		}

		Properties prop = new Properties();
		try {
			if (is != null) {
				prop.load(is);
				Properties localProperties1 = prop;
				return localProperties1;
			}
		} catch (IOException e) {
			log.error("log005", e, new Object[] { this.filePath });
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("log006", e, new Object[] { this.filePath });
			}
		}
		return null;
	}
}
