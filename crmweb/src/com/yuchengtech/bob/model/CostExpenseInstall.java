package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 客户贡献度——成本费用设置
 */
@Entity
@Table(name = "acrm_f_ci_contribution_s")
public class CostExpenseInstall implements Serializable {

	private static final long serialVersionUID = 2057403964492006610L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
    /** 客户编号 */
    @Column(name = "CUST_ID", length = 20)
    private String CUST_ID;
    /**客户关系费用 */
	@Column(precision = 24, scale = 6)
	private BigDecimal GXFY;
	  /**产品相关间接费用 */
	@Column(precision = 24, scale = 6)
	private BigDecimal XGJJFY;
	  /**风险损失拨备 */
	@Column(precision = 24, scale = 6)
	private BigDecimal CREDITLOSE;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCUST_ID() {
		return CUST_ID;
	}
	public void setCUST_ID(String cUST_ID) {
		CUST_ID = cUST_ID;
	}
	public BigDecimal getGXFY() {
		return GXFY;
	}
	public void setGXFY(BigDecimal gXFY) {
		GXFY = gXFY;
	}
	public BigDecimal getXGJJFY() {
		return XGJJFY;
	}
	public void setXGJJFY(BigDecimal xGJJFY) {
		XGJJFY = xGJJFY;
	}
	public BigDecimal getCREDITLOSE() {
		return CREDITLOSE;
	}
	public void setCREDITLOSE(BigDecimal cREDITLOSE) {
		CREDITLOSE = cREDITLOSE;
	}
	
	
}