/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：WriteEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:18:05
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.util.EcifPubDataUtils;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.domain.txp.TxMsgNodeTabMap;
import com.ytec.mdm.integration.check.bs.TxBizRuleFactory;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：WriteEcifDealEngine
 * @类描述：写交易处理引擎
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:18:05
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:18:05
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WriteEcifDealEngine extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory.getLogger(WriteEcifDealEngine.class);
	private Map<Long, List<TxMsgNode>> txMsgNodeMap = new TreeMap<Long, List<TxMsgNode>>();
	/**
	 * @属性名称:authType
	 * @属性描述:数据权限类型
	 * @since 1.0.0
	 */
	private String authType = null;
	/**
	 * @属性名称:authCode
	 * @属性描述:数据权限码
	 * @since 1.0.0
	 */
	private String authCode = null;

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void execute(EcifData data) {
		try {
			this.ecifData = data;
			this.ecifData.setWriteModelObj(new WriteModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			WriteModel generalInfoList = this.ecifData.getWriteModelObj();
			// 根节点
			TxMsgNode rootTxMsgNode = this.getTxDealInfos();
			if (rootTxMsgNode == null) {
				data.setStatus(ErrorCode.ERR_XML_CFG_NO_ROOT_NODE.getCode(), "交易配置错误");
				log.error("交易:{},请求报文配置错误,没有配置根节点!", data.getTxCode());
				return;
			}
			if (StringUtil.isEmpty(data.getAuthCode())) {
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
				authCode = (String) data.getParameterMap().get(MdmConstants.AUTH_CODE);
			} else {
				authCode = data.getAuthCode();
				authType = (String) data.getParameterMap().get(MdmConstants.AUTH_TYPE);
			}
			// 获得过滤条件节点
			this.ecifData.getWriteModelObj().getOperMap().putAll(data.getParameterMap());

			/** 获取客户识别类型 ***/
			this.ecifData.setCustDiscRul(txModel.getTxDef().getTxDiscRul());

			/*** 获取交易的客户类型 ***/
			if (MdmConstants.TX_CODE_PER.equals(txModel.getTxDef().getTxCustType())) {
				this.ecifData.getWriteModelObj().getOperMap().put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_PER_TYPE);
			} else if (MdmConstants.TX_CODE_ORG.equals(txModel.getTxDef().getTxCustType())) {
				this.ecifData.getWriteModelObj().getOperMap().put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_ORG_TYPE);
			} else if (MdmConstants.TX_CODE_INTER.equals(txModel.getTxDef().getTxCustType())) {
				this.ecifData.getWriteModelObj().getOperMap().put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_BANK_TYPE);
			}

			/** 严格区分新增和修改 **/
			if (MdmConstants.TX_DIV_INS_UPD.equals(txModel.getTxDef().getTxDivInsUpd())) {
				this.ecifData.getWriteModelObj().setDivInsUpd(true);
			}

			boolean ref = getTableNodeObect(ecifData.getBodyNode(), rootTxMsgNode, generalInfoList);
			if (!ref) { return; }

			this.ecifData.setTxType(this.txModel.getTxDef().getTxLvl2Tp());
			/**
			 * 交易处理类
			 */
			String txDealClass = txModel.getTxDef().getTxDealClass();
			IEcifBizLogic createBizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);
			if (createBizLogic == null) {
				log.error("交易处理类为空");
				this.ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL);
				return;
			}
			createBizLogic.process(this.ecifData);
			if (this.ecifData.isSuccess()) {
				/***
				 * 打印数据变更记录
				 */
				if (this.ecifData.getDataSynchro() != null) {
					if (BusinessCfg.getBoolean("printChangeLog")) {
						log.info("客户[{}]信息变更如下:", this.ecifData.getCustId());
						for (Object o : this.ecifData.getDataSynchro()) {
							log.info(o.toString());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("错误信息", e);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			return;
		}
		if (this.ecifData.getRepNode() != null) { return; }
		Map map = null;
		Element responseEle = DocumentHelper.createElement(txModel.getResTxMsg().getMainMsgRoot());
		/**
		 * 处理返回报文
		 */
		if ((map = this.ecifData.getWriteModelObj().getResultMap()) != null) {
			Element hand = null;
			Element pointer = responseEle;
			for (TxMsgNode txMsgNode : this.txModel.getResTxMsgNodeList()) {
				if (txMsgNode.getNodeCode() != null && txMsgNode.getNodeCode().equals(txModel.getResTxMsg().getMainMsgRoot())) {
					/* 设置返回值 */
					if (this.txModel.getTxMsgNodeAttrMap() != null && txMsgNode.getNodeId() != null) {
						List<TxMsgNodeAttr> txMsgNodeAttr = this.txModel.getTxMsgNodeAttrMap().get(txMsgNode.getNodeId());
						getResNodeXml(txMsgNodeAttr, map, responseEle);
					}
				} else {
					List<TxMsgNodeAttr> txMsgNodeAttr = this.txModel.getTxMsgNodeAttrMap().get(txMsgNode.getNodeId());
					if (MdmConstants.NODE_GROUP_M.equals(txMsgNode.getNodeGroup())) {
						if (!MdmConstants.NODE_GROUP_NO_LABEL.equals(txMsgNode.getNodeLabel())) {
							hand = pointer.addElement(txMsgNode.getNodeCode() + MdmConstants.NODE_GROUP_SUFFIX);
							pointer = hand;
						}
						List<Map> resList = (List<Map>) map.get(txMsgNode.getNodeCode());
						if (resList != null && !resList.isEmpty()) {
							for (Map listmap : resList) {
								hand = pointer.addElement(txMsgNode.getNodeCode());
								getResNodeXml(txMsgNodeAttr, listmap, hand);
							}
						}
					} else {
						Map resList = (Map) map.get(txMsgNode.getNodeCode());
						if (!"0".equals(txMsgNode.getNodeDisplay())) {
							hand = pointer.addElement(txMsgNode.getNodeCode());
						} else {
							hand = pointer;
						}
						getResNodeXml(txMsgNodeAttr, resList, hand);
					}
				}
			}
		}
		this.ecifData.setRepNode(responseEle);
		return;
	}

	/**
	 * @函数名称:getResNodeXml
	 * @函数描述:获取响应报文
	 * @参数与返回说明:
	 * @param txMsgNodeAttr
	 * @param map
	 * @param pointer
	 * @算法描述:
	 */
	private void getResNodeXml(List<TxMsgNodeAttr> txMsgNodeAttr, Map map, Element pointer) {
		String repValue = null;
		Element hand = null;
		if (txMsgNodeAttr != null) {
			for (TxMsgNodeAttr NodeAttr : txMsgNodeAttr) {
				if (NodeAttr.getAttrCode() != null && NodeAttr.getDataLen() != null && NodeAttr.getDataLen() != 0) {
					hand = pointer.addElement(NodeAttr.getAttrCode());
					if (map != null && map.get(NodeAttr.getAttrCode()) != null) {
						repValue = map.get(NodeAttr.getAttrCode()).toString();
						if (!repValue.isEmpty()) {
							/** 转码,转换 **/
							repValue = processCTDR(NodeAttr, repValue);
							/**
							 * DOM4j设置数据时不能处理NULL数据
							 */
							if (repValue == null) {
								repValue = "";
							}
							hand.setText(repValue);
						}
					} else {
						/**
						 * 设置默认数据
						 */
						hand.setText(StringUtil.toString(NodeAttr.getDefaultVal()));
					}
				}
			}
		}
	}

	/**
	 * @函数名称:getTxDealInfos
	 * @函数描述:获取根节点与结点间关系
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public TxMsgNode getTxDealInfos() {
		List<TxMsgNode> tsMsgNodeList = this.txModel.getReqTxMsgNodeList();
		if (tsMsgNodeList == null || tsMsgNodeList.isEmpty()) { return null; }
		TxMsgNode rootTxMsgNode = null;
		List<TxMsgNode> txMsgNodeList = null;
		/**
		 * 从列表中处理结点关系，将结点处理成逻辑树
		 */
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
		return rootTxMsgNode;
	}

	/**
	 * @函数名称:getTableNodeObect
	 * @函数描述:报文转换成对象
	 * @参数与返回说明:
	 * @param context
	 * @param currentxMsgNode
	 * @param generalInfoList
	 * @算法描述:
	 */
	private boolean getTableNodeObect(Element context, TxMsgNode currentxMsgNode, WriteModel generalInfoList) {
		Long nodeId = currentxMsgNode.getNodeId();
		List<TxMsgNodeTabMap> txMsgNodeTabMaps = txModel.getTxMsgNodeTabMapMap().get(nodeId);
		if (txMsgNodeTabMaps != null && !txMsgNodeTabMaps.isEmpty()) {
			List<TxMsgNodeAttr> txMsgNodeAttrs = txModel.getTxMsgNodeAttrMap().get(nodeId);
			if (txMsgNodeAttrs != null && !txMsgNodeAttrs.isEmpty()) {
				for (TxMsgNodeTabMap txMsgNodeTabMap : txMsgNodeTabMaps) {
					String className = txMsgNodeTabMap.getObjName();
					/**
					 * 生成对象
					 */
					Object obj = getClassByParams(className, context, txMsgNodeAttrs, txMsgNodeTabMap.getTabId(), nodeId);
					/**
					 * 如果返回的对象为空，可能是错误，也可能是数据无效
					 */
					if (obj != null) {
						/**
						 * 数据有效
						 */
						generalInfoList.setOpModelList(obj);
					} else {
						if (!ecifData.isSuccess()) {
							/**
							 * 处理错误
							 */
							return false;
						}
					}
				}
			}
		}
		/**
		 * 当前节点下的所有子节点
		 */
		List<TxMsgNode> txMsgNodeList = txMsgNodeMap.get(nodeId);
		if (txMsgNodeList != null) {
			List<Element> nodeElement = null;
			for (TxMsgNode txMsgNode : txMsgNodeList) {
				String nodeCode = txMsgNode.getNodeCode();
				/**
				 * 处理节点组的标签问题
				 */
				if (MdmConstants.NODE_GROUP_M.equals(txMsgNode.getNodeGroup())) {
					if (MdmConstants.NODE_GROUP_NO_LABEL.equals(txMsgNode.getNodeLabel())) {
						nodeElement = context.selectNodes(nodeCode);
					} else {
						nodeElement = context.selectNodes(nodeCode + MdmConstants.NODE_GROUP_SUFFIX + "/" + nodeCode);
					}
				} else {
					/**
					 * 处理结点的逻辑显影问题
					 */
					if (!"0".equals(txMsgNode.getNodeDisplay())) {
						nodeElement = context.selectNodes(nodeCode);
					} else {
						nodeElement = new LinkedList();
						nodeElement.add(context);
					}
				}
				if (nodeElement != null && !nodeElement.isEmpty()) {
					for (Element oneNode : nodeElement) {
						getTableNodeObect(oneNode, txMsgNode, generalInfoList);
						if (!ecifData.isSuccess()) { return false; }
					}
				}
			}
		}
		return true;
	}

	/**
	 * @函数名称:getClassByParams
	 * @函数描述:对象赋值
	 * @参数与返回说明:
	 * @param className
	 * @param context
	 * @param txMsgNodeAttrs
	 * @param tabId
	 * @return
	 * @算法描述:
	 */
	public <T> T getClassByParams(String className, Element context, List<TxMsgNodeAttr> txMsgNodeAttrs, Long tabId, Long nodeId) {
		T entity = null;
		boolean isAllEmpty = true;
		String val = null;
		StringBuffer txMsgNodeAttrCtList = null;
		if (!StringUtil.isEmpty(className)) {
			try {
				/**
				 * 生成类
				 */
				Class clazz = ServiceEntityMgr.getEntityByName(className);
				if (clazz == null) {
					log.error("没有找到{}的实体类", className);
					this.ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
					return null;
				}
				/**
				 * 实例化该类
				 */
				entity = (T) clazz.newInstance();
				Object value = null;
				Class typeClass = null;
				Field field = null;
				for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrs) {
					if (tabId.equals(txMsgNodeAttr.getTabId())) {
						/**
						 * 修改权限控制
						 */
						/***
						 * 信息漂白
						 ****/
						if ("Y".equals(txMsgNodeAttr.getNulls())
								&& !EcifPubDataUtils.infoLevelRead(authType, authCode, MdmConstants.CTRLTYPE_WRITE, txMsgNodeAttr.getTabId(), txMsgNodeAttr.getColId())) {
							continue;
						}
						Element attrElement = context.element(txMsgNodeAttr.getAttrCode());
						if (!StringUtil.isEmpty(txMsgNodeAttr.getCheckRule())) {
							if (txMsgNodeAttrCtList == null) {
								txMsgNodeAttrCtList = new StringBuffer(txMsgNodeAttr.getCheckRule());
							} else {
								txMsgNodeAttrCtList.append(',').append(txMsgNodeAttr.getCheckRule());
							}
						}
						//通过getTextTrim可能会获取到""类型的值，实际还 为空
						if (attrElement != null&&!StringUtil.isEmpty(attrElement.getTextTrim())) {
							val = attrElement.getTextTrim();
						} else {
							val = txMsgNodeAttr.getDefaultVal();
							/*** 默认值规则 ***/
							if (!StringUtil.isEmpty(val)) {
								/**
								 * 解析默认值
								 */
								val = parseDefaultVal(val, context);
								if (!this.ecifData.isSuccess()) { return null; }
							}
						}
						if (!StringUtil.isEmpty(val)) {
							/**
							 * 获取的属性名称是数据库的字段名称，需要转换成java的命名规范名称
							 */
							field = clazz.getDeclaredField(NameUtil.getColumToJava(txMsgNodeAttr.getColName()));
							typeClass = field.getType();
							/**
							 * 将字符型的数据转换成实体属性的类型
							 */
							value = convertStringToObject(typeClass, val, txMsgNodeAttr.getDataFmt());
							if (value != null) {
								/**
								 * 设置对私的访问权限
								 */
								field.setAccessible(true);
								/**
								 * 设置数据
								 */
								field.set(entity, value);
								isAllEmpty = false;
							} else {
								return null;
							}
						} else {
							/** 判定是否是业务主键，非空 **/
							if ("P".equals(txMsgNodeAttr.getNulls())) { return null; }
						}
					}
				}

			} catch (Exception e) {
				log.error("错误信息：", e);
				this.ecifData.setStatus(ErrorCode.ERR_POJO_UNKNOWN_ERROR);
				return null;
			}
		} else {
			log.warn("数据库数据有误");
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR.getCode(), "数据库数据有误");
			return null;
		}
		/**
		 * 所有的属性为空，返回空
		 */
		if (isAllEmpty) { return null; }
		/******
		 * 写交易过滤器
		 */
		if (txMsgNodeAttrCtList != null && txMsgNodeAttrCtList.length() > 0) {
			boolean rel = TxBizRuleFactory.doFilter(this.ecifData, txMsgNodeAttrCtList.toString(), entity);
			if (!rel) { return null; }
		}
		return entity;
	}
}
