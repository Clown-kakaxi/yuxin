package com.yuchengtech.bcrm.customer.potentialMkt.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_INTERVIEW_NEW_RECORD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_INTERVIEW_NEW_RECORD")
public class OcrmFInterviewNewRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="OCRM_F_INTERVIEW_NEW_RECORD_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
   	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_INTERVIEW_NEW_RECORD_GENERATOR")
	private String id;
	
	@Temporal( TemporalType.DATE)
	@Column(name="CALL_NEXTTIME")
	private Date callNexttime;

	@Column(name="CALL_SPENDTIME")
	private BigDecimal callSpendtime;

    @Temporal( TemporalType.DATE)
	@Column(name="CALL_TIME")
	private Date callTime;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CUS_ACCOUNTPERSON")
	private String cusAccountperson;

	@Column(name="CUS_BUSISTATUS")
	private BigDecimal cusBusistatus;

	@Column(name="CUS_CNTPEOPLE")
	private BigDecimal cusCntpeople;

	@Column(name="CUS_DOMICILE")
	private String cusDomicile;

	@Column(name="CUS_LEGALPERSON")
	private String cusLegalperson;

	@Column(name="CUS_MAJORPRODUCT")
	private String cusMajorproduct;

	@Column(name="CUS_MAJORRIVAL")
	private String cusMajorrival;

	@Column(name="CUS_NATURE")
	private BigDecimal cusNature;

	@Column(name="CUS_ONMARK")
	private BigDecimal cusOnmark;

	@Column(name="CUS_ONMARKPLACE")
	private BigDecimal cusOnmarkplace;

	@Column(name="CUS_OPERATEPERSON")
	private String cusOperateperson;

	@Column(name="CUS_OWNBUSI")
	private BigDecimal cusOwnbusi;

    @Temporal( TemporalType.DATE)
	@Column(name="CUS_REGTIME")
	private Date cusRegtime;

	@Column(name="DCRB_FIXEDASSETS")
	private String dcrbFixedassets;

	@Column(name="DCRB_FLOW")
	private String dcrbFlow;

	@Column(name="DCRB_MAJORSHOLDER")
	private String dcrbMajorsholder;

	@Column(name="DCRB_MYSELFTRADE")
	private String dcrbMyselftrade;

	@Column(name="DCRB_OTHERTRADE")
	private String dcrbOthertrade;

	@Column(name="DCRB_PROFIT")
	private String dcrbProfit;

	@Column(name="DCRB_SYMBIOSIS")
	private String dcrbSymbiosis;

	@Column(name="INTERVIEWEE_NAME")
	private String intervieweeName;

	@Column(name="INTERVIEWEE_PHONE")
	private String intervieweePhone;

	@Column(name="INTERVIEWEE_POST")
	private String intervieweePost;

	@Column(name="JOIN_PERSON")
	private String joinPerson;

	@Column(name="MARK_REFUSEREASON")
	private BigDecimal markRefusereason;

	@Column(name="MARK_RESULT")
	private BigDecimal markResult;

	private String remark;

	@Column(name="RES_CASEBYPERSON")
	private String resCasebyperson;

	@Column(name="RES_CASEBYPTEL")
	private String resCasebyptel;

	@Column(name="RES_CUSTSOURCE")
	private BigDecimal resCustsource;

	@Column(name="RES_FOLLOWUP")
	private String resFollowup;

	@Column(name="RES_OTHERINFO")
	private String resOtherinfo;

	@Column(name="TASK_NUMBER")
	private String taskNumber;
	
	@Column(name="CUS_OPERATEAGE")
	private String cusOperateage;
	
	@Column(name="CUS_SITEOPERATEAGE")
	private String cusSiteoperateage;
	
	@Column(name="CUS_INCOMETY")
	private Long cusIncomety;
	
	@Column(name="CUS_INCOMELY")
	private Long cusIncomely;
	
	@Column(name="CUS_OPERATEADDR")
	private String cusOperateaddr;
	
	@Column(name="CUS_LEGALPTEL")
	private String cusLegalptel;
	
	@Column(name="CUS_OPERATEPTEL")
	private String cusOperateptel;
	
	@Column(name="CUS_ACCOUNTPTEL")
	private String cusAccountptel;
	
	@Column(name="CUS_OPERATEPWAGE")
	private String cusOperatepwage;
	
	@Column(name="CUS_OPERATEPMAGE")
	private String cusOperatepmage;
	
	@Column(name="IF_OWNBANKCUST")
	private Long ifOwnbankcust;
	
    @Temporal( TemporalType.DATE)
	@Column(name="RES_CUSTSOURCEDATE")
	private Date resCustsourcedate;
    
    @Temporal( TemporalType.DATE)
   	@Column(name="CUS_SITEOPERATETIME")
   	private Date cusSiteoperatetime;  //现址经营开始时间
    
    @Column(name="JOIN_PERSON_ID")
	private String joinPersonId;//本次参加人员编号

    public OcrmFInterviewNewRecord() {
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCusAccountperson() {
		return this.cusAccountperson;
	}

	public void setCusAccountperson(String cusAccountperson) {
		this.cusAccountperson = cusAccountperson;
	}

	public BigDecimal getCusBusistatus() {
		return this.cusBusistatus;
	}

	public void setCusBusistatus(BigDecimal cusBusistatus) {
		this.cusBusistatus = cusBusistatus;
	}

	public BigDecimal getCusCntpeople() {
		return this.cusCntpeople;
	}

	public void setCusCntpeople(BigDecimal cusCntpeople) {
		this.cusCntpeople = cusCntpeople;
	}

	public String getCusDomicile() {
		return this.cusDomicile;
	}

	public void setCusDomicile(String cusDomicile) {
		this.cusDomicile = cusDomicile;
	}

	public String getCusLegalperson() {
		return this.cusLegalperson;
	}

	public void setCusLegalperson(String cusLegalperson) {
		this.cusLegalperson = cusLegalperson;
	}

	public String getCusMajorproduct() {
		return this.cusMajorproduct;
	}

	public void setCusMajorproduct(String cusMajorproduct) {
		this.cusMajorproduct = cusMajorproduct;
	}

	public String getCusMajorrival() {
		return this.cusMajorrival;
	}

	public void setCusMajorrival(String cusMajorrival) {
		this.cusMajorrival = cusMajorrival;
	}

	public BigDecimal getCusNature() {
		return this.cusNature;
	}

	public void setCusNature(BigDecimal cusNature) {
		this.cusNature = cusNature;
	}

	public BigDecimal getCusOnmark() {
		return this.cusOnmark;
	}

	public void setCusOnmark(BigDecimal cusOnmark) {
		this.cusOnmark = cusOnmark;
	}

	public BigDecimal getCusOnmarkplace() {
		return this.cusOnmarkplace;
	}

	public void setCusOnmarkplace(BigDecimal cusOnmarkplace) {
		this.cusOnmarkplace = cusOnmarkplace;
	}

	public String getCusOperateperson() {
		return this.cusOperateperson;
	}

	public void setCusOperateperson(String cusOperateperson) {
		this.cusOperateperson = cusOperateperson;
	}

	public BigDecimal getCusOwnbusi() {
		return this.cusOwnbusi;
	}

	public void setCusOwnbusi(BigDecimal cusOwnbusi) {
		this.cusOwnbusi = cusOwnbusi;
	}

	public Date getCusRegtime() {
		return this.cusRegtime;
	}

	public void setCusRegtime(Date cusRegtime) {
		this.cusRegtime = cusRegtime;
	}

	public String getDcrbFixedassets() {
		return this.dcrbFixedassets;
	}

	public void setDcrbFixedassets(String dcrbFixedassets) {
		this.dcrbFixedassets = dcrbFixedassets;
	}

	public String getDcrbFlow() {
		return this.dcrbFlow;
	}

	public void setDcrbFlow(String dcrbFlow) {
		this.dcrbFlow = dcrbFlow;
	}

	public String getDcrbMajorsholder() {
		return this.dcrbMajorsholder;
	}

	public void setDcrbMajorsholder(String dcrbMajorsholder) {
		this.dcrbMajorsholder = dcrbMajorsholder;
	}

	public String getDcrbMyselftrade() {
		return this.dcrbMyselftrade;
	}

	public void setDcrbMyselftrade(String dcrbMyselftrade) {
		this.dcrbMyselftrade = dcrbMyselftrade;
	}

	public String getDcrbOthertrade() {
		return this.dcrbOthertrade;
	}

	public void setDcrbOthertrade(String dcrbOthertrade) {
		this.dcrbOthertrade = dcrbOthertrade;
	}

	public String getDcrbProfit() {
		return this.dcrbProfit;
	}

	public void setDcrbProfit(String dcrbProfit) {
		this.dcrbProfit = dcrbProfit;
	}

	public String getDcrbSymbiosis() {
		return this.dcrbSymbiosis;
	}

	public void setDcrbSymbiosis(String dcrbSymbiosis) {
		this.dcrbSymbiosis = dcrbSymbiosis;
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

	public String getJoinPerson() {
		return this.joinPerson;
	}

	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResCasebyperson() {
		return this.resCasebyperson;
	}

	public void setResCasebyperson(String resCasebyperson) {
		this.resCasebyperson = resCasebyperson;
	}

	public String getResCasebyptel() {
		return this.resCasebyptel;
	}

	public void setResCasebyptel(String resCasebyptel) {
		this.resCasebyptel = resCasebyptel;
	}

	public BigDecimal getResCustsource() {
		return this.resCustsource;
	}

	public void setResCustsource(BigDecimal resCustsource) {
		this.resCustsource = resCustsource;
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

	public String getTaskNumber() {
		return this.taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getCusOperateage() {
		return cusOperateage;
	}

	public void setCusOperateage(String cusOperateage) {
		this.cusOperateage = cusOperateage;
	}

	public String getCusSiteoperateage() {
		return cusSiteoperateage;
	}

	public void setCusSiteoperateage(String cusSiteoperateage) {
		this.cusSiteoperateage = cusSiteoperateage;
	}

	public Long getCusIncomety() {
		return cusIncomety;
	}

	public void setCusIncomety(Long cusIncomety) {
		this.cusIncomety = cusIncomety;
	}

	public Long getCusIncomely() {
		return cusIncomely;
	}

	public void setCusIncomely(Long cusIncomely) {
		this.cusIncomely = cusIncomely;
	}

	public String getCusOperateaddr() {
		return cusOperateaddr;
	}

	public void setCusOperateaddr(String cusOperateaddr) {
		this.cusOperateaddr = cusOperateaddr;
	}

	public String getCusLegalptel() {
		return cusLegalptel;
	}

	public void setCusLegalptel(String cusLegalptel) {
		this.cusLegalptel = cusLegalptel;
	}

	public String getCusOperateptel() {
		return cusOperateptel;
	}

	public void setCusOperateptel(String cusOperateptel) {
		this.cusOperateptel = cusOperateptel;
	}

	public String getCusAccountptel() {
		return cusAccountptel;
	}

	public void setCusAccountptel(String cusAccountptel) {
		this.cusAccountptel = cusAccountptel;
	}

	public String getCusOperatepwage() {
		return cusOperatepwage;
	}

	public void setCusOperatepwage(String cusOperatepwage) {
		this.cusOperatepwage = cusOperatepwage;
	}

	public String getCusOperatepmage() {
		return cusOperatepmage;
	}

	public void setCusOperatepmage(String cusOperatepmage) {
		this.cusOperatepmage = cusOperatepmage;
	}

	public Long getIfOwnbankcust() {
		return ifOwnbankcust;
	}

	public void setIfOwnbankcust(Long ifOwnbankcust) {
		this.ifOwnbankcust = ifOwnbankcust;
	}

	public Date getResCustsourcedate() {
		return resCustsourcedate;
	}

	public void setResCustsourcedate(Date resCustsourcedate) {
		this.resCustsourcedate = resCustsourcedate;
	}

	public Date getCusSiteoperatetime() {
		return cusSiteoperatetime;
	}

	public void setCusSiteoperatetime(Date cusSiteoperatetime) {
		this.cusSiteoperatetime = cusSiteoperatetime;
	}

	public String getJoinPersonId() {
		return joinPersonId;
	}

	public void setJoinPersonId(String joinPersonId) {
		this.joinPersonId = joinPersonId;
	}

}