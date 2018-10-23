package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.util.ArrayList;
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
import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bdsacctinfo;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bdshracctinfo;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bondcontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bugoldcont;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Cesubcredit;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Creditcontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Depositaccount;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Ebankcontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Ebanksignaccount;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Guarantycontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Loanaccount;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Loancontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Oweinterest;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Tbassetacc;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Tbshare;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.AgreementBS;


/**
 * 
 * 
 * <pre>
 * Title:协议信息的Controller端
 * Description: 处理客户协议信息的CRUD操作
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
@RequestMapping("/ecif/agreement")
public class AgreementController extends BaseController {

	@Autowired
	private AgreementBS agreementBS;
	
	@Autowired
	private ResultUtil resultUtil;
	
	protected static Logger log = LoggerFactory
			.getLogger(AgreementController.class);

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
	
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/depositaccount/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showDepositaccount(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Depositaccount> searchResult = agreementBS
					.getDepositaccount(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Depositaccount> list = null;
			List<Depositaccount> list1 = new ArrayList<Depositaccount>();
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Depositaccount.class);
				for(int i=0; i<list.size(); i++){
					Depositaccount temp = new Depositaccount();
					BeanUtils.copy(list.get(i), temp);
					temp.setPrdCode(resultUtil.getProduceName(temp.getPrdCode()));
					list1.add(temp);
					temp = null;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list1);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/loanaccount/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showLoanaccount(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Loanaccount> searchResult = agreementBS
					.getLoanaccount(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Loanaccount> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Loanaccount.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/ebanksignaccount/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showEbanksignaccount(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Ebanksignaccount> searchResult = agreementBS
					.getEbanksignaccount(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Ebanksignaccount> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Ebanksignaccount.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/ebankcontract/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showEbankcontract(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Ebankcontract> searchResult = agreementBS
					.getEbankcontract(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Ebankcontract> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Ebankcontract.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/creditcontract/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showCreditcontract(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Creditcontract> searchResult = agreementBS
					.getCreditcontract(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Creditcontract> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Creditcontract.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/loancontract/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showLoancontract(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Loancontract> searchResult = agreementBS
					.getLoancontract(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Loancontract> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Loancontract.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/guarantycontract/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showGuarantycontract(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Guarantycontract> searchResult = agreementBS
					.getGuarantycontract(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Guarantycontract> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Guarantycontract.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/tbshare/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showTbshare(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Tbshare> searchResult = agreementBS
					.getTbshare(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Tbshare> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Tbshare.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/cesubcredit/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showCesubcredit(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Cesubcredit> searchResult = agreementBS
					.getCesubcredit(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Cesubcredit> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Cesubcredit.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/oweinterest/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showOweinterest(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Oweinterest> searchResult = agreementBS
					.getOweinterest(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Oweinterest> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Oweinterest.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		//理财
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/tbassetacc/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showTbassetacc(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Bdshracctinfo> searchResult = agreementBS
					.getTbassetacc(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Bdshracctinfo> list = null;
			List<Bdshracctinfo> list1 = new ArrayList<Bdshracctinfo>();
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Bdsacctinfo.class);
				for(int i=0; i<list.size(); i++){
					Bdshracctinfo temp = new Bdshracctinfo();
					BeanUtils.copy(list.get(i), temp);
					temp.setPrdCode(resultUtil.getProduceName(temp.getPrdCode()));
					list1.add(temp);
					temp = null;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list1);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/bondcontract/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showBondcontract(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Bondcontract> searchResult = agreementBS
					.getBondcontract(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Bondcontract> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Bondcontract.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
		
		@RequestMapping(value = "/bugoldcont/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showBugoldcont(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Bugoldcont> searchResult = agreementBS
					.getBugoldcont(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Bugoldcont> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Bugoldcont.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
		//凭证式债券
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/bdshracctinfo/list.*", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showBdshracctinfo(Pager pager,
				@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Bdsacctinfo> searchResult = agreementBS
					.getBdshracctinfo(pager.getPageFirstIndex(),
							pager.getPagesize(), pager.getSortname(),
							pager.getSortorder(), pager.getSearchCondition(),
							custId);
			List<Bdsacctinfo> list = null;
			List<Bdsacctinfo> list1 = new ArrayList<Bdsacctinfo>();
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Bdshracctinfo.class);
				for(int i=0; i<list.size(); i++){
					Bdsacctinfo temp = new Bdsacctinfo();
					BeanUtils.copy(list.get(i), temp);
					temp.setPrdCode(resultUtil.getProduceName(temp.getPrdCode()));
					list1.add(temp);
					temp = null;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list1);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
}
