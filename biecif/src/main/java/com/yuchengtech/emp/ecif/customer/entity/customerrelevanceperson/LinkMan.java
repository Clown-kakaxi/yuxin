package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceperson;

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
 * Title:LinkMan的实体类
 * Description: 联系人信息
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
@Table(name="M_CI_PER_LINKMAN")
public class LinkMan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="LINKMAN_ID", unique=true, nullable=false)
	private Long linkmanId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="LINKMAN_TYPE",length = 20)
	private String linkmanType;
	
	@Column(name="LINKMAN_NAME",length = 40)
	private String linkmanName;
	
//	@Column(name="LINKMAN_IDENT_TYPE",length = 20)
//	private String linkmanIdentType;
//	
	@Column(name="LINKMAN_NO",length = 40)
	private String linkmanIdentNo;
	
	@Column(name="TEL",length = 20)
	private String tel;
	
	@Column(name="MOBILE",length = 20)
	private String mobile;
	
	@Column(name="ADDR",length = 255)
	private String addr;
	
	@Column(name="EMAIL",length = 40)
	private String email;
	
	@Column(name="GENDER",length = 20)
	private String gender;
	
	@Column(name="BIRTHDAY",length = 10)
	private String birthday;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(Long linkmanId) {
		this.linkmanId = linkmanId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getLinkmanType() {
		return linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}

//	public String getLinkmanIdentType() {
//		return linkmanIdentType;
//	}
//
//	public void setLinkmanIdentType(String linkmanIdentType) {
//		this.linkmanIdentType = linkmanIdentType;
//	}

	public String getLinkmanIdentNo() {
		return linkmanIdentNo;
	}

	public void setLinkmanIdentNo(String linkmanIdentNo) {
		this.linkmanIdentNo = linkmanIdentNo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	
}