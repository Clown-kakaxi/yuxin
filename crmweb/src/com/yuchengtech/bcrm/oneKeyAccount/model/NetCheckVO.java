package com.yuchengtech.bcrm.oneKeyAccount.model;

/**
 * 联网核查字段实体
 * @author wx
 *
 */
public class NetCheckVO {

	private String SEQNO;//交易流水号
	private String MESID = "0001";//请求类型
	private String BusinessCode = "01";//业务种类
	private String BankCode;//核对机构代码
	private String UserCode;//操作用户
	private String ID;//身份证号码
	private String Name;//被核对人姓名
	
	
	public String getMESID() {
		return MESID;
	}
	public void setMESID(String mESID) {
		MESID = mESID;
	}
	public String getBusinessCode() {
		return BusinessCode;
	}
	public void setBusinessCode(String businessCode) {
		BusinessCode = businessCode;
	}
	public String getBankCode() {
		return BankCode;
	}
	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}
	public String getUserCode() {
		return UserCode;
	}
	public void setUserCode(String userCode) {
		UserCode = userCode;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSEQNO() {
		return SEQNO;
	}
	public void setSEQNO(String sEQNO) {
		SEQNO = sEQNO;
	}
	
}
