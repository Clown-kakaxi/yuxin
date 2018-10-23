package com.yuchengtech.bcrm.calc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OCRM_O_RMB_RATE")
public class OcrmORmbRate implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommonSequnce")
	
	@Column(name="CURRDAT")
	private int currDat;
	
	@Column(name="TENOR")
	private String tenor;
	
	@Column(name="RATE")
	private float rate;
	
	public OcrmORmbRate(){
		
	}

	public int getCurrDat() {
		return currDat;
	}

	public void setCurrDat(int currDat) {
		this.currDat = currDat;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
}
