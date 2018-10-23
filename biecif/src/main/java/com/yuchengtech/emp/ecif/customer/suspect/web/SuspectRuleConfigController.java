/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.SpecialList;
import com.yuchengtech.emp.ecif.customer.entity.other.SpecialListApproval;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectRuleConfig;
import com.yuchengtech.emp.ecif.customer.suspect.service.SuspectRuleConfigBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/customer/suspectruleconfig")
public class SuspectRuleConfigController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(SuspectRuleConfigController.class);

	@Autowired
	private SuspectRuleConfigBS suspectRuleConfigBS;
	
	//一次进入不查询
	private boolean flag = false;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/suspect/suspectruleconfig-index";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		if(flag == false){
			flag = true;
			return null;
		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<SuspectRuleConfig> searchResult = suspectRuleConfigBS.getSuspectRuleConfigList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());	
		userMap.put("Rows", searchResult.getResult());
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 执行更新操作
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		String[] ids = id.split(",");
		//
		if (ids.length > 0) {
			SuspectRuleConfig src = (SuspectRuleConfig) this.suspectRuleConfigBS.getEntityById(ids);
//			sa.setOperator(user.getUserName());
//			sa.setOperTime(new Timestamp(new Date().getTime()));
			//TODO
			this.suspectRuleConfigBS.updateEntity(src);
		}
		return "true";
	}
	
}
