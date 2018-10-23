package com.yuchengtech.emp.ecif.seorg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.seorg.service.SeOrgBS;

/**
 * 
 * <pre>
 * Title:机构管理Action
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author wuhp@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Controller
@RequestMapping("/ecif/seorg/seorgcontroller")
public class SeOrgController extends BaseController {
	@Autowired
	private SeOrgBS seOrgBS;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		String isStart = "";
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		isStart = "true";
		return new ModelAndView("/ecif/seorg/org-index", "isStart", isStart);
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public List<CommonTreeNode> list() {
		List<CommonTreeNode> orgTreeList = seOrgBS.buildOrgTree();
		return orgTreeList;
	}
}
