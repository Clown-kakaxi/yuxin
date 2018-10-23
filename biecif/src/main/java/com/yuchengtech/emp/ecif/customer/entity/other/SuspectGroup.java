/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.entity.other;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name="APP_SUSPECT_GROUP")
public class SuspectGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	疑似客户分组标识	suspect_group_id	SUSPECT_GROUP_ID
	@Id
	@GeneratedValue(generator = "SUSPECT_GROUP_ID_GENERATOR")
	@GenericGenerator(name = "SUSPECT_GROUP_ID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_SUSPECT_GROUP_ID") })	
	@Column(name="SUSPECT_GROUP_ID", unique=true, nullable=false)
	private Long suspectGroupId;
//	疑似客户分组描述	suspect_group_desc	CHAR(18)	SUSPECT_GROUP_DESC
	@Column(name="SUSPECT_GROUP_DESC", length=18)
	private String suspectGroupDesc;
//	疑似信息数据日期	suspect_data_date	CHAR(18)	SUSPECT_DATA_DATE
	@Column(name="SUSPECT_DATA_DATE", length=18)
	private String suspectDataDate;
//	疑似信息生成日期	suspect_search_date	CHAR(18)	SUSPECT_SEARCH_DATE
	@Column(name="SUSPECT_SEARCH_DATE", length=18)
	private String suspectSearchDate;
//	疑似导出处理标志	suspect_export_flag	CHAR(18)	SUSPECT_EXPORT_FLAG
	@Column(name="SUSPECT_EXPORT_FLAG", length=18)
	private String suspectExportFlag;
	
//	归并后客户标识	reserve_cust_id	CHAR(18)	RESERVE_CUST_ID
	@Column(name="RESERVE_CUST_ID", length=18)
	private String reserveCustId;
	
//	疑似确认标志	suspect_confirm_flag	CHAR(18)	SUSPECT_CONFIRM_FLAG
	@Column(name="SUSPECT_CONFIRM_FLAG", length=18)
	private String suspectComfirmFlag;
//	疑似确认结果	suspect_confirm_result	CHAR(18)	SUSPECT_CONFIRM_RESULT
	@Column(name="SUSPECT_CONFIRM_RESULT", length=18)
	private String suspectComfirmResult;
//	疑似确认人	SUSPECT_CONFIRM_OPERATOR	varCHAR(20)
	@Column(name="SUSPECT_CONFIRM_OPERATOR", length=20)
	private String suspectComfirmOperator;
//	合并处理标志	merge_deal_flag	CHAR(18)	MERGE_DEAL_FLAG
	@Column(name="MERGE_DEAL_FLAG")
	private String mergeDealFlag;
//	合并处理日期	merge_deal_date	CHAR(18)	MERGE_DEAL_DATE
	@Column(name="MERGE_DEAL_DATE")
	private Timestamp mergeDealDate;
//	列入原因	ENTER_REASON	CHAR(200)
	@Column(name="ENTER_REASON", length=80)
	private String enterReason;
	
////	审批人	approval_operator	VARCHAR(20)	APPROVAL_OPERATOR
//	@Column(name="APPROVAL_OPERATOR", length=20)
//	private String approvalOperator;
////	审批时间	approval_time	DATE	APPROVAL_TIME
//	@Column(name="APPROVAL_TIME")
//	private Timestamp approvalTime;
////	审批状态	approval_stat	VARCHAR(2)	APPROVAL_STAT
//	@Column(name="APPROVAL_STAT", length=2)
//	private String approvalStat;
////	审批意见	approval_note	VARCHAR(200)	APPROVAL_NOTE
//	@Column(name="APPROVAL_NOTE", length=200)
//	private String approvalNote;
	
	public Long getSuspectGroupId() {
		return suspectGroupId;
	}
	public void setSuspectGroupId(Long suspectGroupId) {
		this.suspectGroupId = suspectGroupId;
	}
	public String getSuspectGroupDesc() {
		return suspectGroupDesc;
	}
	public void setSuspectGroupDesc(String suspectGroupDesc) {
		this.suspectGroupDesc = suspectGroupDesc;
	}
	public String getSuspectDataDate() {
		return suspectDataDate;
	}
	public void setSuspectDataDate(String suspectDataDate) {
		this.suspectDataDate = suspectDataDate;
	}
	public String getSuspectSearchDate() {
		return suspectSearchDate;
	}
	public void setSuspectSearchDate(String suspectSearchDate) {
		this.suspectSearchDate = suspectSearchDate;
	}
	public String getSuspectExportFlag() {
		return suspectExportFlag;
	}
	public void setSuspectExportFlag(String suspectExportFlag) {
		this.suspectExportFlag = suspectExportFlag;
	}
	public String getSuspectComfirmFlag() {
		return suspectComfirmFlag;
	}
	public void setSuspectComfirmFlag(String suspectComfirmFlag) {
		this.suspectComfirmFlag = suspectComfirmFlag;
	}
	public String getSuspectComfirmResult() {
		return suspectComfirmResult;
	}
	public void setSuspectComfirmResult(String suspectComfirmResult) {
		this.suspectComfirmResult = suspectComfirmResult;
	}
	public String getMergeDealFlag() {
		return mergeDealFlag;
	}
	public void setMergeDealFlag(String mergeDealFlag) {
		this.mergeDealFlag = mergeDealFlag;
	}
	public Timestamp getMergeDealDate() {
		return mergeDealDate;
	}
	public void setMergeDealDate(Timestamp mergeDealDate) {
		this.mergeDealDate = mergeDealDate;
	}
	public String getReserveCustId() {
		return reserveCustId;
	}
	public void setReserveCustId(String reserveCustId) {
		this.reserveCustId = reserveCustId;
	}
	public String getSuspectComfirmOperator() {
		return suspectComfirmOperator;
	}
	public void setSuspectComfirmOperator(String suspectComfirmOperator) {
		this.suspectComfirmOperator = suspectComfirmOperator;
	}
	public String getEnterReason() {
		return enterReason;
	}
	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

}
