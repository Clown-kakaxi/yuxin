package com.yuchengtech.bcrm.oneKeyAccount.model;

public class OneKeyAccountVO {
	
	//卡系统
	private String operType;
	private String accNo;//
	private String cardNo;//卡号
	private String passWord;//密码
	private String confPW;//确认密码
	private String brchNo;//
	private String tlrNo;
	private String chkNo;
	private String reqDt;
	
	
	//联网核查
	private String businessCode;//业务种类
	private String bankCode;//核对机构代码
	private String userCode;//操作用户
	private String id;//身份证号码
	private String name;//被核对人姓名
	
	
	//核心开户
	private String txCode;			//交易编号
	private String coreNo;			//核心客户号
	private String identType;		//证件类型
	private String identNo;			//证件号码
	private String identValidPeriod;	//证件有效期
	private String refid;			//台胞证/港澳证号码
	private String custName;		//客户名称
	private String custName2 ;		//0
	private String enName;			//英文名称
	private String enName2;			//0
	private String custType;		//客户类型
	private String nationCode;		//国别
	private String adminZone;		//地区代码
	private String inoutFlag;		//境内外标志
	private String orgType;			//组织机构类型
	private String jointCustType;	//联名户
	private String custManagerNo;	//归属客户经理
	private String arCustFlag;		//是否ar客户
	private String swift;			//SWIFT
	private String riskNationCode;	//风险国家代号
	private String mailad1;			//地址：个人（邮寄地址、居住地址）；机构（邮寄、通讯、注册地址）
	private String contmethInfo;	//手机号码（业务手机号码）
	private String sexind;			//性别
	private String estdate;			//出生日期
	private String custel1;			//电话1
	private String custel2;			//电话2
	private String custel3;			//电话3
	private String emailad;			//邮件地址
	private String branch;			//行号
	private String newold;			//新旧户标识
	private String accate;			//账号属性

	
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getConfPW() {
		return confPW;
	}

	public void setConfPW(String confPW) {
		this.confPW = confPW;
	}

	public String getBrchNo() {
		return brchNo;
	}

	public void setBrchNo(String brchNo) {
		this.brchNo = brchNo;
	}

	public String getTlrNo() {
		return tlrNo;
	}

	public void setTlrNo(String tlrNo) {
		this.tlrNo = tlrNo;
	}

	public String getChkNo() {
		return chkNo;
	}

	public void setChkNo(String chkNo) {
		this.chkNo = chkNo;
	}

	public String getReqDt() {
		return reqDt;
	}

	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getCoreNo() {
		return coreNo;
	}

	public void setCoreNo(String coreNo) {
		this.coreNo = coreNo;
	}

	public String getIdentType() {
		return identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentNo() {
		return identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentValidPeriod() {
		return identValidPeriod;
	}

	public void setIdentValidPeriod(String identValidPeriod) {
		this.identValidPeriod = identValidPeriod;
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustName2() {
		return custName2;
	}

	public void setCustName2(String custName2) {
		this.custName2 = custName2;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnName2() {
		return enName2;
	}

	public void setEnName2(String enName2) {
		this.enName2 = enName2;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getAdminZone() {
		return adminZone;
	}

	public void setAdminZone(String adminZone) {
		this.adminZone = adminZone;
	}

	public String getInoutFlag() {
		return inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getJointCustType() {
		return jointCustType;
	}

	public void setJointCustType(String jointCustType) {
		this.jointCustType = jointCustType;
	}

	public String getCustManagerNo() {
		return custManagerNo;
	}

	public void setCustManagerNo(String custManagerNo) {
		this.custManagerNo = custManagerNo;
	}

	public String getArCustFlag() {
		return arCustFlag;
	}

	public void setArCustFlag(String arCustFlag) {
		this.arCustFlag = arCustFlag;
	}

	public String getSwift() {
		return swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getRiskNationCode() {
		return riskNationCode;
	}

	public void setRiskNationCode(String riskNationCode) {
		this.riskNationCode = riskNationCode;
	}

	public String getMailad1() {
		return mailad1;
	}

	public void setMailad1(String mailad1) {
		this.mailad1 = mailad1;
	}

	public String getContmethInfo() {
		return contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	public String getSexind() {
		return sexind;
	}

	public void setSexind(String sexind) {
		this.sexind = sexind;
	}

	public String getEstdate() {
		return estdate;
	}

	public void setEstdate(String estdate) {
		this.estdate = estdate;
	}

	public String getCustel1() {
		return custel1;
	}

	public void setCustel1(String custel1) {
		this.custel1 = custel1;
	}

	public String getCustel2() {
		return custel2;
	}

	public void setCustel2(String custel2) {
		this.custel2 = custel2;
	}

	public String getCustel3() {
		return custel3;
	}

	public void setCustel3(String custel3) {
		this.custel3 = custel3;
	}

	public String getEmailad() {
		return emailad;
	}

	public void setEmailad(String emailad) {
		this.emailad = emailad;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getNewold() {
		return newold;
	}

	public void setNewold(String newold) {
		this.newold = newold;
	}

	public String getAccate() {
		return accate;
	}

	public void setAccate(String accate) {
		this.accate = accate;
	}
	
}
