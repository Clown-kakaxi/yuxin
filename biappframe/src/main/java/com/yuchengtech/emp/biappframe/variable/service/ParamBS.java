package com.yuchengtech.emp.biappframe.variable.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamInfo;
import com.yuchengtech.emp.biappframe.variable.web.vo.BioneParamInfoVO;

/**
 * <pre>
 * Title:参数的业务逻辑类
 * Description: 提供参数管理相关业务逻辑处理功能，提供事务控制
 * </pre>
 * 
 * @author yangyuhui yangyh4@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("paramBS")
@Transactional(readOnly = true)
public class ParamBS extends BaseBS<BioneParamInfo> {
	/**
	 * 根据条件获取参数信息
	 * @param firstResult
	 * 		第一条记录
	 * @param pageSize
	 * 		每页记录数
	 * @param orderBy
	 * 		排序字段
	 * @param orderType
	 * 		排序方式
	 * @param conditionMap
	 * 		查询参数列表
	 * @param paramTypeNo
	 * 		参数类型标识
	 * @return
	 */
	public List<BioneParamInfo> getParamList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String paramTypeNo) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select param from BioneParamInfo param where 1 = 1");
		if (paramTypeNo != null||"".equals(paramTypeNo)){
			jql.append(" and " + "param.paramTypeNo='" + paramTypeNo + "'");
		}
		jql.append(" and " + "param.logicSysNo='" + BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo() + "'");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by param." + orderBy + " " + orderType);
		}
		Map<String, Object> values=Maps.newHashMap();
		List<BioneParamInfo> paramList = this.baseDAO.findWithNameParm(jql.toString(), values);
		
		/*Map<String, BioneParamInfo> resultMap = Maps.newHashMap();
		for (BioneParamInfo param : paramList) {
			resultMap.put(param.getParamId()+ "_" +param.getParamTypeNo(), param);
		}*/
		return paramList;
	}
	
	/**
	 * 通过参数类型标识查询参数
	 * @param paramTypeNo
	 * @return
	 */
	public List<BioneParamInfo> findParamByParamTypeNo(String paramTypeNo){
		String jql = "select param from BioneParamInfo param where param.paramTypeNo=?0 and param.logicSysNo=?1";
		return this.baseDAO.findWithIndexParam(jql, paramTypeNo, BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
	}
	
	/**
	 * 通过逻辑系统标识和参数类型标识获得参数list
	 * @param logicSysNo   逻辑系统编号
	 * @param paramTypeNo  参数类型标识
	 * @return paramTypeList  参数类型列表
	 */
	public List<BioneParamInfo> findParamBySysAndType(String logicSysNo, String paramTypeNo) {
		List<BioneParamInfo> paramList = null;
		String jql = "SELECT param FROM BioneParamInfo param where param.logicSysNo=?0 and param.paramTypeNo=?1";
		paramList = this.baseDAO.findWithIndexParam(jql, logicSysNo, paramTypeNo);
		return paramList;
	}


	
	
	/**
	 * 查询逻辑系统下所有顶层参数
	 * @param logicSysNo   逻辑系统编号
	 * @param paramTypeNo  参数类型标识
	 * @return paramTypeList  参数类型列表
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchParamsAsTree(Map<String, Object> conditionMap,String logicSysNo,String paramTypeNo) {
		StringBuffer jql = new StringBuffer("select param from BioneParamInfo param where param.logicSysNo='"+logicSysNo+"'");
		if(paramTypeNo!=null&&!"".equals(paramTypeNo)){
			jql.append(" and param.paramTypeNo='"+paramTypeNo+"'" + conditionMap.get("jql"));
		}
		List<BioneParamInfo> params = null;
		if (!conditionMap.get("jql").equals("")) {//有查询条件时，从搜索结果向根节点查询路径上所有节点
			jql.append(" and " + conditionMap.get("jql"));
			Map<String, Object> values = (Map<String, Object>) conditionMap
					.get("params");
			params = this.baseDAO.findWithNameParm(jql.toString(), values);
			this.getParamsToRoot(params);
		}else{//无查询条件时搜索所有节点
			params = this.baseDAO.findWithIndexParam(jql.toString());
		}
		return this.buildTree(params);
	}
	
	/**
	 * 根据upNo构建树形结构
	 * @param params	待构建节点集合
	 * @return
	 */
	private Map<String,Object> buildTree(List<BioneParamInfo> params){
		int count = params.size();
		if(params==null||params.size()==0)
			return null;
		//筛选根节点
		List<BioneParamInfo> roots = new ArrayList<BioneParamInfo>();
		List<BioneParamInfo> noRoots = new ArrayList<BioneParamInfo>();
		for (int i = 0; i < params.size(); i++) {
			BioneParamInfo param = params.get(i);
			if("0".equals(param.getUpNo())||param.getUpNo()==null){
				roots.add(param);
			}else{
				noRoots.add(param);
			}
		}
		//迭代构建
		List<BioneParamInfoVO> vos = Lists.newArrayList();
		for (int i = 0; i < roots.size(); i++) {
			BioneParamInfo root = roots.get(i);
			BioneParamInfoVO vo = new BioneParamInfoVO();
			vo.setParamId(root.getParamId());
			vo.setLogicSysNo(root.getLogicSysNo());
			vo.setOrderNo(root.getOrderNo());
			vo.setParamName(root.getParamName());
			vo.setParamTypeNo(root.getParamTypeNo());
			vo.setParamValue(root.getParamValue());
			vo.setRemark(root.getRemark());
			vo.setUpNo(root.getUpNo());
			generateVoList(noRoots, vo);
			vos.add(vo);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("Rows", vos);
		map.put("Total", count);
		return map;
	}
	
	
	/**
	 * 查询逻辑系统下所有参数
	 * @param logicSysNo   逻辑系统编号
	 * @param paramTypeNo  参数类型标识
	 * @return paramTypeList  参数类型列表
	 */
	public List<BioneParamInfo> findAllParams(String logicSysNo) {
		String jql = "select param from BioneParamInfo param where param.logicSysNo=?0 and param.upNo!='0'";
		return this.baseDAO.findWithIndexParam(jql, logicSysNo);
	}

	/**
	 * 迭代找到所有父节点，直到根节点
	 * 
	 * @param resOperVOList
	 */
	public void getParamsToRoot(List<BioneParamInfo> paramList) {
		if (paramList == null || paramList.size() == 0)
			return;
		
		Map<String, BioneParamInfo> map = new HashMap<String, BioneParamInfo>();
		for (int i = 0; i < paramList.size(); i++) {
			map.put(paramList.get(i).getParamId(), paramList.get(i));
		}
		List<String> upNoList = new ArrayList<String>();
		String upNo = null;
		for (String paramId : map.keySet()) {
			upNo = map.get(paramId).getUpNo();
			while (!"0".equals(upNo)) {
				if (map.get(upNo) == null) {
					upNoList.add(upNo);
					break;
				} else {
					upNo = map.get(upNo).getUpNo();
				}
			}
		}
		if (upNoList != null && upNoList.size() > 0) {
			String jql = "select p from BioneParamInfo p where p.logicSysNo=?0 and p.paramId in (?1)";
			List<BioneParamInfo> fatherList = this.baseDAO
					.findWithIndexParam(jql,BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo() ,upNoList);
			if (fatherList != null && fatherList.size() > 0) {
				paramList.addAll(fatherList);
				this.getParamsToRoot(fatherList);
			}
		}
	}
	
	private void generateVoList(List<BioneParamInfo> paramInfoList,
			BioneParamInfoVO vo) {
		for (BioneParamInfo param : paramInfoList) {
			if (param.getUpNo().equals(vo.getParamId())) {
				BioneParamInfoVO infoVO = new BioneParamInfoVO();
				infoVO.setParamId(param.getParamId());
				infoVO.setLogicSysNo(param.getLogicSysNo());
				infoVO.setOrderNo(param.getOrderNo());
				infoVO.setParamName(param.getParamName());
				infoVO.setParamTypeNo(param.getParamTypeNo());
				infoVO.setParamValue(param.getParamValue());
				infoVO.setRemark(param.getRemark());
				infoVO.setUpNo(param.getUpNo());
				vo.getChildren().add(infoVO);
				generateVoList(paramInfoList, infoVO);
			}
		}
	}
//删除参数
	@Transactional(readOnly=false)
	public void removeEntityBatch(String ids){
		if(ids.endsWith(",")){
			ids=ids.substring(0,ids.length()-1);
		}
		String[] idArray=ids.split(",");
		for(String id:idArray){
			this.removeEntityById(id);
		}
	}
}
