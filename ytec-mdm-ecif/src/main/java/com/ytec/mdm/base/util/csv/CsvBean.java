package com.ytec.mdm.base.util.csv;

public class CsvBean {
	/**
	 * @��������:�к�
	 * @��������:TODO
	 * @since 1.0.0
	 */
	private int id;
	
	private String recNo;
	/**
	 * ԭ����
	 */
	private String primalLineMsg;
	/**
	 * �ָ���������
	 */
	private String[] reqLineMsgs;
	/**
	 * ��֤ת�����������
	 */
	private String[] opLineMsg;
	/**
	 * ��Ӧ��������
	 */
	private String[] rspLineMsgs;
	
	/***
	 *  ״̬
	 */
	private boolean isSuccess=true;
	/***
	 * ������
	 */
	private String errorCode="000000";
	/***
	 * ��������
	 */
	private String errorDesc="�ɹ�";
	
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
