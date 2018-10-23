package com.yuchengtech.emp.biappframe.variable.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamTypeInfo;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

/**
 * <pre>
 * Title:参数类型的业务逻辑类
 * Description: 提供参数类型管理相关业务逻辑处理功能，提供事务控制
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
@Service
@Transactional(readOnly = true)
public class ParamTypeBS extends BaseBS<BioneParamTypeInfo> {

	protected static Logger log = LoggerFactory.getLogger(ParamTypeBS.class);
	List<BioneParamTypeInfo> resultList = null;

	/**
	 * 根据参数类型标识查询参数类型信息
	 * 
	 * @param paramTypeNo
	 * @return
	 */
	public BioneParamTypeInfo findParamTypeInfoByNo(String paramTypeNo) {
		String jql = "SELECT paramType FROM BioneParamTypeInfo paramType where paramType.paramTypeNo=?0 AND paramType.logicSysNo=?1";
		BioneParamTypeInfo paramType = this.baseDAO.findUniqueWithIndexParam(
				jql, paramTypeNo, BiOneSecurityUtils.getCurrentUserInfo()
						.getCurrentLogicSysNo());
		return paramType;
	}

	/**
	 * 验证参数类型标识是否存在
	 * 
	 * @param paramTypeNo
	 * @return
	 */
	public boolean checkIsParamTypeExist(String paramTypeNo) {
		boolean flag = false;
		String jql = "SELECT paramType FROM BioneParamTypeInfo paramType where paramType.paramTypeNo=?0 AND paramType.logicSysNo=?1";
		List<BioneParamTypeInfo> paramTypeList = this.baseDAO
				.findWithIndexParam(jql, paramTypeNo, BiOneSecurityUtils
						.getCurrentUserInfo().getCurrentLogicSysNo());
		if (paramTypeList != null && paramTypeList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除当前参数类型包含所有子类型及相关参数值
	 * 
	 * @param paramTypeNo
	 * @return
	 */
	public void delParamTypeListByNo(String paramTypeNo) {

		List<BioneParamTypeInfo> list = findParamTypeListByNo(paramTypeNo);
		if (list != null) {
			for (BioneParamTypeInfo paramType : list) {
				String curparamTypeNo = paramType.getParamTypeNo();
				// 删除参数类型和关联参数值
				String jql = "DELETE FROM BioneParamInfo param where param.paramTypeNo=?0 AND param.logicSysNo=?1";
				this.baseDAO.batchExecuteWithIndexParam(jql, curparamTypeNo,
						BiOneSecurityUtils.getCurrentUserInfo()
								.getCurrentLogicSysNo());
				String jql2 = "DELETE FROM BioneParamTypeInfo paramType where paramType.paramTypeNo=?0 AND paramType.logicSysNo=?1";
				this.baseDAO.batchExecuteWithIndexParam(jql2, curparamTypeNo,
						BiOneSecurityUtils.getCurrentUserInfo()
								.getCurrentLogicSysNo());
			}
		}
		// 删除参数类型和关联参数值
		String jql = "DELETE FROM BioneParamInfo param where param.paramTypeNo=?0 AND param.logicSysNo=?1";
		this.baseDAO.batchExecuteWithIndexParam(jql, paramTypeNo,
				BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		String jql2 = "DELETE FROM BioneParamTypeInfo paramType where paramType.paramTypeNo=?0 AND paramType.logicSysNo=?1";
		this.baseDAO.batchExecuteWithIndexParam(jql2, paramTypeNo,
				BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
	}

	/**
	 * 递归查询参数类型下所有子类型
	 * 
	 * @param paramTypeNo
	 */
	private List<BioneParamTypeInfo> findParamTypeListByNo(String paramTypeNo) {
		String jql = "SELECT paramType FROM BioneParamTypeInfo paramType where paramType.upNo=?0 AND paramType.logicSysNo=?1";
		List<BioneParamTypeInfo> list = this.baseDAO.findWithIndexParam(jql,
				paramTypeNo, BiOneSecurityUtils.getCurrentUserInfo()
						.getCurrentLogicSysNo());
		for (BioneParamTypeInfo paramType : list) {
			if (resultList == null) {
				resultList = new ArrayList<BioneParamTypeInfo>();
			}
			resultList.add(paramType);
			findParamTypeListByNo(paramType.getParamTypeNo());
		}
		return resultList;
	}

	/**
	 * 生成类型树
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<CommonTreeNode> buildParamTypeTree(String nodeId) {
		List<CommonTreeNode> treeList = new ArrayList<CommonTreeNode>();
		if (nodeId == null || "".equals(nodeId)) {
			nodeId = "0";
		}
		String jql = "SELECT paramType FROM BioneParamTypeInfo paramType where paramType.upNo=?0 AND paramType.logicSysNo=?1";
		List<BioneParamTypeInfo> list = this.baseDAO.findWithIndexParam(jql,
				nodeId, BiOneSecurityUtils.getCurrentUserInfo()
						.getCurrentLogicSysNo());
		for (BioneParamTypeInfo paramType : list) {
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(paramType.getParamTypeNo());
			treeNode.setUpId(paramType.getUpNo());
			treeNode.setText(paramType.getParamTypeName());
			treeNode.setData(paramType);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("isParent", isHadChild(paramType.getParamTypeNo()));
			treeNode.setParams(paramMap);
			treeList.add(treeNode);
		}

		return treeList;
	}

	/**
	 * 是否有子节点
	 * @param nodeId
	 * @return
	 */
	public String isHadChild(String nodeId){
		String jql = "SELECT paramType FROM BioneParamTypeInfo paramType where paramType.upNo=?0 AND paramType.logicSysNo=?1";
		List<BioneParamTypeInfo> list = this.baseDAO.findWithIndexParam(jql,
				nodeId, BiOneSecurityUtils.getCurrentUserInfo()
						.getCurrentLogicSysNo());
		if(list.size()>0){
			return "true";
		}else{
			return "false";
		}
		
	}
	/**
	 * 通过逻辑系统标识获得参数列
	 * 
	 * @param logicSysNo
	 *            逻辑系统编号
	 * @return paramTypeList 参数类型列表
	 */
	public List<BioneParamTypeInfo> findParamTypeList(String logicSysNo) {
		List<BioneParamTypeInfo> paramTypeList = null;
		String jql = "SELECT paramType FROM BioneParamTypeInfo paramType where  paramType.logicSysNo=?0";
		paramTypeList = this.baseDAO.findWithIndexParam(jql, logicSysNo);
		return paramTypeList;
	}
}
