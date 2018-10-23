
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
 * 客户群筛选方式
 */
@Entity
@Table(name = "ocrm_f_ci_base_searchsolution")
public class OcrmFCiBaseSearchsolution implements Serializable {

	private static final long serialVersionUID = -3071512732613148823L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	

	@Column(name = "GROUP_ID")
	private Long groupId;
	
	@Column(name = "GROUP_NAME", length = 100)
	private String groupName;

	@Column(name = "SS_TYPE", length = 100)
	private String ssType;
	@Column(name = "SS_RESULT", length = 1000)
	private String ssResult;
	@Column(name = "SS_SORT", length = 100)
	private String ssSort;
	@Column(name = "SS_USER", length =30)
	private String ssUser ;
	@Temporal(TemporalType.DATE)
	@Column(name = "SS_DATE")
	private Date ssDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getSsType() {
		return ssType;
	}
	public void setSsType(String ssType) {
		this.ssType = ssType;
	}
	public String getSsResult() {
		return ssResult;
	}
	public void setSsResult(String ssResult) {
		this.ssResult = ssResult;
	}
	public String getSsSort() {
		return ssSort;
	}
	public void setSsSort(String ssSort) {
		this.ssSort = ssSort;
	}
	public String getSsUser() {
		return ssUser;
	}
	public void setSsUser(String ssUser) {
		this.ssUser = ssUser;
	}
	public Date getSsDate() {
		return ssDate;
	}
	public void setSsDate(Date ssDate) {
		this.ssDate = ssDate;
	}
	

	
}
