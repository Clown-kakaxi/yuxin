/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper
 * @�ļ�����SyncXmlObject.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-24-����1:33:14
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.util.List;

import org.dom4j.Element;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SyncXmlObject
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-24 ����1:33:14   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-24 ����1:33:14
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SyncXmlObject {
	private String opType;
	private String iSyncXmlFunName;
	private Element point;
	private List<Element> elementList;
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getiSyncXmlFunName() {
		return iSyncXmlFunName;
	}
	public void setiSyncXmlFunName(String iSyncXmlFunName) {
		this.iSyncXmlFunName = iSyncXmlFunName;
	}
	public Element getPoint() {
		return point;
	}
	public void setPoint(Element point) {
		this.point = point;
	}
	public List<Element> getElementList() {
		return elementList;
	}
	public void setElementList(List<Element> elementList) {
		this.elementList = elementList;
	}
	
	
}
