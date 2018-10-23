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
import com.yuchengtech.emp.ecif.customer.entity.perrisk.Personsocialsecurity;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PersonsocialsecurityBS;
@Controller
@RequestMapping("/ecif/perrisk/personsocialsecurity")
public class PersonsocialsecurityController extends BaseController {
	@Autowired
	private PersonsocialsecurityBS PersonsocialsecurityBS;
	@Autowired
	private ResultUtil resultUtil;
	protected static Logger log = LoggerFactory
			.getLogger(PersonsocialsecurityController.class);
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}


	@RequestMapping(value = "/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Personsocialsecurity showPersonsocialsecurity(@PathVariable("custId") long custId) {
		Personsocialsecurity model = PersonsocialsecurityBS.getEntityByProperty(com.yuchengtech.emp.ecif.customer.entity.perrisk.Personsocialsecurity.class, "custId",custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	}

