package com.yuchengtech.emp.ecif.customer.entity.customer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

@Entity
@Table(name = "APP_CUSTREL_APPROVAL")
public class CustrelApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "APP_CUSTREL_APPROVAL_SEQ")
	@GenericGenerator(name = "APP_CUSTREL_APPROVAL_SEQ", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CUST_REL_ID_APPROVAL") })
	@Column(name = "CUSTREL_APPROVAL_ID", unique = true, nullable = false)
	private Long custrelApprovalId;
	@Column(name = "CUST_REL_ID")
	private Long custRelId;
	@Column(name = "CUST_REL_TYPE", length = 20)
	private String custRelType;
	@Column(name = "CUST_REL_DESC", length = 40)
	private String custRelDesc;
	@Column(name = "CUST_REL_STAT", length = 1)
	private String custRelStat;
	@Column(name = "SRC_CUST_ID")
	private Long srcCustId;
	@Column(name = "DEST_CUST_ID")
	private Long destCustId;
	@Column(name = "REL_START_DATE")
	private Date relStartDate;
	@Column(name = "REL_END_DATE")
	private Date relEndDate;
	@Column(name = "OPERATOR", length = 20)
	private String operator;
	@Column(name = "OPER_TIME")
	private Timestamp operTime;
	@Column(name = "OPER_STAT", length = 2)
	private String operStat;
	@Column(name = "APPROVAL_OPERATOR", length = 20)
	private String approvalOperator;
	@Column(name = "APPROVAL_TIME")
	private Timestamp approvalTime;
	@Column(name = "APPROVAL_STAT", length = 2)
	private String approvalStat;
	@Column(name = "APPROVAL_NOTE", length = 200)
	private String approvalNote;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustrelApprovalId() {
		return custrelApprovalId;
	}
	
	public void setCustrelApprovalId(Long custrelApprovalId) {
		this.custrelApprovalId = custrelApprovalId;
	}
	
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustRelId() {
		return custRelId;
	}
    
	public void setCustRelId(Long custRelId) {
		this.custRelId = custRelId;
	}
	
	public String getCustRelType() {
		return custRelType;
	}
	
	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}
	
	public String getCustRelDesc() {
		return custRelDesc;
	}
	
	public void setCustRelDesc(String custRelDesc) {
		this.custRelDesc = custRelDesc;
	}
	
	public String getCustRelStat() {
		return custRelStat;
	}
	
	public void setCustRelStat(String custRelStat) {
		this.custRelStat = custRelStat;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSrcCustId() {
		return srcCustId;
	}
	
	public void setSrcCustId(Long srcCustId) {
		this.srcCustId = srcCustId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getDestCustId() {
		return destCustId;
	}
	
	public void setDestCustId(Long destCustId) {
		this.destCustId = destCustId;
	}

	public Date getRelStartDate() {
		return relStartDate;
	}
	
	public void setRelStartDate(Date relStartDate) {
		this.relStartDate = relStartDate;
	}
	
	public Date getRelEndDate() {
		return relEndDate;
	}
	
	public void setRelEndDate(Date relEndDate) {
		this.relEndDate = relEndDate;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Timestamp getOperTime() {
		return operTime;
	}
	
	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}
	
	public String getOperStat() {
		return operStat;
	}
	
	public void setOperStat(String operStat) {
		this.operStat = operStat;
	}
	
	public String getApprovalOperator() {
		return approvalOperator;
	}
	
	public void setApprovalOperator(String approvalOperator) {
		this.approvalOperator = approvalOperator;
	}
	
	public Timestamp getApprovalTime() {
		return approvalTime;
	}
	
	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	public String getApprovalStat() {
		return approvalStat;
	}
	
	public void setApprovalStat(String approvalStat) {
		this.approvalStat = approvalStat;
	}
	
	public String getApprovalNote() {
		return approvalNote;
	}
	
	public void setApprovalNote(String approvalNote) {
		this.approvalNote = approvalNote;
	}
}
