package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_PD_PROD_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_PD_PROD_INFO")
public class ProductInfo implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -2499687677678383278L;

    @Id
//	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="CATL_CODE", length=20)
	private String catlCode;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="DISPLAY_FLAG", length=32)
	private String displayFlag;

	@Column(name="PROD_CREATOR", length=30)
	private String productCreator;

	@Column(name="PROD_DESC", length=128)
	private String productDescription;

    @Temporal( TemporalType.DATE)
	@Column(name="PROD_END_DATE")
	private Date productEndDate;

	@Column(name="PROD_NAME", nullable=false, length=128)
	private String productName;

	@Column(name="PROD_QUERY_URL", length=32)
	private String productQueryUrl;
	
	@Column(name="RATE", length=32)
	private String rate;
	
	@Column(name="COST_RATE", length=32)
	private String cost_rate;
	
	@Column(name="LIMIT_TIME", length=32)
	private String limit_time;
	
	@Column(name="PROD_CHARACT", length=500)
	private String prod_charact;
	
	@Column(name="OBJ_CUST_DISC", length=500)
	private String obj_cust_disc;
	
	@Column(name="DANGER_DISC", length=500)
	private String danger_disc;
	
	@Column(name="CHANNEL_DISC", length=500)
	private String channel_disc;
	
	@Column(name="ASSURE_DISC", length=500)
	private String assure_disc;
	
	@Column(name="PROD_SEQ", length=32)
	private String productSeq;

	@Column(name="PROD_DEPT", length=32)
	private String productDepartment;

    @Temporal( TemporalType.DATE)
	@Column(name="PROD_START_DATE")
	private Date productStartDate;

	@Column(name="PROD_STATE", length=13)
	private String productState;

	@Column(name="PROD_TYPE_ID", precision=10)
	private Long productTypeId;
	
	@Column(name="PROD_SHOW_URL", length=32)
	private String productShowUrl;

	@Column(name="TJKJ",length=1)
	private String tjkj;

    public ProductInfo() {
    }

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCatlCode() {
		return this.catlCode;
	}

	public void setCatlCode(String catlCode) {
		this.catlCode = catlCode;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDisplayFlag() {
		return this.displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	

	public String getProductCreator() {
		return productCreator;
	}

	public void setProductCreator(String productCreator) {
		this.productCreator = productCreator;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Date getProductEndDate() {
		return productEndDate;
	}

	public void setProductEndDate(Date productEndDate) {
		this.productEndDate = productEndDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductQueryUrl() {
		return productQueryUrl;
	}

	public void setProductQueryUrl(String productQueryUrl) {
		this.productQueryUrl = productQueryUrl;
	}

	public String getProductSeq() {
		return productSeq;
	}

	public void setProductSeq(String productSeq) {
		this.productSeq = productSeq;
	}

	public String getProductShowUrl() {
		return productShowUrl;
	}

	public void setProductShowUrl(String productShowUrl) {
		this.productShowUrl = productShowUrl;
	}

	public Date getProductStartDate() {
		return productStartDate;
	}

	public void setProductStartDate(Date productStartDate) {
		this.productStartDate = productStartDate;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}	

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductDepartment() {
		return productDepartment;
	}
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCost_rate() {
		return cost_rate;
	}

	public void setCost_rate(String cost_rate) {
		this.cost_rate = cost_rate;
	}

	public String getLimit_time() {
		return limit_time;
	}

	public void setLimit_time(String limit_time) {
		this.limit_time = limit_time;
	}

	public String getProd_charact() {
		return prod_charact;
	}

	public void setProd_charact(String prod_charact) {
		this.prod_charact = prod_charact;
	}

	public String getObj_cust_disc() {
		return obj_cust_disc;
	}

	public void setObj_cust_disc(String obj_cust_disc) {
		this.obj_cust_disc = obj_cust_disc;
	}

	public String getDanger_disc() {
		return danger_disc;
	}

	public void setDanger_disc(String danger_disc) {
		this.danger_disc = danger_disc;
	}

	public String getChannel_disc() {
		return channel_disc;
	}

	public void setChannel_disc(String channel_disc) {
		this.channel_disc = channel_disc;
	}

	public String getAssure_disc() {
		return assure_disc;
	}

	public void setAssure_disc(String assure_disc) {
		this.assure_disc = assure_disc;
	}

	public void setProductDepartment(String productDepartment) {
		this.productDepartment = productDepartment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTjkj() {
		return this.tjkj;
	}

	public void setTjkj(String tjkj) {
		this.tjkj = tjkj;
	}

}