package com.yuchengtech.bcrm.customer.model;


import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the OCRM_F_CI_GROUP_INFO_NEW database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GROUP_INFO_NEW")
public class OcrmFCiGroupInfoNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CommonSequnce")
	@Column(name="GRP_NO")
	private String grpNo;

	@Column(name="CUS_MANAGER")
	private String cusManager;

	@Column(name="CUS_MANAGER_NAME")
	private String cusManagerName;

	@Column(name="GRP_DETAIL")
	private String grpDetail;

	@Column(name="GRP_FINANCE_TYPE")
	private String grpFinanceType;

	@Column(name="GRP_NAME")
	private String grpName;

	@Column(name="INPUT_BR_ID")
	private String inputBrId;

	@Column(name="INPUT_BR_NAME")
	private String inputBrName;

    @Temporal( TemporalType.DATE)
	@Column(name="INPUT_DATE")
	private Date inputDate;

	@Column(name="INPUT_USER_ID")
	private String inputUserId;

	@Column(name="INPUT_USER_NAME")
	private String inputUserName;

	@Column(name="MAIN_BR_ID")
	private String mainBrId;

	@Column(name="MAIN_BR_NAME")
	private String mainBrName;

	@Column(name="PARENT_CUS_ID")
	private String parentCusId;

	@Column(name="PARENT_CUS_NAME")
	private String parentCusName;

	@Column(name="PARENT_LOAN_CARD")
	private String parentLoanCard;

    public OcrmFCiGroupInfoNew() {
    }

	public String getGrpNo() {
		return this.grpNo;
	}

	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}

	public String getCusManager() {
		return this.cusManager;
	}

	public void setCusManager(String cusManager) {
		this.cusManager = cusManager;
	}

	public String getCusManagerName() {
		return this.cusManagerName;
	}

	public void setCusManagerName(String cusManagerName) {
		this.cusManagerName = cusManagerName;
	}

	public String getGrpDetail() {
		return this.grpDetail;
	}

	public void setGrpDetail(String grpDetail) {
		this.grpDetail = grpDetail;
	}

	public String getGrpFinanceType() {
		return this.grpFinanceType;
	}

	public void setGrpFinanceType(String grpFinanceType) {
		this.grpFinanceType = grpFinanceType;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getInputBrId() {
		return this.inputBrId;
	}

	public void setInputBrId(String inputBrId) {
		this.inputBrId = inputBrId;
	}

	public String getInputBrName() {
		return this.inputBrName;
	}

	public void setInputBrName(String inputBrName) {
		this.inputBrName = inputBrName;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputUserId() {
		return this.inputUserId;
	}

	public void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}

	public String getInputUserName() {
		return this.inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	public String getMainBrId() {
		return this.mainBrId;
	}

	public void setMainBrId(String mainBrId) {
		this.mainBrId = mainBrId;
	}

	public String getMainBrName() {
		return this.mainBrName;
	}

	public void setMainBrName(String mainBrName) {
		this.mainBrName = mainBrName;
	}

	public String getParentCusId() {
		return this.parentCusId;
	}

	public void setParentCusId(String parentCusId) {
		this.parentCusId = parentCusId;
	}

	public String getParentCusName() {
		return this.parentCusName;
	}

	public void setParentCusName(String parentCusName) {
		this.parentCusName = parentCusName;
	}

	public String getParentLoanCard() {
		return this.parentLoanCard;
	}

	public void setParentLoanCard(String parentLoanCard) {
		this.parentLoanCard = parentLoanCard;
	}

}