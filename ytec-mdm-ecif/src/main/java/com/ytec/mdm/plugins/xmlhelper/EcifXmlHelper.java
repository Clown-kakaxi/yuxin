/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.xmlhelper
 * @文件名：EcifXmlHelper.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-下午3:36:14
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.xmlhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.exception.RequestIOException;
import com.ytec.mdm.base.util.ReflectionUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：EcifXmlHelper
 * @类描述：ECIF报文帮助类
 * @功能描述:为借口提供报文自动解析提取信息，对不同的接入报文，借口不需要修改
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午3:36:14   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午3:36:14
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class EcifXmlHelper {
	private static EcifXmlHelper ecifXmlHelper=new EcifXmlHelper();
	private Logger log = LoggerFactory.getLogger(EcifXmlHelper.class);
	/**
	 * @属性名称:funcMap
	 * @属性描述:响应报文设置操作函数
	 * @since 1.0.0
	 */
	private Map<String,IResponseXmlFun> funcMap=new HashMap<String,IResponseXmlFun>();
	/**
	 * @属性名称:responseXmlTemp
	 * @属性描述:响应报文模板
	 * @since 1.0.0
	 */
	private String responseXmlTemp = null;
	/**
	 * @属性名称:requestPropertyList
	 * @属性描述:请求报文与EcifData属性对应关系
	 * @since 1.0.0
	 */
	private List<RequestProperty> requestPropertyList=new ArrayList<RequestProperty>();
	/**
	 * @属性名称:responseHead
	 * @属性描述:响应头路径
	 * @since 1.0.0
	 */
	private String responseHead;
	/**
	 * @属性名称:responseBodyParent
	 * @属性描述:响应体父节点
	 * @since 1.0.0
	 */
	private String responseBodyParent;
	/**
	 * @属性名称:responseStatus
	 * @属性描述:响应返回码结点
	 * @since 1.0.0
	 */
	private String responseStatus;
	
	/**
	 * @属性名称:responseBodyName
	 * @属性描述:响应体名称
	 * @since 1.0.0
	 */
	private String responseBodyName;
	

	/**
	 *@构造函数 
	 */
	public EcifXmlHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static EcifXmlHelper getInstance(){
		return ecifXmlHelper;
	}
	private void clearXmlFun(){
		this.funcMap.clear();
	}
	public void addXmlFun(String name,IResponseXmlFun fun){
		this.funcMap.put(name, fun);
	}
	
	public String getResponseXmlTemp() {
		return responseXmlTemp;
	}

	public void setResponseXmlTemp(String responseXmlTemp) {
		this.responseXmlTemp = responseXmlTemp;
	}
	
	private void clearRequestProperty(){
		requestPropertyList.clear();
	}
	public void addRequestProperty(RequestProperty p){
		requestPropertyList.add(p);
	}
	
	public void clear(){
		clearRequestProperty();
		clearXmlFun();
	}

	public String getResponseHead() {
		return responseHead;
	}

	public void setResponseHead(String responseHead) {
		this.responseHead = responseHead;
	}

	public String getResponseBodyParent() {
		return responseBodyParent;
	}

	public void setResponseBodyParent(String responseBodyParent) {
		this.responseBodyParent = responseBodyParent;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	public IResponseXmlFun getResponseXmlFun(String name){
		return funcMap.get(name);
	}
	
	public String getResponseBodyName() {
		return responseBodyName;
	}

	public void setResponseBodyName(String responseBodyName) {
		this.responseBodyName = responseBodyName;
	}

	/**
	 * @函数名称:parseRequest
	 * @函数描述:处理请求报文
	 * @参数与返回说明:
	 * 		@param ecifData
	 * @throws Exception 
	 * @算法描述:
	 */
	public void parseRequest(EcifData ecifData) throws Exception{
		Document doc=ecifData.getPrimalDoc();
		RequestProperty popertyObj=null;
		Element point=null;
		for(int i=0;i<requestPropertyList.size();i++){
			popertyObj=requestPropertyList.get(i);
			if("requestHeader".equals(popertyObj.getPropertyName())){
				point=(Element) doc.selectSingleNode(popertyObj.getPropertyXpath());
				if(point==null){
					log.info("请求报文头不存在");
					throw new RequestIOException("请求报文头不存在");
				}
				ecifData.setRequestHeader(point);
			}else if("bodyNode".equals(popertyObj.getPropertyName())){
				point=(Element) doc.selectSingleNode(popertyObj.getPropertyXpath());
				if(point==null){
					log.info("请求报文体不存在");
					throw new RequestIOException("请求报文体不存在");
				}
				ecifData.setBodyNode(point);
			}else{
				point=(Element) doc.selectSingleNode(popertyObj.getPropertyXpath());
				if(point!=null){
					ReflectionUtils.setFieldValue(ecifData, popertyObj.getPropertyName(), point.getTextTrim());
				}
			}
		}
		doc=null;
		popertyObj=null;
		point=null;
	}
	
	public Document parseResponse(EcifData ecifData) throws Exception{
		Document responseDoc = DocumentHelper.parseText(responseXmlTemp);
		responseDoc.accept(new ResponseXmlVisitor(ecifData));
		Element responseBodyParentEle=(Element) responseDoc.selectSingleNode(responseBodyParent);
		if (ecifData.getRepNode() != null) {
			responseBodyParentEle.add(ecifData.getRepNode());
		} else {
			responseBodyParentEle.addElement(responseBodyName);
		}
		return responseDoc;
	}
	
	
}
