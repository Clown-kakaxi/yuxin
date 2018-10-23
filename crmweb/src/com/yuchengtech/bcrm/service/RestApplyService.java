package com.yuchengtech.bcrm.service;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.RestApply;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;


@Service
//@Transactional(value="postgreTransactionManager")
public class RestApplyService extends CommonService {

	@SuppressWarnings("unused")
	private DataSource dsOracle;

	public RestApplyService() {
		JPABaseDAO<RestApply, Long> baseDAO = new JPABaseDAO<RestApply, Long>(
				RestApply.class);
		super.setBaseDAO(baseDAO);
	}

}
