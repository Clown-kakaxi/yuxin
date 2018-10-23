/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @�ļ�����FixedAttrs.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-16-����10:01:22
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.sql.Timestamp;
import java.util.List;

import com.ytec.mdm.domain.txp.TxMsgNodeAttr;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�FixedAttrsModel
 * @��������������������ģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-16 ����10:01:22   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-16 ����10:01:22
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FixedAttrsModel {
	/**
	 * @��������:txCode
	 * @��������:������
	 * @since 1.0.0
	 */
	private String txCode;
	/**
	 * @��������:requestRoot
	 * @��������:������ڵ�
	 * @since 1.0.0
	 */
	private String requestRoot;
	/**
	 * @��������:responseRoot
	 * @��������:��Ӧ���ڵ�
	 * @since 1.0.0
	 */
	private String responseRoot;
	/**
	 * @��������:requestAttrList
	 * @��������:����������
	 * @since 1.0.0
	 */
	private List<TxMsgNodeAttr> requestAttrList;
	/**
	 * @��������:responseAttrList
	 * @��������:��Ӧ������
	 * @since 1.0.0
	 */
	private List<TxMsgNodeAttr> responseAttrList;
	/**
	 * @��������:updateTm
	 * @��������:������ʱ��
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
