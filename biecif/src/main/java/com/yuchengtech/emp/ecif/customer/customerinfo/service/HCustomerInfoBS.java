/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.customerinfo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.HCustomer;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
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
public class HCustomerInfoBS extends BaseBS<HCustomer> {
	
	/**
	 * 通过客户编号，获取客户信息
	 */
	@SuppressWarnings("unchecked")
	public HCustomer getHCustomerByCustNo(String custNo){
		if(StringUtils.isEmpty(custNo)){
			return null;
		}
		String jql = "select c from HCustomer c where c.custNo=?0 and c.hisOperType=?1 ";
		List<HCustomer> result = this.baseDAO.createQueryWithIndexParam(
				jql, custNo, "G").getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
}
