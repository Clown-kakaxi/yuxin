package com.yuchengtech.emp.biappframe.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.user.service.DeptTreeBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

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
@RequestMapping("/bione/admin/depttree")
public class DeptTreeController extends BaseController {
	@Autowired
	private DeptTreeBS deptTreeBS;

	/**
	 * 部门显示视树，初始方法
	 * 
	 * @return 
	 * 		RestFul的增强结果类型
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(String id) {
		return new ModelAndView("/bione/dept/dept-tree-index", "id", id);
	}

	/**
	 * 获取部门树信息
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public List<CommonTreeNode> list(Pager pager, String orgNo) {
		if (orgNo != null) {
			List<CommonTreeNode> list = deptTreeBS.buildDeptTree(BiOneSecurityUtils.getCurrentUserInfo()
					.getCurrentLogicSysNo(), true, pager.getSearchCondition(), orgNo);
			return list;
		}
		return null;
	}

}
