package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:JobResume的实体类
 * Description: 工作信息
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
@Table(name="M_CI_PER_JOBRESUME")
public class JobResume implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="JOBRESUME_ID", unique=true, nullable=false)
	private Long jobResumeId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="START_DATE", length=10)
	private String startDate;
	
	@Column(name="END_DATE", length=10)
	private String endDate;
	
//	@Column(name="UNIT_CODE", length=32)
//	private String unitCode;
//	
	@Column(name="UNIT_CHAR", length=20)
	private String unitChar;
	
//	@Column(name="WORK_UNIT", length=200)
//	private String workUnit;
	
	@Column(name="WORK_DEPT", length=80)
	private String workDept;
	
	@Column(name="POSITION", length=80)
	private String position;
	
	@Column(name="UNIT_TEL", length=32)
	private String unitTel;
	
	@Column(name="UNIT_ADDRESS", length=200)
	private String unitAddress;
	
	@Column(name="UNIT_ZIPCODE", length=32)
	private String unitZipcode;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getJobResumeId() {
		return jobResumeId;
	}

	public void setJobResumeId(Long jobResumeId) {
		this.jobResumeId = jobResumeId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getUnitChar() {
		return unitChar;
	}

	public void setUnitChar(String unitChar) {
		this.unitChar = unitChar;
	}



	public String getWorkDept() {
		return workDept;
	}

	public void setWorkDept(String workDept) {
		this.workDept = workDept;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUnitTel() {
		return unitTel;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getUnitZipcode() {
		return unitZipcode;
	}

	public void setUnitZipcode(String unitZipcode) {
		this.unitZipcode = unitZipcode;
	}
	
}
