package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the OCRM_F_CI_GROUP_INFO database table.
 * 
 * FDM层(基础数据层)，归属于客户集团的第二主题，数据为对客户集团的信息
 */
@Entity
//@Table(name = "OCRM_F_CI_GROUP_INFO", schema = "fdm")
@Table(name = "OCRM_F_CI_GROUP_INFO")
public class ClientGroupInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5198799400488146876L;

	@Id
	/**集团客户主键*/
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false)
	private Long ID;

	/** 集团客户id */
	@Column(name = "GROUP_ID", nullable = false, length = 50)
	private String groupId;

	/** 创建日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;

	/** 创建人 */
	@Column(name = "CREATE_USER_ID", nullable = false, length = 30)
	private String createUserId;

	/** 主办机构 */
	@Column(name = "GROUP_HOST_ORG", nullable = false, length = 30)
	private String groupHostOrganizer;

	/** 集团公司简介 */
	@Column(name = "GROUP_MEMO", length = 500)
	private String groupMemo;

	/** 集团客户名称 */
	@Column(name = "GROUP_NAME", nullable = false, length = 100)
	private String groupName;

	/** 集团客户状态 */
	@Column(name = "GROUP_STATUS", nullable = false, length = 20)
	private String groupStatus;

	/** 集团类型 */
	@Column(name = "GROUP_TYPE", nullable = false, length = 20)
	private String groupType;

	/** 成员企业数 */
	@Column(name = "MEMBER_NUM", nullable = false)
	private Integer memberNumber;

	/** 集团母公司注册地址 */
	@Column(name = "ROOT_COM_ADDRESS", length = 200)
	private String rootCompanyAddress;

	/** 集团母公司id */
	@Column(name = "ROOT_COM_ID", nullable = false, length = 50)
	private String rootCompanyId;

	/** 下属公司规模 */
	@Column(name = "SUB_COM_SCALE", length = 20)
	private String subCompanyScale;

	/** 更新日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE", nullable = false)
	private Date updateDate;

	/** 更新人 */
	@Column(name = "UPDATE_USER_ID", nullable = false, length = 30)
	private String updateUserId;

	// bi-directional many-to-one association to ClientGroupMember
	// @OneToMany(mappedBy = "ocrmFCiGroupInfo")
	// private Set<ClientGroupMember> ocrmFCiGroupMembers;
	@OneToMany(mappedBy = "ocrmFCiGroupInfo")
	private Set<ClientGroupMember> ocrmFCiGroupMembers;

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getGroupMemo() {
		return this.groupMemo;
	}

	public void setGroupMemo(String groupMemo) {
		this.groupMemo = groupMemo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupStatus() {
		return this.groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupHostOrganizer() {
		return groupHostOrganizer;
	}

	public void setGroupHostOrganizer(String groupHostOrganizer) {
		this.groupHostOrganizer = groupHostOrganizer;
	}

	public String getRootCompanyAddress() {
		return rootCompanyAddress;
	}

	public void setRootCompanyAddress(String rootCompanyAddress) {
		this.rootCompanyAddress = rootCompanyAddress;
	}

	public String getRootCompanyId() {
		return rootCompanyId;
	}

	public void setRootCompanyId(String rootCompanyId) {
		this.rootCompanyId = rootCompanyId;
	}

	public String getSubCompanyScale() {
		return subCompanyScale;
	}

	public void setSubCompanyScale(String subCompanyScale) {
		this.subCompanyScale = subCompanyScale;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	// public Set<ClientGroupMember> getOcrmFCiGroupMembers() {
	// return this.ocrmFCiGroupMembers;
	// }
	//
	// public void setOcrmFCiGroupMembers(
	// Set<ClientGroupMember> ocrmFCiGroupMembers) {
	// this.ocrmFCiGroupMembers = ocrmFCiGroupMembers;
	// }

	public void setMemberNumber(Integer memberNumber) {
		this.memberNumber = memberNumber;
	}

	public Integer getMemberNumber() {
		return memberNumber;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getID() {
		return ID;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setOcrmFCiGroupMembers(
			Set<ClientGroupMember> ocrmFCiGroupMembers) {
		this.ocrmFCiGroupMembers = ocrmFCiGroupMembers;
	}

	public Set<ClientGroupMember> getOcrmFCiGroupMembers() {
		return ocrmFCiGroupMembers;
	}

}