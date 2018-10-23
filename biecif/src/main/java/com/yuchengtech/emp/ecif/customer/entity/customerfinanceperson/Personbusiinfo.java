package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONBUSIINFO database table.
 * 
 */
@Entity
@Table(name="PERSONBUSIINFO")
public class Personbusiinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_BUSI_INFO_ID", unique=true, nullable=false)
	private Long personBusiInfoId;

	@Column(name="ADDRDEALYEAR",precision=5)
	private BigDecimal addrdealyear;

	@Column(name="ASSETAMT",precision=17, scale=2)
	private BigDecimal assetamt;

	@Column(name="BASICACNO",length=20)
	private String basicacno;

	@Column(name="BASICBANKNAME",length=100)
	private String basicbankname;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DAYMAXSALEAMT",precision=17, scale=2)
	private BigDecimal daymaxsaleamt;

	@Column(name="DAYMINSALEAMT",precision=17, scale=2)
	private BigDecimal dayminsaleamt;

	@Column(name="DAYTOTALSALEAMT",precision=17, scale=2)
	private BigDecimal daytotalsaleamt;

	@Column(name="DEALADDR",length=80)
	private String dealaddr;

	@Column(name="DEALCASH",precision=17, scale=2)
	private BigDecimal dealcash;

	@Column(name="DEALPROJ",length=40)
	private String dealproj;

	@Column(name="DEALSAVINGS",precision=17, scale=2)
	private BigDecimal dealsavings;

	@Column(name="DEALSITETYPE",length=2)
	private String dealsitetype;

	@Column(name="DEPOFREQ",length=60)
	private String depofreq;

	@Column(name="DOWNCUSTNUMTYPE",length=1)
	private String downcustnumtype;

	@Column(name="FULLTIMENUM")
	private Long fulltimenum;

	@Column(name="INCOMEAMT",precision=17, scale=2)
	private BigDecimal incomeamt;

	@Column(name="INCOMMOWNERSHIP",length=1)
	private String incommownership;

	@Column(name="INNERENVINDUSTRYTYPE",length=20)
	private String innerenvindustrytype;

	@Column(name="KEYNO",length=20)
	private String keyno;

	@Column(name="LASTMONTHSALEAMT",precision=17, scale=2)
	private BigDecimal lastmonthsaleamt;

	@Column(name="LIABAMT",precision=17, scale=2)
	private BigDecimal liabamt;

	@Column(name="LICEADDR",length=80)
	private String liceaddr;

	@Column(name="LICEDATE",length=1)
	private String licedate;

	@Column(name="LICEENDDATE",length=20)
	private String liceenddate;

	@Column(name="LICEID",length=40)
	private String liceid;

	@Column(name="LICEIDTYPE",length=1)
	private String liceidtype;

	@Column(name="LICEISFULL",length=1)
	private String liceisfull;

	@Column(name="LICELIFE")
	private Long licelife;

	@Column(name="LICENSENO",length=40)
	private String licenseno;

	@Column(name="MAINDEALSCOPE",length=80)
	private String maindealscope;

	@Column(name="MANAGEFORM",length=40)
	private String manageform;

	@Column(name="MONTHINCOME",precision=17, scale=2)
	private BigDecimal monthincome;

	@Column(name="MONTHPAY",precision=17, scale=2)
	private BigDecimal monthpay;

	@Column(name="MONTHPROFIT",precision=17, scale=2)
	private BigDecimal monthprofit;

	@Column(name="OCCUINDUSTRY",length=20)
	private String occuindustry;

	@Column(name="OCCUINDUSTRYTYPE",length=20)
	private String occuindustrytype;

	@Column(name="OTHERINCOME",precision=17, scale=2)
	private BigDecimal otherincome;

	@Column(name="PARTNERNUM")
	private Long partnernum;

	@Column(name="REGCAPITAL",precision=17, scale=2)
	private BigDecimal regcapital;

	@Column(name="REGISTDATE",length=20)
	private String registdate;

	@Column(name="RESPONSIBLEPERSON",length=60)
	private String responsibleperson;

	@Column(name="STOCKPER",precision=5, scale=2)
	private BigDecimal stockper;

	@Column(name="STOREADDR",length=100)
	private String storeaddr;

	@Column(name="STORECHECKDATE",length=20)
	private Date storecheckdate;

	@Column(name="STOREESTIAMT",precision=17, scale=2)
	private BigDecimal storeestiamt;

	@Column(name="TAXREGNO",length=20)
	private String taxregno;

	@Column(name="TRANRECORD",length=1)
	private String tranrecord;

	@Column(name="TURNCAPI",precision=17, scale=2)
	private BigDecimal turncapi;

	@Column(name="UPCUSTNUMTYPE",length=1)
	private String upcustnumtype;

	@Column(name="WORKINGTIME",length=20)
	private String workingtime;

	@Column(name="YEARAMT",precision=17, scale=2)
	private BigDecimal yearamt;

	@Column(name="YEARPROFAMT",precision=17, scale=2)
	private BigDecimal yearprofamt;

	@Column(name="YEARSALEAMT",precision=17, scale=2)
	private BigDecimal yearsaleamt;

    public Personbusiinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonBusiInfoId() {
		return personBusiInfoId;
	}

	public void setPersonBusiInfoId(Long personBusiInfoId) {
		this.personBusiInfoId = personBusiInfoId;
	}

	public BigDecimal getAddrdealyear() {
		return addrdealyear;
	}

	public void setAddrdealyear(BigDecimal addrdealyear) {
		this.addrdealyear = addrdealyear;
	}

	public BigDecimal getAssetamt() {
		return assetamt;
	}

	public void setAssetamt(BigDecimal assetamt) {
		this.assetamt = assetamt;
	}

	public String getBasicacno() {
		return basicacno;
	}

	public void setBasicacno(String basicacno) {
		this.basicacno = basicacno;
	}

	public String getBasicbankname() {
		return basicbankname;
	}

	public void setBasicbankname(String basicbankname) {
		this.basicbankname = basicbankname;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public BigDecimal getDaymaxsaleamt() {
		return daymaxsaleamt;
	}

	public void setDaymaxsaleamt(BigDecimal daymaxsaleamt) {
		this.daymaxsaleamt = daymaxsaleamt;
	}

	public BigDecimal getDayminsaleamt() {
		return dayminsaleamt;
	}

	public void setDayminsaleamt(BigDecimal dayminsaleamt) {
		this.dayminsaleamt = dayminsaleamt;
	}

	public BigDecimal getDaytotalsaleamt() {
		return daytotalsaleamt;
	}

	public void setDaytotalsaleamt(BigDecimal daytotalsaleamt) {
		this.daytotalsaleamt = daytotalsaleamt;
	}

	public String getDealaddr() {
		return dealaddr;
	}

	public void setDealaddr(String dealaddr) {
		this.dealaddr = dealaddr;
	}

	public BigDecimal getDealcash() {
		return dealcash;
	}

	public void setDealcash(BigDecimal dealcash) {
		this.dealcash = dealcash;
	}

	public String getDealproj() {
		return dealproj;
	}

	public void setDealproj(String dealproj) {
		this.dealproj = dealproj;
	}

	public BigDecimal getDealsavings() {
		return dealsavings;
	}

	public void setDealsavings(BigDecimal dealsavings) {
		this.dealsavings = dealsavings;
	}

	public String getDealsitetype() {
		return dealsitetype;
	}

	public void setDealsitetype(String dealsitetype) {
		this.dealsitetype = dealsitetype;
	}

	public String getDepofreq() {
		return depofreq;
	}

	public void setDepofreq(String depofreq) {
		this.depofreq = depofreq;
	}

	public String getDowncustnumtype() {
		return downcustnumtype;
	}

	public void setDowncustnumtype(String downcustnumtype) {
		this.downcustnumtype = downcustnumtype;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getFulltimenum() {
		return fulltimenum;
	}

	public void setFulltimenum(Long fulltimenum) {
		this.fulltimenum = fulltimenum;
	}

	public BigDecimal getIncomeamt() {
		return incomeamt;
	}

	public void setIncomeamt(BigDecimal incomeamt) {
		this.incomeamt = incomeamt;
	}

	public String getIncommownership() {
		return incommownership;
	}

	public void setIncommownership(String incommownership) {
		this.incommownership = incommownership;
	}

	public String getInnerenvindustrytype() {
		return innerenvindustrytype;
	}

	public void setInnerenvindustrytype(String innerenvindustrytype) {
		this.innerenvindustrytype = innerenvindustrytype;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public BigDecimal getLastmonthsaleamt() {
		return lastmonthsaleamt;
	}

	public void setLastmonthsaleamt(BigDecimal lastmonthsaleamt) {
		this.lastmonthsaleamt = lastmonthsaleamt;
	}

	public BigDecimal getLiabamt() {
		return liabamt;
	}

	public void setLiabamt(BigDecimal liabamt) {
		this.liabamt = liabamt;
	}

	public String getLiceaddr() {
		return liceaddr;
	}

	public void setLiceaddr(String liceaddr) {
		this.liceaddr = liceaddr;
	}

	public String getLicedate() {
		return licedate;
	}

	public void setLicedate(String licedate) {
		this.licedate = licedate;
	}

	public String getLiceenddate() {
		return liceenddate;
	}

	public void setLiceenddate(String liceenddate) {
		this.liceenddate = liceenddate;
	}

	public String getLiceid() {
		return liceid;
	}

	public void setLiceid(String liceid) {
		this.liceid = liceid;
	}

	public String getLiceidtype() {
		return liceidtype;
	}

	public void setLiceidtype(String liceidtype) {
		this.liceidtype = liceidtype;
	}

	public String getLiceisfull() {
		return liceisfull;
	}

	public void setLiceisfull(String liceisfull) {
		this.liceisfull = liceisfull;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLicelife() {
		return licelife;
	}

	public void setLicelife(Long licelife) {
		this.licelife = licelife;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getMaindealscope() {
		return maindealscope;
	}

	public void setMaindealscope(String maindealscope) {
		this.maindealscope = maindealscope;
	}

	public String getManageform() {
		return manageform;
	}

	public void setManageform(String manageform) {
		this.manageform = manageform;
	}

	public BigDecimal getMonthincome() {
		return monthincome;
	}

	public void setMonthincome(BigDecimal monthincome) {
		this.monthincome = monthincome;
	}

	public BigDecimal getMonthpay() {
		return monthpay;
	}

	public void setMonthpay(BigDecimal monthpay) {
		this.monthpay = monthpay;
	}

	public BigDecimal getMonthprofit() {
		return monthprofit;
	}

	public void setMonthprofit(BigDecimal monthprofit) {
		this.monthprofit = monthprofit;
	}

	public String getOccuindustry() {
		return occuindustry;
	}

	public void setOccuindustry(String occuindustry) {
		this.occuindustry = occuindustry;
	}

	public String getOccuindustrytype() {
		return occuindustrytype;
	}

	public void setOccuindustrytype(String occuindustrytype) {
		this.occuindustrytype = occuindustrytype;
	}

	public BigDecimal getOtherincome() {
		return otherincome;
	}

	public void setOtherincome(BigDecimal otherincome) {
		this.otherincome = otherincome;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPartnernum() {
		return partnernum;
	}

	public void setPartnernum(Long partnernum) {
		this.partnernum = partnernum;
	}

	public BigDecimal getRegcapital() {
		return regcapital;
	}

	public void setRegcapital(BigDecimal regcapital) {
		this.regcapital = regcapital;
	}

	public String getRegistdate() {
		return registdate;
	}

	public void setRegistdate(String registdate) {
		this.registdate = registdate;
	}

	public String getResponsibleperson() {
		return responsibleperson;
	}

	public void setResponsibleperson(String responsibleperson) {
		this.responsibleperson = responsibleperson;
	}

	public BigDecimal getStockper() {
		return stockper;
	}

	public void setStockper(BigDecimal stockper) {
		this.stockper = stockper;
	}

	public String getStoreaddr() {
		return storeaddr;
	}

	public void setStoreaddr(String storeaddr) {
		this.storeaddr = storeaddr;
	}

	public Date getStorecheckdate() {
		return storecheckdate;
	}

	public void setStorecheckdate(Date storecheckdate) {
		this.storecheckdate = storecheckdate;
	}

	public BigDecimal getStoreestiamt() {
		return storeestiamt;
	}

	public void setStoreestiamt(BigDecimal storeestiamt) {
		this.storeestiamt = storeestiamt;
	}

	public String getTaxregno() {
		return taxregno;
	}

	public void setTaxregno(String taxregno) {
		this.taxregno = taxregno;
	}

	public String getTranrecord() {
		return tranrecord;
	}

	public void setTranrecord(String tranrecord) {
		this.tranrecord = tranrecord;
	}

	public BigDecimal getTurncapi() {
		return turncapi;
	}

	public void setTurncapi(BigDecimal turncapi) {
		this.turncapi = turncapi;
	}

	public String getUpcustnumtype() {
		return upcustnumtype;
	}

	public void setUpcustnumtype(String upcustnumtype) {
		this.upcustnumtype = upcustnumtype;
	}

	public String getWorkingtime() {
		return workingtime;
	}

	public void setWorkingtime(String workingtime) {
		this.workingtime = workingtime;
	}

	public BigDecimal getYearamt() {
		return yearamt;
	}

	public void setYearamt(BigDecimal yearamt) {
		this.yearamt = yearamt;
	}

	public BigDecimal getYearprofamt() {
		return yearprofamt;
	}

	public void setYearprofamt(BigDecimal yearprofamt) {
		this.yearprofamt = yearprofamt;
	}

	public BigDecimal getYearsaleamt() {
		return yearsaleamt;
	}

	public void setYearsaleamt(BigDecimal yearsaleamt) {
		this.yearsaleamt = yearsaleamt;
	}

}