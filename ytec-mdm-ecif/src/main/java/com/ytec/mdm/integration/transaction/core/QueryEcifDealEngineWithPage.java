/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：QueryEcifDealEngineWithPage.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:10:57
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：QueryEcifDealEngineWithPage
 * @类描述：查询交易处理引擎(带分页处理)
 * @功能描述:查询交易引擎 ，解析查询条件，调用查询逻辑
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:10:58   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:10:58
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
@Scope("prototype")
public class QueryEcifDealEngineWithPage extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory
			.getLogger(QueryEcifDealEngineWithPage.class);
	private String authType = null;
	private String authCode = null;
	// 是否所有查询为空
	private boolean isAllQueryNull;

	@SuppressWarnings("unchecked")
	@Override
	public void execute(EcifData data) {
		try {
			isAllQueryNull = true;
			this.ecifData = data;
			this.ecifData.setQueryModelObj(new QueryModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			// 获取查询条件,报文定义属性、请求参数都可以作为查询条件
			Map<String, String> reqParamMap = new HashMap<String, String>(data.getParameterMap());
			if (StringUtil.isEmpty(data.getAuthCode())) {
				authType = reqParamMap.get(MdmConstants.AUTH_TYPE);
				authCode = reqParamMap.get(MdmConstants.AUTH_CODE);
			}else{
				authCode=data.getAuthCode();
				authType = reqParamMap.get(MdmConstants.AUTH_TYPE);
			}
			// 生成响应报文
			List<TxMsgNode> tsMsgNodeList = this.txModel.getResTxMsgNodeList();
			// 存储父节点属性信息
			Map<Long, Object> parentNodeValueMap = new HashMap<Long, Object>();

			// 根节点
			TxMsgNode rootTxMsgNode = null;
			Map<Long, List<TxMsgNode>> txMsgNodeMap = new HashMap<Long, List<TxMsgNode>>();
			List<TxMsgNode> txMsgNodeList = null;

			for (TxMsgNode txMsgNode : tsMsgNodeList) {
				if (txMsgNode.getUpNodeId() == -1) {
					rootTxMsgNode = txMsgNode;
				}
				txMsgNodeList = txMsgNodeMap.get(txMsgNode.getUpNodeId());

				if (txMsgNodeList == null) {
					txMsgNodeList = new ArrayList<TxMsgNode>();
					txMsgNodeMap.put(txMsgNode.getUpNodeId(), txMsgNodeList);
				}
				txMsgNodeList.add(txMsgNode);
			}
			// 开始生成响应报文
			if (rootTxMsgNode == null) {
				data.setStatus(ErrorCode.ERR_XML_CFG_NO_ROOT_NODE.getCode(),"交易配置错误");
				log.warn("交易:{},响应报文配置错误,没有配置根节点!", data.getTxCode());
				return;
			}
			// 交易处理类
			String txDealClass = txModel.getTxDef().getTxDealClass();
			this.bizLogic = (IEcifBizLogic) SpringContextUtils
					.getBean(txDealClass);

			Element responseEle = DocumentHelper.createElement(txModel.getResTxMsg().getMainMsgRoot());

			buildNodeXML(responseEle, rootTxMsgNode, txMsgNodeMap, reqParamMap,
					parentNodeValueMap);
			if (isAllQueryNull && data.isSuccess()) {
				data.setStatus(ErrorCode.WRN_NONE_FOUND);
				data.setSuccess(true);
			}
			if (data.isSuccess()) {
				data.setRepNode((Element)responseEle.element(txModel.getResTxMsg().getMainMsgRoot()).detach());
			}
		} catch (Exception e) {
			log.error("错误信息", e);
			data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
		}
		return;

	}

	
	/**
	 * @函数名称:buildNodeXML
	 * @函数描述:将节点转换为XML
	 * @参数与返回说明:
	 * 		@param pointer
	 * 		@param currentxMsgNode
	 * 		@param txMsgNodeMap
	 * 		@param reqParamMap
	 * 		@param parentNodeValueMap
	 * @算法描述:
	 */
	private void buildNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap,
			Map<String, String> reqParamMap,
			Map<Long, Object> parentNodeValueMap) {
		if (MdmConstants.NODE_TP_T.equals(currentxMsgNode.getNodeTp())) {
			buildTableNodeXML(pointer, currentxMsgNode, txMsgNodeMap,
					reqParamMap, parentNodeValueMap);
			if (!this.ecifData.isSuccess()) {
				return;
			}
		} else {// 普通的节点
			if (MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())) {
				if(!MdmConstants.NODE_GROUP_NO_LABEL.equals(currentxMsgNode.getNodeLabel())){
					pointer = pointer.addElement(currentxMsgNode.getNodeCode()+ MdmConstants.NODE_GROUP_SUFFIX);
				}
			} else {
				if (!MdmConstants.NODE_TP_T.equals(currentxMsgNode.getNodeTp())) {
					pointer = pointer.addElement(currentxMsgNode.getNodeCode());
				}

			}
			buildCommonNodeXML(pointer, currentxMsgNode, reqParamMap);

			buildChildNodeXML(pointer, currentxMsgNode, txMsgNodeMap,
					reqParamMap, parentNodeValueMap);
		}
	}

	/**
	 * @param resXMlBuf
	 * @param currentxMsgNode
	 * @param txMsgNodeMap
	 * @param reqParamMap
	 * @param tabStr
	 * @param parentNodeValueMap
	 *            父节点的属性值 ,用于外键查询
	 */
	private void buildChildNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap,
			Map<String, String> reqParamMap,
			Map<Long, Object> parentNodeValueMap) {
		// 当前节点下的所有子节点
		List<TxMsgNode> txMsgNodeList = txMsgNodeMap.get(currentxMsgNode
				.getNodeId());
		if (txMsgNodeList != null) {
			for (TxMsgNode txMsgNode : txMsgNodeList) {
				buildNodeXML(pointer, txMsgNode, txMsgNodeMap, reqParamMap,
						parentNodeValueMap);
				if (!this.ecifData.isSuccess()) {
					return;
				}
			}
		}
	}

	
	/**
	 * @函数名称:buildCommonNodeXML
	 * @函数描述:构造普通节点的XML报文
	 * @参数与返回说明:
	 * 		@param pointer
	 * 		@param currentxMsgNode
	 * 		@param reqParamMap
	 * @算法描述:
	 */
	private void buildCommonNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<String, String> reqParamMap) {
		Long nodeId = currentxMsgNode.getNodeId();
		// 属性字段
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel
				.getTxMsgNodeAttrMap().get(nodeId);
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			if(txMsgNodeAttr.getDataLen()==null||txMsgNodeAttr.getDataLen()==0){
				continue;
			}
			String attrCode = txMsgNodeAttr.getAttrCode();
			String value = "";
			if (txMsgNodeAttr.getDefaultVal() != null) {
				value = txMsgNodeAttr.getDefaultVal();
			} else {
				value = reqParamMap.get(attrCode);
			}
			pointer.addElement(attrCode).setText(StringUtil.toString(value));
		}

	}

	
	/**
	 * @函数名称:buildTableNodeXML
	 * @函数描述:构造基于数据库表的节点的XML报文
	 * @参数与返回说明:
	 * 		@param pointer
	 * 		@param currentxMsgNode
	 * 		@param txMsgNodeMap
	 * 		@param reqParamMap
	 * 		@param parentNodeValueMap
	 * @算法描述:
	 */
	private void buildTableNodeXML(Element pointer, TxMsgNode currentxMsgNode,
			Map<Long, List<TxMsgNode>> txMsgNodeMap,
			Map<String, String> reqParamMap,
			Map<Long, Object> parentNodeValueMap) {
		// 基于数据库表的节点
		Long nodeId = currentxMsgNode.getNodeId();
		String nodeCode = currentxMsgNode.getNodeCode();
		boolean fkAttrIdValueNull=false;
		// 查询字段
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel
				.getTxMsgNodeAttrMap().get(nodeId);
		if (txMsgNodeAttrList == null || txMsgNodeAttrList.size() == 0) {
			String msg = "报文节点:" + currentxMsgNode.getNodeCode() + "没有查询内容";
			log.warn(msg);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"没有配置返回项");
			return;
		}
		Map<String, Object> requestMap = new HashMap<String, Object>();
		List<String> queryFieldList = new ArrayList<String>();
		/**构造查询内容*/
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			queryFieldList.add(txMsgNodeAttr.getAttrCode());
			// 判断是否使用了外键查询条件
			if (txMsgNodeAttr.getFkAttrId() != null
					&& txMsgNodeAttr.getFkAttrId() != 0) {
				Object value = parentNodeValueMap.get(txMsgNodeAttr.getFkAttrId());
				if (StringUtil.isEmpty(value)) {
					fkAttrIdValueNull=true;
					log.warn("报文节点:{},查询属性:{}找不到外键查询条件",nodeCode,txMsgNodeAttr.getAttrCode());
				} else {
					String dataType = txMsgNodeAttr.getDataType();
					if (MdmConstants.NODE_TYPE_FIX_STR.equals(dataType)
							|| MdmConstants.NODE_TYPE_VARI_STR.equals(dataType)) {// 字符型
						value = value.toString();
					} else if (MdmConstants.NODE_TYPE_INT.equals(dataType)) {// 整型
						value = new Long(value.toString());
					} else {
						String msg = "外键参数类型配置错误";
						this.ecifData.setStatus(ErrorCode.ERR_XML_CFG_NO_FK.getCode(),
								msg);
						log.error("结点{}外键参数类型错误,外键类型配置为:{}",nodeCode,dataType);
						return;
					}
					requestMap.put(MdmConstants.QUERY_SQL_FILTER_FK_FLAG
							+ txMsgNodeAttr.getAttrCode(), value);
				}
			}

		}
		this.ecifData.getQueryModelObj().setQueryFieldList(queryFieldList);
		/**
		 * 获取过滤条件数据
		 */
		getFilterParam(currentxMsgNode,requestMap,reqParamMap);
		if (!this.ecifData.isSuccess()) {
			return;
		}
		this.ecifData.getQueryModelObj().setParentNodeValueMap(requestMap);
		// ======================构造查询条件===========end
		//构造查询语句
		this.ecifData.getQueryModelObj().setQuerySql(TxMsgModeQuerySQLHolder.getQuerySQL(this.txModel.getTxDef()
						.getTxCode(), nodeId));
		boolean nodeGroupFlag = false;
		if (!MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup())) {
			nodeGroupFlag = false;
		} else {
			nodeGroupFlag = true;
		}
		if(nodeGroupFlag){
			/**********设置分页查询起始***/
			Element p=(Element)this.ecifData.getBodyNode().selectSingleNode("//"+nodeCode+"PageInfo/currentPage");
			if(p!=null&&!StringUtil.isEmpty(p.getTextTrim())){
				this.ecifData.getQueryModelObj().setPageStart(Integer.valueOf(p.getTextTrim()));
			}else{
				this.ecifData.getQueryModelObj().setPageStart(1);
			}
			p=(Element)this.ecifData.getBodyNode().selectSingleNode("//"+nodeCode+"PageInfo/pageSize");
			if(p!=null&&!StringUtil.isEmpty(p.getTextTrim())){
				this.ecifData.getQueryModelObj().setPageSize(Integer.valueOf(p.getTextTrim()));
			}else{
				this.ecifData.getQueryModelObj().setPageSize(MdmConstants.queryMaxsize);
			}
			/***********************/
		}
		this.ecifData.getQueryModelObj().setPageSelect(nodeGroupFlag);
		
		try {
			// 调用查询服务
			if(!fkAttrIdValueNull){
				bizLogic.process(this.ecifData);
			}else{
				this.ecifData.getQueryModelObj().setResultSize(0);
				this.ecifData.getQueryModelObj().setResulList(null);
			}
			if (!this.ecifData.isSuccess()) {
				return;
			}
		} catch (Exception e) {
			log.error("错误信息：", e);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
			return;
		}
		if (this.ecifData.getQueryModelObj().getResultSize() > 1) {
			if (!MdmConstants.NODE_GROUP_M.equals(currentxMsgNode.getNodeGroup()) && !MdmConstants.NODE_NOGROUP_LIST_SUFFIX) {
				this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_TOO_MANY.getCode(), "返回结果不符");
				log.warn("查询返回的结果记录数和配置的不一致，期望的结果为1条");
				return;
			}
			nodeGroupFlag = true;
		}
		if (nodeGroupFlag) {
			if("0".equals(currentxMsgNode.getNodeDisplay())){
				this.ecifData.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), "响应报文配置错误");
				log.warn("节点组配置不可配成影式标签");
				return;
			}
			if (!MdmConstants.NODE_GROUP_NO_LABEL.equals(currentxMsgNode.getNodeLabel())) {//------------------------------
				pointer = pointer.addElement(nodeCode+ MdmConstants.NODE_GROUP_SUFFIX);
			}
		} else {//------------------------------
			if (!MdmConstants.NODE_TP_T.equals(currentxMsgNode.getNodeTp())) {
				if(!"0".equals(currentxMsgNode.getNodeDisplay())){
					pointer = pointer.addElement(nodeCode);
				}
			}

		}
		List<Map<String, Object>> queryResultList = (List<Map<String, Object>>) this.ecifData
				.getQueryModelObj().getResulList();
		boolean noResult = false;
		if (queryResultList == null || queryResultList.size() == 0) {

			String msg = "警告:报文节点" + nodeCode + "没有查询到数据!";
			log.warn(msg);
			// 生成一条空记录
			queryResultList = new ArrayList<Map<String, Object>>();
			queryResultList.add(new HashMap<String, Object>());
			noResult = true;
		} else {
			isAllQueryNull = false;
		}
		
		if(this.ecifData.getQueryModelObj().isPageSelect()){
			/********设置分页信息**************/
			Element pageInfo=pointer.addElement(currentxMsgNode.getNodeCode()+"PageInfo");
			pageInfo.addElement("currentPage").setText(String.valueOf(this.ecifData
				.getQueryModelObj().getPageStart()));
			pageInfo.addElement("pageSize").setText(String.valueOf(this.ecifData
				.getQueryModelObj().getResultSize()));
			pageInfo.addElement("totalSize").setText(String.valueOf(this.ecifData
				.getQueryModelObj().getTotalCount()));
			/**********************/
		}
		
		
		String newValue;
		for (Map<String, Object> rowMap : queryResultList) {
			Element hand = null;
			if (!noResult || !nodeGroupFlag) {
				if(!"0".equals(currentxMsgNode.getNodeDisplay())){
					hand = pointer.addElement(currentxMsgNode.getNodeCode());//------------------------------
				}else{
					hand = pointer;
				}
				for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
					Object value = rowMap.get(txMsgNodeAttr.getAttrCode());
					newValue = null;
					parentNodeValueMap.put(txMsgNodeAttr.getAttrId(), value);
					if (!StringUtil.isEmpty(value)) {
						/*** 信息漂白 ****/
						if (!EcifPubDataUtils.infoLevelRead(authType, authCode,
								MdmConstants.CTRLTYPE_QUERY,
								txMsgNodeAttr.getTabId(),
								txMsgNodeAttr.getColId())) {
							if(StringUtil.isEmpty(MdmConstants.txInfoCtrlformat)){//如果屏蔽占位符为空，标签不要
								continue;
							}
							if(txMsgNodeAttr.getDataLen()!=null&&txMsgNodeAttr.getDataLen()<MdmConstants.txInfoCtrlformat.length()){
								newValue = MdmConstants.txInfoCtrlformat.substring(
										MdmConstants.txInfoCtrlformat.length()-txMsgNodeAttr.getDataLen().intValue());
							}else{
								newValue = MdmConstants.txInfoCtrlformat;
							}
						} else {
							// 转换成字符串
							newValue = this.convertObjectToString(nodeId,
									txMsgNodeAttr, value);
							if (!this.ecifData.isSuccess()) {
								return;
							}
							// 转换
							newValue = this.processCTDR(txMsgNodeAttr, newValue);
						}
					}
					if (!this.ecifData.isSuccess()) {
						return;
					}
					if(txMsgNodeAttr.getDataLen()!=null && txMsgNodeAttr.getDataLen()!=0){
						hand.addElement(txMsgNodeAttr.getAttrCode()).setText(StringUtil.toString(newValue));
					}
				}
			} else {
				hand = pointer;
			}
//			for (String dataKey : rowMap.keySet()) {
//				for (String reqKey : reqParamMap.keySet()) {
//					if (reqKey.equals(dataKey)) {
//						reqParamMap.put(reqKey,
//								String.valueOf(rowMap.get(reqKey)));
//					}
//				}
//			}
			if (!noResult) {
				buildChildNodeXML(hand, currentxMsgNode, txMsgNodeMap,
						reqParamMap, parentNodeValueMap);
			}
		}

	}

}
