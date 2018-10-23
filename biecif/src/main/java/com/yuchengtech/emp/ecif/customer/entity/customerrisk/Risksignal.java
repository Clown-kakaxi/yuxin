package com.yuchengtech.emp.ecif.customer.entity.customerrisk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the RISKSIGNAL database table.
 * 
 */
@Entity
@Table(name="RISKSIGNAL")
public class Risksignal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RISK_SIGNAL_ID", unique=true, nullable=false)
	private Long riskSignalId;

	@Column(name="ACTIONFLAG",length=1)
	private String actionflag;

	@Column(name="ACTIONTYPE",length=32)
	private String actiontype;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="FINISHDATE",length=20)
	private String finishdate;

	@Column(name="FREEREASON",length=200)
	private String freereason;

	@Column(name="MESSAGECONTENT",length=200)
	private String messagecontent;

	@Column(name="MESSAGEORIGIN",length=18)
	private String messageorigin;

	@Column(name="OBJECTNO",length=32)
	private String objectno;

	@Column(name="OBJECTTYPE",length=18)
	private String objecttype;

	@Column(name="SIGNALCHANNEL",length=18)
	private String signalchannel;

	@Column(name="SIGNALNAME",length=500)
	private String signalname;

	@Column(name="SIGNALNO",length=18)
	private String signalno;

	@Column(name="SIGNALSTATUS",length=18)
	private String signalstatus;

	@Column(name="SIGNALTYPE",length=18)
	private String signaltype;

	@Column(name="SERIAL_NO",length=40)
	private String serialNo;

    public Risksignal() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getRiskSignalId() {
		return this.riskSignalId;
	}

	public void setRiskSignalId(Long riskSignalId) {
		this.riskSignalId = riskSignalId;
	}

	public String getActionflag() {
		return this.actionflag;
	}

	public void setActionflag(String actionflag) {
		this.actionflag = actionflag;
	}

	public String getActiontype() {
		return this.actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFinishdate() {
		return this.finishdate;
	}

	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
	}

	public String getFreereason() {
		return this.freereason;
	}

	public void setFreereason(String freereason) {
		this.freereason = freereason;
	}

	public String getMessagecontent() {
		return this.messagecontent;
	}

	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent;
	}

	public String getMessageorigin() {
		return this.messageorigin;
	}

	public void setMessageorigin(String messageorigin) {
		this.messageorigin = messageorigin;
	}

	public String getObjectno() {
		return this.objectno;
	}

	public void setObjectno(String objectno) {
		this.objectno = objectno;
	}

	public String getObjecttype() {
		return this.objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}

	public String getSignalchannel() {
		return this.signalchannel;
	}

	public void setSignalchannel(String signalchannel) {
		this.signalchannel = signalchannel;
	}

	public String getSignalname() {
		return this.signalname;
	}

	public void setSignalname(String signalname) {
		this.signalname = signalname;
	}

	public String getSignalno() {
		return this.signalno;
	}

	public void setSignalno(String signalno) {
		this.signalno = signalno;
	}

	public String getSignalstatus() {
		return this.signalstatus;
	}

	public void setSignalstatus(String signalstatus) {
		this.signalstatus = signalstatus;
	}

	public String getSignaltype() {
		return this.signaltype;
	}

	public void setSignaltype(String signaltype) {
		this.signaltype = signaltype;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}