package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EMPLOYEEINFO database table.
 * 
 */
@Entity
@Table(name="EMPLOYEEINFO")
public class Employeeinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EMPCODE",unique=true, nullable=false, length=20)
	private String empcode;

	@Column(name="BRCCODE",length=9)
	private String brccode;

	@Column(name="CUSTMANAGERCODE",length=20)
	private String custmanagercode;

	@Column(name="EMPNAME",length=40)
	private String empname;

    public Employeeinfo() {
    }

	public String getEmpcode() {
		return this.empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public String getBrccode() {
		return this.brccode;
	}

	public void setBrccode(String brccode) {
		this.brccode = brccode;
	}

	public String getCustmanagercode() {
		return this.custmanagercode;
	}

	public void setCustmanagercode(String custmanagercode) {
		this.custmanagercode = custmanagercode;
	}

	public String getEmpname() {
		return this.empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

}