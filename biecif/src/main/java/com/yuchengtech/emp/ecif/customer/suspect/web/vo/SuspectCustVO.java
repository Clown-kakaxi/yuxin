/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web.vo;

import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectList;

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
public class SuspectCustVO extends SuspectList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Long suspectListId;
	private String custNo;
//	private String reserveFlag;
	private String custType;
	//CREATE_BRANCH_NO
	private String createBranchNo;
	
	private String suspectGroupDesc;
	private String suspectDataDate;
	private String suspectSearchDate;
	private String suspectExportFlag;
	//private String reserveCustId;
	private String enterReason;
	private String custName;
	
	public SuspectCustVO(){
		
	}
	
	public SuspectCustVO(String suspectGroupDesc, String suspectDataDate, 
			String suspectSearchDate, String suspectExportFlag, 
			String enterReason, String custNo, String custType, 
			String custName, SuspectList entity){
		this.setSuspectGroupDesc(suspectGroupDesc);
		this.setSuspectDataDate(suspectDataDate);
		this.setSuspectSearchDate(suspectSearchDate);
		this.setSuspectExportFlag(suspectExportFlag);
		this.setEnterReason(enterReason);
		this.setCustNo(custNo);
		this.setCustType(custType);
		this.setCustName(custName);
		BeanUtils.copy(entity, this);
	}

	public String getSuspectGroupDesc() {
		return suspectGroupDesc;
	}

	public void setSuspectGroupDesc(String suspectGroupDesc) {
		this.suspectGroupDesc = suspectGroupDesc;
	}

	public String getSuspectDataDate() {
		return suspectDataDate;
	}

	public void setSuspectDataDate(String suspectDataDate) {
		this.suspectDataDate = suspectDataDate;
	}

	public String getSuspectSearchDate() {
		return suspectSearchDate;
	}

	public void setSuspectSearchDate(String suspectSearchDate) {
		this.suspectSearchDate = suspectSearchDate;
	}

	public String getSuspectExportFlag() {
		return suspectExportFlag;
	}

	public void setSuspectExportFlag(String suspectExportFlag) {
		this.suspectExportFlag = suspectExportFlag;
	}

	public String getEnterReason() {
		return enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCreateBranchNo() {
		return createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}
}
