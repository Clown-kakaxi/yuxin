package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
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
 * The persistent class for the ACRM_F_CI_CUST_ELEC_MGR_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CUST_ELEC_MGR_INFO")
public class AcrmFCiCustElecMgrInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_CUST_ELEC_MGR_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_CUST_ELEC_MGR_INFO_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="DOCU_ADDR")
	private String docuAddr;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="FILE_TYPE")
	private String fileType;
	
	@Column(name="LOAN_CONTRACT")
	private String loanContract;
	
	@Column(name="CREATE_USER")
	private String createUser;
	
	@Temporal( TemporalType.DATE)
	@Column(name="ARCHIVE_DATE")
	private Date archiveDate;
	
	@Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	public Long getId() {
		return id;
	}

	public String getLoanContract() {
		return loanContract;
	}

	public void setLoanContract(String loanContract) {
		this.loanContract = loanContract;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDocuAddr() {
		return docuAddr;
	}

	public void setDocuAddr(String docuAddr) {
		this.docuAddr = docuAddr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}