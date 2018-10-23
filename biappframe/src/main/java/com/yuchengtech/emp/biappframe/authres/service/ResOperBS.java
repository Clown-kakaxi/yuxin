package com.yuchengtech.emp.biappframe.authres.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRel;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.biappframe.authres.web.vo.BioneResOperInfoVO;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

/**
 * 
 * 
 * <pre>
 * Title:CRUD操作演示
 * Description: 对资源操作信息表的CURD
 * </pre>
 * 
 * @author xingyw xingyw@gmail.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class ResOperBS extends BaseBS<BioneResOperInfo> {
	protected static Logger log = LoggerFactory.getLogger(ResOperBS.class);

	/**
	 * 查询资源操作信息表
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<BioneResOperInfoVO> findResOperInfoByResNoAndResDefNo(
			String resDefNo, String resNo, Map<String, Object> conditionMap) {
		// 构造树形结构
		List<BioneResOperInfoVO> resOperVOList = new ArrayList<BioneResOperInfoVO>();
		String jql = "select resOper from BioneResOperInfo resOper where 1=1";
		if (!conditionMap.get("jql").equals("")) {
			if (!conditionMap.get("jql").toString().contains("resNo")) {
				return resOperVOList;
			}
			jql += " and " + conditionMap.get("jql") + " ";
		} else {
			if (resNo == null || resDefNo == null) {
				return resOperVOList;
			}
			jql += " and resOper.resDefNo='" + resDefNo
					+ "' and resOper.resNo='" + resNo + "'";
		}
		@SuppressWarnings("unchecked")
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		List<BioneResOperInfo> resOperList = this.baseDAO.findWithNameParm(jql,
				values);
		// 根据搜索条件构建树
		if (!conditionMap.get("jql").equals("")) {
			// 迭代找到所有父节点，直到根节点
			List<BioneResOperInfoVO> resOperVOAllList = this
					.getToorList(resOperList);
			// 找到集合中的根节点迭代构建树
			if (resOperVOAllList != null && resOperVOAllList.size() > 0) {
				for (BioneResOperInfoVO resOperVO : resOperVOAllList) {
					if ("0".equals(resOperVO.getUpNo())) {
						try {
							this.generateVoList(resOperVOAllList, resOperVO);
						} catch (Exception e) {
							log.warn("属性复制发生异常");
						}
						resOperVOList.add(resOperVO);
					}
				}
			}
			// 根据点击右侧树构建树
		} else {
			if (resOperList != null && resOperList.size() > 0) {
				List<BioneResOperInfoVO> resOperVOAllList = new ArrayList<BioneResOperInfoVO>();
				for (BioneResOperInfo resOper : resOperList) {
					BioneResOperInfoVO resOperVO = new BioneResOperInfoVO();
					try {
						BeanUtils.copyProperties(resOperVO, resOper);
					} catch (Exception e) {
						log.warn("属性复制发生异常");
					}
					resOperVOAllList.add(resOperVO);
				}
				// 找到集合中的根节点迭代构建树
				if (resOperVOAllList != null && resOperVOAllList.size() > 0) {
					for (BioneResOperInfoVO resOperVO : resOperVOAllList) {
						if ("0".equals(resOperVO.getUpNo())) {
							this.generateVoList(resOperVOAllList, resOperVO);
							resOperVOList.add(resOperVO);
						}
					}
				}
			}
		}
		return resOperVOList;
	}

	/**
	 * 迭代得到叶子节点
	 * 
	 * @param resOperList
	 * @param resOperVO
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void generateVoList(List<BioneResOperInfoVO> resOperVOList,
			BioneResOperInfoVO resOperVO) {
		for (BioneResOperInfoVO resOper : resOperVOList) {
			if (resOper.getUpNo().equals(resOperVO.getOperId())) {
				resOperVO.getChildren().add(resOper);
				this.generateVoList(resOperVOList, resOper);
			}
		}
	}

	/**
	 * 将查询结果转成VO，并放入resOperVOAllList中
	 * 
	 * @param resOperList
	 * @param resOperVOAllList
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<BioneResOperInfoVO> getToorList(
			List<BioneResOperInfo> resOperList) {
		List<BioneResOperInfoVO> resOperVOList = entityToVO(resOperList);
		if (resOperVOList != null && resOperVOList.size() > 0) {
			this.getFatherList(resOperVOList);
		}
		return resOperVOList;
	}

	/**
	 * 迭代找到所有父节点，直到根节点
	 * 
	 * @param resOperVOList
	 * @param resOperVOAllList
	 */
	public void getFatherList(List<BioneResOperInfoVO> resOperVOList) {
		if (resOperVOList == null || resOperVOList.size() == 0)
			return;
		Map<String, BioneResOperInfoVO> map = new HashMap<String, BioneResOperInfoVO>();
		for (int i = 0; i < resOperVOList.size(); i++) {
			map.put(resOperVOList.get(i).getOperId(), resOperVOList.get(i));
		}
		List<String> upNoList = new ArrayList<String>();
		String upNo = null;
		for (String operId : map.keySet()) {
			upNo = map.get(operId).getUpNo();
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
			String jql = "select resOper from BioneResOperInfo resOper where resOper.operId in (?0)";
			List<BioneResOperInfo> resOperFatherList = this.baseDAO
					.findWithIndexParam(jql, upNoList);
			if (resOperFatherList != null && resOperFatherList.size() > 0) {
				List<BioneResOperInfoVO> resOperFatherVOList = entityToVO(resOperFatherList);
				resOperVOList.addAll(resOperFatherVOList);
				this.getFatherList(resOperVOList);
			}
		}
	}

	/**
	 * 实体变为VO
	 * 
	 * @param resOperList
	 * @return
	 */
	public List<BioneResOperInfoVO> entityToVO(
			List<BioneResOperInfo> resOperList) {
		if (resOperList == null || resOperList.size() == 0)
			return null;
		List<BioneResOperInfoVO> resOperVOList = new ArrayList<BioneResOperInfoVO>();
		for (BioneResOperInfo resOper : resOperList) {
			BioneResOperInfoVO resOperVO = new BioneResOperInfoVO();
			try {
				BeanUtils.copyProperties(resOperVO, resOper);
			} catch (Exception e) {
				log.warn("属性复制发生异常");
			}
			resOperVOList.add(resOperVO);
		}
		return resOperVOList;
	}

	/**
	 * 填充下拉框
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param operId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CommonTreeNode> findResOperTree(String resDefNo, String resNo,
			String operId) {
		// 树信息集合
		List<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();

		String jql = "select resOper from BioneResOperInfo resOper where resOper.resDefNo=?0 and resOper.resNo=?1";
		List<BioneResOperInfo> resOperList = this.baseDAO.findWithIndexParam(
				jql, resDefNo, resNo);
		// 循环结果集放入树
		for (BioneResOperInfo resOperTemp : resOperList) {
			CommonTreeNode treeNode = new CommonTreeNode();
			treeNode.setId(resOperTemp.getOperId());
			treeNode.setText(resOperTemp.getOperNo());
			treeNode.setUpId(resOperTemp.getUpNo());
			Map params = new HashMap();
			params.put("realId", resOperTemp.getOperId());
			treeNode.setParams(params);
			treeNodes.add(treeNode);
		}
		if (operId != null && (!"".equals(operId))) {
			// 修改时过滤当前节点与其子节点
			Map<String, CommonTreeNode> map = new HashMap<String, CommonTreeNode>();
			for (CommonTreeNode node : treeNodes) {
				map.put(node.getId(), node);
			}
			List<BioneResOperInfo> sonList = this.findResOper(resDefNo, resNo,
					operId);
			for (BioneResOperInfo oper : sonList) {
				if (map.keySet().contains(oper.getOperId()))
					map.remove(oper.getOperId());
			}
			treeNodes.clear();
			for (String key : map.keySet()) {
				treeNodes.add(map.get(key));
			}
		}
		CommonTreeNode treeNodeRoot = new CommonTreeNode();
		treeNodeRoot.setId("根目录");
		treeNodeRoot.setText("根目录");
		treeNodeRoot.setUpId("0");
		treeNodes.add(treeNodeRoot);

		return treeNodes;
	}

	/**
	 * 修改时得到当前节点
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param operId
	 * @return
	 */
	public List<BioneResOperInfo> findResOper(String resDefNo, String resNo,
			String operId) {
		String jql = "select resOper from BioneResOperInfo resOper where resOper.resDefNo=?0 and resOper.resNo=?1 and resOper.operId=?2";
		List<BioneResOperInfo> parentResOper = this.baseDAO.findWithIndexParam(
				jql, resDefNo, resNo, operId);
		List<BioneResOperInfo> sonList = null;
		if (parentResOper != null && parentResOper.size() > 0) {
			sonList = new ArrayList<BioneResOperInfo>();
			this.findChildrenResOper(resDefNo, resNo, parentResOper.get(0),
					sonList);
		}
		return sonList;
	}

	/**
	 * 迭代得到叶子节点
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param parentResOper
	 * @param sonList
	 * @return
	 */
	public List<BioneResOperInfo> findChildrenResOper(String resDefNo,
			String resNo, BioneResOperInfo parentResOper,
			List<BioneResOperInfo> sonList) {

		sonList.add(parentResOper);
		String jql = "select resOper from BioneResOperInfo resOper where resOper.resDefNo=?0 and resOper.resNo=?1 and resOper.upNo=?2";
		List<BioneResOperInfo> childList = this.baseDAO.findWithIndexParam(jql,
				resDefNo, resNo, parentResOper.getOperId());
		if (childList != null && childList.size() > 0) {
			for (int i = 0; i < childList.size(); i++) {
				this.findChildrenResOper(resDefNo, resNo, childList.get(i),
						sonList);
			}
		}
		return sonList;
	}

	/**
	 * 查询父节点operNO
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param upNo
	 * @return
	 */
	public String findUpOperNo(String resDefNo, String resNo, String upNo) {
		String upOperNo = "";
		String jql = "select resOper from BioneResOperInfo resOper where resOper.resDefNo=?0 and resOper.resNo=?1 and resOper.operId=?2";
		List<BioneResOperInfo> resOperList = this.baseDAO.findWithIndexParam(
				jql, resDefNo, resNo, upNo);
		if (resOperList != null && resOperList.size() > 0) {
			upOperNo = resOperList.get(0).getOperNo();
		} else {
			upOperNo = "跟目录";
		}
		return upOperNo;
	}

	/**
	 * 级联删除
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param operId
	 * @return
	 */
	public String delResOperByParams(String resDefNo, String resNo,
			String operId) {
		String operNo = this.checkResOperAuth(resDefNo, resNo, operId);
		if (operNo != null && !"".equals(operNo)) {
			return operNo;
		} else {// 删除未被授权的资源操作
			this.generateUpNo(resDefNo, resNo, operId);
			this.deleResoperById(operId);
			return operNo;
		}
	}

	/**
	 * 迭代删除子节点
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param operId
	 */
	public void generateUpNo(String resDefNo, String resNo, String operId) {
		String jql = "select resOper from BioneResOperInfo resOper where resOper.resDefNo=?0 and resOper.resNo=?1 and resOper.upNo=?2";
		List<BioneResOperInfo> resOperList = this.baseDAO.findWithIndexParam(
				jql, resDefNo, resNo, operId);
		if (resOperList != null && resOperList.size() > 0) {
			for (int i = 0; i < resOperList.size(); i++) {
				this.deleResoperById(resOperList.get(i).getOperId());
				this.generateUpNo(resDefNo, resNo, resOperList.get(i)
						.getOperId());
			}
		}
	}

	/**
	 * 删除单个节点
	 * 
	 * @param operId
	 */
	public void deleResoperById(String operId) {
		String jql = "delete from BioneResOperInfo resOper where resOper.operId=?0";
		this.baseDAO.batchExecuteWithIndexParam(jql, operId);
	}

	/**
	 * 查看要删除的资源操作及其子节点是否有被授权的，被授权返回值不为空
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param operId
	 * @return
	 */
	public String checkResOperAuth(String resDefNo, String resNo, String operId) {
		String operNo = null;
		String jqll = "select authObjResRel from BioneAuthObjResRel authObjResRel where authObjResRel.id.resDefNo=?0 and authObjResRel.id.resId=?1 and authObjResRel.id.permissionId=?2";
		List<BioneAuthObjResRel> authObjResRelList = this.baseDAO
				.findWithIndexParam(jqll, resDefNo, resNo, operId);
		if (authObjResRelList != null && authObjResRelList.size() > 0) {
			operNo = this.findExistOperNoById(authObjResRelList.get(0).getId()
					.getPermissionId());
			return operNo;
		} else {
			String jql = "select resOper from BioneResOperInfo resOper where resOper.resDefNo=?0 and resOper.resNo=?1 and resOper.upNo=?2";
			List<BioneResOperInfo> resOperList = this.baseDAO
					.findWithIndexParam(jql, resDefNo, resNo, operId);
			if (resOperList != null && resOperList.size() > 0) {
				for (int i = 0; i < resOperList.size(); i++) {
					operNo = this.checkResOperAuth(resDefNo, resNo, resOperList
							.get(i).getOperId());
					if (operNo != null && !"".equals(operNo)) {
						return operNo;
					}
				}
			}
		}
		return operNo;
	}

	/**
	 * 查到要删除的节点已被授权时，返回该节点operNo
	 * 
	 * @param operId
	 * @return
	 */
	public String findExistOperNoById(String operId) {
		String jql = "select resOper from BioneResOperInfo resOper where resOper.operId=?0";
		List<BioneResOperInfo> resOperList = this.baseDAO.findWithIndexParam(
				jql, operId);
		return resOperList.get(0).getOperNo();
	}

	/**
	 * 非重复验证
	 * 
	 * @param resDefNo
	 * @param resNo
	 * @param operNo
	 * @param currentOperNo
	 * @param urlType
	 * @return
	 */
	public boolean checkedOperNo(String operNo,
			String currentOperNo, String urlType) {
		String jql = "select resOper from BioneResOperInfo resOper where resOper.operNo=?0";
		List<BioneResOperInfo> resOperList = this.baseDAO.findWithIndexParam(
				jql, operNo);
		if ("add".equals(urlType)) {
			if (resOperList != null && resOperList.size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			if (resOperList != null && resOperList.size() > 0
					&& !resOperList.get(0).getOperNo().equals(currentOperNo)) {
				return false;
			} else {
				return true;
			}
		}
	}
}
