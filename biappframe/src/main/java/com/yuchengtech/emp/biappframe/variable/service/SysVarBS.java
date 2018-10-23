/**
 * 
 */
package com.yuchengtech.emp.biappframe.variable.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDsInfo;
import com.yuchengtech.emp.biappframe.variable.entity.BioneSysVarInfo;
import com.yuchengtech.emp.biappframe.variable.web.vo.BioneSysVarInfoVO;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title:CRUD操作
 * Description: 完成系统变量的CRUD操作
 * </pre>
 * 
 * @author xiaofeng xiaofeng.88@hotmail.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：肖丰	  修改日期:     修改内容:
 * </pre>
 */

@Service
@Transactional(readOnly = true)
public class SysVarBS extends BaseBS<BioneSysVarInfo> {

	protected static Logger log = LoggerFactory.getLogger(SysVarBS.class);

	@SuppressWarnings("unchecked")
	public SearchResult<BioneSysVarInfo> getSysVarList(String logicSysNo,
			int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select sysVar from BioneSysVarInfo sysVar where 1=1 and sysVar.logicSysNo = :logicSysNo ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by sysVar." + orderBy + " " + orderType);
		}
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		values.put("logicSysNo", logicSysNo);
		SearchResult<BioneSysVarInfo> sysvarList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);
		List<BioneSysVarInfo> sysvarInfo = sysvarList.getResult();
		for (int i = 0; i < sysvarInfo.size(); i++) {
			if (GlobalConstants.BIONE_SYS_VAR_TYPE_CONSTANT.equals(sysvarInfo
					.get(i).getVarType())) {
				sysvarInfo.get(i).setVarType("常量");
			}
			if (GlobalConstants.BIONE_SYS_VAR_TYPE_SQL.equals(sysvarInfo.get(i)
					.getVarType())) {
				sysvarInfo.get(i).setVarType("SQL语句");
			}
		}
		return sysvarList;
	}

	/**
	 * 根据id得到系统信息变量表信息
	 * 
	 * @param varId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public BioneSysVarInfoVO getSysVarInfoById(String varId)
			throws IllegalAccessException, InvocationTargetException {
		String jql = "select sysVar from BioneSysVarInfo sysVar where sysVar.varId = ?0";
		List<BioneSysVarInfo> varList = this.baseDAO.findWithIndexParam(jql,
				varId);
		List<BioneSysVarInfoVO> varInfoVOList = new ArrayList<BioneSysVarInfoVO>();
		for (BioneSysVarInfo varTemp : varList) {
			BioneSysVarInfoVO varVO = new BioneSysVarInfoVO();
			BeanUtils.copyProperties(varVO, varTemp);
			varInfoVOList.add(varVO);
		}
		BioneSysVarInfoVO varVO = new BioneSysVarInfoVO();
		if (varInfoVOList != null && varInfoVOList.size() > 0) {
			varVO = varInfoVOList.get(0);
		}
		if (GlobalConstants.BIONE_SYS_VAR_TYPE_SQL.equals(varVO.getVarType())) {
			BioneDsInfo dsInfo = getDsInfoById(varVO.getDsId());
			if (dsInfo != null) {
				varVO.setDsName(dsInfo.getDsName());
			}
		}
		return varVO;
	}

	/**
	 * 根据id得到数据源信息表信息
	 * 
	 * @param dsId
	 * @return
	 */
	public BioneDsInfo getDsInfoById(String dsId) {
		String jql = "select ds from BioneDsInfo ds where ds.dsId = ?0";
		List<BioneDsInfo> varList = this.baseDAO.findWithIndexParam(jql, dsId);
		BioneDsInfo dsInfo = new BioneDsInfo();
		if (varList != null && varList.size() > 0) {
			dsInfo = varList.get(0);
		}
		return dsInfo;
	}

	/**
	 * 得到数据源表全部信息
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneDsInfo> getAllDsInfo(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select ds from BioneDsInfo ds where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by ds." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneDsInfo> dsList = this.baseDAO.findPageWithNameParam(
				firstResult, pageSize, jql.toString(), values);
		return dsList;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            记录ID号
	 */
	@Transactional(readOnly = false)
	public void deleteBatch(String[] ids) {
		for (String id : ids) {
			removeEntityById(id);
		}
	}

	/**
	 * 获取系统变量信息
	 * 
	 * @return 按逻辑系统标识分类的系统变量信息
	 */
	public Map<String, Map<String, String>> findSysVar() {

		StringBuffer jql = new StringBuffer(
				"select sysVar from BioneSysVarInfo sysVar where 1=1");

		List<BioneSysVarInfo> sysVarList = this.baseDAO.findWithNameParm(
				jql.toString(), null);

		// 按逻辑系统标识进行分类
		Map<String, Map<String, String>> sysVarMaps = Maps.newHashMap();

		for (BioneSysVarInfo sysVar : sysVarList) {

			Map<String, String> sysVarMap = sysVarMaps.get(sysVar
					.getLogicSysNo());

			if (MapUtils.isEmpty(sysVarMap)) {
				sysVarMap = Maps.newHashMap();
				sysVarMaps.put(sysVar.getLogicSysNo(), sysVarMap);
			}

			sysVarMap.put(sysVar.getVarNo(), sysVar.getVarValue());

		}

		return sysVarMaps;
	}
	
	public boolean checkVarNo(String varNo,String logicSysNo){
		String jql = "select sysVar from BioneSysVarInfo sysVar where sysVar.varNo = ?0 and sysVar.logicSysNo = ?1";
		List<BioneSysVarInfo> sysVarList = this.baseDAO.findWithIndexParam(jql, varNo,logicSysNo);
		if(sysVarList != null&&sysVarList.size() > 0)
			return false;
		return true;
	}

}
