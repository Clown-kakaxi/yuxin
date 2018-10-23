package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * The persistent class for the OCRM_F_CALL_NEW_RECORD database table.
 * 新户电访明细
 * 
 */
@Entity
@Table(name="OCRM_F_CALL_NEW_RECORD")
public class OcrmFCallNewRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键Id
	 */
	@Id
	@SequenceGenerator(name="OCRM_F_CALL_NEW_RECORD_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CALL_NEW_RECORD_ID_GENERATOR")
	private String id;
	
	/**
	 * 客户编号
	 */
	@Column(name="CUST_ID")
	private String custId;
	
	/**
	 * 公司名称
	 */
	@Column(name="CUST_NAME")
	private String custName;
	
	/**
	 * 电访客户经理名称
	 */
	@Column(name="MGR_NAME")
	private String mgrName;
	
	/**
	 * 所属行业
	 */
	@Column(name="CUS_OWNBUSI")
	private BigDecimal cusOwnbusi;
	
	/**
	 * 是否为目标行业
	 */
	@Column(name="IF_TARGETBUSI")
	private BigDecimal ifTargetbusi;
	
	/**
	 * 联系人
	 */
	@Column(name="LINKMAN")
	private String linkman;
	
	/**
	 * 联系号码
	 */
	@Column(name="LINK_TEL")
	private String linkTel;
	
	/**
	 * 电访接洽人
	 */
	@Column(name="TEL_CONTACTER")
	private BigDecimal telContacter;
	
	/**
	 * 电访接洽人备注
	 */
	@Column(name="TEL_CONTACTER_REMARK")
	private String telContacterRemark;
	
	/**
	 * 公司营收
	 */
	@Column(name="CUST_REVENUE")
	private BigDecimal custRevenue;
	
	/**
	 * 公司营收备注
	 */
	@Column(name="CUST_REVENUE_REMARK")
	private String custRevenueRemark;
	
	/**
	 * 他行往来情况
	 */
	@Column(name="OTHERBANK_TRADE")
	private String otherbankTrade;
	
	/**
	 * 拟营销产品
	 */
	@Column(name="MARKT_PRODUCT")
	private String marktProduct;
	
	/**
	 * 拟营销产品备注
	 */
	@Column(name="MARKT_PRODUCT_REMARK")
	private String marktProductRemark;
	
	/**
	 * 其他
	 */
	@Column(name="OTHER")
	private String other;
	
	/**
	 * 客户来源
	 */
	@Column(name="CUST_SOURCE")
	private BigDecimal custSource;
	
	/**
	 * 客户来源备注
	 */
	@Column(name="CUST_SOURCE_REMARK")
	private String custSourceRemark;
	
	/**
	 * 电访结果
	 */
	@Column(name="CALL_RESULT")
	private BigDecimal callResult;
	
	/**
	 * 电访审批状态
	 */
	@Column(name="REVIEW_STATE")
	private BigDecimal reviewState;
	
	/**
	 * 电访日期
	 */
	@Temporal( TemporalType.DATE)
	@Column(name="CALL_DATE")
	private Date callDate;
	
	/**
	 * 客户经理编号
	 */
	@Column(name="MGR_ID")
	private String mgrId;
	
	/**
	 * 拜访日期
	 */
	@Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;
	
	/**
	 * 回拨日期
	 */
	@Temporal( TemporalType.DATE)
	@Column(name="RECAL_DATE")
	private Date recalDate;
	
	/**
	 * 拒绝理由
	 */
	@Column(name="REFUSE_RESON")
	private BigDecimal refuseReson;
	
	public Date getVisitDate() {
		return visitDate;
	}

	public Date getRecalDate() {
		return recalDate;
	}

	public void setRecalDate(Date recalDate) {
		this.recalDate = recalDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public BigDecimal getCusOwnbusi() {
		return cusOwnbusi;
	}

	public void setCusOwnbusi(BigDecimal cusOwnbusi) {
		this.cusOwnbusi = cusOwnbusi;
	}

	public BigDecimal getIfTargetbusi() {
		return ifTargetbusi;
	}

	public void setIfTargetbusi(BigDecimal ifTargetbusi) {
		this.ifTargetbusi = ifTargetbusi;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public BigDecimal getTelContacter() {
		return telContacter;
	}

	public void setTelContacter(BigDecimal telContacter) {
		this.telContacter = telContacter;
	}

	public String getTelContacterRemark() {
		return telContacterRemark;
	}

	public void setTelContacterRemark(String telContacterRemark) {
		this.telContacterRemark = telContacterRemark;
	}

	public BigDecimal getCustRevenue() {
		return custRevenue;
	}

	public void setCustRevenue(BigDecimal custRevenue) {
		this.custRevenue = custRevenue;
	}

	public String getCustRevenueRemark() {
		return custRevenueRemark;
	}

	public void setCustRevenueRemark(String custRevenueRemark) {
		this.custRevenueRemark = custRevenueRemark;
	}

	public String getOtherbankTrade() {
		return otherbankTrade;
	}

	public void setOtherbankTrade(String otherbankTrade) {
		this.otherbankTrade = otherbankTrade;
	}

	public String getMarktProduct() {
		return marktProduct;
	}

	public void setMarktProduct(String marktProduct) {
		this.marktProduct = marktProduct;
	}

	public String getMarktProductRemark() {
		return marktProductRemark;
	}

	public void setMarktProductRemark(String marktProductRemark) {
		this.marktProductRemark = marktProductRemark;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public BigDecimal getCallResult() {
		return callResult;
	}

	public void setCallResult(BigDecimal callResult) {
		this.callResult = callResult;
	}

	public BigDecimal getReviewState() {
		return reviewState;
	}

	public void setReviewState(BigDecimal reviewState) {
		this.reviewState = reviewState;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public BigDecimal getCustSource() {
		return custSource;
	}

	public void setCustSource(BigDecimal custSource) {
		this.custSource = custSource;
	}

	public String getCustSourceRemark() {
		return custSourceRemark;
	}

	public void setCustSourceRemark(String custSourceRemark) {
		this.custSourceRemark = custSourceRemark;
	}

	public BigDecimal getRefuseReson() {
		return refuseReson;
	}

	public void setRefuseReson(BigDecimal refuseReson) {
		this.refuseReson = refuseReson;
	}
}
