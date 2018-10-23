package com.yuchengtech.emp.biappframe.mainpage.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_MP_LAYOUT_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_MP_LAYOUT_INFO")
public class BioneMpLayoutInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LAYOUT_ID")
	private String layoutId;

	@Column(name="LOGIC_SYS_NO")
	private String logicSysNo;
	
	@Column(name="LAYOUT_NAME")
	private String layoutName;

	@Column(name="CSS_PATH")
	private String cssPath;

	@Column(name="PIC_PATH")
	private String picPath;

	private String remark;

    public BioneMpLayoutInfo() {
    }

	public String getLayoutId() {
		return this.layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getLayoutName() {
		return this.layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

}