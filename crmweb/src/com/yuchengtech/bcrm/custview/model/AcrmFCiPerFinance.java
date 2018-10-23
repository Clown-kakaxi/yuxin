package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ACRM_F_CI_PER_FINANCE database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_FINANCE")
public class AcrmFCiPerFinance implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ACRM_F_CI_PER_FINANCE_PRODUCT_ID_GENERATOR", sequenceName="PRODUCT_ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_PER_FINANCE_PRODUCT_ID_GENERATOR")
	@Column(name="FINANCE_ID")
	private String financeId;
	
	@Column(name="CUST_ID")
	private String custId;
	
	@Column(name="SYSTEM_PRO")
	private String systemPro;
	
	@Column(name="MANAGER_PRO")
	private String managerPro;
	
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;

	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getSystemPro() {
		return systemPro;
	}

	public void setSystemPro(String systemPro) {
		this.systemPro = systemPro;
	}

	public String getManagerPro() {
		return managerPro;
	}

	public void setManagerPro(String managerPro) {
		this.managerPro = managerPro;
	}

	public String getFinanceId() {
		return financeId;
	}

	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}

	public String getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

}
