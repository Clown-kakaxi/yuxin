package com.yuchengtech.emp.ecif.report.entity;

import java.io.Serializable;


/**
 * The persistent class for the RPT_PERSON_INFO_DETAIL database table.
 * 
 */
public class RptPersonInfoDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String createBranchNo;

	private Long custSum;

	private String rptMonth;

	private String rptSign1;

	private String rptSign2;

	private String rptType;

    public RptPersonInfoDetail() {
    }

	public String getCreateBranchNo() {
		return this.createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}

	public Long getCustSum() {
		return this.custSum;
	}

	public void setCustSum(Long custSum) {
		this.custSum = custSum;
	}

	public String getRptMonth() {
		return this.rptMonth;
	}

	public void setRptMonth(String rptMonth) {
		this.rptMonth = rptMonth;
	}

	public String getRptSign1() {
		return this.rptSign1;
	}

	public void setRptSign1(String rptSign1) {
		this.rptSign1 = rptSign1;
	}

	public String getRptSign2() {
		return this.rptSign2;
	}

	public void setRptSign2(String rptSign2) {
		this.rptSign2 = rptSign2;
	}

	public String getRptType() {
		return this.rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

}