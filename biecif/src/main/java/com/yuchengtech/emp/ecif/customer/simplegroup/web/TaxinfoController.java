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
import com.yuchengtech.emp.ecif.customer.entity.orgfinance.Taxinfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.TaxinfoBS;
@Controller
@RequestMapping("/ecif/orgfinance/taxinfo")
public class TaxinfoController extends BaseController {
	@Autowired
	private TaxinfoBS TaxinfoBS;
	@Autowired
	private ResultUtil resultUtil;
	protected static Logger log = LoggerFactory
			.getLogger(TaxinfoController.class);
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}


	@RequestMapping(value = "/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Taxinfo showTaxinfo(@PathVariable("custId") long custId) {
		Taxinfo model = TaxinfoBS.getEntityByProperty(com.yuchengtech.emp.ecif.customer.entity.orgfinance.Taxinfo.class, "custId",custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	}

