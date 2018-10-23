package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:IdentityVerify的实体类
 * Description: 
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name="M_CI_IDENTITYVERIFY")
public class IdentityVerify implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="VERIFY_STAT",length=1)
	private String verifyStat;
	
	@Column(name="VERIFY_RESULT",length=20)
	private String verifyResult;
	
	@Column(name="REASON",length=80)
	private String reason;
	
	@Column(name="DEAL_WAY",length=80)
	private String dealway;
	
	@Column(name="VERIFY_BRANCH_NO",length=9)
	private String verifyBrc;
	
	@Column(name="VERIFY_TELLER_NO",length=20)
	private String verifyTeller;
	
	@Column(name="VERIFY_DATE",length=10)
	private String verifyDate;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getVerifyStat() {
		return verifyStat;
	}

	public void setVerifyStat(String verifyStat) {
		this.verifyStat = verifyStat;
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDealway() {
		return dealway;
	}

	public void setDealway(String dealway) {
		this.dealway = dealway;
	}

	public String getVerifyBrc() {
		return verifyBrc;
	}

	public void setVerifyBrc(String verifyBrc) {
		this.verifyBrc = verifyBrc;
	}

	public String getVerifyTeller() {
		return verifyTeller;
	}

	public void setVerifyTeller(String verifyTeller) {
		this.verifyTeller = verifyTeller;
	}

	public String getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}
	
}
