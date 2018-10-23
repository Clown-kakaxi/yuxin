package com.yuchengtech.emp.ecif.alarm.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmRule;
import com.yuchengtech.emp.ecif.alarm.service.TxAlarmRuleBS;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成表的CRUD操作 
 * </pre>	
 * @author shangjf  shangjf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：尚吉峰		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/alarm/txalarmrule")
public class TxAlarmRuleController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxAlarmRuleController.class);

	@Autowired
	private TxAlarmRuleBS txAlarmRuleBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/alarm/txalarmrule-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxAlarmRule> searchResult = this.txAlarmRuleBS.getTxAlarmRuleList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxAlarmRule model) {
		this.txAlarmRuleBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxAlarmRule show(@PathVariable("id") Long id) {
		TxAlarmRule model = this.txAlarmRuleBS.getEntityById(id);
		return model;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/alarm/txalarmrule-edit", "id", id);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/alarm/txalarmrule-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txAlarmRuleBS.removeEntityByProperty("alarmRuleId", id);
		} else {
			this.txAlarmRuleBS.removeEntityById(new Long(id));
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String clientAuthId) {
		return new ModelAndView("/ecif/alarm/txserviceauth-index", "clientAuthId",
				clientAuthId);
	}
	

	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(String id,String groupName) {
		
		String groupId = this.txAlarmRuleBS.getGroupName(groupName);
		List<TxAlarmRule> model = txAlarmRuleBS.findEntityListByProperty("groupId", groupId);
		if (model != null){	
			for(int i=0;i<model.size();i++){
				TxAlarmRule t = model.get(i);
				if(t.getAlarmRuleId().toString().equals(id)){ //修改
					return true;
				}else{
					return false;
				}
			}
			
		}else
			return true;

		return true;
	}

	
}
