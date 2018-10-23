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
import com.yuchengtech.emp.ecif.customer.entity.orgfinance.FinanceBriefReport;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.FinanceBriefReportBS;
@Controller
@RequestMapping("/ecif/orgfinance/financebriefreport")
public class FinanceBriefReportController extends BaseController {
	@Autowired
	private FinanceBriefReportBS FinanceBriefReportBS;
	@Autowired
	private ResultUtil resultUtil;
	protected static Logger log = LoggerFactory
			.getLogger(FinanceBriefReportController.class);
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}


	@RequestMapping(value = "/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public FinanceBriefReport showFinanceBriefReport(@PathVariable("custId") long custId) {
		FinanceBriefReport model = FinanceBriefReportBS.getEntityByProperty(com.yuchengtech.emp.ecif.customer.entity.orgfinance.FinanceBriefReport.class, "custId",custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	}

