package com.yuchengtech.emp.ecif.rulemanage.entity;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DQ_INCR_COL_CONF database table.
 * 
 */
@Entity
@Table(name="ETL_CVR_COL_CONF")
public class DqIncrColConf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DqIncrColConfPK id;

	@Column(name="INS_ONL", length=1)
	private String insOnl;

	@Column(name="SRC_COL", length=256)
	private String srcCol;

	@Column(name="SYS", length=256)
	private String sys;


    public DqIncrColConf() {
    }

	public DqIncrColConfPK getId() {
		return this.id;
	}

	public void setId(DqIncrColConfPK id) {
		this.id = id;
	}
	
	public String getInsOnl() {
		return this.insOnl;
	}

	public void setInsOnl(String insOnl) {
		this.insOnl = insOnl;
	}

	public String getSrcCol() {
		return this.srcCol;
	}

	public void setSrcCol(String srcCol) {
		this.srcCol = srcCol;
	}

	public String getSys() {
		return this.sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}


}