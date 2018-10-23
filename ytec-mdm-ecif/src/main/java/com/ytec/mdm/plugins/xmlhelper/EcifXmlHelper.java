/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.xmlhelper
 * @�ļ�����EcifXmlHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-����3:36:14
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�EcifXmlHelper
 * @��������ECIF���İ�����
 * @��������:Ϊ����ṩ�����Զ�������ȡ��Ϣ���Բ�ͬ�Ľ��뱨�ģ���ڲ���Ҫ�޸�
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����3:36:14   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����3:36:14
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifXmlHelper {
	private static EcifXmlHelper ecifXmlHelper=new EcifXmlHelper();
	private Logger log = LoggerFactory.getLogger(EcifXmlHelper.class);
	/**
	 * @��������:funcMap
	 * @��������:��Ӧ�������ò�������
	 * @since 1.0.0
	 */
	private Map<String,IResponseXmlFun> funcMap=new HashMap<String,IResponseXmlFun>();
	/**
	 * @��������:responseXmlTemp
	 * @��������:��Ӧ����ģ��
	 * @since 1.0.0
	 */
	private String responseXmlTemp = null;
	/**
	 * @��������:requestPropertyList
	 * @��������:��������EcifData���Զ�Ӧ��ϵ
	 * @since 1.0.0
	 */
	private List<RequestProperty> requestPropertyList=new ArrayList<RequestProperty>();
	/**
	 * @��������:responseHead
	 * @��������:��Ӧͷ·��
	 * @since 1.0.0
	 */
	private String responseHead;
	/**
	 * @��������:responseBodyParent
	 * @��������:��Ӧ�常�ڵ�
	 * @since 1.0.0
	 */
	private String responseBodyParent;
	/**
	 * @��������:responseStatus
	 * @��������:��Ӧ��������
	 * @since 1.0.0
	 */
	private String responseStatus;
	
	/**
	 * @��������:responseBodyName
	 * @��������:��Ӧ������
	 * @since 1.0.0
	 */
	private String responseBodyName;
	

	/**
	 *@���캯�� 
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
	 * @��������:parseRequest
	 * @��������:����������
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * @throws Exception 
	 * @�㷨����:
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
					log.info("������ͷ������");
					throw new RequestIOException("������ͷ������");
				}
				ecifData.setRequestHeader(point);
			}else if("bodyNode".equals(popertyObj.getPropertyName())){
				point=(Element) doc.selectSingleNode(popertyObj.getPropertyXpath());
				if(point==null){
					log.info("�������岻����");
					throw new RequestIOException("�������岻����");
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
