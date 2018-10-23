/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.model
 * @�ļ�����TxModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-10-10:56:55
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.model;

import java.util.List;
import java.util.Map;

import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.domain.txp.TxMsg;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeAttrCt;
import com.ytec.mdm.domain.txp.TxMsgNodeFilter;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.domain.txp.TxMsgNodeTabsRel;

/**
 * The Class TxModel.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�TxModel
 * @�����������׶���ģ��
 * @��������:���׶���ģ�ͣ��������ڴ��л�����������������Ϣ
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:30:18
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:30:18
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class TxModel {

	/**
	 * The tx def.
	 * 
	 * @��������: ���׶���
	 */
	private TxDef txDef;
	
	/**
	 * The req tx msg.
	 * 
	 * @��������:������
	 */
	private TxMsg reqTxMsg;
	
	/**
	 * The res tx msg.
	 * 
	 * @��������:��Ӧ����
	 */
	private TxMsg resTxMsg;

	/**
	 * The req tx msg node list.
	 * 
	 * @��������:�����Ľڵ�
	 */
	private List<TxMsgNode> reqTxMsgNodeList;
	
	/**
	 * The res tx msg node list.
	 * 
	 * @��������:��Ӧ���Ľڵ�
	 */
	private List<TxMsgNode> resTxMsgNodeList;

	/**
	 * The tx msg node attr map.
	 * 
	 * @��������:���Ľڵ�����
	 */
	private Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap;
	
	/**
	 * The tx msg node tab map map.
	 * 
	 * @��������:���Ľڵ��Ӧ�����ݿ��
	 */
	private Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap;
	
	/**
	 * The tx msg node filter map.
	 * 
	 * @��������:���Ľڵ����ݿ���������
	 */
	private Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap;
	
	/**
	 * The tx msg node tabs rel map.
	 * 
	 * @��������:���Ľڵ����ݿ�������ϵ
	 */
	private Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabsRelMap;
	
	/**
	 * The tx msg node attr ct map.
	 * 
	 * @��������:���Ľڵ�����ת��У������
	 */
	private Map<Long, List<TxMsgNodeAttrCt>>  txMsgNodeAttrCtMap;

	/**
	 * Gets the tx def.
	 * 
	 * @return the tx def
	 */
	public TxDef getTxDef() {
		return txDef;
	}

	/**
	 * Sets the tx def.
	 * 
	 * @param txDef
	 *            the new tx def
	 */
	public void setTxDef(TxDef txDef) {
		this.txDef = txDef;
	}

	/**
	 * Gets the req tx msg.
	 * 
	 * @return the req tx msg
	 */
	public TxMsg getReqTxMsg() {
		return reqTxMsg;
	}

	/**
	 * Sets the req tx msg.
	 * 
	 * @param reqTxMsg
	 *            the new req tx msg
	 */
	public void setReqTxMsg(TxMsg reqTxMsg) {
		this.reqTxMsg = reqTxMsg;
	}

	/**
	 * Gets the res tx msg.
	 * 
	 * @return the res tx msg
	 */
	public TxMsg getResTxMsg() {
		return resTxMsg;
	}

	/**
	 * Sets the res tx msg.
	 * 
	 * @param resTxMsg
	 *            the new res tx msg
	 */
	public void setResTxMsg(TxMsg resTxMsg) {
		this.resTxMsg = resTxMsg;
	}

	/**
	 * Gets the req tx msg node list.
	 * 
	 * @return the req tx msg node list
	 */
	public List<TxMsgNode> getReqTxMsgNodeList() {
		return reqTxMsgNodeList;
	}

	/**
	 * Sets the req tx msg node list.
	 * 
	 * @param reqTxMsgNodeList
	 *            the new req tx msg node list
	 */
	public void setReqTxMsgNodeList(List<TxMsgNode> reqTxMsgNodeList) {
		this.reqTxMsgNodeList = reqTxMsgNodeList;
	}

	/**
	 * Gets the res tx msg node list.
	 * 
	 * @return the res tx msg node list
	 */
	public List<TxMsgNode> getResTxMsgNodeList() {
		return resTxMsgNodeList;
	}

	/**
	 * Sets the res tx msg node list.
	 * 
	 * @param resTxMsgNodeList
	 *            the new res tx msg node list
	 */
	public void setResTxMsgNodeList(List<TxMsgNode> resTxMsgNodeList) {
		this.resTxMsgNodeList = resTxMsgNodeList;
	}

	/**
	 * Gets the tx msg node attr map.
	 * 
	 * @return the tx msg node attr map
	 */
	public Map<Long, List<TxMsgNodeAttr>> getTxMsgNodeAttrMap() {
		return txMsgNodeAttrMap;
	}

	/**
	 * Sets the tx msg node attr map.
	 * 
	 * @param txMsgNodeAttrMap
	 *            the tx msg node attr map
	 * @��������:void setTxMsgNodeAttrMap(Map<Long,List<TxMsgNodeAttr>>
	 *            txMsgNodeAttrMap)
	 * @��������:
	 * @�����뷵��˵��: void setTxMsgNodeAttrMap(Map<Long,List<TxMsgNodeAttr>>
	 *           txMsgNodeAttrMap)
	 * @�㷨����:
	 */
	public void setTxMsgNodeAttrMap(
			Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap) {
		this.txMsgNodeAttrMap = txMsgNodeAttrMap;
	}

	/**
	 * Gets the tx msg node tab map map.
	 * 
	 * @return the tx msg node tab map map
	 */
	public Map<Long, List<TxMsgNodeTabMap>> getTxMsgNodeTabMapMap() {
		return txMsgNodeTabMapMap;
	}

	/**
	 * Sets the tx msg node tab map map.
	 * 
	 * @param txMsgNodeTabMapMap
	 *            the tx msg node tab map map
	 * @��������:void setTxMsgNodeTabMapMap(Map<Long,List<TxMsgNodeTabMap>>
	 *            txMsgNodeTabMapMap)
	 * @��������:
	 * @�����뷵��˵��: void setTxMsgNodeTabMapMap(Map<Long,List<TxMsgNodeTabMap>>
	 *           txMsgNodeTabMapMap)
	 * @�㷨����:
	 */
	public void setTxMsgNodeTabMapMap(
			Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap) {
		this.txMsgNodeTabMapMap = txMsgNodeTabMapMap;
	}

	/**
	 * Gets the tx msg node filter map.
	 * 
	 * @return the tx msg node filter map
	 */
	public Map<Long, List<TxMsgNodeFilter>> getTxMsgNodeFilterMap() {
		return txMsgNodeFilterMap;
	}

	/**
	 * Sets the tx msg node filter map.
	 * 
	 * @param txMsgNodeFilterMap
	 *            the tx msg node filter map
	 * @��������:void setTxMsgNodeFilterMap(Map<Long,List<TxMsgNodeFilter>>
	 *            txMsgNodeFilterMap)
	 * @��������:
	 * @�����뷵��˵��: void setTxMsgNodeFilterMap(Map<Long,List<TxMsgNodeFilter>>
	 *           txMsgNodeFilterMap)
	 * @�㷨����:
	 */
	public void setTxMsgNodeFilterMap(
			Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap) {
		this.txMsgNodeFilterMap = txMsgNodeFilterMap;
	}

	/**
	 * Gets the tx msg node tabs rel map.
	 * 
	 * @return the tx msg node tabs rel map
	 */
	public Map<Long, List<TxMsgNodeTabsRel>> getTxMsgNodeTabsRelMap() {
		return txMsgNodeTabsRelMap;
	}

	/**
	 * Sets the tx msg node tabs rel map.
	 * 
	 * @param txMsgNodeTabRelMap
	 *            the tx msg node tab rel map
	 * @��������:void setTxMsgNodeTabsRelMap(Map<Long,List<TxMsgNodeTabsRel>>
	 *            txMsgNodeTabRelMap)
	 * @��������:
	 * @�����뷵��˵��: void setTxMsgNodeTabsRelMap(Map<Long,List<TxMsgNodeTabsRel>>
	 *           txMsgNodeTabRelMap)
	 * @�㷨����:
	 */
	public void setTxMsgNodeTabsRelMap(
			Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabRelMap) {
		this.txMsgNodeTabsRelMap = txMsgNodeTabRelMap;
	}

	/**
	 * Gets the tx msg node attr ct map.
	 * 
	 * @return the tx msg node attr ct map
	 */
	public Map<Long, List<TxMsgNodeAttrCt>> getTxMsgNodeAttrCtMap() {
		return txMsgNodeAttrCtMap;
	}

	/**
	 * Sets the tx msg node attr ct map.
	 * 
	 * @param txMsgNodeAttrCtMap
	 *            the tx msg node attr ct map
	 * @��������:void setTxMsgNodeAttrCtMap(Map<Long,List<TxMsgNodeAttrCt>>
	 *            txMsgNodeAttrCtMap)
	 * @��������:
	 * @�����뷵��˵��: void setTxMsgNodeAttrCtMap(Map<Long,List<TxMsgNodeAttrCt>>
	 *           txMsgNodeAttrCtMap)
	 * @�㷨����:
	 */
	public void setTxMsgNodeAttrCtMap(
			Map<Long, List<TxMsgNodeAttrCt>> txMsgNodeAttrCtMap) {
		this.txMsgNodeAttrCtMap = txMsgNodeAttrCtMap;
	}

}
