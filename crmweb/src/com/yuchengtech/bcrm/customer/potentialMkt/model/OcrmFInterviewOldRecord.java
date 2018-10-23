package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_INTERVIEW_OLD_RECORD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_OLD_RECORD")
public class OcrmFInterviewOldRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_OLD_RECORD_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_OLD_RECORD_GENERATOR")
	private String id;
	
	@Column(name="BUS_EXPLAIN")
	private String busExplain;

    @Temporal( TemporalType.DATE)
	@Column(name="CALL_NEXTTIME")
	private Date callNexttime;

	@Column(name="CALL_SPENDTIME")
	private BigDecimal callSpendtime;

    @Temporal( TemporalType.DATE)
	@Column(name="CALL_TIME")
	private Date callTime;

	@Column(name="COL_EXPLAIN")
	private String colExplain;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CUS_STATUS")
	private BigDecimal cusStatus;

	@Column(name="EQU_EXPLAIN")
	private String equExplain;

	@Column(name="INTERVIEWEE_NAME")
	private String intervieweeName;

	@Column(name="INTERVIEWEE_PHONE")
	private String intervieweePhone;

	@Column(name="INTERVIEWEE_POST")
	private String intervieweePost;

	private BigDecimal isbuschange;

	private BigDecimal iscolchange;

	private BigDecimal isequchange;

	private BigDecimal isopcchange;

	private BigDecimal isprochange;

	private BigDecimal ispurchange;

	private BigDecimal isrevchange;

	private BigDecimal issupchange;

	private BigDecimal issymchange;

	@Column(name="JOIN_PERSON")
	private String joinPerson;

	@Column(name="MARK_PRODUCT")
	private String markProduct;

	@Column(name="MARK_REFUSEREASON")
	private BigDecimal markRefusereason;

	@Column(name="MARK_RESULT")
	private BigDecimal markResult;

	@Column(name="OPC_EXPLAIN")
	private String opcExplain;

	@Column(name="PRO_EXPLAIN")
	private String proExplain;

	@Column(name="PUR_EXPLAIN")
	private String purExplain;

	private String remark;

	@Column(name="RES_FOLLOWUP")
	private String resFollowup;

	@Column(name="RES_OTHERINFO")
	private String resOtherinfo;

	@Column(name="REV_EXPLAIN")
	private String revExplain;

	@Column(name="SUP_EXPLAIN")
	private String supExplain;

	@Column(name="SYM_EXPLAIN")
	private String symExplain;

	@Column(name="TASK_NUMBER")
	private String taskNumber;
	
	@Column(name="JOIN_PERSON_ID")
	private String joinPersonId;//本次参加人员编号

    public OcrmFInterviewOldRecord() {
    }

	public String getBusExplain() {
		return this.busExplain;
	}

	public void setBusExplain(String busExplain) {
		this.busExplain = busExplain;
	}

	public Date getCallNexttime() {
		return this.callNexttime;
	}

	public void setCallNexttime(Date callNexttime) {
		this.callNexttime = callNexttime;
	}

	public BigDecimal getCallSpendtime() {
		return this.callSpendtime;
	}

	public void setCallSpendtime(BigDecimal callSpendtime) {
		this.callSpendtime = callSpendtime;
	}

	public Date getCallTime() {
		return this.callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getColExplain() {
		return this.colExplain;
	}

	public void setColExplain(String colExplain) {
		this.colExplain = colExplain;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getCusStatus() {
		return this.cusStatus;
	}

	public void setCusStatus(BigDecimal cusStatus) {
		this.cusStatus = cusStatus;
	}

	public String getEquExplain() {
		return this.equExplain;
	}

	public void setEquExplain(String equExplain) {
		this.equExplain = equExplain;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntervieweeName() {
		return this.intervieweeName;
	}

	public void setIntervieweeName(String intervieweeName) {
		this.intervieweeName = intervieweeName;
	}

	public String getIntervieweePhone() {
		return this.intervieweePhone;
	}

	public void setIntervieweePhone(String intervieweePhone) {
		this.intervieweePhone = intervieweePhone;
	}

	public String getIntervieweePost() {
		return this.intervieweePost;
	}

	public void setIntervieweePost(String intervieweePost) {
		this.intervieweePost = intervieweePost;
	}

	public BigDecimal getIsbuschange() {
		return this.isbuschange;
	}

	public void setIsbuschange(BigDecimal isbuschange) {
		this.isbuschange = isbuschange;
	}

	public BigDecimal getIscolchange() {
		return this.iscolchange;
	}

	public void setIscolchange(BigDecimal iscolchange) {
		this.iscolchange = iscolchange;
	}

	public BigDecimal getIsequchange() {
		return this.isequchange;
	}

	public void setIsequchange(BigDecimal isequchange) {
		this.isequchange = isequchange;
	}

	public BigDecimal getIsopcchange() {
		return this.isopcchange;
	}

	public void setIsopcchange(BigDecimal isopcchange) {
		this.isopcchange = isopcchange;
	}

	public BigDecimal getIsprochange() {
		return this.isprochange;
	}

	public void setIsprochange(BigDecimal isprochange) {
		this.isprochange = isprochange;
	}

	public BigDecimal getIspurchange() {
		return this.ispurchange;
	}

	public void setIspurchange(BigDecimal ispurchange) {
		this.ispurchange = ispurchange;
	}

	public BigDecimal getIsrevchange() {
		return this.isrevchange;
	}

	public void setIsrevchange(BigDecimal isrevchange) {
		this.isrevchange = isrevchange;
	}

	public BigDecimal getIssupchange() {
		return this.issupchange;
	}

	public void setIssupchange(BigDecimal issupchange) {
		this.issupchange = issupchange;
	}

	public BigDecimal getIssymchange() {
		return this.issymchange;
	}

	public void setIssymchange(BigDecimal issymchange) {
		this.issymchange = issymchange;
	}

	public String getJoinPerson() {
		return this.joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
	}

	public String getMarkProduct() {
		return this.markProduct;
	}

	public void setMarkProduct(String markProduct) {
		this.markProduct = markProduct;
	}

	public BigDecimal getMarkRefusereason() {
		return this.markRefusereason;
	}

	public void setMarkRefusereason(BigDecimal markRefusereason) {
		this.markRefusereason = markRefusereason;
	}

	public BigDecimal getMarkResult() {
		return this.markResult;
	}

	public void setMarkResult(BigDecimal markResult) {
		this.markResult = markResult;
	}

	public String getOpcExplain() {
		return this.opcExplain;
	}

	public void setOpcExplain(String opcExplain) {
		this.opcExplain = opcExplain;
	}

	public String getProExplain() {
		return this.proExplain;
	}

	public void setProExplain(String proExplain) {
		this.proExplain = proExplain;
	}

	public String getPurExplain() {
		return this.purExplain;
	}

	public void setPurExplain(String purExplain) {
		this.purExplain = purExplain;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResFollowup() {
		return this.resFollowup;
	}

	public void setResFollowup(String resFollowup) {
		this.resFollowup = resFollowup;
	}

	public String getResOtherinfo() {
		return this.resOtherinfo;
	}

	public void setResOtherinfo(String resOtherinfo) {
		this.resOtherinfo = resOtherinfo;
	}

	public String getRevExplain() {
		return this.revExplain;
	}

	public void setRevExplain(String revExplain) {
		this.revExplain = revExplain;
	}

	public String getSupExplain() {
		return this.supExplain;
	}

	public void setSupExplain(String supExplain) {
		this.supExplain = supExplain;
	}

	public String getSymExplain() {
		return this.symExplain;
	}

	public void setSymExplain(String symExplain) {
		this.symExplain = symExplain;
	}

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getJoinPersonId() {
		return joinPersonId;
	}

	public void setJoinPersonId(String joinPersonId) {
		this.joinPersonId = joinPersonId;
	}

}