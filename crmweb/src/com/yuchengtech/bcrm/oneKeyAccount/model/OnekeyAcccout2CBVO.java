package com.yuchengtech.bcrm.oneKeyAccount.model;

/**
 * CRM请求核心开户报文字段
 * @author wx
 *
 */
public class OnekeyAcccout2CBVO {

	private String ReqSysCd = "CRM";//请求系统
	private String txCode = "onekeyAccount2CB";//交易编号
	private String DestSysCd = "CB";//目标系统
	private String CUSTID = "";//ECIF客户号
	private String TranType = "";//数据类型=‘0’为交易确认/查询信息	=‘1’为交易请求初始信息
	private String IDCODE = "";//证件类型
	private String BRAIDID = "";//证件号码
	private String REFID = "";//台胞证/港澳证号码
	private String CUFULNM = "";//中文名
	private String CUSNM = "";//英文名
	private String CORCOUN = "";//国别
	private String RISCOUN = "";//风险国别
	private String SEXIND = "";//性别
	private String ESTDATE = "";//出生日期
	private String MAILAD1 = "";//地址1
	private String MAILAD2 = "";//地址1
	private String MAILAD3 = "";//地址1
	private String CUSTEL1 = "";//电话1
	private String CUSTEL2 = "";//电话1
	private String CUSTEL3 = "";//电话1
	private String EMAILAD = "";//邮件地址
	private String BRANCH = "";//行号
	private String ACCODE = "";//客户经理代码
	private String COCODE = "";//操作员工号
	private String NEWOLD = "";//新旧户标识
	private String ACCATE = "";//账号属性
	private boolean isOnlyPushInfo;//是否只推送信息不开户
	
	
	
	public String getReqSysCd() {
		return ReqSysCd;
	}
	public void setReqSysCd(String reqSysCd) {
		ReqSysCd = reqSysCd;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getDestSysCd() {
		return DestSysCd;
	}
	public void setDestSysCd(String destSysCd) {
		DestSysCd = destSysCd;
	}
	public String getCUSTID() {
		return CUSTID;
	}
	public void setCUSTID(String cUSTID) {
		CUSTID = cUSTID;
	}
	public String getTranType() {
		return TranType;
	}
	public void setTranType(String tranType) {
		TranType = tranType;
	}
	public String getIDCODE() {
		return IDCODE;
	}
	public void setIDCODE(String iDCODE) {
		IDCODE = iDCODE;
	}
	public String getBRAIDID() {
		return BRAIDID;
	}
	public void setBRAIDID(String bRAIDID) {
		BRAIDID = bRAIDID;
	}
	public String getREFID() {
		return REFID;
	}
	public void setREFID(String rEFID) {
		REFID = rEFID;
	}
	public String getCUFULNM() {
		return CUFULNM;
	}
	public void setCUFULNM(String cUFULNM) {
		CUFULNM = cUFULNM;
	}
	public String getCUSNM() {
		return CUSNM;
	}
	public void setCUSNM(String cUSNM) {
		CUSNM = cUSNM;
	}
	public String getCORCOUN() {
		return CORCOUN;
	}
	public void setCORCOUN(String cORCOUN) {
		CORCOUN = cORCOUN;
	}
	public String getRISCOUN() {
		return RISCOUN;
	}
	public void setRISCOUN(String rISCOUN) {
		RISCOUN = rISCOUN;
	}
	public String getSEXIND() {
		return SEXIND;
	}
	public void setSEXIND(String sEXIND) {
		SEXIND = sEXIND;
	}
	public String getESTDATE() {
		return ESTDATE;
	}
	public void setESTDATE(String eSTDATE) {
		ESTDATE = eSTDATE;
	}
	public String getMAILAD1() {
		return MAILAD1;
	}
	public void setMAILAD1(String mAILAD1) {
		MAILAD1 = mAILAD1;
	}
	public String getMAILAD2() {
		return MAILAD2;
	}
	public void setMAILAD2(String mAILAD2) {
		MAILAD2 = mAILAD2;
	}
	public String getMAILAD3() {
		return MAILAD3;
	}
	public void setMAILAD3(String mAILAD3) {
		MAILAD3 = mAILAD3;
	}
	public String getCUSTEL1() {
		return CUSTEL1;
	}
	public void setCUSTEL1(String cUSTEL1) {
		CUSTEL1 = cUSTEL1;
	}
	public String getCUSTEL2() {
		return CUSTEL2;
	}
	public void setCUSTEL2(String cUSTEL2) {
		CUSTEL2 = cUSTEL2;
	}
	public String getCUSTEL3() {
		return CUSTEL3;
	}
	public void setCUSTEL3(String cUSTEL3) {
		CUSTEL3 = cUSTEL3;
	}
	public String getEMAILAD() {
		return EMAILAD;
	}
	public void setEMAILAD(String eMAILAD) {
		EMAILAD = eMAILAD;
	}
	public String getBRANCH() {
		return BRANCH;
	}
	public void setBRANCH(String bRANCH) {
		BRANCH = bRANCH;
	}
	public String getACCODE() {
		return ACCODE;
	}
	public void setACCODE(String aCCODE) {
		ACCODE = aCCODE;
	}
	public String getNEWOLD() {
		return NEWOLD;
	}
	public void setNEWOLD(String nEWOLD) {
		NEWOLD = nEWOLD;
	}
	
	public String getCOCODE() {
		return COCODE;
	}
	public void setCOCODE(String cOCODE) {
		COCODE = cOCODE;
	}
	public String getACCATE() {
		return ACCATE;
	}
	public void setACCATE(String aCCATE) {
		ACCATE = aCCATE;
	}
	public boolean isOnlyPushInfo() {
		return isOnlyPushInfo;
	}
	public void setOnlyPushInfo(boolean isOnlyPushInfo) {
		this.isOnlyPushInfo = isOnlyPushInfo;
	}
	
	
}
