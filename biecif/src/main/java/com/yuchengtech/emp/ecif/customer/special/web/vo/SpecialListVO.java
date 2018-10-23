/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.special.web.vo;

import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.SpecialList;

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
public class SpecialListVO extends SpecialList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String custNo;//客户编号
	private String custType;//客户类型
//	private String strStartDate;//开始日期
//	private String strEndDate;//结束日期
	private String otherKind;//可控制的特殊名单列表

	public SpecialListVO(){
		
	}
	
	public SpecialListVO(SpecialList entity){
		BeanUtils.copy(entity, this);
	}
	
	public SpecialListVO(String custNo, SpecialList entity){
		this.setCustNo(custNo);
		BeanUtils.copy(entity, this);
	}
	
	public SpecialListVO(String custNo, String custTyep, SpecialList entity){
		this.setCustNo(custNo);
		this.setCustType(custTyep);
		BeanUtils.copy(entity, this);
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getOtherKind() {
		return otherKind;
	}

	public void setOtherKind(String otherKind) {
		this.otherKind = otherKind;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

//	public String getStrStartDate() {
//		return strStartDate;
//	}
//
//	public void setStrStartDate(String strStartDate) {
//		this.strStartDate = strStartDate;
//	}
//
//	public String getStrEndDate() {
//		return strEndDate;
//	}
//
//	public void setStrEndDate(String strEndDate) {
//		this.strEndDate = strEndDate;
//	}
	
}
