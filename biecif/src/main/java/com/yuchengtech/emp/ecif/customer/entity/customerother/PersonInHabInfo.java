package com.yuchengtech.emp.ecif.customer.entity.customerother;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * 
 * <pre>
 * Title:PersonInHabInfo的实体类
 * Description: 个人客户居住信息
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name = "PERSONINHABINFO")
public class PersonInHabInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INHAB_ID", unique = true, nullable = false)
	private Long inhabId;
	
	@Column(name = "CUST_ID")
	private Long custId;
	
	@Column(name = "INHABADDR",length = 100)
	private String inhabAddr;
	
	@Column(name = "INHABPOST",length = 6)
	private String inhabPost;
	
	@Column(name = "INHABSTATSIGN",length = 2)
	private String inhabStatsign;
	
	@Column(name = "INHABMONTH",length = 6)
	private String inhabMonth;
	
	@Column(name = "INHABTIME",length = 6)
	private String inhabTime;
	
	@Column(name = "CURRINHABFLAG",length = 1)
	private String currInhabFlag;
	
	@Column(name = "LOANBANK",length = 40)
	private String loanBank;
	
	@Column(name = "LOANCUSTID",length = 20)
	private String loanCustId;
	
	@Column(name = "LOANCUSTNAME",length = 60)
	private String loanCustName;
	
	@Column(name = "OWNCUSTID",length = 20)
	private String ownCustId;
	
	@Column(name = "OWNCUSTNAME",length = 60)
	private String ownCustName;
	
	@Column(name = "INHABAREA",length = 10)
	private String inhabArea;
	
	@Column(name = "LOANCAPI")
	private Double loanCapi;
	
	@Column(name = "BEGINDATE",length = 10)
	private String beginDate;
	
	@Column(name = "ENDDATE",length = 10)
	private String endDate;
	
	@Column(name = "RETUFREQSIGN",length = 2)
	private String retufreqsign;
	
	@Column(name = "MAMT")
	private Double mamt;
	
	@Column(name = "LOANBAL")
	private Double loanBal;
	
	@Column(name = "HIGHOVERNUM")
	private Double highOvernum;
	
	@Column(name = "OVERFLAG",length = 1)
	private String overFlag;
	
	@Column(name = "OWNERPAPERNO",length = 40)
	private String ownerPaperNo;
	
	@Column(name = "LOCATION",length = 200)
	private String loacation;
	
	@Column(name = "FINISHTIME",length = 4)
	private String finishTime;
	
	@Column(name = "TAXPRICE")
	private Double taxprice;
	
	@Column(name = "MORTFLAG",length = 1)
	private String mortFlag;
	
	@Column(name = "HOUSEATTR",length = 2)
	private String houseAttr;
	
	@Column(name = "TERM")
	private Double term;
	
	@Column(name = "MONTH_RENT")
	private Double monthRent;
	
	@Column(name = "ANNUAL_RENT")
	private Double annualRent;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getInhabId() {
		return inhabId;
	}

	public void setInhabId(Long inhabId) {
		this.inhabId = inhabId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getInhabAddr() {
		return inhabAddr;
	}

	public void setInhabAddr(String inhabAddr) {
		this.inhabAddr = inhabAddr;
	}

	public String getInhabPost() {
		return inhabPost;
	}

	public void setInhabPost(String inhabPost) {
		this.inhabPost = inhabPost;
	}

	public String getInhabStatsign() {
		return inhabStatsign;
	}

	public void setInhabStatsign(String inhabStatsign) {
		this.inhabStatsign = inhabStatsign;
	}

	public String getInhabMonth() {
		return inhabMonth;
	}

	public void setInhabMonth(String inhabMonth) {
		this.inhabMonth = inhabMonth;
	}

	public String getInhabTime() {
		return inhabTime;
	}

	public void setInhabTime(String inhabTime) {
		this.inhabTime = inhabTime;
	}

	public String getCurrInhabFlag() {
		return currInhabFlag;
	}

	public void setCurrInhabFlag(String currInhabFlag) {
		this.currInhabFlag = currInhabFlag;
	}

	public String getLoanBank() {
		return loanBank;
	}

	public void setLoanBank(String loanBank) {
		this.loanBank = loanBank;
	}

	public String getLoanCustId() {
		return loanCustId;
	}

	public void setLoanCustId(String loanCustId) {
		this.loanCustId = loanCustId;
	}

	public String getLoanCustName() {
		return loanCustName;
	}

	public void setLoanCustName(String loanCustName) {
		this.loanCustName = loanCustName;
	}

	public String getOwnCustId() {
		return ownCustId;
	}

	public void setOwnCustId(String ownCustId) {
		this.ownCustId = ownCustId;
	}

	public String getOwnCustName() {
		return ownCustName;
	}

	public void setOwnCustName(String ownCustName) {
		this.ownCustName = ownCustName;
	}

	public String getInhabArea() {
		return inhabArea;
	}

	public void setInhabArea(String inhabArea) {
		this.inhabArea = inhabArea;
	}

	public Double getLoanCapi() {
		return loanCapi;
	}

	public void setLoanCapi(Double loanCapi) {
		this.loanCapi = loanCapi;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRetufreqsign() {
		return retufreqsign;
	}

	public void setRetufreqsign(String retufreqsign) {
		this.retufreqsign = retufreqsign;
	}

	public Double getMamt() {
		return mamt;
	}

	public void setMamt(Double mamt) {
		this.mamt = mamt;
	}

	public Double getLoanBal() {
		return loanBal;
	}

	public void setLoanBal(Double loanBal) {
		this.loanBal = loanBal;
	}

	public Double getHighOvernum() {
		return highOvernum;
	}

	public void setHighOvernum(Double highOvernum) {
		this.highOvernum = highOvernum;
	}

	public String getOverFlag() {
		return overFlag;
	}

	public void setOverFlag(String overFlag) {
		this.overFlag = overFlag;
	}

	public String getOwnerPaperNo() {
		return ownerPaperNo;
	}

	public void setOwnerPaperNo(String ownerPaperNo) {
		this.ownerPaperNo = ownerPaperNo;
	}

	public String getLoacation() {
		return loacation;
	}

	public void setLoacation(String loacation) {
		this.loacation = loacation;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Double getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(Double taxprice) {
		this.taxprice = taxprice;
	}

	public String getMortFlag() {
		return mortFlag;
	}

	public void setMortFlag(String mortFlag) {
		this.mortFlag = mortFlag;
	}

	public String getHouseAttr() {
		return houseAttr;
	}

	public void setHouseAttr(String houseAttr) {
		this.houseAttr = houseAttr;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getMonthRent() {
		return monthRent;
	}

	public void setMonthRent(Double monthRent) {
		this.monthRent = monthRent;
	}

	public Double getAnnualRent() {
		return annualRent;
	}

	public void setAnnualRent(Double annualRent) {
		this.annualRent = annualRent;
	}

}