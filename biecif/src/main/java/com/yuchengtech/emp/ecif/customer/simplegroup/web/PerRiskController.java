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
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.Defaultinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.Mendinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.Overinterest;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.Rating;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.Risksignal;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.SpecialList;
import com.yuchengtech.emp.ecif.customer.entity.customerriskperson.Personbusiinsurance;
import com.yuchengtech.emp.ecif.customer.entity.customerriskperson.Persongjj;
import com.yuchengtech.emp.ecif.customer.entity.customerriskperson.Personsocialinsurance;
import com.yuchengtech.emp.ecif.customer.entity.customerriskperson.Personsocialsecurity;
import com.yuchengtech.emp.ecif.customer.entity.customerriskperson.Personsupervision;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerRiskBS;


/**
 * 
 * 
 * <pre>
 * Title:个人风险信息的Controller端
 * Description: 处理个人风险信息的CRUD操作
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
@RequestMapping("/ecif/perrisk/old")
public class PerRiskController extends BaseController {

	@Autowired
	private PerRiskBS perRiskBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(PerRiskController.class);
	
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
	@RequestMapping(value = "/rating/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showRating(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Rating> searchResult = perRiskBS.getRatingList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Rating> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Rating.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	
	//显示grid数据
	@RequestMapping(value = "/defaultinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showDefaultinfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Defaultinfo> searchResult = perRiskBS.getDefaultinfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Defaultinfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Defaultinfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	//显示grid数据
	@RequestMapping(value = "/overinterest/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showOverinterest(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Overinterest> searchResult = perRiskBS.getOverinterestList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Overinterest> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Overinterest.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/mendinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showMendinfo(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Mendinfo> searchResult = perRiskBS.getMendinfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Mendinfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Mendinfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}

	@RequestMapping(value = "/specialist/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showSpecialList(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<SpecialList> searchResult = perRiskBS.getSpecialListList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<SpecialList> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), SpecialList.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/risksignal/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showRisksignal(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Risksignal> searchResult = perRiskBS.getRisksignalList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Risksignal> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Risksignal.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}

	@RequestMapping(value = "/persupervision/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonsupervision(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Personsupervision> searchResult = perRiskBS.getPersonsupervisionList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Personsupervision> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Personsupervision.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}

	@RequestMapping(value = "/persocialinsurance/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonsocialinsurance(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Personsocialinsurance> searchResult = perRiskBS.getPersonsocialinsuranceList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Personsocialinsurance> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Personsocialinsurance.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/perbusiinsurance/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonbusiinsurance(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Personbusiinsurance> searchResult = perRiskBS.getPersonbusiinsuranceList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Personbusiinsurance> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Personbusiinsurance.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/pergjj/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersongjj(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Persongjj> searchResult = perRiskBS.getPersongjjList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Persongjj> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Persongjj.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/persocialsecurity/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Personsocialsecurity showPersonProfile(@PathVariable("custId") long custId) {
		Personsocialsecurity model = perRiskBS.getEntityById(Personsocialsecurity.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
