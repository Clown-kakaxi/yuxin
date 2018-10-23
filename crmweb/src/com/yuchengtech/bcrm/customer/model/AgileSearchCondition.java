package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户灵活查询--查询条件表
 */
@Entity
@Table(name = "OCRM_F_A_SS_COL")
public class AgileSearchCondition implements Serializable {

	private static final long serialVersionUID = -3071512732613148823L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	/** 查询方案ID */
	@Column(name = "SS_ID")
	private Long ssId;

	/** 顺序号*/
	@Column(name = "SS_COL_ORDER")
	private Long ssColOrder;
	/** 左括号*/
	@Column(name = "SS_COL_LEFT", length = 10)
	private String ssColLeft;
	/** 查询项*/
	@Column(name = "SS_COL_ITEM")
	private Long ssColItem;
	/** 操作符*/
	@Column(name = "SS_COL_OP", length =10)
	private String ssColOp ;
	/**查询值*/
	@Column(name = "SS_COL_VALUE", length =1000)
	private String ssColValue ;
	/**右括号*/
	@Column(name = "SS_COL_RIGHT", length =10)
	private String ssColRight ;
	/** 连接符*/
	@Column(name = "SS_COL_JOIN", length =10)
	private String ssColJoin ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSsId() {
		return ssId;
	}

	public void setSsId(Long ssId) {
		this.ssId = ssId;
	}

	public Long getSsColOrder() {
		return ssColOrder;
	}

	public void setSsColOrder(Long ssColOrder) {
		this.ssColOrder = ssColOrder;
	}

	public String getSsColLeft() {
		return ssColLeft;
	}

	public void setSsColLeft(String ssColLeft) {
		this.ssColLeft = ssColLeft;
	}

	public Long getSsColItem() {
		return ssColItem;
	}

	public void setSsColItem(Long ssColItem) {
		this.ssColItem = ssColItem;
	}

	public String getSsColOp() {
		return ssColOp;
	}

	public void setSsColOp(String ssColOp) {
		this.ssColOp = ssColOp;
	}

	public String getSsColValue() {
		return ssColValue;
	}

	public void setSsColValue(String ssColValue) {
		this.ssColValue = ssColValue;
	}

	public String getSsColRight() {
		return ssColRight;
	}

	public void setSsColRight(String ssColRight) {
		this.ssColRight = ssColRight;
	}

	public String getSsColJoin() {
		return ssColJoin;
	}

	public void setSsColJoin(String ssColJoin) {
		this.ssColJoin = ssColJoin;
	}
	

}