package com.yuchengtech.bcrm.callReport.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_SE_CALLREPORT database table.
 * 访谈记录
 */
@Entity
@Table(name="OCRM_F_SE_CALLREPORT")
public class OcrmFSeCallreport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="CALL_ID")
	private Long callId;
  
	/**
	 * 客户渠道
	 */
	@Column(name="CUST_CHANNEL")
	private String custChannel;
	
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;
	
	@Column(name="BEGIN_DATE")
	private String beginDate;
	
	@Column(name="END_DATE")
	private String endDate;

	@Column(name="CUST_TYPE")
	private String custType;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LINK_PHONE")
	private String linkPhone;

	@Column(name="RECOMMEND_CUST_ID")
	private String recommendCustId;

	@Column(name="VISIT_CONTENT")
	private String visitContent;
	
	@Column(name="MANAGER_OPINION")
	private String managerOpinion;

	@Column(name="MANAGER_USER_NAME")
	private String managerUserName;
	
	@Column(name="MANAGER_USER")
	private String managerUser;

    @Temporal( TemporalType.DATE)
	@Column(name="NEXT_VISIT_DATE")
	private Date nextVisitDate;

	@Column(name="NEXT_VISIT_WAY")
	private String nextVisitWay;
	
    @Temporal( TemporalType.DATE)
	@Column(name="VISIT_DATE")
	private Date visitDate;
    
	@Column(name="VISIT_WAY")
	private String visitWay;
	
	@Column(name="CREATE_USER")
	private String createUser;
	
	@Column(name="MKT_BAK_NOTE")
	private String mktBakNote;
	
    public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getNextVisitDate() {
		return nextVisitDate;
	}

	public void setNextVisitDate(Date nextVisitDate) {
		this.nextVisitDate = nextVisitDate;
	}

	public String getNextVisitWay() {
		return nextVisitWay;
	}

	public void setNextVisitWay(String nextVisitWay) {
		this.nextVisitWay = nextVisitWay;
	}

	public String getCustChannel() {
		return custChannel;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setCustChannel(String custChannel) {
		this.custChannel = custChannel;
	}

	public OcrmFSeCallreport() {
    }

	public Long getCallId() {
		return this.callId;
	}

	public void setCallId(Long callId) {
		this.callId = callId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getRecommendCustId() {
		return this.recommendCustId;
	}

	public void setRecommendCustId(String recommendCustId) {
		this.recommendCustId = recommendCustId;
	}

	public String getVisitContent() {
		return this.visitContent;
	}

	public void setVisitContent(String visitContent) {
		this.visitContent = visitContent;
	}

	public String getManagerOpinion() {
		return managerOpinion;
	}

	public void setManagerOpinion(String managerOpinion) {
		this.managerOpinion = managerOpinion;
	}
	
	public String getManagerUserName() {
		return managerUserName;
	}

	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	
	public String getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(String managerUser) {
		this.managerUser = managerUser;
	}

	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getVisitWay() {
		return this.visitWay;
	}

	public void setVisitWay(String visitWay) {
		this.visitWay = visitWay;
	}
	
	public String getMktBakNote() {
		return this.mktBakNote;
	}

	public void setMktBakNote(String mktBakNote) {
		this.mktBakNote = mktBakNote;
	}

}