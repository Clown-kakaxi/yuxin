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
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Creditinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Orgauth;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Orggrade;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Orgstarlevel;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Scoreinfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgEvaluateBS;


/**
 * 
 * 
 * <pre>
 * Title:机构评价信息的Controller端
 * Description: 处理机构评价信息的CRUD操作
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
@RequestMapping("/ecif/orgevaluate/old")
public class OrgEvaluateController extends BaseController {

	@Autowired
	private OrgEvaluateBS orgEvaluateBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(OrgEvaluateController.class);
	
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
	@RequestMapping(value = "/scoreinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showScoreinfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Scoreinfo> searchResult = orgEvaluateBS.getScoreinfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Scoreinfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Scoreinfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	
	//显示grid数据
	@RequestMapping(value = "/creditinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showCreditinfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Creditinfo> searchResult = orgEvaluateBS.getCreditinfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Creditinfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Creditinfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	//显示grid数据
	@RequestMapping(value = "/orgauth/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonstarlevel(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Orgauth> searchResult = orgEvaluateBS.getOrgauthList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Orgauth> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgauth.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/orggrade/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersongrade(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Orggrade> searchResult = orgEvaluateBS.getOrggradeList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Orggrade> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orggrade.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}

	@RequestMapping(value = "/orgstarlevel/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonpoint(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Orgstarlevel> searchResult = orgEvaluateBS.getOrgstarlevelList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Orgstarlevel> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgstarlevel.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
}
