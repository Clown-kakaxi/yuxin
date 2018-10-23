package com.yuchengtech.emp.ecif.rulemanage.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DQ_INCR_TAB_CONF database table.
 * 
 */
@Entity
@Table(name="ETL_CVR_TAB_CONF")
public class DqIncrTabConf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private String id;

	@Column(length=1)
	private String app;

	@Column(name="BAK_COL", length=500)
	private String bakCol;

	@Column(name="BAK_VAL", length=500)
	private String bakVal;

	@Column(name="DST_JOIN", length=256)
	private String dstJoin;

	@Column(name="DST_TAB", length=64)
	private String dstTab;

	@Column(name="HIS_OPER_TIME")
	private Timestamp hisOperTime;

	@Column(name="PRI_KEY", length=256)
	private String key;
	
	@Column(name="DST_SCH",length=64)
	private String schdst;

	@Column(name="SRC_SCH", length=64)
	private String schsrc;

	@Column(name="SRC_JOIN", length=256)
	private String srcJoin;

	@Column(name="SRC_TAB", length=64)
	private String srcTab;

	@Column(name="SYS_COL", length=64)
	private String sysCol;

	@Column(name="TYPE", length=1)
	private String type;

    public DqIncrTabConf() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getBakCol() {
		return this.bakCol;
	}

	public void setBakCol(String bakCol) {
		this.bakCol = bakCol;
	}

	public String getBakVal() {
		return this.bakVal;
	}

	public void setBakVal(String bakVal) {
		this.bakVal = bakVal;
	}

	public String getDstJoin() {
		return this.dstJoin;
	}

	public void setDstJoin(String dstJoin) {
		this.dstJoin = dstJoin;
	}

	public String getDstTab() {
		return this.dstTab;
	}

	public void setDstTab(String dstTab) {
		this.dstTab = dstTab;
	}

	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSchdst() {
		return this.schdst;
	}

	public void setSchdst(String schdst) {
		this.schdst = schdst;
	}

	public String getSchsrc() {
		return this.schsrc;
	}

	public void setSchsrc(String schsrc) {
		this.schsrc = schsrc;
	}

	public String getSrcJoin() {
		return this.srcJoin;
	}

	public void setSrcJoin(String srcJoin) {
		this.srcJoin = srcJoin;
	}

	public String getSrcTab() {
		return this.srcTab;
	}

	public void setSrcTab(String srcTab) {
		this.srcTab = srcTab;
	}

	public String getSysCol() {
		return this.sysCol;
	}

	public void setSysCol(String sysCol) {
		this.sysCol = sysCol;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}