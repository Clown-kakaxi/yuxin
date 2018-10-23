/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.bs
 * @�ļ�����TxConfigBS.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:04:57
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.transaction.bs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxColDef;
import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.domain.txp.TxMsg;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeAttrCt;
import com.ytec.mdm.domain.txp.TxMsgNodeFilter;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.domain.txp.TxMsgNodeTabsRel;
import com.ytec.mdm.domain.txp.TxTabDef;
import com.ytec.mdm.integration.transaction.model.TxModel;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�TxConfigBS
 * @������������������ص�ҵ������
 * @��������:����������ص�ҵ������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:04:57
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:04:57
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class TxConfigBS {

	protected static Logger log = LoggerFactory.getLogger(TxConfigBS.class);

	@PersistenceContext
	private EntityManager em;

	// private final AtomicBoolean daoInit = new
	// AtomicBoolean(false);//DAO�Ƿ��Ѿ���ʼ��

	private JPABaseDAO<TxDef, Long> txDefDAO = null;// �������ò�ѯ
	private JPABaseDAO<TxMsg, Long> txMsgDAO = null;// ���Ĳ�ѯ
	private JPABaseDAO<TxMsgNode, Long> txMsgNodeDAO = null;// ���Ľڵ��ѯ
	private JPABaseDAO<TxMsgNodeAttr, Long> txMsgNodeAttrDAO = null;// ���Ľڵ����Բ�ѯ
	private JPABaseDAO<TxMsgNodeTabMap, Long> txMsgNodeTabMapDAO = null;// ���Ľڵ����ݿ��ӳ���ѯ
	private JPABaseDAO<TxMsgNodeFilter, Long> txMsgNodeFilterDAO = null;// ���Ľڵ����ݿ�����������ѯ
	private JPABaseDAO<TxMsgNodeTabsRel, Long> txMsgNodeTabRelDAO = null;// ���Ľڵ����ݿ����ϵ��ѯ
	private JPABaseDAO<TxMsgNodeAttrCt, Long> txMsgNodeAttrCtDAO = null;// ���Ľڵ�����ת�����ò�ѯ
	private JPABaseDAO<TxTabDef, Long> tabDefDAO = null;// �����ѯ
	private JPABaseDAO<TxColDef, Long> colDefDAO = null;// �ֶζ����ѯ

	public TxConfigBS() {

	}

	/**
	 * ���ݽ������ʼ������ģ�Ͷ���
	 * 
	 * @param txCode
	 * @return
	 */
	public TxModel getTxModel(String txCode) {
		log.debug("���ݽ��ױ��:{}���콻��������Ϣ.", txCode);
		TxModel txModel = new TxModel();
		// ������Ϣ
		TxDef txDef = txDefDAO.findUniqueByProperty("txCode", txCode);

		if (txDef == null) {
			log.error("���ױ��:{} ��Ӧ�Ľ���������Ϣ������,����", txCode);
			throw new BizException("����" + txCode + "������");
		}

		txModel.setTxDef(txDef);

		// ������Ӧ����
		List<TxMsg> txMsgList = txMsgDAO.findByProperty("txId", txDef.getTxId());

		for (int i = 0; i < txMsgList.size(); i++) {

			TxMsg txMsg = txMsgList.get(i);

			if (MdmConstants.MSG_TYPE_REQ.equals(txMsg.getMsgTp())) {// ������

				txModel.setReqTxMsg(txMsg);

			} else if (MdmConstants.MSG_TYPE_RES.equals(txMsg.getMsgTp())) {// ��Ӧ����

				txModel.setResTxMsg(txMsg);
			}
		}

		Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap = new HashMap<Long, List<TxMsgNodeAttr>>();
		Map<Long, List<TxMsgNodeAttrCt>> txMsgNodeAttrCtMap = new HashMap<Long, List<TxMsgNodeAttrCt>>();
		Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap = new HashMap<Long, List<TxMsgNodeTabMap>>();
		Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabRelMap = new HashMap<Long, List<TxMsgNodeTabsRel>>();
		Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap = new HashMap<Long, List<TxMsgNodeFilter>>();

		txModel.setTxMsgNodeAttrMap(txMsgNodeAttrMap);
		txModel.setTxMsgNodeAttrCtMap(txMsgNodeAttrCtMap);
		txModel.setTxMsgNodeTabsRelMap(txMsgNodeTabRelMap);
		txModel.setTxMsgNodeTabMapMap(txMsgNodeTabMapMap);
		txModel.setTxMsgNodeFilterMap(txMsgNodeFilterMap);

		TxMsgNode txMsgNode = null;
		// �����Ľڵ�
		TxMsg reqTxMsg = txModel.getReqTxMsg();
		if (null != reqTxMsg) {
			List<TxMsgNode> reqTxMsgNodeList = txMsgNodeDAO.findByPropertyAndOrder("msgId", txModel.getReqTxMsg()
					.getMsgId(), "nodeSeq", false);
			txModel.setReqTxMsgNodeList(reqTxMsgNodeList);

			// ���Ľڵ�����

			for (int i = 0; i < reqTxMsgNodeList.size(); i++) {

				txMsgNode = reqTxMsgNodeList.get(i);
				/** ���������ĸ���� **/
				if (txMsgNode.getUpNodeId() == -1 && txModel.getReqTxMsg() != null) {
					txModel.getReqTxMsg().setMainMsgRoot(txMsgNode.getNodeCode());
				}

				findNodeInfo(txMsgNodeAttrMap, txMsgNodeAttrCtMap, txMsgNodeTabMapMap, txMsgNodeTabRelMap,
						txMsgNodeFilterMap, txMsgNode);

			}
		}

		// ��Ӧ�ڵ�
		TxMsg resTxMsg = txModel.getResTxMsg();
		if (null != resTxMsg) {
			List<TxMsgNode> resTxMsgNodeList = txMsgNodeDAO.findByPropertyAndOrder("msgId", txModel.getResTxMsg()
					.getMsgId(), "nodeSeq", false);
			txModel.setResTxMsgNodeList(resTxMsgNodeList);

			// ���Ľڵ�����
			for (int i = 0; i < resTxMsgNodeList.size(); i++) {

				txMsgNode = resTxMsgNodeList.get(i);
				/** ���������ĸ���� **/
				if (txMsgNode.getUpNodeId() == -1 && txModel.getResTxMsg() != null) {
					txModel.getResTxMsg().setMainMsgRoot(txMsgNode.getNodeCode());
				}
				findNodeInfo(txMsgNodeAttrMap, txMsgNodeAttrCtMap, txMsgNodeTabMapMap, txMsgNodeTabRelMap,
						txMsgNodeFilterMap, txMsgNode);

			}
		}

		log.debug("���ݽ��ױ��:{}���콻��������Ϣ���.", txCode);

		return txModel;
	}

	/**
	 * ��ʼ��DAO
	 */
	@PostConstruct
	public void initDAO() {

		log.info("��ʼ���������ô�����");
		// if(this.daoInit.compareAndSet(false, true)){
		txDefDAO = new JPABaseDAO<TxDef, Long>(em, TxDef.class);
		txMsgDAO = new JPABaseDAO<TxMsg, Long>(em, TxMsg.class);
		txMsgNodeDAO = new JPABaseDAO<TxMsgNode, Long>(em, TxMsgNode.class);
		txMsgNodeAttrDAO = new JPABaseDAO<TxMsgNodeAttr, Long>(em, TxMsgNodeAttr.class);
		txMsgNodeAttrCtDAO = new JPABaseDAO<TxMsgNodeAttrCt, Long>(em, TxMsgNodeAttrCt.class);
		txMsgNodeTabMapDAO = new JPABaseDAO<TxMsgNodeTabMap, Long>(em, TxMsgNodeTabMap.class);
		txMsgNodeFilterDAO = new JPABaseDAO<TxMsgNodeFilter, Long>(em, TxMsgNodeFilter.class);
		txMsgNodeTabRelDAO = new JPABaseDAO<TxMsgNodeTabsRel, Long>(em, TxMsgNodeTabsRel.class);
		tabDefDAO = new JPABaseDAO<TxTabDef, Long>(em, TxTabDef.class);
		colDefDAO = new JPABaseDAO<TxColDef, Long>(em, TxColDef.class);
		// }
	}

	/**
	 * ��ѯ�ڵ�������Ϣ
	 * 
	 * @param txMsgNodeAttrMap
	 * @param txMsgNodeAttrCtMap
	 * @param txMsgNodeTabMapMap
	 * @param txMsgNodeTabRelMap
	 * @param txMsgNodeFilterMap
	 * @param nodeId
	 */
	private void findNodeInfo(Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap,
			Map<Long, List<TxMsgNodeAttrCt>> txMsgNodeAttrCtMap, Map<Long, List<TxMsgNodeTabMap>> txMsgNodeTabMapMap,
			Map<Long, List<TxMsgNodeTabsRel>> txMsgNodeTabRelMap, Map<Long, List<TxMsgNodeFilter>> txMsgNodeFilterMap,
			TxMsgNode txMsgNode) {

		Long nodeId = txMsgNode.getNodeId();

		Map<String, String> tabNameMap = new HashMap<String, String>();// ���ݿ��
																		// ����-����ӳ���ϵ
		Map<String, TxColDef> colNameMap = new HashMap<String, TxColDef>();// ���ݿ��ֶ�
																			// ����-����ӳ���ϵ

		// ��ѯ�ڵ��Ӧ��
		List<TxMsgNodeTabMap> txMsgNodeTabMapList = txMsgNodeTabMapDAO.findByProperty("nodeId", nodeId);

		if (txMsgNodeTabMapList != null && txMsgNode.getNodeTp().equals(MdmConstants.NODE_TP_T)) {

			// ����Ԫ�����ҳ���Ӧ�ı����������
			for (TxMsgNodeTabMap txMsgNodeTabMap : txMsgNodeTabMapList) {

				TxTabDef tabDef = this.tabDefDAO.findUniqueByProperty("tabId", txMsgNodeTabMap.getTabId());
				if (tabDef == null) {

					log.error("TxMsgNodeTabMap,�Ҳ���idΪ:{} ��Ӧ��Ԫ���ݱ���Ϣ��", txMsgNodeTabMap.getTabId());
					throw new BizException("����Ԫ���ݱ���Ϣʧ��");
				} else {

					tabNameMap.put(tabDef.getTabId().toString(), tabDef.getTabName());
					txMsgNodeTabMap.setTabName(tabDef.getTabName());
					txMsgNodeTabMap.setObjName(tabDef.getObjName());
					// ���ұ���ֶζ�����Ϣ
					List<TxColDef> colDefList = this.colDefDAO.findByProperty("id.tabId", tabDef.getTabId());
					if (colDefList != null) {
						for (TxColDef colDef : colDefList) {
							colNameMap.put(tabDef.getTabId().toString() + colDef.getId().getColId().toString(), colDef);
						}
					}
				}

			}
		}

		txMsgNodeTabMapMap.put(nodeId, txMsgNodeTabMapList);

		// ��ѯ�ڵ���Ĺ�����ϵ
		List<TxMsgNodeTabsRel> txMsgNodeTabRelList = txMsgNodeTabRelDAO.findByProperty("nodeId", nodeId);

		if (txMsgNodeTabRelList != null && txMsgNode.getNodeTp().equals(MdmConstants.NODE_TP_T)) {
			TxColDef temp = null;
			String colName1 = null;
			String colName2 = null;
			for (TxMsgNodeTabsRel txMsgNodeTabsRel : txMsgNodeTabRelList) {

				txMsgNodeTabsRel.setTabName1(tabNameMap.get(txMsgNodeTabsRel.getTabId1().toString()));
				txMsgNodeTabsRel.setTabName2(tabNameMap.get(txMsgNodeTabsRel.getTabId2().toString()));
				if ((temp = colNameMap.get(txMsgNodeTabsRel.getTabId1().toString()
						+ txMsgNodeTabsRel.getColId1().toString())) != null) {
					colName1 = temp.getColName();
				} else {
					colName1 = null;
				}

				if ((temp = colNameMap.get(txMsgNodeTabsRel.getTabId2().toString()
						+ txMsgNodeTabsRel.getColId2().toString())) != null) {
					colName2 = temp.getColName();
				} else {
					colName2 = null;
				}
				if (colName1 == null || colName2 == null) {
					log.error("TxMsgNodeTabsRel,�Ҳ���idΪ:{}����{} ��Ӧ��Ԫ���ݱ��ֶ���Ϣ��", txMsgNodeTabsRel.getColId1(),
							txMsgNodeTabsRel.getColId2());
					throw new BizException("����Ԫ���ݱ��ֶ���Ϣʧ��");
				}

				txMsgNodeTabsRel.setColName1(colName1);
				txMsgNodeTabsRel.setColName2(colName2);

			}
		}
		txMsgNodeTabRelMap.put(nodeId, txMsgNodeTabRelList);
		// ��ѯ�ڵ��Ĺ�������
		List<TxMsgNodeFilter> txMsgNodeFilterList = txMsgNodeFilterDAO.findByProperty("nodeId", nodeId);

		if (txMsgNodeFilterList != null) {
			TxColDef temp = null;
			String colName = null;
			for (TxMsgNodeFilter txMsgNodeFilter : txMsgNodeFilterList) {// ------------------------------
				txMsgNodeFilter.setTabName(tabNameMap.get(txMsgNodeFilter.getTabId().toString()));
				
				if ((temp = colNameMap.get(txMsgNodeFilter.getTabId().toString()
						+ txMsgNodeFilter.getColId().toString())) != null) {
					colName = temp.getColName();
				} else {
					colName = null;
				}
				if (colName == null) {
					log.error("TxMsgNodeFilter,�Ҳ�����id:{},�ֶ�id:{} ��Ӧ��Ԫ���ݱ��ֶ���Ϣ��", txMsgNodeFilter.getTabId(),
							txMsgNodeFilter.getColId());
					throw new BizException("����Ԫ���ݱ��ֶ���Ϣʧ��");
				}
				txMsgNodeFilter.setColName(colName);
				if (txMsgNodeFilter.getAttrId() != null && txMsgNodeFilter.getAttrId() != 0) {
					TxMsgNodeAttr reqTxMsgNodeAttr = this.txMsgNodeAttrDAO.get(txMsgNodeFilter.getAttrId());
					if (reqTxMsgNodeAttr == null || txMsgNodeAttrMap.get(reqTxMsgNodeAttr.getNodeId()) == null) {
						log.error("TxMsgNodeFilter,���˶�Ӧ������Դ���");
						throw new BizException("TxMsgNodeFilter,���˶�Ӧ������Դ���");
					}
					txMsgNodeFilter.setReqAttrCode(reqTxMsgNodeAttr.getAttrCode());
					txMsgNodeFilter.setReqAttrDataType(reqTxMsgNodeAttr.getDataType());
					txMsgNodeFilter.setReqAttrDataFmt(reqTxMsgNodeAttr.getDataFmt());
					txMsgNodeFilter.setReqAttrNullAble(reqTxMsgNodeAttr.getNulls());
					txMsgNodeFilter.setDefaultVal(reqTxMsgNodeAttr.getDefaultVal());
				} else if (!StringUtil.isEmpty(txMsgNodeFilter.getFilterConditions())) {
					txMsgNodeFilter.setReqAttrCode(txMsgNodeFilter.getTabId().toString()
							+ txMsgNodeFilter.getColId().toString());
					String DataType = convertColDatatype2AttrDatatype(temp.getDataType(), temp.getDataPrec());
					if (StringUtil.isEmpty(DataType)) {
						log.error("TxMsgNodeFilter,��Ӧ�������Ͳ�ȷ��");
						throw new BizException("TxMsgNodeFilter,��Ӧ�������Ͳ�ȷ��");
					}
					txMsgNodeFilter.setReqAttrDataType(DataType);
					txMsgNodeFilter.setReqAttrNullAble("N");
				} else {
					log.error("TxMsgNodeFilter,û������������Ӧ�Ĺ�������");
					throw new BizException("TxMsgNodeFilter,û������������Ӧ�Ĺ�������");
				}
			}
		}

		txMsgNodeFilterMap.put(nodeId, txMsgNodeFilterList);

		// ��ѯ�ڵ�����
		List<TxMsgNodeAttr> txMsgNodeAtrrList = txMsgNodeAttrDAO.findByProperty("nodeId", nodeId);

		List<Long> attrIdList = new ArrayList<Long>();// �ڵ�����ID���ϣ����ں��湦�ܲ�ѯ

		// ֻ�е��ڵ��ǻ������ݿ��ʱ���Ų��ҽڵ���ڵ����ݿ��ֶ�
		if (txMsgNodeAtrrList != null && txMsgNode.getNodeTp().equals(MdmConstants.NODE_TP_T)) {
			TxColDef temp = null;
			String colName = null;
			for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAtrrList) {

				// attrIdList.add(txMsgNodeAttr.getAttrId());
				// ���������Ķ��ԣ�����ڵ����Բ��������ݿ���򲻴���
				if (txMsgNodeAttr.getColId() == null || txMsgNodeAttr.getTabId() == null
						|| txMsgNodeAttr.getTabId() == 0) {
					continue;
				}

				if (txMsgNodeAttr.getFkAttrId() != null && txMsgNodeAttr.getFkAttrId() != 0) {

					TxMsgNodeAttr fkTxMsgNodeAttr = txMsgNodeAttrDAO.get(txMsgNodeAttr.getFkAttrId());
					if (fkTxMsgNodeAttr == null || !txMsgNode.getUpNodeId().equals(fkTxMsgNodeAttr.getNodeId())) {
						log.error("TxMsgNodeAttr,���ڵ��Ӧ����");
						throw new BizException("TxMsgNodeAttr,���ڵ��Ӧ����");
					}
					txMsgNodeAttr.setFkNodeId(fkTxMsgNodeAttr.getNodeId());
					txMsgNodeAttr.setFkAttrCode(fkTxMsgNodeAttr.getAttrCode());
				}

				txMsgNodeAttr.setTabName(tabNameMap.get(txMsgNodeAttr.getTabId().toString()));

				if ((temp = colNameMap.get(txMsgNodeAttr.getTabId().toString() + txMsgNodeAttr.getColId().toString())) != null) {
					colName = temp.getColName();
				} else {
					colName = null;
				}
				if (colName == null) {
					log.error("TxMsgNodeAttr,�Ҳ���id:{} ��Ӧ��Ԫ���ݱ��ֶ���Ϣ��", txMsgNodeAttr.getColId());
					throw new BizException("����Ԫ���ݱ��ֶ���Ϣʧ��");
				}

				txMsgNodeAttr.setColName(colName);
			}
		}

		if (txMsgNodeAtrrList != null) {
			for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAtrrList) {
				attrIdList.add(txMsgNodeAttr.getAttrId());
			}
		}

		txMsgNodeAttrMap.put(nodeId, txMsgNodeAtrrList);

		// ���ҽڵ�����У�顢ת����������
		// Ϊ�˼������ݿ��ѯ�Ĵ�������һ�β�ѯ�ýڵ����е����ã�Ȼ��ֵ���������
		// ��ѯ�ڵ�����
		List<TxMsgNodeAttrCt> txMsgNodeAtrrCtList = txMsgNodeAttrCtDAO.findByPropertyAndOrderWithParams("attrId",
				attrIdList, "ctRule", false);

		if (txMsgNodeAtrrCtList != null) {

			for (TxMsgNodeAttrCt txMsgNodeAttrCt : txMsgNodeAtrrCtList) {

				List<TxMsgNodeAttrCt> txMsgNodeAttrCtList = txMsgNodeAttrCtMap.get(txMsgNodeAttrCt.getAttrId());
				if (txMsgNodeAttrCtList == null) {
					txMsgNodeAttrCtList = new ArrayList<TxMsgNodeAttrCt>();
					txMsgNodeAttrCtMap.put(txMsgNodeAttrCt.getAttrId(), txMsgNodeAttrCtList);
				}

				txMsgNodeAttrCtList.add(txMsgNodeAttrCt);
			}
		}

	}

	/**
	 * �ַ���(�̶�����):'10' �ַ���(�ɱ䳤��):'20' ���� :'30' ������ :'40' ������ :'50' ʱ���� :'60' ʱ�����
	 * :'70' ���ı��� :'80'
	 */
	private String convertColDatatype2AttrDatatype(String datatype, Long dataprec) {
		if (StringUtil.isEmpty(datatype)) {
			return datatype;
		} else if (datatype.equals("VARCHAR") || datatype.equals("VARCHAR2")) {
			return "20";
		} else if (datatype.equals("BIGINT")) {
			return "30";
		} else if (datatype.equals("INT")) {
			return "30";
		} else if (datatype.equals("INTEGER")) {
			return "30";
		} else if (datatype.startsWith("NUMBER")) {
			if (dataprec == null || dataprec.intValue() == 0) { // ����Ϊ0
				return "30";
			} else { // ���Ȳ�Ϊ0
				return "40";
			}
			// }else if(datatype.startsWith("TIME")){
			// return "60";
		} else if (datatype.equals("CHARACTER")) {
			return "10";
		} else if (datatype.equals("CHAR")) {
			return "10";
		} else if (datatype.equals("CLOB")) {
			return "80";
		} else if (datatype.equals("DATE")) {
			return "50";
		} else if (datatype.equals("DECIMAL")) {
			return "40";
		} else if (datatype.equals("BIGDECIMAL")) {
			return "40";
		} else if (datatype.startsWith("TIMESTAMP")) {
			return "70";
		} else {
			return null;
		}
	}

	/**
	 * ���ݽ������ȡ����������Ϣ
	 * 
	 * @param txCode
	 * @return
	 */
	public TxDef getTxDef(String txCode) {

		JPABaseDAO<TxDef, Long> txDefDAO = new JPABaseDAO<TxDef, Long>(em, TxDef.class);
		return txDefDAO.findUniqueByProperty("txCode", txCode);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
