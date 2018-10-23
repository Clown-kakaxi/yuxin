package com.yuchengtech.emp.ecif.syncmanage.web;


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
import com.yuchengtech.emp.ecif.syncmanage.entity.TxSyncLog;
import com.yuchengtech.emp.ecif.syncmanage.service.TxSyncLogBS;

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
@RequestMapping("ecif/syncmanage/txsynclog")
public class TxSyncLogController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxSyncLogController.class);

	@Autowired
	private TxSyncLogBS txSyncLogBS;
	
	@Autowired
	private TxSycSystemBS txSrcSystemBS;
	
	@RequestMapping(value = "/monitor",method = RequestMethod.GET)
	public String monitor() {
		return "/ecif/syncmanage/txmonitor-index";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/syncmanage/txsynclog-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxSyncLog> searchResult = this.txSyncLogBS.getTxSyncLogList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		
		Map map = getSrcSystemMap();
		
//		for(TxSyncLog log:searchResult.getResult() ){
//			Object o =  map.get(log.getSrcSysNo()==null?null:(String)log.getSrcSysNo());
//			String syssNm = o==null?null:(String)o;
//			log.setSrcSysNm(syssNm);
//		}
//		
		return resDefMap;
	}


	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxSyncLog show(@PathVariable("id") Long id) {
		TxSyncLog model = this.txSyncLogBS.getEntityById(id);
		model.setSyncReqMsg(model.getSyncReqMsg()==null?null:XMLFormat.format(model.getSyncReqMsg()));
		
		model.setSyncResMsg(model.getSyncResMsg()==null?null:XMLFormat.format(model.getSyncResMsg()));		
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
		return new ModelAndView("/ecif/syncmanage/txsynclog-edit", "id", id);
		
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
