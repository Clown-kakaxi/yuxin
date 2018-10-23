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
 * Title:RelativeInfo的实体类
 * Description: 亲属信息
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
@Table(name="M_CI_PER_RELATIVEINFO")
public class RelativeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="RELATIVE_INFO_ID", unique=true, nullable=false)
	private Long relativeInfoId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="RELATIVE_ID")
	private Long relativeId;
	
	@Column(name="RELATIVE_TYPE",length = 20)
	private String relativeType;
	
	@Column(name="RELATIVE_NAME",length = 80)
	private String relativeName;
	
	@Column(name="IDENT_TYPE",length = 20)
	private String identType;

	@Column(name="IDENT_NO",length = 40)
	private String identNo;
	
	@Column(name="GENDER",length = 20)
	private String gender;
	
	@Column(name="OFFICE_TEL",length = 32)
	private String tel;
	
	@Column(name="HOME_TEL",length = 32)
	private String homeTel;
	
	@Column(name="MOBILE",length = 20)
	private String mobile;
	
	@Column(name="MONTH_INCOME")
	private Long monthIncome;
	
	@Column(name="HEALTH",length = 80)
	private String healthStat;
//	
	@Column(name="ADDRESS",length = 255)
	private String homeAddr;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getRelativeInfoId() {
		return relativeInfoId;
	}

	public void setRelativeInfoId(Long relativeInfoId) {
		this.relativeInfoId = relativeInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}
    
	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}    

	public void setCustId(Long custId) {
		this.custId = custId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getRelativeId() {
		return relativeId;
	}

	public void setRelativeId(Long relativeId) {
		this.relativeId = relativeId;
	}

	public String getRelativeType() {
		return relativeType;
	}

	public void setRelativeType(String relativeType) {
		this.relativeType = relativeType;
	}

	public String getRelativeName() {
		return relativeName;
	}

	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}

	public String getIdentType() {
		return identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentNo() {
		return identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(Long monthIncome) {
		this.monthIncome = monthIncome;
	}

	public String getHealthStat() {
		return healthStat;
	}

	public void setHealthStat(String healthStat) {
		this.healthStat = healthStat;
	}

	public String getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

}