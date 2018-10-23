package com.yuchengtech.emp.ecif.customer.entity.customercontact;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * <pre>
 * Title:CustAddressRel的实体类
 * Description: 客户与地址信息的关联类
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
@Table(name="CUSTADDRESSREL")
public class CustAddressRel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ADDR_REL_ID", unique=true, nullable=false)
	private Long custAddrRelId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="ADDR_ID")
	private Long addrId;
	
	@Column(name="CUST_ADDR_REL_TYPE", length=20)
	private String custAddrRelType;

	public Long getCustAddrRelId() {
		return custAddrRelId;
	}

	public void setCustAddrRelId(Long custAddrRelId) {
		this.custAddrRelId = custAddrRelId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getCustAddrRelType() {
		return custAddrRelType;
	}

	public void setCustAddrRelType(String custAddrRelType) {
		this.custAddrRelType = custAddrRelType;
	}
	
}
