package com.yuchengtech.emp.ecif.customer.special.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.HSpecialList;
/**
 * <pre>
 * Title:黑名单信息
 * Description: 业务数据展示，对于ecif系统的数据进行修改、删除、新增黑名单，操作的信息状态为待审批。
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class HSpecialListBS extends BaseBS<HSpecialList> {

	
	
}
