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
 * ʹ��DBUnit��ʼ��������H2Ƕ��ʽ���ݿ����ݵĹ�����.
 */
public class DbUnitUtils {

	private static Logger logger = LoggerFactory.getLogger(DbUnitUtils.class);
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	

	/**
	 * ���������XML�����ļ���H2���ݿ�.
	 * 
	 * XML�����ļ����漰�ı��ڲ�������ǰ���Ƚ������. 
	 * 
	 * @param xmlFilePaths ����Spring Resource·����ʽ���ļ��б�.
	 */
	public static void loadData(IDatabaseConnection connection, String... xmlFilePaths) throws Exception {
		execute(DatabaseOperation.CLEAN_INSERT, connection, xmlFilePaths);
	}

	/**
	 * ����XML�����ļ���H2���ݿ�. 
	 */
	public static void appendData(IDatabaseConnection connection, String... xmlFilePaths) throws Exception {
		execute((DatabaseOperation.INSERT), connection, xmlFilePaths);
	}

	/**
	 * ��H2���ݿ���ɾ��XML�����ļ����漰�ı������. 
	 */
	public static void removeData(IDatabaseConnection connection, String... xmlFilePaths) throws Exception {
		execute(DatabaseOperation.DELETE_ALL, connection, xmlFilePaths);
	}

	/**
	 * ��DBUnit Operationִ��XML�����ļ�������.
	 * 
	 * @param xmlFilePaths ����Spring Resource·����ʽ���ļ��б�.
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