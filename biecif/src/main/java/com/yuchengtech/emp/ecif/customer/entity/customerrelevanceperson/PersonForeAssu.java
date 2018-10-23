package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson;

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
 * Title:PersonForeAssu的实体类
 * Description: 个人对外担保信息
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
@Table(name="M_CI_PER_PERSONFOREASSU")
public class PersonForeAssu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="PERSON_FORE_ASSU_ID", unique=true, nullable=false)
	private Long personForeAssuId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="ASSUCUSTID")
	private Long assucustId;
	
	@Column(name="ASSUCUSTNO",length = 20)
	private String assucustNo;
	
	@Column(name="ASSUCUSTNAME",length = 60)
	private String assucustName;
	
	@Column(name="PAPERKIND",length = 20)
	private String paperkind;
	
	@Column(name="ASSUPAPERID",length = 40)
	private String assupaperId;
	
	@Column(name="ASSUCUSTTYPE",length = 20)
	private String assucustType;
	
	@Column(name="BANKNAME",length = 40)
	private String bankName;
	
	@Column(name="EACHASSUFLAG",length = 20)
	private String eachassuFlag;
	
	@Column(name="RELASIGN",length = 20)
	private String relasign;
	
	@Column(name="ASSUPROD",length = 80)
	private String assuprod;
	
	@Column(name="ASSUAMT")
	private Double assuamt;
	
	@Column(name="ASSUBAL")
	private Double assubal;
	
	@Column(name="MRETUAMT")
	private Double mretuamt;
	
	@Column(name="BEGINDATE",length = 10)
	private String beginDate;
	
	@Column(name="ENDDATE",length = 10)
	private String endDate;
	
	@Column(name="ISBADFLAG",length = 1)
	private String isbadFlag;
	
	@Column(name="MOSTASSUFLAG",length = 1)
	private String mostassuFlag;
	
	@Column(name="ASSUKINDNAME",length = 80)
	private String assukindName;
	
	@Column(name="TOTALASSUAMT")
	private Double totalAssuamt;
	
	@Column(name="IS_EXTERNAL_GUARANTY",length = 1)
	private String isExternalGuaranty;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonForeAssuId() {
		return personForeAssuId;
	}

	public void setPersonForeAssuId(Long personForeAssuId) {
		this.personForeAssuId = personForeAssuId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getAssucustId() {
		return assucustId;
	}

	public void setAssucustId(Long assucustId) {
		this.assucustId = assucustId;
	}

	public String getAssucustNo() {
		return assucustNo;
	}

	public void setAssucustNo(String assucustNo) {
		this.assucustNo = assucustNo;
	}

	public String getAssucustName() {
		return assucustName;
	}

	public void setAssucustName(String assucustName) {
		this.assucustName = assucustName;
	}

	public String getPaperkind() {
		return paperkind;
	}

	public void setPaperkind(String paperkind) {
		this.paperkind = paperkind;
	}

	public String getAssupaperId() {
		return assupaperId;
	}

	public void setAssupaperId(String assupaperId) {
		this.assupaperId = assupaperId;
	}

	public String getAssucustType() {
		return assucustType;
	}

	public void setAssucustType(String assucustType) {
		this.assucustType = assucustType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getEachassuFlag() {
		return eachassuFlag;
	}

	public void setEachassuFlag(String eachassuFlag) {
		this.eachassuFlag = eachassuFlag;
	}

	public String getRelasign() {
		return relasign;
	}

	public void setRelasign(String relasign) {
		this.relasign = relasign;
	}

	public String getAssuprod() {
		return assuprod;
	}

	public void setAssuprod(String assuprod) {
		this.assuprod = assuprod;
	}

	public Double getAssuamt() {
		return assuamt;
	}

	public void setAssuamt(Double assuamt) {
		this.assuamt = assuamt;
	}

	public Double getAssubal() {
		return assubal;
	}

	public void setAssubal(Double assubal) {
		this.assubal = assubal;
	}

	public Double getMretuamt() {
		return mretuamt;
	}

	public void setMretuamt(Double mretuamt) {
		this.mretuamt = mretuamt;
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

	public String getIsbadFlag() {
		return isbadFlag;
	}

	public void setIsbadFlag(String isbadFlag) {
		this.isbadFlag = isbadFlag;
	}

	public String getMostassuFlag() {
		return mostassuFlag;
	}

	public void setMostassuFlag(String mostassuFlag) {
		this.mostassuFlag = mostassuFlag;
	}

	public String getAssukindName() {
		return assukindName;
	}

	public void setAssukindName(String assukindName) {
		this.assukindName = assukindName;
	}

	public Double getTotalAssuamt() {
		return totalAssuamt;
	}

	public void setTotalAssuamt(Double totalAssuamt) {
		this.totalAssuamt = totalAssuamt;
	}

	public String getIsExternalGuaranty() {
		return isExternalGuaranty;
	}

	public void setIsExternalGuaranty(String isExternalGuaranty) {
		this.isExternalGuaranty = isExternalGuaranty;
	}
}
	