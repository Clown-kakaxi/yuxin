/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.entity.other;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="ECIF_LOG")
public class EcifLog implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ECIF_LOG_ID BIGINT,
	@Id
	@Column(name="ECIF_LOG_ID", unique=true, nullable=false)
	private Long ecifLogId;
    //ECIF_LOG_TYPE VARCHAR(2),
	@Column(name="ECIF_LOG_TYPE", length=2)
	private String ecifLogType;	
    //ECIF_LOG_OPER VARCHAR(20),
	@Column(name="ECIF_LOG_OPER", length=20)
	private String ecifLogOper;	
    //ECIF_LOG_DATE DATE,
	@Column(name="ECIF_LOG_DATE")
	private Date ecifLogDate;	
    //ECIF_LOG_MARK VARCHAR(500),
	@Column(name="ECIF_LOG_MARK", length=500)
	private String ecifLogMark;	
    //ECIF_LOG_TIME TIMESTAMP,
	@Column(name="ECIF_LOG_TIME")
	private Timestamp ecifLogTime;	
    //ECIF_LOG_FLAG VARCHAR(2)
	@Column(name="ECIF_LOG_FLAG", length=2)
	private String ecifLogFlag;
	
	//
	public Long getEcifLogId() {
		return ecifLogId;
	}
	public void setEcifLogId(Long ecifLogId) {
		this.ecifLogId = ecifLogId;
	}
	public String getEcifLogType() {
		return ecifLogType;
	}
	public void setEcifLogType(String ecifLogType) {
		this.ecifLogType = ecifLogType;
	}
	public String getEcifLogOper() {
		return ecifLogOper;
	}
	public void setEcifLogOper(String ecifLogOper) {
		this.ecifLogOper = ecifLogOper;
	}
	public Date getEcifLogDate() {
		return ecifLogDate;
	}
	public void setEcifLogDate(Date ecifLogDate) {
		this.ecifLogDate = ecifLogDate;
	}
	public String getEcifLogMark() {
		return ecifLogMark;
	}
	public void setEcifLogMark(String ecifLogMark) {
		this.ecifLogMark = ecifLogMark;
	}
	public Timestamp getEcifLogTime() {
		return ecifLogTime;
	}
	public void setEcifLogTime(Timestamp ecifLogTime) {
		this.ecifLogTime = ecifLogTime;
	}
	public String getEcifLogFlag() {
		return ecifLogFlag;
	}
	public void setEcifLogFlag(String ecifLogFlag) {
		this.ecifLogFlag = ecifLogFlag;
	}	
	
}
