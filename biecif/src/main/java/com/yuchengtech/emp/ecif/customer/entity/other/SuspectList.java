/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.entity.other;

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
@Table(name="APP_SUSPECT_LIST")
public class SuspectList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	疑似客户列表标识	suspect_list_id		SUSPECT_LIST_ID
	@Id
	@GeneratedValue(generator = "SUSPECT_LIST_ID_GENERATOR")
	@GenericGenerator(name = "SUSPECT_LIST_ID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_SUSPECT_LIST_ID") })	
	@Column(name="SUSPECT_LIST_ID", unique=true, nullable=false)
	private Long suspectListId;
//	疑似客户分组标识	suspect_group_id		SUSPECT_GROUP_ID
	@Column(name="SUSPECT_GROUP_ID")
	private Long suspectGroupId;
//	客户标识	cust_id	BIGINT	CUST_ID
	@Column(name="CUST_ID")
	private Long custId;
//	客户标识保留标志	reserve_flag	CHAR(18)	RESERVE_FLAG
	@Column(name="RESERVE_FLAG", length=18)
	private String reserveFlag;	
	
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
	
	//客户创建时间、客户号、客户类型(个人、机构)、客户名称
	//cust_no VARCHAR(32) ;
	@Column(name="CUST_NO")
	private String custNo;
	//cust_type VARCHAR(20) ;
	@Column(name="CUST_TYPE")
	private String custType;
	//create_date DATE ;
	@Column(name="CREATE_DATE")
	private Date createDate;
	//name VARCHAR(70) ;
	@Column(name="CUST_NAME")
	private String name;
	
	public Long getSuspectListId() {
		return suspectListId;
	}
	public void setSuspectListId(Long suspectListId) {
		this.suspectListId = suspectListId;
	}
	public Long getSuspectGroupId() {
		return suspectGroupId;
	}
	public void setSuspectGroupId(Long suspectGroupId) {
		this.suspectGroupId = suspectGroupId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getReserveFlag() {
		return reserveFlag;
	}
	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
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
	public String getSuspectComfirmOperator() {
		return suspectComfirmOperator;
	}
	public void setSuspectComfirmOperator(String suspectComfirmOperator) {
		this.suspectComfirmOperator = suspectComfirmOperator;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
