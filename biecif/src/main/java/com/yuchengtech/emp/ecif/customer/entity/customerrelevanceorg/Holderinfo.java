package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the HOLDERINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_HOLDERINFO")
public class Holderinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDER_ID", unique=true, nullable=false)
	private Long holderInfoId;

//	@Column(name="CURRSIGN",length=20)
//	private String currsign;

	@Column(name="CUST_ID")
	private Long custId;

//	@Column(name="FUNDPCT",precision=5, scale=2)
//	private BigDecimal fundpct;

//	@Column(name="HOLDCUSTID")
//	private Long holdcustid;

	@Column(name="HOLDER_NAME",length=80)
	private String holdcustname;

	@Column(name="HOLDER_TYPE",length=20)
	private String holdcusttype;

//	@Column(name="HOLDRELENOTE",length=500)
//	private String holdrelenote;

	@Column(name="INDUSTRY_POSITION",length=40)
	private String industryposition;

//	@Column(name="INVEDATE",length=20)
//	private String invedate;

//	@Column(name="ISCHECKFLAG",length=1)
//	private String ischeckflag;

	@Column(name="IS_OFFENCE_FLAG",length=1)
	private String isoffenceflag;

//	@Column(name="KNOWINVEAMT",precision=17, scale=2)
//	private BigDecimal knowinveamt;

//	@Column(name="KNOWINVEREALAMT",precision=17, scale=2)
//	private BigDecimal knowinverealamt;

//	@Column(name="LANDINVEAMT",precision=17, scale=2)
//	private BigDecimal landinveamt;

//	@Column(name="LANDINVEREALAMT",precision=17, scale=2)
//	private BigDecimal landinverealamt;

//	@Column(name="MONEYINVEAMT",precision=17, scale=2)
//	private BigDecimal moneyinveamt;

//	@Column(name="MONEYINVEREALAMT",precision=17, scale=2)
//	private BigDecimal moneyinverealamt;

//	@Column(name="OTHERINVEAMT",precision=17, scale=2)
//	private BigDecimal otherinveamt;
//
//	@Column(name="OTHERINVEREALAMT",precision=17, scale=2)
//	private BigDecimal otherinverealamt;

//	@Column(name="PROPINVEAMT",precision=17, scale=2)
//	private BigDecimal propinveamt;

//	@Column(name="PROPINVEREALAMT",precision=17, scale=2)
//	private BigDecimal propinverealamt;

//	@Column(name="SPONKIND",length=20)
//	private String sponkind;

//	@Column(name="SPONSORAMOUNT",precision=17, scale=2)
//	private BigDecimal sponsoramount;

//	@Column(name="STOCKPERC",precision=5, scale=2)
//	private BigDecimal stockperc;

    public Holderinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHolderInfoId() {
		return this.holderInfoId;
	}

	public void setHolderInfoId(Long holderInfoId) {
		this.holderInfoId = holderInfoId;
	}

//	public String getCurrsign() {
//		return this.currsign;
//	}
//
//	public void setCurrsign(String currsign) {
//		this.currsign = currsign;
//	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

//	public BigDecimal getFundpct() {
//		return this.fundpct;
//	}
//
//	public void setFundpct(BigDecimal fundpct) {
//		this.fundpct = fundpct;
//	}

    @JsonSerialize(using=BioneLongSerializer.class)
//	public Long getHoldcustid() {
//		return this.holdcustid;
//	}
//
//	public void setHoldcustid(Long holdcustid) {
//		this.holdcustid = holdcustid;
//	}

	public String getHoldcustname() {
		return this.holdcustname;
	}

	public void setHoldcustname(String holdcustname) {
		this.holdcustname = holdcustname;
	}

	public String getHoldcusttype() {
		return this.holdcusttype;
	}

	public void setHoldcusttype(String holdcusttype) {
		this.holdcusttype = holdcusttype;
	}

//	public String getHoldrelenote() {
//		return this.holdrelenote;
//	}
//
//	public void setHoldrelenote(String holdrelenote) {
//		this.holdrelenote = holdrelenote;
//	}

	public String getIndustryposition() {
		return this.industryposition;
	}

	public void setIndustryposition(String industryposition) {
		this.industryposition = industryposition;
	}

//	public String getInvedate() {
//		return this.invedate;
//	}
//
//	public void setInvedate(String invedate) {
//		this.invedate = invedate;
//	}
//
//	public String getIscheckflag() {
//		return this.ischeckflag;
//	}
//
//	public void setIscheckflag(String ischeckflag) {
//		this.ischeckflag = ischeckflag;
//	}
//
//	public String getIsoffenceflag() {
//		return this.isoffenceflag;
//	}
//
//	public void setIsoffenceflag(String isoffenceflag) {
//		this.isoffenceflag = isoffenceflag;
//	}
//
//	public BigDecimal getKnowinveamt() {
//		return this.knowinveamt;
//	}

//	public void setKnowinveamt(BigDecimal knowinveamt) {
//		this.knowinveamt = knowinveamt;
//	}
//
//	public BigDecimal getKnowinverealamt() {
//		return this.knowinverealamt;
//	}
//
//	public void setKnowinverealamt(BigDecimal knowinverealamt) {
//		this.knowinverealamt = knowinverealamt;
//	}
//
//	public BigDecimal getLandinveamt() {
//		return this.landinveamt;
//	}
//
//	public void setLandinveamt(BigDecimal landinveamt) {
//		this.landinveamt = landinveamt;
//	}
//
//	public BigDecimal getLandinverealamt() {
//		return this.landinverealamt;
//	}
//
//	public void setLandinverealamt(BigDecimal landinverealamt) {
//		this.landinverealamt = landinverealamt;
//	}

//	public BigDecimal getMoneyinveamt() {
//		return this.moneyinveamt;
//	}
//
//	public void setMoneyinveamt(BigDecimal moneyinveamt) {
//		this.moneyinveamt = moneyinveamt;
//	}
//
//	public BigDecimal getMoneyinverealamt() {
//		return this.moneyinverealamt;
//	}
//
//	public void setMoneyinverealamt(BigDecimal moneyinverealamt) {
//		this.moneyinverealamt = moneyinverealamt;
//	}
//
//	public BigDecimal getOtherinveamt() {
//		return this.otherinveamt;
//	}
//
//	public void setOtherinveamt(BigDecimal otherinveamt) {
//		this.otherinveamt = otherinveamt;
//	}
//
//	public BigDecimal getOtherinverealamt() {
//		return this.otherinverealamt;
//	}
//
//	public void setOtherinverealamt(BigDecimal otherinverealamt) {
//		this.otherinverealamt = otherinverealamt;
//	}
//
//	public BigDecimal getPropinveamt() {
//		return this.propinveamt;
//	}
//
//	public void setPropinveamt(BigDecimal propinveamt) {
//		this.propinveamt = propinveamt;
//	}
//
//	public BigDecimal getPropinverealamt() {
//		return this.propinverealamt;
//	}
//
//	public void setPropinverealamt(BigDecimal propinverealamt) {
//		this.propinverealamt = propinverealamt;
//	}
//
//	public String getSponkind() {
//		return this.sponkind;
//	}
//
//	public void setSponkind(String sponkind) {
//		this.sponkind = sponkind;
//	}
//
//	public BigDecimal getSponsoramount() {
//		return this.sponsoramount;
//	}
//
//	public void setSponsoramount(BigDecimal sponsoramount) {
//		this.sponsoramount = sponsoramount;
//	}
//
//	public BigDecimal getStockperc() {
//		return this.stockperc;
//	}
//
//	public void setStockperc(BigDecimal stockperc) {
//		this.stockperc = stockperc;
//	}

}