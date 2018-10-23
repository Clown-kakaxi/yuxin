package com.yuchengtech.emp.ecif.customer.entity.customerother;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * 
 * <pre>
 * Title:Consinfo的实体类
 * Description: 个体经营户水电信息表
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
@Table(name = "CONSINFO")
public class ConsInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SERIAL_NO", unique = true, nullable = false)
	private Long serialNo;
	
	@Column(name = "CUST_ID")
	private Long custId;
	
	@Column(name = "ACCOUNT_MONTH", length = 20)
	private String accountMonth;
	
	@Column(name = "RESOURCE_TYPE", length = 20)
	private String resourceType;
	
	@Column(name = "CONSUME_AMOUNT")
	private Double consumeAmount;
	
	@Column(name = "CONSUME_SUM")
	private Double consumeSum;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Double getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Double getConsumeSum() {
		return consumeSum;
	}

	public void setConsumeSum(Double consumeSum) {
		this.consumeSum = consumeSum;
	}
	

}