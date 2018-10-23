package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MCiGroupMemberId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class MCiGroupMemberId implements java.io.Serializable {

	// Fields

	private BigDecimal memberId;
	private BigDecimal groupId;

	// Constructors

	/** default constructor */
	public MCiGroupMemberId() {
	}

	/** full constructor */
	public MCiGroupMemberId(BigDecimal memberId, BigDecimal groupId) {
		this.memberId = memberId;
		this.groupId = groupId;
	}

	// Property accessors

	@Column(name = "MEMBER_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getMemberId() {
		return this.memberId;
	}

	public void setMemberId(BigDecimal memberId) {
		this.memberId = memberId;
	}

	@Column(name = "GROUP_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MCiGroupMemberId))
			return false;
		MCiGroupMemberId castOther = (MCiGroupMemberId) other;

		return ((this.getMemberId() == castOther.getMemberId()) || (this
				.getMemberId() != null
				&& castOther.getMemberId() != null && this.getMemberId()
				.equals(castOther.getMemberId())))
				&& ((this.getGroupId() == castOther.getGroupId()) || (this
						.getGroupId() != null
						&& castOther.getGroupId() != null && this.getGroupId()
						.equals(castOther.getGroupId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMemberId() == null ? 0 : this.getMemberId().hashCode());
		result = 37 * result
				+ (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		return result;
	}

}