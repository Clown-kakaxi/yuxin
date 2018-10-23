package com.ytec.mdm.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;


/**
 * 使用DBUnit初始化测试用H2嵌入式数据库数据的工具类.
 */
public class DbUnitUtils {

	private static Logger logger = LoggerFactory.getLogger(DbUnitUtils.class);
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	

	/**
	 * 清除并插入XML数据文件到H2数据库.
	 * 
	 * XML数据文件中涉及的表在插入数据前会先进行清除. 
	 * 
	 * @param xmlFilePaths 符合Spring Resource路径格式的文件列表.
	 */
	public static void loadData(IDatabaseConnection connection, String... xmlFilePaths) throws Exception {
		execute(DatabaseOperation.CLEAN_INSERT, connection, xmlFilePaths);
	}

	/**
	 * 插入XML数据文件到H2数据库. 
	 */
	public static void appendData(IDatabaseConnection connection, String... xmlFilePaths) throws Exception {
		execute((DatabaseOperation.INSERT), connection, xmlFilePaths);
	}

	/**
	 * 在H2数据库中删除XML数据文件中涉及的表的数据. 
	 */
	public static void removeData(IDatabaseConnection connection, String... xmlFilePaths) throws Exception {
		execute(DatabaseOperation.DELETE_ALL, connection, xmlFilePaths);
	}

	/**
	 * 按DBUnit Operation执行XML数据文件的数据.
	 * 
	 * @param xmlFilePaths 符合Spring Resource路径格式的文件列表.
	 */
	private static void execute(DatabaseOperation operation, IDatabaseConnection connection, String... xmlFilePaths)
			throws DatabaseUnitException, SQLException {
	
		//IDatabaseConnection connection =  new OracleConnection(h2DataSource.getConnection(), schema);
		for (String xmlPath : xmlFilePaths) {
			try {
				InputStream input = resourceLoader.getResource(xmlPath).getInputStream();
				IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(input);
				operation.execute(connection, dataSet);
			} catch (IOException e) {
				logger.warn(xmlPath + " file not found", e);
			}
		}
	}
}