package com.yuchengtech.emp.biappframe.authobj.web;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.auth.service.AuthBS;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpObjRel;
import com.yuchengtech.emp.biappframe.authobj.service.AuthObjgrpRelBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.IAuthObject;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * <pre>
 * Title:对象组与其他授权对象关系维护
 * Description: 对象组与其他授权对象关系维护Action
 * </pre>
 * 
 * @author fanll fanll@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/objgrpRelManage")
public class AuthObjgrpRelController extends BaseController {

	@Autowired
	private AuthBS authBS;

	@Autowired
	private AuthObjgrpRelBS relBS;

	/**
	 * 跳转授权对象组管理页面
	 */
	@RequestMapping("objgrpRelManage")
	public ModelAndView objgrpRelManage(String objgrpId, String logicSysNo) {
		ModelMap mm = new ModelMap();
		mm.put("objgrpId", objgrpId);
		mm.put("logicSysNo", logicSysNo);
		return new ModelAndView("/bione/auth/auth-objgrp-rel-manage", mm);
	}

	/**
	 * 根据不同授权对象类型获取授权对象树
	 */
	@RequestMapping("/getAuthObjTree.*")
	@ResponseBody
	public List<CommonTreeNode> getAuthObjTree(String objDefNo) {
		List<CommonTreeNode> authObjTree = new ArrayList<CommonTreeNode>();
		if (objDefNo == null || "".equals(objDefNo))
			return authObjTree;
		List<String> beanNames = this.authBS.findAuthObjBeanNameByType(objDefNo);
		if (beanNames != null && beanNames.size() > 0) {
			//通过名称获取授权对象实现类
			String beanName = beanNames.get(0);
			IAuthObject authObj = SpringContextHolder.getBean(beanName);
			if (authObj != null) {
				authObjTree = authObj.doGetAuthObjectInfo();

				if (authObjTree != null && authObjTree.size() > 0) {
					//处理图标
					for (CommonTreeNode node : authObjTree) {
						node.setIcon(this.getRequest().getContextPath() + "/" + (node.getIcon()));
					}
				}
			}
		}
		return authObjTree;
	}

	/**
	 * 获取当前授权对象组与指定授权对象的关系集合
	 */
	@RequestMapping("/findRelByAuthObjgrpAndAuthObj.*")
	@ResponseBody
	public List<BioneAuthObjgrpObjRel> findRelByAuthObjgrpAndAuthObj(String objgrpId, String objDefNo, String logicSysNo) {
		if (objgrpId != null && !"".equals(objgrpId)) {
			return relBS.getAuthObjgrpRelByObjgrpId(logicSysNo, objgrpId, objDefNo);
		}
		return null;

	}

	/**
	 * 保存授权对象组关系
	 */
	@RequestMapping("/saveObjgrpRels")
	@ResponseBody
	public void saveObjgrpRels(String logicSysNo, String objgrpId, String relsJson) {
		//将JSON转为java对象集合
		JSONObject jsonobject = JSONObject.fromObject(relsJson);

		JSONArray jsonArray = jsonobject.getJSONArray("relsJson");
		List<BioneAuthObjgrpObjRel> relList = new ArrayList<BioneAuthObjgrpObjRel>();

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = (JSONObject) jsonArray.get(i);
			BioneAuthObjgrpObjRel enumitemVO = (BioneAuthObjgrpObjRel) JSONObject.toBean(object,
					BioneAuthObjgrpObjRel.class);
			if (enumitemVO != null) {
				relList.add(enumitemVO);
			}
		}
		this.relBS.updateAuthObjgrpRelBatch(logicSysNo, objgrpId, relList);
	}

}
