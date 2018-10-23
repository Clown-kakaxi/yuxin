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
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.Career;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.EduResume;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.Family;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.IdentityVerify;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.JobResume;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.NameTitle;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonExtend;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonKeyFlag;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonKeyIndex;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonProfile;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerBasicBS;

/**
 * 
 * 
 * <pre>
 * Title:个人客户基本信息的Controller端
 * Description: 处理个人信息的CRUD操作
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
@RequestMapping("/ecif/perbasic")
public class PerBasicController extends BaseController {

	@Autowired
	private PerBasicBS perBasicBS;
	
	@Autowired
	private ResultUtil resultUtil;
	
	protected static Logger log = LoggerFactory
			.getLogger(PerBasicController.class);

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

	/**
	 * 修改 加载页面
	 */
	@RequestMapping(value = "/personprofile/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public PersonProfile showPersonProfile(@PathVariable("custId") long custId) {
		
		PersonProfile model = perBasicBS.getEntityById(PersonProfile.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/nametitle/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public NameTitle showNameTitle(@PathVariable("custId") long custId) {
		NameTitle model = perBasicBS.getEntityById(NameTitle.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/jobresume/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showJobresume(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<JobResume> searchResult = perBasicBS.getJobResumeList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<JobResume> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), JobResume.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/career/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Career showCareer(@PathVariable("custId") long custId) {
		Career model = perBasicBS.getEntityById(Career.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	//grid形式展示
	@RequestMapping(value = "/eduresume/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showEduresume(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<EduResume> searchResult = perBasicBS.getEduResumeList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<EduResume> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), EduResume.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/family/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Family showFamily(@PathVariable("custId") long custId) {
		Family model = perBasicBS.getEntityById(Family.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/identityverify/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public IdentityVerify showIdentityVerify(@PathVariable("custId") long custId) {
		IdentityVerify model = perBasicBS.getEntityById(IdentityVerify.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	//显示grid数据
	@RequestMapping(value = "/personidentifier/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonIdentifier(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<PersonIdentifier> searchResult = perBasicBS.getPersonIdentifierList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<PersonIdentifier> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), PersonIdentifier.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/personextend/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public PersonExtend showPersonExtend(@PathVariable("custId") long custId) {
		PersonExtend model = perBasicBS.getEntityById(PersonExtend.class, custId);
		PersonExtend tranModel = null;
		try {
			tranModel = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tranModel;
	}
	
	@RequestMapping(value = "/personkeyflag/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public PersonKeyFlag showPersonKeyFlag(@PathVariable("custId") long custId) {
		PersonKeyFlag model = perBasicBS.getEntityById(PersonKeyFlag.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/personkeyindex/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public PersonKeyIndex showPersonKeyindex(@PathVariable("custId") long custId) {
		PersonKeyIndex model = perBasicBS.getEntityById(PersonKeyIndex.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
