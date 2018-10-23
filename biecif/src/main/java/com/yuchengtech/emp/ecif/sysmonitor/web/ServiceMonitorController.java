package com.yuchengtech.emp.ecif.sysmonitor.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.sysmonitor.entity.TxServiceStatus;
import com.yuchengtech.emp.ecif.sysmonitor.service.TxServiceStatusBS;
/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author  
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/servicemonitor")
public class ServiceMonitorController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(ServiceMonitorController.class);
	@Autowired
	private TxServiceStatusBS txServiceStatusBS;
	
	/**
	 * 数据实时加载
	 * 
	 * @return ModelAndView 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String queryServiceStatusInfo(Pager pager) {
		
		return "/ecif/sysmonitor/servicemonitor";
	}
	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxServiceStatus> searchResult = this.txServiceStatusBS.getTxServiceStatusList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		
		return resDefMap;
	}

}
