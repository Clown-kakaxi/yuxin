package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.entity.image.ImageInfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.ImageInfoBS;

@Controller
@RequestMapping("/ecif/image")
public class ImageInfoController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(ImageInfoController.class);
	
	@Autowired
	private ResultUtil resultUtil;

	@Autowired
	private ImageInfoBS imageinfoBS;

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager, @RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<ImageInfo> searchResult = imageinfoBS.getUserList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<ImageInfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), ImageInfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
}
