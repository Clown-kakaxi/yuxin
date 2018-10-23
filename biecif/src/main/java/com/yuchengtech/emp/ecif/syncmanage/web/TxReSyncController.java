package com.yuchengtech.emp.ecif.syncmanage.web;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxEvtNotice;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxSyncConf;
import com.yuchengtech.emp.ecif.syncmanage.service.TxReSyncBS;
import com.yuchengtech.emp.ecif.syncmanage.service.TxSyncConfBS;

/**
 * <pre>
 * Title: 手动数据同步
 * Description:
 * </pre>
 * 
 * @author Han Feng  hanfeng1@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/syncmanage/resync")
public class TxReSyncController extends BaseController {
	@Autowired
	private TxReSyncBS reSyncBS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/syncmanage/txresync-index";
	}
	
	@RequestMapping("/list.json")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		Map<String, Object> ret = Maps.newHashMap();
		SearchResult<TxEvtNotice> sr = reSyncBS.getSearchResult(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSearchCondition());
		if (sr != null) {
			ret.put("Rows", sr.getResult());
			ret.put("Total", sr.getTotalCount());
		}
		return ret;
	}
	
	
	/**
	 * 新增
	 * @return
	 */
	@RequestMapping("/new")
	public ModelAndView editNew() {
		return new ModelAndView("/ecif/syncmanage/txresync-edit");
	}
	
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	@RequestMapping("{id}/edit")
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("/ecif/syncmanage/txresync-edit", "id", id);
	}
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{id}/resync", method = RequestMethod.POST)
	@ResponseBody
	public void resync(@PathVariable String id) throws Exception  {
		if (!StringUtils.isEmpty(id)) {
			String[] ida = StringUtils.split(id, ",");
			Long[] idla = new Long[ida.length];
			for (int i = 0; i < ida.length; i++) {
				idla[i] = Long.parseLong(ida[i]);
			}
			reSyncBS.batchSync(Arrays.asList(idla));
			
			//confBS.batchRemove(Arrays.asList(idla));
		}
	}
	
	
}
