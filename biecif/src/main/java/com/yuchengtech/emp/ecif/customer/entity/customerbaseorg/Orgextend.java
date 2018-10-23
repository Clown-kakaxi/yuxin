package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGEXTEND database table.
 * 
 */
@Entity
@Table(name="ORGEXTEND")
public class Orgextend implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="BUILD_LOAN_ORIGINAL_DATE",length=20)
	private String buildLoanOriginalDate;

	@Column(name="CORP_DEVE_HISTORY", length=1000)
	private String corpDeveHistory;

	@Column(name="CREDIT_BELONG", length=18)
	private String creditBelong;

	@Column(name="CUST_BUSI_AREA", precision=10, scale=2)
	private BigDecimal custBusiArea;

	@Column(name="CUST_HISTORY", length=800)
	private String custHistory;

	@Column(name="CUST_OFFICE_AREA", precision=10, scale=2)
	private BigDecimal custOfficeArea;

	@Column(name="FINANCE_BELONG", length=18)
	private String financeBelong;

	@Column(name="LOAN_BODY_TYPE", length=20)
	private String loanBodyType;

	@Column(name="MANAGE_LEVEL", length=800)
	private String manageLevel;

	@Column(name="MANAGEINFO",length=800)
	private String manageinfo;

	@Column(name="SUPER_LOAN_CARD_NO", length=20)
	private String superLoanCardNo;

	@Column(name="TEMP_SAVE_FLAG", length=18)
	private String tempSaveFlag;

	@Column(name="WORK_FIELD_AREA", precision=10, scale=2)
	private BigDecimal workFieldArea;

	@Column(name="WORK_FIELD_OWNERSHIP", length=20)
	private String workFieldOwnership;
	
	@Column(name="INDUSTRY_POSITION", length=80)
	private String industryPosition;

	@Column(name="OCCUINDUSTRYTYPE",  length=20)
	private String occuindustrytype;

	@Column(name="INNERENVINDUSTRYTYPE", length=20)
	private String innerenvindustrytype;

    public Orgextend() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getBuildLoanOriginalDate() {
		return this.buildLoanOriginalDate;
	}

	public void setBuildLoanOriginalDate(String buildLoanOriginalDate) {
		this.buildLoanOriginalDate = buildLoanOriginalDate;
	}

	public String getCorpDeveHistory() {
		return this.corpDeveHistory;
	}

	public void setCorpDeveHistory(String corpDeveHistory) {
		this.corpDeveHistory = corpDeveHistory;
	}

	public String getCreditBelong() {
		return this.creditBelong;
	}

	public void setCreditBelong(String creditBelong) {
		this.creditBelong = creditBelong;
	}

	public BigDecimal getCustBusiArea() {
		return this.custBusiArea;
	}

	public void setCustBusiArea(BigDecimal custBusiArea) {
		this.custBusiArea = custBusiArea;
	}

	public String getCustHistory() {
		return this.custHistory;
	}

	public void setCustHistory(String custHistory) {
		this.custHistory = custHistory;
	}

	public BigDecimal getCustOfficeArea() {
		return this.custOfficeArea;
	}

	public void setCustOfficeArea(BigDecimal custOfficeArea) {
		this.custOfficeArea = custOfficeArea;
	}

	public String getFinanceBelong() {
		return this.financeBelong;
	}

	public void setFinanceBelong(String financeBelong) {
		this.financeBelong = financeBelong;
	}

	public String getLoanBodyType() {
		return this.loanBodyType;
	}

	public void setLoanBodyType(String loanBodyType) {
		this.loanBodyType = loanBodyType;
	}

	public String getManageLevel() {
		return this.manageLevel;
	}

	public void setManageLevel(String manageLevel) {
		this.manageLevel = manageLevel;
	}

	public String getManageinfo() {
		return this.manageinfo;
	}

	public void setManageinfo(String manageinfo) {
		this.manageinfo = manageinfo;
	}

	public String getSuperLoanCardNo() {
		return this.superLoanCardNo;
	}

	public void setSuperLoanCardNo(String superLoanCardNo) {
		this.superLoanCardNo = superLoanCardNo;
	}

	public String getTempSaveFlag() {
		return this.tempSaveFlag;
	}

	public void setTempSaveFlag(String tempSaveFlag) {
		this.tempSaveFlag = tempSaveFlag;
	}

	public BigDecimal getWorkFieldArea() {
		return this.workFieldArea;
	}

	public void setWorkFieldArea(BigDecimal workFieldArea) {
		this.workFieldArea = workFieldArea;
	}

	public String getWorkFieldOwnership() {
		return this.workFieldOwnership;
	}

	public void setWorkFieldOwnership(String workFieldOwnership) {
		this.workFieldOwnership = workFieldOwnership;
	}

	public String getIndustryPosition() {
		return industryPosition;
	}

	public void setIndustryPosition(String industryPosition) {
		this.industryPosition = industryPosition;
	}

	public String getOccuindustrytype() {
		return occuindustrytype;
	}

	public void setOccuindustrytype(String occuindustrytype) {
		this.occuindustrytype = occuindustrytype;
	}

	public String getInnerenvindustrytype() {
		return innerenvindustrytype;
	}

	public void setInnerenvindustrytype(String innerenvindustrytype) {
		this.innerenvindustrytype = innerenvindustrytype;
	}

}