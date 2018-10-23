package com.ytec.fubonecif.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HMCiPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "H_M_CI_PERSON")
public class HMCiPerson implements java.io.Serializable {

	// Fields

	private HMCiPersonId id;

	// Constructors

	/** default constructor */
	public HMCiPerson() {
	}

	/** full constructor */
	public HMCiPerson(HMCiPersonId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "custId", column = @Column(name = "CUST_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "perCustType", column = @Column(name = "PER_CUST_TYPE", length = 20)),
			@AttributeOverride(name = "jointCustType", column = @Column(name = "JOINT_CUST_TYPE", length = 20)),
			@AttributeOverride(name = "orgSubType", column = @Column(name = "ORG_SUB_TYPE", length = 20)),
			@AttributeOverride(name = "surName", column = @Column(name = "SUR_NAME", length = 20)),
			@AttributeOverride(name = "personalName", column = @Column(name = "PERSONAL_NAME", length = 50)),
			@AttributeOverride(name = "pinyinName", column = @Column(name = "PINYIN_NAME", length = 80)),
			@AttributeOverride(name = "pinyinAbbr", column = @Column(name = "PINYIN_ABBR", length = 20)),
			@AttributeOverride(name = "personTitle", column = @Column(name = "PERSON_TITLE", length = 20)),
			@AttributeOverride(name = "nickName", column = @Column(name = "NICK_NAME", length = 20)),
			@AttributeOverride(name = "usedName", column = @Column(name = "USED_NAME", length = 70)),
			@AttributeOverride(name = "gender", column = @Column(name = "GENDER", length = 20)),
			@AttributeOverride(name = "birthday", column = @Column(name = "BIRTHDAY", length = 7)),
			@AttributeOverride(name = "birthlocale", column = @Column(name = "BIRTHLOCALE", length = 50)),
			@AttributeOverride(name = "citizenship", column = @Column(name = "CITIZENSHIP", length = 20)),
			@AttributeOverride(name = "nationality", column = @Column(name = "NATIONALITY", length = 20)),
			@AttributeOverride(name = "nativeplace", column = @Column(name = "NATIVEPLACE", length = 200)),
			@AttributeOverride(name = "household", column = @Column(name = "HOUSEHOLD", length = 20)),
			@AttributeOverride(name = "hukouPlace", column = @Column(name = "HUKOU_PLACE", length = 60)),
			@AttributeOverride(name = "marriage", column = @Column(name = "MARRIAGE", length = 20)),
			@AttributeOverride(name = "residence", column = @Column(name = "RESIDENCE", length = 20)),
			@AttributeOverride(name = "health", column = @Column(name = "HEALTH", length = 20)),
			@AttributeOverride(name = "religiousBelief", column = @Column(name = "RELIGIOUS_BELIEF", length = 20)),
			@AttributeOverride(name = "politicalFace", column = @Column(name = "POLITICAL_FACE", length = 20)),
			@AttributeOverride(name = "mobilePhone", column = @Column(name = "MOBILE_PHONE", length = 20)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 40)),
			@AttributeOverride(name = "homepage", column = @Column(name = "HOMEPAGE", length = 100)),
			@AttributeOverride(name = "weibo", column = @Column(name = "WEIBO", length = 100)),
			@AttributeOverride(name = "weixin", column = @Column(name = "WEIXIN", length = 100)),
			@AttributeOverride(name = "qq", column = @Column(name = "QQ", length = 20)),
			@AttributeOverride(name = "starSign", column = @Column(name = "STAR_SIGN", length = 20)),
			@AttributeOverride(name = "homeAddr", column = @Column(name = "HOME_ADDR", length = 200)),
			@AttributeOverride(name = "homeZipcode", column = @Column(name = "HOME_ZIPCODE", length = 20)),
			@AttributeOverride(name = "homeTel", column = @Column(name = "HOME_TEL", length = 20)),
			@AttributeOverride(name = "highestSchooling", column = @Column(name = "HIGHEST_SCHOOLING", length = 20)),
			@AttributeOverride(name = "highestDegree", column = @Column(name = "HIGHEST_DEGREE", length = 20)),
			@AttributeOverride(name = "graduateSchool", column = @Column(name = "GRADUATE_SCHOOL", length = 80)),
			@AttributeOverride(name = "major", column = @Column(name = "MAJOR", length = 80)),
			@AttributeOverride(name = "graduationDate", column = @Column(name = "GRADUATION_DATE", length = 7)),
			@AttributeOverride(name = "careerStat", column = @Column(name = "CAREER_STAT", length = 20)),
			@AttributeOverride(name = "careerType", column = @Column(name = "CAREER_TYPE", length = 20)),
			@AttributeOverride(name = "profession", column = @Column(name = "PROFESSION", length = 40)),
			@AttributeOverride(name = "unitName", column = @Column(name = "UNIT_NAME", length = 200)),
			@AttributeOverride(name = "unitChar", column = @Column(name = "UNIT_CHAR", length = 20)),
			@AttributeOverride(name = "unitAddr", column = @Column(name = "UNIT_ADDR", length = 200)),
			@AttributeOverride(name = "unitZipcode", column = @Column(name = "UNIT_ZIPCODE", length = 32)),
			@AttributeOverride(name = "unitTel", column = @Column(name = "UNIT_TEL", length = 30)),
			@AttributeOverride(name = "unitFex", column = @Column(name = "UNIT_FEX", length = 20)),
			@AttributeOverride(name = "postAddr", column = @Column(name = "POST_ADDR", length = 200)),
			@AttributeOverride(name = "postZipcode", column = @Column(name = "POST_ZIPCODE", length = 20)),
			@AttributeOverride(name = "postPhone", column = @Column(name = "POST_PHONE", length = 20)),
			@AttributeOverride(name = "adminLevel", column = @Column(name = "ADMIN_LEVEL", length = 20)),
			@AttributeOverride(name = "cntName", column = @Column(name = "CNT_NAME", length = 30)),
			@AttributeOverride(name = "duty", column = @Column(name = "DUTY", length = 20)),
			@AttributeOverride(name = "workResult", column = @Column(name = "WORK_RESULT", length = 80)),
			@AttributeOverride(name = "careerStartDate", column = @Column(name = "CAREER_START_DATE", length = 7)),
			@AttributeOverride(name = "annualIncomeScope", column = @Column(name = "ANNUAL_INCOME_SCOPE", length = 20)),
			@AttributeOverride(name = "annualIncome", column = @Column(name = "ANNUAL_INCOME", precision = 17)),
			@AttributeOverride(name = "currCareerStartDate", column = @Column(name = "CURR_CAREER_START_DATE", length = 7)),
			@AttributeOverride(name = "hasQualification", column = @Column(name = "HAS_QUALIFICATION", length = 1)),
			@AttributeOverride(name = "qualification", column = @Column(name = "QUALIFICATION", length = 40)),
			@AttributeOverride(name = "careerTitle", column = @Column(name = "CAREER_TITLE", length = 20)),
			@AttributeOverride(name = "holdStockAmt", column = @Column(name = "HOLD_STOCK_AMT", precision = 17)),
			@AttributeOverride(name = "bankDuty", column = @Column(name = "BANK_DUTY", length = 20)),
			@AttributeOverride(name = "salaryAcctBank", column = @Column(name = "SALARY_ACCT_BANK", length = 80)),
			@AttributeOverride(name = "salaryAcctNo", column = @Column(name = "SALARY_ACCT_NO", length = 32)),
			@AttributeOverride(name = "loanCardNo", column = @Column(name = "LOAN_CARD_NO", length = 32)),
			@AttributeOverride(name = "holdAcct", column = @Column(name = "HOLD_ACCT", length = 20)),
			@AttributeOverride(name = "holdCard", column = @Column(name = "HOLD_CARD", length = 20)),
			@AttributeOverride(name = "resume", column = @Column(name = "RESUME", length = 500)),
			@AttributeOverride(name = "usaTaxIdenNo", column = @Column(name = "USA_TAX_IDEN_NO", length = 32)),
			@AttributeOverride(name = "lastDealingsDesc", column = @Column(name = "LAST_DEALINGS_DESC", length = 200)),
			@AttributeOverride(name = "remark", column = @Column(name = "REMARK", length = 200)),
			@AttributeOverride(name = "lastUpdateSys", column = @Column(name = "LAST_UPDATE_SYS", length = 20)),
			@AttributeOverride(name = "lastUpdateUser", column = @Column(name = "LAST_UPDATE_USER", length = 20)),
			@AttributeOverride(name = "lastUpdateTm", column = @Column(name = "LAST_UPDATE_TM")),
			@AttributeOverride(name = "txSeqNo", column = @Column(name = "TX_SEQ_NO", length = 32)),
			@AttributeOverride(name = "hisOperSys", column = @Column(name = "HIS_OPER_SYS", length = 20)),
			@AttributeOverride(name = "hisOperType", column = @Column(name = "HIS_OPER_TYPE", length = 2)),
			@AttributeOverride(name = "hisOperTime", column = @Column(name = "HIS_OPER_TIME", nullable = false)),
			@AttributeOverride(name = "hisDataDate", column = @Column(name = "HIS_DATA_DATE", length = 10)) })
	public HMCiPersonId getId() {
		return this.id;
	}

	public void setId(HMCiPersonId id) {
		this.id = id;
	}

}