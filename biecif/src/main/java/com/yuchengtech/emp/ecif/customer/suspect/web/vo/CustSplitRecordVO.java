/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web.vo;

import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.customer.entity.other.CustSplitRecord;

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
public class CustSplitRecordVO extends CustSplitRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private String identNo;
//	private String identType;
//	private String identCustName;
	
	private String custType;
	
	public CustSplitRecordVO(){
		
	}
	
	public CustSplitRecordVO(String custType, CustSplitRecord entity){
		this.setCustType(custType);
		BeanUtils.copy(entity, this);
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

}
