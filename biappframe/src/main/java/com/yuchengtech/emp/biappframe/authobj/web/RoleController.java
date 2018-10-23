package com.yuchengtech.emp.biappframe.authobj.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.authobj.entity.BioneRoleInfo;
import com.yuchengtech.emp.biappframe.authobj.service.RoleBS;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Controller
@RequestMapping("/bione/admin/role")
public class RoleController extends BaseController {
	@Autowired
	private RoleBS roleBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/role/role-index";
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneRoleInfo> searchResult = roleBS.getRoleList(pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("Rows", searchResult.getResult());
		roleMap.put("Total", searchResult.getTotalCount());
		return roleMap;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneRoleInfo model) {
		logger.info("保存的帐号是:" + model.getRoleId());
		model.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		model.setLastUpdateTime(new Timestamp(new Date().getTime()));
		model.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
		if(model.getRoleId()==null||model.getRoleId().equals("")){
			model.setRoleId(RandomUtils.uuid2());
		}
		roleBS.updateEntity(model);
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/role/role-editNew";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		logger.info("修改的角色ID为：" + id);
		return new ModelAndView("/bione/role/role-edit", "id", id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneRoleInfo show(@PathVariable("id") String id) {
		return roleBS.getEntityById(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		roleBS.removeEntityById(id);
	}

	@RequestMapping("/destroyOwn.*")
	@ResponseBody
	public Map<String, String> destroyOwn(String ids) {
		String message = "";
		Map<String, String> returnMap = new HashMap<String, String>();
		if (ids != null && !"".equals(ids)) {
			String[] idArray = ids.split(",");
			this.roleBS.deleteRolesByIds(idArray);
			message = "success";
			returnMap.put("message", message);
		}
		return returnMap;
	}

	@RequestMapping("/testRoleNo")
	@ResponseBody
	public boolean testRoleNo(String roleNo) {
		return this.roleBS.checkIsRoleNoExist(roleNo);
	}

}
