package com.yuchengtech.emp.ecif.core.web;


import java.math.BigInteger;
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
import com.yuchengtech.emp.ecif.core.entity.ColDef;
import com.yuchengtech.emp.ecif.core.entity.ColDefVO;
import com.yuchengtech.emp.ecif.core.entity.TxColDefPK;
import com.yuchengtech.emp.ecif.core.entity.TxStdCode;
import com.yuchengtech.emp.ecif.core.entity.TxStdCodePK;
import com.yuchengtech.emp.ecif.core.entity.TxStdCodeVO;
import com.yuchengtech.emp.ecif.core.service.ColDefBS;

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
@RequestMapping("/ecif/core/coldef")
public class ColDefController extends BaseController {
	@Autowired
	private ColDefBS colDefBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/core/coldef-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/initlist.*")
	@ResponseBody
	public Map<String, Object> initlist(Pager pager,String tabId ) {
		//pager.setCondition(condition);

		SearchResult<ColDef> searchResult = this.colDefBS.getColDefListByTab(pager.getPageFirstIndex(),
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
	public Map<String, Object> list(Pager pager,String tabId ) {
		//pager.setCondition(condition);
		BigInteger iTabId = null;
        if(tabId!=null){
        	iTabId = new BigInteger(tabId);
        }
		SearchResult<ColDef> searchResult = this.colDefBS.getColDefList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),iTabId);
		

		List volist = new ArrayList();
		List list = searchResult.getResult();
		for(int i=0;i<list.size();i++){
			ColDefVO vo = new  ColDefVO();
			ColDef t = (ColDef)list.get(i);
			
			vo.setTabId(t.getId().getTabId());
			vo.setColId(t.getId().getColId());
			vo.setCateId(t.getCateId());
			vo.setColChName(t.getColChName());
			vo.setColDesc(t.getColDesc());
			vo.setColName(t.getColName());
			vo.setColSeq(t.getColSeq());
			vo.setDataFmt(t.getDataFmt());
			vo.setDataLen(t.getDataLen());
			vo.setDataPrec(t.getDataPrec());
			vo.setDataType(t.getDataType());
			vo.setDecode(t.getDecode());
			vo.setIsCode(t.getIsCode());
			vo.setKeyType(t.getKeyType());
			vo.setNulls(t.getNulls());
			vo.setState(t.getState());
			
			volist.add(vo);	
		}
		Map<String, Object> resDefMap = Maps.newHashMap();
		
		resDefMap.put("Rows", volist);
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/alltablecollist.*")
	@ResponseBody
	public List<ColDefVO> alltablecollist(Pager pager,String tabId ) {
		
		BigInteger iTabId = null;
        if(tabId!=null&&!tabId.equals("")){
        	iTabId =new BigInteger(tabId);
        }else{
        	return null;
        }
		SearchResult<ColDef> searchResult = this.colDefBS.getColDefList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),iTabId);

		
		List<ColDef> tabDefList = searchResult.getResult();
		
		List<ColDefVO> voList = new ArrayList(); 
		for(int i=0;i<tabDefList.size();i++){
			ColDef t = (ColDef)tabDefList.get(i);
			
			ColDefVO vo = new ColDefVO();
			vo.setTabId(t.getId().getTabId());
			vo.setColId(t.getId().getColId());
			vo.setCateId(t.getCateId());
			vo.setColChName(t.getColChName());
			vo.setColDesc(t.getColDesc());
			vo.setColName(t.getColName());
			vo.setColSeq(t.getColSeq());
			vo.setDataFmt(t.getDataFmt());
			vo.setDataLen(t.getDataLen());
			vo.setDataPrec(t.getDataPrec());
			vo.setDataType(t.getDataType());
			vo.setDecode(t.getDecode());
			vo.setIsCode(t.getIsCode());
			vo.setKeyType(t.getKeyType());
			vo.setNulls(t.getNulls());
			vo.setState(t.getState());
			
			voList.add(vo);
		}
				
		return voList;
	}
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(ColDefVO vo) {
		
		TxColDefPK pk = new TxColDefPK();
		pk.setTabId(vo.getTabId());
		if(vo.getColId()!=null){
			pk.setColId(vo.getColId());
		}
		
		ColDef model  = new ColDef();
		model.setId(pk);
		model.setCateId(vo.getCateId());
		model.setColChName(vo.getColChName());
		model.setColDesc(vo.getColDesc());
		model.setColName(vo.getColName());
		model.setColSeq(vo.getColSeq());
		model.setDataFmt(vo.getDataFmt());
		model.setDataLen(vo.getDataLen());
		model.setDataPrec(vo.getDataPrec());
		model.setDataType(vo.getDataType());
		model.setDecode(vo.getDecode());
		model.setIsCode(vo.getIsCode());
		model.setKeyType(vo.getKeyType());
		model.setNulls(vo.getNulls());
		model.setState(vo.getState());
		
		this.colDefBS.updateEntity(model);		
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public ColDefVO show(Long tabId,Long colId) {
		TxColDefPK pk = new TxColDefPK();
		pk.setTabId(tabId);
		pk.setColId(colId);
		
		ColDef t = this.colDefBS.getEntityById(pk);
		
		ColDefVO vo = new ColDefVO();
		vo.setTabId(t.getId().getTabId());
		vo.setColId(t.getId().getColId());
		vo.setCateId(t.getCateId());
		vo.setColChName(t.getColChName());
		vo.setColDesc(t.getColDesc());
		vo.setColName(t.getColName());
		vo.setColSeq(t.getColSeq());
		vo.setDataFmt(t.getDataFmt());
		vo.setDataLen(t.getDataLen());
		vo.setDataPrec(t.getDataPrec());
		vo.setDataType(t.getDataType());
		vo.setDecode(t.getDecode());
		vo.setIsCode(t.getIsCode());
		vo.setKeyType(t.getKeyType());
		vo.setNulls(t.getNulls());
		vo.setState(t.getState());

		
		return vo;

	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/core/coldef-edit", "id", new Long(id));
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(Long tabId,Long colId) {
		ModelMap mm = new ModelMap();
		mm.put("tabId", tabId);
		mm.put("colId", colId);
		
		return new ModelAndView("/ecif/core/coldef-edit",  mm);
	}
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(Long tabId) {
		ModelMap mm = new ModelMap();
		mm.put("tabId", tabId);
		
		return new ModelAndView("/ecif/core/coldef-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
//		String[] ids = id.split(",");
//		if (ids.length > 1) {
//			this.colDefBS.removeEntityByProperty("tabId", id);
//		} else {
//			this.colDefBS.removeEntityById(new Long(id));
//		}
//		
		String[] ids = id.split(",");
		if (ids.length > 1) {
			TxColDefPK pk = new TxColDefPK();
			pk.setTabId(new Long(ids[0]));
			pk.setColId(new Long(ids[1]));
			
			this.colDefBS.removeEntityById(pk);
		}
	}


	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		return this.colDefBS.getComBoBox();
	}
	
}
