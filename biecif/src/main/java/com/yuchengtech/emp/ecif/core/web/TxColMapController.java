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
import com.yuchengtech.emp.ecif.core.entity.TxColMap;
import com.yuchengtech.emp.ecif.core.entity.TxColMapPK;
import com.yuchengtech.emp.ecif.core.entity.TxColMapVO;
import com.yuchengtech.emp.ecif.core.service.TxColMapBS;

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
@RequestMapping("/ecif/core/txcolmap")
public class TxColMapController extends BaseController {
	@Autowired
	private TxColMapBS txColMapBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/core/txcolmap-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {

		SearchResult<TxColMap> searchResult = this.txColMapBS.getTxColMapList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
	
		List volist = new ArrayList();
		List list = searchResult.getResult();
		for(int i=0;i<list.size();i++){
			TxColMapVO vo = new  TxColMapVO();
			TxColMap t = (TxColMap)list.get(i);
			vo.setSrcSysCd(t.getId().getSrcSysCd());						
			vo.setSrcTab(t.getId().getSrcTab());
			vo.setSrcCol(t.getId().getSrcCol());				
			vo.setStdTab(t.getStdTab());
			vo.setStdCol(t.getStdCol());
			vo.setStdCate(t.getStdCate());
			vo.setStdFlag(t.getStdFlag());
			vo.setTrimFlag(t.getTrimFlag());
			vo.setState(t.getState());
			
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
	public void create(TxColMapVO vo) {
		
		TxColMapPK pk = new TxColMapPK();
		pk.setSrcSysCd(vo.getSrcSysCd());
		pk.setSrcTab(vo.getSrcTab());
		pk.setSrcCol(vo.getSrcCol());	

		TxColMap model  = new TxColMap();
		model.setId(pk);
		model.setStdTab(vo.getStdTab());
		model.setStdCol(vo.getStdCol());
		model.setStdCate(vo.getStdCate());
		model.setStdFlag(vo.getStdFlag());
		model.setTrimFlag(vo.getTrimFlag());
		model.setState(vo.getState());
		
		this.txColMapBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public TxColMapVO show(String sycSysCd,String srcTab,String srcCol) {
		
		TxColMapPK pk = new TxColMapPK();
		pk.setSrcSysCd(sycSysCd);
		pk.setSrcTab(srcTab);
		pk.setSrcCol(srcCol);	
		
		TxColMap t = this.txColMapBS.getEntityById(pk);
		
		TxColMapVO vo = new TxColMapVO();
		vo.setSrcSysCd(t.getId().getSrcSysCd());						
		vo.setSrcTab(t.getId().getSrcTab());
		vo.setSrcCol(t.getId().getSrcCol());				
		vo.setStdTab(t.getStdTab());
		vo.setStdCol(t.getStdCol());
		vo.setStdCate(t.getStdCate());
		vo.setStdFlag(t.getStdFlag());
		vo.setTrimFlag(t.getTrimFlag());
		vo.setState(t.getState());
		
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
		return new ModelAndView("/ecif/core/txcolmap-edit",  mm);
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
		
		return new ModelAndView("/ecif/core/txcolmap-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			TxColMapPK pk = new TxColMapPK();
			pk.setSrcSysCd(ids[0]);
			pk.setSrcTab(ids[1]);
			pk.setSrcCol(ids[2]);	
			
			this.txColMapBS.removeEntityById(pk);
			
		} 
	}


}
