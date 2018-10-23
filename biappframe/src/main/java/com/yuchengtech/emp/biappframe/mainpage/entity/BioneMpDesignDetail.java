package com.yuchengtech.emp.biappframe.mainpage.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIONE_MP_DESIGN_DETAIL database table.
 * 
 */
@Entity
@Table(name="BIONE_MP_DESIGN_DETAIL")
public class BioneMpDesignDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DETAIL_ID")
	private String detailId;

	@Column(name="DESIGN_ID")
	private String designId;

	@Column(name="IS_DISPLAY_LABEL")
	private String isDisplayLabel;

	@Column(name="MODULE_ID")
	private String moduleId;

	@Column(name="POS_NO")
	private BigDecimal posNo;

    public BioneMpDesignDetail() {
    }

	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getDesignId() {
		return this.designId;
	}

	public void setDesignId(String designId) {
		this.designId = designId;
	}

	public String getIsDisplayLabel() {
		return this.isDisplayLabel;
	}

	public void setIsDisplayLabel(String isDisplayLabel) {
		this.isDisplayLabel = isDisplayLabel;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public BigDecimal getPosNo() {
		return this.posNo;
	}

	public void setPosNo(BigDecimal posNo) {
		this.posNo = posNo;
	}

}