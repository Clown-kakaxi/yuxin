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

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.core.EcifDealEngineFactory;
import com.ytec.mdm.integration.transaction.facade.IDispatchFun;
import com.ytec.mdm.integration.transaction.model.DispatchNode;
import com.ytec.mdm.integration.transaction.model.DispatchRule;
import com.ytec.mdm.server.common.DealDispatchCfg;

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
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class DealDispatchEngine {
	private static Logger log = LoggerFactory.getLogger(DealDispatchEngine.class);
	/**
	 * @函数名称:DealDispatcher
	 * @函数描述:交易分发控制
	 * @参数与返回说明:
	 * 		@param ecifData
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	public static void DealDispatcher(EcifData ecifData){
		/**
		 * 保存原交易码
		 */
		String originalTxCode=ecifData.getTxCode();
		/**
		 * 收集参数
		*/
		Map<String, String> paramMap =parameterCollect(ecifData.getBodyNode(),MdmConstants.QUERY_TX_REQ_PRARM_NODE);
		ecifData.setParameterMap(paramMap);
		DispatchRule dispRule=null;
		if((dispRule=DealDispatchCfg.getDispatchRule(originalTxCode))==null){
			/**
			 * 不需要调度
			 */
			log.info("直接调用交易[txCode:{}]",originalTxCode);
			EcifDealEngineFactory.getTxDealEngine(originalTxCode).execute(ecifData);
		}else{
			/**
			 * 需要调度
			 */
			log.info("交易[{}]调度开始",originalTxCode);
			Element returnNode=DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
			doDispatcher(dispRule,ecifData,returnNode);
			/**
			 * 调度失败直接返回错误
			 */
			if(!ecifData.isSuccess()){
				log.info("交易[{}]调度中断",originalTxCode);
			}else{
				/**
				 *设置返回报文
				 */
				List<Element> t=returnNode.elements();
				if(t!=null && t.size()>0){
					log.info("交易[{}]设置返回结果",originalTxCode);
					ecifData.setRepNode(returnNode);
					/**
					 * 如果有返回，重置状态说明
					 */
					ecifData.resetStatus();
				}
			}
			log.info("交易[{}]调度结束",originalTxCode);
			ecifData.setTxCode(originalTxCode);
		}
	}
	/**
	 * @函数名称:orderDispatcher
	 * @函数描述:顺序执行控制
	 * @参数与返回说明:
	 * 		@param dispRule
	 * 		@param ecifData
	 * 		@param returnNode
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	private static void orderDispatcher(DispatchRule dispRule,EcifData ecifData,Element returnNode){
		List<DispatchNode> nodeList= dispRule.getNodeList();
		for(DispatchNode node:nodeList){
			/**
			 * 获取执行类型
			 */
			String exeType=node.getExeType();
			if("0".equals(exeType)){
				/**
				 * 使用原子交易
				 */
				log.info("调用原子交易[{}]",node.getTxCode());
				/**
				 * 先校验原子交易是否存在
				 */
				if(!TxModelHolder.txDefCheck(node.getTxCode())){
					log.warn("调用原子交易[{}]不存在或已停用",node.getTxCode());
					ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_FOUND);
					return;
				}
				/**
				 * 将该交易设置到对象中
				 */
				ecifData.setTxCode(node.getTxCode());
				EcifDealEngineFactory.getTxDealEngine(node.getTxCode()).execute(ecifData);
			}else if("1".equals(exeType)){
				/**
				 * 使用原子函数
				 */
				IDispatchFun dispatchFun=node.getBeanClass();
				if(dispatchFun!=null){
					log.info("调用原子交易函数[{}]",dispatchFun.getClass().getName());
					dispatchFun.execute(ecifData);
				}else{
					log.error("调用原子交易函数失败");
					ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL.getCode(),"调用原子交易函数失败");
					return;
				}
			}else if("2".equals(exeType)){
				/**
				 * 有子流程
				 */
				if(node.getRule()!=null){
					doDispatcher(node.getRule(),ecifData,returnNode);
				}
			}else{
				log.error("%s不支持执行规则%s",ecifData.getTxCode(),exeType);
				ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"交易配置错误");
				return;
			}
			if(!ecifData.isSuccess()){
				/**忽略错误?错误？正确？默认****/
				return ;
			}
			/***
			 * 获取结果参数，作为下次调度的参数
			 */
			if(!StringUtil.isEmpty(node.getResultParametPath())){
				log.info("获取{}下的参数",node.getResultParametPath());
				ecifData.getParameterMap().putAll(parameterCollect(ecifData.getRepNode(),node.getResultParametPath()));
			}
			/**
			 * 保存结果集
			 */
			if(node.isReturn()){
				//returnNode=ecifData.getRepNode();
				collectReturnNode(ecifData,returnNode);
			}
		}
	}
	/**
	 * @函数名称:caseDispatcher
	 * @函数描述:分支执行控制
	 * @参数与返回说明:
	 * 		@param dispRule
	 * 		@param ecifData
	 * 		@param returnNode
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	private static void caseDispatcher(DispatchRule dispRule,EcifData ecifData,Element returnNode){
		/**
		 * 通过判断函数，判断走哪个分支
		 */
		String determination=dispRule.getBeanClass().decide(ecifData);
		if(!ecifData.isSuccess()){
			log.warn("判定函数返回错误:{}",ecifData.getDetailDes());
			return;
		}
		log.info("判定函数返回[{}]",determination);
		DispatchNode dispatchNode=null;
		if((dispatchNode=dispRule.getNodeMapValue(determination))!=null){
			/**
			 * 获取执行类型
			 */
			String exeType=dispatchNode.getExeType();
			if("0".equals(exeType)){
				/**
				 * 使用原子交易
				 */
				log.info("调用原子交易[{}]",dispatchNode.getTxCode());
				/**
				 * 先校验原子交易是否存在
				 */
				if(!TxModelHolder.txDefCheck(dispatchNode.getTxCode())){
					log.warn("调用原子交易{}不存在或已停用",dispatchNode.getTxCode());
					ecifData.setStatus(ErrorCode.ERR_CLIENT_REQUEST_NOT_FOUND);
					return;
				}
				ecifData.setTxCode(dispatchNode.getTxCode());
				EcifDealEngineFactory.getTxDealEngine(dispatchNode.getTxCode()).execute(ecifData);
			}else if("1".equals(exeType)){
				/**
				 * 使用原子函数
				 */
				IDispatchFun dispatchFun=dispatchNode.getBeanClass();
				if(dispatchFun!=null){
					log.info("调用原子交易函数[{}]",dispatchFun.getClass().getName());
					dispatchFun.execute(ecifData);
				}else{
					log.error("调用原子交易函数失败");
					ecifData.setStatus(ErrorCode.ERR_POJO_IS_NULL.getCode(),"调用原子交易函数失败");
					return;
				}
			}else if("2".equals(exeType)){
				/**
				 * 有子流程
				 */
				if(dispatchNode.getRule()!=null){
					doDispatcher(dispatchNode.getRule(),ecifData,returnNode);
				}
			}else{
				log.error("%s不支持执行规则%s",ecifData.getTxCode(),exeType);
				ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"交易配置错误");
				return;
			}
			
			/***
			 * 获取结果参数，作为下次调度的参数
			 */
			if(ecifData.isSuccess()){
				if(!StringUtil.isEmpty(dispatchNode.getResultParametPath())){
					log.info("获取{}下的参数",dispatchNode.getResultParametPath());
					ecifData.getParameterMap().putAll(parameterCollect(ecifData.getRepNode(),dispatchNode.getResultParametPath()));
				}
				/**
				 * 保存结果集
				 */
				if(dispatchNode.isReturn()){
					//returnNode=ecifData.getRepNode();
					collectReturnNode(ecifData,returnNode);
				}
			}
		}else{
			log.error("不支持判定的执行规则[{}]",determination);
			ecifData.setStatus(ErrorCode.ERR_SERVER_CFG_FILE_ERROR.getCode(),"交易配置错误");
			return;
		}
	}
	
	/**
	 * @函数名称:doDispatcher
	 * @函数描述:组合控制
	 * @参数与返回说明:
	 * 		@param dispRule
	 * 		@param ecifData
	 * 		@param returnNode
	 * @算法描述:
	 */
	private static void doDispatcher(DispatchRule dispRule,EcifData ecifData,Element returnNode){
		if("case".equals(dispRule.getRuleType())){
			/**
			 * 走分支调度逻辑
			 */
			log.info("分支调度");
			caseDispatcher(dispRule,ecifData,returnNode);
		}else{
			/**
			 * 走顺序调度逻辑
			 */
			log.info("顺序调度");
			orderDispatcher(dispRule,ecifData,returnNode);
		}
	}
	
	
	/**
	 * @函数名称:parameterCollect
	 * @函数描述:获取参数
	 * @参数与返回说明:
	 * 		@param bodyNode
	 * 		@param xpath
	 * 		@return
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parameterCollect(Element bodyNode,String xpath){
		Map<String, String> opm = new HashMap<String, String>();
		if(bodyNode==null){
			return opm;
		}
		/**
		 * 收集xpath下的数据作为参数
		 */
		List<Element> filterChildren =bodyNode.selectNodes(xpath+"/*");
		if(filterChildren!=null){
			for (Element element : filterChildren) {
				if(!StringUtil.isEmpty(element.getText())){
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
	 * 		@param ecifData
	 * 		@param returnNode
	 * @算法描述:
	 */
	@SuppressWarnings("unchecked")
	private static void collectReturnNode(EcifData ecifData,Element returnNode){
		if(ecifData.getRepNode()!=null){
			if(returnNode.elements()==null ||returnNode.elements().size()==0){
				/**
				 * 第一个返回报文设置
				 * 
				 */
				returnNode.setName(ecifData.getRepNode().getName());
			}
			List<Element> tempList= ecifData.getRepNode().elements();
			for(Element point:tempList){
				/**
				 * 将返回的数据复制到返回对象中
				 */
				returnNode.add(point.detach());
			}
		}
		return;
	}

}
