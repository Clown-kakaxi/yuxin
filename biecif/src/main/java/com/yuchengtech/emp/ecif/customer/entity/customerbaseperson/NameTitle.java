package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:NameTitle的实体类
 * Description: 名称称谓
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
@Table(name="M_CI_NAMETITLE")
public class NameTitle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="EN_NAME", length=100)
	private String enName;

	@Column(name="EN_SHORT_NAME", length=80)
	private String enShortName;
	
	@Column(name="OTHER_NAME", length=40)
	private String otherName;
	
	@Column(name="PINYIN_NAME", length=40)
	private String pinYin;
	
	@Column(name="FULL_NAME", length=200)
	private String fullName;
    
	@Column(name="CUST_NAME", length=80)
	private String name;
	
	@Column(name="SHORT_NAME", length=40)
	private String shortName;
	
	@Column(name="CUST_TITLE", length=20)
	private String title;
//	
//	@Column(name="LAST_UPDATE_SYS", length=20)
//	private String lastUpdateSys;
//	
//	@Column(name="LAST_UPDATE_USER", length=20)
//	private String lastUpdateUser;
//	
//	@Column(name="LAST_UPDATE_TM")
//	private String lastUpdateTm;
//	
//	@Column(name="TX_SEQ_NO", length=32)
//	private String txSeqNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnShortName() {
		return enShortName;
	}

	public void setEnShortName(String enShortName) {
		this.enShortName = enShortName;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
}
