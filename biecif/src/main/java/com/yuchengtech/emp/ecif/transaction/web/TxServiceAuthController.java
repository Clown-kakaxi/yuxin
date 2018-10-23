package com.yuchengtech.emp.ecif.transaction.web;

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
import com.yuchengtech.emp.biappframe.base.common.ParamInfoHolder;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.transaction.entity.TxServiceAuth;
import com.yuchengtech.emp.ecif.transaction.service.TxServiceAuthBS;

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
@RequestMapping("/ecif/transaction/txserviceauth")
public class TxServiceAuthController extends BaseController {
	@Autowired
	private TxServiceAuthBS txServiceAuthBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/transaction/txserviceauth-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager,String clientAuthId) {
		SearchResult<TxServiceAuth> searchResult = this.txServiceAuthBS.getTxServiceAuthList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),clientAuthId);
		
		List<TxServiceAuth> txServiceAuthList = searchResult.getResult();

		Map<String, String> paramInfoMap = ParamInfoHolder.getParamInfo(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo(), "AUTH_TYPE");
		
		for (TxServiceAuth txServiceAuth : txServiceAuthList) {		
			txServiceAuth.setAuthType(paramInfoMap.get(txServiceAuth.getAuthType()));

		}
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxServiceAuth model) {
		this.txServiceAuthBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxServiceAuth show(@PathVariable("id") Long id) {
		TxServiceAuth model = this.txServiceAuthBS.getEntityById(id);
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
		return new ModelAndView("/ecif/transaction/txserviceauth-edit", "id", new Long(id));
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(Long clientAuthId) {
		ModelMap mm = new ModelMap();
		mm.put("clientAuthId", clientAuthId);
		//return "/ecif/transaction/txserviceauth-edit";
		
		return new ModelAndView("/ecif/transaction/txserviceauth-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txServiceAuthBS.removeEntityByProperty("serviceAuthId", id);
		} else {
			this.txServiceAuthBS.removeEntityById(new Long(id));
		}
	}


	@RequestMapping(value = "/checkserviceId", method = RequestMethod.GET)
	@ResponseBody
	public String checkGroupName(String txCode,Long clientAuthId) {

		boolean flag = this.txServiceAuthBS.checkGroupName(clientAuthId, txCode);
		return flag ? "true" : "false";
	}
	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		return this.txServiceAuthBS.getComBoBox();
	}
	
}
