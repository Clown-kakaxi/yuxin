package com.yuchengtech.emp.ecif.transaction.web;


import java.util.HashMap;
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
import com.yuchengtech.emp.ecif.base.util.XMLFormat;
import com.yuchengtech.emp.ecif.core.entity.SrcSystem;
import com.yuchengtech.emp.ecif.core.service.TxSycSystemBS;
import com.yuchengtech.emp.ecif.transaction.entity.TxLog;
import com.yuchengtech.emp.ecif.transaction.service.TxLogBS;

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
@RequestMapping("ecif/transaction/txlog")
public class TxLogController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxLogController.class);

	@Autowired
	private TxLogBS txLogBS;
	
	@Autowired
	private TxSycSystemBS txSrcSystemBS;
	
	@RequestMapping(value = "/monitor",method = RequestMethod.GET)
	public String monitor() {
		return "/ecif/transaction/txmonitor-index";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/transaction/txlog-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxLog> searchResult = this.txLogBS.getTxLogList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		
		Map map = getSrcSystemMap();
		
		for(TxLog log:searchResult.getResult() ){
			Object o =  map.get(log.getSrcSysCd()==null?null:(String)log.getSrcSysCd());
			String syssNm = o==null?null:(String)o;
			log.setSrcSysNm(syssNm);
			
		}
		
		return resDefMap;
	}


	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxLog show(@PathVariable("id") Long id) {
		TxLog model = this.txLogBS.getEntityById(id);
		
		model.setReqMsg(model.getReqMsg()==null?null:XMLFormat.format(model.getReqMsg()));
		
		model.setResMsg(model.getResMsg()==null?null:XMLFormat.format(model.getResMsg()));

		return model;
	}


	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/transaction/txlog-edit", "id", id);
		
	}

	/**
	 * 返回源系统的map
	 * @return
	 */
	public Map getSrcSystemMap(){
		
		Map map = new HashMap();
		
		SearchResult<SrcSystem> searchResult = this.txSrcSystemBS.getSrcSystemList(0,
				Integer.MAX_VALUE, null, null, new HashMap());

		for(SrcSystem sys:searchResult.getResult() ){
			map.put(sys.getSrcSysCd(), sys.getSrcSysNm());
		}       
		
		return map;
	}
	
}
