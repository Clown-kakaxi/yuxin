package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the BELONGLINEDEPT database table.
 * 
 */
@Entity
@Table(name="BELONGLINEDEPT")
public class Belonglinedept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BELONG_LINE_DEPT_ID", unique=true, nullable=false)
	private Long belongLineDeptId;

	@Column(name="BELONG_DEPT", length=20)
	private String belongDept;

	@Column(name="BELONG_LINE", length=20)
	private String belongLine;

	@Column(name="CUST_ID")
	private Long custId;

    public Belonglinedept() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getBelongLineDeptId() {
		return this.belongLineDeptId;
	}

	public void setBelongLineDeptId(Long belongLineDeptId) {
		this.belongLineDeptId = belongLineDeptId;
	}

	public String getBelongDept() {
		return this.belongDept;
	}

	public void setBelongDept(String belongDept) {
		this.belongDept = belongDept;
	}

	public String getBelongLine() {
		return this.belongLine;
	}

	public void setBelongLine(String belongLine) {
		this.belongLine = belongLine;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

}