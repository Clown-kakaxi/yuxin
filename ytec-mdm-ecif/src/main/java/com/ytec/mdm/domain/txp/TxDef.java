package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_DEF")
public class TxDef implements java.io.Serializable {

	// Fields

	private Long txId;
	private String txCode;
	private String txName;
	private String txCnName;
	private String txDesc;
	private String txLvl1Tp;
	private String txLvl2Tp;
	private String txCfgTp;
	private String txDealClass;
	private String txState;
	private String txDealEngine;
	private String txCustType;
	private String txDiscRul;
	private String txCheckXsd;
	private String txDivInsUpd;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxDef() {
	}

	/** minimal constructor */
	public TxDef(Long txId) {
		this.txId = txId;
	}

	/** full constructor */
	public TxDef(Long txId, String txCode, String txName,
			String txCnName, String txDesc, String txLvl1Tp, String txLvl2Tp,
			String txCfgTp, String txDealClass, String txState,
			String txDealEngine, String txCustType, String txDiscRul,
			String txCheckXsd ,String txDivInsUpd, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.txId = txId;
		this.txCode = txCode;
		this.txName = txName;
		this.txCnName = txCnName;
		this.txDesc = txDesc;
		this.txLvl1Tp = txLvl1Tp;
		this.txLvl2Tp = txLvl2Tp;
		this.txCfgTp = txCfgTp;
		this.txDealClass = txDealClass;
		this.txState = txState;
		this.txDealEngine = txDealEngine;
		this.txCustType = txCustType;
		this.txDiscRul = txDiscRul;
		this.txCheckXsd = txCheckXsd;
		this.txDivInsUpd = txDivInsUpd;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "TX_ID", unique = true, nullable = false)
	public Long getTxId() {
		return this.txId;
	}

	public void setTxId(Long txId) {
		this.txId = txId;
	}

	@Column(name = "TX_CODE", length = 32)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	@Column(name = "TX_NAME", length = 40)
	public String getTxName() {
		return this.txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	@Column(name = "TX_CN_NAME", length = 80)
	public String getTxCnName() {
		return this.txCnName;
	}

	public void setTxCnName(String txCnName) {
		this.txCnName = txCnName;
	}

	@Column(name = "TX_DESC")
	public String getTxDesc() {
		return this.txDesc;
	}

	public void setTxDesc(String txDesc) {
		this.txDesc = txDesc;
	}

	@Column(name = "TX_LVL1_TP", length = 1)
	public String getTxLvl1Tp() {
		return this.txLvl1Tp;
	}

	public void setTxLvl1Tp(String txLvl1Tp) {
		this.txLvl1Tp = txLvl1Tp;
	}

	@Column(name = "TX_LVL2_TP", length = 10)
	public String getTxLvl2Tp() {
		return this.txLvl2Tp;
	}

	public void setTxLvl2Tp(String txLvl2Tp) {
		this.txLvl2Tp = txLvl2Tp;
	}

	@Column(name = "TX_CFG_TP", length = 1)
	public String getTxCfgTp() {
		return this.txCfgTp;
	}

	public void setTxCfgTp(String txCfgTp) {
		this.txCfgTp = txCfgTp;
	}

	@Column(name = "TX_DEAL_CLASS")
	public String getTxDealClass() {
		return this.txDealClass;
	}

	public void setTxDealClass(String txDealClass) {
		this.txDealClass = txDealClass;
	}

	@Column(name = "TX_STATE", length = 1)
	public String getTxState() {
		return this.txState;
	}

	public void setTxState(String txState) {
		this.txState = txState;
	}

	@Column(name = "TX_DEAL_ENGINE")
	public String getTxDealEngine() {
		return this.txDealEngine;
	}

	public void setTxDealEngine(String txDealEngine) {
		this.txDealEngine = txDealEngine;
	}

	@Column(name = "TX_CUST_TYPE", length = 20)
	public String getTxCustType() {
		return this.txCustType;
	}

	public void setTxCustType(String txCustType) {
		this.txCustType = txCustType;
	}

	@Column(name = "TX_DISC_RUL", length = 20)
	public String getTxDiscRul() {
		return this.txDiscRul;
	}

	public void setTxDiscRul(String txDiscRul) {
		this.txDiscRul = txDiscRul;
	}

	@Column(name = "TX_CHECK_XSD", length = 20)
	public String getTxCheckXsd() {
		return this.txCheckXsd;
	}

	public void setTxCheckXsd(String txCheckXsd) {
		this.txCheckXsd = txCheckXsd;
	}
	
	@Column(name = "TX_DIV_INS_UPD", length = 1)
	public String getTxDivInsUpd() {
		return txDivInsUpd;
	}

	public void setTxDivInsUpd(String txDivInsUpd) {
		this.txDivInsUpd = txDivInsUpd;
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