package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Customer;

@Service
@Transactional(readOnly = true)
public class CustomerBS extends BaseBS<Customer> {

	
	
}
