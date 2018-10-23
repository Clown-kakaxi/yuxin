package com.yuchengtech.emp.ecif.transaction.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * TxClientAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_CLIENT_AUTH")
public class TxClientAuth implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_CLIENT_AUTH_CLIENTAUTHID_GENERATOR")
	@GenericGenerator(name = "TX_CLIENT_AUTH_CLIENTAUTHID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_CLIENT_AUTH") })	
	@Column(name = "CLIENT_AUTH_ID", unique = true, nullable = false)
	private Long clientAuthId;

	@Column(name = "SRC_SYS_CD", nullable = false)
	private String srcSysCd;

	@Column(name = "USERNAME", nullable = false, length = 32)
	private String username;

	@Column(name = "PASSWORD", length = 128)
	private String password;

	@Column(name = "ENC_PWD", length = 128)
	private String encPwd;

	@Column(name = "IPADDR", length = 64)
	private String ipaddr;

	@Column(name = "IPV6ADDR", length = 64)
	private String ipv6addr;

	@Column(name = "MACADDR", length = 64)
	private String macaddr;

	@Column(name = "START_DT", length = 10)
	private Date startDt;

	@Column(name = "END_DT", length = 10)
    private Date endDt;
	
	@Column(name = "FLAG", nullable = false, length = 1)
	private String flag;

	@Column(name = "STATE", length = 1)
	private String state;

	@Column(name = "CREATE_TM", length = 26)
	private Timestamp createTm;
	
	@Column(name = "CREATE_USER", length = 20)
	private String createUser;
	
	@Column(name = "UPDATE_TM", length = 26)
	private Timestamp updateTm;

	@Column(name = "UPDATE_USER", length = 20)
	private String updateUser;

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getClientAuthId() {
		return this.clientAuthId;
	}

	public void setClientAuthId(Long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}

	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncPwd() {
		return this.encPwd;
	}

	public void setEncPwd(String encPwd) {
		this.encPwd = encPwd;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getIpv6addr() {
		return this.ipv6addr;
	}

	public void setIpv6addr(String ipv6addr) {
		this.ipv6addr = ipv6addr;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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