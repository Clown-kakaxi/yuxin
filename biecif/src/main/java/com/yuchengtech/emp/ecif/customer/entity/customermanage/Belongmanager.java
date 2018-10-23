package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the BELONGMANAGER database table.
 * 
 */
@Entity
@Table(name="BELONGMANAGER")
public class Belongmanager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BELONG_MANAGER_ID", unique=true, nullable=false)
	private Long belongManagerId;

	@Column(name="ADMIN_MANAGER_NO", length=20)
	private String adminManagerNo;

	@Column(name="BELONG_TYPE", length=20)
	private String belongType;

	@Column(name="MAIN_TYPE", length=20)
	private String mainType;
	
//	@Column(name="CUST_ASSIGN_STAT", length=20)
//	private String custAssignStat;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EMPCODE1",length=20)
	private String empcode1;

	@Column(name="EMPCODE2",length=20)
	private String empcode2;

    public Belongmanager() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getBelongManagerId() {
		return this.belongManagerId;
	}

	public void setBelongManagerId(Long belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	public String getAdminManagerNo() {
		return this.adminManagerNo;
	}

	public void setAdminManagerNo(String adminManagerNo) {
		this.adminManagerNo = adminManagerNo;
	}

	public String getBelongType() {
		return this.belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}
	
//	public String getCustAssignStat() {
//		return this.custAssignStat;
//	}
//
//	public void setCustAssignStat(String custAssignStat) {
//		this.custAssignStat = custAssignStat;
//	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEmpcode1() {
		return this.empcode1;
	}

	public void setEmpcode1(String empcode1) {
		this.empcode1 = empcode1;
	}

	public String getEmpcode2() {
		return this.empcode2;
	}

	public void setEmpcode2(String empcode2) {
		this.empcode2 = empcode2;
	}

}