package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the PUBBRANCHINFO database table.
 * 
 */
@Entity
@Table(name="PUBBRANCHINFO")
public class Pubbranchinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BRCCODE",unique=true, nullable=false, length=9)
	private String brccode;

	@Column(name="ACCSYSID",length=3)
	private String accsysid;

	@Column(name="ADDRESS",length=70)
	private String address;

	@Column(name="AREACODE",length=6)
	private String areacode;

	@Column(name="BEGINDATE",length=10)
	private String begindate;

	@Column(name="BRANCHNO",length=3)
	private String branchno;

	@Column(name="BRCATTR",length=1)
	private String brcattr;

	@Column(name="BRCLVL")
	private Long brclvl;

	@Column(name="BRCNAME",length=40)
	private String brcname;

	@Column(name="BRCTYPE",length=2)
	private String brctype;

	@Column(name="CITYCODE",length=5)
	private String citycode;

	@Column(name="EXBILLBRC",length=9)
	private String exbillbrc;

	@Column(name="FBBRC",length=9)
	private String fbbrc;

	@Column(name="FLAG",length=8)
	private String flag;

	@Column(name="IPADDR",length=15)
	private String ipaddr;

	@Column(name="PHONE",length=18)
	private String phone;

	@Column(name="PROFITBRC",length=9)
	private String profitbrc;

	@Column(name="REMITBRC",length=9)
	private String remitbrc;

	@Column(name="SELBAL",length=1)
	private String selbal;

	@Column(name="SHNAME",length=20)
	private String shname;

	@Column(name="STATUS",length=1)
	private String status;

	@Column(name="ZIPCODE",length=6)
	private String zipcode;

    public Pubbranchinfo() {
    }

	public String getBrccode() {
		return this.brccode;
	}

	public void setBrccode(String brccode) {
		this.brccode = brccode;
	}

	public String getAccsysid() {
		return this.accsysid;
	}

	public void setAccsysid(String accsysid) {
		this.accsysid = accsysid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getBegindate() {
		return this.begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getBranchno() {
		return this.branchno;
	}

	public void setBranchno(String branchno) {
		this.branchno = branchno;
	}

	public String getBrcattr() {
		return this.brcattr;
	}

	public void setBrcattr(String brcattr) {
		this.brcattr = brcattr;
	}

	public Long getBrclvl() {
		return this.brclvl;
	}

	public void setBrclvl(Long brclvl) {
		this.brclvl = brclvl;
	}

	public String getBrcname() {
		return this.brcname;
	}

	public void setBrcname(String brcname) {
		this.brcname = brcname;
	}

	public String getBrctype() {
		return this.brctype;
	}

	public void setBrctype(String brctype) {
		this.brctype = brctype;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getExbillbrc() {
		return this.exbillbrc;
	}

	public void setExbillbrc(String exbillbrc) {
		this.exbillbrc = exbillbrc;
	}

	public String getFbbrc() {
		return this.fbbrc;
	}

	public void setFbbrc(String fbbrc) {
		this.fbbrc = fbbrc;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfitbrc() {
		return this.profitbrc;
	}

	public void setProfitbrc(String profitbrc) {
		this.profitbrc = profitbrc;
	}

	public String getRemitbrc() {
		return this.remitbrc;
	}

	public void setRemitbrc(String remitbrc) {
		this.remitbrc = remitbrc;
	}

	public String getSelbal() {
		return this.selbal;
	}

	public void setSelbal(String selbal) {
		this.selbal = selbal;
	}

	public String getShname() {
		return this.shname;
	}

	public void setShname(String shname) {
		this.shname = shname;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}