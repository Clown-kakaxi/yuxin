/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.synchelper.util
 * @文件名：SyncRemoveElement.java
 * @版本信息：1.0.0
 * @日期：2014-2-24-下午1:55:54
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.synchelper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.plugins.synchelper.ISyncXmlFun;
import com.ytec.mdm.plugins.synchelper.SyncXmlObject;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SyncRemoveElement
 * @类描述：结点清理函数
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-24 下午1:55:54   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-24 下午1:55:54
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SyncRemoveElement implements ISyncXmlFun{
	private Logger log = LoggerFactory.getLogger(SyncRemoveElement.class);

	/* (non-Javadoc)
	 * @see com.ytec.mdm.plugins.synchelper.ISyncXmlFun#getValueByFun(java.lang.Object[])
	 */
	@Override
	public Object getValueByFun(Object[] arg) {
		// TODO Auto-generated method stub
		if(arg!=null && arg.length==2){
			SyncXmlObject syncXmlObject=(SyncXmlObject)arg[1];
			if("delete".equals(syncXmlObject.getOpType())){
				syncXmlObject.getPoint().detach();
			}else{
				log.warn("结点清理函数参数参数{}不支持",syncXmlObject.getOpType());
			}
			
		}else{
			log.warn("结点清理函数参数为空");
		}
		return null;
	}

}
