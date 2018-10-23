package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * 
 * @description : 反洗钱定期审核信息表
 *
 * @author : zhaolong
 * @date : 2016-1-5 上午11:46:40
 */
@Entity
@Table(name="ACRM_A_ANTI_DQSH_INFO")
public class AcrmAAntiDqshInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CUST_ID")
	private String custId;
	
	@Column(name="DQSH001")
	private String dqsh001;
	
	@Column(name="DQSH002")
	private String dqsh002;
	
	@Column(name="DQSH003")
	private String dqsh003;
	
	@Column(name="DQSH004")
	private String dqsh004;

	@Column(name="DQSH005")
	private String dqsh005;
	
	@Column(name="DQSH006")
	private String dqsh006;

	@Column(name="DQSH007")
	private String dqsh007;

	@Column(name="DQSH008")
	private String dqsh008;

	@Column(name="DQSH009")
	private String dqsh009;

	@Column(name="DQSH010")
	private String dqsh010;

	@Column(name="DQSH011")
	private String dqsh011;

	@Column(name="DQSH012")
	private String dqsh012;

	@Column(name="DQSH013")
	private String dqsh013;

	@Column(name="DQSH014")
	private String dqsh014;

	@Column(name="DQSH015")
	private String dqsh015;
	
	@Column(name="DQSH016")
	private String dqsh016;
	

	@Column(name="DQSH017")
	private String dqsh017;

	@Column(name="DQSH018")
	private String dqsh018;

	@Column(name="DQSH019")
	private String dqsh019;

	@Column(name="DQSH020")
	private String dqsh020;

	@Column(name="DQSH021")
	private String dqsh021;

	@Column(name="DQSH022")
	private String dqsh022;

	@Column(name="DQSH023")
	private String dqsh023;

	@Column(name="DQSH024")
	private String dqsh024;

	@Column(name="DQSH025")
	private String dqsh025;

	@Column(name="DQSH026")
	private String dqsh026;

	@Column(name="DQSH027")
	private String dqsh027;

	@Column(name="DQSH028")
	private String dqsh028;

	@Column(name="DQSH029")
	private String dqsh029;

	@Column(name="DQSH030")
	private String dqsh030;
	
	@Column(name="DQSH031")
	private String dqsh031;
	
	@Column(name="DQSH032")
	private String dqsh032;

	@Column(name="DQSH033")
	private String dqsh033;

	@Column(name="DQSH034")
	private String dqsh034;

	@Column(name="DQSH035")
	private String dqsh035;

	@Column(name="DQSH036")
	private String dqsh036;
	@Column(name="DQSH037")
	private String dqsh037;
	@Column(name="DQSH038")
	private String dqsh038;
	@Transient 
	private String custName;
	//自己用的属性
	@Transient 
	private Date checkEndDate;
	
	@Transient 
	private String checkResult;

	@Transient
	private String checkRq;

	@Transient
	private Date checkStartDate;

	@Transient
	private String checkUser;

	@Transient
	private String custGradeCheck;

	@Transient
	private String custGrade;// 对应原始风险等级custGradeOld
	@Transient
	private String custType;// 获取客户的类型

	@Transient
	private String custGradeType;
	@Transient
	private String instanceid;

	@Transient
	private String instruction;
	@Transient
	private String gradeId;
	
	/**
	 * ACRM_F_SYS_CUST_FXQ_INDEX 表的参数
	 */
/*	@Transient
	private String fxq006;
*/
	@Transient
	private String fxqCode;
	
	@Transient
	private String fxq007;

	@Transient
	private String fxq008;

	@Transient
	private String fxq009;

	@Transient
	private String fxq010;

	@Transient
	private String fxq011;

	@Transient
	private String fxq012;

	@Transient
	private String fxq013;

	@Transient
	private String fxq014;

	@Transient
	private String fxq015;

	@Transient
	private String fxq016;

	@Transient
	private String fxq021;

	@Transient
	private String fxq022;

	@Transient
	private String fxq023;

	@Transient
	private String fxq024;

	@Transient
	private String fxq025;
	@Transient
	private String flag;
	
	
	
	public AcrmAAntiDqshInfo() {
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDqsh001() {
		return this.dqsh001;
	}

	public void setDqsh001(String dqsh001) {
		this.dqsh001 = dqsh001;
	}

	public String getDqsh002() {
		return this.dqsh002;
	}

	public void setDqsh002(String dqsh002) {
		this.dqsh002 = dqsh002;
	}

	public String getDqsh003() {
		return this.dqsh003;
	}

	public void setDqsh003(String dqsh003) {
		this.dqsh003 = dqsh003;
	}

	public String getDqsh004() {
		return this.dqsh004;
	}

	public void setDqsh004(String dqsh004) {
		this.dqsh004 = dqsh004;
	}

	public String getDqsh005() {
		return this.dqsh005;
	}

	public void setDqsh005(String dqsh005) {
		this.dqsh005 = dqsh005;
	}

	public String getDqsh006() {
		return this.dqsh006;
	}

	public void setDqsh006(String dqsh006) {
		this.dqsh006 = dqsh006;
	}

	public String getDqsh007() {
		return this.dqsh007;
	}

	public void setDqsh007(String dqsh007) {
		this.dqsh007 = dqsh007;
	}

	public String getDqsh008() {
		return this.dqsh008;
	}

	public void setDqsh008(String dqsh008) {
		this.dqsh008 = dqsh008;
	}

	public String getDqsh009() {
		return this.dqsh009;
	}

	public void setDqsh009(String dqsh009) {
		this.dqsh009 = dqsh009;
	}

	public String getDqsh010() {
		return this.dqsh010;
	}

	public void setDqsh010(String dqsh010) {
		this.dqsh010 = dqsh010;
	}

	public String getDqsh011() {
		return this.dqsh011;
	}

	public void setDqsh011(String dqsh011) {
		this.dqsh011 = dqsh011;
	}

	public String getDqsh012() {
		return this.dqsh012;
	}

	public void setDqsh012(String dqsh012) {
		this.dqsh012 = dqsh012;
	}

	public String getDqsh013() {
		return this.dqsh013;
	}

	public void setDqsh013(String dqsh013) {
		this.dqsh013 = dqsh013;
	}

	public String getDqsh014() {
		return this.dqsh014;
	}

	public void setDqsh014(String dqsh014) {
		this.dqsh014 = dqsh014;
	}

	public String getDqsh015() {
		return this.dqsh015;
	}

	public void setDqsh015(String dqsh015) {
		this.dqsh015 = dqsh015;
	}

	public String getDqsh016() {
		return this.dqsh016;
	}

	public void setDqsh016(String dqsh016) {
		this.dqsh016 = dqsh016;
	}

	public String getDqsh017() {
		return this.dqsh017;
	}

	public void setDqsh017(String dqsh017) {
		this.dqsh017 = dqsh017;
	}

	public String getDqsh018() {
		return this.dqsh018;
	}

	public void setDqsh018(String dqsh018) {
		this.dqsh018 = dqsh018;
	}

	public String getDqsh019() {
		return this.dqsh019;
	}

	public void setDqsh019(String dqsh019) {
		this.dqsh019 = dqsh019;
	}

	public String getDqsh020() {
		return this.dqsh020;
	}

	public void setDqsh020(String dqsh020) {
		this.dqsh020 = dqsh020;
	}

	public String getDqsh021() {
		return this.dqsh021;
	}

	public void setDqsh021(String dqsh021) {
		this.dqsh021 = dqsh021;
	}

	public String getDqsh022() {
		return this.dqsh022;
	}

	public void setDqsh022(String dqsh022) {
		this.dqsh022 = dqsh022;
	}

	public String getDqsh023() {
		return this.dqsh023;
	}

	public void setDqsh023(String dqsh023) {
		this.dqsh023 = dqsh023;
	}

	public String getDqsh024() {
		return this.dqsh024;
	}

	public void setDqsh024(String dqsh024) {
		this.dqsh024 = dqsh024;
	}

	public String getDqsh025() {
		return this.dqsh025;
	}

	public void setDqsh025(String dqsh025) {
		this.dqsh025 = dqsh025;
	}

	public String getDqsh026() {
		return this.dqsh026;
	}

	public void setDqsh026(String dqsh026) {
		this.dqsh026 = dqsh026;
	}

	public String getDqsh027() {
		return this.dqsh027;
	}

	public void setDqsh027(String dqsh027) {
		this.dqsh027 = dqsh027;
	}

	public String getDqsh028() {
		return this.dqsh028;
	}

	public void setDqsh028(String dqsh028) {
		this.dqsh028 = dqsh028;
	}

	public String getDqsh029() {
		return this.dqsh029;
	}

	public void setDqsh029(String dqsh029) {
		this.dqsh029 = dqsh029;
	}

	public String getDqsh030() {
		return this.dqsh030;
	}

	public void setDqsh030(String dqsh030) {
		this.dqsh030 = dqsh030;
	}

	public String getDqsh031() {
		return this.dqsh031;
	}

	public void setDqsh031(String dqsh031) {
		this.dqsh031 = dqsh031;
	}

	public String getDqsh032() {
		return this.dqsh032;
	}

	public void setDqsh032(String dqsh032) {
		this.dqsh032 = dqsh032;
	}

	public String getDqsh033() {
		return this.dqsh033;
	}

	public void setDqsh033(String dqsh033) {
		this.dqsh033 = dqsh033;
	}

	public String getDqsh034() {
		return this.dqsh034;
	}

	public void setDqsh034(String dqsh034) {
		this.dqsh034 = dqsh034;
	}

	public String getDqsh035() {
		return this.dqsh035;
	}

	public void setDqsh035(String dqsh035) {
		this.dqsh035 = dqsh035;
	}

	public String getDqsh036() {
		return this.dqsh036;
	}

	public void setDqsh036(String dqsh036) {
		this.dqsh036 = dqsh036;
	}

	

	public String getCustGrade() {
		return custGrade;
	}

	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public Date getCheckEndDate() {
		return checkEndDate;
	}

	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckRq() {
		return checkRq;
	}

	public void setCheckRq(String checkRq) {
		this.checkRq = checkRq;
	}

	public Date getCheckStartDate() {
		return checkStartDate;
	}

	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getCustGradeCheck() {
		return custGradeCheck;
	}

	public void setCustGradeCheck(String custGradeCheck) {
		this.custGradeCheck = custGradeCheck;
	}

	public String getCustGradeType() {
		return custGradeType;
	}

	public void setCustGradeType(String custGradeType) {
		this.custGradeType = custGradeType;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getDqsh037() {
		return dqsh037;
	}

	public void setDqsh037(String dqsh037) {
		this.dqsh037 = dqsh037;
	}

	public String getDqsh038() {
		return dqsh038;
	}

	public void setDqsh038(String dqsh038) {
		this.dqsh038 = dqsh038;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	/*public String getFxq006() {
		return fxq006;
	}

	public void setFxq006(String fxq006) {
		this.fxq006 = fxq006;
	}
*/
	public String getFxqCode() {
		return fxqCode;
	}

	public void setFxqCode(String fxqCode) {
		this.fxqCode = fxqCode;
	}

	public String getFxq007() {
		return fxq007;
	}

	public void setFxq007(String fxq007) {
		this.fxq007 = fxq007;
	}

	public String getFxq008() {
		return fxq008;
	}

	public void setFxq008(String fxq008) {
		this.fxq008 = fxq008;
	}

	public String getFxq009() {
		return fxq009;
	}

	public void setFxq009(String fxq009) {
		this.fxq009 = fxq009;
	}

	public String getFxq010() {
		return fxq010;
	}

	public void setFxq010(String fxq010) {
		this.fxq010 = fxq010;
	}

	public String getFxq011() {
		return fxq011;
	}

	public void setFxq011(String fxq011) {
		this.fxq011 = fxq011;
	}

	public String getFxq012() {
		return fxq012;
	}

	public void setFxq012(String fxq012) {
		this.fxq012 = fxq012;
	}

	public String getFxq013() {
		return fxq013;
	}

	public void setFxq013(String fxq013) {
		this.fxq013 = fxq013;
	}

	public String getFxq014() {
		return fxq014;
	}

	public void setFxq014(String fxq014) {
		this.fxq014 = fxq014;
	}

	public String getFxq015() {
		return fxq015;
	}

	public void setFxq015(String fxq015) {
		this.fxq015 = fxq015;
	}

	public String getFxq016() {
		return fxq016;
	}

	public void setFxq016(String fxq016) {
		this.fxq016 = fxq016;
	}

	public String getFxq021() {
		return fxq021;
	}

	public void setFxq021(String fxq021) {
		this.fxq021 = fxq021;
	}

	public String getFxq022() {
		return fxq022;
	}

	public void setFxq022(String fxq022) {
		this.fxq022 = fxq022;
	}

	public String getFxq023() {
		return fxq023;
	}

	public void setFxq023(String fxq023) {
		this.fxq023 = fxq023;
	}

	public String getFxq024() {
		return fxq024;
	}

	public void setFxq024(String fxq024) {
		this.fxq024 = fxq024;
	}

	public String getFxq025() {
		return fxq025;
	}

	public void setFxq025(String fxq025) {
		this.fxq025 = fxq025;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}