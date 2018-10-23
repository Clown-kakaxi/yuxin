/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：TxSession.java
 * @版本信息：1.0.0
 * @日期：2014-5-5-下午3:23:05
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.bo;
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：TxSession
 * @类描述：交易回话对象
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-5 下午3:23:05   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-5 下午3:23:05
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class TxSession {
	/**
	 * 
	 * 
	 * @属性描述:交易码
	 */
	private String txCode; 
	
	/**
	 * The inter face type.
	 * 
	 * @属性描述:接口方式
	 */
	private String interFaceType;
	
	/**
	 * The tx type.
	 * 
	 * @属性描述:交易类型
	 */
	private String txType;
	
	/**
	 * @属性名称:stopWatch
	 * @属性描述:计时工具
	 * @since 1.0.0
	 */
	private StopWatch stopWatch=new StopWatch();
	
	/**
	 * 服务状态*.
	 * 
	 * @属性描述:状态
	 */
	private boolean isSuccess=true;
	
	/**
	 * The rep state cd.
	 * 
	 * @属性描述:返回码
	 */
	private String repStateCd="000000"; 
	
	/**
	 * The detail des.
	 * 
	 * @属性描述:错误详细描述
	 */
	private String detailDes="成功";
	
	/**
	 * 报文*.
	 * 
	 * @属性描述:原始报文  
	 */
	private String  primalMsg; 
	
	/**
	 * 操作相关*.
	 * 
	 * @属性描述:机构号           
	 */
	private String brchNo; 
	
	/**
	 * The tlr no.
	 * 
	 * @属性描述:柜员号           
	 */
	private String tlrNo; 
	
	/**
	 * The op chnl no.
	 * 
	 * @属性描述:操作系统/渠道代码
	 */
	private String opChnlNo;
	/**
	 * The req seq no.
	 * 
	 * @属性描述:操作流水号
	 */
	private String reqSeqNo;
	/**
	 * @属性描述:客户化绑定业务对象
	 */
	private Object attachment = null;
	
	
	/**
	 * @属性名称:charsetName
	 * @属性描述:交易报文使用的字符集
	 * @since 1.0.0
	 */
	private String charsetName;
	
	/**
	 * @属性名称:fullTraceInfo
	 * @属性描述:错误跟踪信息
	 * @since 1.0.0
	 */
	private String fullTraceInfo;
	
	/**
	 * @函数名称:setStatus
	 * @函数描述:设置出错信息
	 * @参数与返回说明:
	 * 		@param repStateCd
	 * 		@param detailDes
	 * @算法描述:
	 */
	public void setStatus(String repStateCd,String detailDes){
		this.repStateCd=repStateCd;
		this.detailDes=detailDes;
		this.isSuccess=false;
		getTraceInfo();
	}
	
	
	/**
	 * @函数名称:setStatus
	 * @函数描述:设置出错信息
	 * @参数与返回说明:
	 * 		@param repStateCd
	 * 		@param format
	 * 		@param args
	 * @算法描述:
	 */
	public void setStatus(String repStateCd,String format, Object... args){
		this.repStateCd=repStateCd;
		this.detailDes=String.format(format,args);
		this.isSuccess=false;
		getTraceInfo();
	}
	
	/**
	 * 设置出错信息*.
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
	 * @函数名称:boolean isSuccess()
	 * @函数描述:
	 * @参数与返回说明: boolean isSuccess()
	 * @算法描述:
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
	 * @函数名称:void resetStatus()
	 * @函数描述:
	 * @参数与返回说明: void resetStatus()
	 * @算法描述:
	 */
	public void resetStatus(){
		this.repStateCd="000000";
		this.detailDes="成功";
		this.isSuccess=true;
		this.fullTraceInfo=null;
	}
	/**
	 * @函数名称:attach
	 * @函数描述:绑定自定义业务对象
	 * @参数与返回说明:
	 * 		@param ob
	 * 		@return
	 * @算法描述:
	 */
	public final Object attach(Object ob) {
		Object a = attachment;
		attachment = ob;
		return a;
	}	

	/**
	 * @函数名称:attachment
	 * @函数描述:获取自定义业务对象
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
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
	 * @函数名称:getTraceInfo
	 * @函数描述:获取设置错误消息的跟踪信息
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
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
