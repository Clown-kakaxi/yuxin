package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

@Entity
@Table(name = "M_CI_ORG_REGISTERINFO")
public class Registerinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="ADMIN_ZONE", length=20)
	private String adminZone;

	@Column(name="BUSINESS_MODE", length=20)
	private String businessMode;

	@Column(name="BUSINESS_SCOPE", length=50)
	private String businessScope;

	@Column(name="FACT_CAPITAL", precision=17, scale=2)
	private BigDecimal factCapital;

	@Column(name="FACT_CAPITAL_CURR", length=20)
	private String factCapitalCurr;

	@Column(name="FINANCE_CONTACT", length=35)
	private String financeContact;

	@Column(name="MAIN_BUSINESS", length=1000)
	private String mainBusiness;


	@Column(name="MATURITY_DATE", length=10)
	private String maturityDate;

	@Column(name="MINOR_BUSINESS", length=1000)
	private String minorBusiness;

	@Column(name="REGISTER_ADDR", length=255)
	private String registerAddr;

	@Column(name="REGISTER_CAPITAL", precision=17, scale=2)
	private BigDecimal registerCapital;

	@Column(name="REGISTER_CCURRENCY", length=20)
	private String registerCcurrency;

	@Column(name="REGISTER_COMPOSING", length=60)
	private String registerComposing;

	@Column(name="REGISTER_TYPE", length=20)
	private String registerType1;

	@Column(name="SETUP_DATE", length=10)
	private String setupDate;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getAdminZone() {
		return adminZone;
	}

	public void setAdminZone(String adminZone) {
		this.adminZone = adminZone;
	}

	public String getBusinessMode() {
		return businessMode;
	}

	public void setBusinessMode(String businessMode) {
		this.businessMode = businessMode;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public BigDecimal getFactCapital() {
		return factCapital;
	}

	public void setFactCapital(BigDecimal factCapital) {
		this.factCapital = factCapital;
	}

	public String getFactCapitalCurr() {
		return factCapitalCurr;
	}

	public void setFactCapitalCurr(String factCapitalCurr) {
		this.factCapitalCurr = factCapitalCurr;
	}

	public String getFinanceContact() {
		return financeContact;
	}

	public void setFinanceContact(String financeContact) {
		this.financeContact = financeContact;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getMinorBusiness() {
		return minorBusiness;
	}

	public void setMinorBusiness(String minorBusiness) {
		this.minorBusiness = minorBusiness;
	}

	public String getRegisterAddr() {
		return registerAddr;
	}

	public void setRegisterAddr(String registerAddr) {
		this.registerAddr = registerAddr;
	}

	public BigDecimal getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(BigDecimal registerCapital) {
		this.registerCapital = registerCapital;
	}

	public String getRegisterCcurrency() {
		return registerCcurrency;
	}

	public void setRegisterCcurrency(String registerCcurrency) {
		this.registerCcurrency = registerCcurrency;
	}

	public String getRegisterComposing() {
		return registerComposing;
	}

	public void setRegisterComposing(String registerComposing) {
		this.registerComposing = registerComposing;
	}

	public String getRegisterType1() {
		return registerType1;
	}

	public void setRegisterType1(String registerType1) {
		this.registerType1 = registerType1;
	}

	public String getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(String setupDate) {
		this.setupDate = setupDate;
	}
}