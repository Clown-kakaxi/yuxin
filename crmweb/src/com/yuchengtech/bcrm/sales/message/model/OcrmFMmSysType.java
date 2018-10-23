package com.yuchengtech.bcrm.sales.message.model;


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
 * The persistent class for the OCRM_F_MM_SYS_TYPE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MM_SYS_TYPE")
public class OcrmFMmSysType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MM_SYS_TYPE_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MM_SYS_TYPE_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=19)
	private Long id;

	@Column(name="CATL_CODE", length=100)
	private String catlCode;

	@Column(name="CATL_NAME", length=100)
	private String catlName;

    @Temporal( TemporalType.DATE)
	@Column(name="CREAT_DATE")
	private Date creatDate;

	@Column(name="CREAT_USER", length=50)
	private String creatUser;

	@Column(name="MODEL_INFO", length=500)
	private String modelInfo;

	@Column(name="MODEL_NAME", length=200)
	private String modelName;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATA_DATE")
	private Date updataDate;

	@Column(name="UPDATA_USER", length=50)
	private String updataUser;
	
	@Column(name="MODEL_TYPE", length=100)
	private String modelType;
	@Column(name="IS_ENABLE", length=10)
	private String isEnable;
	
	@Column(name="CREAT_USER_NAME", length=50)
	private String creatUserName;
	
	@Column(name="UPDATA_USER_NAME", length=50)
	private String updataUserName;
	

    public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public OcrmFMmSysType() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCatlCode() {
		return this.catlCode;
	}

	public void setCatlCode(String catlCode) {
		this.catlCode = catlCode;
	}

	public String getCatlName() {
		return this.catlName;
	}

	public void setCatlName(String catlName) {
		this.catlName = catlName;
	}

	public Date getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getCreatUser() {
		return this.creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}

	public String getModelInfo() {
		return this.modelInfo;
	}

	public void setModelInfo(String modelInfo) {
		this.modelInfo = modelInfo;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Date getUpdataDate() {
		return this.updataDate;
	}

	public void setUpdataDate(Date updataDate) {
		this.updataDate = updataDate;
	}

	public String getUpdataUser() {
		return this.updataUser;
	}

	public void setUpdataUser(String updataUser) {
		this.updataUser = updataUser;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getCreatUserName() {
		return creatUserName;
	}

	public void setCreatUserName(String creatUserName) {
		this.creatUserName = creatUserName;
	}

	public String getUpdataUserName() {
		return updataUserName;
	}

	public void setUpdataUserName(String updataUserName) {
		this.updataUserName = updataUserName;
	}


	
	

}