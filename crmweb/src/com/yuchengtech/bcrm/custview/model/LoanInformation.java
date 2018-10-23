package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 贷款信用风险经济资本手工补录
 */
@Entity
@Table(name = "ocrm_f_ci_credit_risk")
public class LoanInformation implements Serializable {

	private static final long serialVersionUID = 2057403964492006610L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	/** 贷款账户 */
	@Column(length = 50)
	private String PAYACCOUNT;
	
	/**基本比例 */
	@Column(precision = 24, scale = 6)
	private BigDecimal BASERATIO;
	
	/**行业系数 */
	@Column(length = 30)
	private String BUSINESSCOEFF;
	
	/**客户评级系数 */
	@Column(length = 30)
	private String GRADECOEFF;
	
	/**产品权重 */
	@Column(length = 30)
	private String PRODRATIO;
	/**担保系数 */
	@Column(length = 30)
	private String GUARANTEECOEFF;
	/**期限系数 */
	@Column(length = 30)
	private String TIMELIMITCOEFF;
	/**调整系数*/
	@Column(name="ADJUSTCOEFF")
	private Long ADJUSTCOEFF;
	/**调整值 */
	@Column(precision = 24, scale = 6)
	private BigDecimal  AJUSTVALUE;
	
	/**融资平台流动资金覆盖系数*/
	@Column(length = 30)
	private String COVERCOEFF;
	
	@Column(length = 30)
	private String LON_TYPE;
	
	public String getLON_TYPE() {
		return LON_TYPE;
	}
	public void setLON_TYPE(String lON_TYPE) {
		LON_TYPE = lON_TYPE;
	}
	/** 申请人 */
	@Column(length = 50)
	private String APPLICANT;
	/** 申请时间 */
	@Temporal(TemporalType.DATE)
    private Date APPLY_TIME;// 发生日期
	/** 审批人 */
	@Column(length = 50)
	private String APPROVER;
	/** 审批时间 */
	@Temporal(TemporalType.DATE)
    private Date APPROVE_TIME;// 发生日期
	/**审批状态*/
	@Column(length = 1)
	private String APPROVE_STS;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPAYACCOUNT() {
		return PAYACCOUNT;
	}
	public void setPAYACCOUNT(String pAYACCOUNT) {
		PAYACCOUNT = pAYACCOUNT;
	}
	public BigDecimal getBASERATIO() {
		return BASERATIO;
	}
	public void setBASERATIO(BigDecimal bASERATIO) {
		BASERATIO = bASERATIO;
	}

	public String getBUSINESSCOEFF() {
		return BUSINESSCOEFF;
	}
	public void setBUSINESSCOEFF(String bUSINESSCOEFF) {
		BUSINESSCOEFF = bUSINESSCOEFF;
	}
	public String getGRADECOEFF() {
		return GRADECOEFF;
	}
	public void setGRADECOEFF(String gRADECOEFF) {
		GRADECOEFF = gRADECOEFF;
	}
	public String getPRODRATIO() {
		return PRODRATIO;
	}
	public void setPRODRATIO(String pRODRATIO) {
		PRODRATIO = pRODRATIO;
	}
	public String getGUARANTEECOEFF() {
		return GUARANTEECOEFF;
	}
	public void setGUARANTEECOEFF(String gUARANTEECOEFF) {
		GUARANTEECOEFF = gUARANTEECOEFF;
	}
	public String getTIMELIMITCOEFF() {
		return TIMELIMITCOEFF;
	}
	public void setTIMELIMITCOEFF(String tIMELIMITCOEFF) {
		TIMELIMITCOEFF = tIMELIMITCOEFF;
	}
	public String getCOVERCOEFF() {
		return COVERCOEFF;
	}
	public void setCOVERCOEFF(String cOVERCOEFF) {
		COVERCOEFF = cOVERCOEFF;
	}


	public Long getADJUSTCOEFF() {
		return ADJUSTCOEFF;
	}
	public void setADJUSTCOEFF(Long aDJUSTCOEFF) {
		ADJUSTCOEFF = aDJUSTCOEFF;
	}

	public BigDecimal getAJUSTVALUE() {
		return AJUSTVALUE;
	}
	public void setAJUSTVALUE(BigDecimal aJUSTVALUE) {
		AJUSTVALUE = aJUSTVALUE;
	}
	public String getAPPLICANT() {
		return APPLICANT;
	}
	public void setAPPLICANT(String aPPLICANT) {
		APPLICANT = aPPLICANT;
	}
	public Date getAPPLY_TIME() {
		return APPLY_TIME;
	}
	public void setAPPLY_TIME(Date aPPLY_TIME) {
		APPLY_TIME = aPPLY_TIME;
	}
	public String getAPPROVER() {
		return APPROVER;
	}
	public void setAPPROVER(String aPPROVER) {
		APPROVER = aPPROVER;
	}
	public Date getAPPROVE_TIME() {
		return APPROVE_TIME;
	}
	public void setAPPROVE_TIME(Date aPPROVE_TIME) {
		APPROVE_TIME = aPPROVE_TIME;
	}
	public String getAPPROVE_STS() {
		return APPROVE_STS;
	}
	public void setAPPROVE_STS(String aPPROVE_STS) {
		APPROVE_STS = aPPROVE_STS;
	}
}