package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.entity.customerother.ConsInfo;
import com.yuchengtech.emp.ecif.customer.entity.customerother.CustOtherBankActivity;
import com.yuchengtech.emp.ecif.customer.entity.customerother.PersonAssetMaxBranch;
import com.yuchengtech.emp.ecif.customer.entity.customerother.PersonInHabInfo;
import com.yuchengtech.emp.ecif.customer.entity.customerother.PersonOtherInfo;
import com.yuchengtech.emp.ecif.customer.entity.customerother.Vipcardinfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerOtherBS;


/**
 * 
 * 
 * <pre>
 * Title:个人类客户其他信息的Controller端
 * Description: 处理个人类客户其他信息的CRUD操作
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/perother")
public class PerOtherController extends BaseController {

	@Autowired
	private PerOtherBS perOtherBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(PerOtherController.class);
	
	@Autowired
	private ResultUtil resultUtil;

	/**
	 * 跳转 修改 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}
	
	//grid形式展示
	@RequestMapping(value = "/inhabinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonInHabInfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<PersonInHabInfo> searchResult = perOtherBS.getPersonInHabInfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<PersonInHabInfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), PersonInHabInfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	
	//显示grid数据
	@RequestMapping(value = "/otherinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonOtherInfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<PersonOtherInfo> searchResult = perOtherBS.getPersonOtherInfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<PersonOtherInfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), PersonOtherInfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	//显示grid数据
	@RequestMapping(value = "/consinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showConsInfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<ConsInfo> searchResult = perOtherBS.getConsInfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<ConsInfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), ConsInfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/otherbankactivity/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showCustOtherBankActivity(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<CustOtherBankActivity> searchResult = perOtherBS.getCustOtherBankActivityList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<CustOtherBankActivity> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), CustOtherBankActivity.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}

	@RequestMapping(value = "/assetmaxbranch/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public PersonAssetMaxBranch showPersonProfile(@PathVariable("custId") long custId) {
		PersonAssetMaxBranch model = perOtherBS.getEntityById(PersonAssetMaxBranch.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/vipcardinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showVipCardInfo(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Vipcardinfo> searchResult = perOtherBS.getVipCardInfos(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<CustOtherBankActivity> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Vipcardinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
}
