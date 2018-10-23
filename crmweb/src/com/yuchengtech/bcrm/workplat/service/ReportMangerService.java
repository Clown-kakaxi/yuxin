package com.yuchengtech.bcrm.workplat.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpSchedule;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * @describtion: 工作报告
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年9月10日 下午3:11:59
 */
@Service
public class ReportMangerService extends CommonService {
	public ReportMangerService() {
		JPABaseDAO<OcrmFWpSchedule, Long> baseDAO = new JPABaseDAO<OcrmFWpSchedule, Long>(OcrmFWpSchedule.class);
		super.setBaseDAO(baseDAO);
	}

	public String commitApprReport(String id) throws Exception{
		List list1 = this.em.createNativeQuery("select t.reporter_name from OCRM_F_WP_WORK_REPORT t where t.work_report_id = '"+id+"'").getResultList();
		String jobName = "日工作报告(";
		if(list1 != null && list1.size() > 0){
			jobName += list1.get(0) + ")";
		}
		String instanceid = "REP_" + id+"_"+new SimpleDateFormat("yyMMddHHmm").format(new Date());
		this.initWorkflowByWfidAndInstanceid("67", jobName, null, instanceid);
		//将报告更新为审批中
		this.em.createNativeQuery("update OCRM_F_WP_WORK_REPORT t set t.report_stat = '02' where t.work_report_id = '"+id+"'").executeUpdate();
		return instanceid;
	}
}
