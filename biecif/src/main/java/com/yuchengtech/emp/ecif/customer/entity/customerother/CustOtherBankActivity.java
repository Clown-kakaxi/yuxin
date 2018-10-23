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
 * Title:CustOtherBankActivity的实体类
 * Description: 客户外行业务活动情况
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
@Table(name = "CUSTOTHERBANKACTIVITY")
public class CustOtherBankActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUST_OTHER_BANK_ACT_ID", unique = true, nullable = false)
	private Long custOtherBankActId;
	
	@Column(name = "CUST_ID", length = 20)
	private Long custId;
	
	@Column(name = "INFO", length = 200)
	private String info;
	
	@Column(name = "OTHER_BAD_RECORD", length = 200)
	private String otherBadRecord;
	
	@Column(name = "OCCUR_ORG", length = 80)
	private String occurOrg;
	
	@Column(name = "BUSINESS_TYPE", length = 20)
	private String businessType;
	
	@Column(name = "CURRENCY", length = 20)
	private String currency;
	
	@Column(name = "BUSINESS_SUM")
	private Double businessSum;
	
	@Column(name = "UPTODATE",length = 10)
	private String upToDate;
	
	@Column(name = "BAL")
	private Double bal;
	
	@Column(name = "CLASSIFY_5_RESULT",length = 20)
	private String classifyResult;
	
	@Column(name = "BEGIN_DATE",length = 10)
	private String beginDate;
	
	@Column(name = "MATURITY",length = 10)
	private String maturity;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustOtherBankActId() {
		return custOtherBankActId;
	}

	public void setCustOtherBankActId(Long custOtherBankActId) {
		this.custOtherBankActId = custOtherBankActId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getOtherBadRecord() {
		return otherBadRecord;
	}

	public void setOtherBadRecord(String otherBadRecord) {
		this.otherBadRecord = otherBadRecord;
	}

	public String getOccurOrg() {
		return occurOrg;
	}

	public void setOccurOrg(String occurOrg) {
		this.occurOrg = occurOrg;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBusinessSum() {
		return businessSum;
	}

	public void setBusinessSum(Double businessSum) {
		this.businessSum = businessSum;
	}

	public String getUpToDate() {
		return upToDate;
	}

	public void setUpToDate(String upToDate) {
		this.upToDate = upToDate;
	}

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public String getClassifyResult() {
		return classifyResult;
	}

	public void setClassifyResult(String classifyResult) {
		this.classifyResult = classifyResult;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getMaturity() {
		return maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}

} 