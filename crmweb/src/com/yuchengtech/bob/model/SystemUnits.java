package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机构表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_UNITS")
public class SystemUnits {
	
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long  id;
	
	//机构ID
	@Column(name = "SYSTEM_UNITID",length=100)
	private String unitid;
	
	//机构名称
	@Column(name = "SYSTEM_UNITNAME",length=100)
	private String unitname;
	
	//上级机构ID
	@Column(name = "SYSTEM_SUPERUNITID",length=100)
	private String superunitid;
	
	//机构层级
	@Column(name = "SYSTEM_LEVELUNIT",length=100)
	private String levelunit;
//	//开始时间(生效时间)
//	@Column(name = "SYSTEM_SUPERUNITNAME",length=100)
//	private String startdate;	
//	
//	//结束时间(失效时间)
//	private String enddate;
//	
//	//创建时间
//	private String createdate;
//	
//	//更新时间
//	private String updated;
//	
//	//创建人ID
//	private String createid;
	
	//是否启用
	@Column(name = "IS_ENABLED")
	private Boolean isEnabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}


	public String getSuperunitid() {
		return superunitid;
	}

	public void setSuperunitid(String superunitid) {
		this.superunitid = superunitid;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getLevelunit() {
		return levelunit;
	}

	public void setLevelunit(String levelunit) {
		this.levelunit = levelunit;
	}
	
	
	
	
}
