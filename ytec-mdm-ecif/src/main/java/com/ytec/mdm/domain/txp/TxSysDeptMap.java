package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxSysDeptMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SYS_DEPT_MAP")
public class TxSysDeptMap implements java.io.Serializable {

	// Fields

	private Long mapId;
	private String deptCd;
	private String srcSysCd;

	// Constructors

	/** default constructor */
	public TxSysDeptMap() {
	}

	/** full constructor */
	public TxSysDeptMap(String deptCd, String srcSysCd) {
		this.deptCd = deptCd;
		this.srcSysCd = srcSysCd;
	}

	// Property accessors
	@Id
		@Column(name = "MAP_ID", unique = true, nullable = false)
	public Long getMapId() {
		return this.mapId;
	}

	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}

	@Column(name = "DEPT_CD", length = 20)
	public String getDeptCd() {
		return this.deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

}