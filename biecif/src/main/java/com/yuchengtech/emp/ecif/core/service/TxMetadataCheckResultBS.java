package com.yuchengtech.emp.ecif.core.service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.util.DBProperty;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.core.entity.ColDef;
import com.yuchengtech.emp.ecif.core.entity.TabDef;
import com.yuchengtech.emp.ecif.core.entity.TxMetaDataVO;
import com.yuchengtech.emp.ecif.core.entity.TxMetadataCheckResult;


@Service
@Transactional(readOnly = true)
public class TxMetadataCheckResultBS extends BaseBS<TxMetadataCheckResult> {
	
	protected static Logger log = LoggerFactory.getLogger(TxMetadataCheckResultBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxMetadataCheckResult> getResultList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMetadataCheckResult from TxMetadataCheckResult TxMetadataCheckResult where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxMetadataCheckResult." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMetadataCheckResult> resultList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return resultList;

	}
	
	@Transactional(readOnly = true)	
	public List<TxMetaDataVO> queryMetaDataChangeList(){
			
		//找出元数据与物理数据库不同的记录
		StringBuffer sql = new StringBuffer("");
		if(DBProperty.DB_TYPE.equals("oracle")){
			//sql优化前
			//sql.append(" select WMSYS.WM_CONCAT(td.tx_code) as txCodes,WMSYS.WM_CONCAT(td.tx_id) as txIds,d.tab_id,c.col_id,d.tab_schema,d.tab_name,c.col_name,c.data_type,c.data_len,c.data_prec,c.nulls  from tx_msg_node_tab_map t  left join tx_tab_def d on t.tab_id = d.tab_id inner join tx_col_def c on d.tab_id = c.tab_id inner join tx_msg_node n on t.node_id = n.node_id inner join tx_msg m on m.msg_id = n.msg_id inner join tx_def td on td.tx_id = m.tx_id where d.tab_name || c.col_name || c.data_type || c.data_len || c.data_prec||c.nulls  not in( select uc.TABLE_NAME || uc.column_name || uc.data_type || uc.data_length || uc.data_scale || uc.nullable   from user_tab_columns uc )  group by   d.tab_id,c.col_id, d.tab_schema,   d.tab_name,   c.col_name,   c.data_type,   c.data_len,   c.data_prec,   c.nulls order by   d.tab_schema, d.tab_name, c.col_name");
			
			//sql优化后
			//sql.append(" select WMSYS.WM_CONCAT(tx_code) as txCodes,WMSYS.WM_CONCAT(tx_id) as txIds,tab_id,col_id,tab_schema,tab_name,col_name,data_type,data_len,data_prec,nulls  from (select td.tx_code, td.tx_id,d.tab_id,c.col_id,d.tab_schema,d.tab_name,c.col_name,c.data_type, c.data_len,c.data_prec,c.nulls,d.tab_name || c.col_name || c.data_type || c.data_len ||c.data_prec || c.nulls as condition from tx_msg_node_tab_map t left join tx_tab_def d on t.tab_id = d.tab_id inner join tx_col_def c on d.tab_id = c.tab_id inner join tx_msg_node n on t.node_id = n.node_id inner join tx_msg m on m.msg_id = n.msg_id inner join tx_def td on td.tx_id = m.tx_id) where not EXISTS (select joincondition from (select uc.TABLE_NAME || uc.column_name || uc.data_type ||uc.data_length || uc.data_scale || uc.nullable as joincondition from user_tab_columns uc) where condition = joincondition) group by tab_id,col_id,tab_schema,tab_name,col_name,data_type,data_len,data_prec,nulls order by tab_schema, tab_name, col_name");
			//oracle高版本支持listagg
			sql.append(" select listagg(tx_code, ',') within GROUP( order by tx_code)  as txCodes,listagg(tx_id, ',') within GROUP( order by tx_id) as txIds,tab_id,col_id,tab_schema,tab_name,col_name,data_type,data_len,data_prec,nulls  from (select td.tx_code, td.tx_id,d.tab_id,c.col_id,d.tab_schema,d.tab_name,c.col_name,c.data_type, c.data_len,c.data_prec,c.nulls,d.tab_name || c.col_name || c.data_type || c.data_len ||c.data_prec || c.nulls as condition from tx_msg_node_tab_map t left join tx_tab_def d on t.tab_id = d.tab_id inner join tx_col_def c on d.tab_id = c.tab_id inner join tx_msg_node n on t.node_id = n.node_id inner join tx_msg m on m.msg_id = n.msg_id inner join tx_def td on td.tx_id = m.tx_id) where not EXISTS (select joincondition from (select uc.TABLE_NAME || uc.column_name || uc.data_type ||uc.data_length || uc.data_scale || uc.nullable as joincondition from user_tab_columns uc) where condition = joincondition) group by tab_id,col_id,tab_schema,tab_name,col_name,data_type,data_len,data_prec,nulls order by tab_schema, tab_name, col_name");		
		}else if(DBProperty.DB_TYPE.equals("db2")){
			sql.append("select VARCHAR(REPLACE(REPLACE(XML2CLOB(XMLAGG(XMLELEMENT(NAME A,td.tx_code||','))),'<A>',''),'</A>',' ')) AS txCodes,VARCHAR(REPLACE(REPLACE(XML2CLOB(XMLAGG(XMLELEMENT(NAME A,td.tx_id||','))),'<A>',''),'</A>',' ')) AS txIds,d.tab_id,c.col_id,d.tab_schema,d.tab_name,c.col_name,c.data_type,c.data_len,c.data_prec,c.nulls  from tx_msg_node_tab_map t  left join tx_tab_def d on t.tab_id = d.tab_id inner join tx_col_def c on d.tab_id = c.tab_id inner join tx_msg_node n on t.node_id = n.node_id inner join tx_msg m on m.msg_id = n.msg_id inner join tx_def td on td.tx_id = m.tx_id where d.tab_name || c.col_name || c.data_type || c.data_len || c.data_prec||c.nulls  not in( select uc.TABLE_NAME || uc.column_name || uc.data_type || uc.data_length || uc.data_scale || uc.nullable   from user_tab_columns uc )  group by   d.tab_id,c.col_id, d.tab_schema,   d.tab_name,   c.col_name,   c.data_type,   c.data_len,   c.data_prec,   c.nulls order by   d.tab_schema, d.tab_name, c.col_name");
		}
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		
		TxMetaDataVO[] txMetaDataVOs = null;
		try {
			txMetaDataVOs = this.resultUtil.listObjectsToEntityBeans(list, TxMetaDataVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		if(txMetaDataVOs==null)
			return null;
		
		//对不同的元数据设值
		for(int i=0;i<txMetaDataVOs.length;i++) {
	
			//页面换行
			//txMetaDataVOs[i].setTxCodes(txMetaDataVOs[i].getTxCodes().replaceAll(",", "<BR>"));
			
			String txIds = txMetaDataVOs[i].getTxIds();
			String txCodes = txMetaDataVOs[i].getTxCodes();
			String tx = "";
			if(txIds!=null&&!txIds.equals("")){
				String[] txIdArray = txIds.split(",");
				String[] txCodeArray =  txCodes.split(",");
				for(int j=0;j<txIdArray.length;j++){
					
					String href = txCodeArray[j] + "&nbsp;&nbsp;<A href='javascript:tx_modify(" + txIdArray[j] +")'>查看</A><BR>";
					
					tx = tx + href;
				}
			}
			
			//设置交易显示信息
			//txMetaDataVOs[i].setTxCodes(txMetaDataVOs[i].getTxCodes().replaceAll(",", "<BR>"));
			txMetaDataVOs[i].setTxCodes(txCodes);
			txMetaDataVOs[i].setTxCodesDesc(tx);
	
			StringBuffer sql2 = new StringBuffer("");		
			TxMetaDataVO[] txDBMetaDataVOs = null;
			try {
				if(DBProperty.DB_TYPE.equals("oracle")){
					sql2.append("  select TABLE_NAME as tab_name,column_name as col_name,data_type,data_length as data_len,data_scale as data_prec,nullable as nulls from user_tab_columns WHERE TABLE_NAME='"+txMetaDataVOs[i].getTabName() +"' and COLUMN_NAME='"+txMetaDataVOs[i].getColName()+"' order by TABLE_NAME,COLUMN_NAME");
				}else if(DBProperty.DB_TYPE.equals("db2")){
					sql2.append("select tabname as tab_name, colname as col_name , typename as data_type, length  as data_len, scale as data_prec, nulls from syscat.columns WHERE tabname='"+txMetaDataVOs[i].getTabName() +"' and colname='"+txMetaDataVOs[i].getColName()+"' order by TABLE_NAME,COLUMN_NAME" );						
				}
				List<Object[]> list2 = this.baseDAO.findByNativeSQLWithIndexParam(sql2.toString());
				//没有这个列
				if(list2==null||list2.size()==0){
					txMetaDataVOs[i].setChangeDesc("数据库没有此列");
					
				}else{
					StringBuffer value = new StringBuffer("");
					StringBuffer desc = new StringBuffer("");
					txDBMetaDataVOs = this.resultUtil.listObjectsToEntityBeans(list2, TxMetaDataVO.class, sql2.toString());
					
					//列名不同
					if(!txMetaDataVOs[i].getColName().equals(txDBMetaDataVOs[0].getColName())){
						value.append("COL_NAME="+txDBMetaDataVOs[0].getColName());
						desc.append("列名不同,数据库值：").append(txDBMetaDataVOs[0].getColName()).append("<BR>");
					}
					//字段类型不同
					if(!txMetaDataVOs[i].getDataType().equals(txDBMetaDataVOs[0].getDataType())){
						value.append("DATA_TYPE="+txDBMetaDataVOs[0].getDataType());
						desc.append("字段类型不同,数据库值：").append(txDBMetaDataVOs[0].getDataType()).append("<BR>");
					}
					//长度不同
					if(!txMetaDataVOs[i].getDataLen().equals(txDBMetaDataVOs[0].getDataLen())){
						value.append("DATA_LEN="+txDBMetaDataVOs[0].getDataLen());
						desc.append("长度不同,数据库值：").append(txDBMetaDataVOs[0].getDataLen()).append("<BR>");
					}
					//精度不同
					if(txDBMetaDataVOs[0].getDataPrec()!=null&& !txMetaDataVOs[i].getDataPrec().equals(txDBMetaDataVOs[0].getDataPrec())){
						value.append("DATA_PREC="+txDBMetaDataVOs[0].getDataPrec());
						desc.append("精度不同,数据库值：").append(txDBMetaDataVOs[0].getDataPrec()).append("<BR>");
					}
					//是否为空不同
					if(!txMetaDataVOs[i].getNulls().equals(txDBMetaDataVOs[0].getNulls())){
						value.append("NULLS="+txDBMetaDataVOs[0].getNulls());
						desc.append("可否为空不同,数据库值：").append(txDBMetaDataVOs[0].getNulls()).append("<BR>");
					}
					
					//设置元数据变化值，格式为key=value
					txMetaDataVOs[i].setChangeValue(value.toString());
					
					//设置元数据页面变化描述
					txMetaDataVOs[i].setChangeDesc(desc.toString());
				}
				
				
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		
		
		for(int i=0;i<txMetaDataVOs.length;i++) {
			
			TxMetaDataVO vo = txMetaDataVOs[i];
			//取出未处理的元数据变化记录
			StringBuffer sqlSelect = new StringBuffer("");
//			sqlSelect.append(" select TAB_NAME from TX_METADATA_CHECK_RESULT where TAB_NAME=?0 and COL_NAME=?1 and DATA_TYPE=?2 and DATA_LEN=?3 and DATA_PREC=?4 and NULLS=?5 and CHANGE_DESC=?6 and STATE='0'");
//			Object[] objs = new Object[]{vo.getTabName(),vo.getColName(),vo.getDataType(),vo.getDataLen(),vo.getDataPrec(),vo.getNulls(),vo.getChangeDesc()};
//			List<Object[]> metalist = this.baseDAO.findByNativeSQLWithIndexParam(sqlSelect.toString(),objs);
			sqlSelect.append(" select TAB_NAME from TX_METADATA_CHECK_RESULT where TAB_NAME='"+ vo.getTabName()+"' and COL_NAME='"+ vo.getColName()+"' and DATA_TYPE='"+ vo.getDataType()+"' and DATA_LEN="+ vo.getDataLen() );
			if(vo.getDataPrec()!=null){
				sqlSelect.append(" 		and DATA_PREC=" + vo.getDataPrec()  );
			}
			sqlSelect.append("	and NULLS='"+ vo.getNulls()+"' and CHANGE_DESC='"+ vo.getChangeDesc() +"' and STATE='0' ");
			sqlSelect.append("	and TX_CODES='" + vo.getTxCodes() +"'");
			
			List<Object[]> metalist = this.baseDAO.findByNativeSQLWithIndexParam(sqlSelect.toString());
			
			if(metalist!=null&&metalist.size()>0){
				//已经存在此记录
				continue;
			}else{
				//没插入过或已处理过
				StringBuffer sqlInsert = new StringBuffer("");
				if(vo.getDataPrec()!=null){
					sqlInsert.append(" insert into TX_METADATA_CHECK_RESULT(RESULT_ID,TAB_ID,COL_ID,TAB_NAME,COL_NAME,DATA_TYPE,DATA_LEN,DATA_PREC,NULLS,CHANGE_VALUE,CHANGE_DESC,TX_CODES,TX_CODES_DESC,STATE,CREATE_TM) ");
					if(DBProperty.DB_TYPE.equals("oracle")){
						sqlInsert.append(" values (?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,'0',sysdate)");
					}else if(DBProperty.DB_TYPE.equals("db2")){
						sqlInsert.append(" values (?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,'0',current timestamp)");
					}

					Object[] objs2 = new Object[]{getTimeRandName(),vo.getTabId(),vo.getColId(), vo.getTabName(),vo.getColName(),vo.getDataType(),vo.getDataLen(),vo.getDataPrec(),vo.getNulls(),vo.getChangeValue(), vo.getChangeDesc(),vo.getTxCodes(),vo.getTxCodesDesc()};
					this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), objs2).executeUpdate();
				}else{
					sqlInsert.append(" insert into TX_METADATA_CHECK_RESULT(RESULT_ID,TAB_ID,COL_ID,TAB_NAME,COL_NAME,DATA_TYPE,DATA_LEN,NULLS,CHANGE_VALUE,CHANGE_DESC,TX_CODES,TX_CODES_DESC,STATE,CREATE_TM) ");
					if(DBProperty.DB_TYPE.equals("oracle")){
						sqlInsert.append(" values (?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,'0',sysdate)");
					}else if(DBProperty.DB_TYPE.equals("db2")){
						sqlInsert.append(" values (?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,'0',current timestamp)");
					}
					Object[] objs2 = new Object[]{getTimeRandName(),vo.getTabId(),vo.getColId(),vo.getTabName(),vo.getColName(),vo.getDataType(),vo.getDataLen(),vo.getNulls(),vo.getChangeValue(), vo.getChangeDesc(),vo.getTxCodes(),vo.getTxCodesDesc()};
					this.baseDAO.createNativeQueryWithIndexParam(sqlInsert.toString(), objs2).executeUpdate();
					
				}
	
			}
		}
		
		return  Arrays.asList(txMetaDataVOs);
	}

	/**
	 * java取时间戳加随机数
	 * @return
	 */
	public static String getTimeRandName(){
		
		StringBuffer buf = new StringBuffer();

		buf.append(System.currentTimeMillis());//加上日期
		Random random = new Random();
		buf.append(random.nextInt(10000));
		
		return buf.toString();
		
	}	
	
	@Transactional(readOnly = false)
	public void changeMetadata(TxMetadataCheckResult tmcr) {
		
//		//修改元数据状态
//		tmcr.setState("1");
//		this.updateEntity(tmcr);
		
		String sqlmeata = "select * from tx_metadata_check_result where RESULT_ID = "+tmcr.getResultId() + " for update";
		this.baseDAO.createNativeQueryWithIndexParam(sqlmeata).executeUpdate();		

		String sqlmeata2 = " update tx_metadata_check_result set STATE='1' where RESULT_ID = "+tmcr.getResultId() ; 
		this.baseDAO.createNativeQueryWithIndexParam(sqlmeata2).executeUpdate();		
		
		//同步元数据
		String changevalue = tmcr.getChangeValue();
		if(changevalue!=null&&!changevalue.equals("")){

			String[] values =  changevalue.split("=");
			
			String sql = " update TX_COL_DEF set "+values[0]+"=?0 where TAB_ID=?1  and COL_ID=?2";
			Object[] objs = new Object[]{values[1],tmcr.getTabId(),tmcr.getColId()};
			
			this.baseDAO.createNativeQueryWithIndexParam(sql.toString(), objs).executeUpdate();
		}
	}
		
}
