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
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson.LinkMan;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson.MateInfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson.PersonForeAssu;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson.RelativeInfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerRelativeBS;


/**
 * 
 * 
 * <pre>
 * Title:个人关联信息的Controller端
 * Description: 处理个人关联信息的CRUD操作
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
@RequestMapping("/ecif/perrelative")
public class PerRelativeController extends BaseController {

	@Autowired
	private PerRelativeBS perRelativeBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(PerRelativeController.class);
	
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
	@RequestMapping(value = "/linkman/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showLinkman(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<LinkMan> searchResult = perRelativeBS.getLinkManList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<LinkMan> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), LinkMan.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	
	//显示grid数据
	@RequestMapping(value = "/relative/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showRelative(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<RelativeInfo> searchResult = perRelativeBS.getRelativeInfoList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<RelativeInfo> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), RelativeInfo.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	//显示grid数据
	@RequestMapping(value = "/perforeassu/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPerforeassu(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<PersonForeAssu> searchResult = perRelativeBS.getPerforeassuList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<PersonForeAssu> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), PersonForeAssu.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/mateinfo/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public MateInfo showPersonProfile(@PathVariable("custId") long custId) {
		MateInfo model = perRelativeBS.getEntityById(MateInfo.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
