/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.normalsocket.coder
 * @文件名：IFixedRequestCoder.java
 * @版本信息：1.0.0
 * @日期：2014-4-17-下午2:31:25
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.normalsocket.coder;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IFixedRequestCoder
 * @类描述：定长报文与XML报文转换接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-17 下午2:31:25   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-17 下午2:31:25
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IFixedRequestCoder {
	/**
	 * @函数名称:requestFixedStringToXml
	 * @函数描述:定长报文转换成平面XML
	 * @参数与返回说明:
	 * 		@param data
	 * 		@throws Exception
	 * @算法描述:
	 */
	public void requestFixedStringToXml(EcifData data) throws Exception;
	/**
	 * @函数名称:responseXmlToFixedByte
	 * @函数描述:平面XML转换成定长字节流
	 * @参数与返回说明:
	 * 		@param data
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	public byte[] responseXmlToFixedByte(EcifData data) throws Exception;
	
	
	/**
	 * @函数名称:responseXmlToFixedString
	 * @函数描述:平面XML转换成定长报文
	 * @参数与返回说明:
	 * 		@param data
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	public String responseXmlToFixedString(EcifData data) throws Exception;
}
