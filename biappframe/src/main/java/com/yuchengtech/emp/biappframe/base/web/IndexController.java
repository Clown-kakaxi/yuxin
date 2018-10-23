package com.yuchengtech.emp.biappframe.base.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneMenuInfo;
import com.yuchengtech.emp.biappframe.authres.service.FuncBS;
import com.yuchengtech.emp.biappframe.authres.service.MenuBS;
import com.yuchengtech.emp.biappframe.authres.util.MenuUtils;
import com.yuchengtech.emp.biappframe.authres.web.vo.BioneMenuInfoVO;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.biappframe.user.service.UserBS;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.bione.util.ExDateUtils;

/**
 * <pre>
 * Title:首页Action
 * Description: 负责处理首页菜单展示、导航等相关请求
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(IndexController.class);

	@Autowired
	private MenuBS menuBS;
	@Autowired
	private UserBS userBS;
	@Autowired
	private FuncBS funcBS;
	@Autowired
	private LogicSysBS logicSysBS;

	/**
	 * 显示系统首页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("indexUrl", "index/welcome");
		BiOneUser biOneUser = BiOneSecurityUtils.getCurrentUserInfo();
		BioneLogicSysInfo logicsys = logicSysBS.getBioneLogicSysInfoByLogicSysNo(biOneUser.getCurrentLogicSysNo());
		mav.addObject("logicSysName", logicsys.getLogicSysName());
		List<CommonTreeNode> menuInfoList = this.menuBS.buildMenuTree(biOneUser.getCurrentLogicSysNo(), "0", false);// 只查找第一层的节点
		if (menuInfoList != null) {
			for (CommonTreeNode node : menuInfoList) {
				BioneMenuInfoVO menuInfo = (BioneMenuInfoVO) node.getData();
				if (menuInfo != null && menuInfo.getIndexSts() != null
						&& menuInfo.getIndexSts().equals(GlobalConstants.COMMON_STATUS_VALID)) {
					mav.addObject("indexUrl", menuInfo.getNavPath());
				}
			}
			String menuInfoHTML = MenuUtils.list2HeaderMenu(menuInfoList);
			mav.addObject("menuInfoHTML", menuInfoHTML);

		}
		StringBuilder strBuilder = new StringBuilder();

		// 获取当前用户相关的信息
		strBuilder.append("欢迎您:" + biOneUser.getUserName());

		// 用户所属的机构和部门信息
		if (biOneUser.getOrgName() != null) {

			strBuilder.append("&nbsp;|&nbsp;所属机构:");
			strBuilder.append(biOneUser.getOrgName());
		}

		if (biOneUser.getDeptName() != null) {

			strBuilder.append("&nbsp;|&nbsp;所属部门:");
			strBuilder.append(biOneUser.getDeptName());
		}

		strBuilder.append("&nbsp;|&nbsp;登录时间:" + ExDateUtils.getCurrentStringDateTime());

		String userInfo = strBuilder.toString();

		String userIcon = this.userBS.getUserIcon(biOneUser.getUserId());
		// 系统首页
		mav.setViewName("/index/index");
		/* @Revision 2013-5-10 添加了[userId]和[userName]两个属性。 */
		mav.addObject("userId", biOneUser.getUserId());
		mav.addObject("userName", biOneUser.getUserName());
		mav.addObject("userInfo", userInfo);
		mav.addObject("userIcon", userIcon);
		mav.addObject("logicSys", biOneUser.getCurrentLogicSysNo());
		return mav;
	}

	/**
	 * 获取左侧边栏可折叠菜单数据
	 * 
	 * @return
	 */
	@RequestMapping(value="/initAccordionMenu.json",method=RequestMethod.POST)
	@ResponseBody
	public String initAccordionMenu(String parentId) {
		BiOneUser biOneUser = BiOneSecurityUtils.getCurrentUserInfo();
		String menuId = parentId;
		List<CommonTreeNode> menuInfoList = this.menuBS.buildMenuTree(biOneUser.getCurrentLogicSysNo(), menuId, true);

		BioneMenuInfo parentMenuInfo = this.menuBS.getEntityById(BioneMenuInfo.class, menuId);
		BioneFuncInfo funcInfo = this.funcBS.getEntityById(BioneFuncInfo.class, parentMenuInfo.getFuncId());


		String menuInfoHTML = MenuUtils.list2AccordionMenu(funcInfo, menuInfoList);
		return menuInfoHTML;

	}

	//跳转欢迎页面
	@RequestMapping("/welcome")
	public String welcome() {
		return "/index/welcome";
	}
}
