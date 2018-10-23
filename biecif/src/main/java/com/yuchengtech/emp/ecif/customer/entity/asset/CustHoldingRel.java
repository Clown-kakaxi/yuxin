/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

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
@Table(name="M_REL_CUST_HOLDING")
public class CustHoldingRel implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	CUST_HOLDING_REL_ID BIGINT NOT NULL,
	@Id
	@Column(name="CUST_HOLDING_REL_ID", unique=true, nullable=false)
	private Long custHoldingRelId;
//  CUST_ID BIGINT,
	@Column(name="CUST_ID")
	private Long custId;
//  HOLDING_ID BIGINT,
	@Column(name="HOLDING_ID")
	private Long holdingId;
//  CUST_HOLD_SEQ VARCHAR(10),
	@Column(name="CUST_HOLD_SEQ", length=10)
	private String custHoldSeq;
//  LAST_UPDATE_SYS VARCHAR(20),
	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;
//  LAST_UPDATE_USER VARCHAR(20),
	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;
//  LAST_UPDATE_TM TIMESTAMP,
	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
//  TX_SEQ_NO VARCHAR(32),
	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustHoldingRelId() {
		return custHoldingRelId;
	}
	public void setCustHoldingRelId(Long custHoldingRelId) {
		this.custHoldingRelId = custHoldingRelId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getHoldingId() {
		return holdingId;
	}
	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}
	public String getCustHoldSeq() {
		return custHoldSeq;
	}
	public void setCustHoldSeq(String custHoldSeq) {
		this.custHoldSeq = custHoldSeq;
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

}
