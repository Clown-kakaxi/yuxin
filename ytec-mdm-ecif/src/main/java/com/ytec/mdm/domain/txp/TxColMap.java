package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxColMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_COL_MAP")
public class TxColMap implements java.io.Serializable {

	// Fields

	private TxColMapId id;
	private String stdTab;
	private String stdCol;
	private String stdCate;
	private String stdFlag;
	private String trimFlag;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxColMap() {
	}

	/** minimal constructor */
	public TxColMap(TxColMapId id) {
		this.id = id;
	}

	/** full constructor */
	public TxColMap(TxColMapId id, String stdTab, String stdCol,
			String stdCate, String stdFlag, String trimFlag, String state,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.id = id;
		this.stdTab = stdTab;
		this.stdCol = stdCol;
		this.stdCate = stdCate;
		this.stdFlag = stdFlag;
		this.trimFlag = trimFlag;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "srcSysCd", column = @Column(name = "SRC_SYS_CD", nullable = false, length = 20)),
			@AttributeOverride(name = "srcTab", column = @Column(name = "SRC_TAB", nullable = false, length = 60)),
			@AttributeOverride(name = "srcCol", column = @Column(name = "SRC_COL", nullable = false, length = 60)) })
	public TxColMapId getId() {
		return this.id;
	}

	public void setId(TxColMapId id) {
		this.id = id;
	}

	@Column(name = "STD_TAB", length = 60)
	public String getStdTab() {
		return this.stdTab;
	}

	public void setStdTab(String stdTab) {
		this.stdTab = stdTab;
	}

	@Column(name = "STD_COL", length = 60)
	public String getStdCol() {
		return this.stdCol;
	}

	public void setStdCol(String stdCol) {
		this.stdCol = stdCol;
	}

	@Column(name = "STD_CATE", length = 20)
	public String getStdCate() {
		return this.stdCate;
	}

	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	@Column(name = "STD_FLAG", length = 1)
	public String getStdFlag() {
		return this.stdFlag;
	}

	public void setStdFlag(String stdFlag) {
		this.stdFlag = stdFlag;
	}

	@Column(name = "TRIM_FLAG", length = 1)
	public String getTrimFlag() {
		return this.trimFlag;
	}

	public void setTrimFlag(String trimFlag) {
		this.trimFlag = trimFlag;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}