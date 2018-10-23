/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.adapter.message.xml
 * @文件名：IntegrationLayer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:34:12
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.adapter.message.xml;

import com.ytec.mdm.base.bo.EcifData;
/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IntegrationLayer
 * @类描述：XML报文适配器接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:34:24   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:34:24
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IntegrationLayer {
	/**
	 * 集成层处理
	 * @param data
	 */
	public void process (EcifData data);
	/**
	 * 记录数据库日志
	 * @param resXml
	 */
	public void setTxLog (String resXml);
	
}
