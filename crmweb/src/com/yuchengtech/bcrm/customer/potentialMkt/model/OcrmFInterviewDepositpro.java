package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_INTERVIEW_DEPOSITPRO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_DEPOSITPRO")
public class OcrmFInterviewDepositpro implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="OCRM_F_INTERVIEW_DEPOSITPRO_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_DEPOSITPRO_GENERATOR")
    private String id;
    
	@Column(name="DP_AVGDEPOSIT")
	private BigDecimal dpAvgdeposit;

	@Column(name="DP_NAME")
	private String dpName;

	@Column(name="TASK_NUMBER")
	private String taskNumber;

    public OcrmFInterviewDepositpro() {
    }

	public BigDecimal getDpAvgdeposit() {
		return this.dpAvgdeposit;
	}

	public void setDpAvgdeposit(BigDecimal dpAvgdeposit) {
		this.dpAvgdeposit = dpAvgdeposit;
	}

	public String getDpName() {
		return this.dpName;
	}

	public void setDpName(String dpName) {
		this.dpName = dpName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

}