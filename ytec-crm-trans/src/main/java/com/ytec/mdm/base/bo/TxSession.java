/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����TxSession.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-5-����3:23:05
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.bo;
/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxSession
 * @�����������׻ػ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-5 ����3:23:05   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-5 ����3:23:05
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class TxSession {
	/**
	 * 
	 * 
	 * @��������:������
	 */
	private String txCode; 
	
	/**
	 * The inter face type.
	 * 
	 * @��������:�ӿڷ�ʽ
	 */
	private String interFaceType;
	
	/**
	 * The tx type.
	 * 
	 * @��������:��������
	 */
	private String txType;
	
	/**
	 * @��������:stopWatch
	 * @��������:��ʱ����
	 * @since 1.0.0
	 */
	private StopWatch stopWatch=new StopWatch();
	
	/**
	 * ����״̬*.
	 * 
	 * @��������:״̬
	 */
	private boolean isSuccess=true;
	
	/**
	 * The rep state cd.
	 * 
	 * @��������:������
	 */
	private String repStateCd="000000"; 
	
	/**
	 * The detail des.
	 * 
	 * @��������:������ϸ����
	 */
	private String detailDes="�ɹ�";
	
	/**
	 * ����*.
	 * 
	 * @��������:ԭʼ����  
	 */
	private String  primalMsg; 
	
	/**
	 * �������*.
	 * 
	 * @��������:������           
	 */
	private String brchNo; 
	
	/**
	 * The tlr no.
	 * 
	 * @��������:��Ա��           
	 */
	private String tlrNo; 
	
	/**
	 * The op chnl no.
	 * 
	 * @��������:����ϵͳ/��������
	 */
	private String opChnlNo;
	/**
	 * The req seq no.
	 * 
	 * @��������:������ˮ��
	 */
	private String reqSeqNo;
	/**
	 * @��������:�ͻ�����ҵ�����
	 */
	private Object attachment = null;
	
	
	/**
	 * @��������:charsetName
	 * @��������:���ױ���ʹ�õ��ַ���
	 * @since 1.0.0
	 */
	private String charsetName;
	
	/**
	 * @��������:fullTraceInfo
	 * @��������:���������Ϣ
	 * @since 1.0.0
	 */
	private String fullTraceInfo;
	
	/**
	 * @��������:setStatus
	 * @��������:���ó�����Ϣ
	 * @�����뷵��˵��:
	 * 		@param repStateCd
	 * 		@param detailDes
	 * @�㷨����:
	 */
	public void setStatus(String repStateCd,String detailDes){
		this.repStateCd=repStateCd;
		this.detailDes=detailDes;
		this.isSuccess=false;
		getTraceInfo();
	}
	
	
	/**
	 * @��������:setStatus
	 * @��������:���ó�����Ϣ
	 * @�����뷵��˵��:
	 * 		@param repStateCd
	 * 		@param format
	 * 		@param args
	 * @�㷨����:
	 */
	public void setStatus(String repStateCd,String format, Object... args){
		this.repStateCd=repStateCd;
		this.detailDes=String.format(format,args);
		this.isSuccess=false;
		getTraceInfo();
	}
	
	/**
	 * ���ó�����Ϣ*.
	 * 
	 * @param error
	 *            the new status
	 */
	public void setStatus(Error error){
		this.repStateCd=error.getCode();
		this.detailDes=error.getChDesc();
		this.isSuccess=false;
		getTraceInfo();
	}

	/**
	 * Gets the tx code.
	 * 
	 * @return the tx code
	 */
	public String getTxCode() {
		return txCode;
	}

	/**
	 * Sets the tx code.
	 * 
	 * @param txCode
	 *            the new tx code
	 */
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}



	/**
	 * Gets the inter face type.
	 * 
	 * @return the inter face type
	 */
	public String getInterFaceType() {
		return interFaceType;
	}



	/**
	 * Sets the inter face type.
	 * 
	 * @param interFaceType
	 *            the new inter face type
	 */
	public void setInterFaceType(String interFaceType) {
		this.interFaceType = interFaceType;
	}



	/**
	 * Gets the tx type.
	 * 
	 * @return the tx type
	 */
	public String getTxType() {
		return txType;
	}



	/**
	 * Sets the tx type.
	 * 
	 * @param txType
	 *            the new tx type
	 */
	public void setTxType(String txType) {
		this.txType = txType;
	}
	
	
	public StopWatch getStopWatch() {
		return stopWatch;
	}


	/**
	 * Checks if is success.
	 * 
	 * @return true, if checks if is success
	 * @��������:boolean isSuccess()
	 * @��������:
	 * @�����뷵��˵��: boolean isSuccess()
	 * @�㷨����:
	 */
	public boolean isSuccess() {
		return isSuccess;
	}



	/**
	 * Sets the success.
	 * 
	 * @param isSuccess
	 *            the new success
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}



	/**
	 * Gets the rep state cd.
	 * 
	 * @return the rep state cd
	 */
	public String getRepStateCd() {
		return repStateCd;
	}


	/**
	 * Gets the detail des.
	 * 
	 * @return the detail des
	 */
	public String getDetailDes() {
		return detailDes;
	}


	/**
	 * Gets the primal msg.
	 * 
	 * @return the primal msg
	 */
	public String getPrimalMsg() {
		return primalMsg;
	}



	/**
	 * Sets the primal msg.
	 * 
	 * @param primalMsg
	 *            the new primal msg
	 */
	public void setPrimalMsg(String primalMsg) {
		this.primalMsg = primalMsg;
	}
	/**
	 * Gets the brch no.
	 * 
	 * @return the brch no
	 */
	public String getBrchNo() {
		return brchNo;
	}



	/**
	 * Sets the brch no.
	 * 
	 * @param brchNo
	 *            the new brch no
	 */
	public void setBrchNo(String brchNo) {
		this.brchNo = brchNo;
	}



	/**
	 * Gets the tlr no.
	 * 
	 * @return the tlr no
	 */
	public String getTlrNo() {
		return tlrNo;
	}



	/**
	 * Sets the tlr no.
	 * 
	 * @param tlrNo
	 *            the new tlr no
	 */
	public void setTlrNo(String tlrNo) {
		this.tlrNo = tlrNo;
	}



	/**
	 * Gets the op chnl no.
	 * 
	 * @return the op chnl no
	 */
	public String getOpChnlNo() {
		return opChnlNo;
	}



	/**
	 * Sets the op chnl no.
	 * 
	 * @param opChnlNo
	 *            the new op chnl no
	 */
	public void setOpChnlNo(String opChnlNo) {
		this.opChnlNo = opChnlNo;
	}



	/**
	 * Gets the req seq no.
	 * 
	 * @return the req seq no
	 */
	public String getReqSeqNo() {
		return reqSeqNo;
	}



	/**
	 * Sets the req seq no.
	 * 
	 * @param reqSeqNo
	 *            the new req seq no
	 */
	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}
	/**
	 * Reset status.
	 * 
	 * @��������:void resetStatus()
	 * @��������:
	 * @�����뷵��˵��: void resetStatus()
	 * @�㷨����:
	 */
	public void resetStatus(){
		this.repStateCd="000000";
		this.detailDes="�ɹ�";
		this.isSuccess=true;
		this.fullTraceInfo=null;
	}
	/**
	 * @��������:attach
	 * @��������:���Զ���ҵ�����
	 * @�����뷵��˵��:
	 * 		@param ob
	 * 		@return
	 * @�㷨����:
	 */
	public final Object attach(Object ob) {
		Object a = attachment;
		attachment = ob;
		return a;
	}	

	/**
	 * @��������:attachment
	 * @��������:��ȡ�Զ���ҵ�����
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public final Object attachment() {
		return attachment;
	}


	public String getCharsetName() {
		return charsetName;
	}


	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}	
	
	/**
	 * @��������:getTraceInfo
	 * @��������:��ȡ���ô�����Ϣ�ĸ�����Ϣ
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	private void getTraceInfo(){  
        StackTraceElement[] stacks = new Throwable().getStackTrace();  
        int index;
        if(stacks.length>=3){
        	index = 2;
        }else{
        	index = stacks.length-1;
        }
        this.fullTraceInfo= String.format("file[%s] class[%s] method[%s] Line[%d]", stacks[index].getFileName(),stacks[index].getClassName(),
        		stacks[index].getMethodName(),stacks[index].getLineNumber());    
    }


	public String getFullTraceInfo() {
		return fullTraceInfo;
	}
}
