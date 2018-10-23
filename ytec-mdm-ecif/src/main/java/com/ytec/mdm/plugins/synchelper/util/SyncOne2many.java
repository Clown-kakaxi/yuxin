/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.synchelper.util
 * @�ļ�����SyncOne2many.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-24-����2:37:23
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;
import com.ytec.mdm.plugins.synchelper.SyncXmlObject;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�SyncOne2many
 * @��������һ�Զ�ӳ��
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
public class SyncOne2many implements ISyncXmlFun {
	private Logger log = LoggerFactory.getLogger(SyncOne2many.class);
	/* (non-Javadoc)
	 * @see com.ytec.mdm.plugins.synchelper.ISyncXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public Object getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==3){
			String txt=arg[0].toString();
			if(txt!=null&&!txt.isEmpty()){
				return txt;
			}else{
				boolean nullable=Boolean.parseBoolean(arg[1].toString());
				if(nullable){
					Element point=(Element)arg[2];
					Element parent=  point.getParent();
					SyncXmlObject syncXmlObject=new SyncXmlObject();
					syncXmlObject.setOpType("delete");
					syncXmlObject.setiSyncXmlFunName("SyncRemoveElement");
					syncXmlObject.setPoint((Element)parent);
					return syncXmlObject;
				}else{
					return "";
				}
			}
		}else{
			log.warn("һ�Զ�ӳ�亯����������");
			return "";
		}
	}

}
