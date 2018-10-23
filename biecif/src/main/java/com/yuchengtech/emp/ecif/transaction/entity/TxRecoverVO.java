package com.yuchengtech.emp.ecif.transaction.entity;

public class TxRecoverVO {
	private String txCode;
	private String fileName;
	private String relPath;
	private String createTime;
	public String getTxCode() {
		return txCode;
	}
	public String getRelPath() {
		return relPath;
	}
	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
