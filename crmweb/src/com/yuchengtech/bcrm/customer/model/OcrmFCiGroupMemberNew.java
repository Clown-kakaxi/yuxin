package com.yuchengtech.bcrm.customer.model;



import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the OCRM_F_CI_GROUP_MEMBER_NEW database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GROUP_MEMBER_NEW")
public class OcrmFCiGroupMemberNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID")
	@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_ID", allocationSize = 1)
	@Column(name="MEMBER_ID")
	private String memberId;
	
	
	@Column(name="GRP_NO")
	private String grpNo;

	@Column(name="CUS_ID")
	private String cusId;

	@Column(name="CUS_MANAGER_NAME")
	private String cusManagerName;

	@Column(name="CUS_MANAGER")
	private String cusManager;

	@Column(name="CUS_NAME")
	private String cusName;

	@Column(name="GRP_CORRE_DETAIL")
	private String grpCorreDetail;

	@Column(name="GRP_CORRE_TYPE")
	private String grpCorreType;

	@Column(name="IDENT_NUMBER")
	private String identNumber;

	@Column(name="IDENT_TYPE")
	private String identType;

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

    public OcrmFCiGroupMemberNew() {
    }
    
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGrpNo() {
		return this.grpNo;
	}

	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}

	public String getCusId() {
		return this.cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getCusManagerName() {
		return this.cusManagerName;
	}

	public void setCusManagerName(String cusManagerName) {
		this.cusManagerName = cusManagerName;
	}

	public String getCusManager() {
		return this.cusManager;
	}

	public void setCusManager(String cusManager) {
		this.cusManager = cusManager;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getGrpCorreDetail() {
		return this.grpCorreDetail;
	}

	public void setGrpCorreDetail(String grpCorreDetail) {
		this.grpCorreDetail = grpCorreDetail;
	}

	public String getGrpCorreType() {
		return this.grpCorreType;
	}

	public void setGrpCorreType(String grpCorreType) {
		this.grpCorreType = grpCorreType;
	}

	public String getIdentNumber() {
		return this.identNumber;
	}

	public void setIdentNumber(String identNumber) {
		this.identNumber = identNumber;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
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

}