package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TX_COL_MAP database table.
 * 
 */
@Entity
@Table(name="TX_COL_MAP")
public class TxColMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TxColMapPK id;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="STATE")
	private String state;

	@Column(name="STD_CATE")
	private String stdCate;

	@Column(name="STD_COL")
	private String stdCol;

	@Column(name="STD_FLAG")
	private String stdFlag;

	@Column(name="STD_TAB")
	private String stdTab;

	@Column(name="TRIM_FLAG")
	private String trimFlag;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxColMap() {
    }

	public TxColMapPK getId() {
		return this.id;
	}

	public void setId(TxColMapPK id) {
		this.id = id;
	}
	
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStdCate() {
		return this.stdCate;
	}

	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	public String getStdCol() {
		return this.stdCol;
	}

	public void setStdCol(String stdCol) {
		this.stdCol = stdCol;
	}

	public String getStdFlag() {
		return this.stdFlag;
	}

	public void setStdFlag(String stdFlag) {
		this.stdFlag = stdFlag;
	}

	public String getStdTab() {
		return this.stdTab;
	}

	public void setStdTab(String stdTab) {
		this.stdTab = stdTab;
	}

	public String getTrimFlag() {
		return this.trimFlag;
	}

	public void setTrimFlag(String trimFlag) {
		this.trimFlag = trimFlag;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}