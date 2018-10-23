package com.yuchengtech.emp.ecif.transaction.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

public class TxClientAuthVO  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long clientAuthId;
	private Timestamp createTm;
	private String createUser;
	private String encPwd;
	private Date endDt;
	private String flag;
	private String ipaddr;
	private String ipv6addr;
	private String macaddr;
	private String password;
	private String srcSysCd;
	private Date startDt;
	private String state;
	private Timestamp updateTm;
	private String updateUser;
	private String username;
	private String srcSysNm;
	
	@JsonSerialize(using=BioneLongSerializer.class)
	public long getClientAuthId() {
		return clientAuthId;
	}
	public void setClientAuthId(long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}
	public Timestamp getCreateTm() {
		return createTm;
	}
	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getEncPwd() {
		return encPwd;
	}
	public void setEncPwd(String encPwd) {
		this.encPwd = encPwd;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public String getIpv6addr() {
		return ipv6addr;
	}
	public void setIpv6addr(String ipv6addr) {
		this.ipv6addr = ipv6addr;
	}
	public String getMacaddr() {
		return macaddr;
	}
	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSrcSysCd() {
		return srcSysCd;
	}
	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getUpdateTm() {
		return updateTm;
	}
	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSrcSysNm() {
		return srcSysNm;
	}
	public void setSrcSysNm(String srcSysNm) {
		this.srcSysNm = srcSysNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
