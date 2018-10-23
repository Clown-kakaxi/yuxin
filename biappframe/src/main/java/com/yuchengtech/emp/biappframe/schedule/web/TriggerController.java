package com.yuchengtech.emp.biappframe.schedule.web;

import java.sql.Timestamp;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.TriggerInfoHolder;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTriggerInfo;
import com.yuchengtech.emp.biappframe.schedule.service.TaskBS;
import com.yuchengtech.emp.biappframe.schedule.service.TriggerBS;
import com.yuchengtech.emp.biappframe.schedule.web.vo.BioneTriggerInfoVO;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.bione.util.DateUtils;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title:触发器管理类
 * Description:
 * </pre>
 * 
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/schedule/trigger")
public class TriggerController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(TriggerController.class);
	@Autowired
	private TriggerBS triggerBS;
	@Autowired
	private TaskBS taskBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/trigger/trigger-index";
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneTriggerInfo> triggerResult = this.triggerBS
				.getTriggerList(pager.getPageFirstIndex(), pager.getPagesize(),
						pager.getSortname(), pager.getSortorder(),
						pager.getSearchCondition());
		Map<String, Object> triggerMap = Maps.newHashMap();
		triggerMap.put("Rows", triggerResult.getResult());
		triggerMap.put("Total", triggerResult.getTotalCount());
		return triggerMap;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/trigger/trigger-editNew";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/trigger/trigger-edit", "id", id);
	}

	@RequestMapping("/showTriggerInfo.*")
	@ResponseBody
	public BioneTriggerInfo showTriggerInfo(String id) {
		return this.triggerBS.getEntityById(id);
	}

	@RequestMapping("/checkHasJobOrNot")
	@ResponseBody
	public boolean checkHasJobOrNot(String ids) {
		if (ids != null && !"".equals(ids)) {
			String names = this.triggerBS.checkHasJobTriggers(ids);
			if (names == null || names.equals(""))
				return true;
		}
		return false;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(String id) {
		if (id != null && !"".equals(id)) {
			String[] ids = id.split(",");
			this.triggerBS.deleteBatch(ids);
		}
	}

	@RequestMapping("/destroyOwn")
	public void destroyOwn(String idStr) {
		if (idStr != null && !"".equals(idStr)) {
			String[] ids = idStr.split(",");
			this.triggerBS.deleteBatch(ids);
		}
	}

	@RequestMapping("/checkRunningJob")
	@ResponseBody
	public boolean checkRunningJob(String triggerId, String cron) {
		if (triggerId != null && !"".equals(triggerId) && cron != null
				&& !"".equals(cron)) {
			BioneTriggerInfo oldTrigger = this.triggerBS.getEntityById(
					BioneTriggerInfo.class, triggerId);
			if (oldTrigger != null) {
				if (!cron.equals(oldTrigger.getCron())) {
					// 若触发器发生更改,判断该触发器的关联作业是否有正在运行作业，若有，给出提示
					String str = this.triggerBS
							.checkHasRunningJobOrNot(triggerId);
					if (!"".equals(str) && str != null) {
						// 若有运行作业
						return true;
					}
				}
			}
		}
		return false;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneTriggerInfoVO triggervo, String endTime,
			String startTime) {
		BioneTriggerInfo trigger = new BioneTriggerInfo();
		// 新增的触发器尚未被其它作业引用，则无须对作业进行调度
		triggervo.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());
		// 旧触发器数据
		BioneTriggerInfo ti = null;
		if (triggervo.getTriggerId() != null
				&& !"".equals(triggervo.getTriggerId())) {
			ti = this.triggerBS.getEntityById(triggervo.getTriggerId());
		}
		BeanUtils.copy(triggervo, trigger);
		if (startTime != null && !"".equals(startTime)) {
			trigger.setStartTime(new Timestamp(DateUtils
					.getDateStartLong(startTime)));
		}
		if (endTime != null && !"".equals(endTime)) {
			trigger.setEndTime(new Timestamp(DateUtils
					.getDateStartLong(endTime)));
		}
		if (trigger.getTriggerId() == null || trigger.getTriggerId().equals("")) {
			trigger.setTriggerId(RandomUtils.uuid2());
		}
		this.triggerBS.saveOrUpdateTrigger(trigger);
		// 刷新trigger的缓存
		TriggerInfoHolder.refreshTriggerInfo();
		if (trigger.getTriggerId() != null
				&& !"".equals(trigger.getTriggerId())) {
			// 修改操作时，对触发器信息修改了的进行重跑
			if (ti != null && ti.getCron().equals(trigger.getCron())) {
				// 说明触发器信息都没改变，则可以不采取操作
			} else {
				this.taskBS.updateJobByTriggerId(trigger.getTriggerId());
			}
		}
	}
}
