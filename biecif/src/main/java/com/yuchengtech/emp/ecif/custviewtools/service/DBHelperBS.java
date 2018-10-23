package com.yuchengtech.emp.ecif.custviewtools.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.util.PropertiesUtils;
import com.yuchengtech.emp.ecif.core.entity.ColDefVO;


@Service
@Transactional(readOnly = true)
public class DBHelperBS extends BaseBS<Object>{

	public List<ColDefVO> getColList(String schema,String tabName) {

		String sql = " select COL_NAME,COL_CH_NAME,DATA_TYPE,DATA_LEN,DATA_PREC from TX_TAB_DEF tab,TX_COL_DEF col where tab.TAB_ID=col.TAB_ID and tab.TAB_NAME='"+tabName.toUpperCase() +"'" ;
		sql +=" order by COL_SEQ";

		List<ColDefVO> list = Lists.newArrayList();
		List<Object[]> objList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
		for(Object[] objArr:objList){
			String colName = (String)objArr[0]; 
			String colChName = (String)objArr[1]; 		
			String dataType = (String)objArr[2]; 			
			BigDecimal dataLen = new BigDecimal(objArr[3].toString()) ; 			
			BigDecimal dataPrec = new BigDecimal(objArr[4].toString()) ;  			
			
			ColDefVO vo = new ColDefVO();
			vo.setColName(colName);
			vo.setColChName(colChName);
			vo.setDataType(dataType);
			vo.setDataLen(new Long(dataLen.longValue()));
			if(dataPrec!=null){
				vo.setDataPrec(new Long(dataPrec.longValue()));
			}
			list.add(vo);
		}

		List keyList = getKeyList(schema, tabName);
		//生成列
		for(ColDefVO vo :list){
			String colName = vo.getColName().toUpperCase();
			if(keyList.contains(colName)){
				vo.setKeyType("Primary");
			}
			
		}
		
		return list;
	}
	
	
	public List<String> getKeyList(String schema,String tabName) {
		
		String dbtype = getDbtype();
		
		String sql ="";
		
		if(dbtype.toLowerCase().equals("oracle")){
			sql = " select B.column_name from user_constraints A,user_cons_columns B " +
				  " where A.table_name =B.table_name  " +
				  " and A.table_name = '"+ tabName.toUpperCase()+"'" +
				  " and A.constraint_name = B.constraint_name " + 
				  " and A.constraint_type ='P'  " ;
			
		}else if(dbtype.toLowerCase().equals("db2")){
			
			sql = " select  B.COLNAME  from syscat.tabconst A ,SYSCAT.KEYCOLUSE B    " +
				  "	WHERE A.CONSTNAME = B.CONSTNAME AND   A.TYPE='P'   and A.tabname= '"+tabName.toUpperCase() +"' " ;		
			
			if(schema!=null&&schema.equals("")){
				sql +=" AND A.TABSCHEMA='"+ schema  +"'" ;
			}
		}

		List<String> objList = this.baseDAO.createNativeQueryWithIndexParam(sql).getResultList();
		
		return objList;
	}
	
	/**
	 * 获取当前的数据库类型
	 * @return
	 */
	public String getDbtype(){
		
		String dbtype ="";

		PropertiesUtils tool = new PropertiesUtils("database.properties");
		String driverClassName = tool.getProperty("jdbc.driverClassName");
		
		if(driverClassName!=null&&driverClassName.equals("oracle.jdbc.driver.OracleDriver")){
			dbtype = "oracle";
		}else{
			dbtype = "db2";			
		}
		
		return dbtype;
		
	}	
}
