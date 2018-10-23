package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:Person的实体类
 * Description: 个人信息
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
@Table(name="PERSON")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="PER_CUST_TYPE", length=20)
	private String perCustType;
	
	@Column(name="PER_CUST_STAT", length=20)
	private String perCustStat;
	
	@Column(name="PER_CUST_CHAR", length=20)
	private String perCustChar;
	
	@Column(name="PER_CUST_GROUP", length=20)
	private String perCustGroup;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getPerCustType() {
		return perCustType;
	}

	public void setPerCustType(String perCustType) {
		this.perCustType = perCustType;
	}

	public String getPerCustStat() {
		return perCustStat;
	}

	public void setPerCustStat(String perCustStat) {
		this.perCustStat = perCustStat;
	}

	public String getPerCustChar() {
		return perCustChar;
	}

	public void setPerCustChar(String perCustChar) {
		this.perCustChar = perCustChar;
	}

	public String getPerCustGroup() {
		return perCustGroup;
	}

	public void setPerCustGroup(String perCustGroup) {
		this.perCustGroup = perCustGroup;
	}
}
