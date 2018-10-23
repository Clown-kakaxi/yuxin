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
@Table(name="APP_CUST_SPLIT_RECORD")
public class CustSplitRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	合并客户拆分标识	split_record_id	BIGINT	SPLIT_RECORD_ID
	@Id
	@GeneratedValue(generator = "SPLIT_RECORD_ID_GENERATOR")
	@GenericGenerator(name = "SPLIT_RECORD_ID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_SPLIT_RECORD_ID") })	
	@Column(name="SPLIT_RECORD_ID", unique=true, nullable=false)
	private Long splitRecordId;
//	拆分编码	split_code	VARCHAR(20)	SPLIT_CODE
	@Column(name="SPLIT_CODE", length=20)
	private String splitCode;
//	保留客户	reserve_cust_id	BIGINT	RESERVE_CUST_ID
	@Column(name="RESERVE_CUST_ID")
	private Long reserveCustId;
//	被合并客户	merged_cust_id	BIGINT	MERGED_CUST_ID
	@Column(name="MERGED_CUST_ID")
	private Long mergedCustId;
	
//	保留客户号	reserve_cust_no	BIGINT	RESERVE_CUST_ID
	@Column(name="RESERVE_CUST_NO", length=32)
	private String reserveCustNo;
//	被合并客户号	merged_cust_no	BIGINT	MERGED_CUST_ID
	@Column(name="MERGED_CUST_NO", length=32)
	private String mergedCustNo;
	
//	拆分提交人	split_operator	VARCHAR(20)	SPLIT_OPERATOR
	@Column(name="SPLIT_OPERATOR", length=20)
	private String splitOperator;
//	拆分提交时间	split_oper_time	DATE	SPLIT_OPER_TIME
	@Column(name="SPLIT_OPER_TIME")
	private Timestamp splitOperTimeDate;
//	导入人员	import_operator	VARCHAR(20)	IMPORT_OPERATOR
	@Column(name="IMPORT_OPERATOR", length=20)
	private String importOperator;
//	导入时间	import_oper_time	DATE	IMPORT_OPER_TIME
	@Column(name="IMPORT_OPER_TIME")
	private Timestamp importOperTime;
//	拆分状态	split_stat	CHAR(1)	SPLIT_STAT
	@Column(name="SPLIT_STAT", length=1)
	private String splitStat;
	
//	SPLIT_DESC
//	SPLIT_OPERBRC
//	IMPORT_OPERBRC
	@Column(name="SPLIT_DESC")
	private String splitDesc;
	@Column(name="SPLIT_OPERBRC")
	private String splitOperBrc;
	@Column(name="IMPORT_OPERBRC")
	private String importOperBrc;
	
	public Long getSplitRecordId() {
		return splitRecordId;
	}
	public void setSplitRecordId(Long splitRecordId) {
		this.splitRecordId = splitRecordId;
	}
	public String getSplitCode() {
		return splitCode;
	}
	public void setSplitCode(String splitCode) {
		this.splitCode = splitCode;
	}
	public Long getReserveCustId() {
		return reserveCustId;
	}
	public void setReserveCustId(Long reserveCustId) {
		this.reserveCustId = reserveCustId;
	}
	public Long getMergedCustId() {
		return mergedCustId;
	}
	public void setMergedCustId(Long mergedCustId) {
		this.mergedCustId = mergedCustId;
	}
	public String getSplitOperator() {
		return splitOperator;
	}
	public void setSplitOperator(String splitOperator) {
		this.splitOperator = splitOperator;
	}
	public Timestamp getSplitOperTimeDate() {
		return splitOperTimeDate;
	}
	public void setSplitOperTimeDate(Timestamp splitOperTimeDate) {
		this.splitOperTimeDate = splitOperTimeDate;
	}
	public String getImportOperator() {
		return importOperator;
	}
	public void setImportOperator(String importOperator) {
		this.importOperator = importOperator;
	}
	public Timestamp getImportOperTime() {
		return importOperTime;
	}
	public void setImportOperTime(Timestamp importOperTime) {
		this.importOperTime = importOperTime;
	}
	public String getSplitStat() {
		return splitStat;
	}
	public void setSplitStat(String splitStat) {
		this.splitStat = splitStat;
	}
	public String getReserveCustNo() {
		return reserveCustNo;
	}
	public void setReserveCustNo(String reserveCustNo) {
		this.reserveCustNo = reserveCustNo;
	}
	public String getMergedCustNo() {
		return mergedCustNo;
	}
	public void setMergedCustNo(String mergedCustNo) {
		this.mergedCustNo = mergedCustNo;
	}
	public String getSplitDesc() {
		return splitDesc;
	}
	public void setSplitDesc(String splitDesc) {
		this.splitDesc = splitDesc;
	}
	public String getSplitOperBrc() {
		return splitOperBrc;
	}
	public void setSplitOperBrc(String splitOperBrc) {
		this.splitOperBrc = splitOperBrc;
	}
	public String getImportOperBrc() {
		return importOperBrc;
	}
	public void setImportOperBrc(String importOperBrc) {
		this.importOperBrc = importOperBrc;
	}
}
