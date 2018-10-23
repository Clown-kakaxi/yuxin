package com.yuchengtech.emp.ecif.rulemanage.web;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.entity.event.EventInfo;
import com.yuchengtech.emp.ecif.rulemanage.entity.DqIncrTabConf;
import com.yuchengtech.emp.ecif.rulemanage.service.DqIncrTabConfBS;

/**
 * <pre>
 * Description: 通用覆盖规则管理Controller 
 * </pre>	
 * @author pengsenlin pengsl@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：彭森林		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/rulemanage/dqincrtabconf")
public class DqIncrTabConfController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(DqIncrTabConfController.class);

	@Autowired
	private DqIncrTabConfBS dqIncrTabConfBS;
	
	@Autowired
	private ResultUtil resultUtil;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/rulemanage/dqincrtabconf-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> list(Pager pager) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<DqIncrTabConf> searchResult = dqIncrTabConfBS.getDqIncrTabConfList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		List<EventInfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), DqIncrTabConf.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 执行新增前页面跳转 
	 * @return String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/rulemanage/dqincrtabconf-edit";
	}
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(DqIncrTabConf model) {
		this.dqIncrTabConfBS.updateEntity(model);
	}
	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/rulemanage/dqincrtabconf-edit", "id", id);
	}
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public DqIncrTabConf show(@PathVariable("id") String id) {
		DqIncrTabConf model = this.dqIncrTabConfBS.getEntityById(id.trim());
		return model;
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.dqIncrTabConfBS.removeEntityByProperty("id", id.trim());
		} else {
			this.dqIncrTabConfBS.removeEntityById(id);
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/dqincrcolconf")
	public ModelAndView authservice(String id, String dstTab) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("tid", id);
		map.put("dstTab", dstTab);
		return new ModelAndView("/ecif/rulemanage/dqincrcolconf-index", map);
	}
}
