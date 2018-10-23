package com.yuchengtech.emp.ecif.customer.customization.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.customization.constant.CtztConstant;
import com.yuchengtech.emp.ecif.customer.customization.entity.DefinetableviewLookVO;

@Service("CustomizationColumnQueryBS")
@Transactional(readOnly = true)
public class CustomizationColumnQueryBS extends BaseBS<DefinetableviewLookVO> {

	protected static Logger log = LoggerFactory
			.getLogger(CustomizationColumnQueryBS.class);

	@Autowired
	private ResultUtil resultUtil;
	
	/**
	 * 获取显示列表列名
	 * @return
	 */
	public DefinetableviewLookVO[] getisMustColumn(String tbType) {
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		String oprno = user.getLoginName();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TB.TB_SERIAL_NO, TB_NAME, TB_NM_ACRONYM, TB_CNNM, IS_MANYTOONE, ");
		sql.append(" TB_TYPE, TB_DESCRIBE, IS_MIDDLE_TABLE,MIDDLE_TABLE_NM,RELATION_COLUMN, ");
		sql.append(" TB_SHOW_SEQ, CUM_SERIAL_NO, CUM_NAME, CUM_CNNM,  IS_CODE, CATE_ID, DATA_TYPE, DATA_LEN, ");
		sql.append(" IS_CONDITION, IS_RESULT, IS_MUST, CUM_DESCRIBE, CUM_SHOW_SEQ ");
		sql.append(" FROM APP_SCV_TAB_DEF TB INNER JOIN APP_SCV_COL_DEF CUM ON TB.TB_SERIAL_NO = CUM.TB_SERIAL_NO ");
		sql.append(" LEFT JOIN TX_TAB_DEF TAB ON TB.TB_NAME = TAB.TAB_NAME ");
		sql.append(" LEFT JOIN TX_COL_DEF COL ON TAB.TAB_ID = COL.TAB_ID AND COL.COL_NAME = CUM.CUM_NAME ");
		sql.append(" WHERE (CUM.IS_MUST = '"+CtztConstant.ECIF_CTZT_IS_MUST_YES+"' ");
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_PER)){
			sql.append(" AND TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PER+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"')) ");
		}
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG)){
			sql.append(" AND TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"')) ");
		}
		sql.append(" OR (EXISTS(SELECT 1 FROM APP_SCV_OPER_COL_DEF OCC WHERE OCC.CUM_SERIAL_NO = CUM.CUM_SERIAL_NO AND OCC.OPR_NO = '"+oprno+"' AND OCC.CST_TYPE = '"+tbType+"')) ");
		sql.append(" ORDER BY CUM_SHOW_SEQ ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		DefinetableviewLookVO[] definetableviewLookVOs = null;
		try {
			 definetableviewLookVOs = this.resultUtil.listObjectsToEntityBeans(list, DefinetableviewLookVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return definetableviewLookVOs;
	}
	
	/**
	 * 获取可定制表集合
	 * @return
	 */
	public DefinetableviewLookVO[] getDefinetableviews(String tbType){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TB_SERIAL_NO, TB_NAME, TB_NM_ACRONYM, TB_CNNM, IS_MANYTOONE, ");
		sql.append(" IS_MIDDLE_TABLE,MIDDLE_TABLE_NM,RELATION_COLUMN, ");
		sql.append(" TB_TYPE, TB_DESCRIBE, TB_SHOW_SEQ FROM APP_SCV_TAB_DEF TB WHERE ");
		sql.append(" EXISTS(SELECT 1 FROM APP_SCV_COL_DEF CUM WHERE TB.TB_SERIAL_NO = CUM.TB_SERIAL_NO AND IS_MUST = '"+CtztConstant.ECIF_CTZT_IS_MUST_NO+"') ");
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_PER)){
			sql.append(" AND TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PER+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"') ");
		}
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG)){
			sql.append(" AND TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"') ");
		}
		sql.append(" ORDER BY TB.TB_SHOW_SEQ ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		DefinetableviewLookVO[] definetableviewLookVOs = null;
		try {
			 definetableviewLookVOs = this.resultUtil.listObjectsToEntityBeans(list, DefinetableviewLookVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return definetableviewLookVOs;
	}
	
	/**
	 * 获取是查询结果的表集合
	 * @return
	 */
	public DefinetableviewLookVO[] getIsResultDefinetableviews(String tbType){
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		String oprno = user.getLoginName();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TB_SERIAL_NO, TB_NAME, TB_NM_ACRONYM, TB_CNNM, IS_MANYTOONE, ");
		sql.append(" IS_MIDDLE_TABLE,MIDDLE_TABLE_NM,RELATION_COLUMN, ");
		sql.append(" TB_TYPE, TB_DESCRIBE, TB_SHOW_SEQ FROM APP_SCV_TAB_DEF TB WHERE 1=1 ");
		sql.append(" AND (EXISTS(SELECT 1 FROM APP_SCV_COL_DEF CUM WHERE CUM.TB_SERIAL_NO = TB.TB_SERIAL_NO ");
		sql.append(" AND CUM.IS_MUST = '"+CtztConstant.ECIF_CTZT_IS_MUST_YES+"') ");
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_PER)){
			sql.append(" AND TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PER+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"')) ");
		}
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG)){
			sql.append(" AND TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"')) ");
		}
		sql.append(" OR (EXISTS(SELECT 1 FROM APP_SCV_COL_DEF CUM,APP_SCV_OPER_COL_DEF OCC ");
		sql.append(" WHERE CUM.CUM_SERIAL_NO = OCC.CUM_SERIAL_NO AND TB.TB_SERIAL_NO = CUM.TB_SERIAL_NO ");
		sql.append(" AND OCC.OPR_NO = '"+oprno+"' AND OCC.CST_TYPE = '"+tbType+"')) ");
		sql.append(" ORDER BY TB.TB_SHOW_SEQ ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		DefinetableviewLookVO[] definetableviewLookVOs = null;
		try {
			 definetableviewLookVOs = this.resultUtil.listObjectsToEntityBeans(list, DefinetableviewLookVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return definetableviewLookVOs;
	}
	
	/**
	 * 获取全部列集合
	 * @return
	 */
	public DefinetableviewLookVO[] getDefinecolumnviews(String tbType){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CUM_SERIAL_NO, TB_SERIAL_NO, CUM_NAME, CUM_CNNM, ");
		sql.append(" IS_CONDITION, IS_RESULT, IS_MUST, CUM_DESCRIBE, CUM_SHOW_SEQ ");
		sql.append(" FROM APP_SCV_COL_DEF CUL WHERE CUL.IS_MUST = '"+CtztConstant.ECIF_CTZT_IS_MUST_NO+"' ");
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_PER)){
			sql.append(" AND EXISTS(SELECT 1 FROM APP_SCV_TAB_DEF TB WHERE TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PER+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"') AND TB.TB_SERIAL_NO = CUL.TB_SERIAL_NO) ");
		}
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG)){
			sql.append(" AND EXISTS(SELECT 1 FROM APP_SCV_TAB_DEF TB WHERE TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"') AND TB.TB_SERIAL_NO = CUL.TB_SERIAL_NO) ");
		}
		sql.append(" ORDER BY CUM_SHOW_SEQ ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		DefinetableviewLookVO[] definetableviewLookVOs = null;
		try {
			 definetableviewLookVOs = this.resultUtil.listObjectsToEntityBeans(list, DefinetableviewLookVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return definetableviewLookVOs;
	}
	
	/**
	 * 获取定制列集合
	 * @return
	 */
	public DefinetableviewLookVO[] getOprCtztColumns(String tbType){
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		String oprno = user.getLoginName();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CUM.CUM_SERIAL_NO, TB_SERIAL_NO, CUM_NAME, CUM_CNNM,  ");
		sql.append(" IS_CONDITION, IS_RESULT, IS_MUST, CUM_DESCRIBE, CUM_SHOW_SEQ ");
		sql.append(" FROM APP_SCV_COL_DEF CUM,APP_SCV_OPER_COL_DEF OPRCUM ");
		sql.append(" WHERE CUM.CUM_SERIAL_NO = OPRCUM.CUM_SERIAL_NO AND OPRCUM.CST_TYPE = '"+tbType+"' ");
		sql.append(" AND OPRCUM.OPR_NO = '"+oprno+"' ");
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_PER)){
			sql.append(" AND EXISTS(SELECT 1 FROM APP_SCV_TAB_DEF TB WHERE TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PER+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"') AND TB.TB_SERIAL_NO = CUM.TB_SERIAL_NO) ");
		}
		if(tbType.equals(CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG)){
			sql.append(" AND EXISTS(SELECT 1 FROM APP_SCV_TAB_DEF TB WHERE TB_TYPE IN ('"+CtztConstant.ECIF_CTZT_TABLE_TYPE_ORG+"','"+CtztConstant.ECIF_CTZT_TABLE_TYPE_PUB+"') AND TB.TB_SERIAL_NO = CUM.TB_SERIAL_NO) ");
		}
		sql.append(" ORDER BY CUM.CUM_SHOW_SEQ ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		DefinetableviewLookVO[] definetableviewLookVOs = null;
		try {
			 definetableviewLookVOs = this.resultUtil.listObjectsToEntityBeans(list, DefinetableviewLookVO.class, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return definetableviewLookVOs;
	}
	
	/**
	 * 构造显示树，并返回第一层的节点
	 * 
	 * @param parentId
	 *            上级节点id
	 * @param isLoop
	 *            是否遍历子节点
	 * @param conditionMap
	 * 			生成的搜索查询语句->BaseController->buildSerachCondition
	 * @return tbSerialNo 表流水
	 */
	public List<CommonTreeNode> ctztTree(String tbType) {


		List<CommonTreeNode> commonTreeNodeList = Lists.newArrayList();
		
		CommonTreeNode treeNode = new CommonTreeNode();
		treeNode.setId("000000");
		treeNode.setUpId("000000");
		treeNode.setIcon(null);
		treeNode.setText("定制查询列");
		treeNode.setData(null);
		
		Map<String, String> params = Maps.newHashMap();
		params.put("open", "true");
		
		treeNode.setParams(params);
		
		commonTreeNodeList.add(treeNode);

		this.findAllValidDept(commonTreeNodeList, tbType);
			
		return commonTreeNodeList;
	}
	
	/**
	 * 设置树
	 * @param commonTreeNodeList 树
	 */
	public void findAllValidDept(List<CommonTreeNode> commonTreeNodeList, String tbType) {
		DefinetableviewLookVO[] tables = getDefinetableviews(tbType);
		DefinetableviewLookVO[] columns = getDefinecolumnviews(tbType);
		DefinetableviewLookVO[] ctztcolumns = getOprCtztColumns(tbType);
		if(tables != null && tables.length > 0 && columns != null && columns.length > 0){
			repairTable(Arrays.asList(tables), 0, commonTreeNodeList);
			repairColumn(Arrays.asList(columns), ctztcolumns, 0, commonTreeNodeList,tables.length);
		}
	}
	
	/**
	 * 将查询到的表设置到定制列里
	 * @param list
	 * @param index
	 * @param commonTreeNodeList 树
	 */
	public void repairTable(List<DefinetableviewLookVO> list, int index, List<CommonTreeNode> commonTreeNodeList) {
		DefinetableviewLookVO deTable;
		for(int length = list.size(); index < length; index++) {
			deTable = list.get(index);
			
			// begin 封装的树
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(String.valueOf(deTable.getTbSerialNo()));
			treeNode.setUpId("000000");
			treeNode.setText(deTable.getTbCnnm());
			treeNode.setData(deTable);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("open", "true");
			treeNode.setParams(paramMap);
			commonTreeNodeList.add(treeNode);
		}
	}
	
	/**
	 * 将查询到的定制列信息设置到树里
	 * @param list 定制列信息
	 * @param index 
	 * @param commonTreeNodeList 树
	 */
	public void repairColumn(List<DefinetableviewLookVO> columnList, DefinetableviewLookVO[] ctztcolumns, int index, List<CommonTreeNode> commonTreeNodeList, int tableslength) {
		DefinetableviewLookVO deColumn;
		String tableCountSql = " SELECT MAX(TB_SERIAL_NO) MAXNO FROM APP_SCV_TAB_DEF ";
		Integer tableCount = 0;
		@SuppressWarnings("rawtypes")
		List tableList = this.baseDAO.findByNativeSQLWithIndexParam(tableCountSql.toString());
		if(tableList != null && tableList.size() > 0){
			tableCount = new Integer((tableList.get(0).toString()));
		}
		for(int length = columnList.size(); index < length; index++) {
			deColumn = columnList.get(index);
			
			// begin 封装的树
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(String.valueOf(deColumn.getCumSerialNo()+tableCount));
			treeNode.setUpId(String.valueOf(deColumn.getTbSerialNo()));
			treeNode.setText(deColumn.getCumCnnm());
			treeNode.setData(deColumn);
			if(ctztcolumns != null && ctztcolumns.length > 0){
				for(DefinetableviewLookVO column : ctztcolumns){
					if(deColumn.getCumSerialNo() == column.getCumSerialNo()){
						treeNode.setIschecked(true);
					}
				}
			}
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("open", "false");
			treeNode.setParams(paramMap);
			commonTreeNodeList.add(treeNode);
		}
	}
	
	public void deleteOprCztzColumn(String oprno, String tbType){
		String jql="delete from Operatorctztcolumn occ where occ.id.oprNo=?0 AND occ.id.cstType=?1";
		this.baseDAO.batchExecuteWithIndexParam(jql, oprno,tbType);
	}
	
	
}
