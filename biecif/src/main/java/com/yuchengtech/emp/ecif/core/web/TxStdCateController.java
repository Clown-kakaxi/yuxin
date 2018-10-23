package com.yuchengtech.emp.ecif.core.web;

import java.util.HashMap;
import java.util.List;
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
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.core.entity.TxStdCate;
import com.yuchengtech.emp.ecif.core.service.TxStdCateBS;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成表的CRUD操作 
 * </pre>	
 * @author shangjf  shangjf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：尚吉峰		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/core/txstdcate")
public class TxStdCateController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxStdCateController.class);

	@Autowired
	private TxStdCateBS txStdCateBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/core/txstdcate-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxStdCate> searchResult = this.txStdCateBS.getTabDefList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		List<TxStdCate> txStdCateList = searchResult.getResult();
				
		Map<String, Object> resDefMap = Maps.newHashMap();
//		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Rows", txStdCateList);	
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}
	
	@RequestMapping("/batchimport")
	public ModelAndView batchimport() {
		//return "/ecif/core/txstdcate-batchimport";
		ModelMap mm = new ModelMap();
		String dbType =  txStdCateBS.getDbtype();
		mm.put("dbType", dbType);
		
		return new ModelAndView("/ecif/core/txstdcate-batchimport", mm);
		
	}
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxStdCate model){

		this.txStdCateBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxStdCate show(@PathVariable("id") String id) {
		TxStdCate model = this.txStdCateBS.getEntityById(id);
		return model;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/core/txstdcate-edit", "id", id);
	}

	
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/core/txstdcate-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			for(int i=0;i<ids.length;i++){
				this.txStdCateBS.deleteBatch(ids[i]);
			}

		} else {
			this.txStdCateBS.deleteBatch(id);
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String stdcate) {
		return new ModelAndView("/ecif/core/txstdcode-index", "stdcate",
				stdcate);
	}
	
	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(Long tabId) {
		TxStdCate model = txStdCateBS.findUniqueEntityByProperty("tabId", tabId);
		if (model != null)
			return false;
		else
			return true;
	}

	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		List<Map<String,String>> list = this.txStdCateBS.getComBoBox();
		return list;
	}
	
	public Map<String, String> convertList2Map(List<Map<String,String>> list){
		Map<String, String> newmap = new HashMap<String, String>();
		for(Map<String,String> map:list ){
			String id =    (String)map.get("id");
			String text =(String)map.get("text");
			
			newmap.put(id, text);
		}
		
		return newmap;
	}
	
}
