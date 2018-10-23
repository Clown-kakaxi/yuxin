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
import com.yuchengtech.emp.ecif.customer.entity.customerother.CustOtherBankActivity;
import com.yuchengtech.emp.ecif.customer.entity.customerother.OrgOtherInfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgOtherBS;


/**
 * 
 * 
 * <pre>
 * Title:机构类客户其他信息的Controller端
 * Description: 处理机构类客户其他信息的CRUD操作
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
@RequestMapping("/ecif/orgother")
public class OrgOtherController extends BaseController {

	@Autowired
	private OrgOtherBS orgOtherBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(OrgOtherController.class);
	
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
	@RequestMapping(value = "/otherinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonInHabInfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<OrgOtherInfo> searchResult = orgOtherBS.getOrgOtherInfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<OrgOtherInfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), OrgOtherInfo.class);
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
			SearchResult<CustOtherBankActivity> searchResult = orgOtherBS.getCustOtherBankActivityList(
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

}
