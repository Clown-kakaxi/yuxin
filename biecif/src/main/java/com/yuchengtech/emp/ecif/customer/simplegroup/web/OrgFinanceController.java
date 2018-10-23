package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg.FinanceBriefReport;
import com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg.Issuebond;
import com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg.Issuestock;
import com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg.Orgbusiinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg.Orgotherassetdebt;
import com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg.Taxinfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgFinanceBS;

/**
 * 
 * 
 * <pre>
 * Title:机构类客户财务信息的Controller端
 * Description: 处理机构财务信息的CRUD操作
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
@RequestMapping("/ecif/orgfinance")
public class OrgFinanceController extends BaseController {

	@Autowired
	private OrgFinanceBS orgFinanceBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(OrgFinanceController.class);
	
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

	// grid形式展示
	@RequestMapping(value = "/taxinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showPersonAsset(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Taxinfo> searchResult = orgFinanceBS.getTaxinfoList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<Taxinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Taxinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	// 显示grid数据
	@RequestMapping(value = "/issuestock/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showRelative(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Issuestock> searchResult = orgFinanceBS.getIssuestockList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<Issuestock> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Issuestock.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	// 显示grid数据
	@RequestMapping(value = "/briefreport/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showPerforeassu(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<FinanceBriefReport> searchResult = orgFinanceBS
				.getFinanceBriefReportList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<FinanceBriefReport> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), FinanceBriefReport.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	// 显示grid数据
	@RequestMapping(value = "/orgbusiinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOrgbusiinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orgbusiinfo> searchResult = orgFinanceBS
				.getOrgbusiinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orgbusiinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgbusiinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	// 显示grid数据
	@RequestMapping(value = "/issuebond/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showIssuebond(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Issuebond> searchResult = orgFinanceBS.getIssuebondList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<Issuebond> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Issuebond.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	// 显示grid数据
	@RequestMapping(value = "/otherassetdebt/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOrgotherassetdebt(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orgotherassetdebt> searchResult = orgFinanceBS
				.getOrgotherassetdebtList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orgotherassetdebt> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgotherassetdebt.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

}
