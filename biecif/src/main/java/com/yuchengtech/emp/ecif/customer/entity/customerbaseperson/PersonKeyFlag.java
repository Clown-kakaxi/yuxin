package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:PersonKeyFlag的实体类
 * Description: 个人客户重要标志
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
@Table(name="M_CI_PER_KEYFLAG")
public class PersonKeyFlag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="IS_MERCHANT", length=1)
	private String isMerchant;
	
	@Column(name="IS_PEASANT", length=1)
	private String isPeasant;
	
	@Column(name="IS_CREDIBLE_PEASANT", length=1)
	private String isCrediblePeasant;
	
	@Column(name="PEASANT_TYPE", length=18)
	private String peasantType;
	
	@Column(name="IS_SHAREHOLDER", length=1)
	private String isShareholder;
	
	@Column(name="IS_EMPLOYEE", length=1)
	private String isEmployee;
	
	@Column(name="HAS_THIS_BANK_LOAN", length=1)
	private String hasThisBankLoan;
	
	@Column(name="HAS_OTHER_BANK_LOAN", length=1)
	private String hasOtherBankLoan;
	
	@Column(name="HAS_BAD_LOAN", length=1)
	private String hasBadLoan;
	
	@Column(name="IS_GUARANTEE", length=1)
	private String isGuarantee;
	
//	@Column(name="IS_BLACKLIST_CUST", length=1)
//	private String isBlacklistCust;
	
	@Column(name="IS_NATIVE", length=1)
	private String isNative;
	
	@Column(name="LOCAL_RESIDE_TIME")
	private Integer localResideTime;
	
	@Column(name="IS_ON_JOB_WORKER", length=1)
	private String isOnJobWorker;
	
	@Column(name="HAS_SOCIAL_SECURITY", length=1)
	private String hasSocialSecurity;
	
	@Column(name="HAS_COMMERCIAL_INSURANCE", length=1)
	private String hasCommercialInsurance;
	
	@Column(name="HAS_CREDIT_INFO", length=1)
	private String hasCreditInfo;
	
	@Column(name="IS_IMPORTANT_CUST", length=1)
	private String isImportantCust;
	
//	@Column(name="IMPORTANT_CUST_TYPE", length=20)
//	private String importantCustType;
	
	@Column(name="IS_CREDIT_CUST", length=1)
	private String isCreditCust;
	
	@Column(name="IS_SECRET_CUST", length=1)
	private String isSecretCust;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getIsPeasant() {
		return isPeasant;
	}

	public void setIsPeasant(String isPeasant) {
		this.isPeasant = isPeasant;
	}

	public String getIsCrediblePeasant() {
		return isCrediblePeasant;
	}

	public void setIsCrediblePeasant(String isCrediblePeasant) {
		this.isCrediblePeasant = isCrediblePeasant;
	}

	public String getPeasantType() {
		return peasantType;
	}

	public void setPeasantType(String peasantType) {
		this.peasantType = peasantType;
	}

	public String getIsShareholder() {
		return isShareholder;
	}

	public void setIsShareholder(String isShareholder) {
		this.isShareholder = isShareholder;
	}

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getHasThisBankLoan() {
		return hasThisBankLoan;
	}

	public void setHasThisBankLoan(String hasThisBankLoan) {
		this.hasThisBankLoan = hasThisBankLoan;
	}

	public String getHasOtherBankLoan() {
		return hasOtherBankLoan;
	}

	public void setHasOtherBankLoan(String hasOtherBankLoan) {
		this.hasOtherBankLoan = hasOtherBankLoan;
	}

	public String getHasBadLoan() {
		return hasBadLoan;
	}

	public void setHasBadLoan(String hasBadLoan) {
		this.hasBadLoan = hasBadLoan;
	}

	public String getIsGuarantee() {
		return isGuarantee;
	}

	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

//	public String getIsBlacklistCust() {
//		return isBlacklistCust;
//	}
//
//	public void setIsBlacklistCust(String isBlacklistCust) {
//		this.isBlacklistCust = isBlacklistCust;
//	}

	public String getIsNative() {
		return isNative;
	}

	public void setIsNative(String isNative) {
		this.isNative = isNative;
	}

	public Integer getLocalResideTime() {
		return localResideTime;
	}

	public void setLocalResideTime(Integer localResideTime) {
		this.localResideTime = localResideTime;
	}

	public String getIsOnJobWorker() {
		return isOnJobWorker;
	}

	public void setIsOnJobWorker(String isOnJobWorker) {
		this.isOnJobWorker = isOnJobWorker;
	}

	public String getHasSocialSecurity() {
		return hasSocialSecurity;
	}

	public void setHasSocialSecurity(String hasSocialSecurity) {
		this.hasSocialSecurity = hasSocialSecurity;
	}

	public String getHasCommercialInsurance() {
		return hasCommercialInsurance;
	}

	public void setHasCommercialInsurance(String hasCommercialInsurance) {
		this.hasCommercialInsurance = hasCommercialInsurance;
	}

	public String getHasCreditInfo() {
		return hasCreditInfo;
	}

	public void setHasCreditInfo(String hasCreditInfo) {
		this.hasCreditInfo = hasCreditInfo;
	}

	public String getIsImportantCust() {
		return isImportantCust;
	}

	public void setIsImportantCust(String isImportantCust) {
		this.isImportantCust = isImportantCust;
	}

//	public String getImportantCustType() {
//		return importantCustType;
//	}
//
//	public void setImportantCustType(String importantCustType) {
//		this.importantCustType = importantCustType;
//	}

	public String getIsCreditCust() {
		return isCreditCust;
	}

	public void setIsCreditCust(String isCreditCust) {
		this.isCreditCust = isCreditCust;
	}

	public String getIsSecretCust() {
		return isSecretCust;
	}

	public void setIsSecretCust(String isSecretCust) {
		this.isSecretCust = isSecretCust;
	}

}
