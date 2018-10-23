package com.yuchengtech.bob.model;

import java.io.Serializable;
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
 * SubjectStatistics 科目设置表
 */
@Entity
@Table(name = "OCRM_SYS_SUBJECT_STATISITCS")
public class SubjectStatistics implements Serializable {

	private static final long serialVersionUID = 9085233188675957336L;

	/** 序号 */
	@Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "SUBJECT_ID", nullable = false)
	private Long subjectId;

	/** 科目号 */
	@Column(name = "SUBJECT_KIND", length = 100)
	private String subjectKind;

	/** 科目名称 */
	@Column(name = "SUBJECT_NAME", length = 100)
	private String subjectName;

	/** 业务种类 */
	@Column(name = "BUSSINESS_TYPE", length = 100)
	private String bussinessType;

	/** 创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	/** 创建人 */
	@Column(name = "EMPLOYEE_NAME", length = 200)
	private String employeeName;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectKind() {
		return subjectKind;
	}

	public void setSubjectKind(String subjectKind) {
		this.subjectKind = subjectKind;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}



}
