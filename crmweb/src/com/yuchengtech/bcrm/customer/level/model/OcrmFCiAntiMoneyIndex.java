package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_ANTI_MONEY_INDEX database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ANTI_MONEY_INDEX")
public class OcrmFCiAntiMoneyIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_ANTI_MONEY_INDEX_INDEXID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ANTI_MONEY_INDEX_INDEXID_GENERATOR")
	@Column(name="INDEX_ID")
	private Long indexId;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ID")
	private String createId;

	@Column(name="INDEX_CODE")
	private String indexCode;

	@Column(name="INDEX_DIC")
	private String indexDic;

	@Column(name="INDEX_NAME")
	private String indexName;

	@Column(name="INDEX_STATE")
	private String indexState;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LAST_VERIFIER")
	private String lastVerifier;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_VERIFY_TM")
	private Date lastVerifyTm;

	private String remark;

	@Column(name="VERIFLER_STAT")
	private String veriflerStat;
	
	@Column(name="INDEX_TYPE")
	private String indexType;

    public OcrmFCiAntiMoneyIndex() {
    }

	public Long getIndexId() {
		return this.indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getIndexCode() {
		return this.indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getIndexDic() {
		return this.indexDic;
	}

	public void setIndexDic(String indexDic) {
		this.indexDic = indexDic;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexState() {
		return this.indexState;
	}

	public void setIndexState(String indexState) {
		this.indexState = indexState;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLastVerifier() {
		return this.lastVerifier;
	}

	public void setLastVerifier(String lastVerifier) {
		this.lastVerifier = lastVerifier;
	}

	public Date getLastVerifyTm() {
		return this.lastVerifyTm;
	}

	public void setLastVerifyTm(Date lastVerifyTm) {
		this.lastVerifyTm = lastVerifyTm;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVeriflerStat() {
		return this.veriflerStat;
	}

	public void setVeriflerStat(String veriflerStat) {
		this.veriflerStat = veriflerStat;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

}