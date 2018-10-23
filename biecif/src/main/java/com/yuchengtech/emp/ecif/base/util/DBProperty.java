package com.yuchengtech.emp.ecif.base.util;

import com.yuchengtech.emp.bione.util.PropertiesUtils;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;

public class DBProperty {
	
	public static String DB_TYPE = "";
	static{
		
		String dbtype ="";

		PropertiesUtils tool = new PropertiesUtils("database.properties");
		String driverClassName = tool.getProperty("jdbc.driverClassName");
		
		if(driverClassName!=null&&driverClassName.equals("oracle.jdbc.driver.OracleDriver")){
			dbtype = "oracle";
		}else{
			dbtype = "db2";			
		}
		
		DB_TYPE = dbtype;
	}
}
