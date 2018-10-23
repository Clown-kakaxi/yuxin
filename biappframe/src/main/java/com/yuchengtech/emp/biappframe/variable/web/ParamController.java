package com.yuchengtech.emp.biappframe.variable.web;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamInfo;
import com.yuchengtech.emp.biappframe.variable.entity.BioneParamTypeInfo;
import com.yuchengtech.emp.biappframe.variable.service.ParamBS;
import com.yuchengtech.emp.biappframe.variable.service.ParamTypeBS;
import com.yuchengtech.emp.bione.entity.page.Pager;

/**
 * <pre>
 * Title:参数处理Action类
 * Description: 实现参数视图中对应的增删改查功能，以及树形图展示。
 * </pre>
 * 
 * @author yangyuhui yangyh4@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/variable/param")
public class ParamController extends BaseController {
	@Autowired
	private ParamBS paramBS;
	@Autowired
	private ParamTypeBS paramTypeBS;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String paramTypeNo,String upNo) {
		BioneParamTypeInfo paramtype = paramTypeBS
				.findParamTypeInfoByNo(paramTypeNo);
		ModelMap mm = new ModelMap();
		mm.put("paramTypeId", paramtype.getParamTypeId());
		mm.put("paramTypeNo", paramTypeNo);
		mm.put("paramTypeName", paramtype.getParamTypeName());
		mm.put("upNo", upNo);
		return new ModelAndView("/bione/param/param-editNew", mm);

	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneParamInfo param) {
		param.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());
		paramBS.saveOrUpdateEntity(param);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
			this.paramBS.removeEntityBatch(id);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		BioneParamInfo param = this.paramBS.getEntityById(id);
		String paramTypeName = paramTypeBS.findParamTypeInfoByNo(
				param.getParamTypeNo()).getParamTypeName();
		String paramTypeId = paramTypeBS.findParamTypeInfoByNo(
				param.getParamTypeNo()).getParamTypeId();
		ModelMap mm = new ModelMap();
		mm.put("id", id);
		mm.put("paramTypeId", paramTypeId);
		mm.put("paramTypeName", paramTypeName);
		return new ModelAndView("/bione/param/param-edit", mm);
	}

	/**
	 * 查询参数并转化为页面显示数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneParamInfo show(@PathVariable("id") String id) {
		return this.paramBS.getEntityById(id);
	}

	/**
	 * 验证参数值是否存在
	 */
	@RequestMapping("/testParamValue")
	@ResponseBody
	public boolean testParamValue(String paramTypeId, String paramValue,
			@RequestParam(value = "id", required = false) String id) {
		String paramTypeNo = paramTypeBS.getEntityById(paramTypeId)
				.getParamTypeNo();
		List<BioneParamInfo> paramList = (List<BioneParamInfo>) paramBS
				.findParamByParamTypeNo(paramTypeNo);
		for (BioneParamInfo param : paramList) {
			if (!param.getParamId().equals(id)) {
				if (param.getParamValue().equals(paramValue)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 初始化Grid
	 */
	@RequestMapping(value = "/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(Pager pager,String paramTypeNo) {
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		String cod = pager.getCondition();
		if((cod!=null&&cod.indexOf("paramTypeNo")>=0)||(paramTypeNo!=null&&(!"".equals(paramTypeNo)))){
			Map<String, Object> map = this.paramBS.searchParamsAsTree(pager.getSearchCondition(),logicSysNo,paramTypeNo);
			return map;

		}else{
			return null;
		}
	}
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/param/param-index";
	}

}
