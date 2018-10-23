package com.yuchengtech.emp.biappframe.server.web;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.server.entity.BioneServerInfo;
import com.yuchengtech.emp.biappframe.server.service.ServerInfoBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title: 
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/serverinfo")
public class ServerInfoController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(ServerInfoController.class);

	@Autowired
	private ServerInfoBS serverInfoBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/serverinfo/server-info-index";
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneServerInfo> result = serverInfoBS.getSearch(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		Map<String, Object> rows = Maps.newHashMap();
		rows.put("Rows", result.getResult());
		rows.put("Total", result.getTotalCount());
		return rows;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
		try {
			serverInfoBS.deleteItems(id);
		} catch (Exception e) {
			return "删除失败！";
		}
		return "删除成功！";
	}

	@RequestMapping("/info.*")
	@ResponseBody
	public BioneServerInfo info(String id) {
		BioneServerInfo model = serverInfoBS.getEntityById(id);
		return model;
	}

	@RequestMapping("/checkServerNo")
	@ResponseBody
	public boolean checkServerNo(BioneServerInfo model,String t) {
		if ("edit".equals(t)) {
			return true;
		}
		if (serverInfoBS.isServerNoExist(model.getServerNo())) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		ModelMap mm = new ModelMap();
		mm.addAttribute("id", id);
		mm.addAttribute("t", "edit");
		return new ModelAndView("/bione/serverinfo/server-info-edit");
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew() {
		return new ModelAndView("/bione/serverinfo/server-info-edit", "t",
				"new");
	}
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneServerInfo model) {
		if (model.getServerId() == null || "".equals(model.getServerId())) {
			model.setServerId(RandomUtils.uuid2());
		}
		serverInfoBS.updateEntity(model);
	}
}