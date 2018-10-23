package com.yuchengtech.emp.biappframe.authobj.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.authobj.service.DeptBS;
import com.yuchengtech.emp.biappframe.authobj.web.vo.BioneDeptInfoVO;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;

/**
 * <pre>
 * Title:部门处理Action类
 * Description: 实现部门视图中对应的增删改查功能，以及树形图展示。
 * </pre>
 * 
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/dept")
public class DeptController extends BaseController {

	@Autowired
	private DeptBS deptBS;

	// 是否允许新增，修改，删除；1-是，0-否

	/**
	 * 通过部门标识，查找对应的部门信息并组合成包含上级部门名称的对象
	 */
	@RequestMapping(value = "/showInfo.*", method = RequestMethod.GET)
	@ResponseBody
	public BioneDeptInfo showInfo(String id) {
		BioneDeptInfo model = this.deptBS.getEntityById(id);
		return model;
	}

	/**
	 * 构造树型列表数据
	 */
	@RequestMapping(value = "/findDeptInfoByOrg.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDeptInfoByOrg(Pager pager, String canEdit, String isBasicDept, String orgNo) {
		if (isBasicDept == null || "".equals(isBasicDept)) {
			isBasicDept = this.deptBS.isBasicDept();
		}
		if (canEdit == null || "".equals(canEdit)) {
			String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
			if (GlobalConstants.SUPER_LOGIC_SYSTEM.equals(logicSysNo)) {
				canEdit = "1";
			} else if (GlobalConstants.COMMON_STATUS_INVALID.equals(isBasicDept)) {
				canEdit = "1";
			} else {
				canEdit = "0";
			}
		}
		// 查询符合条件的记录
		List<BioneDeptInfo> deptInfoList = this.deptBS
				.findDeptInfoByOrg(orgNo, pager.getSearchCondition(), isBasicDept);
		// 查询机构下所有部门信息
		Map<String, BioneDeptInfo> deptInfoMap = this.deptBS.findAllDeptInfoWithOrg(orgNo, isBasicDept);

		List<BioneDeptInfo> midList = Lists.newArrayList();

		// 以查询到的记录为入口,以所有部门信息为主干 将查询到的部门信息与其所有上级部门全部放入midList中
		if (deptInfoList != null && deptInfoList.size() > 0) {
			for (BioneDeptInfo dept : deptInfoList) {
				BioneDeptInfo bdi = deptInfoMap.get(dept.getDeptNo() + "_" + dept.getOrgNo());
				if (bdi != null) {
					if (!midList.contains(bdi)) {
						midList.add(bdi);
					}
					generateResultList(deptInfoMap, midList, bdi);
				}
			}
		}

		List<BioneDeptInfoVO> resultList = Lists.newArrayList();
		// 以midList为入口，构造树型结构
		if (midList != null && midList.size() > 0) {

			for (BioneDeptInfo dept : midList) {
				BioneDeptInfoVO vo = new BioneDeptInfoVO();
				if ("0".equals(dept.getUpNo())) {
					vo.setDeptId(dept.getDeptId());
					vo.setDeptName(dept.getDeptName());
					vo.setDeptNo(dept.getDeptNo());
					vo.setDeptSts(dept.getDeptSts());
					vo.setLastUpdateTime(dept.getLastUpdateTime());
					vo.setLastUpdateUser(dept.getLastUpdateUser());
					vo.setLogicSysNo(dept.getLogicSysNo());
					vo.setOrgNo(dept.getOrgNo());
					vo.setRemark(dept.getRemark());
					vo.setUpNo(dept.getUpNo());

					generateVoList(midList, vo);
					resultList.add(vo);
				}
			}
		}
		Map<String, Object> deptMap = Maps.newHashMap();
		deptMap.put("Rows", resultList);
		return deptMap;
	}

	/**
	 * 通过bdi部门信息，将其与其上级部门信息均放入midlist中。
	 * 
	 * @param deptInfoMap
	 * @param midList
	 * @param bdi
	 */
	private void generateResultList(Map<String, BioneDeptInfo> deptInfoMap, List<BioneDeptInfo> midList,
			BioneDeptInfo bdi) {
		// UPNO的机构肯定与自己的机构相同
		BioneDeptInfo newDept = deptInfoMap.get(bdi.getUpNo() + "_" + bdi.getOrgNo());
		if (newDept != null) {
			if (midList.contains(newDept)) {
				return;
			}
			midList.add(newDept);
			generateResultList(deptInfoMap, midList, newDept);
		}
	}

	/**
	 * 从deptInfoList中查找到所有的与vo相关的子部门信息进行组装存入vo的children中.
	 * 
	 * @param deptInfoList
	 * @param vo
	 */
	private void generateVoList(List<BioneDeptInfo> deptInfoList, BioneDeptInfoVO vo) {
		for (BioneDeptInfo dept : deptInfoList) {
			if (dept.getUpNo().equals(vo.getDeptNo())) {
				BioneDeptInfoVO infoVO = new BioneDeptInfoVO();
				infoVO.setDeptId(dept.getDeptId());
				infoVO.setDeptName(dept.getDeptName());
				infoVO.setDeptNo(dept.getDeptNo());
				infoVO.setDeptSts(dept.getDeptSts());
				infoVO.setLastUpdateTime(dept.getLastUpdateTime());
				infoVO.setLastUpdateUser(dept.getLastUpdateUser());
				infoVO.setLogicSysNo(dept.getLogicSysNo());
				infoVO.setOrgNo(dept.getOrgNo());
				infoVO.setRemark(dept.getRemark());
				infoVO.setUpNo(dept.getUpNo());
				vo.getChildren().add(infoVO);
				generateVoList(deptInfoList, infoVO);
			}
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		//this.deptBS.removeEntityById(id);
		this.deptBS.removeEntityBatch(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(String isBasicDept) {
		String canEdit = "";
		if (isBasicDept == null || "".equals(isBasicDept)) {
			isBasicDept = this.deptBS.isBasicDept();
		}
		if (canEdit == null || "".equals(canEdit)) {
			String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
			if (GlobalConstants.SUPER_LOGIC_SYSTEM.equals(logicSysNo)) {
				canEdit = "1";
			} else if (GlobalConstants.COMMON_STATUS_INVALID.equals(isBasicDept)) {
				canEdit = "1";
			} else {
				canEdit = "0";
			}
		}
		return new ModelAndView("/bione/dept/dept-index", "canEdit", canEdit);
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String orgId, String deptId) {
		ModelMap mm = new ModelMap();
		mm.put("orgId", orgId);
		mm.put("deptId", deptId);
		return new ModelAndView("/bione/dept/dept-editNew", mm);
	}

	/**
	 * 跳转 修改 页面
	 * @return
	 */
	@RequestMapping(value = "/{deptId}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("deptId") String deptId, String flag) {
		ModelMap map = new ModelMap();
		map.put("flag", flag);
		map.put("id", deptId);
		return new ModelAndView("/bione/dept/dept-edit", map);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneDeptInfo model) {
		model.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		model.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
		model.setLastUpdateTime(new Timestamp(new Date().getTime()));
		if (model.getDeptId() != null && !"".equals(model.getDeptId())) {
			this.deptBS.updateEntity(model);
		} else {
			model.setDeptId(RandomUtils.uuid2());// 主键为空字符串也会认为是有主键
			this.deptBS.saveEntity(model);
		}
	}

	// 构造部门树
	@RequestMapping("/buildDeptTree.*")
	@ResponseBody
	public List<CommonTreeNode> buildDeptTree(String orgNo) {
		return this.deptBS.buldDeptTree(orgNo);
	}

	@RequestMapping(value = "/testDeptNo")
	@ResponseBody
	public boolean testDeptNo(String orgNo, String deptNo) {
		BioneDeptInfo dept = this.deptBS.findDeptInfoByOrgNoandDeptNo(orgNo, deptNo);
		boolean flag = true;
		if (dept != null) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/getRealCode")
	@ResponseBody
	public Map<String, String> getRealCode(String orgId, String deptId) {
		String orgIdTmp = orgId;
		String deptIdTmp = deptId;
		String realOrgCode = "";
		String realDeptCode = "";
		Map<String, String> returnMap = new HashMap<String, String>();
		if (orgIdTmp != null && !"".equals(orgIdTmp)) {
			BioneOrgInfo orgTmp = this.deptBS.getEntityById(BioneOrgInfo.class, orgIdTmp);
			if (orgTmp != null) {
				realOrgCode = orgTmp.getOrgNo();
			}
		}
		if (deptIdTmp != null && !"".equals(deptIdTmp)) {
			BioneDeptInfo deptTmp = this.deptBS.getEntityById(BioneDeptInfo.class, deptIdTmp);
			if (deptTmp != null) {
				realDeptCode = deptTmp.getDeptNo();
			}
		}
		if ("".equals(realDeptCode) || realDeptCode == null) {
			realDeptCode = CommonTreeNode.ROOT_ID;
		}
		returnMap.put("orgNo", realOrgCode);
		returnMap.put("deptNo", realDeptCode);
		return returnMap;
	}

}
