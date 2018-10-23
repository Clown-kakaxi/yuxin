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
 * The persistent class for the ISSUESTOCK database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_ISSUESTOCK")
public class Issuestock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ISSUE_STOCK_ID", unique=true, nullable=false)
	private Long issueStockId;

	@Column(name="ALLOTMENT_SHARE_AMT",precision=17, scale=2)
	private BigDecimal allotmentshareamt;

	@Column(name="CURR_STOCK_NUM")
	private Long curstocknum;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EXCHANGE_NAME",length=80)
	private String exchangename;

	@Column(name="FLOW_STOCK_NUM")
	private Long flowstocknum;

	@Column(name="IPO_DATE",length=20)
	private String ipodate;

	@Column(name="ISSUE_STOCK_NUM")
	private Long issuestocknum;

	@Column(name="ISSUE_STOCK_PRICE",precision=12, scale=2)
	private BigDecimal issuestockprice;

	@Column(name="MARKET_PLACE",length=20)
	private String marketplace;

	@Column(name="NETASSET_PER_SHARE",precision=17, scale=2)
	private BigDecimal netassetpershare;

	@Column(name="SHAREHOLDER_NUM")
	private Long shareholderNum;

	@Column(name="STOCK_CODE",length=32)
	private String stockcode;

	@Column(name="STOCK_NAME",length=80)
	private String stockname;

	@Column(name="STOCKT_KIND",length=20)
	private String stocktkind;

	@Column(name="STOCK_TYPE",length=20)
	private String stocktype;

    public Issuestock() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getIssueStockId() {
		return this.issueStockId;
	}

	public void setIssueStockId(Long issueStockId) {
		this.issueStockId = issueStockId;
	}

	public BigDecimal getAllotmentshareamt() {
		return this.allotmentshareamt;
	}

	public void setAllotmentshareamt(BigDecimal allotmentshareamt) {
		this.allotmentshareamt = allotmentshareamt;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCurstocknum() {
		return this.curstocknum;
	}

	public void setCurstocknum(Long curstocknum) {
		this.curstocknum = curstocknum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getExchangename() {
		return this.exchangename;
	}

	public void setExchangename(String exchangename) {
		this.exchangename = exchangename;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getFlowstocknum() {
		return this.flowstocknum;
	}

	public void setFlowstocknum(Long flowstocknum) {
		this.flowstocknum = flowstocknum;
	}

	public String getIpodate() {
		return this.ipodate;
	}

	public void setIpodate(String ipodate) {
		this.ipodate = ipodate;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getIssuestocknum() {
		return this.issuestocknum;
	}

	public void setIssuestocknum(Long issuestocknum) {
		this.issuestocknum = issuestocknum;
	}

	public BigDecimal getIssuestockprice() {
		return this.issuestockprice;
	}

	public void setIssuestockprice(BigDecimal issuestockprice) {
		this.issuestockprice = issuestockprice;
	}

	public String getMarketplace() {
		return this.marketplace;
	}

	public void setMarketplace(String marketplace) {
		this.marketplace = marketplace;
	}

	public BigDecimal getNetassetpershare() {
		return this.netassetpershare;
	}

	public void setNetassetpershare(BigDecimal netassetpershare) {
		this.netassetpershare = netassetpershare;
	}

	public Long getShareholderNum() {
		return this.shareholderNum;
	}

	public void setShareholderNum(Long shareholderNum) {
		this.shareholderNum = shareholderNum;
	}

	public String getStockcode() {
		return this.stockcode;
	}

	public void setStockcode(String stockcode) {
		this.stockcode = stockcode;
	}

	public String getStockname() {
		return this.stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	public String getStocktkind() {
		return this.stocktkind;
	}

	public void setStocktkind(String stocktkind) {
		this.stocktkind = stocktkind;
	}

	public String getStocktype() {
		return this.stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}

}