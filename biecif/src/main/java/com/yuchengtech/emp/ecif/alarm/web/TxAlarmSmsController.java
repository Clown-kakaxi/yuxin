package com.yuchengtech.emp.ecif.alarm.web;


import java.text.SimpleDateFormat;
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
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmSms;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmSmsVO;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmSms;
import com.yuchengtech.emp.ecif.alarm.service.TxAlarmSmsBS;

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
@RequestMapping("/ecif/alarm/txalarmsms")
public class TxAlarmSmsController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxAlarmSmsController.class);

	@Autowired
	private TxAlarmSmsBS txAlarmSmsBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/alarm/txalarmsms-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		String loginName = user.getLoginName();
		
		SearchResult<TxAlarmSms> searchResult = this.txAlarmSmsBS.getTxAlarmSmsList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition(),loginName);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
	}

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxAlarmSms model) {
		this.txAlarmSmsBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxAlarmSmsVO show(@PathVariable("id") Long id) {
		TxAlarmSms model = this.txAlarmSmsBS.getEntityById(id);
		SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");    
		String sd = sdf.format(model.getOccurTime());
		TxAlarmSmsVO vo = new TxAlarmSmsVO();  
		BeanUtils.copyProperties(model, vo);
		vo.setOccurTimeStr(sd);
		
		return vo;
	}
	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		TxAlarmSms model = this.txAlarmSmsBS.getEntityById(id);
		model.setAlarmStat("2");
		this.txAlarmSmsBS.updateEntity(model);
		return new ModelAndView("/ecif/alarm/txalarmsms-edit", "id", id);
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/alarm/txalarmsms-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txAlarmSmsBS.removeEntityByProperty("groupId", id);
		} else {
			this.txAlarmSmsBS.removeEntityById(new Long(id));
		}
	}
	

	
}
