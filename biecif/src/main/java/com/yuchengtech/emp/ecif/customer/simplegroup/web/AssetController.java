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
import com.yuchengtech.emp.ecif.customer.entity.asset.Billinfo;
import com.yuchengtech.emp.ecif.customer.entity.asset.Bond;
import com.yuchengtech.emp.ecif.customer.entity.asset.CustomerImasset;
import com.yuchengtech.emp.ecif.customer.entity.asset.CustomerRealty;
import com.yuchengtech.emp.ecif.customer.entity.asset.CustomerVehicle;
import com.yuchengtech.emp.ecif.customer.entity.asset.EntFixedasset;
import com.yuchengtech.emp.ecif.customer.entity.asset.EntInventory;
import com.yuchengtech.emp.ecif.customer.entity.asset.Guaranty;
import com.yuchengtech.emp.ecif.customer.entity.asset.IndOasset;
import com.yuchengtech.emp.ecif.customer.entity.asset.Machine;
import com.yuchengtech.emp.ecif.customer.entity.asset.Otherbankdeposit;
import com.yuchengtech.emp.ecif.customer.entity.asset.Receivable;
import com.yuchengtech.emp.ecif.customer.entity.asset.Right;
import com.yuchengtech.emp.ecif.customer.entity.asset.SmeInveinfo;
import com.yuchengtech.emp.ecif.customer.entity.asset.Stock;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.AssetBS;


/**
 * 
 * 
 * <pre>
 * Title:资产信息的Controller端
 * Description: 处理客户资产信息的CRUD操作
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
@RequestMapping("/ecif/asset")
public class AssetController extends BaseController {

	@Autowired
	private AssetBS assetBS;
	
	@Autowired
	private ResultUtil resultUtil;
	
	protected static Logger log = LoggerFactory
			.getLogger(AssetController.class);

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
	
	// grid形式展示// 展示厂房	SME_INVEINFO的页面
	@RequestMapping(value = "/smeinveinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showSmeInveinfo(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<SmeInveinfo> searchResult = assetBS
				.getSmeInveInfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<SmeInveinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), SmeInveinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示车辆	CUSTOMER_VEHICLE的页面
	@RequestMapping(value = "/customervehicle/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showCustomerVehicle(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<CustomerVehicle> searchResult = assetBS
				.getCustomerVehicleList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<CustomerVehicle> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), CustomerVehicle.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示存货	ENT_INVENTORY的页面
	@RequestMapping(value = "/entinventory/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showEntInventory(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<EntInventory> searchResult = assetBS
				.getEntInventoryList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<EntInventory> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), EntInventory.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示抵质押物	GUARANTY的页面
	@RequestMapping(value = "/guaranty/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showGuaranty(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Guaranty> searchResult = assetBS
				.getGuarantyList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Guaranty> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Guaranty.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示房产	CUSTOMER_REALTY的页面
	@RequestMapping(value = "/customerrealty/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showCustomerRealty(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<CustomerRealty> searchResult = assetBS
				.getCustomerRealtyList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<CustomerRealty> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), CustomerRealty.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示个人其他资产	IND_OASSET的页面
	@RequestMapping(value = "/indoasset/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showIndOasset(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<IndOasset> searchResult = assetBS
				.getIndOassetList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<IndOasset> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), IndOasset.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示固定资产	ENT_FIXEDASSETS的页面
	@RequestMapping(value = "/entfixedassets/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showEntFixedasset(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<EntFixedasset> searchResult = assetBS
				.getEntFixedassetList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<EntFixedasset> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), EntFixedasset.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	// grid形式展示// 展示机器设备	MACHINE的页面
	@RequestMapping(value = "/machine/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showMachine(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Machine> searchResult = assetBS
				.getMachineList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Machine> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Machine.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	
	/**
	 * 票据信息
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/billinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBillinfoList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Billinfo> searchResult = assetBS.getBillInfocustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<Billinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Billinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 债券信息
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/bond/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBondList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Bond> searchResult = assetBS.getBondcustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<Bond> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Bond.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 无形资产信息
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/customerimasset/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCustomerimassetList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<CustomerImasset> searchResult = assetBS.getCustomerImassetcustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<CustomerImasset> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), CustomerImasset.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}	
	
	/**
	 * 他行存款信息
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/otherbankdeposit/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOtherbankdepositList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Otherbankdeposit> searchResult = assetBS.getOtherBankDepositcustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<Otherbankdeposit> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Otherbankdeposit.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 应收账款
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/receivable/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReceivableList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Receivable> searchResult = assetBS.getReceivablecustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<Receivable> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Receivable.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 经营权
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/right/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRightList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Right> searchResult = assetBS.getRightcustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<Right> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Right.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 股票信息
	 * @param pager
	 * @param custId
	 * @return
	 */
	@RequestMapping(value = "/stock/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStockList(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Stock> searchResult = assetBS.getStockcustList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(),custId);
		List<Stock> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Stock.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
}
