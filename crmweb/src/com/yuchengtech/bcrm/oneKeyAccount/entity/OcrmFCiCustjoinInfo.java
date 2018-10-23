package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * 联名户信息 
 */
@Entity
@Table(name="OCRM_F_CI_CUSTJOIN_INFO")
public class OcrmFCiCustjoinInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//SEQ_CUSTJOIN_INFO
	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUSTJOIN_INFO_ID_GENERATOR", sequenceName="SEQ_CUSTJOIN_INFO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUSTJOIN_INFO_ID_GENERATOR")
	@Column(name="ID")
	private long id;
	
	@Column(name="CITIZENSHIP")
	private String citizenship;

	@Column(name="COUNTRY_OR_REGION")
	private String countryOrRegion;

	@Column(name="CREATE_ORG")
	private String createOrg;

    @Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="GENDER")
	private String gender;
	
	@Column(name="IDENT_NO1")
	private String identNo1;

	@Column(name="IDENT_NO2")
	private String identNo2;

	@Column(name="IDENT_TYPE1")
	private String identType1;

	@Column(name="IDENT_TYPE2")
	private String identType2;

    @Temporal( TemporalType.DATE)
	@Column(name="IDT_EXPIRE_DT1")
	private Date idtExpireDt1;

    @Temporal( TemporalType.DATE)
	@Column(name="IDT_EXPIRE_DT2")
	private Date idtExpireDt2;

	@Column(name="IS_ACT_PERM1")
	private String isActPerm1;

	@Column(name="IS_ACT_PERM2")
	private String isActPerm2;

	@Column(name="JOIN_CUST_ID")
	private String joinCustId;

	@Column(name="PINYIN_NAME")
	private String pinyinName;

	@Column(name="UPDATE_ORG")
	private String updateOrg;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OcrmFCiCustjoinInfo() {
    }

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

	public String getIdentNo1() {
		return this.identNo1;
	}

	public void setIdentNo1(String identNo1) {
		this.identNo1 = identNo1;
	}

	public String getIdentNo2() {
		return this.identNo2;
	}

	public void setIdentNo2(String identNo2) {
		this.identNo2 = identNo2;
	}

	public String getIdentType1() {
		return this.identType1;
	}

	public void setIdentType1(String identType1) {
		this.identType1 = identType1;
	}

	public String getIdentType2() {
		return this.identType2;
	}

	public void setIdentType2(String identType2) {
		this.identType2 = identType2;
	}

	public Date getIdtExpireDt1() {
		return this.idtExpireDt1;
	}

	public void setIdtExpireDt1(Date idtExpireDt1) {
		this.idtExpireDt1 = idtExpireDt1;
	}

	public Date getIdtExpireDt2() {
		return this.idtExpireDt2;
	}

	public void setIdtExpireDt2(Date idtExpireDt2) {
		this.idtExpireDt2 = idtExpireDt2;
	}

	public String getIsActPerm1() {
		return this.isActPerm1;
	}

	public void setIsActPerm1(String isActPerm1) {
		this.isActPerm1 = isActPerm1;
	}

	public String getIsActPerm2() {
		return this.isActPerm2;
	}

	public void setIsActPerm2(String isActPerm2) {
		this.isActPerm2 = isActPerm2;
	}

	public String getJoinCustId() {
		return this.joinCustId;
	}

	public void setJoinCustId(String joinCustId) {
		this.joinCustId = joinCustId;
	}

	public String getPinyinName() {
		return this.pinyinName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	public String getUpdateOrg() {
		return this.updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}