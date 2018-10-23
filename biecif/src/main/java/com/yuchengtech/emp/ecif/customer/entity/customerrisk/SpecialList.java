package com.yuchengtech.emp.ecif.customer.entity.customerrisk;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
@Table(name="M_CI_SPECIALLIST")
public class SpecialList implements Serializable {
	private static final long serialVersionUID = 1L;
	
//	特殊名单标识	special_list_id	BIGINT
	@Id
	@GeneratedValue(generator = "SPECIAL_LIST_ID_GENERATOR")
	@GenericGenerator(name = "SPECIAL_LIST_ID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_SPECIAL_LIST_ID") })	
	@Column(name="SPECIAL_LIST_ID", unique=true, nullable=false)
	private Long specialListId;
//	客户标识	cust_id	BIGINT
	@Column(name="CUST_ID")
	private Long custId;
//	特殊名单类型	special_list_type	VARCHAR(20)
	@Column(name="SPECIAL_LIST_TYPE", length=20)
	private String specialListType;
//	特殊名单标志	special_list_flag	CHAR(1)
	@Column(name="SPECIAL_LIST_FLAG", length=1)
	private String specialListFlag;
//	证件类型	ident_type	VARCHAR(20)
	@Column(name="IDENT_TYPE", length=20)
	private String identType;
//	证件号码	ident_no	VARCHAR(40)
	@Column(name="IDENT_NO", length=40)
	private String identNo;
//	证件户名	ident_cust_name	VARCHAR(80)
	@Column(name="IDENT_CUST_NAME", length=80)
	private String identCustName;
//	列入原因	enter_reason	VARCHAR(18)
	@Column(name="ENTER_REASON", length=18)
	private String enterReason;
//	状态标志	stat_flag	VARCHAR(18)
	@Column(name="STAT_FLAG", length=18)
	private String statFlag;
//	起始日期	start_date	DATE
	@Column(name="START_DATE")
	private Date startDate;
//	结束日期	end_date	DATE
	@Column(name="END_DATE")
	private Date endDate;
//	黑名单类型	SPECIAL_LIST_KIND	VARCHAR(20)
	@Column(name="SPECIAL_LIST_KIND", length=20)
	private String specialListKind;
	
//	LAST_UPDATE_SYS，LAST_UPDATE_USER，LAST_UPDATE_TM，TX_SEQ_NO
//	最后修改系统，修改人，时间等
	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;
	
	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;
	
	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
	
	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;
	
	@Column(name="APPROVAL_FLAG", length=12)
	private String approvalFlag;

    public SpecialList() {
    }

	public Long getSpecialListId() {
		return specialListId;
	}

	public void setSpecialListId(Long specialListId) {
		this.specialListId = specialListId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getSpecialListType() {
		return specialListType;
	}

	public void setSpecialListType(String specialListType) {
		this.specialListType = specialListType;
	}

	public String getSpecialListFlag() {
		return specialListFlag;
	}

	public void setSpecialListFlag(String specialListFlag) {
		this.specialListFlag = specialListFlag;
	}

	public String getIdentType() {
		return identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentNo() {
		return identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentCustName() {
		return identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	public String getEnterReason() {
		return enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	public String getStatFlag() {
		return statFlag;
	}

	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLastUpdateSys() {
		return lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String getSpecialListKind() {
		return specialListKind;
	}

	public void setSpecialListKind(String specialListKind) {
		this.specialListKind = specialListKind;
	}
    
}