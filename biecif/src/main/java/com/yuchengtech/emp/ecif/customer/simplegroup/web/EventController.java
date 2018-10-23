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
import com.yuchengtech.emp.ecif.customer.entity.event.AttentEvent;
import com.yuchengtech.emp.ecif.customer.entity.event.EventInfo;
import com.yuchengtech.emp.ecif.customer.entity.event.LargeEvent;
import com.yuchengtech.emp.ecif.customer.entity.event.TxEvent;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.EventBS;


/**
 * 
 * 
 * <pre>
 * Title:事件信息的Controller端
 * Description: 处理客户资产信息的CRUD操作
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/event")
public class EventController extends BaseController {
	
	@Autowired
	private EventBS eventBS;
	
	@Autowired
	private ResultUtil resultUtil;
	
	protected static Logger log = LoggerFactory
			.getLogger(CustomerContactController.class);
	
	
	/**
	 * 跳转 修改 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/attentevent/list.*")
	@ResponseBody
	public Map<String, Object> getAttentList(Pager pager, @RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<EventInfo> searchResult = eventBS.getAttentEventList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<EventInfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), EventInfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/largeevent/list.*")
	@ResponseBody
	public Map<String, Object> getLargeEventList(Pager pager, @RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<EventInfo> searchResult = eventBS.getLargeEventList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<EventInfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), EventInfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/txevent/list.*")
	@ResponseBody
	public Map<String, Object> getTxEventList(Pager pager, @RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<EventInfo> searchResult = eventBS.getTxEventList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<EventInfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), EventInfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}


}