package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the TX_LOG database table.
 * 
 */
@Entity
@Table(name="TX_LOG")
public class TxLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TX_LOG_ID")
	private long txLogId;

    @Lob()
	@Column(name="REQ_MSG")
	private String reqMsg;

    @Lob()
	@Column(name="RES_MSG")
	private String resMsg;

	@Column(name="SRC_SYS_CD")
	private String srcSysCd;

	@Column(name="SRC_SYS_NM")
	private String srcSysNm;

	@Column(name="TX_CN_NAME")
	private String txCnName;

	@Column(name="TX_CODE")
	private String txCode;

    @Temporal( TemporalType.DATE)
	@Column(name="TX_DT")
	private Date txDt;

	@Column(name="TX_FW_ID")
	private String txFwId;

	@Column(name="TX_ID")
	private long txId;

	@Column(name="TX_METHOD")
	private String txMethod;

	@Column(name="TX_NAME")
	private String txName;

	@Column(name="TX_REQ_TM")
	private Timestamp txReqTm;

	@Column(name="TX_RES_TM")
	private Timestamp txResTm;

	@Column(name="TX_RESULT")
	private String txResult;

	@Column(name="TX_RTN_CD")
	private String txRtnCd;

	@Column(name="TX_RTN_MSG")
	private String txRtnMsg;

	@Column(name="TX_SVR_IP")
	private String txSvrIp;

    public TxLog() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public long getTxLogId() {
		return this.txLogId;
	}

	public void setTxLogId(long txLogId) {
		this.txLogId = txLogId;
	}

	public String getReqMsg() {
		return this.reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	public String getResMsg() {
		return this.resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	public String getSrcSysNm() {
		return this.srcSysNm;
	}

	public void setSrcSysNm(String srcSysNm) {
		this.srcSysNm = srcSysNm;
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

	public Date getTxDt() {
		return this.txDt;
	}

	public void setTxDt(Date txDt) {
		this.txDt = txDt;
	}

	public String getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(String txFwId) {
		this.txFwId = txFwId;
	}

	public long getTxId() {
		return this.txId;
	}

	public void setTxId(long txId) {
		this.txId = txId;
	}

	public String getTxMethod() {
		return this.txMethod;
	}

	public void setTxMethod(String txMethod) {
		this.txMethod = txMethod;
	}

	public String getTxName() {
		return this.txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public Timestamp getTxReqTm() {
		return this.txReqTm;
	}

	public void setTxReqTm(Timestamp txReqTm) {
		this.txReqTm = txReqTm;
	}

	public Timestamp getTxResTm() {
		return this.txResTm;
	}

	public void setTxResTm(Timestamp txResTm) {
		this.txResTm = txResTm;
	}

	public String getTxResult() {
		return this.txResult;
	}

	public void setTxResult(String txResult) {
		this.txResult = txResult;
	}

	public String getTxRtnCd() {
		return this.txRtnCd;
	}

	public void setTxRtnCd(String txRtnCd) {
		this.txRtnCd = txRtnCd;
	}

	public String getTxRtnMsg() {
		return this.txRtnMsg;
	}

	public void setTxRtnMsg(String txRtnMsg) {
		this.txRtnMsg = txRtnMsg;
	}

	public String getTxSvrIp() {
		return this.txSvrIp;
	}

	public void setTxSvrIp(String txSvrIp) {
		this.txSvrIp = txSvrIp;
	}

}