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
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Actualcontroller;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Agentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Executiveinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Holderinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Legalreprinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Operatorinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orgassuinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orginvestinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orgkeylinkman;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orglinkman;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Relativecorp;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Seller;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Supplier;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Updownstreaminfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgRelativeBS;

/**
 * 
 * 
 * <pre>
 * Title:机构关联信息的Controller端
 * Description: 处理机构关联信息的CRUD操作
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
@RequestMapping("/ecif/orgrelative")
public class OrgRelativeController extends BaseController {

	@Autowired
	private OrgRelativeBS orgRelativeBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(OrgRelativeController.class);
	
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

	@RequestMapping(value = "/actualcontroller/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showActualcontroller(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Actualcontroller> searchResult = orgRelativeBS
				.getActualcontrollerList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Actualcontroller> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Actualcontroller.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/agentinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showRelative(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Agentinfo> searchResult = orgRelativeBS.getAgentinfoList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<Agentinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Agentinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/executiveinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showExecutiveinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Executiveinfo> searchResult = orgRelativeBS
				.getExecutiveinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Executiveinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Executiveinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/holderinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showHolderinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Holderinfo> searchResult = orgRelativeBS
				.getHolderinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Holderinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Holderinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/legalreprinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showLegalreprinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Legalreprinfo> searchResult = orgRelativeBS
				.getLegalreprinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Legalreprinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Legalreprinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/operatorinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOperatorinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Operatorinfo> searchResult = orgRelativeBS
				.getOperatorinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Operatorinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Operatorinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/orgassuinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOrgassuinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orgassuinfo> searchResult = orgRelativeBS
				.getOrgassuinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orgassuinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgassuinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/orginvestinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOrginvestinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orginvestinfo> searchResult = orgRelativeBS
				.getOrginvestinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orginvestinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orginvestinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/orgkeylinkman/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOrgkeylinkman(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orgkeylinkman> searchResult = orgRelativeBS
				.getOrgkeylinkmanList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orgkeylinkman> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgkeylinkman.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/orglinkman/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOrglinkman(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orglinkman> searchResult = orgRelativeBS
				.getOrglinkmanList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orglinkman> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orglinkman.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/relativecorp/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showRelativecorp(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Relativecorp> searchResult = orgRelativeBS
				.getRelativecorpList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Relativecorp> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Relativecorp.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/seller/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showSeller(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Seller> searchResult = orgRelativeBS.getSellerList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<Seller> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Seller.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/supplier/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showSupplier(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Supplier> searchResult = orgRelativeBS.getSupplierList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		List<Supplier> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Supplier.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	@RequestMapping(value = "/updownstreaminfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showUpdownstreaminfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Updownstreaminfo> searchResult = orgRelativeBS
				.getUpdownstreaminfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Updownstreaminfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Updownstreaminfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
}
