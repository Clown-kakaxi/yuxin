package com.yuchengtech.bcrm.sales.marketTask.service;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTaskTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.core.QueryHelper;


@Service
@Transactional(value="postgreTransactionManager")
public class MktTaskTrackService extends CommonService{
	public MktTaskTrackService() {
		JPABaseDAO<OcrmFMmTaskTarget, Long> baseDao = new JPABaseDAO<OcrmFMmTaskTarget, Long>(
				OcrmFMmTaskTarget.class);
		super.setBaseDAO(baseDao);
	}
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource dsOracle;
	
	/**
	 * @author sujm
	 * 查询营销任务所需字段
	 * @param queryCond
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public Map queryIndexInfo(Map queryCond){
		//查询当前营销任务的相关信息
		StringBuffer sb = new StringBuffer("select t1.*,t2.target_name,t2.target_type from OCRM_F_MM_TASK_TARGET t1 inner join OCRM_F_MM_TARGET t2 " 
											+ " on t1.target_code =t2.target_code "
											+"where T1.task_id = '"+queryCond.get("taskId")+"'"
											+" order by t1.cycle_name,t2.target_name ");
	    QueryHelper qh;
		try {
			qh = new QueryHelper(sb.toString(), dsOracle.getConnection());
			return qh.getJSON(true);//驼峰状封装属性
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
