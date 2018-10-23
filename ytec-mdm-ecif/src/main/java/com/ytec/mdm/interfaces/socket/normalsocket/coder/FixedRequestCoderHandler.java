/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @文件名：FixedRequestCoderHandler.java
 * @版本信息：1.0.0
 * @日期：2014-4-15-下午5:26:56
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxDef;
import com.ytec.mdm.domain.txp.TxMsgNode;
import com.ytec.mdm.domain.txp.TxMsgNodeAttr;
import com.ytec.mdm.integration.transaction.core.TxModelHolder;
import com.ytec.mdm.integration.transaction.model.TxModel;
import com.ytec.mdm.interfaces.socket.http.tools.DynamicBytes;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：FixedRequestCoderHandler
 * @类描述：定长报文解析
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-15 下午5:26:56
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-15 下午5:26:56
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class FixedRequestCoderHandler implements IFixedRequestCoder{
	protected static Logger log = LoggerFactory
			.getLogger(FixedRequestCoderHandler.class);
	/**
	 * @属性名称:bufferSize
	 * @属性描述:缓冲区大小
	 * @since 1.0.0
	 */
	private static int bufferSize = 1024;
	/**
	 * @属性名称:txFixedAttrsModelMap
	 * @属性描述:定长报文数据域序列模型
	 * @since 1.0.0
	 */
	private static ConcurrentHashMap<String, FixedAttrsModel> txFixedAttrsModelMap = new ConcurrentHashMap<String, FixedAttrsModel>();

	/**
	 * @函数名称:requestFixedStringToXml
	 * @函数描述:定长报文转换成平面XML
	 * @参数与返回说明:
	 * @param data
	 * @throws Exception
	 * @算法描述:
	 */
	public void requestFixedStringToXml(EcifData data) throws Exception {
		FixedAttrsModel fixedAttrsModel = null;
		/**到定长报文属性模型Map中查找**/
		if ((fixedAttrsModel = getFixedAttrsModel(data.getTxCode(), true)) != null) {//对应模型存在
			List<TxMsgNodeAttr> requestAttrList = fixedAttrsModel.getRequestAttrList();
			int length = requestAttrList.size();
			TxMsgNodeAttr point = null;
			//新建请求报文
			Document requestDoc = DocumentHelper.createDocument();
			if (StringUtil.isEmpty(fixedAttrsModel.getRequestRoot())) {
				String msg = "交易:" + data.getTxCode() + ",请求报文没有配置根结点";
				data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),msg);
				log.error(msg);
				return;
			}
			//创建根请求结点
			Element rootElement = DocumentHelper.createElement(fixedAttrsModel.getRequestRoot());
			requestDoc.setRootElement(rootElement);
			//获取请求字节流
			byte[] fixedBytes = data.getPrimalMsg().getBytes(data.getCharsetName());
			String text = null;
			int offset = 0;
			int fixedStringLength = fixedBytes.length;
			for (int i = 0; i < length; i++) {//遍历数据域序列
				point = requestAttrList.get(i);
				if (point.getAttrSeq() - 1 == i) {
					if (fixedStringLength < offset + point.getDataLen()) {//数据超长
						String msg = "交易:" + data.getTxCode() + ",请求报文长度不匹配";
						data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(),msg);
						log.error(msg);
						return;
					}
					//通过长度截取数据
					text = new String(fixedBytes, offset, point.getDataLen().intValue(),data.getCharsetName());
					offset += point.getDataLen();
					log.debug("{}:{}|{}|{}", i, point.getAttrCode(),point.getDataLen(), text);
					//新建xml属性并设置数据
					rootElement.addElement(point.getAttrCode()).setText(text);
				} else {
					String msg = "交易:" + data.getTxCode() + ",请求报文定长序列配置错误";
					data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
					log.error(msg);
					return;
				}
			}
			//设置报文
			data.setPrimalDoc(requestDoc);
			//设置请求根结点
			data.setBodyNode(rootElement);
			//log.debug(XMLUtils.elementToString(rootElement));
		} else {
			String msg = "交易:" + data.getTxCode() + ",请求报文配置错误";
			data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
			log.error(msg);
			return;
		}
		return;
	}

	/**
	 * @函数名称:responseXmlToFixedString
	 * @函数描述:平面XML转换成定长报文
	 * @参数与返回说明:
	 * @param data
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	public String responseXmlToFixedString(EcifData data) throws Exception {
		byte[] b = responseXmlToFixedByte(data);
		if(data.isSuccess()){
			return new String(b,data.getCharsetName());
		}else{
			return "";
		}
	}

	/**
	 * @函数名称:responseXmlToFixedByte
	 * @函数描述:平面XML转换成定长字节流
	 * @参数与返回说明:
	 * @param data
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	public byte[] responseXmlToFixedByte(EcifData data) throws Exception {
		FixedAttrsModel fixedAttrsModel = null;
		/**到定长报文属性模型Map中查找**/
		if ((fixedAttrsModel = getFixedAttrsModel(data.getTxCode(), false)) != null) {//对应模型存在
			//初始化响应字节缓冲区
			DynamicBytes bos = new DynamicBytes(bufferSize);
			List<TxMsgNodeAttr> responseAttrList = fixedAttrsModel.getResponseAttrList();
			int length = responseAttrList.size();
			TxMsgNodeAttr point = null;
			//获取响应XML报文
			Element rootElement = data.getRepNode();
			//log.info(XMLUtils.elementToString(rootElement));
			String text = null;
			byte[] b = null;
			for (int i = 0; i < length; i++) {//遍历数据域序列
				point = responseAttrList.get(i);
				if (point.getAttrSeq() - 1 == i) {
					text = rootElement.elementText(point.getAttrCode());
					log.debug("{}:{}|{}|{}", i, point.getAttrCode(),point.getDataLen(), text);
					if(text==null){
						b=null;
					}else{
						b=text.getBytes(data.getCharsetName());
					}
					b = fixedAttrFormat(b, point.getDataLen().intValue(),point.getDataType());//格式化数据
					bos.append(b, b.length);
				} else {
					String msg = "交易:" + data.getTxCode() + ",响应报文定长序列配置错误";
					data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
					log.error(msg);
					return null;
				}
			}
			//返回响应字节流
			return bos.bytes();
		} else {
			String msg = "交易:" + data.getTxCode() + ",请求报文配置错误";
			data.setStatus(ErrorCode.ERR_XML_CFG_UNKNOWN_ERROR.getCode(), msg);
			log.error(msg);
			return null;
		}
	}

	/**
	 * @函数名称:getFixedAttrsModel
	 * @函数描述:获取定长对象
	 * @参数与返回说明:
	 * @param txCode
	 * @param isRequest
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	private FixedAttrsModel getFixedAttrsModel(String txCode, boolean isRequest)
			throws Exception {
		FixedAttrsModel fixedAttrsModel = null;
		TxModel txModel = null;
		if ((txModel = TxModelHolder.getTxModel(txCode)) != null) {
			if ((fixedAttrsModel = txFixedAttrsModelMap.get(txCode)) != null) {//获取交易配置
				if (isRequest) {
					// 检查修改日期是否发生变化
					TxDef txDef = txModel.getTxDef();
					Timestamp newTimestamp = txDef.getUpdateTm();
					Timestamp oldTimestamp = fixedAttrsModel.getUpdateTm();
					if (newTimestamp != null
							&& (oldTimestamp == null || newTimestamp
									.after(oldTimestamp))) {//配置被修改
						fixedAttrsModel = buildFixedAttrsModel(txModel);
						txFixedAttrsModelMap.replace(txCode, fixedAttrsModel);
					}
				}
			} else {
				fixedAttrsModel = buildFixedAttrsModel(txModel);
				FixedAttrsModel txFixedAttrsModelTemp = null;
				if ((txFixedAttrsModelTemp = txFixedAttrsModelMap.putIfAbsent(
						txCode, fixedAttrsModel)) != null) {
					fixedAttrsModel = txFixedAttrsModelTemp;
				}
			}
		}
		return fixedAttrsModel;
	}

	/**
	 * @函数名称:buildFixedAttrsModel
	 * @函数描述:构建定长属性对象
	 * @参数与返回说明:
	 * @param txModel
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	private FixedAttrsModel buildFixedAttrsModel(TxModel txModel)
			throws Exception {
		FixedAttrsModel fixedAttrsModel = new FixedAttrsModel();
		TxDef txDef = txModel.getTxDef();
		//设置更新时间
		fixedAttrsModel.setUpdateTm(txDef.getUpdateTm());
		//设置交易码
		fixedAttrsModel.setTxCode(txDef.getTxCode());
		//设置请求根节点
		fixedAttrsModel.setRequestRoot(txModel.getReqTxMsg().getMainMsgRoot());
		//设置响应根节点
		fixedAttrsModel.setResponseRoot(txModel.getResTxMsg().getMainMsgRoot());
		//设置请求数据域列表
		fixedAttrsModel.setRequestAttrList(getFixedAttrList(
				txModel.getReqTxMsgNodeList(), txModel.getTxMsgNodeAttrMap()));
		//设置响应数据域列表
		fixedAttrsModel.setResponseAttrList(getFixedAttrList(
				txModel.getResTxMsgNodeList(), txModel.getTxMsgNodeAttrMap()));
		return fixedAttrsModel;
	}

	/**
	 * @函数名称:getFixedAttrList
	 * @函数描述:从交易配置中将无序的属性按顺序排列
	 * @参数与返回说明:
	 * @param txMsgNodeList
	 * @param txMsgNodeAttrMap
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	private List<TxMsgNodeAttr> getFixedAttrList(List<TxMsgNode> txMsgNodeList,
			Map<Long, List<TxMsgNodeAttr>> txMsgNodeAttrMap) throws Exception {
		List<TxMsgNodeAttr> temp = new ArrayList<TxMsgNodeAttr>();
		if (txMsgNodeList != null && txMsgNodeList.size() > 0) {
			List<TxMsgNodeAttr> t = null;
			for (TxMsgNode TxMsgNode : txMsgNodeList) {//获取结点下的所有属性
				t = txMsgNodeAttrMap.get(TxMsgNode.getNodeId());
				if (t != null) {
					for (TxMsgNodeAttr t_i : t) {
						if (t_i.getAttrSeq() != null&&t_i.getAttrSeq()!=0) {// 顺序号不能为null
							temp.add(t_i);
						}
					}
				}
			}
			// 按照TxMsgNodeAttr attrSeq升序排序
			if (!temp.isEmpty()) {
				Comparator<TxMsgNodeAttr> comparator = new ComparatorAttr();
				Collections.sort(temp, comparator);
				return temp;
			} else {
				return temp;
			}

		} else {
			return temp;
		}
	}

	/**
	 * @函数名称:fixedAttrFormat
	 * @函数描述:定长报文字段格式化
	 * @参数与返回说明:
	 * @param text
	 *            数据
	 * @param leng
	 *            长度
	 * @param DataType
	 *            类型
	 * @return
	 * @算法描述:
	 */
	public abstract byte[] fixedAttrFormat(byte[] src, int leng,String DataType);

}

class ComparatorAttr implements Comparator<TxMsgNodeAttr> {
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(TxMsgNodeAttr arg0, TxMsgNodeAttr arg1) {
		//升序排列
		return arg0.getAttrSeq().intValue() - arg1.getAttrSeq().intValue();
	}

}
