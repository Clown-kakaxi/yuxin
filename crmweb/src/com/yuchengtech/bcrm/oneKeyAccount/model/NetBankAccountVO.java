package com.yuchengtech.bcrm.oneKeyAccount.model;

import java.util.List;

/**
 * @项目名称：个金一键开户
 * @类名称：NetBankAccountVO
 * @类描述：网银开户所需字段
 * @功能描述:网银开户所需字段
 * @创建人：wx
 * @创建时间：2017年9月12日上午9:41:12
 * @修改人：wx
 * @修改时间：2017年9月12日上午9:41:12
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class NetBankAccountVO {

	private String SerializeId;//交易流水号
	private String CifNo;//核心客户号
	private String CustName;//客户姓名
	private String IdType;//证件类型 IdType.P00=身份证 IdType.P01=军官证 IdType.P02=文职干部证 IdType.P03=警官证 IdType.P04=士兵军人证 IdType.P05=护照 IdType.P06=港澳台居民身份证 IdType.P07=户口簿 IdType.P13=台湾居民往来通行证 IdType.P99=其他
	private String IdNo;//证件号
	private String BirthDate;//出生日期
	private String PmbsTelNo;//手机银行签约手机号码
	private String CifType;//客户类别
	private String TELType;//电话类型 C=办公电话 H=家庭电话 L=固定电话 M=移动电话
	private String TELNo;//电话号码
	private String TelAuthFlg;//验证标识 N=未认证 Y=已认证 Z=自助认证  默认Y
	private String HomeAddr;//家庭地址信息
	private String PostZipCode;//居住地邮编
	private String EAddrType;//地址类型 默认输入Email
	private String EAddr;//地址 邮箱地址
	
	//非必输字段
	private String DayPerLimit = "999999999999.99";//人民币日累计转账限额
	private String DayTransTimes = "200";//人民币日累计转账笔数
	private String LimitPerYear = "999999999999.99";//人民币年累计转账限额
	private List<NetBankAccountChannelVO> channelList;//
	
	
	public String getSerializeId() {
		return SerializeId;
	}
	public void setSerializeId(String serializeId) {
		SerializeId = serializeId;
	}
	public String getCifNo() {
		return CifNo;
	}
	public void setCifNo(String cifNo) {
		CifNo = cifNo;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getIdType() {
		return IdType;
	}
	public void setIdType(String idType) {
		IdType = idType;
	}
	public String getIdNo() {
		return IdNo;
	}
	public void setIdNo(String idNo) {
		IdNo = idNo;
	}
	public String getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(String birthDate) {
		BirthDate = birthDate;
	}
	public String getPmbsTelNo() {
		return PmbsTelNo;
	}
	public void setPmbsTelNo(String pmbsTelNo) {
		PmbsTelNo = pmbsTelNo;
	}
	public String getCifType() {
		return CifType;
	}
	public void setCifType(String cifType) {
		CifType = cifType;
	}
	public String getTELType() {
		return TELType;
	}
	public void setTELType(String tELType) {
		TELType = tELType;
	}
	public String getTELNo() {
		return TELNo;
	}
	public void setTELNo(String tELNo) {
		TELNo = tELNo;
	}
	public String getTelAuthFlg() {
		return TelAuthFlg;
	}
	public void setTelAuthFlg(String telAuthFlg) {
		TelAuthFlg = telAuthFlg;
	}
	public String getEAddrType() {
		return EAddrType;
	}
	public void setEAddrType(String eAddrType) {
		EAddrType = eAddrType;
	}
	public String getEAddr() {
		return EAddr;
	}
	public void setEAddr(String eAddr) {
		EAddr = eAddr;
	}
	public String getDayPerLimit() {
		return DayPerLimit;
	}
	public void setDayPerLimit(String dayPerLimit) {
		DayPerLimit = dayPerLimit;
	}
	public String getDayTransTimes() {
		return DayTransTimes;
	}
	public void setDayTransTimes(String dayTransTimes) {
		DayTransTimes = dayTransTimes;
	}
	public String getLimitPerYear() {
		return LimitPerYear;
	}
	public void setLimitPerYear(String limitPerYear) {
		LimitPerYear = limitPerYear;
	}
	public List<NetBankAccountChannelVO> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<NetBankAccountChannelVO> channelList) {
		this.channelList = channelList;
	}
	public String getHomeAddr() {
		return HomeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		HomeAddr = homeAddr;
	}
	public String getPostZipCode() {
		return PostZipCode;
	}
	public void setPostZipCode(String postZipCode) {
		PostZipCode = postZipCode;
	}
	
	
}
