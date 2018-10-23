package com.yuchengtech.emp.ecif.report.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.user.service.OrgTreeBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.ecif.report.service.OrgTreeListBS;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成用户信息表的CRUD操作 
 * </pre>
 * @author xuguangyuan  xuguangyuansh@gmail.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：许广源		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/report/orgtreelist")
public class OrgTreeListController extends BaseController {

	@Autowired
	private OrgTreeListBS orgTreeBS;

	/**
	 * 部门显示视树，初始方法
	 * 
	 * @return 
	 * 		RestFul的增强结果类型
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/report/org-tree";
	}

	/**
	 * 获取机构树信息
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public List<CommonTreeNode> list(Pager pager) {
		List<CommonTreeNode> list = orgTreeBS.buildOrgTree(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo(), true, pager.getSearchCondition());
		return list;
	}

}
