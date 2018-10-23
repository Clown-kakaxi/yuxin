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
 * The persistent class for the OCRM_F_CALL_OLD_RECORD database table.
 * 旧户电访明细
 * 
 */

@Entity
@Table(name="OCRM_F_CALL_OLD_RECORD")
public class OcrmFCallOldRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键Id
	 */
	@Id
	@SequenceGenerator(name="OCRM_F_CALL_OLD_RECORD_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CALL_OLD_RECORD_ID_GENERATOR")
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
	 * 客户经理编号
	 */
	@Column(name="MGR_ID")
	private String mgrId;
	
	/**
	 * 客户经理名称
	 */
	@Column(name="MGR_NAME")
	private String mgrName;
	
	/**
	 * 受访人职位
	 */
	@Column(name="RESPONDENTS_POSITION")
	private String respondentsPosition;
	
	/**
	 * 受访人名称
	 */
	@Column(name="RESPONDENTS_NAME")
	private String respondentsName;
	
	/**
	 * 受访人联系方式
	 */
	@Column(name="RESPONDENTS_CONTACT")
	private String respondentsContact;
	
	
	/**
	 * 本行参加人员
	 */
	@Column(name="BANK_PARTICIPANTS")
	private String bankParticipants;
	
	/**
	 * 电访目的补充说明
	 */
	@Column(name="CALL_REASON")
	private String callReason;
	
	/**
	 * 客户营运状况
	 */
	@Column(name="CUST_BUSI_CONDITION")
	private BigDecimal custBusiCondition;
	
	/**
	 * 主营业务是否变更
	 */
	@Column(name="MAIN_BUSI_CHANGE")
	private BigDecimal mainBusiChange;
	
	/**
	 * 主营业务是否变更说明
	 */
	@Column(name="MAIN_BUSI_CHANGE_REMARK")
	private String mainBusiChangeRemark;
	
	/**
	 * 营收是否大幅变化
	 */
	@Column(name="REVENUE_CHANGE")
	private BigDecimal revenueChange;
	
	/**
	 * 营收是否大幅变化说明
	 */
	@Column(name="REVENUE_CHANGE_REMARK")
	private String revenueChangeRemark;
	
	/**
	 * 获利率是否大幅变化
	 */
	@Column(name="PROFI_CHANGE")
	private BigDecimal profiChange;
	
	/**
	 * 获利率是否大幅变化说明
	 */
	@Column(name="PROFI_CHANGE_REMARK")
	private String profiChangeRemark;
	
	/**
	 * 主要供应商是否调整
	 */
	@Column(name="MAIN_SUPPLIER_CHANGE")
	private BigDecimal mainSupplierChange;
	
	/**
	 * 主要供应商是否调整说明
	 */
	@Column(name="MAIN_SUPPLIER_CHANGE_REMARK")
	private String mainSupplierChangeRemark;
	
	
	/**
	 * 主要买方是否调整
	 */
	@Column(name="MAIN_BUYER_CHANGE")
	private BigDecimal mainBuyerChange;
	
	/**
	 * 主要买方是否调整说明
	 */
	@Column(name="MAIN_BUYER_CHANGE_REMARK")
	private String mainBuyerChangeRemark;
	
	/**
	 * 股权结构是否变更
	 */
	@Column(name="EQUITY_STRUC_CHANGE")
	private BigDecimal equityStrucChange;
	
	/**
	 * 股权结构是否变更说明
	 */
	@Column(name="EQUITY_STRUC_CHANGE_REMARK")
	private String equityStrucChangeRemark;
	
	/**
	 * 经营层是否有变更
	 */
	@Column(name="MANAGEMENT_CHANGE")
	private BigDecimal managementChange;
	
	/**
	 * 经营层是否有变更说明
	 */
	@Column(name="MANAGEMENT_CHANGE_REMARK")
	private String managementChangeRemark;
	
	/**
	 * 担保品状况
	 */
	@Column(name="COLLATERAL_CONDITION")
	private BigDecimal collateralCondition;
	
	/**
	 * 担保品状况说明
	 */
	@Column(name="COLLATERAL_CONDITION_REMARK")
	private String collateralConditionRemark;
	
	/**
	 * 与银行合作状况是否有变化
	 */
	@Column(name="COOPERATION_CHANGE")
	private BigDecimal cooperationChange;
	
	/**
	 * 与银行合作状况是否有变化说明
	 */
	@Column(name="COOPERATION_CHANGE_REMARK")
	private String cooperationChangeRemark;
	
	/**
	 * 本次拜访拟营销产品
	 */
	@Column(name="MARKT_PRODUCT")
	private String marktProduct;
	
	/**
	 * 营销结果
	 */
	@Column(name="MARKT_RESULT")
	private String marktResult;
	
	/**
	 * 其他补充说明
	 */
	@Column(name="OTHER")
	private String other;
	
	/**
	 * 跟进事项
	 */
	@Column(name="MATTERS_FOLLOW")
	private String mattersFollow;
	
	/**
	 * 是否预约拜访
	 */
	@Column(name="IF_PRECONTRACT")
	private BigDecimal ifPrecontract;
	
	/**
	 * 拜访日期
	 */
	@Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;
	
	/**
	 * 旧户电访审批状态
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
	 * 电访目的
	 */
	@Column(name="CALL_PURPOSE")
	private String callPurpose;
	
	public Date getVisitDate() {
		return visitDate;
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

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public String getRespondentsPosition() {
		return respondentsPosition;
	}

	public void setRespondentsPosition(String respondentsPosition) {
		this.respondentsPosition = respondentsPosition;
	}

	public String getRespondentsName() {
		return respondentsName;
	}

	public void setRespondentsName(String respondentsName) {
		this.respondentsName = respondentsName;
	}

	public String getRespondentsContact() {
		return respondentsContact;
	}

	public void setRespondentsContact(String respondentsContact) {
		this.respondentsContact = respondentsContact;
	}

	public String getBankParticipants() {
		return bankParticipants;
	}

	public void setBankParticipants(String bankParticipants) {
		this.bankParticipants = bankParticipants;
	}

	public String getCallReason() {
		return callReason;
	}

	public void setCallReason(String callReason) {
		this.callReason = callReason;
	}

	public BigDecimal getCustBusiCondition() {
		return custBusiCondition;
	}

	public void setCustBusiCondition(BigDecimal custBusiCondition) {
		this.custBusiCondition = custBusiCondition;
	}

	public BigDecimal getMainBusiChange() {
		return mainBusiChange;
	}

	public void setMainBusiChange(BigDecimal mainBusiChange) {
		this.mainBusiChange = mainBusiChange;
	}

	public String getMainBusiChangeRemark() {
		return mainBusiChangeRemark;
	}

	public void setMainBusiChangeRemark(String mainBusiChangeRemark) {
		this.mainBusiChangeRemark = mainBusiChangeRemark;
	}

	public BigDecimal getRevenueChange() {
		return revenueChange;
	}

	public void setRevenueChange(BigDecimal revenueChange) {
		this.revenueChange = revenueChange;
	}

	public String getRevenueChangeRemark() {
		return revenueChangeRemark;
	}

	public void setRevenueChangeRemark(String revenueChangeRemark) {
		this.revenueChangeRemark = revenueChangeRemark;
	}

	public BigDecimal getProfiChange() {
		return profiChange;
	}

	public void setProfiChange(BigDecimal profiChange) {
		this.profiChange = profiChange;
	}

	public String getProfiChangeRemark() {
		return profiChangeRemark;
	}

	public void setProfiChangeRemark(String profiChangeRemark) {
		this.profiChangeRemark = profiChangeRemark;
	}

	public BigDecimal getMainSupplierChange() {
		return mainSupplierChange;
	}

	public void setMainSupplierChange(BigDecimal mainSupplierChange) {
		this.mainSupplierChange = mainSupplierChange;
	}

	public String getMainSupplierChangeRemark() {
		return mainSupplierChangeRemark;
	}

	public void setMainSupplierChangeRemark(String mainSupplierChangeRemark) {
		this.mainSupplierChangeRemark = mainSupplierChangeRemark;
	}

	public BigDecimal getMainBuyerChange() {
		return mainBuyerChange;
	}

	public void setMainBuyerChange(BigDecimal mainBuyerChange) {
		this.mainBuyerChange = mainBuyerChange;
	}

	public String getMainBuyerChangeRemark() {
		return mainBuyerChangeRemark;
	}

	public void setMainBuyerChangeRemark(String mainBuyerChangeRemark) {
		this.mainBuyerChangeRemark = mainBuyerChangeRemark;
	}

	public BigDecimal getEquityStrucChange() {
		return equityStrucChange;
	}

	public void setEquityStrucChange(BigDecimal equityStrucChange) {
		this.equityStrucChange = equityStrucChange;
	}

	public String getEquityStrucChangeRemark() {
		return equityStrucChangeRemark;
	}

	public void setEquityStrucChangeRemark(String equityStrucChangeRemark) {
		this.equityStrucChangeRemark = equityStrucChangeRemark;
	}

	public BigDecimal getManagementChange() {
		return managementChange;
	}

	public void setManagementChange(BigDecimal managementChange) {
		this.managementChange = managementChange;
	}

	public String getManagementChangeRemark() {
		return managementChangeRemark;
	}

	public void setManagementChangeRemark(String managementChangeRemark) {
		this.managementChangeRemark = managementChangeRemark;
	}

	public BigDecimal getCollateralCondition() {
		return collateralCondition;
	}

	public void setCollateralCondition(BigDecimal collateralCondition) {
		this.collateralCondition = collateralCondition;
	}

	public String getCollateralConditionRemark() {
		return collateralConditionRemark;
	}

	public void setCollateralConditionRemark(String collateralConditionRemark) {
		this.collateralConditionRemark = collateralConditionRemark;
	}

	public BigDecimal getCooperationChange() {
		return cooperationChange;
	}

	public void setCooperationChange(BigDecimal cooperationChange) {
		this.cooperationChange = cooperationChange;
	}

	public String getCooperationChangeRemark() {
		return cooperationChangeRemark;
	}

	public void setCooperationChangeRemark(String cooperationChangeRemark) {
		this.cooperationChangeRemark = cooperationChangeRemark;
	}

	public String getMarktProduct() {
		return marktProduct;
	}

	public void setMarktProduct(String marktProduct) {
		this.marktProduct = marktProduct;
	}

	public String getMarktResult() {
		return marktResult;
	}

	public void setMarktResult(String marktResult) {
		this.marktResult = marktResult;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getMattersFollow() {
		return mattersFollow;
	}

	public void setMattersFollow(String mattersFollow) {
		this.mattersFollow = mattersFollow;
	}

	public BigDecimal getIfPrecontract() {
		return ifPrecontract;
	}

	public void setIfPrecontract(BigDecimal ifPrecontract) {
		this.ifPrecontract = ifPrecontract;
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

	public String getCallPurpose() {
		return callPurpose;
	}

	public void setCallPurpose(String callPurpose) {
		this.callPurpose = callPurpose;
	}

	
}