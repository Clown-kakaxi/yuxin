package com.yuchengtech.emp.ecif.customer.customization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.customization.entity.Operatorctztcolumn;

@Service("OperColumnBS")
@Transactional(readOnly = true)
public class OperColumnBS extends BaseBS<Operatorctztcolumn> {

	protected static Logger log = LoggerFactory
			.getLogger(OperColumnBS.class);
}
