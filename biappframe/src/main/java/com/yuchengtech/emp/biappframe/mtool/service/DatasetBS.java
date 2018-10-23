package com.yuchengtech.emp.biappframe.mtool.service;

import static com.yuchengtech.emp.biappframe.base.common.GlobalConstants.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDatasetCatalogInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDatasetColInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDatasetInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDriverInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDsInfo;
import com.yuchengtech.emp.biappframe.mtool.vo.BioneDatasetVO;
import com.yuchengtech.emp.biappframe.mtool.vo.BioneSqlFilterVO;
import com.yuchengtech.emp.biappframe.mtool.vo.BioneTableVO;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.variable.entity.BioneSysVarInfo;
import com.yuchengtech.emp.bione.common.CommonComboBoxNode;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * 
 * <pre>
 * Title:数据集业务类
 * Description: 提供对数据集及数据项的自定义配置
 * </pre>
 * 
 * @author fanll fanll@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class DatasetBS extends BaseBS<Object> {
	// 数据源类型
	private static final String LOGIC_SOURCE_TYPE_TABLE = "01";// 数据库表
	@SuppressWarnings("unused")
	private static final String LOGIC_SOURCE_TYPE_SQL = "02";// 标准SQL
	private static final String ORACLE_DATA_SOURCE = "1";// 数据源为Oracle标识
	@SuppressWarnings("unused")
	private static final String DB2_DATA_SOURCE = "2";// 数据源为DB2标识

	// 平台逻辑数据项类型(text,number,date,object)
	private static final String[] textTypes = { "char", "varchar", "varchar2",
			"nchar", "nvarchar2", "long varchar", "graphics", "vargraphics",
			"long vargraphic" };
	private final String[] numberTypes = { "int", "long", "smallint", "double",
			"float", "numeric", "decimal", "rowid", "nrowid", "number",
			"integer", "real" };
	private final String[] dateTypes = { "timestamp", "date", "time" };
	private final String[] objectTypes = { "text", "blob", "clob", "nclob",
			"bfile", "raw", "long raw" };

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/*--------------------      接口方法        ----------------------*/

	/**
	 * 根据过滤条件获取数据集列表
	 * 
	 * @param firstResult
	 *            分页开始
	 * @param pageSize
	 *            分页大小
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序类型
	 * @param conditionMap
	 *            过滤条件
	 * @param catalogId
	 *            目录Id
	 * @return 数据集列表
	 * 
	 */
	public Map<String, Object> getDatasetList(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap,
			String catalogId) {
		return this.getDatasets(firstResult, pageSize, orderBy, orderType,
				conditionMap, catalogId);
	}

	/**
	 * 获取数据集的输出字段
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @return 输出字段集合
	 */
	public List<BioneDatasetColInfo> getOutFields(String datasetId) {
		if (notEmpty(datasetId)) {
			String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 and col.isUse=?1";
			return this.baseDAO.findWithIndexParam(jql, datasetId,
					COMMON_STATUS_VALID);
		}
		return new ArrayList<BioneDatasetColInfo>();
	}

	/**
	 * 获取数据集的查询字段
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @return 查询字段集合
	 */
	public List<BioneDatasetColInfo> getQueryFields(String datasetId) {
		if (notEmpty(datasetId)) {
			String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 and col.isQueryField=?1";
			return this.baseDAO.findWithIndexParam(jql, datasetId,
					COMMON_STATUS_VALID);
		}
		return new ArrayList<BioneDatasetColInfo>();
	}

	/**
	 * 通过过滤条件查询数据集的数据
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @param firstResult
	 *            分页开始
	 * @param pageSize
	 *            每页条数
	 * @param condition
	 *            过滤条件（name:value）
	 * @param appendFiltorSql
	 *            自定义过滤条件
	 * @return 查询的分页数据(结果map中结构：{Rows:分页数据,Total:数据总量,errorMsg:错误信息})
	 */
	public Map<String, Object> getDatasetData(String datasetId,
			int firstResult, int pageSize, Map<String, Object> condition,
			String appendFiltorSql) {
		return this.datasetPreview(datasetId, firstResult, pageSize, condition,
				false, appendFiltorSql);
	}

	/*--------------------      功能方法        ----------------------*/

	/**
	 * 数据集目录树
	 * 
	 * @param upId
	 *            上级Id
	 * @param basePath
	 *            工程路径
	 * @return
	 */
	public List<CommonTreeNode> getDatasetCatalogTree(String upId,
			String basePath) {
		List<CommonTreeNode> nodes = Lists.newArrayList();
		if (!notEmpty(upId)) {
			// 创建根节点
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId("ROOT");
			treeNode.setText("数据集");
			treeNode.setIcon(basePath + DATA_TREE_NODE_ICON_ROOT);
			Map<String, String> params = new HashMap<String, String>();
			params.put("realId", "0");
			params.put("isParent", "true");
			treeNode.setParams(params);
			nodes.add(treeNode);
		} else {
			String jql = "select c from BioneDatasetCatalogInfo c where c.logicSysNo=?0 and c.upId=?1";
			List<BioneDatasetCatalogInfo> catalogs = this.baseDAO
					.findWithIndexParam(jql, BiOneSecurityUtils
							.getCurrentUserInfo().getCurrentLogicSysNo(), upId);
			if (notEmpty(catalogs)) {
				for (int i = 0; i < catalogs.size(); i++) {
					BioneDatasetCatalogInfo cl = catalogs.get(i);
					CommonTreeNode treeNode = new CommonTreeNode();
					treeNode.setId(cl.getCatalogId());
					treeNode.setText(cl.getCatalogName());
					treeNode.setIcon(basePath + DATA_TREE_NODE_ICON_CATALOG);
					Map<String, String> params = new HashMap<String, String>();
					params.put("realId", cl.getCatalogId());
					params.put("isParent", "true");
					treeNode.setParams(params);
					nodes.add(treeNode);
				}
			}
		}
		return nodes;
	}

	/**
	 * 判断同路径下是否已存在同名目录
	 * 
	 * @param catalogId
	 *            目录Id
	 * @param upId
	 *            上级目录Id
	 * @param catalogName
	 *            目录名称
	 * @return
	 */
	public boolean catalogNameCanUse(String catalogId, String upId,
			String catalogName) {
		String jql = "select cl from BioneDatasetCatalogInfo cl where cl.upId=?0 and cl.catalogName=?1";
		if (notEmpty(catalogId)) {
			jql += " and cl.catalogId<>'" + catalogId + "'";
		}
		List<BioneDatasetCatalogInfo> list = this.baseDAO.findWithIndexParam(
				jql, upId, catalogName);
		return (notEmpty(list)) ? false : true;
	}

	/**
	 * 删除目录
	 * 
	 * @param catalogId
	 *            目录Id
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean deleteCatalog(String catalogId) {
		if (!notEmpty(catalogId))
			return false;

		List<String> sonIds = new ArrayList<String>();
		sonIds.add(catalogId);
		// 获取所有子目录
		this.getSonsByType(catalogId, sonIds);

		// 删除前检查该目录下是否有配置数据集
		String jql = "select dset from BioneDatasetInfo dset where dset.catalogId in (?0)";
		List<BioneDatasetInfo> list = this.baseDAO.findWithIndexParam(jql,
				sonIds);
		if (notEmpty(list))
			return false;

		jql = "delete from BioneDatasetCatalogInfo cl where cl.catalogId in(?0)";
		this.baseDAO.batchExecuteWithIndexParam(jql, sonIds);
		return true;
	}

	/**
	 * 递归查找目录下的所有子目录
	 * 
	 * @param catalogId
	 *            目录Id
	 * @param sonIds
	 *            子目录Id集合
	 */
	private void getSonsByType(String catalogId, List<String> sonIds) {
		String jql = "select cl from BioneDatasetCatalogInfo cl where cl.upId=?0";
		List<BioneDatasetCatalogInfo> children = this.baseDAO
				.findWithIndexParam(jql, catalogId);
		if (notEmpty(children)) {
			for (BioneDatasetCatalogInfo cl : children) {
				sonIds.add(cl.getCatalogId());
				this.getSonsByType(cl.getCatalogId(), sonIds);
			}
		}
	}

	/**
	 * 获取当前逻辑系统下的所有数据源
	 */
	public List<CommonComboBoxNode> getDataSources() {
		List<CommonComboBoxNode> nodes = Lists.newArrayList();
		String jql = "select ds from BioneDsInfo ds where ds.logicSysNo=?0";
		List<BioneDsInfo> list = this.baseDAO.findWithIndexParam(jql,
				BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		if (notEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				CommonComboBoxNode node = new CommonComboBoxNode();
				node.setId(list.get(i).getDsId());
				node.setText(list.get(i).getDsName());
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * 获取所有逻辑数据集
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param catalogId
	 *            目录Id
	 * @return
	 */
	public Map<String, Object> getDatasets(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap) {
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		StringBuffer jql = new StringBuffer(
				"select ds from BioneDatasetInfo ds where ds.logicSysNo='"
						+ logicSysNo + "' ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by ds." + orderBy + " " + orderType);
		}
		@SuppressWarnings("unchecked")
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneDatasetInfo> sr = this.baseDAO.findPageWithNameParam(
				firstResult, pageSize, jql.toString(), values);

		List<BioneDatasetInfo> datasets = sr.getResult();
		List<BioneDatasetVO> vos = Lists.newArrayList();

		if (notEmpty(datasets)) {
			for (int i = 0; i < datasets.size(); i++) {
				BioneDatasetInfo dset = datasets.get(i);
				BioneDatasetVO vo = new BioneDatasetVO();
				try {
					BeanUtils.copyProperties(vo, dset);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				BioneDsInfo ds = this.getEntityById(BioneDsInfo.class,
						dset.getDsId());
				if (ds != null) {
					vo.setDsName(ds.getDsName());
				}
				vos.add(vo);
			}
		}

		Map<String, Object> result = Maps.newHashMap();
		result.put("Rows", vos);
		result.put("Total", sr.getTotalCount());
		return result;
	}

	/**
	 * 获取指定目录下的所有逻辑数据集
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param catalogId
	 *            目录Id
	 * @return
	 */
	public Map<String, Object> getDatasets(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap,
			String catalogId) {
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		StringBuffer jql = new StringBuffer(
				"select ds from BioneDatasetInfo ds where ds.logicSysNo='"
						+ logicSysNo + "' ");
		if (notEmpty(catalogId)) {
			jql.append(" and ds.catalogId='" + catalogId + "' ");
		}
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by ds." + orderBy + " " + orderType);
		}
		@SuppressWarnings("unchecked")
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneDatasetInfo> sr = this.baseDAO.findPageWithNameParam(
				firstResult, pageSize, jql.toString(), values);

		List<BioneDatasetInfo> datasets = sr.getResult();
		List<BioneDatasetVO> vos = Lists.newArrayList();

		if (notEmpty(datasets)) {
			for (int i = 0; i < datasets.size(); i++) {
				BioneDatasetInfo dset = datasets.get(i);
				BioneDatasetVO vo = new BioneDatasetVO();
				try {
					BeanUtils.copyProperties(vo, dset);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				BioneDsInfo ds = this.getEntityById(BioneDsInfo.class,
						dset.getDsId());
				if (ds != null) {
					vo.setDsName(ds.getDsName());
				}
				vos.add(vo);
			}
		}

		Map<String, Object> result = Maps.newHashMap();
		result.put("Rows", vos);
		result.put("Total", sr.getTotalCount());
		return result;
	}

	/**
	 * 获取逻辑数据集下的所有逻辑数据项
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @return
	 */
	public Map<String, Object> getDatacolsOfDataset(String datasetId) {
		Map<String, Object> map = Maps.newHashMap();
		if (!notEmpty(datasetId))
			return map;
		String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 order by col.orderNo asc";
		List<BioneDatasetColInfo> cols = this.baseDAO.findWithIndexParam(jql,
				datasetId);
		cols = pkToFirst(cols);
		map.put("Rows", cols);
		map.put("Total", cols.size());
		return map;
	}

	/**
	 * 查询数据源下的所有物理表
	 * 
	 * @param dsId
	 *            数据源ID
	 * @return
	 */
	public Map<String, Object> getTablesOfDs(String dsId, String tableName,
			String tableComment, int start, int pagesize) {
		Map<String, Object> map = Maps.newHashMap();
		if (!notEmpty(dsId))
			return map;
		BioneDsInfo ds = this.getEntityById(BioneDsInfo.class, dsId);
		boolean isOracle = (ORACLE_DATA_SOURCE.equals(ds.getDriverId()));
		Connection conn = this.getConnOfDs(ds);
		String sql = "";
		String appendSql = "";
		String countSql = "";
		// 1.查询物理表
		if (conn != null) {
			int startRow = start + 1;
			int endRow = startRow + pagesize - 1;
			if (isOracle) {// ORACLE/DB2两种查表SQL语句
				if (notEmpty(tableName)) {
					appendSql += " AND TABLE_NAME LIKE '%"
							+ tableName.toUpperCase() + "%' ";
				}
				if (notEmpty(tableComment)) {
					appendSql += " AND COMMENTS LIKE '%" + tableComment + "%' ";
				}
				sql = "SELECT TABLE_NAME,COMMENTS FROM (SELECT TABLE_NAME,COMMENTS,ROWNUM AS RN FROM (SELECT TABLE_NAME ,COMMENTS FROM USER_TAB_COMMENTS WHERE TABLE_TYPE='TABLE' ";
				sql += appendSql;
				sql += " ORDER BY TABLE_NAME ASC) WHERE ROWNUM<=" + endRow
						+ ") WHERE RN>=" + startRow;
				countSql = "SELECT COUNT(TABLE_NAME) FROM USER_TAB_COMMENTS WHERE TABLE_TYPE='TABLE' "
						+ appendSql;
			} else {
				if (notEmpty(tableName)) {
					appendSql += " AND TABNAME LIKE '%"
							+ tableName.toUpperCase() + "%' ";
				}
				if (notEmpty(tableComment)) {
					appendSql += " AND REMARKS LIKE '%" + tableComment + "%' ";
				}
				sql = "SELECT TABNAME,REMARKS FROM (SELECT TABNAME,REMARKS,ROWNUMBER() OVER() AS rowid FROM (SELECT TABNAME,REMARKS FROM SYSCAT.TABLES WHERE TYPE='T' AND TABSCHEMA=CURRENT SCHEMA ";
				sql += appendSql;
				sql += "ORDER BY TABNAME ASC)) WHERE rowid>=" + startRow
						+ " AND rowid <=" + endRow;
				countSql = "SELECT COUNT(TABNAME) FROM SYSCAT.TABLES WHERE TYPE='T' AND TABSCHEMA=CURRENT SCHEMA "
						+ appendSql;
			}
			Statement stm;
			try {
				stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				List<BioneTableVO> tables = Lists.newArrayList();
				while (rs.next()) {
					BioneTableVO table = new BioneTableVO();
					table.setTableName(rs.getString(1));// 表名
					table.setTableComment(rs.getString(2));// 表注释
					tables.add(table);
				}
				map.put("Rows", tables);
			} catch (SQLException e) {
				e.printStackTrace();
				return map;
			} finally {
				// 释放连接
				JdbcUtils.closeConnection(conn);
			}
		}
		// 2.查询物理表总数
		conn = this.getConnOfDs(ds);
		if (conn != null) {
			Statement stm;
			try {
				stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(countSql);
				if (rs.next()) {
					map.put("Total", rs.getString(1));
				} else {
					map.put("Total", 0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return map;
			} finally {
				// 释放连接
				JdbcUtils.closeConnection(conn);
			}
		}
		return map;
	}

	/**
	 * 查询物理表中的所有物理字段
	 * 
	 * @param dsId
	 *            数据源ID
	 * @param tableName
	 *            表名
	 * @return
	 */
	public Map<String, Object> getFieldsOfTable(String dsId, String tableName) {
		Map<String, Object> map = Maps.newHashMap();
		if (!notEmpty(dsId))
			return map;
		BioneDsInfo ds = this.getEntityById(BioneDsInfo.class, dsId);
		boolean isOracle = (ORACLE_DATA_SOURCE.equals(ds.getDriverId()));
		Connection conn = this.getConnOfDs(ds);
		Statement stm = null;
		if (conn != null) {
			String sql = "";
			if (isOracle) {// ORACLE/DB2两种查列SQL语句
				sql = "SELECT col.COLUMN_NAME,cmt.COMMENTS,col.DATA_TYPE,col.DATA_LENGTH,col.NULLABLE FROM USER_TAB_COLUMNS col,USER_COL_COMMENTS cmt WHERE col.COLUMN_NAME=cmt.COLUMN_NAME AND col.TABLE_NAME='"
						+ tableName
						+ "' AND cmt.TABLE_NAME='"
						+ tableName
						+ "'";
			} else {
				sql = "SELECT COLNAME,REMARKS,TYPENAME,LENGTH,NULLS FROM SYSCAT.COLUMNS WHERE TABNAME='"
						+ tableName + "' AND TABSCHEMA=CURRENT SCHEMA";
			}
			try {
				stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				List<BioneDatasetColInfo> cols = Lists.newArrayList();
				while (rs.next()) {
					BioneDatasetColInfo col = new BioneDatasetColInfo();
					col.setEnName(rs.getString(1));// 列名
					col.setCnName(rs.getString(2));// 列注释
					col.setFieldType(getLogicDataType(rs.getString(3)));
					col.setLength(new BigDecimal(rs.getString(4)));
					col.setIsNullable(("Y"
							.equals(rs.getString(5).toUpperCase())) ? COMMON_STATUS_VALID
							: COMMON_STATUS_INVALID);
					col.setIsPk(COMMON_STATUS_INVALID);
					col.setIsPk(COMMON_STATUS_INVALID);
					col.setIsUse(COMMON_STATUS_VALID);
					cols.add(col);
				}
				cols = this.getPK(tableName, cols, ds, isOracle);
				cols = pkToFirst(cols);
				map.put("Rows", cols);
				map.put("Total", cols.size());
				return map;
			} catch (SQLException e) {
				e.printStackTrace();
				return map;
			} finally {
				// 释放连接
				JdbcUtils.closeConnection(conn);
			}
		}
		return map;
	}

	// 查询主键
	private List<BioneDatasetColInfo> getPK(String tableName,
			List<BioneDatasetColInfo> columns, BioneDsInfo ds, boolean isOracle) {
		String sql = null;
		if (isOracle) {
			sql = "select column_name from user_constraints c,user_cons_columns col where c.constraint_name=col.constraint_name and c.constraint_type='P'and c.table_name='"
					+ tableName + "'";
		} else {
			sql = "select   colname   from   SYSCAT.KEYCOLUSE   where   TABSCHEMA=CURRENT SCHEMA   and   TABNAME='"
					+ tableName + "'";
		}
		Connection conn = this.getConnOfDs(ds);
		Statement stm = null;
		try {
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				if (notEmpty(rs.getString(1))) {
					String pkName = rs.getString(1).toUpperCase();
					for (BioneDatasetColInfo col : columns) {
						if (col.getEnName().toUpperCase().equals(pkName)) {
							col.setIsPk(COMMON_STATUS_VALID);
						}
					}
				}
			}
			return columns;
		} catch (SQLException e) {
			return columns;
		} finally {
			// 释放连接
			JdbcUtils.closeConnection(conn);
		}
	}

	/**
	 * 保存配置完成的逻辑数据集和逻辑数据项
	 * 
	 * @param dataset
	 *            数据集
	 * @param datacolsJsonStr
	 *            数据项集合json
	 */
	@Transactional(readOnly = false)
	public void save(BioneDatasetInfo dataset, String datacolsJsonStr) {
		String datasetId = dataset.getDatasetId();
		dataset.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());
		// 1.保存数据集
		if (!notEmpty(dataset.getDatasetId())) {
			datasetId = RandomUtils.uuid2();
			dataset.setDatasetId(datasetId);
			this.saveEntity(dataset);
		} else {
			this.updateEntity(dataset);
		}

		// 2.删除原关联的数据项
		String jql = "delete from BioneDatasetColInfo col where col.datasetId=?0";
		this.baseDAO.batchExecuteWithIndexParam(jql, dataset.getDatasetId());

		// 3.添加新的关联数据项
		if (notEmpty(datacolsJsonStr)) {
			// 解析json
			JSONObject jsonObject = JSONObject.fromObject(datacolsJsonStr);
			JSONArray jsonArray = jsonObject.getJSONArray("fields");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				BioneDatasetColInfo col = (BioneDatasetColInfo) JSONObject
						.toBean(object, BioneDatasetColInfo.class);
				if (col != null) {
					col.setFieldId(RandomUtils.uuid2());
					col.setDatasetId(datasetId);
					this.saveEntity(col);
				}
			}
		}
	}

	/**
	 * 批量删除数据集
	 * 
	 * @param ids
	 *            数据集Id集合
	 */
	@Transactional(readOnly = false)
	public void deleteBatch(List<String> ids) {
		String jql = "delete from BioneDatasetColInfo col where col.datasetId in (?0)";
		this.baseDAO.batchExecuteWithIndexParam(jql, ids);
		jql = "delete from BioneDatasetInfo dset where dset.datasetId in (?0)";
		this.baseDAO.batchExecuteWithIndexParam(jql, ids);
	}

	/**
	 * 生成数据集预览查询Form结构
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @return
	 */
	public String getSearchStruct(String datasetId) {
		StringBuffer scs = new StringBuffer();
		if (notEmpty(datasetId)) {
			// 数据项
			String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 and col.isQueryField=?1 order by col.orderNo asc";
			List<BioneDatasetColInfo> cols = this.baseDAO.findWithIndexParam(
					jql, datasetId, COMMON_STATUS_VALID);
			if (notEmpty(cols)) {
				scs.append("[");
				for (int i = 0; i < cols.size(); i++) {
					BioneDatasetColInfo col = cols.get(i);
					scs.append("{display:'");
					scs.append(notEmpty(col.getCnName()) ? col.getCnName()
							: col.getEnName());
					scs.append("',");
					scs.append("name:'");
					scs.append(col.getEnName());
					scs.append("_name',");
					scs.append("type:'");
					if (LOGIC_DATA_TYPE_DATE.equals(col.getFieldType())) {
						scs.append("date");
					} else {
						scs.append("text");
					}
					scs.append("',newline:");
					scs.append(i % 2 == 0);
					scs.append(",cssClass:'field',");
					if (LOGIC_DATA_TYPE_NUMBER.equals(col.getFieldType())) {
						scs.append("options:{digits : true},");
					}
					scs.append("attr : {field :'");
					scs.append(col.getEnName());
					scs.append("',op : 'like'}}");
					if (i != cols.size() - 1) {
						scs.append(",");
					}
				}
				scs.append("]");
			}
		}
		return scs.toString();
	}

	/**
	 * 生成数据集预览Grid列结构
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @return
	 */
	public String getGridStruct(String datasetId) {
		StringBuffer columns = new StringBuffer();
		if (notEmpty(datasetId)) {
			// 数据项
			String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 and col.isUse=?1 order by col.orderNo asc";
			List<BioneDatasetColInfo> cols = this.baseDAO.findWithIndexParam(
					jql, datasetId, COMMON_STATUS_VALID);
			cols = pkToFirst(cols);
			if (notEmpty(cols)) {
				columns.append("[");
				for (int i = 0; i < cols.size(); i++) {
					BioneDatasetColInfo col = cols.get(i);
					columns.append("{display:'");
					columns.append(notEmpty(col.getCnName()) ? col.getCnName()
							: col.getEnName());
					columns.append("',");
					columns.append("name:'");
					columns.append(col.getEnName());
					columns.append("',");
					columns.append("minWidth:100}");
					if (i != cols.size() - 1) {
						columns.append(",");
					}
				}
				columns.append("]");
			}
		}
		return columns.toString();
	}

	/**
	 * 数据集预览信息
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @param firstResult
	 * @param pageSize
	 * @param condition
	 *            过滤条件
	 * @param isStringParam
	 *            过滤条件值是否为页面传入的字符串型参数
	 * @return
	 */
	public Map<String, Object> datasetPreview(String datasetId,
			int firstResult, int pageSize, Map<String, Object> condition,
			boolean isStringParam, String appendFiltorSql) {
		Map<String, Object> preView = Maps.newHashMap();
		if (!notEmpty(datasetId)) {
			preView.put("errorMsg", "数据集不存在。");
			return preView;
		}
		BioneDatasetInfo dataset = this.getEntityById(BioneDatasetInfo.class,
				datasetId);
		if (dataset == null) {
			preView.put("errorMsg", "数据集不存在。");
			return preView;
		}

		// 数据源
		BioneDsInfo ds = this.getEntityById(BioneDsInfo.class,
				dataset.getDsId() == null ? "" : dataset.getDsId());
		if (ds == null) {
			preView.put("errorMsg", "数据源不存在。");
			return preView;
		}
		// 数据集合法
		if (LOGIC_SOURCE_TYPE_TABLE.equals(dataset.getDsType())) {
			if (!notEmpty(dataset.getTableEname())) {
				preView.put("errorMsg", "未正确配置物理表。");
				return preView;
			}
		} else {
			if (!notEmpty(dataset.getQuerySql())) {
				preView.put("errorMsg", "未正确配置SQL语句。");
				return preView;
			}
		}
		// 数据项
		String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 and col.isUse=?1 order by col.orderNo asc";
		List<BioneDatasetColInfo> cols = this.baseDAO.findWithIndexParam(jql,
				datasetId, COMMON_STATUS_VALID);
		if (!notEmpty(cols)) {
			preView.put("errorMsg", "数据集未配置有效数据项。");
			return preView;
		}
		StringBuffer colNamesBuff = new StringBuffer();// 字段名集合
		for (int i = 0; i < cols.size(); i++) {
			colNamesBuff.append(cols.get(i).getEnName());
			colNamesBuff.append(",");
		}
		String colNames = colNamesBuff.toString().substring(0,
				colNamesBuff.length() - 1);

		// 查询数据
		Connection conn = this.getConnOfDs(ds);
		if (conn == null) {
			preView.put("errorMsg", "数据源配置不正确。");
			return preView;
		}
		String sql = "";
		int start = firstResult;
		int end = start + pageSize - 1;
		// 加工过滤条件
		BioneSqlFilterVO filter = this.getFilter(datasetId, condition,
				isStringParam, appendFiltorSql);

		PreparedStatement stm = null;
		ResultSet rs = null;

		if (LOGIC_SOURCE_TYPE_TABLE.equals(dataset.getDsType())) {
			// 数据库表型
			sql = this.getSql(ds.getDriverId(), colNames,
					dataset.getTableEname(), start, end, filter.getFilterSql());
			try {
				stm = conn.prepareStatement(sql);
				for (int i = 0; i < filter.getParams().size(); i++) {
					stm.setObject(i + 1, filter.getParams().get(i));
				}
				rs = stm.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				if (conn != null)
					JdbcUtils.closeConnection(conn);
				preView.put("errorMsg", "数据库中相关表结构发生变化。");
				return preView;
			}
		} else {
			// 标准SQL型
			sql = this.getSql(ds.getDriverId(), colNames,
					"(" + dataset.getQuerySql() + ")", start, end,
					filter.getFilterSql());
			try {
				stm = conn.prepareStatement(sql);
				for (int i = 0; i < filter.getParams().size(); i++) {
					stm.setObject(i + 1, filter.getParams().get(i));
				}
				rs = stm.executeQuery();
			} catch (SQLException e) {
				if (conn != null)
					JdbcUtils.closeConnection(conn);
				preView.put("errorMsg", "SQL语法错误，执行失败。");
				return preView;
			}
		}

		List<Map<String, String>> grid = Lists.newArrayList();
		try {
			while (rs.next()) {
				Map<String, String> row = Maps.newHashMap();
				for (int i = 0; i < cols.size(); i++) {
					row.put(cols.get(i).getEnName(),
							rs.getString(i + 1) == null ? "" : rs
									.getString(i + 1));
				}
				grid.add(row);
			}
		} catch (SQLException e) {
			if (conn != null)
				JdbcUtils.closeConnection(conn);
			preView.put("errorMsg", "未查询到有效的结果。");
			return preView;
		}
		preView.put("Rows", grid);
		preView.put("Total", grid.size());
		// 释放连接
		if (conn != null)
			JdbcUtils.closeConnection(conn);
		return preView;
	}

	/**
	 * oracle/db2的分页SQL
	 * 
	 * @param dbType
	 *            数据库类型(oracle/db2)
	 * @param tableName
	 *            表名
	 * @param cols
	 *            查询的字段字符串
	 * @param start
	 *            分页开始
	 * @param end
	 *            分页结束
	 * @return
	 */
	private String getSql(String dbType, String cols, String sourceTable,
			int start, int end, String filter) {
		StringBuffer sqlBuff = new StringBuffer();
		if (ORACLE_DATA_SOURCE.equals(dbType)) {
			sqlBuff.append("SELECT ");
			sqlBuff.append(cols);
			sqlBuff.append(" FROM (SELECT ");
			sqlBuff.append(cols);
			sqlBuff.append(",ROWNUM AS RN FROM ");
			sqlBuff.append("(SELECT ");
			sqlBuff.append(cols);
			sqlBuff.append(" FROM ");
			sqlBuff.append(sourceTable);
			sqlBuff.append(" WHERE 1=1 ");
			if (notEmpty(filter)) {
				sqlBuff.append(" AND ");
				sqlBuff.append(filter);
			}
			sqlBuff.append(")");
			sqlBuff.append(" WHERE ROWNUM<=");
			sqlBuff.append(end);
			sqlBuff.append(")WHERE RN>=");
			sqlBuff.append(start);
		} else {
			sqlBuff.append("SELECT ");
			sqlBuff.append(cols);
			sqlBuff.append(" FROM (SELECT ");
			sqlBuff.append(cols);
			sqlBuff.append(",ROWNUMBER() OVER() AS rowid FROM ");
			sqlBuff.append("(SELECT ");
			sqlBuff.append(cols);
			sqlBuff.append(" FROM ");
			sqlBuff.append(sourceTable);
			sqlBuff.append(" WHERE 1=1 ");
			if (notEmpty(filter)) {
				sqlBuff.append(" AND ");
				sqlBuff.append(filter);
			}
			sqlBuff.append(")) WHERE rowid>=");
			sqlBuff.append(start);
			sqlBuff.append(" AND rowid <=");
			sqlBuff.append(end);
		}
		return sqlBuff.toString();
	}

	/**
	 * 根据查询condition获取过滤VO
	 * 
	 * @param datasetId
	 *            数据集Id
	 * @param condition
	 *            查询条件字符串
	 * @return
	 */
	private BioneSqlFilterVO getFilter(String datasetId,
			Map<String, Object> condition, boolean isStringParam,
			String appendFiltorSql) {
		BioneSqlFilterVO filter = new BioneSqlFilterVO();
		StringBuffer filterSql = new StringBuffer();

		List<String> names = Lists.newArrayList();
		List<Object> values = Lists.newArrayList();
		Set<String> keys = condition.keySet();
		for (String k : keys) {
			names.add(k);
			values.add(condition.get(k));
		}

		Map<String, BioneDatasetColInfo> cols = null;
		if (notEmpty(names)) {
			cols = this.getColType(datasetId, names);
		}

		for (int i = 0; i < names.size(); i++) {
			if (cols == null || cols.get(names.get(i)) == null) {
				continue;
			}
			String n = names.get(i);
			Object v = values.get(i);
			String t = cols.get(n) != null ? cols.get(n).getFieldType()
					: LOGIC_DATA_TYPE_TEXT;
			String op = cols.get(n) != null ? cols.get(n).getQueryLogic() : "=";
			filterSql.append(n);
			if (LOGIC_DATA_TYPE_DATE.equals(t)
					|| LOGIC_DATA_TYPE_NUMBER.equals(t)) {
				if ("like".equals(op) || op == null) {
					filterSql.append("=? ");
				} else {
					filterSql.append(op);
					filterSql.append("? ");
				}
			} else {
				if ("=".equals(op)) {
					filterSql.append("=? ");
				} else {
					filterSql.append(" like ? ");
				}
			}
			if (i != (names.size() - 1)) {
				filterSql.append(" AND ");
			}

			Object p = null;
			if (isStringParam) {
				String vStr = v.toString();
				try {
					if (LOGIC_DATA_TYPE_DATE.equals(t)) {
						long tt = sdf.parse(vStr).getTime();
						p = new Timestamp(tt);
					} else if (LOGIC_DATA_TYPE_NUMBER.equals(t)) {
						p = new BigDecimal(vStr);
					} else if (LOGIC_DATA_TYPE_TEXT.equals(t)) {
						p = ("=".equals(op)) ? vStr : ("%" + vStr + "%");
					} else {
						p = vStr;
					}
				} catch (Exception e) {
					if (LOGIC_DATA_TYPE_DATE.equals(t)) {
						p = new Timestamp(new Date().getTime());
					} else if (LOGIC_DATA_TYPE_NUMBER.equals(t)) {
						p = new BigDecimal(0);
					} else if (LOGIC_DATA_TYPE_TEXT.equals(t)) {
						p = ("=".equals(op)) ? vStr : ("%" + vStr + "%");
					} else {
						p = vStr;
					}
				}
			} else {
				p = v;
			}
			filter.addParam(p);
		}
		if (notEmpty(appendFiltorSql)) {
			filterSql.append(" AND ");
			filterSql.append(appendFiltorSql);
		}
		filter.setFilterSql(filterSql.toString());
		return filter;
	}

	// 获取数据项数据类型
	private Map<String, BioneDatasetColInfo> getColType(String datasetId,
			List<String> colNames) {
		String jql = "select col from BioneDatasetColInfo col where col.datasetId=?0 and col.enName in (?1)";
		List<BioneDatasetColInfo> cols = this.baseDAO.findWithIndexParam(jql,
				datasetId, colNames);
		Map<String, BioneDatasetColInfo> map = Maps.newHashMap();
		for (BioneDatasetColInfo col : cols) {
			map.put(col.getEnName(), col);
		}
		return map;
	}

	/**
	 * 分页查询系统变量
	 * 
	 * @param firstResult
	 *            第一条数据索引
	 * @param pageSize
	 *            每页显示条数
	 * @param orderBy
	 *            排序列
	 * @param orderType
	 *            排序方式
	 * @param conditionMap
	 *            参数列表
	 * @return 查询结果集
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getSysVars(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap) {
		String currentLogicNo = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		StringBuffer jql = new StringBuffer(
				"select v from BioneSysVarInfo v where v.logicSysNo='"
						+ currentLogicNo + "'");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by g." + orderBy + " " + orderType + " ");
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneSysVarInfo> searchResult = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Rows", searchResult.getResult());
		map.put("Total", searchResult.getTotalCount());
		return map;
	}

	/**
	 * 获取指定数据源的一个连接
	 * 
	 * @param ds
	 *            数据源
	 * @return
	 */
	private Connection getConnOfDs(BioneDsInfo ds) {
		if (ds == null)
			return null;
		BioneDriverInfo dv = this.getEntityById(BioneDriverInfo.class,
				ds.getDriverId());
		if (dv == null)
			return null;
		// 获取数据源连接
		String driverName = dv.getDriverName();
		String url = ds.getConnUrl();
		String user = ds.getConnUser();
		String pwd = ds.getConnPwd();
		Connection conn = null;
		if (notEmpty(driverName) && notEmpty(url) && notEmpty(user)) {
			try {
				// 注册驱动
				Driver driver = (Driver) Class.forName(driverName)
						.newInstance();
				Properties p = new Properties();
				p.put("user", user);
				p.put("password", pwd);
				// 获取连接
				conn = driver.connect(url, p);
				if (conn != null) {
					return conn;
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 将一个物理字段类型转换为平台逻辑字段类型
	 * 
	 * @param physicalDataType
	 *            物理数据类型
	 * @return 逻辑数据类型
	 */
	private String getLogicDataType(String physicalDataType) {
		if (!notEmpty(physicalDataType))
			return "";
		if (typeContains(textTypes, physicalDataType.toLowerCase()))
			return LOGIC_DATA_TYPE_TEXT;
		else if (typeContains(numberTypes, physicalDataType.toLowerCase()))
			return LOGIC_DATA_TYPE_NUMBER;
		else if (typeContains(dateTypes, physicalDataType.toLowerCase()))
			return LOGIC_DATA_TYPE_DATE;
		else if (typeContains(objectTypes, physicalDataType.toLowerCase()))
			return LOGIC_DATA_TYPE_OBJECT;
		else
			return "";
	}

	private boolean typeContains(String[] types, String str) {
		for (int i = 0; i < types.length; i++) {
			if (str.indexOf(types[i]) >= 0)
				return true;
		}
		return false;
	}

	// 主键列置前
	private List<BioneDatasetColInfo> pkToFirst(
			List<BioneDatasetColInfo> orderCols) {
		List<BioneDatasetColInfo> pks = Lists.newArrayList();
		List<BioneDatasetColInfo> cols = Lists.newArrayList();
		for (int i = 0; i < orderCols.size(); i++) {
			if (COMMON_STATUS_VALID.equals(orderCols.get(i).getIsPk())) {
				pks.add(orderCols.get(i));
			} else {
				cols.add(orderCols.get(i));
			}
		}
		pks.addAll(cols);
		return pks;
	}

	// 非空判断
	private boolean notEmpty(String temp) {
		if (temp == null || "".equals(temp) || "null".equals(temp))
			return false;
		else
			return true;
	}

	@SuppressWarnings("rawtypes")
	private boolean notEmpty(List temp) {
		if (temp == null || temp.size() == 0)
			return false;
		else
			return true;
	}
}
