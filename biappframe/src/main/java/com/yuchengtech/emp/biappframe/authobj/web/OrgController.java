package com.yuchengtech.emp.biappframe.authobj.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneDeptInfo;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneOrgInfo;
import com.yuchengtech.emp.biappframe.authobj.service.DeptBS;
import com.yuchengtech.emp.biappframe.authobj.service.OrgBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;

/**
 * 
 * <pre>
 * Title:机构管理Action
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Controller
@RequestMapping("/bione/admin/org")
public class OrgController extends BaseController {
	@Autowired
	private OrgBS orgBS;
	@Autowired
	private LogicSysBS logicSysBS;
	@Autowired
	private DeptBS deptBS;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		String isStart = "";
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		BioneLogicSysInfo bioneLogicSysInfo = logicSysBS.getBioneLogicSysInfoByLogicSysNo(logicSysNo);
		if (GlobalConstants.SUPER_LOGIC_SYSTEM.equals(logicSysNo)) {
			isStart = "true";
		} else if (GlobalConstants.COMMON_STATUS_INVALID.equals(bioneLogicSysInfo.getBasicOrgSts())) {
			isStart = "true";
		} else {
			isStart = "false";
		}
		return new ModelAndView("/bione/org/org-index", "isStart", isStart);
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public List<CommonTreeNode> list() {
		List<CommonTreeNode> orgTreeList = orgBS.buildOrgTree();
		return orgTreeList;
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/{id}/up", method = RequestMethod.GET)
	@ResponseBody
	public BioneOrgInfo showUp(@PathVariable("id") String id) {
		BioneOrgInfo orgInfo = this.orgBS.getEntityById(id);
		return this.orgBS.findOrgInfoByOrgNo(orgInfo.getUpNo());
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/show.*", method = RequestMethod.GET)
	@ResponseBody
	public BioneOrgInfo show(String id) {
		return this.orgBS.getEntityById(id);
		// orgMap = Maps.newHashMap();
		// orgMap.put("Data", model);
		// if (model == null)
		// orgMap.put("Message", "不成功");
		// else
		// orgMap.put("Message", "成功");
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneOrgInfo model) {
		//		if (!GlobalConstants.SUPER_LOGIC_SYSTEM.equals(BiOneSecurityUtils
		//				.getCurrentUserInfo().getCurrentLogicSysNo())) {
		//			model.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
		//					.getCurrentLogicSysNo());
		//			model.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
		//			model.setLastUpdateTime(new Timestamp(new Date().getTime()));
		//			orgBS.updateEntity(model);
		//		}
		model.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		model.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
		model.setLastUpdateTime(new Timestamp(new Date().getTime()));
		if (model.getOrgId() != null) {
			List<BioneOrgInfo> orgList = Lists.newArrayList();
			this.orgBS.findLowerOrgInfosByOrgId(model.getOrgNo(), orgList);
			for (BioneOrgInfo org : orgList) {
				org.setOrgSts(model.getOrgSts());
				updateDeptStsByOrgInfo(org);
				orgBS.updateEntity(org);
			}
		}
		if(model.getOrgId()==null||model.getOrgId().equals("")){
			model.setOrgId(RandomUtils.uuid2());
		}
		orgBS.updateEntity(model);
		updateDeptStsByOrgInfo(model);
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String orgNo,String upName){
		ModelMap mm = new ModelMap();
		mm.put("orgNo", orgNo);
		mm.put("upName", upName);
		return new ModelAndView("/bione/org/org-editNew", mm);
	}
	
	@RequestMapping(value = "/{deptId}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("deptId") String deptId,String orgNo,String upName,String isStart) {
		ModelMap mm=new ModelMap();
		mm.put("id", deptId);
		mm.put("orgNo", orgNo);
		mm.put("upName", upName);
		mm.put("isStart", isStart);
		return new ModelAndView("/bione/org/org-edit", mm);
	}
	
	private void updateDeptStsByOrgInfo(BioneOrgInfo org) {
		List<BioneDeptInfo> deptList = deptBS.findDeptInfoByOrg(null, "", org.getOrgNo());
		for (BioneDeptInfo dept : deptList) {
			dept.setDeptSts(org.getOrgSts());
			deptBS.updateEntity(dept);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void destroy(@PathVariable("id") String id) {
		//		if (!GlobalConstants.SUPER_LOGIC_SYSTEM.equals(BiOneSecurityUtils
		//				.getCurrentUserInfo().getCurrentLogicSysNo())) {
		//			// this.orgBS.removeEntityById(id);
		//			this.orgBS.removeOrgAndDept(id);
		//		}
		this.orgBS.removeOrgAndDept(id);
	}

	@RequestMapping(value = "/testOrgNo")
	public boolean testOrgNo(String orgNo) {
		BioneOrgInfo org = this.orgBS.findOrgInfoByOrgNo(orgNo);
		if (org != null) {
			return false;
		}
		return true;
	}
}
