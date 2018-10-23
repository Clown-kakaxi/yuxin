package com.yuchengtech.bcrm.oneKeyAccount.model;
/**
 * @项目名称：CRM一键开户
 * @类名称：ICCardSysVO
 * @类描述：IC卡字段模型
 * @功能描述:IC卡字段模型
 * @创建人：wx
 * @创建时间：2017年9月1日下午5:18:28
 * @修改人：wx
 * @修改时间：2017年9月1日下午5:18:28
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class ICCardSysVO {
	
	private String dealNo = "CRM00001";//交易代码
	private String serialNo = "";//流水号    交易唯一标识:（参考银联：交易时间MMDDhhmmss + 6位系统跟踪号+11位受理机构标识码+11位发送机构标识码）
	private String channelNo = "CRM";//渠道
	private String accountNo = "";//账户
	private String dealTime = "";//交易时间
	private String cardNo = "";//卡号
	private String mainCardNo = "";//主卡卡号
	private String pinPsw = "";//加密机返回的原始的加密后的密码
	private String psw = "";//解密后的密码字符串
	private byte[] pswCode;//加密后密码字节
	private String cardSerialNo = "";//卡序列号
	private String dataSource = "";//数据来源
	private String secondTrackInfo = "";//二磁道信息
	private String unionpay55Info = "";//银联55域信息
	private String branchNo = "";//分行
	private String operatorNo = "";//操作员
	private String clientNo  = "";//终端
	//2017-09-04补充内容
	private String LMTAMT_D_ATM = "50000";//ATM日累计转账最高限额
	private String LMTCNT_D_ATM = "10";//ATM日累计转账最高笔数
	private String LMTAMT_Y_ATM = "10000000";//ATM年累计转账最高限额 
	private String LMTAMT_POS = "500000";//POS单笔消费限额
	
	//20171021补充内容
	private String custNm;//定制卡凸印姓名
	private String speCardType;//定制卡类型(000010---金卡深圳/000020—数位卡天津/000008----白金卡苏州/000009---钻石卡苏州)


	
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMainCardNo() {
		return mainCardNo;
	}
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getCardSerialNo() {
		return cardSerialNo;
	}
	public void setCardSerialNo(String cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getSecondTrackInfo() {
		return secondTrackInfo;
	}
	public void setSecondTrackInfo(String secondTrackInfo) {
		this.secondTrackInfo = secondTrackInfo;
	}
	public String getUnionpay55Info() {
		return unionpay55Info;
	}
	public void setUnionpay55Info(String unionpay55Info) {
		this.unionpay55Info = unionpay55Info;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getOperatorNo() {
		return operatorNo;
	}
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getLMTAMT_D_ATM() {
		return LMTAMT_D_ATM;
	}
	public void setLMTAMT_D_ATM(String lMTAMT_D_ATM) {
		LMTAMT_D_ATM = lMTAMT_D_ATM;
	}
	public String getLMTCNT_D_ATM() {
		return LMTCNT_D_ATM;
	}
	public void setLMTCNT_D_ATM(String lMTCNT_D_ATM) {
		LMTCNT_D_ATM = lMTCNT_D_ATM;
	}
	public String getLMTAMT_Y_ATM() {
		return LMTAMT_Y_ATM;
	}
	public void setLMTAMT_Y_ATM(String lMTAMT_Y_ATM) {
		LMTAMT_Y_ATM = lMTAMT_Y_ATM;
	}
	public String getLMTAMT_POS() {
		return LMTAMT_POS;
	}
	public void setLMTAMT_POS(String lMTAMT_POS) {
		LMTAMT_POS = lMTAMT_POS;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getSpeCardType() {
		return speCardType;
	}
	public void setSpeCardType(String speCardType) {
		this.speCardType = speCardType;
	}
	public byte[] getPswCode() {
		return pswCode;
	}
	public void setPswCode(byte[] pswCode) {
		this.pswCode = pswCode;
	}
	public String getPinPsw() {
		return pinPsw;
	}
	public void setPinPsw(String pinPsw) {
		this.pinPsw = pinPsw;
	}
	
	
}
