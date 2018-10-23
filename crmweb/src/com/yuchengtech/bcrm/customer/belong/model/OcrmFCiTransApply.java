package com.yuchengtech.bcrm.customer.belong.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_TRANS_APPLY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_TRANS_APPLY")
public class OcrmFCiTransApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_TRANS_APPLY_APPLYNO_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_TRANS_APPLY_APPLYNO_GENERATOR")
	@Column(name="APPLY_NO")
	private Long applyNo;

    @Temporal( TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;

	@Column(name="APPROVE_STAT")
	private String approveStat;

	@Column(name="HAND_KIND")
	private String handKind;

	@Column(name="HAND_OVER_REASON")
	private String handOverReason;

	@Column(name="T_MGR_ID")
	private String tMgrId;

	@Column(name="T_MGR_NAME")
	private String tMgrName;

	@Column(name="T_ORG_ID")
	private String tOrgId;

	@Column(name="T_ORG_NAME")
	private String tOrgName;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="APPLY_TYPE")
	private String applyType;
	
    @Temporal( TemporalType.DATE)
	@Column(name="WORK_INTERFIX_DT")
	private Date workInterfixDt;
    
    @Column(name="OLD_AUM")
    private String oldAum;
    
    @Column(name="NEW_AUM")
    private String newAum;
    
    @Column(name="OLD_CREDIT")
    private String oldCredit;
    
    @Column(name="NEW_CREDIT")
    private String newCredit;
    
    @Column(name="TRANS_CONTENT")
    private String transContent;
    
    @Column(name="TRANS_OTHER")
    private String transOther;
    
    @Column(name="TYPE")
    private String type;

    public OcrmFCiTransApply() {
    }

	public Long getApplyNo() {
		return this.applyNo;
	}

	public void setApplyNo(Long applyNo) {
		this.applyNo = applyNo;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApproveStat() {
		return this.approveStat;
	}

	public void setApproveStat(String approveStat) {
		this.approveStat = approveStat;
	}

	public String getHandKind() {
		return this.handKind;
	}

	public void setHandKind(String handKind) {
		this.handKind = handKind;
	}

	public String getHandOverReason() {
		return this.handOverReason;
	}

	public void setHandOverReason(String handOverReason) {
		this.handOverReason = handOverReason;
	}

	public String getTMgrId() {
		return this.tMgrId;
	}

	public void setTMgrId(String tMgrId) {
		this.tMgrId = tMgrId;
	}

	public String getTMgrName() {
		return this.tMgrName;
	}

	public void setTMgrName(String tMgrName) {
		this.tMgrName = tMgrName;
	}

	public String getTOrgId() {
		return this.tOrgId;
	}

	public void setTOrgId(String tOrgId) {
		this.tOrgId = tOrgId;
	}

	public String getTOrgName() {
		return this.tOrgName;
	}

	public void setTOrgName(String tOrgName) {
		this.tOrgName = tOrgName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getWorkInterfixDt() {
		return this.workInterfixDt;
	}

	public void setWorkInterfixDt(Date workInterfixDt) {
		this.workInterfixDt = workInterfixDt;
	}

	public String gettMgrId() {
		return tMgrId;
	}

	public void settMgrId(String tMgrId) {
		this.tMgrId = tMgrId;
	}

	public String gettMgrName() {
		return tMgrName;
	}

	public void settMgrName(String tMgrName) {
		this.tMgrName = tMgrName;
	}

	public String gettOrgId() {
		return tOrgId;
	}

	public void settOrgId(String tOrgId) {
		this.tOrgId = tOrgId;
	}

	public String gettOrgName() {
		return tOrgName;
	}

	public void settOrgName(String tOrgName) {
		this.tOrgName = tOrgName;
	}

	public String getOldAum() {
		return oldAum;
	}

	public void setOldAum(String oldAum) {
		this.oldAum = oldAum;
	}

	public String getNewAum() {
		return newAum;
	}

	public void setNewAum(String newAum) {
		this.newAum = newAum;
	}

	public String getOldCredit() {
		return oldCredit;
	}

	public void setOldCredit(String oldCredit) {
		this.oldCredit = oldCredit;
	}

	public String getNewCredit() {
		return newCredit;
	}

	public void setNewCredit(String newCredit) {
		this.newCredit = newCredit;
	}

	public String getTransContent() {
		return transContent;
	}

	public void setTransContent(String transContent) {
		this.transContent = transContent;
	}

	public String getTransOther() {
		return transOther;
	}

	public void setTransOther(String transOther) {
		this.transOther = transOther;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}