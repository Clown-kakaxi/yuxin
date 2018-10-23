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
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.entity.customerdealings.Orgchannelsign;
import com.yuchengtech.emp.ecif.customer.entity.customerdealings.Orgspecialcust;
import com.yuchengtech.emp.ecif.customer.entity.customerdealings.Personchannelsign;
import com.yuchengtech.emp.ecif.customer.entity.customerdealings.PersonchannelsignVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgDealingBS;


/**
 * 
 * 
 * <pre>
 * Title:机构类客户往来信息的Controller端
 * Description: 处理机构类客户往来信息的CRUD操作
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
@RequestMapping("/ecif/orgdealing")
public class OrgDealingController extends BaseController {

	@Autowired
	private OrgDealingBS orgDealingBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(OrgDealingController.class);
	
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
	@RequestMapping(value = "/specialcust/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonspecialcust(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Orgspecialcust> searchResult = orgDealingBS.getOrgspecialcustList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Orgspecialcust> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgspecialcust.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	
	//显示grid数据
	@RequestMapping(value = "/orgchannelsign/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showPersonchannelsign(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Orgchannelsign> searchResult = orgDealingBS.getOrgchannelsignList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<PersonchannelsignVO> listVO = new ArrayList<PersonchannelsignVO>();
			try {
				List<Orgchannelsign> list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Orgchannelsign.class);
				if(list != null && list.size() > 0){
					for(Orgchannelsign personchannelsign : list){
						PersonchannelsignVO personchannelsignVO = new PersonchannelsignVO();
						personchannelsignVO.setChannelId(personchannelsign.getChannelId());
						personchannelsignVO.setChannelName(resultUtil.getChannelName(personchannelsign.getChannelId()));
						personchannelsignVO.setCustChannelSignId(personchannelsign.getCustChannelSignId());
						personchannelsignVO.setCustId(personchannelsign.getCustId());
						personchannelsignVO.setSignType(personchannelsign.getSignType());
						listVO.add(personchannelsignVO);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", listVO);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
}
