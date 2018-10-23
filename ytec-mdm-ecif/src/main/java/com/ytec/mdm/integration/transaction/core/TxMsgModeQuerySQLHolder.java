/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：TxMsgModeQuerySQLHolder.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:16:08
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeFilter;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.domain.txp.TxMsgNodeTabsRel;
import com.ytec.mdm.integration.transaction.model.TxModel;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxMsgModeQuerySQLHolder
 * @类描述：存放交易查询SQL语句的容器
 * @功能描述:存放交易查询SQL语句的容器
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:16:09   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:16:09
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxMsgModeQuerySQLHolder {

	private static Logger log = LoggerFactory
			.getLogger(TxMsgModeQuerySQLHolder.class);

	/**
	 * @属性名称:txQuerySQLMap
	 * @属性描述:存放的内容为 节点ID --》SQL
	 * @since 1.0.0
	 */
	private static ConcurrentHashMap<Long, SqlVersion> txQuerySQLMap = new ConcurrentHashMap<Long, SqlVersion>();

	/**
	 *@构造函数 
	 */
	private TxMsgModeQuerySQLHolder() {

	}

	
	/**
	 * @函数名称:getQuerySQL
	 * @函数描述:根据节点ID获取交易查询的SQL语句
	 * @参数与返回说明:
	 * 		@param txCode
	 * 		@param nodeId
	 * 		@return
	 * @算法描述:
	 */
	public static String getQuerySQL(String txCode, Long nodeId) {

		TxModel txModel = TxModelHolder.getTxModel(txCode);
		SqlVersion nodeIdQuerySql = txQuerySQLMap.get(nodeId);
		String sql = null;

		if (nodeIdQuerySql == null) {

			sql = buildQuerySQL(txModel, nodeId);
			SqlVersion nodeIdQuerySqlTemp = null;
			if ((nodeIdQuerySqlTemp = txQuerySQLMap.putIfAbsent(nodeId,
					new SqlVersion(sql, txModel.getTxDef().getUpdateTm()))) != null) {
				sql = nodeIdQuerySqlTemp.getNodeIdSql();
			}
		} else {
			Timestamp newTimestamp = txModel.getTxDef().getUpdateTm();
			Timestamp oldTimestamp = nodeIdQuerySql.getNodeIdTimestamp();
			if (newTimestamp != null && oldTimestamp != null
					&& newTimestamp.after(oldTimestamp)) {
				sql = buildQuerySQL(txModel, nodeId);
				txQuerySQLMap.replace(nodeId, new SqlVersion(sql, txModel
						.getTxDef().getUpdateTm()));
			} else {
				return nodeIdQuerySql.getNodeIdSql();
			}

		}
		return sql;

	}

	/**
	 * @函数名称:buildQuerySQL
	 * @函数描述:根据报文节点ID读取配置信息，构造查询SQL
	 * @参数与返回说明:
	 * 		@param txModel
	 * 		@param nodeId
	 * 		@return
	 * @算法描述:
	 */
	public static String buildQuerySQL(TxModel txModel, Long nodeId) {

		StringBuffer select = new StringBuffer("");
		StringBuffer from = new StringBuffer(" from ");
		StringBuffer on = new StringBuffer("");
		StringBuffer where = new StringBuffer("");
		boolean needDistinct=false;

		// 给表起别名
		Map<Long, String> tabAliasMap = new HashMap<Long, String>();

		// 表连接类型
		Map<Long, String> tabJionMap = new HashMap<Long, String>();

		// 找出查询的表
		List<TxMsgNodeTabMap> txMsgNodeTabMapList = txModel
				.getTxMsgNodeTabMapMap().get(nodeId);

		int count = 1;
		TxMsgNodeTabMap txMsgNodeTabMap = null;
		String tabAlias = null;// 表的别名
		String tabJion = null; // 表的链接类型
		Long tableId=null;

		for (int i = 0; i < txMsgNodeTabMapList.size(); i++) {

			txMsgNodeTabMap = txMsgNodeTabMapList.get(i);
			tableId = txMsgNodeTabMap.getTabId();

			tabAlias = tabAliasMap.get(tableId);
			tabJion = tabJionMap.get(tableId);

			if (tabAlias == null) {
				tabAlias = "t" + (count++);
				tabAliasMap.put(tableId, tabAlias);

				tabJion = getTabRoleTp(txMsgNodeTabMap.getTabRoleTp());
				tabJionMap.put(tableId, tabJion);
			}

			if ("null".equals(tabJion)) {
				if (i != 0){
					from.append(" , ");
				}
				from.append(txMsgNodeTabMap.getTabName());
				from.append(" " + tabAlias);
			}
			if ("".equals(tabJion)) {
				from.append(txMsgNodeTabMap.getTabName());
				from.append(" " + tabAlias);
			}

		}

		// 外键关联字段
		List<TxMsgNodeAttr> fkAttrList = new ArrayList<TxMsgNodeAttr>();

		// 查询的字段
		List<TxMsgNodeAttr> txMsgNodeAttrList = txModel.getTxMsgNodeAttrMap()
				.get(nodeId);

		TxMsgNodeAttr txMsgNodeAttr = null;
		for (int i = 0; i < txMsgNodeAttrList.size(); i++) {

			txMsgNodeAttr = txMsgNodeAttrList.get(i);

			if (txMsgNodeAttr.getFkAttrId() != null
					&& txMsgNodeAttr.getFkAttrId() != 0) {
				fkAttrList.add(txMsgNodeAttr);
			}
			if("P".equals(txMsgNodeAttr.getNulls())){
				/***
				 * 业务主键，需要查询去重
				 */
				needDistinct=true;
				
			}

			if (i != 0){
				select.append(",");
			}

			select.append(tabAliasMap.get(txMsgNodeAttr.getTabId()) + "."
					+ txMsgNodeAttr.getColName() + "  "
					+ txMsgNodeAttr.getAttrCode());

		}
		if(needDistinct){
			select.insert(0, "select distinct ");
		}else{
			select.insert(0, "select ");
		}

		// 表间关联关系
		List<TxMsgNodeTabsRel> txMsgNodeTabsRelList = txModel
				.getTxMsgNodeTabsRelMap().get(nodeId);

		if (txMsgNodeTabsRelList != null) {

			for (TxMsgNodeTabsRel txMsgNodeTabsRel : txMsgNodeTabsRelList) {
				String jionStr = tabJionMap.get(txMsgNodeTabsRel.getTabId1());

				if ("".equals(jionStr) || "null".equals(jionStr)) {
					jionStr = tabJionMap.get(txMsgNodeTabsRel.getTabId2());
				}

				if (!"null".equals(jionStr) && !"".equals(jionStr)) {
					from.append(jionStr);
					from.append(txMsgNodeTabsRel.getTabName2());
					from.append(" ");
					from.append(tabAliasMap.get(txMsgNodeTabsRel.getTabId1()));

					if (on.indexOf(" on ") > -1) {
						on = new StringBuffer(" on ");
					} else {
						on.append(" on ");
					}
					on.append(tabAliasMap.get(txMsgNodeTabsRel.getTabId1())
							+ "." + txMsgNodeTabsRel.getColName1());
					on.append(txMsgNodeTabsRel.getRel());
					on.append(tabAliasMap.get(txMsgNodeTabsRel.getTabId2())
							+ "." + txMsgNodeTabsRel.getColName2());
				} else {
					if(where.length()==0){
						where.append(" where ");
					}else{
						where.append(" and ");
					}
					where.append(tabAliasMap.get(txMsgNodeTabsRel.getTabId1())
							+ "." + txMsgNodeTabsRel.getColName1());
					where.append(txMsgNodeTabsRel.getRel());
					where.append(tabAliasMap.get(txMsgNodeTabsRel.getTabId2())
							+ "." + txMsgNodeTabsRel.getColName2());
				}

			}
		}

		// 过滤条件
		List<TxMsgNodeFilter> txMsgNodeFilterList = txModel
				.getTxMsgNodeFilterMap().get(nodeId);

		if (txMsgNodeFilterList != null) {//------------------------------
			for (TxMsgNodeFilter txMsgNodeFilter : txMsgNodeFilterList) {
				if (!StringUtil.isEmpty(txMsgNodeFilter.getRel())) {
					if(where.length()==0){
						where.append(" where ");
					}else{
						where.append(" and ");
					}
					where.append(tabAliasMap.get(txMsgNodeFilter.getTabId())
							+ "." + txMsgNodeFilter.getColName());
					if ("like".equals(txMsgNodeFilter.getRel())) {
//						select.append(" , "
//								+ tabAliasMap.get(txMsgNodeFilter.getTabId())
//								+ "." + txMsgNodeFilter.getColName());
						where.append(" like :"+ txMsgNodeFilter.getReqAttrCode() + "_1");//
					} else {// <>,>,<,>=,<=
						where.append(txMsgNodeFilter.getRel());//
						where.append(":" + txMsgNodeFilter.getReqAttrCode()
								+ "_1");//
					}
				}
			}
		}

		// 外键过滤条件
		for (TxMsgNodeAttr fkTxMsgNodeAttr : fkAttrList) {
			if(where.length()==0){
				where.append(" where ");
			}else{
				where.append(" and ");
			}
			where.append(tabAliasMap.get(fkTxMsgNodeAttr.getTabId()) + "."
					+ fkTxMsgNodeAttr.getColName());
			where.append("=:" + MdmConstants.QUERY_SQL_FILTER_FK_FLAG
					+ fkTxMsgNodeAttr.getAttrCode());//
		}

		String sql = select.toString() + from.toString() + on.toString()
				+ where.toString();

		// System.out.println(sql);
		log.debug("报文节点:{}对应的SQL语句为:{}", nodeId, sql);

		return sql;

	}

	private static String getTabRoleTp(String tabRoleTp) {
		if(tabRoleTp==null){
			return "null";
		}else if(tabRoleTp.equals("01")){
			return "";
		}else if(tabRoleTp.equals("02")){
			return " inner join ";
		}else if(tabRoleTp.equals("03")){
			return " left join ";
		}else if(tabRoleTp.equals("04")){
			return " right join ";
		}else{
			return null;
		}
	}

}

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SqlVersion
 * @类描述：SQL语句的更新信息
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:17:10   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:17:10
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
class SqlVersion {
	/**
	 * @属性名称:nodeIdSql
	 * @属性描述:TODO
	 * @since 1.0.0
	 */
	String nodeIdSql;
	/**
	 * @属性名称:nodeIdTimestamp
	 * @属性描述:最后更新时间
	 * @since 1.0.0
	 */
	Timestamp nodeIdTimestamp;

	/**
	 *@构造函数 
	 */
	public SqlVersion() {
	}

	public SqlVersion(String nodeIdSql, Timestamp nodeIdTimestamp) {
		super();
		this.nodeIdSql = nodeIdSql;
		this.nodeIdTimestamp = nodeIdTimestamp;
	}

	public String getNodeIdSql() {
		return nodeIdSql;
	}

	public void setNodeIdSql(String nodeIdSql) {
		this.nodeIdSql = nodeIdSql;
	}

	public Timestamp getNodeIdTimestamp() {
		return nodeIdTimestamp;
	}

	public void setNodeIdTimestamp(Timestamp nodeIdTimestamp) {
		this.nodeIdTimestamp = nodeIdTimestamp;
	}

}
