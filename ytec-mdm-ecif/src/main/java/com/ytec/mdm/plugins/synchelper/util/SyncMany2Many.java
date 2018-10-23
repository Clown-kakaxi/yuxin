/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper.util
 * @�ļ�����SyncMany2one.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-24-����2:37:23
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;


import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SyncMany2Many
 * @����������Զ�ӳ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-24 ����2:37:23   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-24 ����2:37:23
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SyncMany2Many implements ISyncXmlFun {
	private Logger log = LoggerFactory.getLogger(SyncMany2Many.class);
	/**
	 * @��������:��Զ౨��ӳ��ת��
	 * @��������:��������Ϣ���ת���ɶ�����Ϣ���
	 * @�����뷵��˵��:
	 * 		@param Object[] arg arg[0] List<Element>  arg[1] Element
	 * 		@return Object
	 * @�㷨����:
	 */
	@Override
	public Object getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==2){
			List<Element> elementList=(List<Element>)arg[0];
			Element point=(Element)arg[1];
			for(Element el:elementList){
				point.add(el.detach());
			}
		}else{
			log.warn("��Զ�ӳ�亯����������");
			return "";
		}
		return "";
	}

}
