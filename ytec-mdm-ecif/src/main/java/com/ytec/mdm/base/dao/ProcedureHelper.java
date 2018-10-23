/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.dao
 * @文件名：ProcedureHelper.java
 * @版本信息：1.0.0
 * @日期：2014-5-13-上午11:28:45
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Repository;

import com.ytec.mdm.base.util.SpringContextUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ProcedureHelper
 * @类描述：调用存储过程帮助类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-13 上午11:28:45   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-13 上午11:28:45
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Repository("procedureHelper")
public class ProcedureHelper {

	/**
	 *@构造函数 
	 */
	public ProcedureHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @函数名称:callProcedureNoReturn
	 * @函数描述:无返回值的存储过程
	 * @参数与返回说明:
	 * 		@param procedure 存储过程  xxx(?,?,?)
	 * 		@param values    参数
	 * 		@return
	 * @算法描述:
	 */
	public int callProcedureNoReturn(final String procedure,final Object... values){
		JPABaseDAO baseDAO=(JPABaseDAO)SpringContextUtils.getBean("baseDAO");
		String procedureSql="{call "+procedure+"}";
		return baseDAO.batchExecuteNativeWithIndexParam(procedureSql, values);
	}
	
	/**
	 * @函数名称:callProcedureUseJpa
	 * @函数描述:通过JPA方式调用存储过程，返回值为ResultSet（以select 形式返回的值）的存储过程(注意：EJB3不能调用以OUT参数返回值的存储过程。)
	 * @参数与返回说明:
	 * 		@param procedure 存储过程  xxx(?,?,?)
	 * 		@param values    参数
	 * 		@return
	 * @算法描述:
	 */
	public List<Object[]> callProcedureUseJpa(final String procedure,final Object... values){
		JPABaseDAO baseDAO=(JPABaseDAO)SpringContextUtils.getBean("baseDAO");
		String procedureSql="{call "+procedure+"}";
		return baseDAO.findByNativeSQLWithIndexParam(procedureSql, values);
	}
	

	/**
	 * @函数名称:callProcedureUseNative
	 * @函数描述:通过JDBC方式调用存储过程，OUT参数返回值的存储过程。
	 * @参数与返回说明:
	 * 		@param procedure   存储过程  xxx(?,?,?)
	 * 		@param parameterIndex   OUT返回位置
	 * 		@param values     参数
	 * 		@return
	 * @算法描述:
	 */
	@Deprecated
	public Object callProcedureUseNative(final String procedure,int parameterIndex,final Object... values){
		BasicDataSource dataSource=(BasicDataSource)SpringContextUtils.getBean("dataSource");
		Connection con=null;
		CallableStatement cs =null;
		try{
			con=dataSource.getConnection();
			String procedureSql="{call "+procedure+"}";
			cs = con.prepareCall(procedureSql);    
			if (values != null) {
				int j = 1;
				for (int i = 0; i < values.length; i++) {
					cs.setObject(j++, values[i]);
				}
			}  
			cs.execute();
			return cs.getObject(parameterIndex);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(cs!=null){
				try {
					cs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		
	}

}
