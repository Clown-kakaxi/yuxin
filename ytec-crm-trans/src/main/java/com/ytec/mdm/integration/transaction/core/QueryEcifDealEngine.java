/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.core
 * @文件名：QueryEcifDealEngine.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:10:57
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.transaction.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.QueryModel;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxNode4CRM;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.integration.transaction.model.TxModel4CRM;

/**
 * @项目名称：ytec-mdm-trans(CRM)
 * @类名称：QueryEcifDealEngine
 * @类描述：查询交易处理引擎
 * @功能描述:查询交易引擎 ，解析查询条件，调用查询逻辑
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：2014-12-06 上午11:10:58
 *                  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */
public class QueryEcifDealEngine extends AbstractEcifDealEngine {

	private static Logger log = LoggerFactory.getLogger(QueryEcifDealEngine.class);

	/**
	 * @属性名称:nodeGroupFlag
	 * @属性描述:节点组控制标识
	 * @since 1.0.0
	 */
	private boolean nodeGroupFlag = false;
	/**
	 * @属性名称:authType
	 * @属性描述:数据访问权限类别
	 * @since 1.0.0
	 */
	private String authType = null;
	/**
	 * @属性名称:authCode
	 * @属性描述:数据权限访问码
	 * @since 1.0.0
	 */
	private String authCode = null;
	/**
	 * @属性名称:isAllQueryNull
	 * @属性描述:是否所有查询为空
	 * @since 1.0.0
	 */
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
			} else {
				authCode = data.getAuthCode();
				authType = reqParamMap.get(MdmConstants.AUTH_TYPE);
			}

			// 获取交易处理类并做查询处理
			String txDealClass = txModel.getTxDef().getDealClass();
			this.bizLogic = (IEcifBizLogic) SpringContextUtils.getBean(txDealClass);

			bizLogic.process(data);

			// 设置默认响应信息根节点
			Element responseEle = DocumentHelper.createElement("ResponseBody");

			if (data.getQueryModelObj().getResulList() != null && data.getQueryModelObj().getResulList().size() != 0) {
				isAllQueryNull = false;
			}

			// 查询结果操作正常结束，但查询结果集中无数据，设置响应信息为ErrorCode.WRN_NONE_FOUND
			if (isAllQueryNull && data.isSuccess()) {
				data.setStatus(ErrorCode.WRN_NONE_FOUND);
				data.setSuccess(true);
				return;
			}
			if (data.isSuccess()) {
				// 处理成功，根据查询结果组装响应报文，从queryBizLogic中获取到查询结果封装在 queryModelObj中的resultList中
				// 存放方式为List,List中为一个Map：key=报文中节点名，value=JQL查询结果集
				buildRespNodeXML(responseEle, data.getQueryModelObj().getResulList(), this.txModel);
				data.setRepNode(responseEle);
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
	 * @param pointer
	 * @param currentxMsgNode
	 * @param txMsgNodeMap
	 * @param reqParamMap
	 * @param parentNodeValueMap
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @算法描述:
	 */
	private void buildRespNodeXML(Element respBody, List<Map<String, Object>> respData, TxModel4CRM model) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException {

		// 获取报文定义，方便后续封装报文时做相应处理
		// 将报文定义存储于Map中，key:nodeName(报文中节点名),value:TxNode4CRM对象，对应transConf.xml中//trans/trans/node节点内容
		List<TxNode4CRM> nodeDef_s = model.getTxNodeList();
		Map<String, TxNode4CRM> nodeMap = new HashMap<String, TxNode4CRM>();

		// 遍历查询结果返回对象，组装XML报文
		// 1、获取XML中node配置信息、查询结果中数据对象；
		// 2、通过node配置，处理数据对象，通过Java反射获取实体类(实际上实体类也可看做是报文配置中一部分)，并通过实体类中属性名设置报文中叶子节点
		// 3、为节省报文长度，将无值有叶子节点不做setText()操作，设置为形如<node/>的空节点
		Map<String, Object> data = respData.get(0);
		Set<String> key_s = data.keySet();
		Iterator<String> itr = key_s.iterator();
		for (TxNode4CRM nodeDef : nodeDef_s) {
			String nodeName = nodeDef.getName();
			nodeMap.put(nodeName, nodeDef);
			Object entity = data.get(nodeName);

			// 做空值处理，添加空XML节点
			if (entity == null || ((List) entity).size() == 0) {
				if (nodeDef.isList() == true) {
					respBody.addElement(nodeName + "List").addElement(nodeName);
				} else {
					respBody.addElement(nodeName);
				}
				continue;
			}

			// TODO:需要将代码做整合，针对是否List(node.isList)代码优化处理

			// 查询结果(表中数据)封装在List中，需要遍历取值
			// 该节点对应List数据对象长度>1，即有多条记录
			if (nodeDef.isList() == true) {// entity instanceof List && ((List) entity).size() > 1) {
				// 根据移动信贷系统需求，针对List节点，表节点上方添加一${nodeName}List
				Element nodeListEle = respBody.addElement(nodeName + "List");
				List<Object> obj_s = (List<Object>) entity;
				for (Object obj : obj_s) {
					Field[] field_s = obj.getClass().getDeclaredFields();
					Element nodeEle = nodeListEle.addElement(nodeName);
					for (Field field : field_s) {
						String fieldName = field.getName();
						if (nodeDef.getRespNodes().contains(fieldName)) {
							String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
							Object fieldValue = MethodUtils.invokeMethod(obj, methodName, null);
							if (fieldValue == null || "null".equals(fieldValue)) {
								nodeEle.addElement(fieldName);
							} else {
								nodeEle.addElement(fieldName).setText("" + fieldValue);
							}
						} else {
							continue;
						}
					}
				}
			}
			// 节点对应记录(实体类)查询结果只有一条
			// 实际中entity instanceof List判断多余，因为queryBizLogic处理类中本身将该内容以JQL查询结果集List形式存储
			// 但为防止 (List) entity 强转出错，加上此判断
			else {
				if (entity instanceof List && ((List) entity).size() > 1) {
					String err = String.format("%s(%s)", ErrorCode.ERR_ECIF_DATA_TOO_MANY.getChDesc(), nodeName);
					this.ecifData.setSuccess(false);
					this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_TOO_MANY.getCode(), err);
					return;
				}
				Element nodeEle = respBody.addElement(nodeName);

				// 查询结果统一封装在List中，但如果只有一条记录，可直接强转
				Object obj = ((List) entity).get(0);
				Field[] field_s = obj.getClass().getDeclaredFields();
				for (Field field : field_s) {
					String fieldName = field.getName();
					if (nodeDef.getRespNodes().contains(fieldName)) {
						String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Object fieldValue = MethodUtils.invokeMethod(obj, methodName, null);
						if (fieldValue == null || "null".equals(fieldValue)) {
							nodeEle.addElement(fieldName);
						} else {
							nodeEle.addElement(fieldName).setText("" + fieldValue);
						}
					} else {
						continue;
					}
				}
			}
		}
	}
}
