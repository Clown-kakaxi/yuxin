/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper.util
 * @文件名：SyncOne2many.java
 * @版本信息：1.0.0
 * @日期：2014-2-24-下午2:37:23
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;
import com.ytec.mdm.plugins.synchelper.SyncXmlObject;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncOne2many
 * @类描述：一对多映射
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-24 下午2:37:23   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-24 下午2:37:23
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
			log.warn("一对多映射函数参数错误");
			return "";
		}
	}

}
