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
 * Title: PersonProfile的实体类
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
@Table(name="M_CI_PERSON")
public class PersonProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="GENDER", length=20)
	private String gender;
	
	@Column(name="BIRTHDAY", length=10)
	private String birthday;
	
	@Column(name="BIRTHLOCALE", length=50)
	private String birthLocale;
	
	@Column(name="CITIZENSHIP", length=20)
	private String citizenship;
	
	@Column(name="NATIONALITY", length=20)
	private String nationality;
	
	@Column(name="NATIVEPLACE", length=200)
	private String nativePlace;
	
	@Column(name="HOUSEHOLD", length=20)
	private String houseHold;
	
	
	@Column(name="HUKOU_PLACE", length=60)
	private String hukouPlace;
	
	@Column(name="MARRIAGE", length=20)
	private String marriage;
	
	@Column(name="RESIDENCE", length=20)
	private String residence;
	
	@Column(name="HEALTH", length=20)
	private String health;
	
	@Column(name="RELIGIOUS_BELIEF", length=20)
	private String religiousBelief;
	
	@Column(name="HIGHEST_SCHOOLING", length=20)
	private String highestSchooling;
	
	@Column(name="HIGHEST_DEGREE", length=20)
	private String highestDegree;
	
	@Column(name="GRADUATE_SCHOOL", length=80)
	private String graduateSchool;
	
	@Column(name="MAJOR", length=80)
	private String major;
	
	@Column(name="GRADUATION_TIME", length=10)
	private String graduationTime;
	
	@Column(name="POLITICAL_FACE", length=20)
	private String politicalFace;
	

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
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

	public String getBirthLocale() {
		return birthLocale;
	}

	public void setBirthLocale(String birthLocale) {
		this.birthLocale = birthLocale;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getHouseHold() {
		return houseHold;
	}

	public void setHouseHold(String houseHold) {
		this.houseHold = houseHold;
	}


	public String getHukouPlace() {
		return hukouPlace;
	}

	public void setHukouPlace(String hukouPlace) {
		this.hukouPlace = hukouPlace;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getReligiousBelief() {
		return religiousBelief;
	}

	public void setReligiousBelief(String religiousBelief) {
		this.religiousBelief = religiousBelief;
	}

	public String getHighestSchooling() {
		return highestSchooling;
	}

	public void setHighestSchooling(String highestSchooling) {
		this.highestSchooling = highestSchooling;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(String graduationTime) {
		this.graduationTime = graduationTime;
	}

	public String getPoliticalFace() {
		return politicalFace;
	}

	public void setPoliticalFace(String politicalFace) {
		this.politicalFace = politicalFace;
	}


}
