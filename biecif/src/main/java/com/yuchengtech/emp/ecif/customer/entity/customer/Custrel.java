/**
 * 
 */
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

/**
 * The persistent class for the CUSTREL database table.
 * 
 */
@Entity
@Table(name = "M_CI_CUSTREL")
public class Custrel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CUSTREL_SEQ")
	@GenericGenerator(name = "CUSTREL_SEQ", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CUST_REL_ID") })
	@Column(name = "CUST_REL_ID", unique = true, nullable = false)
	private Long custRelId;

	@Column(name = "CUST_REL_DESC", length = 200)
	private String custRelDesc;

	@Column(name = "CUST_REL_STAT", length = 1)
	private String custRelStat;

	@Column(name = "CUST_REL_TYPE", length = 20)
	private String custRelType;

	@Column(name = "DEST_CUST_ID")
	private Long destCustId;

	@Column(name = "REL_END_DATE")
	private Date relEndDate;

	@Column(name = "REL_START_DATE", length = 10)
	private Date relStartDate;
	 

	@Column(name = "SRC_CUST_ID")
	private Long srcCustId;

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	private String lastUpdateSys;
	
	@Column(name = "LAST_UPDATE_USER", length = 20)
	private String lastUpdateUser;
	
	@Column(name = "LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
	
	
	@Column(name = "TX_SEQ_NO", length = 32)
	private String txSeqNo;
	
	
	@Column(name = "APPROVAL_FLAG", length = 1 )
    private String approvalFlag;

	public String getApprovalFlag() {
		return approvalFlag;
	}


	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}


    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustRelId() {
		return custRelId;
	}


	public void setCustRelId(Long custRelId) {
		this.custRelId = custRelId;
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


	public String getCustRelType() {
		return custRelType;
	}


	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}


    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getDestCustId() {
		return destCustId;
	}


	public void setDestCustId(Long destCustId) {
		this.destCustId = destCustId;
	}




	public Date getRelEndDate() {
		return relEndDate;
	}


	public void setRelEndDate(Date relEndDate) {
		this.relEndDate = relEndDate;
	}


	public Date getRelStartDate() {
		return relStartDate;
	}


	public void setRelStartDate(Date relStartDate) {
		this.relStartDate = relStartDate;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSrcCustId() {
		return srcCustId;
	}


	public void setSrcCustId(Long srcCustId) {
		this.srcCustId = srcCustId;
	}

	public String getLastUpdateSys() {
		return lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}
}