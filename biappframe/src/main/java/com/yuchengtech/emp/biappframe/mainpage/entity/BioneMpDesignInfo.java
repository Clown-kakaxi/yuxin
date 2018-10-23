package com.yuchengtech.emp.biappframe.mainpage.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_MP_DESIGN_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_MP_DESIGN_INFO")
public class BioneMpDesignInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DESIGN_ID")
	private String designId;

	@Column(name="LAYOUT_ID")
	private String layoutId;

	@Column(name="LOGIC_SYS_NO")
	private String logicSysNo;

	@Column(name="USER_ID")
	private String userId;

    public BioneMpDesignInfo() {
    }

	public String getDesignId() {
		return this.designId;
	}

	public void setDesignId(String designId) {
		this.designId = designId;
	}

	public String getLayoutId() {
		return this.layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}