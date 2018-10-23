package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.NameTitle;

@Service
@Transactional(readOnly = true)
public class NameTitleBS extends BaseBS<NameTitle> {

	
	
}
