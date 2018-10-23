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
import com.yuchengtech.emp.ecif.syncmanage.entity.TxSyncConf;
import com.yuchengtech.emp.ecif.syncmanage.service.TxSyncConfBS;

/**
 * <pre>
 * Title: 数据同步配置
 * Description:
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/syncmanage/syncconf")
public class TxSyncConfController extends BaseController {
	@Autowired
	private TxSyncConfBS confBS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/syncmanage/txsyncconf-index";
	}
	
	@RequestMapping("/list.json")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		Map<String, Object> ret = Maps.newHashMap();
		SearchResult<TxSyncConf> sr = confBS.getSearchResult(
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
		return new ModelAndView("/ecif/syncmanage/txsyncconf-edit");
	}
	
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	@RequestMapping("{id}/edit")
	public ModelAndView edit(@PathVariable String id) {
		return new ModelAndView("/ecif/syncmanage/txsyncconf-edit", "id", id);
	}
	
	/**
	 * 删除
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable String id) throws Exception  {
		if (!StringUtils.isEmpty(id)) {
			String[] ida = StringUtils.split(id, ",");
			Long[] idla = new Long[ida.length];
			for (int i = 0; i < ida.length; i++) {
				idla[i] = Long.parseLong(ida[i]);
			}
			confBS.batchRemove(Arrays.asList(idla));
		}
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(TxSyncConf entity) {
		if (entity != null) {
			String userName = BiOneSecurityUtils.getCurrentUserInfo().getUserName();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			if (entity.getSyncConfId() != null) {
				entity.setUpdateOper(userName);
				entity.setUpdateTime(time);
				confBS.updateEntity(entity);
			} else {
				entity.setCreateOper(userName);
				entity.setCreateTime(time);
				confBS.saveEntity(entity);
			}
		}
		confBS.saveOrUpdateEntity(entity);
	}
	
	/**
	 * 展示
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/show/{id}")
	@ResponseBody
	public TxSyncConf show(@PathVariable Long id) {
		if (id != null) {
			return confBS.getEntityById(id);
		}
		return null;
	}
}
