/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.dao
 * @�ļ�����ProcedureHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-13-����11:28:45
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ProcedureHelper
 * @�����������ô洢���̰�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-13 ����11:28:45   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-13 ����11:28:45
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Repository("procedureHelper")
public class ProcedureHelper {

	/**
	 *@���캯�� 
	 */
	public ProcedureHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @��������:callProcedureNoReturn
	 * @��������:�޷���ֵ�Ĵ洢����
	 * @�����뷵��˵��:
	 * 		@param procedure �洢����  xxx(?,?,?)
	 * 		@param values    ����
	 * 		@return
	 * @�㷨����:
	 */
	public int callProcedureNoReturn(final String procedure,final Object... values){
		JPABaseDAO baseDAO=(JPABaseDAO)SpringContextUtils.getBean("baseDAO");
		String procedureSql="{call "+procedure+"}";
		return baseDAO.batchExecuteNativeWithIndexParam(procedureSql, values);
	}
	
	/**
	 * @��������:callProcedureUseJpa
	 * @��������:ͨ��JPA��ʽ���ô洢���̣�����ֵΪResultSet����select ��ʽ���ص�ֵ���Ĵ洢����(ע�⣺EJB3���ܵ�����OUT��������ֵ�Ĵ洢���̡�)
	 * @�����뷵��˵��:
	 * 		@param procedure �洢����  xxx(?,?,?)
	 * 		@param values    ����
	 * 		@return
	 * @�㷨����:
	 */
	public List<Object[]> callProcedureUseJpa(final String procedure,final Object... values){
		JPABaseDAO baseDAO=(JPABaseDAO)SpringContextUtils.getBean("baseDAO");
		String procedureSql="{call "+procedure+"}";
		return baseDAO.findByNativeSQLWithIndexParam(procedureSql, values);
	}
	

	/**
	 * @��������:callProcedureUseNative
	 * @��������:ͨ��JDBC��ʽ���ô洢���̣�OUT��������ֵ�Ĵ洢���̡�
	 * @�����뷵��˵��:
	 * 		@param procedure   �洢����  xxx(?,?,?)
	 * 		@param parameterIndex   OUT����λ��
	 * 		@param values     ����
	 * 		@return
	 * @�㷨����:
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
