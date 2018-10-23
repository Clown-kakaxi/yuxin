package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;



/**
 * The persistent class for the IND_OASSET database table.
 * 
 */
@Entity
@Table(name="M_HL_OTHER_ASSETS")
public class IndOasset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ASSETDESCRIBE",length=200)
	private String assetdescribe;

	@Column(name="ASSETTYPE",length=18)
	private String assettype;

	@Column(name="ASSETVALUE",precision=17, scale=2)
	private BigDecimal assetvalue;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="UPDATEDATE", length=32)
	private String updatedate;

    @Column(name="UPTODATE", length=32)
	private String uptodate;

    public IndOasset() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getAssetdescribe() {
		return this.assetdescribe;
	}

	public void setAssetdescribe(String assetdescribe) {
		this.assetdescribe = assetdescribe;
	}

	public String getAssettype() {
		return this.assettype;
	}

	public void setAssettype(String assettype) {
		this.assettype = assettype;
	}

	public BigDecimal getAssetvalue() {
		return this.assetvalue;
	}

	public void setAssetvalue(BigDecimal assetvalue) {
		this.assetvalue = assetvalue;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getUptodate() {
		return this.uptodate;
	}

	public void setUptodate(String uptodate) {
		this.uptodate = uptodate;
	}

}