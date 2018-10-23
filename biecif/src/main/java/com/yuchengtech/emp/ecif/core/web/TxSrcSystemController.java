package com.yuchengtech.emp.ecif.core.web;


import java.util.HashMap;
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

import com.yuchengtech.emp.ecif.core.service.TxSycSystemBS;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.core.entity.SrcSystem;

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
@RequestMapping("/ecif/core/txsrcsystem")
public class TxSrcSystemController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxSrcSystemController.class);

	@Autowired
	private TxSycSystemBS txSrcSystemBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/core/txsrcsystem-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<SrcSystem> searchResult = this.txSrcSystemBS.getSrcSystemList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		List<SrcSystem> txSrcSystemList = searchResult.getResult();
				
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", txSrcSystemList);	
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(SrcSystem model) {
		this.txSrcSystemBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SrcSystem show(@PathVariable("id") String id) {
		SrcSystem model = this.txSrcSystemBS.getEntityById(id);
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
		return new ModelAndView("/ecif/core/txsrcsystem-edit", "id", id);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/core/txsrcsystem-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txSrcSystemBS.removeEntityByProperty("srcSysCd", id);
		} else {
			this.txSrcSystemBS.removeEntityById(id);
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String clientAuthId) {
		return new ModelAndView("/ecif/core/txserviceauth-index", "srcSysCd",
				clientAuthId);
	}
	

	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(String id) {
		
		List<SrcSystem> model = txSrcSystemBS.findEntityListByProperty("srcSysCd", id);
		if (model != null){	
			for(int i=0;i<model.size();i++){
				SrcSystem t = model.get(i);
				if(t.getSrcSysCd().toString().equals(id)){ //修改
					return true;
				}else{
					return false;
				}
			}
			
		}else
			return true;

		return true;
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
