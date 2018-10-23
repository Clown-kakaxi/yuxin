package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Parameter;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;


import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_DEF database table.
 * 
 */
@Entity
@Table(name="TX_DEF")
public class TxDef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_DEF_TXID_GENERATOR")
//	@GenericGenerator(name = "TX_DEF_TXID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_DEF") })
	@GenericGenerator(name = "TX_DEF_TXID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="TX_ID")
	private Long txId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="STATE")
	private String state;

	@Column(name="TX_CFG_TP")
	private String txCfgTp;

	@Column(name="TX_CN_NAME")
	private String txCnName;

	@Column(name="TX_CODE")
	private String txCode;

	@Column(name="TX_DEAL_CLASS")
	private String txDealClass;

	@Column(name="TX_DEAL_ENGINE")
	private String txDealEngine;
	
	public String getTxDealEngine() {
		return txDealEngine;
	}

	public void setTxDealEngine(String txDealEngine) {
		this.txDealEngine = txDealEngine;
	}

	@Column(name="TX_DESC")
	private String txDesc;

	@Column(name="TX_LVL1_TP")
	private String txLvl1Tp;

	@Column(name="TX_LVL2_TP")
	private String txLvl2Tp;

	@Column(name="TX_NAME")
	private String txName;

	@Column(name="TX_CUST_TYPE")
	private String txCustType;

	@Column(name="TX_DISC_RUL")
	private String txDiscUrl;

	@Column(name="TX_CHECK_XSD")
	private String txCheckXsd;

	public String getTxCustType() {
		return txCustType;
	}

	public void setTxCustType(String txCustType) {
		this.txCustType = txCustType;
	}

	public String getTxDiscUrl() {
		return txDiscUrl;
	}

	public void setTxDiscUrl(String txDiscUrl) {
		this.txDiscUrl = txDiscUrl;
	}

	public String getTxCheckXsd() {
		return txCheckXsd;
	}

	public void setTxCheckXsd(String txCheckXsd) {
		this.txCheckXsd = txCheckXsd;
	}
	
	public String getTxDivInsUpd() {
		return txDivInsUpd;
	}

	public void setTxDivInsUpd(String txDivInsUpd) {
		this.txDivInsUpd = txDivInsUpd;
	}

	@Column(name="TX_DIV_INS_UPD")
	private String txDivInsUpd;	
	
	@Column(name="TX_STATE")
	private String txState;
	
	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxDef() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getTxId() {
		return this.txId;
	}

	public void setTxId(Long txId) {
		this.txId = txId;
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

	public String getTxCfgTp() {
		return this.txCfgTp;
	}

	public void setTxCfgTp(String txCfgTp) {
		this.txCfgTp = txCfgTp;
	}

	public String getTxCnName() {
		return this.txCnName;
	}

	public void setTxCnName(String txCnName) {
		this.txCnName = txCnName;
	}

	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTxDealClass() {
		return this.txDealClass;
	}

	public void setTxDealClass(String txDealClass) {
		this.txDealClass = txDealClass;
	}

	public String getTxDesc() {
		return this.txDesc;
	}

	public void setTxDesc(String txDesc) {
		this.txDesc = txDesc;
	}

	public String getTxLvl1Tp() {
		return this.txLvl1Tp;
	}

	public void setTxLvl1Tp(String txLvl1Tp) {
		this.txLvl1Tp = txLvl1Tp;
	}

	public String getTxLvl2Tp() {
		return this.txLvl2Tp;
	}

	public void setTxLvl2Tp(String txLvl2Tp) {
		this.txLvl2Tp = txLvl2Tp;
	}

	public String getTxName() {
		return this.txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public String getTxState() {
		return this.txState;
	}

	public void setTxState(String txState) {
		this.txState = txState;
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