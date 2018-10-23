package com.yuchengtech.bcrm.calc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OCRM_O_FTP_RATE")
public class OcrmOFtpRate implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommonSequnce")
	@Column(name = "CURRDAT")
	public int currDat;

	@Column(name = "CCY")
	public String ccy;

	@Column(name = "TYPE")
	public String type;

	@Column(name = "TENOR")
	public String tenor;

	@Column(name = "RATE")
	public float rate;

	@Column(name = "PSNCUSF")
	public String psnCusf;
	
	public OcrmOFtpRate(){
		
	}

	public int getCurrDat() {
		return currDat;
	}

	public void setCurrDat(int currDat) {
		this.currDat = currDat;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getPsnCusf() {
		return psnCusf;
	}

	public void setPsnCusf(String psnCusf) {
		this.psnCusf = psnCusf;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
}
