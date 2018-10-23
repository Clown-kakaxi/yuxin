/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.customerinfo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Customer;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgidentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;

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
public class CustomerInfoBS extends BaseBS<Object> {
	
	/**
	 * 通过客户编号，获取客户信息
	 */
	@SuppressWarnings("unchecked")
	public Customer getCustIdByCustNo(String custNo){
		if(StringUtils.isEmpty(custNo)){
			return null;
		}
		String jql = "select c from Customer c where c.custNo=?0";
		List<Customer> result = this.baseDAO.createQueryWithIndexParam(
				jql, custNo).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 通过三证信息，获取客户标识（个人证件表）
	 */
	@SuppressWarnings("unchecked")
	public PersonIdentifier getPersonIdentifierByCustId(String identType, String identNo, String identCustName){
		if(StringUtils.isEmpty(identType) || 
				StringUtils.isEmpty(identNo) || 
				StringUtils.isEmpty(identCustName)){
			return null;
		}
		String jql = "select c from PersonIdentifier c where " +
				" c.identType=?0 and c.identNo=?1 and c.identCustName=?2 ";
		List<PersonIdentifier> result = this.baseDAO.createQueryWithIndexParam(
				jql, identType, identNo, identCustName).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 通过三证信息，获取客户标识（机构证件表）
	 */
	@SuppressWarnings("unchecked")
	public Orgidentinfo getOrgidentinfoByCustId(String identType, String identNo, String identCustName){
		if(StringUtils.isEmpty(identType) || 
				StringUtils.isEmpty(identNo) || 
				StringUtils.isEmpty(identCustName)){
			return null;
		}
		String jql = "select c from Orgidentinfo c where " +
			" c.identType=?0 and c.identNo=?1 and c.identCustName=?2 ";
		List<Orgidentinfo> result = this.baseDAO.createQueryWithIndexParam(
				jql, identType, identNo, identCustName).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 通过客户编号，获取客户信息
	 */
	@SuppressWarnings("unchecked")
	public Customer getCustIdByCustId(Long custId){
		
		String jql = "select c from Customer c where c.custId=?0";
		List<Customer> result = this.baseDAO.createQueryWithIndexParam(
				jql, custId).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	
}
