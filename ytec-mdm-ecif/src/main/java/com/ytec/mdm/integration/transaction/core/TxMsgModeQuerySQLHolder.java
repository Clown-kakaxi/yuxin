/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����TxMsgModeQuerySQLHolder.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:16:08
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxMsgModeQuerySQLHolder
 * @����������Ž��ײ�ѯSQL��������
 * @��������:��Ž��ײ�ѯSQL��������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:16:09   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:16:09
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxMsgModeQuerySQLHolder {

	private static Logger log = LoggerFactory
			.getLogger(TxMsgModeQuerySQLHolder.class);

	/**
	 * @��������:txQuerySQLMap
	 * @��������:��ŵ�����Ϊ �ڵ�ID --��SQL
	 * @since 1.0.0
	 */
	private static ConcurrentHashMap<Long, SqlVersion> txQuerySQLMap = new ConcurrentHashMap<Long, SqlVersion>();

	/**
	 *@���캯�� 
	 */
	private TxMsgModeQuerySQLHolder() {

	}

	
	/**
	 * @��������:getQuerySQL
	 * @��������:���ݽڵ�ID��ȡ���ײ�ѯ��SQL���
	 * @�����뷵��˵��:
	 * 		@param txCode
	 * 		@param nodeId
	 * 		@return
	 * @�㷨����:
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
	 * @��������:buildQuerySQL
	 * @��������:���ݱ��Ľڵ�ID��ȡ������Ϣ�������ѯSQL
	 * @�����뷵��˵��:
	 * 		@param txModel
	 * 		@param nodeId
	 * 		@return
	 * @�㷨����:
	 */
	public static String buildQuerySQL(TxModel txModel, Long nodeId) {

		StringBuffer select = new StringBuffer("");
		StringBuffer from = new StringBuffer(" from ");
		StringBuffer on = new StringBuffer("");
		StringBuffer where = new StringBuffer("");
		boolean needDistinct=false;

		// ���������
		Map<Long, String> tabAliasMap = new HashMap<Long, String>();

		// ����������
		Map<Long, String> tabJionMap = new HashMap<Long, String>();

		// �ҳ���ѯ�ı�
		List<TxMsgNodeTabMap> txMsgNodeTabMapList = txModel
				.getTxMsgNodeTabMapMap().get(nodeId);

		int count = 1;
		TxMsgNodeTabMap txMsgNodeTabMap = null;
		String tabAlias = null;// ��ı���
		String tabJion = null; // �����������
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

		// ��������ֶ�
		List<TxMsgNodeAttr> fkAttrList = new ArrayList<TxMsgNodeAttr>();

		// ��ѯ���ֶ�
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
				 * ҵ����������Ҫ��ѯȥ��
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

		// ��������ϵ
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

		// ��������
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

		// �����������
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
		log.debug("���Ľڵ�:{}��Ӧ��SQL���Ϊ:{}", nodeId, sql);

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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SqlVersion
 * @��������SQL���ĸ�����Ϣ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:17:10   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:17:10
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
class SqlVersion {
	/**
	 * @��������:nodeIdSql
	 * @��������:TODO
	 * @since 1.0.0
	 */
	String nodeIdSql;
	/**
	 * @��������:nodeIdTimestamp
	 * @��������:������ʱ��
	 * @since 1.0.0
	 */
	Timestamp nodeIdTimestamp;

	/**
	 *@���캯�� 
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
