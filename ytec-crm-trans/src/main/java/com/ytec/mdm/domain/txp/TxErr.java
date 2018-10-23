package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxErr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ERR")
public class TxErr implements java.io.Serializable {

	// Fields

	private Long txErrId;
	private String txFwId;
	private Long txId;
	private String txCode;
	private String txName;
	private String txCnName;
	private String txMethod;
	private Date txDt;
	private Timestamp txReqTm;
	private Timestamp txResTm;
	private String txResult;
	private String txRtnCd;
	private String txRtnMsg;
	private String txSvrIp;
	private String srcSysCd;
	private String srcSysNm;
	private String reqMsg;
	private String resMsg;

	// Constructors

	/** default constructor */
	public TxErr() {
	}

	/** full constructor */
	public TxErr(String txFwId, Long txId, String txCode, String txName,
			String txCnName, String txMethod, Date txDt, Timestamp txReqTm,
			Timestamp txResTm, String txResult, String txRtnCd,
			String txRtnMsg, String txSvrIp, String srcSysCd, String srcSysNm,
			String reqMsg, String resMsg) {
		this.txFwId = txFwId;
		this.txId = txId;
		this.txCode = txCode;
		this.txName = txName;
		this.txCnName = txCnName;
		this.txMethod = txMethod;
		this.txDt = txDt;
		this.txReqTm = txReqTm;
		this.txResTm = txResTm;
		this.txResult = txResult;
		this.txRtnCd = txRtnCd;
		this.txRtnMsg = txRtnMsg;
		this.txSvrIp = txSvrIp;
		this.srcSysCd = srcSysCd;
		this.srcSysNm = srcSysNm;
		this.reqMsg = reqMsg;
		this.resMsg = resMsg;
	}

	// Property accessors
	@Id
	@Column(name = "TX_ERR_ID", unique = true, nullable = false)
	public Long getTxErrId() {
		return this.txErrId;
	}

	public void setTxErrId(Long txErrId) {
		this.txErrId = txErrId;
	}

	@Column(name = "TX_FW_ID", length = 20)
	public String getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(String txFwId) {
		this.txFwId = txFwId;
	}

	@Column(name = "TX_ID")
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

	@Column(name = "TX_METHOD", length = 10)
	public String getTxMethod() {
		return this.txMethod;
	}

	public void setTxMethod(String txMethod) {
		this.txMethod = txMethod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DT", length = 7)
	public Date getTxDt() {
		return this.txDt;
	}

	public void setTxDt(Date txDt) {
		this.txDt = txDt;
	}

	@Column(name = "TX_REQ_TM", length = 11)
	public Timestamp getTxReqTm() {
		return this.txReqTm;
	}

	public void setTxReqTm(Timestamp txReqTm) {
		this.txReqTm = txReqTm;
	}

	@Column(name = "TX_RES_TM", length = 11)
	public Timestamp getTxResTm() {
		return this.txResTm;
	}

	public void setTxResTm(Timestamp txResTm) {
		this.txResTm = txResTm;
	}

	@Column(name = "TX_RESULT", length = 1)
	public String getTxResult() {
		return this.txResult;
	}

	public void setTxResult(String txResult) {
		this.txResult = txResult;
	}

	@Column(name = "TX_RTN_CD", length = 10)
	public String getTxRtnCd() {
		return this.txRtnCd;
	}

	public void setTxRtnCd(String txRtnCd) {
		this.txRtnCd = txRtnCd;
	}

	@Column(name = "TX_RTN_MSG")
	public String getTxRtnMsg() {
		return this.txRtnMsg;
	}

	public void setTxRtnMsg(String txRtnMsg) {
		this.txRtnMsg = txRtnMsg;
	}

	@Column(name = "TX_SVR_IP", length = 64)
	public String getTxSvrIp() {
		return this.txSvrIp;
	}

	public void setTxSvrIp(String txSvrIp) {
		this.txSvrIp = txSvrIp;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "SRC_SYS_NM", length = 20)
	public String getSrcSysNm() {
		return this.srcSysNm;
	}

	public void setSrcSysNm(String srcSysNm) {
		this.srcSysNm = srcSysNm;
	}

	@Column(name = "REQ_MSG")
	public String getReqMsg() {
		return this.reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	@Column(name = "RES_MSG")
	public String getResMsg() {
		return this.resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

}