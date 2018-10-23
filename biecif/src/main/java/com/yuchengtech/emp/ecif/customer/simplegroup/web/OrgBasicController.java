/**
 * 
 */
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
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Basicaccount;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgextend;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgidentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgkeyflag;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgkeyindex;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgkeyno;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgprofile;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Otheraccount;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Registerinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.NameTitle;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgBasicBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author caiqy caiqy@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/orgbasic")
public class OrgBasicController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(OrgBasicController.class);
	
	@Autowired
	private ResultUtil resultUtil;

	@Autowired
	private OrgBasicBS orgBasicBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/customer/simplegroup/org-basic-index";
	}

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

	@RequestMapping(value = "/nametitle/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public NameTitle showNameTitle(@PathVariable("custId") long custId) {
		NameTitle model = orgBasicBS.getEntityById(NameTitle.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/orgextend/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Orgextend showOrgextend(@PathVariable("custId") long custId) {
		Orgextend model = orgBasicBS.getEntityById(Orgextend.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/orgprofile/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Orgprofile showOrgprofile(@PathVariable("custId") long custId) {
		Orgprofile model = orgBasicBS.getEntityById(Orgprofile.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/orgkeyno/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Orgkeyno showOrgkeyno(@PathVariable("custId") long custId) {
		Orgkeyno model = orgBasicBS.getEntityById(Orgkeyno.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/orgkeyindex/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Orgkeyindex showOrgkeyindex(@PathVariable("custId") long custId) {
		Orgkeyindex model = orgBasicBS.getEntityById(Orgkeyindex.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/orgkeyflag/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Orgkeyflag showOrgkeyflag(@PathVariable("custId") long custId) {
		Orgkeyflag model = orgBasicBS.getEntityById(Orgkeyflag.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/registerinfo/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Registerinfo showRegisterinfo(@PathVariable("custId") long custId) {
		Registerinfo model = orgBasicBS.getEntityById(Registerinfo.class,
				custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/basicaccount/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Basicaccount showBasicaccount(@PathVariable("custId") long custId) {
		Basicaccount model = orgBasicBS.getEntityById(Basicaccount.class,
				custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
			model.setBasicAcctName("");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	// grid形式展示
	@RequestMapping(value = "/otheraccount/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showOtheraccount(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Otheraccount> searchResult = orgBasicBS
				.getOtheraccountList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Otheraccount> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Otheraccount.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

	// grid形式展示
	@RequestMapping(value = "/orgidentinfo/list.*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showEduresume(Pager pager,
			@RequestParam("custId") long custId) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<Orgidentinfo> searchResult = orgBasicBS
				.getOrgidentinfoList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition(),
						custId);
		List<Orgidentinfo> list = null;
		try {
			list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgidentinfo.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}

}
