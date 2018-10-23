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
import com.yuchengtech.emp.ecif.rulemanage.entity.DqIncrTabConf;
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
@RequestMapping("/ecif/serviceinfo")
public class ServiceInfoController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(ServiceInfoController.class);
	@Autowired
	private TxServiceStatusBS txServiceStatusBS;
	
	/**
	 * 数据实时加载
	 * 
	 * @return ModelAndView 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView queryServiceStatusInfo() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/ecif/sysmonitor/serviceinfo-index");
		return mav;
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
	
	/**
	 * 用于添加，或修改时的保存对象
	 */

	@RequestMapping(method = RequestMethod.POST)
	public void save(TxServiceStatus txServiceStatus) {
		if(txServiceStatus.getServiceID() == null){
			txServiceStatusBS.saveEntity(txServiceStatus);
		}else{
			txServiceStatusBS.updateTxServiceStatus(txServiceStatus);
		}
	}
	/**
	 * 执行新增前页面跳转 
	 * @return String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/sysmonitor/serviceinfo-edit";
	}
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxServiceStatus show(@PathVariable("id") Long id) {
		TxServiceStatus model = this.txServiceStatusBS.getEntityById(id);
		return model;
	}
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Pager pager) {
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", this.txServiceStatusBS.getAllEntityList());
		resDefMap.put("Total", this.txServiceStatusBS.getAllEntityList().size());
		
		return resDefMap;
	}
	
	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		
		return new ModelAndView("/ecif/sysmonitor/serviceinfo-edit", "id", id);
		
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txServiceStatusBS.removeEntityByProperty("id", id);
		} else {
			this.txServiceStatusBS.removeEntityById(new Long(id));
		}
	}

}
