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
 * Title:OrgOtherInfo的实体类
 * Description: 机构类客户其他信息
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
@Table(name = "ORGOTHERINFO")
public class OrgOtherInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORG_OTHER_INFO_ID", unique = true, nullable = false)
	private Long orgOtherInfoId;
	
	@Column(name = "CUST_ID")
	private Long custId;
	
	@Column(name = "EBANK_REG_NO", length = 20)
	private String ebankRegNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOrgOtherInfoId() {
		return orgOtherInfoId;
	}

	public void setOrgOtherInfoId(Long orgOtherInfoId) {
		this.orgOtherInfoId = orgOtherInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEbankRegNo() {
		return ebankRegNo;
	}

	public void setEbankRegNo(String ebankRegNo) {
		this.ebankRegNo = ebankRegNo;
	}
	
}
