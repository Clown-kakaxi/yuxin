package com.yuchengtech.emp.biappframe.authres.web;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authres.entity.BioneModuleInfo;
import com.yuchengtech.emp.biappframe.authres.service.ModuleBS;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;

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
@RequestMapping("/bione/admin/module")
public class ModuleController extends BaseController {
	@Autowired
	private ModuleBS moduleBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/module/module-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager rf) {
		SearchResult<BioneModuleInfo> searchResult = moduleBS.getModuleList(rf.getPageFirstIndex(), rf.getPagesize(),
				rf.getSortname(), rf.getSortorder(), rf.getSearchCondition());
		Map<String, Object> moduleMap = Maps.newHashMap();
		moduleMap.put("Rows", searchResult.getResult());
		moduleMap.put("Total", searchResult.getTotalCount());
		return moduleMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	// POST /module/
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneModuleInfo model) {
		if(model.getModuleId()==null||model.getModuleId().equals("")){
			model.setModuleId(RandomUtils.uuid2());
		}
		moduleBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneModuleInfo show(@PathVariable("id") String id) {
		BioneModuleInfo model = (BioneModuleInfo) this.moduleBS.getEntityById(id);
		return model;
	}

	/**
	 * 执行修改前的页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/module/module-edit", "id", id);
	}

	/**
	 * 执行添加前的页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/module/module-edit";
	}

	/**
	 * 执行删除操作，可进行批量删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		List<String> moduleNameList = Lists.newArrayList();
		for (String delid : ids) {
			String str = this.moduleBS.findUsedModuleName(delid);
			if (!StringUtils.isEmpty(str)) {
				moduleNameList.add(str);
				continue;
			}
			this.moduleBS.removeModuleById(id);
		}
		if (!moduleNameList.isEmpty()) {
			return StringUtils.join(moduleNameList, ", ");
		}
		return "";
	}

	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping("/moduleNoValid")
	@ResponseBody
	public boolean moduleNoValid(String moduleNo) {
		BioneModuleInfo model = moduleBS.findUniqueEntityByProperty("moduleNo", moduleNo);
		if (model != null)
			return false;
		else
			return true;
	}
}
