package com.yuchengtech.emp.ecif.core.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.yuchengtech.emp.ecif.core.entity.TxStdCode;
import com.yuchengtech.emp.ecif.core.entity.TxStdCodePK;
import com.yuchengtech.emp.ecif.core.entity.TxStdCodeVO;
import com.yuchengtech.emp.ecif.core.service.TxStdCodeBS;

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
@RequestMapping("/ecif/core/txstdcode")
public class TxStdCodeController extends BaseController {
	@Autowired
	private TxStdCodeBS txStdCodeBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/core/txstdcode-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/initlist.*")
	@ResponseBody
	public Map<String, Object> initlist(Pager pager,String tabId ) {
		//pager.setCondition(condition);

		SearchResult<TxStdCode> searchResult = this.txStdCodeBS.getTxStdCodeListByCate(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),tabId);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager,String stdCate ) {

		SearchResult<TxStdCode> searchResult = this.txStdCodeBS.getTxStdCodeList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),stdCate);
	
		List volist = new ArrayList();
		List list = searchResult.getResult();
		for(int i=0;i<list.size();i++){
			TxStdCodeVO vo = new  TxStdCodeVO();
			TxStdCode t = (TxStdCode)list.get(i);
			vo.setStdCate(t.getId().getStdCate());
			vo.setStdCode(t.getId().getStdCode());						
			vo.setOrderId(t.getOrderId());
			vo.setParentStdCode(t.getParentStdCode());
			vo.setState(t.getState());
			vo.setStdCodeDesc(t.getStdCodeDesc());
			
			volist.add(vo);	
		}
		Map<String, Object> resDefMap = Maps.newHashMap();
		
		resDefMap.put("Rows", volist);
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}


	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxStdCodeVO vo) {
		
		TxStdCodePK pk = new TxStdCodePK();
		pk.setStdCate(vo.getStdCate());
		pk.setStdCode(vo.getStdCode());
		
		TxStdCode model  = new TxStdCode();
		model.setId(pk);
		model.setOrderId(vo.getOrderId());
		model.setParentStdCode(vo.getParentStdCode());
		model.setState(vo.getState());
		model.setStdCodeDesc(vo.getStdCodeDesc());
		
		this.txStdCodeBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public TxStdCodeVO show(String stdCate,String stdCode) {
		
		TxStdCodePK pk = new TxStdCodePK();
		pk.setStdCate(stdCate);
		pk.setStdCode(stdCode);
		
		TxStdCode t = this.txStdCodeBS.getEntityById(pk);
		
		TxStdCodeVO vo = new TxStdCodeVO();
		vo.setStdCate(t.getId().getStdCate());
		vo.setStdCode(t.getId().getStdCode());						
		vo.setOrderId(t.getOrderId());
		vo.setParentStdCode(t.getParentStdCode());
		vo.setState(t.getState());
		vo.setStdCodeDesc(t.getStdCodeDesc());
		
		return vo;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(String stdCate,String stdCode) {
		ModelMap mm = new ModelMap();
		mm.put("stdCate", stdCate);
		mm.put("stdCode", stdCode);
		
		return new ModelAndView("/ecif/core/txstdcode-edit",  mm);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String stdCate) {
		ModelMap mm = new ModelMap();
		mm.put("stdCate", stdCate);
		
		return new ModelAndView("/ecif/core/txstdcode-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			TxStdCodePK pk = new TxStdCodePK();
			pk.setStdCate(ids[0]);
			pk.setStdCode(ids[1]);
			
			this.txStdCodeBS.removeEntityById(pk);
			
		} else {
			//
		}
	}


}
