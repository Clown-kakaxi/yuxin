package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_MSG_NODE_ATTR_CT database table.
 * 
 */
@Entity
@Table(name="TX_MSG_NODE_ATTR_CT")
public class TxMsgNodeAttrCt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_NODE_ATTR_CT_CTID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_NODE_ATTR_CT_CTID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG_NODE_ATTR_CT") })
	@GenericGenerator(name = "TX_MSG_NODE_ATTR_CT_CTID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")

	@Column(name="CT_ID", unique=true, nullable=false)
	private Long ctId;

	@Column(name="ATTR_ID", nullable=false)
	private Long attrId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="CT_DESC", length=255)
	private String ctDesc;

	@Column(name="CT_FLAG", nullable=false, length=1)
	private String ctFlag;

	@Column(name="CT_RULE", nullable=false, length=6)
	private String ctRule;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;

    public TxMsgNodeAttrCt() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
    public Long getCtId() {
		return this.ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
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

	public String getCtDesc() {
		return this.ctDesc;
	}

	public void setCtDesc(String ctDesc) {
		this.ctDesc = ctDesc;
	}

	public String getCtFlag() {
		return this.ctFlag;
	}

	public void setCtFlag(String ctFlag) {
		this.ctFlag = ctFlag;
	}

	public String getCtRule() {
		return this.ctRule;
	}

	public void setCtRule(String ctRule) {
		this.ctRule = ctRule;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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