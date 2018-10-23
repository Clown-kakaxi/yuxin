/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.special.web.vo;

import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.customer.entity.other.SpecialListApproval;

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
public class SpecialListApprovalVO extends SpecialListApproval {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String custNo;//客户编号

	public SpecialListApprovalVO(){
		
	}
	
	public SpecialListApprovalVO(String custNo, SpecialListApproval entity){
		this.setCustNo(custNo);
		BeanUtils.copy(entity, this);
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	
}
