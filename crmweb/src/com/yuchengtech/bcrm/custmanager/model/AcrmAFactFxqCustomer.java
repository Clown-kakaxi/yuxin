package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * 
 * @description : 反洗钱有效客户表
 *
 * @author : zhaolong
 * @date : 2016-1-21 下午6:08:21
 */
@Entity
@Table(name="ACRM_A_FACT_FXQ_CUSTOMER")
public class AcrmAFactFxqCustomer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CB_CUST_ID")
	private String cbCustId;

	@Column(name="CREATE_BRANCH_NO")
	private String createBranchNo;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CUST_GRADE_FP")
	private String custGradeFp;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_TYPE")
	private String custType;

	@Temporal(TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	private String flag;

	@Column(name="FLAG_FP")
	private String flagFp;

	private String flag1;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="INDUST_TYPE")
	private String industType;

	@Column(name="MGR_ID_FP")
	private String mgrIdFp;

	@Column(name="STAT_FP")
	private String statFp;

	@Temporal(TemporalType.DATE)
	@Column(name="TIME_FP")
	private Date timeFp;

	public AcrmAFactFxqCustomer() {
	}

	public String getCbCustId() {
		return this.cbCustId;
	}

	public void setCbCustId(String cbCustId) {
		this.cbCustId = cbCustId;
	}

	public String getCreateBranchNo() {
		return this.createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCustGradeFp() {
		return this.custGradeFp;
	}

	public void setCustGradeFp(String custGradeFp) {
		this.custGradeFp = custGradeFp;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlagFp() {
		return this.flagFp;
	}

	public void setFlagFp(String flagFp) {
		this.flagFp = flagFp;
	}

	public String getFlag1() {
		return this.flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getMgrIdFp() {
		return this.mgrIdFp;
	}

	public void setMgrIdFp(String mgrIdFp) {
		this.mgrIdFp = mgrIdFp;
	}

	public String getStatFp() {
		return this.statFp;
	}

	public void setStatFp(String statFp) {
		this.statFp = statFp;
	}

	public Date getTimeFp() {
		return this.timeFp;
	}

	public void setTimeFp(Date timeFp) {
		this.timeFp = timeFp;
	}

}