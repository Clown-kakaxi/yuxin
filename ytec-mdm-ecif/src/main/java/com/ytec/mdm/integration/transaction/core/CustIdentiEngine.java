/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：CustIdentiEngine.java
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：CustIdentiEngine
 * @类描述：自定义客户识别交易引擎
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:10:58
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:10:58
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Component
@Scope("prototype")
public class CustIdentiEngine extends AbstractEcifDealEngine {
	private static Logger log = LoggerFactory.getLogger(CustIdentiEngine.class);

	/*
	 * (non-Javadoc)
	 * @see
	 * com.ytec.mdm.integration.transaction.core.AbstractEcifDealEngine#execute
	 * (com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public void execute(EcifData data) {
		try {
			this.ecifData = data;
			this.ecifData.setQueryModelObj(new QueryModel());
			this.txModel = TxModelHolder.getTxModel(data.getTxCode());
			// 获取查询条件,报文定义属性、请求参数都可以作为查询条件
			Map<String, String> reqParamMap = new HashMap<String, String>(data.getParameterMap());
			// 获取客户识别返回项定义
			List<TxMsgNode> tsMsgNodeList = this.txModel.getResTxMsgNodeList();
			// 根节点(客户识别规则定义在根节点上)
			TxMsgNode rootTxMsgNode = null;
			for (TxMsgNode txMsgNode : tsMsgNodeList) {
				if (txMsgNode.getUpNodeId() == -1) {
					rootTxMsgNode = txMsgNode;
					break;
				}
			}
			// 判定根结点
			if (rootTxMsgNode == null) {
				data.setStatus(ErrorCode.ERR_XML_CFG_NO_ROOT_NODE.getCode(), "交易配置错误");
				log.warn("交易:{},客户识别规则报文配置错误,没有配置根节点!", data.getTxCode());
				return;
			}
			// 交易处理类
			String txDealClass = txModel.getTxDef().getTxDealClass();
			this.bizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);
			// 客户搜索
			searchCustomer(rootTxMsgNode, reqParamMap);
		} catch (Exception e) {
			log.error("错误信息", e);
			data.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
		}
		return;
	}

	/**
	 * @函数名称:searchCustomer
	 * @函数描述:客户识别判定
	 * @参数与返回说明:
	 * @param currentxMsgNode
	 *        客户识别规则定义根节点
	 * @param reqParamMap
	 *        客户识别参数
	 * @throws Exception
	 * @算法描述:
	 */
	private void searchCustomer(TxMsgNode currentxMsgNode, Map<String, String> reqParamMap) throws Exception {
		// 基于数据库表的节点
		Long nodeId = currentxMsgNode.getNodeId();
		// 查询字段
		List<TxMsgNodeAttr> txMsgNodeAttrList = this.txModel.getTxMsgNodeAttrMap().get(nodeId);
		if (txMsgNodeAttrList == null || txMsgNodeAttrList.size() == 0) {
			String msg = "报文节点:" + currentxMsgNode.getNodeCode() + "没有查询内容";
			log.warn(msg);
			this.ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(), "没有配置返回项");
			return;
		}
		Map<String, Object> requestMap = new HashMap<String, Object>();
		List<String> queryFieldList = new ArrayList<String>();
		/** 构造查询内容 */
		for (TxMsgNodeAttr txMsgNodeAttr : txMsgNodeAttrList) {
			queryFieldList.add(txMsgNodeAttr.getAttrCode());
		}
		this.ecifData.getQueryModelObj().setQueryFieldList(queryFieldList);
		/**
		 * 获取过滤条件数据
		 */
		getFilterParam(currentxMsgNode, requestMap, reqParamMap);
		if (!this.ecifData.isSuccess()) { return; }
		this.ecifData.getQueryModelObj().setParentNodeValueMap(requestMap);
		// ======================构造查询条件===========end
		// 构造查询语句
		this.ecifData.getQueryModelObj().setQuerySql(
				TxMsgModeQuerySQLHolder.getQuerySQL(this.txModel.getTxDef().getTxCode(), nodeId));
		// 调用查询服务
		bizLogic.process(this.ecifData);
		List<Map<String, Object>> queryResultList = (List<Map<String, Object>>) this.ecifData.getQueryModelObj()
				.getResulList();
		// 封装查询结果
		if (queryResultList == null || queryResultList.size() == 0) {
			// 识别不到客户
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
			return;
		} else if (queryResultList.size() == 1) {
			ecifData.resetStatus();
			Map ob = queryResultList.get(0);
			CustStatus custStatCtrl = null;
			// 判定客户是否失效
			if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob.get(MdmConstants.TX_CUST_STATE))) != null
					&& custStatCtrl.isReOpen()) {// 失效客户
				log.warn("已识别到客户[{}],但客户状态[{}({})],已失效", (String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO),
						custStatCtrl.getCustStatus(), custStatCtrl.getCustStatusDesc());
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
			} else {
				ecifData.setCustId(StringUtil.toString(ob.get(MdmConstants.CUSTID)));
				// ecifData.setEcifCustNo((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
				ecifData.getCustId((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
				ecifData.setCustType((String) ob.get(MdmConstants.TX_CUST_TYPE));
				ecifData.setCustStatus((String) ob.get(MdmConstants.TX_CUST_STATE));
			}
		} else {
			// 判断查询出多个客户，排除失效客户(注销,合并等)。
			Map ob = null;
			int availableNum = 0;
			CustStatus custStatCtrl = null;
			for (int i = 0; i < queryResultList.size(); i++) {
				ob = queryResultList.get(i);
				// 判定客户是否失效
				if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(
						(String) ob.get(MdmConstants.TX_CUST_STATE))) != null
						&& custStatCtrl.isReOpen()) {// 失效客户
					continue;
				}
				availableNum++;
				// 客户唯一
				if (availableNum == 1) {
					// 设置客户ID
					ecifData.setCustId(StringUtil.toString(ob.get(MdmConstants.CUSTID)));
					// 设置ECIF客户号
					// ecifData.setEcifCustNo((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
					ecifData.getCustId((String) ob.get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO));
					// 设置客户类型
					ecifData.setCustType((String) ob.get(MdmConstants.TX_CUST_TYPE));
					// 设置客户状态
					ecifData.setCustStatus((String) ob.get(MdmConstants.TX_CUST_STATE));
				} else {
					// 客户识别不唯一
					ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "返回客户不唯一");
					return;
				}
			}
			if (availableNum == 0) {
				// 识别不到客户
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else {
				// 返回状态重置
				ecifData.resetStatus();
			}
		}
		return;

	}

}
