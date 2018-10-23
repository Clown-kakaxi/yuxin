package com.yuchengtech.emp.biappframe.schedule.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTaskInfo;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTriggerInfo;
import com.yuchengtech.emp.biappframe.schedule.service.TaskBS;
import com.yuchengtech.emp.biappframe.schedule.service.TaskListBS;
import com.yuchengtech.emp.biappframe.schedule.service.TriggerBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title:任务信息处理Action类
 * Description: 系统任务信息表的增删改查
 * </pre>
 * 
 * @author yangyuhui yangyh4@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/schedule/task")
public class TaskController extends BaseController {
	@Autowired
	private TaskListBS taskListBS;
	@Autowired
	private TaskBS taskBS;
	@Autowired
	private TriggerBS triggerBS;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/task/task-editNew";
	}

	@RequestMapping("selectTrigger")
	public String selectTrigger() {
		return "/bione/task/task-trigger";
	}

	@RequestMapping("/checkIsRunning")
	public String checkIsRunning(String taskId, String triggerId,
			String beanName) {
		if (taskId != null && !"".equals(taskId) && triggerId != null
				&& !"".equals(triggerId) && beanName != null
				&& !"".equals(beanName)) {
			BioneTaskInfo oldTask = this.taskListBS.getEntityById(
					BioneTaskInfo.class, taskId);
			if (oldTask != null) {
				// 若实现类和触发器没发生变化，只是修改任务名称，只进行维护bione任务表动作
				if (oldTask.getBeanName() != null
						&& !"".equals(oldTask.getBeanName())
						&& oldTask.getBeanName().equals(beanName)
						&& oldTask.getTriggerId() != null
						&& !"".equals(oldTask.getTriggerId())
						&& oldTask.getTriggerId().equals(triggerId)) {
					return "";
				} else {
					// 判断该任务在qrtz中是否正在运行
					String triggerState = this.taskBS.getTriggerState(taskId);
					if ("running".equals(triggerState)) {
						// 若是正在运行任务，不允许修改
						return "running";

					}
				}
			}
		}
		return "";
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String create(BioneTaskInfo task, String testTriggerId) {
		if (task.getTaskType() != null && !"".equals(task.getTaskType())) {
			// 修改
			task.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
					.getCurrentLogicSysNo());
			task.setCreateTime(new Timestamp(new Date().getTime()));
			BioneTaskInfo oldTask = this.taskListBS.getEntityById(
					BioneTaskInfo.class, task.getTaskId());
			if (oldTask != null) {
				// 若实现类和触发器没发生变化，只是修改任务名称，只进行维护bione任务表动作
				if (oldTask.getBeanName() != null
						&& !"".equals(oldTask.getBeanName())
						&& oldTask.getBeanName().equals(task.getBeanName())
						&& oldTask.getTriggerId() != null
						&& !"".equals(oldTask.getTriggerId())
						&& oldTask.getTriggerId().equals(task.getTriggerId())) {
					task = this.taskListBS.mergeTask(task);
				} else {
					// 判断该任务在qrtz中是否正在运行
					String triggerState = this.taskBS.getTriggerState(task
							.getTaskId());
					if ("running".equals(triggerState)) {
						// 若是正在运行任务，不允许修改
						return "running";
					} else {
						task = this.taskListBS.mergeTask(task);
						try {
							taskBS.updateJob(task);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			// 新增
			task.setTaskType(GlobalConstants.TASK_TYPE_SYSTASK);
			task.setTaskId(RandomUtils.uuid2());
			if (testTriggerId != null && "".equals(task.getTriggerId())) {
				task.setTriggerId(testTriggerId);
			}
			task.setCreateTime(new Timestamp(new Date().getTime()));
			task.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
					.getCurrentLogicSysNo());
			// test
			this.taskListBS.updateTaskInfo(task);
			if (task != null
					&& task.getTaskId() != null
					&& !"".equals(task.getTaskId())
					&& GlobalConstants.TASK_STS_NORMAL
							.equals(task.getTaskSts())) {
				// 若是正常状态作业，立即调启
				try {
					this.taskBS.startJob(task.getTaskId(),
							Class.forName(task.getBeanName()),
							task.getTriggerId(), task.getTaskName(),
							task.getTaskType(), task.getLogicSysNo());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (task != null && task.getTaskId() != null
					&& !"".equals(task.getTaskId())
					&& GlobalConstants.TASK_STS_STOP.equals(task.getTaskSts())) {
				try {
					this.taskBS.addDurableJob(task.getTaskId(),
							Class.forName(task.getBeanName()),
							task.getTriggerId(), task.getTaskName(),
							task.getTaskType(), task.getLogicSysNo());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void destroy(@PathVariable("id") String id) {
		this.taskListBS.removeEntityById(id);
	}

	@RequestMapping("/testIsExists")
	@ResponseBody
	public boolean testIsExists(String beanName) {
		if (beanName != null && !"".equals(beanName)) {
			try {
				Class.forName(beanName);
			} catch (ClassNotFoundException e) {
				// logger.warn("在任务定制的实现类验证中，任务的实现类不存在");
				return false;
			}
		}
		return true;
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		String triggerId = "";
		String triggerName = "";
		try {
			BioneTaskInfo task = this.taskListBS.getEntityById(id);
			triggerId = task.getTriggerId();
			BioneTriggerInfo triggerTmp = triggerBS.getEntityById(task
					.getTriggerId());
			if (triggerTmp != null) {
				triggerName = triggerTmp.getTriggerName();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ModelMap mm = new ModelMap();
		mm.put("id", id);
		mm.put("triggerId", triggerId);
		mm.put("triggerName", triggerName);
		return new ModelAndView("/bione/task/task-edit", mm);
	}

	@ResponseBody
	@RequestMapping("/start")
	public void start(String id) {
		this.taskBS.startNowJob(id);
	}

	@ResponseBody
	@RequestMapping("/resume")
	public void resume(String id) {
		this.taskBS.resumeJob(id);
	}

	@ResponseBody
	@RequestMapping("/pause")
	public void pause(String id) {
		this.taskBS.pauseJob(id);
	}

	/**
	 * 初始化Grid
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneTaskInfo> searchResult = taskListBS.getTaskList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		Map<String, Object> taskMap = Maps.newHashMap();
		taskMap.put("Rows", searchResult.getResult());
		taskMap.put("Total", searchResult.getTotalCount());
		return taskMap;
	}

	/**
	 * 验证Id是否重复
	 */
	@RequestMapping("/checkIdIsExist")
	public boolean checkIdIsExist(String taskId) {
		return this.taskListBS.checkIdIsExist(taskId);
	}

	/**
	 * 通过id查询并转化为页面显示数据
	 */
	@RequestMapping("/showInfo")
	@ResponseBody
	public BioneTaskInfo showInfo(String id) {
		return this.taskBS.getEntityById(id);
	}

	// 批量删除
	@RequestMapping("/batchDelete")
	public void batchDelete(String ids) {
		if (ids != null && !"".equals(ids)) {
			this.taskListBS.batchDelete(ids);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/task/task-index";
	}

}
