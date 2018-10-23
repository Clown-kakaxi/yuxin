package com.yuchengtech.emp.biappframe.authobj.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpInfo;
import com.yuchengtech.emp.biappframe.authobj.service.AuthObjgrpBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

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
@RequestMapping("/bione/admin/authObjgrp")
public class AuthObjgrpController extends BaseController {

	@Autowired
	private AuthObjgrpBS authObjgrpBS;

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		this.authObjgrpBS.removeEntityBatch(id);
	}

	@RequestMapping("/destroyOwn")
	public void destroyOwn(String delIds) {
		if (delIds != null && !"".equals(delIds)) {
			this.authObjgrpBS.removeEntityBatch(delIds);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/authObjGrp/auth-objgrp-index";
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneAuthObjgrpInfo> searchResult = this.authObjgrpBS.getObjgrpInfoList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		Map<String, Object> authObjgrpMap = Maps.newHashMap();
		authObjgrpMap.put("Rows", searchResult.getResult());
		authObjgrpMap.put("Total", searchResult.getTotalCount());
		return authObjgrpMap;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/authObjGrp/auth-objgrp-editNew";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/authObjGrp/auth-objgrp-edit", "id", id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneAuthObjgrpInfo objgrpInfo) {
		objgrpInfo.setLastUpdateTime(new Timestamp(new Date().getTime()));
		objgrpInfo.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
		objgrpInfo.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		objgrpInfo.setLastUpdateTime(new Timestamp(new Date().getTime()));
		if(objgrpInfo.getObjgrpId()==null||objgrpInfo.getObjgrpId().equals("")){
			objgrpInfo.setObjgrpId(RandomUtils.uuid2());
		}
		this.authObjgrpBS.updateEntity(objgrpInfo);
	}

	@RequestMapping("/testObjgrpNo")
	@ResponseBody
	public boolean testObjgrpNo(String objgrpNo) {
		return this.authObjgrpBS.checkIsObjgrpNoExist(objgrpNo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneAuthObjgrpInfo show(@PathVariable("id") String id) {
		return this.authObjgrpBS.getEntityById(id);
	}

}
