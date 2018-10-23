/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common
 * @文件名：ClientResponse.java
 * @版本信息：1.0.0
 * @日期：2014-2-14-上午10:46:58
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.common;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ClientResponse
 * @类描述：客户端响应结果
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-14 上午10:46:58   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-14 上午10:46:58
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ClientResponse {
	
	/**
	 * @属性名称:success
	 * @属性描述:是否发送成功
	 * @since 1.0.0
	 */
	private boolean success=true;
	/**
	 * @属性名称:responseMsg
	 * @属性描述:响应报文
	 * @since 1.0.0
	 */
	private String responseMsg;
	
	/**
	 * @属性名称:timeout
	 * @属性描述:是否超时
	 * @since 1.0.0
	 */
	private boolean timeout;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public boolean isTimeout() {
		return timeout;
	}
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public String toString(){
		return "success:"+success+";"+
				"responseMsg:"+responseMsg+";"+
				"timeout:"+timeout+";";
	}
	
}
