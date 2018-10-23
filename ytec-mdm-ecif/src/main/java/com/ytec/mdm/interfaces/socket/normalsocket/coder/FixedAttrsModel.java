/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @文件名：FixedAttrs.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-上午10:01:22
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.sql.Timestamp;
import java.util.List;

import com.ytec.mdm.domain.txp.TxMsgNodeAttr;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：FixedAttrsModel
 * @类描述：定长报文属性模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 上午10:01:22   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 上午10:01:22
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class FixedAttrsModel {
	/**
	 * @属性名称:txCode
	 * @属性描述:交易码
	 * @since 1.0.0
	 */
	private String txCode;
	/**
	 * @属性名称:requestRoot
	 * @属性描述:请求根节点
	 * @since 1.0.0
	 */
	private String requestRoot;
	/**
	 * @属性名称:responseRoot
	 * @属性描述:响应根节点
	 * @since 1.0.0
	 */
	private String responseRoot;
	/**
	 * @属性名称:requestAttrList
	 * @属性描述:请求数据域
	 * @since 1.0.0
	 */
	private List<TxMsgNodeAttr> requestAttrList;
	/**
	 * @属性名称:responseAttrList
	 * @属性描述:相应数据域
	 * @since 1.0.0
	 */
	private List<TxMsgNodeAttr> responseAttrList;
	/**
	 * @属性名称:updateTm
	 * @属性描述:最后更新时间
	 * @since 1.0.0
	 */
	private Timestamp updateTm;
	
	
	public FixedAttrsModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getRequestRoot() {
		return requestRoot;
	}
	public void setRequestRoot(String requestRoot) {
		this.requestRoot = requestRoot;
	}
	public String getResponseRoot() {
		return responseRoot;
	}
	public void setResponseRoot(String responseRoot) {
		this.responseRoot = responseRoot;
	}
	public List<TxMsgNodeAttr> getRequestAttrList() {
		return requestAttrList;
	}
	public void setRequestAttrList(List<TxMsgNodeAttr> requestAttrList) {
		this.requestAttrList = requestAttrList;
	}
	public List<TxMsgNodeAttr> getResponseAttrList() {
		return responseAttrList;
	}
	public void setResponseAttrList(List<TxMsgNodeAttr> responseAttrList) {
		this.responseAttrList = responseAttrList;
	}
	public Timestamp getUpdateTm() {
		return updateTm;
	}
	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}
	
}
