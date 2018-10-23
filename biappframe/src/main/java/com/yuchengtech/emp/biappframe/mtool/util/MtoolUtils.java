package com.yuchengtech.emp.biappframe.mtool.util;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * <pre>
 * Title:数据集功能相关工具方法
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class MtoolUtils {

	/**
	 * 通过传入连接基本参数来判断是否是正确有效连接
	 * 
	 * @param
	 * @return
	 */
	public static boolean testConnection(String driverName, String url,
			String usrId, String passwd) {
		boolean flag = false;
		Connection conn = null;
		if (driverName != null && !"".equals(driverName) && url != null
				&& !"".equals(url) && usrId != null && !"".equals(usrId)) {
			try {
//				// 注册驱动
//				Class.forName(driverName).newInstance();
//				// 获取连接
//				conn = DriverManager.getConnection(url, usrId, passwd);
//				if (conn != null) {
//					flag = true;
//				}
				// 注册驱动
				Driver driver = (Driver)Class.forName(driverName).newInstance();
				Properties p = new Properties();
				p.put("user", usrId);
				p.put("password",passwd);
				// 获取连接
				conn = driver.connect(url, p);
				if(conn != null){
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
			} finally {
				if (conn != null) {
					// 释放连接
					JdbcUtils.closeConnection(conn);
				}
			}
		}
		return flag;
	}
}
