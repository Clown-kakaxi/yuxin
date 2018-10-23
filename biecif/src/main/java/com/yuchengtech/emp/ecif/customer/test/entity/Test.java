package com.yuchengtech.emp.ecif.customer.test.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the TEST database table.
 * 
 */
@Entity
@Table(name="TEST")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="TESTID", unique=true, nullable=false)
	private String testId;

	@Column(name="TESTNAME", length=100)
	private String testName;

	@Column(name="TESTTYPE", length=100)
	private String testType;
	
	@Column(name="TESTFLAG", length=100)
	private String testFlag;

    public Test() {
    }

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}
    
}

