package com.yuchengtech.emp.biappframe.schedule.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTaskInfo;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTriggerInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;

/**
 * 
 * <pre>
 * Title:quartz 任务调度
 * Description: 任务的新增停止挂起恢复删除等操作
 * </pre>
 * 
 * @author songxf
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("taskBS")
@Transactional(readOnly = true)
public class TaskBS extends BaseBS<BioneTaskInfo> {
	protected static Logger log = LoggerFactory.getLogger(TaskBS.class);
	@Autowired
	private SchedulerFactoryBean scheduler;
	private final static String TASK_STS_NORMAL = "01";
	private final static String TASK_STS_PAUSE = "02";

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobId
	 *            任务实例ID 如果重复将自动覆盖之前的任务实例
	 * @param jobClass
	 *            Job类
	 * 
	 * @param triggerId
	 *            BIONE中触发器模块的triggerId
	 * 
	 * @param taskType
	 *            任务类型 01 系统任务 02 扩展任务
	 * 
	 * @param taskName
	 *            任务名称
	 * @return boolean 成功 失败
	 * @throws RuntimeException
	 */
	@Transactional(readOnly = false)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean startJob(String jobId, Class jobClass, String triggerId,
			String taskName, String taskType, String logicSysNo) {
		try {
			// 创建触发器
			final TimeZone zone = TimeZone.getTimeZone("GMT+8"); // 获取中国时区
			TimeZone.setDefault(zone); // 设置时区
			BioneTriggerInfo triggerInfo = this.getEntityById(
					BioneTriggerInfo.class, triggerId);
			if (triggerInfo == null) {
				log.info("["
						+ jobId
						+ "] Trigger does not exist。Please check Trigger configuration.");
			} else {
				String Cron = triggerInfo.getCron();
				Date starttime = triggerInfo.getStartTime();
				Date curtime = DateUtils.addMinutes(new Date(), 1);
				if (starttime == null) {
					starttime = curtime;
				}
				Date endtime = triggerInfo.getEndTime();
				JobDetail jobDetail = newJob(jobClass).withIdentity(jobId,
						jobId).build();
				Trigger trigger = newTrigger().withIdentity(jobId, jobId)
						.startAt(starttime).endAt(endtime)
						.withSchedule(cronSchedule(Cron)).build();
				if (this.checkExists(jobId)) {
					this.stopJob(jobId);
					scheduler.getScheduler()
							.deleteJob(new JobKey(jobId, jobId));
				}
				
				scheduler.getScheduler().scheduleJob(jobDetail, trigger);
				BioneTaskInfo taskInfo = new BioneTaskInfo();
				taskInfo.setTaskId(jobId);
				taskInfo.setBeanName(jobClass.getName());
				taskInfo.setCreateTime(new Timestamp(new Date().getTime()));
				taskInfo.setTriggerId(triggerId);
				taskInfo.setTaskSts(TASK_STS_NORMAL);
				taskInfo.setTaskName(taskName);
				taskInfo.setTaskType(taskType);
				taskInfo.setLogicSysNo(logicSysNo);
				this.baseDAO.merge(taskInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return true;
	}
	
	@Transactional(readOnly = false)
	public void addDurableJob(String jobId, Class<?> jobClass, String triggerId,
			String taskName, String taskType, String logicSysNo) {
		
		BioneTaskInfo taskInfo = new BioneTaskInfo();
		taskInfo.setTaskId(jobId);
		taskInfo.setBeanName(jobClass.getName());
		taskInfo.setCreateTime(new Timestamp(new Date().getTime()));
		taskInfo.setTriggerId(triggerId);
		taskInfo.setTaskSts(TASK_STS_PAUSE);
		taskInfo.setTaskName(taskName);
		taskInfo.setTaskType(taskType);
		taskInfo.setLogicSysNo(logicSysNo);
		this.baseDAO.merge(taskInfo);
	}

	/**
	 * 立即执行一个job
	 * 
	 * @param jobId
	 *            任务名Id
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void startNowJob(String jobId) {
		
		try {
			BioneTaskInfo taskInfo = this.getEntityById(jobId);
			JobDetail jobDetail = scheduler.getScheduler().getJobDetail(new JobKey(jobId, jobId)); 
			if (jobDetail != null) {	// jobDetail == null 说明这个任务还没有添加到调度任务
				scheduler.getScheduler().triggerJob(new JobKey(jobId, jobId));
			} else {
				jobDetail = newJob((Class<? extends Job>) Class.forName(taskInfo.getBeanName())).withIdentity(jobId, jobId).build();
				// 创建触发器
				final TimeZone zone = TimeZone.getTimeZone("GMT+8"); // 获取中国时区
				TimeZone.setDefault(zone); // 设置时区
				BioneTriggerInfo triggerInfo = this.getEntityById(
						BioneTriggerInfo.class, taskInfo.getTriggerId());
				String Cron = triggerInfo.getCron();
				Date starttime = triggerInfo.getStartTime();
				Date curtime = DateUtils.addMinutes(new Date(), 1);
				if (starttime == null) {
					starttime = curtime;
				}
				Date endtime = triggerInfo.getEndTime();
				Trigger trigger = newTrigger().withIdentity(jobId, jobId)
						.startAt(starttime).endAt(endtime)
						.withSchedule(cronSchedule(Cron)).build();
				
				scheduler.getScheduler().scheduleJob(jobDetail, trigger);
				scheduler.getScheduler().interrupt(new JobKey(jobId,jobId));
				// scheduleJob 只是添加了任务，并没有马上执行一次
				scheduler.getScheduler().triggerJob(new JobKey(jobId,jobId));
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException("立即运行一个任务时异常", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止一个正在执行的job
	 * 
	 * @param jobId
	 *            任务名Id
	 * @throws RuntimeException
	 */
	@Transactional(readOnly = false)
	public void stopJob(String jobId) {
		try {
			scheduler.getScheduler().interrupt(new JobKey(jobId, jobId));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除一个Job
	 * 
	 * @param jobId
	 *            任务名Id
	 * @throws RuntimeException
	 */
	@Transactional(readOnly = false)
	public void delJob(String jobId) {
		try {
			scheduler.getScheduler().deleteJob(new JobKey(jobId, jobId));
			this.removeEntityById(jobId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 挂起一个Job
	 * 
	 * @param jobId
	 *            任务名Id
	 * @throws RuntimeException
	 */
	@Transactional(readOnly = false)
	public void pauseJob(String jobId) {
		try {
			scheduler.getScheduler().pauseJob(new JobKey(jobId, jobId));
			BioneTaskInfo taskInfo = this.getEntityById(jobId);
			taskInfo.setTaskSts(TASK_STS_PAUSE);
			this.baseDAO.merge(taskInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 恢复一个Job
	 * 
	 * @param jobId
	 *            任务名Id
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void resumeJob(String jobId) {
		try {
			BioneTaskInfo taskInfo = this.getEntityById(jobId);
			JobDetail jobDetail = scheduler.getScheduler().getJobDetail(new JobKey(jobId, jobId)); 
			if (jobDetail != null) {	// jobDetail == null 说明这个任务还没有添加到调度任务
				scheduler.getScheduler().resumeJob(new JobKey(jobId, jobId));
			} else {
				jobDetail = newJob((Class<? extends Job>) Class.forName(taskInfo.getBeanName())).withIdentity(jobId, jobId).build();
				// 创建触发器
				final TimeZone zone = TimeZone.getTimeZone("GMT+8"); // 获取中国时区
				TimeZone.setDefault(zone); // 设置时区
				BioneTriggerInfo triggerInfo = this.getEntityById(
						BioneTriggerInfo.class, taskInfo.getTriggerId());
				String Cron = triggerInfo.getCron();
				Date starttime = triggerInfo.getStartTime();
				Date curtime = DateUtils.addMinutes(new Date(), 1);
				if (starttime == null) {
					starttime = curtime;
				}
				Date endtime = triggerInfo.getEndTime();
				Trigger trigger = newTrigger().withIdentity(jobId, jobId)
						.startAt(starttime).endAt(endtime)
						.withSchedule(cronSchedule(Cron)).build();
				
				scheduler.getScheduler().scheduleJob(jobDetail, trigger);
			}
			taskInfo.setTaskSts(TASK_STS_NORMAL);
			this.baseDAO.merge(taskInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 检查一个job是否存在
	 * 
	 * @param jobId
	 *            任务名Id
	 * @return result 是否存在
	 * @throws RuntimeException
	 */

	public boolean checkExists(String jobId) {
		boolean result = false;
		try {
			result = scheduler.getScheduler().checkExists(
					new JobKey(jobId, jobId));
			if (this.getEntityById(jobId) == null) {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 获得一个Job的信息
	 * 
	 * @param jobId
	 *            任务名Id
	 * @throws RuntimeException
	 */

	public JobDetail getJob(String jobId) {
		JobDetail jobDetail = null;
		try {
			jobDetail = scheduler.getScheduler().getJobDetail(
					new JobKey(jobId, jobId));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return jobDetail;
	}

	/**
	 * 获得一个Job的上次触发时间
	 * 
	 * @param jobId
	 *            任务名Id
	 * @return date
	 * @throws RuntimeException
	 */

	public Date getPreviousFireTime(String jobId) {
		Date date = null;
		Trigger trigger = null;
		try {
			trigger = scheduler.getScheduler().getTrigger(
					new TriggerKey(jobId, jobId));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (trigger != null) {
			date = trigger.getPreviousFireTime();
		}
		return date;
	}

	/**
	 * 获得一个Job的下次触发时间
	 * 
	 * @param jobId
	 *            任务名Id
	 * @return date
	 * @throws RuntimeException
	 */

	public Date getNextFireTime(String jobId) {
		Date date = null;
		Trigger trigger = null;
		try {
			trigger = scheduler.getScheduler().getTrigger(
					new TriggerKey(jobId, jobId));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (trigger != null) {
			date = trigger.getNextFireTime();
		}
		return date;
	}

	/**
	 * 获得一个Job的触发状态
	 * 
	 * @param jobId
	 *            任务名Id
	 * @return state
	 * @throws RuntimeException
	 */

	public String getTriggerState(String jobId) {
		String state = "";
		try {
			TriggerState s = scheduler.getScheduler().getTriggerState(
					new TriggerKey(jobId, jobId));

			if (s == Trigger.TriggerState.NONE) {
				state = "none";
			} else if (s == Trigger.TriggerState.NORMAL) {
				state = "running";
			} else if (s == Trigger.TriggerState.COMPLETE) {
				state = "complete";
			} else if (s == Trigger.TriggerState.PAUSED) {
				state = "paused";
			} else if (s == Trigger.TriggerState.BLOCKED) {
				state = "blocked";
			} else if (s == Trigger.TriggerState.ERROR) {
				state = "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return state;

	}

	/**
	 * 修改触发器时，更新任务相关的触发器信息
	 * 
	 * @param triggerId
	 *            BIONE中触发器模块的triggerId
	 * @return boolean 成功 失败
	 * @throws RuntimeException
	 */
	@Transactional(readOnly = false)
	public void updateJobByTriggerId(String triggerId) {
		String jql = "select task from BioneTaskInfo task where task.triggerId=?0";
		List<BioneTaskInfo> tasklist = this.baseDAO.findWithIndexParam(jql,
				triggerId);
		for (BioneTaskInfo taskInfo : tasklist) {
			String tasksts = taskInfo.getTaskSts();
			if (tasksts.equals(TASK_STS_NORMAL)) {
				try {
					this.startJob(taskInfo.getTaskId(), Class.forName(taskInfo
							.getBeanName()), taskInfo.getTriggerId(), taskInfo
							.getTaskName(), taskInfo.getTaskType(),
							BiOneSecurityUtils.getCurrentUserInfo()
									.getCurrentLogicSysNo());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}

			} else if (tasksts.equals(TASK_STS_PAUSE)) {
				try {
					this.startJob(taskInfo.getTaskId(), Class.forName(taskInfo
							.getBeanName()), taskInfo.getTriggerId(), taskInfo
							.getTaskName(), taskInfo.getTaskType(),
							BiOneSecurityUtils.getCurrentUserInfo()
									.getCurrentLogicSysNo());
					this.pauseJob(taskInfo.getTaskId());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 更新调度作业
	 * 
	 * @param task
	 *            BIONE任务对象
	 */
	@Transactional(readOnly = false)
	public void updateJob(BioneTaskInfo task) {
		if (task == null) {
			return;
		}
		// 先从quartz中删除该作业调度;
		this.delJob(task.getTaskId());
		// 新建作业调度
		try {
			this.startJob(task.getTaskId(), Class.forName(task.getBeanName()),
					task.getTriggerId(), task.getTaskName(),
					task.getTaskType(), BiOneSecurityUtils.getCurrentUserInfo()
							.getCurrentLogicSysNo());
			if (TASK_STS_PAUSE.equals(task.getTaskSts())) {
				// 若是挂起任务
				this.pauseJob(task.getTaskId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
