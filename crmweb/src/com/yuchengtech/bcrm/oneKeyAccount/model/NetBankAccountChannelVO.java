package com.yuchengtech.bcrm.oneKeyAccount.model;
/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @功能描述:
 * @创建人：
 * @创建时间：2017年9月12日上午11:45:55
 * @修改人：
 * @修改时间：2017年9月12日上午11:45:55
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class NetBankAccountChannelVO {

	private String MChannelId;//模块渠道 PIBS=个人网上银行 PMBS=个人手机银行
	private String mchMobilePhone;//手机号
	private String AuthMod;//审核方式 U：USBkey证书， C：云证通，F：文件证书，O：动态口令，Z：普通（无认证）,OU:短信+证书;
	private String State;//渠道状态 N：开通(正常）C:关闭L：锁定F：冻结R：注销重开X：未激活H：睡眠A：申请  默认N
	
	public String getMChannelId() {
		return MChannelId;
	}
	public void setMChannelId(String mChannelId) {
		MChannelId = mChannelId;
	}
	public String getMchMobilePhone() {
		return mchMobilePhone;
	}
	public void setMchMobilePhone(String mchMobilePhone) {
		this.mchMobilePhone = mchMobilePhone;
	}
	public String getAuthMod() {
		return AuthMod;
	}
	public void setAuthMod(String authMod) {
		AuthMod = authMod;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	

}
