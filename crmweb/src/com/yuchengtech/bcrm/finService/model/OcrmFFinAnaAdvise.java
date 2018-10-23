package com.yuchengtech.bcrm.finService.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_FIN_ANA_ADVISE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_ANA_ADVISE")
public class OcrmFFinAnaAdvise implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_ANA_ADVISE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_ANA_ADVISE_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CXNL_ADVISE")
	private String cxnlAdvise;

	@Column(name="FZBL_ADVISE")
	private String fzblAdvise;

	@Column(name="JZC_ADVISE")
	private String jzcAdvise;

	@Column(name="KYJL_ADVISE")
	private String kyjlAdvise;

	@Column(name="ZCNL_ADVISE")
	private String zcnlAdvise;

	@Column(name="ZCSR_ADVISE")
	private String zcsrAdvise;

	@Column(name="ZYQY_ADVISE")
	private String zyqyAdvise;

    public OcrmFFinAnaAdvise() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCxnlAdvise() {
		return this.cxnlAdvise;
	}

	public void setCxnlAdvise(String cxnlAdvise) {
		this.cxnlAdvise = cxnlAdvise;
	}

	public String getFzblAdvise() {
		return this.fzblAdvise;
	}

	public void setFzblAdvise(String fzblAdvise) {
		this.fzblAdvise = fzblAdvise;
	}

	public String getJzcAdvise() {
		return this.jzcAdvise;
	}

	public void setJzcAdvise(String jzcAdvise) {
		this.jzcAdvise = jzcAdvise;
	}

	public String getKyjlAdvise() {
		return this.kyjlAdvise;
	}

	public void setKyjlAdvise(String kyjlAdvise) {
		this.kyjlAdvise = kyjlAdvise;
	}

	public String getZcnlAdvise() {
		return this.zcnlAdvise;
	}

	public void setZcnlAdvise(String zcnlAdvise) {
		this.zcnlAdvise = zcnlAdvise;
	}

	public String getZcsrAdvise() {
		return this.zcsrAdvise;
	}

	public void setZcsrAdvise(String zcsrAdvise) {
		this.zcsrAdvise = zcsrAdvise;
	}

	public String getZyqyAdvise() {
		return this.zyqyAdvise;
	}

	public void setZyqyAdvise(String zyqyAdvise) {
		this.zyqyAdvise = zyqyAdvise;
	}

}