package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxStatMildAggrId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TxStatMildAggrId implements java.io.Serializable {

	// Fields

	private Date txDate;
	private String txSys;
	private String txChannel;
	private String custType;
	private String txKind;
	private String txCode;
	private String txTimeInterval;
	private String txRtnCode;
	private Long txDealTime;

	// Constructors

	/** default constructor */
	public TxStatMildAggrId() {
	}

	/** full constructor */
	public TxStatMildAggrId(Date txDate, String txSys, String txChannel,
			String custType, String txKind, String txCode,
			String txTimeInterval, String txRtnCode, Long txDealTime) {
		this.txDate = txDate;
		this.txSys = txSys;
		this.txChannel = txChannel;
		this.custType = custType;
		this.txKind = txKind;
		this.txCode = txCode;
		this.txTimeInterval = txTimeInterval;
		this.txRtnCode = txRtnCode;
		this.txDealTime = txDealTime;
	}

	// Property accessors
	@Temporal(TemporalType.DATE)
	@Column(name = "TX_DATE", nullable = false, length = 7)
	public Date getTxDate() {
		return this.txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	@Column(name = "TX_SYS", nullable = false, length = 20)
	public String getTxSys() {
		return this.txSys;
	}

	public void setTxSys(String txSys) {
		this.txSys = txSys;
	}

	@Column(name = "TX_CHANNEL", nullable = false, length = 20)
	public String getTxChannel() {
		return this.txChannel;
	}

	public void setTxChannel(String txChannel) {
		this.txChannel = txChannel;
	}

	@Column(name = "CUST_TYPE", nullable = false, length = 20)
	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Column(name = "TX_KIND", nullable = false, length = 20)
	public String getTxKind() {
		return this.txKind;
	}

	public void setTxKind(String txKind) {
		this.txKind = txKind;
	}

	@Column(name = "TX_CODE", nullable = false, length = 20)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	@Column(name = "TX_TIME_INTERVAL", nullable = false, length = 20)
	public String getTxTimeInterval() {
		return this.txTimeInterval;
	}

	public void setTxTimeInterval(String txTimeInterval) {
		this.txTimeInterval = txTimeInterval;
	}

	@Column(name = "TX_RTN_CODE", nullable = false, length = 10)
	public String getTxRtnCode() {
		return this.txRtnCode;
	}

	public void setTxRtnCode(String txRtnCode) {
		this.txRtnCode = txRtnCode;
	}

	@Column(name = "TX_DEAL_TIME", nullable = false, precision = 22)
	public Long getTxDealTime() {
		return this.txDealTime;
	}

	public void setTxDealTime(Long txDealTime) {
		this.txDealTime = txDealTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TxStatMildAggrId))
			return false;
		TxStatMildAggrId castOther = (TxStatMildAggrId) other;

		return ((this.getTxDate() == castOther.getTxDate()) || (this
				.getTxDate() != null
				&& castOther.getTxDate() != null && this.getTxDate().equals(
				castOther.getTxDate())))
				&& ((this.getTxSys() == castOther.getTxSys()) || (this
						.getTxSys() != null
						&& castOther.getTxSys() != null && this.getTxSys()
						.equals(castOther.getTxSys())))
				&& ((this.getTxChannel() == castOther.getTxChannel()) || (this
						.getTxChannel() != null
						&& castOther.getTxChannel() != null && this
						.getTxChannel().equals(castOther.getTxChannel())))
				&& ((this.getCustType() == castOther.getCustType()) || (this
						.getCustType() != null
						&& castOther.getCustType() != null && this
						.getCustType().equals(castOther.getCustType())))
				&& ((this.getTxKind() == castOther.getTxKind()) || (this
						.getTxKind() != null
						&& castOther.getTxKind() != null && this.getTxKind()
						.equals(castOther.getTxKind())))
				&& ((this.getTxCode() == castOther.getTxCode()) || (this
						.getTxCode() != null
						&& castOther.getTxCode() != null && this.getTxCode()
						.equals(castOther.getTxCode())))
				&& ((this.getTxTimeInterval() == castOther.getTxTimeInterval()) || (this
						.getTxTimeInterval() != null
						&& castOther.getTxTimeInterval() != null && this
						.getTxTimeInterval().equals(
								castOther.getTxTimeInterval())))
				&& ((this.getTxRtnCode() == castOther.getTxRtnCode()) || (this
						.getTxRtnCode() != null
						&& castOther.getTxRtnCode() != null && this
						.getTxRtnCode().equals(castOther.getTxRtnCode())))
				&& ((this.getTxDealTime() == castOther.getTxDealTime()) || (this
						.getTxDealTime() != null
						&& castOther.getTxDealTime() != null && this
						.getTxDealTime().equals(castOther.getTxDealTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTxDate() == null ? 0 : this.getTxDate().hashCode());
		result = 37 * result
				+ (getTxSys() == null ? 0 : this.getTxSys().hashCode());
		result = 37 * result
				+ (getTxChannel() == null ? 0 : this.getTxChannel().hashCode());
		result = 37 * result
				+ (getCustType() == null ? 0 : this.getCustType().hashCode());
		result = 37 * result
				+ (getTxKind() == null ? 0 : this.getTxKind().hashCode());
		result = 37 * result
				+ (getTxCode() == null ? 0 : this.getTxCode().hashCode());
		result = 37
				* result
				+ (getTxTimeInterval() == null ? 0 : this.getTxTimeInterval()
						.hashCode());
		result = 37 * result
				+ (getTxRtnCode() == null ? 0 : this.getTxRtnCode().hashCode());
		result = 37
				* result
				+ (getTxDealTime() == null ? 0 : this.getTxDealTime()
						.hashCode());
		return result;
	}

}