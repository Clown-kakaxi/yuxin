package com.yuchengtech.bob.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.workplat.model.WorkingplatformSchedule;
import com.yuchengtech.bob.common.CommonService;

@Service
@Transactional(value = "postgreTransactionManager")
public class ScheduleEventManageService extends CommonService{
	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	//批量删除提醒
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			WorkingplatformSchedule schedule = find(id);
			if (schedule != null) {
				em.remove(schedule);
			}
		}
	}
	
	public  WorkingplatformSchedule find(long id) {
		return em.find( WorkingplatformSchedule.class, id);
	}
	
}
