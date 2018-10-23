package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiCustEdgeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiCustEdgeId implements java.io.Serializable {

	// Fields

	private String custRelId;
	private String graphId;
	private String custRelType;
	private String custRelName;
	private String custRelDesc;
	private String srcCustId;
	private String destCustId;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiCustEdgeId() {
	}

	/** minimal constructor */
	public HMCiCustEdgeId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiCustEdgeId(String custRelId, String graphId, String custRelType,
			String custRelName, String custRelDesc, String srcCustId,
			String destCustId, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.custRelId = custRelId;
		this.graphId = graphId;
		this.custRelType = custRelType;
		this.custRelName = custRelName;
		this.custRelDesc = custRelDesc;
		this.srcCustId = srcCustId;
		this.destCustId = destCustId;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_REL_ID", length = 20)
	public String getCustRelId() {
		return this.custRelId;
	}

	public void setCustRelId(String custRelId) {
		this.custRelId = custRelId;
	}

	@Column(name = "GRAPH_ID", length = 20)
	public String getGraphId() {
		return this.graphId;
	}

	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	@Column(name = "CUST_REL_TYPE", length = 20)
	public String getCustRelType() {
		return this.custRelType;
	}

	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}

	@Column(name = "CUST_REL_NAME", length = 40)
	public String getCustRelName() {
		return this.custRelName;
	}

	public void setCustRelName(String custRelName) {
		this.custRelName = custRelName;
	}

	@Column(name = "CUST_REL_DESC", length = 200)
	public String getCustRelDesc() {
		return this.custRelDesc;
	}

	public void setCustRelDesc(String custRelDesc) {
		this.custRelDesc = custRelDesc;
	}

	@Column(name = "SRC_CUST_ID", length = 20)
	public String getSrcCustId() {
		return this.srcCustId;
	}

	public void setSrcCustId(String srcCustId) {
		this.srcCustId = srcCustId;
	}

	@Column(name = "DEST_CUST_ID", length = 20)
	public String getDestCustId() {
		return this.destCustId;
	}

	public void setDestCustId(String destCustId) {
		this.destCustId = destCustId;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiCustEdgeId))
			return false;
		HMCiCustEdgeId castOther = (HMCiCustEdgeId) other;

		return ((this.getCustRelId() == castOther.getCustRelId()) || (this
				.getCustRelId() != null
				&& castOther.getCustRelId() != null && this.getCustRelId()
				.equals(castOther.getCustRelId())))
				&& ((this.getGraphId() == castOther.getGraphId()) || (this
						.getGraphId() != null
						&& castOther.getGraphId() != null && this.getGraphId()
						.equals(castOther.getGraphId())))
				&& ((this.getCustRelType() == castOther.getCustRelType()) || (this
						.getCustRelType() != null
						&& castOther.getCustRelType() != null && this
						.getCustRelType().equals(castOther.getCustRelType())))
				&& ((this.getCustRelName() == castOther.getCustRelName()) || (this
						.getCustRelName() != null
						&& castOther.getCustRelName() != null && this
						.getCustRelName().equals(castOther.getCustRelName())))
				&& ((this.getCustRelDesc() == castOther.getCustRelDesc()) || (this
						.getCustRelDesc() != null
						&& castOther.getCustRelDesc() != null && this
						.getCustRelDesc().equals(castOther.getCustRelDesc())))
				&& ((this.getSrcCustId() == castOther.getSrcCustId()) || (this
						.getSrcCustId() != null
						&& castOther.getSrcCustId() != null && this
						.getSrcCustId().equals(castOther.getSrcCustId())))
				&& ((this.getDestCustId() == castOther.getDestCustId()) || (this
						.getDestCustId() != null
						&& castOther.getDestCustId() != null && this
						.getDestCustId().equals(castOther.getDestCustId())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCustRelId() == null ? 0 : this.getCustRelId().hashCode());
		result = 37 * result
				+ (getGraphId() == null ? 0 : this.getGraphId().hashCode());
		result = 37
				* result
				+ (getCustRelType() == null ? 0 : this.getCustRelType()
						.hashCode());
		result = 37
				* result
				+ (getCustRelName() == null ? 0 : this.getCustRelName()
						.hashCode());
		result = 37
				* result
				+ (getCustRelDesc() == null ? 0 : this.getCustRelDesc()
						.hashCode());
		result = 37 * result
				+ (getSrcCustId() == null ? 0 : this.getSrcCustId().hashCode());
		result = 37
				* result
				+ (getDestCustId() == null ? 0 : this.getDestCustId()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}