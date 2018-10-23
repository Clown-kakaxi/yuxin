package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:EduResume的实体类
 * Description: 
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
@Table(name="M_CI_PER_EDURESUME")
public class EduResume implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="EDURESUME_ID", unique=true, nullable=false)
	private Long eduResumeId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="START_DATE",length=10)
	private String startDate;
	
	@Column(name="END_DATE",length=10)
	private String endDate;
	
	@Column(name="UNIVERSITY",length=10)
	private String university;
	
	@Column(name="COLLEGE",length=80)
	private String college;
	
	@Column(name="MAJOR",length=80)
	private String major;
	
	@Column(name="EDU_SYS",length=80)
	private String eduSys;
	
	@Column(name="CERTIFICATE_NO",length=32)
	private String certificateNo;
	
	@Column(name="DIPLOMA_NO",length=32)
	private String diplomaNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getEduResumeId() {
		return eduResumeId;
	}

	public void setEduResumeId(Long eduResumeId) {
		this.eduResumeId = eduResumeId;
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

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getEduSys() {
		return eduSys;
	}

	public void setEduSys(String eduSys) {
		this.eduSys = eduSys;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getDiplomaNo() {
		return diplomaNo;
	}

	public void setDiplomaNo(String diplomaNo) {
		this.diplomaNo = diplomaNo;
	}
	
}
