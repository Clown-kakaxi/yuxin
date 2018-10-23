package com.ytec.mdm.domain.biz;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Ocrm_F_Interview_New_Record")
public class OcrmFInterviewNewRecord {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "Ocrm_F_Interview_New_Record_ID_GENERATOR", sequenceName = "ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ocrm_F_Interview_New_Record_ID_GENERATOR")
	private BigDecimal id;

	@Column(name = "task_number")
	private String taskNumber;

	@Column(name = "interviewee_name")
	private String intervieweeName;

	@Column(name = "interviewee_post")
	private String intervieweePost;

	@Column(name = "interviewee_phone")
	private String intervieweePhone;

	@Column(name = "join_person")
	private String joinPerson;

	@Column(name = "call_time")
	private Timestamp callTime;

	@Column(name = "cus_domicile")
	private String cusDomicile;

	@Column(name = "cus_nature")
	private BigDecimal cusNature;

	@Column(name = "cus_legalperson")
	private String cusLegalperson;

	@Column(name = "cus_regtime")
	private Date cusRegtime;

	@Column(name = "cus_cntpeople")
	private BigDecimal cusCntpeople;

	@Column(name = "cus_onmark")
	private BigDecimal cusOnmark;

	@Column(name = "cus_onmarkplace")
	private BigDecimal cusOnmarkplace;

	@Column(name = "cus_ownbusi")
	private BigDecimal cusOwnbusi;

	@Column(name = "cus_busistatus")
	private BigDecimal cusBusistatus;

	@Column(name = "cus_operateperson")
	private String cusOperateperson;

	@Column(name = "cus_accountperson")
	private String cusAccountperson;

	@Column(name = "cus_majorproduct")
	private String cusMajorproduct;

	@Column(name = "cus_majorrival")
	private String cusMajorrival;

	@Column(name = "dcrb_majorsholder")
	private String dcrbMajorsholder;

	@Column(name = "dcrb_flow")
	private String dcrbFlow;

	@Column(name = "dcrb_fixedassets")
	private String dcrbFixedassets;

	@Column(name = "dcrb_profit")
	private String dcrbProfit;

	@Column(name = "dcrb_symbiosis")
	private String dcrbSymbiosis;

	@Column(name = "dcrb_othertrade")
	private String dcrbOthertrade;

	@Column(name = "dcrb_myselftrade")
	private String dcrbMyselftrade;

	@Column(name = "res_custsource")
	private BigDecimal resCustsource;

	@Column(name = "res_casebyperson")
	private String resCasebyperson;

	@Column(name = "res_casebyptel")
	private String resCasebyptel;

	@Column(name = "res_followup")
	private String resFollowup;

	@Column(name = "res_otherinfo")
	private String resOtherinfo;

	@Column(name = "mark_result")
	private BigDecimal markResult;

	@Column(name = "mark_refusereason")
	private BigDecimal markRefusereason;

	@Column(name = "call_spendtime")
	private BigDecimal callSpendtime;

	@Column(name = "call_nexttime")
	private Timestamp callNexttime;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "remark")
	private String remark;

	public OcrmFInterviewNewRecord() {
		super();
	}

	public OcrmFInterviewNewRecord(BigDecimal id, String taskNumber, String intervieweeName, String intervieweePost, String intervieweePhone, String joinPerson, Timestamp callTime, String cusDomicile,
			BigDecimal cusNature, String cusLegalperson, Date cusRegtime, BigDecimal cusCntpeople, BigDecimal cusOnmark, BigDecimal cusOnmarkplace, BigDecimal cusOwnbusi, BigDecimal cusBusistatus,
			String cusOperateperson, String cusAccountperson, String cusMajorproduct, String cusMajorrival, String dcrbMajorsholder, String dcrbFlow, String dcrbFixedassets, String dcrbProfit,
			String dcrbSymbiosis, String dcrbOthertrade, String dcrbMyselftrade, BigDecimal resCustsource, String resCasebyperson, String resCasebyptel, String resFollowup, String resOtherinfo,
			BigDecimal markResult, BigDecimal markRefusereason, BigDecimal callSpendtime, Timestamp callNexttime, Date createTime, String remark) {
		super();
		this.id = id;
		this.taskNumber = taskNumber;
		this.intervieweeName = intervieweeName;
		this.intervieweePost = intervieweePost;
		this.intervieweePhone = intervieweePhone;
		this.joinPerson = joinPerson;
		this.callTime = callTime;
		this.cusDomicile = cusDomicile;
		this.cusNature = cusNature;
		this.cusLegalperson = cusLegalperson;
		this.cusRegtime = cusRegtime;
		this.cusCntpeople = cusCntpeople;
		this.cusOnmark = cusOnmark;
		this.cusOnmarkplace = cusOnmarkplace;
		this.cusOwnbusi = cusOwnbusi;
		this.cusBusistatus = cusBusistatus;
		this.cusOperateperson = cusOperateperson;
		this.cusAccountperson = cusAccountperson;
		this.cusMajorproduct = cusMajorproduct;
		this.cusMajorrival = cusMajorrival;
		this.dcrbMajorsholder = dcrbMajorsholder;
		this.dcrbFlow = dcrbFlow;
		this.dcrbFixedassets = dcrbFixedassets;
		this.dcrbProfit = dcrbProfit;
		this.dcrbSymbiosis = dcrbSymbiosis;
		this.dcrbOthertrade = dcrbOthertrade;
		this.dcrbMyselftrade = dcrbMyselftrade;
		this.resCustsource = resCustsource;
		this.resCasebyperson = resCasebyperson;
		this.resCasebyptel = resCasebyptel;
		this.resFollowup = resFollowup;
		this.resOtherinfo = resOtherinfo;
		this.markResult = markResult;
		this.markRefusereason = markRefusereason;
		this.callSpendtime = callSpendtime;
		this.callNexttime = callNexttime;
		this.createTime = createTime;
		this.remark = remark;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getIntervieweeName() {
		return intervieweeName;
	}

	public void setIntervieweeName(String intervieweeName) {
		this.intervieweeName = intervieweeName;
	}

	public String getIntervieweePost() {
		return intervieweePost;
	}

	public void setIntervieweePost(String intervieweePost) {
		this.intervieweePost = intervieweePost;
	}

	public String getIntervieweePhone() {
		return intervieweePhone;
	}

	public void setIntervieweePhone(String intervieweePhone) {
		this.intervieweePhone = intervieweePhone;
	}

	public String getJoinPerson() {
		return joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
	}

	public Timestamp getCallTime() {
		return callTime;
	}

	public void setCallTime(Timestamp callTime) {
		this.callTime = callTime;
	}

	public String getCusDomicile() {
		return cusDomicile;
	}

	public void setCusDomicile(String cusDomicile) {
		this.cusDomicile = cusDomicile;
	}

	public BigDecimal getCusNature() {
		return cusNature;
	}

	public void setCusNature(BigDecimal cusNature) {
		this.cusNature = cusNature;
	}

	public String getCusLegalperson() {
		return cusLegalperson;
	}

	public void setCusLegalperson(String cusLegalperson) {
		this.cusLegalperson = cusLegalperson;
	}

	public Date getCusRegtime() {
		return cusRegtime;
	}

	public void setCusRegtime(Date cusRegtime) {
		this.cusRegtime = cusRegtime;
	}

	public BigDecimal getCusCntpeople() {
		return cusCntpeople;
	}

	public void setCusCntpeople(BigDecimal cusCntpeople) {
		this.cusCntpeople = cusCntpeople;
	}

	public BigDecimal getCusOnmark() {
		return cusOnmark;
	}

	public void setCusOnmark(BigDecimal cusOnmark) {
		this.cusOnmark = cusOnmark;
	}

	public BigDecimal getCusOnmarkplace() {
		return cusOnmarkplace;
	}

	public void setCusOnmarkplace(BigDecimal cusOnmarkplace) {
		this.cusOnmarkplace = cusOnmarkplace;
	}

	public BigDecimal getCusOwnbusi() {
		return cusOwnbusi;
	}

	public void setCusOwnbusi(BigDecimal cusOwnbusi) {
		this.cusOwnbusi = cusOwnbusi;
	}

	public BigDecimal getCusBusistatus() {
		return cusBusistatus;
	}

	public void setCusBusistatus(BigDecimal cusBusistatus) {
		this.cusBusistatus = cusBusistatus;
	}

	public String getCusOperateperson() {
		return cusOperateperson;
	}

	public void setCusOperateperson(String cusOperateperson) {
		this.cusOperateperson = cusOperateperson;
	}

	public String getCusAccountperson() {
		return cusAccountperson;
	}

	public void setCusAccountperson(String cusAccountperson) {
		this.cusAccountperson = cusAccountperson;
	}

	public String getCusMajorproduct() {
		return cusMajorproduct;
	}

	public void setCusMajorproduct(String cusMajorproduct) {
		this.cusMajorproduct = cusMajorproduct;
	}

	public String getCusMajorrival() {
		return cusMajorrival;
	}

	public void setCusMajorrival(String cusMajorrival) {
		this.cusMajorrival = cusMajorrival;
	}

	public String getDcrbMajorsholder() {
		return dcrbMajorsholder;
	}

	public void setDcrbMajorsholder(String dcrbMajorsholder) {
		this.dcrbMajorsholder = dcrbMajorsholder;
	}

	public String getDcrbFlow() {
		return dcrbFlow;
	}

	public void setDcrbFlow(String dcrbFlow) {
		this.dcrbFlow = dcrbFlow;
	}

	public String getDcrbFixedassets() {
		return dcrbFixedassets;
	}

	public void setDcrbFixedassets(String dcrbFixedassets) {
		this.dcrbFixedassets = dcrbFixedassets;
	}

	public String getDcrbProfit() {
		return dcrbProfit;
	}

	public void setDcrbProfit(String dcrbProfit) {
		this.dcrbProfit = dcrbProfit;
	}

	public String getDcrbSymbiosis() {
		return dcrbSymbiosis;
	}

	public void setDcrbSymbiosis(String dcrbSymbiosis) {
		this.dcrbSymbiosis = dcrbSymbiosis;
	}

	public String getDcrbOthertrade() {
		return dcrbOthertrade;
	}

	public void setDcrbOthertrade(String dcrbOthertrade) {
		this.dcrbOthertrade = dcrbOthertrade;
	}

	public String getDcrbMyselftrade() {
		return dcrbMyselftrade;
	}

	public void setDcrbMyselftrade(String dcrbMyselftrade) {
		this.dcrbMyselftrade = dcrbMyselftrade;
	}

	public BigDecimal getResCustsource() {
		return resCustsource;
	}

	public void setResCustsource(BigDecimal resCustsource) {
		this.resCustsource = resCustsource;
	}

	public String getResCasebyperson() {
		return resCasebyperson;
	}

	public void setResCasebyperson(String resCasebyperson) {
		this.resCasebyperson = resCasebyperson;
	}

	public String getResCasebyptel() {
		return resCasebyptel;
	}

	public void setResCasebyptel(String resCasebyptel) {
		this.resCasebyptel = resCasebyptel;
	}

	public String getResFollowup() {
		return resFollowup;
	}

	public void setResFollowup(String resFollowup) {
		this.resFollowup = resFollowup;
	}

	public String getResOtherinfo() {
		return resOtherinfo;
	}

	public void setResOtherinfo(String resOtherinfo) {
		this.resOtherinfo = resOtherinfo;
	}

	public BigDecimal getMarkResult() {
		return markResult;
	}

	public void setMarkResult(BigDecimal markResult) {
		this.markResult = markResult;
	}

	public BigDecimal getMarkRefusereason() {
		return markRefusereason;
	}

	public void setMarkRefusereason(BigDecimal markRefusereason) {
		this.markRefusereason = markRefusereason;
	}

	public BigDecimal getCallSpendtime() {
		return callSpendtime;
	}

	public void setCallSpendtime(BigDecimal callSpendtime) {
		this.callSpendtime = callSpendtime;
	}

	public Timestamp getCallNexttime() {
		return callNexttime;
	}

	public void setCallNexttime(Timestamp callNexttime) {
		this.callNexttime = callNexttime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
