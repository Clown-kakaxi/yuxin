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
 * TxClientAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_CLIENT_AUTH")
public class TxClientAuth implements java.io.Serializable {

	// Fields

	private Long clientAuthId;
	private String srcSysCd;
	private String username;
	private String password;
	private String encPwd;
	private String ipaddr;
	private String ipv6addr;
	private String macaddr;
	private Date startDt;
	private Date endDt;
	private String flag;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxClientAuth() {
	}

	/** full constructor */
	public TxClientAuth(String srcSysCd, String username, String password,
			String encPwd, String ipaddr, String ipv6addr, String macaddr,
			Date startDt, Date endDt, String flag, String state,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.srcSysCd = srcSysCd;
		this.username = username;
		this.password = password;
		this.encPwd = encPwd;
		this.ipaddr = ipaddr;
		this.ipv6addr = ipv6addr;
		this.macaddr = macaddr;
		this.startDt = startDt;
		this.endDt = endDt;
		this.flag = flag;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "CLIENT_AUTH_ID", unique = true, nullable = false)
	public Long getClientAuthId() {
		return this.clientAuthId;
	}

	public void setClientAuthId(Long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "USERNAME", length = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ENC_PWD", length = 128)
	public String getEncPwd() {
		return this.encPwd;
	}

	public void setEncPwd(String encPwd) {
		this.encPwd = encPwd;
	}

	@Column(name = "IPADDR", length = 64)
	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	@Column(name = "IPV6ADDR", length = 64)
	public String getIpv6addr() {
		return this.ipv6addr;
	}

	public void setIpv6addr(String ipv6addr) {
		this.ipv6addr = ipv6addr;
	}

	@Column(name = "MACADDR", length = 64)
	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DT", length = 7)
	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DT", length = 7)
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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