package com.yuchengtech.emp.ecif.sysmonitor.web;

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
import com.yuchengtech.emp.ecif.transaction.entity.TxErr;
import com.yuchengtech.emp.ecif.transaction.service.TxErrBS;
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
@RequestMapping("/ecif/txmonitor")
public class TXMonitorController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(TXMonitorController.class);
	@Autowired
	private TxErrBS txErrBS;
	
	/**
	 * 数据实时加载
	 * 
	 * @return ModelAndView 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView queryTXErrorInfo() {
		ModelAndView mav = new ModelAndView();
		
		
		mav.setViewName("/ecif/sysmonitor/txmonitor");
		return mav;
	}
	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxErr> searchResult = this.txErrBS.getTxErrList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		
		return resDefMap;
	}
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxErr show(@PathVariable("id") Long id) {
		TxErr model = this.txErrBS.getEntityById(id);
		return model;
	}
	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/detaile", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/sysmonitor/txmonitordetaile", "id", id);
		
	}
}
