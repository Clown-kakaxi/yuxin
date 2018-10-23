package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author zhaotc zhaotc@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("PersonIdentifierBS")
@Transactional(readOnly = true)
public class PersonIdentifierBS extends BaseBS<PersonIdentifier> {

	protected static Logger log = LoggerFactory
			.getLogger(PersonIdentifierBS.class);
	

}
