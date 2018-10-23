package com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ISSUEBOND database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_ISSUEBOND")
public class Issuebond implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ISSUE_BOND_ID", unique=true, nullable=false)
	private Long issueBondId;

	@Column(name="BOND_GRADE",length=20)
	private String bondgrade;

	@Column(name="BOND_KIND",length=20)
	private String bondkind;

	@Column(name="BOND_NAME",length=80)
	private String bondname;

	@Column(name="BOND_SELLER",length=80)
	private String bondseller;

//	@Column(name="BONDSUM",precision=17, scale=2)
//	private BigDecimal bondsum;

	@Column(name="BOND_TERM",precision=10, scale=2)
	private BigDecimal bondterm;

	@Column(name="BOND_TYPE",length=20)
	private String bondtype;

	@Column(name="BOND_WARRANTOR",length=80)
	private String bondwarrantor;

	@Column(name="BOND_CURR",length=20)
	private String currsign;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EVAL_BANK",length=40)
	private String evalbank;

	@Column(name="IRREGULATION",length=200)
	private String irregulation;

	@Column(name="ISSUE_AMT",precision=17, scale=2)
	private BigDecimal issueamt;

	@Column(name="ISSUE_DATE",length=20)
	private String issuedate;

    public Issuebond() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getIssueBondId() {
		return this.issueBondId;
	}

	public void setIssueBondId(Long issueBondId) {
		this.issueBondId = issueBondId;
	}

	public String getBondgrade() {
		return this.bondgrade;
	}

	public void setBondgrade(String bondgrade) {
		this.bondgrade = bondgrade;
	}

	public String getBondkind() {
		return this.bondkind;
	}

	public void setBondkind(String bondkind) {
		this.bondkind = bondkind;
	}

	public String getBondname() {
		return this.bondname;
	}

	public void setBondname(String bondname) {
		this.bondname = bondname;
	}

	public String getBondseller() {
		return this.bondseller;
	}

	public void setBondseller(String bondseller) {
		this.bondseller = bondseller;
	}

//	public BigDecimal getBondsum() {
//		return this.bondsum;
//	}
//
//	public void setBondsum(BigDecimal bondsum) {
//		this.bondsum = bondsum;
//	}

	public BigDecimal getBondterm() {
		return this.bondterm;
	}

	public void setBondterm(BigDecimal bondterm) {
		this.bondterm = bondterm;
	}

	public String getBondtype() {
		return this.bondtype;
	}

	public void setBondtype(String bondtype) {
		this.bondtype = bondtype;
	}

	public String getBondwarrantor() {
		return this.bondwarrantor;
	}

	public void setBondwarrantor(String bondwarrantor) {
		this.bondwarrantor = bondwarrantor;
	}

	public String getCurrsign() {
		return this.currsign;
	}

	public void setCurrsign(String currsign) {
		this.currsign = currsign;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEvalbank() {
		return this.evalbank;
	}

	public void setEvalbank(String evalbank) {
		this.evalbank = evalbank;
	}

	public String getIrregulation() {
		return this.irregulation;
	}

	public void setIrregulation(String irregulation) {
		this.irregulation = irregulation;
	}

	public BigDecimal getIssueamt() {
		return this.issueamt;
	}

	public void setIssueamt(BigDecimal issueamt) {
		this.issueamt = issueamt;
	}

	public String getIssuedate() {
		return this.issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

}