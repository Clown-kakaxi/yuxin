package com.ytec.mdm.base.util.csv;

public class CsvBean {
	/**
	 * @属性名称:行号
	 * @属性描述:TODO
	 * @since 1.0.0
	 */
	private int id;
	
	private String recNo;
	/**
	 * 原数据
	 */
	private String primalLineMsg;
	/**
	 * 分割后的数据组
	 */
	private String[] reqLineMsgs;
	/**
	 * 验证转换后的数据组
	 */
	private String[] opLineMsg;
	/**
	 * 响应的数据组
	 */
	private String[] rspLineMsgs;
	
	/***
	 *  状态
	 */
	private boolean isSuccess=true;
	/***
	 * 错误码
	 */
	private String errorCode="000000";
	/***
	 * 错误描述
	 */
	private String errorDesc="成功";
	
	public String getPrimalLineMsg() {
		return primalLineMsg;
	}
	public void setPrimalLineMsg(String primalLineMsg) {
		this.primalLineMsg = primalLineMsg;
	}
	public String[] getReqLineMsgs() {
		return reqLineMsgs;
	}
	public void setReqLineMsgs(String[] reqLineMsgs) {
		this.reqLineMsgs = reqLineMsgs;
	}
	
	public String[] getOpLineMsg() {
		return opLineMsg;
	}
	public void setOpLineMsg(String[] opLineMsg) {
		this.opLineMsg = opLineMsg;
	}
	public String[] getRspLineMsgs() {
		return rspLineMsgs;
	}
	public void setRspLineMsgs(String[] rspLineMsgs) {
		this.rspLineMsgs = rspLineMsgs;
	}
	public String getRecNo() {
		return recNo;
	}
	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	
	public void setResultState(String errorCode,String errorDesc){
		this.errorCode=errorCode;
		this.errorDesc=errorDesc;
		isSuccess=false;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
