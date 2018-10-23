/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper
 * @文件名：SyncXmlObject.java
 * @版本信息：1.0.0
 * @日期：2014-2-24-下午1:33:14
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper;

import java.util.List;

import org.dom4j.Element;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncXmlObject
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-24 下午1:33:14   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-24 下午1:33:14
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
