package com.yuchengtech.emp.ecif.customer.entity.customercontact;

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
 * Title:CustContmethRel的实体类
 * Description: 客户与联系信息关联类
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
@Table(name="CUSTCONTMETHREL")
public class CustContmethRel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_CONTMETH_ID", unique=true, nullable=false)
	private Long custComtmethId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="CONTMETH_ID")
	private Long contmethId;
	
	@Column(name="CUST_CONTMETH_REL_TYPE", length=20)
	private Long custContmethRelType;
	
	@Column(name="CONTMETH_SEQ")
	private Long contmethSeq;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustComtmethId() {
		return custComtmethId;
	}

	public void setCustComtmethId(Long custComtmethId) {
		this.custComtmethId = custComtmethId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getContmethId() {
		return contmethId;
	}

	public void setContmethId(Long contmethId) {
		this.contmethId = contmethId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustContmethRelType() {
		return custContmethRelType;
	}

	public void setCustContmethRelType(Long custContmethRelType) {
		this.custContmethRelType = custContmethRelType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getContmethSeq() {
		return contmethSeq;
	}

	public void setContmethSeq(Long contmethSeq) {
		this.contmethSeq = contmethSeq;
	}
	
}