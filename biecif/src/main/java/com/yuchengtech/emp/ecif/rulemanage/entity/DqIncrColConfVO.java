package com.yuchengtech.emp.ecif.rulemanage.entity;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DQ_INCR_COL_CONF database table.
 * 
 */
public class DqIncrColConfVO implements Serializable {
	private static final long serialVersionUID = 1L;

    public DqIncrColConfVO() {
    }
	
    private String tid;

    private String dstCol;
    
    public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}


	public String getDstCol() {
		return dstCol;
	}

	public void setDstCol(String dstCol) {
		this.dstCol = dstCol;
	}

	private String insOnl;

	private String srcCol;

	private String sys;


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