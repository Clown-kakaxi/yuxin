/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：DealDispatchEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:06:47
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：DealDispatchEngine
 * @类描述：组合流程引擎
 * @功能描述:交易流程控制
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:06:48
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:06:48
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class DealDispatchEngine {
	private static Logger log = LoggerFactory.getLogger(DealDispatchEngine.class);

	/**
	 * @函数名称:DealDispatcher
	 * @函数描述:交易分发控制
	 * @参数与返回说明:
	 * @param ecifData
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	public static void DealDispatcher(EcifData data) {
		/**
		 * 保存原交易码
		 */
		String txCode = data.getTxCode();
		/**
		 * 收集参数
		 */
		Map<String, String> paramMap = parameterCollect(data.getBodyNode(), MdmConstants.QUERY_TX_REQ_PRARM_NODE);
		data.setParameterMap(paramMap);
		try {
			if (txCode == null || txCode.trim().equals("")) {
				log.error("交易编码不能为空");
				data.setStatus(ErrorCode.ERR_RULE_VALUE_IS_NULL);
				data.setSuccess(false);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("交易节点缺少");
			log.error("{}", e);
			data.setStatus(ErrorCode.ERR_RULE_VALUE_IS_NULL);
			data.setSuccess(false);
			return;
		}
		IEcifDealEngine engine = DealDispatchEngine.getTxDealEngine(txCode);

		if (engine == null) {
			String err = String.format("无法获取交易处理引擎类，交易(%s)处理失败", txCode);
			log.error(err);
			data.setStatus(ErrorCode.ERR_SERVER_PROG_ERROR.getCode(),err);
			data.setSuccess(false);
		} else {
			engine.execute(data);
		}
	}

	/**
	 * @函数名称:getTxDealEngine
	 * @函数描述:获取交易引擎
	 * @参数与返回说明:
	 * @param txCode
	 * @return
	 * @算法描述:
	 */
	public static IEcifDealEngine getTxDealEngine(String txCode) {

		IEcifDealEngine txDealEngine = null;
		try {
			TxModel4CRM txModel = TxModelHolder.getTxModel(txCode);
			// 根据交易类型生成相应的交易处理引擎
			String txType = txModel.getTxDef().getTxType();
			String cfgTy = txModel.getTxDef().getCfgTp();
			if (MdmConstants.TX_CFG_TP_CUS.equals(cfgTy)) {
				log.info("客户化交易[{}:{}]", txModel.getTxDef().getName(), txModel.getTxDef().getCnName());
				txDealEngine = (IEcifDealEngine) SpringContextUtils.getBean(txModel.getTxDef().getDealEngine());
			} else {
				if (MdmConstants.TX_TYPE_R.equals(txType)) {// 查询交易
					log.info("查询交易[{}:{}]", txModel.getTxDef().getName(), txModel.getTxDef().getCnName());
					txDealEngine = new QueryEcifDealEngine();
				} else if (MdmConstants.TX_TYPE_W.equals(txType)) {
					log.info("写交易[{}:{}]", txModel.getTxDef().getName(), txModel.getTxDef().getCnName());
					txDealEngine = new WriteEcifDealEngine();
				} else {
					txDealEngine = new ErrorEcifDealEngine("交易类型:" + txType + " ,系统不支持!");
					log.error("交易类型:{},系统不支持!", txType);
				}
			}

		} catch (Exception e) {
			log.error("构造交易处理引擎失败", e);
			String errorMsg = "交易" + txCode + "不存在或配置信息异常";
			txDealEngine = new ErrorEcifDealEngine(errorMsg);
		}

		return txDealEngine;
	}

	/**
	 * @函数名称:parameterCollect
	 * @函数描述:获取参数
	 * @参数与返回说明:
	 * @param bodyNode
	 * @param xpath
	 * @return
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parameterCollect(Element bodyNode, String xpath) {
		Map<String, String> opm = new HashMap<String, String>();
		if (bodyNode == null) { return opm; }
		/**
		 * 收集xpath下的数据作为参数
		 */
		List<Element> filterChildren = bodyNode.selectNodes(xpath + "/*");
		if (filterChildren != null) {
			for (Element element : filterChildren) {
				if (!StringUtil.isEmpty(element.getText())) {
					opm.put(element.getName(), element.getText());
				}
			}
		}
		return opm;
	}

	/**
	 * @函数名称:collectReturnNode
	 * @函数描述:返回信息获取
	 * @参数与返回说明:
	 * @param ecifData
	 * @param returnNode
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	private static void collectReturnNode(EcifData data, Element returnNode) {
		if (data.getRepNode() != null) {
			if (returnNode.elements() == null || returnNode.elements().size() == 0) {
				/**
				 * 第一个返回报文设置
				 */
				returnNode.setName(data.getRepNode().getName());
			}
			List<Element> tempList = data.getRepNode().elements();
			for (Element point : tempList) {
				/**
				 * 将返回的数据复制到返回对象中
				 */
				returnNode.add(point.detach());
			}
		}
		return;
	}
}
