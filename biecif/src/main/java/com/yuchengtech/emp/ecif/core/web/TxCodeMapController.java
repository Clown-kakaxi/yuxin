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
import com.yuchengtech.emp.ecif.core.entity.TxCodeMap;
import com.yuchengtech.emp.ecif.core.entity.TxCodeMapPK;
import com.yuchengtech.emp.ecif.core.entity.TxCodeMapVO;
import com.yuchengtech.emp.ecif.core.service.TxCodeMapBS;

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
@RequestMapping("/ecif/core/txcodemap")
public class TxCodeMapController extends BaseController {
	@Autowired
	private TxCodeMapBS txCodeMapBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/core/txcodemap-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {

		SearchResult<TxCodeMap> searchResult = this.txCodeMapBS.getTxCodeMapList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
	
		List volist = new ArrayList();
		List list = searchResult.getResult();
		for(int i=0;i<list.size();i++){
			TxCodeMapVO vo = new  TxCodeMapVO();
			TxCodeMap t = (TxCodeMap)list.get(i);
			vo.setSrcSysCd(t.getId().getSrcSysCd());						
			vo.setStdCate(t.getId().getStdCate());
			vo.setSrcCode(t.getId().getSrcCode());	
			
			vo.setSrcCodeDesc(t.getSrcCodeDesc());
			vo.setState(t.getState());
			vo.setStdCode(t.getStdCode());
			
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
	public void create(TxCodeMapVO vo) {
		
		TxCodeMapPK pk = new TxCodeMapPK();
		pk.setStdCate(vo.getStdCate());
		pk.setSrcSysCd(vo.getSrcSysCd());
		pk.setSrcCode(vo.getSrcCode());	

		TxCodeMap model  = new TxCodeMap();
		model.setId(pk);
		model.setSrcCodeDesc(vo.getSrcCodeDesc());
		model.setState(vo.getState());
		model.setStdCode(vo.getStdCode());

		this.txCodeMapBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public TxCodeMapVO show(String sycSysCd,String srcCode,String stdCate) {
		
		TxCodeMapPK pk = new TxCodeMapPK();
		pk.setStdCate(stdCate);
		pk.setSrcSysCd(sycSysCd);
		pk.setSrcCode(srcCode);	
		
		TxCodeMap t = this.txCodeMapBS.getEntityById(pk);
		
		TxCodeMapVO vo = new TxCodeMapVO();
		vo.setSrcSysCd(t.getId().getSrcSysCd());						
		vo.setStdCate(t.getId().getStdCate());
		vo.setSrcCode(t.getId().getSrcCode());	
		
		vo.setSrcCodeDesc(t.getSrcCodeDesc());
		vo.setState(t.getState());
		vo.setStdCode(t.getStdCode());
		
		return vo;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit( String id) {
		ModelMap mm = new ModelMap();
		mm.put("id", id);
//		mm.put("sycSysCd", sycSysCd);
//		mm.put("srcCode", srcCode);
//		mm.put("stdCate", stdCate);
//		
		return new ModelAndView("/ecif/core/txcodemap-edit",  mm);
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
		
		return new ModelAndView("/ecif/core/txcodemap-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			TxCodeMapPK pk = new TxCodeMapPK();
			pk.setSrcSysCd(ids[0]);
			pk.setSrcCode(ids[1]);	
			pk.setStdCate(ids[2]);
			
			this.txCodeMapBS.removeEntityById(pk);
			
		} 
	}


}
